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
package org.eclipse.n4js.jsdoc;

import java.util.Stack;

/**
 * Scanner for JSDoc comments. Begin and end markers are skipped, as are asterixes ('*') at beginning of lines
 * (including preceding whitespaces and one trailing whitespace). In all cases, leading and trailing whitespaces are
 * skipped, except for whitespaces following the first whitespace after a leading asterix.
 * <p>
 * Design rational: Instead of using regular expressions or similar techniques to convert a JSDoc comment into a nicely
 * to parse String, the scanner preserves the correct offset_ positions of all found characters, while releasing clients
 * from the burden to check for comment specific formatting.
 */
public class JSDocCharScanner {

	/**
	 * Start of a JSDoc comment.
	 */
	public static final String JSDOC_START = "/**";
	/**
	 * Start of a multi-line comment.
	 */
	public static final String MLDOC_START = "/*";
	/**
	 * Character indicating start of a tag
	 */
	public static final char TAG_START = '@';
	/**
	 * Start of a comment line, usually ignored.
	 */
	public static final char COMMENT_LINE_PREFIX = '*';

	/**
	 * Utility class to save& restore scanner state.
	 */
	public final class ScannerState {
		int nextOffset_, offset_;

		@SuppressWarnings("javadoc")
		public ScannerState(int nextOffset, int offset) {
			this.nextOffset_ = nextOffset;
			this.offset_ = offset;
		}

	}

	/**
	 * The scanned string, set in the constructor.
	 */
	private final String s;
	/**
	 * Offset of the character returned by next {@link #next()}.
	 */
	private int nextOffset;
	/**
	 * Offset of the character returned by last {@link #next()}.
	 */
	private int offset;
	private int nextFencePost;

	private final Stack<Integer> fencePosts = new Stack<>();

	/**
	 * @param s
	 *            Initial string value kept by the scanner.
	 */
	public JSDocCharScanner(String s) {
		this(s, 0);
	}

	/**
	 * @param s
	 *            Initial string value kept by the scanner.
	 * @param initialOffset
	 *            offset of s from the beginning in orginal source.
	 */
	public JSDocCharScanner(String s, int initialOffset) {
		this(s, initialOffset, s.length());
	}

	/**
	 * @param s
	 *            Initial string value kept by the scanner.
	 * @param initialOffset
	 *            offset of s from the beginning in orginal source.
	 * @param maxOffsetExcluded
	 *            max offset of orginal source that will be held in this scanner
	 */
	public JSDocCharScanner(String s, int initialOffset, int maxOffsetExcluded) {
		this.s = s;
		this.nextFencePost = maxOffsetExcluded;
		nextOffset = initialOffset;
		offset = -1;
		skipJSDocStart();
		more();
	}

	/**
	 * Initiate scanner with values of other scanner.
	 */
	protected JSDocCharScanner(JSDocCharScanner src, int maxOffsetExcluded) {
		this.s = src.s;
		this.nextFencePost = maxOffsetExcluded;
		this.nextOffset = src.nextOffset;
		this.offset = src.offset;
	}

	/**
	 * @return Copy state of the scanner (offset positions)
	 */
	public ScannerState saveState() {
		return new ScannerState(nextOffset, offset);
	}

	/**
	 * @param state
	 *            Restore scanner to given state (offset markers)
	 */
	public void restoreState(ScannerState state) {
		nextOffset = state.nextOffset_;
		offset = state.offset_;
	}

	/**
	 * @return copy of this scanner.
	 */
	public JSDocCharScanner copy() {
		return new JSDocCharScanner(this, this.nextFencePost);
	}

	private void skipJSDocStart() {
		if (nextOffset == 0) {
			if (s.startsWith(JSDOC_START)) {
				nextOffset += 3;
			} else if (s.startsWith(MLDOC_START)) {
				nextOffset += 2;
			} else {
				return;
			}
			// skip white spaces after doc start
			while (hasNext() && isWhitespaceNoNL(peek())) {
				next();
			}
			if (isNL(peek())) {
				next();
			}
			skipLineStart();
		}
	}

	/**
	 * Returns true if more comment contains more characters. Closing marker '*''/' is recognized. If no more characters
	 * are contained, nextOffset is set to -1.
	 */
	private boolean more() {
		if (nextOffset < 0) {
			return false;
		}
		int skippedWS = 0;
		char c = 0;
		while (nextOffset + skippedWS < nextFencePost) {
			c = s.charAt(nextOffset + skippedWS);
			if (Character.isWhitespace(c)) {
				skippedWS++;
			} else {
				break;
			}
		}
		if (nextOffset + 1 + skippedWS < nextFencePost && c == '*'
				&& s.charAt(skippedWS + nextOffset + 1) == '/') {
			nextOffset = -1;
			return false;
		}
		if (nextOffset + skippedWS < nextFencePost) {
			return true;
		}
		nextOffset = -1;
		return false;
	}

	private char peekNextChar() {
		return s.charAt(nextOffset);
	}

	private void findNextCharacter() {
		if (more()) {
			if (offset >= 0) {
				char c = s.charAt(offset);
				if (isNL(c)) {
					skipLineStart();
				}
			}
			more();
		}
	}

