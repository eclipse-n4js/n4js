/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project

import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.workspace.locations.FileURI
import org.junit.Before
import org.junit.Test

/**
 */
// converted from TransitiveDependencyPluginTest
class TransitiveDependencyIdeTest extends ConvertedIdeTest {

	private static final String PROJECT_A = "multiProjectTest.a";
	private static final String PROJECT_B = "multiProjectTest.b";
	private static final String PROJECT_C = "multiProjectTest.c";
	private static final String PROJECT_D = "multiProjectTest.d";

	private FileURI dProjectUnderTest
	private FileURI cProjectUnderTest
	private FileURI bProjectUnderTest
	private FileURI aProjectUnderTest
	private FileURI srcD
	private FileURI srcC
	private FileURI srcB
	private FileURI srcA

	@Before
	def void setUp2() {
		testWorkspaceManager.createTestOnDisk(
			PROJECT_D -> #[],
			PROJECT_C -> #[],
			PROJECT_B -> #[],
			PROJECT_A -> #[]
		);
		startAndWaitForLspServer();
		assertNoIssues();
		dProjectUnderTest = getProjectRoot(PROJECT_D).toFileURI
		cProjectUnderTest = getProjectRoot(PROJECT_C).toFileURI
		bProjectUnderTest = getProjectRoot(PROJECT_B).toFileURI
		aProjectUnderTest = getProjectRoot(PROJECT_A).toFileURI
		srcD = aProjectUnderTest.appendSegment(DEFAULT_SOURCE_FOLDER);
		srcC = bProjectUnderTest.appendSegment(DEFAULT_SOURCE_FOLDER);
		srcB = cProjectUnderTest.appendSegment(DEFAULT_SOURCE_FOLDER);
		srcA = dProjectUnderTest.appendSegment(DEFAULT_SOURCE_FOLDER);
	}

	def void setDependsOn(FileURI dependendProject, FileURI dependsOn) {
		PackageJsonUtils.addDependenciesToPackageJsonFile(
			dependendProject.appendSegment(N4JSGlobals.PACKAGE_JSON).toPath,
			dependsOn.name -> "*"
		);
		joinServerRequests();
	}

	@Test
	def void testAddTransitiveDependency() throws Exception {
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.n()
					}
				}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {
					public n() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'n'.)"
			],
			"B" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)",
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);

		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testAddTransitiveDependency2() throws Exception {
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public n() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			],
			"B" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)"
			]
		);

		cProjectUnderTest.dependsOn = bProjectUnderTest
		bProjectUnderTest.dependsOn = aProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testAddTransitiveDependency3() throws Exception {
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			],
			"B" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)"
			]
		);

		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testModifyTransitiveDependency() throws Exception {
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {}
			'''
		);

		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"C" -> #[
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);

		changeNonOpenedFile("A", '''
			export public class A {
				protected m(): void {}
			}
		''');
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testModifyTransitiveDependency2() throws Exception {
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);

		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();

		changeNonOpenedFile("A", '''
			export public class A {}
		''');
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"C" -> #[
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);
	}

	@Test
	def void testModifyTransitiveDependency3() throws Exception {
		createFile(PROJECT_D, "D",
			'''
				import { C } from "C"
				export @Internal public class D extends C {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		createFile(PROJECT_C, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {}
			'''
		);
		createFile(PROJECT_B, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		createFile(PROJECT_A, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);

		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest
		dProjectUnderTest.dependsOn = cProjectUnderTest

// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();

		changeNonOpenedFile("A", '''
			export public class A {}
		''');
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"D" -> #[
				"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)"
			]
		);
	}
}
