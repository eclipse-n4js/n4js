/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.lsp;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.n4js.xtext.ide.server.util.LspLogger;

/**
 * A print stream that forwards character-based output to an {@link LspLogger}. Low-level byte-based output sent to this
 * stream via {@link PrintStream#write(int) #write(int)}, etc. is not forwarded.
 */
public class LoggingPrintStream extends PrintStream {

	/***/
	protected static final String NL = System.lineSeparator();

	/***/
	protected final LspLogger lspLogger;

	/** Creates a new {@link LoggingPrintStream}. */
	@SuppressWarnings("resource")
	public LoggingPrintStream(LspLogger lspLogger) {
		super(OutputStream.nullOutputStream());
		this.lspLogger = lspLogger;
	}

	@Override
	public void print(boolean b) {
		print(String.valueOf(b));
	}

	@Override
	public void print(char c) {
		print(String.valueOf(c));
	}

	@Override
	public void print(int i) {
		print(String.valueOf(i));
	}

	@Override
	public void print(long l) {
		print(String.valueOf(l));
	}

	@Override
	public void print(float f) {
		print(String.valueOf(f));
	}

	@Override
	public void print(double d) {
		print(String.valueOf(d));
	}

	@Override
	public void print(char s[]) {
		print(String.valueOf(s));
	}

	@Override
	public void print(Object obj) {
		print(String.valueOf(obj));
	}

	@Override
	public void print(String s) {
		lspLogger.log(s);
	}

	// NOTE #1: unfortunately, #newLine() of super class is private, so we have to override the following methods too.
	// NOTE #2: LSP's logging notification always appends a line break, so here #println(...) delegates to #print(...).

	@Override
	public void println() {
		print("");
	}

	@Override
	public void println(boolean x) {
		print(x);
	}

	@Override
	public void println(char x) {
		print(x);
	}

	@Override
	public void println(int x) {
		print(x);
	}

	@Override
	public void println(long x) {
		print(x);
	}

	@Override
	public void println(float x) {
		print(x);
	}

	@Override
	public void println(double x) {
		print(x);
	}

	@Override
	public void println(char x[]) {
		print(x);
	}

	@Override
	public void println(Object x) {
		print(x);
	}

	@Override
	public void println(String x) {
		print(x);
	}
}
