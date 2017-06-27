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
 * The type of usage intended when accessing a member of the supplier.
 */
enum UsageType {
	/**
	 * Client intends to access (read / write, call) a member (field, getter, setter, method) of the supplier.
	 */
	ACCESS,
	/**
	 * Client intends to override a member of the supplier.
	 */
	OVERRIDE;

	/**
	 * Parses the given string representation of the usage type, which was read from the input table.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the enum value that corresponds to the given string
	 */
	public static UsageType parse(String str) {
		switch (Objects.requireNonNull(str)) {
		case "Access":
			return ACCESS;
		case "Override":
			return OVERRIDE;
		}
		throw new IllegalArgumentException("Unexpected usage type: '" + str + "'");
	}
}
