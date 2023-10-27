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
package org.eclipse.n4js.xpect.config;

import org.eclipse.xpect.setup.XpectSetupComponent;

/**
 * Simple Preference to be read in xpect-setup section.
 *
 * <pre>
 *  Preference "key1" "value1" {}
 *  Preference "key2" "value2" {}
 *  ...
 * </pre>
 */
@XpectSetupComponent
public class Preference {

	private final String key;
	private final String value;

	/** Inits full pair. */
	public Preference(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/** Access key */
	public String getKey() {
		return key;
	}

	/** Access value */
	public String getValue() {
		return value;
	}
}
