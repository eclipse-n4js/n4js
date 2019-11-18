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
package org.eclipse.n4js.jsdoc;

import java.util.ArrayList;
import java.util.List;

/**
 * StringBuilder-like converter either simply appending strings (just as StringBuilder) or converting strings from
 * markdown to HTML on the fly. <b>Warning: Do not use {@link #toString()} to get the emitted string</b>, but use
 * {@link #done()} instead. Note that {@link #done()} is changing the internal state and cannot be called multiple
 * times.
 * <p>
 * Strings can be appended without modification via {@link #append(CharSequence)}.
 * <p>
 * With {@link #convertAndAppend(CharSequence)}, the given char sequence is first converted from markdown to HTML. This
 * is not a full markdown parser, only the following markdown formattings are recognized;
 * <ul>
 * <li>paragraphs (empty line)
 * <li>unordered lists starting with "-", tab and space aware; nested lists are recognized.
 * </ul>
 * The emitted HTML is minimal, just enough to produce correct output in hovers for example. That is, paragraphs or list
 * items are not closed.
 */
public class MD2HTMLConvertingBuilder {

	/**
	 * Helper type for modeling block structure of the converted markdown.
	 */
	private static class Block {
		final int depth;
		final String startTag;
		final String endTag;
		final String mdTag;

		Block(int depth, String mdTag, String startTag, String endTag) {
			this.depth = depth;
			this.startTag = startTag;
			this.endTag = endTag;
			this.mdTag = mdTag;
		}

		/**
		 * Only for debugging purposes.
		 */
		@Override
		public String toString() {
			return depth + " " + encode(mdTag) + " --> " + encode(startTag) + " / " + encode(endTag);
		}
	}

	/**
	 * Converts markdown string to html for hovers. This is basically for testing.
	 */
	public static String convert(String markdown) {
		MD2HTMLConvertingBuilder m2h = new MD2HTMLConvertingBuilder();
		m2h.convertAndAppend(markdown);
		return m2h.done();
	}

	/**
	 * The markdown currently processed, set in {@link #convertAndAppend(CharSequence)}.
	 */
	private String md;
	/**
	 * The position in md, set in {@link #convertAndAppend(CharSequence)}
	 */
	private int pos;
	/**
	 * The html builder to which outout is appended.
	 */
	private final StringBuilder html;

	/**
	 * Beginning of line, set to recognize special handling of list item identifiers.
	 */
	private boolean bol = true;
	/**
	 * The last consumed character (via next()).
	 */
	private char current;

	/**
	 * The blocks created during conversion (for lists etc.). These blocks are closed either on {@link #done()} or
	 * {@link #resetMarkdownConverter()} via {@link #closeNestedBlocks(String)}
	 */
	private final List<Block> blocks = new ArrayList<>();

	/**
	 * The current indentation depth of the line, used to detect end of list items.
	 */
	private int depth = 0;

	/**
	 * Creates parser
	 */
	public MD2HTMLConvertingBuilder() {
		html = new StringBuilder();
		resetMarkdownConverter();
	}

	/**
	 * Converts the given string, internal state is preserved, open blocks are not closed. Call {@link #done()} to close
	 * all blocks and return generated html string.
	 */
	public void convertAndAppend(CharSequence markdown) {
		if (markdown == null) {
			return;
		}
		this.md = markdown.toString();
		pos = 0;

		do {
			next();
			switch (current) {
			case 0:
				break;
			case '\r':
				break; // ignore
			case '\n':
				handleEOL();
				break;
			case '-':
				handleUL();
				break;
			default:
				handleChar();
			}
		} while (current != 0);

	}

	/**
	 * Appends raw text without converting it.
	 */
	public MD2HTMLConvertingBuilder append(CharSequence cs) {
		html.append(cs);
		return this;

	}

	/**
	 * Returns the converted html as string after closing all open blocks. Resets internal state, that is, when called
	 * again, it will return an empty string.
	 */
	public String done() {
		closeAllBlocks();
		String result = html.toString();
		resetMarkdownConverter();
		return result;
	}

	/**
	 * Resets internal state so that all blocks are closed. Emitted HTML is not removed.
	 */
	public void resetMarkdownConverter() {
		closeAllBlocks();
		bol = true;
		depth = 0;
		md = "";
		pos = 0;
	}

	/**
	 * Opens a new block, emitting the opening html tag.
	 */
	private void openBlock(int blockDepth, String mdTag, String startTag, String endTag) {
		Block b = new Block(blockDepth, mdTag, startTag, endTag);
		html.append(startTag);
		blocks.add(b);
	}

