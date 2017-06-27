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
package org.eclipse.n4js.tester.server;

/**
 */
public abstract class HttpConstants {

	/**
	 * The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
	 * (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
	 * correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
	 * instructions. For example, this error condition may occur if an XML request body contains well-formed (i.e.,
	 * syntactically correct), but semantically erroneous, XML instructions.
	 * <p>
	 * <a href="https://tools.ietf.org/html/rfc4918#section-11.2">422 Unprocessable Entity</a>
	 */
	public static int SC_UNPROCESSABLE_ENTITY = 422;

	private HttpConstants() {
	}

}
