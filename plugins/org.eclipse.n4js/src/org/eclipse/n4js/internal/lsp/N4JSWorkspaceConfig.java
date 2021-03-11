/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal.lsp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.util.UriExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Accessing workspace information on disk (e.g. existing projects).
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceConfig implements XIWorkspaceConfig {

	protected final URI baseDirectory;
	protected final ProjectDiscoveryHelper projectDiscoveryHelper;
	protected final ProjectDescriptionLoader projectDescriptionLoader;
	protected final ConfigSnapshotFactory configSnapshotFactory;
	protected final UriExtensions uriExtensions;

	protected final Set<N4JSProjectConfig> projects = new LinkedHashSet<>();
	protected final Map<String, N4JSProjectConfig> projectsByName = new HashMap<>();
	protected final Map<FileURI, N4JSProjectConfig> projectsByURI = new HashMap<>();

	/**
	 * Creates a new, empty {@link N4JSWorkspaceConfig}. It will not contain any projects until
	 * {@link #scanDiskForProjects()} is invoked for the first time.
	 */
	public N4JSWorkspaceConfig(URI baseDirectory, ProjectDiscoveryHelper projectDiscoveryHelper,
			ProjectDescriptionLoader projectDescriptionLoader, ConfigSnapshotFactory configSnapshotFactory,
			UriExtensions uriExtensions) {

		this.baseDirectory = baseDirectory;
		this.projectDiscoveryHelper = projectDiscoveryHelper;
		this.projectDescriptionLoader = projectDescriptionLoader;
		this.configSnapshotFactory = configSnapshotFactory;
		this.uriExtensions = uriExtensions;
	}

	protected FileURI createFileURI(URI uri) {
		return new FileURI(uriExtensions.withEmptyAuthority(uri));
	}

	protected void deregisterAllProjects() {
		projects.clear();
		projectsByName.clear();
		projectsByURI.clear();
	}

	/**
	 * Registers the project at the given path with the given project description. Does nothing if a project for that
	 * path or name was already registered*.
	 * <p>
	 * * this behavior means that projects registered first will shadow all projects registered later; together with the
	 * fact that the {@link ProjectDiscoveryHelper} will return dependencies after workspace projects, this leads to
	 * workspace projects shadowing projects of same name in the {@code node_modules} folder.
	 */
	// TODO GH-1314 reconsider shadowing of projects with same name
	protected void registerProject(FileURI path, ProjectDescription pd) {
		if (projectsByURI.containsKey(path)) {
			return;
		}
		String name = pd.getProjectName();
		if (projectsByName.containsKey(name)) {
			return; // see note on shadowing in API doc of this method!
		}
		N4JSProjectConfig newProject = createProjectConfig(path, pd);
		projects.add(newProject);
		projectsByName.put(newProject.getName(), newProject);
		projectsByURI.put(path, newProject);
	}

	/** Creates an instance of {@link N4JSProjectConfig} without registering it. */
	protected N4JSProjectConfig createProjectConfig(FileURI path, ProjectDescription pd) {
		return new N4JSProjectConfig(this, path, pd, projectDescriptionLoader);
	}

	protected void scanDiskForProjects() {
		deregisterAllProjects();
		Path baseDir = getPathAsFileURI().toPath();
		Map<Path, ProjectDescription> pdCache = new HashMap<>();
		List<Path> newProjectPaths = projectDiscoveryHelper.collectAllProjectDirs(Collections.singleton(baseDir),
				pdCache, true /* force loading of all project descriptions */);
		for (Path newProjectPath : newProjectPaths) {
			FileURI newProjectPathAsFileURI = new FileURI(newProjectPath);
			ProjectDescription pd = pdCache.get(newProjectPath); // should not be null (we forced loading above)
			registerProject(newProjectPathAsFileURI, pd);
		}
	}

	@Override
	public URI getPath() {
		return baseDirectory;
	}

	public FileURI getPathAsFileURI() {
		return createFileURI(baseDirectory);
	}

	@Override
	public Set<? extends N4JSProjectConfig> getProjects() {
		return projects;
	}

	@Override
	public N4JSProjectConfig findProjectByName(String name) {
		return projectsByName.get(name);
	}

	/**
	 * No longer supported; will throw {@code UnsupportedOperationException}. See
	 * {@link XIWorkspaceConfig#findProjectContaining(URI)} for details on deprecation.
	 */
	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public N4JSProjectConfig findProjectContaining(URI nestedURI) {
		throw new UnsupportedOperationException(
				"obtaining an N4JSProjectConfig by nested URI is not supported (see API documentation for details)");
	}

	@Override
	public WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, Set<URI> dirtyFiles,
			Set<URI> deletedFiles, boolean refresh) {

		WorkspaceChanges changes = refresh
				? doCompleteUpdate(oldWorkspaceConfig)
				: doMinimalUpdate(oldWorkspaceConfig, dirtyFiles, deletedFiles);

		changes = recomputeSortedDependenciesIfNecessary(oldWorkspaceConfig, changes);

		return changes;
	}

	/** Always performs a complete re-computation of the workspace configuration. */
	private WorkspaceChanges doCompleteUpdate(WorkspaceConfigSnapshot oldWorkspaceConfig) {
		return detectAddedRemovedProjects(oldWorkspaceConfig, true);
	}

	/** Based on the given dirty/deleted files, performs a workspace update with as few computations as possible. */
	private WorkspaceChanges doMinimalUpdate(WorkspaceConfigSnapshot oldWorkspaceConfig, Set<URI> dirtyFiles,
			Set<URI> deletedFiles) {

		WorkspaceChanges changes = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);

		boolean needToDetectAddedRemovedProjects = false;
		for (URI changedResource : Iterables.concat(dirtyFiles, deletedFiles)) {
			String lastSegment = changedResource.lastSegment();
			boolean isPackageJson = lastSegment != null && lastSegment.equals(N4JSGlobals.PACKAGE_JSON);
			if (!isPackageJson) {
				continue;
			}

			ProjectConfigSnapshot oldProject = oldWorkspaceConfig.findProjectContaining(changedResource);
			XIProjectConfig project = oldProject != null ? findProjectByName(oldProject.getName()) : null;
			if (oldProject != null && project != null) {
				// an existing project was modified (maybe removed)
				changes = changes.merge(((N4JSProjectConfig) project).update(oldWorkspaceConfig, changedResource,
						configSnapshotFactory));

				if (((N4JSProjectConfig) project).isWorkspacesProject()) {
					needToDetectAddedRemovedProjects = true;
				}
			} else {
				// a new project was created
				needToDetectAddedRemovedProjects = true;
			}
		}

		// because the ProjectDiscoveryHelper discovers a plainJS-project P only if there is an N4JS project with a
		// dependency to P, we need to re-discover projects whenever dependencies of N4JS projects change:
		needToDetectAddedRemovedProjects |= didDependenciesOfAnyN4JSProjectChange(changes, oldWorkspaceConfig);

		if (needToDetectAddedRemovedProjects) {
			changes = changes.merge(detectAddedRemovedProjects(oldWorkspaceConfig, false));
		}

		return changes;
	}

	private WorkspaceChanges detectAddedRemovedProjects(WorkspaceConfigSnapshot oldWorkspaceConfig,
			boolean alsoDetectChangedProjects) {

		// update all projects
		scanDiskForProjects();

		// detect project additions, removals (and also changes, iff 'alsoDetectChangedProjects' is set)
		Map<URI, ProjectConfigSnapshot> oldProjectsMap = IterableExtensions.toMap(oldWorkspaceConfig.getProjects(),
				ProjectConfigSnapshot::getPath);
		Map<URI, N4JSProjectConfig> newProjectsMap = IterableExtensions.toMap(getProjects(), XIProjectConfig::getPath);
		WorkspaceChanges changes = new WorkspaceChanges();
		for (URI uri : Sets.union(oldProjectsMap.keySet(), newProjectsMap.keySet())) {
			boolean isOld = oldProjectsMap.containsKey(uri);
			boolean isNew = newProjectsMap.containsKey(uri);
			if (isOld && !isNew) {
				changes = changes.merge(WorkspaceChanges.createProjectRemoved(oldProjectsMap.get(uri)));
			} else if (!isOld && isNew) {
				ProjectConfigSnapshot newPC = configSnapshotFactory
						.createProjectConfigSnapshot(newProjectsMap.get(uri));
				changes = changes.merge(WorkspaceChanges.createProjectAdded(newPC));
			} else if (isOld && isNew) {
				if (alsoDetectChangedProjects) {
					ProjectConfigSnapshot oldPC = oldProjectsMap.get(uri);
					ProjectConfigSnapshot newPC = configSnapshotFactory
							.createProjectConfigSnapshot(newProjectsMap.get(uri));

					changes = changes.merge(N4JSProjectConfig.computeChanges(oldPC, newPC));
				}
			}
		}
		return changes;
	}

	/**
	 * The list of {@link N4JSProjectConfig#getSortedDependencies() sorted dependencies} of {@link N4JSProjectConfig}s
	 * is tricky for two reasons:
	 * <ol>
	 * <li>the sorted dependencies do not contain names of non-existing projects (in case of unresolved project
	 * references in the package.json),
	 * <li>the order of the sorted dependencies depends on the characteristics of the target projects (mainly the
	 * {@link ProjectDescription#getDefinesPackage() "defines package"} property).
	 * </ol>
	 * Therefore, the "sorted dependencies" can change even without a change in the <code>package.json</code> file of
	 * the source project. To detect and apply these changes is the purpose of this method.
	 * <p>
	 * TODO: sorted dependencies should not be a property of IN4JSProject/N4JSProjectConfig/N4JSProjectConfigSnapshot
	 * (probably the scoping has to be adjusted, because the sorted dependencies ensure correct shadowing between
	 * definition and defined projects)
	 */
	protected WorkspaceChanges recomputeSortedDependenciesIfNecessary(WorkspaceConfigSnapshot oldWorkspaceConfig,
			WorkspaceChanges changes) {

		boolean needRecompute = !changes.getAddedProjects().isEmpty() || !changes.getRemovedProjects().isEmpty()
				|| Iterables.any(changes.getChangedProjects(), pc -> didDefinesPackageChange(pc, oldWorkspaceConfig));

		if (needRecompute) {
			// FIXME GH-2073 at this point a recomputation of sorted deps was triggered!! (by way of cache clear)
			List<ProjectConfigSnapshot> projectsWithChangedSortedDeps = new ArrayList<>();
			for (XIProjectConfig pc : getProjects()) {
				String projectName = pc.getName();
				ProjectConfigSnapshot oldSnapshot = oldWorkspaceConfig.findProjectByName(projectName);
				if (oldSnapshot == null) {
					continue;
				}
				List<String> oldSortedDeps = ((N4JSProjectConfigSnapshot) oldSnapshot).getSortedDependencies();
				List<String> newSortedDeps = ((N4JSProjectConfig) pc).getSortedDependencies().stream()
						.map(N4JSProjectConfig::getName)
						.collect(Collectors.toList());
				if (!newSortedDeps.equals(oldSortedDeps)) {
					ProjectConfigSnapshot newSnapshot = configSnapshotFactory.createProjectConfigSnapshot(pc);
					if (!newSnapshot.equals(oldSnapshot)) {
						projectsWithChangedSortedDeps.add(newSnapshot);
					}
				}
			}
			if (!projectsWithChangedSortedDeps.isEmpty()) {
				changes = changes.merge(WorkspaceChanges.createProjectsChanged(projectsWithChangedSortedDeps));
			}
		}
		return changes;
	}

	/** Tells whether the dependencies of any N4JS project changed w.r.t. the given old workspace config. */
	private static boolean didDependenciesOfAnyN4JSProjectChange(WorkspaceChanges changes,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {
		return !allExceptPlainjs(changes.getAddedProjects()).isEmpty()
				|| !allExceptPlainjs(changes.getRemovedProjects()).isEmpty()
				|| allExceptPlainjs(changes.getChangedProjects())
						.anyMatch(pc -> didDependenciesChange(pc, oldWorkspaceConfig));
	}

	/** Tells whether the dependencies of the given project changed w.r.t. the given old workspace config. */
	private static boolean didDependenciesChange(ProjectConfigSnapshot projectConfig,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {
		ProjectConfigSnapshot oldProjectConfig = oldWorkspaceConfig.findProjectByName(projectConfig.getName());
		return oldProjectConfig == null || !Objects.equals(
				((N4JSProjectConfigSnapshot) projectConfig).getDependencies(),
				((N4JSProjectConfigSnapshot) oldProjectConfig).getDependencies());
	}

	/** Tells whether the property {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} changed. */
	private static boolean didDefinesPackageChange(ProjectConfigSnapshot projectConfig,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {
		ProjectConfigSnapshot oldProjectConfig = oldWorkspaceConfig.findProjectByName(projectConfig.getName());
		return oldProjectConfig == null || !Objects.equals(
				((N4JSProjectConfigSnapshot) projectConfig).getDefinesPackage(),
				((N4JSProjectConfigSnapshot) oldProjectConfig).getDefinesPackage());
	}

	private static FluentIterable<? extends ProjectConfigSnapshot> allExceptPlainjs(
			Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		return FluentIterable.from(projectConfigs)
				.filter(pc -> ((N4JSProjectConfigSnapshot) pc).getType() != ProjectType.PLAINJS);
	}
}
