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

import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.CommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;

/**
 * Turned off replacer. Will not alter the comment.
 */
@SuppressWarnings("restriction")
public class OffMultilineCommentReplacer extends CommentReplacer {

	private final boolean noIndent;

	/**
	 * Ctor for comment with indentation.
	 *
	 * @param comment
	 *            to be processed
	 */
	public OffMultilineCommentReplacer(IComment comment) {
		this(comment, false);
	}

	/**
	 *
	 * @param comment
	 *            comment not to alter.
	 * @param noIndent
	 *            if true Comment will not be indented.
	 */
	public OffMultilineCommentReplacer(IComment comment, boolean noIndent) {
		super(comment);
		this.noIndent = noIndent;
	}

	@Override
	public ITextReplacerContext createReplacements(ITextReplacerContext context) {
		return context;
	}

	@Override
	public void configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
		if (noIndent) {
			leading.getFormatting().setNoIndentation(true);
			trailing.getFormatting().setNoIndentation(true);
		}
	}

}
