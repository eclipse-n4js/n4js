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
package org.eclipse.n4js.ui.external;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.addAll;
import static com.google.common.collect.Iterators.emptyIterator;
import static com.google.common.collect.Iterators.unmodifiableIterator;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newTreeMap;
import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.SubMonitor.convert;
import static org.eclipse.n4js.internal.N4JSSourceContainerType.PROJECT;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProjectCacheLoader;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.NpmProjectAdaptionResult;
import org.eclipse.n4js.external.RebuildWorkspaceProjectsScheduler;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.ExternalProjectLocationsProvider;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore.StoreUpdatedListener;
import org.eclipse.n4js.utils.Procedure;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.util.Pair;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Eclipse based implementation of the external library workspace. This class assumes a running {@link Platform
 * platform}.
 */
@Singleton
public class EclipseExternalLibraryWorkspace extends ExternalLibraryWorkspace implements StoreUpdatedListener {

	private static final Logger LOGGER = Logger.getLogger(EclipseExternalLibraryWorkspace.class);

	@Inject
	private ExternalProjectCacheLoader cacheLoader;

	@Inject
	private ProjectStateChangeListener projectStateChangeListener;

	@Inject
	private ExternalLibraryBuilderHelper builderHelper;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private RebuildWorkspaceProjectsScheduler scheduler;

	private LoadingCache<URI, Optional<Pair<ExternalProject, ProjectDescription>>> projectCache;

	private Map<String, ExternalProject> projectMapping;

	private final Collection<java.net.URI> locations;

	/**
	 * Creates a new external library workspace instance with the preference store that provides the configured library
	 * location.
	 *
	 * @param preferenceStore
	 *            the preference store to get the registered external library locations.
	 */
	@Inject
	public EclipseExternalLibraryWorkspace(final ExternalLibraryPreferenceStore preferenceStore) {
		locations = newHashSet(preferenceStore.getLocations());
		preferenceStore.addListener(this);
	}

	/**
	 * Initializes the backing cache with the cache loader and registers a {@link ProjectStateChangeListener} into the
	 * workspace.
	 */
	@Inject
	public void init() {
		projectCache = CacheBuilder.newBuilder().build(cacheLoader);
		if (Platform.isRunning()) {
			getWorkspace().addResourceChangeListener(projectStateChangeListener);
		}
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		ensureInitialized();
		final Pair<ExternalProject, ProjectDescription> pair = get(location);
		return null == pair ? null : pair.getSecond();
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference reference,
			N4JSSourceContainerType expectedN4JSSourceContainerType) {

		ensureInitialized();
		if (PROJECT.equals(expectedN4JSSourceContainerType)) {

			final String name = reference.getProject().getProjectId();
			final ExternalProject project = getProjectMapping().get(name);

			if (null == project) {
				return null;
			}

			final File referencedProject = new File(project.getLocationURI());
			final URI referencedLocation = URI.createFileURI(referencedProject.getAbsolutePath());
			final Pair<ExternalProject, ProjectDescription> pair = get(referencedLocation);
			if (null != pair) {
				return referencedLocation;
			}

		}

		return null;
	}

	@Override
	public Iterator<URI> getArchiveIterator(URI archiveLocation, String archiveRelativeLocation) {
		ensureInitialized();
		return emptyIterator();
	}

	@Override
	public Iterator<URI> getFolderIterator(URI folderLocation) {
		ensureInitialized();
		final URI findProjectWith = findProjectWith(folderLocation);
		if (null != findProjectWith) {
			final String projectName = findProjectWith.lastSegment();
			final ExternalProject project = getProjectMapping().get(projectName);
			if (null != project) {
				String projectPath = new File(project.getLocationURI()).getAbsolutePath();
				String folderPath = folderLocation.toFileString();
				final IContainer container = projectPath.equals(folderPath) ? project
						: project.getFolder(folderPath.substring(projectPath.length() + 1));
				final Collection<URI> result = Lists.newLinkedList();
				try {
					container.accept(resource -> {
						if (resource instanceof IFile) {
							final String path = new File(resource.getLocationURI()).getAbsolutePath();
							result.add(URI.createFileURI(path));
						}
						return true;
					});
					return unmodifiableIterator(result.iterator());
				} catch (CoreException e) {
					return unmodifiableIterator(result.iterator());
				}
			}
		}

		return emptyIterator();
	}

