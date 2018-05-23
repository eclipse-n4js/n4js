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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_HEAD;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_MIDDLE;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.xtext.ui.editor.model.ILexerTokenRegion;
import org.eclipse.xtext.ui.editor.syntaxcoloring.TokenScanner;

/**
 */
public class TemplateAwareTokenScanner extends TokenScanner {

	/**
	 * Creates a new template token for the given token id. A template token is in fact a list of tokens for the
	 * colorer, e.g a single terminal on the grammar level may yield various tokens for the syntax coloring.
	 */
	protected TemplateToken createTemplateToken(int id, ILexerTokenRegion token) {
		int tokenOffset = token.getOffset();
		int tokenLength = token.getLength();
		TemplateToken result = new TemplateToken();
		switch (id) {
		case RULE_TEMPLATE_HEAD:
			result.delimiter = false; // ranges defined by 'offsets' and 'lengths' do *not* start with a delimiter
			result.offsets = new int[] { tokenOffset, tokenOffset + tokenLength - 2 };
			result.lengths = new int[] { tokenLength - 2, 2 };
			break;
		case RULE_TEMPLATE_MIDDLE:
			if (tokenLength == 2) {
				result.delimiter = true; // ranges defined by 'offsets' and 'lengths' start with a delimiter
				result.offsets = new int[] { tokenOffset };
				result.lengths = new int[] { tokenLength };
			} else {
				result.delimiter = false; // ranges defined by 'offsets' and 'lengths' do *not* start with a delimiter
				result.offsets = new int[] { tokenOffset, tokenOffset + tokenLength - 2 };
				result.lengths = new int[] { tokenLength - 2, 2 };
			}
		}
		return result;
	}

	private class TemplateToken {

		private int index = -1;
		private int[] offsets;
		private int[] lengths;
		private boolean delimiter;

		public int getTokenLength() {
			return lengths[index];
		}

		public int getTokenOffset() {
			return offsets[index];
		}

		public boolean hasNext() {
			return index < offsets.length - 1;
		}

		public IToken nextToken() {
			IToken result = createToken();
			index++;
			delimiter = !delimiter;
			return result;
		}

		protected IToken createToken() {
			String attributeId = delimiter
					? HighlightingConfiguration.TEMPLATE_DELIMITER_ID
					: HighlightingConfiguration.TEMPLATE_ID;
			Token token = new Token(getAttribute(attributeId));
			return token;
		}
	}

	private TemplateToken currentTemplateTextToken;

	@Override
	public void setRange(IDocument document, int offset, int length) {
		currentTemplateTextToken = null;
		super.setRange(document, offset, length);
	}

	@Override
	public IToken nextToken() {
		if (currentTemplateTextToken != null) {
			if (currentTemplateTextToken.hasNext())
				return currentTemplateTextToken.nextToken();
			else
				currentTemplateTextToken = null;
		}
		if (!getIterator().hasNext())
			return Token.EOF;
		ILexerTokenRegion next = getIterator().next();
		int tokenType = next.getLexerTokenType();
		switch (tokenType) {
		case RULE_TEMPLATE_HEAD:
		case RULE_TEMPLATE_MIDDLE: {
			currentTemplateTextToken = createTemplateToken(tokenType, next);
			return currentTemplateTextToken.nextToken();
		}
		default:
			setCurrentToken(next);
			return createToken(next);
		}
	}

	@Override
	public int getTokenLength() {
		if (currentTemplateTextToken != null) {
			return currentTemplateTextToken.getTokenLength();
		}
		return super.getTokenLength();
	}

	@Override
	public int getTokenOffset() {
		if (currentTemplateTextToken != null) {
			return currentTemplateTextToken.getTokenOffset();
		}
		return super.getTokenOffset();
	}

}
