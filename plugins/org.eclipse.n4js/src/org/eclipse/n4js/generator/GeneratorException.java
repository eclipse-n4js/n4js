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
package org.eclipse.n4js.generator;

/**
 * Wraps exceptions occurred during transpilation / compilation / generation.
 */
public class GeneratorException extends RuntimeException implements IGeneratorException {

	private final String file;
	private final int line;

	/**
	 * @param message
	 *            the exception message
	 */
	public GeneratorException(String message, Throwable cause) {
		this(message, null, -1, cause);
	}

	/**
	 * @param message
	 *            the exception message
	 * @param file
	 *            file descriptor to be included in exception
	 */
	public GeneratorException(String message, String file, Throwable cause) {
		this(message, file, -1, cause);
	}

	/**
	 * @param message
	 *            the exception message
	 * @param file
	 *            file descriptor to be included in exception
	 * @param line
	 *            line info to be included in exception
	 */
	public GeneratorException(String message, String file, int line, Throwable cause) {
		super(message, cause);
		this.file = file;
		this.line = line;
	}

	/**
	 * @return the file
	 */
	@Override
	public String getFile() {
		return file;
	}

	/**
	 * @return the line
	 */
	@Override
	public int getLine() {
		return line;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + ((file != null) ? " in " + file + " " : "")
				+ ((line > 0) ? " in line " + line : "");
	}
}
