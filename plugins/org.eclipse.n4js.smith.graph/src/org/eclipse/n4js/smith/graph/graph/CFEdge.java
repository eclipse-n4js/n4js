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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

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
public class CFEdge extends Edge {

	/**
	 */
	public CFEdge(String label, Node startNode, Collection<? extends Node> endNodes) {
		super(label, false, startNode, endNodes, Collections.emptyList());
	}

	/**
	 * Paint edge to given GC.
	 */
	@Override
	public void paint(GC gc) {
		Node start = startNodes.get(0);
		for (Node endNode : endNodes) {
			paintEdgeLine(gc, start, endNode);
		}
	}

	void paintEdgeLine(GC gc, Node srcN, Node tgtN) {
		Color c = GraphUtils.getColor(50, 50, 50);
		gc.setForeground(c);
		gc.setBackground(c);

		Point src = srcN.getCenter();
		Point tgt = tgtN.getCenter();
		Point ctr = getArcPoint(src, tgt);

		Point srcB = GraphUtils.pointOnRect(ctr.x, ctr.y, srcN.x, srcN.y, srcN.x + srcN.width, srcN.y + srcN.height);
		Point tgtB = GraphUtils.pointOnRect(ctr.x, ctr.y, tgtN.x, tgtN.y, tgtN.x + tgtN.width, tgtN.y + tgtN.height);

		GraphUtils.arc(gc, ctr, srcB, tgtB);

		drawArrowHead(gc, tgt, tgtB);
	}

	private Point getArcPoint(Point src, Point tgt) {
		float diffX = (src.x - tgt.x) / 2;
		float diffY = (src.y - tgt.y) / 2;
		float ctrX = src.x - diffX - diffY / 2;
		float ctrY = src.y - diffY + diffX / 2;
		Point ctr = new Point(ctrX, ctrY);
		return ctr;
	}

	private void drawArrowHead(GC gc, Point tgt, Point tgtB) {
		float xx = (tgt.x - tgtB.x) / 2;
		float yy = (tgt.y - tgtB.y) / 2;
		Point arrP1 = new Point(tgtB.x - yy - xx, tgtB.y + xx - yy);
		Point arrP2 = new Point(tgtB.x + yy - xx, tgtB.y - xx - yy);
		gc.drawLine((int) tgtB.x, (int) tgtB.y, (int) arrP1.x, (int) arrP1.y);
		gc.drawLine((int) tgtB.x, (int) tgtB.y, (int) arrP2.x, (int) arrP2.y);
	}

}
