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

import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.n4mf.ModuleFilter
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4mfFactory
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SourceFragmentType
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.junit.Before
import org.junit.Test

/**
 * Tests if the source folder can be set to {@code "."}
 */
class NoValidationPluginTestSrcIsProject extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder src_P
	IFolder src_P_JuergensHacks
	IFile manifest

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("IDE_754")
		src = configureProjectWithXtext(projectUnderTest);
		src_P = createFolder(src, "p");
		src_P_JuergensHacks = createFolder(src_P, "juergensHacks");
		manifest = projectUnderTest.project.getFile("manifest.n4mf");
		setProjectAsSource();
		waitForAutoBuild
	}

	@Test
	def void testFileInSrc() throws Exception {
		val fileAValidated = createTestFile(src_P, "A", fileA);
		val fileBValidated = createTestFile(src_P, "B", fileB);
		val fileMyAlreadyAsModuleHack = createTestFile(src_P, "myAlreadyAsModuleHack", fileMyAlreadyAsModuleHack);
		val fileWolfgangsUglyHack = createTestFile(src_P, "wolfgangsUglyHack", fileWolfgangsUglyHack);
		val fileJuergenA = createTestFile(src_P_JuergensHacks, "A", fileJuergenA);
		val fileJuergenB = createTestFile(src_P_JuergensHacks, "B", fileJuergenB);
		assertMarkers("file A should have 3 markers", fileAValidated, 3);
		assertMarkers("file B should have markers", fileBValidated, 2);
		assertMarkers("file MyAlreadyAsModuleHack should have markers", fileMyAlreadyAsModuleHack, 2);
		assertMarkers("file WolfgangsUglyHack should have markers", fileWolfgangsUglyHack, 2);
		assertMarkers("file JuergenA should have markers", fileJuergenA, 6);
		assertMarkers("file JuergenB should have markers", fileJuergenB, 2);

		addPathsToNoValidate("p/wolfgangsUglyHack" -> null, "p/juergensHacks/*" -> null)
		assertMarkers("file WolfgangsUglyHack should have no markers", fileWolfgangsUglyHack, 0);
		assertMarkers("file JuergensA should have no markers", fileJuergenA, 0);
		assertMarkers("file JuergenB should have no markers", fileJuergenB, 0);
	}

	def void setProjectAsSource() {
		val pd = getProjectDescription;
		val srcPaths = pd.sourceFragment.filter[sourceFragmentType == SourceFragmentType.SOURCE].head.paths;
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

	def fileA() '''
		{
			function funA() {
			}
		}
		
		import * as JN from "src/p/juergensHacks/B"
		
		export public class A {
		
			$doIt() {
				var classB : JN.ClassB;
		
			}
		}
	'''

	def fileB() '''
		import { A } from "src/p/juergensHacks/A"
		
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

	def fileMyAlreadyAsModuleHack() '''
		{
			function Module() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def fileWolfgangsUglyHack() '''
		{
			function ugly() {
			}
		}
		
		export public class Hack {
			$hack() {
		
			}
		}
	'''

	def fileJuergenA() '''
		{
			function funA() {
			}
		}
		
		import { B } from "src/p/juergensHacks/B"
		
		export public class A {
			$b : B;
		}
	'''

	def fileJuergenB() '''
		import { A } from "src/p/juergensHacks/A"
		
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
