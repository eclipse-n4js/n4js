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
package org.eclipse.n4js.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A base class for simple parsers.
 */
public class SimpleParser {
	/**
	 * Represents a token position consisting of the zero based line number, the zero based offset into that line, and
	 * the zero based offset into the string being parsed.
	 */
	protected static class TokenPosition {
		/**
		 * The zero based number of the line that contains this token.
		 */
		public final int line;
		/**
		 * The zero based offset into the line at which this column begins.
		 */
		public final int column;
		/**
		 * The zero based offset at which this token begins in the input data.
		 */
		public final int offset;

		/**
		 * Creates a new instance with the given parameters.
		 *
		 * @param line
		 *            the zero based number of the line that contains the token
		 * @param column
		 *            the zero based offset at which the token begins
		 * @param offset
		 *            the zero based offset of the token into the input data
		 *
		 * @throws IllegalArgumentException
		 *             if any of the given parameters is negative
		 */
		public TokenPosition(int line, int column, int offset) {
			if (line < 0)
				throw new IllegalArgumentException("Token line number must not be negative");
			if (column < 0)
				throw new IllegalArgumentException("Token column must not be negative");
			if (offset < 0)
				throw new IllegalArgumentException("Token offset must not be negative");
			this.line = line;
			this.column = column;
			this.offset = offset;
		}

		/**
		 * Creates a new instance with the values of the given token position.
		 *
		 * @param other
		 *            the token position to take the values from
		 */
		protected TokenPosition(TokenPosition other) {
			Objects.requireNonNull(other);

			this.line = other.line;
			this.column = other.column;
			this.offset = other.offset;
		}
	}

	/**
	 * A template for tokens emitted by {@link SimpleTokenizer}.
	 */
	protected static class Token<TokenType> extends TokenPosition {
		/**
		 * The token type.
		 */
		public final TokenType type;
		/**
		 * The length of this token, i.e., the number of characters it occupies int he input data.
		 */
		public final int length;
		/**
		 * The data attached to this token. This may be null for simple one-character tokens.
		 */
		public final String data;

		/**
		 * Creates a new token with the given parameters.
		 *
		 * @param type
		 *            the token type
		 * @param position
		 *            the token position
		 * @param length
		 *            the length of the token
		 * @param data
		 *            the data attached to the token
		 *
		 * @throws IllegalArgumentException
		 *             if the given line, column, offset or length is negative
		 */
		public Token(TokenType type, TokenPosition position, int length, String data) {
			super(position);

			if (length < 0)
				throw new IllegalArgumentException("Token length must not be negative");

			this.type = Objects.requireNonNull(type);
			this.length = length;
			this.data = data;
		}

		/**
		 * Creates a new token without data that has the given type, offset, and length.
		 *
		 * @param type
		 *            the token type
		 * @param position
		 *            the token position
		 * @param length
		 *            the length of the token
		 */
		public Token(TokenType type, TokenPosition position, int length) {
			this(type, position, length, null);
		}

		/**
		 * Creates a new token of length 1 with the given type and offset.
		 *
		 * @param type
		 *            the token type
		 * @param position
		 *            the token position
		 */
		public Token(TokenType type, TokenPosition position) {
			this(type, position, 1);
		}

		/**
		 * Ensures that this token has one of the given expected types.
		 *
		 * @param expected
		 *            the list of expected types
		 * @return this token
		 * @throws SimpleParserException
		 *             if the type of this token is not contained in the given list
		 */
		@SafeVarargs // This prevents a warning since vararg parameters with generic types are potentially unsafe. In
						// our case however, we can be (mostly) sure that the user will only call this method with a
						// list of TokenType instances.
		public final Token<TokenType> expect(TokenType... expected) throws SimpleParserException {
			if (hasType(expected))
				return this;

			throw new SimpleParserException(line, column,
					"Expected " + Arrays.toString(expected) + ", but got " + toString());
		}

		/**
		 * Indicates whether this token has one of the given types.
		 *
		 * @param expected
		 *            the expected types
		 * @return <code>true</code> if this token has one of the given types and <code>false</code> otherwise
		 */
		@SafeVarargs // This prevents a warning since vararg parameters with generic types are potentially unsafe. In
		// our case however, we can be (mostly) sure that the user will only call this method with a
		// list of TokenType instances.
		public final boolean hasType(TokenType... expected) {
			for (int i = 0; i < expected.length; i++) {
				if (type == expected[i])
					return true;
			}
			return false;
		}

