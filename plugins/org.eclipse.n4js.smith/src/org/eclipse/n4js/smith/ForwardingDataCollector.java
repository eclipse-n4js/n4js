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

/**
 * Base class for decorators of a DataCollector.
 */
public abstract class ForwardingDataCollector extends DataCollector {

	private final DataCollector delegate;

	/**
	 * Constructor
	 */
	protected ForwardingDataCollector(DataCollector delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getId() {
		return delegate.getId();
	}

	@Override
	public DataCollector getParent() {
		return delegate.getParent();
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
	public Measurement getMeasurement() {
		return delegate.getMeasurement();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public Measurement getMeasurementIfInactive() {
		return delegate.getMeasurementIfInactive();
	}

	@Override
	public Measurement getMeasurementIfInactive(String name) {
		return delegate.getMeasurementIfInactive(name);
	}

	@Override
	public Measurement getMeasurement(String name) {
		return delegate.getMeasurement(name);
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
