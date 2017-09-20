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
package org.eclipse.n4js.smith.dash.ui.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

/**
 * Some utility methods.
 */
@SuppressWarnings("javadoc")
public class GraphUtils {

	private static final Map<RGB, Color> colors = new HashMap<>();

	public static Color getColor(int red, int green, int blue) {
		return getColor(new RGB(red, green, blue));
	}

	public static Color getColor(RGB rgb) {
		if (!colors.containsKey(rgb)) {
			colors.put(
					new RGB(rgb.red, rgb.green, rgb.blue),
					new Color(Display.getCurrent(), rgb.red, rgb.green, rgb.blue));
		}
		return colors.get(rgb);
	}

	public static Rectangle getClip(GC gc) {
		org.eclipse.swt.graphics.Rectangle clip = gc.getClipping();
		return new Rectangle(clip.x, clip.y, clip.width, clip.height);
	}

	public static void drawString(GC gc, String str, float x, float y) {
		drawString(gc, str, x, y, 0, 0, 255);
	}

	public static void drawString(GC gc, String str, float x, float y, float width, float height, int bgAlpha) {
		if (str != null) {
			org.eclipse.swt.graphics.Point size = gc.stringExtent(str);
			if (bgAlpha >= 255) {
				gc.drawString(str, Math.round(x + width / 2 - size.x / 2), Math.round(y + height / 2 - size.y / 2));
			}
			else {
				gc.drawString(str, Math.round(x + width / 2 - size.x / 2), Math.round(y + height / 2 - size.y / 2),
						true);
				if (bgAlpha > 0) {
					gc.setAlpha(bgAlpha);
					gc.fillRectangle(Math.round(x + width / 2 - size.x / 2), Math.round(y + height / 2 - size.y / 2),
							size.x, size.y);
					gc.setAlpha(255);
				}
			}
		}
	}

	public static List<Node> getNodesForElements(Collection<?> elements, List<? extends Node> allNodes) {
		return elements.stream()
				.map(elem -> getNodeForElement(elem, allNodes))
				.filter(node -> node != null)
				.collect(Collectors.toList());
	}

	public static Node getNodeForElement(Object element, List<? extends Node> allNodes) {
		for (Node currN : allNodes)
			if (currN.getElement() == element)
				return currN;
		return null;
	}

	public static Rectangle getBounds(Stream<Node> nodes) {
		final Iterator<Node> i = nodes.iterator();
		if (i.hasNext()) {
			float xMin = Float.MAX_VALUE;
			float xMax = Float.MIN_VALUE;
			float yMin = Float.MAX_VALUE;
			float yMax = Float.MIN_VALUE;
			while (i.hasNext()) {
				final Node n = i.next();
				xMin = Math.min(xMin, n.getX());
				yMin = Math.min(yMin, n.getY());
				xMax = Math.max(xMax, n.getX() + n.getWidth());
				yMax = Math.max(yMax, n.getY() + n.getHeight());
			}
			return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		}
		else
			return Rectangle.EMPTY;
	}

	public static void drawLine(GC gc, Point p1, Point p2, boolean directed) {
		gc.drawLine(Math.round(p1.x), Math.round(p1.y), Math.round(p2.x), Math.round(p2.y));
		if (directed)
			drawArrowHead(gc, p1, p2);
	}

	// poor man's implementation of decorators
	public static void drawArrowHead(GC gc, Point referencePoint, Point p) {
		final double angle = Math.atan2(p.y - referencePoint.y, p.x - referencePoint.x) * 180.0 / Math.PI;
		final Transform tf = new Transform(gc.getDevice());
		tf.rotate(new Double(angle).floatValue());
		tf.scale(7, 3);
		final float[] pnts = new float[] { -1, 1, -1, -1 };
		tf.transform(pnts);
		gc.drawLine(Math.round(p.x), Math.round(p.y), Math.round(p.x + pnts[0]), Math.round(p.y + pnts[1]));
		gc.drawLine(Math.round(p.x), Math.round(p.y), Math.round(p.x + pnts[2]), Math.round(p.y + pnts[3]));
		tf.dispose();
	}

	public static void drawRectangle(GC gc, float x, float y, float width, float height) {
		gc.drawRectangle(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
	}

	public static <T> List<T> concat(Collection<? extends T> coll1, Collection<? extends T> coll2) {
		final List<T> result = new ArrayList<>(coll1.size() + coll2.size());
		result.addAll(coll1);
		result.addAll(coll2);
		return result;
	}
}
