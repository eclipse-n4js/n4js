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
package org.eclipse.n4js.ui.contentassist;

import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher.LastSegmentFinder;

/**
 * The default last segment finder is tailored to Java type names, e.g. {@code java.util.Map.Entry} yields the substring
 * {@code Map.Entry} as its last segment. In JS, module names also often start with an uppercase letter thus we use a
 * simpler implementation that just looks for the last dot in the name, e.g it returns {@code Entry} as the last
 * segment.
 */
public class SimpleLastSegmentFinder implements LastSegmentFinder {

	@Override
	public String getLastSegment(String fqn, char delimiter) {
		int idx = fqn.lastIndexOf(delimiter);
		if (idx != -1 && idx < fqn.length() - 1) {
			return fqn.substring(idx + 1);
		}
		return null;
	}

}
