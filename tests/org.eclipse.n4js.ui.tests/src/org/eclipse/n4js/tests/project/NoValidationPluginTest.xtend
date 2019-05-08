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

import java.util.List
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.projectDescription.ModuleFilterType
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.junit.Test

/**
 */
class NoValidationPluginTest extends AbstractBuilderParticipantTest {
	@Test
	def void testFileInSrc() throws Exception {
		// setup test workspace
		val IProject projectUnderTest = createJSProject("NoValidationPluginTest")
		val IFolder src = configureProjectWithXtext(projectUnderTest)
		val IFolder scr_P = createFolder(src, "p");
		val IFolder src_P_Q = createFolder(scr_P, "q");
		val IFolder src2 = createFolder(projectUnderTest.project, "src2");
		val IFolder src2_P2 = createFolder(src2, "p2");
		val IFile packageJson = projectUnderTest.project.getFile(IN4JSProject.PACKAGE_JSON)
		val IFolder src_external = projectUnderTest.project.getFolder("src-external");
		src_external.create(false, true, null)
		// 'src' is a source folder by default (see #createJSProject)
		addFolderAsSource(packageJson, src2.name)
		
		waitForAutoBuild
		
		// create files
		val fileAValidated = createTestFile(scr_P, "A", fileA);
		val fileAValidatedInSrc2 = createTestFile(src2_P2, "A", fileA);
		val fileBValidated = createTestFile(scr_P, "B", fileB);
		val fileC = createTestFile(scr_P, "C", fileC);
		val fileD = createTestFile(scr_P, "D", fileD);
		val fileE = createTestFile(src_P_Q, "E", fileE);
		val fileF = createTestFile(src_P_Q, "F", fileF);
		
		// assert markers
		assertMarkers("file A should have 3 markers", fileAValidated, 3);
		assertMarkers("file AInSrc should have 3 markers", fileAValidatedInSrc2, 3);
		assertMarkers("file B should have markers", fileBValidated, 2);
		assertMarkers("file C should have markers", fileC, 2);
		assertMarkers("file D should have markers", fileD, 2);
		assertMarkers("file E should have markers", fileE, 6);
		assertMarkers("file F should have markers", fileF, 2);

		addPathsToNoValidate(packageJson, "p/D" -> null, "p/q/*" -> null)
		assertMarkers("file D should have no markers", fileD, 0);
		assertMarkers("file E should have no markers", fileE, 0);
		assertMarkers("file F should have no markers", fileF, 0);
		assertMarkers("file AInSrc should have still 3 markers", fileAValidatedInSrc2, 3);
		addPathsToNoValidate(packageJson, "p2/*" -> "src2")
		assertMarkers("file AInSrc2 should have no markers", fileAValidatedInSrc2, 0);
	}
	
	@Test
	def void testTypeDefinitionsShadowing() throws Exception {
		// setup test workspace
		val IProject projectUnderTest = createJSProject("NoValidationPluginTest", "src", "src-gen", [withDependency(N4JSGlobals.N4JS_RUNTIME)]);
		createAndRegisterDummyN4JSRuntime(projectUnderTest);
		val IFolder src = configureProjectWithXtext(projectUnderTest)
		val IFolder scr_js = createFolder(src, "js");
		val IFolder src_n4js = createFolder(src, "n4js");
		val IFile packageJson = projectUnderTest.project.getFile(IN4JSProject.PACKAGE_JSON)

		setSourceContainers(packageJson, SourceContainerType.SOURCE, #["src/n4js"]);
		setSourceContainers(packageJson, SourceContainerType.EXTERNAL, #["src/js"]);
		
		waitForAutoBuild();
		
		// create files
		val fileImpl = createTestJSFile(scr_js, "Shadowed", "INVALID console.log('impl');");
		val fileTypeDef = createTestN4JSDFile(src_n4js, "Shadowed", "// invalid, since 'external' modifier is missing \n export public class Shadow {}");

		waitForAutoBuild();

		// assert markers
		assertMarkers("file src/js/Shadowed.js should have 0 markers", fileImpl, 0);
		assertMarkers("file src/n4js/Shadowed.n4jsd should have 2 markers since it's invalid", fileTypeDef, 1);
		
		addPathsToNoValidate(packageJson, "Shadowed" -> null);
		
		waitForAutoBuild();

		assertIssues(packageJson, "line 22: Module filters of type noValidate must not match N4JS modules/files.");
		assertMarkers("file package.json should have 1 marker since the module filter is invalid", packageJson, 1);
		assertMarkers("file src/js/Shadowed.js should have 0 markers", fileImpl, 0);
		assertMarkers("file src/n4js/Shadowed.n4jsd should still have 0 markers since it is filtered by the invalid module filter", fileTypeDef, 0);
	}
	
	def void setSourceContainers(IFile packageJson, SourceContainerType type, List<String> sourceContainerPaths) {
		val packageJsonContents = getPackageJSONContent(packageJson);
		
		PackageJSONTestUtils.setSourceContainerSpecifiers(packageJsonContents, type, sourceContainerPaths);
		
		packageJsonContents.eResource.save(null)
		waitForAutoBuild();
	}
	
	def void addFolderAsSource(IFile packageJson, String folderName) {
		val packageJsonContents = getPackageJSONContent(packageJson);
		
		PackageJSONTestUtils.addSourceContainerSpecifier(packageJsonContents, SourceContainerType.SOURCE, folderName);
		
		packageJsonContents.eResource.save(null)
		waitForAutoBuild();
	}

	def void addPathsToNoValidate(IFile packageJson, Pair<String, String>... pathToSource) {
		val packageJsonContents = getPackageJSONContent(packageJson);

		pathToSource.forEach[ filter | 
			PackageJSONTestUtils.addModuleFilter(packageJsonContents, ModuleFilterType.NO_VALIDATE, 
				filter.key, filter.value);
		]


		packageJsonContents.eResource.save(null)
		waitForAutoBuild();
	}

	def JSONObject getPackageJSONContent(IFile packageJson) {
		val uri = URI.createPlatformResourceURI(packageJson.fullPath.toString, true);
		val rs = getResourceSet(packageJson.project);
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
