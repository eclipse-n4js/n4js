/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.codeActions.util

import org.eclipse.lsp4j.TextEdit
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.xtext.server.XDocument
import org.eclipse.xtext.ide.server.Document
import org.junit.Test

/**
 * Low-level tests for {@link ChangeProvider}.
 */
class ChangeProviderTest extends AbstractN4JSTest {

	@Test
	def void testLineDelimiter_emptyDoc() {
		assertEquals("\n", ChangeProvider.lineDelimiter("".toDocument, 0));
	}

	@Test
	def void testLineDelimiter_singleLineDoc() {
		assertEquals("\n", ChangeProvider.lineDelimiter("// some content but without a line break".toDocument, 10));
	}

	@Test
	def void testLineDelimiter_docWithTwoLines() {
		assertEquals("\n", ChangeProvider.lineDelimiter("\n".toDocument, 0));
		assertEquals("\n", ChangeProvider.lineDelimiter("\n".toDocument, 1));
		assertEquals("\r\n", ChangeProvider.lineDelimiter("\r\n".toDocument, 0));
		assertEquals("\r\n", ChangeProvider.lineDelimiter("\r\n".toDocument, 2));
	}

	@Test
	def void testLineDelimiter_docWithDifferentSequences() {
		val code = "___\n___\r\n__X__\r\n___\n___";
		assertEquals("\r\n", ChangeProvider.lineDelimiter(code.toDocument, code.indexOf('X')));
	}

	@Test
	def void testInsertLinesAbove_intoEmptyDocument() {
		val code = "";

		val offset = 0;
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, false, #[
			"// new line"
		]);

		assertEdits(code, edit, '''
			// new line
		''');
	}

	@Test
	def void testInsertLinesAbove_intoSingleLineDocument() {
		val code = "console.log('hello');";

		val offset = code.indexOf(".log(");
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, false, #[
			"// new line"
		]);

		assertEdits(code, edit, '''
			// new line
			console.log('hello');''');
	}

	@Test
	def void testInsertLinesAbove_atEndOfDocument() {
		val code = '''
			console.log('hello');
		''';

		val offset = code.length; // end of document
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, false, #[
			"// new line"
		]);

		assertEdits(code, edit, '''
			console.log('hello');
			// new line
		''');
	}

	@Test
	def void testInsertLinesAbove_severalLines() {
		val code = '''
			// before
			   console.log('hello');
			// end
		''';

		val offset = code.indexOf(".log(");
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, false, #[
			"// new line 1",
			"// new line 2",
			"// new line 3"
		]);

		assertEdits(code, edit, '''
			// before
			// new line 1
			// new line 2
			// new line 3
			   console.log('hello');
			// end
		''');
	}

	@Test
	def void testInsertLinesAbove_sameIndentation01() {
		val code = '''
			// before
			   console.log('hello');
			// end
		''';

		val offset = code.indexOf(".log(");
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, true, #[
			"// new line"
		]);

		assertEdits(code, edit, '''
			// before
			   // new line
			   console.log('hello');
			// end
		''');
	}

	@Test
	def void testInsertLinesAbove_sameIndentation02() {
		val code = '''
			// before
			   console.log('hello');
			// end
		''';

		val offset = code.indexOf(".log(");
		val edit = ChangeProvider.insertLinesAbove(code.toDocument, offset, true, #[
			"// new line 1",
			"// new line 2",
			"// new line 3"
		]);

		assertEdits(code, edit, '''
			// before
			   // new line 1
			   // new line 2
			   // new line 3
			   console.log('hello');
			// end
		''');
	}

	def private void assertEdits(CharSequence contentsBefore, TextEdit edit, CharSequence expectedContentsAfter) {
		assertEdits(contentsBefore, #[ edit ], expectedContentsAfter);
	}

	def private void assertEdits(CharSequence contentsBefore, Iterable<? extends TextEdit> edits, CharSequence expectedContentsAfter) {
		val docBefore = new XDocument(0, contentsBefore.toString, true, true);
		val docAfter = docBefore.applyChanges(edits);
		val actualContentAfter = docAfter.getContents();
		assertEquals(expectedContentsAfter.toString, actualContentAfter);
	}

	def private Document toDocument(CharSequence contents) {
		return new Document(1, contents.toString, true);
	}
}
