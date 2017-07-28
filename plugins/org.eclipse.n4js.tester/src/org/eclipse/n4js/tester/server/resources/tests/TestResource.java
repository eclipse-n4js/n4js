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
package org.eclipse.n4js.tester.server.resources.tests;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.util.List;

import javax.servlet.ServletException;

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.server.resources.BaseResource;
import org.eclipse.n4js.tester.server.resources.ClientResourceException;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Base of all resources used for interacting tests such as {@code start test}, {@code end test}, and {@code ping test}.
 */
/* default */abstract class TestResource extends BaseResource {

	@Inject
	private TesterEventBus bus;

	@Override
	protected int doHandle(final String body, final String pathInfo) throws ServletException {

		final List<String> pathValues = newArrayList(on("/").omitEmptyStrings().split(pathInfo));
		if (4 != pathValues.size()) { // sessionID, tests, testId, ${operation}
			throw new ClientResourceException(SC_NOT_FOUND);
		}

		final String sessionId = pathValues.get(0);
		final String testId = pathValues.get(2);

		if (sessionExists(sessionId)) {
			try {
				bus.post(createEvent(sessionId, testId, body));
			} catch (final Exception e) {
				final Throwable rootCause = Throwables.getRootCause(e);
				if (rootCause instanceof ClientResourceException) {
					throw (ClientResourceException) rootCause;
				}
				LOGGER.error("Error while creating event for test session. [Session ID: " + sessionId + "]", e);
				throw new ServletException(e);
			}
			return SC_OK;
		}

		return SC_NOT_FOUND;

	}

	/**
	 * Creates and returns with a new test event instance from the session ID, the test ID and the message body
	 * arguments representing the successful REST resource call. <br>
	 * This event will be posted on the {@link TesterEventBus event bus}.
	 *
	 * @param sessionId
	 *            the unique ID of the associated session.
	 * @param testId
	 *            the unique ID of the associated test.
	 * @param body
	 *            the message body of the HTTP request. Never {@code null}
	 * @return the event representing a successful resource call
	 * @throws ServletException
	 *             if the event cannot be created due to an unexpected exception.
	 */
	protected abstract TestEvent createEvent(final String sessionId, final String testId, final String body)
			throws ServletException;

}
