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
package org.eclipse.n4js.runner.ui;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.inject.Inject;

/**
 * Base class for runner delegates.
 */
// extend org.eclipse.debug.core.model.ILaunchConfigurationDelegate2 maybe?
public abstract class IDERunnerDelegate implements ILaunchConfigurationDelegate {
	// FIXME rename class to AbstractRunnerLaunchConfigurationDelegate

	/**
	 * This id is also used to connect a hyper link tracker to the IOConsole (see ui plugin.xml)
	 */
	private static final String N4JS_PROCESS_TYPE = "n4js";

	private static final Logger LOGGER = Logger.getLogger(IDERunnerDelegate.class);

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;

	@Inject
	private RunConfigurationConverter runConfigurationConverter;

	@Inject
	private StatusHelper statusHelper;

	/**
	 * Return the ID of the runner this {@link ILaunchConfigurationDelegate} and its corresponding
	 * {@link ILaunchConfigurationType} is intended for.
	 */
	public abstract String getRunnerId();

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		final RunConfiguration runConfig = runConfigurationConverter.toRunConfiguration(configuration);
		if (runConfig == null) {
			throw new CoreException(statusHelper.createError("Couldn't obtain run configuration."));
		}

		try {
			HashMap<String, String> attributes = new HashMap<>(1);
			attributes.put(IProcess.ATTR_PROCESS_TYPE, N4JS_PROCESS_TYPE);
			DebugPlugin.newProcess(launch, runnerFrontEndUI.runInUI(runConfig),
					launch.getLaunchConfiguration().getName(), attributes);

		} catch (Exception e) {
			LOGGER.error("Error occurred while trying to execute module.", e);
			if (e instanceof CoreException) {
				throw (CoreException) e;
			}
			throw new CoreException(statusHelper.createError(e));
		}
	}
}
