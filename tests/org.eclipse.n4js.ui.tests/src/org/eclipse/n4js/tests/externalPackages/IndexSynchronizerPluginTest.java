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
package org.eclipse.n4js.tests.externalPackages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.function.Consumer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the external libraries.
 */
public class IndexSynchronizerPluginTest extends AbstractBuilderParticipantTest {

	private static final N4JSProjectName NPM_SNAFU = new N4JSProjectName("snafu");
	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "IndexSynchronizer";
	private static final N4JSProjectName PROJECT_NAME = new N4JSProjectName("IndexSynchronizer");

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private EclipseExternalIndexSynchronizer indexSynchronizer;

	/** Checks whether the platform is running or not. */
	@BeforeClass
	public static void checkTestMode() {
		assertTrue("Expected running platform. Run the tests as JUnit Plug-in Tests.", Platform.isRunning());
	}

	/** Install an NPM, delete folder of NPM on disk, run IndexSynchronizer, check if NPM was removed from index */
	@Test
	public void testCleanRemovedNpm() throws Exception {

		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		testedWorkspace.fullBuild();

		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		libraryManager.installNPM(NPM_SNAFU, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		waitForAutoBuild();

		assertTrue(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		N4JSExternalProject snafu = externalLibraryWorkspace.getProject(NPM_SNAFU);
		assertTrue(snafu != null);
		IPath location = snafu.getLocation();
		File file = location.toFile();
		assertTrue(file.exists());

		FileUtils.deleteFileOrFolder(file);
		assertFalse(file.exists());
		assertTrue(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		indexSynchronizer.checkAndClearIndex(new NullProgressMonitor());
		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
	}

	/***/
	@Test
	public void testInstallDeregisterAndReregisterNpmCleanBuild() throws Exception {
		installDeregisterAndReregisterNpm((p) -> {
			refreshProject(p);
			testedWorkspace.cleanBuild();
			testedWorkspace.fullBuild();
		});
	}

	/***/
	@Test
	public void testInstallDeregisterAndReregisterNpmIncrementalBuild() throws Exception {
		installDeregisterAndReregisterNpm((p) -> {
			refreshProject(p);
			testedWorkspace.build();
		});
	}

	/***/
	@Test
	public void testInstallDeregisterAndReregisterNpmIncrementalBuild2() throws Exception {
		installDeregisterAndReregisterNpm((p) -> {
			refreshProject(p);
			waitForAutoBuild();
		});
	}

	private void refreshProject(IProject p) {
		try {
			p.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	/***/
	@Test
	public void testInstallDeregisterAndReregisterNpmFullBuild() throws Exception {
		installDeregisterAndReregisterNpm((p) -> {
			refreshProject(p);
			testedWorkspace.fullBuild();
		});
	}

	/***/
	private void installDeregisterAndReregisterNpm(Consumer<IProject> build) throws Exception {

		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		IResource packagejson = project.findMember("package.json");
		IResource abc = project.findMember("src/ABC.n4js");
		build.accept(project);

		assertIssues(packagejson, "line 6: Project does not exist with project ID: snafu.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving simple module import : found no matching modules");

		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		libraryManager.installNPM(NPM_SNAFU, new PlatformResourceURI(project).toFileURI(), new NullProgressMonitor());
		// waitForAutoBuild();
		assertTrue(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));

		assertIssues(packagejson);
		assertIssues(abc);

		N4JSExternalProject snafu = externalLibraryWorkspace.getProject(NPM_SNAFU);
		assertTrue(snafu != null);
		IPath location = snafu.getLocation();
		File file = location.toFile();
		assertTrue(file.exists());

		File tmpLocation = file.getParentFile().getParentFile();
		File oldCopy = tmpLocation.toPath().resolve(file.getName()).toFile();
		if (oldCopy.exists()) {
			FileUtils.deleteFileOrFolder(oldCopy);
		}
		Files.move(file.toPath(), oldCopy.toPath());
		assertFalse(file.exists());
		build.accept(project);

		assertIssues(packagejson, "line 6: Project does not exist with project ID: snafu.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving simple module import : found no matching modules");

		build.accept(project);
		assertIssues(packagejson, "line 6: Project does not exist with project ID: snafu.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving simple module import : found no matching modules");

		Files.move(oldCopy.toPath(), file.toPath());
		assertTrue(file.exists());
		build.accept(project);

		assertIssues(packagejson, "line 6: Project snafu is not registered.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving project import : found no matching modules");

		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		build.accept(project);

		assertIssues(packagejson);
		assertIssues(abc);
	}
}
