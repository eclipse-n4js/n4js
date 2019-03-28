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
package org.eclipse.n4js.tester.ui;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.n4js.runner.ui.IDERunnerDelegate;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.inject.Inject;

/**
 * Base class for tester launch configuration delegates.
 */
public abstract class AbstractTesterLaunchConfigurationDelegate extends IDERunnerDelegate {

	private static final Logger LOGGER = Logger.getLogger(AbstractTesterLaunchConfigurationDelegate.class);

	@Inject
	private TesterFrontEndUI testerFrontEndUI;

	@Inject
	private TestConfigurationConverter testConfigurationConverter;

	@Inject
	private StatusHelper statusHelper;

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		final TestConfiguration testConfig = testConfigurationConverter.toTestConfiguration(configuration);
		if (testConfig == null) {
			throw new CoreException(statusHelper.createError("Couldn't obtain test configuration."));
		}

		try {
			String launchConfigName = launch.getLaunchConfiguration().getName();
			Process process = testerFrontEndUI.runInUI(testConfig);
			DebugPlugin.newProcess(launch, process, launchConfigName);
		} catch (Exception e) {
			LOGGER.error("Error occurred while trying to execute module.", e);
			if (e instanceof CoreException) {
				throw (CoreException) e;
			}
			throw new CoreException(statusHelper.createError(e));
		}
	}
}
