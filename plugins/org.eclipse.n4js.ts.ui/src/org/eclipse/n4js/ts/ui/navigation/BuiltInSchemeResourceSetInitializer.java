/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.ui.navigation;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.ConfiguredResourceSetProvider;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Configures a created resource set with the builtin scheme
 */
@Singleton
public class BuiltInSchemeResourceSetInitializer extends ConfiguredResourceSetProvider
		implements IResourceSetInitializer {

	/**
	 * Standard constructor used by the injector.
	 */
	@Inject
	public BuiltInSchemeResourceSetInitializer(EffectiveRegistrarProvider registrar,
			ClassLoader classLoader,
			ResourceSetWithBuiltInSchemeProvider builtInProvider,
			UriExtensions uriExtensions) {
		super(registrar.get(), classLoader, builtInProvider, uriExtensions);
	}

	@Override
	public void initialize(ResourceSet resourceSet, IProject project) {
		initialize((SynchronizedXtextResourceSet) resourceSet);
	}

}
