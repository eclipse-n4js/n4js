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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Source Map Revision 3
 *
 * TODO: IDE-2203
 *
 * @see <a href="https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k">Source Map Revision 3
 *      Proposal</a>
 */
public class SourceMapRev3Generator implements SourceMapGenerator {

	private static class LineMappingEntry implements Comparable<LineMappingEntry> {
		final int outputColumn;
		final int sourceIndex;
		final int inputLine;
		final int inputColumn;
		final int nameIndex;

		/**
		 * Creates a new line mapping entry; the output line number is indirectly defined by the index of the mapping in
		 * the mappins list.
		 */
		public LineMappingEntry(int sourceIndex, int nameIndex, FilePosition srcStartPos, FilePosition outStartPos) {
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

		@Override
		public int compareTo(LineMappingEntry o) {
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

	/** Source file name used in mapping */
	Map<String, Integer> sources = new LinkedHashMap<>();
	/** Symbol names used in mapping */
	Map<String, Integer> names = new LinkedHashMap<>();
	/** The source mappings: List with each entry is a line, containing a map fromColumnNumber to Segment */
	List<Set<LineMappingEntry>> mappings = new ArrayList<>();

	@Override
	public void appendTo(Appendable appOut, String fileName) throws IOException {
		writeBeginning(appOut);
		writeMappings(appOut);
		writeClosing(appOut);

	}

	private void appendProp(Appendable out, String propName) throws IOException {
		out.append("\n\t\"");
		out.append(propName);
		out.append("\": ");
	}

	private void writeBeginning(Appendable out) throws IOException {
		out.append("{");
		appendProp(out, "version");
		out.append("3,");
		appendProp(out, "sourceRoot");
		out.append("\"\",");
		appendProp(out, "sources");
		out.append("[");
		out.append(sources.keySet().stream().map(source -> "\"" + source + "\"").collect(Collectors.joining(",")));
		out.append("],");
		// appendProp(out, "sourceContent");
		// out.append("[");
		// out.append(sources.keySet().stream().map(source -> "null").collect(Collectors.joining(",")););
		// out.append("],");
		appendProp(out, "names");
		out.append("[");
		out.append(names.keySet().stream().map(name -> "\"" + esc(name) + "\"").collect(Collectors.joining(",")));
		out.append("],");
		appendProp(out, "mappings");
		out.append("\"");
	}

	private CharSequence esc(String name) {
		StringBuilder strb = new StringBuilder(name.length() + 2);
		int length = name.length();
		for (int i = 0; i < length; i++) {
			char c = name.charAt(i);
			if (c == '"') {
				strb.append("\\");
			}
			strb.append(c);
		}
		return strb;
	}

	private void writeClosing(Appendable out) throws IOException {
		out.append("\"\n}");
	}

	private void writeMappings(Appendable out) throws IOException {
		for (Set<LineMappingEntry> lineMappings : mappings) {
			if (lineMappings != null) {
				int prevOutputColumn = 0;
				int prevSourceIndex = 0;
				int prevInputLine = 0;
				int prevInputColumn = 0;
				int prevNameIndex = 0;

				Iterator<LineMappingEntry> iter = lineMappings.iterator();
				LineMappingEntry entry = iter.next();
				String segment = entry.toBase64VLQRelative(prevOutputColumn, prevSourceIndex, prevInputLine,
						prevInputColumn, prevNameIndex);
				out.append(segment);
				while (iter.hasNext()) {
					out.append(',');
					prevOutputColumn = entry.outputColumn;
					if (entry.sourceIndex >= 0) {
						prevSourceIndex = entry.sourceIndex;
						prevInputLine = entry.inputLine;
						prevInputColumn = entry.inputColumn;
						if (entry.nameIndex >= 0) {
							prevNameIndex = entry.nameIndex;
						}
					}
					entry = iter.next();
					segment = entry.toBase64VLQRelative(prevOutputColumn, prevSourceIndex, prevInputLine,
							prevInputColumn, prevNameIndex);
					out.append(segment);
				}
			}
			out.append(';');
		}

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMapping(String srcName, String symbolName, FilePosition srcStartPos, FilePosition outStartPos,
			FilePosition outEndPos) {
		Set<LineMappingEntry> lineMappings = getOrCreateLineMapping(outStartPos);
		int nameIndex = symbolName != null ? getOrCreateNameIndex(symbolName) : -1;
		if (srcName == null) {
			lineMappings.add(new LineMappingEntry(-1, nameIndex, null, outStartPos));

		} else {
			int sourceIndex = getOrCreateSourceIndex(srcName);
			lineMappings.add(new LineMappingEntry(sourceIndex, nameIndex, srcStartPos, outStartPos));
		}
	}

	/**
	 * Gets and creates on demand a new set of LineMappingEntries for the line identified by outStartPos
	 */
	private Set<LineMappingEntry> getOrCreateLineMapping(FilePosition outStartPos) {
		int line = outStartPos.getLine();
		Set<LineMappingEntry> lineMappings;
		if (line < mappings.size()) {
			lineMappings = mappings.get(line);
			if (lineMappings == null) {
				lineMappings = new TreeSet<>();
				mappings.set(line, lineMappings);
			}
		} else {
			for (int i = mappings.size(); i < line; i++) {
				mappings.add(null);
			}
			lineMappings = new TreeSet<>();
			mappings.add(lineMappings);
		}
		return lineMappings;
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

}
