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
package org.eclipse.n4js.smith.dash.ui.views;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.smith.dash.data.BuildQueueData;
import org.eclipse.n4js.smith.dash.data.SomeDataPoint;
import org.eclipse.n4js.smith.dash.ui.graph.Edge;
import org.eclipse.n4js.smith.dash.ui.graph.Graph.GraphProvider;
import org.eclipse.n4js.smith.dash.ui.graph.Node;

/**
 * Creates nodes and edges from an EMF {@link ResourceSet} as input.
 */
public class BuildQueueGraphProvider implements GraphProvider {
	private static final String KEY = "BuildQueue";

	@Override
	public List<Object> getElements(Object input) {
		final List<Object> result = new LinkedList<>();
		result.addAll(BuildQueueData.gRawData2(KEY));
		return result;
	}

	@Override
	public Node getNode(Object element) {
		if (element instanceof SomeDataPoint) {
			SomeDataPoint datapoint = (SomeDataPoint) element;
			return new Node(datapoint, "" + datapoint.elapsedNanos, datapoint.name);
		}

		final String type = "";
		final String name = "";
		final String desc = "";
		final String title = type + (name != null ? " " + name : "");
		return new Node(element, title, desc);
	}

	@Override
	public List<Edge> getConnectedEdges(Node node, final List<Node> allNodes) {
		final List<Edge> result = new ArrayList<>();
		final Object element = node.getElement();
		return result;
	}

}