	@Override
	public URI findArtifactInFolder(URI folderLocation, String folderRelativePath) {
		ensureInitialized();
		final IResource folder = getResource(folderLocation);
		if (folder instanceof IFolder) {
			final IFile file = ((IFolder) folder).getFile(folderRelativePath);
			if (file instanceof IExternalResource) {
				final File externalResource = ((IExternalResource) file).getExternalResource();
				return URI.createFileURI(externalResource.getAbsolutePath());
			}
		}

		return null;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		final String path = nestedLocation.toFileString();
		if (null == path) {
			return null;
		}

		final File nestedResource = new File(path);
		if (!nestedResource.exists()) {
			return null;
		}

		final Path nestedResourcePath = nestedResource.toPath();

		final Iterable<URI> registeredProjectUris = projectCache.asMap().keySet();
		for (final URI projectUri : registeredProjectUris) {
			if (projectUri.isFile()) {
				final File projectRoot = new File(projectUri.toFileString());
				final Path projectRootPath = projectRoot.toPath();
				if (nestedResourcePath.startsWith(projectRootPath)) {
					return projectUri;
				}
			}
		}

		return null;
	}

	@Override
	public void storeUpdated(final ExternalLibraryPreferenceStore store, final IProgressMonitor monitor) {
		final ISchedulingRule rule = builderHelper.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			storeUpdatedInternal(store, monitor);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private void storeUpdatedInternal(final ExternalLibraryPreferenceStore store, final IProgressMonitor monitor) {
		ensureInitialized();
		final Set<java.net.URI> oldLocations = newLinkedHashSet(locations);
		final Set<java.net.URI> newLocation = newLinkedHashSet(store.getLocations());
		final Collection<java.net.URI> removedLocations = difference(oldLocations, newLocation);
		final Collection<java.net.URI> addedLocations = difference(newLocation, oldLocations);

		final SubMonitor subMonitor = convert(monitor, 3);

		final Iterable<IProject> projectsToClean = getProjects(removedLocations);

		// Clean projects.
		if (!Iterables.isEmpty(projectsToClean)) {
			builderHelper.clean(projectsToClean, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		// Invalidate before collecting dependencies.
		invalidateCache(store);

		final Collection<IProject> workspaceProjectsToRebuild = newHashSet(
				collector.collectProjectsWithDirectExternalDependencies(projectsToClean));

		// Cache could be polluted with external projects while collecting associated workspace ones.
		invalidateCache(store);

		// Rebuild whole external workspace. Filter out projects that are present in the Eclipse workspace (if any).
		final Collection<String> eclipseWorkspaceProjectNames = getAllEclipseWorkspaceProjectNames();
		final Predicate<String> eclipseWorkspaceProjectNamesFilter = Predicates.in(eclipseWorkspaceProjectNames);

		final Iterable<ExternalProject> projectsToBuild = from(
				collector.hookUpReferencedBuildConfigs(getProjects(addedLocations)))
						.filter(p -> !eclipseWorkspaceProjectNamesFilter.apply(p.getName()));

		// Build recently added projects that do not exist in workspace.
		// XXX akitta: consider filtering out external projects that exists in index already. (@ higher priority level)
		if (!Iterables.isEmpty(projectsToBuild)) {
			builderHelper.build(projectsToBuild, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		addAll(workspaceProjectsToRebuild, collector.collectProjectsWithDirectExternalDependencies(projectsToBuild));
		scheduler.scheduleBuildIfNecessary(workspaceProjectsToRebuild);
	}

	private void invalidateCache(final ExternalLibraryPreferenceStore store) {
		locations.clear();
		locations.addAll(store.getLocations());
		updateState();
	}

	@Override
	public void registerProjects(NpmProjectAdaptionResult result, IProgressMonitor monitor, boolean triggerCleanbuild) {
		final ISchedulingRule rule = builderHelper.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			registerProjectsInternal(result, monitor, triggerCleanbuild);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private void registerProjectsInternal(NpmProjectAdaptionResult result, IProgressMonitor monitor,
			boolean triggerCleanbuild) {

		checkState(result.isOK(), "Expected OK result: " + result);
		ensureInitialized();

		if (!ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			LOGGER.warn("Built-in libraries and NPM support are disabled.");
		}

		final SubMonitor subMonitor = convert(monitor, 3);

		final Iterable<IProject> projectsToClean = from(result.getToBeBuilt().getToBeDeleted())
				.transform(uri -> getProject(new File(uri).getName())).filter(notNull());

		final Set<IProject> workspaceProjectsToRebuild = newHashSet(
				collector.collectProjectsWithDirectExternalDependencies(projectsToClean));

		// Clean projects.
		if (!Iterables.isEmpty(projectsToClean)) {
			builderHelper.clean(projectsToClean, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		// Update internal state.
		updateState();

		// Rebuild whole external workspace. Filter out projects that are present in the Eclipse workspace (if any).
		final Collection<String> eclipseWorkspaceProjectNames = getAllEclipseWorkspaceProjectNames();
		final Predicate<String> eclipseWorkspaceProjectNamesFilter = Predicates.in(eclipseWorkspaceProjectNames);

		final Iterable<IProject> projectsToBuild = from(result.getToBeBuilt().getToBeUpdated())
				.transform(uri -> getProject(new File(uri).getName())).filter(notNull())
				.filter(p -> !eclipseWorkspaceProjectNamesFilter.apply(p.getName()));

		// Build recently added projects that do not exist in workspace.
		// Also includes projects that exist already in the index, but are shadowed.
		if (!Iterables.isEmpty(projectsToBuild)) {
			builderHelper.build(projectsToBuild, subMonitor.newChild(1));
		}
		subMonitor.worked(1);

		if (triggerCleanbuild) {
			Iterable<IProject> depPjs = collector.collectProjectsWithDirectExternalDependencies(projectsToBuild);
			addAll(workspaceProjectsToRebuild, depPjs);

			if (getWorkspace().getDescription().isAutoBuilding()) {
				scheduler.scheduleBuildIfNecessary(workspaceProjectsToRebuild);
			}
		}
	}

	@Override
	public Iterable<IProject> getProjects() {
		ensureInitialized();
		final Map<String, ExternalProject> projects = getProjectMapping();
		return unmodifiableCollection(projects.values());
	}

	@Override
	public Iterable<IProject> getProjects(java.net.URI rootLocation) {
		ensureInitialized();
		final File rootFolder = new File(rootLocation);

		final Map<String, IProject> projectsMapping = newTreeMap();
		final URI rootUri = URI.createFileURI(rootFolder.getAbsolutePath());

		for (Entry<URI, Optional<Pair<ExternalProject, ProjectDescription>>> entry : projectCache.asMap().entrySet()) {
			final URI projectLocation = entry.getKey();
			if (rootUri.equals(projectLocation.trimSegments(1))) {
				final Pair<ExternalProject, ProjectDescription> pair = entry.getValue().orNull();
				if (null != pair && null != pair.getFirst()) {
					final ExternalProject project = pair.getFirst();
					projectsMapping.put(project.getName(), project);
				}
			}
		}

		return unmodifiableCollection(projectsMapping.values());
	}

	@Override
	public Iterable<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation) {
		ensureInitialized();
		final File rootFolder = new File(rootLocation);

		final Set<ProjectDescription> projectsMapping = newHashSet();
		final URI rootUri = URI.createFileURI(rootFolder.getAbsolutePath());

		for (Entry<URI, Optional<Pair<ExternalProject, ProjectDescription>>> entry : projectCache.asMap().entrySet()) {
			final URI projectLocation = entry.getKey();
			if (rootUri.equals(projectLocation.trimSegments(1))) {
				final Pair<ExternalProject, ProjectDescription> pair = entry.getValue().orNull();
				if (null != pair && null != pair.getFirst()) {
					final ProjectDescription description = pair.getSecond();
					if (description != null) {
						projectsMapping.add(description);
					}
				}
			}
		}

		return unmodifiableCollection(projectsMapping);
	}

	@Override
	public IProject getProject(final String projectName) {
		ensureInitialized();
		return getProjectMapping().get(projectName);
	}

	@Override
	public IResource getResource(URI location) {
		ensureInitialized();
		final String path = location.toFileString();
		if (null == path) {
			return null;
		}
		final File nestedResource = new File(path);
		if (nestedResource.exists()) {
			final URI projectLocation = findProjectWith(location);
			if (null != projectLocation) {
				final String projectName = projectLocation.lastSegment();
				final IProject project = getProject(projectName);
				if (project instanceof ExternalProject) {
					final File projectResource = new File(project.getLocationURI());
					if (projectResource.exists() && projectResource.isDirectory()) {

						final Path projectPath = projectResource.toPath();
						final Path nestedPath = nestedResource.toPath();

						if (projectPath.equals(nestedPath)) {
							return project;
						}

						// TODO: project.getFile and project.getFolder don't check whether then given path is a file or
						// a folder, and they should not?
						final Path relativePath = projectPath.relativize(nestedPath);
						final IFile file = project.getFile(relativePath.toString());
						if (file.exists())
							return file;

						final IFolder folder = project.getFolder(relativePath.toString());
						if (folder.exists())
							return folder;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Updates the internal state based on the available external project root locations.
	 * <p>
	 * This cannot be done in construction time, because it might happen that N4MF is not initialized yet, hence not
	 * available when injecting this instance.
	 */
	@Override
	public void updateState() {
		projectCache.invalidateAll();
		visitAllExternalProjects(locations, new Procedure<File>() {

			@Override
			public void doApply(File projectRoot) {
				final URI location = URI.createFileURI(projectRoot.getAbsolutePath());
				final Pair<ExternalProject, ProjectDescription> pair = get(location);
				if (null == pair) { // Removed trash.
					projectCache.invalidate(location);
				}
			}
		});
		final Map<String, ExternalProject> projectIdProjectMap = newHashMap();
		final Map<URI, Optional<Pair<ExternalProject, ProjectDescription>>> availableProjects = projectCache.asMap();
		visitAllExternalProjects(locations, new Procedure<File>() {

			@Override
			public void doApply(File input) {
				final URI projectLocation = URI.createFileURI(input.getAbsolutePath());
				if (availableProjects.containsKey(projectLocation)) {
					Pair<ExternalProject, ProjectDescription> pair = availableProjects.get(projectLocation)
							.orNull();
					if (null != pair) {
						final ExternalProject project = pair.getFirst();
						if (!projectIdProjectMap.containsKey(project.getName())) {
							projectIdProjectMap.put(project.getName(), project);
						}
					}
				}
			}
		});
		projectMapping = Collections.unmodifiableMap(projectIdProjectMap);
	}

	private Iterable<IProject> getProjects(final Iterable<java.net.URI> rootLocations) {
		return from(ExternalProjectLocationsProvider.INSTANCE.convertToProjectRootLocations(rootLocations))
				.transform(uri -> URI.createFileURI(new File(uri).getAbsolutePath()))
				.transform(uri -> get(uri))
				.filter(pair -> null != pair)
				.transform(pair -> pair.getFirst())
				.filter(project -> null != project && project.exists())
				.filter(IProject.class);
	}

	private void ensureInitialized() {
		checkNotNull(getProjectMapping(), "Eclipse based external library workspace is not initialized yet.");
	}

	private Map<String, ExternalProject> getProjectMapping() {

		if (null == projectMapping) {
			synchronized (this) {
				if (null == projectMapping) {
					updateState();
				}
			}
		}

		return projectMapping;
	}

	private void visitAllExternalProjects(Iterable<java.net.URI> rootLocations, Procedure<File> procedure) {
		for (java.net.URI projectRoot : ExternalProjectLocationsProvider.INSTANCE
				.convertToProjectRootLocations(rootLocations)) {
			procedure.apply(new File(projectRoot));
		}
	}

	private Pair<ExternalProject, ProjectDescription> get(URI location) {
		try {
			return projectCache.get(location).orNull();
		} catch (ExecutionException e) {
			final String message = "Error while getting external project with description for location: " + location;
			LOGGER.error(message, e);
			return null;
		}

	}

	private Collection<String> getAllEclipseWorkspaceProjectNames() {
		if (Platform.isRunning()) {
			return from(Arrays.asList(getWorkspace().getRoot().getProjects()))
					.filter(p -> p.isAccessible())
					.transform(p -> p.getName())
					.toSet();
		}
		return emptyList();
	}

}
