/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.ui;

import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher.CamelCase;

/**
 * This class makes available some utility functionality from bundle <code>org.eclipse.jdt.core</code> for the UI case
 * (not intended for headless case).
 * <p>
 * Since Xtext is using functionality from that bundle anyway (e.g. see {@link CamelCase PrefixMatcher.CamelCase}; only
 * optional dependency but we rely on that functionality in the UI case, for now), we do not introduce any additional
 * bundle dependencies by making available this functionality.
 */
public final class JDTUtils {

	/**
	 * Cf. {@link org.eclipse.jdt.core.search.SearchPattern#getMatchingRegions(String,String,int)}.
	 */
	public static int[] getMatchingRegions(String pattern, String name, int matchRule) {
		return org.eclipse.jdt.core.search.SearchPattern.getMatchingRegions(pattern, name, matchRule);
	}
}
