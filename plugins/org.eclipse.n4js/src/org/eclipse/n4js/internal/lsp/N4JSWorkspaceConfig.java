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

import static java.util.Collections.emptyList;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

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
	public IProjectConfig findProjectByName(String name) {
		IN4JSProject project = delegate.findProject(new N4JSProjectName(name)).orNull();
		if (project != null) {
			return new N4JSProjectConfig(this, project);
		}
		return null;
	}

	@Override
	public IProjectConfig findProjectContaining(URI member) {
		IN4JSProject project = delegate.findProject(member).orNull();
		if (project != null) {
			return new N4JSProjectConfig(this, project);
		}
		return null;
	}

	@Override
	public Set<? extends IProjectConfig> getProjects() {
		Set<IProjectConfig> pConfigs = new HashSet<>();
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
	public WorkspaceChanges update(URI changedResource, Function<String, ProjectDescription> pdProvider) {
		IProjectConfig project = this.findProjectContaining(changedResource);

		if (project == null) {
			// project was deleted
			// note: currently this should never happen; but
			// if so: TODO: return other than WorkspaceUpdateChanges.NO_CHANGES
			return WorkspaceChanges.NO_CHANGES;
		}

		// get old projects here before it gets invalidated by N4JSProjectConfig#update()
		Set<? extends IProjectConfig> oldProjects = getProjects();

		WorkspaceChanges update = new WorkspaceChanges();

		// project location do not end with an empty segment
		FileURI projectUri = new FileURI(new File(project.getPath().toFileString()));
		boolean wasExistingInWorkspace = ((N4JSRuntimeCore) delegate).isRegistered(projectUri);
		if (wasExistingInWorkspace) {
			// an existing project was modified
			ProjectDescription pd = pdProvider.apply(project.getName());
			update.merge(((N4JSProjectConfig) project).update(changedResource, pd));
		} else {
			// a new project was created
			update.merge(WorkspaceChanges.createProjectAdded(project));
		}

		if (((N4JSProjectConfig) project).isWorkspacesProject()) {
			update.merge(detectWorkspacesChanges(project, oldProjects));
		}

		return update;
	}

	private WorkspaceChanges detectWorkspacesChanges(IProjectConfig project,
			Set<? extends IProjectConfig> oldProjects) {

		IN4JSProject n4jsProject = ((N4JSProjectConfig) project).toProject();

		// update all projects
		((N4JSProject) n4jsProject).invalidate();
		((N4JSRuntimeCore) delegate).deregisterAll();

		ProjectDiscoveryHelper projectDiscoveryHelper = ((N4JSRuntimeCore) delegate).getProjectDiscoveryHelper();
		Path baseDir = new FileURI(getPath()).toFile().toPath();
		LinkedHashSet<Path> newProjectPaths = projectDiscoveryHelper.collectAllProjectDirs(baseDir);
		for (Path newProjectPath : newProjectPaths) {
			((N4JSRuntimeCore) delegate).registerProject(newProjectPath.toFile());
		}

		// detect changes
		Map<URI, IProjectConfig> oldProjectsMap = getProjectsMap(oldProjects);
		Map<URI, IProjectConfig> newProjectsMap = getProjectsMap(getProjects());
		List<IProjectConfig> addedProjects = new ArrayList<>();
		List<IProjectConfig> removedProjects = new ArrayList<>();
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
		return new WorkspaceChanges(dependenciesChanged, emptyList(), emptyList(), emptyList(), emptyList(),
				emptyList(), removedProjects, addedProjects);
	}

	private Map<URI, IProjectConfig> getProjectsMap(Set<? extends IProjectConfig> projects) {
		Map<URI, IProjectConfig> projectsMap = new HashMap<>();
		for (IProjectConfig projectConfig : projects) {
			projectsMap.put(projectConfig.getPath(), projectConfig);
		}
		return projectsMap;
	}
}