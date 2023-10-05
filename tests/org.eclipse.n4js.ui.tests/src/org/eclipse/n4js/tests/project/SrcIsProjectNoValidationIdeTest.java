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

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests if the source folder can be set to {@code "."}
 */
// converted from SrcIsProjectNoValidationPluginTest
public class SrcIsProjectNoValidationIdeTest extends ConvertedIdeTest {

	IProject projectUnderTest;
	IFolder src;
	IFolder src_P;
	IFolder src_P_Q;
	IFile projectDescriptionFile;

	@Test
	public void testFileInSrc() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("P/A", getFileContentsA()),
				Pair.of("P/B", getFileContentsB()),
				Pair.of("P/C", getFileContentsC()),
				Pair.of("P/D", getFileContentsD()),
				Pair.of("P/Q/E", getFileContentsE()),
				Pair.of("P/Q/F", getFileContentsF()));
		startAndWaitForLspServer();

		FileURI fileA = getFileURIFromModuleName("A");
		FileURI fileB = getFileURIFromModuleName("B");
		FileURI fileC = getFileURIFromModuleName("C");
		FileURI fileD = getFileURIFromModuleName("D");
		FileURI fileE = getFileURIFromModuleName("E");
		FileURI fileF = getFileURIFromModuleName("F");
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile());

		assertEquals("file A should have 5 markers", 5, getIssuesInFile(fileA).size());
		assertEquals("file B should have 5 markers", 5, getIssuesInFile(fileB).size());
		assertEquals("file C should have 2 markers", 2, getIssuesInFile(fileC).size());
		assertEquals("file D should have 2 markers", 2, getIssuesInFile(fileD).size());
		assertEquals("file E should have 5 markers", 5, getIssuesInFile(fileE).size());
		assertEquals("file F should have 5 markers", 5, getIssuesInFile(fileF).size());
		assertEquals("project description file (package.json) should have 0 marker", 0,
				getIssuesInFile(packageJsonFileURI).size());

		String moduleFilters = "\"moduleFilters\": { \"noValidate\": [ \"P/D\", \"P/Q/*\" ] }";
		changeNonOpenedFile(packageJsonFileURI,
				Pair.of("\"library\"", "\"library\", " + moduleFilters));
		joinServerRequests();

		assertEquals("project description file (package.json) should have 2 marker", 2,
				getIssuesInFile(packageJsonFileURI).size());
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size());
		assertIssuesInFiles(
				Map.of(
						// file E should have no markers created by validation (just one from failed transpilation)
						fileE, Collections.emptyList(),
						// file F should have no markers created by validation (just one from failed transpilation)
						fileF, Collections.emptyList()));
	}

	String getFileContentsA() {
		return """
				{
					function funA() {
					}
				}

				import * as JN from "src/P/Q/F"

				export public class A {

					$doIt() {
						var classB : JN.ClassB;

					}
				}
				""";
	}

	String getFileContentsB() {
		return """
				import { A } from "src/P/Q/E"

				{
					function B() {
						var a : A;
						a.$b;
					}
				}

				export public class ClassB {
					$a : A;
				}
				""";
	}

	String getFileContentsC() {
		return """
				{
					function Module() {
					}
				}

				export public class Hack {
					$hack() {

					}
				}
				""";
	}

	String getFileContentsD() {
		return """
				{
					function ugly() {
					}
				}

				export public class Hack {
					$hack() {

					}
				}
				""";
	}

	String getFileContentsE() {
		return """
				{
					function funA() {
					}
				}

				import { B } from "src/P/Q/F"

				export public class A {
					$b : B;
				}
				""";
	}

	String getFileContentsF() {
		return """
				import { A } from "src/P/Q/E"

				{
					function B() {
						var a : A;
						a.$b;
					}
				}

				export public class ClassB {
					$a : A;
				}
				""";
	}
}
