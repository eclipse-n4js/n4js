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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Factory for creating snapshots
 */
@SuppressWarnings("restriction")
public class ConfigSnapshotFactory {

	/** Provider to create {@link ProjectBuildOrderInfo} instances */
	@Inject
	protected ProjectBuildOrderFactory buildOrderFactory;

	/** Creates an instance of {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(XIWorkspaceConfig workspaceConfig) {
		List<? extends XIProjectConfig> projects = new ArrayList<>(workspaceConfig.getProjects());
		List<ProjectConfigSnapshot> projectSnapshots = Lists.transform(projects, this::createProjectConfigSnapshot);

		return createWorkspaceConfigSnapshot(workspaceConfig.getPath(), projectSnapshots);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path,
			Iterable<? extends ProjectConfigSnapshot> projects) {

		Map<String, ProjectConfigSnapshot> lookupName2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();
		updateWorkspaceConfigSnapshotLookupMaps(lookupName2Project, lookupProjectPath2Project,
				lookupSourceFolderPath2Project, projects, Collections.emptyList());

		return createWorkspaceConfigSnapshot(path,
				ImmutableBiMap.copyOf(lookupName2Project),
				ImmutableMap.copyOf(lookupProjectPath2Project),
				ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(WorkspaceConfigSnapshot snapshot) {

		return createWorkspaceConfigSnapshot(snapshot.path,
				snapshot.name2Project, snapshot.projectPath2Project, snapshot.sourceFolderPath2Project);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path) {
		return createWorkspaceConfigSnapshot(path, ImmutableBiMap.of(), ImmutableMap.of(), ImmutableMap.of());
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot createWorkspaceConfigSnapshot(URI path,
			ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> sourceFolderPath2Project) {

		return new WorkspaceConfigSnapshot(path,
				ImmutableBiMap.copyOf(name2Project),
				ImmutableMap.copyOf(projectPath2Project),
				ImmutableMap.copyOf(sourceFolderPath2Project),
				buildOrderFactory);
	}

	/** Creates an instance of {@link WorkspaceConfigSnapshot} only with the path of the given snapshot */
	public WorkspaceConfigSnapshot clear(WorkspaceConfigSnapshot wcs) {
		return createWorkspaceConfigSnapshot(wcs.path);
	}

	/** Updates the given {@link WorkspaceConfigSnapshot} */
	public WorkspaceConfigSnapshot update(WorkspaceConfigSnapshot wcs,
			ImmutableList<? extends ProjectConfigSnapshot> changedProjects,
			ImmutableSet<String> removedProjects) {

		Map<String, ProjectConfigSnapshot> lookupName2Project = new HashMap<>(wcs.name2Project);
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>(wcs.projectPath2Project);
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>(wcs.sourceFolderPath2Project);
		updateWorkspaceConfigSnapshotLookupMaps(lookupName2Project,
				lookupProjectPath2Project,
				lookupSourceFolderPath2Project,
				changedProjects, removedProjects);

		return createWorkspaceConfigSnapshot(wcs.path,
				ImmutableBiMap.copyOf(lookupName2Project),
				ImmutableMap.copyOf(lookupProjectPath2Project),
				ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/** Change the given lookup maps to include the given project changes and removals. */
	protected void updateWorkspaceConfigSnapshotLookupMaps(
			Map<String, ProjectConfigSnapshot> lookupName2Project,
			Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project,
			Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project,
			Iterable<? extends ProjectConfigSnapshot> changedProjects, Iterable<String> removedProjectNames) {

		// collect removed projects
		List<ProjectConfigSnapshot> removedProjects = new ArrayList<>();
		for (String projectName : removedProjectNames) {
			ProjectConfigSnapshot removedProject = lookupName2Project.get(projectName);
			if (removedProject != null) {
				removedProjects.add(removedProject);
			}
		}

		// apply updates for changed projects
		for (ProjectConfigSnapshot project : changedProjects) {
			ProjectConfigSnapshot oldProject = lookupName2Project.put(project.getName(), project);
			if (oldProject != null) {
				lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(oldProject.getPath()));
				for (SourceFolderSnapshot sourceFolder : oldProject.getSourceFolders()) {
					lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
				}
			}
			lookupProjectPath2Project.put(URIUtils.trimTrailingPathSeparator(project.getPath()), project);
			for (SourceFolderSnapshot sourceFolder : project.getSourceFolders()) {
				lookupSourceFolderPath2Project.put(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()), project);
			}
		}

		// apply updates for removed projects
		for (ProjectConfigSnapshot removedProject : removedProjects) {
			lookupName2Project.remove(removedProject.getName());
			lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(removedProject.getPath()));
			for (SourceFolderSnapshot sourceFolder : removedProject.getSourceFolders()) {
				lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
			}
		}
	}

	/** Creates instances of {@link ProjectConfigSnapshot} */
	public ProjectConfigSnapshot createProjectConfigSnapshot(XIProjectConfig projectConfig) {
		return new ProjectConfigSnapshot(
				projectConfig.getName(),
				projectConfig.getPath(),
				projectConfig.getProjectDescriptionUris(),
				projectConfig.indexOnly(),
				projectConfig.getDependencies(),
				Iterables.transform(projectConfig.getSourceFolders(), this::createSourceFolderSnapshot));
	}

	/** Creates instances of {@link SourceFolderSnapshot} */
	public SourceFolderSnapshot createSourceFolderSnapshot(ISourceFolder sourceFolder) {
		return new SourceFolderSnapshot(sourceFolder.getName(), sourceFolder.getPath());
	}

}
