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

import java.util.List;

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
	/** Project build order */
	protected final ProjectBuildOrderInfo projectBuildOrderInfo;

	/** See {@link WorkspaceConfigSnapshot}. */
	public WorkspaceConfigSnapshot(URI path, ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ProjectConfigSnapshot> sourceFolderPath2Project,
			ProjectBuildOrderFactory projectBuildOrderFactory) {

		this.path = path;
		this.name2Project = name2Project;
		this.projectPath2Project = projectPath2Project;
		this.sourceFolderPath2Project = sourceFolderPath2Project;
		this.projectBuildOrderInfo = projectBuildOrderFactory.compute(this);
	}

	/** Getter for the root path. */
	public URI getPath() {
		return path;
	}

	/** Get all the projects known in this snapshot. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjects() {
		return name2Project.values();
	}

	/** Get build order of all projects of this workspace snapshot */
	public ProjectBuildOrderInfo getProjectBuildOrderInfo() {
		return projectBuildOrderInfo;
	}

	/** Find the project with the given name. */
	public ProjectConfigSnapshot findProjectByName(String name) {
		return name2Project.get(name);
	}

	/**
	 * Finds the project having a path that is a prefix of the given URI. Returns <code>null</code> if no such project
	 * is found. The given URI may point to a file or folder.
	 * <p>
	 * Note the difference to {@link #findProjectContaining(URI)}: this method will return a project <code>P</code> when
	 * given any URI denoting a file/folder below the path of <code>P</code>, whereas {@code #findProjectContaining()}
	 * will return <code>P</code> only when given URIs {@link SourceFolderSnapshot#contains(URI) actually contained} in
	 * one of <code>P</code>'s source folders.
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

	/** Return true iff there exist projects in this workspace that have a dependency cycle */
	public boolean hasDependencyCycle() {
		return !projectBuildOrderInfo.projectCycles.isEmpty();
	}

	/** Return true iff the given project is part of a dependency cycle */
	public boolean isInDependencyCycle(String projectName) {
		for (List<String> projectCycle : projectBuildOrderInfo.projectCycles) {
			if (projectCycle.contains(projectName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name2Project == null) ? 0 : name2Project.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		// note: no need to consider "projectBuildOrderInfo" and the lookup maps "projectPath2Project" and
		// "sourceFolderPath2Project"
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
		// note: no need to check "projectBuildOrderInfo" and the lookup maps "projectPath2Project" and
		// "sourceFolderPath2Project"
		return true;
	}

	@Override
	public String toString() {
		return "WorkspaceConfigSnapshot [path=" + path + ", name2Project=" + name2Project
				+ ", sourceFolderPath2Project=" + sourceFolderPath2Project + "]";
	}

}
