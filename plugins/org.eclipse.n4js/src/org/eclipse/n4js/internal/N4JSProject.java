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

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static java.util.Collections.emptyList;
import static org.eclipse.emf.common.util.URI.createFileURI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ExecModule;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSProject implements IN4JSProject {

	private final N4JSModel model;
	private final URI location;
	private Boolean exists;
	private final boolean external;

	protected N4JSProject(URI location, boolean external, N4JSModel model) {
		this.location = location;
		this.external = external;
		this.model = model;
	}

	@Override
	public boolean isProject() {
		return true;
	}

	@Override
	public boolean isArchive() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof N4JSProject) {
			return URIUtils.equals(getLocation(), ((N4JSProject) obj).getLocation());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return URIUtils.hashCode(getLocation());
	}

	protected N4JSModel getModel() {
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
	public Optional<URI> getManifestLocation() {
		final File manifestFile = getManifestFile().orNull();
		if (null == manifestFile) {
			return absent();
		}
		return fromNullable(getFileUri(manifestFile));
	}

	/**
	 * Returns with the {@link URI EMF URI} of the file. Returns with {@code null} if the file argument is {@code null}
	 * or the file does not exist. This method may throw runtime exception if the canonical file cannot be retrieved of
	 * the argument.
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
			throw new RuntimeException("Error while trying to getting canonical file of the N4JS manifest.", e);
		}
	}

	private Optional<File> getManifestFile() {
		final File locationAsFile = new File(java.net.URI.create(location.toString()));
		if (locationAsFile.exists() && locationAsFile.isDirectory()) {
			final File manifest = new File(locationAsFile, IN4JSProject.N4MF_MANIFEST);
			return manifest.isFile() ? fromNullable(manifest) : absent();
		}

		return absent();
	}

	protected boolean checkExists() {
		return getManifestFile().isPresent();
	}

	@Override
	public ImmutableList<? extends IN4JSArchive> getLibraries() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getLibraries(this);
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getDependencies(this);
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getDependenciesAndImplementedApis(this);
	}

	@Override
	public Optional<String> getImplementationId() {
		if (!exists()) {
			return Optional.absent();
		}
		final ProjectDescription pd = model.getProjectDescription(getLocation());
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

	@Override
	public ImmutableList<? extends IN4JSSourceContainerAware> getAllDirectDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		ImmutableList.Builder<IN4JSSourceContainerAware> result = ImmutableList.builder();
		result.addAll(getDependencies());
		result.addAll(getLibraries());
		return result.build();
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainerAware> getProvidedRuntimeLibraries() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getProvidedRuntimeLibraries(this);
	}

	@Override
	public String getProjectId() {
		// because the projectId must be available even if the project does not exist, we do not read from the
		// ProjectDescription, here, but instead use the last segment of the location URI (equality between the two is
		// ensured by an n4mf validation)
		return location.lastSegment();
	}

	@Override
	public String getVendorID() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getVendorId();
	}

	@Override
	public List<BootstrapModule> getInitModules() {
		if (!exists())
			return new ArrayList<>();
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return new ArrayList<>();
		}
		return pd.getAllInitModules();
	}

	@Override
	public Optional<BootstrapModule> getExecModule() {
		if (!exists()) {
			return absent();
		}
		final ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return absent();
		}
		final ExecModule execModule = pd.getExecModule();
		if (null == execModule) {
			return absent();
		}
		return Optional.fromNullable(execModule.getExecModule());
	}

	@Override
	public URI getLocation() {
		return location;
	}

	@Override
	public Path getLocationPath() {
		final String pathStr = location.toFileString(); // pathStr will be null if location is not a file URI
		return pathStr != null ? Paths.get(pathStr) : null;
	}

	@Override
	public ImmutableList<? extends IN4JSSourceContainer> getSourceContainers() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getN4JSSourceContainers(this);
	}

	@Override
	public DeclaredVersion getVersion() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getProjectVersion();
	}

	@Override
	public String getOutputPath() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getOutputPath();
	}

	@Override
	public List<String> getResourcePaths() {
		if (!exists())
			return emptyList();
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return emptyList();
		}
		return pd.getResourcePaths();
	}

	@Override
	public ModuleFilter getModuleValidationFilter() {
		return getModuleFilterByType(ModuleFilterType.NO_VALIDATE);
	}

	@Override
	public ModuleFilter getNoModuleWrappingFilter() {
		return getModuleFilterByType(ModuleFilterType.NO_MODULE_WRAPPING);
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
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return emptyList();
		}
		return pd.getModuleFilters();
	}

	@Override
	public List<String> getLibraryFolders() {
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return emptyList();
		} else {
			return pd.getLibraryPaths();
		}
	}

	@Override
	public ProjectType getProjectType() {
		if (!exists())
			return null;

		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getProjectType();
	}

	@Override
	public String getMainModule() {
		if (!exists())
			return null;
		ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getMainModule();
	}

	@Override
	public Optional<String> getExtendedRuntimeEnvironmentId() {
		return fromNullable(model.getExtendedRuntimeEnvironmentName(this.location).orNull());
	}

	@Override
	public Optional<IN4JSSourceContainerAware> getExtendedRuntimeEnvironment() {
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
	public ModuleLoader getModuleLoader() {
		if (!exists())
			return null;
		final ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return null;
		}
		return pd.getModuleLoader() != null ? pd.getModuleLoader() : N4JSLanguageConstants.MODULE_LOADER_DEFAULT;
	}

	@Override
	public String toString() {
		String str = getProjectId();
		str += " (" + (exists() ? getProjectType() : "doesn't exist") + ")";
		return str;
	}

	@Override
	public boolean isExternal() {
		return external;
	}
}
