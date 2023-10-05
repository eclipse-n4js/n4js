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

import java.util.List;
import java.util.Map;

import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests indirect dependencies between files.
 */

public class IncrementalBuilderIndirectDependencyTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testIndirectDependencyThroughInheritance() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
						export public class A {
						    public m() {}
						}
						""",
				"B", """
						import {A} from "A"
						export public class B extends A {}
						""",
				"Main", """
						import {B} from "B"
						new B().m();
						"""));
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", """
				//export public class A {
				//    public m() {}
				//}
				""");
		saveOpenedFile("A");
		joinServerRequests();
		assertIssuesInModules(
				Pair.of("A", List.of()),
				Pair.of("B", List.of(
						"(Error, [0:8 - 0:9], Import of A cannot be resolved.)",
						"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)")),
				Pair.of("Main", List.of(
						"(Error, [1:8 - 1:9], Couldn't resolve reference to IdentifiableElement 'm'.)")));

		changeOpenedFile("A", """
				export public class A {
				    public m() {}
				}
				""");
		saveOpenedFile("A");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	public void testIndirectDependencyThroughInferredTypeOfExportedVariable_Part1() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
						export public var a: string;
						""",
				"B", """
						import {a} from "A"
						export public var b = a;
						""",
				"Main", """
						import {b} from "B"
						b.length;
						"""));
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", Pair.of(": string;", ": number;"));
		saveOpenedFile("A");
		joinServerRequests();
		assertIssuesInModules(
				Pair.of("A", List.of()),
				Pair.of("B", List.of()),
				Pair.of("Main", List.of(
						"(Error, [1:2 - 1:8], Couldn't resolve reference to IdentifiableElement 'length'.)")));
	}

	@Test
	public void testIndirectDependencyThroughInferredTypeOfExportedVariable_Part2() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
						export public var a: string;
						""",
				"B", """
						import {a} from "A"
						export public var b = a;
						""",
				"Main", """
						import {b} from "B"
						b.length;
						"""));
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", Pair.of(": string;", ": number;"));
		saveOpenedFile("A");
		cleanBuildAndWait(); // NOTE: difference to Part 1!
		assertIssuesInModules(
				Pair.of("A", List.of()),
				Pair.of("B", List.of()),
				Pair.of("Main", List.of(
						"(Error, [1:2 - 1:8], Couldn't resolve reference to IdentifiableElement 'length'.)")));

		changeOpenedFile("A", Pair.of(": number;", ": string;"));
		saveOpenedFile("A");
		joinServerRequests();
		assertNoIssues();
	}
}
