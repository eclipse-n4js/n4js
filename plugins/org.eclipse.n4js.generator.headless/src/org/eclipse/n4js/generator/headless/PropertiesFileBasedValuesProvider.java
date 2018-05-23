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
package org.eclipse.n4js.generator.headless;

import java.util.Properties;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.preferences.PreferenceKey;

/**
 */
@SuppressWarnings("restriction")
public class PropertiesFileBasedValuesProvider implements IPreferenceValuesProvider {
	private IPreferenceValues values = null;

	/**
	 * Values are provided from loaded properties file
	 *
	 * @param properties
	 *            the settings for the project (usually found in the preference-store) can be <code>null</code>
	 */
	public PropertiesFileBasedValuesProvider(Properties properties) {
		final Properties props = new Properties(properties);
		values = new IPreferenceValues() {

			@Override
			public String getPreference(PreferenceKey key) {
				return props.getProperty(key.getId(), key.getDefaultValue());
			}
		};
	}

	@Override
	public IPreferenceValues getPreferenceValues(Resource context) {
		return values;
	}
}
