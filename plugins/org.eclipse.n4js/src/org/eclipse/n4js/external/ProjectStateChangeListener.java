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
package org.eclipse.n4js.external;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.OPEN;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Resource change listener implementation for listening project open/close event and running the
 * {@link ExternalLibraryBuilderHelper external library build helper} according to the changes. Also got notified if new
 * workspace project is being created or an existing, accessible project is being deleted.
 */
public class ProjectStateChangeListener implements IResourceChangeListener {

	private static final Logger LOGGER = Logger.getLogger(ProjectStateChangeListener.class);

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private ExternalLibraryBuildJobProvider buildJobProvider;

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {

		if (null == event || null == event.getDelta()) {
			return;
		}

		final Collection<IProject> toClean = newLinkedHashSet();
		final Collection<IProject> toBuild = newLinkedHashSet();

		try {
			event.getDelta().accept(delta -> {

				final IResource resource = delta.getResource();
				if (resource instanceof IProject) {

					final IProject project = (IProject) resource;
					// XXX What if project is not accessible and/or not Xtext project with the corresponding builder?
					final IProject externalProject = externalLibraryWorkspace.getProject(project.getName());
					if (null != externalProject && externalProject.exists()) {

						if (CHANGED == delta.getKind() && (delta.getFlags() & OPEN) != 0) {

							// Workspace project close/open
							if (project.isOpen()) {
								toClean.add(externalProject);
							} else {
								toBuild.add(externalProject);
							}

						} else if (REMOVED == delta.getKind()) {

							// Workspace project deletion
							toBuild.add(externalProject);

						} else if (ADDED == delta.getKind()) {

							// Workspace project creation
							toClean.add(externalProject);

						}
					}
				}

				return true;
			});

			if (!toClean.isEmpty() || !toBuild.isEmpty()) {
				LOGGER.info("Received project open/close change.");
				LOGGER.info("Opened projects: " + Iterables.toString(from(toClean).transform(p -> p.getName())));
				LOGGER.info("Closed projects: " + Iterables.toString(from(toBuild).transform(p -> p.getName())));

				buildJobProvider.createBuildJob(toBuild, toClean).schedule();
			}

		} catch (final CoreException e) {
			LOGGER.error("Error while visiting resource change event.", e);
		}
	}

}
