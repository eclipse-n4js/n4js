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
 * Tests if the source folder can be set to {@code "."}
 */
class SrcIsProjectNoValidationPluginTest extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder src_P
	IFolder src_P_Q
	IFile manifest

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("IDE_754")
		src = configureProjectWithXtext(projectUnderTest);
		src_P = createFolder(src, "P");
		src_P_Q = createFolder(src_P, "Q");
		manifest = projectUnderTest.project.getFile("manifest.n4mf");
		setProjectAsSource();
		waitForAutoBuild
	}

	@Test
	def void testFileInSrc() throws Exception {
		val fileA = createTestFile(src_P, "A", fileContentsA);
		val fileB = createTestFile(src_P, "B", fileContentsB);
		val fileC = createTestFile(src_P, "C", fileContentsC);
		val fileD = createTestFile(src_P, "D", fileContentsD);
		val fileE = createTestFile(src_P_Q, "E", fileContentsE);
		val fileF = createTestFile(src_P_Q, "F", fileContentsF);
		assertMarkers("file A should have 3 markers", fileA, 3);
		assertMarkers("file B should have 2 markers", fileB, 2);
		assertMarkers("file C should have 2 markers", fileC, 2);
		assertMarkers("file D should have 2 markers", fileD, 2);
		assertMarkers("file E should have 6 markers", fileE, 6);
		assertMarkers("file F should have 2 markers", fileF, 2);
		assertMarkers("manifest should have 1 marker", manifest, 1);

		addPathsToNoValidate("P/D" -> null, "P/Q/*" -> null)
		assertMarkers("file D should have no markers", fileD, 0);
		assertMarkers("file E should have no markers", fileE, 0);
		assertMarkers("file F should have no markers", fileF, 0);
	}

	def void setProjectAsSource() {
		val pd = getProjectDescription;
		val srcPaths = pd.sourceContainers.filter[getSourceContainerType == SourceContainerType.SOURCE].head.pathsRaw;
		srcPaths.clear;
		srcPaths.add(".");
		pd.eResource.save(null)
		waitForAutoBuild();
	}

	def void addPathsToNoValidate(Pair<String, String>... pathToSource) {
		val pd = getProjectDescription
		val noValidates = pd.getNoValidateProjectPath
		pathToSource.forEach [
			val filters = findFiltersWithPath(noValidates, it.key, it.value)
			if (filters.empty) createNoValidatePath(pd, noValidates, it.key,
				it.value) else throw new IllegalArgumentException(it + " was already there.")
		]
		pd.eResource.save(null)
		waitForAutoBuild();
	}

	def createNoValidatePath(ProjectDescription projectDescription, Iterable<ModuleFilter> existingNoValidateFilters,
		String path, String sourceFolder) {

		val moduleFilter = if (existingNoValidateFilters.empty) {
				val mf = N4mfFactory.eINSTANCE.createModuleFilter => [
					moduleFilterType = ModuleFilterType.NO_VALIDATE
				]
				projectDescription.moduleFilters += mf
				mf
			} else {
				existingNoValidateFilters.head
			};

		moduleFilter.moduleSpecifiers += N4mfFactory.eINSTANCE.createModuleFilterSpecifier => [
			moduleSpecifierWithWildcard = path
			if (sourceFolder !== null) {
				sourcePath = sourceFolder
			}
		]
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
