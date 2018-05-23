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
 * The type of classifier to be created for the supplier or client.
 */
enum ClassifierType {
	/**
	 * Concrete class that can be instantiated.
	 */
	CLASS,
	/**
	 * Abstract class where all generated members except for fields are abstract.
	 */
	ABSTRACT_CLASS,
	/**
	 * Interface with abstract members.
	 */
	INTERFACE,
	/**
	 * Interface where all generated members except for fields have default implementations.
	 */
	DEFAULT_INTERFACE;

	/**
	 * Returns the correct enum value for a given string representation which is read from the input table.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the enum value that corresponds to the given string
	 */
	public static ClassifierType parse(String str) {
		switch (Objects.requireNonNull(str)) {
		case "Class":
			return CLASS;
		case "Abstract Class":
			return ABSTRACT_CLASS;
		case "Interface":
			return INTERFACE;
		case "Default Interface":
			return DEFAULT_INTERFACE;
		}
		throw new IllegalArgumentException("Unexpected classifier type: '" + str + "'");
	}
}
