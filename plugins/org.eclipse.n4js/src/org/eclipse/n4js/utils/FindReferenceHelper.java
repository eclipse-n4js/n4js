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
import org.eclipse.emf.ecore.resource.ResourceSet;
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
 * Helper class to find references.
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

	/** @return all references to the given declaration. Respect editor states. */
	public List<EObject> findReferences(EObject declaration, ResourceSet resourceSet) {
		declaration = getDeclaration(declaration);
		TargetURIs targets = getTargets(declaration);
		SimpleResourceAccess resourceAccess = new SimpleResourceAccess(resourceSet);
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		ReferenceAcceptor acceptor = new ReferenceAcceptor();

		referenceFinder.findAllReferences(targets, resourceAccess, index, acceptor, null);

		ResourceSet maybeBuiltInResourceSet = declaration.eResource().getResourceSet();
		if (maybeBuiltInResourceSet != resourceSet) {
			resourceAccess = new SimpleResourceAccess(maybeBuiltInResourceSet);
			index = resourceDescriptionsProvider.getResourceDescriptions(maybeBuiltInResourceSet);
			referenceFinder.findAllReferences(targets, resourceAccess, index, acceptor, null);
		}

		return acceptor.results;
	}

	/** @return all references to the given declaration in the given {@link Resource} */
	public List<EObject> findReferencesInResource(EObject declaration, Resource resource) {
		declaration = getDeclaration(declaration);
		TargetURIs targets = getTargets(declaration);
		ReferenceAcceptor acceptor = new ReferenceAcceptor();

		referenceFinder.findReferences(targets, resource, acceptor, null);

		return acceptor.results;
	}

	private EObject getDeclaration(EObject declaration) {
		if (declaration instanceof ParameterizedTypeRef) {
			declaration = ((ParameterizedTypeRef) declaration).getDeclaredType();
		}
		return declaration;
	}

	private TargetURIs getTargets(EObject declaration) {
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

		TargetURIs targets = targetURISetProvider.get();

		for (EObject realTarget : realTargets) {
			collector.add(realTarget, targets);
		}
		return targets;
	}

	static class ReferenceAcceptor implements IReferenceFinder.Acceptor {
		final ArrayList<EObject> results = Lists.newArrayList();

		@Override
		public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
			results.add(src);
		}

		@Override
		public void accept(IReferenceDescription description) {
			// This method is only called in case of finding refs for primitives.
			// For instance, the method is called when a reference to a primitive type (e.g. string)
			// is found in primitive_ts.n4ts
			// We don't care about those in Xpect test.
		}
	}

}
