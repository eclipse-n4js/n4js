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
package org.eclipse.n4js.ide.tests;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * IDE test for validations related to runtime dependency analysis.
 */
@SuppressWarnings("javadoc")
public class RuntimeDependencyValidationIdeTest extends AbstractIdeTest {

	// runtime dependency cycle: C -> B -> A -> Y -> X -> C

	Map<String, String> defaultTestCode = Map.of(
			"A",
			"""
					import "Y"; // using bare imports to represent any form of runtime dependency that is NOT a load-time dependency
					export public class A {
						public m() {}
					}
					""",
			"B", """
					import {A} from "A";
					export public class B extends A {}
					function foo() {
						// some tests will remove 'extends A' above; this reference to A makes sure we will still
						// have a runtime dependency to A after removing 'extends A' (but not a load-time dependency)
						A;
					}
					""",
			"C", """
						import {B} from "B";
						export public class C extends B {}
						@StringBased
						export public enum EnumInC { L1, L2 }
					""",
			"X", """
						// top of file
						import "C";
						// bottom of file
					""",
			"Y", """
						import "X";
					""",
			"MainBad", """
						// top of file
						import {A} from "A";
						new A().m();
						// bottom of file
					""",
			"MainGood", """
						// good import:
						import {C} from "C";
						new C().m();
					""");

	String defaultExpectedIssueInMainBad = """
				(Error, [1:17 - 1:20], When importing modules from a runtime cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module A must be preceded by an import of one of the modules C, X, Y.
			Containing runtime dependency cycle cluster:
			    *A.n4js --> Y.n4js
			    *B.n4js --> A.n4js
			    C.n4js --> B.n4js
			    X.n4js --> C.n4js
			    Y.n4js --> X.n4js)
			""";

	List<Pair<String, List<String>>> defaultExpectedIssues = List.of(
			Pair.of("MainBad", List.of(
					defaultExpectedIssueInMainBad)));

