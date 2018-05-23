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
package org.eclipse.n4js.transpiler.sourcemap;

/**
 * Holds information about position in a source file.
 */
public final class FilePosition {
	private final int line;
	private final int column;

	/**
	 * Constructor
	 */
	public FilePosition(int line, int column) {
		this.line = line;
		this.column = column;
	}

	/**
	 * @return line number, starting at 0.
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @return column position, starting at 0.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "[" + line + "," + column + "]";
	}
}
