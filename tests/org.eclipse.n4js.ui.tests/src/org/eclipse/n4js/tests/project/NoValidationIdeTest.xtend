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

import java.nio.file.Files
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import static org.eclipse.n4js.utils.N4JSLanguageHelper.*
import static org.junit.Assert.*

/**
 */
// converted from NoValidationPluginTest
class NoValidationIdeTest extends ConvertedIdeTest {

	private static boolean oldOpaqueJsModules;

	@BeforeClass
	def static void setOpaqueJsModules() {
		oldOpaqueJsModules = N4JSLanguageHelper.OPAQUE_JS_MODULES;
		N4JSLanguageHelper.OPAQUE_JS_MODULES = true;
	}

	@AfterClass
	def static void restoreOpaqueJsModules() {
		N4JSLanguageHelper.OPAQUE_JS_MODULES = oldOpaqueJsModules;
	}

	@Test
	def void testFileInSrc() throws Exception {
		// setup test workspace
		testWorkspaceManager.createTestProjectOnDisk(
			"p/A" -> fileA,
			"p/B" -> fileB,
			"p/C" -> fileC,
			"p/D" -> fileD,
			"p/q/E" -> fileE,
			"p/q/F" -> fileF
		);
		startAndWaitForLspServer();

		val fileAValidated = getFileURIFromModuleName("A");
		val fileBValidated = getFileURIFromModuleName("B");
		val fileC = getFileURIFromModuleName("C");
		val fileD = getFileURIFromModuleName("D");
		val fileE = getFileURIFromModuleName("E");
		val fileF = getFileURIFromModuleName("F");

		// create a second source folder with a file p2/A.n4js
		val packageJsonFileURI = getPackageJsonFile(DEFAULT_PROJECT_NAME).toFileURI;
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(packageJsonFileURI.toPath, SourceContainerType.SOURCE, "src2");
		sendDidChangeWatchedFiles(packageJsonFileURI);
		val fileAValidatedInSrc2 = fileAValidated.parent.parent.appendSegments("src2", "p2", "A.n4js");
		createFile(fileAValidatedInSrc2, fileA);
		cleanBuildAndWait();

		// assert markers
		assertEquals("file A should have 3 markers", 3, getIssuesInFile(fileAValidated).size);
		assertEquals("file AInSrc2 should have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size);
		assertEquals("file B should have markers", 2, getIssuesInFile(fileBValidated).size);
		assertEquals("file C should have markers", 2, getIssuesInFile(fileC).size);
		assertEquals("file D should have markers", 2, getIssuesInFile(fileD).size);
		assertEquals("file E should have markers", 6, getIssuesInFile(fileE).size);
		assertEquals("file F should have markers", 2, getIssuesInFile(fileF).size);

		val moduleFiltersStep1 = '''"moduleFilters": { "noValidate": [ "p/D", "p/q/*" ] }''';
		val moduleFiltersStep2 = '''"moduleFilters": { "noValidate": [ "p/D", "p/q/*", { "module": "p2/*", "sourceContainer": "src2" } ] }''';
		changeNonOpenedFile(packageJsonFileURI,
			'''"library"''' -> '''"library", «moduleFiltersStep1»''');
		joinServerRequests();

		assertEquals("file A should still have 3 markers", 3, getIssuesInFile(fileAValidated).size);
		assertEquals("file AInSrc2 should still have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size);
		assertEquals("file B should still have markers", 2, getIssuesInFile(fileBValidated).size);
		assertEquals("file C should still have markers", 2, getIssuesInFile(fileC).size);
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size);
		assertEquals("file E should have no markers", 0, getIssuesInFile(fileE).size);
		assertEquals("file F should have no markers", 0, getIssuesInFile(fileF).size);

		changeNonOpenedFile(packageJsonFileURI, moduleFiltersStep1 -> moduleFiltersStep2);
		joinServerRequests();

		assertEquals("file A should still have 3 markers", 3, getIssuesInFile(fileAValidated).size);
		assertEquals("file AInSrc2 should still have 3 markers", 3, getIssuesInFile(fileAValidatedInSrc2).size);
		assertEquals("file B should still have markers", 2, getIssuesInFile(fileBValidated).size);
		assertEquals("file C should still have markers", 2, getIssuesInFile(fileC).size);
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size);
		assertEquals("file E should have no markers", 0, getIssuesInFile(fileE).size);
		assertEquals("file F should have no markers", 0, getIssuesInFile(fileF).size);
	}


	@Test
	def void testTypeDefinitionsShadowing() throws Exception {
		// setup test workspace
		testWorkspaceManager.createTestProjectOnDisk();

		// create a second source folder
		val packageJsonFileURI = getPackageJsonFile(DEFAULT_PROJECT_NAME).toFileURI;
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(packageJsonFileURI.toPath, SourceContainerType.EXTERNAL, "src-ext");
		// (do not send a notification, because server is not started yet)

		// create files
		val fileImpl = getProjectRoot().toFileURI.appendSegments("src-ext", "Shadowed.js");
		val fileTypeDef = getProjectRoot().toFileURI.appendSegments("src", "Shadowed.n4jsd");
		fileImpl.parent.toFile.mkdirs();
		Files.writeString(fileImpl.toPath, '''
			INVALID console.log('impl');
		''');
		fileTypeDef.parent.toFile.mkdirs();
		Files.writeString(fileTypeDef.toPath, '''
			// invalid, since 'external' modifier is missing
			export public class Shadow {}
		''');

		startAndWaitForLspServer();

		// assert markers
		assertIssues(
			"Shadowed.n4jsd" -> #[
				"(Error, [1:20 - 1:26], Only classes, interfaces, enums and functions declared as external as well as structural typed interfaces are allowed in n4jsd files.)"
			]
		);
		assertEquals("file src/js/Shadowed.js should have 0 markers", 0, getIssuesInFile(fileImpl).size);
		assertEquals("file src/n4js/Shadowed.n4jsd should have 1 marker since it's invalid", 1, getIssuesInFile(fileTypeDef).size);

		val moduleFilters = '''"moduleFilters": { "noValidate": [ "Shadowed" ] }''';
		changeNonOpenedFile(packageJsonFileURI,
			'''"library"''' -> '''"library", «moduleFilters»''');
		joinServerRequests();

		assertIssues(
			DEFAULT_PROJECT_NAME+ "/package.json" -> #[
				"(Error, [6:65 - 6:75], Module filters of type noValidate must not match N4JS modules/files.)"
			]
		);
		assertEquals("file package.json should have 1 marker since the module filter is invalid", 1, getIssuesInFile(packageJsonFileURI).size);
		assertEquals("file src/js/Shadowed.js should have 0 markers", 0, getIssuesInFile(fileImpl).size);
		assertEquals("file src/n4js/Shadowed.n4jsd should still have 0 markers since it is filtered by the invalid module filter", 0, getIssuesInFile(fileTypeDef).size);
	}

	def fileA() '''
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
	'''

	def fileB() '''
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
	'''

	def fileC() '''
		{
			function Module() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def fileD() '''
		{
			function ugly() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def fileE() '''
		{
			function funA() {
			}
		}
		
		import { B } from "p/q/F"
		
		export public class A {
			$b : B;
		}
	'''

	def fileF() '''
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
	'''
}
