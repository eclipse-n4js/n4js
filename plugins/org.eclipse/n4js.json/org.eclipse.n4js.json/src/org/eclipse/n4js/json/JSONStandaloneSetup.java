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
package org.eclipse.n4js.json;


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class JSONStandaloneSetup extends JSONStandaloneSetupGenerated {

	public static void doSetup() {
		new JSONStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
