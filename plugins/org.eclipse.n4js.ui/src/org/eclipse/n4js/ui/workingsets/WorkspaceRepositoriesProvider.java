/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.project.GitProjectData;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;

import com.google.inject.Singleton;

/**
 * The {@link WorkspaceRepositoriesProvider} allows to access the set of git repositories that are associated with
 * workspace projects.
 *
 * It is also possible to register listeners to listen for changes regarding git repositories.
 *
 * This is different fromm {@link RepositoryCache} in that it is always in sync with the (open) workspace projects,
 * whereas {@link RepositoryCache} might also contain repositories that have already been removed. It also allows to
 * listen for changes.
 */
@Singleton
@SuppressWarnings("restriction") /* To access GitProjectData */
public class WorkspaceRepositoriesProvider {
	private static final Logger LOGGER = Logger.getLogger(WorkspaceRepositoriesProvider.class);

	/**
	 * Listener for workspace repository changes.
	 */
	public static interface WorkspaceRepositoriesChangedListener {
		/**
		 * This listener method is invoked whenever the value of {@link #getWorkspaceRepositories()} changes.
		 */
		public void workspaceRepositoriesChanged(Set<Repository> repositories);
	}

	// Keeps track of existing working sets for repositories.
	// Might be {@code null} if this manager is uninitialized
	private Set<Repository> knownRepositories;
	final private IWorkspaceRoot workspaceRoot;

	// collection of listeners to notify on workspace repository changes
	private final Collection<WorkspaceRepositoriesChangedListener> changeListeners;

	/** Initializes a new {@link WorkspaceRepositoriesProvider}. */
	public WorkspaceRepositoriesProvider() {
		workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		changeListeners = new HashSet<>();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this::resourceChanged,
				IResourceChangeEvent.POST_CHANGE);

		GitProjectData.addRepositoryChangeListener(this::repositoryChanged);
	}

	/**
	 * Returns the set of repositories that are currently associated with workspace projects.
	 */
	public Set<Repository> getWorkspaceRepositories() {
		knownRepositories = getWorkspaceProjects().stream()
				.filter(p -> p.isOpen()) // do not include project mapping for closed projects
				.map(GitProjectData::get) // get project data for project
				.filter(Objects::nonNull) // filter null project data (no project associated or not yet initialized)
				.map(WorkspaceRepositoriesProvider::getOfProjectData) // get mapping for project
				.map(RepositoryMapping::getRepository) // get repository of mapping
				.collect(Collectors.toSet()); // return set of distinct repositories

		return knownRepositories;
	}

	/**
	 * Sends a workspace repositories changed event to all {@link #changeListeners}.
	 */
	private void fireWorkspaceRepositoriesChanged() {
		Set<Repository> newValue = getWorkspaceRepositories();
		changeListeners.forEach(listener -> listener.workspaceRepositoriesChanged(newValue));
	}

	/**
	 * Adds a new listener that is invoked whenever the value of {@link #getWorkspaceRepositories()} changes.
	 */
	public void addWorkspaceRepositoriesChangedListener(WorkspaceRepositoriesChangedListener listener) {
		changeListeners.add(checkNotNull(listener, "listener must not be null"));
	}

	/**
	 * Removes the given workspace repositories changed listener.
	 */
	public void removeWorkspaceRepositoriesChangedListener(WorkspaceRepositoriesChangedListener listener) {
		changeListeners.remove(checkNotNull(listener, "listener must not be null"));
	}

	/**
	 * Returns the repository mapping of the project itself the given projectData is associated with.
	 */
	private static RepositoryMapping getOfProjectData(GitProjectData projectData) {
		return projectData.getRepositoryMapping(projectData.getProject());
	}

	/**
	 * Returns a list of all projects in the current workspace.
	 */
	private List<IProject> getWorkspaceProjects() {
		return Arrays.asList(workspaceRoot.getProjects());
	}

	/** invoked for each repository mapping change */
	private void repositoryChanged(RepositoryMapping which) {
		// if a so-far unknown repository appears in a changed mapping
		@SuppressWarnings("resource")
		Repository repository = which.getRepository();
		if (null != knownRepositories && !knownRepositories.contains(repository)) {
			// trigger workspace repositories changed listener
			this.fireWorkspaceRepositoriesChanged();
		}
	}

	/**
	 * Listener method that is invoked for every resource change in the workspace
	 */
	private void resourceChanged(IResourceChangeEvent event) {
		try {
			event.getDelta().accept(this::handleResourceDelta);
		} catch (CoreException e) {
			LOGGER.error("Error occurred while handling resource change event.", e);
		}
	}

	/**
	 * Handles the given resource delta.
	 *
	 * Fires workspace repository changed event if a project was opened/closed or a project was added/removed.
	 */
	private boolean handleResourceDelta(IResourceDelta delta) {
		IResource affectedResource = delta.getResource();

		if (affectedResource instanceof IProject) {
			// check if kind is CHANGED
			if ((delta.getKind() & IResourceDelta.CHANGED) == IResourceDelta.CHANGED) {
				// if project was opened/closed
				if ((delta.getFlags() & IResourceDelta.OPEN) == IResourceDelta.OPEN) {
					fireWorkspaceRepositoriesChanged();
					return false;
				}
			}
			// check if kind is ADDED or REMOVED
			else if ((delta.getKind() & IResourceDelta.ADDED | IResourceDelta.REMOVED) != 0) {
				fireWorkspaceRepositoriesChanged();
				return false;
			}
		}
		return true;
	}
}
