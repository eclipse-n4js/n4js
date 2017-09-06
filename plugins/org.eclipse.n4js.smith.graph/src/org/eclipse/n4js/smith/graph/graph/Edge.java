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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * An edge represents an n:m association in the graph.
 * <p>
 * An edge may have an optional <b>label</b>.
 * <p>
 * It may have 1 or more <b>start</b> nodes, 0 or more <b>end</b> nodes, and 0 or more so-called <b>external end</b>
 * nodes. External end nodes are nodes outside the current graph that are represented by a label string only and will
 * only be shown to the user under certain circumstances.
 * <p>
 * An edge is always <b>directed</b> from the start nodes to the end nodes and thus arrow heads will be drawn at the end
 * of the lines pointing to the end nodes. To realize an undirected association, simply create an Edge having only start
 * nodes.
 * <p>
 * Edges can be flagged as <b>cross-link</b> edges. Such edges are to be ignored by layout algorithms, will be drawn in
 * a different style, and will be presented to the user only under certain circumstances (e.g. if a node is hovered /
 * selected or if drawing of all cross-links is turned on).
 */
public class Edge {

	/**
	 * @see Edge
	 */
	protected String label;

	/**
	 * @see Edge
	 */
	protected final List<Node> startNodes = new ArrayList<>();
	/**
	 * @see Edge
	 */
	protected final List<Node> endNodes = new ArrayList<>();
	/**
	 * @see Edge
	 */
	protected final List<String> endNodesExternal = new ArrayList<>();

	/**
	 * A cross-link is an edge that is to be ignored by layout algorithms.
	 */
	protected boolean crossLink;

	/**
	 * @see Edge
	 */
	public Edge(String label, boolean crossLink,
			Node startNode,
			Collection<? extends Node> endNodes, Collection<String> endNodesExternal) {
		this(label, crossLink, Collections.singletonList(startNode), endNodes, endNodesExternal);
	}

	/**
	 * @see Edge
	 */
	public Edge(String label, boolean crossLink,
			Collection<? extends Node> startNodes,
			Collection<? extends Node> endNodes, Collection<String> endNodesExternal) {
		this.label = label;
		this.crossLink = crossLink;
		this.startNodes.addAll(startNodes);
		this.endNodes.addAll(endNodes);
		this.endNodesExternal.addAll(endNodesExternal);
	}

	/**
	 * @see Edge
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns start <em>and</em> end nodes (but not external end nodes).
	 */
	public List<Node> getNodes() {
		return GraphUtils.concat(startNodes, endNodes);
	}

	/**
	 * @see Edge
	 */
	public Node getFirstStartNode() {
		return startNodes.isEmpty() ? null : startNodes.get(0);
	}

	/**
	 * @see Edge
	 */
	public List<Node> getStartNodes() {
		return startNodes;
	}

	/**
	 * @see Edge
	 */
	public List<Node> getEndNodes() {
		return endNodes;
	}

	/**
	 * @see Edge
	 */
	public List<String> getEndNodesExternal() {
		return endNodesExternal;
	}

	/**
	 * @see Edge
	 */
	public void setCrossLink(boolean value) {
		crossLink = value;
	}

	/**
	 * @see Edge
	 */
	public boolean isCrossLink() {
		return crossLink;
	}

	/**
	 * Returns true iff given node is among the receiving edge's start nodes.
	 */
	public boolean isStartNode(Node node) {
		return startNodes.contains(node);
	}

	/**
	 * Returns true iff given node is among the receiving edge's end nodes (not considering external end nodes).
	 */
	public boolean isEndNode(Node node) {
		return endNodes.contains(node);
	}

	/**
	 * Derived property. For an edge to be an internal link, it must be connected to at least one (internal) end node.
	 */
	public boolean isInternal() {
		return !endNodes.isEmpty();
	}

	/**
	 * Derived property. For an edge to be an external link, it must be connected to at least one external end node.
	 */
	public boolean isExternal() {
		return !endNodesExternal.isEmpty();
	}

