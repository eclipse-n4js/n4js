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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
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
public class N4JSModel<Loc extends SafeURI<Loc>> {

	private final InternalN4JSWorkspace<Loc> workspace;

	@Inject
	protected ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	protected FileBasedExternalPackageManager packageManager;

	@Inject
	private MultiCleartriggerCache cache;

	@Inject
	public N4JSModel(InternalN4JSWorkspace<Loc> workspace) {
		this.workspace = workspace;
	}

	public N4JSProject getN4JSProject(URI location) {
		checkArgument(location.isFile(), "Expecting file URI. Was: " + location);
		boolean external = isExternal(location);
		if (external) {
			return getN4JSProject(externalLibraryWorkspace.fromURI(location), external);
		}
		return getN4JSProject(workspace.fromURI(location), external);
	}

	public SafeURI<?> toProjectLocation(URI location) {
		boolean external = isExternal(location);
		if (external) {
			return externalLibraryWorkspace.fromURI(location);
		}
		return workspace.fromURI(location);
	}

	public N4JSProject getN4JSProject(SafeURI<?> location) {
		return getN4JSProject(location, isExternal(location.toURI()));
	}

	/**
	 * @param location
	 * @return
	 */
	protected boolean isExternal(URI location) {
		if (externalLibraryWorkspace != null && location.isFile()) {
			return externalLibraryWorkspace.getProject(new FileURI(location)) != null;
		}
		return false;
	}

	protected N4JSProject getN4JSProject(SafeURI<?> location, boolean external) {
		return new N4JSProject(location, external, this);
	}

	public Set<? extends IN4JSProject> getAllProjects() {
		Set<IN4JSProject> prjConfs = new LinkedHashSet<>();
		for (Loc prjLoc : workspace.getAllProjectLocations()) {
			prjConfs.add(getN4JSProject(prjLoc, false));
		}
		return prjConfs;
	}

	public IN4JSProject findProject(N4JSProjectName name) {
		Loc loc = workspace.getProjectLocation(name);
		if (loc != null) {
			return getN4JSProject(loc, false);
		}
		return null;
	}

	public IN4JSProject findProjectWith(URI nestedLocation) {
		// FIXME: mm
		SafeURI<?> location = fromURI(workspace, nestedLocation);
		if (location != null) {
			return getN4JSProject(location, false);
		}
		SafeURI<?> externalLocation = fromURI(externalLibraryWorkspace, nestedLocation);
		if (null != externalLocation) {
			return getN4JSProject(externalLocation, true);
		}
		return null;
	}

