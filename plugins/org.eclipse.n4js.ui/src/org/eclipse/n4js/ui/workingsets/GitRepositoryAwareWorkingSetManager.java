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

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Working set manager based on Git repositories.
 */
public class GitRepositoryAwareWorkingSetManager extends WorkingSetManagerImpl {
	final WorkspaceRepositoriesProvider repositoriesProvider;

	/**
	 * Sole constructor for creating the working set manager.
	 */
	@Inject
	public GitRepositoryAwareWorkingSetManager(WorkspaceRepositoriesProvider repositoriesProvider) {
		this.repositoriesProvider = repositoriesProvider;
		this.repositoriesProvider.addWorkspaceRepositoriesChangedListener(repos -> this.reload());
	}

	@Override
	protected void discardWorkingSetCaches() {
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
		final Collection<Repository> repositories = repositoriesProvider.getWorkspaceRepositories();

		System.out.println(repositories);
		System.out.println(Thread.currentThread());
		new Exception().printStackTrace(System.out);

		if (repositories.isEmpty()) {
			boolean sleep = true;
			if (sleep) {
				System.out.println("START SLEEP 2 sec");
				try {
					Thread.sleep(2000);
					System.out.println("END SLEEP 2 sec");
				} catch (InterruptedException e) {
					// ignore
				}
			}
		}
		if (!repositories.isEmpty()) {
			System.out.print("");
		}

		repositories.add(null); // For 'Other Projects'.

		System.out.println("!!!");

		return repositories.stream().map(r -> new GitRepositoryWorkingSet(r, this)).collect(Collectors.toList());
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
