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
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.ui.external.ProjectStateChangeListener;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.BuilderStateDiscarder;
import org.eclipse.xtext.builder.impl.IBuildFlag;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * N4JS IDE startup hook to makes sure that the local Git repository exists for the type definition files.
 */
@SuppressWarnings("restriction")
public class N4JSExternalLibraryStartup implements IStartup {

	@Inject
	private ContributingResourceDescriptionPersister descriptionPersister;

	@Inject
	private IWorkspace workspace;

	@Inject
	private BuilderStateDiscarder builderStateDiscarder;

	@Inject
	private IBuilderState builderState;

	@Inject
	private ProjectStateChangeListener indexSyncScheduler;

	@Inject
	private NpmLogger npmLogger;

	@Override
	public void earlyStartup() {
		// Client code can still clone the repository on demand. (Mind plug-in UI tests.)

		// TODO this should be a job that we can wait for
		new Thread(() -> {
			// trigger index loading which will potentially announce a recovery build on all projects to be
			// necessary

			// XXX it is crucial to call isEmpty before isRecoveryBuildRequired is checked, since isEmpty
			// will set internal state that is afterwards queried by isRecoveryBuildRequired
			boolean indexIsEmpty = builderState.isEmpty();

			// check if this recovery build was really required
			if (descriptionPersister.isRecoveryBuildRequired() || indexIsEmpty) {
				// TODO return something like a Future that allows to say
				// descriptionPersister.scheduleRecoveryBuildOnContributions().andThen(buildManager...)
				descriptionPersister.scheduleRecoveryBuildOnContributions();
				Map<String, String> args = Maps.newHashMap();
				IBuildFlag.RECOVERY_BUILD.addToMap(args);
				builderStateDiscarder.forgetLastBuildState(Arrays.asList(workspace.getRoot().getProjects()), args);
			}
		}).start();

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
				indexSyncScheduler.forceIndexSync();
				npmLogger.logInfo("external locations updated");
			}
		}

	}
}
