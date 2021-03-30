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

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager
import org.junit.Test

/**
 * GH-1951: Exception in open editors due to stale URIs from other opened editors
 */
class GH_1951_ExceptionInOpenEditorsDueToStaleURIsTest extends AbstractIdeTest {

	@Test
	def void testMinimal() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				export public class A {}
			''',
			"B" -> '''
				import {A} from "A";
				export public class B {
					public field: A;
				}
			''',
			"Main" -> '''
				import {B} from "B";
				new B().field;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("B");
		joinServerRequests();
		openFile("Main");
		joinServerRequests();
		assertNoIssues();

		deleteNonOpenedFile(getFileURIFromModuleName("A"));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // before the fix it logged: "cannot install derived state in resource '.../A.n4js' without AST"
		assertIssues(
			"B" -> #[
				"(Error, [0:16 - 0:19], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [2:15 - 2:16], Couldn't resolve reference to Type 'A'.)"
			]
		);
	}

	/**
	 * Exactly the same test as {@link #testMinimal()}, but the relation between the two opened files is symmetrical.
	 * This ensures that the order in which their resource task contexts are refreshed does not matter. With the current
	 * implementation of {@link ResourceTaskManager} the code in {@link #testMinimal()} is sufficient to consistently
	 * reproduce the bug (if the fix is commented out), but this might change in the future, so we also provide this
	 * symmetrical test case.
	 */
	@Test
	def void testSymmetrical() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				export public class A {}
			''',
			"Main1" -> '''
				import {A} from "A";
				import {Main2} from "Main2";
				export public class Main1 {
					public field: A;
				}
				function foo() {
					new Main2().field; // wrapped in foo() only to avoid load time dependency cycle
				}
			''',
			"Main2" -> '''
				import {A} from "A";
				import {Main1} from "Main1";
				export public class Main2 {
					public field: A;
				}
				function foo() {
					new Main1().field; // wrapped in foo() only to avoid load time dependency cycle
				}
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main1");
		joinServerRequests();
		openFile("Main2");
		joinServerRequests();
		assertNoIssues();

		deleteNonOpenedFile(getFileURIFromModuleName("A"));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // before the fix it logged: "cannot install derived state in resource '.../A.n4js' without AST"
		assertIssues(
			"Main1" -> #[
				"(Error, [0:16 - 0:19], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [3:15 - 3:16], Couldn't resolve reference to Type 'A'.)"
			],
			"Main2" -> #[
				"(Error, [0:16 - 0:19], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [3:15 - 3:16], Couldn't resolve reference to Type 'A'.)"
			]
		);
	}
}
