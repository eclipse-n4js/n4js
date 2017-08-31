/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

/**
 * This class contains helper methods for working with Xtext.
 */
 @Singleton
class XtextUtilN4 {
	@Inject IContainer.Manager containerManager;
	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject	IResourceDescription.Manager descriptionManager;

	/**
	 * Return all EObjectDescriptions in the index that are visible from an EObject.
	 *  @param o
	 * 			the EObject
	 * <p>
	 * See Lorenzo's book page 260.
	 */
	def Iterable<IEObjectDescription> getVisibleEObjectDescriptions(EObject o) {
		o.visibleContainers.map [ container | container.exportedObjects ].flatten;
	}

	/**
	 * Return all EObjectDescriptions of a certain type in the index that are visible from an EObject.
	 * @param o
	 *			the EObject.
	 * @param type
	 * 			the type of EObjectDescriptions to filter.
	 * <p>
	 * See Lorenzo's book page 260.
	 */
	def Iterable<IEObjectDescription> getVisibleEObjectDescriptions(EObject o, EClass type) {
		o.visibleContainers.map [ container | container.getExportedObjectsByType(type) ].flatten;
	}

	/**
	 * Return all visible containers from within a given resource.
	 * @param resource
	 * 					the resource
	 */
	def List<IContainer> getVisibleContainers(Resource resource) {
		val index = resourceDescriptionsProvider.getResourceDescriptions(resource.getResourceSet());
		val resourceDescription = descriptionManager.getResourceDescription(resource);
		return  containerManager.getVisibleContainers(resourceDescription, index);
	}

	def private List<IContainer> getVisibleContainers(EObject o) {
		val index = resourceDescriptionsProvider.getResourceDescriptions((o.eResource));
		val resourceDescription = index.getResourceDescription(o.eResource.URI)
		if (resourceDescription === null) {
			Collections.emptyList;
		} else {
			containerManager.getVisibleContainers(resourceDescription, index);
		}
	}
}
