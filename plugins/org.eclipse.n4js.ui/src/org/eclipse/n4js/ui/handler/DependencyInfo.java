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
package org.eclipse.n4js.ui.handler;

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat;

import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.TestedProject;

class DependencyInfo {
	/**
	 * version representation for projects with no declared versions, mimics behavior of
	 * {@link org.eclipse.n4js.external.version.VersionConstraintFormatUtil#npmFormat}
	 */
	private static String NO_VERSION = "";
	private final String id;
	private final String version;

	private DependencyInfo(String id, String version) {
		this.id = id;
		this.version = version;
	}

	public String getID() {
		return this.id;
	}

	public String getVersion() {
		return this.version;
	}

	/** Resolve conflict between two versions. Simple strategy - returns first if it is not empty. */
	public static String resolve(String version1, String version2) {
		return NO_VERSION.equals(version1) ? version2 : version1;
	}

	public static DependencyInfo create(ProjectReference projectReference) {
		return new DependencyInfo(toID(projectReference), toVersion(projectReference));
	}

	private static String toID(ProjectReference projectReference) {
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