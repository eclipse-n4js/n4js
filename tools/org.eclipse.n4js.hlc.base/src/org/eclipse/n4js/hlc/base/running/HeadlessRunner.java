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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.ExitCodeException;
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

	/**
	 * Actually start the requested file with the chosen runner. Workspace from headlesCompiler should be configured
	 * before calling this method.
	 *
	 * @param runner
	 *            the runner to be used
	 * @param implementationId
	 *            to be used for API-IMPL projects
	 * @param systemLoader
	 *            to be used when loading the modules
	 * @param locationToRun
	 *            location of the code to be executed
	 * @throws ExitCodeException
	 *             in cases of errors
	 */
	public void startRunner(String runner, String implementationId, String systemLoader, URI locationToRun)
			throws ExitCodeException {

		IRunnerDescriptor runnerDescriptor = checkRunner(runner);
		logger.info("Using runner :" + runnerDescriptor.getId());

		RunConfiguration runConfiguration = null;
		try {
			runConfiguration = runnerFrontEnd.createConfiguration(runnerDescriptor.getId(), implementationId,
					systemLoader, locationToRun);
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

		} catch (InterruptedException e1) {
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
		final List<IRunnerDescriptor> matchingRunnerDescs = findRunnerById(runner);

		if (matchingRunnerDescs.isEmpty()) {
			throw new ExitCodeException(EXITCODE_RUNNER_NOT_FOUND, "no runner found for id: " + runner);
		} else if (matchingRunnerDescs.size() > 1) {
			throw new ExitCodeException(EXITCODE_RUNNER_NOT_FOUND, "several runners match given id \"" + runner
					+ "\":\n\t" + Joiner.on("\n\t").join(matchingRunnerDescs));
		}

		return matchingRunnerDescs.get(0);
	}

	private List<IRunnerDescriptor> findRunnerById(String runnerId) {
		if (runnerId == null || runnerId.trim().isEmpty())
			return Collections.emptyList();
		// 1st attempt: look for exact match
		final IRunnerDescriptor rd = runnerRegistry.getDescriptors().get(runnerId);
		if (rd != null)
			return Collections.singletonList(rd);

		logger.warn("Could not find runner by ID: " + runnerId + ", switching to fuzzy search.");

		// 2nd attempt: look for sloppy match (but full segments required!)
		final int r_len = runnerId.length();
		final List<IRunnerDescriptor> matchingRDs = runnerRegistry.getDescriptors().values().stream()
				.map(descriptor -> descriptor.getId())
				.filter(id -> {
					final int id_len = id.length();
					return id_len >= r_len
							// a) id ends with runnerId (ignore case)
							&& id.substring(id_len - r_len, id_len).equalsIgnoreCase(runnerId)
					// b) full segment match (either full string or previous char is .)
							&& (id_len == r_len || id.charAt(id_len - r_len - 1) == '.');
				})
				.peek(id -> logger.debug("Candidate runner ID: " + id))
				.map(id -> runnerRegistry.getDescriptor(id))
				.collect(Collectors.toList());
		return matchingRDs;
	}

}
