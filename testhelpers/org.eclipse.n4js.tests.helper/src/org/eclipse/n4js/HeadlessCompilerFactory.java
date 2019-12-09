/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import java.util.Properties;

import org.eclipse.n4js.generator.headless.N4HeadlessCompiler;
import org.eclipse.n4js.generator.headless.N4JSHeadlessStandaloneSetup;
import org.eclipse.n4js.ide.server.HeadlessExtensionRegistrationHelper;

import com.google.inject.Injector;

/**
 * Factory that creates fully injected instance of the {@link N4HeadlessCompiler}. Convenience for some test setups.
 */
public class HeadlessCompilerFactory {

	/**
	 * Construct a {@link N4HeadlessCompiler} with default bindings and no custom properties.
	 *
	 */
	public static N4HeadlessCompiler createCompilerWithDefaults() {
		return createCompilerWithProperties(null);
	}

	/**
	 * Construct a {@link N4HeadlessCompiler} with configuration based on the provided properties.
	 *
	 * @param properties
	 *            preferences.
	 */
	public static N4HeadlessCompiler createCompilerWithProperties(Properties properties) {
		Injector localInjector = new N4JSHeadlessStandaloneSetup(properties).createInjectorAndDoEMFRegistration();
		N4HeadlessCompiler instance = localInjector.getInstance(N4HeadlessCompiler.class);
		HeadlessExtensionRegistrationHelper headlessExtensionRegistrationHelper = localInjector
				.getInstance(HeadlessExtensionRegistrationHelper.class);
		headlessExtensionRegistrationHelper.registerExtensions();
		return instance;
	}

}
