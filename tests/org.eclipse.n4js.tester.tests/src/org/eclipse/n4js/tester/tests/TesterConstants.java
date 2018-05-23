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
package org.eclipse.n4js.tester.tests;

import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.server.resources.ResourceRouterServlet;

/**
 * Contains constants for the tests.
 */
public abstract class TesterConstants {

	/** The host for the server. */
	public static final String HOST = "localhost";
	/** The default port for the server. */
	public static final int DEFAULT_PORT = 9415;
	/** Context root for the RESTful web service. */
	public static final String CONTEXT_ROOT = HttpServerManager.CONTEXT_ROOT;
	/** Context path for the resource router servlet. */
	public static final String CONTEXT_PATH = ResourceRouterServlet.CONTEXT_PATH;

	private TesterConstants() {
	}

}
