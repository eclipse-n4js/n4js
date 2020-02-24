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
package org.eclipse.n4js.ide.server.codeActions.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.ReplaceRegion;

/**
 * Collection of high-level convenience methods for creating {@link TextEdit}s.
 */
public class ChangeProvider {

	/**
	 * Convenience method to create a simple replacement. Provided only to allow creating such simple changes in the
	 * same style as the other, high-level changes provided in this class.
	 */
	public static TextEdit replace(int startLine, int startChar, int endLine, int endChar, String replacementText) {
		Position posStart = new Position(startLine, startChar);
		Position posEnd = new Position(endLine, endChar);
		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, replacementText);
	}

	/**
	 * Convenience method to create a simple replacement. Provided only to allow creating such simple changes in the
	 * same style as the other, high-level changes provided in this class.
	 */
	public static TextEdit replace(Document doc, int offset, int length, String replacementText) {
		Position posStart = doc.getPosition(offset);
		Position posEnd = doc.getPosition(offset + length);
		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, replacementText);
	}

	/**
	 * Insert the given string as a new line above the line at the given offset. The given string need not contain any
	 * line delimiters and the offset need not point to the beginning of a line. If 'sameIndentation' is set to
	 * <code>true</code>, the new line will be indented as the line at the given offset (i.e. same leading white space).
	 */
	public static TextEdit insertLineAbove(Document doc, int offset, String txt, boolean sameIndentation) {
		Position offsetPos = doc.getPosition(offset);

		String NL = lineDelimiter(doc, offset);
		String replacementText = txt + NL;

		if (sameIndentation) {
			String lineContent = doc.getLineContent(offsetPos.getLine());
			int idx = 0;
			while (idx < lineContent.length() && Character.isWhitespace(lineContent.charAt(idx))) {
				idx++;
			}
			String indent = lineContent.substring(0, idx);
			replacementText = indent + replacementText;
		}

		Position posStart = doc.getPosition(offsetPos.getLine());
		Position posEnd = doc.getPosition(offsetPos.getLine());
		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, replacementText);
	}

	/**
	 * Determine the current Line delimiter at the given offset.
	 *
	 * @param doc
	 *            the document to query for the newline sequence.
	 * @param offset
	 *            absolute character position
	 * @return the new line sequence used in the line containing offset
	 */
	public static String lineDelimiter(Document doc, int offset) {
		if (doc.getLineCount() < 1) {
			return "\n";
		}

		Position offsetPos = doc.getPosition(offset);
		int lineN0 = offsetPos.getLine();
		if (offsetPos.getLine() > 0) {
			lineN0--;
		}
		int lineN1 = lineN0 + 1;
		int offsetAtStart = doc.getOffSet(new Position(lineN1, 0));

		Position posStart = doc.getPosition(offsetAtStart - 2);
		Position posEnd = doc.getPosition(offsetAtStart);
		Range range = new Range(posStart, posEnd);
		String lineBreakString = doc.getSubstring(range);
		switch (lineBreakString) {
		case "\r\n":
			return "\r\n";
		case "\n":
			return "\n";
		default:
			return "\n";
		}
	}

	/**
	 * Same as {@link #removeText( Document, int, int, boolean)}, but the text region to remove can be specified by
	 * providing a semantic object, i.e. AST node.
	 */
	public static TextEdit removeSemanticObject(Document doc, EObject obj, boolean removeEntireLineIfEmpty) {
		if (obj == null) {
			return null;
		}
		return removeNode(doc, NodeModelUtils.findActualNodeFor(obj), removeEntireLineIfEmpty);
	}

	/**
	 * Same as {@link #removeText(Document, int, int, boolean)}, but the text region to remove can be specified by
	 * providing a parse tree node.
	 */
	public static TextEdit removeNode(Document doc, INode node, boolean removeEntireLineIfEmpty) {
		if (node == null) {
			return null;
		}
		return removeText(doc, node.getOffset(), node.getLength(), removeEntireLineIfEmpty);
	}

	/**
	 * Removes text of the given length at the given offset. If 'removeEntireLineIfEmpty' is set to <code>true</code>,
	 * the line containing the given text region will be deleted entirely iff the change would leave the line empty
	 * (i.e. contains only white space) <em>after</em> the removal of the text region.
	 */
	public static TextEdit removeText(Document doc, int offset, int length, boolean removeEntireLineIfEmpty) {
		Position posStart = doc.getPosition(offset);
		Position posEnd = doc.getPosition(offset + length);

		if (removeEntireLineIfEmpty) {
			int startLine = posStart.getLine();
			int endLine = posEnd.getLine();
			String startLineContent = doc.getLineContent(startLine);
			String endLineContent = (startLine != endLine) ? doc.getLineContent(endLine) : startLineContent;

			startLineContent = startLineContent.substring(0, posStart.getCharacter());
			endLineContent = endLineContent.substring(posEnd.getCharacter());

			String resultLineContent = startLineContent + endLineContent;

			if (resultLineContent.isBlank()) {
				posStart = new Position(posStart.getLine(), 0);
				posEnd = new Position(posEnd.getLine() + 1, 0);
			}
		}

		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, "");
	}

	/**
	 * Delete line containing the given offset (the offset need not point to the beginning of the line). Will do nothing
	 * if 'deleteOnlyIfEmpty' is set to <code>true</code> and the given line is non-empty, i.e. contains characters
	 * other than {@link Character#isWhitespace(char) white space}.
	 */
	public static TextEdit deleteLine(Document doc, int offset, boolean deleteOnlyIfEmpty) {
		Position offPosition = doc.getPosition(offset);
		String lineContent = doc.getLineContent(offPosition.getLine());
		if (deleteOnlyIfEmpty && !lineContent.isBlank()) {
			return null;
		}

		Position posStart = new Position(offPosition.getLine(), 0);
		Position posEnd = new Position(offPosition.getLine() + 1, 0);
		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, "");
	}

	/**
	 * Apply the given replacement to the document.
	 */
	public static TextEdit replace(Document doc, ReplaceRegion replaceRegion) {
		return replace(doc, replaceRegion.getOffset(), replaceRegion.getLength(), replaceRegion.getText());
	}

}
