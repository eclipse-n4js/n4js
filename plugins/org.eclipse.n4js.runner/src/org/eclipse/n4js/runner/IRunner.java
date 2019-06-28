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
 * All concrete runners for executing N4JS code are expected to implement this interface (for example, the node.js
 * runner).
 */
public interface IRunner {

	/**
	 * Create an empty run configuration appropriate for the receiving runner. Most runners will simply implement this
	 * with
	 *
	 * <pre>
	 * return new RunConfiguration();
	 * </pre>
	 *
	 * but some runners may choose to create a runner-specific subclass of {@link RunConfiguration}. The returned run
	 * configuration will be populated with default values by the {@link RunnerFrontEnd} and then method
	 * {@link #prepareConfiguration(RunConfiguration)} will be invoked.
	 */
	public RunConfiguration createConfiguration();

	/**
	 * Invoked by the {@link RunnerFrontEnd} from method {@link RunnerFrontEnd#computeDerivedValues(RunConfiguration)}
	 * to give a runner a chance to customize the run configuration in a runner-specific way, in particular to compute
	 * additional, runner-specific derived values. When this method is called, the given configuration will have been
	 * populated with default values by the {@link RunnerFrontEnd}. Many runners will simply leave the given
	 * configuration unchanged.
	 */
	public void prepareConfiguration(RunConfiguration config);

	/**
	 * Actually launch the code, based on the given configuration by calling method
	 * {@link IExecutor#exec(String[], File, Map) exec()} on the given executor.
	 */
	public Process run(RunConfiguration config, IExecutor executor) throws ExecutionException;
}
