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
import static org.eclipse.n4js.internal.N4JSSourceContainerType.ARCHIVE;
import static org.eclipse.n4js.internal.N4JSSourceContainerType.PROJECT;
import static org.eclipse.n4js.n4mf.ProjectType.TEST;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.NoopExternalLibraryWorkspace;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency;
import org.eclipse.n4js.n4mf.SimpleProjectDependency;
import org.eclipse.n4js.n4mf.SimpleProjectDescription;
import org.eclipse.n4js.n4mf.SourceFragment;
import org.eclipse.n4js.n4mf.SourceFragmentType;
import org.eclipse.n4js.n4mf.TestedProject;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Objects;
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

	private static final Logger LOGGER = Logger.getLogger(N4JSModel.class);

	/**
	 * Segment count indicating that a resource is directly a sub resource of a particular project. Namely if the
	 * resource has the following URI: platform:/resource/P/someResource.extension
	 *
	 * Then the first segment (at 0 index) will be the project P, and the second segment is the someResource.extension
	 * that is directly contained in the project.
	 *
	 */
	public static final int DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT = 2;

	private final InternalN4JSWorkspace workspace;

	@Inject
	protected ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	protected FileBasedExternalPackageManager packageManager;

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	public N4JSModel(InternalN4JSWorkspace workspace) {
		this.workspace = workspace;
	}

	public N4JSModel(InternalN4JSWorkspace workspace, TargetPlatformInstallLocationProvider installLocationProvider) {
		this.workspace = workspace;
		this.installLocationProvider = installLocationProvider;
	}

	public N4JSProject getN4JSProject(URI location) {
		checkArgument(location.isFile(), "Expecting file URI. Was: " + location);
		boolean external = false;
		if (null != installLocationProvider.getTargetPlatformInstallLocation()) {
			Path projectPath = new File(location.toFileString()).toPath();
			Path nodeModulesPath = new File(installLocationProvider.getTargetPlatformNodeModulesLocation()).toPath();
			try {

				final Path projectRoot = projectPath.getRoot();
				final Path nodeModulesRoot = nodeModulesPath.getRoot();

				if (Objects.equal(projectRoot, nodeModulesRoot)) {
					final String relativePath = nodeModulesPath.relativize(projectPath).toString();
					external = location.lastSegment().equals(relativePath);
				}

			} catch (final IllegalArgumentException e) {
				final String message = "Error while trying to relativize paths. Project path was: " + projectPath
						+ " target platform node modules location was: " + nodeModulesPath + ".";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			}
		}
		return new N4JSProject(location, external, this);
	}

	public N4JSProject findProjectWith(URI nestedLocation) {
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

	public Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation) {
		Optional<? extends IN4JSSourceContainer> foundN4JSSourceContainer = Optional.absent();
		if (!nestedLocation.isArchive()) {
			N4JSProject project = findProjectWith(nestedLocation);
			foundN4JSSourceContainer = findN4JSSourceContainerInProject(project, nestedLocation);
		} else {
			String pathToArchive = nestedLocation.authority();
			URI archiveURI = URI.createURI(pathToArchive.substring(0, pathToArchive.length() - 1));
			N4JSProject project = findProjectWith(archiveURI);
			N4JSArchive archive = getN4JSArchive(project, archiveURI);
			foundN4JSSourceContainer = findN4JSSourceContainerInArchive(archiveURI, archive);
		}
		return foundN4JSSourceContainer;
	}

	public Optional<? extends IN4JSSourceContainer> findN4JSExternalSourceContainer(IN4JSProject extPackage,
			URI nestedLocation) {
		return findN4JSSourceContainerInProject(extPackage, nestedLocation);
	}

	protected Optional<? extends IN4JSSourceContainer> findN4JSSourceContainerInProject(IN4JSProject project,
			URI nestedLocation) {
		Optional<? extends IN4JSSourceContainer> foundN4JSSourceContainer = Optional.absent();
		if (project != null) {
			for (IN4JSSourceContainer n4jsSourceContainer : project.getSourceContainers()) {
				if (matchPaths(nestedLocation, n4jsSourceContainer)) {
					return Optional.of(n4jsSourceContainer);
				}
			}
		}
		return foundN4JSSourceContainer;
	}

	private boolean matchPaths(URI nestedLocation, IN4JSSourceContainer container) {
		URI location = container.getLocation();
		return pathStartsWithFolder(nestedLocation, location);
	}

	private boolean pathStartsWithFolder(URI nestedLocation, URI containerLocation) {
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

	protected Optional<? extends IN4JSSourceContainer> findN4JSSourceContainerInArchive(URI location,
			N4JSArchive archive) {
		int maxSegments = location.segmentCount();
		OUTER: for (IN4JSSourceContainer sourceContainer : archive.getSourceContainers()) {
			URI sourceContainerLocation = sourceContainer.getLocation();
			if (sourceContainerLocation.segmentCount() <= maxSegments) {
				for (int i = 0; i < sourceContainerLocation.segmentCount(); i++) {
					if (!sourceContainerLocation.segment(i).equals(location.segment(i))) {
						continue OUTER;
					}
				}
				return Optional.of(sourceContainer);
			}
		}
		return Optional.absent();
	}

	public N4JSArchive getN4JSArchive(N4JSProject project, URI archiveLocation) {
		return new N4JSArchive(project, archiveLocation);
	}

	public ImmutableList<? extends IN4JSArchive> getLibraries(N4JSProject project) {
		URI location = project.getLocation();
		return doGetLibraries(project, location);
	}

	protected InternalN4JSWorkspace getInternalWorkspace() {
		return workspace;
	}

	protected ImmutableList<? extends IN4JSArchive> doGetLibraries(N4JSProject project, URI location) {
		ImmutableList.Builder<IN4JSArchive> result = ImmutableList.builder();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			description.getAllRequiredRuntimeLibraries().forEach(
					lib -> addArchiveFromDependency(project, location, lib, result));
			description.getAllProjectDependencies().forEach(
					lib -> addArchiveFromDependency(project, location, lib, result));
		}
		return result.build();
	}

	private void addArchiveFromDependency(final N4JSProject project, final URI location,
			final SimpleProjectDependency dependency, final ImmutableList.Builder<IN4JSArchive> result) {

		if (null != dependency && null != dependency.getProject()) {
			final URI dependencyLocation = workspace.getLocation(location, dependency,
					N4JSSourceContainerType.ARCHIVE);
			if (dependencyLocation != null) {
				result.add(getN4JSArchive(project, dependencyLocation));
			}
		}
	}

	public ImmutableList<? extends IN4JSArchive> getLibraries(N4JSArchive archive) {
		URI location = archive.getLocation();
		return doGetLibraries(archive.getProject(), location);
	}

	/**
	 * This delegates to {@link InternalN4JSWorkspace#getProjectDescription(URI)} to allow caching.
	 */
	protected ProjectDescription getProjectDescription(URI location) {
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
			List<SourceFragment> sourceFragments = newArrayList(from(description.getSourceFragment()));
			sourceFragments.sort((f1, fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) -> f1
					.compareByFragmentType(fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT));
			for (SourceFragment sourceFragment : sourceFragments) {
				List<String> paths = sourceFragment.getPaths();
				for (String path : paths) {
					// XXX poor man's canonical path conversion. Consider headless compiler with npm projects.
					final String relativeLocation = ".".equals(path) ? "" : path;
					IN4JSSourceContainer sourceContainer = this.createProjectN4JSSourceContainer(project,
							sourceFragment.getSourceFragmentType(), relativeLocation);
					result.add(sourceContainer);
				}
			}
		}
		return result.build();
	}

	protected String getLocationPath(URI location) {
		return location.toFileString();
	}

	protected IN4JSSourceContainer createArchiveN4JSSourceContainer(N4JSArchive archive, SourceFragmentType type,
			String relativeLocation) {
		return new N4JSArchiveSourceContainer(archive, type, relativeLocation);
	}

	protected IN4JSSourceContainer createProjectN4JSSourceContainer(N4JSProject project, SourceFragmentType type,
			String relativeLocation) {
		return new N4JSProjectSourceContainer(project, type, relativeLocation);
	}

	public ImmutableList<IN4JSSourceContainer> getSourceContainers(N4JSArchive archive) {
		ImmutableList.Builder<IN4JSSourceContainer> result = ImmutableList.builder();
		URI location = archive.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			List<SourceFragment> sourceFragments = newArrayList(from(description.getSourceFragment()));
			sourceFragments.sort((f1, fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) -> f1
					.compareByFragmentType(fDIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT));
			for (SourceFragment sourceFragment : sourceFragments) {
				List<String> paths = sourceFragment.getPaths();
				for (String path : paths) {
					result.add(createArchiveN4JSSourceContainer(archive, sourceFragment.getSourceFragmentType(), path));
				}
			}
		}
		return result.build();
	}

	public ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project) {
		return getDependencies(project, false);
	}

	public ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis(N4JSProject project) {
		return getDependencies(project, true);
	}

	private ImmutableList<? extends IN4JSProject> getDependencies(N4JSProject project, boolean includeApis) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			result.addAll(resolveProjectReferences(project, description.getAllRequiredRuntimeLibraries()));
			result.addAll(resolveProjectReferences(project, description.getAllProjectDependencies()));
			result.addAll(getTestedProjects(project));
			if (includeApis) {
				result.addAll(resolveProjectReferences(project, description.getImplementedProjects()));
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
		final ExtendedRuntimeEnvironment re = description.getExtendedRuntimeEnvironment();
		if (null == re) {
			return absent();
		}
		final ProjectReference ref = re.getExtendedRuntimeEnvironment();
		return resolveProjectReference(project, ref);
	}

	public ImmutableList<? extends IN4JSProject> getImplementedProjects(N4JSProject project) {
		ImmutableList.Builder<IN4JSProject> result = ImmutableList.builder();
		URI location = project.getLocation();
		ProjectDescription description = getProjectDescription(location);
		if (description != null) {
			result.addAll(resolveProjectReferences(project, description.getAllImplementedProjects()));
		}
		return result.build();
	}

	public ImmutableList<? extends IN4JSSourceContainerAware> getProvidedRuntimeLibraries(N4JSProject project) {

		ImmutableList.Builder<IN4JSSourceContainerAware> providedRuntimes = ImmutableList.builder();
		EList<ProvidedRuntimeLibraryDependency> runtimeLibraries = getAllProvidedRuntimeLibraries(project);
		URI projectLocation = project.getLocation();

		// GHOLD-249: If the project n4mf file has parse errors, we need a lot of null checks.
		for (ProvidedRuntimeLibraryDependency runtimeLibrary : runtimeLibraries) {
			if (null != runtimeLibrary.getProject()) {
				URI location = workspace.getLocation(projectLocation, runtimeLibrary, PROJECT);
				if (null == location) {
					location = externalLibraryWorkspace.getLocation(projectLocation, runtimeLibrary,
							PROJECT);
				}

				if (null != location) {
					providedRuntimes.add(getN4JSProject(location));
				} else {

					// Assuming archive (NFAR)
					location = workspace.getLocation(projectLocation, runtimeLibrary, ARCHIVE);
					if (null != location) {
						providedRuntimes.add(getN4JSArchive(project, location));
					}
				}
			}
		}

		return providedRuntimes.build();
	}

	private EList<ProvidedRuntimeLibraryDependency> getAllProvidedRuntimeLibraries(N4JSProject project) {
		URI projectLocation = project.getLocation();
		if (projectLocation == null)
			return ECollections.emptyEList();

		ProjectDescription description = getProjectDescription(projectLocation);
		if (description == null)
			return ECollections.emptyEList();

		EList<ProvidedRuntimeLibraryDependency> runtimeLibraries = description.getAllProvidedRuntimeLibraries();
		if (runtimeLibraries == null)
			return ECollections.emptyEList();

		return runtimeLibraries;
	}

	public Iterator<URI> iterator(IN4JSSourceContainer sourceContainer) {
		if (sourceContainer.isLibrary()) {
			return workspace.getArchiveIterator(sourceContainer.getLibrary().getLocation(),
					sourceContainer.getRelativeLocation());
		} else {
			if (sourceContainer.getProject().isExternal() && Platform.isRunning()) {
				// The `Platform.isRunning()` is not valid check for the OSGI headless compiler
				// it may still be valid in some scenarios (maybe some test scenarios)
				if (externalLibraryWorkspace instanceof NoopExternalLibraryWorkspace
						&& workspace instanceof FileBasedWorkspace
						&& workspace.findProjectWith(sourceContainer.getLocation()) != null) {
					return workspace.getFolderIterator(sourceContainer.getLocation());
				}

				return externalLibraryWorkspace.getFolderIterator(sourceContainer.getLocation());
			}
			return workspace.getFolderIterator(sourceContainer.getLocation());
		}
	}

	/**
	 * @see IN4JSSourceContainer#findArtifact(QualifiedName, Optional)
	 */
	public URI findArtifact(IN4JSSourceContainer sourceContainer, QualifiedName name, Optional<String> fileExtension) {
		final String ext = fileExtension.or("").trim();
		final String extWithDot = !ext.isEmpty() && !ext.startsWith(".") ? "." + ext : ext;
		final String pathStr = name.toString("/") + extWithDot; // no need for IQualifiedNameConverter here!
		if (sourceContainer.isLibrary()) {
			return null; // TODO support for finding artifacts in libraries
		} else {
			URI artifactLocation = workspace.findArtifactInFolder(sourceContainer.getLocation(), pathStr);
			if (null == artifactLocation) {
				artifactLocation = externalLibraryWorkspace.findArtifactInFolder(sourceContainer.getLocation(),
						pathStr);
			}
			return artifactLocation;
		}
	}

	public Optional<String> getExtendedRuntimeEnvironmentName(URI location) {
		if (null == location) {
			return absent();
		}
		final ProjectDescription description = getProjectDescription(location);
		if (null == description) {
			return absent();
		}
		final ExtendedRuntimeEnvironment extendedRe = description.getExtendedRuntimeEnvironment();
		if (null == extendedRe) {
			return absent();
		}
		final ProjectReference reRef = extendedRe.getExtendedRuntimeEnvironment();
		if (null == reRef) {
			return absent();
		}
		final SimpleProjectDescription project = reRef.getProject();
		if (null == project) {
			return absent();
		}
		return fromNullable(project.getProjectId());
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
			for (TestedProject testedProject : description.getAllTestedProjects()) {
				if (null != testedProject.getProject()) {
					URI hostLocation = workspace.getLocation(location, testedProject, PROJECT);

					if (null == hostLocation) {
						hostLocation = externalLibraryWorkspace.getLocation(location, testedProject, PROJECT);
					}

					if (hostLocation != null) {
						final N4JSProject tested = getN4JSProject(hostLocation);
						if (null != tested && tested.exists()) {
							builder.add(tested);
						}
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
			final ProjectReference reference) {

		if (null == project || null == reference || null == reference.getProject()) {
			return absent();
		}

		final URI location = project.getLocation();
		if (null == location) {
			return absent();
		}

		URI dependencyLocation = workspace.getLocation(location, reference, PROJECT);
		if (null != dependencyLocation) {
			return fromNullable(getN4JSProject(dependencyLocation));
		}

		dependencyLocation = externalLibraryWorkspace.getLocation(location, reference, PROJECT);
		if (null != dependencyLocation) {
			return fromNullable(getN4JSProject(dependencyLocation));
		}

		return absent();
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
			final Iterable<? extends ProjectReference> references) {

		if (null == project || null == references || isEmpty(references)) {
			return emptyList();
		}
		return from(references).transform(ref -> resolveProjectReference(project, ref)).filter(opt -> opt.isPresent())
				.transform(opt -> opt.get()).toList();
	}

	/**
	 *
	 * @param project
	 * @param implementedProjects
	 * @return
	 */
	private Iterable<? extends IN4JSProject> resolveProjectReferences(N4JSProject project,
			ImplementedProjects implementedProjects) {
		if (implementedProjects == null || implementedProjects.getImplementedProjects() == null) {
			return emptyList();
		}
		return resolveProjectReferences(project, implementedProjects.getImplementedProjects());
	}

}
