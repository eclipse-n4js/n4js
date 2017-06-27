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
package org.eclipse.n4js.common.unicode;

/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class UnicodeStandaloneSetup extends UnicodeStandaloneSetupGenerated {

	/**
	 * Perform the setup and register at global EMF singletons.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		new UnicodeStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
