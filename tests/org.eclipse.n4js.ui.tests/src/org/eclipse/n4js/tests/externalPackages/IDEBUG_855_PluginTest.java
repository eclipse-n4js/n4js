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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.EMFBasedPersister;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
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
public class IDEBUG_855_PluginTest extends AbstractBuilderParticipantTest {

	private static final String EXT_LOC = "IDE_1977_ExternalLibs";
	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "IDEBUG_855";
	private static final N4JSProjectName PROJECT = new N4JSProjectName("IDEBUG_855");
	private static final String SRC_FOLDER = "src";
	private static final String MODULE = "Client";

	@Inject
	private IBuilderState builderState;

	@Inject
	private ContributingResourceDescriptionPersister persister;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	@Before
	synchronized public void setupWorkspace() throws Exception {
		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PROJECT);

		java.net.URI externalRootLocation = getResourceUri(PROBANDS, EXT_LOC);
		ProjectTestsUtils.importDependencies(PROJECT, externalRootLocation, libraryManager);

		waitForAutoBuild();
	}

	/**
	 * See description at class declaration.
	 */
	@Test
	public void testAllIndexElementsCanBeAddedToAResource() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJECT.toEclipseProjectName().getRawName());
		assertTrue(PROJECT + " project is not accessible.", project.isAccessible());

		final IFile clientModule = project.getFile(getResourceName(SRC_FOLDER, MODULE + "." + N4JS_FILE_EXTENSION));
		assertTrue(clientModule + " client module is not accessible.", clientModule.isAccessible());

		final IFile projectDescriptionFile = project.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		assertTrue(projectDescriptionFile + " client module is not accessible.", projectDescriptionFile.isAccessible());

		final Resource resource = persister.createResource();
		assertNotNull("Test resource was null.", resource);

		final int builderStateBeforeReloadSize = Iterables.size(builderState.getAllResourceDescriptions());
		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final int persistedBeforeReloadSize = resource.getContents().size();

		assertMarkers("Expected exactly zero errors in the project description file (package.json).",
				projectDescriptionFile, 0);
		assertMarkers("Expected exactly zero errors in client module.", clientModule, 0);

		resource.getContents().clear();

		syncExtAndBuild();

		final int builderStateAfterReloadSize = Iterables.size(builderState.getAllResourceDescriptions());
		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final int persistedAfterReloadSize = resource.getContents().size();

		assertMarkers("Expected exactly zero errors in project description file (package.json).",
				projectDescriptionFile, 0);
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
