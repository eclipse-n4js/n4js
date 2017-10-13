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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * StackNode in a graph. Shameless copy of Node.
 */
@SuppressWarnings("javadoc")
public class StackNode extends VisualisationNode {

	protected String title;
	protected String description;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	private final float parentScale;
	private final float siblingScale;

	protected StackNode parent;
	protected final List<StackNode> children = new ArrayList<>();

	public StackNode(String title, String description, float width, float height, float parentScale,
			float siblingScale) {
		this.title = title;
		this.description = description;
		this.width = width;
		this.height = height;
		this.parentScale = parentScale;
		this.siblingScale = siblingScale;
	}

	public void setParent(StackNode parent) {
		this.parent = parent;
	}

	public void addChild(StackNode child) {
		this.children.add(child);
		child.setParent(this);
	}

	/** return how deep in the stack this node is */
	int getDepth() {
		int d = 0;
		StackNode node = this;
		while (node.parent != null) {
			d += 1;
			node = node.parent;
		}
		return d;
	}

	/** return offset in the layer this node is */
	int getOffset() {
		// TODO handle root(s) better
		// instead of early return we should use all root nodes of the graph it the code below
		if (this.parent == null)
			return 0;

		StackNode node = this.parent;
		List<StackNode> layer = node == null ? Collections.emptyList() : node.children;
		int d = layer.indexOf(this);
		if (d < 0) {
			throw new RuntimeException("Cannot locate node in the graph.");
		}
		return d;
	}

	/**
	 * Paint the ... guess what!
	 */
	@Override
	public void paint(GC gc) {
		gc.setBackground(getColor(parentScale, siblingScale));
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));

		gc.fillRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height));

		// StackUtils.drawString(gc, title, x, y, width, height, 0);

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	/**
	 * Calculate color based on the position in the flame graph. From {@code red} to {@code yellow} - based on the scale
	 * in comparison to parent. Darkness based on comparison to the previous sibling in layer.
	 */
	private static Color getColor(float depthInStack, float depthInLayer) {
		float factor = 1.0f - depthInStack;
		RGB depthColor = new RGB(255, ColorUtils.clamp(255 * factor), 0);

		return ColorUtils.darken(depthColor, depthInLayer);
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
		this.children.forEach(c -> c.sortChildren(cmp));
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
