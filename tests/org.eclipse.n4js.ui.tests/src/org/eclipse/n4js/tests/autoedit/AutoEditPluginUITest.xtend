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
package org.eclipse.n4js.tests.autoedit

import com.google.common.base.Charsets
import org.eclipse.core.resources.IEncodedStorage
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.PlatformObject
import org.eclipse.core.runtime.preferences.InstanceScope
import org.eclipse.n4js.ui.internal.N4JSActivator
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.preferences.ScopedPreferenceStore
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.ui.editor.utils.EditorUtils
import org.eclipse.xtext.ui.testing.AbstractCStyleLanguageAutoEditTest
import org.eclipse.xtext.util.StringInputStream
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 */
class AutoEditPluginUITest extends AbstractCStyleLanguageAutoEditTest {

	private static final String ORG_ECLIPSE_UI_EDITORS = "org.eclipse.ui.editors";
	private static final String SPACES_FOR_TABS = "spacesForTabs";
	private static final String LINE_SEPARATOR = System.lineSeparator;

	@FinalFieldsConstructor
	static class StringBasedStorage extends PlatformObject implements IEncodedStorage {
		val String content
		val String fileName

		override getCharset() throws CoreException {
			return Charsets.UTF_8.name
		}

		override getContents() throws CoreException {
			new StringInputStream(content, charset)
		}

		override getFullPath() {
			return new Path('/doesNotExist/' + fileName)
		}

		override getName() {
			return fileName
		}

		override isReadOnly() {
			return false
		}

	}
	
	var ScopedPreferenceStore prefsUiEditors;
	var boolean prefsUiEditors_spacesForTabs;

	@Before
	override void setUp() throws Exception {
		closeWelcomePage();
		closeEditors();
		
		// Some test cases of class AbstractCStyleLanguageAutoEditTest expect tabs,
		// hence we need to set the default to tabs instead of spaces.
		prefsUiEditors = new ScopedPreferenceStore(InstanceScope.INSTANCE, ORG_ECLIPSE_UI_EDITORS);
		prefsUiEditors_spacesForTabs = prefsUiEditors.getBoolean(SPACES_FOR_TABS);
		if (prefsUiEditors_spacesForTabs) {
			prefsUiEditors.setValue(SPACES_FOR_TABS, false);
		}
	}

	@After
	override void tearDown() throws Exception {
		closeEditors();
		// Restore initial setting
		prefsUiEditors.setValue(SPACES_FOR_TABS, prefsUiEditors_spacesForTabs);
	}

	override protected String getEditorId() {
		return N4JSActivator.ORG_ECLIPSE_N4JS_N4JS
	}

	override protected String getFileExtension() {
		throw new UnsupportedOperationException
	}

	override protected XtextEditor openEditor(String stringRaw) throws Exception {
		val string = stringRaw.replace("\n", LINE_SEPARATOR); // support for windows, etc.
		val storage = new StringBasedStorage(string.replace("|", ""), 'TestMe.n4js')
		val editorInput = EditorUtils.createEditorInput(storage)
		val editor = PlatformUI.workbench.activeWorkbenchWindow.activePage.openEditor(editorInput, editorId) as XtextEditor
		var int cursor = string.indexOf('|')
		editor.getInternalSourceViewer().setSelectedRange(cursor, 0)
		editor.getInternalSourceViewer().getTextWidget().setFocus()
		return editor
	}

	override protected pasteText(XtextEditor editor, String text) throws Exception {
		super.pasteText(editor, text.replace("\n", LINE_SEPARATOR)) // support for windows, etc.
	}

	override protected assertState(String string, XtextEditor editor) {
		super.assertState(string.replace("\n", LINE_SEPARATOR), editor) // support for windows, etc.
	}

	@Test def void testBug335634_08() throws Exception {
		val editor = openEditor(" // /*|\n");
		pressKey(editor, '\n');
		assertState(" // /*\n |\n", editor);
	}

	@Test def void testBug335634_09() throws Exception {
		val editor = openEditor("// /*|\n");
		pressKey(editor, '\n');
		assertState("// /*\n|\n", editor);
	}

	@Test def void testAsteriskAfterRegEx_01() throws Exception {
		val editor = openEditor("val x = /a/|\n");
		pressKey(editor, '*');
		assertState("val x = /a/*|\n", editor);
	}

	@Test def void testAsteriskAfterRegEx_02() throws Exception {
		val editor = openEditor("val x = /a/*|\n");
		pressKey(editor, '\n');
		assertState("val x = /a/*\n|\n", editor);
	}

	@Test def void testMLComments_18() throws Exception {
		val editor = openEditor("   | ");
		pressKey(editor, '/');
		pressKey(editor, '*');
		assertState("   /*| */ ", editor);
	}

	@Test def void testMLComments_19() throws Exception {
		val editor = openEditor("   |\n");
		pressKey(editor, '/');
		pressKey(editor, '*');
		assertState("   /*| */\n", editor);
	}

	@Test def void testMLComments_20() throws Exception {
		val editor = openEditor("   /*|\n");
		pressKey(editor, '\n');
		assertState("   /*\n    * |\n    */\n", editor);
	}

	@Test def void testMLComments_21() throws Exception {
		val editor = openEditor("   /* |\n");
		pressKey(editor, '\n');
		assertState("   /* \n    * |\n    */\n", editor);
	}

	@Test def void testMLComments_22() throws Exception {
		val editor = openEditor("   /** |\n");
		pressKey(editor, '\n');
		assertState("   /** \n    * |\n    */\n", editor);
	}

	@Test def void testMLComments_23() throws Exception {
		val editor = openEditor("   /*** |\n");
		pressKey(editor, '\n');
		assertState("   /*** \n    * |\n    */\n", editor);
	}

	@Test def void testMLCommentsJSDoc_01() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "public getA(): A {return null}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_02() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "public getThis(): this {return this}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_03() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "public doStuff(): void {}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_04() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "public getBoolean(n:number): boolean {return false}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @param n\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_05() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "public getBoolean(n:number,s:String): boolean {return false}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @param n\n * @param s\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_06() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "getBoolean(n:number,s:String): boolean {return false}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @param n\n * @param s\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_07() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "constructor(n:number,s:String) {}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @param n\n * @param s\n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_08() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "getOne() { return 1; }\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n * @return \n */\n" + prepEnd;
		assertState(result, editor);
	}

	@Test def void testMLCommentsJSDoc_09() throws Exception {
		val prepBegin = "export public class A {\n";
		val prepS = "/**| */\n";
		val prepEnd = "constructor() {}\n}";
		val editor = openEditor(prepBegin + prepS + prepEnd);
		pressKey(editor, '\n');
		val result = prepBegin + "/**\n * |\n */\n" + prepEnd;
		assertState(result, editor);
	}

	/**  IDEBUG-390 */
	@Test def void testBug368519() throws Exception {

		// Do the Action under test
		val editor = openEditor("|x('')");
		val toggleAction = editor.getAction("ToggleComment")
		toggleAction.run();

		// assure behaviour
		assertState("|//x('')", editor);
	}

}
