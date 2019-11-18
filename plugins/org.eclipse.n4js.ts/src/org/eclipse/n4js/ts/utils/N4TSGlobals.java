/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.utils;

import org.eclipse.n4js.ts.naming.N4TSQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Global constants to be used in the implementation of the TS language.
 */
public class N4TSGlobals {

	/**
	 * String used to separate segments in the string representation of a {@link QualifiedName qualified name}.
	 *
	 * @see N4TSQualifiedNameConverter#DELIMITER
	 */
	public static final String QUALIFIED_NAME_DELIMITER = "/";

	/**
	 * Character used to separate the namespace name from the exported element's name when referring to an element via a
	 * namespace import. For example:
	 *
	 * <pre>
	 * import * as NS from "some/other/module"
	 *
	 * let c: NS.OtherClass;
	 * </pre>
	 */
	public static final char NAMESPACE_ACCESS_DELIMITER = '.';
}
