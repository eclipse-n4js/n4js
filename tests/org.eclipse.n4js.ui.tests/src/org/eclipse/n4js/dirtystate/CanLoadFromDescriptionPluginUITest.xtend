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
import org.eclipse.ui.PlatformUI
import org.junit.Test

/**
 * 
 */
class CanLoadFromDescriptionPluginUITest extends AbstractCanLoadFromDescriptionTest {
	/*
	 * A1 -> B1 -> C -> D
	 * |           ^
	 * ----> B2 -----
	 *       ^
	 * A2 ----
	 * |
	 * ----> P -> Q
	 *       ^    |
	 *       ------ 
	 */
	 
	private val sourceA1 = '''
		import {b1} from "m/B1";
		import {b2} from "m/B2";
		
		var a1: string = b1;
		var a2: string = b2;
		a1; a2; // avoid unused variable warning
	''';

	private val sourceA2 = '''
		import {b2} from "m/B2";
		import {p, p2} from "m/P";
		
		var a2: string = b2;
		var a2p: string = p;
		var a2q: string = p2;
		a2; a2p; a2q; // avoid unused variable warning
	''';
	
	private val sourceB1 = '''
		import {c1} from "m/C";
		
		export public var b1 = c1;
	''';

	private val sourceB2 = '''
		import {c2} from "m/C";
				
		export public var b2 = c2;
	''';
	
	private val sourceC = '''
		import {d} from "m/D";
		
		export public var c1 = '';
		export public var c2 = d;
	''';
	
	private val sourceD = '''
		export public var d = '';
	''';
	
	private val sourceP = '''
		import {q} from "m/Q";
		export public var p = '';
		
		export public var p2 = q;
	''';
	
	private val sourceQ = '''
		import {p} from "m/P";
		export public var q = p;
	''';
	
	private val sourceB1_modify_b1 = sourceB1.replace(
		"b1 = c1",
		'b1 = c1 || 1');
	
	private val sourceC_modify_c1 = sourceC.replace(
		"c1 = ''",
		'c1 = 1');
		
	private val sourceD_modify_d = sourceD.replace(
		"d = ''",
		'd = 1');
		
	private val sourceP_modify_p = sourceP.replace(
		"p = ''",
		'p = 2');
		
	private val sourceQ_modify_q = sourceQ.replace(
		"q = p",
		'q = p || 1');
	 
	private var IProject project;
	private var IFolder srcFolder;
	private var IFolder srcFolderM;
	private var IFile fileA1;
	private var IFile fileA2;
	private var IFile fileB1;
	private var IFile fileB2;
	private var IFile fileC;
	private var IFile fileD;
	private var IFile fileP;
	private var IFile fileQ;

	def private void prepare(String projectName) {
		project = createJSProject(projectName)
		val ICoreRunnable runnable = [
			srcFolder = configureProjectWithXtext(project)
			val manifest = project.getFile("manifest.n4mf")
			assertMarkers("manifest should have no errors", manifest, 0)
	
			srcFolderM = srcFolder.getFolder("m")
			srcFolderM.create(true, true, null)
	
			fileA1 = createTestFile(srcFolderM, "A1", sourceA1)
			fileA2 = createTestFile(srcFolderM, "A2", sourceA2)
			fileB1 = createTestFile(srcFolderM, "B1", sourceB1)
			fileB2 = createTestFile(srcFolderM, "B2", sourceB2)
			fileC = createTestFile(srcFolderM, "C", sourceC)
			fileD = createTestFile(srcFolderM, "D", sourceD)
			fileP = createTestFile(srcFolderM, "P", sourceP)
			fileQ = createTestFile(srcFolderM, "Q", sourceQ)
		]
		ResourcesPlugin.workspace.run(runnable, new NullProgressMonitor)

		cleanBuild
		waitForAutoBuild

		assertMarkers("file A1 should have no errors", fileA1, 0)
		assertMarkers("file A2 should have no errors", fileA2, 0)
		assertMarkers("file B1 should have no errors", fileB1, 0)
		assertMarkers("file B2 should have no errors", fileB2, 0)
		assertMarkers("file C should have no errors", fileC, 0)
	}

