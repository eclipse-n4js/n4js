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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

/** */
public class SomeDataAccess {
	private static final DataCollectors collectors = DataCollectors.INSTANCE;

	/** */
	public static Set<String> getCollectorsKeys() {
		return collectors.getRootCollectors().keySet();
	}

	public static boolean isValid(String key) {
		DataCollector dataCollector = collectors.getRootCollectors().get(key);
		if (dataCollector == null)
			return false;

		return !dataCollector.getData().isEmpty();
	}

	public static boolean hasNestedData(String key) {
		DataCollector dataCollector = collectors.getRootCollectors().get(key);
		if (dataCollector == null)
			throw new RuntimeException("Can't locate data collector for the key: " + key);

		return !dataCollector.getChildren().isEmpty();
	}

	/** */
	public static List<SomeDataPoint> gRawData2(String key) {

		DataCollector dataCollector = collectors.getRootCollectors().get(key);
		if (dataCollector == null)
			return Collections.emptyList();

		return dataCollector.getData().stream().sorted(SomeDataAccess::compareDesc)
				.collect(Collectors.toList());
	}

	private static int compareDesc(SomeDataPoint data1, SomeDataPoint data2) {
		return Long.compare(data2.nanos, data1.nanos);
	}

	/** */
	public static List<SomeDataPoint> getData(String key) {

		DataCollector dataCollector = collectors.getRootCollectors().get(key);
		if (dataCollector == null)
			return Collections.emptyList();

		return dataCollector.getData();
	}

	public static DataSeries getDataSeries(String key) {
		if (Strings.isNullOrEmpty(key))
			throw new RuntimeException("Invalid key");

		DataCollector dataCollector = collectors.getRootCollectors().get(key);

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

	public static void setPaused(boolean paused) {
		collectors.setPaused(paused);
	}

	/**
	 *
	 */
	public static void purgeAllData() {
		collectors.getRootCollectors().values().forEach(c -> c.purgeData());
	}

}
