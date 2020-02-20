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
package org.eclipse.n4js.ide.tests

import java.io.File
import java.util.LinkedHashSet
import java.util.List
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.tests.codegen.Project
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * IDE test for validations related to run-time dependency analysis.
 */
class RunTimeDependencyValidationIdeTest extends AbstractIdeTest<List<Pair<String,List<String>>>> {

	@Test
	def void testSimple() throws Exception {
		val modules = #[
			"A" -> '''
				import "C";
				export public class A {
					public m() {}
				}
			''',
			"B" -> '''
				import {A} from "A";
				export public class B extends A {}
			''',
			"C" -> '''
				import {B} from "B";
				export public class C extends B {}
			''',
			"MainBad" -> '''
				import {A} from "A";
				new A().m();
			'''
		];
		val expectedIssues = #[
			"MainBad" -> #[
				'''
					WRN 1:16     Import of load-time dependency target A will be healed by inserting additional import in output code.
					Containing run-time dependency cycle cluster:
					    C.n4js --> B.n4js
					    *B.n4js --> A.n4js
					    *A.n4js --> C.n4js
				'''
			]
		];

		test(modules, "MainBad", expectedIssues);
	}

	@Test
	def void testLoadTimeDependencyCycle() throws Exception {
		val modules = #[
			"A" -> '''
				import {C} from "C"
				export public class A {
					public m() {}
				}
				class D extends C {}
			''',
			"B" -> '''
				import {A} from "A";
				export public class B extends A {}
			''',
			"C" -> '''
				import {B} from "B";
				export public class C extends B {}
			''',
			"Main" -> '''
				import {B} from "B";
				new B();
			'''
		];
		val expectedIssues = #[
			"A" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *B.n4js --> A.n4js
					    *A.n4js --> C.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"B" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *C.n4js --> B.n4js
					    *B.n4js --> A.n4js
					    *A.n4js --> C.n4js
				'''
			],
			"C" -> #[
				'''
					ERR 1:16     Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *C.n4js --> B.n4js
					    *B.n4js --> A.n4js
				'''
			],
			"Main" -> #[
				// no errors/warnings expected here! (i.e. avoid pure follow-up errors/warnings)
			]
		];

		test(modules, "A", expectedIssues);
	}

	override protected performTest(File root, Project project, List<Pair<String,List<String>>> moduleNameToExpectedIssues) throws Exception {
		for (pair : moduleNameToExpectedIssues) {
			val moduleName = pair.key;
			val expectedIssues = pair.value;
			val uri = getFileUriFromModuleName(root, moduleName);
	
//val errors = languageClient.getErrors(uri);
//val warnings = languageClient.getWarnings(uri);
//println("ERRORS (" + languageClient.errorsCount + "):")
//println(errors.join('\n'));
//println("WARNINGS (" + languageClient.warningsCount + "):")
//println(warnings.join('\n'));
	
			val actualIssues = languageClient.getErrors(uri) + languageClient.getWarnings(uri);
			val actualIssuesAsSet = new LinkedHashSet(actualIssues.map[trim].toList);
			val expectedIssuesAsSet = new LinkedHashSet(expectedIssues.map[trim].toList);
			assertEquals(expectedIssuesAsSet, actualIssuesAsSet);
		}
	}
}
