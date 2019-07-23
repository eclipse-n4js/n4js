/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import static com.google.common.base.Optional.fromNullable;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.io.FileUtils;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 */
@SuppressWarnings({ "javadoc" })
public class N4JSProject implements IN4JSProject {

	private final N4JSModel<? extends SafeURI<?>> model;
	private final SafeURI<?> location;
	private Boolean exists;
	private final boolean external;

	protected N4JSProject(SafeURI<?> location, boolean external,
			N4JSModel<? extends SafeURI<?>> model) {
		this.location = location;
		this.external = external;
		this.model = model;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof N4JSProject) {
			N4JSProject otherP = ((N4JSProject) obj);
			if (external != otherP.external) {
				return false;
			}
			return location.equals(otherP.location);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (external ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public IN4JSSourceContainer findSourceContainerWith(URI member) {
		return model.findN4JSSourceContainerInProject(this, member).orNull();
	}

	protected N4JSModel<? extends SafeURI<?>> getModel() {
		return model;
	}

	@Override
	public boolean exists() {
		if (exists != null) {
			return exists.booleanValue();
		}
		return exists = checkExists();
	}

	@Override
	public SafeURI<?> getProjectDescriptionLocation() {
		return getProjectDescriptionFile();
	}

	protected boolean checkExists() {
		return getProjectDescriptionFile() != null;
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getDependencies(this, false);
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getSortedDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(model.getSortedDependencies(this));
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getDependenciesAndImplementedApis(this, false);
	}

	@Override
	public Optional<N4JSProjectName> getImplementationId() {
		if (!exists()) {
			return Optional.absent();
		}
		final ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return Optional.absent();
		}
		return Optional.fromNullable(pd.getImplementationId()).transform(N4JSProjectName::new);
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getImplementedProjects() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getImplementedProjects(this);
	}

	// TODO appears to be redundant
	@Override
	public ImmutableList<? extends IN4JSProject> getAllDirectDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return getDependencies();
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getProvidedRuntimeLibraries() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getProvidedRuntimeLibraries(this);
	}

	@Override
	public N4JSProjectName getProjectName() {
		// because the projectName must be available even if the project does not exist, we do not read from the
		// ProjectDescription, here, but instead derive the projectName from the location URI
		return location.getProjectName();
	}

	@Override
	public String getVendorID() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return null;
		}
		return pd.getVendorId();
	}

	@Override
	public SafeURI<?> getLocation() {
		return location;
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainer> getSourceContainers() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getN4JSSourceContainers(this);
	}

	@Override
	public VersionNumber getVersion() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return null;
		}
		return pd.getProjectVersion();
	}

	@Override
	public String getOutputPath() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return null;
		}
		return FileUtils.normalizeToDotWhenEmpty(pd.getOutputPath());
	}

	@Override
	public ModuleFilter getModuleValidationFilter() {
		return getModuleFilterByType(ModuleFilterType.NO_VALIDATE);
	}

	private ModuleFilter getModuleFilterByType(ModuleFilterType type) {
		for (ModuleFilter moduleFilter : getModuleFilters()) {
			if (moduleFilter.getModuleFilterType() == type) {
				return moduleFilter;
			}
		}
		return null;
	}

	private List<ModuleFilter> getModuleFilters() {
		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return emptyList();
		}
		return pd.getModuleFilters();
	}

	@Override
	public ProjectType getProjectType() {
		if (!exists())
			return null;

		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return null;
		}
		return pd.getProjectType();
	}

	@Override
	public String getMainModule() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return null;
		}
		return pd.getMainModule();
	}

	@Override
	public Optional<N4JSProjectName> getExtendedRuntimeEnvironmentId() {
		return model.getExtendedRuntimeEnvironmentName(this).transform(N4JSProjectName::new);
	}

	@Override
	public Optional<IN4JSProject> getExtendedRuntimeEnvironment() {
		return fromNullable(model.getExtendedRuntimeEnvironment(this).orNull());
	}

	@Override
	public Collection<IN4JSProject> getTestedProjects() {
		if (!exists()) {
			return emptyList();
		}
		return model.getTestedProjects(this);
	}

	@Override
	public String toString() {
		String str = getProjectName().getRawName();
		str += " (" + (exists() ? getProjectType() : "doesn't exist") + ")";
		return str;
	}

	@Override
	public boolean isExternal() {
		return external;
		// boolean result = getLocation().segmentsList().contains(N4JSGlobals.NODE_MODULES);
		// return result;
	}

	private SafeURI<?> getProjectDescriptionFile() {
		if (location.isDirectory()) {
			// first check for a 'package.json' file
			SafeURI<?> packageJson = location.appendSegment(IN4JSProject.PACKAGE_JSON);
			if (packageJson.isFile()) {
				return packageJson;
			}
			// next check for an XPECT 'package.json.xt' file
			SafeURI<?> packageJsonXpect = location
					.appendSegment(IN4JSProject.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION);
			if (packageJsonXpect.isFile()) {
				return packageJsonXpect;
			}
		}
		return null;
	}

	@Override
	public boolean hasN4JSNature() {
		if (!exists())
			return false;
		final ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return false;
		}
		return pd.isHasN4JSNature();
	}

	@Override
	public N4JSProjectName getDefinesPackageName() {
		String raw = getModel().getDefinesPackage(this);
		if (raw == null) {
			return null;
		}
		return new N4JSProjectName(raw);
	}
}
