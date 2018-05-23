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

/**
 * Enumeration of allowed HTTP request content types.
 */
public enum ContentType {

	/** Content type for the {@code start session} RESTful resource. */
	START_SESSION("application/vnd.n4js.start_session_req.tm+json"),
	/** Content type for the {@code end session} RESTful resource. */
	END_SESSION("application/vnd.n4js.end_session_req.tm+json"),
	/** Content type for the {@code ping session} RESTful resource. */
	PING_SESSION("application/vnd.n4js.ping_session_req.tm+json"),
	/** Content type for the {@code start test} RESTful resource. */
	START_TEST("application/vnd.n4js.start_test_req.tm+json"),
	/** Content type for the {@code end test} RESTful resource. */
	END_TEST("application/vnd.n4js.end_test_req.tm+json"),
	/** Content type for the {@code ping test} RESTful resource. */
	PING_TEST("application/vnd.n4js.ping_test_req.tm+json"),
	/** Content type for assembling the test catalog RESTful resource. */
	ASSEMBLE_TEST_CATALOG("application/vnd.n4js.assemble_test_catalog_req.tm+json");

	private final String literal;

	private ContentType(final String literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal;
	}

}
