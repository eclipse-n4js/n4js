/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.tests.codegen.YarnWorkspaceProject;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Base class for all tests converted from old Eclipse {@code *PluginTest}s or {@code *PluginUITest}s.
 */
public abstract class ConvertedIdeTest extends AbstractIdeTest {

	@Inject
	private ConcurrentIndex concurrentIndex;

	protected IResourceDescriptions getXtextIndex() {
		return new ResourceDescriptionsData(FluentIterable.from(concurrentIndex.entries())
				.transformAndConcat(e -> e.getValue().getAllResourceDescriptions()));
	}

	protected void assertAllDescriptionsHaveModuleData() throws IOException {
		Iterable<IResourceDescription> descriptions = IterableExtensions.flatMap(concurrentIndex.entries(),
				e -> e.getValue().getAllResourceDescriptions());
		for (IResourceDescription description : descriptions) {
			if (N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(description.getURI().fileExtension())) {
				IEObjectDescription moduleDescription = IterableExtensions
						.head(description.getExportedObjectsByType(TypesPackage.Literals.TMODULE));
				Assert.assertNotNull(description.getURI().toString(), moduleDescription);
				String moduleAsString = UserDataMapper.getDeserializedModuleFromDescriptionAsString(moduleDescription,
						description.getURI());
				Assert.assertNotNull(description.getURI().toString(), moduleAsString);
			}
		}
	}

	/** Same as {@link #importProband(File, Collection)}, but without importing any n4js-libs. */
	protected void importProband(File probandFolder) {
		importProband(probandFolder, Collections.emptyList());
	}

	protected List<N4JSProjectName> importProband(File probandFolder, Collection<N4JSProjectName> n4jsLibs) {
		testWorkspaceManager.createTestOnDisk();
		startAndWaitForLspServer();
		// import the projects
		final List<N4JSProjectName> importedProjects = new ArrayList<>();
		boolean needToCopyLibs = true;
		for (final File child : probandFolder.listFiles()) {
			if (child.isDirectory()) {
				final N4JSProjectName name = new N4JSProjectName(child.getName());
				importProject(probandFolder, name,
						needToCopyLibs ? n4jsLibs : Collections.emptyList());
				importedProjects.add(name);
				needToCopyLibs = false;
			}
		}
		joinServerRequests();
		// ensure that all projects have been imported properly
		final Set<String> expectedProjects = importedProjects.stream()
				.map(N4JSProjectName::getRawName).collect(Collectors.toSet());
		final Set<String> actualProjects = concurrentIndex.entries().stream()
				.map(Entry::getKey).collect(Collectors.toSet());
		final Set<String> missingProjects = new HashSet<>(Sets.difference(expectedProjects, actualProjects));
		Assert.assertTrue("some projects were not correctly imported: " + missingProjects, missingProjects.isEmpty());
		return importedProjects;
	}

	/** Same as {@link #importProject(File, N4JSProjectName, Collection)}, but without installing any n4js libraries. */
	protected File importProject(File probandFolder, N4JSProjectName projectName) {
		return importProject(probandFolder, projectName, Collections.emptyList());
	}

	/**
	 * Imports a project into the running JUnit test workspace. Usage:
	 *
	 * <pre>
	 * IProject project = ProjectTestsUtils.importProject(new File(&quot;probands&quot;), &quot;TestProject&quot;, n4jsLibs);
	 * </pre>
	 *
	 * @param probandFolder
	 *            the parent folder in which the test project is found
	 * @param projectName
	 *            the name of the test project, must be folder contained in probandsFolder
	 * @param n4jsLibs
	 *            names of N4JS libraries to install from the local <code>n4js-libs</code> top-level folder (see
	 *            {@link N4jsLibsAccess#installN4jsLibs(Path, boolean, boolean, boolean, N4JSProjectName...)}).
	 * @return the imported project
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/12484128/how-do-i-import-an-eclipse-project-from-a-zip-file-programmatically">
	 *      stackoverflow: from zip</a>
	 */
	protected File importProject(File probandFolder, N4JSProjectName projectName,
			Collection<N4JSProjectName> n4jsLibs) {

		File projectSourceFolder = new File(probandFolder, projectName.getRawName());
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}

		File projectLocation = getProjectLocation();
		final File projectFolder = new File(projectLocation, projectName.getRawName());

		// install n4js-libs (if desired)
		if (!n4jsLibs.isEmpty()) {
			final File nodeModulesFolder = isYarnWorkspace()
					? new File(new File(getRoot(), TestWorkspaceManager.YARN_TEST_PROJECT), N4JSGlobals.NODE_MODULES)
					: new File(projectFolder, N4JSGlobals.NODE_MODULES);
			nodeModulesFolder.mkdirs();
			try {
				N4jsLibsAccess.installN4jsLibs(nodeModulesFolder.toPath(), true, false, false,
						n4jsLibs.toArray(new N4JSProjectName[0]));
			} catch (IOException e) {
				throw new RuntimeException("unable to install n4js-libs from local checkout", e);
			}
		}

		// copy project into workspace
		// (need to do that manually to properly handle NPM scopes, because the Eclipse import functionality won't put
		// those projects into an "@myScope" subfolder)
		final List<Path> filesCopied = new ArrayList<>();
		try {
			projectFolder.mkdirs();
			FileCopier.copy(projectSourceFolder.toPath(), projectFolder.toPath(),
					path -> filesCopied.add(path));
		} catch (IOException e) {
			throw new WrappedException("exception while copying project into workspace", e);
		}

		// notify LSP server
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(FluentIterable.from(filesCopied)
				.transform(path -> new FileURI(path.toFile()))
				.transform(fileURI -> new FileEvent(fileURI.toString(), FileChangeType.Created))
				.toList());
		languageServer.didChangeWatchedFiles(params);

		// load actual project name from "package.json" file (might be different in case of NPM scopes)
		// File packageJsonFile = new File(projectTargetFolder, N4JSGlobals.PACKAGE_JSON);
		// JsonElement root = JsonUtils.loadJson(packageJsonFile.toPath());
		// String projectNameFromDotProjectFile = JsonUtils.getDeepAsString(root, PackageJsonProperties.NAME.name);

		return projectFolder;
	}

	protected String runMangelhaft(String projectName, Optional<String> moduleName, boolean quiet) {
		File workingDir = getProjectRootForImportedProject(projectName);

		List<String> args = new ArrayList<>();
		if (quiet) {
			args.add("-q");
		}
		if (moduleName.isPresent()) {
			args.add("-f");
			args.add("/" + moduleName.get() + "/");
		}

		ProcessResult result = new CliTools().runNodejs(workingDir.toPath(),
				Path.of("../../node_modules/n4js-mangelhaft-cli/bin/n4js-mangelhaft-cli.js"),
				args.toArray(String[]::new));

		String output = result.getStdOut();
		output = output.replaceAll("\\e\\[\\d+m", "");
		return output;
	}

	// TODO align this with TestWorkspaceManager#getProjectRoot()
	protected File getProjectRootForImportedProject(String projectName) {
		if (isYarnWorkspace()) {
			return getRoot().toPath()
					.resolve(TestWorkspaceManager.YARN_TEST_PROJECT)
					.resolve(YarnWorkspaceProject.PACKAGES)
					.resolve(projectName).toFile();
		}
		return new File(getRoot(), projectName);
	}
}
