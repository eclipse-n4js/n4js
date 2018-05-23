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

import javax.servlet.http.HttpServletRequest;

/**
 * Enumeration of HTTP/1.1 <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">method definitions</a>.
 */
public enum HttpMethod {

	/** The DELETE HTTP method. */
	DELETE,
	/** The HEAD HTTP method. */
	HEAD,
	/** The GET HTTP method. */
	GET,
	/** The OPTIONS HTTP method. */
	OPTIONS,
	/** The POST HTTP method. */
	POST,
	/** The PUT HTTP method. */
	PUT,
	/** The TRACE HTTP method. */
	TRACE,
	/** The PATCH HTPP method. */
	PATCH;

	/**
	 * Returns with {@code true} if the {@link HttpServletRequest#getMethod() method} of the HTTP request equals with
	 * the corresponding HTTP method argument. Otherwise returns with {@code false}.
	 *
	 * @param method
	 *            the HTTP method.
	 * @param req
	 *            the HTTP request which method should be compared.
	 * @return {@code true} if the method equals, otherwise {@code false}.
	 */
	public static boolean equalsWithMethod(final HttpMethod method, final HttpServletRequest req) {
		return null != method && null != req && method.toString().equals(req.getMethod());
	}

}
