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
package org.eclipse.n4js.ide.xtext.server.index;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.base.Preconditions;

/**
 *
 */
public class ExtendedChunkedResourceDescriptions extends AbstractIndexedChunkedResourceDescriptions {

	private final ExtendedResourceDescriptionsData indexedDelegate;

	/**
	 * Constructor.
	 */
	public ExtendedChunkedResourceDescriptions() {
		indexedDelegate = new ExtendedResourceDescriptionsData();
	}

	/**
	 * Constructor.
	 */
	public ExtendedChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial,
			ResourceSet context) {
		this(initial);
		setResourceSet(context);
	}

	/**
	 * Constructor.
	 */
	public ExtendedChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial) {
		super(initial);
		indexedDelegate = new ExtendedResourceDescriptionsData();
		for (ImmutableResourceDescriptionsData chunk : initial.values()) {
			for (IResourceDescription description : chunk.getAllResourceDescriptions()) {
				indexedDelegate.addDescription(description.getURI(), description);
			}
		}
	}

	/**
	 * Constructor.
	 */
	ExtendedChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial,
			ExtendedResourceDescriptionsData indexedDelegate) {
		super(initial);
		this.indexedDelegate = indexedDelegate;
	}

	/**
	 * Constructor.
	 */
	ExtendedChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial,
			ExtendedResourceDescriptionsData indexedDelegate,
			ResourceSet context) {
		super(initial);
		this.indexedDelegate = indexedDelegate;
		setResourceSet(context);
	}

	/**
	 * Create an immutable snapshot of this instance.
	 */
	public ImmutableChunkedResourceDescriptions snapshot() {
		return new ImmutableChunkedResourceDescriptions(downcast(chunk2resourceDescriptions),
				indexedDelegate.snapshot());
	}

	/**
	 * Create an immutable snapshot of this instance attached to the given resource set.
	 */
	public ImmutableChunkedResourceDescriptions snapshot(@SuppressWarnings("hiding") ResourceSet resourceSet) {
		return new ImmutableChunkedResourceDescriptions(downcast(chunk2resourceDescriptions),
				indexedDelegate.snapshot(), resourceSet);
	}

	@Override
	ExtendedResourceDescriptionsData getIndexedDelegate() {
		return indexedDelegate;
	}

	/**
	 * Returns all the known containers in this index.
	 */
	public Set<String> getAllContainers() {
		return Collections.unmodifiableSet(chunk2resourceDescriptions.keySet());
	}

	void clear() {
		chunk2resourceDescriptions.clear();
		indexedDelegate.clear();
	}

	@Override
	public ExtendedChunkedResourceDescriptions createShallowCopyWith(
			@SuppressWarnings("hiding") ResourceSet resourceSet) {
		return new ExtendedChunkedResourceDescriptions(downcast(chunk2resourceDescriptions), indexedDelegate.copy(),
				resourceSet);
	}

	/**
	 * @deprecated Clients should directly call {@link #setContainer(String, ImmutableResourceDescriptionsData)}.
	 */
	@Deprecated
	@Override
	public ImmutableResourceDescriptionsData setContainer(String name, ResourceDescriptionsData descriptions) {
		Preconditions.checkArgument(descriptions instanceof ImmutableResourceDescriptionsData);
		return setContainer(name, (ImmutableResourceDescriptionsData) descriptions);
	}

	/**
	 * Installs the given descriptions as the index chunk with the given name.
	 */
	public ImmutableResourceDescriptionsData setContainer(String name, ImmutableResourceDescriptionsData descriptions) {
		ImmutableResourceDescriptionsData result = super.setContainer(name, descriptions);
		if (result != null) {
			// TODO consider moving this to ExtendedResourceDescriptionsData as a merge or replace operation
			// It has the potential to be implemented more efficiently based on the maps.
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
	public ImmutableResourceDescriptionsData removeContainer(String name) {
		ImmutableResourceDescriptionsData result = super.removeContainer(name);
		if (result != null) {
			for (IResourceDescription prev : result.getAllResourceDescriptions()) {
				indexedDelegate.removeDescription(prev.getURI());
			}
		}
		return result;
	}

}