	protected <PL extends SafeURI<PL>> PL fromURI(InternalN4JSWorkspace<PL> ws, URI uri) {
		PL safeURI = ws.fromURI(uri);
		if (safeURI == null) {
			return null;
		}
		return ws.findProjectWith(safeURI);
	}

	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation) {
		Optional<? extends IN4JSSourceContainer> foundN4JSSourceContainer = Optional.absent();

		IN4JSProject project = findProjectWith(nestedLocation);
		foundN4JSSourceContainer = findN4JSSourceContainerInProject(project, nestedLocation);

		return foundN4JSSourceContainer;
	}

	protected Optional<? extends IN4JSSourceContainer> findN4JSSourceContainerInProject(IN4JSProject project,
			URI nestedLocation) {
		IN4JSSourceContainer matchingContainer = null;
		int matchingSegmentCount = -1;
		if (project != null) {
			for (IN4JSSourceContainer n4jsSourceContainer : project.getSourceContainers()) {
				if (isLocationInNestedInContainer(nestedLocation, n4jsSourceContainer)) {
					int segmentCount = n4jsSourceContainer.getLocation().toURI().segmentCount();
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
		URI containerLocation = container.getLocation().toURI();
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

	protected InternalN4JSWorkspace<Loc> getInternalWorkspace() {
		return workspace;
	}

	/**
	 * This delegates to {@link InternalN4JSWorkspace#getProjectDescription(URI)} to allow caching.
	 */
	public ProjectDescription getProjectDescription(SafeURI<?> location) {
		if (location instanceof FileURI) {
			ProjectDescription result = externalLibraryWorkspace.getProjectDescription((FileURI) location);
			if (result != null) {
				return result;
			}
		}
		@SuppressWarnings("unchecked")
		Loc casted = (Loc) location;
		return workspace.getProjectDescription(casted);
	}

	public ProjectDescription getProjectDescription(IN4JSProject project) {
		SafeURI<?> location = project.getLocation();
		if (!(location instanceof FileURI)) {
			throw new IllegalArgumentException(
					"Unexpected location of external project " + project.getProjectName() + " at " + location);
		}
		if (project.isExternal()) {
			return externalLibraryWorkspace.getProjectDescription((FileURI) location);
		}
		@SuppressWarnings("unchecked")
		Loc casted = (Loc) location;
		return workspace.getProjectDescription(casted);
	}

	public ImmutableList<? extends IN4JSSourceContainer> getN4JSSourceContainers(N4JSProject project) {
		ImmutableList.Builder<IN4JSSourceContainer> result = ImmutableList.builder();
		ProjectDescription description = getProjectDescription(project);
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

	protected IN4JSSourceContainer createProjectN4JSSourceContainer(N4JSProject project, SourceContainerType type,
			String relativeLocation) {
		return new N4JSProjectSourceContainer(project, type, relativeLocation);
	}

	public ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project,
			boolean includeAbsentProjects) {
		return getDependencies(project, false, includeAbsentProjects);
	}

	public ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis(N4JSProject project,
			boolean includeAbsentProjects) {
		return getDependencies(project, true, includeAbsentProjects);
	}

	private ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project, boolean includeApis,
			boolean includeAbsentProjects) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		ProjectDescription description = getProjectDescription(project);
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
		ProjectDescription description = getProjectDescription(project);
		if (null == description) {
			return absent();
		}
		final ProjectReference ref = description.getExtendedRuntimeEnvironment();
		return resolveProjectReference(project, ref, false);
	}

	public ImmutableList<? extends IN4JSProject> getImplementedProjects(N4JSProject project) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		ProjectDescription description = getProjectDescription(project);
		if (description != null) {
			result.addAll(resolveProjectReferences(project, description.getImplementedProjects(), false));
		}
		return result.build();
	}

	public ImmutableList<? extends IN4JSProject> getProvidedRuntimeLibraries(N4JSProject project) {
		ImmutableList.Builder<IN4JSProject> providedRuntimes = ImmutableList.builder();
		List<ProjectReference> runtimeLibraries = getAllProvidedRuntimeLibraries(project);
		for (ProjectReference runtimeLibrary : runtimeLibraries) {
			Loc location = workspace.getLocation(runtimeLibrary);
			if (location != null) {
				providedRuntimes.add(getN4JSProject(location, false));
			} else {
				SafeURI<?> external = externalLibraryWorkspace.getLocation(runtimeLibrary);
				if (external != null) {
					providedRuntimes.add(getN4JSProject(external, true));
				}
			}
		}

		return providedRuntimes.build();
	}

	private EList<ProjectReference> getAllProvidedRuntimeLibraries(N4JSProject project) {
		ProjectDescription description = getProjectDescription(project);
		if (description == null)
			return ECollections.emptyEList();

		EList<ProjectReference> runtimeLibraries = description.getProvidedRuntimeLibraries();
		if (runtimeLibraries == null)
			return ECollections.emptyEList();

		return runtimeLibraries;
	}

	/**
	 * @see IN4JSSourceContainer#findArtifact(QualifiedName, Optional)
	 */
	public SafeURI<?> findArtifact(IN4JSSourceContainer sourceContainer, QualifiedName name,
			Optional<String> fileExtension) {
		final String ext = fileExtension.or("").trim();
		final String extWithDot = !ext.isEmpty() && !ext.startsWith(".") ? "." + ext : ext;
		final String pathStr = name.toString("/") + extWithDot; // no need for IQualifiedNameConverter here!

		SafeURI<?> artifactLocation = findArtifactInFolder(workspace, sourceContainer.getLocation().toURI(),
				pathStr);
		if (null == artifactLocation) {
			artifactLocation = findArtifactInFolder(externalLibraryWorkspace, sourceContainer.getLocation().toURI(),
					pathStr);
		}
		return artifactLocation;
	}

	private <PL extends SafeURI<PL>> PL findArtifactInFolder(InternalN4JSWorkspace<PL> ws,
			URI location, String pathStr) {
		PL safeURI = ws.fromURI(location);
		if (safeURI == null) {
			return null;
		}
		return ws.findArtifactInFolder(safeURI, pathStr);
	}

	public Optional<String> getExtendedRuntimeEnvironmentName(N4JSProject project) {
		final ProjectDescription description = getProjectDescription(project);
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
		final ProjectDescription description = getProjectDescription(project);
		if (null != description) {
			for (ProjectReference testedProject : description.getTestedProjects()) {
				SafeURI<?> hostLocation = workspace.getLocation(testedProject);
				IN4JSProject tested = null;
				if (hostLocation != null) {
					tested = getN4JSProject(hostLocation, false);
				} else {
					hostLocation = externalLibraryWorkspace.getLocation(testedProject);
					if (hostLocation != null) {
						tested = getN4JSProject(hostLocation, true);
					}
				}
				if (null != tested && tested.exists()) {
					builder.add(tested);
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
		SafeURI<?> dependencyLocation = workspace.getLocation(reference);
		if (null != dependencyLocation) {
			return fromNullable(getN4JSProject(dependencyLocation));
		}

		dependencyLocation = externalLibraryWorkspace.getLocation(reference);
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
		// create a file uri of the shape file:/projectName - authority will be added by fromURI below
		final URI absent = URI.createFileURI(projectName);
		return getN4JSProject(workspace.fromURI(absent), false);
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
		final ProjectDescription projectDescription = getProjectDescription(project);
		if (null != projectDescription) {
			return projectDescription.getDefinesPackage();
		}
		return null;
	}

	/**
	 * @see SortedDependenciesProvider
	 */
	public Iterable<IN4JSProject> getSortedDependencies(IN4JSProject project) {
		SortedDependenciesProvider sdProvider = new SortedDependenciesProvider(project);
		Iterable<IN4JSProject> existing = cache.get(sdProvider, MultiCleartriggerCache.CACHE_KEY_SORTED_DEPENDENCIES,
				project.getLocation().toURI());
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
					URI uri = dep.getLocation().toURI();
					triggerURIs.add(uri);
				}
			}
			return triggerURIs;
		}
	}

}
