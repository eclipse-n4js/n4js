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

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

/**
 * <p>
 * A specialized {@link XtextTokenStream} that does not fill the complete token buffer up-front but waits on each
 * occurrence of a {@code '/' DIV} character.
 * </p>
 *
 * <p>
 * The parser will afterwards announce whether a regular expression or a binary operation is expected.
 * </p>
 */
public class LazyTokenStream extends XtextTokenStream {

	/**
	 * Create a new stream with the given source.
	 */
	public LazyTokenStream(TokenSource tokenSource, ITokenDefProvider tokenDefProvider) {
		super(tokenSource, tokenDefProvider);
		tokens = new JSTokenList();
	}

	/**
	 * An implementation of {@link #toString()} that does not fill the buffer but uses the string representation of the
	 * underlying {@link TokenSource source}.
	 */
	@Override
	public String toString() {
		return tokenSource.toString();
	}

	/**
	 * Fills the buffer but stops on a div or div-equals token.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void fillBuffer() {
		int oldP = p;
		int index = tokens.size();
		Token t = tokenSource.nextToken();
		while (t != null && t.getType() != CharStream.EOF) {
			// stop on div, div-equal and right curly brace tokens.
			int type = t.getType();
			if (type == InternalN4JSLexer.Solidus || type == InternalN4JSLexer.SolidusEqualsSign
					|| type == InternalN4JSLexer.RightCurlyBracket) {
				t.setTokenIndex(index);
				tokens.add(t);
				index++;
				break;
			}
			boolean discard = false;
			// is there a channel override for token type?
			if (channelOverrideMap != null) {
				Integer channelI = (Integer) channelOverrideMap.get(Integer.valueOf(type));
				if (channelI != null) {
					t.setChannel(channelI.intValue());
				}
			}
			if (discardSet != null &&
					discardSet.contains(Integer.valueOf(type))) {
				discard = true;
			} else if (discardOffChannelTokens && t.getChannel() != this.channel) {
				discard = true;
			}
			if (!discard) {
				t.setTokenIndex(index);
				tokens.add(t);
				index++;
			}
			t = tokenSource.nextToken();
		}
		// leave p pointing at first token on channel
		p = oldP == -1 ? 0 : oldP;
		p = skipOffTokenChannels(p);
	}

	/**
	 * Drop the given marker but keep the current pointer where it is.
	 */
	public void rewindAndKeepIndex(int marker) {
		int current = this.p;
		rewind(marker);
		this.p = current;
	}

	/**
	 * Continue filling the buffer.
	 */
	public void fillBuffer(int lookAhead) {
		if ((p + lookAhead - 1) >= tokens.size()) {
			fillBuffer();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Token LT(int k) {
		fillBuffer(k);
		return super.LT(k);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(int start, int stop) {
		if (start < 0 || stop < 0) {
			return null;
		}
		CommonToken startToken = (CommonToken) tokens.get(start);
		CommonToken stopToken = (CommonToken) tokens.get(stop);
		CharStream charStream = ((Lexer) tokenSource).getCharStream();
		String result = charStream.toString().substring(startToken.getStartIndex(), stopToken.getStopIndex());
		return result;
	}
}
