/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace;

import java.util.Collections;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilter;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ModuleFilterUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;

/**
 * Extends Xtext's default {@link ProjectConfigSnapshot} by some additional attributes (e.g. project type).
 */
public class N4JSProjectConfigSnapshot extends ProjectConfigSnapshot {

	private final ProjectDescription projectDescription;
	private final boolean external;

	/** Creates a new {@link N4JSProjectConfigSnapshot}. */
	public N4JSProjectConfigSnapshot(ProjectDescription projectDescription, URI path,
			boolean indexOnly, boolean generatorEnabled, Iterable<String> dependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders) {

		super(projectDescription.getProjectName(), path,
				Collections.singleton(URIUtils.trimTrailingPathSeparator(path).trimSegments(1)
						.appendSegment(N4JSGlobals.PACKAGE_JSON)),
				indexOnly, generatorEnabled, dependencies, sourceFolders);

		this.projectDescription = Objects.requireNonNull(projectDescription);
		this.external = isDirectlyLocatedInNodeModulesFolder(path);
	}

	private static boolean isDirectlyLocatedInNodeModulesFolder(URI location) {
		URI parent = URIUtils.trimTrailingPathSeparator(location).trimSegments(1);
		String lastSegment = parent.lastSegment();
		if (lastSegment != null && lastSegment.startsWith("@")) {
			parent = parent.trimSegments(1);
			lastSegment = parent.lastSegment();
		}
		if (N4JSGlobals.NODE_MODULES.equals(lastSegment)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the project dependencies.
	 * <p>
	 * Note that this method does not return the {@link N4JSProjectConfig#getDependencies() raw dependencies} as given
	 * in the <code>package.json</code> but the {@link N4JSProjectConfig#computeSemanticDependencies() "semantic"
	 * dependencies} computed by class {@link N4JSProjectConfig}.
	 */
	@Override
	public ImmutableSet<String> getDependencies() {
		return super.getDependencies();
	}

	/** Returns the {@link ProjectDescription}. */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Tells whether this project is located in a <code>node_modules</code> folder. */
	public boolean isExternal() {
		return external;
	}

	/** Returns the value of the {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} property. */
	public N4JSProjectName getDefinesPackage() {
		String definesPackage = projectDescription.getDefinesPackage();
		return definesPackage != null ? new N4JSProjectName(definesPackage) : null;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				super.computeHashCode(),
				projectDescription);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		N4JSProjectConfigSnapshot other = (N4JSProjectConfigSnapshot) obj;
		return super.computeEquals(other)
				&& Objects.equals(projectDescription, other.projectDescription);
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	/** This project's name as an {@link N4JSProjectName}. */
	public N4JSProjectName getN4JSProjectName() {
		return new N4JSProjectName(getName());
	}

	/** Returns this project's {@link #getPath() path} as a {@link FileURI}. */
	public FileURI getPathAsFileURI() {
		return new FileURI(new UriExtensions().withEmptyAuthority(getPath()));
	}

	@Override
	public ImmutableSet<N4JSSourceFolderSnapshot> getSourceFolders() {
		@SuppressWarnings("unchecked")
		ImmutableSet<N4JSSourceFolderSnapshot> sourceFolders = (ImmutableSet<N4JSSourceFolderSnapshot>) super.getSourceFolders();
		return sourceFolders;
	}

	@Override
	public N4JSSourceFolderSnapshot findSourceFolderContaining(URI uri) {
		return (N4JSSourceFolderSnapshot) super.findSourceFolderContaining(uri);
	}

	/** Returns this project's {@link ProjectDescription#getProjectType() type}. */
	public ProjectType getType() {
		return projectDescription.getProjectType();
	}

	/** Returns this project's {@link ProjectDescription#getProjectVersion() version}. */
	public VersionNumber getVersion() {
		return projectDescription.getProjectVersion();
	}

	/** Returns this project's {@link ProjectDescription#getVendorId() vendor ID}. */
	public String getVendorId() {
		return projectDescription.getVendorId();
	}

	/** Returns this project's {@link ProjectDescription#getVendorName() vendor name}. */
	public String getVendorName() {
		return projectDescription.getVendorName();
	}

	/** Returns this project's {@link ProjectDescription#getOutputPath() output path}. */
	public String getOutputPath() {
		return projectDescription.getOutputPath();
	}

	/** Returns this project's {@link ProjectDescription#getMainModule() main module}. */
	public String getMainModule() {
		return projectDescription.getMainModule();
	}

	/** Returns this project's {@link ProjectDescription#getImplementationId() implementation ID}. */
	public String getImplementationId() {
		return projectDescription.getImplementationId();
	}

	/**
	 * Returns this project's {@link ProjectDescription#getProjectDependencies() dependencies} and
	 * {@link ProjectDescription#getImplementedProjects() implemented projects} (in this order).
	 */
	public ImmutableList<ProjectReference> getDependenciesAndImplementedApis() {
		ImmutableList.Builder<ProjectReference> result = ImmutableList.builder();
		result.addAll(projectDescription.getProjectDependencies());
		result.addAll(projectDescription.getImplementedProjects());
		return result.build();
	}

	/**
	 * Tells whether the given nested location URI is matched by a module filter in this project's
	 * <code>package.json</code> file, only taking into account module filters of the given type.
	 */
	public boolean isMatchedByModuleFilterOfType(URI nestedLocation, ModuleFilterType moduleFilterType) {
		return ModuleFilterUtils.isPathContainedByFilter(this, nestedLocation,
				getModuleFilterSpecifiersByType(moduleFilterType));
	}

	/** Returns this project's {@link ProjectDescription#getModuleFilters() module filers} of the given type */
	public ImmutableList<ModuleFilterSpecifier> getModuleFilterSpecifiersByType(ModuleFilterType moduleFilterType) {
		Builder<ModuleFilterSpecifier> result = ImmutableList.builder();
		for (ModuleFilter mf : projectDescription.getModuleFilters()) {
			if (mf.getModuleFilterType() == moduleFilterType) {
				result.addAll(mf.getModuleSpecifiers());
			}
		}
		return result.build();
	}
}
