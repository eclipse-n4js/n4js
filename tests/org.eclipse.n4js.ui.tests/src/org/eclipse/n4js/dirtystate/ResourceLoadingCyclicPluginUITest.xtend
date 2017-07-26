/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dirtystate

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import org.junit.Test

/**
 *
 */
class ResourceLoadingCyclicPluginUITest extends AbstractBuilderParticipantTest {

	// X <- Y <- A <- B <- C <- D
	//            ------------->

	private val sourceX = '''
		export public var x = "hello";
		export public var x2 = "hello";
		export public var x3 = "hello";
	''';

	private val sourceY = '''
		import {x} from "m/X";
		import {x2} from "m/X";
		import {x3} from "m/X";
		export public var y = x;
		export public var y2 = x2;
		var y3 = x3; // non-exported var, so change of y3's type won't affect Y's TModule
		y3; // avoid unused variable warning
	''';

	private val sourceA = '''
		import {y} from "m/Y";
		import {y2} from "m/Y";

		import {d} from "m/D";

		export public var a = y;
		var a2 = y2; // non-exported var, so change of a2's type won't affect A's TModule
		a2; // avoid unused variable warning
		
		export public var end: string = d;
	''';

	private val sourceB = '''
		import {a} from "m/A";
		export public var b = a;
	''';

	private val sourceC = '''
		import {b} from "m/B";
		export public var c = b;
	''';

	private val sourceD = '''
		import {c} from "m/C";
		export public var d = c;
	''';

	// change inferred type of 'x' from string to number
	private val sourceX_modify_x = sourceX.replace(
		'x = "hello";',
		'x = 42;');

	// change inferred type of 'x2' from string to number
	private val sourceX_modify_x2 = sourceX.replace(
		'x2 = "hello";',
		'x2 = 42;');

	// change inferred type of 'x3' from string to number
	private val sourceX_modify_x3 = sourceX.replace(
		'x3 = "hello";',
		'x3 = 42;');

	// change inferred type of 'a' from string to number
	// (keep reference to 'y' to avoid unused import warning)
	private val sourceA_modify_a = sourceA.replace(
		'a = y;',
		'a = 42;y;');

	private var IProject project;
	private var IFolder srcFolder;
	private var IFolder srcFolderM;
	private var IFile fileX;
	private var IFile fileY;
	private var IFile fileA;
	private var IFile fileB;
	private var IFile fileC;
	private var IFile fileD;

	def private void prepare(String projectName) {
		project = createJSProject(projectName)
		srcFolder = configureProjectWithXtext(project)
		val manifest = project.getFile("manifest.n4mf")
		assertMarkers("manifest should have no errors", manifest, 0)

		srcFolderM = srcFolder.getFolder("m")
		srcFolderM.create(true, true, null)

		fileX = createTestFile(srcFolderM, "X", sourceX)
		fileY = createTestFile(srcFolderM, "Y", sourceY)
		fileA = createTestFile(srcFolderM, "A", sourceA)
		fileB = createTestFile(srcFolderM, "B", sourceB)
		fileC = createTestFile(srcFolderM, "C", sourceC)
		fileD = createTestFile(srcFolderM, "D", sourceD)

		cleanBuild
		waitForAutoBuild

		assertMarkers("file X should have no errors", fileX, 0)
		assertMarkers("file Y should have no errors", fileY, 0)
		assertMarkers("file A should have no errors", fileA, 0)
		assertMarkers("file B should have no errors", fileB, 0)
		assertMarkers("file C should have no errors", fileC, 0)
		assertMarkers("file D should have no errors", fileD, 0)
	}

	@Test
	def void test_inEditor_simple() {
		prepare("TestInEditorSimple")

		val page = getActivePage()
		val editorA = openAndGetXtextEditor(fileA, page)
		val errorsA = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should not have any errors", #[], errorsA)

		setDocumentContent("file A", fileA, editorA, sourceA_modify_a)
		val errorsA_modified = getEditorValidationErrors(editorA)

		assertEquals("editor for file A should now have exactly 1 error", 1, errorsA_modified.size)
		assertEquals("editor for file A should now have the expected error",
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorSimple/src/m/A.n4js line : 10 column : 33)",
			errorsA_modified.head.toString)

