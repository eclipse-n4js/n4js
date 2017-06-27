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
package org.eclipse.n4js.runner;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * An instance of this interface will be provided to the runner to provide the functionality for actual execution.
 */
public interface IExecutor {
	/**
	 * Implementors of {@link IRunner#run(RunConfiguration, IExecutor)} are expected to invoke this method in order to
	 * perform execution. In headless mode, this method will forward to {@link Runtime#exec(String[], String[], File)},
	 * within Eclipse it will forward to <code>DebugPlugin#exec(String[], File, String[])</code>.
	 */
	public Process exec(String[] cmdLine, File workingDirectory, Map<String, String> env) throws ExecutionException;
}
