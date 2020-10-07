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

import java.util.List
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

import static org.junit.Assert.assertTrue

class GH_1846_InitialBuildShowsMoreErrorsThanIncrementalBuild extends AbstractIdeTest {

	@Test
	def void testMinimal_singleProject() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				export public const someConstant: string = null;
				export public class A {}
			''',
			"Main" -> '''
				import {A} from "A"
				new A();
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", "string" -> "X");
		joinServerRequests();
		val expectedErrors = #[
			"A" -> #[
				"(Error, [0:34 - 0:35], Couldn't resolve reference to Type 'X'.)"
			]
		];
		assertIssues(expectedErrors);
		assertStableState("Main", expectedErrors);

		cleanBuildAndWait();
		assertIssues(expectedErrors);
		assertStableState(#["A", "Main"], expectedErrors);
	}

	@Test
	def void testMinimal_multipleProjects() {
		testWorkspaceManager.createTestOnDisk(
			"ProjectA" -> #[
				"A" -> '''
					export public const someConstant: string = null;
					export public class A {}
				'''
			],
			"ProjectMain" -> #[
				"Main" -> '''
					import {A} from "A"
					new A();
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					ProjectA
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", "string" -> "X");
		joinServerRequests();
		val expectedErrors = #[
			"A" -> #[
				"(Error, [0:34 - 0:35], Couldn't resolve reference to Type 'X'.)"
			]
		];
		assertIssues(expectedErrors);
		assertStableState("Main", expectedErrors);

		cleanBuildAndWait();
		assertIssues(expectedErrors);
		assertStableState(#["A", "Main"], expectedErrors);
	}

	@Test
	def void testExampleFromTaskDescription() {
		testWorkspaceManager.createTestOnDisk(
			"ProjectA" -> #[
				"IDataCollection" -> '''
					export public interface IDataCollection {
						public size() : number;
					}
				''',
				"IDataList" -> '''
					import {IDataCollection} from "IDataCollection";

					export public interface IDataList extends IDataCollection {
						public get(idx: number): Object;
					}
				''',
				"MainA" -> '''
					import {IDataList} from "IDataList";

					let l: IDataList;
					l = null;
				'''
			],
			"ProjectB" -> #[
				"MainB" -> '''
					import {IDataList} from "IDataList";

					let l: IDataList;
					l = null;
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> '''
					ProjectA
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("IDataCollection", "IDataCollection" -> "IDataCollectionX");
		joinServerRequests();
		val expectedErrors = #[
			"IDataList" -> #[
				"(Error, [0:8 - 0:23], Import of IDataCollection cannot be resolved.)",
				"(Error, [2:42 - 2:57], Couldn't resolve reference to Type 'IDataCollection'.)"
			]
		];
		assertIssues(expectedErrors);
		assertStableState(#[ "MainA", "MainB" ], expectedErrors);

		cleanBuildAndWait();
		assertIssues(expectedErrors);
		assertStableState(#[ "MainA", "MainB" ], expectedErrors);
	}

	def private void assertStableState(List<String> namesOfModulesToTouch, Pair<String, List<String>>... expectedErrors) {
		assertTrue(namesOfModulesToTouch !== null && !namesOfModulesToTouch.empty);
		for (moduleName : namesOfModulesToTouch) {
			assertStableState(moduleName, expectedErrors);
		}
	}

	def private void assertStableState(String nameOfModuleToTouch, Pair<String, List<String>>... expectedErrors) {
		openFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues(expectedErrors);

		// apply a change that will alter the nameOfModuleToTouch's TModule (i.e. add exported element)
		val codeToAppend = '''
			export public const dummy = "dummy";
		''';
		changeOpenedFile(nameOfModuleToTouch, [it + codeToAppend]);
		joinServerRequests();
		assertIssues(expectedErrors);

		saveOpenedFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues(expectedErrors);

		// undo the above change
		changeOpenedFile(nameOfModuleToTouch, codeToAppend -> "");
		joinServerRequests();
		assertIssues(expectedErrors);

		saveOpenedFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues(expectedErrors);

		closeFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues(expectedErrors);
	}
}
