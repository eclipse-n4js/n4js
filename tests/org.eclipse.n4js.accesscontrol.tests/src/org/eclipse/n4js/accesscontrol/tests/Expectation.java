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
 * The different possible expectations for a test scenario.
 */
enum Expectation {
	/**
	 * Expect no errors or warnings ('y' in the matrix table).
	 */
	OK,
	/**
	 * Expect one error in the client module ('n' in the matrix table).
	 */
	FAIL,
	/**
	 * Expect one error in the client module at the client and one additional error in the client module ('u' in the
	 * matrix table).
	 */
	UNUSABLE,
	/**
	 * Should have no errors, but due to bugs we currently do get them ('y?' in the matrix table). Will fail once the
	 * bugs are fixed.
	 */
	FIXME_OK,
	/**
	 * Should have errors, but due to bugs we currently get none ('n?' in the matrix table). Will fail once the bugs are
	 * fixed.
	 */
	FIXME_FAIL,
	/**
	 * Should have errors, but due to bugs we currently get none ('u?' in the matrix table). Will fail once the bugs are
	 * fixed.
	 */
	FIXME_UNUSABLE,
	/**
	 * Skip this test scenario ('#' in the matrix table).
	 */
	SKIP;

	/**
	 * Parses a string representation of an expectation that was read from the input table.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the enum value that corresponds to the given string
	 */
	public static Expectation parse(String str) {
		switch (Objects.requireNonNull(str)) {
		case "y":
			return OK;
		case "n":
			return FAIL;
		case "u":
			return UNUSABLE;
		case "y?":
			return FIXME_OK;
		case "n?":
			return FIXME_FAIL;
		case "u?":
			return FIXME_UNUSABLE;
		case "":
		case "#":
			return SKIP;
		}
		throw new IllegalArgumentException("Unexpected expectation: '" + str + "'");
	}

	/**
	 * Indicates whether this enum value is a fixme.
	 *
	 * @return <code>true</code> if this enum value is a fixme and <code>false</code> otherwise
	 */
	public boolean isFixMe() {
		switch (this) {
		case OK:
		case FAIL:
		case UNUSABLE:
		case SKIP:
			return false;
		case FIXME_OK:
		case FIXME_FAIL:
		case FIXME_UNUSABLE:
			return true;
		}

		throw new IllegalArgumentException("Unknown expectation: '" + this + "'");
	}
}
