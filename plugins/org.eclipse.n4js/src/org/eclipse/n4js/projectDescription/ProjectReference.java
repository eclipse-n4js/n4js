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
package org.eclipse.n4js.projectDescription;

import java.util.Objects;

import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ImmutableDataClass;

/**
 * Reference to another project without version requirement.
 */
@SuppressWarnings("javadoc")
public class ProjectReference extends ImmutableDataClass {

	private final String projectName;

	public ProjectReference(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public N4JSProjectName getN4JSProjectName() {
		return projectName != null ? new N4JSProjectName(projectName) : null;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				projectName);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectReference other = (ProjectReference) obj;
		return Objects.equals(projectName, other.projectName);
	}
}
