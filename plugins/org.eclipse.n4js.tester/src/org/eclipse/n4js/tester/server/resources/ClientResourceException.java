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

import javax.servlet.ServletException;

/**
 * Exception handling any expected client side errors in the servlet level.
 */
public class ClientResourceException extends ServletException {

	private final int statusCode;

	/**
	 * Creates a new exposition instance with the HTTP status code argument.
	 *
	 * @param statusCode
	 *            the status code.
	 */
	public ClientResourceException(final int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Returns with the status code describing the error.
	 *
	 * @return the status code.
	 */
	public int getStatusCode() {
		return statusCode;
	}

}