	int findLineTagEnd() {
		int oldNextOffset = nextOffset; // use nextOffset to enable better debugging thanks to toString
		try {
			nextOffset = offset;
			while (nextOffset < nextFencePost) {
				while (nextOffset < nextFencePost && !isNL(s.charAt(nextOffset)))
					nextOffset++;
				int nlpos = nextOffset;
				while (nextOffset < nextFencePost && Character.isWhitespace(s.charAt(nextOffset)))
					nextOffset++;
				if (nextOffset < nextFencePost && s.charAt(nextOffset) == COMMENT_LINE_PREFIX)
					nextOffset++;
				while (nextOffset < nextFencePost && isWhitespaceNoNL(s.charAt(nextOffset)))
					nextOffset++;
				if (nextOffset < nextFencePost && s.charAt(nextOffset) == TAG_START) {
					return nlpos;
				}
			}
			return nextOffset - 1;
		} finally {
			nextOffset = oldNextOffset; // ensure to restore state
		}
	}

	/**
	 * Returns true if character is a newline character, that is '\n' or '\r'.
	 */
	public static boolean isNL(char c) {
		return c == '\n' || c == '\r';
	}

	private static boolean isWhitespaceNoNL(char c) {
		if (isNL(c)) {
			return false;
		}
		return Character.isWhitespace(c);
	}

	private void skipLineStart() {
		int savedNextOffset = nextOffset;
		while (more()) {
			char c = peekNextChar();
			if (isWhitespaceNoNL(c)) {
				nextOffset++;
			} else {
				break;
			}
		}
		if (!more())
			return;

		char c = peekNextChar();
		if (c != COMMENT_LINE_PREFIX) {
			// no COMMENT_LINE_PREFIX found, do not skip in order to enable markdown formatting
			nextOffset = savedNextOffset;
		} else {
			nextOffset++; // consume COMMENT_LINE_PREFIX

			// consume whitespace after COMMENT_LINE_PREFIX
			if (more() && isWhitespaceNoNL(peekNextChar())) {
				nextOffset++;
			}
		}

		savedNextOffset = nextOffset;
		while (more() && isWhitespaceNoNL(peekNextChar())) {
			nextOffset++;
		}
		if (more() && peekNextChar() != TAG_START) {
			nextOffset = savedNextOffset;
		}
	}

	/**
	 *
	 * @return true if there are still characters pending
	 */
	public boolean hasNext() {
		return nextOffset >= 0 && nextOffset < nextFencePost;
	}

	/**
	 * Returns true if characters are skipped when {@link #next()} is called again, such as leading asterixes on a new
	 * line.
	 */
	public boolean skipped() {
		return offset + 1 != nextOffset;
	}

	/**
	 * Returns the current offset_, i.e. the position of the character returned by the last {@link #next()} call; or -1,
	 * if no characters were ever available or {@link #next()} has never been called.
	 */
	public int offset() {
		return offset;
	}

	/**
	 * Returns the offset_ of the next char to be read by {@link #next()} or {@link #peek()}.
	 */
	public int nextOffset() {
		return nextOffset;
	}

	/**
	 * Skip white spaces
	 */
	public void skipWS() {
		while (hasNext() && Character.isWhitespace(peek())) {
			next();
		}
	}

	/**
	 * Skips whitespaces and returns next non-whitespace.
	 */
	public char nextNonWS() {
		skipWS();
		return next();
	}

	/**
	 * Returns the current character.
	 */
	public char next() {
		if (nextOffset >= 0 && nextOffset < nextFencePost) {
			char current = peek();
			offset = nextOffset;
			nextOffset++;
			findNextCharacter();
			return current;
		} else {
			return 0;
		}
	}

	/**
	 * Returns the character that would be returned by calling {@link #next()}, without moving the current offset_.
	 */
	public char peek() {
		if (nextOffset >= 0 && nextOffset < nextFencePost) {
			return s.charAt(nextOffset);
		} else {
			return 0;
		}
	}

	/**
	 * Returns the character at given offset without changing the scanners state. If offset is out of bound, 0 is
	 * returned.
	 */
	public char charAt(int index) {
		if (index < 0 || index >= nextFencePost)
			return 0;
		return s.charAt(index);
	}

	/**
	 * Sets next offset -- this method must not be called during parsing of JSDoc, but only for completion hint!
	 *
	 */
	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
		this.offset = nextOffset - 1;
	}

	/**
	 * Sets next offset to the next preceeding WS -- this method must not be called during parsing of JSDoc, but only
	 * for completion hint!
	 */
	public void rewindToWS() {
		if (nextOffset == 0) {
			return;
		}
		nextOffset--;
		while (nextOffset > 0 && !Character.isWhitespace(s.charAt(nextOffset))) {
			nextOffset--;
		}
		this.offset = nextOffset - 1;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		int from = Math.max(0, nextOffset - 10);
		int to = Math.min(nextOffset + 10, nextFencePost - 1);
		for (int i = from; i < to; i++) {
			if (i == nextOffset) {
				strb.append("«");
			}
			strb.append(s.charAt(i));
			if (i == nextOffset) {
				strb.append("»");
			}
		}
		String pre = from != 0 ? "…" : "";
		String post = to < nextFencePost - 1 ? "…" : "";
		return pre + strb.toString() + post;
	}

	/**
	 * Length of parsed text.
	 */
	public int length() {
		return nextFencePost;
	}

	/**
	 * Temporarily sets end of text in order to ensure that sub-parsers do not exceed that point. Has to be undone via
	 * {@link #unfence()} eventually. The fencePosition must be AFTER the current offset!
	 *
	 * @param fencePosition
	 *            the end of the fence, this position is excluded from current area.
	 */
	public void fence(int fencePosition) {
		if (offset > fencePosition) {
			throw new IndexOutOfBoundsException("Scanner offest " + offset + " already after fence position "
					+ fencePosition);
		}
		fencePosts.push(nextFencePost);
		nextFencePost = fencePosition;
	}

	/**
	 * Removes a temporary maximal end offset set via {@link #fence(int)}.
	 */
	public void unfence() {
		nextFencePost = fencePosts.pop();
	}

}