	@Test
	public void testIllegalImportOfLoadtimeTarget() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);
	}

	@Test
	public void testHealingThroughPreceedingImports_nonBareImport() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add a healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
				Pair.of("// top of file", "import {C} from \"C\";"),
				Pair.of("// bottom of file", "new C();"));
		joinServerRequests();

		assertNoIssues();

		// remove both import and usage
		changeNonOpenedFile("MainBad",
				Pair.of("import {C} from \"C\";", ""),
				Pair.of("new C();", ""));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues);
	}

	@Test
	public void testHealingThroughPreceedingImports_bareImport() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add a healing bare import to MainBad.n4js
		changeNonOpenedFile("MainBad",
				Pair.of("// top of file", "import \"C\";"));
		joinServerRequests();

		assertNoIssues();

		// remove the healing import
		changeNonOpenedFile("MainBad",
				Pair.of("import \"C\";", ""));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues);
	}

	// unused imports must not have a healing effect
	@Test
	public void testHealingThroughPreceedingImports_unusedImport() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
				Pair.of("// top of file", "import {C} from \"C\";"));
		joinServerRequests();

		assertIssues2(
				Pair.of("MainBad", List.of(
						defaultExpectedIssueInMainBad, "(Warning, [0:9 - 0:10], The import of C is unused.)")));
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	public void testHealingThroughPreceedingImports_nonRetainedImport01() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
				Pair.of("// top of file", "import {C} from \"C\";"),
				Pair.of("// bottom of file", "export function foo(p: C) {}"));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues); // issue must *not* be gone
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	public void testHealingThroughPreceedingImports_nonRetainedImport02() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeNonOpenedFile("MainBad",
				Pair.of("// top of file", "import {EnumInC} from \"C\";"),
				Pair.of("// bottom of file", "console.log(EnumInC.L1);"));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues); // issue must *not* be gone
	}

	@Test
	public void testLoadtimeDependencyConflict() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// add additional usage of load-time dependency target B from within the cycle
		changeNonOpenedFile("X",
				Pair.of("// top of file", "import {B} from \"B\";"),
				Pair.of("// bottom of file", "class X extends B {}"));
		joinServerRequests();

		assertIssuesInModules(
				Pair.of("X", List.of(
						"""
								(Error, [0:17 - 0:20], A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module C.
								Containing runtime dependency cycle cluster:
								    *A.n4js --> Y.n4js
								    *B.n4js --> A.n4js
								    C.n4js --> B.n4js
								    X.n4js --> B.n4js, C.n4js
								    Y.n4js --> X.n4js)
								""")),
				Pair.of("C", List.of(
						"""
								(Error, [0:17 - 0:20], A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module X.
								Containing runtime dependency cycle cluster:
								    *A.n4js --> Y.n4js
								    *B.n4js --> A.n4js
								    C.n4js --> B.n4js
								    X.n4js --> B.n4js, C.n4js
								    Y.n4js --> X.n4js)
								 """)));

		// revert the above change
		changeNonOpenedFile("X",
				Pair.of("import {B} from \"B\";", ""),
				Pair.of("class X extends B {}", ""));
		joinServerRequests();

		assertIssuesInModules(
				Pair.of("X", List.of()),
				Pair.of("C", List.of()));
	}

	@Test
	public void testLoadtimeDependencyCycle() {

		// add a load-time dependency from A.n4js to C.n4js to obtain a load-time dependency cycle:
		Iterable<Pair<String, String>> testCodeWithLoadtimeCycle = IterableExtensions.map(defaultTestCode.entrySet(),
				(moduleNameToContent) -> {
					if ("A".equals(moduleNameToContent.getKey())) {
						return Pair.of(moduleNameToContent.getKey(), """
									import {C} from "C";
									%s
									class D extends C {}
								""".formatted(moduleNameToContent.getValue()));
					} else {
						return Pair.of(moduleNameToContent.getKey(), moduleNameToContent.getValue()); // unchanged
					}
				});

		testWorkspaceManager.createTestProjectOnDisk(testCodeWithLoadtimeCycle);
		startAndWaitForLspServer();

		assertIssues2(
				Pair.of("A", List.of(
						"""
								(Error, [0:17 - 0:20], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
								    A.n4js --> C.n4js
								    B.n4js --> A.n4js
								    C.n4js --> B.n4js)
								 """)),
				Pair.of("B", List.of(
						"""
								(Error, [0:16 - 0:19], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
								    A.n4js --> C.n4js
								    B.n4js --> A.n4js
								    C.n4js --> B.n4js)
								""")),
				Pair.of("C", List.of(
						"""
								(Error, [0:17 - 0:20], Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
								    A.n4js --> C.n4js
								    B.n4js --> A.n4js
								    C.n4js --> B.n4js)
								 """)),
				Pair.of("MainBad", List.of(
				// no errors/warnings expected here! (i.e. avoid pure follow-up errors/warnings)
				)));
	}

	@Test
	public void testIllegalLoadtimeReferencesWithinRuntimeCycle() {
		// add some load-time and runtime references to file B.n4js

		Iterable<Pair<String, String>> testCodeWithIllegalLoadtimeReferences = IterableExtensions.map(
				defaultTestCode.entrySet(),
				(moduleNameToContent) -> {
					if ("B".equals(moduleNameToContent.getKey())) {
						return Pair.of(moduleNameToContent.getKey(), """
									%s

									class SomeClass1 extends A {} // ok (because it's in extends/implements clause)
									class SomeClass2 extends B {} // ok (for the same reason)

									function fun1() {}
									fun1(); // error

									new A(); // error

									function fun2() {
										new A(); // ok
									}
								""".formatted(moduleNameToContent.getValue()));
					} else {
						return Pair.of(moduleNameToContent.getKey(), moduleNameToContent.getValue()); // unchanged
					}
				});

		testWorkspaceManager.createTestProjectOnDisk(testCodeWithIllegalLoadtimeReferences);
		startAndWaitForLspServer();

		Iterable<Pair<String, List<String>>> expectedIssuesWithIllegalLoadtimeReferences = Iterables
				.concat(defaultExpectedIssues, List.of(
						Pair.of("B", List.of(
								"""
											(Error, [13:1 - 13:5], Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
										    *A.n4js --> Y.n4js
										    *B.n4js --> A.n4js
										    C.n4js --> B.n4js
										    X.n4js --> C.n4js
										    Y.n4js --> X.n4js)
										""",
								"""
											(Error, [15:5 - 15:6], Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
										    *A.n4js --> Y.n4js
										    *B.n4js --> A.n4js
										    C.n4js --> B.n4js
										    X.n4js --> C.n4js
										    Y.n4js --> X.n4js)
										"""))));

		assertIssues2(expectedIssuesWithIllegalLoadtimeReferences);

		// comment out the runtime dependency X -> C
		changeNonOpenedFile("X", Pair.of("import \"C\";", "// import \"C\";"));
		joinServerRequests();

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeNonOpenedFile("X", Pair.of("// import \"C\";", "import \"C\";"));
		joinServerRequests();

		assertIssues2(expectedIssuesWithIllegalLoadtimeReferences);
	}

	@Test
	public void testIncrementalBuild01_openCloseRuntimeCycle() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		// comment out the runtime dependency X -> C
		changeNonOpenedFile("X", Pair.of("import \"C\";", "// import \"C\";"));
		joinServerRequests();

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeNonOpenedFile("X", Pair.of("// import \"C\";", "import \"C\";"));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues);
	}

	@Test
	public void testIncrementalBuild02_addRemoveLoadtimeDependency() {

		testWorkspaceManager.createTestProjectOnDisk(defaultTestCode);
		startAndWaitForLspServer();

		assertIssues2(defaultExpectedIssues);

		changeNonOpenedFile("B", Pair.of("extends A", ""));
		joinServerRequests();

		assertIssues2(
				Pair.of("MainBad", List.of()), // original issue should be gone
				Pair.of("MainGood", List.of(
						"(Error, [2:9 - 2:10], Couldn't resolve reference to IdentifiableElement 'm'.)")));

		changeNonOpenedFile("B", Pair.of("class B ", "class B extends A "));
		joinServerRequests();

		assertIssues2(defaultExpectedIssues); // original issue should have come back
	}

	@Test
	public void testReexportDoesNotIntroduceLoadTimeDependency01() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("A", """
							export public class ClsA {}
							export { ClsB as ClsBViaA } from "B"
						"""),
				Pair.of("B", """
							export public class ClsB {}
							export { ClsA as ClsAViaB } from "A"
						"""));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("A", List.of(
						"(Error, [1:1 - 2:0], Unsupported feature: separate export statements (add keyword 'export' directly before a class, interface, enum, function or variable declaration).)")),
				Pair.of("B", List.of(
						"(Error, [1:1 - 2:0], Unsupported feature: separate export statements (add keyword 'export' directly before a class, interface, enum, function or variable declaration).)")));
	}

	@Test
	public void testReexportDoesNotIntroduceLoadTimeDependency02() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("A", """
							import { ClsB } from "B"
							export public class ClsA {}
							export { ClsB as ClsBViaA }
						"""),
				Pair.of("B", """
							import { ClsA } from "A"
							export public class ClsB {}
							export { ClsA as ClsAViaB }
						"""));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("A", List.of(
						"(Error, [2:1 - 3:0], Unsupported feature: separate export statements (add keyword 'export' directly before a class, interface, enum, function or variable declaration).)")),
				Pair.of("B", List.of(
						"(Error, [2:1 - 3:0], Unsupported feature: separate export statements (add keyword 'export' directly before a class, interface, enum, function or variable declaration).)")));
	}
}
