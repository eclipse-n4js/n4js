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
package org.eclipse.n4js.ts.ui.search;

import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;

import org.eclipse.n4js.ts.findReferences.TargetURIKey;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;

/**
 * Specialization of the TargetURIKey that knows how to find objects by an {@link N4Scheme#SCHEME n4scheme}.
 */
@SuppressWarnings("restriction")
public class BuiltinSchemeAwareTargetURIKey extends TargetURIKey {

	@Inject
	private IResourceSetProvider resourceSetProvider;

	@Override
	public Data getData(TargetURIs targetURIs, IResourceAccess resourceAccess) {
		return super.getData(targetURIs, new ForwardingResourceAccess(resourceAccess, resourceSetProvider));
	}

}
