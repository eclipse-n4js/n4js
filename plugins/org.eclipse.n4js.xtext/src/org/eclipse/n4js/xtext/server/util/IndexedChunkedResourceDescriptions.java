/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.server.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

/**
 * TODO implement "builder pattern" where we start with a copy and create snapshots from there.
 */
public class IndexedChunkedResourceDescriptions extends ChunkedResourceDescriptions {

	private final ResourceDescriptionsData indexedDelegate;

	/**
	 * Constructor.
	 */
	public IndexedChunkedResourceDescriptions() {
		indexedDelegate = new ResourceDescriptionsData(Collections.emptyList());
	}

	private IndexedChunkedResourceDescriptions(ConcurrentHashMap<String, ResourceDescriptionsData> initial,
			ResourceDescriptionsData indexedDelegate) {
		super(initial);
		this.indexedDelegate = indexedDelegate.copy();
	}

	@Override
	public IndexedChunkedResourceDescriptions createShallowCopyWith(
			@SuppressWarnings("hiding") ResourceSet resourceSet) {
		if (this.resourceSet == resourceSet) {
			return this;
		}
		IndexedChunkedResourceDescriptions result = new IndexedChunkedResourceDescriptions(chunk2resourceDescriptions,
				indexedDelegate);
		result.setResourceSet(resourceSet);
		return result;
	}

	@Override
	public Collection<IResourceDescription> getAllResourceDescriptions() {
		return (Collection<IResourceDescription>) indexedDelegate.getAllResourceDescriptions();
	}

	@Override
	public ResourceDescriptionsData setContainer(String name, ResourceDescriptionsData descriptions) {
		ResourceDescriptionsData result = super.setContainer(name, descriptions);
		if (result != null) {
			for (IResourceDescription removed : result.getAllResourceDescriptions()) {
				URI uri = removed.getURI();
				IResourceDescription inserted = descriptions.getResourceDescription(uri);
				if (inserted != removed) {
					indexedDelegate.removeDescription(uri);
					if (inserted != null) {
						indexedDelegate.addDescription(uri, inserted);
					}
				}
			}
			for (IResourceDescription inserted : descriptions.getAllResourceDescriptions()) {
				URI uri = inserted.getURI();
				if (result.getResourceDescription(uri) == null) {
					indexedDelegate.addDescription(uri, inserted);
				}
			}
		} else {
			for (IResourceDescription inserted : descriptions.getAllResourceDescriptions()) {
				indexedDelegate.addDescription(inserted.getURI(), inserted);
			}
		}
		return result;
	}

	@Override
	public ResourceDescriptionsData removeContainer(String name) {
		ResourceDescriptionsData result = super.removeContainer(name);
		if (result != null) {
			for (IResourceDescription prev : result.getAllResourceDescriptions()) {
				indexedDelegate.removeDescription(prev.getURI());
			}
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return indexedDelegate.isEmpty();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return indexedDelegate.getExportedObjects();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		return indexedDelegate.getExportedObjectsByType(type);
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		return indexedDelegate.getResourceDescription(uri);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName qualifiedName,
			boolean ignoreCase) {
		return indexedDelegate.getExportedObjects(type, qualifiedName, ignoreCase);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		return indexedDelegate.getExportedObjectsByObject(object);
	}

	/**
	 * Return all the URIs in this index.
	 */
	public Set<URI> getAllURIs() {
		return indexedDelegate.getAllURIs();
	}

}
