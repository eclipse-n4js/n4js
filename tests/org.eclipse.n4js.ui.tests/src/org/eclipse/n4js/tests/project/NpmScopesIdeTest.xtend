/** 
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project

import com.google.common.collect.Lists
import java.io.File
import org.eclipse.core.runtime.CoreException
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

/**
 * Testing the use of npm scopes as part of N4JS project names, i.e. project names of
 * the form "@myScope/myProject".
 */
// converted from NpmScopesPluginTest
class NpmScopesIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final String YARN_WORKSPACE_BASE = "npmScopes";

	private FileURI yarnProject;
	private FileURI scopedProject;
	private FileURI nonScopedProject;
	private FileURI clientProject;
	private FileURI clientModule;
	private FileURI clientModuleOutputFile;

	@Before
	def void before() {
		importProband(new File(PROBANDS, YARN_WORKSPACE_BASE), Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		assertNoIssues();

		scopedProject = getProjectLocation().toFileURI.appendSegments("@myScope", "Lib");
		nonScopedProject = getProjectLocation().toFileURI.appendSegments("Lib");
		clientProject = getProjectLocation().toFileURI.appendSegments("XClient");

		assertTrue(scopedProject.exists);
		assertTrue(nonScopedProject.exists);
		assertTrue(clientProject.exists);

		clientModule = clientProject.appendSegments("src", "ClientModule.n4js");
		clientModuleOutputFile = clientProject.appendSegments("src-gen", "ClientModule.js");
		assertTrue(clientModule.exists);
		assertTrue(clientModuleOutputFile.exists);
	}

	@Test
	def void testImportModuleThatExistsOnlyInScopedProject() throws CoreException {
		setContentsOfClientModule('''
			import {A} from "A"
			new A().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from A in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {A} from "@myScope/Lib/A"     // <-- should have same result as above
			new A().foo();
		''');
		assertNoIssues();
		assertCorrectOutput('''
			Hello from A in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {A} from "Lib/A"              // <-- must *not* work (because module A not contained in non-scoped project "Lib")
		''');
		assertIssues(
			"ClientModule" -> #[
				"(Error, [0:16 - 0:23], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testImportModuleThatExistsOnlyInNonScopedProject() throws CoreException {
		setContentsOfClientModule('''
			import {B} from "B"
			new B().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from B in Lib!
		''')

		setContentsOfClientModule('''
			import {B} from "@myScope/Lib/B"     // <-- must *not* work (because module B not contained in scoped project "@myScope/Lib")
		''')
		assertIssues(
			"ClientModule" -> #[
				"(Error, [0:16 - 0:32], Cannot resolve complete module specifier (with project name as first segment): no matching module found.)"
			]
		);

		setContentsOfClientModule('''
			import {B} from "Lib/B"              // <-- should have same result as first import above
			new B().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from B in Lib!
		''')
	}

	@Test
	def void testImportModuleThatExistsInBothProjects() throws CoreException {
		setContentsOfClientModule('''
			import {C} from "@myScope/Lib/C"
			new C().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from C in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {C as C_scoped} from "@myScope/Lib/C"
			import {C as C_nonScoped} from "Lib/C"
			new C_scoped().foo();
			new C_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from C in @myScope/Lib!
			Hello from C in Lib!
		''')
	}

	@Test
	def void testImportModuleThatExistsInBothProjectsInSubFolders() throws CoreException {
		setContentsOfClientModule('''
			import {C} from "@myScope/Lib/folder1/folder2/C"
			new C().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from folder1/folder2/C in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {C as C_scoped} from "@myScope/Lib/folder1/folder2/C"
			import {C as C_nonScoped} from "Lib/folder1/folder2/C"
			new C_scoped().foo();
			new C_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from folder1/folder2/C in @myScope/Lib!
			Hello from folder1/folder2/C in Lib!
		''')
	}

	@Test
	def void testImportMainModule() throws CoreException {
		setContentsOfClientModule('''
			import {D as D_scoped} from "@myScope/Lib"
			import {D as D_nonScoped} from "Lib"
			new D_scoped().foo();
			new D_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from D in @myScope/Lib!
			Hello from D in Lib!
		''')
	}

	def private void setContentsOfClientModule(CharSequence source) {
		changeNonOpenedFile(clientModule, source.toString);
		joinServerRequests();
	}

	def private void assertCorrectOutput(CharSequence expectedOutput) {
		val result = runInNodejs(clientProject.toFile, clientModuleOutputFile);
		val actualOutput = result.stdOut.trim;
		val expectedOutputTrimmed = expectedOutput.toString.trim;
		assertEquals("incorrect output when running " + clientModule.name, expectedOutputTrimmed, actualOutput);
	}
}
