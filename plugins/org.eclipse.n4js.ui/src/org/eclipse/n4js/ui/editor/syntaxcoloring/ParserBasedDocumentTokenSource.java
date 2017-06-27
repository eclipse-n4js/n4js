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

import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.xtext.ui.editor.model.BacktrackingLexerDocumentTokenSource;

import com.google.inject.Inject;

/**
 */
public class ParserBasedDocumentTokenSource extends BacktrackingLexerDocumentTokenSource implements PseudoTokens {

	@Inject
	private HighlightingParser highlightingParser;

	@Override
	protected TokenSource createTokenSource(String string) {
		List<Token> tokens = highlightingParser.getTokens(string);
		Iterator<Token> iter = tokens.iterator();
		return new TokenSource() {

			@Override
			public Token nextToken() {
				if (iter.hasNext()) {
					return iter.next();
				}
				return Token.EOF_TOKEN;
			}

			@Override
			public String getSourceName() {
				return "Text: " + string;
			}
		};
	}

	/**
	 * JSDoc comments are identified by the lexer as normal ML comments to simplify the ASI code. If a comment starts
	 * with the sequence @çode{/**} it is remapped to a JS Doc token.
	 */
	@Override
	protected TokenInfo createTokenInfo(CommonToken token) {
		if (token.getType() == InternalN4JSParser.RULE_ML_COMMENT) {
			String text = token.getText();
			if (text.length() > 4 && text.startsWith("/**") && text.charAt(3) != '*') {
				CommonToken jsDoc = new CommonToken(token);
				jsDoc.setType(JS_DOC_TOKEN);
				return super.createTokenInfo(jsDoc);
			}
		}
		return super.createTokenInfo(token);
	}
}
