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
package org.eclipse.n4js.ts.scoping.builtin;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.n4js.xtext.resourceset.EmptyAuthorityAddingNormalizer;
import org.eclipse.n4js.xtext.resourceset.XtextResourceLocator;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides access to a singleton resource set with all the built in resources being available.
 */
@Singleton
public class ResourceSetWithBuiltInSchemeProvider {

	private final ResourceSet resourceSet;

	/**
	 * Standard constructor used by the injector.
	 */
	@Inject
	public ResourceSetWithBuiltInSchemeProvider(
			BuiltInSchemeRegistrar registrar,
			ClassLoader classLoader,
			UriExtensions uriExtensions) {
		@SuppressWarnings("hiding")
		SynchronizedXtextResourceSet resourceSet = new SynchronizedXtextResourceSet();
		attachXtextResourceLocator(resourceSet);
		resourceSet.setClasspathURIContext(classLoader);
		registrar.registerScheme(resourceSet);
		resourceSet.setURIConverter(new EmptyAuthorityAddingNormalizer(resourceSet.getURIConverter(), uriExtensions));
		this.resourceSet = resourceSet;
	}

	/**
	 * Obtain the resource set.
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	private ResourceLocator attachXtextResourceLocator(
			@SuppressWarnings("hiding") SynchronizedXtextResourceSet resourceSet) {
		return new XtextResourceLocator(resourceSet);
	}
}
