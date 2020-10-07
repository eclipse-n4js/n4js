/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

/**
 * Basic tests for the incremental builder.
 */
class IncrementalBuilderBasicTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testBuildAfterServerInitialization() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject" -> #[
				"Other" -> '''
					export public class Other {
						public mOther() {}
					}
				'''
			],
			"MainProject" -> #[
				"A" -> '''
					export public class A {
						public ma() {}
					}
				''',
				"Main" -> '''
					import {A} from "A";
					import {Other} from "Other";

					new A().ma();
					new Other().mOther();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					n4js-runtime,
					OtherProject
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		assertOutputFileExists("OtherProject", "Other");
		assertOutputFileExists("MainProject", "A");
		assertOutputFileExists("MainProject", "Main");
	}
}
