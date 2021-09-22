/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

/**
 * Immutable set of {@link ProjectConfigSnapshot}s, with various lookup maps for fast project access and traversal.
 */
public class ProjectSet {

	/** An empty project set. */
	public static final ProjectSet EMPTY = new ProjectSet(Collections.emptyList());

	/** Map from {@link ProjectConfigSnapshot#getName() project id} to the project's configuration snapshot. */
	protected final ImmutableBiMap<String, ? extends ProjectConfigSnapshot> id2Project;
	/** Inverse dependencies, i.e. map from id of a project P to all projects with a dependency to P. */
	protected final ImmutableSetMultimap<String, ? extends ProjectConfigSnapshot> id2DependentProjects;
	/** Keys are URIs <em>without</em> trailing path separator! */
	protected final ImmutableMap<URI, ? extends ProjectConfigSnapshot> projectPath2Project;
	/** Keys are URIs <em>without</em> trailing path separator! */
	protected final ImmutableMap<URI, ? extends ProjectConfigSnapshot> sourceFolderPath2Project;

	/** Create a new {@link ProjectSet} containing the given projects. */
	public ProjectSet(Iterable<? extends ProjectConfigSnapshot> projects) {
		BiMap<String, ProjectConfigSnapshot> lookupId2Project = HashBiMap.create();
		SetMultimap<String, ProjectConfigSnapshot> lookupId2DependentProjects = HashMultimap.create();
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();

		updateLookupMaps(lookupId2Project, lookupId2DependentProjects, lookupProjectPath2Project,
				lookupSourceFolderPath2Project, projects, Collections.emptyList());

		this.id2Project = ImmutableBiMap.copyOf(lookupId2Project);
		this.id2DependentProjects = ImmutableSetMultimap.copyOf(lookupId2DependentProjects);
		this.projectPath2Project = ImmutableMap.copyOf(lookupProjectPath2Project);
		this.sourceFolderPath2Project = ImmutableMap.copyOf(lookupSourceFolderPath2Project);
	}

	/** Internal. Create an instance with existing lookup maps. */
	protected ProjectSet(ImmutableBiMap<String, ? extends ProjectConfigSnapshot> id2Project,
			ImmutableSetMultimap<String, ? extends ProjectConfigSnapshot> id2DependentProjects,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> sourceFolderPath2Project) {
		this.id2Project = id2Project;
		this.id2DependentProjects = id2DependentProjects;
		this.projectPath2Project = projectPath2Project;
		this.sourceFolderPath2Project = sourceFolderPath2Project;
	}

