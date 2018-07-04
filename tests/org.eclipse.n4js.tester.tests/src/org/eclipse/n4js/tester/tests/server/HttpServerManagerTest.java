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
package org.eclipse.n4js.tester.tests.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.MockTesterModule;
import org.eclipse.n4js.tester.tests.TesterTestsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test for checking the runtime behavior of the {@link HttpServerManager HTTP server manager}.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { MockTesterModule.class })
public class HttpServerManagerTest extends AbstractTestTreeTest {

	@Inject
	private HttpServerManager manager;

	@Inject
	private TesterTestsConfiguration ttConfig;

	/***/
	@Test
	public void testStartServer() {
		// first init the configuration:
		ttConfig.computePortAndValidConfig();

		assertFalse(isRunning(ttConfig.getPORT()));
		manager.startServer(ttConfig.getVALID_CONFIG());
		assertTrue(isRunning(ttConfig.getPORT()));
		manager.stopServer(ttConfig.getPORT());
		assertFalse(isRunning(ttConfig.getPORT()));
	}

	private boolean isRunning(final int port) {
		if (0 > port || 65535 < port) {
			throw new IllegalArgumentException("Invalid port number: " + port);
		}
		try (final ServerSocket ss = new ServerSocket(port); final DatagramSocket ds = new DatagramSocket(port)) {
			ss.setReuseAddress(true);
			ds.setReuseAddress(true);
			return false;
		} catch (final IOException e) {
			return true;
		}
	}

}
