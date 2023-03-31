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
package org.eclipse.n4js.ide.dts.tests;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Test;

/**
 */
@SuppressWarnings("javadoc")
public class TsConfigCreationTest extends AbstractIdeTest {

	private static Map<String, String> testData = Map.of(
			"SomeModule", """
						export public class SomeClass {
						}
					""",
			PACKAGE_JSON, """
						{
							"name": "%s",
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
					""".formatted(DEFAULT_PROJECT_NAME));

	@Test
	public void testCreateTSConfigFile() {
		testWorkspaceManager.createTestProjectOnDisk(testData);
		startAndWaitForLspServer();
		assertNoIssues();

		shutdownLspServer();

		FileURI tsconfigUri = getFileURIFromModuleName(N4JSGlobals.TS_CONFIG);
		String contents = getContentOfFileOnDisk(tsconfigUri);
		assertEquals("""
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
				""", contents);
	}

}
