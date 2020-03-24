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

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.n4js.utils.IndentLevel;

/**
 * Utilities for data collection.
 */
public class DataCollectorUtils {

	/**
	 * Same as {@link #dataToString(DataSeries, String)}, but accepts a data collector key.
	 */
	public static String dataToString(String key, String indent) {
		final DataSeries dataSeries = CollectedDataAccess.getDataSeries(key);
		return dataToString(dataSeries, indent);
	}

	/**
	 * Returns a simple, human-readable string with the data of the given {@link DataSeries}.
	 */
	public static String dataToString(DataSeries series, String indent) {
		StringJoiner sj = new StringJoiner("\n");
		IndentLevel indentLevel = new IndentLevel(indent);
		sj.add(indentLevel.get() + SimpleTimeFormat.convert(series.sum) + " - " + series.name);
		collectData(series, sj, indentLevel);
		return sj.toString();
	}

	private static void collectData(DataSeries parentSeries, StringJoiner sj, IndentLevel indentLevel) {

		if (parentSeries.hasNoChildren())
			return;

		indentLevel.increase();

		List<DataSeries> siblings = parentSeries.getChildren().collect(Collectors.toList());
		Collections.sort(siblings, (s1, s2) -> Long.compare(s2.sum, s1.sum));

		long total = 0L;
		for (DataSeries series : siblings) {
			sj.add(indentLevel.get() + SimpleTimeFormat.convert(series.sum) + " - " + series.name);
			collectData(series, sj, indentLevel);

			total += series.sum;
		}

		long missing = parentSeries.sum - total;
		sj.add(indentLevel.get() + "NOT INCLUDED: " + SimpleTimeFormat.convert(missing));

		indentLevel.decrease();
	}
}
