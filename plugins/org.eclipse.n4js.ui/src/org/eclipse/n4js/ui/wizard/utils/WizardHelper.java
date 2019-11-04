/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.wizard.utils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper methods for N4JS wizards (e.g. new project wizard).
 */
@Singleton
public class WizardHelper {

	@Inject
	private LibraryManager libManager;

	/**
	 * Runs npm/yarn install in the given projects. The library manage will decide as usual whether npm or yarn is to be
	 * used. Intended for use in {@link Wizard#performFinish() #performFinish()} of N4JS wizards.
	 *
	 * @param container
	 *            the wizard's {@link IWizardContainer container}.
	 * @param projects
	 *            the projects on which npm/yarn install should be executed. Must be accessible, i.e. must exist and be
	 *            open.
	 */
	public void runNpmInstallInWizard(IWizardContainer container, Iterable<IProject> projects) {
		if (Iterables.isEmpty(projects)) {
			return;
		}
		for (IProject project : projects) {
			if (!project.isAccessible()) {
				throw new IllegalArgumentException("project not accessible: " + project.getLocationURI());
			}
		}
		try {
			container.run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.subTask("Installing dependencies");
						for (IProject project : projects) {
							IStatus status = libManager.runNpmYarnInstall(new PlatformResourceURI(project),
									monitor);
							if (status.matches(IStatus.ERROR)) {
								throw status.getException();
							}
						}
					} catch (Throwable e) {
						throw new InvocationTargetException(e,
								"An error occurred while installing dependencies");
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			String pluginId = N4JSActivator.getInstance().getBundle().getSymbolicName();
			IStatus status = new Status(IStatus.ERROR, pluginId, e.getCause().getMessage(), e.getCause());

			ErrorDialog.openError(container.getShell(), "Error", e.getMessage(), status);
			StatusManager.getManager().handle(status);
		}
	}
}
