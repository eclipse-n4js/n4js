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
 * The data from a CSV file.
 */
public class CSVData implements Iterable<CSVRecord> {
	private final List<CSVRecord> records;

	/**
	 * Creates a new empty instance.
	 */
	protected CSVData() {
		records = new ArrayList<>();
	}

	/**
	 * Creates a new instance with the given records.
	 *
	 * @param records
	 *            the records that make up the CSV data
	 */
	private CSVData(List<CSVRecord> records) {
		this.records = Objects.requireNonNull(records);
	}

	/**
	 * Adds the given record to this data.
	 *
	 * @param record
	 *            the record to add
	 */
	protected void add(CSVRecord record) {
		records.add(Objects.requireNonNull(record));
	}

	/**
	 * Returns the number of records.
	 *
	 * @return the number of records
	 */
	public int getSize() {
		return records.size();
	}

	/**
	 * Returns the record with the given index.
	 *
	 * @param index
	 *            the index of the record to return
	 *
	 * @return the record with the given index
	 *
	 * @throws IndexOutOfBoundsException
	 *             if the given index is out of bounds
	 */
	public CSVRecord get(int index) {
		return records.get(index);
	}

	/**
	 * Returns the field with the given row and column indices.
	 *
	 * @param rowIndex
	 *            the row index that identifies the record
	 * @param colIndex
	 *            the column index that identifies the field
	 *
	 * @return the field at the given row and column indices
	 *
	 * @throws IndexOutOfBoundsException
	 *             if any of the given indices is out of bounds
	 */
	public String get(int rowIndex, int colIndex) {
		return get(rowIndex).get(colIndex);
	}

	/**
	 * Returns a view of this CSV data with the given parameters. Passing negative values for the row or column count
	 * will include all rows or columns, respectively, starting at the given row and column offsets.
	 *
	 * @param rowIndex
	 *            the index of the first row to include in the view
	 * @param colIndex
	 *            the index of the first column to include in the view
	 * @param rowCount
	 *            the number of rows to include in the view (pass negative value to include all remaining rows)
	 * @param colCount
	 *            the number of columns to include in the view (pass negative value to include all remaining columns)
	 *
	 * @return a view of this data with the specified parameters
	 *
	 * @throws IndexOutOfBoundsException
	 *             if any of the given indices or counts is out of bounds
	 */
	public CSVData getRange(int rowIndex, int colIndex, int rowCount, int colCount) {
		final int actualRowCount = (rowCount < 0) ? getSize() - rowIndex : rowCount;
		List<CSVRecord> result = new ArrayList<>();
		for (int i = rowIndex; i < rowIndex + actualRowCount; i++)
			result.add(get(i).getRange(colIndex, colCount));
		return new CSVData(result);
	}

	@Override
	public Iterator<CSVRecord> iterator() {
		return Collections.unmodifiableList(records).iterator();
	}

	@Override
	public String toString() {
		return records.toString();
	}
}
