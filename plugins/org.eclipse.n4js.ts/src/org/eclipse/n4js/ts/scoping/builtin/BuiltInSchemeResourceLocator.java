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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;

/**
 * A locator for resources with the builtin scheme.
 *
 * We do load these resources into a dedicated resource set to allow reusing the loaded instances across multiple
 * projects.
 */
public class BuiltInSchemeResourceLocator extends ResourceLocator {

	private final ResourceSet builtInSchemeResourceSet;

	/**
	 * Standard constructor.
	 */
	public BuiltInSchemeResourceLocator(ResourceSetImpl primaryResourceSet, ResourceSet builtInSchemeResourceSet) {
		super(primaryResourceSet);
		this.builtInSchemeResourceSet = builtInSchemeResourceSet;
	}

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		if (N4Scheme.isN4Scheme(uri)) {
			return builtInSchemeResourceSet.getResource(uri, loadOnDemand);
		} else {
			// try to find the built-in resource with a normalized URI
			Resource resource = builtInSchemeResourceSet.getResource(uri, false);
			if (resource != null) {
				if (!resource.isLoaded() && loadOnDemand) {
					demandLoadHelper(resource);
				}
				return resource;
			}
		}
		return basicGetResource(uri, loadOnDemand);
	}

}
