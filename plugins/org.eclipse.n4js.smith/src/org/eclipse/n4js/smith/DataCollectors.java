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
	 * returns existing collector for the provided key. If there is no collector corresponding to the key creates new
	 * one and returns it. Any further call with the same key will return previously created instance.
	 */
	public DataCollector getOrCreateDataCollector(String key) {
		if (Strings.isNullOrEmpty(key)) {
			throw new RuntimeException("DataCollector key cannot be null or empty");
		}

		return get(key, null);
	}

	/**
	 * returns collector for a given key, that is child of a parent collector specified by the provided keys. Parent are
	 * looked up according to the order of the provided keys. Parents are <b>NOT</b> created. If parent with given key
	 * cannot be located exception is thrown. If no keys are provided returns behaves as
	 * {@link #getOrCreateDataCollector(String)}
	 */
	public DataCollector getOrCreateDataCollector(String key, String... parentKeys) {

		if (Strings.isNullOrEmpty(key)) {
			throw new RuntimeException("DataCollector key cannot be null or empty");
		}

		DataCollector parent = getParent(parentKeys);
		return get(key, parent);
	}

	/**
	 * returns collector for a given key, that is child of the provided parent collector. If no keys are provided
	 * returns behaves as {@link #getOrCreateDataCollector(String)}
	 */
	public DataCollector getOrCreateDataCollector(String key, DataCollector parent) {

		if (Strings.isNullOrEmpty(key)) {
			throw new RuntimeException("DataCollector key cannot be null or empty");
		}

		return get(key, parent);
	}

	private synchronized DataCollector getParent(String... parentKeys) {
		DataCollector parent = null;
		if (parentKeys != null) {
			String parentKey = parentKeys[0];
			String prevParentKey = parentKey;
			parent = collectors.get(parentKey);
			if (parent == null)
				throw new RuntimeException("Cannot find parent for key " + parentKey);
			// we have root parent, iterate over its children
			for (int i = 1; i < parentKeys.length; i++) {
				parentKey = parentKeys[i];
				parent = parent.getChild(parentKey);
				// since we have parent keys we break at key with no mapping to parent
				if (parent == null)
					throw new RuntimeException(
							"Cannot find collector for key " + parentKey + " and parent " + prevParentKey);
				prevParentKey = parentKey;
			}
		}
		return parent;
	}

	private synchronized DataCollector get(String key, DataCollector parent) {
		DataCollector collector = null;

		if (parent == null) {
			collector = collectors.get(key);
			if (collector == null) {
				collector = new TimedDataCollector();
				collector.setPaused(this.pauseAllCollectors.get());
				collectors.put(key, collector);
			}
		} else {
			collector = parent.getChild(key);
			if (collector == null) {
				collector = new TimedDataCollector();
				collector.setPaused(this.pauseAllCollectors.get());
				parent.addChild(key, collector);
			}
		}

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

}