		/**
		 * Returns the integer value of this token by passing the token data to {@link Integer#parseInt(String)}.
		 *
		 * @return the integer value of this token
		 */
		public final int toInt() {
			return Integer.parseInt(data);
		}

		/**
		 * Returns the float value of this token by passing the token data to {@link Float#parseFloat(String)}.
		 *
		 * @return the float value of this token
		 */
		public final float toFloat() {
			return Float.parseFloat(data);
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append(type).append("[").append(offset).append(", ").append(length).append("]");
			if (data != null)
				result.append(": ").append(data);
			return result.toString();
		}
	}

	/**
	 * A base class for a simple tokenizer that provides the tokenizer's state and some convenience methods.
	 * <p>
	 * This tokenizer manages a token stack that can be used to push tokens back into the tokenizer for later retrieval.
	 * This is useful for peeking at tokens in a parser.
	 * </p>
	 */
	protected static abstract class SimpleTokenizer<TokenType> {
		/**
		 * The data that is being parsed.
		 */
		private final String data;
		/**
		 * The current parsing position.
		 */
		private int pos;
		/**
		 * The number of the line currently being processed.
		 */
		private int line;
		/**
		 * The offset of the current parsing position into the current line.
		 */
		private int column;
		/**
		 * The stack of pushed tokens.
		 *
		 * @see #pushToken(Token)
		 */
		private final LinkedList<Token<TokenType>> tokenStack = new LinkedList<>();

		/**
		 * Creates a new instance that processes the given string.
		 *
		 * @param data
		 *            the string to parse
		 */
		public SimpleTokenizer(String data) {
			this.data = Objects.requireNonNull(data);
			reset();
		}

		/**
		 * Resets the state as if the tokenizer had just been created.
		 */
		public void reset() {
			pos = 0;
			line = 0;
			column = 0;
			tokenStack.clear();
		}

		/**
		 * Returns the substring of the data starting at the given start position and ending at, but not including, the
		 * current parsing position.
		 *
		 * @see #getSubstring(TokenPosition, int)
		 *
		 * @param position
		 *            the start position
		 * @return the substring
		 */
		protected String getSubstring(TokenPosition position) {
			return getSubstring(position, getCurrentPosition());
		}

		/**
		 * Returns the substring of the data starting at the given start position and ending at, but not including, the
		 * given end position.
		 *
		 * @see #getSubstring(int, int)
		 *
		 * @param position
		 *            the start position
		 * @param endChar
		 *            the end position
		 * @return the substring
		 */
		protected String getSubstring(TokenPosition position, int endChar) {
			return data.substring(position.offset, endChar);
		}

		/**
		 * Returns the substring of the data starting at the given start position and ending at, but not including, the
		 * current parsing position.
		 *
		 * @see #getSubstring(int, int)
		 *
		 * @param startChar
		 *            the start position
		 * @return the substring
		 */
		protected String getSubstring(int startChar) {
			return getSubstring(startChar, getCurrentPosition());
		}

		/**
		 * Returns the substring of the data starting at the given start position and ending at, but not including, the
		 * given end position.
		 *
		 * @see String#substring(int, int)
		 *
		 * @param startChar
		 *            the start position
		 * @param endChar
		 *            the end position
		 * @return the substring
		 */
		protected String getSubstring(int startChar, int endChar) {
			return data.substring(startChar, endChar);
		}

		/**
		 * Creates a new token with the given position, containing as data the substring starting at the given position
		 * up to, but not including, the current parsing position.
		 *
		 * @param tokenType
		 *            the token type
		 * @param position
		 *            the token's starting position
		 * @return the token
		 */
		protected Token<TokenType> createTokenFromSubstring(TokenType tokenType, TokenPosition position) {
			return createTokenFromSubstring(tokenType, position, getCurrentPosition());
		}

