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
package org.eclipse.n4js.tester.server.resources;

import static com.google.common.base.Strings.nullToEmpty;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.log4j.Logger.getLogger;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;

import com.google.inject.Inject;

/**
 * Base class of the HTTP resources.
 */
public abstract class BaseResource {

	/** Shared logger instance. */
	protected static final Logger LOGGER = getLogger(BaseResource.class);

	@Inject
	private TestFsmRegistry fsmRegistry;

	/**
	 * Handles the HTTP method. In case of supported request media type it reads the request body and delegates to
	 * {@link #doHandle(String, String)} method. sets the response status code and the response body.
	 *
	 * @param req
	 *            the HTTP servlet request.
	 * @param resp
	 *            the HTTP servlet response.
	 * @param pathInfo
	 *            the path info of the requested resource
	 * @throws IOException
	 *             if processing the HTTP request fails.
	 * @throws ServletException
	 *             if processing the HTTP request fails.
	 */
	public void doHandle(final HttpServletRequest req, final HttpServletResponse resp, String pathInfo)
			throws IOException, ServletException {
		final String body = getRequestBody(req);
		resp.setStatus(doHandle(body, pathInfo));
	}

	/**
	 * Returns with the request body as a string. Since this method is responsible to close the request reader this
	 * method can be invoked only once from the client.
	 *
	 * @param req
	 *            the servlet request.
	 * @return the request body as a string.
	 * @throws IOException
	 *             if reading the request body failed.
	 */
	private String getRequestBody(final ServletRequest req) throws IOException {
		final StringBuilder sb = new StringBuilder();
		try (final BufferedReader br = req.getReader();) {
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
		}
		return sb.toString();
	}

	/**
	 * Processes the HTTP request body and the path info and returns with the HTTP response code. <br>
	 * By default returns with {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR HTTP 500}.
	 *
	 * @param body
	 *            the unparsed request body.
	 * @param pathInfo
	 *            the path info for the request.
	 * @return the HTTP response code.
	 * @throws ServletException
	 *             if unexpected error happened while handling the request.
	 */
	protected int doHandle(final String body, final String pathInfo) throws ServletException {
		return SC_INTERNAL_SERVER_ERROR;
	}

	/**
	 * Returns with {@code true} if a test session exists with unique session ID argument, otherwise returns with
	 * {@code false}.
	 *
	 * @param sessionId
	 *            the ID of the session to check its existence. Can be {@code null}.
	 * @return {@code true} if the session exists. Otherwise {@code false}.
	 */
	protected boolean sessionExists(final String sessionId) {
		return fsmRegistry.isSessionExist(nullToEmpty(sessionId));
	}

	/**
	 * This method is called when the HTTP request was successfully processed and before sending back the HTTP response
	 * to the client. In practice this is the last point where clients can hook any custom logic before the response
	 * leaves the servlet.
	 * <p>
	 * This method is called only and if only neither client nor server side errors occurred during the request
	 * processing (and the the response writing).
	 * <p>
	 * Does nothing by default. Clients may override this method and can add any custom logic.
	 *
	 * @param req
	 *            the HTTP request.
	 * @param resp
	 *            the HTTP servlet.
	 * @param escapedPathInfo
	 *            the escaped path info of the requested resource
	 * @throws ServletException
	 *             if the request-response post processing fails.
	 */
	protected void handleStatusOk(final HttpServletRequest req, final HttpServletResponse resp, String escapedPathInfo)
			throws ServletException {
		// Does nothing by default.
	}

}
