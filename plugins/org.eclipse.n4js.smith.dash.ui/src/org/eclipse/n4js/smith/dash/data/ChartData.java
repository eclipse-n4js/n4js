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

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 *
 */
public class ChartData {
	public final ObservableList<PieChart.Data> pieChartData;
	public final int notShownDataCount;
	public final long notShownDataAccumulate;

	public ChartData(ObservableList<PieChart.Data> pieChartData, int notShownDataCount, long notShownDataAccumulate) {
		this.pieChartData = pieChartData;
		this.notShownDataCount = notShownDataCount;
		this.notShownDataAccumulate = notShownDataAccumulate;
	}

}
