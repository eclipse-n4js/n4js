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
package org.eclipse.n4js.ts;

import com.google.inject.Injector;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class TypesStandaloneSetup extends TypesStandaloneSetupGenerated {
	/**
	 * Performs the setup and populates the EMF registries in the standalone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	public static void doSetup() {
		new TypesStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		// register the types package
		TypeRefsPackage.eINSTANCE.getBaseTypeRef();
		TypesPackage.eINSTANCE.getAnyType();
		return super.createInjectorAndDoEMFRegistration();
	}
}
