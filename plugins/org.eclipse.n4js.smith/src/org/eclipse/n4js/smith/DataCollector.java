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
import java.util.List;

/**
 * Container for collecting {@link DataPoint}s based on {@link Measurement}s. Can be used stand alone to gather stand
 * alone data. Can have {@link #getChildren() children} which allows to express nested or more elaborated data
 * relations.
 * <p>
 * Collector has internal states: {@code paused} and {@code not paused}. In {@code paused} state data is not collected
 * by the collector. Default state is {@code paused}.
 */
public abstract class DataCollector {

	/** Returns this collector's unique identifier. */
	public abstract String getId();

	/** Returns collection of child collector, can be empty list (never {@code null}). */
	public abstract Collection<DataCollector> getChildren();

	/** Tells if this collector is paused. */
	public abstract boolean isPaused();

	/** Tells if this collector currently has an active measurement. */
	public abstract boolean hasActiveMeasurement();

	/**
	 * Convenience method. Same as {@link #getMeasurement(String)}, using this collector's {@link #getId() id} as
	 * <code>name</code>.
	 */
	public Measurement getMeasurement() {
		return getMeasurement(getId());
	}

	/**
	 * Convenience method. Same as {@link #getMeasurementIfInactive(String)}, using this collector's {@link #getId() id}
	 * as <code>name</code>.
	 * <p>
	 * Use of this method should be avoided as far as possible, because it will bypass some consistency checks.
	 */
	public Measurement getMeasurementIfInactive() {
		return getMeasurementIfInactive(getId());
	}

	/**
	 * Same as {@link #getMeasurement(String)}, but does not warn about reentrant invocations.
	 * <p>
	 * Use of this method should be avoided as far as possible, because it will bypass some consistency checks.
	 */
	public abstract Measurement getMeasurementIfInactive(String name);

	/**
	 * Returns new instance of the {@link Measurement}. When user invokes {@link Measurement#close()} this collector
	 * will collect its data.
	 */
	public abstract Measurement getMeasurement(String name);

	/** Returns list of collected data. */
	public abstract List<DataPoint> getData();

	/** Pauses set paused state according to the provided flag. */
	public abstract void setPaused(boolean paused);

	/** Stop this data collector and all children but continue normally with ongoing measurements */
	public abstract void stop();

	/** Clear all data collected so far but continue normally with ongoing measurements */
	public abstract void resetData();

	/** Clear all data collected so far. */
	public abstract void purgeData();

	// package
	/** Return immediate child with the provided ID. */
	abstract DataCollector getChild(String id);

	/** Add given collector as child. */
	abstract void addChild(DataCollector child);

	/** Get keys for all immediate children. */
	abstract Collection<String> childrenKeys();
}
