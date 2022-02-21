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

import org.eclipse.n4js.cli.helper.CliTools.CliException
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Assert
import org.junit.Test

/**
 * Test for package.json property {@code n4js -> generator -> rewriteCjsImports}.
 */
class RewriteCjsImportsTest extends AbstractIdeTest {

	@Test
	def void testRewriteNotRequiredBecauseCjsModuleIsGoodCitizen() {
		testWorkspaceManager.createTestProjectOnDisk(
			"plain.cjs" -> '''
				module.exports.foo = function() {
					return "message from foo";
				};
			''',
			"plain.n4jsd" -> '''
				export external function foo(): string;
			''',
			"ModuleWithNamedImport" -> '''
				import { foo } from "plain";
				console.log(foo());
			''',
			"ModuleWithNamespaceImport" -> '''
				import * as P from "plain";
				console.log(P.foo());
			'''
		);
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// ensure the output files contain the import we want to test:
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamedImport",     "import {foo} from './plain.cjs'");
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamespaceImport", "import * as P from './plain.cjs'");

		// main test #1: named import in native esm output code should work:
		val fileURI1 = getOutputFile("ModuleWithNamedImport").toFileURI;
		val actualOutput1 = runInNodejs(fileURI1).stdOut;
		val expectedOutput1 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput1, actualOutput1);
		// main test #2: namespace import in native esm output code should work:
		val fileURI2 = getOutputFile("ModuleWithNamespaceImport").toFileURI;
		val actualOutput2 = runInNodejs(fileURI2).stdOut;
		val expectedOutput2 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput2, actualOutput2);
	}

	@Test
	def void testRewriteRequiredBecauseCjsModuleIsBadCitizen_failing() {
		testWorkspaceManager.createTestProjectOnDisk(
			"plain.cjs" -> '''
				module.exports[getNameFoo()] = function() {
					return "message from foo";
				}
				function getNameFoo() {
					return "foo";
				}
			''',
			"plain.n4jsd" -> '''
				export external function foo(): string;
			''',
			"ModuleWithNamedImport" -> '''
				import { foo } from "plain";
				console.log(foo());
			''',
			"ModuleWithNamespaceImport" -> '''
				import * as P from "plain";
				console.log(P.foo());
			'''
		);
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// ensure the output file contains the import we want to test:
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamedImport",     "import {foo} from './plain.cjs'");
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamespaceImport", "import * as P from './plain.cjs'");

		// main test #1: named import in native esm output code should fail:
		val fileURI1 = getOutputFile("ModuleWithNamedImport").toFileURI;
		try {
			runInNodejs(fileURI1).stdOut;
			Assert.fail("expected exception was not thrown");
		} catch(CliException e) {
			Assert.assertTrue("message of exception did not contain the expected sub-string:\n" + e.message, e.message.contains(
				"SyntaxError: Named export 'foo' not found. The requested module './plain.cjs' is a CommonJS module, which may not support all module.exports as named exports.\n"
				+ "CommonJS modules can always be imported via the default export"));
		}
		// main test #2: namespace import in native esm output code should fail:
		// (in this case, the import itself does not throw an exception but property access on the namespace evaluates to 'undefined')
		val fileURI = getOutputFile("ModuleWithNamespaceImport").toFileURI;
		try {
			runInNodejs(fileURI).stdOut;
			Assert.fail("expected exception was not thrown");
		} catch(CliException e) {
			Assert.assertTrue("message of exception did not contain the expected sub-string:\n" + e.message, e.message.contains(
				"TypeError: P.foo is not a function"));
		}
	}

	@Test
	def void testRewriteRequiredBecauseCjsModuleIsBadCitizen_fixedByTranspilerMagic() {
		testWorkspaceManager.createTestProjectOnDisk(
			"plain.cjs" -> '''
				module.exports[getNameFoo()] = function() {
					return "message from foo";
				}
				function getNameFoo() {
					return "foo";
				}
			''',
			"plain.n4jsd" -> '''
				export external function foo(): string;
			''',
			"ModuleWithNamedImport" -> '''
				import { foo } from "plain";
				console.log(foo());
			''',
			"ModuleWithNamespaceImport" -> '''
				import * as P from "plain";
				console.log(P.foo());
			''',
			PACKAGE_JSON -> '''
				{
					"name": "«DEFAULT_PROJECT_NAME»",
					"version": "0.0.1",
					"type": "module",
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
							"rewriteCjsImports": true  ««« turning on transpiler magic here!
						}
					},
					"dependencies": {
						"n4js-runtime": "*"
					}
				}
			'''
		);
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// named import in native esm output code should succeed because it was rewritten to default import + destructuring:
		val fileURI1 = getOutputFile("ModuleWithNamedImport").toFileURI;
		val actualOutput1 = runInNodejs(fileURI1).stdOut;
		val expectedOutput1 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput1, actualOutput1);
		// namespace import in native esm output code should succeed because it was rewritten to default import + destructuring:
		val fileURI2 = getOutputFile("ModuleWithNamespaceImport").toFileURI;
		val actualOutput2 = runInNodejs(fileURI2).stdOut;
		val expectedOutput2 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput2, actualOutput2);
	}
}
