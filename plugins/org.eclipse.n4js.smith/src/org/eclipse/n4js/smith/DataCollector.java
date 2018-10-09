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
 * alone data. Can have {@link #getParent()} and / or {@link #getChildren()} which allows to express nested or more
 * elaborated data relations.
 * <p>
 * Collector has internal states: {@code paused} and {@code not paused}. In {@code paused} state data is not collected
 * by the collector. Default state is {@code paused}.
 */
public abstract class DataCollector {

	/** Returns this collector's unique identifier. */
	public abstract String getId();

	/** returns parent collector or {@code null} */
	public abstract DataCollector getParent();

	/** returns collection of child collector, can be empty list (never {@code null}) */
	public abstract Collection<DataCollector> getChildren();

	/** tells if this collector is paused. */
	public abstract boolean isPaused();

	/** Tells if this collector currently has an active measurement. */
	public abstract boolean hasActiveMeasurement();

	/**
	 * returns new instance of the {@link Measurement}. When user invokes {@link Measurement#close()} this collector
	 * will collect its data.
	 */
	public abstract Measurement getMeasurement(String name);

	/** returns list of collected data. */
	public abstract List<DataPoint> getData();

	/** Pauses set paused state according to the provided flag. */
	public abstract void setPaused(boolean paused);

	/** Clear all data collected so far. */
	public abstract void purgeData();

	// package
	/** return immediate child with the provided key. */
	abstract DataCollector getChild(String key);

	/** add given collector as child under given key. */
	abstract void addChild(String key, DataCollector child);

	/** get keys for all immidate children. */
	abstract Collection<String> childrenKeys();
}
