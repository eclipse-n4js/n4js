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

import static com.google.common.collect.FluentIterable.from;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.wizard.ExampleInstallerWizard;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.NpmManager;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.collect.ImmutableMap;
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
	private NpmManager npmManager;

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Override
	public boolean performFinish() {
		if (super.performFinish()) {
			installDependencies(ImmutableMap.of(
					"mongodb", "@\">=2.0.0 <3.0.0\"",
					"express", ""));
			return true;
		}
		return false;
	}

	/**
	 * @param namesAndVersions
	 *            a map from NPM package names to NPM version constraints; use an empty string as version constraint to
	 *            install the newest version.
	 */
	private void installDependencies(Map<String, String> namesAndVersions) {
		Set<String> toInstall = new HashSet<>(namesAndVersions.keySet());
		toInstall.removeAll(getInstalledNpmPackages());

		try {
			getContainer().run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					for (String name : toInstall) {
						try {
							String version = namesAndVersions.get(name).trim();
							IStatus status;
							if (!version.isEmpty()) {
								monitor.subTask("Installing dependency '" + name + "' in version " + version);
								status = npmManager.installDependency(name, version, monitor);
							} else {
								monitor.subTask("Installing dependency '" + name + "'");
								status = npmManager.installDependency(name, monitor);
							}
							if (status.matches(IStatus.ERROR))
								throw status.getException();
						} catch (Throwable e) {
							throw new InvocationTargetException(e,
									"An error occurred while installing dependency '" + name + "'");
						}
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

	private Collection<String> getInstalledNpmPackages() {
		final File root = new File(installLocationProvider.getTargetPlatformNodeModulesLocation());
		return from(externalLibraryWorkspace.getProjectsIn(root.toURI())).transform(p -> p.getName()).toSet();
	}
}
