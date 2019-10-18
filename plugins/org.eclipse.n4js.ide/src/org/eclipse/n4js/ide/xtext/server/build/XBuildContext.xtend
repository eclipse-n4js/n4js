/*******************************************************************************
 * Copyright (c) 2015, 2016 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.xtext.server.build

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy
import org.eclipse.xtext.util.CancelIndicator

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
@FinalFieldsConstructor
class XBuildContext {
	val (URI)=>IResourceServiceProvider resourceServiceProviderProvider
	@Accessors val XtextResourceSet resourceSet
	@Accessors val XIndexState oldState
	@Accessors val IResourceClusteringPolicy clusteringPolicy
	@Accessors val CancelIndicator cancelIndicator
	
	XClusteringStorageAwareResourceLoader loader
	
	def <T> Iterable<T> executeClustered(Iterable<URI> uri, (Resource)=>T operation) {
		if(loader === null) 
			loader = new XClusteringStorageAwareResourceLoader(this)
		return loader.executeClustered(uri.filter[canHandle], operation)
	}
	
	protected def boolean canHandle(URI uri) {
		val resourceServiceProvider = resourceServiceProviderProvider.apply(uri)
		if (resourceServiceProvider === null)
			return false

		return resourceServiceProvider.canHandle(uri)
	}
	
	def getResourceServiceProvider(URI uri) {
		val resourceServiceProvider = resourceServiceProviderProvider.apply(uri)
		return resourceServiceProvider
	}
	
}
