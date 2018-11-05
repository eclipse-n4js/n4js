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
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.ICoreRunnable
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.junit.Test
import org.eclipse.n4js.N4JSGlobals

import static org.junit.Assert.*
import org.eclipse.n4js.tests.util.EclipseUIUtils
import org.eclipse.xtext.util.StringInputStream
import org.eclipse.core.runtime.CoreException
import com.google.inject.Inject
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.junit.Before
import org.junit.Assume

/**
 * Test builder / editor behavior with multiple files and cyclic dependencies.
 */
class CanLoadFromDescriptionCyclicPluginUITest extends AbstractCanLoadFromDescriptionTest {
	@Inject CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;
	
	/*
	 * X <- Y <- A <- B <- C <- D
	 *           |              ^
	 *           ----------------
	 * P -> Q
	 * ^    | 
	 * ------
	 */

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
	
	private val sourceP = '''
		import {q} from "m/Q";
		export public var p = '';
		
		var p2: string = q;
		
		p2; // avoid unused warning
	''';
	
	private val sourceQ = '''
		import {p} from "m/P";
		export public var q = p;
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
		
	// change the inferred type of variable p to int
	private val sourceP_modify_p = sourceP.replace(
		"p = ''",
		'p = 1;');

	private var IProject project;
	private var IFolder srcFolder;
	private var IFolder srcFolderM;
	private var IFile fileX;
	private var IFile fileY;
	private var IFile fileA;
	private var IFile fileB;
	private var IFile fileC;
	private var IFile fileD;
	private var IFile fileP;
	private var IFile fileQ;

	def private void prepare(String projectName) {
		project = createJSProject(projectName)
		val ICoreRunnable runnable = [
			srcFolder = configureProjectWithXtext(project)
			val projectDescriptionFile = project.getFile(N4JSGlobals.PACKAGE_JSON);
			assertMarkers("project description file (package.json) should have no errors", projectDescriptionFile, 0)
	
			srcFolderM = srcFolder.getFolder("m")
			srcFolderM.create(true, true, null)
	
			fileX = createTestFile(srcFolderM, "X", sourceX)
			fileY = createTestFile(srcFolderM, "Y", sourceY)
			fileA = createTestFile(srcFolderM, "A", sourceA)
			fileB = createTestFile(srcFolderM, "B", sourceB)
			fileC = createTestFile(srcFolderM, "C", sourceC)
			fileD = createTestFile(srcFolderM, "D", sourceD)
			fileP = createTestFile(srcFolderM, "P", sourceP)
			fileQ = createTestFile(srcFolderM, "Q", sourceQ)
		]
		ResourcesPlugin.workspace.run(runnable, new NullProgressMonitor)

		cleanBuild
		waitForAutoBuild

		assertMarkers("file X should have no errors", fileX, 0)
		assertMarkers("file Y should have no errors", fileY, 0)
		assertMarkers("file A should have no errors", fileA, 0)
		assertMarkers("file B should have no errors", fileB, 0)
		assertMarkers("file C should have no errors", fileC, 0)
		assertMarkers("file D should have no errors", fileD, 0)
		assertMarkers("file P should have no errors", fileP, 0)
		assertMarkers("file Q should have no errors", fileQ, 0)
	}
	
	/* Same as the super impl but without the super expensive waitForAutobuild */	
	override protected IFile doCreateTestFile(IFolder folder, String fullName, CharSequence content) throws CoreException {
		return folder.getFile(fullName) => [
			create(new StringInputStream(content.toString()), true, new NullProgressMonitor())
		]
	}

	@Before
	def void before() {
		Assume.assumeTrue(!canLoadFromDescriptionHelper.isLoadFromSourceDeactivated);
	}

	@Test
	def void test_inEditor_simple() {
		prepare("TestInEditorSimple")

		val page = EclipseUIUtils.getActivePage()
		val editorA = openAndGetXtextEditor(fileA, page)
		val errorsA = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should not have any errors", #[], errorsA)

		setDocumentContent("file A", fileA, editorA, sourceA_modify_a)
		waitForUpdateEditorJob

		val errorsA_modified = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should now have exactly 1 expected error", #[
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorSimple/src/m/A.n4js line : 10 column : 33)"
		], errorsA_modified.map[toString])

		setDocumentContent("file A", fileA, editorA, sourceA)
		waitForUpdateEditorJob

		val errorsA_backToOriginal = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should no longer have any errors", #[], errorsA_backToOriginal)
	}

	@Test
	def void test_inEditor_throughXY() {
		prepare("TestInEditorThroughXY");
		
		val page = EclipseUIUtils.getActivePage()
		val editorA = openAndGetXtextEditor(fileA, page)
		val errorsA = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should not have any errors", #[], errorsA)

		changeTestFile(fileX, sourceX_modify_x)
		waitForAutoBuild
		waitForUpdateEditorJob

		val errorsA_modified = getEditorValidationErrors(editorA)
		assertEquals("editor for file A should now have exactly 1 expected error", #[
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorThroughXY/src/m/A.n4js line : 10 column : 33)"
		], errorsA_modified.map[toString])

		changeTestFile(fileX, sourceX)
		waitForAutoBuild
		waitForUpdateEditorJob

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

	@Test
	def void test_builder_changeP() {
		prepare("TestBuilderChangeP")

		changeTestFile(fileP, sourceP_modify_p)
		waitForAutoBuild

		assertMarkers("file P should now have exactly 1 error", fileP, 1)
		assertIssues(fileP, "line 4: int is not a subtype of string.")

		changeTestFile(fileP, sourceP)
		waitForAutoBuild

		assertMarkers("file P should no longer have any errors", fileP, 0)
	}
	
	@Test
	def void test_inEditor_changeP() {
		prepare("TestInEditorChangeP")

		val page = EclipseUIUtils.getActivePage()
		val editorP = openAndGetXtextEditor(fileP, page)
		val errorsP = getEditorValidationErrors(editorP)
		assertEquals("editor for file P should not have any errors", #[], errorsP)
		val expectedFromSource = #{
			URI.createPlatformResourceURI('TestInEditorChangeP/src/m/P.n4js', true),
			URI.createPlatformResourceURI('TestInEditorChangeP/src/m/Q.n4js', true)
		}
		editorP.assertFromSource(expectedFromSource)

		setDocumentContent("file P", fileP, editorP, sourceP_modify_p)
		waitForUpdateEditorJob
		editorP.assertFromSource(expectedFromSource)

		val errorsP_modified = getEditorValidationErrors(editorP)
		assertEquals("editor for file P should now have exactly 1 expected error", #[
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorChangeP/src/m/P.n4js line : 4 column : 18)"
		].join('\n'), errorsP_modified.map[toString].join('\n'))

		setDocumentContent("file P", fileA, editorP, sourceP)
		waitForUpdateEditorJob

		val errorsP_backToOriginal = getEditorValidationErrors(editorP)
		assertEquals("editor for file P should no longer have any errors", #[], errorsP_backToOriginal)
	}

}
