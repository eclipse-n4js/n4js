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
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.junit.Before
import org.junit.Test

/**
 */
class NoValidationPluginTest extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder scr_P
	IFolder src2
	IFolder src2_P2
	IFolder src_P_Q
	IFolder src_external
	IFile packageJson

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("IDE_754")
		src = configureProjectWithXtext(projectUnderTest)
		scr_P = createFolder(src, "p");
		src_P_Q = createFolder(scr_P, "q");
		src2 = createFolder(projectUnderTest.project, "src2");
		src2_P2 = createFolder(src2, "p2");
		packageJson = projectUnderTest.project.getFile(IN4JSProject.PACKAGE_JSON)
		src_external = projectUnderTest.project.getFolder("src-external");
		src_external.create(false, true, null)
		addFolderAsSource(src2.name)
		waitForAutoBuild
	}

	@Test
	def void testFileInSrc() throws Exception {
		val fileAValidated = createTestFile(scr_P, "A", fileA);
		val fileAValidatedInSrc2 = createTestFile(src2_P2, "A", fileA);
		val fileBValidated = createTestFile(scr_P, "B", fileB);
		val fileC = createTestFile(scr_P, "C", fileC);
		val fileD = createTestFile(scr_P, "D", fileD);
		val fileE = createTestFile(src_P_Q, "E", fileE);
		val fileF = createTestFile(src_P_Q, "F", fileF);
		assertMarkers("file A should have 3 markers", fileAValidated, 3);
		assertMarkers("file AInSrc should have 3 markers", fileAValidatedInSrc2, 3);
		assertMarkers("file B should have markers", fileBValidated, 2);
		assertMarkers("file C should have markers", fileC, 2);
		assertMarkers("file D should have markers", fileD, 2);
		assertMarkers("file E should have markers", fileE, 6);
		assertMarkers("file F should have markers", fileF, 2);

		addPathsToNoValidate("p/D" -> null, "p/q/*" -> null)
		assertMarkers("file D should have no markers", fileD, 0);
		assertMarkers("file E should have no markers", fileE, 0);
		assertMarkers("file F should have no markers", fileF, 0);
		assertMarkers("file AInSrc should have still 3 markers", fileAValidatedInSrc2, 3);
		addPathsToNoValidate("p2/*" -> "src2")
		assertMarkers("file AInSrc2 should have no markers", fileAValidatedInSrc2, 0);
	}

	def void addFolderAsSource(String folderName) {
		val packageJson = getPackageJSONContent();
		
		PackageJSONTestUtils.addSourceContainerSpecifier(packageJson, SourceContainerType.SOURCE, folderName);
		
		packageJson.eResource.save(null)
		waitForAutoBuild();
	}

	def void addPathsToNoValidate(Pair<String, String>... pathToSource) {
		val packageJson = getPackageJSONContent();

		pathToSource.forEach[ filter | 
			PackageJSONTestUtils.addModuleFilter(packageJson, ModuleFilterType.NO_VALIDATE, 
				filter.key, filter.value);
		]


		packageJson.eResource.save(null)
		waitForAutoBuild();
	}

	def JSONObject getPackageJSONContent() {
		val uri = URI.createPlatformResourceURI(packageJson.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val document = resource.contents.head as JSONDocument;
		return document.content as JSONObject;
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
