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
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.eclipse.n4js.parser.antlr.lexer.InternalN4JSLexer;

/**
 * <p>
 * A customized lexer that is aware of regular expression literals. As it encounters a {@code '/'} character, it will
 * wait for the parser to announce its expectation by means of {@link #setInRegularExpression()} or not. If a regular
 * expression is expected, the next parsed token will be a {@link #mRULE_ACTUAL_REGEX_TAIL() RULE_ACTUAL_REGEX_TAIL}
 * which cloaked as the expected token type {@link #RULE_REGEX_TAIL}.
 * </p>
 */
public class RegExLiteralAwareLexer extends InternalN4JSLexer {

	/**
	 * <p>
	 * Default constructor. Initializes a new lexer without a {@link CharStream}.
	 * </p>
	 */
	public RegExLiteralAwareLexer() {
		super(null);
	}

	/**
	 * <p>
	 * Resets the current state of the lexer, especially the flags {@link #inRegularExpression}. and
	 * {@link #inTemplateSegment}
	 * </p>
	 */
	@Override
	public void reset() {
		super.reset();
		inRegularExpression = false;
		inTemplateSegment = false;
		inJsxChildren = false;
	}

	/**
	 * Announce the next token to be expected as a regular expression tail.
	 */
	public void setInRegularExpression() {
		inRegularExpression = true;
	}

	/**
	 * Announce the next token to be expected as a part of a template literal
	 */
	public void setInTemplateSegment() {
		inTemplateSegment = true;
	}

	/**
	 * Announce the next token to be a JSX_TEXT token.
	 */
	public void setInJsxChildren() {
		inJsxChildren = true;
	}

	/**
	 * <p>
	 * If we expect a {@link #inRegularExpression regular expression}, we check whether we are at the end of the file or
	 * at the end of the line, otherwise a {@link #mRULE_ACTUAL_REGEX_TAIL()} is consumed.
	 * </p>
	 */
	@Override
	public Token nextToken() {
		if (inRegularExpression) {
			clearAndResetTokenState();
			switch (input.LA(1)) {
			// end of file
			case CharStream.EOF:
				inRegularExpression = false;
				return Token.EOF_TOKEN;
			// line break
			case 0x000A:
			case 0x000D:
			case 0x2028:
			case 0x2029:
				inRegularExpression = false;
				return super.nextToken();
			}
			try {
				mRULE_ACTUAL_REGEX_TAIL();
				if (this.state.token == null) {
					emit();
					this.state.token.setType(RULE_REGEX_TAIL);
					inRegularExpression = false;
				}
				return this.state.token;
			} catch (RecognitionException re) {
				// this is basically impossible since the regex tail consumes any char except for line breaks
				throw new RuntimeException("Unexpected recognition problem for\n" + input, re);
			}
		} else if (inTemplateSegment) {
			clearAndResetTokenState();
			switch (input.LA(1)) {
			// end of file
			case CharStream.EOF:
				inTemplateSegment = false;
				return Token.EOF_TOKEN;
			}
			try {
				mRULE_ACTUAL_TEMPLATE_END();
				if (this.state.token == null) {
					emit();
					Token token = this.state.token;
					String tokenText = token.getText();
					if (tokenText.endsWith("${") && !tokenText.endsWith("\\${")) {
						token.setType(RULE_TEMPLATE_MIDDLE);
					} else {
						token.setType(RULE_TEMPLATE_END);
					}
					inTemplateSegment = false;
				}
				return this.state.token;
			} catch (RecognitionException re) {
				// this is basically impossible since the regex tail consumes any char except for line breaks
				throw new RuntimeException("Unexpected recognition problem for\n" + input, re);
			}
		} else if (inJsxChildren) {
			clearAndResetTokenState();
			switch (input.LA(1)) {
			case '<':
			case '>':
			case '{':
			case '}':
				inJsxChildren = false;
				return super.nextToken();
			case CharStream.EOF:
				inJsxChildren = false;
				return Token.EOF_TOKEN;
			default:
				try {
					mRULE_JSX_TEXT_FRAGMENT();
					if (this.state.token == null) {
						emit();
						this.state.token.setType(RULE_JSX_TEXT);
						inJsxChildren = false;
					}
					return this.state.token;
				} catch (RecognitionException re) {
					// this is basically impossible since the regex tail consumes any char except for line breaks
					throw new RuntimeException("Unexpected recognition problem for\n" + input, re);
				}
			}

		} else {
			// check for a sequence `?.[digit]` which must not yield `?.`. `[digit]` but `?` `.[digit]`
			if (input.LA(1) == '?' && input.LA(2) == '.') {
				int propablyDigit = input.LA(3);
				if (propablyDigit >= '0' && propablyDigit <= '9') {
					try {
						clearAndResetTokenState();
						mQuestionMark();
						if (this.state.token == null) {
							emit();
						}
						return this.state.token;
					} catch (RecognitionException re) {
						// this is basically impossible since the regex tail consumes any char except for line breaks
						throw new RuntimeException("Unexpected recognition problem for\n" + input, re);
					}
				}
			}
			return super.nextToken();
		}
	}

	private void clearAndResetTokenState() {
		this.state.token = null;
		this.state.channel = Token.DEFAULT_CHANNEL;
		this.state.tokenStartCharIndex = input.index();
		this.state.tokenStartCharPositionInLine = input.getCharPositionInLine();
		this.state.tokenStartLine = input.getLine();
		this.state.text = null;
	}

	/**
	 * <p>
	 * Use the string representation of the char stream, which was expected to be a custom
	 * {@link AntlrStreamWithToString}.
	 * </p>
	 */
	@Override
	public String toString() {
		return getCharStream().toString();
	}

}
