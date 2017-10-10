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
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * StackNode in a graph. Shameless copy of Node.
 */
@SuppressWarnings("javadoc")
public class StackNode {

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
	private final float originalWidth;
	private final float originalHeight;
	private final float parentScale;
	private final float siblingScale;

	protected StackNode parent;
	protected final List<StackNode> children = new ArrayList<>();

	public StackNode(String title, String description, float width, float height, float parentScale,
			float siblingScale) {
		this.title = title;
		this.description = description;
		this.originalWidth = width;
		this.originalHeight = height;
		this.width = originalWidth;
		this.height = originalHeight;
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
		this.width = originalWidth;
		this.height = originalHeight;
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
	public void paint(GC gc) {
		gc.setBackground(StackUtils.getColor(parentScale, siblingScale));
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));

		gc.fillRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height));

		// StackUtils.drawString(gc, title, x, y, width, height, 0);

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}
}
