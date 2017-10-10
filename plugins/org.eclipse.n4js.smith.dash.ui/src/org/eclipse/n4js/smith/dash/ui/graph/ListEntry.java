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

class ListEntry {
	public final String label;
	public final VisualisationGraph graph;
	public final String text;

	public ListEntry(String label, VisualisationGraph graph, String text) {
		this.label = label;
		this.graph = graph;
		this.text = text;
	}
}