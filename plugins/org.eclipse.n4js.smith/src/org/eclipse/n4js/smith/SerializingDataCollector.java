/**
 * Copyright (c) 2020 NumberFour AG.
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
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Supplier;

/**
 * A collector decorator that will ensure that measurements are not created concurrently.
 */
class SerializingDataCollector extends DataCollector {

	/**
	 * A decorator for a measurement that aquires a lock before measuring.
	 */
	class LockedMeasurement implements Measurement {
		private final Measurement m;

		LockedMeasurement(Supplier<Measurement> supplier) {
			lock.lock();
			m = supplier.get();
		}

		@Override
		public void close() {
			try {
				m.close();
			} finally {
				lock.unlock();
			}
		}
	}

	private final ReentrantLock lock = new ReentrantLock(true);
	private final DataCollector delegate;

	SerializingDataCollector(DataCollector delegate) {
		this.delegate = delegate;
	}

	private Measurement getMeasurement(Supplier<Measurement> supplier) {
		if (!isPaused()) {
			return new LockedMeasurement(supplier);
		} else {
			return supplier.get();
		}
	}

	@Override
	public Measurement getMeasurement() {
		return getMeasurement(() -> delegate.getMeasurement());
	}

	@Override
	public Measurement getMeasurementIfInactive() {
		return getMeasurement(() -> delegate.getMeasurementIfInactive());
	}

	@Override
	public Measurement getMeasurementIfInactive(String name) {
		return getMeasurement(() -> delegate.getMeasurementIfInactive(name));
	}

	@Override
	public Measurement getMeasurement(String name) {
		return getMeasurement(() -> delegate.getMeasurement(name));
	}

	@Override
	public String getId() {
		return delegate.getId();
	}

	@Override
	public Collection<DataCollector> getChildren() {
		return delegate.getChildren();
	}

	@Override
	public boolean isPaused() {
		return delegate.isPaused();
	}

	@Override
	public boolean hasActiveMeasurement() {
		return delegate.hasActiveMeasurement();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public List<DataPoint> getData() {
		return delegate.getData();
	}

	@Override
	public void setPaused(boolean paused) {
		delegate.setPaused(paused);
	}

	@Override
	public void purgeData() {
		delegate.purgeData();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public void resetData() {
		delegate.resetData();
	}

	@Override
	public void stop() {
		delegate.stop();
	}

	@Override
	DataCollector getChild(String id) {
		return delegate.getChild(id);
	}

	@Override
	void addChild(DataCollector child) {
		delegate.addChild(child);
	}

	@Override
	Collection<String> childrenKeys() {
		return delegate.childrenKeys();
	}
}