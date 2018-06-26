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
 */
@Singleton
public class N4JSStandloneRegistrationHelper {
	/** Registration method that is executed on injector instantiation. */
	@Inject
	public void registerServiceProvider(Injector injector) {
		// register N4JS resource service providers
		new N4JSStandaloneSetup().register(injector);

		// make sure the N4JSValidator instance of the current injector is used for validating N4JS resources
		EValidator.Registry.INSTANCE.put(N4JSPackage.eINSTANCE, injector.getInstance(N4JSValidator.class));
	}
}
