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
package org.eclipse.n4js.hlc.base.running;

import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_RUNNER_NOT_FOUND;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_RUNNER_STOPPED_WITH_ERROR;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.extension.IRunnerDescriptor;
import org.eclipse.n4js.runner.extension.RunnerRegistry;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Headless runner creates external process that executes code in the provided location.
 */
public class HeadlessRunner {

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private RunnerRegistry runnerRegistry;

	@Inject
	private IHeadlessLogger logger;

	@Inject
	RunnableLookupHelper runnerLookup;

	/**
	 * Actually start the requested file with the chosen runner. Workspace from headlesCompiler should be configured
	 * before calling this method.
	 *
	 * @param runner
	 *            the runner to be used
	 * @param implementationId
	 *            to be used for API-IMPL projects
	 * @param locationToRun
	 *            location of the code to be executed
	 * @param additionalPaths
	 *            location for externally installed node_modules
	 * @throws ExitCodeException
	 *             in cases of errors
	 */
	public void startRunner(String runner, N4JSProjectName implementationId, URI locationToRun,
			Collection<String> additionalPaths) throws ExitCodeException {

		IRunnerDescriptor runnerDescriptor = checkRunner(runner);
		logger.info("Using runner :" + runnerDescriptor.getId());

		RunConfiguration runConfiguration = null;
		try {
			runConfiguration = runnerFrontEnd.createConfiguration(runnerDescriptor.getId(), implementationId,
					locationToRun);

			runConfiguration.addAdditionalPath(additionalPaths);

		} catch (java.lang.IllegalStateException e2) {
			logger.error(Throwables.getStackTraceAsString(e2));
			throw new ExitCodeException(EXITCODE_RUNNER_STOPPED_WITH_ERROR,
					"Cannot create run configuration.", e2);
		}

		try {
			Process process = runnerFrontEnd.run(runConfiguration);

			int exit = process.waitFor();

			if (exit != 0) {
				throw new ExitCodeException(EXITCODE_RUNNER_STOPPED_WITH_ERROR,
						"The spawned runner '" + runnerDescriptor.getId()
								+ "' exited with code=" + exit);
			}

		} catch (Exception e1) {
			logger.error(Throwables.getStackTraceAsString(e1));
			throw new ExitCodeException(EXITCODE_RUNNER_STOPPED_WITH_ERROR,
					"The spawned runner exited by throwing an exception", e1);
		}
	}

	/**
	 * Reads the runner name/ID from the --runWith command line option and checks for validity and, if successful,
	 * returns the {@link IRunnerDescriptor} for the given name/ID. Otherwise, an {@link ExitCodeException} is thrown.
	 *
	 * @return descriptor of runner selected via command line option --runWith
	 * @throws ExitCodeException
	 *             in Error cases
	 */
	private IRunnerDescriptor checkRunner(String runner) throws ExitCodeException {
		final List<IRunnerDescriptor> matchingRunnerDescs = runnerLookup.<IRunnerDescriptor> findRunnableById(
				runner,
				runnerRegistry.getDescriptors());

		if (matchingRunnerDescs.isEmpty()) {
			throw new ExitCodeException(EXITCODE_RUNNER_NOT_FOUND, "no runner found for id: " + runner);
		} else if (matchingRunnerDescs.size() > 1) {
			throw new ExitCodeException(EXITCODE_RUNNER_NOT_FOUND, "several runners match given id \"" + runner
					+ "\":\n\t" + Joiner.on("\n\t").join(matchingRunnerDescs));
		}

		return matchingRunnerDescs.get(0);
	}

}
