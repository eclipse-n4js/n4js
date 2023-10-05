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
package org.eclipse.n4js.tests.project;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 */
// converted from TransitiveDependencyPluginTest
public class TransitiveDependencyIdeTest extends ConvertedIdeTest {

	private static final String PROJECT_A = "multiProjectTest.a";
	private static final String PROJECT_B = "multiProjectTest.b";
	private static final String PROJECT_C = "multiProjectTest.c";
	private static final String PROJECT_D = "multiProjectTest.d";

	private FileURI dProjectUnderTest;
	private FileURI cProjectUnderTest;
	private FileURI bProjectUnderTest;
	private FileURI aProjectUnderTest;

	@Before
	public void setUp2() {
		testWorkspaceManager.createTestOnDisk(
				Pair.of(PROJECT_D, Collections.emptyList()),
				Pair.of(PROJECT_C, Collections.emptyList()),
				Pair.of(PROJECT_B, Collections.emptyList()),
				Pair.of(PROJECT_A, Collections.emptyList()));
		startAndWaitForLspServer();
		assertNoIssues();
		dProjectUnderTest = toFileURI(getProjectRoot(PROJECT_D));
		cProjectUnderTest = toFileURI(getProjectRoot(PROJECT_C));
		bProjectUnderTest = toFileURI(getProjectRoot(PROJECT_B));
		aProjectUnderTest = toFileURI(getProjectRoot(PROJECT_A));
	}

	public void setDependsOn(FileURI dependendProject, FileURI dependsOn) throws IOException {
		PackageJsonUtils.addDependenciesToPackageJsonFile(
				dependendProject.appendSegment(N4JSGlobals.PACKAGE_JSON).toPath(),
				Pair.of(dependsOn.getName(), "*"));
		joinServerRequests();
	}

	@Test
	public void testAddTransitiveDependency() throws Exception {
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {
							public k() : void {
								this.n()
							}
						}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {
							public n() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {
							public m() : void {}
						}
						""");
		joinServerRequests();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'n'.)")),
				Pair.of("B", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)",
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")));

		setDependsOn(bProjectUnderTest, aProjectUnderTest);
		setDependsOn(cProjectUnderTest, bProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	public void testAddTransitiveDependency2() throws Exception {
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {
							public n() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {
							public m() : void {}
						}
						""");
		joinServerRequests();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")),
				Pair.of("B", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)")));

		setDependsOn(cProjectUnderTest, bProjectUnderTest);
		setDependsOn(bProjectUnderTest, aProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	public void testAddTransitiveDependency3() throws Exception {
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {
							public k() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {
							public m() : void {}
						}
						""");
		joinServerRequests();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:40 - 1:41], Couldn't resolve reference to Type 'B'.)",
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")),
				Pair.of("B", List.of(
						"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [1:30 - 1:31], Couldn't resolve reference to Type 'A'.)")));

		setDependsOn(bProjectUnderTest, aProjectUnderTest);
		setDependsOn(cProjectUnderTest, bProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	public void testModifyTransitiveDependency() throws Exception {
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {
							public k() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {}
						""");

		setDependsOn(bProjectUnderTest, aProjectUnderTest);
		setDependsOn(cProjectUnderTest, bProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")));

		changeNonOpenedFile("A", """
				export public class A {
					protected m(): void {}
				}
				""");
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	public void testModifyTransitiveDependency2() throws Exception {
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {
							public k() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {
							public m() : void {}
						}
						""");

		setDependsOn(bProjectUnderTest, aProjectUnderTest);
		setDependsOn(cProjectUnderTest, bProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();

		changeNonOpenedFile("A", """
				export public class A {}
				""");
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertIssues2(
				Pair.of("C", List.of(
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")));
	}

	@Test
	public void testModifyTransitiveDependency3() throws Exception {
		createFile(PROJECT_D, "D",
				"""
						import { C } from "C"
						export @Internal public class D extends C {
							public k() : void {
								this.m()
							}
						}
						""");
		createFile(PROJECT_C, "C",
				"""
						import { B } from "B"
						export @Internal public class C extends B {}
						""");
		createFile(PROJECT_B, "B",
				"""
						import { A } from "A"
						export public class B extends A {}
						""");
		createFile(PROJECT_A, "A",
				"""
						export public class A {
							public m() : void {}
						}
						""");

		setDependsOn(bProjectUnderTest, aProjectUnderTest);
		setDependsOn(cProjectUnderTest, bProjectUnderTest);
		setDependsOn(dProjectUnderTest, cProjectUnderTest);

		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertNoIssues();

		changeNonOpenedFile("A", """
				export public class A {}
				""");
		joinServerRequests();
		// TODO GH-2060 next line should not be necessary
		cleanBuildAndWait();
		assertIssues2(
				Pair.of("D", List.of(
						"(Error, [3:7 - 3:8], Couldn't resolve reference to IdentifiableElement 'm'.)")));
	}
}
