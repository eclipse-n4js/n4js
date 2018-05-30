/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester;

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFileBasedShippedCodeConfigurationHelper;
import org.eclipse.n4js.runner.RunnerHelper;
import org.eclipse.n4js.runner.internal.RunnerN4JSCore;
import org.eclipse.n4js.tester.extension.TesterRegistry;

import com.google.inject.Inject;

/**
 * Helper for reconfiguring {@link RunConfiguration} with file based access to shipped code.
 */
public class TesterFileBasedShippedCodeConfigurationHelper extends RunnerFileBasedShippedCodeConfigurationHelper {

	@Inject
	private RunnerHelper runnerHelper;

	@Inject
	private TesterRegistry testerRegistry;

	@Inject
	private RunnerN4JSCore runnerN4JSCore;

	/**
	 * Reconfigures provided run configuration in regards of {@link TestConfiguration#getExecModule()},
	 * {@link TestConfiguration#getInitModules()} and {@link TestConfiguration#getCoreProjectPaths()} by plain using
	 * file system access to the shipped code. Intended to be used in situations where proper workspace setup is not
	 * available and run configurations created by default are lacking essential information.
	 *
	 * It is up to the caller to decide when it is appropriate to call this method.
	 *
	 * @param config
	 *            the configuration to be reconfigured.
	 */
	public void configureFromFileSystem(TestConfiguration config) {
		Iterable<IN4JSProject> allShippedProjects = runnerN4JSCore.getAllShippedProjects();
		IN4JSProject customRuntimeEnvironment = getCustomRuntimeEnvironment(config, allShippedProjects);
		reconfigure(config, allShippedProjects, customRuntimeEnvironment);
	}

	/**
	 * Obtains custom runtime environment based on the provided projects and config.
	 */
	protected IN4JSProject getCustomRuntimeEnvironment(TestConfiguration config,
			Iterable<IN4JSProject> allShippedProjects) {
		return runnerHelper.findRuntimeEnvironemtnWithName(
				testerRegistry.getDescriptor(config.getTesterId()).getEnvironment().getProjectId(),
				allShippedProjects).orNull();
	}
}
