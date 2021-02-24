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

import java.util.Map
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests if the source folder can be set to {@code "."}
 */
// converted from SrcIsProjectNoValidationPluginTest
class SrcIsProjectNoValidationIdeTest extends ConvertedIdeTest {

	IProject projectUnderTest
	IFolder src
	IFolder src_P
	IFolder src_P_Q
	IFile projectDescriptionFile

	@Test
	def void testFileInSrc() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"P/A" -> fileContentsA,
			"P/B" -> fileContentsB,
			"P/C" -> fileContentsC,
			"P/D" -> fileContentsD,
			"P/Q/E" -> fileContentsE,
			"P/Q/F" -> fileContentsF
		);
		startAndWaitForLspServer();

		val fileA = getFileURIFromModuleName("A");
		val fileB = getFileURIFromModuleName("B");
		val fileC = getFileURIFromModuleName("C");
		val fileD = getFileURIFromModuleName("D");
		val fileE = getFileURIFromModuleName("E");
		val fileF = getFileURIFromModuleName("F");
		val packageJsonFileURI = getPackageJsonFile().toFileURI;

		assertEquals("file A should have 5 markers", 5, getIssuesInFile(fileA).size);
		assertEquals("file B should have 5 markers", 5, getIssuesInFile(fileB).size);
		assertEquals("file C should have 2 markers", 2, getIssuesInFile(fileC).size);
		assertEquals("file D should have 2 markers", 2, getIssuesInFile(fileD).size);
		assertEquals("file E should have 5 markers", 5, getIssuesInFile(fileE).size);
		assertEquals("file F should have 5 markers", 5, getIssuesInFile(fileF).size);
		assertEquals("project description file (package.json) should have 0 marker", 0, getIssuesInFile(packageJsonFileURI).size);

		val moduleFilters = '''"moduleFilters": { "noValidate": [ "P/D", "P/Q/*" ] }''';
		changeNonOpenedFile(packageJsonFileURI,
			'''"library"''' -> '''"library", «moduleFilters»''');
		joinServerRequests();

		assertEquals("project description file (package.json) should have 2 marker", 2, getIssuesInFile(packageJsonFileURI).size);
		assertEquals("file D should have no markers", 0, getIssuesInFile(fileD).size);
		assertIssuesInFiles(
			Map.of(
				// file E should have no markers created by validation (just one from failed transpilation)
				fileE, #[],
				// file F should have no markers created by validation (just one from failed transpilation)
				fileF, #[]
			)
		);
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
