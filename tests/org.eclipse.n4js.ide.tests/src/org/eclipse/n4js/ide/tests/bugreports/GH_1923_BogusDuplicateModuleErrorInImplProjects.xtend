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
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

class GH_1923_BogusDuplicateModuleErrorInImplProjects extends AbstractIdeTest {

	@Test
	def void testApiImplProjects() {
		testWorkspaceManager.createTestOnDisk(
			"ProjectApi" -> #[
				"Module.n4jsd" -> '''
					export external public class Cls {
						public m(p: string): number;
					}
				''',
				TestWorkspaceManager.PACKAGE_JSON -> '''
					{
						"name": "ProjectApi",
						"version": "0.0.1",
						"n4js": {
							"projectType": "api",
							"sources": {
								"source": [
									"src"
								]
							},
							"output": "src-gen"
						}
					}
				'''
			],
			"ProjectImpl" -> #[
				"Module.n4js" -> '''
					export public class Cls {
						public m(p: string): number {
							console.log('I am the implementation.');
							return 0;
						}
					}
				''',
				TestWorkspaceManager.PACKAGE_JSON -> '''
					{
						"name": "ProjectImpl",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"implementationId": "web",
							"implementedProjects": [
								"ProjectApi"
							],
							"sources": {
								"source": [
									"src"
								]
							},
							"output": "src-gen"
						},
						"dependencies": {
							"n4js-runtime": "*"
						}
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Module.n4js");
		joinServerRequests();
		// bogus error before the fix:
		// (Error, [0:0 - 5:1], A duplicate module Module is also defined in .../yarn-test-project/packages/ProjectApi/src/Module.n4jsd.)
		assertNoIssues();

		closeFile("Module.n4js");
		joinServerRequests();
		assertNoIssues();

		openFile("Module.n4jsd");
		joinServerRequests();
		assertNoIssues();

		closeFile("Module.n4jsd");
		joinServerRequests();
		assertNoIssues();
	}
}
