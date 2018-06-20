/**
 * Copyright (c) 2017 NumberFour AG.
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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Source Map Revision 3
 *
 * TODO: IDE-2203
 *
 * @see <a href="https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k">Source Map Revision 3
 *      Proposal</a>
 */
public class SourceMapRev3Generator implements SourceMapGenerator {

	/** Source file name used in mapping */
	Map<String, Integer> sources = new LinkedHashMap<>();
	/** Symbol names used in mapping */
	Map<String, Integer> names = new LinkedHashMap<>();
	/**
	 * The produced mapping.
	 */
	SourceMap map = new SourceMap();

	@Override
	public void reset() {
		sources.clear();
		names.clear();
		map = new SourceMap();

	}

	@Override
	public void addMapping(String srcName, String symbolName, FilePosition srcStartPos, FilePosition outStartPos,
			FilePosition outEndPos) {
		if (srcName == null) {
			map.addMappig(new MappingEntry(outStartPos));
		} else {
			int sourceIndex = getOrCreateSourceIndex(srcName);
			int nameIndex = symbolName != null ? getOrCreateNameIndex(symbolName) : -1;
			map.addMappig(new MappingEntry(outStartPos, sourceIndex, srcStartPos, nameIndex));
		}
	}

	/**
	 * Gets and creates on demand an entry for the given symbolName.
	 */
	private int getOrCreateNameIndex(String symbolName) {
		Integer index = names.get(symbolName);
		if (index == null) {
			index = names.size();
			names.put(symbolName, index);
		}
		return index;
	}

	/**
	 * Gets and creates on demand an entry for the given soureName.
	 */
	private int getOrCreateSourceIndex(String sourceName) {
		Integer index = sources.get(sourceName);
		if (index == null) {
			index = sources.size();
			sources.put(sourceName, index);
		}
		return index;
	}

	@Override
	public void appendTo(Appendable out, String fileName) throws IOException {
		map.file = fileName;
		map.sources.addAll(sources.keySet());
		map.names.addAll(names.keySet());
		map.toString(out);
	}

}
