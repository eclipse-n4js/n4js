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
package org.eclipse.n4js.ui.changes;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.AbstractDocument;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import org.eclipse.n4js.ui.utils.DocumentUtilN4;

/**
 * Collection of high-level convenience methods for creating {@link IChange}s.
 * <p>
 * By convention, all methods take an {@link IXtextDocument} as first parameter and return an instance of IChange. The
 * methods in this class must <b>not</b> change the provided document directly but instead perform all text
 * manipulations via the returned IChange.
 */
public class ChangeProvider {

	// TODO check if this might be a performance issue
	private static URI getURI(IXtextDocument doc) {
		return doc.readOnly((XtextResource resource) -> {
			return resource.getURI().trimFragment();
		});
	}

	/**
	 * Convenience method to create a simple replacement. Provided only to allow creating such simple changes in the
	 * same style as the other, high-level changes provided in this class.
	 */
	public static IChange replace(IXtextDocument doc, int offset, int length, String replacementText) {
		return new Replacement(getURI(doc), offset, length, replacementText);
	}

	/**
	 * Insert the given string as a new line above the line at the given offset. The given string need not contain any
	 * line delimiters and the offset need not point to the beginning of a line. If 'sameIndentation' is set to
	 * <code>true</code>, the new line will be indented as the line at the given offset (i.e. same leading white space).
	 */
	public static IChange insertLineAbove(IXtextDocument doc, int offset, String txt, boolean sameIndentation)
			throws BadLocationException {
		final String NL = lineDelimiter(doc, offset);
		final IRegion currLineReg = doc.getLineInformationOfOffset(offset);
		String indent = "";
		if (sameIndentation) {
			final String currLine = doc.get(currLineReg.getOffset(), currLineReg.getLength());
			int idx = 0;
			while (idx < currLine.length() && Character.isWhitespace(currLine.charAt(idx))) {
				idx++;
			}
			indent = currLine.substring(0, idx);
		}
		return new Replacement(getURI(doc), currLineReg.getOffset(), 0, indent + txt + NL);
	}

	/**
	 * Determine the current Line delimiter at the given offset.
	 *
	 * @param doc
	 *            the document to query for the newline sequence.
	 * @param offset
	 *            absolute character position
	 * @return the new line sequence used in the line containing offset
	 * @throws BadLocationException
	 *             in case offset doesn't fit a legal position
	 */
	public static String lineDelimiter(IXtextDocument doc, int offset) throws BadLocationException {
		String nl = doc.getLineDelimiter(doc.getLineOfOffset(offset));
		if (nl == null) {
			if (doc instanceof AbstractDocument) {
				nl = ((AbstractDocument) doc).getDefaultLineDelimiter();
			}
		}
		return nl;
	}

	/**
	 * Same as {@link #removeText(IXtextDocument, int, int, boolean)}, but the text region to remove can be specified by
	 * providing a semantic object, i.e. AST node.
	 */
	public static IChange removeSemanticObject(IXtextDocument doc, EObject obj, boolean removeEntireLineIfEmpty)
			throws BadLocationException {
		if (obj == null)
			return IChange.IDENTITY;
		return removeNode(doc, NodeModelUtils.findActualNodeFor(obj), removeEntireLineIfEmpty);
	}

	/**
	 * Same as {@link #removeText(IXtextDocument, int, int, boolean)}, but the text region to remove can be specified by
	 * providing a parse tree node.
	 */
	public static IChange removeNode(IXtextDocument doc, INode node, boolean removeEntireLineIfEmpty)
			throws BadLocationException {
		if (node == null)
			return IChange.IDENTITY;
		return removeText(doc, node.getOffset(), node.getLength(), removeEntireLineIfEmpty);
	}

	/**
	 * Removes text of the given length at the given offset. If 'removeEntireLineIfEmpty' is set to <code>true</code>,
	 * the line containing the given text region will be deleted entirely iff the change would leave the line empty
	 * (i.e. contains only white space) <em>after</em> the removal of the text region.
	 */
	public static IChange removeText(IXtextDocument doc, int offset, int length, boolean removeEntireLineIfEmpty)
			throws BadLocationException {
		if (!removeEntireLineIfEmpty) {
			// simple
			return new Replacement(getURI(doc), offset, length, "");
		} else {
			// get entire line containing the region to be removed
			// OR in case the region spans multiple lines: get *all* lines affected by the removal
			final IRegion linesRegion = DocumentUtilN4.getLineInformationOfRegion(doc, offset, length, true);
			final String lines = doc.get(linesRegion.getOffset(), linesRegion.getLength());
			// simulate the removal
			final int offsetRelative = offset - linesRegion.getOffset();
			final String lineAfterRemoval = removeSubstring(lines, offsetRelative, length);
			final boolean isEmptyAfterRemoval = lineAfterRemoval.trim().isEmpty();
			if (isEmptyAfterRemoval) {
				// remove entire line (or in case the removal spans multiple lines: remove all affected lines entirely)
				return new Replacement(getURI(doc),
						linesRegion.getOffset(), linesRegion.getLength(), "");
			} else {
				// just remove the given text region
				return new Replacement(getURI(doc), offset, length, "");
			}
		}
	}

	/**
	 * Delete line containing the given offset (the offset need not point to the beginning of the line). Will do nothing
	 * if 'deleteOnlyIfEmpty' is set to <code>true</code> and the given line is non-empty, i.e. contains characters
	 * other than {@link Character#isWhitespace(char) white space}.
	 */
	public static IChange deleteLine(IXtextDocument doc, int offset, boolean deleteOnlyIfEmpty)
			throws BadLocationException {
		if (deleteOnlyIfEmpty && !DocumentUtilN4.getLine(doc, offset).trim().isEmpty())
			return IChange.IDENTITY;
		final int lineNo = doc.getLineOfOffset(offset);
		final int currLineOffset = doc.getLineOffset(lineNo);
		final int currLineLen = doc.getLineLength(lineNo); // n.b.: includes line delimiter!
		return new Replacement(getURI(doc), currLineOffset, currLineLen, "");
	}

	/**
	 * Removes the substring at the given region. The region may extend beyond the given string's bounds.
	 */
	private static String removeSubstring(String str, int beginIndex, int length) {
		final int len = str.length();
		final String part1 = str.substring(0, Math.max(beginIndex, 0));
		final String part2 = str.substring(Math.min(beginIndex + length, len), len);
		return part1 + part2;
	}
}
