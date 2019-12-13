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
package org.eclipse.n4js.packagejson;

import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;
import org.eclipse.n4js.projectDescription.impl.ProjectDependencyImpl;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;

/**
 * Allows for lazy parsing of version constraints.
 */
public class LazyParsingProjectDependencyImpl extends ProjectDependencyImpl {

	private SemverHelper semVerHelper;

	LazyParsingProjectDependencyImpl() {
	}

	@Override
	public boolean eIsSet(int featureID) {
		if (featureID == ProjectDescriptionPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT) {
			return versionRequirement != null || (versionRequirementString != null && semVerHelper != null);
		}
		return super.eIsSet(featureID);
	}

	@Override
	public NPMVersionRequirement getVersionRequirement() {
		if (semVerHelper != null) {
			if (versionRequirementString != null) {
				versionRequirement = semVerHelper.parse(versionRequirementString);
			}
			semVerHelper = null;
		}
		return super.getVersionRequirement();
	}

	void setLazyVersionRequirement(SemverHelper semVerHelper, String versionRequirement) {
		setVersionRequirementString(versionRequirement);
		this.semVerHelper = semVerHelper;
	}

}
