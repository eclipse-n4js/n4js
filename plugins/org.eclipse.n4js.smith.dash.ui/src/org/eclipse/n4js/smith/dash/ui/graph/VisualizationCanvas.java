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

import org.eclipse.swt.graphics.Rectangle;

/**
 * SWT widget to draw a {@link Graph}.
 */
@SuppressWarnings("javadoc")
public interface VisualizationCanvas {

	public void setGraph(VisualisationGraph graph);

	public VisualisationGraph getGraph();

	/**
	 *
	 */
	void clear();

	public void redraw();

	public Rectangle getClientArea();
}
