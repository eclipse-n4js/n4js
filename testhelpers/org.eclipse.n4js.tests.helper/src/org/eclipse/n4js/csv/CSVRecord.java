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
package org.eclipse.n4js.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A record of CSV data.
 */
public class CSVRecord implements Iterable<String> {
	private final List<String> record;

	/**
	 * Creates a new empty CSV record.
	 */
	protected CSVRecord() {
		this.record = new ArrayList<>();
	}

	/**
	 * Creates a new CSV record with the given data.
	 *
	 * @param record
	 *            the records
	 */
	private CSVRecord(List<String> record) {
		this.record = Objects.requireNonNull(record);
	}

	/**
	 * Adds the given field to this record.
	 *
	 * @param field
	 *            the field to add
	 */
	protected void add(String field) {
		record.add(Objects.requireNonNull(field));
	}

	/**
	 * Returns the number of fields (columns) in this record.
	 *
	 * @return the number of fields in this record
	 */
	public int getSize() {
		return record.size();
	}

	/**
	 * Returns the field at the given index.
	 *
	 * @param index
	 *            the index of the field to return
	 *
	 * @return the field at the given index
	 *
	 * @throws IndexOutOfBoundsException
	 *             if the given index is out of bounds
	 */
	public String get(int index) {
		return record.get(index);
	}

	/**
	 * Returns a view of this record with the given parameters.
	 *
	 * @param index
	 *            the index of the first field to include in the view
	 * @param count
	 *            the number of fields to include in the view (pass negative value to include all remaining fields)
	 *
	 * @return a view of this record with the given parameters
	 *
	 * @throws IndexOutOfBoundsException
	 *             if either of the given parameters is out of bounds
	 */
	public CSVRecord getRange(int index, int count) {
		final int actualCount = count < 0 ? getSize() - index : count;
		return new CSVRecord(record.subList(index, index + actualCount));
	}

	@Override
	public Iterator<String> iterator() {
		return Collections.unmodifiableList(record).iterator();
	}

	@Override
	public String toString() {
		return record.toString();
	}
}
