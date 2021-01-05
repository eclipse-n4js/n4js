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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Wrapper around {@link IN4JSCore}.
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceConfig implements XIWorkspaceConfig {

	private final URI baseDirectory;
	private final IN4JSCore delegate;
	private final MultiCleartriggerCache multiCleartriggerCache;
	private final ConfigSnapshotFactory configSnapshotFactory;

	/** Constructor */
	public N4JSWorkspaceConfig(URI baseDirectory, IN4JSCore delegate, MultiCleartriggerCache multiCleartriggerCache,
			ConfigSnapshotFactory configSnapshotFactory) {

		this.baseDirectory = baseDirectory;
		this.delegate = delegate;
		this.multiCleartriggerCache = multiCleartriggerCache;
		this.configSnapshotFactory = configSnapshotFactory;
	}

	@Override
	public XIProjectConfig findProjectByName(String name) {
		IN4JSProject project = delegate.findProject(new N4JSProjectName(name)).orNull();
		if (project != null) {
			return new N4JSProjectConfig(this, project);
		}
		return null;
	}

	@Override
	public XIProjectConfig findProjectContaining(URI member) {
		IN4JSProject project = delegate.findProject(member).orNull();
		if (project != null) {
			return new N4JSProjectConfig(this, project);
		}
		return null;
	}

	@Override
	public Set<? extends XIProjectConfig> getProjects() {
		Set<XIProjectConfig> pConfigs = new LinkedHashSet<>();
		for (IN4JSProject project : delegate.findAllProjects()) {
			pConfigs.add(new N4JSProjectConfig(this, project));
		}
		return pConfigs;
	}

	@Override
	public URI getPath() {
		return baseDirectory;
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
		((N4JSRuntimeCore) delegate).deregisterAll();

		ProjectDiscoveryHelper projectDiscoveryHelper = ((N4JSRuntimeCore) delegate).getProjectDiscoveryHelper();
		Path baseDir = new FileURI(getPath()).toFile().toPath();
		LinkedHashSet<Path> newProjectPaths = projectDiscoveryHelper.collectAllProjectDirs(baseDir);
		for (Path newProjectPath : newProjectPaths) {
			((N4JSRuntimeCore) delegate).registerProject(newProjectPath.toFile());
		}

		// detect project additions, removals (and also changes, iff 'alsoDetectChangedProjects' is set)
		Map<URI, ProjectConfigSnapshot> oldProjectsMap = IterableExtensions.toMap(oldWorkspaceConfig.getProjects(),
				ProjectConfigSnapshot::getPath);
		Map<URI, XIProjectConfig> newProjectsMap = IterableExtensions.toMap(getProjects(), XIProjectConfig::getPath);
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
	 * The list of {@link IN4JSProject#getSortedDependencies() sorted dependencies} of {@link IN4JSProject}s is tricky
	 * for two reasons:
	 * <ol>
	 * <li>the sorted dependencies do not contain names of non-existing projects (in case of unresolved project
	 * references in the package.json),
	 * <li>the order of the sorted dependencies depends on the characteristics of the target projects (mainly the
	 * {@link IN4JSProject#getDefinesPackageName() "defines package"} property).
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
			multiCleartriggerCache.clear(MultiCleartriggerCache.CACHE_KEY_SORTED_DEPENDENCIES);
			List<ProjectConfigSnapshot> projectsWithChangedSortedDeps = new ArrayList<>();
			for (XIProjectConfig pc : getProjects()) {
				String projectName = pc.getName();
				ProjectConfigSnapshot oldSnapshot = oldWorkspaceConfig.findProjectByName(projectName);
				if (oldSnapshot == null) {
					continue;
				}
				List<String> oldSortedDeps = ((N4JSProjectConfigSnapshot) oldSnapshot).getSortedDependencies();
				List<String> newSortedDeps = ((N4JSProjectConfig) pc).toProject().getSortedDependencies().stream()
						.map(IN4JSProject::getProjectName)
						.map(N4JSProjectName::getRawName)
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
