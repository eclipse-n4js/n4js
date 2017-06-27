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

/**
 * An exception thrown by {@link SimpleParser}.
 */
public class SimpleParserException extends Exception {
	/**
	 * Creates a new exception for an error at the given line and column and with the given message.
	 *
	 * @param line
	 *            the line at which the error occurred
	 * @param column
	 *            the column at which the error occurred
	 * @param message
	 *            the error message
	 */
	public SimpleParserException(int line, int column, String message) {
		super(formatMessage(line, column, message));
	}

	private static String formatMessage(int line, int column, String message) {
		StringBuilder result = new StringBuilder();
		result.append("Parse error at line ").append(line + 1).append(", column ").append(column + 1).append(": ")
				.append(message);
		return result.toString();
	}
}
