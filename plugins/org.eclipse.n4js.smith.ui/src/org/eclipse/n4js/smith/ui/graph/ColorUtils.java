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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Utilities for color calculations.
 */
public class ColorUtils {
	private static final Map<RGB, Color> colors = new HashMap<>();

	/** returns {@link Color} based on the provided {@link RGB} color. */
	public static Color getColor(RGB rgb) {
		if (!colors.containsKey(rgb)) {
			colors.put(
					new RGB(rgb.red, rgb.green, rgb.blue),
					new Color(Display.getCurrent(), rgb.red, rgb.green, rgb.blue));
		}
		return colors.get(rgb);
	}

	/** Ensures that provided float is within RGB for range. */
	public static int clamp(float channel) {
		int v = (int) channel;
		if (v < 0)
			return 0;
		else if (v > 0xFF)
			return 0xFF;
		else
			return v;
	}

	/** Darkens provided color based on the provided factor. */
	public static Color darken(RGB color, float factor) {
		factor = (float) Math.cbrt(factor);

		int dr = clamp(color.red * factor);
		int dg = clamp(color.green * factor);
		int db = clamp(color.blue * factor);

		return ColorUtils.getColor(dr, dg, db);
	}

	private static Color getColor(int red, int green, int blue) {
		return getColor(new RGB(red, green, blue));
	}

}
