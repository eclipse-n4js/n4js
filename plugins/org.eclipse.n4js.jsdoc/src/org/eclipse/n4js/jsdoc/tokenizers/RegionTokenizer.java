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
package org.eclipse.n4js.jsdoc.tokenizers;

import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocCharScanner.ScannerState;
import org.eclipse.n4js.jsdoc.JSDocToken;

/**
 * Returns a defined region as a single token. This token can then be processed by other (sub) parsers and tools. NB:
 * Preceeding whitespaces are not skipped but usually result in null returned by {@link #nextToken(JSDocCharScanner)}.
 */
public class RegionTokenizer extends AbstractJSDocTokenizer {

	/**
	 * represents beginning of the region
	 */
	public final String startMarker;
	/**
	 * represents end of the region
	 */
	public final String endMarker;
	/**
	 * determines if nesting regoins is allowed
	 */
	public final boolean nested;
	/**
	 * defines escape characters
	 */
	public final char escapeChar;
	/**
	 * defines if returned token will include start/end markers
	 */
	public final boolean includeMarkers;
	/**
	 * defines if breaklines are allowed in region
	 */
	public final boolean allowLinebreaks;

	/**
	 * @param startMarker
	 *            represents beginning of the region
	 * @param endMarker
	 *            represents end of the region
	 * @param nested
	 *            determines if nesting regoins is allowed
	 * @param escapeChar
	 *            defines escape characters
	 * @param includeMarkers
	 *            defines if returned token will include start/end markers
	 * @param allowLinebreaks
	 *            defines if breaklines are allowed in region
	 */
	public RegionTokenizer(String startMarker, String endMarker, boolean nested, char escapeChar,
			boolean includeMarkers,
			boolean allowLinebreaks) {
		this.startMarker = startMarker;
		this.endMarker = endMarker;
		this.nested = nested;
		this.escapeChar = escapeChar;
		this.includeMarkers = includeMarkers;
		this.allowLinebreaks = allowLinebreaks;
	}

	@Override
	public JSDocToken nextToken(JSDocCharScanner scanner) {

		int start = scanner.nextOffset();
		if (!findMarker(scanner, startMarker)) {
			return null;
		}

		StringBuilder strb = new StringBuilder();
		if (includeMarkers) {
			strb.append(startMarker);
		} else {
			start = scanner.nextOffset();
		}

		int nestedRegions = 0;
		int end = start;
		while (scanner.hasNext() && (allowLinebreaks || !scanner.skipped())) {
			if (scanner.peek() == escapeChar) {
				strb.append(scanner.next());
				if (scanner.hasNext() && (allowLinebreaks || !scanner.skipped())) {
					strb.append(scanner.next());
					end = scanner.offset();
				}
				continue;
			}

			if (findMarker(scanner, startMarker)) {
				if (nested) {
					nestedRegions++;
				}
				strb.append(startMarker);
			} else if (findMarker(scanner, endMarker)) {
				if (nestedRegions == 0) {
					if (includeMarkers) {
						strb.append(endMarker);
						end = scanner.offset();
					}
					return new JSDocToken(strb.toString(), start, end);
				}
				strb.append(endMarker);
				nestedRegions--;
			} else {
				strb.append(scanner.next());
				end = scanner.offset();
			}
		}

		return null;
	}

	private boolean findMarker(JSDocCharScanner scanner, final String marker) {
		ScannerState state = scanner.saveState();
		for (int i = 0; i < marker.length(); i++) {
			if (!scanner.hasNext() || (i > 0 && scanner.skipped())) {
				scanner.restoreState(state);
				return false;
			}

			char expected = marker.charAt(i);
			char ch = scanner.next();
			if (expected != ch) {
				scanner.restoreState(state);
				return false;
			}
		}
		return true;
	}
}
