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
package org.eclipse.n4js.utils.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Should be used as base implementation of all {@link EObject}s that may possibly be contained in a
 * {@link ProxyResolvingResource}. For details see API documentation of {@link ProxyResolvingResource}.
 */
public abstract class ProxyResolvingEObjectImpl extends MinimalEObjectImpl.Container {

	/**
	 * Invokes {@link ProxyResolvingResource#doResolveProxy(InternalEObject, EObject) #doResolveProxy()} iff this
	 * {@link EObject} is contained in a {@link ProxyResolvingResource}.
	 */
	@Override
	public EObject eResolveProxy(InternalEObject proxy) {
		final Resource resource = eResource();
		if (resource instanceof ProxyResolvingResource) {
			return ((ProxyResolvingResource) resource).doResolveProxy(proxy, this);
		}
		return super.eResolveProxy(proxy);
	}
}
