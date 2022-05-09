/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.build;

import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.utils.URIUtils;

/**
 * This adapter is installed on host resources and holds information about nested resources that need to be created and
 * loaded when loading this resource.
 */
public interface ILoadResultInfoAdapter extends Adapter {

	/** Returns the {@link ILoadResultInfoAdapter} that is installed on the given resource. */
	public static ILoadResultInfoAdapter get(Resource resource) {
		for (Adapter adapter : resource.eAdapters()) {
			if (adapter instanceof ILoadResultInfoAdapter) {
				return (ILoadResultInfoAdapter) adapter;
			}
		}
		return null;
	}

	/** Returns the {@link ILoadResultInfoAdapter} that is installed on the given resource or null. */
	public static ILoadResultInfoAdapter remove(Resource resource) {
		ILoadResultInfoAdapter adapter = get(resource);
		if (adapter != null) {
			resource.eAdapters().remove(adapter);
			return adapter;
		}
		return adapter;
	}

	/** Returns all virtual {@link URI}s of nested resources */
	public Collection<URI> getNewUris();

	/**
	 * The {@link ResourceSet} of the given resource is checked that all nested resources of the given resource exist.
	 */
	public void ensureNestedResourcesExist(Resource resource);

	/**
	 * Ensures that all nested resources of the host resource corresponding to the given URI exist.
	 */
	public static void ensureNestedResourcesExist(ResourceSet resourceSet, URI hostOrNestedResourceURI) {
		URI hostURI = URIUtils.getBaseOfVirtualResourceURI(hostOrNestedResourceURI);
		Resource hostResource = resourceSet.getResource(hostURI, true);
		ILoadResultInfoAdapter loadResultInfoAdapter = ILoadResultInfoAdapter.get(hostResource);
		if (loadResultInfoAdapter != null) {
			loadResultInfoAdapter.ensureNestedResourcesExist(hostResource);
		}
	}
}
