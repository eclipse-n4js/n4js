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
 * Tests indirect dependencies between files.
 */
class IncrementalBuilderIndirectDependencyTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testIndirectDependencyThroughInheritance() {
		workspaceCreator.createTestProjectOnDisk(
			"A" -> '''
				export public class A {
				    public m() {}
				}
			''',
			"B" -> '''
				import {A} from "A"
				export public class B extends A {}
			''',
			"Main" -> '''
				import {B} from "B"
				new B().m();
			'''
		);
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", '''
			//export public class A {
			//    public m() {}
			//}
		''');
		saveOpenedFile("A");
		joinServerRequests();
		assertIssuesInModules(
			"A" -> #[],
			"B" -> #[
				"(Error, [0:8 - 0:9], Couldn't resolve reference to TExportableElement 'A'.)",
				"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)",
				"(Warning, [0:8 - 0:9], The import of <A>(proxy) is unused.)"
			],
			"Main" -> #[
				"(Error, [1:8 - 1:9], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);

		changeOpenedFile("A", '''
			export public class A {
			    public m() {}
			}
		''');
		saveOpenedFile("A");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testIndirectDependencyThroughInferredTypeOfExportedVariable_Part1() {
		workspaceCreator.createTestProjectOnDisk(
			"A" -> '''
				export public var a: string;
			''',
			"B" -> '''
				import {a} from "A"
				export public var b = a;
			''',
			"Main" -> '''
				import {b} from "B"
				b.length;
			'''
		);
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", ": string;" -> ": number;");
		saveOpenedFile("A");
		joinServerRequests();
		assertIssuesInModules(
			"A" -> #[],
			"B" -> #[],
			"Main" -> #[
				"(Error, [1:2 - 1:8], Couldn't resolve reference to IdentifiableElement 'length'.)"
			]
		);
	}

	@Test
	def void testIndirectDependencyThroughInferredTypeOfExportedVariable_Part2() {
		workspaceCreator.createTestProjectOnDisk(
			"A" -> '''
				export public var a: string;
			''',
			"B" -> '''
				import {a} from "A"
				export public var b = a;
			''',
			"Main" -> '''
				import {b} from "B"
				b.length;
			'''
		);
		startAndWaitForLspServer();
		cleanBuildAndWait();
		openFile("A");

		changeOpenedFile("A", ": string;" -> ": number;");
		saveOpenedFile("A");
		cleanBuildAndWait(); // NOTE: difference to Part 1!
		assertIssuesInModules(
			"A" -> #[],
			"B" -> #[],
			"Main" -> #[
				"(Error, [1:2 - 1:8], Couldn't resolve reference to IdentifiableElement 'length'.)"
			]
		);

		changeOpenedFile("A", ": number;" -> ": string;");
		saveOpenedFile("A");
		joinServerRequests();
		assertNoIssues();
	}
}
