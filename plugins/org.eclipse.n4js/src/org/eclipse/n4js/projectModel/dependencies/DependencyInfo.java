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

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat;

import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.TestedProject;

/** Custom type for {@code Pair<String, String>} that is used to describe dependency (i.e. npm package). */
public class DependencyInfo {
	/**
	 * version representation for projects with no declared versions, mimics behavior of
	 * {@link org.eclipse.n4js.external.version.VersionConstraintFormatUtil#npmFormat}
	 */
	private static String NO_VERSION = "";

	/** name of the dependency */
	final public String name;
	/** version of the dependency */
	final public String version;

	/** Simple constructor, client might need to use {@link #create(ProjectReference)} */
	public DependencyInfo(String id, String version) {
		this.name = id;
		this.version = version;
	}

	/** Resolve conflict between two versions. Simple strategy - returns first if it is not empty. */
	public static String resolve(String version1, String version2) {
		return NO_VERSION.equals(version1) ? version2 : version1;
	}

	/** factory method to create instances form {@code ProjectReference} */
	public static DependencyInfo create(ProjectReference projectReference) {
		return new DependencyInfo(toName(projectReference), toVersion(projectReference));
	}

	private static String toName(ProjectReference projectReference) {
		return projectReference.getProject().getProjectId();
	}

	private static String toVersion(ProjectReference projectReference) {
		String version = NO_VERSION;
		if (projectReference instanceof ProjectDependency)
			version = npmFormat(((ProjectDependency) projectReference).getVersionConstraint());
		else if (projectReference instanceof TestedProject)
			version = npmFormat(((TestedProject) projectReference).getVersionConstraint());

		return version;
	}
}
