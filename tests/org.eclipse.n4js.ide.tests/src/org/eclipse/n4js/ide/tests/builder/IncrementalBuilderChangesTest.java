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

import static com.google.common.collect.Sets.newTreeSet;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests incremental builds triggered by changes in source files.
 */
@SuppressWarnings("javadoc")
public class IncrementalBuilderChangesTest extends AbstractIncrementalBuilderTest {

	private static Map<String, String> testDataSingleFile = Map.of(
			"A", """
						const x = 42;
						const y: number = x;
					""");

	private static Map<String, String> testDataAcrossFiles = Map.of(
			"A", """
						export const x = 42;
					""",
			"B", """
						import {x} from "A";
						const y: number = x;
					""");

	private static Map<String, Map<String, String>> testDataAcrossProjects = Map.of(
			"#NODE_MODULES:n4js-runtime", Map.of(),
			"ProjectA", Map.of(
					"A", """
								export public const x = 42;
							""",
					"#DEPENDENCY", "n4js-runtime"),
			"ProjectB", Map.of(
					"B", """
								import {x} from "A";
								const y: number = x;
							""",
					"#DEPENDENCY", """
								n4js-runtime,
								ProjectA
							"""));

	@Test
	public void testChangeInNonOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataSingleFile);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("A", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeNonOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInNonOpenedFile_acrossFiles() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeNonOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInNonOpenedFile_acrossProjects() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeNonOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInOpenedFile() {
		testWorkspaceManager.createTestProjectOnDisk(testDataSingleFile);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("A", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInOpenedFile_acrossFiles01() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInOpenedFile_acrossFiles02() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		saveOpenedFile("A");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeInOpenedFile_acrossFiles03() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		openFile("B");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeInOpenedFile_acrossFiles04() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();

		saveOpenedFile("A");
		joinServerRequests();

		closeFile("A");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		openFile("B");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeInOpenedFile_acrossProjects01() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		changeOpenedFile("A", Pair.of("\"hello\"", "42"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInOpenedFile_acrossProjects02() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		saveOpenedFile("A");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeInOpenedFile_acrossProjects03() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertNoIssues(); // error in "B" not showing up, because A not saved and B not opened

		openFile("B");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeInOpenedFile_acrossProjects04() {
		testWorkspaceManager.createTestOnDisk(testDataAcrossProjects);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();

		saveOpenedFile("A");
		joinServerRequests();

		closeFile("A");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		openFile("B");
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));
	}

	@Test
	public void testChangeFileByClosingIt() {
		testWorkspaceManager.createTestProjectOnDisk(testDataAcrossFiles);
		startAndWaitForLspServer();
		assertNoIssues();

		openFile("A");
		openFile("B");

		changeOpenedFile("A", Pair.of("42", "\"hello\""));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], \"hello\" is not a subtype of number.)")));

		closeFileDiscardingChanges("A", true);
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInDts() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A.d.ts", """
							export const x: number;
						""",
				"B", """
							import {x} from "A";
							const y: number = x;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", Pair.of(": number", ": string"));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], string is not a subtype of number.)")));

		changeNonOpenedFile("A.d.ts", Pair.of(": string", ": number"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInDts_declaredModule() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A.d.ts", """
							declare module "declModuleA" {
								export class ClsA {
									fieldA: number;
								}
							}
						""",
				"B.d.ts", """
							declare module "declModuleB" {
								import {ClsA} from "declModuleA"
								export class ClsB {
									fieldB: ClsA;
								}
							}
						""",
				"Main", """
							import {ClsB} from "declModuleB";
							const y: number = new ClsB().fieldB.fieldA;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", Pair.of(": number", ": string"));
		joinServerRequests();
		assertIssues2(Pair.of("Main", List.of(
				// what we don't want to see:
				// "(Error, [0:xx - 0:xx], Cannot resolve plain module specifier (without project name as first
				// segment): no matching module found.)"
				"(Error, [1:19 - 1:43], string is not a subtype of number.)")));

		changeNonOpenedFile("A.d.ts", Pair.of(": string", ": number"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInDts_declaredModuleAffectedButNotItsHost() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A.d.ts", """
							declare module "a/b/declModule" {
								export const x: number;
							}
						""",
				"B", """
							import {x} from "a/b/declModule";
							const y: number = x;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A.d.ts", Pair.of(": number", ": string"));
		joinServerRequests();
		assertIssues2(Pair.of("B", List.of(
				"(Error, [1:19 - 1:20], string is not a subtype of number.)")));

		changeNonOpenedFile("A.d.ts", Pair.of(": string", ": number"));
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testChangeInPlainJS() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"PlainJSModule.js", """
							console.log('hello');
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI sourceFileURI = toFileURI(
				new File(new File(getProjectRoot(DEFAULT_PROJECT_NAME), DEFAULT_SOURCE_FOLDER), "PlainJSModule.js"));
		FileURI outputFileURI = toFileURI(new File(getOutputFolder(), "PlainJSModule.js"));

		assertContentOfFileOnDisk(outputFileURI, "console.log('hello');");

		changeNonOpenedFile(sourceFileURI, (s12) -> "// changed");
		joinServerRequests();

		assertContentOfFileOnDisk(outputFileURI, "// changed");
	}

	// TODO GH-1822, GH-2060
	@Ignore("https://github.com/eclipse/n4js/issues/1822")
	@Test
	public void testTransitivelyAffected() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							export public let a = {
								a1 : {
									a2 : ''
								}
							};
						""",
				"B", """
							import {a} from "A";
							export public let b = a.a1;
						""",
				"C", """
							import {b} from "B";
							export public let c = b.a3; // initially broken
						"""));
		startAndWaitForLspServer();
		TreeSet<String> issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(Strings.join("\n", issues), 1, issues.size());
		assertEquals("(Error, [1:24 - 1:26], Couldn't resolve reference to IdentifiableElement 'a3'.)", issues.first());

		// Rename a2 to a3 to fix the reference in C
		changeNonOpenedFile("A", Pair.of("a2", "a3"));
		joinServerRequests();
		issues = newTreeSet(map(getIssues().values(), (d) -> getStringLSP4J().toStringShort(d)));
		assertEquals(Strings.join("\n", issues), 0, issues.size());
	}

	@Test
	public void testTransitivelyAffected2() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject1", Map.of(
						"Other1", """
									export public class Cls1 {
										public field: string;
									}
								"""),
				"OtherProject2", Map.of(
						"Other2", """
									import { Cls1 } from "Other1"
									export public class Cls2 extends Cls1 {}
								""",
						CFG_DEPENDENCIES, """
									OtherProject1
								"""),
				"OtherProject3", Map.of(
						"Other3", """
									import { Cls2 } from "Other2"
									export public class Cls3 extends Cls2 {}
								""",
						CFG_DEPENDENCIES, """
									OtherProject2
								"""),
				"MainProject", Map.of(
						"Main", """
									import {Cls3} from "Other3";

									let x: string = new Cls3().field;
								""",
						CFG_DEPENDENCIES, """
									OtherProject3
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		changeNonOpenedFile("Other1", Pair.of("field: string", "field: number"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertIssues2(Pair.of(
				"Main", List.of(
						"(Error, [2:17 - 2:33], number is not a subtype of string.)")));

		changeNonOpenedFile("Other1", Pair.of("field: number", "field: string"));
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();

		assertNoIssues();
	}
}
