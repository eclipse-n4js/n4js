/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.draw2d.ChopboxAnchor
 *	in bundle org.eclipse.draw2d
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2000, 2010 IBM Corporation and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.ui.graph;

/**
 * A rectangle.
 */
@SuppressWarnings("javadoc")
public class Rectangle {

	public static final Rectangle EMPTY = new Rectangle(0, 0, 0, 0);

	public final float x;
	public final float y;
	public final float width;
	public final float height;

	public Rectangle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Point getCenter() {
		return new Point(x + width / 2, y + height / 2);
	}

	public boolean isEmpty() {
		return width <= 0 || height <= 0;
	}

	public boolean contains(@SuppressWarnings("hiding") float x, @SuppressWarnings("hiding") float y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
	}

	/**
	 * Returns the Point where a line from the center of the receiving Rectangle to the Point <i>reference</i>
	 * intersects the Rectangle.
	 *
	 * Copied from org.eclipse.draw2d.ChopboxAnchor#getLocation().
	 */
	public Point getIntersectionLocation(Point reference) {
		final Rectangle r = new Rectangle(x, y, width, height);
		// r.translate(-1, -1);
		// r.resize(1, 1);

		// getOwner().translateToAbsolute(r);
		float centerX = r.x + 0.5f * r.width;
		float centerY = r.y + 0.5f * r.height;

		if (r.isEmpty()
				|| (reference.x == (int) centerX && reference.y == (int) centerY))
			return new Point(centerX, centerY); // This avoids divide-by-zero

		float dx = reference.x - centerX;
		float dy = reference.y - centerY;

		// r.width, r.height, dx, and dy are guaranteed to be non-zero.
		float scale = 0.5f / Math.max(Math.abs(dx) / r.width, Math.abs(dy)
				/ r.height);

		dx *= scale;
		dy *= scale;
		centerX += dx;
		centerY += dy;

		return new Point(centerX, centerY);
	}
}
