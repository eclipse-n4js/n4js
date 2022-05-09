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

import java.io.File
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Tests incremental builds triggered by changes in source files.
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
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
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
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
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
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
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
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile_acrossFiles01() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile_acrossFiles02() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened
		
		saveOpenedFile("A");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeInOpenedFile_acrossFiles03() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened
		
		openFile("B");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeInOpenedFile_acrossFiles04() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();

		saveOpenedFile("A");
		joinServerRequests();

		closeFile("A");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		openFile("B");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeInOpenedFile_acrossProjects01() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		changeOpenedFile("A", '"hello"' -> '42');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInOpenedFile_acrossProjects02() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		saveOpenedFile("A");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeInOpenedFile_acrossProjects03() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		openFile("B");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeInOpenedFile_acrossProjects04() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();

		saveOpenedFile("A");
		joinServerRequests();

		closeFile("A");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		openFile("B");
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);
	}

	@Test
	def void testChangeFileByClosingIt() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");

		changeOpenedFile("A", '42' -> '"hello"');
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], "hello" is not a subtype of number.)'
		]);

		closeFileDiscardingChanges("A", true);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInDts() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"A.d.ts" -> '''
				export const x: number;
			''',
			"B" -> '''
				import {x} from "A";
				const y: number = x;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", ': number' -> ': string');
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], string is not a subtype of number.)'
		]);

		changeNonOpenedFile("A.d.ts", ': string' -> ': number');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInDts_declaredModule() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"A.d.ts" -> '''
				declare module "declModuleA" {
					export class ClsA {
						fieldA: number;
					}
				}
			''',
			"B.d.ts" -> '''
				declare module "declModuleB" {
					import {ClsA} from "declModuleA"
					export class ClsB {
						fieldB: ClsA;
					}
				}
			''',
			"Main" -> '''
				import {ClsB} from "declModuleB";
				const y: number = new ClsB().fieldB.fieldA;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", ': number' -> ': string');
		joinServerRequests();
		assertIssues("Main" -> #[
			// what we don't want to see:
			// "(Error, [0:xx - 0:xx], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			'(Error, [1:18 - 1:42], string is not a subtype of number.)'
		]);

		changeNonOpenedFile("A.d.ts", ': string' -> ': number');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInDts_declaredModuleAffectedButNotItsHost() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"A.d.ts" -> '''
				declare module "a/b/declModule" {
					export const x: number;
				}
			''',
			"B" -> '''
				import {x} from "a/b/declModule";
				const y: number = x;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", ': number' -> ': string');
		joinServerRequests();
		assertIssues("B" -> #[
			'(Error, [1:18 - 1:19], string is not a subtype of number.)'
		]);

		changeNonOpenedFile("A.d.ts", ': string' -> ': number');
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testChangeInPlainJS() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"PlainJSModule.js" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val sourceFileURI = new File(new File(getProjectRoot(DEFAULT_PROJECT_NAME), DEFAULT_SOURCE_FOLDER), "PlainJSModule.js").toFileURI;
		val outputFileURI = new File(getOutputFolder(), "PlainJSModule.js").toFileURI;

		assertContentOfFileOnDisk(outputFileURI, "console.log('hello');");

		changeNonOpenedFile(sourceFileURI, ["// changed"]);
		joinServerRequests();

		assertContentOfFileOnDisk(outputFileURI, "// changed");
	}

	// TODO GH-1822, GH-2060
	@Ignore("https://github.com/eclipse/n4js/issues/1822")
	@Test
	def void testTransitivelyAffected() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				export public let a = {
					a1 : {
						a2 : ''
					}
				};
			''',
			"B" -> '''
				import {a} from "A";
				export public let b = a.a1;
			''',
			"C" -> '''
				import {b} from "B";
				export public let c = b.a3; // initially broken
			'''
		);
		startAndWaitForLspServer();
		var issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(issues.join('\n'), 1, issues.size);
		assertEquals("(Error, [1:24 - 1:26], Couldn't resolve reference to IdentifiableElement 'a3'.)",	issues.head)

		// Rename a2 to a3 to fix the reference in C
		changeNonOpenedFile("A", 'a2' -> 'a3');
		joinServerRequests();
		issues = getIssues().values().map[getStringLSP4J.toStringShort(it)].toSet;
		assertEquals(issues.join('\n'), 0, issues.size);
	}

	@Test
	def void testTransitivelyAffected2() {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject1" -> #[
				"Other1" -> '''
					export public class Cls1 {
						public field: string;
					}
				'''
			],
			"OtherProject2" -> #[
				"Other2" -> '''
					import { Cls1 } from "Other1"
					export public class Cls2 extends Cls1 {}
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject1
				'''
			],
			"OtherProject3" -> #[
				"Other3" -> '''
					import { Cls2 } from "Other2"
					export public class Cls3 extends Cls2 {}
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject2
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Cls3} from "Other3";

					let x: string = new Cls3().field;
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject3
				'''
			]
		);
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", "field: string" -> "field: number");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertIssues(
			"Main" -> #[
				"(Error, [2:16 - 2:32], number is not a subtype of string.)"
			]
		);

		changeNonOpenedFile("Other1", "field: number" -> "field: string");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();

		assertNoIssues();
	}
}
