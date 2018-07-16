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
package org.eclipse.n4js.tests.exclude;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tests.bugs.AbstractIDEBUG_Test;
import org.eclipse.n4js.tests.bugs.ProjectImporter;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 *
 */
public class ExcludePckJson_PluginUITest extends AbstractIDEBUG_Test {

	private static final String PROJECT_NAME = "ExcludePckJson";

	@Inject
	private IN4JSCore core;

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/" + PROJECT_NAME + "/").getAbsolutePath()));
	}

	/**
	 * Checks that only one package.json is contained in the index. This one package.json is the one from the project
	 * root folder.
	 */
	@Test
	public void checkIndex() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		assertTrue("Test project is not accessible.", project.isAccessible());

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		ResourceSet resourceSet = core.createResourceSet(Optional.absent());
		IResourceDescriptions index = core.getXtextIndex(resourceSet);

		for (IResourceDescription res : index.getAllResourceDescriptions()) {
			String resLocation = res.getURI().toString();

			if (resLocation.endsWith(".json")) {
				System.out.println("Indexing found: " + resLocation);
			}
		}
	}

	/**
	 * Checks that there is exactly one error marker in the problems view. This single marker must be from the
	 * package.json of the project root folder.
	 */
	@Test
	public void checkProblemMarkers() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		assertTrue("Test project is not accessible.", project.isAccessible());

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();
		assertMarkers("Expected exactly 1 issues.", project, 1);
	}
}
