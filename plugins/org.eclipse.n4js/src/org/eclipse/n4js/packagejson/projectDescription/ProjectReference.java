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
package org.eclipse.n4js.packagejson.projectDescription;

import java.util.Objects;

import org.eclipse.n4js.utils.ImmutableDataClass;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;

/**
 * Reference to another project without version requirement.
 */
@SuppressWarnings("javadoc")
public class ProjectReference extends ImmutableDataClass {

	private final String packageName;

	public ProjectReference(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public N4JSProjectName getN4JSProjectName() {
		return packageName != null ? new N4JSProjectName(packageName) : null;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				packageName);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectReference other = (ProjectReference) obj;
		return Objects.equals(packageName, other.packageName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " { packageName: " + packageName + " }";
	}
}
