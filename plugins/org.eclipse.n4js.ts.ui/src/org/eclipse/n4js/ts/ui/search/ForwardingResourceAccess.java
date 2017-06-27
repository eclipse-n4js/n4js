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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;

/**
 * A specialized {@link org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess } that handles resources with
 * the {@link N4Scheme#SCHEME n4js} scheme. It has to provide a resource set even though no directly associated project
 * is found.
 */
@SuppressWarnings("restriction")
public class ForwardingResourceAccess implements IReferenceFinder.IResourceAccess, N4Scheme {

	private final IResourceSetProvider resourceSetProvider;

	private final IReferenceFinder.IResourceAccess delegate;

	/*
	 * Cache the resource set and reuse it for all built in resources that have to be processed.
	 */
	private ResourceSet resourceSet;

	/**
	 * Configure the delegate.
	 *
	 * @param delegate
	 *            the delegate to use.
	 */

	public ForwardingResourceAccess(IReferenceFinder.IResourceAccess delegate,
			IResourceSetProvider resourceSetProvider) {
		this.delegate = delegate;
		this.resourceSetProvider = resourceSetProvider;
	}

	@Override
	public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		if (N4Scheme.isN4Scheme(targetURI)) {
			if (resourceSet == null) {
				resourceSet = resourceSetProvider.get(null);
			}
			resourceSet.getResource(targetURI, true);
			try {
				return work.exec(resourceSet);
			} catch (Exception e) {
				throw new WrappedException(e);
			}
		}
		return delegate.readOnly(targetURI, work);
	}

}
