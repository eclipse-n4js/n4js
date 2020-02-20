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
import java.util.Collection
import java.util.LinkedHashSet
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.tests.codegen.Project
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * IDE test for validations related to run-time dependency analysis.
 */
class RunTimeDependencyValidationIdeTest extends AbstractIdeTest<Collection<String>> {

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

		test(modules, "MainBad", #[
			'''
				WRN 1:16     Import of load-time dependency target A will be healed by inserting additional import in output code.
				Containing run-time dependency cycle cluster:
				    C.n4js --> B.n4js
				    *B.n4js --> A.n4js
				    *A.n4js --> C.n4js
			'''
		]);
	}

	override protected performTest(File root, Project project, Collection<String> expectedIssues) throws Exception {
		val uri = getFileUriFromModuleName(root, "MainBad");

		val errors = languageClient.getErrors(uri);
		val warnings = languageClient.getWarnings(uri);
		println("ERRORS:")
		println(languageClient.errorsCount);
		println(errors.join('\n'));
		println("WARNINGS:")
		println(languageClient.warningsCount);
		println(warnings.join('\n'));

		val actualIssues = languageClient.getErrors(uri) + languageClient.getWarnings(uri);
		val actualIssuesAsSet = new LinkedHashSet(actualIssues.map[trim].toList);
		val expectedIssuesAsSet = new LinkedHashSet(expectedIssues.map[trim].toList);
		assertEquals(expectedIssuesAsSet, actualIssuesAsSet);
	}
}
