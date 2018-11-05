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
package org.eclipse.n4js.ui.internal;

import static org.eclipse.core.runtime.Status.OK_STATUS;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.ui.resource.IResourceDescriptionPersisterContribution;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.inject.Inject;

/**
 * Resource description persister contribution to restore the state of the Xtext index for the external libraries on
 * application startup.
 */
public class N4JSExternalLibraryResourceDescriptionsPersisterContribution
		implements IResourceDescriptionPersisterContribution {

	private static final Logger LOGGER = Logger
			.getLogger(N4JSExternalLibraryResourceDescriptionsPersisterContribution.class);

	@Inject
	private LibraryManager libraryManager;

	@Inject
	private StatusHelper statusHelper;

	@Override
	public void scheduleRecoveryBuild() {

		final Job job = new Job("Register all npms") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					final IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

						@Override
						public void run(final IProgressMonitor progress) throws CoreException {
							try {
								libraryManager.registerAllExternalProjects(progress);
							} catch (final Exception e) {
								throw new CoreException(statusHelper.createError(e));
							}
						}

					};
					final IWorkspace workspace = ResourcesPlugin.getWorkspace();
					// Workspace is already locked.
					if (workspace.isTreeLocked()) {
						runnable.run(monitor);
					} else {
						workspace.run(runnable, null, IWorkspace.AVOID_UPDATE, monitor);
					}
				} catch (final CoreException e) {
					final String message = "Unexpected error occurred while initializing N4JS Built-ins.";
					LOGGER.error(message, e);
					return statusHelper.createError(message, e);
				}
				return OK_STATUS;
			}

		};

		job.setUser(true);
		job.setSystem(false);
		job.schedule();

	}

}
