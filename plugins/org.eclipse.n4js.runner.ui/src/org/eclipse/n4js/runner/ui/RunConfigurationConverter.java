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

import static org.eclipse.n4js.runner.ui.RunnerUiActivator.PLUGIN_ID;
import static org.eclipse.core.runtime.IStatus.ERROR;

import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.WrappedException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;

/**
 * Converts a {@link RunConfiguration} to an {@link ILaunchConfiguration} and vice versa.
 */
@Singleton
public class RunConfigurationConverter {

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	/**
	 * Converts an {@link ILaunchConfiguration} to a {@link RunConfiguration}. Will throw a {@link WrappedException} in
	 * case of error.
	 *
	 * @see RunConfiguration#writePersistentValues(Map)
	 */
	public RunConfiguration toRunConfiguration(ILaunchConfiguration launchConfig) throws CoreException {
		try {
			final Map<String, Object> properties = launchConfig.getAttributes();
			// special treatment of name required:
			// name is already included in 'properties', but we have to make sure that the name is up-to-date
			// in case the name of the launch configuration has been changed after the launch configuration
			// was created via method #toLaunchConfiguration()
			properties.put(RunConfiguration.NAME, launchConfig.getName());
			return runnerFrontEnd.createConfiguration(properties);
		} catch (Exception e) {
			String msg = "Error occurred while trying to launch module.";
			if (null != e.getMessage()) {
				msg += "\nReason: " + e.getMessage();
			}
			throw new CoreException(new Status(ERROR, PLUGIN_ID, msg, e));
		}
	}

	/**
	 * Converts a {@link RunConfiguration} to an {@link ILaunchConfiguration}. Will throw a {@link WrappedException} in
	 * case of error.
	 *
	 * @see RunConfiguration#readPersistentValues()
	 */
	public ILaunchConfiguration toLaunchConfiguration(ILaunchConfigurationType type, RunConfiguration runConfig) {
		try {
			final ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurations(type);

			for (ILaunchConfiguration config : configs) {
				if (equals(runConfig, config))
					return config;
			}

			final IContainer container = null;
			final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(container, runConfig.getName());

			workingCopy.setAttributes(runConfig.readPersistentValues());

			return workingCopy.doSave();
		} catch (Exception e) {
			throw new WrappedException("could not convert N4JS RunConfiguration to Eclipse ILaunchConfiguration", e);
		}
	}

	private boolean equals(RunConfiguration runConfig, ILaunchConfiguration launchConfig) {
		return runConfig.getName().equals(launchConfig.getName());
	}
}
