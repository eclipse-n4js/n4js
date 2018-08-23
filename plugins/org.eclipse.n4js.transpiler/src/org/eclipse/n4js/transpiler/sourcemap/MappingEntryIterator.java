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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over mapping entries of a source map. These mappings are organized by the source map by line, this is made
 * transparent for users of this iterator.
 * <p>
 * This helper class is supposed to be only used by {@link SourceMap#getGenMappings()}.
 */
class MappingEntryIterator implements Iterator<MappingEntry> {

	final Iterator<? extends LineMappings> lineIter;
	Iterator<MappingEntry> columnIter;

	MappingEntryIterator(Iterator<? extends LineMappings> lineIter) {
		this.lineIter = lineIter;
		udpateColumnIter();
	}

	private void udpateColumnIter() {
		while (lineIter.hasNext()) {
			LineMappings lineMappings = lineIter.next();
			if (lineMappings != null) { // null in case line is not mapped
				columnIter = lineMappings.iterator();
				if (columnIter.hasNext()) {
					return;
				}
			}
		}
		columnIter = null;
	}

	@Override
	public boolean hasNext() {
		return (columnIter != null);
	}

	@Override
	public MappingEntry next() {
		if (columnIter == null) {
			throw new NoSuchElementException("no more mappings can be iterated");
		}
		MappingEntry next = columnIter.next();
		if (!columnIter.hasNext()) {
			udpateColumnIter();
		}
		return next;
	}

}