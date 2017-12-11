/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl;

/**
 * Contains constants for the N4IDL language.
 */
public class N4IDLGlobals {

	/**
	 * Files extension of N4IDL source files (<b>not</b> including the separator dot).
	 */
	public static final String N4IDL_FILE_EXTENSION = "n4idl";

	/**
	 * Files extension of IDL source files (<b>not</b> including the separator dot).
	 */
	public static final String IDL_FILE_EXTENSION = "idl";

	/**
	 * The version separator is used to separate a type name from the declared/requested version. E.g. A#1
	 */
	public static final String VERSION_SEPARATOR = "#";

	/**
	 * The version separator that is used in compiled code to separate a type name from the declared/requested version.
	 * E.g. A#1
	 */
	public static final String COMPILED_VERSION_SEPARATOR = "$";
}