		/**
		 * Creates a new token with the given position, containing as data the substring starting at the given start
		 * position up to, but not including, the given end position
		 *
		 * @param tokenType
		 *            the token type
		 * @param startPosition
		 *            the token's starting position
		 * @param endPosition
		 *            the token's end position (the position after the last character belonging to the token)
		 * @return the token
		 */
		protected Token<TokenType> createTokenFromSubstring(TokenType tokenType, TokenPosition startPosition,
				int endPosition) {
			return new Token<>(tokenType, startPosition, getTokenLength(startPosition, endPosition),
					getSubstring(startPosition, endPosition));
		}

		/**
		 * Returns the next token. If the token stack is not empty, this method pops the top element from the token
		 * stack and returns it, i.e., it returns the most recently pushed token. Otherwise, if the token stack is
		 * empty, it reads the next token via the {@link #readToken()} method and returns that.
		 *
		 * @return the next token
		 * @throws SimpleParserException
		 *             if an error occurs while parsing
		 */
		public Token<TokenType> nextToken() throws SimpleParserException {
			if (!tokenStack.isEmpty())
				return tokenStack.pop();
			return readToken();
		}

		/**
		 * Pushes the given token onto the token stack.
		 *
		 * @param token
		 *            the token to push onto the token stack
		 */
		public void pushToken(Token<TokenType> token) {
			tokenStack.push(Objects.requireNonNull(token));
		}

		/**
		 * Indicates whether this tokenizer's token stack is empty or not.
		 *
		 * @return <code>true</code> if the token stack is not empty and <code>false</code> if it is empty
		 */
		protected boolean hasPushedTokens() {
			return !tokenStack.isEmpty();
		}

		/**
		 * Reads the next token from the input.
		 *
		 * @return the next token
		 *
		 * @throws SimpleParserException
		 *             if an error occurs
		 */
		protected abstract Token<TokenType> readToken() throws SimpleParserException;

		/**
		 * Reads a token that represents a delimited string.
		 *
		 * @see #readDelimitedString(char, char)
		 *
		 * @param delimiter
		 *            the delimiter character
		 * @param escapeChar
		 *            the escape character to escape the delimiter inside of the string
		 * @param tokenType
		 *            the type of the token to create
		 * @return the newly created token
		 * @throws SimpleParserException
		 *             if an error occurs during parsing
		 */
		protected Token<TokenType> readDelimitedStringToken(char delimiter, char escapeChar, TokenType tokenType)
				throws SimpleParserException {
			final TokenPosition position = getTokenPosition();
			final String attachment = readDelimitedString(delimiter, escapeChar);
			return new Token<>(tokenType, position, attachment.length(), attachment);
		}

		/**
		 * Convenience method to read a string enclosed in the given delimiter character. This method also handles
		 * escaped delimiters. Be aware that this method assumes that the opening delimiter was already consumed! Let
		 *
		 * <pre>
		 * The quick "brown fox" jumps!
		 *            ^
		 * </pre>
		 *
		 * be the input data, and let <code>^</code> indicate the current parsing position, that is, the opening double
		 * quotation mark has already been consumed. Then calling this method with '"' as the delimiter will return the
		 * string <code>brown fox</code> and it will leave the current parsing position as follows:
		 *
		 * <pre>
		 * The quick "brown fox" jumps!
		 *                      ^
		 * </pre>
		 *
		 * @see #eof()
		 *
		 * @param delimiter
		 *            the delimiter character
		 * @param escapeChar
		 *            the escape char to escape the delimiter inside of the string
		 * @return the string between the delimiter just before the current parsing position and the next, unescaped
		 *         delimiter character
		 * @throws SimpleParserException
		 *             if this method reaches the end of the input data before finding a closing delimiter
		 */
		protected String readDelimitedString(char delimiter, char escapeChar) throws SimpleParserException {
			int startPos = getCurrentPosition();
			while (!eof()) {
				final char p = getPreviousChar(); // safe because we know that curPos > 0
				final char c = getCurrentCharAndAdvance();

				if (c == delimiter && p != escapeChar)
					return data.substring(startPos, getCurrentPosition() - 1);
			}

			throw new SimpleParserException(line, column, "Expected '" + delimiter + "', but reached end of file");
		}

