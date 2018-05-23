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
package org.eclipse.n4js.ui.external;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.N4JSExternalProject;

import com.google.inject.Inject;

/**
 * Provides a workspace job to clients that can be run to perform external project clean/build. This workspace job is
 * required to avoid any deadlocks on the UI thread via the proper {@link ISchedulingRule workspace scheduling rule}
 * usage. This class should be used for instance from a {@link IResourceChangeListener} implementation, when we are in
 * the middle of a workspace modification and we would like to avoid any deadlocks.
 */
public class ExternalLibraryBuildJobProvider {

	@Inject
	private ExternalLibraryBuilder builderHelper;

	/**
	 * Creates a new build job that cleans and builds the given external projects.
	 *
	 * @param toBuild
	 *            the projects that has to be build.
	 * @param toClean
	 *            the projects that has to be cleaned.
	 * @return a job for building and cleaning the projects.
	 */
	public Job createBuildJob(final Collection<N4JSExternalProject> toBuild,
			final Collection<N4JSExternalProject> toClean) {

		return new ExternalLibraryBuildJob(builderHelper, toBuild, toClean);
	}

	private static class ExternalLibraryBuildJob extends WorkspaceJob {
		private final Iterable<N4JSExternalProject> toBuild;
		private final Iterable<N4JSExternalProject> toClean;
		private final ExternalLibraryBuilder builderHelper;

		private ExternalLibraryBuildJob(final ExternalLibraryBuilder builderHelper,
				final Collection<N4JSExternalProject> toBuild, final Collection<N4JSExternalProject> toClean) {

			super("External library build");
			this.builderHelper = checkNotNull(builderHelper, "builderHelper");
			this.toBuild = checkNotNull(Collections.unmodifiableCollection(new LinkedList<>(toBuild)), "toBuild");
			this.toClean = checkNotNull(Collections.unmodifiableCollection(new LinkedList<>(toClean)), "toClean");
			setSystem(true);
			setRule(builderHelper.getRule());
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			monitor.beginTask("Building external libraries...", IProgressMonitor.UNKNOWN);

			for (final N4JSExternalProject project : toClean) {
				builderHelper.clean(project, monitor);
			}
			for (final N4JSExternalProject project : toBuild) {
				builderHelper.build(project, monitor);
			}

			return Status.OK_STATUS;
		}

	}

}
