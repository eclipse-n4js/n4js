/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.xtext.server;

import java.util.Comparator;
import java.util.List;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
public class XDocument extends Document {
	private final int version;
	private final String contents;
	private final boolean printSourceOnError;
	private final boolean isDirty;

	/**
	 * Sorts {@link TextEdit}s based on their affected region in descending order, i.e. edits with higher offsets come
	 * first.
	 */
	private final static Comparator<TextEdit> textEditComparator = Comparator
			.<TextEdit, Integer> comparing(edit -> edit.getRange().getStart().getLine())
			.thenComparing(edit -> edit.getRange().getStart().getCharacter())
			.reversed();

	/** Constructor */
	public XDocument(int version, String contents) {
		this(version, contents, true, false);
	}

	/**
	 * @since 2.15
	 */
	public XDocument(int version, String contents, boolean printSourceOnError, boolean isDirty) {
		super(version, contents, true);
		this.version = version;
		this.contents = contents;
		this.printSourceOnError = printSourceOnError;
		this.isDirty = isDirty;
	}

	@Override
	public int getOffSet(Position position) throws IndexOutOfBoundsException {
		int l = contents.length();
		char NL = '\n';
		int line = 0;
		int column = 0;
		for (var i = 0; i < l; i++) {
			char ch = contents.charAt(i);
			if (position.getLine() == line && position.getCharacter() == column) {
				return i;
			}
			if (ch == NL) {
				line++;
				column = 0;
			} else {
				column++;
			}
		}
		if (position.getLine() == line && position.getCharacter() == column) {
			return l;
		}

		throw new IndexOutOfBoundsException(
				position.toString() + ((printSourceOnError) ? "" : (" text was : " + contents)));
	}

	@Override
	public Position getPosition(int offset) throws IndexOutOfBoundsException {
		int l = contents.length();
		if (offset < 0 || offset > l)
			throw new IndexOutOfBoundsException(offset + ((printSourceOnError) ? "" : (" text was : " + contents)));

		char NL = '\n';
		int line = 0;
		int column = 0;
		for (int i = 0; i < l; i++) {
			char ch = contents.charAt(i);
			if (i == offset) {
				return new Position(line, column);
			}
			if (ch == NL) {
				line++;
				column = 0;
			} else {
				column++;
			}
		}
		return new Position(line, column);
	}

	/**
	 * Returns with the text for a certain line without the trailing LF. Throws an {@link IndexOutOfBoundsException} if
	 * the zero-based {@code lineNumber} argument is negative or exceeds the number of lines in the document.
	 */
	@Override
	public String getLineContent(int lineNumber) throws IndexOutOfBoundsException {
		if (lineNumber < 0) {
			throw new IndexOutOfBoundsException(lineNumber + ((printSourceOnError) ? "" : (" text was : " + contents)));
		}
		char NL = '\n';
		int l = contents.length();
		StringBuilder lineContent = new StringBuilder();
		int line = 0;
		for (var i = 0; i < l; i++) {
			if (line > lineNumber) {
				return lineContent.toString();
			}
			char ch = contents.charAt(i);
			if (line == lineNumber && ch != NL) {
				lineContent.append(ch);
			}
			if (ch == NL) {
				line++;
			}
		}
		if (line < lineNumber) {
			throw new IndexOutOfBoundsException(lineNumber + ((printSourceOnError) ? "" : (" text was : " + contents)));
		}
		return lineContent.toString();
	}

	/**
	 * Get the number of lines in the document. Empty document has line count: {@code 1}.
	 */
	@Override
	public int getLineCount() {
		return getPosition(contents.length()).getLine() + 1;
	}

	@Override
	public String getSubstring(Range range) {
		int start = getOffSet(range.getStart());
		int end = getOffSet(range.getEnd());
		return this.contents.substring(start, end);
	}

	/**
	 * As opposed to {@link TextEdit}[] the positions in the edits of a {@link DidChangeTextDocumentParams} refer to the
	 * state after applying the preceding edits. See
	 * https://microsoft.github.io/language-server-protocol/specification#textedit-1 and
	 * https://github.com/microsoft/vscode/issues/23173#issuecomment-289378160 for details.
	 *
	 * @return a new document with an incremented version and the text document changes applied.
	 * @since 2.18
	 */
	@Override
	public XDocument applyTextDocumentChanges(Iterable<? extends TextDocumentContentChangeEvent> changes) {
		XDocument currentDocument = this;
		int newVersion = currentDocument.version + 1;
		for (TextDocumentContentChangeEvent change : changes) {
			String newContent;

			Range range = change.getRange();
			if (range == null) {
				newContent = change.getText();
			} else {
				int start = currentDocument.getOffSet(range.getStart());
				int end = currentDocument.getOffSet(range.getEnd());
				String oldContents = currentDocument.contents;
				newContent = oldContents.substring(0, start) + change.getText() + oldContents.substring(end);
			}
			currentDocument = new XDocument(newVersion, newContent, printSourceOnError, true);
		}
		return currentDocument;
	}

	/**
	 * Only use for testing.
	 *
	 * All positions in the {@link TextEdit}s refer to the same original document (this).
	 */
	@Override
	public XDocument applyChanges(Iterable<? extends TextEdit> changes) {
		List<? extends TextEdit> changesSorted = IterableExtensions.sortWith(changes, textEditComparator);
		String newContent = contents;
		for (TextEdit change : changesSorted) {
			if (change.getRange() == null) {
				newContent = change.getNewText();
			} else {
				int start = getOffSet(change.getRange().getStart());
				int end = getOffSet(change.getRange().getEnd());
				newContent = newContent.substring(0, start) + change.getNewText() + newContent.substring(end);
			}
		}
		XDocument document = new XDocument(version + 1, newContent, true, true);
		return document;
	}

	/** @return the string contents of this document */
	@Override
	public String getContents() {
		return this.contents;
	}

	/** @return true iff this file has unsaved changes */
	public boolean isDirty() {
		return this.isDirty;
	}

	/**
	 * @since 2.15
	 */
	@Override
	public boolean isPrintSourceOnError() {
		return this.printSourceOnError;
	}

	/** @return a new document with the same properties but {@link #isDirty} set to {@code false} */
	public XDocument save() {
		return new XDocument(version, contents, printSourceOnError, false);
	}

}
