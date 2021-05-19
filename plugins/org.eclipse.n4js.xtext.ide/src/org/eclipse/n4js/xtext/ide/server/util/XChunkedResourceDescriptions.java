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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions.IResourceSetAware;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableSet;

/**
 * Same as {@link ChunkedResourceDescriptions}, but adds some missing methods.
 */
public class XChunkedResourceDescriptions extends ChunkedResourceDescriptions implements IResourceSetAware {

	public XChunkedResourceDescriptions() {
	}

	public XChunkedResourceDescriptions(Map<String, ResourceDescriptionsData> initialData) {
		super(initialData);
	}

	public XChunkedResourceDescriptions(Map<String, ResourceDescriptionsData> initialData, ResourceSet resourceSet) {
		super(initialData, resourceSet);
	}

	@Override
	public void setResourceSet(ResourceSet resourceSet) {
		super.setResourceSet(resourceSet);
	}

	public Set<String> getContainerHandles() {
		return ImmutableSet.copyOf(chunk2resourceDescriptions.keySet());
	}

	public ResourceDescriptionsData getOrCreateContainer(String containerHandle) {
		ResourceDescriptionsData container = getContainer(containerHandle);
		if (container == null) {
			container = new ResourceDescriptionsData(Collections.emptyList());
			setContainer(containerHandle, container);
		}
		return container;
	}

	public void addDescription(String containerHandle, IResourceDescription desc) {
		getOrCreateContainer(containerHandle).addDescription(desc.getURI(), desc);
	}

	/** @see ResourceDescriptionsData#register(IResourceDescription.Delta) */
	public void register(String containerHandle, IResourceDescription.Delta delta) {
		getOrCreateContainer(containerHandle).register(delta);
	}

	public XChunkedResourceDescriptions createDeepCopy() {
		XChunkedResourceDescriptions result = new XChunkedResourceDescriptions(this.chunk2resourceDescriptions);
		result.chunk2resourceDescriptions.replaceAll((k, v) -> v.copy());
		return result;
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName qualifiedName,
			boolean ignoreCase) {
		return super.getExportedObjects(type, qualifiedName, ignoreCase);
	}
}
