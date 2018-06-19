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
package org.eclipse.n4js.tester.internal;

import static java.util.Collections.singletonMap;
import static org.eclipse.n4js.tester.TesterModuleDefaults.HTTP_SERVER_PORT_KEY;
import static org.eclipse.n4js.tester.server.HttpServerManager.HTTP_PORT;

import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.server.JettyManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Facade implementation for setting up the infrastructure for a test session.
 */
@Singleton
public class TesterFacadeImpl implements TesterFacade {

	private final TestFsmRegistry fsmRegistry;
	private final HttpServerManager serverManager;
	private final InternalTestTreeRegistry treeRegistry;
	private final int port;
	private int actualPort = -1;

	@Inject
	TesterFacadeImpl(final TestFsmRegistry fsmRegistry, final HttpServerManager serverManager,
			final InternalTestTreeRegistry treeRegistry, @Named(HTTP_SERVER_PORT_KEY) final int port) {

		this.fsmRegistry = fsmRegistry;
		this.serverManager = serverManager;
		this.treeRegistry = treeRegistry;
		this.port = port;
		this.actualPort = port; // try with default.
	}

	@Override
	public int prepareTestSession(final TestTree tree) {
		if (!serverManager.isRunning(actualPort)) {
			actualPort = ((JettyManager) serverManager).ensurePortIsAvailable(port);
			serverManager.startServer(singletonMap(HTTP_PORT, actualPort));
		}

		final String sessionId = tree.getSessionId().getValue();
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(tree);
		return actualPort;
	}

	@Override
	public void shutdownFramework() {
		serverManager.stopServer(actualPort);
	}
}
