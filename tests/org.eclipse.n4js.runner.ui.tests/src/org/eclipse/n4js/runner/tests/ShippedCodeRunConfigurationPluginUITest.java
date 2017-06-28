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
package org.eclipse.n4js.runner.tests;

import static com.google.common.collect.Sets.difference;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFileBasedShippedCodeConfigurationHelper;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.ProjectUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Tests if runner configuration computed with workspace access to the shipped code is corresponds to the configuration
 * using alternative access. Basically checks if derived values produced equivalent configuration in both cases.
 */
public class ShippedCodeRunConfigurationPluginUITest extends AbstractBuilderParticipantTest {
	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	/** ID of the Node.js runner as defined in the plugin.xml. */
	private static final String NODE_RUNNER_ID = "org.eclipse.n4js.runner.nodejs.NODEJS";

	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "ws_runner_config";
	private static final String CLIENT = "Client";
	private static final String MODULE_TO_RUN = "ClientModule";
	private static final URI MODULE_TO_RUN_URI = URI.createPlatformResourceURI(
			CLIENT + "/" + "src" + "/" + MODULE_TO_RUN + ".n4js", true);

	private static final String SHIPPED_CODE_PATH_PREFIX = buildPattern();
	private static final String EMPTY_STRING = "";
	private static final String NL = "\n";

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private RunnerFileBasedShippedCodeConfigurationHelper shippedCodeConfigurationHelper;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	StringJoiner logs = new StringJoiner(NL);

	/** Rule that will log extra information in case test fails. */
	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			System.out.println(logs.toString());
			super.failed(e, description);
		}
	};

	/**
	 * Enables built ins.
	 */
	@Before
	public void setupWorkspace() throws Exception {
		super.setUp();
		shippedCodeInitializeTestHelper.setupBuiltIns();
		waitForAutoBuild();
	}

	/**
	 * Undoes changes to the external libraries.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		shippedCodeInitializeTestHelper.teardowneBuiltIns();
		waitForAutoBuild();
		super.tearDown();
	}

	/**
	 * Checks if exec module, init modules, and class path (core project paths) are in sync.
	 */
	@Test
	public void testBootstrapConfiguration() throws Exception {

		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectUtils.importProject(projectsRoot, CLIENT);
		waitForAutoBuild();

		final RunConfiguration config = runnerFrontEnd.createConfiguration(NODE_RUNNER_ID,
				MODULE_TO_RUN_URI);

		assertFalse("Expected some init modules", config.getInitModules().isEmpty());

		// compute config
		runnerFrontEnd.computeDerivedValues(config);
		String execModule1 = config.getExecModule();
		assertFalse("Expected exec module to be configured", Strings.isNullOrEmpty(execModule1));
		Set<String> execModules1 = new HashSet<>(config.getInitModules());
		assertFalse("Expected some init modules", execModules1.isEmpty());
		Set<String> corePaths1 = new HashSet<>(config.getCoreProjectPaths());
		assertFalse("Expected core projects paths to contain more than project output", corePaths1.size() <= 1);

		// reconfigure config
		shippedCodeConfigurationHelper.configureFromFileSystem(config);
		String execModule2 = config.getExecModule();
		assertFalse("Expected exec module to be configured", Strings.isNullOrEmpty(execModule2));
		Set<String> execModules2 = new HashSet<>(config.getInitModules());
		assertFalse("Expected some init modules", execModules2.isEmpty());
		Set<String> corePaths2 = new HashSet<>(config.getCoreProjectPaths());
		assertFalse("Expected core projects paths to contain more than project output", corePaths2.size() <= 1);

		// record data to log in case of failure
		recordLogData(">>> Configured paths", corePaths1, "<<< Configured paths");
		recordLogData(">>> Reconfigured paths", corePaths2, "<<< Reconfigured paths");

		// compare if for every path in the first config
		// there exists alternative path in the second config

		// reconfiguration adds new paths, remove previously known to get only those added
		corePaths2.removeAll(corePaths1);
		//
		Set<String> configuredCorePaths = processPaths(corePaths1);
		Set<String> reconfiguredCorePaths = processPaths(corePaths2);

		final Collection<String> removedPaths = difference(configuredCorePaths, reconfiguredCorePaths);
		final Collection<String> addedPaths = difference(reconfiguredCorePaths, configuredCorePaths);
		assertTrue("Expected no paths removed, but diff was " + removedPaths.size(), removedPaths.isEmpty());
		assertTrue("Expected no paths added, but diff was " + addedPaths.size(), addedPaths.isEmpty());

	}

	/**
	 * remove from paths client project itself, then drop path prefix that changes, e.g.
	 * {@code "/Users/me/repo/n4js/plugins/org.eclipse.n4js.external.libraries/shipped-code/runtime/n4js-node/src/js/index.js"}
	 * becomes {@code "runtime/n4js-node/src/js/index.js"}
	 */
	private Set<String> processPaths(Collection<String> paths) {
		return paths.stream()
				// filter project path itself
				.filter(path -> !path.contains(CLIENT))
				// strip path prefix
				.map(path -> path.replaceFirst(SHIPPED_CODE_PATH_PREFIX, EMPTY_STRING))
				.collect(Collectors.toSet());
	}

	private void recordLogData(String prefix, Set<String> data, String suffix) {
		logs.add(prefix);
		data.forEach(logs::add);
		logs.add(suffix);
		logs.add(NL);
	}

	/**
	 * Depending on the resource loading context shipped paths will have different prefix. This pattern should handle
	 * various forms of, e.g.
	 *
	 * <pre>
	 * <ul>
	 * <li>/jenkins_slave_home/workspace/job/n4js/tests/org.eclipse.n4js.runner.ui.tests/target/work/plugins/org.eclipse.n4js.external.libraries_0.0.1.201706161936/shipped-code/runtime/n4js-es5/src-gen/es
	 * <li>/jenkins_slave_home/workspace/job/n4js/tests/org.eclipse.n4js.runner.ui.tests/target/work/plugins/org.eclipse.n4js.external.libraries_0.0.1.201706161936/runtime/n4js-es5/src-gen/es
	 * <li>/Users/me/git/n4js/plugins/org.eclipse.n4js.external.libraries/bin/runtime/n4js-es5/src-gen/es
	 * <li>/Users/me/git/n4js/plugins/org.eclipse.n4js.external.libraries/shipped-code/runtime/n4js-es5/src-gen/es
	 * </ul>
	 * </pre>
	 */
	private static String buildPattern() {
		StringJoiner regex = new StringJoiner("|", ".*(\\\\|\\/)(", ")(\\\\|\\/)");
		ExternalLibrariesActivator.EXTERNAL_LIBRARY_FOLDER_NAMES.forEach(regex::add);
		return regex.toString();
	}

}
