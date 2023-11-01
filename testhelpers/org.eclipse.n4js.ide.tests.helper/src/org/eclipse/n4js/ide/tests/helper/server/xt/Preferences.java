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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.xtext.preferences.ITypedPreferenceValues;
import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.preferences.TypedPreferenceKey;

/**
 * Preferences Container for Xpect-Setup sections.
 */
public class Preferences implements ITypedPreferenceValues {

	HashMap<String, Preference> prefs;

	public Preferences() {
		prefs = new HashMap<>();
	}

	/** adds a new Preference entry - filled when initialized */
	public void add(Preference s) {
		prefs.put(s.getKey(), s);
	}

	/** Set value-pairs. */
	public void setMap(HashMap<String, Preference> map) {
		prefs = new HashMap<>(map);
	}

	/** return the list of keys */
	public Set<String> keys() {
		return prefs.keySet();
	}

	/** return a value to the key. */
	public String getValue(String key) {
		Preference pref = prefs.get(key);
		if (pref == null)
			return null;
		return pref.getValue();
	}

	/** Puts key-value pair */
	public void put(PreferenceKey key, Object value) {
		put(key.getId(), value.toString());
	}

	/** Puts key-value pair */
	public void put(String key, String value) {
		prefs.put(key, new Preference(key, value));
	}

	/** Puts key-value pair */
	public <T> void put(TypedPreferenceKey<T> key, T value) {
		put(key.getId(), key.toString(value));
	}

	@Override
	public String getPreference(PreferenceKey key) {
		return getValue(key.getId());
	}

	@Override
	public <T> T getPreference(TypedPreferenceKey<T> key) {
		String valueString = getValue(key.getId());
		return key.toValue(valueString != null ? valueString : key.getDefaultValue());
	}

}
