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
package org.eclipse.n4js.scoping.accessModifiers;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.ErrorAwareLinkingService;
import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * A scope implementation that annotates invisible types as such to flag them with errors immediately on access.
 *
 * @see ErrorAwareLinkingService#addIssue
 */
public class VisibilityAwareTypeScope extends FilterWithErrorMarkerScope {

	private final TypeVisibilityChecker checker;

	/**
	 * Stores all access modifier suggestions for IEObjectDescriptions uri's for descriptions that weren't accepted in
	 * the isAccepted method.
	 */
	protected final HashMap<String, String> accessModifierSuggestionStore = new HashMap<>();

	/**
	 * The resource from which the type is to be accessed.
	 */
	protected final Resource contextResource;

	/**
	 * Creates a new scope instance.
	 */
	public VisibilityAwareTypeScope(IScope parent, TypeVisibilityChecker checker, Resource contextResource) {
		super(parent);
		this.checker = checker;
		this.contextResource = contextResource;
	}

	@Override
	protected boolean isAccepted(IEObjectDescription description) {
		EObject proxyOrInstance = description.getEObjectOrProxy();

		if (proxyOrInstance instanceof Type) {
			Type type = (Type) proxyOrInstance;
			if (type.eIsProxy() && contextResource != null) {
				// we found a proxy. Only valid precondition for a proxy that was returned from a
				// UserDataAwareScope is that the resource that contains the real instance is already
				// available in the resource set and loaded.
				// thus it's save to use resourceSet.getEObject(uri, false) and it has to return a proper instance
				//
				ResourceSet resourceSet = contextResource.getResourceSet();
				// don't load on demand
				EObject fromResourceSet = resourceSet.getEObject(description.getEObjectURI(), false);
				if (fromResourceSet instanceof Type) {
					type = (Type) fromResourceSet;
				} else {
					// type is no longer available in resource set because index state is out of sync
					// assume the unresolved proxy to be visible to reduce number of follow-up problems.
					return true;
				}
			}
			// paranoid double check - object from resource set should never be a proxy
			if (!type.eIsProxy()) {
				TypeVisibility typeVisibility = checker.isVisible(contextResource, type);

				if (typeVisibility.visibility) {
					accessModifierSuggestionStore.put(description.getEObjectURI().toString(),
							typeVisibility.accessModifierSuggestion);
				}
				return typeVisibility.visibility;

			} else {
				throw new IllegalStateException("Unexpected proxy:" + type);

			}
		}
		return true;

	}

	@Override
	protected boolean tryAcceptWithoutResolve(IEObjectDescription originalDescr) {
		TypeVisibility typeVisibility = checker.isVisible(contextResource, originalDescr);
		if (typeVisibility.visibility) {
			accessModifierSuggestionStore.put(originalDescr.getEObjectURI().toString(),
					typeVisibility.accessModifierSuggestion);
		}
		return typeVisibility.visibility;
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription result) {
		return new InvisibleTypeOrVariableDescription(result,
				accessModifierSuggestionStore.get(result.getEObjectURI().toString()));
	}
}
