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
package org.eclipse.n4js.smith.dash.ui.graph;

import java.util.StringJoiner;

import org.eclipse.n4js.smith.dash.data.DataSeries;
import org.eclipse.n4js.smith.dash.data.SimpleTimeFormat;
import org.eclipse.n4js.smith.dash.data.SomeDataAccess;
import org.eclipse.n4js.utils.IndentLevel;

/**
 * Factory for graphs representing collected performance data.
 */
public class StackGraphFactory {

	/** Uses provided key to obtain performance data and builds graph representing it. */
	public static ListEntry buildGraph(String key, float baseHeight, float baseWidth, String label) {
		DataSeries dataSeries = SomeDataAccess.getDataSeries(key);
		if (dataSeries.hasNoData())
			return new ListEntry(label, new StackGraph(), label + " (no data)");

		return createStackGraph(dataSeries, baseHeight, baseWidth, label);
	}

	private static ListEntry createStackGraph(DataSeries series, float baseHeight, float baseWidth, String label) {
		StringJoiner sj = new StringJoiner("\n");
		IndentLevel indent = new IndentLevel("\t");
		sj.add(indent.get() + SimpleTimeFormat.convert(series.getSum()) + " - " + series.name);
		StackNode root = new StackNode(series.name, series.name + " took " + SimpleTimeFormat.convert(series.getSum()),
				baseWidth, baseHeight, 1.0f, 1.0f);
		collectData(series, root, baseHeight, baseWidth, sj, indent);
		return new ListEntry(label, new StackGraph(root), sj.toString());
	}

	private static void collectData(DataSeries parentSeries, StackNode parentNode, float baseHeight, float baseWidth,
			StringJoiner sj, IndentLevel indent) {

		if (parentSeries.hasNoChildren())
			return;

		indent.increase();

		Long siblingSum = parentSeries.getChildren().map(s -> s.getSum()).max(Long::compare).get();
		parentSeries.getChildren().forEach(series -> {
			sj.add(indent.get() + SimpleTimeFormat.convert(series.getSum()) + " - " + series.name);
			float parentScale = (float) series.getSum() / parentSeries.getSum();
			float siblingScale = (float) series.getSum() / siblingSum;
			StackNode node = new StackNode(series.name,
					series.name + " took " + SimpleTimeFormat.convert(series.getSum()),
					parentNode.width * parentScale, baseHeight, parentScale, siblingScale);

			parentNode.addChild(node);
			collectData(series, node, baseHeight, baseWidth, sj, indent);
		});

		indent.decrease();
	}
}
