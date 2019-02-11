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
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.eclipse.n4js.projectDescription.ProjectType.TEST;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.HlcExternalLibraryWorkspace;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@SuppressWarnings({ "javadoc" })
@Singleton
public class N4JSModel {

	private final InternalN4JSWorkspace workspace;

	@Inject
	protected ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	protected FileBasedExternalPackageManager packageManager;

	@Inject
	private MultiCleartriggerCache cache;

	@Inject
	private ExternalLibraryPreferenceStore prefStore;

	@Inject
	public N4JSModel(InternalN4JSWorkspace workspace) {
		this.workspace = workspace;
	}

	public N4JSProject getN4JSProject(URI location) {
		checkArgument(location.isFile(), "Expecting file URI. Was: " + location);
		boolean external = (externalLibraryWorkspace != null && externalLibraryWorkspace.getProject(location) != null);
		return new N4JSProject(location, external, this);
	}

	public N4JSProject findProjectWith(URI nestedLocation) {
		// FIXME: mm
		// URI correctNestedLocation = convertToCorrespondingLocation(nestedLocation);
		URI location = workspace.findProjectWith(nestedLocation);
		if (location != null) {
			return getN4JSProject(location);
		}

		location = externalLibraryWorkspace.findProjectWith(nestedLocation);
		if (null != location) {
			return getN4JSProject(location);
		}

		return null;
	}

	public URI convertToCorrespondingLocation(URI uri) {
		String fileString = uri.toString();
		String nodeModulesElement = "/" + N4JSGlobals.NODE_MODULES + "/";
		if (fileString.contains(nodeModulesElement) && !fileString.endsWith(nodeModulesElement)) {
			if (uri.isPlatform()) {
				uri = tryConvertToFileUri(uri, fileString);
			}

		} else {
			if (uri.isFile()) {
				// FIXME: do like in ExternalLibraryErrorMarkerManager#setIssues
				uri = tryConvertToPlatformUri(uri);
			}
		}

		return uri;
	}

	private URI tryConvertToFileUri(URI uri, String fileString) {
		URI projectLoc = workspace.findProjectWith(uri);
		String lastSegment = projectLoc.lastSegment();
		String segment = uri.segment(1);

		if (Objects.equals(segment, lastSegment)) {
			N4JSProject n4jsProject = getN4JSProject(projectLoc);
			Path projectPath = n4jsProject.getLocationPath();
			String platformString = uri.toPlatformString(true);
			Path platformPath = Paths.get(platformString);
			Path platformPathWithoutProject = platformPath.subpath(1, platformPath.getNameCount());
			Path completeFilePath = projectPath.resolve(platformPathWithoutProject);

			boolean isInNodeModulesLocation = false;
			for (java.net.URI nmLocation : prefStore.getNodeModulesLocations()) {
				String nmLocString = nmLocation.getPath();
				if (completeFilePath.startsWith(nmLocString)) {
					isInNodeModulesLocation = true;
					break;
				}
			}

			if (isInNodeModulesLocation) {
				URI fileURI = URI.createFileURI(completeFilePath.toString());
				return fileURI;
			}
		}
		return uri;
	}

