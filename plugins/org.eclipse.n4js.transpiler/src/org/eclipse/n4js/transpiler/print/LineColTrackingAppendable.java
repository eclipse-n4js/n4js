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
package org.eclipse.n4js.transpiler.print;

import java.io.IOException;

import org.eclipse.n4js.transpiler.sourcemap.FilePosition;

/**
 * Wraps an existing {@link Appendable} and tracks line and column positions while appending to it.
 * <p>
 * <b>NOTE: this class only supports pure <code>'\n'</code> line endings; it ignores <code>'\r'</code> (i.e. treats it
 * as an ordinary, printable character).</b>
 */
public class LineColTrackingAppendable implements Appendable {

	private final Appendable out;
	private final CharSequence indent;

	private int currentIndentLevel = 0;

	private int currentLine = 0;
	private int currentCol = 0;

	/**
	 * Only way to instantiate this class.
	 */
	public LineColTrackingAppendable(Appendable out, CharSequence indent) {
		this.out = out;
		this.indent = indent;
	}

	@Override
	public Appendable append(char c) throws IOException {
		if (c == '\n') {
			newLine();
		} else {
			out.append(c);
			currentCol++;
		}
		return this;
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		if (null == csq) {
			throw new NullPointerException("CharSequence must not be null when appending.");
		}
		append(csq, 0, csq.length());
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		while (start < end) {
			// find next new line character OR end of sequence
			int nextNL = start;
			while (nextNL < end && csq.charAt(nextNL) != '\n')
				nextNL++;
			// append everything up to that character (exclusive)
			out.append(csq, start, nextNL);
			currentCol += csq.length();
			// if a new line character was found -> emit a new line
			if (nextNL < end) {
				newLine();
			}
			// continue after the new line character
			start = nextNL + 1;
		}
		return this;
	}

	/**
	 * Emits a <code>'\n'</code> plus required indentation characters for the current indentation level.
	 */
	public void newLine() throws IOException {
		out.append('\n');
		for (int n = 0; n < currentIndentLevel; n++) {
			out.append(indent);
		}
		currentLine++;
		currentCol = currentIndentLevel * indent.length();
	}

	/**
	 * Returns the current indent level.
	 */
	public int getIndentLevel() {
		return currentIndentLevel;
	}

	/**
	 * Changes the current indent level to the given value. Most clients should use methods {@link #indent()} and
	 * {@link #undent()}, instead.
	 */
	public void setIndentLevel(int indentLevel) {
		this.currentIndentLevel = indentLevel;
	}

	/**
	 * Increases indentation level from the next line to be written, onwards.
	 */
	public void indent() {
		currentIndentLevel++;
	}

	/**
	 * Decreases indentation level from the next line to be written, onwards.
	 */
	public void undent() {
		if (currentIndentLevel <= 0)
			throw new IllegalStateException();
		currentIndentLevel--;
	}

	/**
	 * Position of the next char to be written.
	 */
	public FilePosition getCurrentPos() {
		return new FilePosition(currentLine, currentCol);
	}
}
