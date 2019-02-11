/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Manager for project location aware working sets.
 */
public class ProjectLocationAwareWorkingSetManager extends WorkingSetManagerImpl {
	private final WorkspaceRepositoriesProvider repositoriesProvider;

	private static final Path WS_ROOT_PATH = getWorkspace().getRoot().getLocation().toFile().toPath();
	private final Multimap<String, IProject> projectLocations = HashMultimap.create();

	// nature id to identify the RemoteSystemsTempFiles project
	private static final String REMOTE_EDIT_PROJECT_NATURE_ID = "org.eclipse.rse.ui.remoteSystemsTempNature";

	/**
	 * List of all the repository paths the IDE is currently aware of. This field is initialized by
	 * {@link #initProjectLocation()}.
	 */
	private Collection<Path> repositoryPaths;

	/**
	 * Sole constructor for creating a new working set manager instance.
	 */
	@Inject
	public ProjectLocationAwareWorkingSetManager(WorkspaceRepositoriesProvider repositoriesProvider) {
		this.repositoriesProvider = repositoriesProvider;

		// reload on workspace repository changes
		this.repositoriesProvider.addWorkspaceRepositoriesChangedListener(repos -> this.reload());
	}

	@Override
	public String getLabel() {
		return "Project Location";
	}

	@Override
	public Optional<Image> getImage() {
		return ImageRef.URL_LOCATION.asImage();
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		if (projectLocations.isEmpty()) {
			projectLocations.putAll(initProjectLocation());
		}
		return newArrayList(from(projectLocations.keySet()).transform(id -> new ProjectLocationWorkingSet(id, this)));
	}

	@Override
	public void discardWorkingSetCaches() {
		super.discardWorkingSetCaches();
		projectLocations.clear();
	}

	private Multimap<String, IProject> initProjectLocation() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject[] projects = root.getProjects();
		final Multimap<String, IProject> locations = HashMultimap.create();

		// initialize the repository paths
		repositoryPaths = repositoriesProvider.getWorkspaceRepositories().stream()
				.map(r -> r.getDirectory().getParentFile().toPath()).collect(Collectors.toSet());

		for (final IProject project : projects) {
			if (isRemoteEditNature(project)) {
				continue;
			}
			final String pair = getWorkingSetId(project);
			locations.put(pair, project);
		}

		return locations;
	}

	/**
	 * Returns {@code true} if the given project is of the
	 * {@link ProjectLocationAwareWorkingSetManager#REMOTE_EDIT_PROJECT_NATURE_ID} nature.
	 */
	private boolean isRemoteEditNature(IProject project) {
		try {
			if (project.hasNature(REMOTE_EDIT_PROJECT_NATURE_ID)) {
				return true;
			}
		} catch (CoreException e) {
			return false;
		}
		return false;
	}

	private String getWorkingSetId(final IProject project) {
		final Path projectPath = project.getLocation().toFile().toPath();
		final Path parentPath = projectPath.getParent();

		if (WS_ROOT_PATH.equals(parentPath)) {
			return OTHERS_WORKING_SET_ID;
		}

		if (parentPath.startsWith(WS_ROOT_PATH)) {
			return parentPath.toFile().getName();
		}

		if (!project.isOpen()) { // closed project appear under Other Projects WS
			return OTHERS_WORKING_SET_ID;
		}

		for (final Path repositoryPath : repositoryPaths) {
			if (repositoryPath.equals(projectPath)) {
				return projectPath.toFile().getName();
			} else if (projectPath.startsWith(repositoryPath)) {
				return parentPath.toFile().getName();
			}
		}

		return OTHERS_WORKING_SET_ID;
	}

	/**
	 * Working set for projects based on their location.
	 */
	public static final class ProjectLocationWorkingSet extends WorkingSetImpl {

		/**
		 * Creates a new working set manager with the unique ID and the container manager.
		 *
		 * @param id
		 *            the unique ID of the working set. This ID is calculated by the container manager.
		 * @param manager
		 *            the container manager.
		 */
		@VisibleForTesting
		public ProjectLocationWorkingSet(final String id, final ProjectLocationAwareWorkingSetManager manager) {
			super(id, manager);
		}

		@Override
		public ProjectLocationAwareWorkingSetManager getWorkingSetManager() {
			return (ProjectLocationAwareWorkingSetManager) super.getWorkingSetManager();
		}

		@Override
		public IAdaptable[] getElements() {
			return Iterables.toArray(getWorkingSetManager().projectLocations.get(getId()), IAdaptable.class);
		}

	}

}
