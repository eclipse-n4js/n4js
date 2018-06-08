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

import java.util.Collection;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.n4js.external.N4JSExternalProject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Schedules a workspace job that performs a (clean) build for external projects. The workspace job is required to avoid
 * any deadlocks on the UI thread via the proper {@link ISchedulingRule workspace scheduling rule} usage. This class
 * should be used for instance from a {@link IResourceChangeListener} implementation, when we are in the middle of a
 * workspace modification and we would like to avoid any deadlocks.
 */
@Singleton
public class ExternalLibraryBuildScheduler {

	private class ExternalLibraryBuildJob extends WorkspaceJob {

		private ExternalLibraryBuildJob(ExternalLibraryBuilder builderHelper) {
			super("External library build");
			setSystem(true);
			setRule(builderHelper.getRule());
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			buildExternalProjectsNow(monitor);
			return Status.OK_STATUS;
		}
	}

	private final ExternalLibraryBuilder builderHelper;

	private final ExternalLibraryBuildQueue builderQueue;

	private final ExternalLibraryBuildJob buildJob;

	/**
	 * Creates a new scheduler.
	 *
	 * @param builderHelper
	 *            the builder helper.
	 * @param builderQueue
	 *            the builder queue.
	 */
	@Inject
	public ExternalLibraryBuildScheduler(ExternalLibraryBuilder builderHelper, ExternalLibraryBuildQueue builderQueue) {
		this.builderHelper = builderHelper;
		this.builderQueue = builderQueue;
		this.buildJob = new ExternalLibraryBuildJob(builderHelper);
	}

	/**
	 * Creates a new build job that cleans and builds the given external projects.
	 *
	 * @param toBuild
	 *            the projects that has to be build.
	 * @param toClean
	 *            the projects that has to be cleaned.
	 */
	public void scheduleBuildJob(Collection<N4JSExternalProject> toBuild, Collection<N4JSExternalProject> toClean) {
		builderQueue.enqueue(toBuild, toClean);
		buildJob.schedule();
	}

	/**
	 * Run the build for the to-be-processed external projects now.
	 *
	 * @param monitor
	 *            the monitor.
	 */
	public void buildExternalProjectsNow(IProgressMonitor monitor) {
		builderHelper.process(builderQueue.exhaust(), monitor);
	}

	/**
	 * Wait for the external projects build job to finish.
	 */
	public void joinBuildJob() {
		try {
			// Pseudo fence to make sure that we see what we want to see.
			synchronized (this) {
				wait(1);
			}
			this.buildJob.join();
		} catch (InterruptedException e) {
			// ignore
			e.printStackTrace();
		}
	}

}
