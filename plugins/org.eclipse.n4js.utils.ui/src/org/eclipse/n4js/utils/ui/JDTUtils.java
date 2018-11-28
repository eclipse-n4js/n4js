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
 * This class makes available some utility functionality from bundle <code>org.eclipse.jdt.core</code>. Since Xtext is
 * using functionality from that bundle anyway (e.g. see {@link CamelCase PrefixMatcher.CamelCase}), we do not introduce
 * any additional bundle dependencies by making available this functionality.
 * <p>
 * This class follows the approach implemented in {@link CamelCase PrefixMatcher.CamelCase}.
 */
public final class JDTUtils {

	private static boolean jdtAvailable = checkJDTAvailable();

	private JDTUtils() {
		// no instances allowed
	}

	private static boolean checkJDTAvailable() {
		try {
			org.eclipse.jdt.core.search.SearchPattern.getMatchingRegions(null, null, 0);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Tells if the JDT is available for use. If this method returns false, all other public methods in this class will
	 * fail safe by returning a fall-back default value.
	 */
	public static boolean isJDTAvailable() {
		return jdtAvailable;
	}

	/**
	 * Cf. {@link org.eclipse.jdt.core.search.SearchPattern#getMatchingRegions(String,String,int)}.
	 */
	public static int[] getMatchingRegions(String pattern, String name, int matchRule) {
		if (!jdtAvailable) {
			return new int[] {};
		}
		return org.eclipse.jdt.core.search.SearchPattern.getMatchingRegions(pattern, name, matchRule);
	}
}
