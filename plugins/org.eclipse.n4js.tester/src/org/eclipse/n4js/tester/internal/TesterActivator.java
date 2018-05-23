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

import static com.google.inject.name.Names.named;
import static org.eclipse.n4js.tester.TesterModule.N4_TESTER_MODULE_ID;
import static org.eclipse.n4js.tester.TesterModuleDefaults.HTTP_SERVER_PORT_KEY;
import static org.eclipse.n4js.tester.server.HttpServerManager.HTTP_PORT;
import static java.util.Collections.singletonMap;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.google.inject.Key;

import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.server.HttpServerManager;

/**
 * Activator for the {@code tester.core} module. This activator is responsible for automatically starting the embedded
 * HTTP server instance.
 *
 * This class is indirectly being activated from {@code org.eclipse.n4js.tester.ui.TesterUiStartup} to ensure lazy
 * bundle policy.
 */
public class TesterActivator implements BundleActivator {

	private static BundleContext context;
	private static TesterActivator instance;

	/* default */static BundleContext getContext() {
		return context;
	}

	/** Reference to the started bundle-instance */
	public static TesterActivator getInstance() {
		return instance;
	}

	/** effective server port after starting up. */
	private int serverPort = -1;

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		TesterActivator.context = bundleContext;
		final HttpServerManager serverManager = getServerManager();
		final int port = getPort();
		final int usedPort = serverManager.startServer(singletonMap(HTTP_PORT, port));
		this.serverPort = usedPort;
		instance = this;
	}

	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		instance = null;
		TesterActivator.context = null;
		this.serverPort = -1;
		final HttpServerManager serverManager = getServerManager();
		// -1 to make sure all servers all stopped
		serverManager.stopServer(-1);
	}

	/** Effectively used port */
	public int getServerPort() {
		return serverPort;
	}

	private HttpServerManager getServerManager() {
		return getInjector().getInstance(HttpServerManager.class);
	}

	private int getPort() {
		return getInjector().getInstance(Key.get(Integer.class, named(HTTP_SERVER_PORT_KEY)));
	}

	private Injector getInjector() {
		return TesterModule.getInjector(N4_TESTER_MODULE_ID);
	}

}
