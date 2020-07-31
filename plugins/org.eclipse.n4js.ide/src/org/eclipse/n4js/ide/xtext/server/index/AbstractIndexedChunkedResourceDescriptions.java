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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

/**
 * Abstract base class that introduces an additional index for the {@link ChunkedResourceDescriptions} to avoid linear
 * scans on each attempt to find an {@link EObjectDescription}.
 */
abstract class AbstractIndexedChunkedResourceDescriptions extends ChunkedResourceDescriptions {

	@SuppressWarnings("unchecked")
	static Map<String, ResourceDescriptionsData> upcast(Map<String, ImmutableResourceDescriptionsData> map) {
		return (Map<String, ResourceDescriptionsData>) (Map<String, ?>) map;
	}

	@SuppressWarnings("unchecked")
	static Map<String, ImmutableResourceDescriptionsData> downcast(Map<String, ResourceDescriptionsData> map) {
		return (Map<String, ImmutableResourceDescriptionsData>) (Map<String, ?>) map;
	}

	AbstractIndexedChunkedResourceDescriptions() {
		super();
	}

	AbstractIndexedChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initialData) {
		super(upcast(initialData));
	}

	abstract ResourceDescriptionsData getIndexedDelegate();

	@Override
	public Collection<IResourceDescription> getAllResourceDescriptions() {
		return (Collection<IResourceDescription>) getIndexedDelegate().getAllResourceDescriptions();
	}

	@Override
	protected Collection<IResourceDescription> getSelectables() {
		return getAllResourceDescriptions();
	}

	@Override
	public ImmutableResourceDescriptionsData getContainer(String containerHandle) {
		return (ImmutableResourceDescriptionsData) super.getContainer(containerHandle);
	}

	@Override
	public ImmutableResourceDescriptionsData getContainer(URI uri) {
		return (ImmutableResourceDescriptionsData) super.getContainer(uri);
	}

	@Override
	public ImmutableResourceDescriptionsData removeContainer(String name) {
		return (ImmutableResourceDescriptionsData) super.removeContainer(name);
	}

	@Override
	public ImmutableResourceDescriptionsData setContainer(String name, ResourceDescriptionsData descriptions) {
		return (ImmutableResourceDescriptionsData) super.setContainer(name, descriptions);
	}

	@Override
	public boolean isEmpty() {
		return getIndexedDelegate().isEmpty();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return getIndexedDelegate().getExportedObjects();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		return getIndexedDelegate().getExportedObjectsByType(type);
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		return getIndexedDelegate().getResourceDescription(uri);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName qualifiedName,
			boolean ignoreCase) {
		return getIndexedDelegate().getExportedObjects(type, qualifiedName, ignoreCase);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		return getIndexedDelegate().getExportedObjectsByObject(object);
	}

	/**
	 * Return all the URIs in this index.
	 */
	public Set<URI> getAllURIs() {
		return getIndexedDelegate().getAllURIs();
	}

	/**
	 * @deprecated Serialization / deserialization is not supported.
	 */
	@Deprecated
	@Override
	public void readExternal(ObjectInput in) {
		throw new UnsupportedOperationException("Unexpected deserialization attempt");
	}

	/**
	 * @deprecated Serialization / deserialization is not supported.
	 */
	@Deprecated
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		throw new UnsupportedOperationException("Unexpected serialization attempt");
	}
}
