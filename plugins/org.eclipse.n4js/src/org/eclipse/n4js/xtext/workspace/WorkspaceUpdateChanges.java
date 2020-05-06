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

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * This data class contains information about all changes that happened to the workspace setup, which boils down to (1)
 * all deleted and all changed {@link URI}s. Triggers for changes are (usually) not modifications of source code
 * {@link URI}s. Instead the assumption is that the triggers are modifications in some kind of manifest files that
 * describes the project or workspace setup. These modifications will result in changes to {@link IWorkspaceConfig} and
 * {@link IProjectConfig}, e.g. a project will be added/removed or one of its properties (name, dependency, source
 * folder) will be modified.
 * <p>
 * Instances of {@link WorkspaceUpdateChanges} will mainly focus on deleted/changed {@link URI}s that need to be
 * respected by the builder due to caching of the builder. Other changes that affect the build order (e.g. name,
 * dependencies) are only reflected by {@link #namesOrDependenciesChanged}.
 * <p>
 * All data fields of {@link WorkspaceUpdateChanges} (e.g. {@link #removedURIs}) are mutually exclusive, i.e. that
 * {@link URI}s mentioned in {@link #removedURIs} are neither listed in {@link #removedSourceFolders} nor in one of the
 * source folders of #{@link #removedProjects}.
 * <p>
 * At a first glance it seems redundant to provide added/removed data fields on different levels (plain, source folders,
 * projects). However, mind that returning the highest level is always desired (i.e. projects prior to source folders
 * prior to plain uris), but that this might not be possible in all languages/cases.
 */
@SuppressWarnings("restriction")
public class WorkspaceUpdateChanges {
	static final public WorkspaceUpdateChanges NO_CHANGES = new WorkspaceUpdateChanges();

	/** true iff a name or a dependency of a (still existing) project have been modified */
	protected boolean namesOrDependenciesChanged;
	protected List<URI> removedURIs;
	protected List<URI> addedURIs;
	protected List<ISourceFolder> removedSourceFolders;
	protected List<ISourceFolder> addedSourceFolders;
	protected List<IProjectConfig> removedProjects;
	protected List<IProjectConfig> addedProjects;

	/** Constructor */
	public WorkspaceUpdateChanges() {
		this(false, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
	}

	/** Constructor */
	public WorkspaceUpdateChanges(boolean namesOrDependenciesChanged, List<URI> removedURIs, List<URI> addedURIs,
			List<ISourceFolder> removedSourceFolders, List<ISourceFolder> addedSourceFolders,
			List<IProjectConfig> removedProjects, List<IProjectConfig> addedProjects) {

		this.namesOrDependenciesChanged = namesOrDependenciesChanged;
		this.removedURIs = removedURIs;
		this.addedURIs = addedURIs;
		this.removedSourceFolders = removedSourceFolders;
		this.addedSourceFolders = addedSourceFolders;
		this.removedProjects = removedProjects;
		this.addedProjects = addedProjects;
	}

	public boolean isNamesOrDependenciesChanged() {
		return namesOrDependenciesChanged;
	}

	public List<URI> getRemovedURIs() {
		return removedURIs;
	}

	public List<URI> getAddedURIs() {
		return addedURIs;
	}

	public List<ISourceFolder> getRemovedSourceFolders() {
		return removedSourceFolders;
	}

	public List<ISourceFolder> getAddedSourceFolders() {
		return addedSourceFolders;
	}

	public List<IProjectConfig> getRemovedProjects() {
		return removedProjects;
	}

	public List<IProjectConfig> getAddedProjects() {
		return addedProjects;
	}

	public List<ISourceFolder> getAllAddedSourceFolders() {
		List<ISourceFolder> sourceFolders = new ArrayList<>(addedSourceFolders);
		for (IProjectConfig project : addedProjects) {
			sourceFolders.addAll(project.getSourceFolders());
		}
		return sourceFolders;
	}

	public List<URI> getChangedURIs(IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>(addedURIs);
		for (ISourceFolder sourceFolder : getAllAddedSourceFolders()) {
			uris.addAll(sourceFolder.getAllResources(scanner));
		}
		return uris;
	}

	public boolean isBuildOrderAffected() {
		return namesOrDependenciesChanged || !addedProjects.isEmpty() || !removedProjects.isEmpty();
	}

}