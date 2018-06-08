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

import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

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

	/* default */static BundleContext getContext() {
		return context;
	}

	/** Reference to the started bundle-instance */
	public static TesterActivator getInstance() {
		return instance;
	}

	/** effective server port after starting up. */
	private int effectiveServerPort = -1;

	@Named(HTTP_SERVER_PORT_KEY)
	@Inject
	private Integer configuredServerPort;

	@Inject
	private HttpServerManager serverManager;

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		TesterActivator.context = bundleContext;
		instance = this;
		injector = Guice.createInjector(new TesterModule());
		injector.injectMembers(this);
		this.effectiveServerPort = serverManager.startServer(singletonMap(HTTP_PORT, configuredServerPort));
	}

	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		instance = null;
		TesterActivator.context = null;
		this.effectiveServerPort = -1;
		// -1 to make sure all servers all stopped
		serverManager.stopServer(-1);
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
