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
package org.eclipse.n4js.analysis;

//TODO after java update bring back null analysis
//import javax.annotation.Nonnull;

import org.eclipse.n4js.n4JS.Script;

/**
 * Common interface for {@link Script} object.
 */
public interface Analyser {

	/**
	 * Performs analysis of {@link Script} object. Concrete implementations determine behaviour of concrete analysis.
	 */
	public void analyse(final Script script, final String codeName, final String code);
	// public void analyse(final @Nonnull Script script, final @Nonnull String codeName, final @Nonnull String code);

	/**
	 * Returns true if test is negative.
	 */
	public boolean isNegative();
}
