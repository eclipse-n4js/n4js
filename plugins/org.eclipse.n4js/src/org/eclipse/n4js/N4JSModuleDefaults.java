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
package org.eclipse.n4js;

/**
 * Defaults and constants for the N4 tester core module.
 */
public abstract class N4JSModuleDefaults {

	/**
	 * Key for the embedded HTTP server port.
	 */
	public static final String HTTP_SERVER_PORT_KEY = "httpServerPortKey";

	/**
	 * Port for the embedded HTTP server.
	 */
	/* default */static final int HTTP_SERVER_PORT_VALUE = 9415;

	private N4JSModuleDefaults() {
	}

}
