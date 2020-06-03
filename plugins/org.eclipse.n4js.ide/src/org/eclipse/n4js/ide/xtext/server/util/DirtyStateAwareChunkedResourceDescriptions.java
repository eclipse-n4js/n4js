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
package org.eclipse.n4js.ide.xtext.server.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.Iterables;

/**
 *
 */
public class DirtyStateAwareChunkedResourceDescriptions extends ChunkedResourceDescriptions {

	protected final ResourceDescriptionsData dirtyState;

	public DirtyStateAwareChunkedResourceDescriptions(ConcurrentHashMap<String, ResourceDescriptionsData> initialData,
			ResourceSet resourceSet, ResourceDescriptionsData dirtyState) {
		super(initialData, resourceSet);
		this.chunk2resourceDescriptions = initialData; // avoid creation of a copy!
		this.dirtyState = dirtyState;
	}

	@Override
	@SuppressWarnings("hiding")
	public ChunkedResourceDescriptions createShallowCopyWith(ResourceSet resourceSet) {
		return new DirtyStateAwareChunkedResourceDescriptions(chunk2resourceDescriptions, resourceSet,
				dirtyState);
	}

	@Override
	public boolean isEmpty() {
		return dirtyState.isEmpty() && super.isEmpty();
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		IResourceDescription shadowingDesc = dirtyState.getResourceDescription(uri);
		if (shadowingDesc == null) {
			shadowingDesc = super.getResourceDescription(uri);
		}
		return shadowingDesc;
	}

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		return ShadowingResourceDescriptionsData.concatResourceDescriptionsWithShadowing(
				dirtyState.getAllURIs(),
				dirtyState.getAllResourceDescriptions(),
				super.getAllResourceDescriptions());
	}

	@Override
	public ResourceDescriptionsData getContainer(String containerHandle) {
		ResourceDescriptionsData data = super.getContainer(containerHandle);
		return new ShadowingResourceDescriptionsData(dirtyState, data);
	}

	@Override
	public ResourceDescriptionsData getContainer(URI uri) {
		ResourceDescriptionsData data = super.getContainer(uri);
		return new ShadowingResourceDescriptionsData(dirtyState, data);
	}

	/**
	 * Implementation note: by overriding this method we cover all of the following methods:
	 * <ul>
	 * <li>{@link #getExportedObjects()}
	 * <li>{@link #getExportedObjects(EClass, QualifiedName, boolean)}
	 * <li>{@link #getExportedObjectsByType(EClass)}
	 * <li>{@link #getExportedObjectsByObject(EObject)}
	 * </ul>
	 */
	@Override
	protected Iterable<? extends ResourceDescriptionsData> getSelectables() {
		return Iterables.transform(chunk2resourceDescriptions.keySet(), this::getContainer);
	}

	@Override
	public ResourceDescriptionsData setContainer(String name, ResourceDescriptionsData descriptions) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResourceDescriptionsData removeContainer(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		throw new UnsupportedOperationException();
	}
}
