/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods.output;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newLinkedList;
import static org.eclipse.n4js.utils.io.FileUtils.createDirectory;
import static org.eclipse.n4js.utils.io.FileUtils.createNestedDirectory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.generator.AbstractSubGenerator;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xpect.common.ResourceTweaker;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;

import com.google.inject.Inject;

/**
 * Xpect helper that allows to compile and execute resources on demand in xpect tests. Uses
 * {@link EcmaScriptSubGenerator} for compilation and {@link CliTools} for execution.
 */
public class XpectN4JSES5TranspilerHelper {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private XpectN4JSES5GeneratorHelper xpectGenerator;

	private ReadOutConfiguration readOutConfiguration;

	/**
	 * Compile provided resource, execute in Node.js and return execution output.
	 *
	 * @param resource
	 *            Script to execute
	 * @param init
	 *            xpect setup-init
	 * @param fileSetupContext
	 *            xpect injected
	 * @param decorateStdStreams
	 *            false-just connect stdout+stderr or errors to resulting string, true - decorate the streams with "<==
	 *            stdout: ..."
	 * @param resourceTweaker
	 *            - resource-modifier like QuickFix application, can be null
	 * @return output streams concatenated
	 */
	public String doCompileAndExecute(final XtextResource resource,
			org.eclipse.xpect.setup.ISetupInitializer<Object> init,
			FileSetupContext fileSetupContext, boolean decorateStdStreams, ResourceTweaker resourceTweaker,
			GeneratorOption[] options) throws IOException {

		Path temporaryRoot = Files.createTempDirectory("n4jsXpectOutputTest");
		try {
			return doCompileAndExecute((N4JSResource) resource, init, fileSetupContext, decorateStdStreams,
					resourceTweaker, options, temporaryRoot);
		} finally {
			FileDeleter.deleteOnExit(temporaryRoot);
		}
	}

	/**
	 *
	 * @param root
	 *            location where all temporary files should be placed.
	 */
	private String doCompileAndExecute(final N4JSResource resource,
			org.eclipse.xpect.setup.ISetupInitializer<Object> init,
			FileSetupContext fileSetupContext, boolean decorateStdStreams, ResourceTweaker resourceTweaker,
			GeneratorOption[] options,
			Path root) throws IOException {

		// Apply some modification to the resource here.
		if (resourceTweaker != null) {
			resourceTweaker.tweak(resource);
		}

		loadXpectConfiguration(init, fileSetupContext);

		// In the non-GUI case, we need to calculate dependencies etc. manually
		final Iterable<Resource> dependencies = from(getDependentResources());
		boolean replaceQuotes = false;

		// compile all file resources
		StringBuilder errorResult = new StringBuilder();

		Script testScript = resource.getScript();
		TModule testModule = resource.getModule();

		Path projectFolder = root.resolve(testModule.getPackageName().replace("/", File.separator));
		createPackageJson(projectFolder);

		// replace n4jsd resource with provided js resource
		for (final Resource dep : from(dependencies).filter(r -> !r.getURI().equals(resource.getURI()))) {
			if (N4JSGlobals.N4JSD_FILE_EXTENSION.equalsIgnoreCase(URIUtils.fileExtension(dep.getURI()))) {
				compileImplementationOfN4JSDFile(projectFolder, errorResult, dep, options, replaceQuotes);
			} else if (xpectGenerator.isCompilable(dep, errorResult)) {
				final Script script = (Script) dep.getContents().get(0);
				createTempJsFileWithScript(projectFolder, script, options, replaceQuotes);
			}
		}

		if (errorResult.length() != 0) {
			return errorResult.toString();
		}

		// No error so far
		// determine module to run
		createTempJsFileWithScript(projectFolder, testScript, options, replaceQuotes);
		String fileToRun = jsModulePathToRun(testScript);

		// Not in UI case, hence manually set up the resources
		String artificialPackageName = testScript.getModule().getPackageName();

		// provide n4js-runtime in the version of the current build
		N4jsLibsAccess.installN4jsLibs(
				root.resolve(artificialPackageName).resolve(N4JSGlobals.NODE_MODULES),
				true, false, false,
				N4JSGlobals.N4JS_RUNTIME);

		Path artificialProjectPath = root.resolve(artificialPackageName);
		return doExecute(artificialProjectPath, artificialProjectPath.resolve(fileToRun), decorateStdStreams);
	}

	private String doExecute(Path workingDir, Path fileToRun, boolean decorateStdStreams, String... options) {
		ProcessResult processResult = new CliTools().nodejsRun(workingDir, fileToRun, options);
		StringBuffer output = new StringBuffer();
		output.append(processResult.getStdOut().trim());
		String errOut = processResult.getErrOut().trim();
		if (!errOut.isEmpty()) {
			if (decorateStdStreams) {
				output.append("=== stderr ===");
			}
			output.append(errOut);
		}
		return output.toString();
	}

	/**
	 * Load Xpect configuration
	 */
	private void loadXpectConfiguration(
			org.eclipse.xpect.setup.ISetupInitializer<Object> init, FileSetupContext fileSetupContext) {
		readOutConfiguration = new ReadOutResourceSetConfiguration(fileSetupContext, workspaceAccess);
		init.initialize(readOutConfiguration);
	}

