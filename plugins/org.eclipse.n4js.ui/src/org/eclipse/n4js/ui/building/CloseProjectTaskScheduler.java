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
		private RemoveProjectJob(String name) {
			super(name);
			setRule(ResourcesPlugin.getWorkspace().getRoot());
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == ResourcesPlugin.FAMILY_AUTO_BUILD;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			processClosedProjects(monitor);
			return Status.OK_STATUS;
		}
	}

	private final ClosedProjectQueue closedProjectQueue;

	private final RemoveProjectJob removeProjectJob = new RemoveProjectJob("");

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

	/**
	 * Specialized behavior of the super impl. We schedule a job, that will process all closed projects in bulk rather
	 * than individually.
	 */
	@Override
	protected void scheduleRemoveProjectJob(final IProject project) {
		final ToBeBuilt toBeBuilt = getToBeBuiltComputer().removeProject(project, new NullProgressMonitor());
		if (toBeBuilt.getToBeDeleted().isEmpty() && toBeBuilt.getToBeUpdated().isEmpty()) {
			return;
		}
		scheduleJob(project.getName(), toBeBuilt);
	}

	// FIXME GH-1234: remove overrides that are now obsolete after adjustments were moved to Xtext builder!
	@Override
	protected void scheduleJob(String name, ToBeBuilt toBeBuilt) {
		RaceDetectionHelper.log("Enqueue remove project job for %s", name);
		closedProjectQueue.enqueue(ImmutableSet.of(name), toBeBuilt);
		removeProjectJob.setName(Messages.ProjectOpenedOrClosedListener_RemovingProject + name
				+ Messages.ProjectOpenedOrClosedListener_FromIndex);
		removeProjectJob.schedule();
	}

	/**
	 * Process the closed projects now. Must be called when holding the workspace lock.
	 *
	 * @param monitor
	 *            the monitor.
	 */
	// FIXME GH-1234: remove overrides that are now obsolete after adjustments were moved to Xtext builder!
	@Override
	public void processClosedProjects(IProgressMonitor monitor) {
		Task task = closedProjectQueue.exhaust();
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
			BuildDataWithRequestRebuild buildData = new BuildDataWithRequestRebuild(projectNames, resourceSet,
					task.toBeBuilt, queuedBuildData, BuildManagerAccess::needBuild);
			getBuilderState().update(buildData, progress.newChild(1));
		} catch (Error | RuntimeException e) {
			task.reschedule();
			throw e;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Wait for the removeProjectJob to finish.
	 *
	 * Public for testing purpose.
	 */
	// FIXME GH-1234: remove overrides that are now obsolete after adjustments were moved to Xtext builder!
	@Override
	public void joinRemoveProjectJob() {
		/*
		 * szarnekow: Tests in tight loops revealed that join does not always wait for the job to really finish.
		 * Including the wait serves as a poor attempt to ensure that the current thread really sees the correct
		 * internal state of the job instance when it tries to join.
		 *
		 * To be honest, I do not understand why the synchronized block in the job manager is not sufficient for this,
		 * but after the synchronized block was introduced here, I do no longer see spurious races between the join and
		 * the job execution. I suspect it is related to the Java memory model, but that is only a vague feeling.
		 */
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
