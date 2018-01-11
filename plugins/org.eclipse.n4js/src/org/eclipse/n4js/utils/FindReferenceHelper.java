/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ts.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */
@SuppressWarnings("restriction")
public class FindReferenceHelper {

	@Inject
	private Provider<TargetURIs> targetURISetProvider;

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private TargetURICollector collector;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	/**
	 */
	public List<EObject> findReferences(EObject declaration) {
		if (declaration instanceof ParameterizedTypeRef) {
			declaration = ((ParameterizedTypeRef) declaration).getDeclaredType();
		}

		// Special handling for composed members
		List<EObject> realTargets = new ArrayList<>();
		if ((declaration instanceof TMember) && ((TMember) declaration).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) declaration).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				realTargets.add(constituentMember);
			}
		} else {
			// Standard case
			realTargets.add(declaration);
		}

		Resource eResource = declaration.eResource();
		TargetURIs targets = targetURISetProvider.get();

		for (EObject realTarget : realTargets) {
			collector.add(realTarget, targets);
		}

		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(eResource);
		ArrayList<EObject> result = Lists.newArrayList();

		IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {
			@Override
			public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
				result.add(src);
			}

			@Override
			public void accept(IReferenceDescription description) {
				// This method is only called in case of finding refs for primitives.
				// For instance, the method is called when a reference to a primitive type (e.g. string)
				// is found in primitive_ts.n4ts
				// We don't care about those in Xpect test.
			}
		};

		SimpleResourceAccess resourceAccess = new SimpleResourceAccess(eResource.getResourceSet());
		referenceFinder.findAllReferences(targets, resourceAccess, index, acceptor, null);

		return result;
	}
}
