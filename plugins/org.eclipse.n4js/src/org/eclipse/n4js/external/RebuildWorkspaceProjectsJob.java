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
package org.eclipse.n4js.external;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.core.resources.IncrementalProjectBuilder.CLEAN_BUILD;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.n4js.N4JSPluginId.N4JS_PLUGIN_ID;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * Job for building projects in the Eclipse workspace.
 */
public class RebuildWorkspaceProjectsJob extends WorkspaceJob {

	private static final Logger LOGGER = Logger.getLogger(RebuildWorkspaceProjectsJob.class);

	private static final ISchedulingRule BUILD_RULE = ResourcesPlugin.getWorkspace().getRuleFactory().buildRule();

	private final IBuildConfiguration[] configs;

	/**
	 * Creates a new job with the already ordered configurations to builder.
	 *
	 * @param configs
	 *            the configurations to build.
	 *
	 */
	public RebuildWorkspaceProjectsJob(final IBuildConfiguration[] configs) {
		super("Building workspace");
		checkState(Platform.isRunning(), "This job requires a running platform");
		this.configs = checkNotNull(configs, "configs");
		setSystem(false);
		setUser(true);
		setRule(BUILD_RULE);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		try {
			ResourcesPlugin.getWorkspace().build(configs, CLEAN_BUILD, false, monitor);
			return OK_STATUS;
		} catch (final CoreException e) {
			final String message = "Error while rebuilding workspace content.";
			LOGGER.error(message, e);
			return new Status(ERROR, N4JS_PLUGIN_ID, message, e);
		}
	}

}
