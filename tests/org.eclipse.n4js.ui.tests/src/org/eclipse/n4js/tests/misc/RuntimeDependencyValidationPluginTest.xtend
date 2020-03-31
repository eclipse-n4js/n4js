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
package org.eclipse.n4js.tests.misc

import com.google.common.io.CharStreams
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.junit.Test

import static org.junit.Assert.*

/**
 * NOTE: this entire test class is redundant to class {@code RuntimeDependencyValidationIdeTest} and only provided
 * because incremental building cannot be tested in that other class yet!
 * 
 * TODO GH-1675 delete this entire file!
 */
class RuntimeDependencyValidationPluginTest extends AbstractBuilderParticipantTest {

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
		line 2: When importing modules from a runtime cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module A must be preceded by an import of one of the modules C, X, Y.
		Containing runtime dependency cycle cluster:
		    *A.n4js --> Y.n4js
		    *B.n4js --> A.n4js
		    C.n4js --> B.n4js
		    X.n4js --> C.n4js
		    Y.n4js --> X.n4js
	''';

	val defaultExpectedIssues = #[
		"MainBad" -> #[
			defaultExpectedIssueInMainBad
		]
	];

	@Test
	def void testIllegalImportOfLoadtimeTarget() {

		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);
	}

	@Test
	def void testHealingThroughPreceedingImports_nonBareImport() {
		
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add a healing non-bare import to top of MainBad.n4js
		changeFile(files.get("MainBad"),
			'// top of file' -> 'import {C} from "C";',
			'// bottom of file' -> 'new C();'
		);
		waitForIncrementalBuild();

		assertNoIssues();

		// remove both import and usage
		changeFile(files.get("MainBad"),
			'import {C} from "C";' -> '',
			'new C();' -> ''
		);
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues);
	}

	@Test
	def void testHealingThroughPreceedingImports_bareImport() {
		
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add a healing bare import to MainBad.n4js
		changeFile(files.get("MainBad"),
			'// top of file' -> 'import "C";'
		);
		waitForIncrementalBuild();

		assertNoIssues();

		// remove the healing import
		changeFile(files.get("MainBad"),
			'import "C";' -> ''
		);
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues);
	}

	// unused imports must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_unusedImport() {
		
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeFile(files.get("MainBad"),
			'// top of file' -> 'import {C} from "C";'
		);
		waitForIncrementalBuild();

		assertIssues(files,
			"MainBad" -> #[
				defaultExpectedIssueInMainBad,
				'''
					line 1: The import of C is unused.
				'''
			]
		);
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_nonRetainedImport01() {
		
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeFile(files.get("MainBad"),
			'// top of file' -> 'import {C} from "C";',
			'// bottom of file' -> 'export function foo(p: C) {}'
		);
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues); // issue must *not* be gone
	}

	// imports not retained at runtime must not have a healing effect
	@Test
	def void testHealingThroughPreceedingImports_nonRetainedImport02() {
		
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add an unused healing non-bare import to top of MainBad.n4js
		changeFile(files.get("MainBad"),
			'// top of file' -> 'import {EnumInC} from "C";',
			'// bottom of file' -> 'console.log(EnumInC.L1);'
		);
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues); // issue must *not* be gone
	}

	@Test
	def void testLoadtimeDependencyConflict() {

		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// add additional usage of load-time dependency target B from within the cycle
		changeFile(files.get("X"),
			'// top of file' -> 'import {B} from "B";',
			'// bottom of file' -> 'class X extends B {}'
		);
		waitForIncrementalBuild();

		assertIssuesInModules(files, #[
			"X" -> #[
				'''
					line 1: A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module C.
					Containing runtime dependency cycle cluster:
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> B.n4js, C.n4js
					    Y.n4js --> X.n4js
				'''
			],
			"C" -> #[
				'''
					line 1: A load-time dependency target module B must only be imported once within the same runtime dependency cycle, but B is also imported by module X.
					Containing runtime dependency cycle cluster:
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> B.n4js, C.n4js
					    Y.n4js --> X.n4js
				'''
			]
		]);

		// revert the above change
		changeFile(files.get("X"),
			'import {B} from "B";' -> '',
			'class X extends B {}' -> ''
		);
		waitForIncrementalBuild();

		assertIssuesInModules(files, #[
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

		val files = createTestProjectOnDisk(testCodeWithLoadtimeCycle);
		cleanBuild();

		assertIssues(files,
			"A" -> #[
				'''
					line 1: Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js
				'''
			],
			"B" -> #[
				'''
					line 1: Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js
				'''
			],
			"C" -> #[
				'''
					line 1: Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.
					    A.n4js --> C.n4js
					    B.n4js --> A.n4js
					    C.n4js --> B.n4js
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