	private URI tryConvertToPlatformUri(URI uri) {
		String nested = uri.toFileString();
		java.nio.file.Path nestedPath = Paths.get(nested);

		for (URI projLoc : workspace.getAllProjectLocations()) {
			N4JSProject n4jsProject = getN4JSProject(projLoc);
			java.nio.file.Path locationPath = n4jsProject.getLocationPath();

			if (nestedPath.startsWith(locationPath)) {
				java.nio.file.Path nodeModulesPath = locationPath.resolve(N4JSGlobals.NODE_MODULES);

				if (!nestedPath.startsWith(nodeModulesPath) || nestedPath.equals(nodeModulesPath)) {
					// Note: There can be projects in nested node_modules folder.
					// The node_modules folder is still part of a project, but all
					// elements below the node_modules folder are not part of this project.
					Path projectRelativePath = locationPath.relativize(nestedPath);
					String platformString = n4jsProject.getLocation().toPlatformString(true)
							+ "/" + projectRelativePath.toString();
					URI projURI = URI.createPlatformResourceURI(platformString, true);
					return projURI;
				}
			}
		}
		return uri;
	}

	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation) {
		Optional<? extends IN4JSSourceContainer> foundN4JSSourceContainer = Optional.absent();

		N4JSProject project = findProjectWith(nestedLocation);
		foundN4JSSourceContainer = findN4JSSourceContainerInProject(project, nestedLocation);

		return foundN4JSSourceContainer;
	}

	public Optional<? extends IN4JSSourceContainer> findN4JSExternalSourceContainer(IN4JSProject extPackage,
			URI nestedLocation) {
		return findN4JSSourceContainerInProject(extPackage, nestedLocation);
	}

	protected Optional<? extends IN4JSSourceContainer> findN4JSSourceContainerInProject(IN4JSProject project,
			URI nestedLocation) {
		IN4JSSourceContainer matchingContainer = null;
		int matchingSegmentCount = -1;
		if (project != null) {
			for (IN4JSSourceContainer n4jsSourceContainer : project.getSourceContainers()) {
				if (isLocationInNestedInContainer(nestedLocation, n4jsSourceContainer)) {
					int segmentCount = n4jsSourceContainer.getLocation().segmentCount();
					if (segmentCount > matchingSegmentCount) {
						matchingContainer = n4jsSourceContainer;
						matchingSegmentCount = segmentCount;
					}
				}
			}
		}
		return Optional.fromNullable(matchingContainer);
	}

	private boolean isLocationInNestedInContainer(URI nestedLocation, IN4JSSourceContainer container) {
		URI containerLocation = container.getLocation();
		if (containerLocation == null || nestedLocation == null)
			return false;
		int maxSegments = containerLocation.segmentCount();
		if (nestedLocation.segmentCount() >= maxSegments) {
			for (int i = 0; i < maxSegments; i++) {
				if (!nestedLocation.segment(i).equals(containerLocation.segment(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected InternalN4JSWorkspace getInternalWorkspace() {
		return workspace;
	}

	/**
	 * This delegates to {@link InternalN4JSWorkspace#getProjectDescription(URI)} to allow caching.
	 */
	public ProjectDescription getProjectDescription(URI location) {
		ProjectDescription description = workspace.getProjectDescription(location);
		if (null == description) {
			description = externalLibraryWorkspace.getProjectDescription(location);
		}
		return description;
	}

	public ImmutableList<? extends IN4JSSourceContainer> getN4JSSourceContainers(N4JSProject project) {
		ImmutableList.Builder<IN4JSSourceContainer> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			List<SourceContainerDescription> sourceFragments = newArrayList(from(description.getSourceContainers()));
			sourceFragments.sort((f1, fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) -> ProjectDescriptionUtils
					.compareBySourceContainerType(f1, fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT));
			for (SourceContainerDescription sourceFragment : sourceFragments) {
				List<String> paths = ProjectDescriptionUtils.getPathsNormalized(sourceFragment);
				for (String path : paths) {
					// XXX poor man's canonical path conversion. Consider headless compiler with npm projects.
					final String relativeLocation = ".".equals(path) ? "" : path;
					IN4JSSourceContainer sourceContainer = this.createProjectN4JSSourceContainer(project,
							sourceFragment.getSourceContainerType(), relativeLocation);
					result.add(sourceContainer);
				}
			}
		}
		return result.build();
	}

	protected String getLocationPath(URI location) {
		return location.toFileString();
	}

	protected IN4JSSourceContainer createProjectN4JSSourceContainer(N4JSProject project, SourceContainerType type,
			String relativeLocation) {
		return new N4JSProjectSourceContainer(project, type, relativeLocation);
	}

	public ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project, boolean includeAbsentProjects) {
		return getDependencies(project, false, includeAbsentProjects);
	}

	public ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis(N4JSProject project,
			boolean includeAbsentProjects) {
		return getDependencies(project, true, includeAbsentProjects);
	}

	private ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project, boolean includeApis,
			boolean includeAbsentProjects) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			result.addAll(
					resolveProjectReferences(project, description.getProjectDependencies(), includeAbsentProjects));
			if (includeApis) {
				result.addAll(
						resolveProjectReferences(project, description.getImplementedProjects(), includeAbsentProjects));
			}
		}
		return result.build();
	}

	public Optional<IN4JSProject> getExtendedRuntimeEnvironment(N4JSProject project) {
		final URI location = project.getLocation();
		final ProjectDescription description = getProjectDescription(location);
		if (null == description) {
			return absent();
		}
		final ProjectReference ref = description.getExtendedRuntimeEnvironment();
		return resolveProjectReference(project, ref, false);
	}

	public ImmutableList<? extends IN4JSProject> getImplementedProjects(N4JSProject project) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			result.addAll(resolveProjectReferences(project, description.getImplementedProjects(), false));
		}
		return result.build();
	}

	public ImmutableList<? extends IN4JSProject> getProvidedRuntimeLibraries(N4JSProject project) {
		ImmutableList.Builder<IN4JSProject> providedRuntimes = ImmutableList.builder();

		EList<ProjectReference> runtimeLibraries = getAllProvidedRuntimeLibraries(project);
		URI projectLocation = project.getLocation();

		for (ProjectReference runtimeLibrary : runtimeLibraries) {
			URI location = workspace.getLocation(projectLocation, runtimeLibrary);
			if (null == location) {
				location = externalLibraryWorkspace.getLocation(projectLocation, runtimeLibrary);
			}

			if (null != location) {
				providedRuntimes.add(getN4JSProject(location));
			}
		}

		return providedRuntimes.build();
	}

	private EList<ProjectReference> getAllProvidedRuntimeLibraries(N4JSProject project) {
		URI projectLocation = project.getLocation();
		if (projectLocation == null)
			return ECollections.emptyEList();

		ProjectDescription description = getProjectDescription(projectLocation);
		if (description == null)
			return ECollections.emptyEList();

		EList<ProjectReference> runtimeLibraries = description.getProvidedRuntimeLibraries();
		if (runtimeLibraries == null)
			return ECollections.emptyEList();

		return runtimeLibraries;
	}

	public Iterator<URI> iterator(IN4JSSourceContainer sourceContainer) {
		if (sourceContainer.getProject().isExternal() && Platform.isRunning()) {
			// The `Platform.isRunning()` is not valid check for the OSGI headless compiler
			// it may still be valid in some scenarios (maybe some test scenarios)
			if (externalLibraryWorkspace instanceof HlcExternalLibraryWorkspace
					&& workspace instanceof FileBasedWorkspace
					&& workspace.findProjectWith(sourceContainer.getLocation()) != null) {
				return workspace.getFolderIterator(sourceContainer.getLocation());
			}

			return externalLibraryWorkspace.getFolderIterator(sourceContainer.getLocation());
		}
		return workspace.getFolderIterator(sourceContainer.getLocation());
	}

	/**
	 * @see IN4JSSourceContainer#findArtifact(QualifiedName, Optional)
	 */
	public URI findArtifact(IN4JSSourceContainer sourceContainer, QualifiedName name, Optional<String> fileExtension) {
		final String ext = fileExtension.or("").trim();
		final String extWithDot = !ext.isEmpty() && !ext.startsWith(".") ? "." + ext : ext;
		final String pathStr = name.toString("/") + extWithDot; // no need for IQualifiedNameConverter here!

		URI artifactLocation = workspace.findArtifactInFolder(sourceContainer.getLocation(), pathStr);
		if (null == artifactLocation) {
			artifactLocation = externalLibraryWorkspace.findArtifactInFolder(sourceContainer.getLocation(),
					pathStr);
		}
		return artifactLocation;
	}

	public Optional<String> getExtendedRuntimeEnvironmentName(URI location) {
		if (null == location) {
			return absent();
		}
		final ProjectDescription description = getProjectDescription(location);
		if (null == description) {
			return absent();
		}
		final ProjectReference reRef = description.getExtendedRuntimeEnvironment();
		if (null == reRef) {
			return absent();
		}
		return fromNullable(reRef.getProjectName());
	}

	public Collection<IN4JSProject> getTestedProjects(final N4JSProject project) {
		if (null == project || !project.exists()) {
			return emptyList();
		}

		// Shortcut to avoid reading the project description at all.
		if (!TEST.equals(project.getProjectType())) {
			return emptyList();
		}

		final Builder<IN4JSProject> builder = ImmutableList.builder();
		final URI location = project.getLocation();
		final ProjectDescription description = getProjectDescription(location);

		if (null != description) {
			for (ProjectReference testedProject : description.getTestedProjects()) {
				URI hostLocation = workspace.getLocation(location, testedProject);

				if (null == hostLocation) {
					hostLocation = externalLibraryWorkspace.getLocation(location, testedProject);
				}

				if (hostLocation != null) {
					final N4JSProject tested = getN4JSProject(hostLocation);
					if (null != tested && tested.exists()) {
						builder.add(tested);
					}
				}
			}
		}

		return builder.build();
	}

	/**
	 * Resolves the project reference argument for based on the given N4JS project. May return with an absent referenced
	 * project if any of the arguments are {@code null} or the reference cannot be resolved.
	 *
	 * @param project
	 *            the relative project to resolve the reference for. Optional, could be {@code null}.
	 * @param reference
	 *            the reference to resolve. Optional, could be {@code null}.
	 * @return the referenced project as an N4JS project instance. Could be missing if resolution failed but never
	 *         {@code null}.
	 */
	public Optional<IN4JSProject> resolveProjectReference(final IN4JSProject project,
			final ProjectReference reference, boolean includeAbsentProjects) {

		if (null == project || null == reference) {
			return absent();
		}

		final URI location = project.getLocation();
		if (null == location) {
			return absent();
		}

		URI dependencyLocation = workspace.getLocation(location, reference);
		if (null != dependencyLocation) {
			return fromNullable(getN4JSProject(dependencyLocation));
		}

		dependencyLocation = externalLibraryWorkspace.getLocation(location, reference);
		if (null != dependencyLocation) {
			return fromNullable(getN4JSProject(dependencyLocation));
		}
		if (includeAbsentProjects) {
			return fromNullable(newAbsentProject(reference.getProjectName()));
		}

		return absent();
	}

	/**
	 * Create a project handle for a project that does not exist. This is used to track unfulfilled dependencies to
	 * to-be-created projects.
	 */
	protected IN4JSProject newAbsentProject(String projectName) {
		final URI absent = URI.createFileURI(projectName);
		return new N4JSProject(absent, false, this);
	}

	/**
	 * Resolves an iterable of project references described at
	 * {@link #resolveProjectReference(IN4JSProject, ProjectReference)}, and returns with an collection of resolved N4JS
	 * projects. All unresolved references will be filtered out from the returning collection.
	 *
	 * @param project
	 *            the relative project.
	 * @param references
	 *            the references to resolve.
	 * @return a collection of resolved project references. Could be empty but never {@code null}.
	 */
	public Collection<IN4JSProject> resolveProjectReferences(final IN4JSProject project,
			final Iterable<? extends ProjectReference> references, boolean includeAbsentProjects) {

		if (null == project || null == references || isEmpty(references)) {
			return emptyList();
		}

		LinkedList<IN4JSProject> resolvedReferences = new LinkedList<>();
		for (ProjectReference ref : references) {
			IN4JSProject projectReference = resolveProjectReference(project, ref, includeAbsentProjects).orNull();
			if (projectReference != null) {
				resolvedReferences.add(projectReference);
			}
		}

		return resolvedReferences;
	}

	/**
	 * Returns the name of the package the given {@code project} provides type definitions for.
	 *
	 * {@code null} if this project does not specify the property (i.e. not a type definitions project (cf.
	 * {@link ProjectType#DEFINITION}).
	 */
	public String getDefinesPackage(final IN4JSProject project) {
		if (null == project) {
			return null;
		}
		final ProjectDescription projectDescription = getProjectDescription(project.getLocation());
		if (null != projectDescription) {
			return projectDescription.getDefinesPackage();
		}
		return null;
	}

	public Iterable<IN4JSProject> getSortedDependencies(IN4JSProject project) {
		SortedDependenciesProvider sdProvider = new SortedDependenciesProvider(project);
		Iterable<IN4JSProject> existing = cache.get(sdProvider, MultiCleartriggerCache.CACHE_KEY_SORTED_DEPENDENCIES,
				project.getLocation());
		return existing;
	}

	/**
	 * The idea of this mechanism is that following use case should be supported:
	 * <p>
	 * Given the projects {@code Impl}, {@code Def} and {@code Client} where {@code Client} depends on the former two
	 * and where {@code Def} is a definition project that provides n4jsd files for the project {@code Impl}.<br/>
	 * Consider that the workspace of these three projects is error-free and that the user changes the attribute
	 * {@code definesPackage} in the package.json file of {@code Def}. This will cause the result of
	 * {@link TypeDefinitionsAwareDependenciesSupplier#get(IN4JSProject)} to change (parameter is {@code Client}).
	 * Consequently, the cache has to be invalidated every time the package.json of {@code Client} changes <b>and/or</b>
	 * the package.json of all projects change which provide type definitions used in {@code Client}.
	 */
	private class SortedDependenciesProvider implements CleartriggerSupplier<Iterable<IN4JSProject>> {
		final IN4JSProject project;
		private Iterable<IN4JSProject> sortedDeps;

		SortedDependenciesProvider(IN4JSProject project) {
			this.project = project;
		}

		@Override
		public Iterable<IN4JSProject> get() {
			computeIfNull();
			return sortedDeps;
		}

		private void computeIfNull() {
			if (sortedDeps != null) {
				return;
			}
			sortedDeps = TypeDefinitionsAwareDependenciesSupplier.get(project);
		}

		@Override
		public Collection<URI> getCleartriggers() {
			computeIfNull();
			Set<URI> triggerURIs = new HashSet<>();
			for (IN4JSProject dep : sortedDeps) {
				if (dep.getDefinesPackageName() != null) {
					URI uri = dep.getLocation();
					triggerURIs.add(uri);
				}
			}
			return triggerURIs;
		}
	}

	/** @see IN4JSEclipseCore#mapExternalResourceToUserWorkspaceLocalResource(URI) */
	public URI mapExternalResourceToUserWorkspaceLocalResource(URI fileUri) {
		java.net.URI rootLocation = externalLibraryWorkspace.getRootLocationForResource(fileUri);
		if (rootLocation == null) {
			return null;
		}
		URI rootLocationEmfUri = URI.createURI(rootLocation.toString());
		if (rootLocationEmfUri == null) {
			return null;
		}
		N4JSProject findProjectWith = findProjectWith(rootLocationEmfUri);
		if (findProjectWith == null) {
			return null;
		}

		String uriString = fileUri.toFileString();
		java.nio.file.Path locationPath = findProjectWith.getLocationPath();
		String prjLocalFile = uriString.substring(locationPath.toString().length());
		URI prjLocalPlatformUri = URI.createPlatformResourceURI(findProjectWith.getProjectName() + prjLocalFile, true);
		return prjLocalPlatformUri;
	}

}
