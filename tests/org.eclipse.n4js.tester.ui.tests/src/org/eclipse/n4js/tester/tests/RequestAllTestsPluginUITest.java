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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.n4js.tester.internal.TesterActivator;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.server.resources.ContentType;
import org.eclipse.n4js.tester.server.resources.ResourceRouterServlet;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;

/**
 * This test triggers the tester servlet implementation and with it lazy injection. Hence, this test assures that our
 * fragile injector setup works in the UI case regarding the tester.
 */
public class RequestAllTestsPluginUITest extends AbstractBuilderParticipantTest {

	static final String HOST = "localhost";
	static final int PORT = 9415;
	static final String URL = "http://" + HOST + ":" + PORT + HttpServerManager.CONTEXT_ROOT
			+ ResourceRouterServlet.CONTEXT_PATH;

	private HttpServerManager serverManager;

	/** Start the Jetty Server if not started yet. */
	@Before
	public void startServer() {
		Map<String, Object> serverConfig = new HashMap<>();
		serverConfig.put(HttpServerManager.HTTP_PORT, PORT);
		serverManager = TesterActivator.getInjector().getInstance(HttpServerManager.class);
		if (serverManager != null && !serverManager.isRunning(PORT)) {
			serverManager.startServer(serverConfig);
			// assertTrue("Server is not running on port " + PORT, serverManager.isRunning(PORT));
		}
	}

	/** Stop the server. */
	@Before
	public void stopServer() {
		if (serverManager != null && serverManager.isRunning(PORT)) {
			serverManager.stopServer(PORT);
		}
	}

	/**
	 * This test triggers the tester servlet implementation and with it lazy injection.
	 */
	@Test
	public void requestAllTestsFromIDE() throws Exception {
		IResourcesSetupUtil.waitForBuild();

		ValidatableResponse then = RestAssured.given().contentType(ContentType.ASSEMBLE_TEST_CATALOG.toString())
				.when().get(URL + "testcatalog")
				.then();
		expectStatusCode(then, HttpServletResponse.SC_OK);
	}

	ValidatableResponse expectStatusCode(ValidatableResponse resp, int statusCode) {
		return resp.statusCode(new BaseMatcher<Integer>() {
			@Override
			public boolean matches(Object item) {
				if (item instanceof Integer) {
					return statusCode == ((Integer) item).intValue();
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Expected status code " + statusCode + ".");
			}
		});
	}
}
