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
import org.eclipse.xtext.formatting2.internal.CommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.ILineRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

/** workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=488767 */
@SuppressWarnings("restriction")
public class FixedMultilineCommentReplacer extends CommentReplacer {

	private final boolean multiline;

	/** */
	public FixedMultilineCommentReplacer(IComment comment) {
		super(comment);
		this.multiline = comment.isMultiline();
	}

	@Override
	public void configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
		if (multiline) {
			enforceNewLine(leading);
			enforceNewLine(trailing);
		} else {
			enforceSingleSpace(leading);
			enforceSingleSpace(trailing);
		}
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
		String newIndentation = indentationString;
		for (int i = 1; i < lines.size() - 1; i++) {
			ITextSegment line = lines.get(i);
			String text = line.getText();
			ITextSegment target;
			if (text.startsWith(oldIndentation))
				target = access.regionForOffset(line.getOffset(), oldIndentation.length());
			else
				target = access.regionForOffset(line.getOffset(), 0);
			context.addReplacement(target.replaceWith(newIndentation));
		}
		if (lines.size() > 1) {
			ILineRegion line = lines.get(lines.size() - 1);
			context.addReplacement(line.getIndentation().replaceWith(indentationString + " "));
		}
		return context;
	}

	/** */
	protected void enforceNewLine(WhitespaceReplacer replacer) {
		if (replacer.getRegion().getOffset() <= 0)
			return;
		Integer min = replacer.getFormatting().getNewLineMin();
		if (min == null || min < 1)
			replacer.getFormatting().setNewLinesMin(1);
	}

	/** */
	protected void enforceSingleSpace(WhitespaceReplacer replacer) {
		if (replacer.getRegion().getOffset() <= 0)
			return;
		String space = replacer.getFormatting().getSpace();
		if (space == null || space.length() < 1)
			replacer.getFormatting().setSpace(" ");
	}

}
