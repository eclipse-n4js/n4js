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
package org.eclipse.n4js.ide.server.build;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;
import org.eclipse.n4js.xtext.ide.server.build.XClusteringStorageAwareResourceLoader;

/**
 * N4JS-specific adjustments for {@link XClusteringStorageAwareResourceLoader}.
 */
public class N4JSClusteringStorageAwareResourceLoader extends XClusteringStorageAwareResourceLoader {

	@Override
	protected LoadResult loadResource(ResourceSet resourceSet, URI uri, Set<URI> addNewUrisHere, Set<URI> urisDone) {
		if (URIUtils.isVirtualResourceURI(uri)) {
			ILoadResultInfoAdapter.ensureNestedResourcesExist(resourceSet, uri);
		}
		return super.loadResource(resourceSet, uri, addNewUrisHere, urisDone);
	}
}
