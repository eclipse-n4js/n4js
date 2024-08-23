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

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilter;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectExports;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ModuleFilterUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.UriExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Extends Xtext's default {@link ProjectConfigSnapshot} by some additional attributes (e.g. project type).
 */
public class N4JSProjectConfigSnapshot extends ProjectConfigSnapshot {

	private final ProjectDescription projectDescription;
	private final ImmutableBiMap<String, String> packageNameToProjectIds;
	private final boolean external;

	/** Creates a new {@link N4JSProjectConfigSnapshot}. */
	public N4JSProjectConfigSnapshot(ProjectDescription projectDescription, URI path,
			boolean indexOnly, boolean generatorEnabled, Iterable<String> dependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders, Map<String, String> packageNameToProjectIds) {

		super(projectDescription.getId(), path,
				Collections.singleton(URIUtils.trimTrailingPathSeparator(path).appendSegment(N4JSGlobals.PACKAGE_JSON)),
				indexOnly, generatorEnabled, dependencies, sourceFolders);

		this.projectDescription = Objects.requireNonNull(projectDescription);
		this.packageNameToProjectIds = ImmutableBiMap.copyOf(packageNameToProjectIds);
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
	 * in the <code>package.json</code> but the {@link N4JSProjectConfig#getSemanticDependencies() "semantic"
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

	/**
	 * Returns the value of the {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} property or the plain
	 * package name iff this project is an {@code @types}-project.
	 */
	public N4JSPackageName getDefinesPackage() {
		N4JSPackageName definesPackageRaw = getDefinesPackageRaw();
		if (definesPackageRaw == null && getN4JSPackageName().isScopeTypes()) {
			definesPackageRaw = new N4JSPackageName(getN4JSPackageName().getPlainName());
		}
		return definesPackageRaw;
	}

	/** Returns the value of the {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} property. */
	public N4JSPackageName getDefinesPackageRaw() {
		String definesPackage = projectDescription.getDefinesPackage();
		return definesPackage != null ? new N4JSPackageName(definesPackage) : null;
	}

	@Override
	protected int computeHashCode() {
		// note: no need to consider "packageNameToProjectIds" and "external" because it is derived information
		return Objects.hash(
				super.computeHashCode(),
				projectDescription);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		// note: no need to check "packageNameToProjectIds" and "external" because it is derived information
		N4JSProjectConfigSnapshot other = (N4JSProjectConfigSnapshot) obj;
		return super.computeEquals(other)
				&& Objects.equals(projectDescription, other.projectDescription);
	}

	@Override
	protected void toStringAdditionalProperties(StringBuilder sb) {
		if (external) {
			sb.append("    external: true\n");
		}
		projectDescription.toStringAdditionalProperties(sb);
	}

	@Override
	protected boolean isOmittedFromToString() {
		return isExternal();
	}

	/** Returns the project id for a given package name or the package name itself. */
	public String getProjectIdForPackageName(String packageName) {
		return packageNameToProjectIds.getOrDefault(packageName, packageName);
	}

	/**
	 * Returns true iff the given packageName can be resolved to a project id. Usually it cannot be resolved for ignored
	 * dependencies of plain-js projects and returns false in those cases.
	 */
	public boolean isKnownDependency(N4JSPackageName packageName) {
		return packageNameToProjectIds.containsKey(packageName.toString());
	}

	/**
	 * Returns true iff the given project id can be resolved. Usually it cannot be resolved for ignored dependencies of
	 * plain-js projects and returns false in those cases.
	 */
	public boolean isKnownDependency(String projectId) {
		return packageNameToProjectIds.containsValue(projectId);
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	/** This project's name as an {@link N4JSPackageName}. */
	public N4JSPackageName getN4JSPackageName() {
		if (Strings.isNullOrEmpty(getPackageName())) {
			return new N4JSPackageName(getName());
		}
		return new N4JSPackageName(getPackageName());
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

	/** Return the project id. */
	@Override
	public String getName() {
		return super.getName();
	}

	/** Returns this project's {@link ProjectDescription#getPackageName() package name}. */
	public String getPackageName() {
		return projectDescription.getPackageName();
	}

	/**
	 * Returns this project's {@link ProjectDescription#getProjectType() type} or {@link ProjectType#DEFINITION} iff
	 * this project is an {@code @types}-project.
	 */
	public ProjectType getType() {
		ProjectType typeRaw = getTypeRaw();
		if (typeRaw == ProjectType.PLAINJS && getN4JSPackageName().isScopeTypes()) {
			return ProjectType.DEFINITION;
		}
		return typeRaw;
	}

	/** Returns this project's {@link ProjectDescription#getProjectType() type}. */
	public ProjectType getTypeRaw() {
		return projectDescription.getProjectType();
	}

	/** Returns this project's {@link ProjectDescription#getVersion() version}. */
	public VersionNumber getVersion() {
		return projectDescription.getVersion();
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

	/** See {@link ProjectDescription#isESM()}. */
	public boolean isESM() {
		return projectDescription.isESM();
	}

	/**
	 * Returns this project's {@link ProjectDescription#getProjectDependencies() dependencies} and
	 * {@link ProjectDescription#getImplementedProjects() implemented projects} (in this order).
	 */
	public ImmutableList<String> getDependenciesAndImplementedApis() {
		ImmutableList.Builder<String> result = ImmutableList.builder();
		result.addAll(getDependencies());
		result.addAll(
				Iterables.transform(projectDescription.getImplementedProjects(), ProjectReference::getPackageName));
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
			if (mf.getType() == moduleFilterType) {
				result.addAll(mf.getSpecifiers());
			}
		}
		return result.build();
	}

	/** Returns all Uris that are contained in a source folder of this project */
	public Set<URI> getAllContents(IFileSystemScanner scanner) {
		return Sets.newLinkedHashSet(
				IterableExtensions.flatMap(getSourceFolders(), (srcFolder) -> srcFolder.getAllResources(scanner)));
	}

	/**
	 * Returns {@code true} iff this is a plain js project in scope '@types'.
	 * <p>
	 * If {@code true}, the builder will build only the closure of all included files started from those files defined
	 * in the tsconfig.json properties {@code files}, {@code include} and in the package.json properties {@code main},
	 * {@code type}. As a consequence, there might be files in the source folders of this project that remain unbuilt.
	 * <p>
	 * If {@code false}, the builder will build all files included in the source folders.
	 */
	public boolean hasTsConfigBuildSemantic() {
		ProjectDescription pd = getProjectDescription();
		N4JSPackageName packageName = pd.getN4JSProjectName();
		if (packageName != null
				&& N4JSGlobals.NPM_PACKAGES_WITH_TS_CONFIG_BUILD_SEMANTICS.contains(packageName)) {
			return true;
		}
		// default rules
		if (pd.getProjectType() != ProjectType.PLAINJS) {
			return false;
		}
		if (packageName != null && packageName.isScopeTypes()) {
			return true;
		}
		if (pd.getTypes() != null && !pd.getTypes().isBlank()) {
			return true;
		}
		return false;
	}

	/**
	 * If {@link #hasTsConfigBuildSemantic()} is {@code true}, this method returns a set of all {@link URI}s declared in
	 * the tsconfig.json properties {@code files}, {@code include} and in the package.json properties {@code main},
	 * {@code type}. Otherwise an empty set is returned.
	 */
	@SuppressWarnings("resource") // due to call to FileSystems.getDefault()
	public Set<URI> computeStartUris(IFileSystemScanner fileSystemScanner) {
		if (hasTsConfigBuildSemantic()) {
			Set<URI> startUris = new LinkedHashSet<>();

			FileSystem fs = FileSystems.getDefault();
			ProjectDescription pd = getProjectDescription();

			List<Path> files = new ArrayList<>();
			List<String> globsToInclude = new ArrayList<>();
			List<String> globsToExclude = new ArrayList<>();

			List<String> sourceContainerPaths = getProjectDescription().getSourceContainers().stream()
					.flatMap(scd -> ProjectDescriptionUtils.getPathsNormalized(scd.getPaths()).stream())
					.collect(Collectors.toList());
			if (pd.getMainModule() != null) {
				String mainModule = pd.getMainModule();
				for (String srcPath : sourceContainerPaths) {
					String mainModulePath = new File(srcPath, mainModule).toString();
					URI main = URI.createFileURI(mainModulePath).resolve(getPath());

					if (URIUtils.toFile(main).isFile()) {
						startUris.add(main);
					} else if (URIUtils.toFile(main.appendFileExtension(N4JSGlobals.DTS_FILE_EXTENSION)).isFile()) {
						startUris.add(main.appendFileExtension(N4JSGlobals.DTS_FILE_EXTENSION));
					} else if (URIUtils.toFile(main.appendFileExtension(N4JSGlobals.JS_FILE_EXTENSION)).isFile()) {
						startUris.add(main.appendFileExtension(N4JSGlobals.JS_FILE_EXTENSION));
					}
				}
			}
			for (ProjectExports pe : pd.getExports()) {
				if (pe.getMainModule() == null) {
					continue;
				}
				for (String srcPath : sourceContainerPaths) {
					String mainModulePath = new File(srcPath, pe.getMainModule().toString("/")).toString();
					URI expMain = URI.createFileURI(mainModulePath).resolve(getPath());
					if (URIUtils.toFile(expMain).isFile()) {
						startUris.add(expMain);
					} else if (URIUtils.toFile(expMain.appendFileExtension(N4JSGlobals.DTS_FILE_EXTENSION)).isFile()) {
						startUris.add(expMain.appendFileExtension(N4JSGlobals.DTS_FILE_EXTENSION));
					} else if (URIUtils.toFile(expMain.appendFileExtension(N4JSGlobals.JS_FILE_EXTENSION)).isFile()) {
						startUris.add(expMain.appendFileExtension(N4JSGlobals.JS_FILE_EXTENSION));
					}
				}
			}

			files.addAll(Lists.transform(pd.getTsFiles(), Path::of));
			globsToInclude.addAll(pd.getTsInclude());
			globsToExclude.addAll(pd.getTsExclude());

			if (files.isEmpty() && globsToInclude.isEmpty()) {
				return startUris;
			}

			List<PathMatcher> pmInclude = new ArrayList<>();
			List<PathMatcher> pmExclude = new ArrayList<>();
			for (String glob : globsToInclude) {
				PathMatcher pathMatcher = fs.getPathMatcher("glob:" + glob);
				pmInclude.add(pathMatcher);
			}
			for (String glob : globsToExclude) {
				PathMatcher pathMatcher = fs.getPathMatcher("glob:" + glob);
				pmExclude.add(pathMatcher);
			}

			LOOP_ALL: for (URI someUri : getAllContents(fileSystemScanner)) {
				Path somePath = URIUtils.toPath(someUri.deresolve(getPath()));
				for (Path file : files) {
					if (Objects.equals(file, somePath)) {
						startUris.add(someUri);
						continue LOOP_ALL;
					}
				}

				boolean isIncluded = false;
				LOOP_INCLUDED: for (PathMatcher pm : pmInclude) {
					if (pm.matches(somePath)) {
						isIncluded = true;
						break LOOP_INCLUDED;
					}
				}
				if (isIncluded) {
					LOOP_EXCLUDED: for (PathMatcher pm : pmExclude) {
						if (pm.matches(somePath)) {
							isIncluded = false;
							break LOOP_EXCLUDED;
						}
					}

					if (isIncluded) {
						startUris.add(someUri);
					}
				}
			}
			return startUris;
		} else {
			return Collections.emptySet();
		}
	}
}
