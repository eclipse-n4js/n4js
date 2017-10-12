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
 *
 */
public abstract class VisualisationNode {

	public abstract void paint(GC gc);

	// public abstract void trim(GC gc);

	public abstract String getDescription();

	public abstract List<VisualisationNode> getChildren();

	public abstract void sortChildren(Comparator<VisualisationNode> cmp);

	public abstract float getX();

	public abstract void setX(float x);

	public abstract float getY();

	public abstract void setY(float y);

	public abstract float getWidth();

	public abstract float getHeight();

	public boolean contains(float x, float y) {
		return getBounds().contains(x, y);
	}

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

}