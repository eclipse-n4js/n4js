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
package org.eclipse.n4js.ide.tests.spec

import java.util.Map
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.junit.Test

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.DEPENDENCIES
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.MAIN_MODULE
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.N4JS_RUNTIME
import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.NODE_MODULES

/**
 * Tests for the error messages of unresolved imports. See also Xpect test file 'ImportsUnresolved.n4js.xt'.
 */
class ImportsUnresolvedTest extends AbstractIdeTest {

	@Test
	def void testPlainModuleSpecifier01() {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {X} from "OtherProject"
				''',
				DEPENDENCIES -> N4JS_RUNTIME // note: OtherProject not defined here!
			],
			"OtherProject" -> #[
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		// The module specifier looks like a project import because its only segment denotes a project,
		// but it is interpreted as a plain module specifier, because that project is NOT among the project
		// dependencies defined in the package.json file.
		assertIssues(
			"Main" -> #[
				"(Error, [0:16 - 0:30], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testPlainModuleSpecifier02() {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {X} from "OtherProject/a/b/SomeModule"
				''',
				DEPENDENCIES -> N4JS_RUNTIME // note: OtherProject not defined here!
			],
			"OtherProject" -> #[
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		// The module specifier looks like a complete module specifier, because its first segment denotes a project,
		// but it is interpreted as a plain module specifier, because that project is NOT among the project
		// dependencies defined in the package.json file.
		assertIssues(
			"Main" -> #[
				"(Error, [0:16 - 0:45], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testCompleteModuleSpecifier() {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {X} from "OtherProject/a/b/SomeModule"
				''',
				DEPENDENCIES -> '''
					«N4JS_RUNTIME»,
					OtherProject
				'''
			],
			"OtherProject" -> #[
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		// The module specifier is interpreted as a complete module specifier because its first segment denotes
		// a project that is also among the project dependencies defined in the package.json file.
		assertIssues(
			"Main" -> #[
				"(Error, [0:16 - 0:45], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testProjectImport_noMainModuleDefined() {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {X} from "OtherProject"
				''',
				DEPENDENCIES -> '''
					«N4JS_RUNTIME»,
					OtherProject
				'''
			],
			"OtherProject" -> #[
				// no main module defined
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		assertIssues(
			"Main" -> #[
				// This is the message we would like to see here:
				// "(Error, [0:16 - 0:30], Cannot resolve project import: target project does not define a main module.)"
				// However, package.json property "mainModule" has a default value of "index", so we cannot really test this
				// case and instead get the error message as in case "main module defined but does not exist":
				"(Error, [0:16 - 0:30], Cannot resolve project import: no matching module found.)"
			]
		);
	}

	@Test
	def void testProjectImport_mainModuleDefinedButDoesNotExist() {
		testWorkspaceManager.createTestOnDisk(
			NODE_MODULES + N4JS_RUNTIME -> null,
			"MainProject" -> #[
				"Main" -> '''
					import {X} from "OtherProject"
				''',
				DEPENDENCIES -> '''
					«N4JS_RUNTIME»,
					OtherProject
				'''
			],
			"OtherProject" -> #[
				MAIN_MODULE -> "Other",
				DEPENDENCIES -> N4JS_RUNTIME
			]
		);
		startAndWaitForLspServer();

		assertIssues(Map.of(
			getFileURIFromModuleName("Main"), #[
				"(Error, [0:16 - 0:30], Cannot resolve project import: no matching module found.)"
			],
			getPackageJsonFile("OtherProject").toFileURI, #[
				"(Error, [7:17 - 7:24], Main module specifier Other does not exist.)"
			]
		));
	}
}
