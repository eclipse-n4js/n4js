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
package org.eclipse.n4js.projectModel.names;

import org.eclipse.n4js.utils.ProjectDescriptionUtils;

/**
 *
 */
public class EclipseProjectName {

	private final String name;

	public EclipseProjectName(String name) {
		this.name = name;
	}

	public String getRawName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public N4JSProjectName toN4JSProjectName() {
		return new N4JSProjectName(ProjectDescriptionUtils.convertEclipseProjectNameToN4JSProjectName(name));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			throw new IllegalArgumentException("Cannot compare to type " + obj.getClass().getName());
		EclipseProjectName other = (EclipseProjectName) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
