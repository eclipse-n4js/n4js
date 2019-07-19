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
package org.eclipse.n4js.tests.util;

import static com.google.common.base.Predicates.alwaysTrue;
import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.core.runtime.jobs.Job.getJobManager;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addNature;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.monitor;
import static org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil.createSimpleProject;
import static org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil.createSubFolder;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.packagejson.PackageJsonBuilder;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.test.helper.hlc.N4jsLibsAccess;
import org.eclipse.n4js.ui.editor.N4JSDirtyStateEditorSupport;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.TimeoutRuntimeException;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.ui.MarkerTypes;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

/**
 * Utilities for for tests that setup / assert N4JS projects.
 */
public class ProjectTestsUtils {

	/**
	 * Version used for dummy "n4js-runtime" packages created with method {@link #createDummyN4JSRuntime(Path)}.
	 */
	public static final String N4JS_RUNTIME_DUMMY_VERSION = "0.0.1-dummy";

	/** Default wait time used in waiting for jobs. */
	public static final long MAX_WAIT_2_MINUTES = 1000 * 60 * 2;
	/** Default interval used to check state of jobs. */
	public static final long CHECK_INTERVAL_100_MS = 100L;

	private static Logger LOGGER = Logger.getLogger(ProjectTestsUtils.class);

	static private boolean allPredicatesApply(Predicate<IMarker>[] preds, IMarker marker) {
		for (Predicate<IMarker> p : preds) {
			if (!p.apply(marker)) {
				return false;
			}
		}
		return true;
	}

	/** Same as {@link #importProject(File, N4JSProjectName, Collection)}, but without installing any n4js libraries. */
	public static IProject importProject(File probandsFolder, N4JSProjectName projectName) throws CoreException {
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
	public static IProject importProject(File probandsFolder, N4JSProjectName projectName,
			Collection<N4JSProjectName> n4jsLibs)
			throws CoreException {
		return importProject(probandsFolder, projectName, true, true, n4jsLibs);
	}

	/**
	 * Same as {@link #importProject(File, N4JSProjectName)}, but won't enforce the convention that ".project" files
	 * should be named "_project" in the proband folder. This should only be used as a rare exception when tests import
	 * projects from Git repositories other than the N4JS or N4JS-N4 source repositories (e.g. for integration tests).
	 */
	public static IProject importProjectFromExternalSource(File probandsFolder, N4JSProjectName projectName,
			boolean copyIntoWorkspace) throws Exception {
		return importProject(probandsFolder, projectName, copyIntoWorkspace, false, Collections.emptyList());
	}

	/**
	 * Creates a new workspace project with the given project location in the probands folder (cf.
	 * {@code probandsName}).
	 *
	 * Creates a new workspace project with name {@code workspaceName}. Note that the project folder (based on the given
	 * project location) and the project name may differ.
	 *
	 * Does not copy the proband resources into the workspaces, but keeps the project files at the given location (cf.
	 * create new project with non-default location outside of workspace)
	 *
	 * @throws CoreException
	 *             If the project creation does not succeed.
	 */
	public static IProject createProjectWithLocation(File probandsFolder, String projectLocationFolder,
			String workspaceName) throws CoreException {
		File projectSourceFolder = new File(probandsFolder, projectLocationFolder);
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}
		prepareDotProject(projectSourceFolder);

		IProgressMonitor monitor = new NullProgressMonitor();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(workspaceName);

		workspace.run((mon) -> {
			IProjectDescription newProjectDescription = workspace.newProjectDescription(workspaceName);
			final IPath absoluteProjectPath = new org.eclipse.core.runtime.Path(projectSourceFolder.getAbsolutePath());
			newProjectDescription.setLocation(absoluteProjectPath);
			project.create(newProjectDescription, mon);
			project.open(mon);
		}, monitor);

		return project;

	}

	private static IProject importProject(File probandsFolder, N4JSProjectName projectName, boolean copyIntoWorkspace,
			boolean prepareDotProject, Collection<N4JSProjectName> n4jsLibs) throws CoreException {
		File projectSourceFolder = new File(probandsFolder, projectName.getRawName());
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}

