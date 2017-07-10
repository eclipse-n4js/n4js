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
package org.eclipse.n4js.n4jsx

import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage
import org.eclipse.emf.ecore.xml.type.XMLTypePackage
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4mf.N4mfPackage
import com.google.inject.Injector
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TypesPackage

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class N4JSXStandaloneSetup extends N4JSXStandaloneSetupGenerated {

	/**
	 * Performs the setup and populates the EMF registers in the stand-alone environment.
	 *
	 * @see #createInjectorAndDoEMFRegistration()
	 */
	def static void doSetup() {
		new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistration()
	}

	/**
	 * Same as {@link #doSetup()}, but won't invoke {@code N4JSStandaloneSetup#doSetup()}. For details, see
	 * {@link #createInjectorAndDoEMFRegistrationWithoutParentLanguages()}.
	 */
	def static Injector doSetupWithoutParentLanguages() {
		return new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistrationWithoutParentLanguages();
	}

	override Injector createInjectorAndDoEMFRegistration() {
		// trigger class loading
		TypeRefsPackage.eINSTANCE.getNsURI();
		TypesPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		N4mfPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();
		N4JSXPackage.eINSTANCE.getNsURI();

		return super.createInjectorAndDoEMFRegistration();
	}

	/**
	 * Same as {@link #createInjectorAndDoEMFRegistration()}, but won't invoke {@code N4JSStandaloneSetup#doSetup()}.
	 * <p>
	 * The default Xtext behavior of {@link #createInjectorAndDoEMFRegistration()} is to invoke {@link #doSetup()} on
	 * the parent language(s), which is N4JS in our case. This method performs all registration for N4JSX <b>without</b>
	 * triggering such a setup of N4JS.
	 * <p>
	 * <b>This method assumes that the setup of N4JS has already taken place.</b>
	 */
	def Injector createInjectorAndDoEMFRegistrationWithoutParentLanguages() {
		// trigger class loading
		TypeRefsPackage.eINSTANCE.getNsURI();
		TypesPackage.eINSTANCE.getNsURI();
		N4JSPackage.eINSTANCE.getNsURI();
		N4mfPackage.eINSTANCE.getNsURI();
		XMLTypePackage.eINSTANCE.getNsURI();
		N4JSXPackage.eINSTANCE.getNsURI();

		val Injector injector = createInjector();
		register(injector);
		return injector;
	}
}
