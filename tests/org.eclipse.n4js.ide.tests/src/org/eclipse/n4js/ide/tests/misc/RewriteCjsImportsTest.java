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
package org.eclipse.n4js.ide.tests.misc;

import java.util.Map;

import org.eclipse.n4js.cli.helper.CliTools.CliException;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for package.json property {@code n4js , generator , rewriteCjsImports}.
 */
@SuppressWarnings("javadoc")
public class RewriteCjsImportsTest extends AbstractIdeTest {

	@Test
	public void testRewriteNotRequiredBecauseCjsModuleIsGoodCitizen() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"plain.cjs", """
						module.exports.foo = function() {
							return "message from foo";
						};
						""",
				"plain.n4jsd", """
						export external function foo(): string;
						""",
				"ModuleWithNamedImport", """
						import { foo } from "plain";
						console.log(foo());
						""",
				"ModuleWithNamespaceImport", """
						import * as P from "plain";
						console.log(P.foo());
						"""));
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// ensure the output files contain the import we want to test:
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamedImport", "import {foo} from './plain.cjs'");
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamespaceImport", "import * as P from './plain.cjs'");

		// main test #1: named import in native esm output code should work:
		FileURI fileURI1 = toFileURI(getOutputFile("ModuleWithNamedImport"));
		String actualOutput1 = runInNodejs(fileURI1).getStdOut();
		String expectedOutput1 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput1, actualOutput1);
		// main test #2: namespace import in native esm output code should work:
		FileURI fileURI2 = toFileURI(getOutputFile("ModuleWithNamespaceImport"));
		String actualOutput2 = runInNodejs(fileURI2).getStdOut();
		String expectedOutput2 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput2, actualOutput2);
	}

	@Test
	public void testRewriteRequiredBecauseCjsModuleIsBadCitizen_failing() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"plain.cjs", """
						module.exports[getNameFoo()] = function() {
							return "message from foo";
						}
						function getNameFoo() {
							return "foo";
						}
						""",
				"plain.n4jsd", """
						export external function foo(): string;
						""",
				"ModuleWithNamedImport", """
						import { foo } from "plain";
						console.log(foo());
						""",
				"ModuleWithNamespaceImport", """
						import * as P from "plain";
						console.log(P.foo());
						"""));
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// ensure the output file contains the import we want to test:
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamedImport", "import {foo} from './plain.cjs'");
		assertOutputFileContains(DEFAULT_PROJECT_NAME, "ModuleWithNamespaceImport", "import * as P from './plain.cjs'");

		// main test #1: named import in native esm output code should fail:
		FileURI fileURI1 = toFileURI(getOutputFile("ModuleWithNamedImport"));
		try {
			runInNodejs(fileURI1).getStdOut();
			Assert.fail("expected exception was not thrown");
		} catch (CliException e) {
			Assert.assertTrue("message of exception did not contain the expected sub-string:\n" + e.getMessage(),
					e.getMessage().contains(
							"SyntaxError: Named export 'foo' not found. The requested module './plain.cjs' is a CommonJS module, which may not support all module.exports as named exports.\n"
									+ "CommonJS modules can always be imported via the default export"));
		}
		// main test #2: namespace import in native esm output code should fail:
		// (in this case, the import itself does not throw an exception but property access on the namespace evaluates
		// to 'undefined')
		FileURI fileURI = toFileURI(getOutputFile("ModuleWithNamespaceImport"));
		try {
			runInNodejs(fileURI).getStdOut();
			Assert.fail("expected exception was not thrown");
		} catch (CliException e) {
			Assert.assertTrue("message of exception did not contain the expected sub-string:\n" + e.getMessage(),
					e.getMessage().contains(
							"TypeError: P.foo is not a function"));
		}
	}

	@Test
	public void testRewriteRequiredBecauseCjsModuleIsBadCitizen_fixedByTranspilerMagic() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"plain.cjs", """
						module.exports[getNameFoo()] = function() {
							return "message from foo";
						}
						function getNameFoo() {
							return "foo";
						}
						""",
				"plain.n4jsd", """
						export external function foo(): string;
						""",
				"ModuleWithNamedImport", """
						import { foo } from "plain";
						console.log(foo());
						""",
				"ModuleWithNamespaceImport", """
						import * as P from "plain";
						console.log(P.foo());
						""",

				// turning on transpiler magic with 'rewriteCjsImports'
				PACKAGE_JSON, """
						{
							"name": "%s",
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
									"rewriteCjsImports": true
								}
							},
							"dependencies": {
								"n4js-runtime": "*"
							}
						}
						""".formatted(DEFAULT_PROJECT_NAME)));
		installN4JSRuntime();
		startAndWaitForLspServer();
		assertNoIssues();

		// named import in native esm output code should succeed because it was rewritten to default import +
		// destructuring:
		FileURI fileURI1 = toFileURI(getOutputFile("ModuleWithNamedImport"));
		String actualOutput1 = runInNodejs(fileURI1).getStdOut();
		String expectedOutput1 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput1, actualOutput1);
		// namespace import in native esm output code should succeed because it was rewritten to default import +
		// destructuring:
		FileURI fileURI2 = toFileURI(getOutputFile("ModuleWithNamespaceImport"));
		String actualOutput2 = runInNodejs(fileURI2).getStdOut();
		String expectedOutput2 = "message from foo";
		Assert.assertEquals("wrong output", expectedOutput2, actualOutput2);
	}
}