		if (prepareDotProject) {
			prepareDotProject(projectSourceFolder);
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// copy project into workspace
		// (need to do that manually to properly handle NPM scopes, because the Eclipse import functionality won't put
		// those projects into an "@myScope" subfolder)
		final File projectTargetFolder;
		if (copyIntoWorkspace) {
			File workspaceFolder = workspace.getRoot().getLocation().toFile();
			projectTargetFolder = new File(workspaceFolder, projectName.getRawName());
			try {
				projectTargetFolder.mkdirs();
				FileCopier.copy(projectSourceFolder.toPath(), projectTargetFolder.toPath());
			} catch (IOException e) {
				throw new WrappedException("exception while copying project into workspace", e);
			}
		} else {
			// not copying, so source and target folders are identical:
			projectTargetFolder = projectSourceFolder;
		}

		// install n4js-libs (if desired)
		if (!n4jsLibs.isEmpty()) {
			try {
				N4jsLibsAccess.installN4jsLibs(
						projectTargetFolder.toPath().resolve(N4JSGlobals.NODE_MODULES),
						true, false, false,
						n4jsLibs.toArray(new N4JSProjectName[0]));
			} catch (IOException e) {
				throw new RuntimeException("unable to install n4js-libs from local checkout", e);
			}
		}

		// load actual project name from ".project" file (might be different in case of NPM scopes)
		File dotProjectFile = new File(projectTargetFolder, ".project");
		if (!dotProjectFile.exists()) {
			throw new IllegalArgumentException(
					"project to import does not contain a .project file: " + projectTargetFolder);
		}
		IProjectDescription description = workspace
				.loadProjectDescription(new org.eclipse.core.runtime.Path(dotProjectFile.getAbsolutePath()));
		String projectNameFromDotProjectFile = description.getName();

		IProject project = workspace.getRoot().getProject(projectNameFromDotProjectFile);

		IProgressMonitor monitor = new NullProgressMonitor();
		workspace.run((mon) -> {
			// NOTE: the following two lines would create a new project description and make Eclipse copy the projects
			// and its contents into the workspace:
			// IProjectDescription newDescription = workspace.newProjectDescription(projectNameFromDotProjectFile);
			// project.create(newDescription, mon);
			// However, we do not want that (see above); instead, we create a project from the description we loaded
			// above:
			project.create(description, mon);
			project.open(mon);
			if (!project.getLocation().toFile().exists()) {
				throw new IllegalArgumentException("test project not correctly created in " + project.getLocation());
			}

			IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
				@Override
				public String queryOverwrite(String file) {
					return ALL;
				}
			};
			ImportOperation importOperation = new ImportOperation(project.getFullPath(), projectTargetFolder,
					FileSystemStructureProvider.INSTANCE, overwriteQuery);
			importOperation.setCreateContainerStructure(false);
			try {
				importOperation.run(mon);
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, monitor);

		waitForAllJobs();
		return project;
	}

	/**
	 * Same as {@link #importYarnWorkspace(LibraryManager, File, N4JSProjectName, Predicate, Collection)}, but imports
	 * all packages contained in subfolder "packages" of the yarn workspace and does not install any N4JS libraries.
	 */
	public static IProject importYarnWorkspace(LibraryManager libraryManager, File parentFolder,
			N4JSProjectName yarnProjectName)
			throws CoreException {
		return importYarnWorkspace(libraryManager, parentFolder, yarnProjectName, Predicates.alwaysTrue(),
				Collections.emptyList());
	}

	/**
	 * Same as {@link #importYarnWorkspace(LibraryManager, File, N4JSProjectName, Predicate, Collection)}, but imports
	 * all packages contained in subfolder "packages" of the yarn workspace.
	 */
	public static IProject importYarnWorkspace(LibraryManager libraryManager, File parentFolder,
			N4JSProjectName yarnProjectName,
			Collection<N4JSProjectName> n4jsLibs) throws CoreException {
		return importYarnWorkspace(libraryManager, parentFolder, yarnProjectName, Predicates.alwaysTrue(), n4jsLibs);
	}

	/**
	 * Imports the given yarn workspace project as an Eclipse project into the Eclipse workspace. Also imports (by
	 * reference) those projects located in the subfolder 'packages' for which the given predicate returns
	 * <code>true</code>.
	 *
	 * @param libraryManager
	 *            library manager instance.
	 * @param parentFolder
	 *            folder containing the test data.
	 * @param yarnProjectName
	 *            name of the folder containing the yarn workspace project.
	 * @param packagesToImport
	 *            predicate telling whether a given package contained in the 'packages' subfolder of the yarn workspace
	 *            should be imported as well (the predicate's argument is the package name).
	 * @param n4jsLibs
	 *            names of N4JS libraries to install from the local <code>n4js-libs</code> top-level folder (see
	 *            {@link N4jsLibsAccess#installN4jsLibs(Path, boolean, boolean, boolean, N4JSProjectName...)}).
	 * @return yarn workspace project
	 */
	public static IProject importYarnWorkspace(LibraryManager libraryManager, File parentFolder,
			N4JSProjectName yarnProjectName,
			Predicate<N4JSProjectName> packagesToImport, Collection<N4JSProjectName> n4jsLibs) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject yarnProject = ProjectTestsUtils.importProject(parentFolder, yarnProjectName);

		IPath yarnPath = yarnProject.getLocation();
		IPath yarnPackagesPath = yarnPath.append("packages");
		for (String yarnPackageName : yarnPackagesPath.toFile().list()) {
			IPath packagePath = yarnPackagesPath.append(yarnPackageName);
			if (yarnPackageName.startsWith("@")) {
				for (String scopedPackageName : packagePath.toFile().list()) {
					IPath scopedPackagePath = packagePath.append(scopedPackageName);
					if (packagesToImport.apply(new N4JSProjectName(yarnPackageName + '/' + scopedPackageName))) {
						importProjectNotCopy(workspace, scopedPackagePath.toFile(), new NullProgressMonitor());
					}
				}
			} else {
				if (packagesToImport.apply(new N4JSProjectName(yarnPackageName))) {
					importProjectNotCopy(workspace, packagePath.toFile(), new NullProgressMonitor());
				}
			}
		}

		if (!n4jsLibs.isEmpty()) {
			try {
				N4jsLibsAccess.installN4jsLibs(
						yarnPackagesPath.toFile().toPath(),
						true, false, false,
						n4jsLibs.toArray(new N4JSProjectName[0]));
				yarnProject.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (IOException e) {
				throw new RuntimeException("unable to install n4js-libs from local checkout", e);
			}
		}

		if (libraryManager != null) {
			waitForAllJobs();
			libraryManager.runNpmYarnInstall(new PlatformResourceURI(yarnProject), new NullProgressMonitor());
		}
		waitForAllJobs();
		waitForAutoBuild();
		return yarnProject;
	}

	/**
	 * Imports a project by reference into the workspace
	 *
	 * @return the created project
	 */
	public static IProject importProjectNotCopy(IWorkspace workspace, File rootFolder, IProgressMonitor progressMonitor)
			throws CoreException {
		IPath path = new org.eclipse.core.runtime.Path(new File(rootFolder, "_project").getAbsolutePath());
		IProjectDescription desc = workspace.loadProjectDescription(path);
		IProject project = workspace.getRoot().getProject(desc.getName());
		project.create(desc, progressMonitor);
		project.open(progressMonitor);
		return project;
	}

	/**
	 * For Test-mocks copies over "_project"-files to ".project" files and supplies a delete-on-exit shutdown hook for
	 * them.
	 *
	 * @param projectSourceFolder
	 *            folder potentially containing "_project", that is a potential workspace-project
	 * @returns <code>true</code> if a .project file was created. <code>false</code> otherwise.
	 */
	public static boolean prepareDotProject(File projectSourceFolder) {
		File dotProject = new File(projectSourceFolder, ".project");
		File dotProjectTemplate = new File(projectSourceFolder, "_project");

		if (dotProject.exists()) {
			if (!dotProjectTemplate.exists()) {
				// err if giving direct .project files cluttering the developers workspace
				throw new IllegalArgumentException("proband must rename the '.project' file to '_project'");
			} // else assume crashed VM leftover of .project, will be overwritten below.
		}
		if (dotProjectTemplate.exists()) {
			try {
				Files.copy(dotProjectTemplate.toPath(), dotProject.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IllegalStateException("unable to prepare .project-file", e);
			}
			dotProject.deleteOnExit();
			return true;
		}
		return false;
	}

	/***/
	public static IProject createJSProject(String projectName)
			throws CoreException {
		return createJSProject(projectName, "src", "src-gen", null);
	}

	/**
	 * @param packageJSONAdjustments
	 *            for details see method {@link #createProjectDescriptionFile(IProject, String, String, Consumer)}.
	 */
	public static IProject createJSProject(String projectName, String sourceFolder, String outputFolder,
			Consumer<PackageJsonBuilder> packageJSONAdjustments) throws CoreException {
		IProject result = createSimpleProject(projectName);
		createSubFolder(result.getProject(), sourceFolder);
		createSubFolder(result.getProject(), outputFolder);
		createProjectDescriptionFile(result.getProject(), sourceFolder, outputFolder, packageJSONAdjustments);
		return result;
	}

	/**
	 * Similar to {@link #createDummyN4JSRuntime(Path)}, this method will create a dummy version of "n4js-runtime" for
	 * testing purposes, with the following differences:
	 * <ol>
	 * <li>the new npm package will be created in the given Eclipse project's <code>node_modules</code> folder.
	 * <li>the given Eclipse project will be refreshed afterwards, to make the newly created files/folders available in
	 * the Eclipse workspace.
	 * </ol>
	 * Note that this method will *not* issue an update of external libraries via the library manager or a rebuild!
	 */
	public static IFolder createDummyN4JSRuntime(IProject project) throws CoreException {
		IFolder nodeModulesFolder = project.getFolder(N4JSGlobals.NODE_MODULES);
		createDummyN4JSRuntime(nodeModulesFolder.getLocation().toFile().toPath());
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		return nodeModulesFolder.getFolder(N4JSGlobals.N4JS_RUNTIME.toEclipseProjectName().getRawName());
	}

	/**
	 * Creates a dummy version of "n4js-runtime" that is sufficient to avoid compilation errors in package.json files
	 * (i.e. missing dependency to "n4js-runtime") but won't suffice for executing N4JS code. Intended for simple tests
	 * that only require compilation.
	 * <p>
	 * The newly created npm package will have a special dummy version which is available as constant
	 * {@link #N4JS_RUNTIME_DUMMY_VERSION}, allowing clients to check for this particular version, where needed.
	 * <p>
	 * If execution of N4JS code is required for testing, use method
	 * {@link N4jsLibsAccess#installN4jsLibs(Path, boolean, boolean, boolean, N4JSProjectName...)} instead.
	 *
	 * @param location
	 *            path to a folder that will become the parent folder of the newly created npm package "n4js-runtime".
	 * @return path to the root folder of the newly created npm package.
	 */
	public static Path createDummyN4JSRuntime(Path location) {
		Path projectPath = location.resolve(N4JSGlobals.N4JS_RUNTIME.getRawName());
		Path packageJsonFile = projectPath.resolve(N4JSGlobals.PACKAGE_JSON);
		try {
			Files.createDirectories(projectPath);
			Files.write(packageJsonFile, Lists.newArrayList(
					"{",
					"    \"name\": \"" + N4JSGlobals.N4JS_RUNTIME + "\",",
					"    \"version\": \"" + N4JS_RUNTIME_DUMMY_VERSION + "\"",
					"}"));
		} catch (IOException e) {
			throw new RuntimeException("failed to create dummy n4js-runtime for testing purposes", e);
		}
		return projectPath;
	}

	/***/
	public static void createProjectDescriptionFile(IProject project) throws CoreException {
		createProjectDescriptionFile(project, "src", "src-gen", null);
	}

	/**
	 * @param packageJSONBuilderAdjustments
	 *            This procedure will be invoked with the {@link PackageJsonBuilder} instances that is used to create
	 *            the project description {@link JSONDocument} instance. The builder instance will be pre-configured
	 *            with default values (cf {@link PackageJSONTestUtils#defaultPackageJson}). May be <code>null</code> if
	 *            no adjustments are required.
	 */
	public static void createProjectDescriptionFile(IProject project, String sourceFolder, String outputFolder,
			Consumer<PackageJsonBuilder> packageJSONBuilderAdjustments) throws CoreException {

		IFile projectDescriptionWorkspaceFile = project.getFile(N4JSGlobals.PACKAGE_JSON);
		URI uri = URI.createPlatformResourceURI(projectDescriptionWorkspaceFile.getFullPath().toString(), true);

		final PackageJsonBuilder packageJsonBuilder = PackageJSONTestUtils
				.defaultPackageJson(project.getName(), sourceFolder, outputFolder);

		if (packageJSONBuilderAdjustments != null)
			packageJSONBuilderAdjustments.accept(packageJsonBuilder);

		final JSONDocument document = packageJsonBuilder.buildModel();

		final ResourceSet rs = createResourceSet(project);
		final Resource projectDescriptionResource = rs.createResource(uri);
		projectDescriptionResource.getContents().add(document);

		try {
			// save formatted package.json file to disk
			projectDescriptionResource.save(SaveOptions.newBuilder().format().getOptions().toOptionsMap());
		} catch (IOException e) {
			e.printStackTrace();
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		Assert.assertTrue("project description file (package.json) should have been created",
				projectDescriptionWorkspaceFile.exists());
	}

	/**
	 * Add the given dependency to the package.json file of the given project. The version constraint may not be
	 * <code>null</code> but may be the empty string or <code>"*"</code>.
	 */
	public static void addProjectToDependencies(IProject toChange, N4JSProjectName projectName,
			String versionConstraint)
			throws IOException {
		URI uri = URI.createPlatformResourceURI(toChange.getFile(N4JSGlobals.PACKAGE_JSON).getFullPath().toString(),
				true);
		ResourceSet rs = createResourceSet(toChange);
		Resource resource = rs.getResource(uri, true);

		JSONObject packageJSONRoot = PackageJSONTestUtils.getPackageJSONRoot(resource);
		PackageJSONTestUtils.addProjectDependency(packageJSONRoot, projectName.getRawName(), versionConstraint);

		resource.save(null);
		waitForAutoBuild();
	}

	// moved here from AbstractBuilderParticipantTest:
	/** Applies the Xtext nature to the project and creates (if necessary) and returns the source folder "src". */
	public static IFolder configureProjectWithXtext(IProject project) throws CoreException {
		return configureProjectWithXtext(project, "src");
	}

	// moved here from AbstractBuilderParticipantTest:
	/** Applies the Xtext nature to the project and creates (if necessary) and returns the source folder. */
	public static IFolder configureProjectWithXtext(IProject project, String sourceFolder) throws CoreException {
		addNature(project.getProject(), XtextProjectHelper.NATURE_ID);
		IFolder folder = project.getProject().getFolder(sourceFolder);
		if (!folder.exists()) {
			folder.create(true, true, null);
		}
		return folder;
	}

	private static ResourceSet createResourceSet(IProject project) {
		return N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS)
				.getInstance(IResourceSetProvider.class).get(project);
	}

	/***/
	public static void waitForAutoBuild() {
		final int maxTry = 3;
		int currentTry = 1;
		long start = System.currentTimeMillis();
		long end = start;
		boolean wasInterrupted = false;
		boolean foundJob = false;
		do {
			do {
				try {
					Job[] foundJobs = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
					if (foundJobs.length > 0) {
						foundJob = true;
						Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD,
								null);
					}
					wasInterrupted = false;
					end = System.currentTimeMillis();
				} catch (OperationCanceledException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					wasInterrupted = true;
				}
			} while (wasInterrupted && (end - start) < MAX_WAIT_2_MINUTES);
			if (!foundJob) {
				LOGGER.debug("Auto build job hasn't been found, but maybe already run.");

				currentTry += 1;
				try {
					Thread.sleep(CHECK_INTERVAL_100_MS);
				} catch (InterruptedException e) {
					e.printStackTrace();
					LOGGER.debug("Couldn't sleep, abort waiting");
					return;
				}
				LOGGER.debug("Try again, try " + currentTry + " of " + maxTry);
			}
		} while (!foundJob && currentTry < maxTry);
	}

	/**
	 * Waits for N4JSDirtyStateEditorSupport job to be run
	 */
	public static void waitForUpdateEditorJob() {
		int maxWait = 100 * 60 * 2;
		long start = System.currentTimeMillis();
		long end = start;
		boolean wasInterrupted = false;
		boolean foundJob = false;
		do {
			try {
				Job[] foundJobs = Job.getJobManager().find(N4JSDirtyStateEditorSupport.FAMILY_UPDATE_JOB);
				if (foundJobs.length > 0) {
					foundJob = true;
					Job.getJobManager().join(N4JSDirtyStateEditorSupport.FAMILY_UPDATE_JOB, null);
				}
				wasInterrupted = false;
				end = System.currentTimeMillis();
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted && (end - start) < maxWait);
		if (!foundJob) {
			LOGGER.warn("Update editor job hasn't been found, but maybe already run.");
		}
	}

	/** Delegates to {@link #waitForAllJobs(long)} with default values {@link #MAX_WAIT_2_MINUTES}. */
	public static void waitForAllJobs() {
		waitForAllJobs(MAX_WAIT_2_MINUTES);
	}

	/**
	 * Waits for all the jobs that have status {@link Job#RUNNING} of {@link Job#WAITING}. Uses {@link Thread#sleep}, so
	 * use with care.
	 *
	 * Intentionally throws runtime exception if there are still jobs running after given timeout. Don't catch it,
	 * increase timeout in your tests.
	 *
	 * @param maxWait
	 *            maximum wait time in {@link TimeUnit#MILLISECONDS}
	 */
	public static void waitForAllJobs(final long maxWait) {
		if (maxWait < 1)
			throw new IllegalArgumentException("Wait time needs to be > 0, was " + maxWait + ".");

		final boolean runsInUI = UIUtils.runsInUIThread();
		if (runsInUI)
			LOGGER.warn("Waiting for jobs runs in the UI thread which can lead to UI thread starvation.");

		List<String> foundJobs = listJobsRunningWaiting();
		if (foundJobs.isEmpty()) {
			LOGGER.info("No running nor waiting jobs found, maybe all have already finished.");
			return;
		}
		boolean wasInterrupted = false;
		boolean wasCancelled = false;
		boolean foundJob = false;
		Stopwatch sw = Stopwatch.createStarted();
		do {
			try {
				foundJobs = listJobsRunningWaiting();
				foundJob = !foundJobs.isEmpty();
				if (foundJob) {
					if (LOGGER.isInfoEnabled())
						LOGGER.info("Found " + foundJobs.size() + " after " + sw + ", going to sleep for a while.");

					if (runsInUI)
						UIUtils.waitForUiThread();
					else
						Thread.sleep(CHECK_INTERVAL_100_MS);
				}
			} catch (OperationCanceledException e) {
				wasCancelled = true;
				LOGGER.error("Waiting for jobs was cancelled after " + sw + ".", e);
			} catch (InterruptedException e) {
				wasInterrupted = true;
				LOGGER.error("Waiting for jobs was interrupted after " + sw + ".", e);
			}
		} while (sw.elapsed(TimeUnit.MILLISECONDS) < maxWait
				&& foundJob == true
				&& wasInterrupted == false
				&& wasCancelled == false);
		sw.stop();
		if (foundJob) {
			if (LOGGER.isInfoEnabled()) {
				StringJoiner sj = new StringJoiner("\n");
				sj.add("Expected no jobs, found " + foundJobs.size() + ".");
				foundJobs.forEach(sj::add);
				LOGGER.info(sj.toString());
			}
			if (!(wasCancelled == true || wasInterrupted == true))
				throw new TimeoutRuntimeException(
						"Expected no jobs, found " + foundJobs.size() + " after " + sw + ". " + foundJobs);
		}
	}

	/** @return list of running jobs descriptions */
	public final static List<String> listJobsRunningWaiting() {
		return Arrays.stream(getJobManager().find(null))
				.filter(job -> !(job.getState() == Job.SLEEPING || job.getState() == Job.NONE))
				.map(job -> " - " + job.getName() + " : " + job.getState()).collect(Collectors.toList());
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IProject project, int count)
			throws CoreException {

		return assertMarkers(assertMessage, project, MarkerTypes.ANY_VALIDATION, count);
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, int count)
			throws CoreException {
		return assertMarkers(assertMessage, resource, MarkerTypes.ANY_VALIDATION, count);
	}

	/***/
	@SafeVarargs
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, int count,
			final Predicate<IMarker>... markerPredicates) throws CoreException {

		return assertMarkers(assertMessage, resource, MarkerTypes.ANY_VALIDATION, count, markerPredicates);
	}

	/***/
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count)
			throws CoreException {
		return assertMarkers(assertMessage, resource, markerType, count, alwaysTrue());
	}

