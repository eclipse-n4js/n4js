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
package org.eclipse.n4js.generator;

/**
 * Code generator exception handler.
 */
public class GeneratorExceptionHandler {

	/**
	 * Prints provided message to the out stream, and the stack trace of the provided cause. Afterwards throws new
	 * {@link GeneratorException} wrapping provided cause.
	 */
	public void handleError(String message, Throwable cause) {
		System.out.println(message);
		cause.printStackTrace();
		throw new GeneratorException(message, cause);
	}
}
