/**
 * Copyright (c) 2015, 2016 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.XClusteringStorageAwareResourceLoader.LoadResult;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.common.collect.Iterables;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
@SuppressWarnings("restriction")
public class XBuildContext {
	private final Function1<? super URI, ? extends IResourceServiceProvider> resourceServiceProviderProvider;

	private final XtextResourceSet resourceSet;

	private final XIndexState oldState;

	private final IResourceClusteringPolicy clusteringPolicy;

	private final CancelIndicator cancelIndicator;

	private XClusteringStorageAwareResourceLoader loader;

	/**
	 * Constructor.
	 */
	public XBuildContext(
			Function1<? super URI, ? extends IResourceServiceProvider> resourceServiceProviderProvider,
			XtextResourceSet resourceSet, XIndexState oldState,
			IResourceClusteringPolicy clusteringPolicy, CancelIndicator cancelIndicator) {
		this.resourceServiceProviderProvider = resourceServiceProviderProvider;
		this.resourceSet = resourceSet;
		this.oldState = oldState;
		this.clusteringPolicy = clusteringPolicy;
		this.cancelIndicator = cancelIndicator;
	}

	/**
	 * Run the given logic on all uris with clustering enabled.
	 */
	public <T> List<T> executeClustered(Iterable<URI> uri, Function1<? super LoadResult, ? extends T> operation) {
		if (this.loader == null) {
			this.loader = new XClusteringStorageAwareResourceLoader(this);
		}
		return this.loader.executeClustered(Iterables.filter(uri, this::canHandle), operation);
	}

	/**
	 * Return true, if the given can be handled by any available language.
	 */
	protected boolean canHandle(URI uri) {
		IResourceServiceProvider resourceServiceProvider = getResourceServiceProvider(uri);
		if ((resourceServiceProvider == null)) {
			return false;
		}
		return resourceServiceProvider.canHandle(uri);
	}

	/**
	 * Get the resource service provider for this URI.
	 */
	public IResourceServiceProvider getResourceServiceProvider(URI uri) {
		return resourceServiceProviderProvider.apply(uri);
	}

	/**
	 * Getter
	 */
	public XtextResourceSet getResourceSet() {
		return this.resourceSet;
	}

	/**
	 * Getter
	 */
	public XIndexState getOldState() {
		return this.oldState;
	}

	/**
	 * Getter
	 */
	public IResourceClusteringPolicy getClusteringPolicy() {
		return this.clusteringPolicy;
	}

	/**
	 * Getter
	 */
	public CancelIndicator getCancelIndicator() {
		return this.cancelIndicator;
	}
}
