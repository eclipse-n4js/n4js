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
package org.eclipse.n4js.semver;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.common.unicode.UnicodeStandaloneSetup;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class SEMVERStandaloneSetup extends SEMVERStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registers in a stand-alone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		new SEMVERStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		SEMVERPackage.eINSTANCE.getNsURI();

		EPackage.Registry.INSTANCE.put(SEMVERPackage.eNS_URI, SEMVERPackage.eINSTANCE);

		UnicodeStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}
}
