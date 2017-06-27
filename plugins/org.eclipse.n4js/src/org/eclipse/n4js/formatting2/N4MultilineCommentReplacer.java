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
package org.eclipse.n4js.formatting2;

import java.util.List;

import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.MultilineCommentReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.ILineRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

/** Overridden to prevent whitespaces after prefix-character if lines are empty. */
@SuppressWarnings("restriction")
public class N4MultilineCommentReplacer extends MultilineCommentReplacer {

	private final boolean multiline;
	private final char prefix;

	/** @see MultilineCommentReplacer#MultilineCommentReplacer(IComment, char) */
	public N4MultilineCommentReplacer(IComment comment, char prefix) {
		super(comment, prefix);
		this.prefix = prefix;
		multiline = comment.isMultiline();

	}

	@Override
	public ITextReplacerContext createReplacements(ITextReplacerContext context) {
		if (!multiline)
			return context;
		IComment comment = getComment();
		ITextRegionAccess access = comment.getTextRegionAccess();
		List<ILineRegion> lines = comment.getLineRegions();
		String oldIndentation = lines.get(0).getIndentation().getText();
		String indentationString = context.getIndentationString();
		String newIndentation = indentationString + " " + prefix /* only change: */ /* + " " */;
		String newIndentation2 = newIndentation + " ";
		for (int i = 1; i < lines.size() - 1; i++) {
			ITextSegment line = lines.get(i);
			String text = line.getText();
			int prefixOffset = prefixOffset(text);
			ITextSegment target;
			if (prefixOffset >= 0)
				target = access.regionForOffset(line.getOffset(), prefixOffset + 1);
			else if (text.startsWith(oldIndentation))
				target = access.regionForOffset(line.getOffset(),
						oldIndentation.length() + nonWhiteSpaceOffset(text.substring(oldIndentation.length())));
			else
				target = access.regionForOffset(line.getOffset(), /* 0 */ nonWhiteSpaceOffset(text));

			if (line.getEndOffset() - target.getEndOffset() > 0) { // content left in line
				context.addReplacement(target.replaceWith(newIndentation2));
			} else { // no content left
				context.addReplacement(target.replaceWith(newIndentation));
			}
		}
		if (lines.size() > 1) {
			ILineRegion line = lines.get(lines.size() - 1);
			context.addReplacement(line.getIndentation().replaceWith(indentationString + " "));
		}
		return context;
	}

	/** Gives offset for first non-white character, returns length of string if all chars are white */
	protected int nonWhiteSpaceOffset(String string) {
		for (int i = 0; i < string.length(); i++) {
			char charAt = string.charAt(i);
			if (!Character.isWhitespace(charAt))
				return i;
		}
		// All White
		return string.length();
	}

}
