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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;

/**
 * Helper service that allows to check whether a given type is visible in a certain context.
 */
public class TypeVisibilityChecker extends AbstractTypeVisibilityChecker<Type> {

	/**
	 * Returns <code>true</code> if the given type can be used from within the given module.
	 */
	@Override
	public TypeVisibility isVisible(Resource contextResource, Type t) {
		TypeAccessModifier typeAccessModifier = t.getTypeAccessModifier();
		return isVisible(contextResource, typeAccessModifier, t);
	}

	/**
	 * Returns the TypeVisibility of the <i>element</i> in the given <i>context</i>(that is the given resource) with the
	 * given accessModifier. That is, the actual access modifier of the element is not considered here, usually this is
	 * done in the caller via {@code getTypeAccessModifier}. However, there is no common interface for retrieving that
	 * information.
	 */
	protected TypeVisibility isVisible(Resource contextResource, TypeAccessModifier accessModifier,
			URI elementLocation) {

		int startIndex = accessModifier.getValue();

		boolean visibility = false;
		String firstVisible = "PUBLIC";

		for (int i = startIndex; i < TypeAccessModifier.values().length; i++) {

			boolean visibilityForModifier = false;
			TypeAccessModifier modifier = TypeAccessModifier.get(i);
			switch (modifier) {
			case PRIVATE: {
				visibilityForModifier = isPrivateVisible(contextResource, elementLocation);
				break;
			}
			case PROJECT: {
				visibilityForModifier = isProjectVisible(contextResource, elementLocation);
				break;
			}
			case PUBLIC_INTERNAL: {
				visibilityForModifier = isPublicInternalVisible(contextResource, elementLocation);
				break;
			}
			case PUBLIC: {
				visibilityForModifier = true;
				break;
			}
			default:
				visibilityForModifier = false;
				break;
			}
			// First modifier = element modifier
			if (i - startIndex < 1) {
				visibility = visibilityForModifier;
			}
			// First visible modifier = suggested element modifier
			if (visibilityForModifier) {
				firstVisible = modifier.getName().toUpperCase();
				break;
			}
		}
		return new TypeVisibility(visibility, firstVisible);

	}

	private boolean isPrivateVisible(Resource contextResource, URI elementLocation) {
		URI resURI = getURI(contextResource);
		boolean result = resURI.equals(elementLocation.trimFragment());
		return result;
	}

	private boolean isProjectVisible(Resource contextResource, URI elementLocation) {
		URI moduleURI = getURI(contextResource);

		if (moduleURI.isPlatformResource() && elementLocation.isPlatformResource()) {
			// Not valid for PUBLIC_INTERNAL
			final boolean visible = moduleURI.segment(1).equals(elementLocation.segment(1));
			// A special case when checking visibility between host and test project. Since type is not resolved and
			// we do not want additional CPU cycles we check whether the context resource contained in a test
			// project that has a host at all.
			// TODO IDEBUG-640 this needs to be reviewed again, since the refactoring made here are invalid.
			if (visible) {
				return true;
			} else {
				if (getTestedProjects(getURI(contextResource)).isEmpty()) {
					return false;
				}
				//$FALL-THROUGH$ if the project for the context resource has a host project.
			}
		} else {
			ResourceSet resourceSet = contextResource.getResourceSet();

			EObject loadedType = resourceSet.getEObject(elementLocation, false);
			if (loadedType == null) {
				loadedType = resourceSet.getEObject(elementLocation, true);
			}
			if (loadedType instanceof Type) {
				// delegate to the *real* impl
				return isVisible(contextResource, TypeAccessModifier.PROJECT, (Type) loadedType).visibility;
			}
		}
		return false;
	}

	private boolean isPublicInternalVisible(Resource contextResource, URI elementLocation) {
		// for more detailed checks, we have to obtain the referenced type from the resource set
		ResourceSet resourceSet = contextResource.getResourceSet();

		EObject loadedType = resourceSet.getEObject(elementLocation, false);
		if (loadedType == null) {
			loadedType = resourceSet.getEObject(elementLocation, true);
		}
		if (loadedType instanceof Type) {
			// delegate to the *real* impl
			return isVisible(contextResource, TypeAccessModifier.PUBLIC_INTERNAL, (Type) loadedType).visibility;
		}
		return false;
	}

	private URI getURI(Resource contextResource) {
		if (contextResource == null) {
			return null;
		}
		return contextResource.getURI();
	}

}
