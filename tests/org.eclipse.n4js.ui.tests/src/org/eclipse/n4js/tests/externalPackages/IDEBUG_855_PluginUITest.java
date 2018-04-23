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

import static org.eclipse.n4js.N4JSGlobals.N4JS_FILE_EXTENSION;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.EMFBasedPersister;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Test for checking whether the whole Xtext index content can be persisted by checking the size of the
 * {@link IResourceDescription resource description} instances in the {@link IResourceDescriptions Xtext index}, then
 * writing the resource descriptions into a {@link Resource} via the {@link EMFBasedPersister} and comparing the size of
 * the content with the number of elements in the index . Should be the same.
 */
@SuppressWarnings("restriction")
public class IDEBUG_855_PluginUITest extends AbstractBuilderParticipantTest {

	private static final String EXT_LOC = "IDE_1977_ExternalLibs";
	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "IDEBUG_855";
	private static final String PROJECT = "IDEBUG_855";
	private static final String SRC_FOLDER = "src";
	private static final String MODULE = "Client";

	@Inject
	private IBuilderState builderState;

	@Inject
	private ContributingResourceDescriptionPersister persister;

	@Inject
	private LibraryManager npmManager;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	@Before
	public void setupWorkspace() throws Exception {
		final URI externalRootLocation = getResourceUri(PROBANDS, EXT_LOC);
		externalLibraryPreferenceStore.add(externalRootLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
		waitForAutoBuild();
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PROJECT);
		waitForAutoBuild();
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		final URI externalRootLocation = getResourceUri(PROBANDS, EXT_LOC);
		externalLibraryPreferenceStore.remove(externalRootLocation);
		final IStatus result = externalLibraryPreferenceStore.save(new NullProgressMonitor());
		assertTrue("Error while saving external library preference changes.", result.isOK());
		waitForAutoBuild();
		super.tearDown();
	}

	/**
	 * See description at class declaration.
	 */
	@Test
	public void testAllIndexElementsCanBeAddedToAResource() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
		assertTrue(PROJECT + " project is not accessible.", project.isAccessible());

		final IFile clientModule = project.getFile(getResourceName(SRC_FOLDER, MODULE + "." + N4JS_FILE_EXTENSION));
		assertTrue(clientModule + " client module is not accessible.", clientModule.isAccessible());

		final IFile manifest = project.getFile(getResourceName(N4MF_MANIFEST));
		assertTrue(manifest + " client module is not accessible.", manifest.isAccessible());

		final Resource resource = persister.createResource();
		assertNotNull("Test resource was null.", resource);

		final int builderStateBeforeReloadSize = Iterables.size(builderState.getAllResourceDescriptions());
		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final int persistedBeforeReloadSize = resource.getContents().size();

		assertMarkers("Expected exactly zero errors in manifest.", manifest, 0);
		assertMarkers("Expected exactly zero errors in client module.", clientModule, 0);

		resource.getContents().clear();
		npmManager.reloadAllExternalProjects(new NullProgressMonitor());

		final int builderStateAfterReloadSize = Iterables.size(builderState.getAllResourceDescriptions());
		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final int persistedAfterReloadSize = resource.getContents().size();

		assertMarkers("Expected exactly zero errors in manifest.", manifest, 0);
		assertMarkers("Expected exactly zero errors in client module.", clientModule, 0);

		assertTrue("Expected same number of persisted and available resource description before and after refresh. Was:"
				+ "\nBuilder state before reload size: " + builderStateBeforeReloadSize
				+ "\nBuilder state after reload size: " + builderStateAfterReloadSize
				+ "\nPersisted before reload size: " + persistedBeforeReloadSize
				+ "\nPersisted after reload size: " + persistedAfterReloadSize,
				builderStateBeforeReloadSize == builderStateAfterReloadSize
						&& persistedBeforeReloadSize == persistedAfterReloadSize
						&& builderStateBeforeReloadSize == persistedBeforeReloadSize);

	}

}
