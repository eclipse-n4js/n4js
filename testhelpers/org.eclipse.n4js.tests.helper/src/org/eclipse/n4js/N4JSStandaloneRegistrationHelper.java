/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.validation.N4JSValidator;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Registration helper that can be bound in order to trigger a
 * {@link org.eclipse.xtext.resource.IResourceServiceProvider.Registry} registration of the services provided by the
 * current injector (e.g. validators, resource description managers, etc.).
 *
 * Also updates the {@link org.eclipse.emf.ecore.EValidator.Registry} accordingly, so that only the
 * {@link N4JSValidator} instance of the current injector is used.
 *
 * Make sure to bind this helper as eager singleton (cf. {@link SingletonBinding#eager()}) so that it is executed on
 * injector creation.
 *
 * This helper will unregister all languages services that were registered by other injectors.
 */
@Singleton
public class N4JSStandaloneRegistrationHelper {
	/** Registration method that is executed on injector instantiation. */
	@Inject
	public void registerServiceProvider(Injector injector) {
		// register N4JS resource service providers
		new N4JSStandaloneSetup().register(injector);

		final N4JSValidator validator = injector.getInstance(N4JSValidator.class);
		final EValidatorRegistrar registrar = injector.getInstance(EValidatorRegistrar.class);

		// clear list of existing N4JS-package validators (removes obsolete validators originating from other injectors)
		EValidator.Registry.INSTANCE.remove(N4JSPackage.eINSTANCE);
		// re-register N4JSValidator
		validator.register(registrar);
	}
}
