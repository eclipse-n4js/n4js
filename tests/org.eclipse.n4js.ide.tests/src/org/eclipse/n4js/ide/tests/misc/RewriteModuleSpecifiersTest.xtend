/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.misc

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

/**
 * Test for package.json property {@code n4js -> generator -> rewriteModuleSpecifiers}.
 */
class RewriteModuleSpecifiersTest extends AbstractIdeTest {

	@Test
	def void testRewriteModuleSpecifiers() {
		testWorkspaceManager.createTestProjectOnDisk(
			"folder/someModule.n4jsd" -> '''
				export external public function foo();
			''',
			"N4jsModule" -> '''
				import * as stuff from "folder/someModule";
				stuff.foo();
			''',
			PACKAGE_JSON -> '''
				{
					"name": "«DEFAULT_PROJECT_NAME»",
					"version": "0.0.1",
					"n4js": {
						"projectType": "library",
						"vendorId": "org.eclipse.n4js",
						"sources": {
							"source": [
								"src"
							]
						},
						"output": "src-gen",
						"generator": {
							"rewriteModuleSpecifiers": {
								"folder/someModule": "dummyModule.jsx"
							}
						}
					},
					"dependencies": {
						"n4js-runtime": "*"
					}
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "N4jsModule", "import * as stuff from 'dummyModule.jsx'");
	}
}
