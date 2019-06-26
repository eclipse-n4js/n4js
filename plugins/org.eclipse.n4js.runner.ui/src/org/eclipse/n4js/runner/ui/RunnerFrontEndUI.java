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

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Similar to {@link RunnerFrontEnd}, but adds a method for running code within Eclipse.
 */
@Singleton
public class RunnerFrontEndUI {

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	/**
	 * Convenience method. Same as {@link RunnerFrontEnd#run(RunConfiguration, IExecutor)}, but uses a default executor
	 * that will delegate to {@link DebugPlugin#exec(String[], File, String[])}.
	 * <p>
	 * This method may be invoked from non-UI threads.
	 */
	public Process runInUI(RunConfiguration config) throws ExecutionException {
		return runnerFrontEnd.run(config, createEclipseExecutor());
	}

	/**
	 * Returns a new executor that will delegate execution to {@link DebugPlugin#exec(String[], File, String[])} for
	 * execution within the Eclipse launch framework. This executor is intended for the UI case.
	 *
	 * @see RunnerFrontEnd#createDefaultExecutor()
	 */
	public IExecutor createEclipseExecutor() {
		return new IExecutor() {
			@Override
			public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> envp)
					throws ExecutionException {

				String[] envpArray = envp.entrySet().stream()
						.map(pair -> pair.getKey() + "=" + pair.getValue())
						.toArray(String[]::new);
				try {
					return DebugPlugin.exec(cmdLine, workingDirectory, envpArray);
				} catch (CoreException e) {
					throw new ExecutionException(e);
				}
			}
		};
	}
}
