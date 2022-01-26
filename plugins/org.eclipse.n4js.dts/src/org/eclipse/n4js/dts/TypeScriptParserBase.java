/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 by studentmain (initial contribution)
 * Copyright (c) 2022 by NumberFour AG (contributor -> overall restructuring and d.ts support)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.eclipse.n4js.dts;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

/**
 * All parser methods that used in grammar (p, prev, notLineTerminator, etc.) should start with lower case char similar
 * to parser rules.
 */
public abstract class TypeScriptParserBase extends Parser {

	/** Constructor */
	public TypeScriptParserBase(TokenStream input) {
		super(input);
	}

	/** Short form for {@link #prev(String)} */
	protected boolean p(String str) {
		return prev(str);
	}

	/** Whether the previous token value equals to @param str */
	protected boolean prev(String str) {
		return _input.LT(-1).getText().equals(str);
	}

	/** Short form for {@link #next(String)} */
	protected boolean n(String str) {
		return next(str);
	}

	/** Whether the next token value equals to @param str */
	protected boolean next(String str) {
		return _input.LT(1).getText().equals(str);
	}

	/** @return {@code true} iff */
	protected boolean notLineTerminator() {
		return !here(TypeScriptParser.LineTerminator);
	}

	/**
	 * @return {@code true} iff the next token is neither an {@link TypeScriptParser#notOpenBraceAndNotFunction()} nor a
	 *         {@link TypeScriptParser#arrowFunctionBody()}
	 */
	protected boolean notOpenBraceAndNotFunction() {
		int nextTokenType = _input.LT(1).getType();
		return nextTokenType != TypeScriptParser.OpenBrace && nextTokenType != TypeScriptParser.Function;
	}

	/** @return {@code true} iff the next token is {@link TypeScriptParser#CloseBrace} */
	protected boolean closeBrace() {
		return _input.LT(1).getType() == TypeScriptParser.CloseBrace;
	}

	/**
	 * Returns {@code true} iff on the current index of the parser's token stream a token of the given {@code type}
	 * exists on the {@code HIDDEN} channel.
	 *
	 * @param type
	 *            the type of the token on the {@code HIDDEN} channel to check.
	 *
	 * @return {@code true} iff on the current index of the parser's token stream a token of the given {@code type}
	 *         exists on the {@code HIDDEN} channel.
	 */
	private boolean here(final int type) {
		// Get the token ahead of the current index.
		int possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 1;
		Token ahead = _input.get(possibleIndexEosToken);

		// Check if the token resides on the HIDDEN channel and if it's of the
		// provided type.
		return (ahead.getChannel() == Lexer.HIDDEN) && (ahead.getType() == type);
	}

	/**
	 * Returns {@code true} iff on the current index of the parser's token stream a token exists on the {@code HIDDEN}
	 * channel which either is a line terminator, or is a multi line comment that contains a line terminator.
	 *
	 * @return {@code true} iff on the current index of the parser's token stream a token exists on the {@code HIDDEN}
	 *         channel which either is a line terminator, or is a multi line comment that contains a line terminator.
	 */
	protected boolean lineTerminatorAhead() {

		// Get the token ahead of the current index.
		int possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 1;
		Token ahead = _input.get(possibleIndexEosToken);

		if (ahead.getChannel() != Lexer.HIDDEN) {
			// We're only interested in tokens on the HIDDEN channel.
			return false;
		}

		if (ahead.getType() == TypeScriptParser.LineTerminator) {
			// There is definitely a line terminator ahead.
			return true;
		}

		if (ahead.getType() == TypeScriptParser.WhiteSpaces) {
			// Get the token ahead of the current whitespaces.
			possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 2;
			ahead = _input.get(possibleIndexEosToken);
		}

		// Get the token's text and type.
		String text = ahead.getText();
		int type = ahead.getType();

		// Check if the token is, or contains a line terminator.
		return (type == TypeScriptParser.MultiLineComment && (text.contains("\r") || text.contains("\n"))) ||
				(type == TypeScriptParser.LineTerminator);
	}
}