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
package org.eclipse.n4js.ide.xtext.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * Do never unload builtin resources for performance considerations.
 */
public class BuiltInAwareIncrementalBuilder extends XStatefulIncrementalBuilder {

	@Override
	protected void unloadResource(URI uri) {
		XtextResourceSet resourceSet = getRequest().getResourceSet();
		Resource resource = resourceSet.getResource(uri, false);
		// cannot use the given URI here since it may be the normalized file:/// variant of the n4scheme URI
		// that's why we ask the resource itself.
		if (resource != null && !N4Scheme.isN4Scheme(resource.getURI())) {
			resourceSet.getResources().remove(resource);
			// proxify
			resource.unload();
		}
	}

}
