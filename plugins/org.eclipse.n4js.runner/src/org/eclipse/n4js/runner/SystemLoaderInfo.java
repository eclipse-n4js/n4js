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
package org.eclipse.n4js.runner;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Marker class to distinguish different loading mechanisms.
 */
public enum SystemLoaderInfo {

	/** System JS, default mechanism */
	SYSTEM_JS("systemjs", "System.js"),

	/** Common JS loader. */
	COMMON_JS("cjs", "Common JS");

	private final String id;
	private final String label;

	private SystemLoaderInfo(final String id, final String label) {
		this.id = checkNotNull(id, "id");
		this.label = checkNotNull(label, "label");
	}

	/**
	 * @returns the SysmtLoaderinfo or {@code null} if the passed in string is unmatched.
	 */
	public static SystemLoaderInfo fromString(final String loader) {
		if (loader == null) {
			return null;
		}
		final String trimmed = loader.trim().toLowerCase();
		switch (trimmed) {
		case "cjs":
		case "commonjs":
			return COMMON_JS;

		case "systemjs":
			return SYSTEM_JS;
		}
		return null;
	}

	/**
	 * Returns with the human readable system loader type.
	 *
	 * @return the label of the system loader.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns with the application specific unique identifier of the system loader.
	 *
	 * @return the ID of the system loader.
	 */
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return label;
	}

}
