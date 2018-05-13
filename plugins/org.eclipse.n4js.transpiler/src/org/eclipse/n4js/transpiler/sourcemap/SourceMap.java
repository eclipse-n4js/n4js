/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
public class SourceMap {

	public static class Mapping implements Comparable<Mapping> {
		public int outputLine;
		public int outputColumn;
		public int sourceIndex;
		public int inputLine;
		public int inputColumn;
		public int nameIndex;

		/**
		 * Returns an initial mapping used for loading a mapping file.
		 */
		public static Mapping init() {
			return new Mapping(0, 0, new FilePosition(0, 0), new FilePosition(0, 0));
		}

		/**
		 * Creates a new line mapping entry; the output line number is indirectly defined by the index of the mapping in
		 * the mappins list.
		 */
		public Mapping(int sourceIndex, int nameIndex, FilePosition srcStartPos, FilePosition outStartPos) {
			this.outputLine = outStartPos.getLine();
			this.outputColumn = outStartPos.getColumn();
			this.sourceIndex = sourceIndex;
			if (srcStartPos != null) {
				this.inputLine = srcStartPos.getLine();
				this.inputColumn = srcStartPos.getColumn();
			} else {
				this.inputLine = -1;
				this.inputColumn = -1;
			}
			this.nameIndex = nameIndex;
		}

		public Mapping(int outLine, int[] mappingInfo, Mapping prev) {
			this.outputLine = outLine;
			if (mappingInfo == null || mappingInfo.length == 0) {
				this.outputColumn = -1;
				this.sourceIndex = -1;
				this.inputLine = -1;
				this.inputColumn = -1;
				this.nameIndex = -1;
				return;
			}

			if (mappingInfo.length == 1) {
				this.outputColumn = prev.outputColumn + mappingInfo[0];
				this.sourceIndex = -1;
				this.inputLine = -1;
				this.inputColumn = -1;
				this.nameIndex = -1;

				prev.outputColumn = outputColumn;
				return;
			}

			if (mappingInfo.length < 4 || mappingInfo.length > 5) {
				throw new IllegalArgumentException();
			}

			this.outputColumn = prev.outputColumn + mappingInfo[0];
			this.sourceIndex = prev.sourceIndex + mappingInfo[1];
			this.inputLine = prev.inputLine + mappingInfo[2];
			this.inputColumn = prev.inputColumn + mappingInfo[3];

			prev.outputColumn = outputColumn;
			prev.sourceIndex = sourceIndex;
			prev.inputLine = inputLine;
			prev.inputColumn = inputColumn;

			if (mappingInfo.length == 5) {
				nameIndex = prev.nameIndex + mappingInfo[4];
				prev.nameIndex = nameIndex;
			}

		}

		@Override
		public int compareTo(Mapping o) {
			return outputColumn - o.outputColumn;
		}

		public String toBase64VLQRelative(
				int prevOutputColumn,
				int prevSourceIndex,
				int prevInputLine,
				int prevInputColumn,
				int prevNameIndex) {
			if (sourceIndex < 0) {
				return Base64VLQ.encode(outputColumn - prevOutputColumn);
			}
			if (nameIndex < 0) {
				return Base64VLQ.encode(
						outputColumn - prevOutputColumn,
						sourceIndex - prevSourceIndex,
						inputLine - prevInputLine,
						inputColumn - prevInputColumn);
			}
			return Base64VLQ.encode(
					outputColumn - prevOutputColumn,
					sourceIndex - prevSourceIndex,
					inputLine - prevInputLine,
					inputColumn - prevInputColumn,
					nameIndex - prevNameIndex);
		}

	}

	public String version;
	public String sourceRoot;
	final public List<String> sources = new ArrayList<>();
	final public List<String> names = new ArrayList<>();
	final List<Set<Mapping>> mappings = new ArrayList<>();

	public void addMappig(Mapping mapping) {
		Set<Mapping> lineMapping = getOrCreateLineMapping(mapping.inputLine);
		lineMapping.add(mapping);
	}

	/**
	 * Gets and creates on demand a new set of LineMappingEntries for the line identified by outStartPos
	 */
	private Set<Mapping> getOrCreateLineMapping(int srcLine) {
		Set<Mapping> lineMappings;
		if (srcLine < mappings.size()) {
			lineMappings = mappings.get(srcLine);
			if (lineMappings == null) {
				lineMappings = new TreeSet<>();
				mappings.set(srcLine, lineMappings);
			}
		} else {
			for (int i = mappings.size(); i < srcLine; i++) {
				mappings.add(null);
			}
			lineMappings = new TreeSet<>();
			mappings.add(lineMappings);
		}
		return lineMappings;
	}
}
