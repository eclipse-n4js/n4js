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

import org.eclipse.swt.graphics.GC;

/**
 * Provides methods to access the data of different graph types.
 */
abstract public class Graph<P extends GraphProvider<?, ?>> {
	/** All nodes */
	protected final List<Node> nodes = new ArrayList<>();
	/** All edges */
	protected final List<Edge> edges = new ArrayList<>();

	/**
	 * Returns all nodes of the graph.
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Returns all edges of the graph.
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * Clears the graph
	 */
	public void clear() {
		nodes.clear();
		edges.clear();
	}

	/**
	 * Returns a node at the specified location. Otherwise null.
	 */
	public Node getNodeAt(float x, float y) {
		for (Node n : nodes)
			if (n.contains(x, y))
				return n;
		return null;
	}

	/**
	 * Computes this graph's bounds from its current nodes (i.e. their current positions and sizes). No layout will be
	 * performed by this method!
	 */
	public Rectangle getBounds() {
		return GraphUtils.getBounds(nodes.stream());
	}

	void setGraph(List<Node> nodes, List<Edge> edges) {
		clear();
		this.nodes.addAll(nodes);
		this.edges.addAll(edges);
	}

	/**
	 * Rebuilds the entire graph from 'input', using the given 'provider'.
	 */
	abstract public void build(P provider, Object input);

	/**
	 * Layout the nodes and edges in the receiving graph.
	 * <p>
	 * Current implementation is using a simple tree layout algorithm.
	 */
	abstract public void layout(GC gc);

}
