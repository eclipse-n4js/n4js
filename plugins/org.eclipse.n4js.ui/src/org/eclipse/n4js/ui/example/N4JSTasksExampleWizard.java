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
package org.eclipse.n4js.ui.example;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.wizard.ExampleInstallerWizard;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.internal.N4JSEclipseModel;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.inject.Inject;

/**
 * Wizard for the {@code N4JS Tasks Example} projects.
 */
public class N4JSTasksExampleWizard extends ExampleInstallerWizard {

	/**
	 * Unique ID of the {@code N4JS Tasks Example} wizard.
	 */
	public static final String ID = N4JSTasksExampleWizard.class.getName();

	@Inject
	private LibraryManager libManager;

	@Inject
	private N4JSEclipseModel model;

	@Override
	public boolean performFinish() {
		if (super.performFinish()) {
			runNpmInstall();
			return true;
		}
		return false;
	}

	private void runNpmInstall() {
		try {
			getContainer().run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.subTask("Installing dependencies");
						IN4JSProject taskExampleProject = model.findAllProjectMappings().get("task.example");
						URI location = taskExampleProject.getLocation();
						IStatus status = libManager.runNpmInstall(location, monitor);
						if (status.matches(IStatus.ERROR))
							throw status.getException();
					} catch (Throwable e) {
						throw new InvocationTargetException(e,
								"An error occurred while installing dependencies");
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			String pluginId = N4JSActivator.getInstance().getBundle().getSymbolicName();
			IStatus status = new Status(IStatus.ERROR, pluginId, e.getCause().getMessage(), e.getCause());

			ErrorDialog.openError(getShell(), "Error", e.getMessage(), status);
			StatusManager.getManager().handle(status);
		}
	}

}
