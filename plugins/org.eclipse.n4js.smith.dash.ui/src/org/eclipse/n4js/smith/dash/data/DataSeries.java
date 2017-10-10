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
 *
 */
public class DataSeries {
	private Long sum;
	public final String name;
	List<SomeDataPoint> data = new LinkedList<>();

	private final List<DataSeries> children = new LinkedList();

	public DataSeries(String name, List<SomeDataPoint> data) {
		this.name = name;
		this.data.addAll(data);
		this.sum = this.data.stream().map(d -> d.nanos).reduce(0L, Long::sum);
	}

	public void addChild(DataSeries child) {
		this.children.add(child);
		this.sum += child.sum;
	}

	public Long getSum() {
		return this.sum;
	}

	public boolean hasNoChildren() {
		return this.children.isEmpty();
	}

	public boolean hasNoData() {
		return this.data.isEmpty();
	}

	public Stream<DataSeries> getChildren() {
		return this.children.stream();
	}

	public List<SomeDataPoint> getData() {
		return new LinkedList<>(this.data);
	}

}
