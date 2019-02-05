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
package org.eclipse.n4js.tests.staticpolyfill

import com.google.common.io.Files
import java.nio.charset.Charset
import org.eclipse.core.resources.IFile
import org.eclipse.n4js.tests.util.EclipseUIUtils
import org.eclipse.ui.IWorkbenchPage
import org.eclipse.xtext.ui.editor.XtextEditor
import org.junit.Ignore
import org.junit.Test

import static org.eclipse.n4js.tests.staticpolyfill.SingleProject_Probands.*
import static org.junit.Assert.*

/**
 */
@Ignore("random")
class SingleProjectPluginUITest extends AbstractStaticPolyfillBuilderTest {

	@Test
	def void testStaticPolyfill_filling_updates_filled_module() throws Exception {
		val srcAB= createFolder(src,"a/b")
		val src2AB= createFolder(src2,"a/b")

		val cFilling = createTestFile(src2AB, "Poly",validContent_Filling );
		addSrc2ToSources

		//but was:
		//line 3: Couldn't resolve reference to Type 'K'.
		//line 20: Couldn't resolve reference to IdentifiableElement 'fromParent'.
		//line 8: The method constructor must override or implement a method from a super class or interface.
		//line 15: The method greet must override or implement a method from a super class or interface.
		// expected:<0> but was:<7>
		assertMarkers("filling file should have X errors", cFilling, 4);

		// OUTPUT structure:	src-gen/a/b/Poly.js
		val xRelPath = cFilling.projectRelativePath.removeFirstSegments(1).removeFileExtension.addFileExtension("js")
		val String compiledFilePath = '''src-gen/«xRelPath.toString»'''
		val IFile compiledFile = projectUnderTest.project.getFile(compiledFilePath)

		assertFalse("Nothing should be compiled in error case.",compiledFile.exists)

		val cFilled = createTestFile(srcAB, "Poly", validContent_Filled );

		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);
		// assertExistence of compiled file.
		assertTrue("No compiled file.",compiledFile.exists)


		// now change Filling and see, that filled is updated.
		// 1. invalid code --> will remove compiled,

		// open editors of test files
		val IWorkbenchPage page = EclipseUIUtils.getActivePage()
		val XtextEditor fileCFillingXtextEditor = openAndGetXtextEditor(cFilling, page);

		setDocumentContent("invalidating the filling", cFilling, fileCFillingXtextEditor,invalidContent_filling)
		fileCFillingXtextEditor.doSave(null)
		waitForAutoBuild

		assertMarkers("filled file should have no errors", cFilled, 0);
		// line 8: Signature of method K.constructor (line 7) does not conform to overridden method K.constructor: {function(number):void} is not a subtype of {function(string):void}. expected:<0> but was:<1>
		assertMarkers("filling file should have no errors", cFilling, 1);
		//  assert compiled file is deleted.
		assertFalse("Compiled file should be deleted",compiledFile.exists)


		// 2. valid code again --> will be compiled again.

		setDocumentContent("invalidating the filling", cFilling, fileCFillingXtextEditor,validContent_Filling)
		fileCFillingXtextEditor.doSave(null)
		waitForAutoBuild

		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);

		//  assert compiled file is back again + new content.
		assertTrue("No compiled file.",compiledFile.exists)
		val actualContent = Files.toString(compiledFile.location.toFile, Charset.defaultCharset);
		assertTrue('Generated file content was empty: ' + compiledFile, !actualContent.nullOrEmpty)

		assertEquals("test-setup broken", 0,matchCount(pattern_1_Unique,validContent_Filled))
		assertEquals("test-setup broken", 1,matchCount(pattern_1_Unique,validContent_Filling))
		assertTrue("New filled in method was not compiled.",matchCount( pattern_1_Unique, actualContent ) >= 1);


		// 3. set valid extended content  --> check compiled


		setDocumentContent("extending the filling", cFilling, fileCFillingXtextEditor,validExtendedContent_filling)
		fileCFillingXtextEditor.doSave(null)
		waitForAutoBuild

		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);

		val actualContent2 = Files.toString(compiledFile.location.toFile, Charset.defaultCharset);
		assertTrue('Generated file content was empty: ' + compiledFile, !actualContent2.nullOrEmpty)

		assertEquals("test-setup broken", 0,matchCount(pattern_2_ExtendedContent,validContent_Filled))
		assertEquals("test-setup broken", 1,matchCount(pattern_2_ExtendedContent,validExtendedContent_filling))
		assertTrue("New filled in method was not compiled.",matchCount( pattern_2_ExtendedContent, actualContent2 ) >= 1);

		val savedModStamp_since_3 = compiledFile.modificationStamp

		// 4. update only inside a comment --> no structural change of the AST, but we expect a rebuild of the filled file.

		setDocumentContent("modify comment of extended filling", cFilling, fileCFillingXtextEditor,validModifiedExtendedContent_filling)
		fileCFillingXtextEditor.doSave(null)
		waitForAutoBuild

		assertMarkers("filled file should have no errors", cFilled, 0);
		assertMarkers("filling file should have no errors", cFilling, 0);

		val actualContent3 = Files.toString(compiledFile.location.toFile, Charset.defaultCharset);
		assertTrue('Generated file content was empty: ' + compiledFile, !actualContent3.nullOrEmpty)

		val savedModStamp_since_4 = compiledFile.modificationStamp
		assertEquals("test-setup broken", 0,matchCount(pattern_3_ModifiedExtendedContent,validContent_Filled))
		assertEquals("test-setup broken", 1,matchCount(pattern_3_ModifiedExtendedContent,validModifiedExtendedContent_filling))
		// The following line cannot be checked any more, since we do not transfer comments in with the new transpiler.
		//assertTrue("New filled in line of code inside of a method was not compiled.",matchCount( pattern_3_ModifiedExtendedContent, actualContent3 ) >= 1);
		assertTrue( "Update after comment-modification did not trigger new build.", savedModStamp_since_4 > savedModStamp_since_3 )
	}

}
