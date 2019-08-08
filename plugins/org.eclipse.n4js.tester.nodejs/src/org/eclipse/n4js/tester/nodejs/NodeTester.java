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
package org.eclipse.n4js.tester.nodejs;

import static org.eclipse.n4js.runner.extension.RuntimeEnvironment.NODEJS_MANGELHAFT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.runner.IExecutor;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.nodejs.NodeRunner;
import org.eclipse.n4js.tester.ITester;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.extension.ITesterDescriptor;
import org.eclipse.n4js.tester.extension.TesterDescriptorImpl;

import com.google.common.base.Charsets;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
public class NodeTester implements ITester {

	/** ID of the Node.js tester as defined in the plugin.xml. */
	public static final String ID = "org.eclipse.n4js.tester.nodejs.NODEJS_MANGELHAFT";

	/**
	 * Class for providing descriptors for the Node.js tester with the same information as defined in the plugin.xml
	 * (used only by N4jsc). Supports instance supplying with injection.
	 */
	public static final class NodeTesterDescriptorProvider implements Provider<ITesterDescriptor> {

		@Inject
		private Provider<NodeTester> nodeTesterProvider;

		@Override
		public ITesterDescriptor get() {
			return new TesterDescriptorImpl(ID, "Node.js Tester", NODEJS_MANGELHAFT, nodeTesterProvider.get());
		}

	}

	@Override
	public TestConfiguration createConfiguration() {
		return new TestConfiguration();
	}

	@Override
	public void prepareConfiguration(TestConfiguration config) {
		// no special preparations required
	}

	@Override
	public String getRunnerIdForTesting() {
		return NodeRunner.ID;
	}

	@Override
	public Process test(TestConfiguration config, IExecutor executor, RunnerFrontEnd runnerFrontEnd)
			throws ExecutionException {

		Path testCatalog = createTestCatalog(config);

		Path fileToRun = findMangelhaftCliEntryPoint(config);
		config.setFileToRun(fileToRun);

		config.setRunOptions("--testCatalog " + testCatalog.toAbsolutePath() + " --quiet");

		return runnerFrontEnd.run(config, executor);
	}

	private Path createTestCatalog(TestConfiguration config) {
		try {
			Path testCatalog = Files.createTempFile("n4js-testCatalog-", "");
			testCatalog.toFile().deleteOnExit();
			String testTreeAsJSON = config.getTestTreeAsJSON();
			Files.write(testCatalog, Collections.singletonList(testTreeAsJSON), Charsets.UTF_8);
			return testCatalog;
		} catch (IOException e) {
			throw new IllegalStateException("unable to write test catalog to temporary file", e);
		}
	}

	private Path findMangelhaftCliEntryPoint(TestConfiguration config) {
		Path workingDirectory = config.getWorkingDirectory();
		if (workingDirectory == null) {
			throw new IllegalArgumentException("test configuration does not specify a working directory");
		}

		Path base = workingDirectory;
		while (base != null
				&& !Files.isDirectory(
						base.resolve(N4JSGlobals.NODE_MODULES).resolve(N4JSGlobals.MANGELHAFT_CLI.getRawName()))) {
			base = base.getParent();
		}
		if (base == null) {
			throw new IllegalStateException("unable to find npm package '" + N4JSGlobals.MANGELHAFT_CLI + "'");
		}

		return base.resolve(N4JSGlobals.NODE_MODULES).resolve(N4JSGlobals.MANGELHAFT_CLI.getRawName())
				.resolve("bin/n4js-mangelhaft-cli.js");
	}
}
