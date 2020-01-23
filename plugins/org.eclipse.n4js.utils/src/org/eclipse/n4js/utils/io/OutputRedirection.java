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
package org.eclipse.n4js.utils.io;

/**
 * Used to control redirection of the captured process outputs.
 */
public enum OutputRedirection {
	/** Forward all captured output the the configured stream. */
	REDIRECT,
	/** Do forward to the output stream. */
	SUPPRESS
}
