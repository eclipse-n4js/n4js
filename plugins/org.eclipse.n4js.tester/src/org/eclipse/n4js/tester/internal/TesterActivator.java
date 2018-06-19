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

import java.util.Map;

import org.eclipse.n4js.tester.server.HttpServerManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

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

	private Injector injector;

	/** effective server port after starting up. */
	private int effectiveServerPort = -1;

	private Integer configuredServerPort;

	private HttpServerManager serverManager;

	static BundleContext getContext() {
		return context;
	}

	/** Reference to the started bundle-instance */
	public static TesterActivator getInstance() {
		return instance;
	}

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		TesterActivator.context = bundleContext;
		instance = this;
	}

	/** Starts the Jetty server */
	public void startupWithInjector(Injector uiInjector) {
		this.injector = uiInjector;
		this.serverManager = injector.getInstance(HttpServerManager.class);
		this.configuredServerPort = injector.getInstance(Key.get(Integer.class, Names.named(HTTP_SERVER_PORT_KEY)));
		Map<String, Object> portMap = singletonMap(HttpServerManager.HTTP_PORT, configuredServerPort);
		this.effectiveServerPort = serverManager.startServer(portMap);
	}

	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		instance = null;
		TesterActivator.context = null;
		this.effectiveServerPort = -1;
		// -1 to make sure all servers all stopped
		if (serverManager != null) {
			serverManager.stopServer(-1);
		}
	}

	/** Effectively used port */
	public int getServerPort() {
		return effectiveServerPort;
	}

	/**
	 * @return the ui-independent tester injector
	 */
	public static Injector getInjector() {
		return getInstance().injector;
	}

}
