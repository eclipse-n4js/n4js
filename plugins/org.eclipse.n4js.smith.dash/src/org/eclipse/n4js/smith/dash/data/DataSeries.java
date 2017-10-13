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
package org.eclipse.n4js.smith.dash.data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Abstraction for the series of {@link DataPoint}s.
 */
public class DataSeries {
	/** name of this series */
	public final String name;
	/** sum of data in this series */
	public final Long sum;
	private final List<DataPoint> data = new LinkedList<>();
	private final List<DataSeries> children = new LinkedList<>();

	/** construct series from the provided data points list */
	public DataSeries(String name, List<DataPoint> data) {
		this.name = name;
		this.data.addAll(data);
		this.sum = this.data.stream().map(d -> d.nanos).reduce(0L, Long::sum);
	}

	/** add child series to this series. */
	public void addChild(DataSeries child) {
		this.children.add(child);
	}

	/** check if this series has nested series */
	public boolean hasNoChildren() {
		return this.children.isEmpty();
	}

	/** check if this data has nested data */
	public boolean hasNoData() {
		return this.data.isEmpty();
	}

	/** get stream of nested series (just immediate children) */
	public Stream<DataSeries> getChildren() {
		return this.children.stream();
	}

	/** return copy of the data */
	public List<DataPoint> getData() {
		return new LinkedList<>(this.data);
	}

}