	@Test
	def void test_inEditor_allFromIndex() {
		prepare("TestInEditorSimple")

		val page = getActivePage()
		val editorA1 = openAndGetXtextEditor(fileA1, page)
		val errorsA1 = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A should not have any errors", #[], errorsA1)
		editorA1.assertAllFromIndex

		setDocumentContent("file A1", fileA1, editorA1, sourceA1 + ' ')
		waitForUpdateEditorJob

		val errorsA1_modified = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A should not have any errors", #[], errorsA1_modified)
		editorA1.assertAllFromIndex
	}
	
	@Test
	def void test_inEditor_a1_c() {
		prepare("TestInEditorA1_C")

		val page = getActivePage()
		val editorA1 = openAndGetXtextEditor(fileA1, page)
		val editorC = openAndGetXtextEditor(fileC, page)
		val errorsA1 = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A should not have any errors", #[], errorsA1)
		editorA1.assertAllFromIndex

		setDocumentContent("file C", fileC, editorC, sourceC_modify_c1)
		waitForUpdateEditorJob

		val errorsA1_modified = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A1 should now have exactly 1 expected error", #[
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorA1_C/src/m/A1.n4js line : 4 column : 18)"
		].join('\n'), errorsA1_modified.map[toString].join('\n'))
		
		val expectedFromSource = #{
			URI.createPlatformResourceURI('TestInEditorA1_C/src/m/A1.n4js', true),
			URI.createPlatformResourceURI('TestInEditorA1_C/src/m/B1.n4js', true),
			URI.createPlatformResourceURI('TestInEditorA1_C/src/m/B2.n4js', true)
			// we do not load C from source since the entry in the index is up to date
		}
		editorA1.assertFromSource(expectedFromSource)
	}
	
	@Test
	def void test_inEditor_a2_d() {
		prepare("TestInEditorA2_D")

		val page = getActivePage()
		val editorA2 = openAndGetXtextEditor(fileA2, page)
		val editorD = openAndGetXtextEditor(fileD, page)
		val errorsA2 = getEditorValidationErrors(editorA2)
		assertEquals("editor for file A should not have any errors", #[], errorsA2)
		editorA2.assertAllFromIndex
		
		setDocumentContent("file D", fileD, editorD, sourceD_modify_d)
		waitForUpdateEditorJob

		val errorsA2_modified = getEditorValidationErrors(editorA2)
		assertEquals("editor for file A2 should now have exactly 1 expected error", #[
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorA2_D/src/m/A2.n4js line : 4 column : 18)"
		].join('\n'), errorsA2_modified.map[toString].join('\n'))
		
		val expectedFromSource = #{
			URI.createPlatformResourceURI('TestInEditorA2_D/src/m/A2.n4js', true),
			URI.createPlatformResourceURI('TestInEditorA2_D/src/m/B2.n4js', true),
			URI.createPlatformResourceURI('TestInEditorA2_D/src/m/C.n4js', true)
			// we do not load D from source since the entry in the index is up to date
		}
		editorA2.assertFromSource(expectedFromSource)

		// We keep the resources from source as is, even though from index would be sufficient when we reset the
		// fileD		
		editorD.close(false)
		editorA2.assertFromSource(expectedFromSource)
	}
	
	@Test
	def void test_inEditor_a1_b1() {
		prepare("TestInEditorA1_B1")

		val page = getActivePage()
		val editorA1 = openAndGetXtextEditor(fileA1, page)
		val editorB1 = openAndGetXtextEditor(fileB1, page)
		val errorsA1 = getEditorValidationErrors(editorA1)
		val errorsB1 = getEditorValidationErrors(editorB1)
		assertEquals("editor for file A1 should not have any errors", #[], errorsA1)
		assertEquals("editor for file B1 should not have any errors", #[], errorsB1)
		editorA1.assertAllFromIndex

		setDocumentContent("file B1", fileB1, editorB1, sourceB1_modify_b1)
		waitForUpdateEditorJob
		
		val errorsB1_modified = getEditorValidationErrors(editorB1)
		assertEquals("editor for file B1 should not have any errors", #[], errorsB1_modified)

		val errorsA1_modified = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A1 should now have exactly 1 expected error", #[
			"ERROR:union{int,string} is not a subtype of string. (platform:/resource/TestInEditorA1_B1/src/m/A1.n4js line : 4 column : 18)"
		].join('\n'), errorsA1_modified.map[toString].join('\n'))
		
		editorA1.assertAllFromIndex
	}
	
	// FIXME: This is a poor fix for a race and insufficient cancellation handling
	private def void withoutOutline(()=>void procedure) {
		val activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activePage.hideView(activePage.findViewReference('org.eclipse.ui.views.ContentOutline'));	
		try {
			procedure.apply;
		} finally {
			activePage.showView('org.eclipse.ui.views.ContentOutline');
		}
	}
	
	@Test
	def void test_inEditor_a2_p() {
		withoutOutline [|
			prepare("TestInEditorA2_P")

			val page = getActivePage()
			val editorA2 = openAndGetXtextEditor(fileA2, page)
			val editorP = openAndGetXtextEditor(fileP, page)
			val errorsA2 = getEditorValidationErrors(editorA2)
			assertEquals("editor for file A should not have any errors", #[], errorsA2)
			editorA2.assertAllFromIndex
			
			setDocumentContent("file P", fileP, editorP, sourceP_modify_p)
			waitForUpdateEditorJob
	
			val errorsA2_modified = getEditorValidationErrors(editorA2)
			assertEquals("editor for file A2 should now have exactly 2 expected errors", #[
				"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorA2_P/src/m/A2.n4js line : 5 column : 19)",
				"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorA2_P/src/m/A2.n4js line : 6 column : 19)"
			].join('\n'), errorsA2_modified.map[toString].join('\n'))
			
			val expectedFromSource = #{
				URI.createPlatformResourceURI('TestInEditorA2_P/src/m/A2.n4js', true),
				URI.createPlatformResourceURI('TestInEditorA2_P/src/m/P.n4js', true),
				URI.createPlatformResourceURI('TestInEditorA2_P/src/m/Q.n4js', true)
			}
			editorA2.assertFromSource(expectedFromSource)
		]
	}
	
	@Test
	def void test_inEditor_a2_q() {
		withoutOutline[|
			prepare("TestInEditorA2_Q")
	
			val page = getActivePage()
			val editorA2 = openAndGetXtextEditor(fileA2, page)
			val editorQ = openAndGetXtextEditor(fileQ, page)
			val errorsA2 = getEditorValidationErrors(editorA2)
			assertEquals("editor for file A should not have any errors", #[], errorsA2)
			editorA2.assertAllFromIndex
			
			setDocumentContent("file Q", fileQ, editorQ, sourceQ_modify_q)
			waitForUpdateEditorJob
	
			val errorsA2_modified = getEditorValidationErrors(editorA2)
			assertEquals("editor for file A2 should now have exactly 1 expected error", #[
				"ERROR:union{int,string} is not a subtype of string. (platform:/resource/TestInEditorA2_Q/src/m/A2.n4js line : 6 column : 19)"
			].join('\n'), errorsA2_modified.map[toString].join('\n'))
			
			val expectedFromSource = #{
				URI.createPlatformResourceURI('TestInEditorA2_Q/src/m/A2.n4js', true),
				URI.createPlatformResourceURI('TestInEditorA2_Q/src/m/P.n4js', true),
				URI.createPlatformResourceURI('TestInEditorA2_Q/src/m/Q.n4js', true)
			}
			editorA2.assertFromSource(expectedFromSource)
		]
	}

}
