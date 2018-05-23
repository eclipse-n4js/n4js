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

import org.eclipse.xtext.preferences.IPreferenceValuesProvider;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 */
@SuppressWarnings("restriction")
public class N4JSHeadlessGeneratorModule implements Module {
	private final Properties propertiesFile;

	/**
	 * @param propertiesFile
	 *            the properties file to decide whether an additional values provider should be registered.
	 */
	public N4JSHeadlessGeneratorModule(Properties propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	@Override
	public void configure(Binder binder) {
		if (propertiesFile != null) {
			binder.bind(IPreferenceValuesProvider.class).toInstance(
					new PropertiesFileBasedValuesProvider(propertiesFile));
		}

	}

}
