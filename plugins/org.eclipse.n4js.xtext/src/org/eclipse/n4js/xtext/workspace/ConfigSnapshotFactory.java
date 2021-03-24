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
package org.eclipse.n4js.xtext.workspace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Factory for creating snapshots
 */
@SuppressWarnings("restriction")
public class ConfigSnapshotFactory {

	/** Provider to create {@link BuildOrderInfo} instances */
	@Inject
	protected BuildOrderFactory buildOrderFactory;

	/** Creates an empty instance of {@link WorkspaceConfigSnapshot} representing an empty workspace. */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path) {
		return createWorkspaceConfigSnapshot(path, Collections.emptyList());
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} representing the current workspace. */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(XIWorkspaceConfig workspaceConfig) {
		List<? extends XIProjectConfig> projects = new ArrayList<>(workspaceConfig.getProjects());
		List<ProjectConfigSnapshot> projectSnapshots = Lists.transform(projects, this::createProjectConfigSnapshot);

		return createWorkspaceConfigSnapshot(workspaceConfig.getPath(), projectSnapshots);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} for the given project snapshots. */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path,
			Iterable<? extends ProjectConfigSnapshot> projects) {

		ProjectSet projectSet = createProjectSet(projects);
		return createWorkspaceConfigSnapshot(path, projectSet);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} for the given project snapshots. */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path, ProjectSet projects) {
		BuildOrderInfo buildOrderInfo = buildOrderFactory.createBuildOrderInfo(projects);
		return createWorkspaceConfigSnapshot(path, projects, buildOrderInfo);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path, ProjectSet projects,
			BuildOrderInfo buildOrderInfo) {

		return new WorkspaceConfigSnapshot(path, projects, buildOrderInfo);
	}

	/** Creates an empty instance of {@link WorkspaceConfigSnapshot} with the path of the given snapshot. */
	public WorkspaceConfigSnapshot clear(WorkspaceConfigSnapshot wcs) {
		return createWorkspaceConfigSnapshot(wcs.path);
	}

	/** Updates the given {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot update(WorkspaceConfigSnapshot wcs,
			ImmutableList<? extends ProjectConfigSnapshot> changedProjects,
			ImmutableSet<String> removedProjects) {

		ProjectSet projectSetOld = wcs.projects;
		ProjectSet projectSetNew = updateProjectSet(projectSetOld, changedProjects, removedProjects);
		return createWorkspaceConfigSnapshot(wcs.path, projectSetNew);
	}

	/** Creates instances of {@link ProjectConfigSnapshot} */
	public ProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig) {
		Iterable<SourceFolderSnapshot> sourceFolders = Iterables.transform(projectConfig.getSourceFolders(),
				this::createSourceFolderSnapshot);
		return createProjectConfigSnapshot(projectConfig, sourceFolders);
	}

	/** Creates instances of {@link ProjectConfigSnapshot} */
	public ProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig,
			Iterable<SourceFolderSnapshot> sourceFolders) {
		return new ProjectConfigSnapshot(
				projectConfig.getName(),
				projectConfig.getPath(),
				projectConfig.getProjectDescriptionUris(),
				projectConfig.indexOnly(),
				projectConfig.isGeneratorEnabled(),
				projectConfig.getDependencies(),
				sourceFolders);
	}

	/** Creates instances of {@link SourceFolderSnapshot} */
	public SourceFolderSnapshot createSourceFolderSnapshot(ISourceFolder sourceFolder) {
		return new SourceFolderSnapshot(sourceFolder.getName(), sourceFolder.getPath());
	}

	public ProjectSet createProjectSet(Iterable<? extends ProjectConfigSnapshot> projects) {
		return new ProjectSet(projects);
	}

	public ProjectSet updateProjectSet(ProjectSet projects,
			Iterable<? extends ProjectConfigSnapshot> changedProjects, Iterable<String> removedProjects) {
		return projects.update(changedProjects, removedProjects);
	}
}
