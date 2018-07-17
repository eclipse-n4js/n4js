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

//import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat;

import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.semver.model.SEMVERSerializer;

/** Custom type for {@code Pair<String, String>} that is used to describe dependency (i.e. npm package). */
public class DependencyInfo {

	/** name of the dependency */
	final public String name;
	/** version of the dependency */
	final public String version;

	/** Simple constructor, client might need to use {@link #create(ProjectReference)} */
	public DependencyInfo(String id, String version) {
		this.name = id;
		this.version = version;
	}

	/** factory method to create instances form {@code ProjectReference} */
	public static DependencyInfo create(ProjectReference projectReference) {
		return new DependencyInfo(toName(projectReference), toVersion(projectReference));
	}

	private static String toName(ProjectReference projectReference) {
		return projectReference.getProjectId();
	}

	private static String toVersion(ProjectReference projectReference) {
		String version = "";
		if (projectReference instanceof ProjectDependency) {
			ProjectDependency prjDep = (ProjectDependency) projectReference;
			version = SEMVERSerializer.toString(prjDep.getVersionConstraint());
		}
		return version;
	}
}
