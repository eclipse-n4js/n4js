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

import java.util.Comparator;
import java.util.TreeSet;

/**
 * TreeSet with improved toString method for simplifying debugging. The line mappings are a tree set to automatically
 * sort the mappings by column, the default sort order for mapping entries.
 */
public abstract class LineMappings extends TreeSet<MappingEntry> {

	LineMappings(Comparator<MappingEntry> comparator) {
		super(comparator);
	}

	/**
	 * Basically for debugging, the real "output" is created by {@link SourceMap}.
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return "";
		}
		StringBuilder strb = new StringBuilder();
		strb.append(iterator().next().genLine);
		strb.append(":");
		for (MappingEntry entry : this) {
			strb.append(" ");
			strb.append(entry.toString());
		}
		return strb.toString();
	}

	/**
	 * Returns the closes mapping entry or null, if no such entry is found.
	 */
	public abstract MappingEntry findEntryByColumn(int col);

	/**
	 * Organizes the mapping by position in the generated file.
	 */
	public static class ByGen extends LineMappings {

		/**
		 * Creates a new line mapping, mapping entries will be ordered by genColumn.
		 */
		public ByGen() {
			super(new Comparator<MappingEntry>() {
				@Override
				public int compare(MappingEntry o1, MappingEntry o2) {
					return o1.genColumn - o2.genColumn;
				}
			});
		}

		@Override
		public MappingEntry findEntryByColumn(int col) {
			MappingEntry match = null;
			for (MappingEntry entry : this) {
				if (entry.srcColumn <= col) {
					match = entry;
				} else {
					break;
				}
			}
			return match;
		}
	}

	/**
	 * Organizes the mapping by position in the source file.
	 */
	public static class BySrc extends LineMappings {

		/**
		 * Creates a new line mapping, mapping entries will be ordered by srcColumn.
		 */
		public BySrc() {
			super(new Comparator<MappingEntry>() {
				@Override
				public int compare(MappingEntry o1, MappingEntry o2) {
					return o1.srcColumn - o2.srcColumn;
				}
			});
		}

		@Override
		public MappingEntry findEntryByColumn(int col) {
			MappingEntry match = null;
			for (MappingEntry entry : this) {
				if (entry.srcColumn <= col) {
					match = entry;
				} else {
					break;
				}
			}
			return match;
		}
	}
}