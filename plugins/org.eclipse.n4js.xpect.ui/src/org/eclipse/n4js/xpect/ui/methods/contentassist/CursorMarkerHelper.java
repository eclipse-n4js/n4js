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
package org.eclipse.n4js.xpect.ui.methods.contentassist;

/**
 */
public class CursorMarkerHelper {

	final static String markerCursor = "<|>";
	final static String markerSelectionStart = "<[>";
	final static String markerSelectionEnd = "<]>";

	/**
	 * @param val
	 *            container
	 * @param marker
	 *            to be searched for.
	 * @return true if val contains (at least one) marker
	 */
	public static boolean exists(String val, String marker) {
		return val.indexOf(marker) >= 0;
	}

	/**
	 * @param sb
	 *            removing first occurrence of marker from this Stringbuilder
	 * @param marker
	 *            marker symbol
	 * @return index where the marker was found or 0
	 */
	public static final int deleteMarker(StringBuffer sb, String marker) {
		int idx = sb.indexOf(marker);
		if (idx >= 0) {
			sb.replace(idx, idx + marker.length(), "");
			return idx;
		}
		return 0;
	}

}
