/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.PackageJSONTestUtils;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Checks that the {@link EclipseBasedN4JSWorkspace} correctly invalidates its cache of project descriptions based on
 * resource change events.
 */
public class ProjectCacheInvalidationPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";

	private static final String PROBANDS_SUBFOLDER = "GH-984-nested-projects";

	@Inject
	private XtextResourceSetProvider resourceSetProvider;

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Alters a single project description and asserts that the project handle obtained from {@link IN4JSCore} correctly
	 * reflects the changes after the operation has been performed.
	 */
	@Test
	public void testSingleProjectDescriptionChange() throws CoreException {
		// test case constants
		final String updatedImplementationId1 = "updated1";
		// setup
		final IProject testProject1 = createN4JSProject("P1", ProjectType.LIBRARY);

		// perform package.json modification
		runAtomicWorkspaceOperation(monitor -> {
			final IFile packageJson1 = testProject1.getFile(IN4JSProject.PACKAGE_JSON);

			updatePackageJsonFile(packageJson1,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId1));
		});

		// obtain workspace representation of test projects
		Optional<? extends IN4JSProject> projectHandle1 = n4jsCore
				.findProject(URI.createPlatformResourceURI(testProject1.getFullPath().toString(), true));

		// make assertions
		assertTrue("Project handle for the test project can be obtained.", projectHandle1.isPresent());

		assertTrue("Project handle for the test project should have implementation ID (cache was invalidated).",
				projectHandle1.get().getImplementationId().isPresent());

		assertEquals(updatedImplementationId1, projectHandle1.get().getImplementationId().get().getRawName());

		// tear down
		testProject1.delete(true, new NullProgressMonitor());
	}

	/**
	 * Updates two project description files in the workspace within one atomic workspace operation, resulting in a
	 * single {@link IResourceChangeEvent} that notifies clients of both modifications.
	 *
	 * Asserts that the {@link EclipseBasedN4JSWorkspace} correctly invalidates its cache for both projects and
	 * correctly reflects the changes of both project description files.
	 */
	@Test
	public void testMultipleProjectDescriptionChanges() throws CoreException {
		// test case constants
		final String updatedImplementationId1 = "updated1";
		final String updatedImplementationId2 = "updated2";

		// setup
		final IProject testProject1 = createN4JSProject("P1", ProjectType.LIBRARY);
		final IProject testProject2 = createN4JSProject("P2", ProjectType.LIBRARY);

		// perform package.json modification
		runAtomicWorkspaceOperation(monitor -> {
			final IFile packageJson1 = testProject1.getFile(IN4JSProject.PACKAGE_JSON);
			final IFile packageJson2 = testProject2.getFile(IN4JSProject.PACKAGE_JSON);

			updatePackageJsonFile(packageJson1,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId1));
			updatePackageJsonFile(packageJson2,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId2));
		});

		// obtain workspace representation of test projects
		Optional<? extends IN4JSProject> projectHandle1 = n4jsCore
				.findProject(URI.createPlatformResourceURI(testProject1.getFullPath().toString(), true));
		Optional<? extends IN4JSProject> projectHandle2 = n4jsCore
				.findProject(URI.createPlatformResourceURI(testProject2.getFullPath().toString(), true));

		// make assertions
		assertTrue("Project handle for test project 1 can be obtained.", projectHandle1.isPresent());
		assertTrue("Project handle for test project 2 can be obtained.", projectHandle2.isPresent());

		assertTrue("Project handle for test project 1 should have implementation ID (cache was invalidated).",
				projectHandle1.get().getImplementationId().isPresent());
		assertTrue("Project handle for test project 2 should have implementation ID (cache was invalidated).",
				projectHandle2.get().getImplementationId().isPresent());

		assertEquals(updatedImplementationId1, projectHandle1.get().getImplementationId().get().getRawName());
		assertEquals(updatedImplementationId2, projectHandle2.get().getImplementationId().get().getRawName());

		// tear down
		testProject1.delete(true, new NullProgressMonitor());
		testProject2.delete(true, new NullProgressMonitor());
	}

	/**
	 * Creates three projects, one of which is the top-level in which the two other projects are nested.
	 *
	 * Performs an atomic package.json modification in both nested projects and asserts that the
	 * {@link EclipseBasedN4JSWorkspace} correctly invalidates its cache for both projects and correctly reflects the
	 * changes of both project description files.
	 */
	@Test
	public void testUpdateNestedProjectDescription() throws CoreException, IOException {
		// test case constants
		final String updatedImplementationId1 = "updated1";
		final String updatedImplementationId2 = "updated2";
		final String updatedImplementationId3 = "updated3";

		// setup probands
		File projectsRoot = Files.createTempDir();
		projectsRoot.deleteOnExit();
		final File probandsLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));
		FileCopier.copy(probandsLocation.toPath(), projectsRoot.toPath());

		// setup workspace
		final IProject nestedTestProject1 = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"TopLevel/packages/A_Nested", "A_Nested");
		final IProject nestedTestProject2 = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"TopLevel/packages/Z_Nested", "Z_Nested");
		final IProject topLevelTestProject = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"TopLevel", "TopLevel");

		// obtain workspace representation of the nested test projects
		Optional<? extends IN4JSProject> projectHandle1 = n4jsCore
				.findProject(URI.createPlatformResourceURI(nestedTestProject1.getFullPath().toString(), true));
		Optional<? extends IN4JSProject> projectHandle2 = n4jsCore
				.findProject(URI.createPlatformResourceURI(nestedTestProject2.getFullPath().toString(), true));
		Optional<? extends IN4JSProject> projectHandle3 = n4jsCore
				.findProject(URI.createPlatformResourceURI(topLevelTestProject.getFullPath().toString(), true));

		// check for initial value of implementationId (triggers initial population of cache)
		assertFalse(
				"Project handle for nested test project 1 should initially not have implementation ID (cache was invalidated).",
				projectHandle1.get().getImplementationId().isPresent());
		assertFalse(
				"Project handle for nested test project 2 should initially not have implementation ID (cache was invalidated).",
				projectHandle2.get().getImplementationId().isPresent());
		assertFalse(
				"Project handle for the top-level test project should initially not have implementation ID (cache was invalidated).",
				projectHandle3.get().getImplementationId().isPresent());

		// perform package.json modification
		runAtomicWorkspaceOperation(monitor -> {
			final IFile packageJson1 = nestedTestProject1.getFile(IN4JSProject.PACKAGE_JSON);
			final IFile packageJson2 = nestedTestProject2.getFile(IN4JSProject.PACKAGE_JSON);
			final IFile packageJson3 = topLevelTestProject.getFile(IN4JSProject.PACKAGE_JSON);

			// add implementation ID for both nested projects
			updatePackageJsonFile(packageJson1,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId1));
			updatePackageJsonFile(packageJson2,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId2));

			// add implementation ID to top-level project
			updatePackageJsonFile(packageJson3,
					o -> PackageJSONTestUtils.setImplementationId(o, updatedImplementationId3));
		});

		// make assertions
		assertTrue("Project handle for nested test project 1 can be obtained.", projectHandle1.isPresent());
		assertTrue("Project handle for nested test project 2 can be obtained.", projectHandle2.isPresent());
		assertTrue("Project handle for top-level test project can be obtained.", projectHandle3.isPresent());

		assertTrue("Project handle for nested test project 1 should have implementation ID (cache was invalidated).",
				projectHandle1.get().getImplementationId().isPresent());
		assertTrue("Project handle for nested test project 2 should have implementation ID (cache was invalidated).",
				projectHandle2.get().getImplementationId().isPresent());
		assertTrue(
				"Project handle for the top-level test project should have implementation ID (cache was invalidated).",
				projectHandle3.get().getImplementationId().isPresent());

		assertEquals(updatedImplementationId1, projectHandle1.get().getImplementationId().get().getRawName());
		assertEquals(updatedImplementationId2, projectHandle2.get().getImplementationId().get().getRawName());
		assertEquals(updatedImplementationId3, projectHandle3.get().getImplementationId().get().getRawName());

		// tear down
		nestedTestProject1.delete(true, new NullProgressMonitor());
		nestedTestProject2.delete(true, new NullProgressMonitor());
		topLevelTestProject.delete(true, new NullProgressMonitor());
	}

	/**
	 * Runs the given {@link ICoreRunnable} as an atomic workspace operation with a lock on the whole workspace.
	 *
	 * All resource changes performed by the given {@code operation} will be collected and a single
	 * {@link IResourceChangeEvent} will be issued for all changes.
	 */
	private void runAtomicWorkspaceOperation(ICoreRunnable operation) throws CoreException {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.run(operation, workspace.getRoot(),
				IWorkspace.AVOID_UPDATE, // issue one resource change event at the end of the operation
				new NullProgressMonitor());
	}

	/**
	 * Performs the given {@code updateOperation} on the loaded AST of the given {@code packageJsonFile} and saves it to
	 * disk.
	 */
	private void updatePackageJsonFile(IFile packageJsonFile, Consumer<JSONObject> updateOperation)
			throws CoreException {
		final IProject project = packageJsonFile.getProject();
		final ResourceSet resourceSet = resourceSetProvider.get(project);

		// read and parse package.json contents
		final String path = packageJsonFile.getFullPath().toString();
		final URI uri = URI.createPlatformResourceURI(path, true);
		final Resource resource = resourceSet.getResource(uri, true);

		final JSONObject root = PackageJSONTestUtils.getPackageJSONRoot(resource);
		updateOperation.accept(root);

		try {
			resource.save(null);
		} catch (IOException e) {
			throw new WrappedException("Failed to save package.json resource at " + resource.getURI().toString() + ".",
					e);
		}
		packageJsonFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
	}
}