	/**
	 * Paint edge to given GC.
	 */
	public void paint(GC gc) {
		if (startNodes.isEmpty() || endNodes.isEmpty())
			return;

		Display displ = Display.getCurrent();
		Color color = crossLink ? displ.getSystemColor(SWT.COLOR_RED) : displ.getSystemColor(SWT.COLOR_DARK_GRAY);
		gc.setForeground(color);

		final List<Rectangle> nodesExternalBounds = drawTemporaryLabels(gc);
		final List<Node> nodes = getNodes();
		final List<Point> ctrPoints = getCenterPoints(nodesExternalBounds, nodes);
		final Point rp = getReferencePoint(ctrPoints);

		// collect anchors & draw connections
		for (Node startNode : startNodes) {
			Point anchor = startNode.getAnchor(this, rp);
			paintEdgeLine(gc, anchor, rp, false);
		}
		for (Node endNode : endNodes) {
			Point anchor = endNode.getAnchor(this, rp);
			paintEdgeLine(gc, rp, anchor, crossLink);
		}
		for (Rectangle extBound : nodesExternalBounds) {
			Point anchor = extBound.getIntersectionLocation(rp);
			paintEdgeLine(gc, rp, anchor, crossLink);
		}

		// draw label
		drawLabel(gc, nodes, rp);
	}

	/***/
	protected void paintEdgeLine(GC gc, Point src, Point tgt, boolean drawArrow) {
		GraphUtils.drawLine(gc, src, tgt, drawArrow);
	}

	private void drawLabel(GC gc, final List<Node> nodes, final Point rp) {
		if (label == null)
			return;

		float x = rp.x;
		float y = rp.y;
		if (getStartNodes().size() == 1 && getEndNodes().size() == 1 && isCrossLink()) {
			// special case for 1:1 cross links:
			// take last end node or if not there (?) last start node and compute half of distance to rp.
			// (rationale: for these links there might be a reverse link and then labels would overlap)
			final Node labelNode = nodes.get(nodes.size() - 1);
			final Point labelNodeAnchor = labelNode.getAnchor(this, rp);
			x = (labelNodeAnchor.x + rp.x) / 2f;
			y = (labelNodeAnchor.y + rp.y) / 2f;
		}
		GraphUtils.drawString(gc, label, x, y);
	}

	private Point getReferencePoint(final List<Point> ctrPoints) {
		float minx = Float.MAX_VALUE;
		float maxx = Float.MIN_VALUE;
		float miny = Float.MAX_VALUE;
		float maxy = Float.MIN_VALUE;
		for (Point c : ctrPoints) {
			minx = Math.min(minx, c.x);
			maxx = Math.max(maxx, c.x);
			miny = Math.min(miny, c.y);
			maxy = Math.max(maxy, c.y);
		}
		final float width = maxx - minx;
		final float height = maxy - miny;
		final Point rp = new Point(minx + width / 2, miny + height / 2);
		return rp;
	}

	private List<Point> getCenterPoints(final List<Rectangle> nodesExternalBounds, final List<Node> nodes) {
		final Stream<Point> nodeCenters = nodes.stream().map(n -> n.getCenter());
		final Stream<Point> extBoundCenters = nodesExternalBounds.stream().map(b -> b.getCenter());
		final Stream<Point> allCenters = Stream.concat(nodeCenters, extBoundCenters);
		final List<Point> ctrPoints = allCenters.collect(Collectors.toList());
		return ctrPoints;
	}

	/**
	 * draw temporary labels for external nodes (and save their bounds for later)
	 */
	private List<Rectangle> drawTemporaryLabels(GC gc) {
		final List<Rectangle> nodesExternalBounds = new ArrayList<>();
		final Rectangle clip = GraphUtils.getClip(gc);
		float px = clip.x + clip.width;
		float py = clip.y + clip.height;
		for (String currNE : endNodesExternal) {
			final org.eclipse.swt.graphics.Point size = gc.stringExtent(currNE);
			py -= size.y + 4;
			final float rx = px - (size.x + 4);
			final Rectangle b = new Rectangle(rx, py, size.x, size.y);
			nodesExternalBounds.add(b);
			// TODO string extent will be computed twice :(
			GraphUtils.drawString(gc, currNE, b.x + b.width / 2, b.y + b.height / 2);
		}
		return nodesExternalBounds;
	}
}
