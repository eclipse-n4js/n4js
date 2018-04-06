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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.n4js.external.GitCloneSupplier;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import com.google.inject.Inject;

/**
 * N4JS IDE startup hook to makes sure that the local Git repository exists for the type definition files.
 */
public class N4JSExternalLibraryStartup implements IStartup {

	@Inject
	private GitCloneSupplier gitCloneSupplier;

	@Inject
	private EclipseExternalIndexSynchronizer indexSynchronizer;

	@Override
	public void earlyStartup() {
		// Client code can still clone the repository on demand. (Mind plug-in UI tests.)
		if (ExternalLibrariesActivator.requiresInfrastructureForLibraryManager()) {
			new Thread(() -> {
				indexSynchronizer.checkAndSetOutOfSyncMarkers();
			}).start();

			new Thread(() -> {
				gitCloneSupplier.synchronizeTypeDefinitions();
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
				indexSynchronizer.checkAndSetOutOfSyncMarkers();
			}
		}

	}
}
