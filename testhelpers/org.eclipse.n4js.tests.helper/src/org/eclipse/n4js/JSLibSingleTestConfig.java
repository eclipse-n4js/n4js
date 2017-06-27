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
package org.eclipse.n4js;

import java.util.zip.ZipEntry;

import com.google.common.base.Strings;

/**
 * Configuration of a single JavaScript library (e.g. ECMAScript) test. All parameters of a test are found in this
 * class, this makes setup better maintainable since not an array of arbitrary objects is passed around, but only a
 * single configuration instance.
 */
public class JSLibSingleTestConfig {

	/**
	 * Modifier for tests on the blacklist. These tests are expected to fail.
	 */
	public final static String BLACKLIST = "blacklist";

	/**
	 * Modifier for tests on the validator list. These tests are expected to require the validator.
	 */
	public final static String VALIDATOR = "validator";

	/**
	 * Every generated test will use different ZipFile entry as test data
	 */
	public final ZipEntry entry;

	/**
	 * Name of resource containing corresponding ZipEntry
	 */
	public final String resourceName;

	/**
	 * Modifier is usually derived from the configuration or black list file name
	 */
	public final String modifier;

	/**
	 * Creates a new config for the entry from the resource, optionally containing a modifier (.e.g BLACKLIST or
	 * VALIDATOR).
	 */
	public JSLibSingleTestConfig(ZipEntry entry, String resourceName, String modifier) {
		this.entry = entry;
		this.resourceName = resourceName;
		this.modifier = modifier;

	}

	@Override
	public String toString() {
		return entry.getName();
	}

	/**
	 * Returns true if the test contains a modifier.
	 */
	public boolean isModified() {
		return !Strings.isNullOrEmpty(modifier);
	}

	/**
	 * Returns true if the test is on the blacklist, i.e. modifier is {@link #BLACKLIST}.
	 */
	public boolean isBlackList() {
		return BLACKLIST.equals(modifier);
	}

	/**
	 * Returns true if the test is on the validator list, i.e. modifier is {@link #VALIDATOR}.
	 */

	public boolean isValidator() {
		return VALIDATOR.equals(modifier);
	}

}
