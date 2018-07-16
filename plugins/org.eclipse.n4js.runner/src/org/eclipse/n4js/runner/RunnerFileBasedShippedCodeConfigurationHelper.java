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
package org.eclipse.n4js.runner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.runner.internal.RunnerN4JSCore;

import com.google.inject.Inject;

/**
 * Helper for reconfiguring {@link RunConfiguration} with file based access to shipped code.
 */
public class RunnerFileBasedShippedCodeConfigurationHelper {
	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private RunnerHelper runnerHelper;

	@Inject
	private RunnerRegistry runnerRegistry;

	@Inject
	private RunnerN4JSCore runnerN4JSCore;

	/**
	 * Reconfigures provided run configuration in regards of {@link RunConfiguration#getExecModule()},
	 * {@link RunConfiguration#getInitModules()} and {@link RunConfiguration#getCoreProjectPaths()} by plain using file
	 * system access to the shipped code. Intended to be used in situations where proper workspace setup is not
	 * available and run configurations created by default are lacking essential information.
	 *
	 * It is up to the caller to decide when it is appropriate to call this method.
	 *
	 * @param config
	 *            the configuration to be reconfigured.
	 */
	public void configureFromFileSystem(RunConfiguration config) {
		Iterable<IN4JSProject> allShippedProjects = runnerN4JSCore.getAllShippedProjects();
		IN4JSProject customRuntimeEnvironment = getCustomRuntimeEnvironment(config, allShippedProjects);
		reconfigure(config, allShippedProjects, customRuntimeEnvironment);
	}

	/**
	 * Reconfigures the configuration.
	 */
	protected void reconfigure(RunConfiguration config, Iterable<IN4JSProject> allShippedProjects,
			IN4JSProject customRuntimeEnvironment) {
		if (customRuntimeEnvironment == null) {
			throw new RuntimeException("Custom bootstrap code was requested but cannot be determined.");
		}

		List<IN4JSProject> customBootstrapProjects = new ArrayList<>();
		runnerHelper.recursiveExtendedREsCollector(customRuntimeEnvironment, customBootstrapProjects,
				allShippedProjects);
		if (!customBootstrapProjects.isEmpty()) {
			runnerFrontEnd.configureRuntimeEnvironment(config, customBootstrapProjects);
			Collection<String> coreProjectPaths = runnerHelper.getCoreProjectPaths(customBootstrapProjects);
			config.addCoreProjectPaths(coreProjectPaths);
		}
	}

	/**
	 * Obtains custom runtime environment based on the provided projects and config.
	 */
	protected IN4JSProject getCustomRuntimeEnvironment(RunConfiguration config,
			Iterable<IN4JSProject> allShippedProjects) {
		return runnerHelper.findRuntimeEnvironemtnWithName(
				runnerRegistry.getDescriptor(config.getRunnerId()).getEnvironment().getProjectId(),
				allShippedProjects).orNull();
	}
}