		val files = createTestProjectOnDisk(testCodeWithIllegalLoadtimeReferences);
		cleanBuild();

		val expectedIssuesWithIllegalLoadtimeReferences = defaultExpectedIssues + #[
			"B" -> #[
				'''
					line 13: Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js
				''',
				'''
					line 15: Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js
				'''
			]
		];

		assertIssues(files, expectedIssuesWithIllegalLoadtimeReferences);

		// comment out the runtime dependency X -> C
		changeFile(files.get("X"), 'import "C";' -> '// import "C";');
		waitForIncrementalBuild();

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeFile(files.get("X"), '// import "C";' -> 'import "C";');
		waitForIncrementalBuild();

		assertIssues(files, expectedIssuesWithIllegalLoadtimeReferences);
	}

	@Test
	def void testIncrementalBuild01_openCloseRuntimeCycle() {
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// comment out the runtime dependency X -> C
		changeFile(files.get("X"), 'import "C";' -> '// import "C";');
		waitForIncrementalBuild();

		assertNoIssues();

		// re-enable the runtime dependency X -> C
		changeFile(files.get("X"), '// import "C";' -> 'import "C";');
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues);
	}

	@Test
	def void testIncrementalBuild02_addRemoveLoadtimeDependency() {

		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		changeFile(files.get("B"), "extends A" -> "");
		waitForIncrementalBuild();

		assertIssues(files,
			"MainBad" -> #[], // original issue should be gone
			"MainGood" -> #[
				"line 3: Couldn't resolve reference to IdentifiableElement 'm'."
			]
		);

		changeFile(files.get("B"), "class B " -> "class B extends A ");
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues); // original issue should have come back
	}

	// utility methods:

	def private Map<String, IFile> createTestProjectOnDisk(Iterable<Pair<String, String>> moduleNameToContents) {
		val testProject = createN4JSProject("TestProject", ProjectType.LIBRARY);
		createAndRegisterDummyN4JSRuntime(testProject);
		val folderSrc = testProject.project.getFolder("src");
		assertTrue(folderSrc.exists);
		return createTestFiles(folderSrc, moduleNameToContents);
	}

	def private Map<String, IFile> createTestFiles(IFolder folder, Iterable<Pair<String, String>> moduleNameToContents) {
		val result = new LinkedHashMap<String, IFile>();
		for (pair : moduleNameToContents) {
			val moduleName = pair.key;
			val content = pair.value;
			val file = createTestFile(folder, moduleName, content);
			result.put(moduleName, file);
		}
		return result;
	}

	def private void changeFile(IFile file, Pair<CharSequence, CharSequence>... targetsToReplacement) {
		var String oldContent;
		val in = new InputStreamReader(file.contents, StandardCharsets.UTF_8);
		try {
			oldContent = CharStreams.toString(in);
		} finally {
			in.close();
		}
		var newContent = oldContent;
		for (targetToReplacement : targetsToReplacement) {
			if (!oldContent.contains(targetToReplacement.key)) {
				println(oldContent);
				fail("file " + file.name + " does not contain search string \"" + targetToReplacement.key + "\"");
			}
			newContent = newContent.replace(targetToReplacement.key, targetToReplacement.value);
		}
		changeTestFile(file, newContent);
		file.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
	}

	def private void assertIssues(Map<String, IFile> moduleNameToFile, Pair<String, List<String>>... moduleNameToExpectedIssues) {
		for (entry : moduleNameToFile.entrySet) {
			val moduleName = entry.key;
			val file = entry.value;
			val expectedIssues = moduleNameToExpectedIssues.findFirst[key == moduleName]?.value?.map[trim] ?: #[];
			assertIssues(file, expectedIssues);
		}
	}

	def private void assertIssuesInModules(Map<String, IFile> moduleNameToFile, Pair<String, List<String>>... moduleNameToExpectedIssues) {
		for (entry : moduleNameToFile.entrySet) {
			val moduleName = entry.key;
			val file = entry.value;
			val expectedIssues = moduleNameToExpectedIssues.findFirst[key == moduleName]?.value?.map[trim];
			if (expectedIssues !== null) {
				assertIssues(file, expectedIssues);
			}
		}
	}
}
