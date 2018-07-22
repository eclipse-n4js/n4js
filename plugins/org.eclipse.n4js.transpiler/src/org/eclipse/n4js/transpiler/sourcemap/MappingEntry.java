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

/**
 * A MappingEntry represents a single mapping from the generated code position (zero-based line and column) to a source
 * position (also zero-based column and line, and the index of the source file name in the containing source map), and
 * an index to the containing source map names is given.
 *
 * Source position and name index are optional.
 */
public class MappingEntry implements Comparable<MappingEntry> {

	/**
	 * Mutable version of mapping entry used by the parser and when emitting a source map to convert entry values to
	 * relative values.
	 */
	static class PreviousEntry {
		int genLine;
		int genColumn;
		int srcIndex;
		int srcLine;
		int srcColumn;
		int nameIndex;
	}

	/**
	 * The zero-based line in the generated file (i.e. the JavaScript file).
	 */
	public final int genLine;
	/**
	 * The zero-based column in the generated file (i.e. the JavaScript file). This information is indirectly stored in
	 * the source map: the n-th mapping defines the mappings for the n-th line of the generated file.
	 */
	public final int genColumn;
	/**
	 * The (zero-based) index of the source file name in the containing source map. If this is not specified, it is set
	 * to -1. If set (>=0), srcLine and srcColumn must be set as well.
	 */
	public final int srcIndex;
	/**
	 * The zero-based line in the source file (i.e. the N4JS file). Must be set (i.e. >=0) if srcIndex is set (>=0).
	 */
	public final int srcLine;
	/**
	 * The zero-based column in the source file (i.e. the N4JS file). Must be set (i.e. >=0) if srcIndex is set (>=0).
	 */
	public final int srcColumn;
	/**
	 * The (zero-based) index to a symbol name in the containing source map. This is optional, if not set, it is -1.
	 */
	public final int nameIndex;

	/**
	 * Creates a mapping with does not refer to any input documents.
	 */
	public MappingEntry(FilePosition genPos) {
		this(genPos, -1, null, -1);
	}

	/**
	 * Creates a new line mapping entry; the output line number is indirectly defined by the index of the mapping in the
	 * mappings list.
	 */
	public MappingEntry(FilePosition genPos, int srcIndex, FilePosition srcPos, int nameIndex) {
		this.genLine = genPos.getLine();
		this.genColumn = genPos.getColumn();
		this.srcIndex = srcIndex;
		if (srcPos != null) {
			this.srcLine = srcPos.getLine();
			this.srcColumn = srcPos.getColumn();
		} else {
			this.srcLine = -1;
			this.srcColumn = -1;
		}
		this.nameIndex = nameIndex;
	}

	/**
	 * Constructor used by parser, creates a mapping from the encoded VLQ representation according to the source map
	 * spec.
	 *
	 * @param genLine
	 *            the line in the output file, this does not stem from the VLQ but from the line count
	 * @param segmentValues
	 *            the encoded VLQ values
	 * @param prev
	 *            the previous values to which the given mapping values are relative.
	 */
	public MappingEntry(int genLine, int[] segmentValues, PreviousEntry prev) {
		this.genLine = genLine;
		if (segmentValues == null || segmentValues.length == 0) {
			this.genColumn = -1;
			this.srcIndex = -1;
			this.srcLine = -1;
			this.srcColumn = -1;
			this.nameIndex = -1;
			return;
		}

		if (segmentValues.length == 1) {
			this.genColumn = prev.genColumn + segmentValues[0];
			this.srcIndex = -1;
			this.srcLine = -1;
			this.srcColumn = -1;
			this.nameIndex = -1;

			prev.genColumn = genColumn;
			return;
		}

		if (segmentValues.length < 4 || segmentValues.length > 5) {
			throw new IllegalArgumentException();
		}

		this.genColumn = prev.genColumn + segmentValues[0];
		this.srcIndex = prev.srcIndex + segmentValues[1];
		this.srcLine = prev.srcLine + segmentValues[2];
		this.srcColumn = prev.srcColumn + segmentValues[3];

		if (segmentValues.length == 5) {
			nameIndex = prev.nameIndex + segmentValues[4];
		} else {
			this.nameIndex = -1;
		}

	}

	/**
	 * MappingEntry implements comparable to automatically sort the entries when creating them from the transpiler.
	 */
	@Override
	public int compareTo(MappingEntry o) {
		return genColumn - o.genColumn;
	}

	/**
	 * Returns a Base64VLQ representation of this mapping as used for a source map, i.e. without output line number and
	 * relative to given previous mapping. The fields are written in the following order:
	 *
	 * <ol>
	 * <li>relative genColumn
	 * <li>relative srcIndex
	 * <li>relative srcLine
	 * <li>relative srcColumn
	 * <li>relative nameIndex
	 * </ol>
	 *
	 * The previous mapping is not updated, this must be done explicitly via {@link #updatePrev(PreviousEntry)}.
	 */
	public String toBase64VLQRelative(PreviousEntry prev) {
		if (srcIndex < 0) {
			return Base64VLQ.encode(
					genColumn - prev.genColumn);
		}
		if (nameIndex < 0) {
			return Base64VLQ.encode(
					genColumn - prev.genColumn,
					srcIndex - prev.srcIndex,
					srcLine - prev.srcLine,
					srcColumn - prev.srcColumn);
		}
		return Base64VLQ.encode(
				genColumn - prev.genColumn,
				srcIndex - prev.srcIndex,
				srcLine - prev.srcLine,
				srcColumn - prev.srcColumn,
				nameIndex - prev.nameIndex);
	}

	/**
	 * Updates the base values used for computing the relative VLQ values.
	 *
	 * @param prev
	 *            the entry that is to be updated
	 */
	public void updatePrev(PreviousEntry prev) {
		prev.genColumn = genColumn;
		if (srcIndex >= 0) {
			prev.srcIndex = srcIndex;
			prev.srcLine = srcLine;
			prev.srcColumn = srcColumn;
			if (nameIndex >= 0) {
				prev.nameIndex = nameIndex;
			}
		}
	}

	/**
	 * Basically for debugging purposes.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append(genColumn);
		if (srcLine >= 0) {
			strb.append("->");
			strb.append(srcLine);
			strb.append(":");
			strb.append(srcColumn);
			if (srcIndex > 0) {
				strb.append('@');
				strb.append(srcIndex);
			}
		}
		return strb.toString();
	}

}