/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

/**
 * Console for the user to see.
 */
public class N4jscConsole {
	private static final Stack<String> moduleNames = new Stack<>();
	private static boolean suppress = false;
	private static String infoLine = null;
	private static boolean atStartPosition = true;

	/** Set to {@code true} to disable console print outs */
	static public void setSuppress(boolean pSuppress) {
		suppress = pSuppress;
	}

	/** @return the output stream for user directed output */
	static public PrintStream getPrintStream() {
		return System.out;
	}

	/** Sets the info line. Used to report progress. */
	@SuppressWarnings("resource")
	static public void setInfoLine(String line) {
		if (StringUtils.isBlank(line)) {
			line = null;
		}

		String infoLineOld = infoLine;
		infoLine = line;

		if (atStartPosition) {
			if (infoLineOld == null && line != null) {
				doPrint(getPrintStream(), "", true);
			} else if (infoLineOld != null && line == null) {
				doPrint(getPrintStream(), "", true);
			} else if (infoLineOld != null && line != null) {
				doPrint(getPrintStream(), "", true);
			}
		}
	}

	/** Prints a message on the console */
	@SuppressWarnings("resource")
	static public void println(String msg) {
		println(getPrintStream(), msg);
	}

	/** Prints a message on the console */
	@SuppressWarnings("resource")
	static public void print(String msg) {
		print(getPrintStream(), msg);
	}

	/** Prints a message on the given stream */
	static public void println(PrintStream ps, String msg) {
		print(ps, msg + System.lineSeparator());
	}

	/** Prints a message on the given stream */
	static public void print(PrintStream ps, String msg) {
		if (!suppress) {
			doPrint(ps, addModuleNamePrefix(msg), false);
		}
	}

	static private void doPrint(PrintStream ps, String msg, boolean forceInfoLine) {
		ps.print(msg);
		atStartPosition = msg.endsWith("\n") || msg.endsWith("\n\r");
		if (infoLine != null && (atStartPosition || forceInfoLine)) {
			ps.print(infoLine + "\r");
		}
	}

	static private String addModuleNamePrefix(String msg) {
		if (moduleNames.isEmpty()) {
			return msg;
		}
		String prefix = "[" + String.join(">", moduleNames) + "]";
		return prefix + " " + msg;
	}

	/** Adds a module name. Module names are prefixes in the output. */
	static public AutoCloseable addModuleName(final String moduleName) {
		moduleNames.add(moduleName);

		AutoCloseable ac = new AutoCloseable() {
			@Override
			public void close() throws Exception {
				if (moduleName.equals(moduleNames.peek())) {
					moduleNames.pop();
				}
			}
		};

		return ac;
	}

	/** Reads a string terminated with a new-line from the console */
	public static String readLine() {
		Console console = System.console();
		if (console == null) {

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				return reader.readLine();
				// System.in should not be closed by us

			} catch (IOException e) {
				throw new RuntimeException("Error while reading from console", e);
			}

			// Scanner scanInput = new Scanner(System.in);
			// return scanInput.nextLine();
			// throw new RuntimeException("No console available");
		}
		return console.readLine();
	}
}
