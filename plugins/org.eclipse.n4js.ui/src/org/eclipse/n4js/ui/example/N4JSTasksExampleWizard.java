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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.wizard.ExampleInstallerWizard;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
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

	@Inject
	private SemverHelper semverHelper;

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
						IN4JSProject taskExample = model.findAllProjectMappings().get("n4js.example.tasks");
						URI location = taskExample.getLocation();
						Map<String, NPMVersionRequirement> taskDeps = new HashMap<>();
						taskDeps.put("n4js-runtime-es2015", semverHelper.parse("*"));
						taskDeps.put("n4js.lang", semverHelper.parse("^0.13.1"));
						taskDeps.put("mongodb", semverHelper.parse("^2.1.0"));
						taskDeps.put("express", semverHelper.parse("*"));
						taskDeps.put("@n4jsd/mongodb", semverHelper.parse("<=2.1.*"));
						taskDeps.put("@n4jsd/express", semverHelper.parse("*"));
						IStatus status = libManager.installNPMs(taskDeps, true, location, monitor);

						if (status.matches(IStatus.ERROR))
							throw status.getException();

						IN4JSProject taskExampleTest = model.findAllProjectMappings().get("n4js.example.tasks.tests");
						location = taskExampleTest.getLocation();
						taskDeps.clear();
						taskDeps.put("n4js.lang", semverHelper.parse("^0.13.1"));
						taskDeps.put("org.eclipse.n4js.mangelhaft", semverHelper.parse("^0.13.1"));
						taskDeps.put("org.eclipse.n4js.mangelhaft.assert", semverHelper.parse("^0.13.1"));
						status = libManager.installNPMs(taskDeps, true, location, monitor);

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
