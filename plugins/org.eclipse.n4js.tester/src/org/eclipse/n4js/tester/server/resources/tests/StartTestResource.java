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

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.ImmutableMap.builder;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;
import static java.text.MessageFormat.format;
import static java.util.Collections.singletonMap;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.eclipse.n4js.tester.server.HttpConstants.SC_UNPROCESSABLE_ENTITY;
import static org.eclipse.n4js.tester.server.resources.ContentType.START_TEST;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.POST;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;
import org.eclipse.n4js.tester.server.resources.ClientResourceException;
import org.eclipse.n4js.tester.server.resources.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Implementation of the {@code start test} RESTful resource.
 * <p>
 * <tt>HTTP PUT/n4js/testing/sessions/{sessionId}/tests/{testId}/start/</tt>
 */
@Resource(path = "/{sessionId}/tests/{testId}/start/", method = POST, requestContentType = START_TEST, responseContentType = "application/vnd.n4js.start_test_res.tm+json")
public class StartTestResource extends TestResource {

	private static final String TIMEOUT_KEY = "timeout";
	private static final String PROPERTIES = "properties";

	@Inject
	private ObjectMapper mapper;

	@Override
	@SuppressWarnings("unchecked")
	protected TestEvent createEvent(final String sessionId, final String testId, final String body)
			throws ClientResourceException {

		if (isNullOrEmpty(body))
			throw new ClientResourceException(SC_BAD_REQUEST);

		final Map<?, ?> values = newHashMap();
		try {
			values.putAll(mapper.readValue(body, Map.class));
		} catch (JsonMappingException | JsonParseException e) {
			throw new ClientResourceException(SC_UNPROCESSABLE_ENTITY);
		} catch (final IOException e) {
			throw new ClientResourceException(SC_BAD_REQUEST);
		}

		final Object value = values.get(TIMEOUT_KEY);
		// incorrect schema
		if (null == value) {
			throw new ClientResourceException(SC_UNPROCESSABLE_ENTITY);
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

		try {
			final long timeout = parseLong(valueOf(value));
			return new TestStartedEvent(sessionId, testId, timeout, properties);
		} catch (final NumberFormatException e) {
			// although schema was valid the data was indeed invalid
			throw new ClientResourceException(SC_BAD_REQUEST);
		}
	}

	@Override
	protected void handleStatusOk(final HttpServletRequest req, final HttpServletResponse resp, String escapedPathInfo)
			throws ClientResourceException {

		TestResourceParameters parameters = getParametersFromPathInfo(escapedPathInfo);

		if (null != parameters && !isNullOrEmpty(parameters.sessionId) && !isNullOrEmpty(parameters.testId)) {
			try {
				final String body = mapper
						.writeValueAsString(new StartTestResponse(parameters.sessionId, parameters.testId).data);
				try (final OutputStream os = resp.getOutputStream();
						final OutputStreamWriter osw = new OutputStreamWriter(os)) {
					osw.write(body);
					osw.flush();
				}
			} catch (final IOException e) {
				throw new ClientResourceException(SC_BAD_REQUEST);
			}
		}
	}

	/**
	 * Helper class to simplify the response creation for the {@code test start} resource.
	 */
	private static final class StartTestResponse {
		private static final String PING_TEMP = "/n4js/testing/sessions/{0}/tests/{1}/ping";
		private static final String END_TEMP = "/n4js/testing/sessions/{0}/tests/{1}/end";
		private final Object data;

		private StartTestResponse(final String sessionId, final String testId) {
			data = singletonMap("links", newArrayList(
					builder()
							.put("rel", "ping test")
							.put("uri", format(PING_TEMP, sessionId, testId))
							.build(),
					builder()
							.put("rel", "end test")
							.put("uri", format(END_TEMP, sessionId, testId))
							.build()));
		}
	}

}
