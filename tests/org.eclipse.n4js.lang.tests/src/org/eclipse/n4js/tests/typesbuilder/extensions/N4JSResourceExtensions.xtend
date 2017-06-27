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
package org.eclipse.n4js.tests.typesbuilder.extensions

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.util.CancelIndicator

/**
 */
class N4JSResourceExtensions {
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject Provider<XtextResourceSet> resourceSetProvider

	def reloadResourceFromDescription(Resource testResource, IResourceDescription updatedResourceDescription) {
		var rs2 = resourceSetProvider.get()
		val newN4jsResource = rs2.createResource(testResource.URI) as N4JSResource
		newN4jsResource.loadFromDescription(updatedResourceDescription);
		newN4jsResource
	}

	def getResourceDescription(Resource testResource) {
		val uri = testResource.URI
		val resourceSet = testResource.resourceSet
		val updatedResourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		val updatedResourceDescription = updatedResourceDescriptions.getResourceDescription(uri)
		updatedResourceDescription.getExportedObjectsByType(TypesPackage.eINSTANCE.TModule).forEach[EObjectURI]  // trigger user data computation (see N4JSResourceDescriptionStrategy#internalCreateEObjectDescriptionForRoot(TModule, IAcceptor<IEObjectDescription>))
		updatedResourceDescription
	}

	def unloadResource(Resource testResource) {
		val resourceSet = testResource.resourceSet
		testResource.unload
		resourceSet.resources.remove(testResource)
	}

	def resolve(Resource testResource) {
		if(testResource instanceof N4JSResource)
			testResource.resolveLazyCrossReferences(CancelIndicator.NullImpl)
		else
			EcoreUtil2.resolveAll(testResource)
	}
}
