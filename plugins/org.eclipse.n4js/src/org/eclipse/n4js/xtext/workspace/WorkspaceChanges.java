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
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * This data class contains information about all changes that happened to the workspace setup, which boils down to (1)
 * all removed and all added/changed {@link URI}s. Triggers for changes are (usually) modifications of source code
 * {@link URI}s. Other triggers are modifications in some kind of manifest files that describes the project or workspace
 * setup. These modifications will result in changes to {@link IWorkspaceConfig} and {@link IProjectConfig}, e.g. a
 * project will be added/removed or one of its properties (name, dependency, source folder) will be modified.
 * <p>
 * Instances of {@link WorkspaceChanges} will mainly focus on removed/added/changed {@link URI}s that need to be
 * respected by the builder due to caching of the builder. Other changes that affect the build order (e.g. name,
 * dependencies) are only reflected by {@link #namesOrDependenciesChanged}.
 * <p>
 * All data fields of {@link WorkspaceChanges} (e.g. {@link #removedURIs}) are mutually exclusive, i.e. that
 * {@link URI}s mentioned in {@link #removedURIs} are neither listed in {@link #removedSourceFolders} nor in one of the
 * source folders of #{@link #removedProjects}.
 * <p>
 * At a first glance it seems redundant to provide added/removed data fields on different levels (plain, source folders,
 * projects). However, mind that returning the highest level is always desired (i.e. projects prior to source folders
 * prior to plain uris), but that this might not be possible in all languages/cases. Note that the term <i>removed</i>
 * is used in favor of <i>deleted</i> since being removed from the workspace is necessary to know which can not only
 * happen due to deletion but also due to changes to source folders.
 */
@SuppressWarnings("restriction")
public class WorkspaceChanges {
	/** Singleton instance containing empty change sets only */
	public static final WorkspaceChanges NO_CHANGES = new WorkspaceChanges();

	/** @return a new instance of {@link WorkspaceChanges} contains the given project as removed */
	public static WorkspaceChanges createProjectRemoved(ProjectConfigSnapshot project) {
		return new WorkspaceChanges(false, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(project), ImmutableList.of(), ImmutableList.of());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given project as added */
	public static WorkspaceChanges createProjectAdded(ProjectConfigSnapshot project) {
		return new WorkspaceChanges(false, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(project), ImmutableList.of());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as changed */
	public static WorkspaceChanges createUrisChanged(List<URI> changedURIs) {
		return new WorkspaceChanges(false, ImmutableList.of(), ImmutableList.of(), ImmutableList.copyOf(changedURIs),
				ImmutableList.of(),
				ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as removed */
	public static WorkspaceChanges createUrisRemoved(List<URI> removedURIs) {
		return new WorkspaceChanges(false, ImmutableList.copyOf(removedURIs), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(),
				ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as removed / changed */
	public static WorkspaceChanges createUrisRemovedAndChanged(List<URI> removedURIs, List<URI> changedURIs) {
		return new WorkspaceChanges(false, ImmutableList.copyOf(removedURIs), ImmutableList.copyOf(changedURIs),
				ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
	}

	/** true iff a name or a dependency of a (still existing) project have been modified */
	protected final boolean namesOrDependenciesChanged;
	/** removed uris (excluding those from {@link #removedSourceFolders} and {@link #removedProjects}) */
	protected final ImmutableList<URI> removedURIs;
	/** added uris (excluding those from {@link #addedSourceFolders} and {@link #addedProjects}) */
	protected final ImmutableList<URI> addedURIs;
	/** changed uris */
	protected final ImmutableList<URI> changedURIs;
	/** removed source folders (excluding those from {@link #removedProjects}) */
	protected final ImmutableList<SourceFolderSnapshot> removedSourceFolders;
	/** added source folders (excluding those from {@link #addedProjects}) */
	protected final ImmutableList<SourceFolderSnapshot> addedSourceFolders;
	/** removed projects */
	protected final ImmutableList<ProjectConfigSnapshot> removedProjects;
	/** added projects */
	protected final ImmutableList<ProjectConfigSnapshot> addedProjects;
	/** projects that were neither added nor removed but had their dependencies changed */
	protected final ImmutableList<ProjectConfigSnapshot> projectsWithChangedDependencies;

	/** Constructor */
	public WorkspaceChanges() {
		this(false, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of(), ImmutableList.of(),
				ImmutableList.of());
	}

	/** Constructor */
	public WorkspaceChanges(boolean namesOrDependenciesChanged,
			ImmutableList<URI> removedURIs, ImmutableList<URI> addedURIs, ImmutableList<URI> changedURIs,
			ImmutableList<SourceFolderSnapshot> removedSourceFolders,
			ImmutableList<SourceFolderSnapshot> addedSourceFolders,
			ImmutableList<ProjectConfigSnapshot> removedProjects,
			ImmutableList<ProjectConfigSnapshot> addedProjects,
			ImmutableList<ProjectConfigSnapshot> projectsWithChangedDependencies) {

		this.namesOrDependenciesChanged = namesOrDependenciesChanged;
		this.removedURIs = removedURIs;
		this.addedURIs = addedURIs;
		this.changedURIs = changedURIs;
		this.removedSourceFolders = removedSourceFolders;
		this.addedSourceFolders = addedSourceFolders;
		this.removedProjects = removedProjects;
		this.addedProjects = addedProjects;
		this.projectsWithChangedDependencies = projectsWithChangedDependencies;
	}

	/** @return true iff a name or dependencies of a still existing project changed */
	public boolean isNamesOrDependenciesChanged() {
		return namesOrDependenciesChanged;
	}

	/**
	 * @return all uris that have been removed (excluding those from {@link #removedSourceFolders} and
	 *         {@link #removedProjects})
	 */
	public List<URI> getRemovedURIs() {
		return removedURIs;
	}

	/**
	 * @return all uris that have been added (excluding those from {@link #addedSourceFolders} and
	 *         {@link #addedProjects})
	 */
	public List<URI> getAddedURIs() {
		return addedURIs;
	}

	/** @return all uris that have been changed */
	public List<URI> getChangedURIs() {
		return changedURIs;
	}

	/** @return all source folders that have been removed (excluding those from {@link #removedProjects}) */
	public List<SourceFolderSnapshot> getRemovedSourceFolders() {
		return removedSourceFolders;
	}

	/** @return all source folders that have been added (excluding those from {@link #addedProjects}) */
	public List<SourceFolderSnapshot> getAddedSourceFolders() {
		return addedSourceFolders;
	}

	/** @return all projects that have been removed */
	public List<ProjectConfigSnapshot> getRemovedProjects() {
		return removedProjects;
	}

	/** @return all projects that have been added */
	public List<ProjectConfigSnapshot> getAddedProjects() {
		return addedProjects;
	}

	/** @return that were neither added nor removed but had their dependencies changed */
	public List<ProjectConfigSnapshot> getProjectsWithChangedDependencies() {
		return projectsWithChangedDependencies;
	}

	/** @return a list of all removed source folders including those inside {@link #removedProjects} */
	public List<SourceFolderSnapshot> getAllRemovedSourceFolders() {
		List<SourceFolderSnapshot> sourceFolders = new ArrayList<>(removedSourceFolders);
		for (ProjectConfigSnapshot project : removedProjects) {
			sourceFolders.addAll(project.getSourceFolders());
		}
		return sourceFolders;
	}

	/** @return a list of all added source folders including those inside {@link #addedProjects} */
	public List<SourceFolderSnapshot> getAllAddedSourceFolders() {
		List<SourceFolderSnapshot> sourceFolders = new ArrayList<>(addedSourceFolders);
		for (ProjectConfigSnapshot project : addedProjects) {
			sourceFolders.addAll(project.getSourceFolders());
		}
		return sourceFolders;
	}

	/** @return true iff this instance implies a change of the build order */
	public boolean isBuildOrderAffected() {
		return namesOrDependenciesChanged || !addedProjects.isEmpty() || !removedProjects.isEmpty();
	}

	/** Merges the given changes into a new instance */
	public WorkspaceChanges merge(WorkspaceChanges changes) {
		ImmutableList<ProjectConfigSnapshot> newAdded = concat(addedProjects, changes.addedProjects);
		ImmutableList<ProjectConfigSnapshot> newRemoved = concat(removedProjects, changes.removedProjects);

		ImmutableSet<String> allAddedOrRemoved = FluentIterable.concat(newAdded, newRemoved).transform(p -> p.getName())
				.toSet();

		return new WorkspaceChanges(
				this.namesOrDependenciesChanged || changes.namesOrDependenciesChanged,
				concat(removedURIs, changes.removedURIs),
				concat(addedURIs, changes.addedURIs),
				concat(changedURIs, changes.changedURIs),
				concat(removedSourceFolders, changes.removedSourceFolders),
				concat(addedSourceFolders, changes.addedSourceFolders),
				newRemoved,
				newAdded,
				FluentIterable.concat(projectsWithChangedDependencies, changes.projectsWithChangedDependencies)
						.filter(config -> !allAddedOrRemoved.contains(config.getName())).toList());
	}

	private <T> ImmutableList<T> concat(ImmutableList<T> a, ImmutableList<T> b) {
		return FluentIterable.concat(a, b).toList();
	}

}
