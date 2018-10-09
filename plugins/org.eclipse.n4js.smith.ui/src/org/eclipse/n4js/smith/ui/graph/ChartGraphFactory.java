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
package org.eclipse.n4js.smith.ui.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataPoint;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.SimpleTimeFormat;
import org.eclipse.n4js.utils.IndentLevel;

/**
 * Factory for graphs representing collected performance data.
 */
public class ChartGraphFactory {

	/** Uses provided key to obtain performance data and builds graph representing it. */
	public static VisualisationSnapshot buildGraph(String key, float baseHeight, float baseWidth, String label) {
		DataSeries dataSeries = CollectedDataAccess.getDataSeries(key);
		if (dataSeries.hasNoData())
			return new VisualisationSnapshot(label, new VisualisationGraph(), label + " (no data)");

		return createChartGraph(dataSeries, baseHeight, baseWidth, label);
	}

	private static VisualisationSnapshot createChartGraph(DataSeries series, float baseHeight, float baseWidth,
			String label) {
		StringJoiner sj = new StringJoiner("\n");
		IndentLevel indent = new IndentLevel("\t");
		final List<DataPoint> data = series.getData().stream().sorted(ChartGraphFactory::compareDesc)
				.collect(Collectors.toList());
		final int size = data.size();

		if (size == 1) {
			sj.add(indent.get() + SimpleTimeFormat.convert(data.get(0).nanos) + " - " + series.name);
			float max = data.get(0).nanos.floatValue();
			float min = 0.0f;
			List<Long> data100 = new LinkedList<>();
			data100.add(data.get(0).nanos);
			ChartNode node100 = new ChartNode("100%", "all 100 %", data100, 1.0f, min, max, baseHeight, baseWidth,
					0, 1, size);
			return new VisualisationSnapshot(label, new VisualisationGraph(node100), sj.toString());
		}

		final int top20PercentMarker = (int) Math.floor(size * 0.20) + 1;
		float max = data.get(0).nanos.floatValue();
		float min = data.get(size - 1).nanos.floatValue();

		int i = 0;
		List<Long> data20 = new LinkedList<>();
		sj.add(indent.get() + "top 20% :");
		indent.increase();
		DataPoint dataPoint = null;
		while (i < top20PercentMarker) {
			dataPoint = data.get(i);
			data20.add(dataPoint.nanos);
			sj.add(indent.get() + SimpleTimeFormat.convert(dataPoint.nanos) + " - " + dataPoint.name);
			i += 1;
		}

		List<Long> dataGap = new LinkedList<>();
		dataGap.add(data.get(i - 1).nanos);
		dataGap.add(data.get(i).nanos);

		indent.decrease();
		sj.add(indent.get() + "the 80% :");
		indent.increase();
		List<Long> data80 = new LinkedList<>();
		while (i < size) {
			dataPoint = data.get(i);
			data80.add(dataPoint.nanos);
			sj.add(indent.get() + SimpleTimeFormat.convert(dataPoint.nanos) + " - " + dataPoint.name);
			i += 1;
		}

		ChartNode node20 = new ChartNode("20%", "top 20 %", data20, 0.2f, min, max, baseHeight, baseWidth, 0,
				top20PercentMarker,
				size);
		ChartNode nodeGap = new ChartNode("GAP", "gap<20%, 80%>", dataGap, 0.5f, min, max, baseHeight, baseWidth,
				top20PercentMarker - 1, top20PercentMarker + 1, size);
		ChartNode node80 = new ChartNode("80%", "bottom 80 %", data80, 0.8f, min, max, baseHeight, baseWidth,
				top20PercentMarker, size, size);
		return new VisualisationSnapshot(label, new VisualisationGraph(node20, nodeGap, node80), sj.toString());
	}

	private static int compareDesc(DataPoint data1, DataPoint data2) {
		return Long.compare(data2.nanos, data1.nanos);
	}
}
