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
 * Control flow edges are 1:1 associations in the control flow graph. They are displayed using arcs to prevent that two
 * edges lie on top of each other. Edges that point to the lower left are called <i>reverse arc</i> and displayed
 * differently to improve to visual appearance w.r.t. crossings of other edges.
 */
public class CFEdge extends Edge {

	/**
	 * Constructor
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
			paintEdge(gc, start, endNode);
		}
	}

	/**
	 * Paints an edge
	 */
	void paintEdge(GC gc, Node srcN, Node tgtN) {
		Color c = GraphUtils.getColor(50, 50, 50);
		gc.setForeground(c);
		gc.setBackground(c);

		Point src = srcN.getCenter();
		Point tgt = tgtN.getCenter();
		Point tgtB;
		if (isReverseArc(src, tgt)) {
			tgtB = paintReverseArc(gc, srcN, tgtN, src, tgt);
		} else {
			tgtB = paintArc(gc, srcN, tgtN, src, tgt);
		}

		drawArrowHead(gc, tgt, tgtB);
	}

	/**
	 * Paints an arc for arbitrary, non-reverse edges starting at S and targeting T. The arc uses an control point C to
	 * set the arc's bow direction. The control point is located at the half length of the line that connects S to T,
	 * but shifted perpendicular to the left, like shown below:
	 *
	 * <pre>
	 *       o C
	 *       |
	 *  o-------->o
	 *  S         T
	 * </pre>
	 */
	private Point paintArc(GC gc, Node srcN, Node tgtN, Point src, Point tgt) {
		Point srcB, tgtB;
		Point ctr = getArcControlPoint(src, tgt);

		Rectangle tgtR = new Rectangle(tgtN.x - 2, tgtN.y - 2, tgtN.width + 1, tgtN.height + 1);
		tgtB = GraphUtils.pointOnRect(ctr, tgtR);

		Rectangle srcR = new Rectangle(srcN.x - 2, srcN.y - 2, srcN.width + 1, srcN.height + 1);
		srcB = GraphUtils.pointOnRect(ctr, srcR);

		GraphUtils.arc(gc, ctr, srcB, tgtB);
		return tgtB;
	}

	/**
	 * Paints the reverse arc. Since reverse arcs always point downwards, the start and end points are simply located at
	 * the lower/upper center of the source/target nodes.
	 */
	private Point paintReverseArc(GC gc, Node srcN, Node tgtN, Point src, Point tgt) {
		Point tgtB = new Point(tgt.x, tgt.y - tgtN.height / 2 - 2);
		Point srcB = new Point(src.x, src.y + srcN.height / 2 + 1);
		GraphUtils.arcReversed(gc, srcB, tgtB);
		return tgtB;
	}

	/**
	 * Given there is a connecting line from src to tgt, this method returns a point ctr that lies between src and tgt
	 * points, moved perpendicular away from that connecting line to the left the side. If it is a reverse edge, the
	 * point ctr will be moved to the right side.
	 */
	private Point getArcControlPoint(Point src, Point tgt) {
		// when reversed, the control point is on the other side of the edge,
		// so that the arc will bow counter clock wise
		float reverser = isReverseArc(src, tgt) ? -1 : 1;
		float diffX = (src.x - tgt.x) / 2;
		float diffY = (src.y - tgt.y) / 2;
		float ctrX = src.x - diffX - reverser * diffY / 2;
		float ctrY = src.y - diffY + reverser * diffX / 2;
		Point ctr = new Point(ctrX, ctrY);
		return ctr;
	}

	/**
	 * returns true iff the edge points to the lower left
	 */
	private boolean isReverseArc(Point src, Point tgt) {
		boolean isReverse = src.x > tgt.x && src.y < tgt.y;
		return isReverse;
	}

	/**
	 * Draws the head of an arrow, that is two lines each starting at the target point of the edge. Depending on the
	 * direction of the edge, the arrow head lines are calculated. Also, the arrow head lines have always a length of 7
	 * pixel.
	 */
	private void drawArrowHead(GC gc, Point tgt, Point tgtB) {
		// Based on the edge direction, the arrow line end points are calculated
		float xx = (tgt.x - tgtB.x) / 2;
		float yy = (tgt.y - tgtB.y) / 2;
		float p1x = -yy - xx;
		float p1y = +xx - yy;
		float p2x = +yy - xx;
		float p2y = -xx - yy;

		// This is a correction factor to unify the lengths of the arrow heads
		float l = (float) (7 / Math.sqrt(p1x * p1x + p1y * p1y));
		p1x *= l;
		p1y *= l;
		p2x *= l;
		p2y *= l;

		Point arrP1 = new Point(tgtB.x + p1x, tgtB.y + p1y);
		Point arrP2 = new Point(tgtB.x + p2x, tgtB.y + p2y);
		gc.drawLine((int) tgtB.x, (int) tgtB.y, (int) arrP1.x, (int) arrP1.y);
		gc.drawLine((int) tgtB.x, (int) tgtB.y, (int) arrP2.x, (int) arrP2.y);
	}

}
