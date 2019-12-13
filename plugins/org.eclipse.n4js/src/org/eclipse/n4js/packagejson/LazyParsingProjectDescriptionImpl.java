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
import org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.VersionNumber;

/**
 * Allows for lazy parsing of versions.
 */
public class LazyParsingProjectDescriptionImpl extends ProjectDescriptionImpl {

	private String lazyProjectVersion;
	private SemverHelper semVerHelper;

	LazyParsingProjectDescriptionImpl() {
	}

	@Override
	public boolean eIsSet(int featureID) {
		if (featureID == ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION) {
			return projectVersion != null || lazyProjectVersion != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public VersionNumber getProjectVersion() {
		if (semVerHelper != null) {
			if (lazyProjectVersion != null) {
				projectVersion = semVerHelper.parseVersionNumber(lazyProjectVersion);
			}
			// lazyProjectVersion not reset for debugging purpose
			semVerHelper = null;
		}
		return super.getProjectVersion();
	}

	void setLazyProjectVersion(SemverHelper semVerHelper, String lazyProjectVersion) {
		this.lazyProjectVersion = lazyProjectVersion;
		this.semVerHelper = semVerHelper;
	}

	String getLazyProjectVersion() {
		return lazyProjectVersion;
	}

}
