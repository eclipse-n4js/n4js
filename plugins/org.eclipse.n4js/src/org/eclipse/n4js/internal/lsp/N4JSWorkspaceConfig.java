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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Wrapper around {@link IN4JSCore}.
 */
@SuppressWarnings("restriction")
public class N4JSWorkspaceConfig implements XIWorkspaceConfig {

	private final URI baseDirectory;
	private final IN4JSCore delegate;

	/** Constructor */
	public N4JSWorkspaceConfig(URI baseDirectory, IN4JSCore delegate) {
		this.baseDirectory = baseDirectory;
		this.delegate = delegate;
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
		Set<XIProjectConfig> pConfigs = new HashSet<>();
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
	public WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, List<URI> dirtyFiles,
			List<URI> deletedFiles) {
		WorkspaceChanges result = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);
		for (URI changedResource : Iterables.concat(dirtyFiles, deletedFiles)) {
			result = result.merge(update(oldWorkspaceConfig, changedResource));
		}
		return result;
	}

	private WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, URI changedResource) {
		IProjectConfig project = this.findProjectContaining(changedResource);

		WorkspaceChanges update = new WorkspaceChanges();

		// project location do not end with an empty segment
		FileURI projectUri = project != null ? new FileURI(new File(project.getPath().toFileString())) : null;
		boolean wasExistingInWorkspace = projectUri != null && ((N4JSRuntimeCore) delegate).isRegistered(projectUri);
		if (project != null && wasExistingInWorkspace) {
			// an existing project was modified
			update = update.merge(((N4JSProjectConfig) project).update(oldWorkspaceConfig, changedResource));

			if (((N4JSProjectConfig) project).isWorkspacesProject()) {
				update = update.merge(detectAddedRemovedProjects(oldWorkspaceConfig));
			}
		} else {
			// a new project was created
			update = update.merge(detectAddedRemovedProjects(oldWorkspaceConfig));
		}

		return update;
	}

	private WorkspaceChanges detectAddedRemovedProjects(WorkspaceConfigSnapshot oldWorkspaceConfig) {

		// update all projects
		((N4JSRuntimeCore) delegate).deregisterAll();

		ProjectDiscoveryHelper projectDiscoveryHelper = ((N4JSRuntimeCore) delegate).getProjectDiscoveryHelper();
		Path baseDir = new FileURI(getPath()).toFile().toPath();
		LinkedHashSet<Path> newProjectPaths = projectDiscoveryHelper.collectAllProjectDirs(baseDir);
		for (Path newProjectPath : newProjectPaths) {
			((N4JSRuntimeCore) delegate).registerProject(newProjectPath.toFile());
		}

		// detect changes
		Set<? extends ProjectConfigSnapshot> oldProjects = oldWorkspaceConfig.getProjects();
		List<? extends ProjectConfigSnapshot> newProjects = getProjects().stream()
				.map(XIProjectConfig::toSnapshot).collect(Collectors.toList());
		Map<URI, ProjectConfigSnapshot> oldProjectsMap = getProjectsMap(oldProjects);
		Map<URI, ProjectConfigSnapshot> newProjectsMap = getProjectsMap(newProjects);
		List<ProjectConfigSnapshot> addedProjects = new ArrayList<>();
		List<ProjectConfigSnapshot> removedProjects = new ArrayList<>();
		for (URI uri : Sets.union(oldProjectsMap.keySet(), newProjectsMap.keySet())) {
			boolean isOld = oldProjectsMap.containsKey(uri);
			boolean isNew = newProjectsMap.containsKey(uri);
			if (isOld && !isNew) {
				removedProjects.add(oldProjectsMap.get(uri));
			} else if (!isOld && isNew) {
				addedProjects.add(newProjectsMap.get(uri));
			}
		}

		boolean dependenciesChanged = !addedProjects.isEmpty() || !removedProjects.isEmpty();
		return new WorkspaceChanges(dependenciesChanged, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(),
				ImmutableList.of(), ImmutableList.copyOf(removedProjects), ImmutableList.copyOf(addedProjects),
				ImmutableList.of());
	}

	private Map<URI, ProjectConfigSnapshot> getProjectsMap(Iterable<? extends ProjectConfigSnapshot> projects) {
		Map<URI, ProjectConfigSnapshot> projectsMap = new HashMap<>();
		for (ProjectConfigSnapshot projectConfig : projects) {
			projectsMap.put(projectConfig.getPath(), projectConfig);
		}
		return projectsMap;
	}
}
