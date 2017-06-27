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
package org.eclipse.n4js.accesscontrol.tests;

import java.util.Objects;

/**
 * The location of the client attempting to access a member of the supplier.
 */
enum ClientLocation {
	/**
	 * Client and supplier are the same type.
	 */
	SAME_TYPE,
	/**
	 * Client and supplier are in the same module, but not in the same type.
	 */
	SAME_MODULE,
	/**
	 * Client and supplier are in the same project, but not in the same module.
	 */
	SAME_PROJECT,
	/**
	 * Client and supplier have the same vendor, but are in different projects.
	 */
	SAME_VENDOR,
	/**
	 * Client and supplier have different vendors and are thus in different projects.
	 */
	OTHER;

	/**
	 * Parses the given string representation of a client location, which is read from the input table.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the enum value that corresponds to the given string.
	 */
	public static ClientLocation parse(String str) {
		switch (Objects.requireNonNull(str)) {
		case "Same Type":
			return SAME_TYPE;
		case "Same Module":
			return SAME_MODULE;
		case "Same Project":
			return SAME_PROJECT;
		case "Same Vendor":
			return SAME_VENDOR;
		case "Other":
			return OTHER;
		}
		throw new IllegalArgumentException("Unexpected client location: '" + str + "'");
	}
}
