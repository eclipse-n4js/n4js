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
package org.eclipse.n4js.tester.server.resources.sessions;

import static java.util.regex.Pattern.compile;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.server.resources.BaseResource;
import org.eclipse.n4js.tester.server.resources.ClientResourceException;

/**
 * Base of all resources used for interacting test sessions such as {@code start session}, {@code end session},
 * {@code ping session} and {@code test tree session}.
 */
/* default */abstract class SessionResource extends BaseResource {

	private static final Pattern SESSION_ID_PATTERN = compile("\\b([^ /]+)\\b");

	@Inject
	private TesterEventBus bus;

	@Override
	protected int doHandle(final String body, final String pathInfo) throws ServletException {
		final Matcher matcher = SESSION_ID_PATTERN.matcher(pathInfo);
		String sessionId = null;
		if (matcher.find()) {
			sessionId = matcher.group();
		}

		if (sessionExists(sessionId)) {
			try {
				bus.post(createEvent(sessionId, body));
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
	 * Creates and returns with a new test event instance from the session ID and the message body arguments
	 * representing the successful REST resource call. <br>
	 * This event will be posted on the {@link TesterEventBus event bus}.
	 *
	 * @param sessionId
	 *            the unique ID of the associated session.
	 * @param body
	 *            the message body of the HTTP request. Never {@code null}
	 * @return the event representing a successful resource call
	 * @throws ServletException
	 *             if the event cannot be created due to an unexpected exception.
	 */
	protected abstract TestEvent createEvent(final String sessionId, final String body) throws ServletException;

}
