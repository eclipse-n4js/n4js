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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.smith.dash.data.internal.InstanceDataCollector;
import org.eclipse.n4js.smith.dash.data.internal.SomeDataCollector;

import com.google.common.base.Strings;

/**
 * Using injector causes issues in the SharedContributions, i.e. N4JS*ClusteringBuilderState gets different instance
 * than the rest of the system.
 */
public enum DataCollectors {
	/** The instance. */
	INSTANCE;

	// Private constructor prevents instantiation from other classes
	private DataCollectors() {
	}

	public static DataCollectors getInstance() {
		return INSTANCE;
	}

	private final Map<String, DataCollector> collectors = new HashMap<>();
	private final Map<String, DataCollector> queuecollectors = new HashMap<>();

	public DataCollector getOrCreateDataCollector(String key) {
		if (Strings.isNullOrEmpty(key)) {
			throw new RuntimeException("DataCollector key cannot be null or empty");
		}

		DataCollector collector = collectors.get(key);
		if (collector == null) {
			collector = new SomeDataCollector();
			collectors.put(key, collector);
		}
		return collector;
	}

	public DataCollector getOrCreateQueueCollector(String key) {
		if (Strings.isNullOrEmpty(key)) {
			throw new RuntimeException("DataCollector key cannot be null or empty");
		}

		DataCollector collector = queuecollectors.get(key);
		if (collector == null) {
			collector = new InstanceDataCollector();
			queuecollectors.put(key, collector);
		}
		return collector;
	}

	public Map<String, DataCollector> getSimpleCollectors() {
		return Collections.unmodifiableMap(collectors);
	}

	public Map<String, DataCollector> getQueueCollectors() {
		return Collections.unmodifiableMap(queuecollectors);
	}

}
