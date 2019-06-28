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

import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.eclipse.xtext.util.StringInputStream
import org.junit.Before
import org.junit.Test

/**
 */
class TransitiveDependencyPluginTest extends AbstractBuilderParticipantTest {

	IProject dProjectUnderTest
	IProject cProjectUnderTest
	IProject bProjectUnderTest
	IProject aProjectUnderTest
	IFolder srcD
	IFolder srcC
	IFolder srcB
	IFolder srcA

	@Before
	def void setUp2() {
		super.setUp
		dProjectUnderTest = createJSProject("multiProjectTest.d")
		cProjectUnderTest = createJSProject("multiProjectTest.c")
		bProjectUnderTest = createJSProject("multiProjectTest.b")
		aProjectUnderTest = createJSProject("multiProjectTest.a")
		srcD = configureProjectWithXtext(dProjectUnderTest)
		srcC = configureProjectWithXtext(cProjectUnderTest)
		srcB = configureProjectWithXtext(bProjectUnderTest)
		srcA = configureProjectWithXtext(aProjectUnderTest)
		waitForAutoBuild
	}

	def void setDependsOn(IProject dependendProject, IProject dependsOn) {
		dependendProject.addProjectToDependencies(dependsOn.project.name)
	}

	def void addProjectToDependencies(IProject dependendProject, String projectName) {
		val projectDescriptionFile = dependendProject.project.getFile(N4JSGlobals.PACKAGE_JSON);
		projectDescriptionTestHelper.updateProjectDescription(projectDescriptionFile) [ root |
			PackageJSONTestUtils.addProjectDependency(root, projectName, "*")
		]
		waitForAutoBuild();
	}

	@Test
	def void testAddTransitiveDependency() throws Exception {
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.n()
					}
				}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {
					public n() : void {
						this.m()
					}
				}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'B'.
		// Couldn't resolve reference to IdentifiableElement 'n'.
		// Couldn't resolve reference to Type 'B'.
		// Import of B cannot be resolved.
		assertMarkers("file should have errors", c, 5);

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'A'.
		// Couldn't resolve reference to IdentifiableElement 'm'.
		// Couldn't resolve reference to Type 'A'.
		// Import of A cannot be resolved.
		assertMarkers("file should have errors", b, 5);
		assertMarkers("file should have no errors", a, 0);
		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

		assertMarkers("file should have no errors", a, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testAddTransitiveDependency2() throws Exception {
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public n() : void {
						this.m()
					}
				}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'B'.
		// Couldn't resolve reference to IdentifiableElement 'm'.
		// Couldn't resolve reference to Type 'B'.
		// Import of B cannot be resolved.
		assertMarkers("file should have errors", c, 5);

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'A'.
		// Couldn't resolve reference to Type 'A'.
		// Import of A cannot be resolved.
		assertMarkers("file should have errors", b, 4);
		assertMarkers("file should have no errors", a, 0);
		cProjectUnderTest.dependsOn = bProjectUnderTest
		bProjectUnderTest.dependsOn = aProjectUnderTest

		assertMarkers("file should have no errors", c, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have no errors", a, 0);
	}

	@Test
	def void testAddTransitiveDependency3() throws Exception {
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'B'.
		// Couldn't resolve reference to IdentifiableElement 'm'.
		// Couldn't resolve reference to Type 'B'.
		// Import of B cannot be resolved.
		assertMarkers("file should have errors", c, 5);

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'A'.
		// Couldn't resolve reference to Type 'A'.
		// Import of A cannot be resolved.
		assertMarkers("file should have errors", b, 4);
		assertMarkers("file should have no errors", a, 0);
		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

		assertMarkers("file should have no errors", a, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testModifyTransitiveDependency() throws Exception {
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {}
			'''
		);
		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

		assertMarkers("file should have no errors", a, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have an error", c, 1);

		a.setContents(new StringInputStream("export public class A { protected m(): void {} }", a.charset), false, true, null)
		waitForAutoBuild
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testModifyTransitiveDependency2() throws Exception {
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest

		assertMarkers("file should have no errors", a, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have no errors", c, 0);

		a.setContents(new StringInputStream("export public class A {}", a.charset), false, true, null)
		waitForAutoBuild
		assertMarkers("file should have an error", c, 1);
	}

	@Test
	def void testModifyTransitiveDependency3() throws Exception {
		val d = createTestFile(srcD, "D",
			'''
				import { C } from "C"
				export @Internal public class D extends C {
					public k() : void {
						this.m()
					}
				}
			'''
		);
		val c = createTestFile(srcC, "C",
			'''
				import { B } from "B"
				export @Internal public class C extends B {}
			'''
		);
		val b = createTestFile(srcB, "B",
			'''
				import { A } from "A"
				export public class B extends A {}
			'''
		);
		val a = createTestFile(srcA, "A",
			'''
				export public class A {
					public m() : void {}
				}
			'''
		);
		bProjectUnderTest.dependsOn = aProjectUnderTest
		cProjectUnderTest.dependsOn = bProjectUnderTest
		dProjectUnderTest.dependsOn = cProjectUnderTest

		assertMarkers("file should have no errors", a, 0);
		assertMarkers("file should have no errors", b, 0);
		assertMarkers("file should have no errors", c, 0);
		assertMarkers("file should have no errors", d, 0);

		a.setContents(new StringInputStream("export public class A {}", a.charset), false, true, null)
		waitForAutoBuild
		assertMarkers("file should have an error", d, 1);
	}
}
