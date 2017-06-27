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
package org.eclipse.n4js.n4jsx;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

/**
 * Globals for N4JSX sub-language
 */
public final class N4JSXGlobals {

	/**
	 * Files extension of JSX source files (<b>not</b> including the separator dot).
	 */
	public static final String JSX_FILE_EXTENSION = "jsx";

	/**
	 * Files extension of N4JSX source files (<b>not</b> including the separator dot).
	 */
	public static final String N4JSX_FILE_EXTENSION = "n4jsx";

	/**
	 * Unmodifiable list containing {@link #N4JSX_FILE_EXTENSION} and {@link #JSX_FILE_EXTENSION}.
	 */
	public static final Collection<String> ALL_JSX_FILE_EXTENSIONS = unmodifiableCollection(newLinkedHashSet(asList(
			N4JSX_FILE_EXTENSION,
			JSX_FILE_EXTENSION)));

	private N4JSXGlobals() {
		// private to prevent inheritance & instantiation.
	}
}
