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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

	/** Map from {@link ProjectConfigSnapshot#getName() project name} to the project's configuration snapshot. */
	protected final ImmutableBiMap<String, ? extends ProjectConfigSnapshot> name2Project;
	/** Inverse dependencies, i.e. map from name of a project P to all projects with a dependency to P. */
	protected final ImmutableSetMultimap<String, ? extends ProjectConfigSnapshot> name2DependentProjects;
	/** Keys are URIs <em>without</em> trailing path separator! */
	protected final ImmutableMap<URI, ? extends ProjectConfigSnapshot> projectPath2Project;
	/** Keys are URIs <em>without</em> trailing path separator! */
	protected final ImmutableMap<URI, ? extends ProjectConfigSnapshot> sourceFolderPath2Project;

	/** Create a new {@link ProjectSet} containing the given projects. */
	public ProjectSet(Iterable<? extends ProjectConfigSnapshot> projects) {
		BiMap<String, ProjectConfigSnapshot> lookupName2Project = HashBiMap.create();
		SetMultimap<String, ProjectConfigSnapshot> lookupName2DependentProjects = HashMultimap.create();
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>();
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>();

		updateLookupMaps(lookupName2Project, lookupName2DependentProjects, lookupProjectPath2Project,
				lookupSourceFolderPath2Project, projects, Collections.emptyList());

		this.name2Project = ImmutableBiMap.copyOf(lookupName2Project);
		this.name2DependentProjects = ImmutableSetMultimap.copyOf(lookupName2DependentProjects);
		this.projectPath2Project = ImmutableMap.copyOf(lookupProjectPath2Project);
		this.sourceFolderPath2Project = ImmutableMap.copyOf(lookupSourceFolderPath2Project);
	}

	/** Internal. Create an instance with existing lookup maps. */
	protected ProjectSet(ImmutableBiMap<String, ? extends ProjectConfigSnapshot> name2Project,
			ImmutableSetMultimap<String, ? extends ProjectConfigSnapshot> name2DependentProjects,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> projectPath2Project,
			ImmutableMap<URI, ? extends ProjectConfigSnapshot> sourceFolderPath2Project) {
		this.name2Project = name2Project;
		this.name2DependentProjects = name2DependentProjects;
		this.projectPath2Project = projectPath2Project;
		this.sourceFolderPath2Project = sourceFolderPath2Project;
	}

	/** Return an updated lookup registry with some projects added, changed, or removed. */
	public ProjectSet update(Iterable<? extends ProjectConfigSnapshot> changedProjects,
			Iterable<String> removedProjects) {

		BiMap<String, ProjectConfigSnapshot> lookupName2Project = HashBiMap.create(name2Project);
		SetMultimap<String, ProjectConfigSnapshot> lookupName2DependentProjects = HashMultimap.create(
				name2DependentProjects);
		Map<URI, ProjectConfigSnapshot> lookupProjectPath2Project = new HashMap<>(projectPath2Project);
		Map<URI, ProjectConfigSnapshot> lookupSourceFolderPath2Project = new HashMap<>(sourceFolderPath2Project);

		updateLookupMaps(lookupName2Project, lookupName2DependentProjects, lookupProjectPath2Project,
				lookupSourceFolderPath2Project, changedProjects, removedProjects);

		return new ProjectSet(ImmutableBiMap.copyOf(lookupName2Project),
				ImmutableSetMultimap.copyOf(lookupName2DependentProjects),
				ImmutableMap.copyOf(lookupProjectPath2Project),
				ImmutableMap.copyOf(lookupSourceFolderPath2Project));
	}

	/** Change the given lookup maps to include the given project changes and removals. */
	protected void updateLookupMaps(
			BiMap<String, ProjectConfigSnapshot> lookupName2Project,
			SetMultimap<String, ProjectConfigSnapshot> lookupName2DependentProjects,
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
				for (String dependencyName : oldProject.getDependencies()) {
					lookupName2DependentProjects.remove(dependencyName, oldProject);
				}
				lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(oldProject.getPath()));
				for (SourceFolderSnapshot sourceFolder : oldProject.getSourceFolders()) {
					lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
				}
			}
			for (String dependencyName : project.getDependencies()) {
				lookupName2DependentProjects.put(dependencyName, project);
			}
			lookupProjectPath2Project.put(URIUtils.trimTrailingPathSeparator(project.getPath()), project);
			for (SourceFolderSnapshot sourceFolder : project.getSourceFolders()) {
				lookupSourceFolderPath2Project.put(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()), project);
			}
		}

		// apply updates for removed projects
		for (ProjectConfigSnapshot removedProject : removedProjects) {
			lookupName2Project.remove(removedProject.getName());
			for (String dependencyName : removedProject.getDependencies()) {
				lookupName2DependentProjects.remove(dependencyName, removedProject);
			}
			lookupProjectPath2Project.remove(URIUtils.trimTrailingPathSeparator(removedProject.getPath()));
			for (SourceFolderSnapshot sourceFolder : removedProject.getSourceFolders()) {
				lookupSourceFolderPath2Project.remove(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()));
			}
		}
	}

	/** Tells whether this project set is empty. */
	public boolean isEmpty() {
		return name2Project.isEmpty();
	}

	/** Returns the number of projects in this project set. */
	public int size() {
		return name2Project.values().size();
	}

	/** Tells whether the given project is contained in this set. */
	public boolean contains(ProjectConfigSnapshot object) {
		return name2Project.containsValue(object);
	}

	/** Get all the projects known in this snapshot. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjects() {
		return name2Project.values();
	}

	/** Returns all projects as a map from name to project configuration. */
	public ImmutableBiMap<String, ? extends ProjectConfigSnapshot> getProjectsByName() {
		return name2Project;
	}

	/** Returns the project with the given name or <code>null</code> if not found. */
	public ProjectConfigSnapshot findProjectByName(String name) {
		return name2Project.get(name);
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

	/** Returns all projects that depend on the project with the given name or an empty set if the name is not found. */
	public ImmutableSet<? extends ProjectConfigSnapshot> getProjectsDependingOn(String projectName) {
		return name2DependentProjects.get(projectName);
	}
}
