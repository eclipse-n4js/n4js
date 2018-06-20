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
package org.eclipse.n4js.tests.manifest

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.n4mf.ModuleFilter
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4mfFactory
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
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
	IFile manifest

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("IDE_754")
		src = configureProjectWithXtext(projectUnderTest)
		scr_P = createFolder(src, "p");
		src_P_Q = createFolder(scr_P, "q");
		src2 = createFolder(projectUnderTest.project, "src2");
		src2_P2 = createFolder(src2, "p2");
		manifest = projectUnderTest.project.getFile("manifest.n4mf")
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
		val pd = getProjectDescription
		pd.sourceContainers.filter[sourceContainerType == SourceContainerType.SOURCE].head.pathsRaw += folderName
		pd.eResource.save(null)
		waitForAutoBuild();
	}

	def void addPathsToNoValidate(Pair<String, String>... pathToSource) {
		val pd = getProjectDescription
		val noValidates = pd.getNoValidateProjectPath
		pathToSource.forEach[
			val filters = findFiltersWithPath(noValidates, it.key, it.value)
			if(filters.empty) createNoValidatePath(pd, noValidates, it.key, it.value)
			else throw new IllegalArgumentException(it + " was already there.")
		]
		pd.eResource.save(null)
		waitForAutoBuild();
	}

	def createNoValidatePath(ProjectDescription projectDescription, Iterable<ModuleFilter> existingNoValidateFilters, String path, String sourceFolder) {
		val moduleFilter = if(existingNoValidateFilters.empty) {
			val mf = N4mfFactory.eINSTANCE.createModuleFilter => [
				moduleFilterType = ModuleFilterType.NO_VALIDATE
			]
			projectDescription.moduleFilters += mf
			mf
		} else {
			existingNoValidateFilters.head
		}
		moduleFilter.moduleSpecifiers += N4mfFactory.eINSTANCE.createModuleFilterSpecifier => [
			moduleSpecifierWithWildcard = path
			if(sourceFolder !== null) {
				sourcePath = sourceFolder
			}
		]
	}

	def removeNoValidatePath(Iterable<ModuleFilter> existingNoValidateFilters, String path) {
		existingNoValidateFilters.forEach[
			val toBeRemoved = moduleSpecifiers.filter[moduleSpecifierWithWildcard == path]
			moduleSpecifiers.removeAll(toBeRemoved)
		]
	}

	def Iterable<ModuleFilterSpecifier> findFiltersWithPath(Iterable<ModuleFilter> noValidates, String path, String sourceFolder) {
		noValidates.map[moduleSpecifiers].flatten.filter[moduleSpecifierWithWildcard == path && sourcePath == sourceFolder]
	}

	def void removePathsFromNoValidate(String... paths) {
		val pd = getProjectDescription
		val noValidates = pd.getNoValidateProjectPath
		paths.forEach[removeNoValidatePath(noValidates, it)]
		pd.eResource.save(null)
		waitForAutoBuild();
	}

	def getNoValidateProjectPath(ProjectDescription pd) {
		pd.moduleFilters.filter[moduleFilterType == ModuleFilterType.NO_VALIDATE]
	}

	def ProjectDescription getProjectDescription() {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		resource.contents.head as ProjectDescription
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
