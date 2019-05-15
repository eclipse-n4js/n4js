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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
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

	/** Key used for attribute specifying test cases, also see {@link #getTestMethodSelection()} */
	public final static String TESTCASE_SELECTION = "TESTCASE_SELECTION";

	private String testerId;

	private TestTree testTree;

	private String testTreeAsJSON;

	private int resultReportingPort; // volatile

	private String launchConfigurationTypeIdentifier;

	/**
	 * Test methods specifications, see {@link #getTestMethodSelection()} for details.
	 */
	private List<URI> testMethods;

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

	/**
	 * Returns the {@link #getTestTree() test tree} in JSON format, possibly including some additional information not
	 * part of the original test tree as returned by {@link #getTestTree()}, e.g. HTTP server port.
	 */
	public String getTestTreeAsJSON() {
		return testTreeAsJSON;
	}

	/** @see #getTestTreeAsJSON() */
	public void setTestTreeAsJSON(String testTreeAsJSON) {
		this.testTreeAsJSON = testTreeAsJSON;
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

	/**
	 * Returns list of selected test methods. A test method selection is a fully qualified URI method specifier, e.g.
	 * "platform:/resource/TestProj/test/pack/Test_TestProj2.n4js#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0".
	 * If this returns a non-empty list, the user selection is ignored. This is usually only used for running tests from
	 * the test view (run all failures).
	 *
	 * @return unmodifiable list with method specifiers or empty, never null
	 */
	public List<URI> getTestMethodSelection() {
		return testMethods == null ? Collections.emptyList() : Collections.unmodifiableList(testMethods);
	}

	/**
	 * Sets test method specifiers, see {@link #getTestMethodSelection()} for details.
	 */
	public void setTestMethodSelection(List<URI> testMethods) {
		this.testMethods = testMethods;
	}

}
