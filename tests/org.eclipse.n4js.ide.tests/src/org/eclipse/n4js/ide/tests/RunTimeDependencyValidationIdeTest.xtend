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

import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.junit.Test

/**
 * IDE test for validations related to run-time dependency analysis.
 */
class RunTimeDependencyValidationIdeTest extends AbstractIdeTest {

	// run-time dependency cycle: C -> B -> A -> Y -> X -> C
	val defaultTestCode = #[
		"A" -> '''
			import "Y"; // using bare imports to represent any form of run-time dependency that is NOT a load-time dependency
			export public class A {
				public m() {}
			}
		''',
		"B" -> '''
			import {A} from "A";
			export public class B extends A {}
			function foo() {
				// some tests will remove 'extends A' above; this reference to A makes sure we will still
				// have a run-time dependency to A after removing 'extends A' (but not a load-time dependency)
				A;
			}
		''',
		"C" -> '''
			import {B} from "B";
			export public class C extends B {}
		''',
		"X" -> '''
			import "C";
		''',
		"Y" -> '''
			import "X";
		''',
		"MainBad" -> '''
			import {A} from "A";
			new A().m();
		''',
		"MainGood" -> '''
			import {C} from "C";
			new C().m();
		'''
	];

	val defaultExpectedIssues = #[
		"MainBad" -> #[
			'''
				(Warning, [0:16 - 0:19], (LTD) When importing modules from a run-time cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module A must be preceded by an import of one of the modules C, X, Y.
				Containing run-time dependency cycle cluster:
				    *A.n4js --> Y.n4js
				    *B.n4js --> A.n4js
				    C.n4js --> B.n4js
				    X.n4js --> C.n4js
				    Y.n4js --> X.n4js)
			'''
		]
	];

	@Test
	def void testIllegalImportOfLoadTimeTarget() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testLoadTimeDependencyCycle() throws Exception {

		// add a load-time dependency from A.n4js to C.n4js to obtain a load-time dependency cycle:
		val testCodeWithLoadTimeCycle = defaultTestCode.map[moduleNameToContent|
			if (moduleNameToContent.key == "A") {
				moduleNameToContent.key -> '''
					import {C} from "C";
					«moduleNameToContent.value»
					class D extends C {}
				'''
			} else {
				moduleNameToContent // unchanged
			}
		];

		createTestProjectOnDisk(testCodeWithLoadTimeCycle);
		startAndWaitForLspServer();

		assertIssues(
			"A" -> #[
				'''
					(Warning, [0:16 - 0:19], (LTD) Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js)
				'''
			],
			"B" -> #[
				'''
					(Warning, [0:16 - 0:19], (LTD) Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js)
				'''
			],
			"C" -> #[
				'''
					(Warning, [0:16 - 0:19], (LTD) Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js)
				'''
			],
			"MainBad" -> #[
				// no errors/warnings expected here! (i.e. avoid pure follow-up errors/warnings)
			]
		);
	}

	@Test
	def void testIllegalLoadTimeReferencesWithinRunTimeCycle() throws Exception {
		// add some load-time and run-time references to file B.n4js
		val testCodeWithIllegalLoadTimeReferences = defaultTestCode.map[moduleNameToContent|
			if (moduleNameToContent.key == "B") {
				moduleNameToContent.key -> '''
					«moduleNameToContent.value»

					class SomeClass1 extends A {} // ok (because it's in extends/implements clause)
					class SomeClass2 extends B {} // ok (for the same reason)

					function fun1() {}
					fun1(); // error

					new A(); // error

					function fun2() {
						new A(); // ok
					}
				'''
			} else {
				moduleNameToContent // unchanged
			}
		];

		createTestProjectOnDisk(testCodeWithIllegalLoadTimeReferences);
		startAndWaitForLspServer();

		val expectedIssuesWithIllegalLoadTimeReferences = defaultExpectedIssues + #[
			"B" -> #[
				'''
					(Warning, [12:0 - 12:4], (LTD) Load-time references to the same or other modules are not allowed within a run-time dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js)
				''',
				'''
					(Warning, [14:4 - 14:5], (LTD) Load-time references to the same or other modules are not allowed within a run-time dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js)
				'''
			]
		];

		assertIssues(expectedIssuesWithIllegalLoadTimeReferences);

		// comment out the run-time dependency X -> C
		changeNonOpenedFile("X", 'import "C";' -> '// import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// re-enable the run-time dependency X -> C
		changeNonOpenedFile("X", '// import "C";' -> 'import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line
		
		assertIssues(expectedIssuesWithIllegalLoadTimeReferences);
	}

	@Test
	def void testIncrementalBuild01_openCloseRunTimeCycle() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// comment out the run-time dependency X -> C
		changeNonOpenedFile("X", 'import "C";' -> '// import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// re-enable the run-time dependency X -> C
		changeNonOpenedFile("X", '// import "C";' -> 'import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line
		
		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testIncrementalBuild02_addRemoveLoadTimeDependency() throws Exception {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		changeNonOpenedFile("B", "extends A" -> "");
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(
			"MainBad" -> #[], // original issue should be gone
			"MainGood" -> #[
				"(Error, [1:8 - 1:9], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);

		changeNonOpenedFile("B", "class B " -> "class B extends A ");
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues); // original issue should have come back
	}
}