		setDocumentContent("file A", fileA, editorA, sourceA)
		val errorsA_backToOriginal = getEditorValidationErrors(editorA)

		assertEquals("editor for file A should no longer have any errors", #[], errorsA_backToOriginal)
	}

	@Test
	def void test_builder_simple() {
		prepare("TestBuilderSimple")

		changeTestFile(fileA, sourceA_modify_a)
		waitForAutoBuild

		assertMarkers("file A should now have exactly 1 error", fileA, 1)
		assertIssues(fileA, "line 10: int is not a subtype of string.")

		changeTestFile(fileA, sourceA)
		waitForAutoBuild

		assertMarkers("file A should no longer have any errors", fileA, 0)
	}

	/** Change A indirectly through a change in X that is forwarded through Y. */
	@Test
	def void test_builder_throughXY() {
		prepare("TestBuilderThroughXY")

		changeTestFile(fileX, sourceX_modify_x)
		waitForAutoBuild

		assertMarkers("file A should now have exactly 1 error", fileA, 1)
		assertIssues(fileA, "line 10: int is not a subtype of string.")

		changeTestFile(fileX, sourceX)
		waitForAutoBuild

		assertMarkers("file A should no longer have any errors", fileA, 0)
	}

	@Test
	def void test_builder_stopsInY() {
		prepare("TestBuilderStopsInY")

		val outputY = getOutputFileForTestFile(fileY)
		val outputA = getOutputFileForTestFile(fileA)

		val stampY = outputY.modificationStamp
		val stampA = outputA.modificationStamp

		changeTestFile(fileX, sourceX_modify_x3)
		waitForAutoBuild

		val changedY = outputY.modificationStamp !== stampY;
		val unchangedA = outputA.modificationStamp === stampA;
		assertTrue("output file of file Y should have been rebuilt", changedY);
		assertTrue("output file of file A should NOT have been rebuilt", unchangedA);
	}

	@Test
	def void test_builder_stopsInA() {
		prepare("TestBuilderStopsInA")

		val outputY = getOutputFileForTestFile(fileY)
		val outputA = getOutputFileForTestFile(fileA)
		val outputB = getOutputFileForTestFile(fileB)
		val outputC = getOutputFileForTestFile(fileC)
		val outputD = getOutputFileForTestFile(fileD)

		val stampY = outputY.modificationStamp
		val stampA = outputA.modificationStamp
		val stampB = outputB.modificationStamp
		val stampC = outputC.modificationStamp
		val stampD = outputD.modificationStamp

		changeTestFile(fileX, sourceX_modify_x2)
		waitForAutoBuild

		val changedY = outputY.modificationStamp !== stampY;
		val changedA = outputA.modificationStamp !== stampA;
		val unchangedB = outputB.modificationStamp === stampB;
		val unchangedC = outputC.modificationStamp === stampC;
		val unchangedD = outputD.modificationStamp === stampD;
		assertTrue("output file of file Y should have been rebuilt", changedY);
		assertTrue("output file of file A should have been rebuilt", changedA);
		assertTrue("output file of file B should NOT have been rebuilt", unchangedB);
		assertTrue("output file of file C should NOT have been rebuilt", unchangedC);
		assertTrue("output file of file D should NOT have been rebuilt", unchangedD);
	}

	// FIXME GH-66 add test: modify X while an editor for A is open


	// ======================================================================
	// some utility methods

	def private static IFile getOutputFileForTestFile(IFile file) {
		val name = file.name;
		val idx = name.lastIndexOf('.');
		val baseName = if(idx>=0) name.substring(0, idx) else name;
		val project = file.project;
		return project.getFile(
			"src-gen/"
			+ N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS
			+ "/"
			+ project.name
			+ "/m/"
			+ baseName + ".js"
		);
	}
}
