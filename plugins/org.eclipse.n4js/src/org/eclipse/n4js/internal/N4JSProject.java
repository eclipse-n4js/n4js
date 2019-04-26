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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.projectDescription.BootstrapModule;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ModuleLoader;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.ISourceFolderEx;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

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
			return URIUtils.equals(location, otherP.location);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return URIUtils.hashCode(getLocation());
	}

	/// IProjectConfig

	@Override
	public String getName() {
		return this.getProjectName();
	}

	/**
	 * FIXME: MISSING JAVA-DOC<br/>
	 * THIS METHOD MUST RETURN AN URI THAT ENDS WITH '/'.<br/>
	 * ADD TO JAVA-DOC
	 * <p>
	 * Otherwise, {@link URIBasedFileSystemAccess#getURI(String, String)} will omit the project directory, ie. the last
	 * directory. The getURI call happens during {@link URIBasedFileSystemAccess#generateFile(...)}
	 */
	@Override
	public URI getPath() {
		URI locationURI = this.getLocation();
		if (!locationURI.lastSegment().isEmpty()) {
			locationURI = locationURI.appendSegment("");
		}
		return locationURI;
	}

	final class SourceContainterForPackageJson implements ISourceFolderEx {
		@Override
		public String getName() {
			return N4JSGlobals.PACKAGE_JSON;
		}

		@Override
		public List<URI> getAllResources() {
			return Collections.singletonList(getPath());
		}

		@Override
		public URI getPath() {
			URI projectURI = N4JSProject.this.getPath();
			URI trimedProjectURI = projectURI.hasTrailingPathSeparator() ? projectURI.trimSegments(1) : projectURI;
			URI packageJsonURI = trimedProjectURI.appendSegment(N4JSGlobals.PACKAGE_JSON);
			return packageJsonURI;
		}
	}

	@Override
	public Set<? extends ISourceFolderEx> getSourceFolders() {
		HashSet<ISourceFolderEx> sourceFolders = new HashSet<>(this.getSourceContainers());
		sourceFolders.add(new SourceContainterForPackageJson());
		return sourceFolders;
	}

	@Override
	public URI getOutputFolder() {
		String outputPath = this.getOutputPath();
		URI projectURI = this.getPath();
		URI outputURI = URI.createFileURI(outputPath);
		URI completeOutputURI = projectURI.appendSegments(outputURI.segments());
		return completeOutputURI;
	}

	@Override
	public IWorkspaceConfig getWorkspaceConfig() {
		return this.model;
	}

	/// END: IProjectConfig

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
	public Optional<URI> getProjectDescriptionLocation() {
		final File projectDescriptionFile = getProjectDescriptionFile(location).orNull();
		if (null == projectDescriptionFile) {
			return absent();
		}
		return fromNullable(getFileUri(projectDescriptionFile));
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
		return getProjectDescriptionFile(location).isPresent();
	}

	@Override
	public ImmutableList<? extends IN4JSProject> getDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		return model.getDependencies(this, false);
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
	public ImmutableList<? extends IN4JSProject> getAllDirectDependencies() {
		if (!exists()) {
			return ImmutableList.of();
		}
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		result.addAll(getDependencies());
		return result.build();
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
		return pd.getInitModules();
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
		return Optional.fromNullable(pd.getExecModule());
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
	public VersionNumber getVersion() {
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
		return FileUtils.normalizeToDotWhenEmpty(pd.getOutputPath());
	}

	@Override
	public ModuleFilter getModuleValidationFilter() {
		return getModuleFilterByType(ModuleFilterType.NO_VALIDATE);
	}

	@Override
	public ModuleFilter getNoModuleWrappingFilter() {
		return getModuleFilterByType(ModuleFilterType.NO_MODULE_WRAP);
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
		String str = getProjectName();
		str += " (" + (exists() ? getProjectType() : "doesn't exist") + ")";
		return str;
	}

	@Override
	public boolean isExternal() {
		boolean external = getLocation().segmentsList().contains(N4JSGlobals.NODE_MODULES);
		return external;
	}

	/**
	 * Indicates whether {@code directory} may be regarded as valid N4JS project directory.
	 */
	public static boolean isN4JSProjectDirectory(URI location) {
		return getProjectDescriptionFile(location).isPresent();
	}

	private static Optional<File> getProjectDescriptionFile(URI projectLocation) {
		final File locationAsFile = new File(projectLocation.toFileString());
		if (locationAsFile.isDirectory()) {
			// first check for a 'package.json' file
			final File packageJSON = new File(locationAsFile, IN4JSProject.PACKAGE_JSON);
			if (packageJSON.isFile()) {
				return fromNullable(packageJSON);
			}
			// next check for an XPECT 'package.json.xt' file
			final File packageJSONXpect = new File(locationAsFile,
					IN4JSProject.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION);

			if (packageJSONXpect.isFile()) {
				return fromNullable(packageJSONXpect);
			}
		}

		return absent();
	}

	@Override
	public boolean hasN4JSNature() {
		if (!exists())
			return false;
		final ProjectDescription pd = model.getProjectDescription(getLocation());
		if (pd == null) {
			return false;
		}
		return pd.isHasN4JSNature();
	}

	@Override
	public String getDefinesPackageName() {
		return getModel().getDefinesPackage(this);
	}

	@Override
	public boolean isUseES6Imports() {
		return getModel().isUseES6Imports(this);
	}
}
