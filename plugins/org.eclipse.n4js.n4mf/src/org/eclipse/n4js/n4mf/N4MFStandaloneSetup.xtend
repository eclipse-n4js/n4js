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
package org.eclipse.n4js.n4mf

import com.google.inject.Injector
import org.eclipse.emf.ecore.EPackage

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class N4MFStandaloneSetup extends N4MFStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registries in the standalone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	def static void doSetup() {
		new N4MFStandaloneSetup().createInjectorAndDoEMFRegistration()
	}

	override Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		N4mfPackage.eINSTANCE.getNsURI();

		return super.createInjectorAndDoEMFRegistration();
	}

	override void register(Injector injector) {
		EPackage.Registry.INSTANCE.put(N4mfPackage.eINSTANCE.getNsURI(), N4mfPackage.eINSTANCE);
		super.register(injector);
	}
}
