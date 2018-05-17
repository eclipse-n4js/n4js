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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

/**
 * Runnable collector of the workspace projects setting files.
 */
public class RunnableSettingsFilesLocator implements IRunnableWithProgress {
	private static final Logger LOGGER = Logger.getLogger(RunnableSettingsFilesLocator.class);
	private ProjectsSettingsFilesLocator files = null;

	@Override
	synchronized public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		int projectsCount = ResourcesPlugin.getWorkspace().getRoot().getProjects().length;
		int estimate = projectsCount > 0 ? projectsCount : 100;
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 3 * estimate);
		final SubMonitor subMonitor0 = subMonitor.split(estimate);
		refreshWorkspace(subMonitor0);

		final SubMonitor subMonitor1 = subMonitor.split(2 * estimate);
		files = ProjectsSettingsFilesLocator.findFiles(subMonitor1);

	}

	private void refreshWorkspace(IProgressMonitor monitor) {
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			LOGGER.error("Error when refreshing workspace", e);
		}
	}

	/** get collected files */
	synchronized public Collection<File> getCollectedConfigFiles() {
		if (files == null)
			return Collections.emptySet();
		return files.getNPMRCs();
	}

}
