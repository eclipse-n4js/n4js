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

import java.io.PrintStream;
import java.util.Stack;

/**
 * Console for the user to see.
 */
public class N4jscConsole {
	private static final Stack<String> moduleNames = new Stack<>();

	/** @return the output stream for user directed output */
	static public PrintStream getPrintStream() {
		return System.out;
	}

	/** Prints a message on the console */
	static public void println(String msg) {
		println(getPrintStream(), msg);
	}

	/** Prints a message on the given stream */
	static public void println(PrintStream ps, String msg) {
		ps.println(addModuleNamePrefix(msg));
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
}
