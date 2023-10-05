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

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;


public class GH_1846_InitialBuildShowsMoreErrorsThanIncrementalBuildTest extends AbstractIdeTest {

	@Test
	public void testMinimal_singleProject() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("A", """
							export public const someConstant: string = null;
							export public class A {}
						"""),
				Pair.of("Main", """
							import {A} from "A"
							new A();
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", Pair.of("string", "X"));
		joinServerRequests();
		List<Pair<String, List<String>>> expectedErrors = List.of(
				Pair.of("A", List.of(
						"(Error, [0:35 - 0:36], Couldn't resolve reference to Type 'X'.)")));
		assertIssues2(expectedErrors);
		assertStableState("Main", expectedErrors);

		cleanBuildAndWait();
		assertIssues2(expectedErrors);
		assertStableState(List.of("A", "Main"), expectedErrors);
	}

	@Test
	public void testMinimal_multipleProjects() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"A", """
									export public const someConstant: string = null;
									export public class A {}
								"""),
				"ProjectMain", Map.of(
						"Main", """
									import {A} from "A"
									new A();
								""",
						CFG_DEPENDENCIES, """
									ProjectA
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", Pair.of("string", "X"));
		joinServerRequests();
		List<Pair<String, List<String>>> expectedErrors = List.of(
				Pair.of("A", List.of(
						"(Error, [0:35 - 0:36], Couldn't resolve reference to Type 'X'.)")));
		assertIssues2(expectedErrors);
		assertStableState("Main", expectedErrors);

		cleanBuildAndWait();
		assertIssues2(expectedErrors);
		assertStableState(List.of("A", "Main"), expectedErrors);
	}

	@Test
	public void testExampleFromTaskDescription() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"IDataCollection", """
									export public interface IDataCollection {
										public size() : number;
									}
								""",
						"IDataList", """
									import {IDataCollection} from "IDataCollection";

									export public interface IDataList extends IDataCollection {
										public get(idx: number): Object;
									}
								""",
						"MainA", """
									import {IDataList} from "IDataList";

									let l: IDataList;
									l = null;
								"""),
				"ProjectB", Map.of(
						"MainB", """
									import {IDataList} from "IDataList";

									let l: IDataList;
									l = null;
								""",
						CFG_DEPENDENCIES, """
									ProjectA
								""")));

		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("IDataCollection", Pair.of("IDataCollection", "IDataCollectionX"));
		joinServerRequests();
		List<Pair<String, List<String>>> expectedErrors = List.of(
				Pair.of("IDataList", List.of(
						"(Error, [0:9 - 0:24], Import of IDataCollection cannot be resolved.)",
						"(Error, [2:43 - 2:58], Couldn't resolve reference to Type 'IDataCollection'.)")));
		assertIssues2(expectedErrors);
		assertStableState(List.of("MainA", "MainB"), expectedErrors);

		cleanBuildAndWait();
		assertIssues2(expectedErrors);
		assertStableState(List.of("MainA", "MainB"), expectedErrors);
	}

	private void assertStableState(List<String> namesOfModulesToTouch,
			List<Pair<String, List<String>>> expectedErrors) {

		assertTrue(namesOfModulesToTouch != null && !namesOfModulesToTouch.isEmpty());
		for (String moduleName : namesOfModulesToTouch) {
			assertStableState(moduleName, expectedErrors);
		}
	}

	private void assertStableState(String nameOfModuleToTouch, List<Pair<String, List<String>>> expectedErrors) {
		openFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues2(expectedErrors);

		// apply a change that will alter the nameOfModuleToTouch's TModule (i.e. add exported element)
		String codeToAppend = """
					export public const dummy = "dummy";
				""";
		changeOpenedFile(nameOfModuleToTouch, str -> str + codeToAppend);
		joinServerRequests();
		assertIssues2(expectedErrors);

		saveOpenedFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues2(expectedErrors);

		// undo the above change
		changeOpenedFile(nameOfModuleToTouch, Pair.of(codeToAppend, ""));
		joinServerRequests();
		assertIssues2(expectedErrors);

		saveOpenedFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues2(expectedErrors);

		closeFile(nameOfModuleToTouch);
		joinServerRequests();
		assertIssues2(expectedErrors);
	}
}
