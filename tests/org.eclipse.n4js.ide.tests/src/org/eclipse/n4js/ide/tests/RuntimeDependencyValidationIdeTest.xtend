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
 * IDE test for validations related to runtime dependency analysis.
 */
class RuntimeDependencyValidationIdeTest extends AbstractIdeTest {

	// runtime dependency cycle: C -> B -> A -> Y -> X -> C
	val defaultTestCode = #[
		"A" -> '''
			import "Y"; // using bare imports to represent any form of runtime dependency that is NOT a load-time dependency
			export public class A {
				public m() {}
			}
		''',
		"B" -> '''
			import {A} from "A";
			export public class B extends A {}
			function foo() {
				// some tests will remove 'extends A' above; this reference to A makes sure we will still
				// have a runtime dependency to A after removing 'extends A' (but not a load-time dependency)
				A;
			}
		''',
		"C" -> '''
			import {B} from "B";
			export public class C extends B {}
			@StringBased
			export public enum EnumInC { L1, L2 }
		''',
		"X" -> '''
			// top of file
			import "C";
			// bottom of file
		''',
		"Y" -> '''
			import "X";
		''',
		"MainBad" -> '''
			// top of file
			import {A} from "A";
			new A().m();
			// bottom of file
		''',
		"MainGood" -> '''
			// good import:
			import {C} from "C";
			new C().m();
		'''
	];

	val defaultExpectedIssueInMainBad = '''
		(Error, [1:16 - 1:19], When importing modules from a runtime cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module A must be preceded by an import of one of the modules C, X, Y.
		Containing runtime dependency cycle cluster:
		    *A.n4js --> Y.n4js
		    *B.n4js --> A.n4js
		    C.n4js --> B.n4js
		    X.n4js --> C.n4js
		    Y.n4js --> X.n4js)
	''';

	val defaultExpectedIssues = #[
		"MainBad" -> #[
			defaultExpectedIssueInMainBad
		]
	];

	@Test
	def void testIllegalImportOfLoadtimeTarget() {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testHealingThroughPreceedingImports_nonBareImport() {
		
		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add a healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
			'// top of file' -> 'import {C} from "C";',
			'// bottom of file' -> 'new C();'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// remove both import and usage
		changeNonOpenedFile("MainBad",
			'import {C} from "C";' -> '',
			'new C();' -> ''
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testHealingThroughPreceedingImports_bareImport() {
		
		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add a healing bare import to MainBad.n4js
		changeNonOpenedFile("MainBad",
			'// top of file' -> 'import "C";'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// remove the healing import
		changeNonOpenedFile("MainBad",
			'import "C";' -> ''
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues);
	}

	// unused imports must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_unusedImport() {
		
		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
			'// top of file' -> 'import {C} from "C";'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(
			"MainBad" -> #[
				defaultExpectedIssueInMainBad,
				'''
					(Warning, [0:8 - 0:9], The import of C is unused.)
				'''
			]
		);
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_nonRetainedImport01() {
		
		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
			'// top of file' -> 'import {C} from "C";',
			'// bottom of file' -> 'export function foo(p: C) {}'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues); // issue must *not* be gone
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_nonRetainedImport02() {
		
		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
			'// top of file' -> 'import {EnumInC} from "C";',
			'// bottom of file' -> 'console.log(EnumInC.L1);'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues); // issue must *not* be gone
	}

	@Test
	def void testLoadtimeDependencyConflict() {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// add additional usage of load-time dependency target B from within the cycle
		changeNonOpenedFile("X",
			'// top of file' -> 'import {B} from "B";',
			'// bottom of file' -> 'class X extends B {}'
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssuesInModules(#[
			"X" -> #[
				'''
					(Error, [0:16 - 0:19], A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module C.
					Containing runtime dependency cycle cluster:
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> B.n4js, C.n4js
					    Y.n4js --> X.n4js)
				'''
			],
			"C" -> #[
				'''
					(Error, [0:16 - 0:19], A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module X.
					Containing runtime dependency cycle cluster:
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> B.n4js, C.n4js
					    Y.n4js --> X.n4js)
				'''
			]
		]);

		// revert the above change
		changeNonOpenedFile("X",
			'import {B} from "B";' -> '',
			'class X extends B {}' -> ''
		);
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssuesInModules(#[
			"X" -> #[],
			"C" -> #[]
		]);
	}

	@Test
	def void testLoadtimeDependencyCycle() {

		// add a load-time dependency from A.n4js to C.n4js to obtain a load-time dependency cycle:
		val testCodeWithLoadtimeCycle = defaultTestCode.map[moduleNameToContent|
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

		createTestProjectOnDisk(testCodeWithLoadtimeCycle);
		startAndWaitForLspServer();

		assertIssues(
			"A" -> #[
				'''
					(Error, [0:16 - 0:19], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js)
				'''
			],
			"B" -> #[
				'''
					(Error, [0:16 - 0:19], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js)
				'''
			],
			"C" -> #[
				'''
					(Error, [0:16 - 0:19], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
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
	def void testIllegalLoadtimeReferencesWithinRuntimeCycle() {
		// add some load-time and runtime references to file B.n4js
		val testCodeWithIllegalLoadtimeReferences = defaultTestCode.map[moduleNameToContent|
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

		createTestProjectOnDisk(testCodeWithIllegalLoadtimeReferences);
		startAndWaitForLspServer();

		val expectedIssuesWithIllegalLoadtimeReferences = defaultExpectedIssues + #[
			"B" -> #[
				'''
					(Error, [12:0 - 12:4], Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js)
				''',
				'''
					(Error, [14:4 - 14:5], Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js)
				'''
			]
		];

		assertIssues(expectedIssuesWithIllegalLoadtimeReferences);

		// comment out the runtime dependency X -> C
		changeNonOpenedFile("X", 'import "C";' -> '// import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeNonOpenedFile("X", '// import "C";' -> 'import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line
		
		assertIssues(expectedIssuesWithIllegalLoadtimeReferences);
	}

	@Test
	def void testIncrementalBuild01_openCloseRuntimeCycle() {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		// comment out the runtime dependency X -> C
		changeNonOpenedFile("X", 'import "C";' -> '// import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeNonOpenedFile("X", '// import "C";' -> 'import "C";');
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line
		
		assertIssues(defaultExpectedIssues);
	}

	@Test
	def void testIncrementalBuild02_addRemoveLoadtimeDependency() {

		createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues(defaultExpectedIssues);

		changeNonOpenedFile("B", "extends A" -> "");
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(
			"MainBad" -> #[], // original issue should be gone
			"MainGood" -> #[
				"(Error, [2:8 - 2:9], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);

		changeNonOpenedFile("B", "class B " -> "class B extends A ");
		joinServerRequests();
cleanBuildAndWait(); // TODO GH-1675 remove this line

		assertIssues(defaultExpectedIssues); // original issue should have come back
	}
}
