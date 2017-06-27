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

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.n4js.tester.server.HttpConstants.SC_UNPROCESSABLE_ENTITY;
import static org.eclipse.n4js.tester.server.resources.ContentType.START_SESSION;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.POST;
import static java.lang.String.valueOf;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.server.resources.ClientResourceException;
import org.eclipse.n4js.tester.server.resources.Resource;

/**
 * Implementation of the {@code start session} RESTful resource.
 * <p>
 * <tt>HTTP PUT/n4js/testing/sessions/{sessionId}/start</tt>
 */
@Resource(path = "/{sessionId}/start/", method = POST, requestContentType = START_SESSION)
public class StartSessionResource extends SessionResource {

	private static final String PROPERTIES = "properties";

	@Inject
	private ObjectMapper mapper;

	@Override
	@SuppressWarnings("unchecked")
	protected TestEvent createEvent(final String sessionId, final String body) throws ClientResourceException {

		final Map<?, ?> values = newHashMap();
		try {
			if (!isNullOrEmpty(body)) {
				values.putAll(mapper.readValue(body, Map.class));
			}
		} catch (JsonMappingException | JsonParseException e) {
			throw new ClientResourceException(SC_UNPROCESSABLE_ENTITY);
		} catch (final IOException e) {
			throw new ClientResourceException(SC_BAD_REQUEST);
		}

		final Map<String, String> properties = newHashMap();
		if (null != values.get(PROPERTIES)) {
			if (!(values.get(PROPERTIES) instanceof Map)) {
				throw new ClientResourceException(SC_UNPROCESSABLE_ENTITY);
			} else {
				((Map<?, ?>) values.get(PROPERTIES)).entrySet().forEach(new Consumer<Entry<?, ?>>() {
					@Override
					public void accept(final Entry<?, ?> entry) {
						properties.put(valueOf(entry.getKey()), valueOf(entry.getValue()));
					}
				});
			}
		}

		return new SessionStartedEvent(sessionId, properties);
	}

	/**
	 * Always returns with {@code true}. Session will be created right now.
	 */
	@Override
	protected boolean sessionExists(final String sessionId) {
		return true;
	}

}
