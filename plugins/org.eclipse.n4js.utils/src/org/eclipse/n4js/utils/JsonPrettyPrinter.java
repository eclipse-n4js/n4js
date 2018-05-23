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
package org.eclipse.n4js.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

/**
 * Custom {@code Jackson} {@link DefaultPrettyPrinter pretty printer} implementation that puts each element of an array
 * value into separate line. Basically this prettifies the JSON output.
 *
 * Instead of producing such default output:
 *
 * <pre>
 * {
 *   "foo" : "bar",
 *   "bar" : [1, 2, 3]
 * }
 * </pre>
 *
 * it creates this format:
 *
 * <pre>
 * {
 *   "foo" : "bar",
 *   "bar" : [
 *      1,
 *      2,
 *      3
 *    ]
 * }
 * </pre>
 */
/* default */ class JsonPrettyPrinter extends DefaultPrettyPrinter {

	/**
	 * Sole constructor of the pretty printer.
	 */
	/* default */ JsonPrettyPrinter() {
		this._arrayIndenter = new DefaultIndenter(" ", "\n");
	}

}
