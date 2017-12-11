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
package com.enfore.n4idl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.n4idl.N4IDLRuntimeModule;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("all")
public class N4IDLStandaloneSetupGenerated implements ISetup {

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		N4JSStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}

	public Injector createInjector() {
		return Guice.createInjector(new N4IDLRuntimeModule());
	}

	public void register(Injector injector) {
		IResourceFactory resourceFactory = injector.getInstance(IResourceFactory.class);
		IResourceServiceProvider serviceProvider = injector.getInstance(IResourceServiceProvider.class);

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("n4idl", resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("n4idl", serviceProvider);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("idl", resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("idl", serviceProvider);
	}
}
