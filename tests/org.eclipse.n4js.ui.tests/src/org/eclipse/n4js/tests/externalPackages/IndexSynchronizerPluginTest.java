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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.utils.io.FileUtils;
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

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private LibraryManager libraryManager;

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
		setupExternalLibraries(true);
		waitForAutoBuild();
	}

	/**  */
	@After
	@Override
	public void tearDown() throws Exception {
		tearDownExternalLibraries(true);
	}

	/** Install an NPM, delete folder of NPM on disk, run IndexSynchronizer, check if NPM was removed from index */
	@Test
	public void testCleanRemovedNpm() {
		assertFalse(indexSynchronizer.findNpmsInIndex().containsKey(NPM_SNAFU));

		libraryManager.installNPM(NPM_SNAFU, new NullProgressMonitor());
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
}
