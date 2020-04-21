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
package org.eclipse.n4js.smith;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Collector for timed data. NOT thread safe.
 */
class TimedDataCollector extends DataCollector {

	public static final boolean AVOID_EXCESSIVE_DATA_COLLECTION = true;

	private final String id;
	private final DataCollector parent;
	// maintains insertion order
	private final Map<String, DataCollector> children = new LinkedHashMap<>();

	private static final TimedMeasurement NULL_MEASURMENT = new TimedMeasurement("NOOP", TimedDataCollector::noop);
	private boolean paused = true;

	private Measurement activeMeasurement = null;

	private final List<DataPoint> data = new LinkedList<>();

	/** Convenience constructors, delegates to {@link #TimedDataCollector(String, DataCollector)} with null argument. */
	public TimedDataCollector(String id) {
		this(id, null);
	}

	/** Creates instance of the collector. Provided parent can be {@code null}. */
	public TimedDataCollector(String id, DataCollector parent) {
		this.id = id;
		this.parent = parent;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public boolean hasActiveMeasurement() {
		return activeMeasurement != null;
	}

	@Override
	public Measurement getMeasurementIfInactive(String name) {
		return getMeasurement(name, true);
	}

	@Override
	public Measurement getMeasurement(String name) {
		return getMeasurement(name, false);
	}

	synchronized private Measurement getMeasurement(String name, boolean allowReentrantInvocation) {
		if (name == null || name.isEmpty())
			throw new RuntimeException(TimedMeasurement.class.getName() + " needs non empty name.");

		if (paused)
			return NULL_MEASURMENT;

		if (activeMeasurement != null) {
			if (!allowReentrantInvocation) {
				DataCollectors.INSTANCE.warn("reentrant invocation of #getMeasurement() in data collector " + id);
			}
			return NULL_MEASURMENT;
		}
		activeMeasurement = new TimedMeasurement(name, this::consume);
		return activeMeasurement;
	}

	/** This method must be synchronized to protect race conditions when calling data.add */
	synchronized private void consume(TimedMeasurement measurement) {
		if (measurement != activeMeasurement) {
			DataCollectors.INSTANCE.warn(
					"invocation of #consume() without matching prior call to #getMeasurement() in data collector " + id
							+ "#" + measurement.name);
			return;
		}
		activeMeasurement = null;
		String name = measurement.name;
		long elapsed = measurement.elapsed(TimeUnit.NANOSECONDS);
		if (AVOID_EXCESSIVE_DATA_COLLECTION) {
			// storing every individual data point causes memory issues in large builds;
			// therefore we only keep a single data point accumulating all measurements:
			for (DataPoint dp : data) {
				elapsed += dp.nanos;
			}
			data.clear();
		}
		data.add(new DataPoint(name, elapsed));
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
	public DataCollector getChild(String childId) {
		return this.children.get(childId);
	}

	@Override
	public void addChild(DataCollector child) {
		String childId = child.getId();
		if (this.children.containsKey(childId)) {
			throw new RuntimeException("Already contains key " + childId + " with child " + this.children.get(childId));
		}

		if (this.children.containsValue(child)) {
			String keys = this.children.entrySet()
					.stream()
					.filter(entry -> Objects.equals(entry.getValue(), child))
					.map(Map.Entry::getKey)
					.collect(Collectors.joining(",", "[", "]"));
			throw new RuntimeException("Already contains child " + child + " with keys " + keys);
		}

		this.children.put(childId, child);
	}

	@Override
	public Collection<String> childrenKeys() {
		return this.children.keySet();
	}

	@Override
	public void setPaused(boolean paused) {
		this.activeMeasurement = null;
		this.paused = paused;
		this.children.values().forEach(child -> child.setPaused(paused));
	}

	@Override
	public void stop() {
		synchronized (this) {
			this.paused = true;
		}
		this.children.values().forEach(child -> child.stop());
	}

	@Override
	public void resetData() {
		synchronized (this) {
			this.data.clear();
		}
		this.children.values().forEach(c -> c.resetData());
	}

	@Override
	public void purgeData() {
		this.activeMeasurement = null;
		synchronized (this) {
			this.data.clear();
		}
		this.children.values().forEach(c -> c.purgeData());
	}

	/** NOOP consumer. */
	private static void noop(@SuppressWarnings("unused") Measurement measurement) {
		// NOOP
	}
}
