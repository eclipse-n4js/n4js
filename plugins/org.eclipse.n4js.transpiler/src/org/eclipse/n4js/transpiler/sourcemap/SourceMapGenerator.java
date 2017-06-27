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

import java.io.IOException;

/**
 * Generates a mapping for debugging in N4JS-IDE, Chrome, etc.
 */
public interface SourceMapGenerator {

	/**
	 * Appends the source map to the given buffer.
	 *
	 * @param appOut
	 *            The stream to which the map will be appended.
	 * @param fileName
	 *            The name of the generated source file that this source map represents.
	 */
	void appendTo(Appendable appOut, String fileName) throws IOException;

	/**
	 * Resets and reinitializes the source map. Can be used between the generation of two output files.
	 */
	void reset();

	/**
	 * Adds a source mapping. Note that the mapping order has to be respected.
	 *
	 * @param srcName
	 *            The source file name.
	 * @param symbolName
	 *            The symbol name of the element to be mapped.
	 * @param srcStartPos
	 *            The start position of the element to be mapped.
	 * @param outStartPos
	 *            The start position of the mapped element in the generated file.
	 * @param outEndPos
	 *            The end position of the mapped element in the generated file.
	 */
	void addMapping(String srcName, String symbolName, FilePosition srcStartPos, FilePosition outStartPos,
			FilePosition outEndPos);

}
