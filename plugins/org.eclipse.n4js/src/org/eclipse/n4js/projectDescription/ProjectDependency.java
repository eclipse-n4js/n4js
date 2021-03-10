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

import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.model.SemverSerializer;

/**
 * Reference to another project, including a version requirement.
 */
@SuppressWarnings("javadoc")
public class ProjectDependency extends ProjectReference {

	private final DependencyType type;
	private final String versionRequirementString;
	private final NPMVersionRequirement versionRequirement;
	private final String internalVersionRequirementStr; // used for hashCode computation and equality check

	public ProjectDependency(String projectName, DependencyType type, String versionRequirementString,
			NPMVersionRequirement versionRequirement) {
		super(projectName);
		this.type = type;
		this.versionRequirementString = versionRequirementString;
		this.versionRequirement = versionRequirement;
		this.internalVersionRequirementStr = SemverSerializer.serialize(versionRequirement);
	}

	public DependencyType getType() {
		return type;
	}

	public String getVersionRequirementString() {
		return versionRequirementString;
	}

	public NPMVersionRequirement getVersionRequirement() {
		return versionRequirement;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				super.computeHashCode(),
				type,
				versionRequirementString,
				internalVersionRequirementStr);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectDependency other = (ProjectDependency) obj;
		return super.computeEquals(obj)
				&& type == other.type
				&& Objects.equals(versionRequirementString, other.versionRequirementString)
				&& Objects.equals(internalVersionRequirementStr, other.internalVersionRequirementStr);
	}
}
