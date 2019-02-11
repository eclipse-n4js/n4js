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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the external libraries.
 */
public class IndexSynchronizerPluginTest extends AbstractBuilderParticipantTest {

	private static final String NPM_SNAFU = "snafu";
	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "IndexSynchronizer";
	private static final String PROJECT_NAME = "IndexSynchronizer";

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private EclipseExternalIndexSynchronizer indexSynchronizer;

	/** Checks whether the platform is running or not. */
	@BeforeClass
	public static void checkTestMode() {
		assertTrue("Expected running platform. Run the tests as JUnit Plug-in Tests.", Platform.isRunning());
	}

	/**  */
	@Before
	public void setupWorkspace() throws Exception {
		setupShippedLibraries();
		waitForAutoBuild();
	}

	/**  */
	@After
	@Override
	public void tearDown() throws Exception {
		waitForAutoBuild();
		tearDownShippedLibraries();
		super.tearDown();
	}

	/** Install an NPM, delete folder of NPM on disk, run IndexSynchronizer, check if NPM was removed from index */
	@Test
	public void testCleanRemovedNpm() throws Exception {

		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		libraryManager.installNPM(NPM_SNAFU, URIUtils.toFileUri(project), new NullProgressMonitor());
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
	public void testInstallDeregisterAndReregisterNpm() throws Exception {

		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		IResource packagejson = project.findMember("package.json");
		IResource abc = project.findMember("src/ABC.n4js");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));
		libraryManager.installNPM(NPM_SNAFU, URIUtils.toFileUri(project), new NullProgressMonitor());
		waitForAutoBuild();
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
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertIssues(packagejson, "line 5: Project does not exist with project ID: snafu.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving project import : found no matching modules");

		Files.move(oldCopy.toPath(), file.toPath());
		assertTrue(file.exists());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertIssues(packagejson, "line 5: Project snafu is not registered.");
		assertIssues(abc,
				"line 12: Cannot resolve import target :: resolving project import : found no matching modules");

		syncExtAndBuild();

		assertIssues(packagejson);
		assertIssues(abc);
	}
}
