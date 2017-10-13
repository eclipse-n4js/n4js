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

import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.graphics.GC;

/**
 * Abstract node used to draw visualizations
 */
public abstract class VisualisationNode {

	/** paint this node */
	public abstract void paint(GC gc);

	/** returns node title */
	public abstract String getTitle();

	/** returns description of this node */
	public abstract String getDescription();

	/** returns children of this node */
	public abstract List<VisualisationNode> getChildren();

	/** sorts children of this node according to the provided comparator */
	public abstract void sortChildren(Comparator<VisualisationNode> cmp);

	/** get node x coordinate */
	public abstract float getX();

	/** set node x coordinate */
	public abstract void setX(float x);

	/** get node y coordinate */
	public abstract float getY();

	/** set node y coordinate */
	public abstract void setY(float y);

	/** get node width */
	public abstract float getWidth();

	/** get node height */
	public abstract float getHeight();

	/** check if a given coordinates are within {@link #getBounds() bounds} of this node. */
	public boolean contains(float x, float y) {
		return getBounds().contains(x, y);
	}

	/** get {@link Rectangle} for this node. */
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Translate receiving node in its containing graph.
	 */
	public void move(float dx, float dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}

	/** resets node position, usually used during {@link VisualisationGraph#layout()} */
	protected void trim() {
		setX(0);
		setY(0);
	}

}