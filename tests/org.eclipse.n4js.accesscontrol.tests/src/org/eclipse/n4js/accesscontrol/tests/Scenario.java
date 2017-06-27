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
 * Enumerates the different possible scenarios.
 */
enum Scenario {
	/**
	 * Extension scenario where the client extends the supplier.
	 */
	EXTENDS,
	/**
	 * Implementation scenario where the client implements the supplier, which must be an interface.
	 */
	IMPLEMENTS,
	/**
	 * Reference scenario where the client, which must be a concrete class, references the supplier.
	 */
	REFERENCES;

	/**
	 * Parses the given string representation of a scenario which is read from the input table.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the enum value that corresponds to the given string
	 */
	public static Scenario parse(String str) {
		switch (Objects.requireNonNull(str)) {
		case "Extends":
			return EXTENDS;
		case "Implements":
			return IMPLEMENTS;
		case "References":
			return REFERENCES;
		}
		throw new IllegalArgumentException("Unexpected scenario: '" + str + "'");
	}
}
