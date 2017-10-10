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
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

/**
 * StackNode in a graph. Shameless copy of Node.
 */
@SuppressWarnings("javadoc")
public class ChartNode {

	private static final float BORDER = 4;
	/** Base width of the nodes for this canvas. */
	public static final float BASE_WIDTH = 1000.0f;
	/** Base height of the nodes for this canvas. */
	public static final float BASE_HEIGHT = 10.0f;

	protected String title;
	protected String description;

	protected float x = 0;
	protected float y = 0;
	protected float width = 0;
	protected float height = 0;
	// private final float originalWidth;
	// private final float originalHeight;
	// private final float parentScale;
	// private final float siblingScale;

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

	public boolean contains(@SuppressWarnings("hiding") float x, @SuppressWarnings("hiding") float y) {
		return getBounds().contains(x, y);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}

	/**
	 * Translate receiving node in its containing graph.
	 */
	public void move(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Change size of receiving Node to its preferred size.
	 */
	public void trim(GC gc) {
		// this.width = originalWidth;
		// this.height = originalHeight;

		// if (title != null) {
		// final org.eclipse.swt.graphics.Point size = gc.stringExtent(title);
		// this.width = size.x + BORDER * 2;
		// this.height = size.y + BORDER * 2;
		// } else {
		// this.width = originalWidth;
		// this.height = originalHeight;
		// }
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
	public void paint(GC gc) {
		final Color olgBackground = gc.getBackground();
		final Color oldForeground = gc.getForeground();
		try {
			Long d = null;
			int data20Size = 2 * ((dataEnd - dataStart) + 2);
			int[] data20 = new int[data20Size];
			data20[0] = (int) (linearScale(dataStart, 0, dataSize) * chartWidth);
			data20[1] = 0;
			for (int j = dataStart; j < dataEnd; j++) {
				int jj = j - dataStart;
				int s = (jj + 1) * 2;
				d = data.get(jj);
				int pointX = (int) (linearScale(j, 0, dataSize) * chartWidth);
				data20[s] = pointX;
				int pointY = (int) (logScale(d.floatValue(), dataMin, dataMax) * chartHeight);
				data20[s + 1] = pointY;
			}
			data20[data20Size - 2] = data20[data20Size - 4];
			data20[data20Size - 1] = 0;

			gc.setBackground(ChartUtils.getColor(totalScale));
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.fillPolygon(data20);
			// System.out.println("draw " + title + " " + Arrays.toString(data20));
		} finally {
			gc.setBackground(olgBackground);
			gc.setForeground(oldForeground);
		}
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
}
