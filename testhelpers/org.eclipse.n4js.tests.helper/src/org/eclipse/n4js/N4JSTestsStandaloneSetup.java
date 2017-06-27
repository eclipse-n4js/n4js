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
package org.eclipse.n4js;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/***/
public class N4JSTestsStandaloneSetup extends N4JSStandaloneSetup {
	@Override
	public Injector createInjector() {
		/*
		 * Was: return Guice.createInjector(Modules2.mixin(new TypesRuntimeModule(), new JSRuntimeModule(), new
		 * JSTestsModule()));
		 *
		 * It should be avoided to use runtime modules of different languages in a single injector since the services
		 * from one languages would impact the registrations of the other
		 *
		 * Instead, the standalone setup of the used language should be invoked explicitly if it needs to register some
		 * services on its own.
		 *
		 * Shared / common registrations should be extracted into a single module that's used by all the languages that
		 * need this.
		 */
		return Guice.createInjector(Modules2.mixin(new N4JSRuntimeModule(), new N4JSTestsModule()));
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		EPackage.Registry.INSTANCE.put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypeRefsPackage.eNS_URI, TypeRefsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, TypesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(N4JSPackage.eNS_URI, N4JSPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(N4mfPackage.eNS_URI, N4mfPackage.eINSTANCE);
		return super.createInjectorAndDoEMFRegistration();
	}
}
