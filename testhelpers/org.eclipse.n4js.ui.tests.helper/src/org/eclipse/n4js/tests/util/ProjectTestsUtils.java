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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.ui.editor.N4JSDirtyStateEditorSupport;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.TimeoutRuntimeException;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.xtext.ui.MarkerTypes;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

/**
 * Utilities for for tests that setup / assert N4JS projects.
 */
public class ProjectTestsUtils {

	/** Default wait time used in waiting for jobs. */
	public static final long MAX_WAIT_2_MINUTES = 100 * 60 * 2;
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

	/**
	 * Imports a project into the running JUnit test workspace. Usage:
	 *
	 * <pre>
	 * IProject project = ProjectUtils.importProject(new File(&quot;probands&quot;), &quot;TestProject&quot;);
	 * </pre>
	 *
	 * @param probandsFolder
	 *            the parent folder in which the test project is found
	 * @param projectName
	 *            the name of the test project, must be folder contained in probandsFolder
	 * @return the imported project
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/12484128/how-do-i-import-an-eclipse-project-from-a-zip-file-programmatically">
	 *      stackoverflow: from zip</a>
	 */
	public static IProject importProject(File probandsFolder, String projectName) throws Exception {
		return importProject(probandsFolder, projectName, true);
	}

	/**
	 * Same as {@link #importProject(File, String)}, but won't enforce the convention that ".project" files should be
	 * named "_project" in the proband folder. This should only be used as a rare exception when tests import projects
	 * from Git repositories other than the N4JS or N4JS-N4 source repositories (e.g. for integration tests).
	 */
	public static IProject importProjectFromExternalSource(File probandsFolder, String projectName) throws Exception {
		return importProject(probandsFolder, projectName, false);
	}

