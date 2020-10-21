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
package org.eclipse.n4js.ide.tests.bugreports

import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.junit.Test

/**
 * This test is required, because the Xpect tests and Xpect UI tests for package.json files are green even when
 * the exceptions reported in GH-1943 are thrown.
 */
class GH_1943_ExceptionInCaseOfSemverSyntaxErrorTest extends AbstractIdeTest {

	@Test
	def void testVersionConstraintWithSyntaxError() {
		testWorkspaceManager.createTestOnDisk(
			"ProjectA" -> #[],
			"ProjectB" -> #[],
			"ProjectMain" -> #[
				PACKAGE_JSON -> '''
					{
						"name": "ProjectMain",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"output": "src-gen",
							"sources": { "source": ["src"] }
						},
						"dependencies": {
							"n4js-runtime": "*",
							"ProjectA": "^1..3",
							"ProjectB": "^.2.3"
						}
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(
			"ProjectMain/" + PACKAGE_JSON -> #[
				'''(Error, [10:18 - 10:20], Invalid version number "^1..3": no viable alternative at input '.'.)''',
				'''(Error, [11:16 - 11:20], Invalid version number "^.2.3": no viable alternative at input '.'.)'''
			]
		);
		assertNoErrorsInLogOrOutput();
	}
}
