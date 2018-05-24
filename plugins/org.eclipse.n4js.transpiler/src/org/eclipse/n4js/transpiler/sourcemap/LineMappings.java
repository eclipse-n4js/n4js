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

import java.util.TreeSet;

/**
 * TreeSet with improved toString method for simplifying debugging. The line mappings are a tree set to automatically
 * sort the mappings by column.
 */
public class LineMappings extends TreeSet<MappingEntry> {

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
	MappingEntry findEntryByGenColumn(int col) {
		for (MappingEntry entry : this) {
			if (entry.srcColumn <= col) {
				return entry;
			}
		}
		return null;
	}

}