/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.apache.log4j.Logger;
import org.eclipse.n4js.dts.utils.TripleSlashDirective;

/**
 * Overwritten to add methods for retrieving tokens from the channel {@link TypeScriptLexer#JSDocComment}
 */
public class DtsTokenStream extends CommonTokenStream {

	private final static Logger LOG = Logger.getLogger(DtsTokenStream.class);

	/** Constructor */
	public DtsTokenStream(TokenSource tokenSource) {
		super(tokenSource);
	}

	/** @return the nearest previous token in channel {@link TypeScriptLexer#JSDOC} from the given token */
	public Token getPreviousJsDocToken(Token startToken) {
		return getPreviousJsDocToken(startToken.getTokenIndex());
	}

	/** @return the nearest previous token in channel {@link TypeScriptLexer#JSDOC} from the given token index */
	public Token getPreviousJsDocToken(int idx) {
		sync(idx);
		if (idx >= size()) {
			// the EOF token is on every channel
			return null;
		}

		boolean encounteredNL = false;

		while (idx >= 0) {
			Token token = tokens.get(idx);
			int tokenChannel = token.getChannel();
			int tokenType = token.getType();
			if (tokenType == Token.EOF) {
				return null;
			}
			if (tokenChannel == TypeScriptLexer.JSDOC) {
				return token;
			}
			if (encounteredNL && tokenChannel == Lexer.DEFAULT_TOKEN_CHANNEL) {
				return null;
			}
			if (tokenType == TypeScriptLexer.LineTerminator || tokenType == TypeScriptLexer.SemiColon) {
				encounteredNL = true;
			}

			idx--;
		}

		return null;
	}

	/** @return all {@link TripleSlashDirective triple-slash directive}s given in the source code or an empty list. */
	public List<TripleSlashDirective> getTripleSlashDirectives() {
		List<TripleSlashDirective> result = new ArrayList<>();

		int idx = 0;
		while (idx < size()) {
			Token token = tokens.get(idx);
			int tokenType = token.getType();
			int tokenChannel = token.getChannel();
			if (tokenType == Token.EOF) {
				break;
			}
			if (tokenChannel != Lexer.HIDDEN && tokenChannel != TypeScriptLexer.JSDOC) {
				break;
			}
			String text = token.getText().trim();
			if (text.startsWith("///")) {
				String directiveStr = text.substring(3).trim();
				TripleSlashDirective directive = TripleSlashDirective.parse(directiveStr);
				if (directive != null) {
					result.add(directive);
				} else {
					LOG.error("cannot parse triple-slash directive: " + directiveStr);
				}
			}
			++idx;
		}

		return result;
	}
}
