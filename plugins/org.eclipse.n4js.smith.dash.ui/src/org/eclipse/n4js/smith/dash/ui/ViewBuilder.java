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
package org.eclipse.n4js.smith.dash.ui;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.n4js.smith.dash.data.SomeDataAccess;
import org.eclipse.n4js.smith.dash.data.SomeDataPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.IBarSeries.BarWidthStyle;
import org.swtchart.ISeries.SeriesType;

/**
 *
 */
public class ViewBuilder {
	private static final String NL = "\n";

	public static void createView(final Composite parent, String key) {

		GridLayout gl = new GridLayout(2, true);
		parent.setLayout(gl);

		Chart chart = new Chart(parent, SWT.NONE);
		chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		chart.getLegend().setVisible(false);

		Text text = new Text(parent, SWT.LEFT | SWT.MULTI);
		text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		// set titles
		chart.getTitle().setText(key);
		chart.getAxisSet().getXAxis(0).getTitle().setText("Resource");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Elapsed time [ms]");

		IBarSeries barSeries0 = (IBarSeries) chart.getSeriesSet()
				.createSeries(SeriesType.BAR, "bar series 0");
		double[] y0 = { 0 };
		barSeries0.setYSeries(y0);
		double[] x0 = { 0 };
		barSeries0.setXSeries(x0);

		AtomicInteger ai = new AtomicInteger(0);
		List<SomeDataPoint> data = SomeDataAccess.gRawData2(key);
		final int size = data.size();
		final boolean large = data.size() > 100;
		final int mid = (int) Math.floor(data.size() * 0.25);
		final StringJoiner sj = new StringJoiner(NL);
		for (int j = 0; j < size; j++) {
			SomeDataPoint d = data.get(j);
			IBarSeries barSeries = (IBarSeries) chart.getSeriesSet()
					.createSeries(SeriesType.BAR, "bar series" + j);
			double[] y = { d.elapsedNanos };
			barSeries.setYSeries(y);
			double[] x = { j + 1 };
			barSeries.setXSeries(x);
			if (!large) {
				barSeries.setBarWidthStyle(BarWidthStyle.FIXED);
			}

			if (j < mid) {
				if (!large) {
					barSeries.setBarWidth(100);
				}
				barSeries.setBarColor(new Color(Display.getDefault(), 255, 0, 0));
			}

			sj.add(d.elapsedNanos + "ms " + d.name);

		}

		// adjust the axis range
		chart.getAxisSet().adjustRange();

		text.setText(sj.toString());
	}
}
