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
package org.eclipse.n4js.smith.dash.data.internal;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.n4js.smith.dash.data.DataCollector;
import org.eclipse.n4js.smith.dash.data.DataPoint;
import org.eclipse.n4js.smith.dash.data.Measurement;

/**
 * Collector for timed data. NOT thread safe.
 */
public class TimedDataCollector extends DataCollector {

	private final DataCollector parent;
	private final Map<String, DataCollector> children = new HashMap<>();

	private static final TimedMeasurement NULL_MEASURMENT = new TimedMeasurement("NOOP", TimedDataCollector::noop);
	private boolean notCollecting = true;

	private final List<DataPoint> data = new LinkedList<>();

	/** Convenience constructors, delegates to {@link #TimedDataCollector(DataCollector)} with null argument. */
	public TimedDataCollector() {
		this(null);
	}

	/** Creates instance of the collector. Provided parent can be {@code null}. */
	public TimedDataCollector(DataCollector parent) {
		this.parent = parent;
	}

	@Override
	public Measurement getMeasurement(String name) {
		if (name == null || name.isEmpty())
			throw new RuntimeException(TimedMeasurement.class.getName() + " needs non empty name.");

		if (notCollecting)
			return NULL_MEASURMENT;

		return new TimedMeasurement(name, this::consume);
	}

	private void consume(Measurement measurement) {
		if (measurement instanceof TimedMeasurement) {
			TimedMeasurement timed = (TimedMeasurement) measurement;
			data.add(new DataPoint(timed.name, timed.sw.elapsed(NANOSECONDS)));
		}
	}

	@Override
	public List<DataPoint> getData() {
		return Collections.unmodifiableList(data);
	}

	@Override
	public DataCollector getParent() {
		return parent;
	}

	@Override
	public Collection<DataCollector> getChildren() {
		return children.values();
	}

	@Override
	protected DataCollector getChild(String key) {
		return this.children.get(key);
	}

	@Override
	protected void addChild(String key, DataCollector child) {
		if (this.children.containsKey(key)) {
			throw new RuntimeException("Already contains key " + key + " with child " + this.children.get(key));
		}

		if (this.children.containsValue(child)) {
			String keys = this.children.entrySet()
					.stream()
					.filter(entry -> Objects.equals(entry.getValue(), child))
					.map(Map.Entry::getKey)
					.collect(Collectors.joining(",", "[", "]"));
			throw new RuntimeException("Already contains child " + child + " with keys " + keys);
		}

		this.children.put(key, child);
	}

	@Override
	public Collection<String> childrenKeys() {
		return this.children.keySet();
	}

	@Override
	public void setPaused(boolean paused) {
		this.notCollecting = paused;
		this.children.values().forEach(child -> child.setPaused(paused));
	}

	@Override
	public void purgeData() {
		this.data.clear();
		this.children.values().forEach(c -> c.purgeData());
	}

	/** NOOP consumer. */
	private static void noop(@SuppressWarnings("unused") Measurement measurement) {
		// NOOP
	}
}
