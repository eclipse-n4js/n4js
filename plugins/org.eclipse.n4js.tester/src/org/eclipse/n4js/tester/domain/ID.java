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
package org.eclipse.n4js.tester.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of an identifier.
 */
@JsonAutoDetect
public class ID implements Cloneable {

	@JsonProperty
	private String value;

	/* default */static ID copyOf(final ID id) {
		return new ID(id.value);
	}

	/** Sole constructor. Used for serialization. */
	@SuppressWarnings("unused")
	private ID() {
	}

	/**
	 * Creates a new ID instance with the value argument.
	 *
	 * @param value
	 *            the unique value for the identifier instance.
	 */
	public ID(final String value) {
		this.value = value;
	}

	/**
	 * Returns with the value of the ID.
	 *
	 * @return the value of the identifier.
	 */
	public String getValue() {
		return value;
	}

	@Override
	public ID clone() throws CloneNotSupportedException {
		return copyOf(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ID other = (ID) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value;
	}

}
