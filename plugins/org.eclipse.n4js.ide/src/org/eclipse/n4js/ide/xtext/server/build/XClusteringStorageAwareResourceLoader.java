/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.IResourceServiceProviderExtension;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.persistence.SourceLevelURIsAdapter;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import com.google.common.base.Throwables;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
@SuppressWarnings("restriction")
public class XClusteringStorageAwareResourceLoader {
	private final XBuildContext context;

	/** The result of loading an EMF resource. */
	public static class LoadResult {

		/** The URI of the resource that was loaded. */
		public final URI uri;
		/** The resource that was loaded or <code>null</code> iff loading failed. */
		public final Resource resource;
		/** The exception or error during loading or <code>null</code> iff loading succeeded. */
		public final Throwable throwable;

		/** Creates a load result in the success case. */
		public LoadResult(Resource resource) {
			this.uri = resource.getURI();
			this.resource = resource;
			this.throwable = null;
		}

		/** Creates a load result in the failure case. */
		public LoadResult(URI uri, Throwable throwable) {
			this.uri = uri;
			this.resource = null;
			this.throwable = throwable;
		}

		/** Tells whether loading failed with a {@link FileNotFoundException}. */
		public boolean isFileNotFound() {
			return throwable != null && Throwables.getRootCause(throwable) instanceof FileNotFoundException;
		}
	}

	/**
	 * Constructor
	 */
	public XClusteringStorageAwareResourceLoader(XBuildContext context) {
		this.context = context;
	}

	/**
	 * Execute the given operation in a clustered fashion.
	 */
	public <T> List<T> executeClustered(Iterable<URI> uris,
			Function1<? super LoadResult, ? extends T> operation) {
		int loadedURIsCount = 0;
		Set<URI> sourceLevelURIs = new HashSet<>();
		List<LoadResult> resources = new ArrayList<>();
		List<T> result = new ArrayList<>();
		Iterator<URI> iter = uris.iterator();
		while (iter.hasNext()) {
			URI uri = iter.next();
			XtextResourceSet resourceSet = context.getResourceSet();
			if (!context.getClusteringPolicy().continueProcessing(resourceSet, uri, loadedURIsCount)) {
				result.addAll(ListExtensions.map(resources, operation::apply));
				this.clearResourceSet();
				resources.clear();
				loadedURIsCount = 0;
			}
			loadedURIsCount++;
			if (this.isSource(uri)) {
				sourceLevelURIs.add(uri);
				Resource existingResource = resourceSet.getResource(uri, false);
				if (existingResource instanceof StorageAwareResource) {
					if (((StorageAwareResource) existingResource).isLoadedFromStorage()) {
						existingResource.unload();
					}
				}
				SourceLevelURIsAdapter.setSourceLevelUrisWithoutCopy(resourceSet, sourceLevelURIs);
			}
			resources.add(loadResource(resourceSet, uri));
		}
		result.addAll(ListExtensions.map(resources, operation::apply));
		return result;
	}

	/** Actually loads a resource. */
	protected LoadResult loadResource(ResourceSet resourceSet, URI uri) {
		try {
			Resource resource = resourceSet.getResource(uri, true);
			return new LoadResult(resource);
		} catch (Throwable th) {
			return new LoadResult(uri, th);
		}
	}

	/**
	 * Return true if the given uri must be loaded from source.
	 */
	protected boolean isSource(URI uri) {
		IResourceServiceProvider provider = context.getResourceServiceProvider(uri);
		return (provider instanceof IResourceServiceProviderExtension
				&& ((IResourceServiceProviderExtension) provider).isSource(uri));
	}

	/**
	 * Remove all resoures from the resource set without delivering notifications.
	 */
	protected void clearResourceSet() {
		XtextResourceSet resourceSet = context.getResourceSet();
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
	}

}
