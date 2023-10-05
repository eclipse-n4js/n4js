/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Testing the use of npm scopes as part of N4JS project names, i.e. project names of the form "@myScope/myProject".
 */
// converted from NpmScopesPluginTest
public class NpmScopesIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final String YARN_WORKSPACE_BASE = "npmScopes";

	private FileURI scopedProject;
	private FileURI nonScopedProject;
	private FileURI clientProject;
	private FileURI clientModule;
	private FileURI clientModuleOutputFile;

	@Before
	public void before() {
		importProband(new File(PROBANDS, YARN_WORKSPACE_BASE), Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		assertNoIssues();

		FileURI prjURI = toFileURI(getProjectLocation());
		scopedProject = prjURI.appendSegments("@myScope", "Lib");
		nonScopedProject = prjURI.appendSegments("Lib");
		clientProject = prjURI.appendSegments("XClient");

		assertTrue(scopedProject.exists());
		assertTrue(nonScopedProject.exists());
		assertTrue(clientProject.exists());

		clientModule = clientProject.appendSegments("src", "ClientModule.n4js");
		clientModuleOutputFile = clientProject.appendSegments("src-gen", "ClientModule.js");
		assertTrue(clientModule.exists());
		assertTrue(clientModuleOutputFile.exists());
	}

	@Test
	public void testImportModuleThatExistsOnlyInScopedProject() {
		setContentsOfClientModule("""
					import {A} from "A"
					new A().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from A in @myScope/Lib!
				""");

		setContentsOfClientModule("""
					import {A} from "@myScope/Lib/A"     // <-- should have same result as above
					new A().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from A in @myScope/Lib!
				""");

		setContentsOfClientModule(
				"""
							import {A} from "Lib/A"              // <-- must *not* work (because module A not contained in non-scoped project "Lib")
						""");
		assertIssues2(
				Pair.of("ClientModule", List.of(
						"(Error, [0:16 - 0:23], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)")));
	}

	@Test
	public void testImportModuleThatExistsOnlyInNonScopedProject() {
		setContentsOfClientModule("""
					import {B} from "B"
					new B().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from B in Lib!
				""");

		setContentsOfClientModule(
				"""
							import {B} from "@myScope/Lib/B"     // <-- must *not* work (because module B not contained in scoped project "@myScope/Lib")
						""");
		assertIssues2(
				Pair.of("ClientModule", List.of(
						"(Error, [0:16 - 0:32], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)")));

		setContentsOfClientModule("""
					import {B} from "Lib/B"              // <-- should have same result as first import above
					new B().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from B in Lib!
				""");
	}

	@Test
	public void testImportModuleThatExistsInBothProjects() {
		setContentsOfClientModule("""
					import {C} from "@myScope/Lib/C"
					new C().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from C in @myScope/Lib!
				""");

		setContentsOfClientModule("""
					import {C as C_scoped} from "@myScope/Lib/C"
					import {C as C_nonScoped} from "Lib/C"
					new C_scoped().foo();
					new C_nonScoped().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from C in @myScope/Lib!
					Hello from C in Lib!
				""");
	}

	@Test
	public void testImportModuleThatExistsInBothProjectsInSubFolders() {
		setContentsOfClientModule("""
					import {C} from "@myScope/Lib/folder1/folder2/C"
					new C().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from folder1/folder2/C in @myScope/Lib!
				""");

		setContentsOfClientModule("""
					import {C as C_scoped} from "@myScope/Lib/folder1/folder2/C"
					import {C as C_nonScoped} from "Lib/folder1/folder2/C"
					new C_scoped().foo();
					new C_nonScoped().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from folder1/folder2/C in @myScope/Lib!
					Hello from folder1/folder2/C in Lib!
				""");
	}

	@Test
	public void testImportMainModule() {
		setContentsOfClientModule("""
					import {D as D_scoped} from "@myScope/Lib"
					import {D as D_nonScoped} from "Lib"
					new D_scoped().foo();
					new D_nonScoped().foo();
				""");
		assertNoIssues();
		assertCorrectOutput("""
					Hello from D in @myScope/Lib!
					Hello from D in Lib!
				""");
	}

	private void setContentsOfClientModule(CharSequence source) {
		changeNonOpenedFile(clientModule, source.toString());
		joinServerRequests();
	}

	private void assertCorrectOutput(CharSequence expectedOutput) {
		ProcessResult result = runInNodejs(clientProject.toFile(), clientModuleOutputFile);
		String actualOutput = result.getStdOut().trim();
		String expectedOutputTrimmed = expectedOutput.toString().trim();
		assertEquals("incorrect output when running " + clientModule.getName(), expectedOutputTrimmed, actualOutput);
	}
}
