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

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.cleanWorkspace;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.resources.WorkManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.junit.After;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test for checking whether the external workspace build jobs will not cause a deadlock in the {@link WorkManager} due
 * to unnecessary checkpoint creation in the workspace. Also ensures workspace project refresh jobs belong to the proper
 * job family.
 */
@SuppressWarnings("restriction")
public class IDEBUG_856_PluginUITest extends AbstractBuilderParticipantTest {

	private static final Logger LOGGER = Logger.getLogger(IDEBUG_856_PluginUITest.class);

	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "IDEBUG_856";
	private static final String PROJECT = "IDEBUG_856";

	private static final int ITERATION_COUNT = 30;

	@Inject
	private LibraryManager libManager;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	public void setupWorkspace() throws Exception {
		super.setUp();
		shippedCodeInitializeTestHelper.setupBuiltIns();
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PROJECT);
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
		assertTrue("Cannot access project: " + project, project.isAccessible());
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		cleanWorkspace();
		waitForAutoBuild();
		shippedCodeInitializeTestHelper.teardowneBuiltIns();
		waitForAutoBuild();
		super.tearDown();
	}

	/**
	 * Checks whether the external refreshing does not cause deadlock due to incorrect workspace checkpoints and
	 * incorrect job family configuration.
	 */
	@Test
	public void testMultipleExternalRefresh() throws Exception {
		for (int i = 1; i <= ITERATION_COUNT; i++) {
			LOGGER.info("------------------------------------------------------------");
			LOGGER.info("| Iteration " + i + " of " + ITERATION_COUNT + ".");
			LOGGER.info("------------------------------------------------------------");
			setupWorkspace();
			libManager.reloadAllExternalProjects(new NullProgressMonitor());
			libManager.reloadAllExternalProjects(new NullProgressMonitor());
			tearDown();
		}
	}

}
