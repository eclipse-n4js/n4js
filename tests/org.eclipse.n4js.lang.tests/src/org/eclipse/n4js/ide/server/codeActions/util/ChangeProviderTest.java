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
package org.eclipse.n4js.ide.server.codeActions.util;

import java.util.List;

import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.xtext.ide.server.XDocument;
import org.eclipse.xtext.ide.server.Document;
import org.junit.Test;

/**
 * Low-level tests for {@link ChangeProvider}.
 */
public class ChangeProviderTest extends AbstractN4JSTest {

	@Test
	public void testLineDelimiter_emptyDoc() {
		assertEquals("\n", ChangeProvider.lineDelimiter(toDocument(""), 0));
	}

	@Test
	public void testLineDelimiter_singleLineDoc() {
		assertEquals("\n", ChangeProvider.lineDelimiter(toDocument("// some content but without a line break"), 10));
	}

	@Test
	public void testLineDelimiter_docWithTwoLines() {
		assertEquals("\n", ChangeProvider.lineDelimiter(toDocument("\n"), 0));
		assertEquals("\n", ChangeProvider.lineDelimiter(toDocument("\n"), 1));
		assertEquals("\r\n", ChangeProvider.lineDelimiter(toDocument("\r\n"), 0));
		assertEquals("\r\n", ChangeProvider.lineDelimiter(toDocument("\r\n"), 2));
	}

	@Test
	public void testLineDelimiter_docWithDifferentSequences() {
		String code = "___\n___\r\n__X__\r\n___\n___";
		assertEquals("\r\n", ChangeProvider.lineDelimiter(toDocument(code), code.indexOf('X')));
	}

	@Test
	public void testInsertLinesAbove_intoEmptyDocument() {
		String code = "";

		int offset = 0;
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, false, new String[] {
				"// new line"
		});

		assertEdits(code, edit, """
				// new line
				""");
	}

	@Test
	public void testInsertLinesAbove_intoSingleLineDocument() {
		String code = "console.log('hello');";

		int offset = code.indexOf(".log(");
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, false, new String[] {
				"// new line"
		});

		assertEdits(code, edit, """
				// new line
				console.log('hello');""");
	}

	@Test
	public void testInsertLinesAbove_atEndOfDocument() {
		String code = """
				console.log('hello');
				""";

		int offset = code.length(); // end of document
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, false, new String[] {
				"// new line"
		});

		assertEdits(code, edit, """
				console.log('hello');
				// new line
				""");
	}

	@Test
	public void testInsertLinesAbove_severalLines() {
		String code = """
				// before
				   console.log('hello');
				// end
				""";

		int offset = code.indexOf(".log(");
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, false, new String[] {
				"// new line 1",
				"// new line 2",
				"// new line 3"
		});

		assertEdits(code, edit, """
				// before
				// new line 1
				// new line 2
				// new line 3
				   console.log('hello');
				// end
				""");
	}

	@Test
	public void testInsertLinesAbove_sameIndentation01() {
		String code = """
				// before
				   console.log('hello');
				// end
				""";

		int offset = code.indexOf(".log(");
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, true, new String[] {
				"// new line"
		});

		assertEdits(code, edit, """
				// before
				   // new line
				   console.log('hello');
				// end
				""");
	}

	@Test
	public void testInsertLinesAbove_sameIndentation02() {
		String code = """
				// before
				   console.log('hello');
				// end
				""";

		int offset = code.indexOf(".log(");
		TextEdit edit = ChangeProvider.insertLinesAbove(toDocument(code), offset, true, new String[] {
				"// new line 1",
				"// new line 2",
				"// new line 3"
		});

		assertEdits(code, edit, """
				// before
				   // new line 1
				   // new line 2
				   // new line 3
				   console.log('hello');
				// end
				""");
	}

	private void assertEdits(CharSequence contentsBefore, TextEdit edit, CharSequence expectedContentsAfter) {
		assertEdits(contentsBefore, List.of(edit), expectedContentsAfter);
	}

	private void assertEdits(CharSequence contentsBefore, Iterable<? extends TextEdit> edits,
			CharSequence expectedContentsAfter) {

		XDocument docBefore = new XDocument(0, contentsBefore.toString(), true, true);
		XDocument docAfter = docBefore.applyChanges(edits);
		String actualContentAfter = docAfter.getContents();
		assertEquals(expectedContentsAfter.toString(), actualContentAfter);
	}

	private Document toDocument(CharSequence contents) {
		return new Document(1, contents.toString(), true);
	}
}