	private static IProject importProject(File probandsFolder, String projectName, boolean prepareDotProject)
			throws Exception {
		File projectSourceFolder = new File(probandsFolder, projectName);
		if (!projectSourceFolder.exists()) {
			throw new IllegalArgumentException("proband not found in " + projectSourceFolder);
		}

		if (prepareDotProject) {
			prepareDotProject(projectSourceFolder);
		}

		IProgressMonitor monitor = new NullProgressMonitor();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(projectName);

		workspace.run((mon) -> {
			IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
			project.create(newProjectDescription, mon);
			project.open(mon);
			if (!project.getLocation().toFile().exists()) {
				throw new IllegalArgumentException("test project correctly created in " + project.getLocation());
			}

			IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
				@Override
				public String queryOverwrite(String file) {
					return ALL;
				}
			};
			ImportOperation importOperation = new ImportOperation(project.getFullPath(), projectSourceFolder,
					FileSystemStructureProvider.INSTANCE, overwriteQuery);
			importOperation.setCreateContainerStructure(false);
			try {
				importOperation.run(mon);
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, monitor);

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
	public static IProject createJSProject(String projectName) throws CoreException {
		return createJSProject(projectName, "src", "src-gen", null);
	}

	/**
	 * Creates a new N4JS project with the given name and project type. The source and output folders will be named as
	 * {@code src} and {@code src-gen}. The Xtext project nature will be already configured on the N4JS project.
	 *
	 * @param projectName
	 *            the name of the project.
	 * @param type
	 *            the desired project type of the new project.
	 * @return the new N4JS project with the desired type.
	 * @throws CoreException
	 *             if the project creation failed.
	 */
	public static IProject createN4JSProject(String projectName, ProjectType type) throws CoreException {
		final IProject project = createJSProject(projectName, "src", "src-gen", t -> t.setProjectType(type));
		configureProjectWithXtext(project);
		return project;
	}

	/**
	 * @param manifestAdjustments
	 *            for details see method {@link #createManifestN4MFFile(IProject, String, String, Consumer)}.
	 */
	public static IProject createJSProject(String projectName, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		IProject result = createSimpleProject(projectName);
		createSubFolder(result.getProject(), sourceFolder);
		createSubFolder(result.getProject(), outputFolder);
		createManifestN4MFFile(result.getProject(), sourceFolder, outputFolder, manifestAdjustments);
		return result;
	}

	/***/
	public static void createManifestN4MFFile(IProject project) throws CoreException {
		createManifestN4MFFile(project, "src", "src-gen", null);
	}

	/**
	 * @param manifestAdjustments
	 *            before saving the manifest this procedure will be called to allow adjustments to the manifest's
	 *            properties (the ProjectDescription object passed to the procedure will already contain all default
	 *            values). May be <code>null</code> if no adjustments are required.
	 */
	public static void createManifestN4MFFile(IProject project, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		IFile config = project.getFile("manifest.n4mf");
		URI uri = URI.createPlatformResourceURI(config.getFullPath().toString(), true);
		ProjectDescription projectDesc = N4mfFactory.eINSTANCE.createProjectDescription();
		projectDesc.setVendorId("org.eclipse.n4js");
		projectDesc.setVendorName("Eclipse N4JS Project");
		projectDesc.setProjectId(project.getName());
		projectDesc.setProjectType(ProjectType.LIBRARY);
		DeclaredVersion projectVersion = N4mfFactory.eINSTANCE.createDeclaredVersion();
		projectVersion.setMajor(0);
		projectVersion.setMinor(0);
		projectVersion.setMicro(1);
		projectDesc.setProjectVersion(projectVersion);
		projectDesc.setOutputPath(outputFolder);
		SourceContainerDescription sourceProjectPath = N4mfFactory.eINSTANCE.createSourceContainerDescription();
		sourceProjectPath.setSourceContainerType(SourceContainerType.SOURCE);
		sourceProjectPath.getPathsRaw().add(sourceFolder);
		projectDesc.getSourceContainers().add(sourceProjectPath);
		if (manifestAdjustments != null)
			manifestAdjustments.accept(projectDesc);
		ResourceSet rs = createResourceSet(project);
		Resource res = rs.createResource(uri);
		res.getContents().add(projectDesc);

		try {
			res.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		Assert.assertTrue("manifest.n4mf should have been created", config.exists());
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
				throw new TimeoutRuntimeException("Expected no jobs, found " + foundJobs.size() + " after " + sw + ".");
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
	 * Asserts that the given resource (usually an N4JS file) contains issues with the given messages and no other
	 * issues. Each message given should be prefixed with the line numer where the issues occurs, e.g.:
	 *
	 * <pre>
	 * line 5: Couldn't resolve reference to identifiable element 'unknown'.
	 * </pre>
	 *
	 * Column information is not provided, so this method is not intended for several issues within a single line.
	 */
	public static void assertIssues(final IResource resource, String... expectedMessages) throws CoreException {
		final IMarker[] markers = resource.findMarkers(MarkerTypes.ANY_VALIDATION, true, IResource.DEPTH_INFINITE);
		final String[] actualMessages = new String[markers.length];
		for (int i = 0; i < markers.length; i++) {
			final IMarker m = markers[i];
			actualMessages[i] = "line " + MarkerUtilities.getLineNumber(m) + ": " + m.getAttribute(IMarker.MESSAGE);
		}
		if (!Objects.equals(
				new HashSet<>(Arrays.asList(actualMessages)),
				new HashSet<>(Arrays.asList(expectedMessages)))) {
			final Joiner joiner = Joiner.on("\n    ");
			final String msg = "expected these issues:\n"
					+ "    " + joiner.join(expectedMessages) + "\n"
					+ "but got these:\n"
					+ "    " + joiner.join(actualMessages);
			System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*");
			System.out.println(msg);
			System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*");
			Assert.fail(msg);
		}
	}

	/***/
	public static void deleteProject(IProject project) throws CoreException {
		project.delete(true, true, new NullProgressMonitor());
	}

	/**
	 * Returns with the {@link IProject project} from the {@link IWorkspace workspace} with the given project name.
	 * Makes no assertions whether the project can be accessed or not.
	 *
	 * @param projectName
	 *            the name of the desired project.
	 * @return the project we are looking for. Could be non-{@link IProject#isAccessible() accessible} project.
	 */
	public static IProject getProjectByName(final String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
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
	public static IProject[] getProjectsByName(final String projectName, final String otherName,
			final String... rest) {
		final List<String> projectNames = Lists.asList(projectName, otherName, rest);
		final IProject[] projects = new IProject[projectNames.size()];
		for (int i = 0; i < projects.length; i++) {
			projects[i] = getProjectByName(projectNames.get(i));
		}
		return projects;
	}
}