	/**
	 * Closes the current block if it exists, emitting the closing html tag.
	 */
	private void closeBlock() {
		int i = blocks.size() - 1;
		if (i >= 0) {
			html.append(blocks.get(i).endTag);
			html.append("\n");
		}
		blocks.remove(i);
	}

	/**
	 * Closes all blocks.
	 */
	private void closeAllBlocks() {
		while (!blocks.isEmpty())
			closeBlock();
	}

	/**
	 * Closes all blocks with a greater depth than the current depth. If depth is similar it is closed if it contains
	 * the same markdown tag; this is used for detecting list item siblings.
	 *
	 * @return current block (i.e. block that was not closed) or null, if no block is found.
	 */
	private Block closeNestedBlocks(String mdTag) {
		for (int i = blocks.size() - 1; i >= 0; i--) {
			Block b = blocks.get(i);
			if (b.depth > depth) {
				closeBlock();
			} else if (b.depth == depth) {
				if (mdTag != null && mdTag.equals(b.mdTag)) {
					return b;
				} else {
					closeBlock();
				}
			} else { // b.depth<depth
				return b;
			}
		}
		return null;
	}

	/**
	 * Consumes the next character in the converting markdown string and assings it to {@link #current}. If end of
	 * string is found, {@link #current} is set to 0.
	 */
	private void next() {
		if (pos < md.length()) {
			current = md.charAt(pos);
			pos++;
		} else {
			current = 0;
		}
	}

	/**
	 * Returns the character which would be consumed next (in {@link #next()}) without consuming it. If the end of the
	 * string is found, 0 is returned.
	 */
	private char peek() {
		if (pos < md.length()) {
			char p = md.charAt(pos);
			return p;
		}
		return 0;
	}

	/**
	 * Handles "end of line" (EOL) recognizing paragraphs (two empty lines in a row) and computing the depth of the next
	 * line.
	 */
	private void handleEOL() {
		html.append("\n");
		int currentDepth = depth;
		depth = 0;
		boolean parFound = false;
		char s = peek();
		while (Character.isWhitespace(s)) {
			switch (s) {
			case '\n': {
				parFound = true;
				depth = 0;
				break;
			}
			case '\t': {
				depth += 4;
				break;
			}
			case ' ': {
				depth++;
			}
			}
			next();
			s = peek();
		}
		if (parFound) {
			if (depth > currentDepth) {
				html.append("<p>");
			} else {
				Block b = closeNestedBlocks(null);
				if (b == null) {
					html.append("<p>");
				}
			}
		}
		bol = true;
	}

	/**
	 * Handles unsorted lists (UL), that is, list items in markdown.
	 */
	private void handleUL() {
		if (!bol) {
			html.append(current);
			return;
		}

		if (!Character.isWhitespace(peek())) {
			handleChar();
		}

		String mdTag = String.valueOf(current);
		Block b = closeNestedBlocks(mdTag);
		if (b == null || depth > b.depth) {
			openBlock(depth, mdTag, "<ul>\n", "</ul>");
		}
		html.append("<li>");
		next(); // consume whitespace after the list item
		bol = false;
	}

	/**
	 * Handles a character or white space character (except at beginning of line).
	 */
	private void handleChar() {
		if (bol) {
			if (depth > 0) { // similar to github md
				closeNestedBlocks(null);
			}
			bol = false;
		}
		html.append(current);
	}

	/**
	 * Only for debugging -- do not use to get the emitted html string, use {@link #done()} instead.
	 */
	@Override
	public String toString() {
		int from = pos - 4;
		int to = pos + 4;
		if (from < 0)
			from = 0;
		if (to >= md.length())
			to = md.length() - 1;

		StringBuilder strb = new StringBuilder();

		if (from > 0)
			strb.append("…");

		for (int i = from; i <= to; i++) {
			if (i == pos - 1) {
				strb.append("»");
			}
			strb.append(encode(md.charAt(i)));
			if (i == pos - 1) {
				strb.append("«");
			}
		}
		if (to < md.length() - 1)
			strb.append("…");
		strb.append(", depth " + depth);
		if (!blocks.isEmpty()) {
			strb.append(", blocks[" + (blocks.size() - 1) + "]=");
			strb.append(blocks.get(blocks.size() - 1));
		}
		return strb.toString();
	}

	/**
	 * Used for improving string debug output.
	 */
	private static String encode(char raw) {
		switch (raw) {
		case '\n':
			return "\\n";
		case '\t':
			return "\\t";
		case '\r':
			return "\\r";
		case '0':
			return "†";
		default:
			return String.valueOf(raw);
		}
	}

	/**
	 * Used for improving string debug output.
	 */
	private static String encode(String raw) {
		if (raw == null)
			return "null";
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < raw.length(); i++) {
			strb.append(encode(raw.charAt(i)));
		}
		return strb.toString();
	}

}
