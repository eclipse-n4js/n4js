/**
 * Copyright (c) 2017 NumberFour AG.
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
 * NOTE: this entire test class is redundant to class {@code RunTimeDependencyValidationIdeTest} and only provided
 * because incremental building cannot be tested in that other class yet!
 * 
 * FIXME delete this entire class
 */
class RunTimeDependencyValidationPluginTest extends AbstractBuilderParticipantTest {

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
				line 1: Import of load-time dependency target A will be healed by inserting an additional import in output code.
				Containing run-time dependency cycle cluster:
				    *A.n4js --> Y.n4js
				    *B.n4js --> A.n4js
				    C.n4js --> B.n4js
				    X.n4js --> C.n4js
				    Y.n4js --> X.n4js
			'''
		]
	];

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

		val files = createTestProjectOnDisk(testCodeWithLoadTimeCycle);
		cleanBuild();

		assertIssues(files,
			"A" -> #[
				'''
					line 1: Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"B" -> #[
				'''
					line 1: Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
				'''
			],
			"C" -> #[
				'''
					line 1: Load-time dependency cycle.
					    *A.n4js --> C.n4js
					    *B.n4js --> A.n4js
					    *C.n4js --> B.n4js
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

		val files = createTestProjectOnDisk(testCodeWithIllegalLoadTimeReferences);
		cleanBuild();

		val expectedIssuesWithIllegalLoadTimeReferences = defaultExpectedIssues + #[
			"B" -> #[
				'''
					line 13: Illegal load-time reference within run-time dependency cycle cluster.
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js
				''',
				'''
					line 15: Illegal load-time reference within run-time dependency cycle cluster.
					    *A.n4js --> Y.n4js
					    *B.n4js --> A.n4js
					    C.n4js --> B.n4js
					    X.n4js --> C.n4js
					    Y.n4js --> X.n4js
				'''
			]
		];

		assertIssues(files, expectedIssuesWithIllegalLoadTimeReferences);

		// comment out the run-time dependency X -> C
		changeFile(files.get("X"), 'import "C";' -> '// import "C";');
		waitForIncrementalBuild();

		assertNoIssues();

		// re-enable the run-time dependency X -> C
		changeFile(files.get("X"), '// import "C";' -> 'import "C";');
		waitForIncrementalBuild();

		assertIssues(files, expectedIssuesWithIllegalLoadTimeReferences);
	}

	@Test
	def void testIncrementalBuild01_openCloseRunTimeCycle() throws Exception {
		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		// comment out the run-time dependency X -> C
		changeFile(files.get("X"), 'import "C";' -> '// import "C";');
		waitForIncrementalBuild();

		assertNoIssues();

		// re-enable the run-time dependency X -> C
		changeFile(files.get("X"), '// import "C";' -> 'import "C";');
		waitForIncrementalBuild();

		assertIssues(files, defaultExpectedIssues);
	}

	@Test
	def void testIncrementalBuild02_addRemoveLoadTimeDependency() throws Exception {

		val files = createTestProjectOnDisk(defaultTestCode);
		cleanBuild();

		assertIssues(files, defaultExpectedIssues);

		changeFile(files.get("B"), "extends A" -> "");
		waitForIncrementalBuild();

		assertIssues(files,
			"MainBad" -> #[], // original issue should be gone
			"MainGood" -> #[
				"line 2: Couldn't resolve reference to IdentifiableElement 'm'."
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

	def private void changeFile(IFile file, Pair<CharSequence, CharSequence> targetToReplacement) {
		var String oldContent;
		val in = new InputStreamReader(file.contents, StandardCharsets.UTF_8);
		try {
			oldContent = CharStreams.toString(in);
		} finally {
			in.close();
		}
		if (!oldContent.contains(targetToReplacement.key)) {
			println(oldContent);
			fail("file " + file.name + " does not contain search string \"" + targetToReplacement.key + "\"");
		}
		val newContent = oldContent.replace(targetToReplacement.key, targetToReplacement.value);
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
}
