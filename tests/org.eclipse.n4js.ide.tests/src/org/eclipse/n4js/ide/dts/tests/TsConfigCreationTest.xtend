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
package org.eclipse.n4js.ide.dts.tests

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test
import static org.junit.Assert.assertEquals
import org.eclipse.n4js.N4JSGlobals

/**
 */
class TsConfigCreationTest extends AbstractIdeTest {

	private static val testData = #[
			"SomeModule" -> '''
				export public class SomeClass {
				}
			''',
			PACKAGE_JSON -> '''
				{
					"name": "«DEFAULT_PROJECT_NAME»",
					"version": "0.0.1",
					"dependencies": {
						"n4js-runtime": "*"
					},
					"n4js": {
						"projectType": "library",
						"output": "src-gen",
						"sources": {
							"source": [
								"src"
							]
						},
						"generator": {
							"d.ts": true
						}
					}
				}
			'''
		];


	@Test
	def void testCreateTSConfigFile() {
		testWorkspaceManager.createTestProjectOnDisk(testData);
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();
		
		val tsconfigUri = getFileURIFromModuleName(N4JSGlobals.TS_CONFIG);
		val contents = getContentOfFileOnDisk(tsconfigUri);
		assertEquals('''
		{
		    "include": ["src-gen/**/*.ts"],
		    "exclude": ["node_modules"],
		    "compilerOptions": {
		        "target": "es5",
		        "lib": ["es2019", "es2020"],
		        "module": "commonjs",
		        "noImplicitAny": false
		    }
		}
		'''.toString, contents);
	}

}
