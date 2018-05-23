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

import static org.eclipse.n4js.tester.server.resources.ContentType.END_SESSION;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.POST;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.TestEvent;
import org.eclipse.n4js.tester.server.resources.Resource;

/**
 * Implementation of the {@code end session} RESTful resource.
 * <p>
 * <tt>HTTP POST/n4js/testing/sessions/{sessionId}/end</tt>
 */
@Resource(path = "/{sessionId}/end/", method = POST, requestContentType = END_SESSION)
public class EndSessionResource extends SessionResource {

	@Override
	protected TestEvent createEvent(final String sessionId, final String body) {
		return new SessionEndedEvent(sessionId);
	}

}
