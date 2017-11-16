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
package org.eclipse.n4js.generator.common

import org.eclipse.n4js.utils.Log

/**
 */
@Log
class ExceptionHandler {

	def handleError(String message, Throwable cause) {
		println(message)
		cause.printStackTrace
		throw new GeneratorException( message, cause);
	}
}
