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
package org.eclipse.n4js.ide.tests.builder

import java.util.List
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

import static org.junit.Assert.assertTrue

/**
 * Test to ensure consistency between initial and incremental builds: an incremental build must yield
 * the same result as a following clean & full build.
 */
class BuilderConsistencyTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testFullBuildConsistentWithIncrementalBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.NODE_MODULES + "n4js-runtime" -> null,
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

					//export let x: XXX = undefined;
				''',
				"MainA" -> '''
					import {IDataList} from "IDataList";
					let l: IDataList;
					l = null;
				''',
				TestWorkspaceManager.DEPENDENCIES -> "n4js-runtime"
			],
			"ProjectB" -> #[
				"MainB" -> '''
					import {IDataList} from "IDataList";
					let l: IDataList;
					l = null;
				''',
				TestWorkspaceManager.DEPENDENCIES -> '''
					n4js-runtime,
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
