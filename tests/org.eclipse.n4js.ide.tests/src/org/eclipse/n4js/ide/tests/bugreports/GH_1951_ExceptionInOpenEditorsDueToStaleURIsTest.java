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
package org.eclipse.n4js.ide.tests.bugreports;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * GH-1951: Exception in open editors due to stale URIs from other opened editors
 */

public class GH_1951_ExceptionInOpenEditorsDueToStaleURIsTest extends AbstractIdeTest {

	@Test
	public void testMinimal() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							export public class A {}
						""",
				"B", """
							import {A} from "A";
							export public class B {
								public field: A;
							}
						""",
				"Main", """
							import {B} from "B";
							new B().field;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("B");
		joinServerRequests();
		openFile("Main");
		joinServerRequests();
		assertNoIssues();

		deleteNonOpenedFile(getFileURIFromModuleName("A"));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // before the fix it logged: "cannot install derived state in resource
										// '.../A.n4js' without AST"
		assertIssues2(Pair.of(
				"B", List.of(
						"(Error, [0:17 - 0:20], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [2:16 - 2:17], Couldn't resolve reference to Type 'A'.)")));
	}

	/**
	 * Exactly the same test as {@link #testMinimal()}, but the relation between the two opened files is symmetrical.
	 * This ensures that the order in which their resource task contexts are refreshed does not matter. With the current
	 * implementation of {@link ResourceTaskManager} the code in {@link #testMinimal()} is sufficient to consistently
	 * reproduce the bug (if the fix is commented out), but this might change in the future, so we also provide this
	 * symmetrical test case.
	 */
	@Test
	public void testSymmetrical() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							export public class A {}
						""",
				"Main1", """
							import {A} from "A";
							import {Main2} from "Main2";
							export public class Main1 {
								public field: A;
							}
							function foo() {
								new Main2().field; // wrapped in foo() only to avoid load time dependency cycle
							}
						""",
				"Main2", """
							import {A} from "A";
							import {Main1} from "Main1";
							export public class Main2 {
								public field: A;
							}
							function foo() {
								new Main1().field; // wrapped in foo() only to avoid load time dependency cycle
							}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("Main1");
		joinServerRequests();
		openFile("Main2");
		joinServerRequests();
		assertNoIssues();

		deleteNonOpenedFile(getFileURIFromModuleName("A"));
		joinServerRequests();
		assertNoErrorsInLogOrOutput(); // before the fix it logged: "cannot install derived state in resource
										// '.../A.n4js' without AST"
		assertIssues2(Pair.of(
				"Main1", List.of(
						"(Error, [0:17 - 0:20], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [3:16 - 3:17], Couldn't resolve reference to Type 'A'.)")),
				Pair.of("Main2", List.of(
						"(Error, [0:17 - 0:20], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [3:16 - 3:17], Couldn't resolve reference to Type 'A'.)")));
	}
}
