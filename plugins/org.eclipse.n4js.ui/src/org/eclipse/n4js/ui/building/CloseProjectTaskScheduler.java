/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.n4js.internal.RaceDetectionHelper;
import org.eclipse.n4js.ui.building.ClosedProjectQueue.Task;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.Messages;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.shared.internal.ListenerRegistrar;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Specialized project open or closed listener that fixes a bunch of races and misbehavior that is present in the
 * default impl.
 */
@SuppressWarnings("restriction")
@Singleton
public class CloseProjectTaskScheduler extends ProjectOpenedOrClosedListener {

	private final class RemoveProjectJob extends WorkspaceJob {
		{
			setRule(ResourcesPlugin.getWorkspace().getRoot());
		}

		/**
		 * @param name
		 *            the name of the job. May be null.
		 */
		private RemoveProjectJob(String name) {
			super(name);
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == ResourcesPlugin.FAMILY_AUTO_BUILD;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			processTaskNow(closedProjectQueue.exhaust(), monitor);
			return Status.OK_STATUS;
		}

		private void processTaskNow(Task task, IProgressMonitor monitor) {
			if (task.isEmpty()) {
				return;
			}
			String projectNames = Joiner.on(", ").join(task.projectNames);
			String taskName = Messages.ProjectOpenedOrClosedListener_RemovingProject + projectNames
					+ Messages.ProjectOpenedOrClosedListener_FromIndex;
			RaceDetectionHelper.log(taskName);
			monitor.setTaskName(taskName);
			SubMonitor progress = SubMonitor.convert(monitor, 1);
			try {
				ResourceSet resourceSet = getResourceSetProvider().get(null);
				resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE, Boolean.TRUE);
				if (resourceSet instanceof ResourceSetImpl) {
					((ResourceSetImpl) resourceSet).setURIResourceMap(Maps.<URI, Resource> newHashMap());
				}
				BuildData buildData = new BuildData(projectNames, resourceSet, task.toBeBuilt,
						queuedBuildData);
				getBuilderState().update(buildData, progress.newChild(1));
			} catch (Error | RuntimeException e) {
				task.reschedule();
				throw e;
			} finally {
				monitor.done();
			}
		}
	}

	private final ClosedProjectQueue closedProjectQueue;

	private final RemoveProjectJob removeProjectJob = new RemoveProjectJob("Dummy");

	@Inject
	private QueuedBuildData queuedBuildData;

	/**
	 * Create a new listener. Registration is done by the {@link ListenerRegistrar}.
	 */
	@Inject
	public CloseProjectTaskScheduler(ClosedProjectQueue closedProjectQueue) {
		super();
		this.closedProjectQueue = closedProjectQueue;
	}

	/*
	 * This overrides the super implementation to avoid races between the builder and other jobs.
	 *
	 * We schedule a job only if the project will no longer be seen by any Xtext project, otherwise we rely on the
	 * N4JSBuildTypeTrackingBuilder to update the index when it processes the downstream project.
	 */
	@Override
	protected void scheduleRemoveProjectJob(final IProject project) {
		final ToBeBuilt toBeBuilt = getToBeBuiltComputer().removeProject(project, new NullProgressMonitor());
		if (toBeBuilt.getToBeDeleted().isEmpty() && toBeBuilt.getToBeUpdated().isEmpty()) {
			return;
		}
		RaceDetectionHelper.log("Enqueue remove project job for %s", project.getName());
		closedProjectQueue.enqueue(ImmutableSet.of(project.getName()), toBeBuilt);
		scheduleJob(project.getName());
	}

	private void scheduleJob(String name) {
		removeProjectJob.setName(Messages.ProjectOpenedOrClosedListener_RemovingProject + name
				+ Messages.ProjectOpenedOrClosedListener_FromIndex);
		removeProjectJob.schedule();
	}

	/**
	 * Wait for the removeProjectJob to finish.
	 */
	public void joinRemoveProjectJob() {
		try {
			// Pseudo fence to make sure that we see what we want to see.
			synchronized (this) {
				wait(1);
			}
			this.removeProjectJob.join();
		} catch (InterruptedException e) {
			// ignore
			e.printStackTrace();
		}
	}

}
