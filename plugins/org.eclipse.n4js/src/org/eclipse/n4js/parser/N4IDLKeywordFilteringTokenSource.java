/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;

/**
 * A delegating token-source that filters all N4IDL-specific keywords by mapping them to RULE_IDENTIFIER tokens.
 *
 * @see InternalN4JSLexer
 */
public class N4IDLKeywordFilteringTokenSource implements TokenSource {

	private final TokenSource delegate;

	/** Instantiates a new {@link N4IDLKeywordFilteringTokenSource} */
	public N4IDLKeywordFilteringTokenSource(TokenSource delegate) {
		this.delegate = delegate;
	}

	@Override
	public Token nextToken() {
		Token token = delegate.nextToken();

		// map Migrate operator keyword to RULE_IDENTIFIER
		if (token.getType() == InternalN4JSLexer.Migrate) {
			token.setType(InternalN4JSLexer.RULE_IDENTIFIER);
		}

		return token;
	}

	@Override
	public String getSourceName() {
		return delegate.getSourceName();
	}

	@Override
	public String toString() {
		// Delegate toString, since this is used to obtain text of
		// tokens by offset and length
		return delegate.toString();
	}
}
