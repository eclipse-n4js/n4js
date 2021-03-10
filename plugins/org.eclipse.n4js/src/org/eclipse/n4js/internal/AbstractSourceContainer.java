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
package org.eclipse.n4js.internal;

import org.eclipse.n4js.projectDescription.SourceContainerType;

/**
 */
abstract class AbstractSourceContainer {

	private final SourceContainerType type;
	private final String relativeLocation;

	protected AbstractSourceContainer(SourceContainerType type, String relativeLocation) {
		this.type = type;
		switch (type) {
		case SOURCE:
		case EXTERNAL:
		case TEST:
			// expected
			break;
		default:
			// unknown
			throw new IllegalArgumentException(String.valueOf(type));
		}
		this.relativeLocation = relativeLocation;
	}

	public SourceContainerType getType() {
		return type;
	}

	public boolean isTest() {
		return type == SourceContainerType.TEST;
	}

	public boolean isSource() {
		return type == SourceContainerType.SOURCE;
	}

	public boolean isExternal() {
		return type == SourceContainerType.EXTERNAL;
	}

	public String getRelativeLocation() {
		return relativeLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relativeLocation == null) ? 0 : relativeLocation.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractSourceContainer))
			return false;
		AbstractSourceContainer other = (AbstractSourceContainer) obj;
		if (relativeLocation == null) {
			if (other.relativeLocation != null)
				return false;
		} else if (!relativeLocation.equals(other.relativeLocation))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public boolean exists() {
		return true;
	}
}