		/**
		 * Reads a token that represents an integer number. For the expected format, see method
		 * {@link #readIntegerString()}.
		 *
		 * @param tokenType
		 *            the type of the token to create
		 * @return the newly created token
		 * @throws SimpleParserException
		 *             if the current parsing position is not the start of an integer of the expected format
		 */
		protected Token<TokenType> readIntegerToken(TokenType tokenType) throws SimpleParserException {
			final TokenPosition position = getTokenPosition();
			final String attachment = readIntegerString();
			return new Token<>(tokenType, position, attachment.length(), attachment);
		}

		/**
		 * Reads an integer string with the format <code>[+-]?\d+</code>. The string of numbers is delimited by any
		 * non-digit character or the end of file.
		 *
		 * @return the integer string
		 *
		 * @throws SimpleParserException
		 *             if the current parsing position is not the start of an integer of the expected format
		 */
		protected String readIntegerString() throws SimpleParserException {
			int startPos = getCurrentPosition();

			expectChar("+-0123456789");
			if (getCurrentChar() == '+' || getCurrentChar() == '-')
				advance();

			expectChar("0123456789");
			readWhile("0123456789");

			return data.substring(startPos, getCurrentPosition());
		}

		/**
		 * Forwards the current parsing position while the current character is contained in the given string and while
		 * the current parsing position is not the end of file.
		 *
		 * @see #eof()
		 *
		 * @param include
		 *            the characters to skip
		 * @return the number of characters read
		 */
		protected int readWhile(final String include) {
			int count = 0;
			while (!eof() && include.indexOf(getCurrentChar()) != -1) {
				advance();
				count++;
			}
			return count;
		}

		/**
		 * Forwards the current parsing position until the current character is contained in the given string or the
		 * current parsing position has reached the end of file.
		 *
		 * @see #eof()
		 *
		 * @param delimiters
		 *            the characters to stop at
		 * @return the number of characters read
		 */
		protected int readUntil(final String delimiters) {
			int count = 0;
			while (!eof() && delimiters.indexOf(getCurrentChar()) == -1) {
				advance();
				count++;
			}
			return count;
		}

		/**
		 * Returns the character at the current parsing position and advances.
		 *
		 * @return the character at the parsing position before it was advanced
		 */
		protected char getCurrentCharAndAdvance() {
			final char c = getCurrentChar();
			advance();
			return c;
		}

		/**
		 * Returns the character at the current parsing position.
		 *
		 * @return the character at the current parsing position or <code>0</code> if {@link #eof()} is
		 *         <code>true</code>
		 */
		protected char getCurrentChar() {
			if (eof())
				return 0;
			return data.charAt(getCurrentPosition());
		}

		/**
		 * Returns the character before the current parsing position.
		 *
		 * @return the character before the current parsing position or <code>0</code> if there is no such character
		 */
		protected char getPreviousChar() {
			if (getCurrentPosition() > 0)
				return data.charAt(getCurrentPosition() - 1);
			return 0;
		}

		/**
		 * Returns the character after the current parsing position.
		 *
		 * @return the character after the current parsing position or <code>0</code> if there is no such character
		 */
		protected char getNextChar() {
			if (getCurrentPosition() >= data.length() - 1)
				return 0;
			return data.charAt(getCurrentPosition() + 1);
		}

		/**
		 * Returns the current parsing position.
		 *
		 * @return the current parsing position
		 */
		protected int getCurrentPosition() {
			return pos;
		}

		/**
		 * Returns the current line.
		 *
		 * @return the current line
		 */
		protected int getCurrentLine() {
			return line;
		}

		/**
		 * Returns the current column.
		 *
		 * @return the current column
		 */
		protected int getCurrentColumn() {
			return column;
		}

		/**
		 * Returns a token position with its values set according to the current parsing position.
		 *
		 * @return the token position
		 */
		protected TokenPosition getTokenPosition() {
			return new TokenPosition(line, column, pos);
		}

		/**
		 * Returns the length of a token starting at the given token position and ending at the current parsing
		 * position.
		 *
		 * @param position
		 *            the token's position
		 * @return the length of the token
		 */
		protected int getTokenLength(TokenPosition position) {
			return getTokenLength(position, getCurrentPosition());
		}

