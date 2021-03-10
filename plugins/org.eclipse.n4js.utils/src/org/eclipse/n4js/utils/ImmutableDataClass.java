/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

/**
 * Abstract base class for immutable data classes, e.g. hash code caching.
 */
public abstract class ImmutableDataClass {

	/** The hash code or <code>null</code> if not computed yet. */
	protected Integer cachedHashCode = null;

	@Override
	public final int hashCode() {
		if (cachedHashCode == null) {
			cachedHashCode = computeHashCode();
		}
		return cachedHashCode;
	}

	/**
	 * Implement to provide the actual hash code computation. The returned hash code will always be cached, so this need
	 * not be taken care of by the implementation of this method.
	 */
	protected abstract int computeHashCode();

	@Override
	public final boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!getClass().isInstance(obj))
			return false;
		return super.equals(obj);
	}

	/**
	 * Implement to check the properties of a subclass for equality.
	 *
	 * @param obj
	 *            the object to check. This will never be <code>null</code>, will never be <code>this</code>, and will
	 *            always be of a subtype of <code>this.getClass()</code>, so you can immediately cast it.
	 */
	protected abstract boolean computeEquals(Object obj);
}
