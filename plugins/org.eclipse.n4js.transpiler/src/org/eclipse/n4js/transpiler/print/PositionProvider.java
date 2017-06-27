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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.transpiler.sourcemap.FilePosition;

/**
 * Maps offsets to {@link FilePosition}s, i.e. (line, column) information.
 *
 * @see java.io.LineNumberReader
 */
public class PositionProvider {

	/**
	 * lineStarts[idx] is the offset of the first char of the line with number idx
	 */
	private final int[] lineStarts;

	/**
	 * Create a new instance for the given input stream. The stream will be completely read.
	 */
	public PositionProvider(InputStream in) throws IOException {
		Reader r = new InputStreamReader(in);
		r = new BufferedReader(r);
		int i;
		// starts(lineNr) is the offset at which lineNr starts
		ArrayList<Integer> starts = new ArrayList<>();
		int idx = -1;
		// line-0 starts at offset-0
		starts.add(0);
		while ((i = r.read()) != -1) {
			idx++;
			char ch = (char) i;
			if (ch == '\n') {
				// the newline isn't part of the new line
				starts.add(idx + 1);
			}
		}
		// this line isn't really there, added just to avoid off-by-1 special casing during lookups.
		starts.add(Integer.MAX_VALUE);
		this.lineStarts = starts.stream().mapToInt(k -> k).toArray();
	}

	/**
	 * Create a new instance for the given resource.
	 */
	public static PositionProvider from(N4JSResource resource) {
		String originalSource = NodeModelUtils.getNode(resource.getScript()).getText();
		InputStream in = new ByteArrayInputStream(originalSource.getBytes());
		try {
			PositionProvider pp = new PositionProvider(in);
			in.close();
			return pp;
		} catch (IOException e) {
			throw new WrappedException("exception while trying to compute line/column positions in resource", e);
		}
	}

	/**
	 * Line in the original source file for the given offset.
	 */
	public int findLine(int offset) {
		int line = lineStarts.length - 1;
		while ((lineStarts[line] > offset) && (line > 0)) {
			line--;
		}
		return line;
	}

	/**
	 * Column in the original source file for the given offset.
	 */
	public int findColumn(int offset) {
		int line = findLine(offset);
		int offsetAtLineStart = lineStarts[line];
		return offset - offsetAtLineStart;
	}

	/**
	 * What is the length of the given line?
	 */
	public int length(int line) {
		int offsetStart = lineStarts[line];
		int offsetEnd = lineStarts[line + 1];
		return offsetEnd - offsetStart;
	}

	/**
	 * Location in the original source file for the given offset.
	 */
	public FilePosition toPosition(int offset) {
		int line = findLine(offset);
		int offsetAtLineStart = lineStarts[line];
		int col = offset - offsetAtLineStart;
		return new FilePosition(line, col);
	}
}
