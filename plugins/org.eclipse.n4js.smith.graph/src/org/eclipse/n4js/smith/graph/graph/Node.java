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
package org.eclipse.n4js.smith.graph.graph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * Node in a graph.
 */
public class Node {
	private static final float BORDER = 4;
	private static final float DEFAULT_WIDTH = 200;
	private static final float DEFAULT_HEIGHT = 30;
	private static final float SIZE_CROSS_LINKS_MARKER = 6;

	/** User data */
	protected Object element;
	/** Title displayed as label in the graph */
	protected String title;
	/** Description displayed as hover text in the graph */
	protected String description;

	/** X-coordinate of the rectangle */
	protected float x;
	/** Y-coordinate of the rectangle */
	protected float y;
	/** Width of the rectangle */
	protected float width;
	/** Height of the rectangle */
	protected float height;

	/** this flag is set by Graph#build() */
	protected boolean hasOutgoingCrossLinksInternal = false;
	/** this flag is set by Graph#build() */
	protected boolean hasOutgoingCrossLinksExternal = false;
	/** this flag is set by Graph#build() */
	protected boolean hasIncomingCrossLinksInternal = false;
	/** this flag is set by Graph#build() */
	protected Node parent;
	/** this flag is set by Graph#build() */
	protected final List<Node> children = new ArrayList<>();

	/** Constructor, using default rectangle size */
	public Node(Object element, String title, String description) {
		this(element, title, description, 10, 10, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/** Constructor */
	public Node(Object element, String title, String description, float x, float y, float width, float height) {
		this.element = element;
		this.title = title;
		this.description = description;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/** @returns the element of this node */
	public Object getElement() {
		return element;
	}

	/** @returns the node's title */
	public String getTitle() {
		return title;
	}

	/** Sets the node's title */
	public void setTitle(String title) {
		this.title = title;
	}

	/** @returns the description text */
	public String getDescription() {
		return description;
	}

	/** @returns the description text */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @returns the x-coordinate of the node's rectangle */
	public float getX() {
		return x;
	}

	/** Sets the x-coordinate of the node's rectangle */
	public void setX(float x) {
		this.x = x;
	}

	/** @returns the y-coordinate of the node's rectangle */
	public float getY() {
		return y;
	}

	/** Sets the y-coordinate of the node's rectangle */
	public void setY(float y) {
		this.y = y;
	}

	/** @returns the width of the node's rectangle */
	public float getWidth() {
		return width;
	}

	/** Sets the width of the node's rectangle */
	public void setWidth(float width) {
		this.width = width;
	}

	/** @returns the height of the node's rectangle */
	public float getHeight() {
		return height;
	}

	/** Sets the height of the node's rectangle */
	public void setHeight(float height) {
		this.height = height;
	}

	/** @returns true iff the given point lies within the node's bounds */
	public boolean contains(@SuppressWarnings("hiding") float x, @SuppressWarnings("hiding") float y) {
		return getBounds().contains(x, y);
	}

	/** @returns the bounds of the node's rectangle */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/** @returns the center of the node's rectangle */
	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}

	/**
	 * Returns the end point for the given edge. The reference point defines the direction from where the connection is
	 * coming.
	 */
	public Point getAnchor(@SuppressWarnings("unused") Edge edge, Point referencePoint) {
		return getBounds().getIntersectionLocation(referencePoint);
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
		if (title != null) {
			final org.eclipse.swt.graphics.Point size = gc.stringExtent(title);
			this.width = size.x + BORDER * 2;
			this.height = size.y + BORDER * 2;
		} else {
			this.width = DEFAULT_WIDTH;
			this.height = DEFAULT_HEIGHT;
		}
	}

	/**
	 * Paints the Node
	 */
	public void paint(GC gc) {
		gc.setBackground(GraphUtils.getColor(200, 200, 255));
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));

		gc.fillRoundRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height), 5, 5);
		GraphUtils.drawString(gc, title, x, y, width, height, 0);

		if (hasOutgoingCrossLinksInternal || hasOutgoingCrossLinksExternal) {
			Color colorRed = gc.getDevice().getSystemColor(SWT.COLOR_RED);
			gc.setBackground(colorRed);
			gc.setForeground(colorRed);

			int ovalX = Math.round(x + width - SIZE_CROSS_LINKS_MARKER - 2);
			int ovalY = Math.round(y + 2);
			int ovalSize = Math.round(SIZE_CROSS_LINKS_MARKER);
			if (hasOutgoingCrossLinksInternal) {
				gc.fillOval(ovalX, ovalY, ovalSize, ovalSize);
			} else {
				gc.drawOval(ovalX, ovalY, ovalSize, ovalSize);
			}
		}

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	@Override
	public String toString() {
		String toString = (title != null) ? title : super.toString();
		return toString;
	}
}