	/**
	 * Return an updated lookup registry with some projects added, changed, or removed.
	 *
	 * <h2>Same Project Name In Both Arguments</h2>
	 *
	 * In case {@code changedProjects} contains a project description with a project id that is also in
	 * {@code removedProjects}, it is assumed that a project of that project id was first removed and then re-created,
	 * possibly with a different path. This special case has two important real-world use cases:
	 * <ol>
	 * <li>a project is moved and all related updates are sent to the LSP server in a single
	 * {@code didChangeWatchedFiles} notification.
	 * <li>a project is created or deleted in the "packages" folder that shadows an existing project in the
	 * <code>node_modules</code> folder; this amounts to an event chain similar to case 1.
	 * </ol>
	 * <p>
	 * Note that the case of a project being created and then immediately deleted within a single notification cannot be
	 * represented by the parameters to this method. Client code is expected to recognize this case and not produce an
	 * update for such a change at all.
	 *
	 * @param changedProjects
	 *            newly added projects and projects with a changed configuration (i.e. one of the properties in
	 *            {@link ProjectConfigSnapshot} has changed).
	 * @param removedProjects
	 *            ids of removed projects. Removals are assumed to have happened before the changes defined in
	 *            {@code changedProjects} (see details above).
	 */
	public ProjectSet update(Iterable<? extends ProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects) {

		BiMap<String, ProjectConfigSnapshot> lookupId2Project = HashBiMap.create(id2Project);
		SetMultimap<String, ProjectConfigSnapshot> lookupId2DependentProjects = HashMultimap.create(
				id2DependentProjects);
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>(projectPath2Project);
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>(sourceFolderPath2Project);

		updateLookupMaps(lookupId2Project, lookupId2DependentProjects, lookupProjectPath2Project,
				lookupSourceFolderPath2Project, changedProjects, removedProjects);

		return new ProjectSet(ImmutableBiMap.copyOf(lookupId2Project),
				ImmutableSetMultimap.copyOf(lookupId2DependentProjects),
				ImmutableMap.copyOf(lookupProjectPath2Project),
				ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/**
	 * Change the given lookup maps to include the given project changes and removals.
	 * <p>
	 * For important notes on the meaning of the two parameters {@code changedProjects} and {@code removedProjects}, see
	 * method {@link #update(Iterable, Iterable)}.
	 */
	protected void updateLookupMaps(
			BiMap<String, ProjectConfigSnapshot> lookupId2Project,
			SetMultimap<String, ProjectConfigSnapshot> lookupId2DependentProjects,
			Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project,
			Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project,
			Iterable<? extends ProjectConfigSnapshot> changedProjects, Iterable<String> removedProjectIDs) {

		// apply updates for removed projects (this must be done first!)
		for (String projectID : removedProjectIDs) {
			ProjectConfigSnapshot removedProject = lookupId2Project.get(projectID);
			if (removedProject != null) {
				lookupId2Project.remove(removedProject.getName());
				for (String dependencyID : removedProject.getDependencies()) {
					lookupId2DependentProjects.remove(dependencyID, removedProject);
				}
				lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(removedProject.getPath()));
				for (SourceFolderSnapshot sourceFolder : removedProject.getSourceFolders()) {
					lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
				}
			}
		}

		// apply updates for added/changed projects
		for (ProjectConfigSnapshot project : changedProjects) {
			ProjectConfigSnapshot oldProject = lookupId2Project.put(project.getName(), project);
			if (oldProject != null) {
				for (String dependencyID : oldProject.getDependencies()) {
					lookupId2DependentProjects.remove(dependencyID, oldProject);
				}
				lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(oldProject.getPath()));
				for (SourceFolderSnapshot sourceFolder : oldProject.getSourceFolders()) {
					lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
				}
			}
			for (String dependencyID : project.getDependencies()) {
				lookupId2DependentProjects.put(dependencyID, project);
			}
			lookupProjectPath2Project.put(URIUtils.trimTrailingPathSeparator(project.getPath()), project);
			for (SourceFolderSnapshot sourceFolder : project.getSourceFolders()) {
				lookupSourceFolderPath2Project.put(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()), project);
			}
		}
	}

	/** Tells whether this project set is empty. */
	public boolean isEmpty() {
		return id2Project.isEmpty();
	}

	/** Returns the number of projects in this project set. */
	public int size() {
		return id2Project.values().size();
	}

	/** Tells whether the given project is contained in this set. */
	public boolean contains(ProjectConfigSnapshot object) {
		return id2Project.containsValue(object);
	}

	/** Get all the projects known in this snapshot. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjects() {
		return id2Project.values();
	}

	/** Returns all projects as a map from project id to project configuration. */
	public ImmutableBiMap<String, ? extends ProjectConfigSnapshot> getProjectsByProjectID() {
		return id2Project;
	}

	/** Returns the project with the given project id or <code>null</code> if not found. */
	public ProjectConfigSnapshot findProjectByProjectID(String projectID) {
		return id2Project.get(projectID);
	}

	/**
	 * Returns the project with the given exact project path or <code>null</code> if not found.
	 * <p>
	 * Does <em>not</em> find projects for nested locations.
	 */
	public ProjectConfigSnapshot findProjectByPath(URI path) {
		return projectPath2Project.get(URIUtils.trimTrailingPathSeparator(path));
	}

	/**
	 * Finds the project having a path that is a prefix of the given URI. Returns <code>null</code> if no such project
	 * is found. The given URI may point to a file or folder.
	 * <p>
	 * Note the difference to {@link #findProjectContaining(URI)} which will return <code>P</code> only when given a URI
	 * that is {@link SourceFolderSnapshot#contains(URI) actually contained} in one of <code>P</code>'s source folders,
	 * whereas this method will return a project <code>P</code> when given any URI denoting a file/folder below the path
	 * of <code>P</code>.
	 */
	public ProjectConfigSnapshot findProjectByNestedLocation(URI nestedLocation) {
		return URIUtils.findInMapByNestedURI(projectPath2Project, nestedLocation);
	}

	/**
	 * Finds the project having a source folder that {@link SourceFolderSnapshot#contains(URI) actually contains} the
	 * given nested URI. Returns <code>null</code> if no such project is found. The given URI may point to a file or
	 * folder.
	 * <p>
	 * Note the difference to {@link #findProjectByNestedLocation(URI)} which will return a project <code>P</code> when
	 * given any URI denoting a file/folder below the path of <code>P</code>, whereas this method will return
	 * <code>P</code> only when given a URI that is {@link SourceFolderSnapshot#contains(URI) actually contained} in one
	 * of <code>P</code>'s source folders.
	 *
	 * @see SourceFolderSnapshot#contains(URI)
	 * @see XIWorkspaceConfig#findProjectContaining(URI)
	 */
	public ProjectConfigSnapshot findProjectContaining(URI nestedSourceLocation) {
		Pair<ProjectConfigSnapshot, SourceFolderSnapshot> result = findProjectAndSourceFolderContaining(
				nestedSourceLocation);
		return result != null ? result.getKey() : null;
	}

	/** Same as {@link #findProjectContaining(URI)}, but returns the containing source folder instead of project. */
	public SourceFolderSnapshot findSourceFolderContaining(URI nestedSourceLocation) {
		Pair<ProjectConfigSnapshot, SourceFolderSnapshot> result = findProjectAndSourceFolderContaining(
				nestedSourceLocation);
		return result != null ? result.getValue() : null;
	}

	/** Same as {@link #findProjectContaining(URI)}, but returns both the containing project and source folder. */
	public Pair<ProjectConfigSnapshot, SourceFolderSnapshot> findProjectAndSourceFolderContaining(
			URI nestedSourceLocation) {
		ProjectConfigSnapshot candidate = URIUtils.findInMapByNestedURI(sourceFolderPath2Project, nestedSourceLocation);
		if (candidate != null) {
			// in addition to checking the source folder paths, we have to make sure the source folder actually
			// "contains" the URI as defined by method SourceFolderSnapshot#contains(URI) (note that based on the
			// API doc of #findProjectContaining(), this is necessary even if we a are only interested in the project!):
			SourceFolderSnapshot sourceFolder = candidate.findSourceFolderContaining(nestedSourceLocation);
			if (sourceFolder != null) {
				return Pair.of(candidate, sourceFolder);
			}
		}
		return null;
	}

	/**
	 * Returns all projects that depend on the project with the given project id or an empty set if the project id is
	 * not found.
	 */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjectsDependingOn(String projectID) {
		return id2DependentProjects.get(projectID);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		if (isEmpty()) {
			sb.append("]");
		} else {
			int numOmitted = 0;
			for (ProjectConfigSnapshot p : getProjects()) {
				if (p.isOmittedFromToString()) {
					numOmitted++;
					continue;
				}
				sb.append("\n    ");
				sb.append(p.toStringBrief());
			}
			if (numOmitted > 0) {
				sb.append("\n    ");
				sb.append("(... " + numOmitted + " project(s) omitted ...)");
			}
			sb.append("\n]");
		}
		return sb.toString();
	}
}
