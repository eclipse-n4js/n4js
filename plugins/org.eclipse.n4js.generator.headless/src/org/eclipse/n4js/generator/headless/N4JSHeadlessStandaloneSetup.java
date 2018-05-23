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

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.eclipse.n4js.N4JSStandaloneSetup;

/**
 */
public class N4JSHeadlessStandaloneSetup extends N4JSStandaloneSetup {
	private final Properties propertiesFile;

	/**
	 * @param propertiesFile
	 *            the properties file to decide whether an additional values provider should be registered.
	 */
	public N4JSHeadlessStandaloneSetup(Properties propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(new org.eclipse.n4js.N4JSRuntimeModule(),
				new N4JSHeadlessGeneratorModule(propertiesFile));
	}
}
