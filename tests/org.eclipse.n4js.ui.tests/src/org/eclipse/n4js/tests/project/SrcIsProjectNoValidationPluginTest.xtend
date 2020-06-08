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

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.projectDescription.ModuleFilter
import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier
import org.eclipse.n4js.projectDescription.ModuleFilterType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.junit.Before
import org.junit.Test

/**
 * Tests if the source folder can be set to {@code "."}
 */
class SrcIsProjectNoValidationPluginTest extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder src_P
	IFolder src_P_Q
	IFile projectDescriptionFile

	@Before
	def void setUp2() {
		projectUnderTest = createJSProject("IDE_754");
		addProjectToDependencies(projectUnderTest, N4JSGlobals.N4JS_RUNTIME, "0.0.1-dummy");
		createAndRegisterDummyN4JSRuntime(projectUnderTest);
		src = configureProjectWithXtext(projectUnderTest);
		src_P = createFolder(src, "P");
		src_P_Q = createFolder(src_P, "Q");
		projectDescriptionFile = projectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON);
		waitForAutoBuild();
	}

	@Test
	def void testFileInSrc() throws Exception {
		val fileA = createTestFile(src_P, "A", fileContentsA);
		val fileB = createTestFile(src_P, "B", fileContentsB);
		val fileC = createTestFile(src_P, "C", fileContentsC);
		val fileD = createTestFile(src_P, "D", fileContentsD);
		val fileE = createTestFile(src_P_Q, "E", fileContentsE);
		val fileF = createTestFile(src_P_Q, "F", fileContentsF);
		assertMarkers("file A should have 5 markers", fileA, 5);
		assertMarkers("file B should have 5 markers", fileB, 5);
		assertMarkers("file C should have 2 markers", fileC, 2);
		assertMarkers("file D should have 2 markers", fileD, 2);
		assertMarkers("file E should have 5 markers", fileE, 5);
		assertMarkers("file F should have 5 markers", fileF, 5);
		assertMarkers("project description file (package.json) should have 0 marker", projectDescriptionFile, 0);

		addPathsToNoValidate("P/D", "P/Q/*")
		assertMarkers("project description file (package.json) should have 2 marker", projectDescriptionFile, 2);
		assertMarkers("file D should have no markers", fileD, 0);
		assertIssues("file E should have no markers created by validation (just one from failed transpilation)", fileE,
			"line 1: unable to transpile file E.n4js due to an unresolved reference in line 9 at column 7");
		assertIssues("file F should have no markers created by validation (just one from failed transpilation)", fileF,
			"line 1: unable to transpile file F.n4js due to an unresolved reference in line 5 at column 11");
	}

	def void addPathsToNoValidate(String... filterSpecifiers) {
		val packageJSON = getPackageJSONContent

		PackageJSONTestUtils.setModuleFilters(packageJSON, ModuleFilterType.NO_VALIDATE, 
			filterSpecifiers);

		packageJSON.eResource.save(null)
		waitForAutoBuild();
	}

	def removeNoValidatePath(Iterable<ModuleFilter> existingNoValidateFilters, String path) {
		existingNoValidateFilters.forEach [
			val toBeRemoved = moduleSpecifiers.filter[moduleSpecifierWithWildcard == path]
			moduleSpecifiers.removeAll(toBeRemoved)
		]
	}

	def Iterable<ModuleFilterSpecifier> findFiltersWithPath(Iterable<ModuleFilter> noValidates, String path,
		String sourceFolder) {
		noValidates.map[moduleSpecifiers].flatten.filter [
			moduleSpecifierWithWildcard == path && sourcePath == sourceFolder
		]
	}

	def void removePathsFromNoValidate(String... paths) {
		val packageJSON = getPackageJSONContent
		
		paths.forEach[ path | 
			PackageJSONTestUtils.removePathFromModuleFilter(packageJSON, ModuleFilterType.NO_VALIDATE, path)
		];
		
		// save changes to disk
		packageJSON.eResource.save(null)
		waitForAutoBuild();
	}

	def JSONObject getPackageJSONContent() {
		val uri = URI.createPlatformResourceURI(projectDescriptionFile.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val document = resource.contents.head as JSONDocument;
		return document.content as JSONObject;
	}

	def getFileContentsA() '''
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
	'''

	def getFileContentsB() '''
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
	'''

	def getFileContentsC() '''
		{
			function Module() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def getFileContentsD() '''
		{
			function ugly() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def getFileContentsE() '''
		{
			function funA() {
			}
		}
		
		import { B } from "src/P/Q/F"
		
		export public class A {
			$b : B;
		}
	'''

	def getFileContentsF() '''
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
	'''
}
