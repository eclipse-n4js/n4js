/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
 *	in bundle org.eclipse.xtext
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2011, 2017 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;

import com.google.inject.Inject;

/**
 */
public class N4JSExternalReferenceChecker {
	private final Logger LOG = Logger.getLogger(N4JSExternalReferenceChecker.class);

	@Inject
	private LazyURIEncoder uriEncoder;

	/**
	 * Checks, if the second EObject is resolved and if it is contained in different resource than the first EObject. If
	 * the second EObject is a proxy than its proxy URI fragment is used to check for different resources. The first
	 * EObject is expected to be already resolved.
	 *
	 * @param from
	 *            the EObject containing the reference to the second EObject
	 * @param to
	 *            the EObject referenced by the first EObject
	 * @return true, when second EObject can be resolved and not in the same resource as the first EObject is
	 */
	// Copied from DefaultResourceDescriptionStrategy
	public boolean isResolvedAndExternal(EObject from, EObject to) {
		if (to == null)
			return false;
		if (!to.eIsProxy()) {
			if (to.eResource() == null) {
				LOG.error("Reference from " + EcoreUtil.getURI(from) + " to " + to
						+ " cannot be exported as the target is not contained in a resource.");
				return false;
			}
			return from.eResource() != to.eResource();
		}
		return !uriEncoder
				.isCrossLinkFragment(from.eResource(), ((InternalEObject) to).eProxyURI().fragment());
	}

	/**
	 * Checks if the second EObject is resolved and contained in a different resource than the first given resource.If
	 * the second EObject is a proxy than its proxy URI fragment is used to check. The first resource is expected to be
	 * already resolved.
	 *
	 * @param resource
	 *            the resource
	 * @param to
	 *            The EObject that should be checked if it is located in a resource other than the given resource
	 * @return true, if the EObject is resolved and not in the same resource.
	 */
	public boolean isResolvedAndExternal(Resource resource, EObject to) {
		if (to == null)
			return false;
		if (!to.eIsProxy()) {
			if (to.eResource() == null) {
				LOG.error("The target is not contained in a resource.");
				return false;
			}
			return resource != to.eResource();
		}
		return !uriEncoder
				.isCrossLinkFragment(resource, ((InternalEObject) to).eProxyURI().fragment());
	}
}
