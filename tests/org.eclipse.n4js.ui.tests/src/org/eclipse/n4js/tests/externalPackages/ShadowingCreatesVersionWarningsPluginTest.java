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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

/**
 */
public class ShadowingCreatesVersionWarningsPluginTest extends AbstractBuilderParticipantTest {
	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "ShadowingCreatesVersionWarnings";
	private static final String PROJECT_P1 = "P1";
	private static final String PROJECT_N4JSLANG = "n4js.lang";

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/**
	 * Updates the known external library locations with the {@code node_modules} folder.
	 */
	@Before
	public void setupWorkspace() throws Exception {
		shippedCodeInitializeTestHelper.setupBuiltIns();
		waitForAutoBuild();
	}

	/**
	 * Tries to make sure the external libraries are cleaned from the Xtext index.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		waitForAutoBuild();
		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		waitForAutoBuild();
	}

	/**
	 * Checks whether the external refreshing does not cause deadlock due to incorrect workspace checkpoints and
	 * incorrect job family configuration.
	 */
	@Test
	public void testShadowingCreatesVersionWarnings() throws Exception {
		File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, PROJECT_N4JSLANG);
		ProjectTestsUtils.importProject(projectsRoot, PROJECT_P1);
		waitForAutoBuild();

		IProject prjP1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_P1);
		IProject prjN4JSLang = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_N4JSLANG);
		assertTrue("Cannot access project: " + prjP1, prjP1.isAccessible());
		assertTrue("Cannot access project: " + prjN4JSLang, prjN4JSLang.isAccessible());

		waitForAutoBuild();

		IFile prjDescrP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile prjDescrN4JSLang = prjN4JSLang.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		assertTrue(prjDescrP1 + " client module is not accessible.", prjDescrP1.isAccessible());
		assertTrue(prjDescrN4JSLang + " client module is not accessible.", prjDescrN4JSLang.isAccessible());

		assertIssues(prjDescrP1,
				"line 5: This project requires shadowed project n4js.lang in version ^1.0.0-rc.2, but only version 0.0.1 is present.");
		assertIssues(prjDescrN4JSLang,
				"line 5: This shadowing project requires project n4js-es5 in version 13, but only version 0.1.0 is present.");
	}

}
