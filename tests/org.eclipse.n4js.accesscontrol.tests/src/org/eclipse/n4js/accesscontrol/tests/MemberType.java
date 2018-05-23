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

/**
 * The type of the member being accessed by the client.
 */
enum MemberType {
	FIELD, GETTER, SETTER, METHOD;

	/**
	 * Parse the given string representation of the member type.
	 *
	 * @param str
	 *            the string to parse
	 * @return the enum value that corresponds to the given string
	 */
	public static MemberType parse(String str) {
		switch (str) {
		case "Field":
			return FIELD;
		case "Getter":
			return GETTER;
		case "Setter":
			return SETTER;
		case "Method":
			return METHOD;
		}
		throw new IllegalArgumentException("Unexpected member type: '" + str + "'");
	}
}
