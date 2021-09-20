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
package org.eclipse.n4js.workspace;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.DefinitionProjectMap;
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.util.UriExtensions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Accessing workspace information on disk (e.g. existing projects).
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceConfig implements XIWorkspaceConfig {

	/** The root path of this workspace. */
	protected final URI baseDirectory;

	/***/
	protected final ProjectDiscoveryHelper projectDiscoveryHelper;
	/***/
	protected final ProjectDescriptionLoader projectDescriptionLoader;
	/***/
	protected final SemanticDependencySupplier semanticDependencySupplier;
	/***/
	protected final ConfigSnapshotFactory configSnapshotFactory;
	/***/
	protected final UriExtensions uriExtensions;

	/** All projects registered in this workspace by their id. */
	protected final Map<String, N4JSProjectConfig> projectId2ProjectConfig = new LinkedHashMap<>();
	/** All projects by their package name. */
	protected final HashMultimap<String, N4JSProjectConfig> packageName2ProjectConfigs = HashMultimap.create();
	/** Map between definition projects and their defined projects. */
	protected final DefinitionProjectMap definitionProjects = new DefinitionProjectMap();

	/**
	 * Creates a new, empty {@link N4JSWorkspaceConfig}. It will not contain any projects until
	 * {@link #reloadAllProjectInformationFromDisk()} is invoked for the first time.
	 */
	public N4JSWorkspaceConfig(URI baseDirectory, ProjectDiscoveryHelper projectDiscoveryHelper,
			ProjectDescriptionLoader projectDescriptionLoader, SemanticDependencySupplier semanticDependencySupplier,
			ConfigSnapshotFactory configSnapshotFactory, UriExtensions uriExtensions) {

		this.baseDirectory = baseDirectory;
		this.projectDiscoveryHelper = projectDiscoveryHelper;
		this.projectDescriptionLoader = projectDescriptionLoader;
		this.semanticDependencySupplier = semanticDependencySupplier;
		this.configSnapshotFactory = configSnapshotFactory;
		this.uriExtensions = uriExtensions;
	}

	@Override
	public URI getPath() {
		return baseDirectory;
	}

	/** Returns the {@link #getPath() path} as a {@link FileURI}. */
	public FileURI getPathAsFileURI() {
		return new FileURI(uriExtensions.withEmptyAuthority(baseDirectory));
	}

	@Override
	public Set<? extends N4JSProjectConfig> getProjects() {
		return ImmutableSet.copyOf(projectId2ProjectConfig.values());
	}

	@Override
	public N4JSProjectConfig findProjectByName(String name) {
		return projectId2ProjectConfig.get(name);
	}

	/** @return a project config for the given package name */
	public Set<N4JSProjectConfig> findProjectsByPackageName(String packageName) {
		return packageName2ProjectConfigs.get(packageName);
	}

	/** @return all package names in the workspace */
	public Set<String> getAllPackageNames() {
		return Collections.unmodifiableSet(packageName2ProjectConfigs.keySet());
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

	/** Remove all {@link N4JSProjectConfig}s from this workspace. */
	protected void deregisterAllProjects() {
		projectId2ProjectConfig.clear();
		packageName2ProjectConfigs.clear();
		definitionProjects.clear();
	}

	/**
	 * Registers the project at the given path with the given project description.
	 */
	public N4JSProjectConfig registerProject(FileURI path, ProjectDescription pd) {
		String qualifiedName = pd.getId();
		if (projectId2ProjectConfig.containsKey(qualifiedName)) {
			return null;
		}
		N4JSProjectConfig newProject = createProjectConfig(path, pd);
		projectId2ProjectConfig.put(qualifiedName, newProject);
		packageName2ProjectConfigs.put(pd.getPackageName(), newProject);
		updateDefinitionProjects(null, pd);
		return newProject;
	}

	/** Creates an instance of {@link N4JSProjectConfig} without registering it. */
	protected N4JSProjectConfig createProjectConfig(FileURI path, ProjectDescription pd) {
		return new N4JSProjectConfig(this, path, pd, projectDescriptionLoader, semanticDependencySupplier);
	}

	/** Invoked by {@link N4JSProjectConfig} when its state changes. */
	protected void onProjectChanged(@SuppressWarnings("unused") FileURI path, ProjectDescription pdOld,
			ProjectDescription pdNew) {
		updateDefinitionProjects(pdOld, pdNew);
	}

	/** Internal. Updates the registry of definition projects in {@link #definitionProjects}. */
	protected void updateDefinitionProjects(ProjectDescription pdOldOrNull, ProjectDescription pdNew) {
		if (pdOldOrNull != null) {
			definitionProjects.changeProject(pdOldOrNull, pdNew);
		} else {
			definitionProjects.addProject(pdNew);
		}
	}

	/** Clears this workspace and re-initializes it by searching the file system for projects. */
	protected void reloadAllProjectInformationFromDisk() {
		deregisterAllProjects();
		Path baseDir = getPathAsFileURI().toPath();
		Map<Path, ProjectDescription> projects = projectDiscoveryHelper
				.collectAllProjectDirs(Collections.singleton(baseDir));

		for (Path newProjectPath : projects.keySet()) {
			FileURI newProjectPathAsFileURI = new FileURI(newProjectPath);
			ProjectDescription pd = projects.get(newProjectPath);
			registerProject(newProjectPathAsFileURI, pd);
		}
	}

	@Override
	public WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, Set<URI> dirtyFiles,
			Set<URI> deletedFiles, boolean refresh) {

		WorkspaceChanges changes = refresh
				? doCompleteUpdate(oldWorkspaceConfig)
				: doMinimalUpdate(oldWorkspaceConfig, dirtyFiles, deletedFiles);

		changes = recomputeSemanticDependenciesIfNecessary(oldWorkspaceConfig, changes);

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

				if (((N4JSProjectConfig) project).isWorkspaceProject()) {
					needToDetectAddedRemovedProjects = true;
				}
			} else {
				// a new project was created
				needToDetectAddedRemovedProjects = true;
			}
		}

		needToDetectAddedRemovedProjects |= isProjectDiscoveryRequired(changes, oldWorkspaceConfig);

		if (needToDetectAddedRemovedProjects) {
			changes = changes.merge(detectAddedRemovedProjects(oldWorkspaceConfig, false));
		}

		return changes;
	}

	private WorkspaceChanges detectAddedRemovedProjects(WorkspaceConfigSnapshot oldWorkspaceConfig,
			boolean alsoDetectChangedProjects) {

		// update all projects
		reloadAllProjectInformationFromDisk();

		// detect project additions, removals (and also changes, iff 'alsoDetectChangedProjects' is set)
		Map<URI, ProjectConfigSnapshot> oldProjectsMap = IterableExtensions.toMap(oldWorkspaceConfig.getProjects(),
				ProjectConfigSnapshot::getPath);
		Map<URI, N4JSProjectConfig> newProjectsMap = IterableExtensions.toMap(getProjects(), XIProjectConfig::getPath);
		WorkspaceChanges changes = new WorkspaceChanges();
		for (URI uri : Sets.union(oldProjectsMap.keySet(), newProjectsMap.keySet())) {
			boolean isOld = oldProjectsMap.containsKey(uri);
			boolean isNew = newProjectsMap.containsKey(uri);

			if (isOld && !isNew) {
				// deleted
				changes = changes.merge(WorkspaceChanges.createProjectRemoved(oldProjectsMap.get(uri)));
			} else if (!isOld && isNew) {
				// added
				ProjectConfigSnapshot newPC = configSnapshotFactory
						.createProjectConfigSnapshot(newProjectsMap.get(uri));
				changes = changes.merge(WorkspaceChanges.createProjectAdded(newPC));
			} else if (isOld && isNew) {
				// check name change
				String oldPackageName = ((N4JSProjectConfigSnapshot) oldProjectsMap.get(uri)).getPackageName();
				String newPackageName = newProjectsMap.get(uri).getPackageName();
				if (Objects.equals(oldPackageName, newPackageName)) {
					// no change
					if (alsoDetectChangedProjects) {
						ProjectConfigSnapshot oldPC = oldProjectsMap.get(uri);
						ProjectConfigSnapshot newPC = configSnapshotFactory
								.createProjectConfigSnapshot(newProjectsMap.get(uri));

						changes = changes.merge(N4JSProjectConfig.computeChanges(oldPC, newPC));
					}
				} else {
					// names changed
					ProjectConfigSnapshot newPC = configSnapshotFactory
							.createProjectConfigSnapshot(newProjectsMap.get(uri));
					changes = changes.merge(WorkspaceChanges.createProjectAdded(newPC));
					changes = changes.merge(WorkspaceChanges.createProjectRemoved(oldProjectsMap.get(uri)));
				}

			}
		}
		return changes;
	}

	/**
	 * The list of {@link N4JSProjectConfig#getSemanticDependencies() semantic dependencies} of
	 * {@link N4JSProjectConfig}s is tricky for three reasons:
	 * <ol>
	 * <li>the semantic dependencies do not contain names of non-existing projects (in case of unresolved project
	 * references in the package.json),
	 * <li>the order of the semantic dependencies depends on the characteristics of the target projects (mainly the
	 * {@link ProjectDescription#getDefinesPackage() "defines package"} property),
	 * <li>each implicitly added dependency to a definition project PI depends on the characteristics of the target
	 * project PT triggering this implicit dependency (i.e. the defined project) <em>and</em> the overall workspace
	 * contents (Are definition projects available in the workspace for project PT?).
	 * </ol>
	 * Therefore, the "semantic dependencies" of a project P can change even without a change in the
	 * <code>package.json</code> file of P. To detect and apply these changes is the purpose of this method.
	 */
	protected WorkspaceChanges recomputeSemanticDependenciesIfNecessary(WorkspaceConfigSnapshot oldWorkspaceConfig,
			WorkspaceChanges changes) {

		boolean needRecompute = !changes.getAddedProjects().isEmpty()
				|| !changes.getRemovedProjects().isEmpty()
				|| Iterables.any(changes.getChangedProjects(),
						pc -> affectedByPropertyChanges(pc, oldWorkspaceConfig));

		if (needRecompute) {
			List<ProjectConfigSnapshot> projectsWithChangedSemanticDeps = new ArrayList<>();
			for (XIProjectConfig pc : getProjects()) {
				String projectName = pc.getName();
				N4JSProjectConfigSnapshot oldSnapshot = (N4JSProjectConfigSnapshot) oldWorkspaceConfig
						.findProjectByName(projectName);
				if (oldSnapshot == null) {
					continue;
				}
				// convert to list in next line, because we want the below #equals() check to also include the order:
				List<String> oldSemanticDeps = new ArrayList<>(oldSnapshot.getDependencies());
				List<String> newSemanticDeps = ((N4JSProjectConfig) pc).getSemanticDependencies().stream()
						.map(ProjectDependency::getPackageName)
						.collect(Collectors.toList());
				if (!newSemanticDeps.equals(oldSemanticDeps)) {
					ProjectConfigSnapshot newSnapshot = configSnapshotFactory.createProjectConfigSnapshot(pc);
					if (!newSnapshot.equals(oldSnapshot)) {
						projectsWithChangedSemanticDeps.add(newSnapshot);
					}
				}
			}
			if (!projectsWithChangedSemanticDeps.isEmpty()) {
				changes = changes.merge(WorkspaceChanges.createProjectsChanged(projectsWithChangedSemanticDeps));
			}
		}
		return changes;
	}

	/**
	 * Tells whether the given workspace changes, when applied to the given old workspace configuration, call for an
	 * overall re-discovery of all projects in the workspace.
	 * <p>
	 * Some reasons why this is required:
	 * <ul>
	 * <li>Because the {@link ProjectDiscoveryHelper} discovers a plainJS-project P only if there is an N4JS project
	 * with a dependency to P, we need to re-discover projects whenever dependencies of N4JS projects change.
	 * <li>Because of workspace projects shadowing projects of same name in the <code>node_modules</code> folder, we
	 * need to re-discover projects whenever workspace projects are added/removed.
	 * </ul>
	 */
	private static boolean isProjectDiscoveryRequired(WorkspaceChanges changes,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {
		return !allExceptPlainjsInNodeModules(changes.getAddedProjects()).isEmpty()
				|| !allExceptPlainjsInNodeModules(changes.getRemovedProjects()).isEmpty()
				|| allExceptPlainjs(changes.getChangedProjects())
						.anyMatch(pc -> didDependenciesChange(pc, oldWorkspaceConfig));
	}

	/** Tells whether the dependencies of the given project changed w.r.t. the given old workspace config. */
	private static boolean didDependenciesChange(ProjectConfigSnapshot projectConfig,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {

		ProjectConfigSnapshot oldProjectConfig = oldWorkspaceConfig.findProjectByName(projectConfig.getName());
		N4JSProjectConfigSnapshot newCasted = (N4JSProjectConfigSnapshot) projectConfig;
		N4JSProjectConfigSnapshot oldCasted = (N4JSProjectConfigSnapshot) oldProjectConfig;

		return oldProjectConfig == null
				|| !Objects.equals(oldCasted.getPackageName(), newCasted.getPackageName())
				|| !Objects.equals(
						((N4JSProjectConfigSnapshot) projectConfig).getDependencies(),
						((N4JSProjectConfigSnapshot) oldProjectConfig).getDependencies());
	}

	/** Tells whether the property {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} changed. */
	private static boolean affectedByPropertyChanges(ProjectConfigSnapshot newProjectConfig,
			WorkspaceConfigSnapshot oldWorkspaceConfig) {
		ProjectConfigSnapshot oldProjectConfig = oldWorkspaceConfig.findProjectByName(newProjectConfig.getName());
		if (oldProjectConfig == null) {
			return true;
		}
		N4JSProjectConfigSnapshot oldCasted = (N4JSProjectConfigSnapshot) oldProjectConfig;
		N4JSProjectConfigSnapshot newCasted = (N4JSProjectConfigSnapshot) newProjectConfig;

		if (didDefinitionPropertiesChange(oldCasted, newCasted)) {
			return true;
		}

		if (!Objects.equals(oldCasted.getPackageName(), newCasted.getPackageName())) {
			return true;
		}

		return false;
	}

	/** Tells whether the property {@link PackageJsonProperties#DEFINES_PACKAGE "definesPackage"} changed. */
	private static boolean didDefinitionPropertiesChange(N4JSProjectConfigSnapshot oldCasted,
			N4JSProjectConfigSnapshot newCasted) {

		boolean newIsDefinition = newCasted.getType() == ProjectType.DEFINITION;
		boolean oldIsDefinition = oldCasted.getType() == ProjectType.DEFINITION;
		if (newIsDefinition != oldIsDefinition) {
			return true;
		}
		if (newIsDefinition && oldIsDefinition) {
			if (!Objects.equals(newCasted.getDefinesPackage(), oldCasted.getDefinesPackage())) {
				return true;
			}
		}
		return false;
	}

	private static FluentIterable<? extends ProjectConfigSnapshot> allExceptPlainjs(
			Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		return FluentIterable.from(projectConfigs)
				.filter(pc -> ((N4JSProjectConfigSnapshot) pc).getType() != ProjectType.PLAINJS);
	}

	private static FluentIterable<? extends ProjectConfigSnapshot> allExceptPlainjsInNodeModules(
			Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		return FluentIterable.from(projectConfigs)
				.filter(pc -> ((N4JSProjectConfigSnapshot) pc).getType() != ProjectType.PLAINJS
						|| !((N4JSProjectConfigSnapshot) pc).isExternal());
	}
}
