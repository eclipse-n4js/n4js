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

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.n4js.utils.io.FileDeleter
import org.eclipse.n4js.utils.io.FileUtils
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

/**
 * Test various cases of changes between LSP server sessions to ensure the initial build of the later session
 * correctly reacts to those changes.
 */
class CyclicDependenciesBuilderTest extends AbstractIncrementalBuilderTest {

	private static val testData1 = #[
		"P1" -> #[
			"M1" -> '''
				export public class C1 {
				}
			'''
		],
		"P2" -> #[
			"M2" -> '''
				import {C1} from "M1";
				const x: C1 = null;
				x;
			''',
			CFG_DEPENDENCIES -> '''
				P1
			'''
		]
	];

	private static val testData2 = #[
		"P1" -> #[
			"M1" -> '''
				export public class C1 {
				}
			''',
			CFG_DEPENDENCIES -> '''
				P2
			'''
		],
		"P2" -> #[
			"M2" -> '''
				import {C1} from "M1";
				const x: C1 = null;
				x;
			''',
			CFG_DEPENDENCIES -> '''
				P1
			'''
		]
	];

	private static Path temporaryFolder;

	@BeforeClass
	def static void createTemporaryFolder() throws IOException {
		temporaryFolder = Files.createTempDirectory("temp_InitialBuildTest_");
	}

	@AfterClass
	def static void deleteTemporaryFolder() throws IOException {
		if (temporaryFolder !== null) {
			FileDeleter.delete(temporaryFolder);
		}
	}

	@Before
	def void cleanTemporaryFolder() throws IOException {
		FileUtils.cleanFolder(temporaryFolder);
	}

	@Test
	def void testAddCycle() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData1);

		startAndWaitForLspServer();
		
		assertNoIssues();
		
		openFile("P1/package.json");
		
		assertNoIssues();

		changeOpenedFile("P1/package.json", "\"n4js-runtime\": \"*\"" -> "\"n4js-runtime\": \"*\", \"P2\": \"*\"");
		
		assertNoIssues();
		
		saveOpenedFile("P1/package.json");
		
		assertIssues(
			"P1/package.json" -> #["(Error, [15:24 - 15:28], Dependency cycle of the projects: P1, P2.)"],
			"P2/package.json" -> #["(Error, [15:3 - 15:7], Dependency cycle of the projects: P1, P2.)"]
		);
	}

	@Test
	def void testRemoveCycle() throws IOException {
		testWorkspaceManager.createTestOnDisk(testData2);

		startAndWaitForLspServer();
		
		assertIssues(
			"P1/package.json" -> #["(Error, [15:3 - 15:7], Dependency cycle of the projects: P1, P2.)"],
			"P2/package.json" -> #["(Error, [15:3 - 15:7], Dependency cycle of the projects: P1, P2.)"],
			"M2" -> #[
				"(Error, [0:17 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:9 - 1:11], Couldn't resolve reference to Type 'C1'.)"]
		);
		
		openFile("P1/package.json");
		
		changeOpenedFile("P1/package.json", "\"P2\": \"*\"," -> "");
		
		saveOpenedFile("P1/package.json");
		
		assertNoIssues();
	}
}
