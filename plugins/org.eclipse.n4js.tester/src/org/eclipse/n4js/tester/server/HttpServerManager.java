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
package org.eclipse.n4js.tester.server;

import java.util.Map;

/**
 * Representation of an HTTP server manager.
 */
public interface HttpServerManager {

	/** The context root for the RESTful services. */
	String CONTEXT_ROOT = "/n4js";

	/**
	 * The port for the embedded HTTP server listening on. Type: {@code Integer}.
	 */
	String HTTP_PORT = "http.port";

	/**
	 * Constant accessing the local-host server name
	 */
	String LOCALHOST = "localhost";

	/**
	 * Starts the server with the given configuration.
	 *
	 * @param config
	 *            the configuration for the server setup. Such as host, port.
	 * @return actual port number of the started server
	 */
	int startServer(final Map<String, Object> config);

	/**
	 * Stops the HTTP server specified with the port argument. If the port is {@code -1}, then all the running HTTP
	 * servers will be stopped by the current manager.
	 *
	 * @param port
	 *            the port for the server.
	 */
	void stopServer(final int port);

	/**
	 * Returns with {@code true} if an HTTP server instance is up, running and listening on the given port. Otherwise
	 * returns with {@code true}.
	 *
	 * @param port
	 *            the port to check whether an HTTP server instance is available or not.
	 * @return {@code true} if a server is running and available on the given port, otherwise {@code false}.
	 */
	boolean isRunning(final int port);

}
