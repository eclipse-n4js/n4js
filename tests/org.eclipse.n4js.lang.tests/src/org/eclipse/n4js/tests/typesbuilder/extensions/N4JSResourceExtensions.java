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
package org.eclipse.n4js.tests.typesbuilder.extensions;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class N4JSResourceExtensions {
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	public N4JSResource reloadResourceFromDescription(Resource testResource,
			IResourceDescription updatedResourceDescription) {
		XtextResourceSet rs2 = resourceSetProvider.get();
		N4JSResource newN4jsResource = (N4JSResource) rs2.createResource(testResource.getURI());
		newN4jsResource.loadFromDescription(updatedResourceDescription);
		return newN4jsResource;
	}

	public IResourceDescription getResourceDescription(Resource testResource) {
		URI uri = testResource.getURI();
		ResourceSet resourceSet = testResource.getResourceSet();
		IResourceDescriptions updatedResourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(resourceSet);
		IResourceDescription updatedResourceDescription = updatedResourceDescriptions.getResourceDescription(uri);

		// trigger user data computation (see
		// N4JSResourceDescriptionStrategy#internalCreateEObjectDescriptionForRoot(TModule,
		// IAcceptor<IEObjectDescription>))
		for (IEObjectDescription od : updatedResourceDescription
				.getExportedObjectsByType(TypesPackage.eINSTANCE.getTModule())) {
			od.getEObjectURI();
		}
		return updatedResourceDescription;
	}

	public boolean unloadResource(Resource testResource) {
		ResourceSet resourceSet = testResource.getResourceSet();
		testResource.unload();
		return resourceSet.getResources().remove(testResource);
	}

	public void resolve(Resource testResource) {
		if (testResource instanceof N4JSResource) {
			((N4JSResource) testResource).resolveLazyCrossReferences(CancelIndicator.NullImpl);
		} else {
			EcoreUtil.resolveAll(testResource);
		}
	}
}
