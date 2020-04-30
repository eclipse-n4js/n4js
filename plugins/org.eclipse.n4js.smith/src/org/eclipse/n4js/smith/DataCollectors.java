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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Singleton managing {@link DataCollector} instances. Should be used for creating and obtaining instances of the
 * collectors. Data collectors obtained by other means will not be reachable by this class APIs.
 * <p>
 * Using injector causes issues in the SharedContributions, i.e. N4JS*ClusteringBuilderState gets different instance
 * than the rest of the system.
 */
// TODO replace with Singleton once injection in shared ui contribution is clarified
public enum DataCollectors {
	/** The instance. */
	INSTANCE;

	private final Map<String, DataCollector> collectors = new HashMap<>();
	private final AtomicBoolean pauseAllCollectors = new AtomicBoolean(true);

	/**
	 * returns a new or an existing collector for the provided key. If there is no collector corresponding to the key
	 * creates new one and returns it. Any further call with the same key will return previously created instance.
	 */
	public DataCollector getOrCreateDataCollector(String key) {
		return getOrCreateDataCollector(key, null);
	}

	/**
	 * returns a new or an existing sequentializing collector for the provided key. If there is no collector
	 * corresponding to the key creates new one and returns it. Any further call with the same key will return
	 * previously created instance.
	 */
	public DataCollector getOrCreateSerialDataCollector(String key) {
		return getOrCreateSerialDataCollector(key, null);
	}

	/**
	 * returns a new or an existing collector for a given key, that is child of the provided parent collector. If no
	 * parent is provided behaves as {@link #getOrCreateDataCollector(String)}
	 */
	public DataCollector getOrCreateDataCollector(String key, DataCollector parent) {
		return get(key, parent, false);
	}

	/**
	 * returns a new or an existing sequentializing collector for a given key, that is child of the provided parent
	 * collector. If no parent is provided behaves as {@link #getOrCreateDataCollector(String)}
	 */
	public DataCollector getOrCreateSerialDataCollector(String key, DataCollector parent) {
		DataCollector result = get(key, parent, true);
		Preconditions.checkState(result instanceof SerializingDataCollector);
		return result;
	}

	private synchronized DataCollector get(String key, DataCollector parent, boolean serial) {
		if (Strings.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("DataCollector key cannot be null or empty");
		}

		DataCollector collector = null;

		if (parent == null) {
			collector = collectors.get(key);
			if (collector == null) {
				collector = createCollector(key, serial);
				collectors.put(key, collector);
			}
		} else {
			collector = parent.getChild(key);
			if (collector == null) {
				collector = createCollector(key, serial);
				parent.addChild(collector);
			}
		}

		return collector;
	}

	private DataCollector createCollector(String key, boolean serial) {
		DataCollector collector = new TimedDataCollector(key);
		if (serial) {
			collector = new SerializingDataCollector(collector);
		}
		collector.setPaused(this.pauseAllCollectors.get());
		return collector;
	}

	// package
	/** Returns mapping between all top level collectors and their names. */
	Map<String, DataCollector> getRootCollectors() {
		return Collections.unmodifiableMap(collectors);
	}

	/** sets {@code paused} state for all data collectors. */
	synchronized void setPaused(boolean paused) {
		this.pauseAllCollectors.set(paused);
		collectors.values().forEach(collector -> collector.setPaused(paused));
	}

	/**
	 * Shows a warning on {@link System#err standard error}, but only if data collection is active, i.e. *not* paused.
	 */
	public void warn(String msg) {
		if (!pauseAllCollectors.get()) {
			// since this will only happen during local, manual debugging of the N4JS IDE, we can simply use System.err:
			System.err.println("WARNING: " + msg);
		}
	}
}
