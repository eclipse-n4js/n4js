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
package org.eclipse.n4js.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.n4js.utils.SimpleParser;
import org.eclipse.n4js.utils.SimpleParserException;

/**
 * This is a simple CSV (comma-separated values) parser that handles escaping of control characters in fields (commas
 * and newlines) using double quotation marks and escaping double quotation marks as a sequence of two double quotation
 * marks.
 */
public class CSVParser extends SimpleParser {
	private static enum TokenType {
		FIELD, FIELD_SEPARATOR, ROW_SEPARATOR, EOF
	}

	private static class Tokenizer extends SimpleTokenizer<TokenType> {
		/**
		 * Creates a new tokenizer that will process the given string.
		 *
		 * @param data
		 *            the string to process
		 */
		Tokenizer(String data) {
			super(data);
		}

		private static final char COMMA = ',';
		private static final char NEWLINE = '\n';
		private static final char CARRIAGE_RETURN = '\r';
		private static final char DOUBLE_QUOTATION_MARK = '"';

		@Override
		protected Token<TokenType> readToken() throws SimpleParserException {
			while (!eof()) {
				final TokenPosition position = getTokenPosition();
				final char c = getCurrentChar();
				if (c == COMMA) {
					advance();
					return createTokenFromSubstring(TokenType.FIELD_SEPARATOR, position);
				} else if (c == NEWLINE) {
					advance();
					return createTokenFromSubstring(TokenType.ROW_SEPARATOR, position);
				} else if (c != CARRIAGE_RETURN) {
					return readField();
				} else {
					advance();
				}
			}

			return new Token<>(TokenType.EOF, getTokenPosition());
		}

		private Token<TokenType> readField() {
			if (getCurrentChar() == DOUBLE_QUOTATION_MARK)
				return readQuotedField();

			final TokenPosition position = getTokenPosition();
			char c = getCurrentChar();
			while (c != CARRIAGE_RETURN && c != NEWLINE && c != COMMA && !eof()) {
				advance();
				if (!eof())
					c = getCurrentChar();
			}

			final Token<TokenType> result = createTokenFromSubstring(TokenType.FIELD, position);
			if (c == CARRIAGE_RETURN)
				advance();
			return result;
		}

		private Token<TokenType> readQuotedField() {
			advance();

			final TokenPosition position = getTokenPosition();
			StringBuilder buf = new StringBuilder();

			while (!eof()) {
				final char c = getCurrentCharAndAdvance();
				if (c == DOUBLE_QUOTATION_MARK) {
					if (eof() || getCurrentChar() != DOUBLE_QUOTATION_MARK)
						return new Token<>(TokenType.FIELD, position, getTokenLength(position) - 2, buf.toString());
					// next is also ", skip it
					advance();
				}
				buf.append(c);
			}
			return new Token<>(TokenType.EOF, getTokenPosition());
		}
	}

	private final Tokenizer tokenizer;

	/**
	 * Creates a new parser that parses the given string.
	 *
	 * @param data
	 *            the string to parse
	 */
	private CSVParser(String data) {
		this.tokenizer = new Tokenizer(data);
	}

	/**
	 * Creates a new parser that parses the file at the given location with the given encoding.
	 *
	 * @param path
	 *            the path to the file to be parsed
	 * @param encoding
	 *            the encoding of the file to be parsed
	 *
	 * @throws IOException
	 *             if the file cannot be found or cannot be opened
	 */
	private CSVParser(String path, Charset encoding) throws IOException {
		this(Paths.get(path), encoding);
	}

	/**
	 * Creates a new parser that parses the file at the given location with the given encoding.
	 *
	 * @param path
	 *            the path to the file to be parsed
	 * @param encoding
	 *            the encoding of the file to be parsed
	 *
	 * @throws IOException
	 *             if the file cannot be found or cannot be opened
	 */
	private CSVParser(Path path, Charset encoding) throws IOException {
		this(new String(Files.readAllBytes(path), encoding));
	}

	/**
	 * Parses the CSV data contained in the given string.
	 *
	 * @param data
	 *            the string to parse
	 * @return the CSV data that was parsed from the string
	 * @throws SimpleParserException
	 *             if an error occurs during parsing
	 */
	public static CSVData parse(String data) throws SimpleParserException {
		return new CSVParser(data).doParse();
	}

	/**
	 * Parses the file at the given location with the given encoding.
	 *
	 * @param path
	 *            the path to the file to be parsed
	 * @param encoding
	 *            the encoding of the file to be parsed
	 * @return the CSV data that was parsed from the file at the given location
	 *
	 * @throws IOException
	 *             if the file cannot be found or cannot be opened
	 * @throws SimpleParserException
	 *             if an error occurs during parsing
	 */
	public static CSVData parse(String path, Charset encoding) throws IOException, SimpleParserException {
		return new CSVParser(path, encoding).doParse();
	}

	/**
	 * Parses the file at the given location with the given encoding.
	 *
	 * @param path
	 *            the path to the file to be parsed
	 * @param encoding
	 *            the encoding of the file to be parsed
	 * @return the CSV data that was parsed from the file at the given location
	 *
	 * @throws IOException
	 *             if the file cannot be found or cannot be opened
	 * @throws SimpleParserException
	 *             if an error occurs during parsing
	 */
	public static CSVData parse(Path path, Charset encoding) throws IOException, SimpleParserException {
		return new CSVParser(path, encoding).doParse();
	}

	private CSVData doParse() throws SimpleParserException {
		CSVData result = new CSVData();
		tokenizer.reset();

		Token<TokenType> token = tokenizer.nextToken().expect(TokenType.EOF, TokenType.ROW_SEPARATOR,
				TokenType.FIELD_SEPARATOR, TokenType.FIELD);
		while (!token.hasType(TokenType.EOF)) {
			tokenizer.pushToken(token);
			result.add(parseRow());
			token = tokenizer.nextToken();
		}

		return result;
	}

	private CSVRecord parseRow() throws SimpleParserException {
		CSVRecord result = new CSVRecord();

		Token<TokenType> token = null;
		do {
			result.add(parseField());
			token = tokenizer.nextToken().expect(TokenType.FIELD_SEPARATOR, TokenType.ROW_SEPARATOR, TokenType.EOF);
		} while (!token.hasType(TokenType.ROW_SEPARATOR, TokenType.EOF));

		return result;
	}

	private String parseField() throws SimpleParserException {
		Token<TokenType> token = tokenizer.nextToken().expect(TokenType.FIELD, TokenType.ROW_SEPARATOR,
				TokenType.FIELD_SEPARATOR, TokenType.EOF);

		if (token.hasType(TokenType.FIELD))
			return token.data;

		tokenizer.pushToken(token);
		return "";
	}
}
