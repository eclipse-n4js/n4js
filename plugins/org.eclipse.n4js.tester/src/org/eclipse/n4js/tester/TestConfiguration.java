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

import java.util.Map;

import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.tester.domain.TestTree;

/**
 * Special case of a {@link RunConfiguration} used for testing. Adds the following attributes:
 * <ul>
 * <li><code>resourceToTest</code> ({@link RunConfiguration primary} and {@link RunConfiguration persistent})
 * </ul>
 */
public class TestConfiguration extends RunConfiguration {

	/**
	 * Key used for attribute specifying the tester ID.
	 */
	public final static String TESTER_ID = "TESTER_ID";

	/** Within the execution data passed to the exec module, this key is used to store the test tree. */
	public final static String EXEC_DATA_KEY__TEST_TREE = "testTree";

	private String testerId;

	private TestTree testTree;

	private int resultReportingPort; // volatile

	private String launchConfigurationTypeIdentifier;

	/**
	 * Identifier of the tester to use.
	 */
	public String getTesterId() {
		return testerId;
	}

	/** @see #getTesterId() */
	public void setTesterId(String testerId) {
		this.testerId = testerId;
	}

	/**
	 * Return (computed) tree of tests to execute.
	 */
	public TestTree getTestTree() {
		return testTree;
	}

	/** @see #getTestTree() */
	public void setTestTree(TestTree testTree) {
		this.testTree = testTree;
	}

	@Override
	public Map<String, Object> readPersistentValues() {
		final Map<String, Object> map = super.readPersistentValues();
		map.put(TESTER_ID, this.testerId);
		return map;
	}

	@Override
	public void writePersistentValues(Map<String, Object> map) {
		super.writePersistentValues(map);
		this.testerId = RunConfiguration.getString(map, TESTER_ID, false);
	}

	/**
	 * @param port
	 *            actual port number of the server listening for test results.
	 */
	public void setResultReportingPort(int port) {
		this.resultReportingPort = port;
	}

	/**
	 * @return TCP-port of the result listening server
	 */
	public int getResultReportingPort() {
		return this.resultReportingPort;
	}

	/**
	 * Sets the launch configuration type identifier, required to enable shortcuts for opening the launch configuration.
	 */
	public void setLaunchConfigurationTypeIdentifier(String identifier) {
		this.launchConfigurationTypeIdentifier = identifier;

	}

	/**
	 * Returns the launchConfigurationTypeIdentifier previously set via
	 * {@link #setLaunchConfigurationTypeIdentifier(String)}
	 */
	public String getLaunchConfigurationTypeIdentifier() {
		return launchConfigurationTypeIdentifier;
	}

}
