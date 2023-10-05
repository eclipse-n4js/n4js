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

import java.nio.file.Files;
import java.util.List;

import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
// converted from NoValidationPluginTest
public class NoValidationIdeTest extends ConvertedIdeTest {

	private static boolean oldOpaqueJsModules;

	@BeforeClass
	public static void setOpaqueJsModules() {
		oldOpaqueJsModules = N4JSLanguageUtils.OPAQUE_JS_MODULES;
		N4JSLanguageUtils.OPAQUE_JS_MODULES = true;
	}

	@AfterClass
	public static void restoreOpaqueJsModules() {
		N4JSLanguageUtils.OPAQUE_JS_MODULES = oldOpaqueJsModules;
	}

	@Test
	public void testFileInSrc() throws Exception {
		// setup test workspace
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("p/A", fileA()),
				Pair.of("p/B", fileB()),
				Pair.of("p/C", fileC()),
				Pair.of("p/D", fileD()),
				Pair.of("p/q/E", fileE()),
				Pair.of("p/q/F", fileF()));
		startAndWaitForLspServer();

		FileURI fileAValidated = getFileURIFromModuleName("A");
		FileURI fileBValidated = getFileURIFromModuleName("B");
		FileURI fileC = getFileURIFromModuleName("C");
		FileURI fileD = getFileURIFromModuleName("D");
		FileURI fileE = getFileURIFromModuleName("E");
		FileURI fileF = getFileURIFromModuleName("F");

		// create a second source folder with a file p2/A.n4js
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile(DEFAULT_PROJECT_NAME));
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(packageJsonFileURI.toPath(), SourceContainerType.SOURCE,
				"src2");
		sendDidChangeWatchedFiles(packageJsonFileURI);
		FileURI fileAValidatedInSrc2 = fileAValidated.getParent().getParent().appendSegments("src2", "p2", "A.n4js");
		createFile(fileAValidatedInSrc2, fileA());
		cleanBuildAndWait();

		// assert markers
		assertEquals("file A should have 3 markers", 3, getIssuesInFile(fileAValidated).size());
		assertEquals("file AInSrc2 should have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size());
		assertEquals("file B should have markers", 2, getIssuesInFile(fileBValidated).size());
		assertEquals("file C should have markers", 2, getIssuesInFile(fileC).size());
		assertEquals("file D should have markers", 2, getIssuesInFile(fileD).size());
		assertEquals("file E should have markers", 5, getIssuesInFile(fileE).size());
		assertEquals("file F should have markers", 2, getIssuesInFile(fileF).size());

		String moduleFiltersStep1 = "\"moduleFilters\": { \"noValidate\": [ \"p/D\", \"p/q/*\" ] }";
		String moduleFiltersStep2 = "\"moduleFilters\": { \"noValidate\": [ \"p/D\", \"p/q/*\", { \"module\": \"p2/*\", \"sourceContainer\": \"src2\" } ] }";
		changeNonOpenedFile(packageJsonFileURI,
				Pair.of("\"library\"", "\"library\", " + moduleFiltersStep1));
		joinServerRequests();

		assertEquals("file A should still have 3 markers", 3, getIssuesInFile(fileAValidated).size());
		assertEquals("file AInSrc2 should still have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size());
		assertEquals("file B should still have markers", 2, getIssuesInFile(fileBValidated).size());
		assertEquals("file C should still have markers", 2, getIssuesInFile(fileC).size());
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size());
		assertEquals("file E should have no markers", 0, getIssuesInFile(fileE).size());
		assertEquals("file F should have no markers", 0, getIssuesInFile(fileF).size());

		changeNonOpenedFile(packageJsonFileURI, Pair.of(moduleFiltersStep1, moduleFiltersStep2));
		joinServerRequests();

		assertEquals("file A should still have 3 markers", 3, getIssuesInFile(fileAValidated).size());
		assertEquals("file AInSrc2 should still have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size());
		assertEquals("file B should still have markers", 2, getIssuesInFile(fileBValidated).size());
		assertEquals("file C should still have markers", 2, getIssuesInFile(fileC).size());
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size());
		assertEquals("file E should have no markers", 0, getIssuesInFile(fileE).size());
		assertEquals("file F should have no markers", 0, getIssuesInFile(fileF).size());
	}

	@Test
	public void testTypeDefinitionsShadowing() throws Exception {
		// setup test workspace
		testWorkspaceManager.createTestProjectOnDisk();

		// create a second source folder
		FileURI packageJsonFileURI = toFileURI(getPackageJsonFile(DEFAULT_PROJECT_NAME));
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(packageJsonFileURI.toPath(), SourceContainerType.EXTERNAL,
				"src-ext");
		// (do not send a notification, because server is not started yet)

		// create files
		FileURI fileImpl = toFileURI(getProjectRoot()).appendSegments("src-ext", "Shadowed.js");
		FileURI fileTypeDef = toFileURI(getProjectRoot()).appendSegments("src", "Shadowed.n4jsd");
		fileImpl.getParent().toFile().mkdirs();
		Files.writeString(fileImpl.toPath(), """
				INVALID console.log('impl');
				""");
		fileTypeDef.getParent().toFile().mkdirs();
		Files.writeString(fileTypeDef.toPath(), """
				// invalid, since 'external' modifier is missing
				export public class Shadow {}
				""");

		startAndWaitForLspServer();

		// assert markers
		assertIssues2(
				Pair.of("Shadowed.n4jsd", List.of(
						"(Error, [1:20 - 1:26], Only namespaces, classes, interfaces, enums, type aliases and functions declared as external as well as structurally typed interfaces are allowed in n4jsd files.)")));
		assertEquals("file src/js/Shadowed.js should have 0 markers", 0, getIssuesInFile(fileImpl).size());
		assertEquals("file src/n4js/Shadowed.n4jsd should have 1 marker since it's invalid", 1,
				getIssuesInFile(fileTypeDef).size());

		String moduleFilters = "\"moduleFilters\": { \"noValidate\": [ \"Shadowed\" ] }";
		changeNonOpenedFile(packageJsonFileURI,
				Pair.of("\"library\"", "\"library\", " + moduleFilters));
		joinServerRequests();

		assertIssues2(
				Pair.of(DEFAULT_PROJECT_NAME + "/package.json", List.of(
						"(Error, [7:69 - 7:79], Module filters of type noValidate must not match N4JS modules/files.)")));
		assertEquals("file package.json should have 1 marker since the module filter is invalid", 1,
				getIssuesInFile(packageJsonFileURI).size());
		assertEquals("file src/js/Shadowed.js should have 0 markers", 0, getIssuesInFile(fileImpl).size());
		assertEquals(
				"file src/n4js/Shadowed.n4jsd should still have 0 markers since it is filtered by the invalid module filter",
				0, getIssuesInFile(fileTypeDef).size());
	}

	String fileA() {
		return """
				{
					function funA() {
					}
				}

				import * as JN from "p/q/F"

				export public class A {

					$doIt() {
						var classB : JN.ClassB;

					}
				}
				""";
	}

	String fileB() {
		return """
				import { A } from "p/q/E"

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

	String fileC() {
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

	String fileD() {
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

	String fileE() {
		return """
				{
					function funA() {
					}
				}

				import { B } from "p/q/F"

				export public class A {
					$b : B;
				}
				""";
	}

	String fileF() {
		return """
				import { A } from "p/q/E"

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
