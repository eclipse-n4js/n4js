/**
y * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.dash.ui.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;

import org.eclipse.swt.graphics.GC;

/**
 * Graph customized for stack like view of the graph.
 */
public class StackGraph extends VisualisationGraph {
	private final List<StackNode> roots = new LinkedList<>();
	private final Set<StackNode> nodes = Collections.newSetFromMap(
			new WeakHashMap<StackNode, Boolean>());

	public StackGraph(StackNode... rootNodes) {
		for (int i = 0; i < rootNodes.length; i++) {
			StackNode stackNode = rootNodes[i];
			// TODO too many iterations
			sort(stackNode);
			roots.add(stackNode);
			getTree(stackNode).forEach(nodes::add);
		}
	}

	private static void sort(StackNode node) {
		node.children.sort(StackGraph::desc);
		node.children.forEach(StackGraph::sort);
	}

	private static int desc(StackNode node1, StackNode node2) {
		return Float.compare(node2.width, node1.width);
	}

	public Rectangle getBounds() {
		return StackUtils.getStackBounds(nodes.stream());
	}

	/** Layouts whole graph by stacking children next to each other under the parent. */
	public void layout(GC gc) {
		doStackLayout(gc);
	}

	private void doStackLayout(GC gc) {
		// trim all nodes
		nodes.forEach(n -> n.trim(gc));
		// actual layout
		layoutLayer(null, roots);
	}

	/**
	 * Returns a stream of 'node' and all nodes of the (sub-)tree below 'node'.
	 */
	public Stream<StackNode> getTree(StackNode node) {
		return Stream.concat(Stream.of(node), node.children.stream().flatMap(this::getTree));
	}

	private void layoutLayer(StackNode parent, List<StackNode> siblings) {
		StackNode prevY = null;
		for (int i = 0; i < siblings.size(); i++) {
			StackNode child = siblings.get(i);
			positionNode(child, prevY, parent);
			layoutLayer(child, child.children);
			prevY = child;
		}
	}

	/** Positions node in relation to its neighbors. */
	private void positionNode(StackNode node, StackNode prevX, StackNode prevY) {
		float px = 0;
		float py = 0;
		if (prevX != null) {
			// position according to previous in this layer
			px = prevX.x + prevX.width;
			py = prevX.y;
		} else if (prevY != null) {
			// position according to previous layer
			px = prevY.x;
			py = prevY.y + prevY.height;
		}
		// System.out.println("moving " + node.description + " by " + px + ", " + py);
		node.move(px, py);
	}

	/**
	 * Translates 'node' and all nodes of the (sub-)tree below 'node' by the given deltas.
	 */
	public void moveTree(StackNode node, float dx, float dy) {
		node.move(dx, dy);
		node.children.forEach(ch -> moveTree(ch, dx, dy));
	}

	/** returns node form given position, or null */
	public StackNode getNodeAt(float x, float y) {
		for (StackNode n : nodes)
			if (n.contains(x, y))
				return n;
		return null;
	}

	/** returns set of all nodes in this graph */
	public Set<StackNode> getNodes() {
		return new HashSet<>(nodes);
	}

}
