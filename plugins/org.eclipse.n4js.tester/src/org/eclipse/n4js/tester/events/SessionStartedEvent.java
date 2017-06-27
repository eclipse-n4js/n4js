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
package org.eclipse.n4js.tester.events;

import static com.google.common.collect.ImmutableMap.copyOf;
import static java.util.Collections.emptyMap;

import java.util.Map;

/**
 * Representation of a test session started event.
 */
public class SessionStartedEvent extends TestEvent {

	private final Map<String, String> properties;

	/**
	 * Creates a new session started even instance with the given session ID and with a {@code null} properties.
	 *
	 * @param sessionId
	 *            the unique identifier of the associated session.
	 *
	 */
	public SessionStartedEvent(final String sessionId) {
		this(sessionId, null);
	}

	/**
	 * Creates a new session started even instance with the given arguments.
	 *
	 * @param sessionId
	 *            the unique identifier of the associated session.
	 * @param properties
	 *            a map of properties for the session start event. Optional can be {@code null}.
	 *
	 */
	public SessionStartedEvent(final String sessionId, final Map<String, String> properties) {
		super(sessionId);
		this.properties = null == properties ? emptyMap() : copyOf(properties);
	}

	/**
	 * Returns with a view of properties for the session start event. Never {@code null}.
	 *
	 * @return a map of properties for the event.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

}
