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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.extension.ITesterDescriptor;
import org.eclipse.n4js.tester.extension.TesterRegistry;
import org.eclipse.n4js.tester.internal.DefaultTestTreeTransformer;
import org.eclipse.n4js.tester.internal.TesterActivator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class TesterFrontEnd {

	private static final int PORT_PLACEHOLDER_MAGIC_NUMBER = 919191919;

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private TesterRegistry testerRegistry;

	@Inject
	private TestDiscoveryHelper testDiscoveryHelper;

	@Inject
	private DefaultTestTreeTransformer testTreeTransformer;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private TesterFacade testerFacade;

	/**
	 * Similar to {@link RunnerFrontEnd#canRun(String, URI)}, but for testing.
	 */
	public boolean canTest(@SuppressWarnings("unused") final URI resourceToTest) {
		return true; // FIXME IDE-1393 consider re-using IsTestableLocationPropertyTester
	}

	/**
	 * Sugar for creating a new configuration from the existing configuration. All properties are copied expect the
	 * {@link TestTree#getSessionId() session ID} of the test tree.
	 *
	 * @param configuration
	 *            the configuration to replicate.
	 * @return a new test configuration with different test tree session ID.
	 */
	public TestConfiguration createConfiguration(final TestConfiguration configuration) {
		return createConfiguration(configuration.getTesterId(), configuration.getImplementationId(),
				configuration.getUserSelection());
	}

	/**
	 * Similar to {@link RunnerFrontEnd#createConfiguration(String, String, URI)}, but for testing.
	 *
	 * @param testerId
	 *            ID of the tester to use.
	 * @param implementationId
	 *            implementation ID to use or <code>null</code>. See {@link RunConfiguration#getImplementationId() here}
	 *            for details.
	 * @param moduleToTest
	 *            URI referencing a resource to test. Can be an N4JS project, or a folder or file within an N4JS project
	 *            for which {@link #canTest(URI)} returns true.
	 * @return the new run configuration.
	 */
	public TestConfiguration createConfiguration(String testerId, String implementationId, final URI moduleToTest) {
		final ITesterDescriptor testerDesc = testerRegistry.getDescriptor(testerId);
		final ITester tester = testerDesc.getTester();

		final TestConfiguration config = tester.createConfiguration();
		config.setName(computeConfigurationName(testerId, moduleToTest));
		config.setTesterId(testerId);
		config.setRunnerId(tester.getRunnerIdForTesting());
		config.setRuntimeEnvironment(testerDesc.getEnvironment());
		config.setImplementationId(implementationId);
		config.setUserSelection(moduleToTest);

		computeDerivedValues(config);

		return config;
	}

	/**
	 * Similar to {@link RunnerFrontEnd#createConfiguration(Map)}, but for testing.
	 */
	public TestConfiguration createConfiguration(Map<String, Object> values) {
		final String testerId = RunConfiguration.getString(values, TestConfiguration.TESTER_ID, false);
		final ITester tester = testerRegistry.getTester(testerId);

		final TestConfiguration config = tester.createConfiguration();
		config.writePersistentValues(values);

		List<String> testCaseURIStrings = RunConfiguration.getListOfString(values,
				TestConfiguration.TESTCASE_SELECTION, true);
		if (testCaseURIStrings != null && !testCaseURIStrings.isEmpty()) {
			List<URI> uris = testCaseURIStrings.stream().map(s -> URI.createURI(s))
					.collect(Collectors.toList());
			config.setTestMethodSelection(uris);
		}
		computeDerivedValues(config);

		return config;
	}

	/**
	 * Similar to {@link RunnerFrontEnd#computeDerivedValues(RunConfiguration)}, but for testing.
	 */
	public void computeDerivedValues(TestConfiguration config) {
		// A) compute derived values for the run(!) configuration (will delegate to runner)
		runnerFrontEnd.computeDerivedValues(config, false);

		// B) compute derived values for the test configuration

		// B.1) compute the test tree (note: will create a new session ID every time this is called)

		List<URI> testMethods = config.getTestMethodSelection();
		if (testMethods.isEmpty()) {
			testMethods = Collections.singletonList(config.getUserSelection());
		}

		final TestTree testTree = testDiscoveryHelper.collectTests(testMethods);
		config.setTestTree(testTree);

		// B.2) pass test tree as execution data
		try {
			// Read out port of running IDE-server and pass it to end-point-computation in tree-transformer
			TesterActivator testerActivator = TesterActivator.getInstance();
			int port = testerActivator != null ? testerActivator.getServerPort() : PORT_PLACEHOLDER_MAGIC_NUMBER;
			config.setResultReportingPort(port);
			testTreeTransformer.setHttpServerPort(Integer.toString(port));
			final String testTreeAsJSON = objectMapper.writeValueAsString(testTreeTransformer.apply(testTree));
			config.setExecutionData(TestConfiguration.EXEC_DATA_KEY__TEST_TREE, testTreeAsJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// B.3) delegate further computation to the specific tester implementation
		ITester tester = testerRegistry.getTester(config);
		tester.prepareConfiguration(config);
	}

	/**
	 * Similar to {@link RunnerFrontEnd#run(String, String, URI)}, but for testing.
	 */
	public Process test(String testerId, String implementationId, URI resourceToTest) {
		return test(createConfiguration(testerId, implementationId, resourceToTest));
	}

	/**
	 * Similar to {@link RunnerFrontEnd#run(RunConfiguration)}, but for testing.
	 */
	public Process test(TestConfiguration config) {
		return test(config, runnerFrontEnd.createDefaultExecutor());
	}

	/**
	 * Similar to {@link RunnerFrontEnd#run(RunConfiguration, IExecutor)}, but for testing.
	 */
	public Process test(TestConfiguration config, IExecutor executor) {
		final TestTree testTree = config.getTestTree();

		// prepare HTTP server for receiving test results
		int port = testerFacade.prepareTestSession(testTree);
		updateTestTreeDescription(config, port);

		// actually launch the test
		ITester tester = testerRegistry.getTester(config);
		return tester.test(config, executor, runnerFrontEnd);
	}

	/**
	 * Update the configuration to the real port values. If the configuration was created before the server was started,
	 * then the {@link #PORT_PLACEHOLDER_MAGIC_NUMBER} is inserted as a placeholder.
	 *
	 * Exchanges this placeholder with the passed in port value under the
	 * {@link TestConfiguration#EXEC_DATA_KEY__TEST_TREE} key.
	 *
	 * Sets the {@link TestConfiguration#setResultReportingPort(int)} to port.
	 *
	 * @param config
	 *            to be updated
	 * @param port
	 *            effective port
	 */
	private static void updateTestTreeDescription(TestConfiguration config, int port) {
		if (config.getResultReportingPort() == PORT_PLACEHOLDER_MAGIC_NUMBER) {
			// probably running in a non-UI variant, update the magic number with real port:
			Map<String, Object> execData = config.getExecutionData();
			String testTreeJsonEncoded = (String) execData.get(TestConfiguration.EXEC_DATA_KEY__TEST_TREE);
			String updatedTestTreeJsonEncoded = testTreeJsonEncoded
					.replaceFirst(Integer.toString(PORT_PLACEHOLDER_MAGIC_NUMBER), Integer.toString(port));
			execData.put(TestConfiguration.EXEC_DATA_KEY__TEST_TREE, updatedTestTreeJsonEncoded);
		}
		config.setResultReportingPort(port);
	}

	/**
	 * Create a default name for a new test configuration with the given runnerId and moduleToTest.
	 */
	private String computeConfigurationName(String testerId, URI moduleToTest) {
		String modulePath = moduleToTest.path();
		modulePath = stripStart(modulePath, "/", "resource/", "plugin/");
		final String moduleName = modulePath.replace('/', '-');
		final String runnerName = testerRegistry.getDescriptor(testerId).getName();
		return moduleName + " (" + runnerName + ")";
	}

	private static final String stripStart(String str, String... prefixesToStrip) {
		for (String currPrefix : prefixesToStrip) {
			if (str.startsWith(currPrefix)) {
				str = str.substring(currPrefix.length());
			}
		}
		return str;
	}

}
