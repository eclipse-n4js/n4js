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
package org.eclipse.n4js.integration.tests.dts

import java.util.List
import org.eclipse.n4js.cli.helper.CliTools
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.workspace.locations.FileURI
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

/**
 * Testing .d.ts support with real-world type definitions.
 */
class DtsRealWorldIdeTest extends AbstractIdeTest {

	/**
	 * Real-world npm packages containing .d.ts files that will be tested in the test cases
	 * of this class, given as pairs of package name and version constraint.
	 * <p>
	 * These projects may or may not be <code>@types</code> projects.
	 */
	private static final Pair<String, String>[] PACKAGES_TO_TEST = #[
		"@types/node" -> "17.0.27"
	];


	@Test
	def void testNode() {
		assertDtsUsage('''
			import * as url from "url"
			let u: url.Url;
			let v01: number = u.href;
			let v02: string = u.href;
		''', #[
			"(Error, [2:18 - 2:24], string is not a subtype of number.)"
		]);
	}

	@Test
	def void testNode_withColonInModuleSpecifier() {
		assertDtsUsage('''
			import * as url from "node:url"
			let u: url.Url;
			let v01: number = u.href;
			let v02: string = u.href;
		''', #[
			"(Error, [2:18 - 2:24], string is not a subtype of number.)"
		]);
	}


	private FileURI mainModuleURI = null;

	@Before
	def void prepare() {
		if (mainModuleURI !== null) {
			return; // already prepared
		}

		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				// test cases will replace this with test code
			''',
			PACKAGE_JSON -> '''
				{
					"name": "«DEFAULT_PROJECT_NAME»",
					"version": "0.0.1",
					"type": "module",
					"n4js": {
						"projectType": "library",
						"vendorId": "VENDOR",
						"vendorName": "VENDOR_name",
						"output": "src-gen",
						"sources": {
							"source": [
								"src"
							]
						}
					},
					"dependencies": {
						"n4js-runtime": "",
						«PACKAGES_TO_TEST.map['''"«key»": "«value»"'''].join(", ")»
					}
				}
			'''
		);

		val root = getProjectRoot();
		val result = new CliTools().npmInstall(root.toPath);
		assertNull(result.exception);
		assertEquals(0, result.exitCode);

		startAndWaitForLspServer();
		mainModuleURI = getFileURIFromModuleName("Main");
		openFile(mainModuleURI);

		joinServerRequests();
		assertNoErrors();
		// note: we will have warnings along the lines of ...
		// "The implementation project node of type definition project @types/node is missing from the dependencies section."
	}

	def private void assertDtsUsage(CharSequence sourceCode, List<String> expectedIssuesInMain) {
		assertNotNull(mainModuleURI);
		changeOpenedFile(mainModuleURI, sourceCode);
		joinServerRequests();
		assertIssuesInModules("Main" -> expectedIssuesInMain);
	}
}
