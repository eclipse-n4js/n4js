/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.dash.ui.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

/**
 * StackNode in a graph. Shameless copy of Node.
 */
@SuppressWarnings("javadoc")
public class ChartNode extends VisualisationNode {

	protected String title;
	protected String description;

	protected float x = 0;
	protected float y = 0;
	protected float width = 0;
	protected float height = 0;

	protected ChartNode parent;
	protected final List<ChartNode> children = new ArrayList<>();

	private final List<Long> data;
	private final float totalScale;

	final Float dataMin;
	final Float dataMax;
	final float chartHeight;
	final float chartWidth;

	final int dataStart;
	final int dataEnd;
	final int dataSize;

	public ChartNode(String title, String description, List<Long> data, float totalScale,
			final Float dataMin,
			final Float dataMax,
			final float chartHeight,
			final float chartWidth,

			final int dataStart,
			final int dataEnd,
			final int dataSize) {
		this.title = title;
		this.description = description;
		this.data = data;
		this.totalScale = totalScale;
		this.dataMin = dataMin;
		this.dataMax = dataMax;
		this.chartHeight = chartHeight;
		this.chartWidth = chartWidth;

		this.dataStart = dataStart;
		this.dataEnd = dataEnd;
		this.dataSize = dataSize;
	}

	public void setParent(ChartNode parent) {
		this.parent = parent;
	}

	public void addChild(ChartNode child) {
		this.children.add(child);
		child.setParent(this);
	}

	/** return how deep in the stack this node is */
	int getDepth() {
		int d = 0;
		ChartNode node = this;
		while (node.parent != null) {
			d += 1;
			node = node.parent;
		}
		return d;
	}

	/**
	 * Paint the ... guess what!
	 */
	@Override
	public void paint(GC gc) {
		final Color oldBackground = gc.getBackground();
		final Color oldForeground = gc.getForeground();
		try {
			long value = -1;
			int coordinatesSize = 2 * ((dataEnd - dataStart) + 2);
			int[] coordinates = new int[coordinatesSize];
			coordinates[0] = (int) (linearScale(dataStart, 0, dataSize) * chartWidth);
			coordinates[1] = 0;
			for (int j = dataStart; j < dataEnd; j++) {
				int jj = j - dataStart;
				int s = (jj + 1) * 2;
				value = data.get(jj);
				int pointX = (int) (linearScale(j, 0, dataSize) * chartWidth);
				coordinates[s] = pointX;
				int pointY = (int) (logScale(value, dataMin, dataMax) * chartHeight);
				coordinates[s + 1] = pointY;
			}
			coordinates[coordinatesSize - 2] = coordinates[coordinatesSize - 4];
			coordinates[coordinatesSize - 1] = 0;

			gc.setBackground(getColor(totalScale));
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.fillPolygon(coordinates);
		} finally {
			gc.setBackground(oldBackground);
			gc.setForeground(oldForeground);
		}
	}

	/** Data with higher values gives {@code red} values. */
	private static Color getColor(float volumePercentage) {
		float factor = volumePercentage;
		RGB color = new RGB(255, ColorUtils.clamp(255 * factor), ColorUtils.clamp(255 * factor));
		return ColorUtils.getColor(color);
	}

	public static float logScale(final float valueIn, final float minData, final float maxData) {
		float logScale = (float) ((Math.log10(valueIn) - Math.log10(minData))
				/ (Math.log10(maxData) - Math.log10(minData)));
		return logScale;
	}

	public static float linearScale(final float valueIn, final float minData, final float maxData) {
		float linearScale = (valueIn - minData) / (maxData - minData);
		return linearScale;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public List<VisualisationNode> getChildren() {
		return new LinkedList<>(this.children);
	}

	@Override
	public void sortChildren(Comparator<VisualisationNode> cmp) {
		this.children.sort(cmp);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String getTitle() {
		return this.title;
	}
}
