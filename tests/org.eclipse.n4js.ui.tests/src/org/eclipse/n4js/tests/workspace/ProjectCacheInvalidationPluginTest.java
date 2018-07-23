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
import static org.junit.Assert.assertTrue;

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
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.PackageJSONTestUtils;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 *
 */
public class ProjectCacheInvalidationPluginTest extends AbstractBuilderParticipantTest {

	@Inject
	private XtextResourceSetProvider resourceSetProvider;

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Updates two project description files in the workspace within one atomic workspace operation, resulting in a
	 * single {@link IResourceChangeEvent} that notifies clients of both modifications.
	 *
	 * Asserts that the {@link EclipseBasedN4JSWorkspace} correctly invalidates its cache for both projects and
	 * correctly reflects the changes of both project description files.
	 */
	@Test
	public void testUpdateProjectDescription() throws CoreException {
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

		assertEquals(updatedImplementationId1, projectHandle1.get().getImplementationId().get());
		assertEquals(updatedImplementationId2, projectHandle2.get().getImplementationId().get());

		// tear down
		testProject1.delete(true, new NullProgressMonitor());
		testProject2.delete(true, new NullProgressMonitor());
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