	/**
	 * Get dependent resources. Before calling this method make sure xpect configuration has been loaded.
	 */
	private List<Resource> getDependentResources() {
		Assert.assertNotNull("Xpect configuration has not been loaded!", readOutConfiguration);

		List<Resource> resourcesFromXpectConfiguredResourceSet = new ArrayList<>();
		resourcesFromXpectConfiguredResourceSet = readOutConfiguration.getResources();
		return Collections.unmodifiableList(new ArrayList<>(resourcesFromXpectConfiguredResourceSet));
	}

	private void compileImplementationOfN4JSDFile(final Path projectFolder, StringBuilder errorResult, Resource dep,
			GeneratorOption[] options,
			boolean replaceQuotes) {

		Script script = (Script) dep.getContents().get(0);
		if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(script)) {
			return;
		}

		Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot> pair = workspaceAccess
				.findProjectAndSourceFolderContaining(dep, dep.getURI());
		N4JSProjectConfigSnapshot project = pair.getKey();
		N4JSSourceFolderSnapshot source = pair.getValue();
		if (project != null && source != null) {
			for (N4JSSourceFolderSnapshot c : project.getSourceFolders()) {
				if (c.isExternal()) {
					String sourceRelativePath = dep.getURI().toString()
							.replace(source.getPathAsFileURI().toString(), "");
					String[] potentialExternalSourceRelativeURISegments = null;
					String potentialExternalSourceRelativePath = sourceRelativePath.replace(
							"." + N4JSGlobals.N4JSD_FILE_EXTENSION, "." + N4JSGlobals.JS_FILE_EXTENSION);
					potentialExternalSourceRelativeURISegments = URI.createURI(potentialExternalSourceRelativePath)
							.segments();

					if (potentialExternalSourceRelativeURISegments != null) {
						URI potentialExternalSourceURI = c.getPathAsFileURI().appendSegments(
								potentialExternalSourceRelativeURISegments).toURI();
						try {
							Resource externalDep = dep.getResourceSet().getResource(potentialExternalSourceURI, true);
							script = (Script) externalDep.getContents().get(0);
							if (xpectGenerator.isCompilable(externalDep, errorResult)) {
								createTempJsFileWithScript(projectFolder, script, options, replaceQuotes);
							}
						} catch (Exception e) {
							throw new RuntimeException("Couldn't load " + potentialExternalSourceURI + ".", e);
						}
					}
				}
			}
		}
	}

	private File createTempJsFileWithScript(final Path projectFolder, final Script script, GeneratorOption[] options,
			final boolean replaceQuotes)
			throws IOException {
		// Compile script to get file content.
		final String content = xpectGenerator.compile(script, options, replaceQuotes);

		String srcgenSegments = getCompiledFileBasePath(script);
		Path srcgenPath = createNestedDirectory(projectFolder, srcgenSegments);

		// Get folder structure from qualified names.
		final LinkedList<String> segments = moduleQualifiedNameSegments(script);
		// Pop the file name from the segments.
		final String fileName = segments.removeLast();
		// Recursively create temporary folder structure.
		for (final String folderName : segments) {
			srcgenPath = createDirectory(srcgenPath, folderName);
		}

		final File file = new File(srcgenPath.toFile(), fileName + "." + N4JSGlobals.JS_FILE_EXTENSION);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new RuntimeException("Exception when creating new file: " + file);
			}
		}

		try (final PrintWriter pw = new PrintWriter(file)) {
			pw.print(content); // Make sure not to append but override content.
		}

		return file;
	}

	private void createPackageJson(Path folder) throws IOException {
		String packageName = ProjectDescriptionUtils.deriveN4JSPackageNameFromPath(folder);
		Files.createDirectories(folder);
		Files.writeString(folder.resolve(N4JSGlobals.PACKAGE_JSON), "{\n"
				+ "  \"name\": \"" + packageName + "\",\n"
				+ "  \"version\": \"0.0.1\",\n"
				+ "  \"type\": \"module\"\n"
				+ "}\n");
	}

	/** Splits up the script's qualified name along the delimiters. */
	private LinkedList<String> moduleQualifiedNameSegments(final Script script) {
		return newLinkedList(on(N4JSQualifiedNameConverter.DELIMITER).split(script.getModule().getQualifiedName()));
	}

	/**
	 * Composes the name of the module as seen from the root folder of the containing npm package. Is uses '/' as the
	 * path-separator, since this path is consumed from js-code.
	 */
	private String jsModulePathToRun(Script script) {
		StringJoiner sj = new StringJoiner("/");
		sj.add(getCompiledFileBasePath(script));
		moduleQualifiedNameSegments(script).forEach(sj::add);
		return sj.toString();
	}

	private String getCompiledFileBasePath(final Script script) {
		String path = N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT;

		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(script);
		if (project != null) {
			path = AbstractSubGenerator.calculateProjectBasedOutputDirectory(project, false);
		}

		return path;
	}

}
