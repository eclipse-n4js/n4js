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

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.google.common.collect.Lists;

/**
 * Control flow edges are 1:1 associations in the control flow graph. They are displayed using arcs to prevent that two
 * edges lie on top of each other. Edges that point to the lower left are called <i>reverse arc</i> and displayed
 * differently to improve to visual appearance w.r.t. crossings of other edges.
 */
public class CFEdge extends Edge {
	final TreeSet<ControlFlowType> cfTypes;

	/**
	 * Constructor
	 */
	public CFEdge(String label, Node startNode, Node endNode, Set<ControlFlowType> cfTypes) {
		super(label, false, startNode, Lists.newArrayList(endNode), Collections.emptyList());
		this.cfTypes = new TreeSet<>(cfTypes);
	}

	/** Paint edge to given GC. */
	@Override
	public void paint(GC gc) {
		Node startNode = startNodes.get(0);
		Node endNode = endNodes.get(0);
		paintEdge(gc, startNode, endNode);
	}

	/** Paints an edge */
	void paintEdge(GC gc, Node srcN, Node tgtN) {
		setColor(gc);

		Point src = srcN.getCenter();
		Point tgt = tgtN.getCenter();
		Point tgtB;

		if (srcN == tgtN) {
			tgtB = paintSelfArc(gc, tgtN, tgt);
		} else if (isReverseArc(src, tgt)) {
			tgtB = paintReverseArc(gc, srcN, tgtN, src, tgt);
		} else {
			tgtB = paintArc(gc, srcN, tgtN, src, tgt);
		}

		drawArrowHead(gc, tgt, tgtB);
	}

	/** Sets the color of the {@link GC} depending on the edge type. */
	void setColor(GC gc) {
		Display displ = Display.getCurrent();
		Color color = null;

		ControlFlowType cfType = cfTypes.first();
		switch (cfType) {
		case Repeat:
			color = displ.getSystemColor(SWT.COLOR_GREEN);
			break;
		case Break:
		case Continue:
		case Return:
			color = displ.getSystemColor(SWT.COLOR_BLUE);
			break;
		case Throw:
			color = displ.getSystemColor(SWT.COLOR_RED);
			break;
		default:
			color = GraphUtils.getColor(50, 50, 50);
		}
		gc.setForeground(color);
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

		drawLabel(gc, ctr);
		GraphUtils.arc(gc, ctr, srcB, tgtB);

		return tgtB;
	}

	/**
	 * Paints the reverse arc. Since reverse arcs always point downwards, the start and end points are simply located at
	 * the lower/upper center of the source/target nodes.
	 */
	private Point paintSelfArc(GC gc, Node tgtN, Point tgt) {
		Point srcB = new Point(tgt.x + tgtN.width / 2 + 2, tgt.y);
		Point tgtB = new Point(tgt.x, tgt.y - tgtN.height / 2 - 2);
		Point tgtL = new Point(tgtB.x, tgtB.y - 20);

		drawLabel(gc, tgtL);
		GraphUtils.arcSelf(gc, srcB, tgtB);

		return tgtB;
	}

	/**
	 * Paints the reverse arc. Since reverse arcs always point downwards, the start and end points are simply located at
	 * the lower/upper center of the source/target nodes.
	 */
	private Point paintReverseArc(GC gc, Node srcN, Node tgtN, Point src, Point tgt) {
		Point tgtB = new Point(tgt.x, tgt.y - tgtN.height / 2 - 2);
		Point srcB = new Point(src.x, src.y + srcN.height / 2 + 1);
		Point tgtL = new Point(tgtB.x, tgtB.y - 20);

		drawLabel(gc, tgtL);
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

	/** @returns true iff the edge points to the lower left */
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

	/** Draws the label between jumping control flow elements. */
	void drawLabel(GC gc, Point p) {
		label = "";
		for (ControlFlowType cfType : cfTypes) {
			switch (cfType) {
			case Break:
			case Continue:
			case Return:
			case Throw:
			case Repeat:
				if (!label.isEmpty())
					label += "|";
				label += cfType.name();
				break;
			default:
			}
		}

		org.eclipse.swt.graphics.Point size = gc.stringExtent(label);
		float x = p.x - size.x / 2;
		float y = p.y - size.y / 2;
		GraphUtils.drawString(gc, label, x, y);
	}

}
