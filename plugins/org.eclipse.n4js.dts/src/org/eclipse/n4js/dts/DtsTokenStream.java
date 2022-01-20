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

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

/**
 *
 */
public class DtsTokenStream extends CommonTokenStream {

	/** Constructor */
	public DtsTokenStream(TokenSource tokenSource) {
		super(tokenSource);
	}

	public Token getPreviousJsDocToken(Token index) {
		return getPreviousJsDocToken(index.getTokenIndex());
	}

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

}
