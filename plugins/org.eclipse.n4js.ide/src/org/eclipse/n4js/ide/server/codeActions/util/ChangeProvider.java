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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.common.base.Joiner;

/**
 * Collection of low-level convenience methods for creating {@link TextEdit}s.
 * <p>
 * Class {@link SemanticChangeProvider} contains higher-level convenience methods that accept AST nodes instead of
 * character offsets, etc.
 */
public class ChangeProvider {

	/**
	 * Insert the given text.
	 */
	public static TextEdit insert(int line, int character, String text) {
		return replace(line, character, line, character, text);
	}

	/**
	 * Insert the given text.
	 */
	public static TextEdit insert(Document doc, int offset, String text) {
		return replace(doc, offset, 0, text);
	}

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
	 * Same as {@link #replace(Document, int, int, String)}, but accepting a {@link ReplaceRegion}.
	 */
	public static TextEdit replace(Document doc, ReplaceRegion replaceRegion) {
		return replace(doc, replaceRegion.getOffset(), replaceRegion.getLength(), replaceRegion.getText());
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
	 * Insert the given strings as new lines above the line at the given offset. The given strings need not contain any
	 * line delimiters and the offset need not point to the beginning of a line. If 'sameIndentation' is set to
	 * <code>true</code>, the new line will be indented as the line at the given offset (i.e. same leading white space).
	 */
	public static TextEdit insertLinesAbove(Document doc, int offset, boolean sameIndentation,
			String... linesToInsert) {

		if (linesToInsert == null || linesToInsert.length == 0) {
			throw new IllegalArgumentException("must pass in at least one line to insert");
		}

		Position offsetPos = doc.getPosition(offset);

		String NL = lineDelimiter(doc, offset);
		String replacementText = Joiner.on(NL).join(linesToInsert);

		if (sameIndentation) {
			String lineContent = doc.getLineContent(offsetPos.getLine());
			int idx = 0;
			while (idx < lineContent.length() && Character.isWhitespace(lineContent.charAt(idx))) {
				idx++;
			}
			String indent = lineContent.substring(0, idx);
			replacementText = indent + replacementText.replace(NL, NL + indent);
		}

		replacementText = replacementText + NL;

		Position posStart = new Position(offsetPos.getLine(), 0);
		Position posEnd = new Position(offsetPos.getLine(), 0);
		Range range = new Range(posStart, posEnd);
		return new TextEdit(range, replacementText);
	}

	/**
	 * Determine the current line delimiter at the given offset.
	 *
	 * @param doc
	 *            the document to query for the line separator sequence.
	 * @param offset
	 *            absolute character position
	 * @return the line separator sequence used in the line containing offset or <code>"\n"</code> if the document
	 *         contains only a single line.
	 */
	public static String lineDelimiter(Document doc, int offset) {
		String content = doc.getContents();
		int lineCount = doc.getLineCount();
		if (lineCount < 2) {
			return "\n"; // a document with a single line does not have any line separators
		}

		Position offsetPos = doc.getPosition(offset);
		int line = offsetPos.getLine();

		if (line > 0) { // no use looking for leading line separator in first line
			int offsetOfLine = doc.getOffSet(new Position(line, 0));
			String sequence = content.substring(Math.max(offsetOfLine - 2, 0), offsetOfLine);
			if (sequence.equals("\r\n")) {
				return "\r\n";
			} else if (sequence.endsWith("\n")) {
				return "\n";
			}
		}
		if (line + 1 < lineCount) { // no use looking for trailing line separator in last line
			int offsetOfFollowingLine = doc.getOffSet(new Position(line + 1, 0));
			String sequence = content.substring(Math.max(offsetOfFollowingLine - 2, 0), offsetOfFollowingLine);
			if (sequence.equals("\r\n")) {
				return "\r\n";
			} else if (sequence.endsWith("\n")) {
				return "\n";
			}
		}
		return "\n";
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
	 * Extends the regions of the given {@link TextEdit}s such that there are no gaps between them that contain only
	 * white space. Gaps which contain both white space and other characters (including comments) remain unchanged.
	 * <p>
	 * This is mainly intended for removal edits, i.e. edits with the empty string as {@link TextEdit#getNewText()
	 * replacement text}, to ensure no white space remains when removing several successive statements, declarations,
	 * etc. while not deleting existing comments, etc.
	 * <p>
	 *
	 * @return a copy of the given list of text edits with gaps closed as described above. Always returns a newly
	 *         created list, but copies of individual text edits are created only as necessary (i.e. the returned list
	 *         may contain some of the text edit instances passed in). The returned list is guaranteed to be modifiable.
	 */
	public static List<TextEdit> closeGapsIfEmpty(Document doc, List<? extends TextEdit> edits) {
		if (edits.isEmpty()) {
			return new ArrayList<>();
		}
		List<TextEdit> result = new ArrayList<>();
		int size = edits.size();
		for (int i = 0; i + 1 < size; i++) {
			TextEdit curr = edits.get(i);
			TextEdit next = edits.get(i + 1);
			Position currEnd = curr.getRange().getEnd();
			Position nextStart = next.getRange().getStart();
			String gap = doc.getSubstring(new Range(currEnd, nextStart));
			if (gap.isBlank()) {
				Position newEnd = new Position(nextStart.getLine(), nextStart.getCharacter());
				curr = new TextEdit(new Range(curr.getRange().getStart(), newEnd), curr.getNewText());
			}
			result.add(curr);
		}
		result.add(edits.get(size - 1));
		return result;
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

}
