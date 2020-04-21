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

import java.util.Set;

import com.google.common.base.Strings;

/** Allows to manage collectors, e.g. querying their data, deleting data or pausing whole data collectors. */
public class CollectedDataAccess {

	/** Pauses all collectors. */
	public static void setPaused(boolean paused) {
		getCollectors().setPaused(paused);
	}

	/** Stops all collectors but allows ongoing measurements to complete normally. */
	public static void stop() {
		getCollectors().stop();
	}

	/**
	 * Deletes data from all collectors.
	 */
	public static void purgeAllData() {
		getCollectors().getRootCollectors().values().forEach(c -> c.purgeData());
	}

	/**
	 * Deletes data from all collectors but allows ongoing measurements to complete normally.
	 */
	public static void resetAllData() {
		getCollectors().getRootCollectors().values().forEach(c -> c.resetData());
	}

	/** returns keys for all top level collectors. */
	public static Set<String> getCollectorsKeys() {
		return getCollectors().getRootCollectors().keySet();
	}

	/** returns true if collector for the provided key has children */
	public static boolean hasNestedData(String key) {
		DataCollector dataCollector = getCollectors().getRootCollectors().get(key);
		if (dataCollector == null)
			throw new RuntimeException("Can't locate data collector for the key: " + key);

		return !dataCollector.getChildren().isEmpty();
	}

	/** return {@link DataSeries} for all data captured by the collector for the provided key */
	public static DataSeries getDataSeries(String key) {
		if (Strings.isNullOrEmpty(key))
			throw new RuntimeException("Invalid key");

		DataCollector dataCollector = getCollectors().getRootCollectors().get(key);
		if (dataCollector == null)
			throw new RuntimeException("Key not found: " + key);

		DataSeries rootSeries = new DataSeries(key, dataCollector.getData());
		collectSeries(dataCollector, rootSeries);

		return rootSeries;
	}

	private static void collectSeries(DataCollector parentCollector, DataSeries parentSeries) {
		parentCollector.childrenKeys().forEach(name -> {
			DataCollector collector = parentCollector.getChild(name);
			DataSeries series = new DataSeries(name, collector.getData());
			parentSeries.addChild(series);
			collectSeries(collector, series);
		});
	}

	/** wrap static access */
	protected static DataCollectors getCollectors() {
		// TODO replace with injection
		return DataCollectors.INSTANCE;
	}

}
