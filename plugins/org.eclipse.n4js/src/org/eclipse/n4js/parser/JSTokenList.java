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
package org.eclipse.n4js.parser;

import java.util.ArrayList;

import org.antlr.runtime.Token;
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser;

/**
 * <p>
 * This internal class effectively changes the handling of hidden tokens. Rather than changing the channel on each
 * request (as done by the default internal class TokenList of {@link org.eclipse.xtext.parser.antlr.XtextTokenStream
 * XtextTokenStream}), we do this only when the list of tokens is created. Later processing in the parser will alter the
 * channel actively which would be rendered useless if it is reset each time on an invocation of {@link #get(int)} (as
 * done by the default token list).
 * </p>
 */
public class JSTokenList extends ArrayList<Token> {

	JSTokenList() {
		super(500);
	}

	/**
	 * Adds token and adjust channel.
	 */
	@Override
	public boolean add(Token tok) {
		super.add(tok);
		int type = tok.getType();
		if (type == InternalN4JSParser.EqualsSignGreaterThanSign) {
			// The arrow expression may not follow a semicolon thus we promote those here
			// to the default channel if they precede the arrow => operator
			for (int i = size() - 2; i >= 0; i--) {
				Token prev = get(i);
				if (prev.getChannel() == Token.HIDDEN_CHANNEL) {
					if (SemicolonInjectionHelper.isSemicolonEquivalent(prev)) {
						prev.setChannel(Token.DEFAULT_CHANNEL);
						break;
					}
				} else {
					break;
				}
			}
		} else if (type == InternalN4JSParser.RULE_EOL
				|| type == InternalN4JSParser.RULE_ML_COMMENT
				|| type == InternalN4JSParser.RULE_WS
				|| type == InternalN4JSParser.RULE_SL_COMMENT) {
			tok.setChannel(Token.HIDDEN_CHANNEL);
		} else {
			tok.setChannel(Token.DEFAULT_CHANNEL);
		}
		return true;
	}

	/**
	 * No need to eagerly clear the underlying array.
	 */
	@Override
	public void clear() {
		if (isEmpty())
			return;
		super.clear();
	}
}