		/**
		 * Returns the length of a token starting at the given token position and ending at the given position.
		 *
		 * @param startPosition
		 *            the token's position
		 * @param endPosition
		 *            end position
		 * @return the length of the token
		 */
		protected int getTokenLength(TokenPosition startPosition, int endPosition) {
			return endPosition - startPosition.offset;
		}

		/**
		 * Advances the current parsing position by 1 and updates the line and column numbers accordingly.
		 */
		protected void advance() {
			advance(1);
		}

		/**
		 * Advances the current parsing position by the given number and updates the line and column numbers
		 * accordingly.
		 *
		 * @param count
		 *            the number by which to increase the parsing position
		 *
		 * @throws IllegalArgumentException
		 *             if the given number is negative
		 */
		protected void advance(int count) {
			if (count < 0)
				throw new IllegalArgumentException("Cannot advance backwards");

			for (int i = 0; i < count; i++) {
				if (getCurrentChar() == '\n') {
					line++;
					column = 0;
				} else {
					column++;
				}
				pos++;
			}
		}

		/**
		 * Indicates whether the current parsing position has reached the end of the input data.
		 *
		 * @return <code>true</code> if the current parsing position has reached the end of the input data and
		 *         <code>false</code> otherwise
		 */
		protected boolean eof() {
			return eof(getCurrentPosition());
		}

		/**
		 * Indicates whether the given parsing position has reached the end of the input data.
		 *
		 * @param aPos
		 *            a position
		 * @return <code>true</code> if the current given position has reached the end of the input data and
		 *         <code>false</code> otherwise
		 */
		protected boolean eof(int aPos) {
			return aPos >= data.length();
		}

		/**
		 * Throws an exception if the current parsing position has reached the end of the input data
		 *
		 * @throws SimpleParserException
		 *             if the current parsing position has reached the end of the input data
		 */
		protected void throwIfEof() throws SimpleParserException {
			if (eof())
				throw new SimpleParserException(line, column, "Unexpected end of file");
		}

		/**
		 * Throws an exception indicating that an unexpected character was encountered.
		 *
		 * @param c
		 *            the unexpected character
		 */
		protected void unexpectedChar(char c) throws SimpleParserException {
			throw new SimpleParserException(line, column, "Unexpected character '" + c + "'");
		}

		/**
		 * Checks whether the current character is the given character and throws an exception otherwise.
		 *
		 * @param c
		 *            the expected character
		 */
		protected void expectChar(char c) throws SimpleParserException {
			if (getCurrentChar() != c)
				unexpectedChar(c);
		}

		/**
		 * Checks whether the current character is any of the given characters and throws an exception otherwise.
		 *
		 * @param s
		 *            the expected characters
		 */
		protected void expectChar(String s) throws SimpleParserException {
			if (s.indexOf(getCurrentChar()) == -1)
				unexpectedChar(getCurrentChar());
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			appendExcerpt("Data    : '...", "...'", result);
			result.append("Position: ").append(getCurrentPosition()).append("\n");
			result.append("Line    : ").append(getCurrentLine()).append("\n");
			result.append("Column  : ").append(getCurrentColumn()).append("\n");
			return result.toString();
		}

		private void appendExcerpt(String prefix, String suffix, StringBuilder result) {
			final String excerptPrefix = sanitizeExcerpt(getExcerpt(getCurrentPosition(), -2));
			final String excerptInfix = sanitizeExcerpt(getExcerpt(getCurrentPosition(), 1));
			final String excerptSuffix = sanitizeExcerpt(getExcerpt(getCurrentPosition() + 1, 2));

			result.append(prefix).append(excerptPrefix).append(excerptInfix).append(excerptSuffix).append(suffix)
					.append("\n");
			for (int i = 0; i < prefix.length() + excerptPrefix.length(); i++)
				result.append(" ");
			result.append("^").append("\n");
		}

		private String getExcerpt(int position, int offset) {
			final int minIndex = Math.min(position, position + offset);
			final int maxIndex = Math.max(position, position + offset);

			final int start = Math.max(minIndex, 0);
			final int end = Math.min(maxIndex, data.length());

			return data.substring(start, end);
		}

		private String sanitizeExcerpt(String str) {
			return str.replaceAll("\n", "\\n").replaceAll("\t", "\\t");
		}
	}
}
