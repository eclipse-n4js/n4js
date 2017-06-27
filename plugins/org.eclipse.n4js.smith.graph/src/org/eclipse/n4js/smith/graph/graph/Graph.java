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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.swt.graphics.GC;

/**
 * A Graph is mainly a container for {@link Node}s and {@link Edge}s. In addition, this class is responsible for
 * creating the nodes and edges based on a given {@link GraphProvider} (see {@link #build(GraphProvider, Object)}) and
 * for layout, see method {@link #layout(GC)}.
 */
@SuppressWarnings("javadoc")
public class Graph {

	protected final List<Node> nodes = new ArrayList<>();
	protected final List<Edge> edges = new ArrayList<>();

	/**
	 * Implementations of this interface define how to create a graph, i.e. nodes and edges, from a given input object.
	 * Call sequence:
	 * <ol>
	 * <li>{@link #getElements(Object)}
	 * <li>{@link #getNode(Object)}
	 * <li>{@link #getConnectedEdges(Node, List)}
	 * </ol>
	 */
	public interface GraphProvider {
		/**
		 * Return the elements for the given input. Each element will later be passed to {@link #getNode(Object)} to
		 * create a corresponding node.
		 */
		public List<Object> getElements(Object input);

		/**
		 * Create and return a node for the given element.
		 */
		public Node getNode(Object element);

		/**
		 * Create and return all edges for the given node.
		 */
		public List<Edge> getConnectedEdges(Node node, List<Node> allNodes);
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void clear() {
		nodes.clear();
		edges.clear();
	}

	public void setGraph(List<Node> nodes, List<Edge> edges) {
		clear();
		this.nodes.addAll(nodes);
		this.edges.addAll(edges);
	}

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

	/**
	 * Returns a stream of 'node' and all nodes of the (sub-)tree below 'node'.
	 */
	public Stream<Node> getTree(Node node) {
		return Stream.concat(Stream.of(node), node.children.stream().flatMap(this::getTree));
	}

	/**
	 * Translates 'node' and all nodes of the (sub-)tree below 'node' by the given deltas.
	 */
	public void moveTree(Node node, float dx, float dy) {
		node.move(dx, dy);
		node.children.forEach(ch -> moveTree(ch, dx, dy));
	}

	/**
	 * Rebuilds the entire graph from 'input', using the given 'provider'.
	 */
	public void build(GraphProvider provider, Object input) {
		// build nodes and edges (using the provider)
		final List<Object> elements = new ArrayList<>();
		final List<Node> newNodes = new ArrayList<>();
		final List<Edge> newEdges = new ArrayList<>();
		if (input != null && provider != null) {
			elements.addAll(provider.getElements(input));
			for (Object currE : elements)
				newNodes.add(provider.getNode(currE));
			for (Node currNode : newNodes)
				newEdges.addAll(provider.getConnectedEdges(currNode, newNodes));
		}
		// set derived properties
		newEdges.stream().forEach(e -> {
			if (e.isCrossLink()) {
				// set hasCrossLinks flags
				final boolean internal = e.isInternal();
				final boolean external = e.isExternal();
				e.getStartNodes().forEach(n -> {
					n.hasOutgoingCrossLinksInternal |= internal;
					n.hasOutgoingCrossLinksExternal |= external;
				});
				e.getEndNodes().forEach(n -> {
					n.hasIncomingCrossLinksInternal |= internal;
				});
			} else {
				// set children property
				// (simply add all end nodes to the first(!) start node's children property)
				final Node parent = e.getFirstStartNode();
				if (parent != null) {
					parent.children.addAll(e.getEndNodes());
					e.getEndNodes().forEach(en -> en.parent = parent);
				}
			}
		});
		// store newly created nodes, edges
		setGraph(newNodes, newEdges);
	}

	/**
	 * Layout the nodes and edges in the receiving graph.
	 * <p>
	 * Current implementation is using a simple tree layout algorithm.
	 */
	public void layout(GC gc) {
		doTreeLayout(gc);
	}

	private final float GAP_X = 10;
	private final float GAP_Y = 60;

	private void doTreeLayout(GC gc) {
		// trim all nodes
		nodes.forEach(n -> n.trim(gc));
		// actual layout
		final Stream<Node> roots = nodes.stream().filter(n -> n.parent == null);
		doLayoutSubtrees(roots);
	}

	/**
	 * Lay out given subtrees side by side.
	 */
	private void doLayoutSubtrees(@SuppressWarnings("hiding") Stream<Node> nodes) {
		float px = 0;
		final Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			final Node n = i.next();
			doLayoutSubtree(n);
			final Rectangle b = GraphUtils.getBounds(getTree(n));
			moveTree(n, px - b.x, 0);
			px += b.width + GAP_X;
		}
	}

	/**
	 * Layout the subtree below 'node'.
	 */
	private void doLayoutSubtree(Node node) {
		// layout my children next to each other
		doLayoutSubtrees(node.children.stream());
		// layout myself above my children
		if (!node.children.isEmpty()) {
			// position myself in the center of my immediate children
			final Node first = node.children.get(0);
			final Node last = node.children.get(node.children.size() - 1);
			final float xLeft = first.getX() + first.getWidth() / 2;
			final float xRight = last.getX() + last.getWidth() / 2;
			node.x = xLeft + (xRight - xLeft) / 2 - node.getWidth() / 2;
		} else {
			node.x = 0;
		}
		node.y = -(40 + GAP_Y);
		// adjust so that I am at Y coordinate 0
		moveTree(node, 0, -node.y);
	}
}
