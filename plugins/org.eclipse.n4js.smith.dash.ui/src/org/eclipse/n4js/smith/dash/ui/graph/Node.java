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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * Node in a graph.
 */
@SuppressWarnings("javadoc")
public class Node {

	private static final float BORDER = 4;
	private static final float DEFAULT_WIDTH = 200;
	private static final float DEFAULT_HEIGHT = 30;
	private static final float SIZE_CROSS_LINKS_MARKER = 6;

	protected Object element;

	protected String title;
	protected String description;

	protected float x;
	protected float y;
	protected float width;
	protected float height;

	// this flag is set by Graph#build()
	protected boolean hasOutgoingCrossLinksInternal = false;
	// this flag is set by Graph#build()
	protected boolean hasOutgoingCrossLinksExternal = false;
	// this flag is set by Graph#build()
	protected boolean hasIncomingCrossLinksInternal = false;
	// this flag is set by Graph#build()
	protected Node parent;
	// this flag is set by Graph#build()
	protected final List<Node> children = new ArrayList<>();

	public Node(Object element, String title, String description) {
		this(element, title, description, 10, 10, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Node(Object element, String title, String description, float x, float y, float width, float height) {
		this.element = element;
		this.title = title;
		this.description = description;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Object getElement() {
		return element;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
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
		}
		else {
			this.width = DEFAULT_WIDTH;
			this.height = DEFAULT_HEIGHT;
		}
	}

	/**
	 * Paint the ... guess what!
	 */
	public void paint(GC gc) {
		gc.setBackground(GraphUtils.getColor(200, 200, 255));
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));

		gc.fillRoundRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height), 5, 5);
		// gc.drawRoundRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height), 5, 5);
		GraphUtils.drawString(gc, title, x, y, width, height, 0);

		if (hasOutgoingCrossLinksInternal || hasOutgoingCrossLinksExternal) {
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
			if (hasOutgoingCrossLinksInternal)
				gc.fillOval(
						Math.round(x + width - SIZE_CROSS_LINKS_MARKER - 2), Math.round(y + 2),
						Math.round(SIZE_CROSS_LINKS_MARKER), Math.round(SIZE_CROSS_LINKS_MARKER));
			else
				gc.drawOval(
						Math.round(x + width - SIZE_CROSS_LINKS_MARKER - 2), Math.round(y + 2),
						Math.round(SIZE_CROSS_LINKS_MARKER), Math.round(SIZE_CROSS_LINKS_MARKER));
		}

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}
}
