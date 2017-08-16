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
import org.junit.Test
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.emf.common.util.URI

/**
 * 
 */
class CanLoadFromDescriptionPluginUITest extends AbstractResourceLoadingTest {
	/*
	 * A1 -> B1 -> C -> D
	 * |           ^
	 * ---> B2 -----
	 *      ^
	 * A2 --- 
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
		
		var a2: string = b2;
		a2; // avoid unused variable warning
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
	
	private val sourceB1_modify_b1 = sourceB1.replace(
		"b1 = c1",
		'b1 = c1 || 1');
	
	private val sourceC_modify_c1 = sourceC.replace(
		"c1 = ''",
		'c1 = 1');
		
	private val sourceD_modify_d = sourceD.replace(
		"d = ''",
		'd = 1');
	 
	private var IProject project;
	private var IFolder srcFolder;
	private var IFolder srcFolderM;
	private var IFile fileA1;
	private var IFile fileA2;
	private var IFile fileB1;
	private var IFile fileB2;
	private var IFile fileC;
	private var IFile fileD;

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
		editorA1.assertResource [ resource |
			val resourceSet = resource.resourceSet;
			resourceSet.resources.forEach [ other |
				if (other !== resource) {
					if (other instanceof N4JSResource) {
						assertTrue(other.URI.toString + ' was loaded from source', other.isLoadedFromDescription)
					}
				}
			]
		]

		setDocumentContent("file A1", fileA1, editorA1, sourceA1 + ' ')
		waitForUpdateEditorJob

		val errorsA1_modified = getEditorValidationErrors(editorA1)
		assertEquals("editor for file A should not have any errors", #[], errorsA1_modified)
		editorA1.assertResource [ resource |
			val resourceSet = resource.resourceSet;
			resourceSet.resources.forEach [ other |
				if (other !== resource) {
					if (other instanceof N4JSResource) {
						assertTrue(other.URI.toString + ' was loaded from source', other.isLoadedFromDescription)
					}
				}
			]
		]
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
			"ERROR:int is not a subtype of string. (platform:/resource/TestInEditorA2_D/src/m/A2.n4js line : 3 column : 18)"
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

}
