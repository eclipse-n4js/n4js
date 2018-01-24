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
package org.eclipse.n4js.flowgraphs.dataflow;

/**
 * A {@link HoldAssertion} tells if a guard always, never or sometimes holds.
 */
public enum HoldAssertion {
	/** The guard is always true. */
	AlwaysHolds,
	/** The guard is always false. */
	NeverHolds,
	/** There is no guarantee for the guard. */
	MayHold;

	/** @return true iff this {@link HoldAssertion} is {@link #AlwaysHolds} or {@link #MayHold}. */
	public boolean canHold() {
		return this == AlwaysHolds || this == MayHold;
	}

}
