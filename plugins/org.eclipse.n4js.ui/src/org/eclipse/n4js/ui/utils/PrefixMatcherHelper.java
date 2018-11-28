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
package org.eclipse.n4js.ui.utils;

import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher.CamelCase;

import com.google.inject.Inject;

/**
 * Provides access to Xtext's camel case matching algorithm, as implemented in class {@link CamelCase
 * PrefixMatcher.CamelCase} (actually Xtext delegates to the JDT).
 */
public final class PrefixMatcherHelper {

	private final PrefixMatcher.IgnoreCase prefixMatcher;
	private final boolean canDoCamelCaseMatch;

	/***/
	// NOTE: Xtext's FQNPrefixMatcher injects PrefixMatcher.IgnoreCase, not PrefixMatcher, so we have to follow suit:
	@Inject
	public PrefixMatcherHelper(PrefixMatcher.IgnoreCase prefixMatcher) {
		if (!(prefixMatcher instanceof N4JSPrefixMatcher)) {
			throw new IllegalStateException("wrong injection setup: "
					+ "PrefixMatcher.IgnoreCase is expected to be bound to "
					+ N4JSPrefixMatcher.class.getSimpleName() + " but is bound to "
					+ prefixMatcher.getClass().getName());
		}
		this.prefixMatcher = prefixMatcher;
		this.canDoCamelCaseMatch = ((N4JSPrefixMatcher) prefixMatcher).isJdtAvailable();
	}

	/**
	 * See {@link org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher.CamelCase#camelCaseMatch(String, String)
	 * PrefixMatcher.CamelCase#camelCaseMatch()}.
	 */
	@SuppressWarnings("javadoc")
	public boolean camelCaseMatch(String name, String prefix) {
		return canDoCamelCaseMatch && ((N4JSPrefixMatcher) prefixMatcher).camelCaseMatch(name, prefix);
	}

	/**
	 * Sub-classed only to provide access to protected methods. Do not use directly; use {@link PrefixMatcherHelper}
	 * instead!
	 */
	public static final class N4JSPrefixMatcher extends CamelCase {

		@Override
		protected boolean isJdtAvailable() {
			return super.isJdtAvailable();
		}

		@Override
		protected boolean camelCaseMatch(String name, String prefix) {
			return super.camelCaseMatch(name, prefix);
		}
	}
}
