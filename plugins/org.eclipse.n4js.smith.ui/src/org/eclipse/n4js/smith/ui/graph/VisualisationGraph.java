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
package org.eclipse.n4js.smith.ui.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * Graph of nodes to be visualized. Layouts nodes in layers based on their child - parent relation.
 */
public class VisualisationGraph {

	private final List<VisualisationNode> roots = new LinkedList<>();
	private final Set<VisualisationNode> nodes = Collections.newSetFromMap(
			new WeakHashMap<VisualisationNode, Boolean>());

	/** Creates graph from the provided nodes. */
	public VisualisationGraph(VisualisationNode... rootNodes) {
		for (int i = 0; i < rootNodes.length; i++) {
			VisualisationNode stackNode = rootNodes[i];
			// note that char nodes are sorted in the factory,
			// also the string representation created in stack
			// factory will not might not correspond to sorted
			// stack graph visual representation
			stackNode.sortChildren(VisualisationGraph::desc);
			roots.add(stackNode);
			getTree(stackNode).forEach(nodes::add);
		}
	}

	/** returns bounds of this graph */
	public Rectangle getBounds() {
		final Iterator<VisualisationNode> i = nodes.iterator();
		if (i.hasNext()) {
			float xMin = Float.MAX_VALUE;
			float xMax = Float.MIN_VALUE;
			float yMin = Float.MAX_VALUE;
			float yMax = Float.MIN_VALUE;
			while (i.hasNext()) {
				final VisualisationNode n = i.next();
				xMin = Math.min(xMin, n.getX());
				yMin = Math.min(yMin, n.getY());
				xMax = Math.max(xMax, n.getX() + n.getWidth());
				yMax = Math.max(yMax, n.getY() + n.getHeight());
			}
			return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		} else
			return Rectangle.EMPTY;
	}

	private static int desc(VisualisationNode node1, VisualisationNode node2) {
		return Float.compare(node2.getWidth(), node1.getWidth());
	}

	/** Layouts whole graph by stacking children next to each other under the parent. */
	public void layout() {
		roots.stream().flatMap(this::getTree).forEach(n -> n.trim());
		doStackLayout();
	}

	private void doStackLayout() {
		layoutLayer(null, roots);
	}

	/**
	 * Returns a stream of 'node' and all nodes of the (sub-)tree below 'node'.
	 */
	public Stream<VisualisationNode> getTree(VisualisationNode node) {
		return Stream.concat(Stream.of(node), node.getChildren().stream().flatMap(this::getTree));
	}

	private void layoutLayer(VisualisationNode parent, List<VisualisationNode> siblings) {
		VisualisationNode prevY = null;
		for (int i = 0; i < siblings.size(); i++) {
			VisualisationNode child = siblings.get(i);
			positionNode(child, prevY, parent);
			layoutLayer(child, child.getChildren());
			prevY = child;
		}
	}

	/** Positions node in relation to its neighbors. */
	private void positionNode(VisualisationNode node, VisualisationNode prevX, VisualisationNode prevY) {
		float px = 0;
		float py = 0;
		if (prevX != null) {
			// position according to previous in this layer
			px = prevX.getX() + prevX.getWidth();
			py = prevX.getY();
		} else if (prevY != null) {
			// position according to previous layer
			px = prevY.getX();
			py = prevY.getY() + prevY.getHeight();
		}
		node.move(px, py);
	}

	/**
	 * Translates 'node' and all nodes of the (sub-)tree below 'node' by the given deltas.
	 */
	public void moveTree(VisualisationNode node, float dx, float dy) {
		node.move(dx, dy);
		node.getChildren().forEach(ch -> moveTree(ch, dx, dy));
	}

	/** returns node form given position, or null */
	public VisualisationNode getNodeAt(float x, float y) {
		for (VisualisationNode n : nodes)
			if (n.contains(x, y))
				return n;
		return null;
	}

	/** returns set of all nodes in this graph */
	public Set<VisualisationNode> getNodes() {
		return new HashSet<>(nodes);
	}

}
