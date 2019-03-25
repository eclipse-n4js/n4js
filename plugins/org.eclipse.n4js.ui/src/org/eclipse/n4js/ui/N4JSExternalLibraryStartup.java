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
package org.eclipse.n4js.ui;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.ui.external.ExternalLibraryBuilder;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.BuildScheduler;
import org.eclipse.xtext.builder.impl.IBuildFlag;

import com.google.inject.Inject;

/**
 * N4JS IDE startup hook to makes sure that the local Git repository exists for the type definition files.
 */
@SuppressWarnings("restriction")
public class N4JSExternalLibraryStartup implements IStartup {

	@Inject
	private EclipseExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private ContributingResourceDescriptionPersister descriptionPersister;

	@Inject
	private IWorkspace workspace;

	@Inject
	private BuildScheduler buildManager;

	@Inject
	private IBuilderState builderState;

	@Inject
	private ExternalLibraryBuilder builder;

	@Override
	public void earlyStartup() {
		// Client code can still clone the repository on demand. (Mind plug-in UI tests.)
		if (ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			new Thread(() -> {
				// trigger index loading which will potentially announce a recovery build on all projects to be
				// necessary

				// XXX is is crucial to call isEmpty before isRecoveryBuildRequired is checked, since isEmpty
				// will set internal state that is afterwards queried by isRecoveryBuildRequired
				boolean indexIsEmpty = builderState.isEmpty();

				// check if this recovery build was really required
				if (descriptionPersister.isRecoveryBuildRequired() || indexIsEmpty) {
					// TODO return something like a Future that allows to say
					// descriptionPersister.scheduleRecoveryBuildOnContributions().andThen(buildManager...)
					descriptionPersister.scheduleRecoveryBuildOnContributions();
					buildManager.scheduleBuildIfNecessary(Arrays.asList(workspace.getRoot().getProjects()),
							IBuildFlag.RECOVERY_BUILD);
				}
			}).start();

		}

		// Add listener to monitor Cut and Copy commands
		ICommandService commandService = PlatformUI.getWorkbench().getAdapter(ICommandService.class);
		if (commandService != null) {
			commandService.addExecutionListener(new CheckNodeModulesSyncOnRefresh());
		}
	}

	class CheckNodeModulesSyncOnRefresh implements IExecutionListener {

		@Override
		public void notHandled(String commandId, NotHandledException exception) {
			// nothing to do
		}

		@Override
		public void postExecuteFailure(String commandId, ExecutionException exception) {
			// nothing to do
		}

		@Override
		public void preExecute(String commandId, ExecutionEvent event) {
			// nothing to do
		}

		@Override
		public void postExecuteSuccess(String commandId, Object returnValue) {
			if ("org.eclipse.ui.file.refresh".equals(commandId)) {
				Job job = new Job("Update locations of node_modules folders") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						ISchedulingRule rule = builder.getRule();
						Job.getJobManager().beginRule(rule, monitor);
						try {
							indexSynchronizer.checkAndClearIndex(monitor);
						} finally {
							Job.getJobManager().endRule(rule);
						}
						return Status.OK_STATUS;
					}
				};
				job.schedule();
			}
		}

	}
}
