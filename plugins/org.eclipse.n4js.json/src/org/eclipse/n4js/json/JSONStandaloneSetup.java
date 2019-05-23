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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.common.unicode.UnicodeStandaloneSetup;
import org.eclipse.n4js.json.JSON.JSONPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class JSONStandaloneSetup extends JSONStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registers in a stand-alone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		UnicodeStandaloneSetup.doSetup();

		new JSONStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		JSONPackage.eINSTANCE.getNsURI();

		EPackage.Registry.INSTANCE.put(JSONPackage.eNS_URI, JSONPackage.eINSTANCE);

		UnicodeStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}

	/**
	 * Tells whether the JSON Xtext language has already been set up. This implements a heuristic only in that it simply
	 * checks EMF's EPackage registry, but not any other aspects of a proper Xtext language setup.
	 */
	public static boolean isSetUp() {
		return EPackage.Registry.INSTANCE.containsKey(JSONPackage.eNS_URI);
	}
}
