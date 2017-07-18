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
package org.eclipse.n4js.tests.bugs;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.tests.util.ProjectUtils;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;

/**
 * Test class for checking type system does not cause build failure when processing and traversing broken AST.
 */
public class GHOLD_129_BrokenAstMustNotCauseBuildFailure_PluginUITest extends AbstractIDEBUG_Test {

	private static final String PROJECT_NAME = "GHOLD-129";

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/" + PROJECT_NAME + "/").getAbsolutePath()));
	}

	/**
	 * We expect validation errors which means the build was not broken and interrupted due to broken AST when inferring
	 * the types.
	 */
	@Test
	public void checkBrokenAstDoesNotCauseBuildFailure_ExpectValidationErrors() throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		assertTrue("Project '" + PROJECT_NAME + "' does not exist in workspace.", project.isAccessible());

		IResourcesSetupUtil.fullBuild();
		ProjectUtils.waitForAutoBuild();

		// Couldn't resolve reference to IdentifiableElement 'A'.
		// Name missing in type declaration.
		ProjectUtils.assertMarkers("Expected exactly two validation errors.", project, 2);
	}

}
