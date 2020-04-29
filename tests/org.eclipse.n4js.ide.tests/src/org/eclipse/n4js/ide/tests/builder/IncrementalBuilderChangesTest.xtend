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

import org.junit.Test

/**
 * Tests incremental builds triggered by changes in files.
 */
class IncrementalBuilderChangesTest extends AbstractIncrementalBuilderTest {

	private static val testDataSingleFile = #[
		"A" -> '''
			const x = 42;
			const y: number = x;
		'''
	];

	private static val testDataAcrossFiles = #[
		"A" -> '''
			export const x = 42;
		''',
		"B" -> '''
			import {x} from "A";
			const y: number = x;
		'''
	];

	private static val testDataAcrossProjects = #[
		"#NODE_MODULES:n4js-runtime" -> null,
		"ProjectA" -> #[
			"A" -> '''
				export public const x = 42;
			''',
			"#DEPENDENCY" -> "n4js-runtime"
		],
		"ProjectB" -> #[
			"B" -> '''
				import {x} from "A";
				const y: number = x;
			''',
			"#DEPENDENCY" -> '''
				n4js-runtime,
				ProjectA
			'''
		]
	];

	@Test
	def void testChangeInNonOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataSingleFile);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("A" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeNonOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInNonOpenedFile_acrossFiles() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeNonOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInNonOpenedFile_acrossProjects() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeNonOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataSingleFile);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("A" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile_acrossFiles() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile_acrossProjects() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			"(Error, [1:18 - 1:19], string is not a subtype of number.)"
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}
}
