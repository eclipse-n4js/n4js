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
package org.eclipse.n4js.tester;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunnerFrontEnd;

/**
 * All concrete testers for launching N4JS test code are expected to implement this interface (for example, the node.js
 * tester).
 */
public interface ITester {

	/**
	 * Create an empty test configuration appropriate for the receiving tester. Most testers will simply implement this
	 * with
	 *
	 * <pre>
	 * return new TestConfiguration();
	 * </pre>
	 *
	 * but some testers may choose to create a tester-specific subclass of {@link TestConfiguration}. The returned test
	 * configuration will be populated with default values by the {@link TesterFrontEnd} and then method
	 * {@link #prepareConfiguration(TestConfiguration)} will be invoked.
	 */
	public TestConfiguration createConfiguration();

	/**
	 * Invoked by the {@link TesterFrontEnd} from method {@link TesterFrontEnd#computeDerivedValues(TestConfiguration)}
	 * to give a tester a chance to customize the test configuration in a tester-specific way, in particular to compute
	 * additional, tester-specific derived values. When this method is called, the given configuration will have been
	 * populated with default values by the {@link TesterFrontEnd}. Many testers will simply leave the given
	 * configuration unchanged.
	 */
	public void prepareConfiguration(TestConfiguration config);

	/**
	 * Used to be used to delegate test code execution to a specific runner.
	 *
	 * Depending IDE-1393 this might get removed.
	 */
	public String getRunnerIdForTesting();

	/**
	 * Actually launch the code, based on the given configuration by calling method
	 * {@link IExecutor#exec(String[], File, Map) exec()} on the given executor.
	 */
	public Process test(TestConfiguration config, IExecutor executor, RunnerFrontEnd runnerFrontEnd)
			throws ExecutionException;
}
