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
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;

/**
 * Default implementation of an {@link ISourceFolderSnapshot}.
 */
public class WorkspaceConfigSnapshot implements IWorkspaceConfigSnapshot {

	private final URI path;
	/** Map from project name to the project's configuration snapshot. */
	protected final ImmutableMap<String, IProjectConfigSnapshot> name2Project;
	/** Keys are URIs <em>without</em> trailing path separator. */
	protected final ImmutableMap<URI, IProjectConfigSnapshot> sourceFolderPath2Project;

	/** See {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot(XIWorkspaceConfig workspace) {
		this(workspace.getPath(),
				workspace.getProjects().stream().map(ProjectConfigSnapshot::new).collect(Collectors.toList()));
	}

	/** See {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot(URI path, Iterable<? extends IProjectConfigSnapshot> projects) {
		Map<String, IProjectConfigSnapshot> lookupName2Project = new HashMap<>();
		Map<URI, IProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();
		updateLookupMaps(lookupName2Project, lookupSourceFolderPath2Project, projects, Collections.emptyList());
		this.path = path;
		this.name2Project = ImmutableMap.copyOf(lookupName2Project);
		this.sourceFolderPath2Project = ImmutableMap.copyOf(lookupSourceFolderPath2Project);
	}

	/** See {@link WorkspaceConfigSnapshot}. */
	protected WorkspaceConfigSnapshot(URI path, ImmutableMap<String, IProjectConfigSnapshot> name2Project,
			ImmutableMap<URI, IProjectConfigSnapshot> sourceFolderPath2Project) {
		this.path = path;
		this.name2Project = name2Project;
		this.sourceFolderPath2Project = sourceFolderPath2Project;
	}

	@Override
	public URI getPath() {
		return path;
	}

	@Override
	public ImmutableCollection<? extends IProjectConfigSnapshot> getProjects() {
		return name2Project.values();
	}

	@Override
	public IProjectConfigSnapshot findProjectContaining(URI nestedLocation) {
		nestedLocation = trimTrailingPathSeparator(nestedLocation);
		do {
			IProjectConfigSnapshot match = sourceFolderPath2Project.get(nestedLocation);
			if (match != null) {
				return match;
			}
			nestedLocation = nestedLocation.segmentCount() > 0 ? nestedLocation.trimSegments(1) : null;
		} while (nestedLocation != null);

		return null;
	}

	@Override
	public IProjectConfigSnapshot findProjectByName(String name) {
		return name2Project.get(name);
	}

	@Override
	public IWorkspaceConfigSnapshot clear() {
		return new WorkspaceConfigSnapshot(getPath(), Collections.emptyList());
	}

	@Override
	public WorkspaceConfigSnapshot update(Iterable<? extends IProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects) {

		Map<String, IProjectConfigSnapshot> lookupName2Project = new HashMap<>();
		Map<URI, IProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();
		updateLookupMaps(lookupName2Project, lookupSourceFolderPath2Project, changedProjects, removedProjects);
		return new WorkspaceConfigSnapshot(path, ImmutableMap.copyOf(lookupName2Project),
				ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/** Change the given lookup maps to include the given project changes and removals. */
	protected void updateLookupMaps(
			Map<String, IProjectConfigSnapshot> lookupName2Project,
			Map<URI, IProjectConfigSnapshot> lookupSourceFolderPath2Project,
			Iterable<? extends IProjectConfigSnapshot> changedProjects, Iterable<String> removedProjectNames) {

		// collect removed projects
		List<IProjectConfigSnapshot> removedProjects = new ArrayList<>();
		for (String projectName : removedProjectNames) {
			IProjectConfigSnapshot removedProject = lookupName2Project.get(projectName);
			if (removedProject != null) {
				removedProjects.add(removedProject);
			}
		}

		// apply updates for changed projects
		for (IProjectConfigSnapshot project : changedProjects) {
			IProjectConfigSnapshot oldProject = lookupName2Project.put(project.getName(), project);
			if (oldProject != null) {
				for (ISourceFolderSnapshot sourceFolder : oldProject.getSourceFolders()) {
					lookupSourceFolderPath2Project.remove(trimTrailingPathSeparator(sourceFolder.getPath()));
				}
			}
			for (ISourceFolderSnapshot sourceFolder : project.getSourceFolders()) {
				lookupSourceFolderPath2Project.put(trimTrailingPathSeparator(sourceFolder.getPath()), project);
			}
		}

		// apply updates for removed projects
		for (IProjectConfigSnapshot removedProject : removedProjects) {
			lookupName2Project.remove(removedProject.getName());
			for (ISourceFolderSnapshot sourceFolder : removedProject.getSourceFolders()) {
				lookupSourceFolderPath2Project.remove(trimTrailingPathSeparator(sourceFolder.getPath()));
			}
		}
	}

	private static URI trimTrailingPathSeparator(URI uri) {
		return uri.hasTrailingPathSeparator() ? uri.trimSegments(1) : uri;
	}
}
