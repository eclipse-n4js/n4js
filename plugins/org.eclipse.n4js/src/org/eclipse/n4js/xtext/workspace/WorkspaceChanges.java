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

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
	public static WorkspaceChanges createProjectRemoved(IProjectConfig project) {
		return new WorkspaceChanges(false, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
				singletonList(project), emptyList());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given project as added */
	public static WorkspaceChanges createProjectAdded(IProjectConfig project) {
		return new WorkspaceChanges(false, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
				emptyList(), singletonList(project));
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as changed */
	public static WorkspaceChanges createUrisChanged(List<URI> changedURIs) {
		return new WorkspaceChanges(false, emptyList(), emptyList(), changedURIs, emptyList(), emptyList(),
				emptyList(), emptyList());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as removed */
	public static WorkspaceChanges createUrisRemoved(List<URI> removedURIs) {
		return new WorkspaceChanges(false, removedURIs, emptyList(), emptyList(), emptyList(), emptyList(),
				emptyList(), emptyList());
	}

	/** @return a new instance of {@link WorkspaceChanges} contains the given uris as removed / changed */
	public static WorkspaceChanges createUrisRemovedAndChanged(List<URI> removedURIs, List<URI> changedURIs) {
		return new WorkspaceChanges(false, removedURIs, changedURIs, emptyList(), emptyList(), emptyList(),
				emptyList(), emptyList());
	}

	/** true iff a name or a dependency of a (still existing) project have been modified */
	protected boolean namesOrDependenciesChanged;
	/** removed uris (excluding those from {@link #removedSourceFolders} and {@link #removedProjects}) */
	protected List<URI> removedURIs;
	/** added uris (excluding those from {@link #addedSourceFolders} and {@link #addedProjects}) */
	protected List<URI> addedURIs;
	/** changed uris */
	protected List<URI> changedURIs;
	/** removed source folders (excluding those from {@link #removedProjects}) */
	protected List<ISourceFolder> removedSourceFolders;
	/** added source folders (excluding those from {@link #addedProjects}) */
	protected List<ISourceFolder> addedSourceFolders;
	/** removed projects */
	protected List<IProjectConfig> removedProjects;
	/** added projects */
	protected List<IProjectConfig> addedProjects;

	/** Constructor */
	public WorkspaceChanges() {
		this(false, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
	}

	/** Constructor */
	public WorkspaceChanges(boolean namesOrDependenciesChanged,
			List<URI> removedURIs, List<URI> addedURIs, List<URI> changedURIs,
			List<ISourceFolder> removedSourceFolders, List<ISourceFolder> addedSourceFolders,
			List<IProjectConfig> removedProjects, List<IProjectConfig> addedProjects) {

		this.namesOrDependenciesChanged = namesOrDependenciesChanged;
		this.removedURIs = removedURIs;
		this.addedURIs = addedURIs;
		this.changedURIs = changedURIs;
		this.removedSourceFolders = removedSourceFolders;
		this.addedSourceFolders = addedSourceFolders;
		this.removedProjects = removedProjects;
		this.addedProjects = addedProjects;
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
	public List<ISourceFolder> getRemovedSourceFolders() {
		return removedSourceFolders;
	}

	/** @return all source folders that have been added (excluding those from {@link #addedProjects}) */
	public List<ISourceFolder> getAddedSourceFolders() {
		return addedSourceFolders;
	}

	/** @return all projects that have been removed */
	public List<IProjectConfig> getRemovedProjects() {
		return removedProjects;
	}

	/** @return all projects that have been added */
	public List<IProjectConfig> getAddedProjects() {
		return addedProjects;
	}

	/** @return a list of all removed source folders including those inside {@link #removedProjects} */
	public List<ISourceFolder> getAllRemovedSourceFolders() {
		List<ISourceFolder> sourceFolders = new ArrayList<>(removedSourceFolders);
		for (IProjectConfig project : removedProjects) {
			sourceFolders.addAll(project.getSourceFolders());
		}
		return sourceFolders;
	}

	/** @return a list of all added source folders including those inside {@link #addedProjects} */
	public List<ISourceFolder> getAllAddedSourceFolders() {
		List<ISourceFolder> sourceFolders = new ArrayList<>(addedSourceFolders);
		for (IProjectConfig project : addedProjects) {
			sourceFolders.addAll(project.getSourceFolders());
		}
		return sourceFolders;
	}

	/**
	 * Note that scanning in source folders of {@link #getAllRemovedSourceFolders()} might retrieve non-reliable results
	 * in case the underlying resources have been actually already deleted.
	 *
	 * @return a list of all {@link URI}s that have been removed including those inside
	 *         {@link #getAllRemovedSourceFolders()}
	 */
	public List<URI> scanAllRemovedURIs(IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>(removedURIs);
		for (ISourceFolder sourceFolder : getAllRemovedSourceFolders()) {
			uris.addAll(sourceFolder.getAllResources(scanner));
		}
		return uris;
	}

	/**
	 * @return a list of all {@link URI}s that have been added including those inside
	 *         {@link #getAllAddedSourceFolders()}
	 */
	public List<URI> scanAllAddedURIs(IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>(addedURIs);
		for (ISourceFolder sourceFolder : getAllAddedSourceFolders()) {
			uris.addAll(sourceFolder.getAllResources(scanner));
		}
		return uris;
	}

	/**
	 * @return a list of all {@link URI}s that have been changed / added including those of
	 *         {@link #scanAllAddedURIs(IFileSystemScanner)}
	 */
	public List<URI> scanAllAddedAndChangedURIs(IFileSystemScanner scanner) {
		List<URI> allChangedURIs = Lists.newArrayList(Iterables.concat(scanAllAddedURIs(scanner), getChangedURIs()));
		return allChangedURIs;
	}

	/** @return true iff this instance implies a change of the build order */
	public boolean isBuildOrderAffected() {
		return namesOrDependenciesChanged || !addedProjects.isEmpty() || !removedProjects.isEmpty();
	}

	/** Merges the given changes into this instance */
	public void merge(WorkspaceChanges changes) {
		this.namesOrDependenciesChanged |= changes.namesOrDependenciesChanged;
		this.removedURIs = newArrayList(concat(this.removedURIs, changes.removedURIs));
		this.addedURIs = newArrayList(concat(this.addedURIs, changes.addedURIs));
		this.changedURIs = newArrayList(concat(this.changedURIs, changes.changedURIs));
		this.removedSourceFolders = newArrayList(concat(this.removedSourceFolders, changes.removedSourceFolders));
		this.addedSourceFolders = newArrayList(concat(this.addedSourceFolders, changes.addedSourceFolders));
		this.removedProjects = newArrayList(concat(this.removedProjects, changes.removedProjects));
		this.addedProjects = newArrayList(concat(this.addedProjects, changes.addedProjects));
	}

}