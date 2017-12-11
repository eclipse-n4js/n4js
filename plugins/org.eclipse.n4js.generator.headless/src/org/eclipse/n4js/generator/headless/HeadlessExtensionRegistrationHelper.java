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
package org.eclipse.n4js.generator.headless;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.generator.SubGeneratorRegistry;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;

import com.google.inject.Inject;

/**
 * This class provides helper methods for registering extensions in headless case. This should become obsolete when
 * extension points fully works for headless case.
 */
public class HeadlessExtensionRegistrationHelper {
	@Inject
	private SubGeneratorRegistry subGeneratorRegistry;

	@Inject
	private EcmaScriptSubGenerator ecmaScriptSubGenerator;

	/**
	 * Register extensions manually. This method should become obsolete when extension point fully works in headless
	 * case.
	 */
	public void registerExtensionsManually() {
		// Register ECMAScript subgenerator
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.N4JS_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.JS_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.N4JSX_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.JSX_FILE_EXTENSION);
	}
}
