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

import static org.eclipse.xtext.junit4.ui.util.IResourcesSetupUtil.cleanWorkspace;

import java.io.File;
import java.net.URI;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.resources.WorkManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.inject.Inject;

import org.eclipse.n4js.external.ExternalLibrariesReloadHelper;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectUtils;

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
	private ExternalLibrariesReloadHelper reloadHelper;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	public void setupWorkspace() throws Exception {
		final BiMap<URI, String> locations = ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER.get();
		final URI location = locations.inverse().get(ExternalLibrariesActivator.MANGELHAFT_CATEGORY);
		externalLibraryPreferenceStore.add(location);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectUtils.importProject(projectsRoot, PROJECT);
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
		final BiMap<URI, String> locations = ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER.get();
		final URI location = locations.inverse().get(ExternalLibrariesActivator.MANGELHAFT_CATEGORY);
		externalLibraryPreferenceStore.remove(location);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
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
			reloadHelper.reloadLibraries(true, new NullProgressMonitor());
			reloadHelper.reloadLibraries(true, new NullProgressMonitor());
			tearDown();
		}
	}

}
