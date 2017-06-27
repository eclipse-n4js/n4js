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
package org.eclipse.n4js.tester.tests

import java.util.Map
import com.google.inject.Inject
import org.eclipse.n4js.tester.server.HttpServerManager
import org.eclipse.n4js.tester.server.JettyManager
import com.google.common.collect.ImmutableMap

import static org.eclipse.n4js.tester.server.HttpServerManager.HTTP_PORT;
import static org.eclipse.n4js.tester.tests.TesterConstants.*

/**
 * Tester test configuration to be used with injections & complemented by TesterConstants
 *
 */
class TesterTestsConfiguration {

	@Inject
	private HttpServerManager serverManager;

	// the actual port for the testServer, will be computed in #before()
	private int port;
	private Map<String,Object> valid_config;

	/** computes available port and stores the value into #port, also create a minimal valid map for tests -stored in valid_config */
	public def Map<String,Object> computePortAndValidConfig() {
		port = (serverManager as JettyManager).ensurePortIsAvailable(DEFAULT_PORT);
		/** A valid configuration for the server. */
		valid_config = ImmutableMap.<String, Object> builder()
			.put(HTTP_PORT, port)
		 	.build();
		return valid_config;
	}

	def int getPORT() { return port; }

	def Map<String, Object> getVALID_CONFIG() { return valid_config; }

	def String getURL() {
		HOST_AND_PORT + CONTEXT_ROOT + CONTEXT_PATH;
	}

	def String HOST_AND_PORT() {
		"http://" + HOST + ":" + PORT
	}
}
