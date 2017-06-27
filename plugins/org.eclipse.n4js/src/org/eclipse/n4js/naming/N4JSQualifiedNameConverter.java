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
package org.eclipse.n4js.naming;

import org.eclipse.n4js.ts.naming.N4TSQualifiedNameConverter;

/**
 * Changes separator for string representations of qualified names to "/".
 */
public class N4JSQualifiedNameConverter extends N4TSQualifiedNameConverter {

	/**
	 * Delimiter used in string representations of fully-qualified names.
	 * <p>
	 * Copied from super class to avoid annoying warnings when accessing the constant via this class:
	 * "The static field N4TSQualifiedNameConverter.DELIMITER should be accessed directly".
	 */
	@SuppressWarnings("hiding")
	public static final String DELIMITER = N4TSQualifiedNameConverter.DELIMITER;
}
