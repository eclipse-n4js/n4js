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
package org.eclipse.n4js.tests.compare

import com.google.inject.Inject
import java.io.File
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.internal.FileBasedWorkspace
import org.eclipse.n4js.internal.N4JSRuntimeCore
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.projectModel.names.N4JSProjectName

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ApiImplCompareTest extends AbstractApiImplCompareTest {

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private FileBasedWorkspace fbWorkspace;

	private boolean setupDone = false;


	@Before
	public def void setupHeadlessWorkspace() {

		if(setupDone) // cannot use @BeforeClass here (because static), so using this work-around
			return;
		setupDone = true;

		// make sure this test runs in headless mode
		assertTrue(n4jsCore instanceof N4JSRuntimeCore)

		registerProject(PROJECT_ID_API)
		registerProject(PROJECT_ID_IMPL)
		registerProject(PROJECT_ID_UTILS)

		assertEquals(3, n4jsCore.findAllProjects.length);
	}


	// note: main test contained in super class (common for UI and headless cases)


	private def registerProject(N4JSProjectName name) {
		fbWorkspace.registerProject(new FileURI(new File("probands/ApiImplCompare/YarnWorkspaceProject/packages/"+name)))
	}
}
