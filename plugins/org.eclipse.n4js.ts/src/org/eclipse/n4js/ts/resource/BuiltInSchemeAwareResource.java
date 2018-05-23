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
package org.eclipse.n4js.ts.resource;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

import com.google.inject.Inject;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;

/**
 */
public class BuiltInSchemeAwareResource extends LazyLinkingResource {

	@Inject
	private BuiltInSchemeRegistrar registrar;

	@Override
	protected URIConverter getURIConverter() {
		return getResourceSet() == null ? createNewURIConverter() : getResourceSet().getURIConverter();
	}

	private URIConverter createNewURIConverter() {
		URIConverter result = new ExtensibleURIConverterImpl();
		registrar.registerScheme(result);
		return result;
	}
}
