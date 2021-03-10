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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.tests.codegen.YarnWorkspaceProject;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.server.build.ConcurrentIndex;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * Base class for all tests converted from old Eclipse {@code *PluginTest}s or {@code *PluginUITest}s.
 */
public abstract class ConvertedIdeTest extends AbstractIdeTest {

	/**
	 * Asserts that all {@link IResourceDescription}s currently contained in the {@link ConcurrentIndex} have a correct
	 * TModule user data entry.
	 */
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

	/**
	 * Asserts that the file denoted by the given {@link FileURI} contains an issue of code
	 * {@link IssueCodes#CLF_DUP_MODULE CLF_DUP_MODULE}.
	 */
	protected void assertDuplicateModuleIssue(FileURI fileURI, String duplicateProjectName,
			String duplicateModulePathAndNameWithExt) {
		Multimap<FileURI, Diagnostic> issues = getIssues();
		Collection<Diagnostic> issuesInFile = issues.get(fileURI);
		for (Diagnostic d : issuesInFile) {
			String msg = d.getMessage();
			boolean isDuplicateModuleIssue = msg.startsWith("A duplicate module C is also defined in ")
					&& msg.endsWith("/" + duplicateProjectName + "/" + duplicateModulePathAndNameWithExt + ".");
			if (isDuplicateModuleIssue) {
				return; // success!
			}
		}
		Assert.fail("expected duplicate module issue not found in module: " + fileURI);
	}

	/** Same as {@link #importProband(File, Collection)}, but without importing any n4js-libs. */
	protected void importProband(File probandFolder) {
		importProband(probandFolder, Collections.emptyList());
	}

	/**
	 * Creates an empty yarn workspace project with
	 * {@link TestWorkspaceManager#createTestOnDisk(org.eclipse.xtext.xbase.lib.Pair...) the test workspace manager},
	 * starts the LSP server, and then imports all projects in the given proband folder. Each sub-folder of the given
	 * proband folder is assumed to be a project and will be imported.
	 */
	protected List<N4JSProjectName> importProband(File probandFolder, Collection<N4JSProjectName> n4jsLibs) {
		if (testWorkspaceManager.isCreated()) {
			throw new IllegalStateException("the test workspace has already been created");
		}

		testWorkspaceManager.createTestOnDisk(); // this will create an empty yarn workspace
		startAndWaitForLspServer();
		// import the projects
		final List<N4JSProjectName> importedProjects = new ArrayList<>();
		boolean needToCopyLibs = true;
		for (final File child : probandFolder.listFiles()) {
			if (child.isDirectory()) {
				if (child.getName().startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX)) {
					for (final File grandChild : child.listFiles()) {
						if (grandChild.isDirectory()) {
							final N4JSProjectName name = new N4JSProjectName(child.getName(), grandChild.getName());
							importProject(probandFolder, name, needToCopyLibs ? n4jsLibs : Collections.emptyList());
							importedProjects.add(name);
							needToCopyLibs = false;
						}
					}
				} else {
					final N4JSProjectName name = new N4JSProjectName(child.getName());
					importProject(probandFolder, name, needToCopyLibs ? n4jsLibs : Collections.emptyList());
					importedProjects.add(name);
					needToCopyLibs = false;
				}
			}
		}
		cleanBuildAndWait();
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
	protected File importProject(File probandsFolder, N4JSProjectName projectName) {
		return importProject(probandsFolder, projectName, Collections.emptyList());
	}

	/**
	 * Imports a project into the running JUnit test workspace. Usage:
	 *
	 * <pre>
	 * IProject project = ProjectTestsUtils.importProject(new File(&quot;probands&quot;), &quot;TestProject&quot;, n4jsLibs);
	 * </pre>
	 *
	 * @param probandsFolder
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
	protected File importProject(File probandsFolder, N4JSProjectName projectName,
			Collection<N4JSProjectName> n4jsLibs) {

		if (!testWorkspaceManager.isCreated()) {
			throw new IllegalStateException("the test workspace is not yet created");
		}

		Path projectNameAsRelativePath = projectName.getProjectNameAsRelativePath();
		File projectSourceFolder = probandsFolder.toPath().resolve(projectNameAsRelativePath).toFile();
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}

		File projectFolder = projectName.getLocation(getProjectLocation().toPath());

		installN4jsLibs(projectName, n4jsLibs.toArray(new N4JSProjectName[0]));

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

		// create symbolic link in node_modules folder of root project (if necessary)
		if (isYarnWorkspace()) {
			try {
				File nodeModulesFolder = getNodeModulesFolder(projectName);
				Path from = nodeModulesFolder.toPath().resolve(projectNameAsRelativePath);
				Files.createDirectories(from.getParent());
				Files.createSymbolicLink(from, projectFolder.toPath());
			} catch (IOException e) {
				throw new WrappedException(
						"exception while creating symbolic link from node_modules folder to project folder", e);
			}
		}

		// notify LSP server
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(FluentIterable.from(filesCopied)
				.transform(path -> new FileURI(path.toFile()))
				.transform(fileURI -> new FileEvent(fileURI.toString(), FileChangeType.Created))
				.toList());
		languageServer.didChangeWatchedFiles(params);
		joinServerRequests();

		return projectFolder;
	}

	/**
	 * Executes mangelhaft for the given output file. The {@code outputFilePath} should be a relative path from the
	 * project's root folder.
	 */
	protected String runMangelhaft(String projectName, Optional<String> outputFilePath, boolean quiet) {
		File workingDir = getProjectRootForImportedProject(projectName);

		List<String> args = new ArrayList<>();
		if (quiet) {
			args.add("-q");
		}
		if (outputFilePath.isPresent()) {
			args.add("-f");
			args.add("/" + outputFilePath.get() + "/");
		}

		ProcessResult result = new CliTools().runNodejs(workingDir.toPath(),
				Path.of("../../node_modules/n4js-mangelhaft-cli/bin/n4js-mangelhaft-cli.js"),
				args.toArray(String[]::new));

		String output = result.getStdOut();
		output = output.replaceAll("\\e\\[\\d+m", "");
		return output;
	}

	/**
	 * Same as {@link #getProjectRoot(String)}, but works for projects imported into the test workspace after its
	 * creation.
	 */
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

	/** Resets the last modified time of the given file to the epoch. */
	protected long resetFileModificationTimeStamp(FileURI fileURI) {
		FileTime epoch = FileTime.fromMillis(0);
		try {
			Files.setLastModifiedTime(fileURI.toPath(), epoch);
		} catch (IOException e) {
			throw new RuntimeException("exception while resetting last modified time of: " + fileURI, e);
		}
		return epoch.toMillis();
	}

	/** Returns the last modified time of the given file. */
	protected long getFileModificationTimeStamp(FileURI fileURI) {
		try {
			return Files.getLastModifiedTime(fileURI.toPath()).toMillis();
		} catch (IOException e) {
			throw new RuntimeException("exception while reading last modified time of: " + fileURI, e);
		}
	}
}
