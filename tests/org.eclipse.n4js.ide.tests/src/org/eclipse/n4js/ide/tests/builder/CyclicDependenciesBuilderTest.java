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
package org.eclipse.n4js.ide.tests.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test various cases of changes between LSP server sessions to ensure the initial build of the later session correctly
 * reacts to those changes.
 */

public class CyclicDependenciesBuilderTest extends AbstractIncrementalBuilderTest {

	private static Map<String, Map<String, String>> testData1 = Map.of(
			"P1", Map.of(
					"M1", """
							export public class C1 {
							}
							"""),
			"P2", Map.of(
					"M2", """
							export public class C2 {}
							""",
					CFG_DEPENDENCIES, """
							P1
							"""));

	private static Map<String, Map<String, String>> testData2 = Map.of(
			"P1", Map.of(
					"M1", """
							export public class C1 {
							}
							""",
					CFG_DEPENDENCIES, """
							P2
							"""),
			"P2", Map.of(
					"M2", """
							export public class C2 {}
							""",
					CFG_DEPENDENCIES, """
							P1
							"""));

	private static Map<String, Map<String, String>> testData3 = Map.of(
			"P1", Map.of(
					"M1", """
							export public class C1 {
							}
							""",
					"I1A", """
							import {C2} from "M2"; // expect error
							const x: C2 = null;
							x;
							""",
					"I1B", """
							import {C1} from "M1"; // expect no error
							const x: C1 = null;
							x;
							""",
					CFG_DEPENDENCIES, """
							P2
							"""),
			"P2", Map.of(
					"M2", """
									export public class C2 {}
							""",
					"I2A", """
							import {C1} from "M1"; // expect error
							const x: C1 = null;
							x;
							""",
					"I2B", """
							import {C2} from "M2"; // expect no error
							const x: C2 = null;
							x;
							""",
					CFG_DEPENDENCIES, """
							P1
							"""));

	private static Path temporaryFolder;

	@BeforeClass
	public static void createTemporaryFolder() throws IOException {
		temporaryFolder = Files.createTempDirectory("temp_InitialBuildTest_");
	}

	@AfterClass
	public static void deleteTemporaryFolder() throws IOException {
		if (temporaryFolder != null) {
			FileDeleter.delete(temporaryFolder);
		}
	}

	@Before
	public void cleanTemporaryFolder() throws IOException {
		FileUtils.cleanFolder(temporaryFolder);
	}

	@Test
	public void testAddCycle() {
		testWorkspaceManager.createTestOnDisk(testData1);

		startAndWaitForLspServer();

		assertNoIssues();

		openFile("P1/package.json");

		assertNoIssues();

		changeOpenedFile("P1/package.json", Pair.of("\"n4js-runtime\": \"\"", "\"n4js-runtime\": \"\", \"P2\": \"\""));

		assertNoIssues();

		saveOpenedFile("P1/package.json");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:38 - 14:42], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));
	}

	@Test
	public void testRemoveCycle() {
		testWorkspaceManager.createTestOnDisk(testData2);

		startAndWaitForLspServer();

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));

		openFile("P1/package.json");

		changeOpenedFile("P1/package.json", Pair.of("\"P2\": \"\",", ""));

		saveOpenedFile("P1/package.json");

		assertNoIssues();
	}

	@Test
	public void testOnlyDirtyStateBuildInsideCycle() {
		testWorkspaceManager.createTestOnDisk(testData2);

		startAndWaitForLspServer();

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));

		openFile("M1");

		changeOpenedFile("M1", Pair.of("export public class C1 {", "export public class C1 { #"));

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		saveOpenedFile("M1");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		closeFile("M1");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));
	}

	@Test
	public void testBuildDirtyAfterRemoveCycle1() {
		testWorkspaceManager.createTestOnDisk(testData2);

		startAndWaitForLspServer();

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));

		openFile("M1");

		changeOpenedFile("M1", Pair.of("export public class C1 {", "export public class C1 { #"));

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		saveOpenedFile("M1");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		closeFile("M1");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		// remove cycle
		openFile("P1/package.json");
		changeOpenedFile("P1/package.json", Pair.of("\"P2\": \"\",", ""));
		saveOpenedFile("P1/package.json");

		assertIssues2(Map.of(
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));
	}

	@Test
	public void testBuildDirtyAfterRemoveCycle2() {
		testWorkspaceManager.createTestOnDisk(testData2);

		startAndWaitForLspServer();

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)")));

		openFile("M1");

		changeOpenedFile("M1", Pair.of("export public class C1 {", "export public class C1 { #"));

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		saveOpenedFile("M1");

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));

		// remove cycle
		openFile("P1/package.json");
		changeOpenedFile("P1/package.json", Pair.of("\"P2\": \"\",", ""));
		saveOpenedFile("P1/package.json");

		assertIssues2(Map.of(
				"M1", List.of("(Error, [0:25 - 1:0], extraneous input '#\\n' expecting '}')")));
	}

	@Test
	public void testCycleImportErrors() {
		testWorkspaceManager.createTestOnDisk(testData3);

		startAndWaitForLspServer();

		assertIssues2(Map.of(
				"P1/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),
				"P2/package.json", List.of(
						"(Error, [14:18 - 14:22], Dependency cycle of the projects: yarn-test-project/packages/P1, yarn-test-project/packages/P2.)"),

				"I1A",
				List.of("(Error, [0:17 - 0:21], Couldn't resolve reference to TModule 'M2'.)",
						"(Error, [1:9 - 1:11], Couldn't resolve reference to Type 'C2'.)"),
				"I2A",
				List.of("(Error, [0:17 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:9 - 1:11], Couldn't resolve reference to Type 'C1'.)")));

	}
}
