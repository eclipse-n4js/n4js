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
package org.eclipse.n4js.tests.util;

import java.util.concurrent.ExecutionException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.nodejs.NodeRunner;
import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.inject.Inject;

/**
 * Like {@link ProjectTestsUtils}, but contains stuff that requires dependency injection.
 */
public class ProjectTestsHelper {

	@Inject
	private RunnerFrontEndUI runnerFrontEndUI;
	@Inject
	private RunnerFrontEnd runnerFrontEnd;
	@Inject
	private ProcessExecutor processExecutor;

	/**
	 * Run an N4JS file in the same way as if right-clicking and selecting "Run As ..." / "Launch in Node.js".
	 * <p>
	 * Never returns <code>null</code> or a result with errors (i.e {@link ProcessResult#isOK() isOK()} will always
	 * return <code>true</code>).
	 *
	 * @param moduleToRun
	 *            a platform resource URI pointing to the module to run.
	 */
	public ProcessResult runWithNodeRunnerUI(URI moduleToRun) throws ExecutionException {
		return runWithRunnerUI(NodeRunner.ID, null, moduleToRun);
	}

	/**
	 * Run an N4JS file in the same way as if right-clicking and selecting "Run As ...".
	 * <p>
	 * Never returns <code>null</code> or a result with errors (i.e {@link ProcessResult#isOK() isOK()} will always
	 * return <code>true</code>).
	 *
	 * @param moduleToRun
	 *            a platform resource URI pointing to the module to run.
	 */
	public ProcessResult runWithRunnerUI(String runnerId, N4JSProjectName implementationId, URI moduleToRun)
			throws ExecutionException {
		RunConfiguration config = runnerFrontEnd.createConfiguration(runnerId, implementationId, moduleToRun);
		Process process = runnerFrontEndUI.runInUI(config);
		ProcessResult result = processExecutor.execute(process, "", OutputRedirection.REDIRECT);
		if (!result.isOK()) {
			throw new IllegalStateException("runner process exited with error: " + result);
		}
		return result;
	}
}
