/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions.IResourceSetAware;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableSet;

/**
 * Same as {@link ChunkedResourceDescriptions}, but adds some methods that require access to protected members of the
 * super class (and can therefore not be implemented outside), plus some convenience methods.
 */
public class XChunkedResourceDescriptions extends ChunkedResourceDescriptions implements IResourceSetAware {

	/** @see ChunkedResourceDescriptions#ChunkedResourceDescriptions() */
	public XChunkedResourceDescriptions() {
	}

	/** @see ChunkedResourceDescriptions#ChunkedResourceDescriptions(Map) */
	public XChunkedResourceDescriptions(Map<String, ResourceDescriptionsData> initialData) {
		super(initialData);
	}

	/** @see ChunkedResourceDescriptions#ChunkedResourceDescriptions(Map, ResourceSet) */
	public XChunkedResourceDescriptions(Map<String, ResourceDescriptionsData> initialData, ResourceSet resourceSet) {
		super(initialData, resourceSet);
	}

	@Override
	public void setResourceSet(ResourceSet resourceSet) {
		super.setResourceSet(resourceSet);
	}

	/** @return an immutable set of the handles of all containers. */
	public Set<String> getContainerHandles() {
		return ImmutableSet.copyOf(chunk2resourceDescriptions.keySet());
	}

	/** Returns the container for the given handle, creating an empty container if it does not exist. */
	public ResourceDescriptionsData getOrCreateContainer(String containerHandle) {
		ResourceDescriptionsData container = getContainer(containerHandle);
		if (container == null) {
			container = new ResourceDescriptionsData(Collections.emptyList());
			setContainer(containerHandle, container);
		}
		return container;
	}

	/** @see ResourceDescriptionsData#addDescription(URI, IResourceDescription) */
	public void addDescription(String containerHandle, IResourceDescription desc) {
		getOrCreateContainer(containerHandle).addDescription(desc.getURI(), desc);
	}

	/** @see ResourceDescriptionsData#register(IResourceDescription.Delta) */
	public void register(String containerHandle, IResourceDescription.Delta delta) {
		getOrCreateContainer(containerHandle).register(delta);
	}

	/**
	 * Creates a copy, also copying the {@link ResourceDescriptionsData} instances that represent the individual
	 * containers, but not their contained {@link IResourceDescription}s.
	 *
	 * @see ResourceDescriptionsData#copy()
	 */
	public XChunkedResourceDescriptions createDeepCopy() {
		XChunkedResourceDescriptions result = new XChunkedResourceDescriptions(this.chunk2resourceDescriptions);
		result.chunk2resourceDescriptions.replaceAll((k, v) -> v.copy());
		return result;
	}
}
