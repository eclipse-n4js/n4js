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

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.toMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.io.File.pathSeparator;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.core.project.GitProjectData;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

/**
 * Working set manager based on Git repositories.
 */
@SuppressWarnings("restriction")
public class GitRepositoryAwareWorkingSetManager extends WorkingSetManagerImpl {

	private final RepositoryCache repositoryCache;
	private final IPreferenceChangeListener repositoryChangeListener;

	// Keeps track of existing working sets for repositories.
	// Might be {@code null} if this manager is uninitialized
	private Set<Repository> knownRepositories;

	/**
	 * Sole constructor for creating the working set manager. Internally initializes the cache for repositories.
	 */
	public GitRepositoryAwareWorkingSetManager() {
		repositoryCache = Activator.getDefault().getRepositoryCache(); // might not be initialized yet.
		repositoryChangeListener = new IPreferenceChangeListener() {

			@SuppressWarnings("deprecation")
			// keep deprecated RepositoryUtil.PREFS_DIRECTORIES for backward-compatibility
			@Override
			public void preferenceChange(final PreferenceChangeEvent event) {
				if (!RepositoryUtil.PREFS_DIRECTORIES_REL.equals(event.getKey())
						&& !RepositoryUtil.PREFS_DIRECTORIES.equals(event.getKey())) {
					return;
				}

				if (!orderedWorkingSetIds.isEmpty() && !visibleWorkingSetIds.isEmpty()) {

					MapDifference<String, String> diff = calculateDifference(event);
					if (!diff.areEqual()) {

						// Deletions
						final Set<String> deletions = diff.entriesOnlyOnLeft().keySet();
						for (String deletedUrl : deletions) {
							orderedWorkingSetIds.remove(deletedUrl);
							visibleWorkingSetIds.remove(deletedUrl);
						}

						// Additions
						final Set<String> additions = diff.entriesOnlyOnRight().keySet();
						for (String addedUrl : additions) {
							orderedWorkingSetIds.add(addedUrl);
							visibleWorkingSetIds.add(addedUrl);
						}

					}

				}
				// trigger a reload based on new repository information
				reload();
			}

			private MapDifference<String, String> calculateDifference(PreferenceChangeEvent event) {
				String oldValue = Strings.nullToEmpty((String) event.getOldValue());
				String newValue = Strings.nullToEmpty((String) event.getNewValue());

				Map<String, String> oldMappings = toMap(newHashSet(Splitter.on(pathSeparator).split(oldValue)), i -> i);
				Map<String, String> newMappings = toMap(newHashSet(Splitter.on(pathSeparator).split(newValue)), i -> i);

				return Maps.difference(oldMappings, newMappings);

			}

		};

		final IEclipsePreferences gitNode = InstanceScope.INSTANCE.getNode(Activator.getPluginId());
		gitNode.addPreferenceChangeListener(repositoryChangeListener);

		final BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		context.addBundleListener(new BundleListener() {

			@Override
			public void bundleChanged(final BundleEvent event) {
				if (BundleEvent.STOPPING == event.getType()) {
					gitNode.removePreferenceChangeListener(repositoryChangeListener);
				}
			}

		});

		GitProjectData.addRepositoryChangeListener(which -> {
			if (null != knownRepositories
					&& !knownRepositories.contains(which.getRepository())) {
				// only reload, if the mapping change introduced an up-to-now unknown repository
				reload();
			}
		});

	}

	@Override
	protected void discardWorkingSetCaches() {
		this.knownRepositories = null;
		super.discardWorkingSetCaches();
	}

	@Override
	public String getLabel() {
		return "Git Repository";
	}

	@Override
	public Optional<Image> getImage() {
		return ImageRef.REPOSITORY.asImage();
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		final Collection<Repository> repositories = newArrayList(repositoryCache.getAllRepositories());

		// keep track of which repositories are currently represented in terms of git repository working sets
		knownRepositories = newHashSet(repositories);

		repositories.add(null); // For 'Other Projects'.

		return repositories.stream().map(r -> new GitRepositoryWorkingSet(r, this)).collect(Collectors.toList());
	}

	/**
	 * Reloads the working set manager by invalidating its cache and re-triggering the initialization logic.
	 */
	private void reload() {
		discardWorkingSetCaches();
		saveState(new NullProgressMonitor());

		WorkingSetManagerBroker workingSetManagerBroker = getWorkingSetManagerBroker();
		if (workingSetManagerBroker.isWorkingSetTopLevel()) {
			final WorkingSetManager activeManager = workingSetManagerBroker.getActiveManager();
			if (activeManager != null) {
				if (activeManager.getId().equals(getId())) {
					workingSetManagerBroker.refreshNavigator();
				}
			}
		}
	}

	/**
	 * Working set for grouping projects based on the attached local Git repository.
	 */
	public static final class GitRepositoryWorkingSet extends DefaultWorkingSetImpl {

		private final String rootUri;
		private final String name;

		private static String repositoryToId(Repository repository) {
			if (null == repository) {
				return OTHERS_WORKING_SET_ID;
			}
			return toUriString(repository.getDirectory().getParentFile().toURI());
		}

		/**
		 * Creates a new working set instance with the optional {@link Repository Git repository} and the container
		 * working set manager.
		 *
		 * @param repository
		 *            the associated Git repository. Could be {@code null} if the working set is for
		 *            {@link WorkingSet#OTHERS_WORKING_SET_ID <em>'Other Project'</em>} purposes.
		 * @param manager
		 *            the container manager.
		 */
		@VisibleForTesting
		public GitRepositoryWorkingSet(/* nullable */ final Repository repository, final WorkingSetManager manager) {
			super(repositoryToId(repository), manager);
			if (repository == null) {
				rootUri = null;
				name = OTHERS_WORKING_SET_ID;
			} else {
				final File directory = repository.getDirectory().getParentFile();
				rootUri = toUriString(directory.toURI());
				name = directory.getName();
			}
		}

		@Override
		public boolean apply(final IProject project) {
			return rootUri != null && toUriString(project.getLocationURI()).startsWith(rootUri);
		}

		@Override
		public String getName() {
			return name;
		}

		/**
		 * Returns with the {@link URI#toString()} of the argument. Trims the trailing forward slash if any.
		 */
		private static String toUriString(final URI uri) {
			final String uriString = uri.toString();
			final int length = uriString.length();
			if (uriString.charAt(length - 1) == '/') {
				return uriString.substring(0, length - 1);
			}
			return uriString;
		}

	}

}
