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
import static org.eclipse.n4js.tester.server.HttpConstants.SC_UNPROCESSABLE_ENTITY;
import static org.eclipse.n4js.tester.server.resources.ContentType.END_TEST;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.POST;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.server.resources.ClientResourceException;
import org.eclipse.n4js.tester.server.resources.Resource;

/**
 * Implementation of the {@code end test} RESTful resource.
 * <p>
 * <tt>HTTP PUT/n4js/testing/sessions/{sessionId}/tests/{testId}/end/</tt>
 */
@Resource(path = "/{sessionId}/tests/{testId}/end/", method = POST, requestContentType = END_TEST)
public class EndTestResource extends TestResource {

	@Inject
	private ObjectMapper mapper;

	@Override
	protected TestEvent createEvent(final String sessionId, final String testId, final String body)
			throws ClientResourceException {

		if (isNullOrEmpty(body))
			throw new ClientResourceException(SC_BAD_REQUEST);

		final AtomicReference<TestResult> result = new AtomicReference<>();
		try {
			result.set(mapper.readValue(body, TestResult.class));
		} catch (JsonMappingException | JsonParseException e) {
			throw new ClientResourceException(SC_UNPROCESSABLE_ENTITY);
		} catch (final IOException e) {
			throw new ClientResourceException(SC_BAD_REQUEST);
		}
		return new TestEndedEvent(sessionId, testId, result.get());
	}

}
