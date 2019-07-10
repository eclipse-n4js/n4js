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
import static org.eclipse.emf.common.util.URI.createFileURI;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
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
			if (external && otherP.external) {
				return location == otherP.location;
			}
			return location.equals(otherP.location);
			// return URIUtils.equals(location, otherP.location);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exists == null) ? 0 : exists.hashCode());
		result = prime * result + (external ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public IN4JSSourceContainer findSourceFolderContaining(URI member) {
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

	/**
	 * Returns with the {@link org.eclipse.emf.common.util.URI} of the file. Returns with {@code null} if the file
	 * argument is {@code null} or the file does not exist. This method may throw runtime exception if the canonical
	 * file cannot be retrieved of the argument.
	 *
	 * @param file
	 *            the file to get the URI of.
	 * @return the URI of the file.
	 */
	protected URI getFileUri(final File file) {
		if (null == file || !file.exists()) {
			return null;
		}
		try {
			final File canonicalFile = file.getCanonicalFile();
			return createFileURI(canonicalFile.getAbsolutePath());
		} catch (final IOException e) {
			throw new RuntimeException(
					"Error while resolving the canonical File of package.json file " + file.getPath() + ".", e);
		}
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
	public Optional<String> getImplementationId() {
		if (!exists()) {
			return Optional.absent();
		}
		final ProjectDescription pd = model.getProjectDescription(this);
		if (pd == null) {
			return Optional.absent();
		}
		return Optional.fromNullable(pd.getImplementationId());
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
	public String getProjectName() {
		// because the projectName must be available even if the project does not exist, we do not read from the
		// ProjectDescription, here, but instead derive the projectName from the location URI
		return ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
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
	public URI _getLocation() {
		return location.toURI();
	}

	@Override
	public SafeURI<?> getSafeLocation() {
		return location;
	}

	// public Path _getLocationPath() {
	// final String pathStr = location.toFileString(); // pathStr will be null if location is not a file URI
	// return pathStr != null ? Paths.get(pathStr) : null;
	// }

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
	public Optional<String> getExtendedRuntimeEnvironmentId() {
		return fromNullable(model.getExtendedRuntimeEnvironmentName(this).orNull());
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
		String str = getProjectName();
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
	public String getDefinesPackageName() {
		return getModel().getDefinesPackage(this);
	}
}
