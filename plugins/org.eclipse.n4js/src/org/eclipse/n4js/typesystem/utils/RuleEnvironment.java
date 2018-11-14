/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// FIXME clean up chained environments vs. copied upon wrap
@SuppressWarnings("javadoc")
public class RuleEnvironment {

	private final Map<Object, Object> entries = new LinkedHashMap<>();

	public RuleEnvironment() {
	}

	public RuleEnvironment(RuleEnvironment env) {
		if (env != null) {
			entries.putAll(env.entries);
		}
	}

	public boolean put(Object key, Object value) {
		return put(key, value, false);
	}

	public boolean put(Object key, Object value, boolean checkExist) {
		if (checkExist && entries.containsKey(key)) {
			return false;
		}

		entries.put(key, value);

		return true;
	}

	public Object get(Object key) {
		Object value = entries.get(key);
		// if (value == null && next != null) {
		// return next.get(key);
		// }
		return value;
	}

	public Map<Object, Object> getEnvironment() {
		return entries;
	}

	public Set<Entry<Object, Object>> entrySet() {
		return entries.entrySet();
	}

	public RuleEnvironment getNext() {
		return null;
	}
}
