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
package org.eclipse.n4js.ui.utils;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.ui.editor.model.DocumentUtil;

/**
 * Utility methods for manipulating {@link IDocument}s. This extends the standard Xtext utility class
 * {@link DocumentUtil}.
 */
public class DocumentUtilN4 extends DocumentUtil {

	/**
	 * Similar to {@link IDocument#getLineInformationOfOffset(int)}, but the client can provide a text region instead of
	 * only an offset. If the given region spans multiple lines, all affected lines will be returned, i.e. entire line
	 * containing beginning of region, all lines contained in the region, and entire line containing the end of the
	 * region.
	 */
	public static IRegion getLineInformationOfRegion(IDocument doc, int offset, int length,
			boolean includeLineDelimiterOfLastLine) throws BadLocationException {
		// get the line containing the beginning of the given text region
		final int firstLineNo = doc.getLineOfOffset(offset);
		// get the line containing the end of the given text region
		// (may be the same line if removal does not span multiple lines)
		final int lastLineNo = doc.getLineOfOffset(offset + length);
		// compute result
		final int startOffset = doc.getLineOffset(firstLineNo);
		final int endOffset = doc.getLineOffset(lastLineNo) + (includeLineDelimiterOfLastLine ?
				doc.getLineLength(lastLineNo) // includes line delimiters!
				: doc.getLineInformation(lastLineNo).getLength()); // does *not* include line delimiters!
		return new Region(
				startOffset,
				endOffset - startOffset);
	}

	/**
	 * Returns the line containing the given offset without any line delimiters. The offset need not point to the
	 * beginning of the line.
	 */
	public static String getLine(IDocument doc, int offset) throws BadLocationException {
		final IRegion reg = doc.getLineInformationOfOffset(offset);
		return doc.get(reg.getOffset(), reg.getLength());
	}

	/**
	 * Returns indentation, i.e. leading white space characters, of the line at the given offset. The offset need not
	 * point to the beginning of the line.
	 */
	public static String getIndent(IDocument doc, int offset) throws BadLocationException {
		return getIndent(doc, doc.getLineInformationOfOffset(offset));
	}

	/**
	 * Returns indentation, i.e. leading white space characters, of the line at the given region. Argument for
	 * 'lineRegion' must cover the entire line excluding any line delimiters (i.e. exactly as returned by
	 * {@link IDocument#getLineInformationOfOffset(int)}.
	 */
	public static String getIndent(IDocument doc, IRegion lineRegion) throws BadLocationException {
		final String currLine = doc.get(lineRegion.getOffset(), lineRegion.getLength());
		int idx = 0;
		while (idx < currLine.length() && Character.isWhitespace(currLine.charAt(idx))) {
			idx++;
		}
		return currLine.substring(0, idx);
	}
}
