/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal.locations;

import org.eclipse.emf.common.util.URI;

import com.google.common.base.Preconditions;

/**
 * A data type that denotes a location based on a URI. The structure of the wrapped URI is asserted by
 * {@link #validate(URI)}.
 */
public abstract class AbstractUriWrapper {

	/**
	 * The bare wrapped URI.
	 */
	private final URI wrapped;

	/**
	 * Wraps the given URI.
	 *
	 * @param wrapped
	 *            the bare URI.
	 */
	protected AbstractUriWrapper(URI wrapped) {
		this.wrapped = validate(wrapped);
	}

	/**
	 * Validate the given URI to be of the correct shape.
	 *
	 * @param given
	 *            the URI.
	 * @return the URI if it
	 */
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		return Preconditions.checkNotNull(given);
	}

	/**
	 * Returns the bare URI.
	 * 
	 * @return the wrapped uri.
	 */
	public URI toURI() {
		return wrapped;
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractUriWrapper other = (AbstractUriWrapper) obj;
		return wrapped.equals(other.wrapped);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + toURI() + "]";
	}

}
