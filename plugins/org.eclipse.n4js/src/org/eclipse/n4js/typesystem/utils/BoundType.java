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
package org.eclipse.n4js.typesystem.utils;

/**
 * Type of a bound, i.e. either upper or lower bound.
 */
public enum BoundType {
	/** Upper bound. */
	UPPER,
	/** Lower bound. */
	LOWER;

	/**
	 * Returns the inverse bound type, i.e. {@link #LOWER} for {@link #UPPER} and vice versa.
	 */
	public BoundType inverse() {
		switch (this) {
		case UPPER:
			return LOWER;
		case LOWER:
			return UPPER;
		default:
			throw new UnsupportedOperationException("unsupported literal: " + this);
		}
	}
}
