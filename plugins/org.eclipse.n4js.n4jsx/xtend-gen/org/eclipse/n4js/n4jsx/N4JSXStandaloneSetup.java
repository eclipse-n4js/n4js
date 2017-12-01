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
package org.eclipse.n4js.n4jsx;

import com.google.inject.Injector;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4jsx.N4JSXStandaloneSetupGenerated;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class N4JSXStandaloneSetup extends N4JSXStandaloneSetupGenerated {
  /**
   * Performs the setup and populates the EMF registers in the stand-alone environment.
   * 
   * @see #createInjectorAndDoEMFRegistration()
   */
  public static void doSetup() {
    new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
  
  /**
   * Same as {@link #doSetup()}, but won't invoke {@code N4JSStandaloneSetup#doSetup()}. For details, see
   * {@link #createInjectorAndDoEMFRegistrationWithoutParentLanguages()}.
   */
  public static Injector doSetupWithoutParentLanguages() {
    return new N4JSXStandaloneSetup().createInjectorAndDoEMFRegistrationWithoutParentLanguages();
  }
  
  @Override
  public Injector createInjectorAndDoEMFRegistration() {
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
  public Injector createInjectorAndDoEMFRegistrationWithoutParentLanguages() {
    TypeRefsPackage.eINSTANCE.getNsURI();
    TypesPackage.eINSTANCE.getNsURI();
    N4JSPackage.eINSTANCE.getNsURI();
    N4mfPackage.eINSTANCE.getNsURI();
    XMLTypePackage.eINSTANCE.getNsURI();
    N4JSXPackage.eINSTANCE.getNsURI();
    final Injector injector = this.createInjector();
    this.register(injector);
    return injector;
  }
}
