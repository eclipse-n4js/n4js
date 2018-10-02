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

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.SimpleTimeFormat;
import org.eclipse.n4js.utils.IndentLevel;

/**
 * Factory for graphs representing collected performance data.
 */
public class StackGraphFactory {

	/** Uses provided key to obtain performance data and builds graph representing it. */
	public static VisualisationSnapshot buildGraph(String key, float baseHeight, float baseWidth, String label) {
		DataSeries dataSeries = CollectedDataAccess.getDataSeries(key);
		if (dataSeries.hasNoData())
			return new VisualisationSnapshot(label, new VisualisationGraph(), label + " (no data)");

		return createStackGraph(dataSeries, baseHeight, baseWidth, label);
	}

	private static VisualisationSnapshot createStackGraph(DataSeries series, float baseHeight, float baseWidth,
			String label) {
		StringJoiner sj = new StringJoiner("\n");
		IndentLevel indent = new IndentLevel("\t");
		sj.add(indent.get() + SimpleTimeFormat.convert(series.sum) + " - " + series.name);
		StackNode root = new StackNode(series.name, series.name + " took " + SimpleTimeFormat.convert(series.sum),
				baseWidth, baseHeight, 1.0f, 1.0f);
		collectData(series, root, baseHeight, baseWidth, sj, indent);
		return new VisualisationSnapshot(label, new VisualisationGraph(root), sj.toString());
	}

	private static void collectData(DataSeries parentSeries, StackNode parentNode, float baseHeight, float baseWidth,
			StringJoiner sj, IndentLevel indent) {

		if (parentSeries.hasNoChildren())
			return;

		indent.increase();

		Long siblingSum = parentSeries.getChildren().map(s -> s.sum).max(Long::compare).get();

		List<DataSeries> siblings = parentSeries.getChildren().collect(Collectors.toList());
		Collections.sort(siblings, (s1, s2) -> Long.compare(s2.sum, s1.sum));

		long total = 0L;
		for (DataSeries series : siblings) {
			sj.add(indent.get() + SimpleTimeFormat.convert(series.sum) + " - " + series.name);
			float parentScale = (float) series.sum / parentSeries.sum;
			float siblingScale = (float) series.sum / siblingSum;
			StackNode node = new StackNode(series.name,
					series.name + " took " + SimpleTimeFormat.convert(series.sum),
					parentNode.width * parentScale, baseHeight, parentScale, siblingScale);

			parentNode.addChild(node);
			collectData(series, node, baseHeight, baseWidth, sj, indent);

			total += series.sum;
		}

		long missing = parentSeries.sum - total;
		sj.add(indent.get() + "NOT INCLUDED: " + SimpleTimeFormat.convert(missing));

		indent.decrease();
	}
}
