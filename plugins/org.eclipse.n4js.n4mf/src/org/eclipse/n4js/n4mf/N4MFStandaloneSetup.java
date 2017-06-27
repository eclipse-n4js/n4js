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
package org.eclipse.n4js.n4mf;

import org.eclipse.emf.ecore.EPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class N4MFStandaloneSetup extends N4MFStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registries in the standalone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		new N4MFStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		N4mfPackage.eINSTANCE.getNsURI();

		return super.createInjectorAndDoEMFRegistration();
	}

	@Override
	public void register(Injector injector) {
		EPackage.Registry.INSTANCE.put(N4mfPackage.eINSTANCE.getNsURI(), N4mfPackage.eINSTANCE);
		super.register(injector);
	}
}
