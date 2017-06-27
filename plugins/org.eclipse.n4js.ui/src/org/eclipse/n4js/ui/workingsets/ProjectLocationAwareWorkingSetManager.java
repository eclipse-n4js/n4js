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
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.swt.graphics.Image;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;

/**
 * Manager for project location aware working sets.
 */
@SuppressWarnings("restriction")
public class ProjectLocationAwareWorkingSetManager extends WorkingSetManagerImpl implements IDeferredInitializer {

	private static final Path WS_ROOT_PATH = getWorkspace().getRoot().getLocation().toFile().toPath();

	private final Multimap<String, IProject> projectLocations;

	private boolean deferredInitializerSucceeded = false;

	/**
	 * Sole constructor for creating a new working set manager instance.
	 */
	public ProjectLocationAwareWorkingSetManager() {
		projectLocations = initProjectLocation();
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
	protected void discardWorkingSetState() {
		super.discardWorkingSetState();
		projectLocations.clear();
	}

	private Multimap<String, IProject> initProjectLocation() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject[] projects = root.getProjects();
		final Multimap<String, IProject> locations = HashMultimap.create();
		for (final IProject project : projects) {
			final String pair = getWorkingSetId(project);
			locations.put(pair, project);
		}

		if (!deferredInitializerSucceeded) // only once ever.
		{
			// assume not properly initialized if only "other projects" is available as key.
			deferredInitializerSucceeded = locations.keySet().size() > 1;
		}

		return locations;
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

		final Collection<Path> repositoryPaths = from(asList(getRepositoryCache().getAllRepositories()))
				.transform(r -> r.getDirectory().getParentFile().toPath()).toSet();

		for (final Path repositoryPath : repositoryPaths) {
			if (repositoryPath.equals(projectPath)) {
				return projectPath.toFile().getName();
			} else if (projectPath.startsWith(repositoryPath)) {
				return parentPath.toFile().getName();
			}
		}

		return OTHERS_WORKING_SET_ID;
	}

	private RepositoryCache getRepositoryCache() {
		return Activator.getDefault().getRepositoryCache();
	}

	@Override
	public boolean isInitializationRequired() {
		return !deferredInitializerSucceeded;
	}

	@Override
	public boolean lateInit() {

		if (deferredInitializerSucceeded) {
			return true;
		}

		discardWorkingSetState();
		restoreState(new NullProgressMonitor());

		return deferredInitializerSucceeded;
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
