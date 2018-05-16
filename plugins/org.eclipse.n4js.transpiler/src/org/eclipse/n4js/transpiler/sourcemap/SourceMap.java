/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Source map data structure according to the
 * <a href="https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k">Source Map Revision 3
 * Proposal</a> including output line (which is not present in the Base64 VLQ representation.
 */
public class SourceMap {

	/**
	 * Used to create a source map from a source map file. This uses the {@link SourceMapParser} to parse the given
	 * source map.
	 */
	public static SourceMap parse(CharSequence s) {
		return SourceMapParser.parse(s);
	}

	/**
	 * The version, this is usally 3.
	 */
	public String version = "3";
	/**
	 * The generated file for which the source map was created.
	 */
	public String file = "";
	/**
	 * Optionally, see source map spec. Not used here.
	 */
	public String sourceRoot = "";
	/**
	 * The list of source files.
	 */
	final public List<String> sources = new ArrayList<>();
	/**
	 * The list of symbol names. Not used here.
	 */
	final public List<String> names = new ArrayList<>();
	/**
	 * The mappings, the index of the mapping defines the zero-based line number.
	 */
	final List<LineMappings> mappings = new ArrayList<>();

	/**
	 * Adds a new mapping entry.
	 */
	public void addMappig(MappingEntry entry) {
		LineMappings lineMapping = getOrCreateLineMapping(entry.genLine);
		lineMapping.add(entry);
	}

	/**
	 * Gets and creates on demand a new set of LineMappingEntries for the line identified by outStartPos
	 */
	private LineMappings getOrCreateLineMapping(int outputLine) {
		LineMappings lineMappings;
		if (outputLine < mappings.size()) {
			lineMappings = mappings.get(outputLine);
			if (lineMappings == null) {
				lineMappings = new LineMappings();
				mappings.set(outputLine, lineMappings);
			}
		} else {
			for (int i = mappings.size(); i < outputLine; i++) {
				mappings.add(null);
			}
			lineMappings = new LineMappings();
			mappings.add(lineMappings);
		}
		return lineMappings;
	}

	/**
	 * Emits the source map in the JSON format with Base64 VLQ values. Uses {@link #toString(Appendable)} internally.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		try {
			toString(strb);
		} catch (IOException ex) {
			// should not happen for string builder
			throw new IllegalStateException(ex);
		}
		return strb.toString();
	}

	/**
	 * Emits the source map in the JSON format with Base64 VLQ values to the given appendable.
	 */
	public void toString(Appendable out) throws IOException {
		writeBeginning(out);
		writeMappings(out);
		writeClosing(out);
	}

	private void appendProp(Appendable out, String propName) throws IOException {
		out.append("\n\t\"");
		out.append(propName);
		out.append("\": ");
	}

	private void writeBeginning(Appendable out) throws IOException {
		out.append("{");
		appendProp(out, "version");
		out.append(version);
		out.append(",");
		appendProp(out, "file");
		out.append("\"");
		out.append(esc(file));
		out.append("\",");
		appendProp(out, "sourceRoot");
		out.append("\"");
		out.append(esc(sourceRoot));
		out.append("\",");
		appendProp(out, "sources");
		out.append("[");
		out.append(sources.stream().map(source -> "\"" + esc(source) + "\"").collect(Collectors.joining(",")));
		out.append("],");
		// appendProp(out, "sourceContent");
		// out.append("[");
		// out.append(sources.keySet().stream().map(source -> "null").collect(Collectors.joining(",")););
		// out.append("],");
		appendProp(out, "names");
		out.append("[");
		out.append(names.stream().map(name -> "\"" + esc(name) + "\"").collect(Collectors.joining(",")));
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
		boolean bFirst = true;
		MappingEntry.PreviousEntry prev = new MappingEntry.PreviousEntry();
		for (LineMappings lineMappings : mappings) {
			if (bFirst) {
				bFirst = false;
			} else {
				out.append(';');
			}
			if (lineMappings != null) {
				Iterator<MappingEntry> iter = lineMappings.iterator();
				MappingEntry entry = iter.next();
				String segment = entry.toBase64VLQRelative(prev);
				entry.updatePrev(prev);
				out.append(segment);
				while (iter.hasNext()) {
					out.append(',');
					entry = iter.next();
					segment = entry.toBase64VLQRelative(prev);
					entry.updatePrev(prev);
					out.append(segment);
				}
				prev.genColumn = 0;
			}
		}

	}

}
