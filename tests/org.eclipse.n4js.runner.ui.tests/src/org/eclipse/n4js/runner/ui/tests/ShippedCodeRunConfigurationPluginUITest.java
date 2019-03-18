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
package org.eclipse.n4js.runner.ui.tests;

import static com.google.common.collect.Sets.difference;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.runner.RunConfiguration;
import org.eclipse.n4js.runner.RunnerFileBasedShippedCodeConfigurationHelper;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.base.Joiner;
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

	private static final String NL = "\n";

	@Inject
	private RunnerFrontEnd runnerFrontEnd;

	@Inject
	private RunnerFileBasedShippedCodeConfigurationHelper shippedCodeConfigurationHelper;

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

	@Override
	protected boolean provideShippedCode() {
		return true;
	}

	/**
	 * Checks if exec module, init modules, and class path (core project paths) are in sync.
	 */
	@Test
	public void testBootstrapConfiguration() throws Exception {

		final File projectsRoot = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		ProjectTestsUtils.importProject(projectsRoot, CLIENT);
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
		Map<Path, String> corePaths1 = new LinkedHashMap<>(config.getCoreProjectPaths());
		assertFalse("Expected core projects paths to contain more than project output", corePaths1.size() <= 1);

		// clear project paths to ensure we only get paths newly computed in #configureFromFileSystem()
		config.setCoreProjectPaths(Collections.emptyMap());

		// reconfigure config
		shippedCodeConfigurationHelper.configureFromFileSystem(config);
		String execModule2 = config.getExecModule();
		assertFalse("Expected exec module to be configured", Strings.isNullOrEmpty(execModule2));
		Set<String> execModules2 = new HashSet<>(config.getInitModules());
		assertFalse("Expected some init modules", execModules2.isEmpty());
		Map<Path, String> corePaths2 = new LinkedHashMap<>(config.getCoreProjectPaths());
		assertFalse("Expected core projects paths to contain more than project output", corePaths2.size() <= 1);

		// record data to log in case of failure
		recordLogData(">>> Configured paths", corePaths1, "<<< Configured paths");
		recordLogData(">>> Reconfigured paths", corePaths2, "<<< Reconfigured paths");

		// compare if for every path in the first config
		// there exists alternative path in the second config

		Set<String> configuredCorePaths = processPaths(corePaths1);
		Set<String> reconfiguredCorePaths = processPaths(corePaths2);

		final Collection<String> removedPaths = difference(configuredCorePaths, reconfiguredCorePaths);
		final Collection<String> addedPaths = difference(reconfiguredCorePaths, configuredCorePaths);
		assertTrue("Expected no paths removed, but diff was " + removedPaths.size(), removedPaths.isEmpty());
		assertTrue("Expected no paths added, but diff was " + addedPaths.size() +
				". The following paths were added: \n    " +
				Joiner.on("\n    ").join(addedPaths), addedPaths.isEmpty());

	}

	/**
	 * remove from paths client project itself.
	 */
	private Set<String> processPaths(Map<Path, String> paths) {
		return paths.keySet().stream()
				.map(Object::toString)
				// filter project path itself
				.filter(path -> !path.contains(CLIENT))
				.collect(Collectors.toSet());
	}

	private void recordLogData(String prefix, Map<Path, String> data, String suffix) {
		logs.add(prefix);
		data.keySet().stream().map(Object::toString).forEach(logs::add);
		logs.add(suffix);
		logs.add(NL);
	}
}
