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
package org.eclipse.n4js.projectModel.dependencies;

import org.eclipse.n4js.projectDescription.DependencyType;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.semver.model.SemverSerializer;

/** Custom type for {@code Pair<String, String>} that is used to describe dependency (i.e. npm package). */
public class DependencyInfo {

	/** name of the dependency */
	final public String name;
	/** version of the dependency */
	final public String version;
	/** type of the dependency */
	final public DependencyType type;

	/** Simple constructor, client might need to use {@link #create(ProjectReference)} */
	public DependencyInfo(String id, String version, DependencyType type) {
		this.name = id;
		this.version = version;
		this.type = type;
	}

	/** factory method to create instances from {@code ProjectReference}s. */
	public static DependencyInfo create(ProjectReference projectReference) {
		return new DependencyInfo(toName(projectReference), toVersion(projectReference), DependencyType.RUNTIME);
	}

	/** factory method to create instances from {@code ProjectDependency}s. */
	public static DependencyInfo create(ProjectDependency projectDependency) {
		return new DependencyInfo(toName(projectDependency), toVersion(projectDependency), projectDependency.getType());
	}

	private static String toName(ProjectReference projectReference) {
		return projectReference.getProjectId();
	}

	private static String toVersion(ProjectReference projectReference) {
		String version = "";
		if (projectReference instanceof ProjectDependency) {
			ProjectDependency prjDep = (ProjectDependency) projectReference;
			version = SemverSerializer.serialize(prjDep.getVersionRequirement());
		}
		return version;
	}
}
