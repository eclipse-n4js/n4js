/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.lsp4j.Location;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.xtext.ide.server.DocumentExtensions;
import org.eclipse.xtext.util.ITextRegion;

import com.google.inject.Singleton;

/**
 * Allow to obtain a file system location from a n4scheme URI.
 */
@Singleton
public class N4JSDocumentExtensions extends DocumentExtensions {

	@Override
	public Location newLocation(Resource resource, ITextRegion textRegion) {
		Location result = super.newLocation(resource, textRegion);
		if (result != null) {
			if (N4Scheme.isResourceWithN4Scheme(resource)) {
				URI uri = resource.getURI();
				URIConverter uriConverter = resource.getResourceSet().getURIConverter();
				URI classpathURI = N4Scheme.toClasspathURI(uri);
				URI resolvedURI = uriConverter.normalize(classpathURI);
				return new Location(resolvedURI.toString(), result.getRange());
			}
		}
		return result;
	}

}
