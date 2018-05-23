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
package org.eclipse.n4js.ts.types.util;

/**
 * Type variance.
 */
public enum Variance {
	/** Literal denoting covariance. */
	CO,
	/** Literal denoting contravariance. */
	CONTRA,
	/** Literal denoting two types are invariant. */
	INV;

	/**
	 * Inverse operation for variance.
	 */
	public Variance inverse() {
		switch (this) {
		case CO:
			return CONTRA;
		case CONTRA:
			return CO;
		case INV:
			return INV;
		default:
			throw new UnsupportedOperationException("unsupported literal: " + this);
		}
	}

	/**
	 * Multiplication operation for variance.
	 */
	public Variance mult(Variance other) {
		if (this == INV || other == INV)
			return INV;
		if (this == CONTRA && other == CONTRA)
			return CO;
		if (this == CONTRA || other == CONTRA)
			return CONTRA;
		if (this == CO && other == CO)
			return CO;
		throw new UnsupportedOperationException("unsupported literals: " + this + ", " + other);
	}

	/**
	 * Return human-readable string representation for this variance.
	 */
	public String getDescriptiveString(boolean inOutHint) {
		switch (this) {
		case CO:
			return inOutHint ? "covariant (out)" : "covariant";
		case CONTRA:
			return inOutHint ? "contravariant (in)" : "contravariant";
		case INV:
			return "invariant";
		default:
			throw new UnsupportedOperationException("unsupported literal: " + this);
		}
	}

	/**
	 * Return human-readable string representation for this variance as a noun.
	 */
	public String getDescriptiveStringNoun(boolean inOutHint) {
		switch (this) {
		case CO:
			return inOutHint ? "covariance (out)" : "covariance";
		case CONTRA:
			return inOutHint ? "contravariance (in)" : "contravariance";
		case INV:
			return "invariance";
		default:
			throw new UnsupportedOperationException("unsupported literal: " + this);
		}
	}

	/**
	 * Returns an operator-like representation of this literal.
	 */
	public String getRelationString() {
		if (this == CO)
			return "<:";
		if (this == CONTRA)
			return ":>";
		return "=";
	}
}