	/***/
	@SafeVarargs
	public static IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count,
			final Predicate<IMarker>... markerPredicates) throws CoreException {

		IMarker[] markers = resource.findMarkers(markerType, true, IResource.DEPTH_INFINITE);
		List<IMarker> markerList = from(Arrays.asList(markers))
				.filter(m -> allPredicatesApply(markerPredicates, m))
				.toList();

		if (markerList.size() != count) {
			StringBuilder message = new StringBuilder(assertMessage);
			message.append("\nbut was:");
			for (IMarker marker : markerList) {
				message.append("\n");
				message.append("line " + MarkerUtilities.getLineNumber(marker) + ": ");
				message.append(marker.getAttribute(IMarker.MESSAGE, "<no message>"));
			}
			Assert.assertEquals(message.toString(), count, markerList.size());
		}
		return markers;
	}

	/**
	 * Like {@link #assertIssues(IResource, String...)}, but asserts that there are no issues in the entire workspace.
	 */
	public static void assertNoIssues() throws CoreException {
		assertIssues(new String[] {});
	}

	/**
	 * Asserts that there are no errors. However, warnings may still exist.
	 */
	public static void assertNoErrors() throws CoreException {
		waitForAutoBuild();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IMarker[] markers = root.findMarkers(MarkerTypes.ANY_VALIDATION, true, IResource.DEPTH_INFINITE);
		for (int i = 0; i < markers.length; i++) {
			IMarker m = markers[i];
			int severity = m.getAttribute(IMarker.SEVERITY, -1);
			assertNotEquals("Expected no errors but found:\n" + m.toString(), IMarker.SEVERITY_ERROR, severity);
		}
	}

	/**
	 * Like {@link #assertIssues(IResource, String...)}, but checks for issues in entire workspace.
	 */
	public static void assertIssues(String... expectedMessages) throws CoreException {
		assertIssues(ResourcesPlugin.getWorkspace().getRoot(), expectedMessages);
	}

	/**
	 * Like {@link #assertIssues(String, IResource, String...)}, but without a custom message.
	 */
	public static void assertIssues(IResource resource, String... expectedMessages) throws CoreException {
		assertIssues(null, resource, expectedMessages);
	}

	/**
	 * Asserts that the given resource (usually an N4JS file) contains issues with the given messages and no other
	 * issues. Each message given should be prefixed with the line numer where the issues occurs, e.g.:
	 *
	 * <pre>
	 * line 5: Couldn't resolve reference to identifiable element 'unknown'.
	 * </pre>
	 *
	 * Column information is not provided, so this method is not intended for several issues within a single line.
	 *
	 * @param msg
	 *            human-readable, informative message prepended to the standard message in case of assertion failure.
	 * @param resource
	 *            resource to be validated.
	 * @param expectedMessages
	 *            expected issues messages to check or empty array to assert no issues.
	 * @throws CoreException
	 *             in case of mishap.
	 */
	public static void assertIssues(String msg, final IResource resource, String... expectedMessages)
			throws CoreException {
		waitForAutoBuild();

		final IMarker[] markers = resource.findMarkers(MarkerTypes.ANY_VALIDATION, true, IResource.DEPTH_INFINITE);
		final String[] actualMessages = new String[markers.length];
		for (int i = 0; i < markers.length; i++) {
			final IMarker m = markers[i];
			actualMessages[i] = "line " + MarkerUtilities.getLineNumber(m) + ": " + m.getAttribute(IMarker.MESSAGE);
		}
		Set<String> actual = new TreeSet<>(Arrays.asList(actualMessages));
		Set<String> expected = new TreeSet<>(Arrays.asList(expectedMessages));
		Assert.assertEquals(Joiner.on('\n').join(expected), Joiner.on('\n').join(actual));
	}

	/***/
	public static void deleteProject(IProject project) throws CoreException {
		project.delete(true, true, new NullProgressMonitor());
	}

	/**
	 * Close all projects in workspace. When having a yarn workspace project in the workspace, run this method before
	 * invoking {@link IResourcesSetupUtil#cleanWorkspace()}. This will remove all projects from the index.
	 */
	public static void closeAllProjectsInWorkspace() {
		try {
			new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor monitor)
						throws CoreException, InvocationTargetException,
						InterruptedException {

					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					IProject[] visibleProjects = root.getProjects();
					for (IProject visibleProject : visibleProjects) {
						visibleProject.close(monitor);
					}

					IProject[] hiddenProjects = root.getProjects(IContainer.INCLUDE_HIDDEN);
					for (IProject hiddenProject : hiddenProjects) {
						hiddenProject.close(monitor);
					}

				}
			}.run(monitor());
		} catch (InvocationTargetException e) {
			Exceptions.sneakyThrow(e.getCause());
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Returns with the {@link IProject project} from the {@link IWorkspace workspace} with the given project name.
	 * Makes no assertions whether the project can be accessed or not.
	 *
	 * @param projectName
	 *            the name of the desired project.
	 * @return the project we are looking for. Could be non-{@link IProject#isAccessible() accessible} project.
	 */
	public static IProject getProjectByName(final EclipseProjectName projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName.getRawName());
	}

	/**
	 * Returns with an array or project form the workspace. for the given subset of unique project names. Sugar for
	 *
	 * @param projectName
	 *            the name of the project.
	 * @param otherName
	 *            the name of another project.
	 * @param rest
	 *            additional names of desired projects.
	 * @return an array of projects, could contain non-accessible project.
	 */
	public static IProject[] getProjectsByName(final EclipseProjectName projectName, final EclipseProjectName otherName,
			final EclipseProjectName... rest) {
		final List<EclipseProjectName> projectNames = Lists.asList(projectName, otherName, rest);
		final IProject[] projects = new IProject[projectNames.size()];
		for (int i = 0; i < projects.length; i++) {
			projects[i] = getProjectByName(projectNames.get(i));
		}
		return projects;
	}

	/**
	 * Copies projects from the given location to the node_modules folder of the given project
	 */
	public static void importDependencies(N4JSProjectName projectName, java.net.URI externalRootLocation,
			LibraryManager libraryManager) throws IOException, CoreException {

		IProject clientProject = getProjectByName(projectName.toEclipseProjectName());
		java.net.URI clientLocation = clientProject.getLocationURI();
		File nodeModulesDir = new File(clientLocation.getPath(), N4JSGlobals.NODE_MODULES);
		if (!nodeModulesDir.isDirectory()) {
			Files.createDirectory(nodeModulesDir.toPath());
		}

		java.nio.file.Path probandsSource = Paths.get(externalRootLocation.getPath());
		FileCopier.copy(probandsSource, nodeModulesDir.toPath());

		libraryManager.synchronizeNpms(new NullProgressMonitor());

		IResourcesSetupUtil.fullBuild();
		waitForAllJobs();
	}
}
