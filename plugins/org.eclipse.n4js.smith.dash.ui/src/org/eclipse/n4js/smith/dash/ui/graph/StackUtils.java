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
package org.eclipse.n4js.smith.dash.ui.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class StackUtils {
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

	public static Color getColor(float depthScale, float layerScale) {
		return darken(getCalculateColor(depthScale), layerScale);
	}

	private static RGB getCalculateColor(float depthScale) {
		float factor = 1.0f - depthScale;
		return new RGB(255, clamp(255 * factor), 0);
	}

	private static int clamp(float channel) {
		int v = (int) channel;
		if (v < 0)
			return 0;
		else if (v > 0xFF)
			return 0xFF;
		else
			return v;
	}

	private static Color darken(RGB color, float factor) {
		factor = (float) Math.cbrt(factor);

		int dr = clamp(color.red * factor);
		int dg = clamp(color.green * factor);
		int db = clamp(color.blue * factor);

		return getColor(dr, dg, db);
	}

	public static Rectangle getStackBounds(Stream<StackNode> nodes) {
		final Iterator<StackNode> i = nodes.iterator();
		if (i.hasNext()) {
			float xMin = Float.MAX_VALUE;
			float xMax = Float.MIN_VALUE;
			float yMin = Float.MAX_VALUE;
			float yMax = Float.MIN_VALUE;
			while (i.hasNext()) {
				final StackNode n = i.next();
				xMin = Math.min(xMin, n.x);
				yMin = Math.min(yMin, n.y);
				xMax = Math.max(xMax, n.x + n.width);
				yMax = Math.max(yMax, n.y + n.height);
			}
			return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		} else
			return Rectangle.EMPTY;
	}

	public static void drawString(GC gc, String str, float x, float y) {
		drawString(gc, str, x, y, 0, 0, 255);
	}

	public static void drawString(GC gc, String str, float x, float y, float width, float height, int bgAlpha) {
		if (str != null) {
			org.eclipse.swt.graphics.Point size = gc.stringExtent(str);
			if (bgAlpha >= 255) {
				gc.drawString(str, Math.round(x + width / 2 - size.x / 2), Math.round(y + height / 2 - size.y / 2));
			} else {
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

}
