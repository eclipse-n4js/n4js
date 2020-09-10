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
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Immutable equivalent of an {@link IWorkspaceConfig}.
 */
@SuppressWarnings("restriction")
public class WorkspaceConfigSnapshot {

	/** The root path of the workspace */
	protected final URI path;
	/** Map from project name to the project's configuration snapshot. */
	protected final ImmutableBiMap<String, ProjectConfigSnapshot> name2Project;
	/** Keys are URIs <em>without</em> trailing path separator. */
	protected final ImmutableMap<URI, ProjectConfigSnapshot> projectPath2Project;
	/** Keys are URIs <em>without</em> trailing path separator. */
	protected final ImmutableMap<URI, ProjectConfigSnapshot> sourceFolderPath2Project;

	/** See {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot(URI path, Iterable<? extends ProjectConfigSnapshot> projects) {
		Map<String, ProjectConfigSnapshot> lookupName2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();
		updateLookupMaps(lookupName2Project, lookupProjectPath2Project, lookupSourceFolderPath2Project, projects,
				Collections.emptyList());
		this.path = path;
		this.name2Project = ImmutableBiMap.copyOf(lookupName2Project);
		this.projectPath2Project = ImmutableMap.copyOf(lookupProjectPath2Project);
		this.sourceFolderPath2Project = ImmutableMap.copyOf(lookupSourceFolderPath2Project);
	}

	/** See {@link WorkspaceConfigSnapshot}. */
	protected WorkspaceConfigSnapshot(URI path, ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> sourceFolderPath2Project) {
		this.path = path;
		this.name2Project = name2Project;
		this.projectPath2Project = projectPath2Project;
		this.sourceFolderPath2Project = sourceFolderPath2Project;
	}

	/**
	 * Getter for the root path.
	 */
	public URI getPath() {
		return path;
	}

	/**
	 * Get all the projects known in this snapshot.
	 */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjects() {
		return name2Project.values();
	}

	/**
	 * Find the project with the given name.
	 */
	public ProjectConfigSnapshot findProjectByName(String name) {
		return name2Project.get(name);
	}

	/**
	 * Finds the project having a path that is a prefix of the given URI.
	 * <p>
	 * Note the difference to {@link #findProjectContaining(URI)}!
	 */
	public ProjectConfigSnapshot findProjectByNestedLocation(URI nestedLocation) {
		return URIUtils.findInMapByNestedURI(projectPath2Project, nestedLocation);
	}

	/**
	 * Same as {@link IWorkspaceConfig#findProjectContaining(URI)}: finds the project having a source folder that
	 * {@link SourceFolderSnapshot#contains(URI) actually contains} the given nested URI. Returns <code>null</code> if
	 * no such project is found. The given URI may point to a file or folder.
	 *
	 * @see IWorkspaceConfig#findProjectContaining(URI)
	 * @see SourceFolderSnapshot#contains(URI)
	 */
	public ProjectConfigSnapshot findProjectContaining(URI sourceLocation) {
		ProjectConfigSnapshot candidate = URIUtils.findInMapByNestedURI(sourceFolderPath2Project, sourceLocation);
		if (candidate != null) {
			// in addition to checking the source folder paths, we have to make sure the source folder actually
			// "contains" the URI as defined by method SourceFolderSnapshot#contains(URI):
			for (SourceFolderSnapshot sourceFolder : candidate.getSourceFolders()) {
				if (sourceFolder.contains(sourceLocation)) {
					return candidate;
				}
			}
		}
		return null;
	}

	/**
	 * Return an empty workspace snapshot.
	 */
	public WorkspaceConfigSnapshot clear() {
		return new WorkspaceConfigSnapshot(getPath(), Collections.emptyList());
	}

	/**
	 * Return an updated version snapshot.
	 */
	public WorkspaceConfigSnapshot update(Iterable<? extends ProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects) {

		Map<String, ProjectConfigSnapshot> lookupName2Project = new HashMap<>(name2Project);
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>(projectPath2Project);
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>(sourceFolderPath2Project);
		updateLookupMaps(lookupName2Project, lookupProjectPath2Project, lookupSourceFolderPath2Project,
				changedProjects, removedProjects);
		return new WorkspaceConfigSnapshot(path, ImmutableBiMap.copyOf(lookupName2Project),
				ImmutableMap.copyOf(lookupProjectPath2Project), ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/** Change the given lookup maps to include the given project changes and removals. */
	protected void updateLookupMaps(
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name2Project == null) ? 0 : name2Project.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((projectPath2Project == null) ? 0 : projectPath2Project.hashCode());
		result = prime * result + ((sourceFolderPath2Project == null) ? 0 : sourceFolderPath2Project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkspaceConfigSnapshot other = (WorkspaceConfigSnapshot) obj;
		if (name2Project == null) {
			if (other.name2Project != null)
				return false;
		} else if (!name2Project.equals(other.name2Project))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (projectPath2Project == null) {
			if (other.projectPath2Project != null)
				return false;
		} else if (!projectPath2Project.equals(other.projectPath2Project))
			return false;
		if (sourceFolderPath2Project == null) {
			if (other.sourceFolderPath2Project != null)
				return false;
		} else if (!sourceFolderPath2Project.equals(other.sourceFolderPath2Project))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WorkspaceConfigSnapshot [path=" + path + ", name2Project=" + name2Project
				+ ", sourceFolderPath2Project=" + sourceFolderPath2Project + "]";
	}

}
