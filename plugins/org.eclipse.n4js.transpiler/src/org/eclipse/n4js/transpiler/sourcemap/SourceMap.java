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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

/**
 * Source map data structure according to the
 * <a href="https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k">Source Map Revision 3
 * Proposal</a> including output line which is not present in the Base64 VLQ representation. The output line is used to
 * enable efficient bidirectional mappings for tools such as sourcemap viewers.
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
	 * Loads a source map from given file and resolves all paths as long as their location is defined relative to the
	 * given source map path.
	 */
	public static SourceMap loadAndResolve(Path sourceMapPath) throws IOException {
		InputStream inStream = Files.newInputStream(sourceMapPath);
		try (InputStreamReader reader = new InputStreamReader(inStream, Charsets.UTF_8)) {
			SourceMap sourceMap = parse(CharStreams.toString(reader));
			sourceMap.resolvedMapFile = sourceMapPath;
			Path sourceMapFolder = sourceMapPath.getParent();
			sourceMap.resolvedFile = sourceMapFolder.resolve(sourceMap.file);
			ArrayList<Path> list = new ArrayList<>(sourceMap.sources.size());
			for (String src : sourceMap.sources) {
				Path path = sourceMapFolder.resolve(src);
				list.add(path);
			}
			sourceMap.resolvedSources = Collections.unmodifiableList(list);
			return sourceMap;
		}
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
	 * The mappings, the index of the mapping defines the zero-based line number in the gen file.
	 */
	final List<LineMappings.ByGen> genMappings = new ArrayList<>();

	/**
	 * Similar to mappings but based on src. This is a derived property not directly found in the source map file (which
	 * only maps from generated code to source).
	 */
	final List<List<LineMappings.BySrc>> srcMappings = new ArrayList<>();

	/**
	 * set only in {@link #loadAndResolve(Path)}
	 */
	Path resolvedMapFile = null;
	/**
	 * set only in {@link #loadAndResolve(Path)}
	 */
	Path resolvedFile = null;
	/**
	 * The list of resolved source files, set only in {@link #loadAndResolve(Path)}
	 */
	List<Path> resolvedSources = Collections.emptyList();

	/**
	 * Adds a new mapping entry.
	 */
	public void addMappig(MappingEntry entry) {
		LineMappings.ByGen lineMapping = getOrCreateSrcLineMapping(entry.genLine);
		lineMapping.add(entry);

		LineMappings.BySrc lineSrcMapping = getOrCreateSrcLineMapping(entry.srcIndex, entry.srcLine);
		lineSrcMapping.add(entry);
	}

	/**
	 * Gets and creates on demand a new set of LineMappingEntries for the line identified by outStartPos
	 */
	private LineMappings.ByGen getOrCreateSrcLineMapping(int outputLine) {
		return getOrCreateLineMapping(genMappings, outputLine, LineMappings.ByGen::new);
	}

	private LineMappings.BySrc getOrCreateSrcLineMapping(int srcIndex, int srcLine) {
		List<LineMappings.BySrc> mappings = null;
		if (srcIndex < srcMappings.size()) {
			mappings = srcMappings.get(srcIndex);
			if (mappings == null) {
				mappings = new ArrayList<>();
				srcMappings.set(srcIndex, mappings);
			}
		} else {
			for (int i = srcMappings.size(); i < srcIndex; i++) {
				srcMappings.add(null);
			}
			mappings = new ArrayList<>();
			srcMappings.add(mappings);
		}
		return getOrCreateLineMapping(mappings, srcLine, LineMappings.BySrc::new);

	}

	private <T extends TreeSet<MappingEntry>> T getOrCreateLineMapping(List<T> mappings, int line,
			Supplier<T> ctor) {
		T lineMappings;
		if (line < mappings.size()) {
			lineMappings = mappings.get(line);
			if (lineMappings == null) {
				lineMappings = ctor.get();
				mappings.set(line, lineMappings);
			}
		} else {
			for (int i = mappings.size(); i < line; i++) {
				mappings.add(null);
			}
			lineMappings = ctor.get();
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
		for (LineMappings.ByGen lineMappings : genMappings) {
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

	/**
	 * Returns the resolved map file. This is only set if the source map was created via {@link #loadAndResolve(Path)}.
	 */
	public Path getResolvedMapFile() {
		return resolvedMapFile;
	}

	/**
	 * Returns the resolved generated file. This is only set if the source map was created via
	 * {@link #loadAndResolve(Path)}.
	 */
	public Path getResolvedFile() {
		return resolvedFile;
	}

	/**
	 * Returns the resolved source files in the same order as the sources. This is only set if the source map was
	 * created via {@link #loadAndResolve(Path)}.
	 */
	public List<Path> getResolvedSources() {
		return resolvedSources;
	}

	/**
	 * Find mapping entry at given generated (JavaScript) position.
	 *
	 * @return the closest entry or null, if no such entry was found.
	 */
	public MappingEntry findMappingForGenPosition(int genLine, int genColumns) {
		if (genLine < 0 || genColumns < 0 || genLine >= genMappings.size()) {
			return null;
		}
		LineMappings.ByGen lineMappings = genMappings.get(genLine);
		if (lineMappings == null) {
			return null;
		}
		MappingEntry entry = lineMappings.findEntryByColumn(genColumns);
		return entry;
	}

	/**
	 * Find mapping entry at given source (N4JS) position.
	 *
	 * @return the closest entry or null, if no such entry was found.
	 */
	public MappingEntry findMappingForSrcPosition(int sourceIndex, int sourceLine, int sourceColumn) {
		if (sourceColumn < 0 || sourceLine < 0 || sourceIndex < 0 || sourceIndex >= srcMappings.size()) {
			return null;
		}
		List<LineMappings.BySrc> mappings = srcMappings.get(sourceIndex);
		if (sourceLine >= mappings.size()) {
			return null;
		}
		LineMappings.BySrc lineMappings = mappings.get(sourceLine);
		if (lineMappings == null) {
			return null;
		}
		MappingEntry entry = lineMappings.findEntryByColumn(sourceColumn);
		return entry;
	}

	/**
	 * Returns the length of the mapping in the generated code, that is the length either of the referenced name or the
	 * length to the next mapping in the same line. Returns -1 if length cannot be computed.
	 */
	public int computeLengthGen(MappingEntry entry) {
		if (entry.nameIndex >= 0 && entry.nameIndex < names.size()) {
			return names.get(entry.nameIndex).length();
		}
		MappingEntry next = nextEntryForTarget(entry, entry.genLine, genMappings);
		if (next == null) {
			return -1;
		}
		return next.genColumn - entry.genColumn;
	}

	/**
	 * Returns the length of the mapping in the source code, that is the length either of the referenced name or the
	 * length to the next mapping in the same line. Returns -1 if length cannot be computed.
	 */
	public int computeLengthSrc(MappingEntry entry) {
		MappingEntry next = nextEntryForTarget(entry, entry.srcLine, srcMappings.get(entry.srcIndex));
		if (next == null) {
			return -1;
		}
		return next.srcColumn - entry.srcColumn;
	}

	/**
	 * Used by computeLength method to find the next mapping entry on the same line of the source or generated code.
	 */
	private MappingEntry nextEntryForTarget(MappingEntry entry, int targetLine,
			List<? extends LineMappings> targetMappings) {
		if (entry == null || targetLine < 0 || targetLine >= targetMappings.size()) {
			return null;
		}

		LineMappings lineMappings = targetMappings.get(targetLine);
		boolean entryFound = false;
		MappingEntry next = null;
		for (MappingEntry e : lineMappings) {
			if (e == entry) {
				entryFound = true;
			} else {
				if (entryFound) {
					next = e;
					break;
				}
			}
		}
		return next;
	}

	/**
	 * Returns an iterable over the gen mappings (i.e. the mapping s found in the source file).
	 */
	public Iterable<MappingEntry> getGenMappings() {
		return new Iterable<>() {
			@Override
			public Iterator<MappingEntry> iterator() {
				return new MappingEntryIterator(genMappings.iterator());
			}
		};
	}

}
