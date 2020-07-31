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

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * A specialization of the {@link ChunkedResourceDescriptions} that is immutable and thereby thread-safe. Accessing
 * {@link #getExportedObjects(EClass, QualifiedName, boolean) objects by name} is very efficient.
 */
public final class ImmutableChunkedResourceDescriptions extends AbstractIndexedChunkedResourceDescriptions
		implements ImmutableResourceDescriptions {

	/**
	 * An empty index.
	 */
	public static ImmutableChunkedResourceDescriptions empty() {
		return new ImmutableChunkedResourceDescriptions(ImmutableMap.of(), ImmutableResourceDescriptionsData.empty());
	}

	private final ImmutableResourceDescriptionsData indexedDelegate;

	/**
	 * Constructor.
	 */
	ImmutableChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial,
			ImmutableResourceDescriptionsData indexedDelegate) {
		super(initial);
		this.indexedDelegate = indexedDelegate;
	}

	/**
	 * Constructor.
	 */
	ImmutableChunkedResourceDescriptions(Map<String, ImmutableResourceDescriptionsData> initial,
			ImmutableResourceDescriptionsData indexedDelegate,
			ResourceSet context) {
		this(initial, indexedDelegate);
		setResourceSet(context);
	}

	@Override
	ImmutableResourceDescriptionsData getIndexedDelegate() {
		return indexedDelegate;
	}

	@Override
	public ImmutableChunkedResourceDescriptions createShallowCopyWith(
			@SuppressWarnings("hiding") ResourceSet resourceSet) {
		return new ImmutableChunkedResourceDescriptions(downcast(chunk2resourceDescriptions), indexedDelegate,
				resourceSet);
	}

	/**
	 * Create a mutable instance starting with the entries of this instance. The new instance is not attached to a
	 * resource set.
	 */
	public ExtendedChunkedResourceDescriptions builder() {
		return new ExtendedChunkedResourceDescriptions(downcast(chunk2resourceDescriptions), indexedDelegate.builder());
	}

	/**
	 * Create a mutable instance starting with the entries of this instance.
	 */
	public ExtendedChunkedResourceDescriptions builder(@SuppressWarnings("hiding") ResourceSet resourceSet) {
		return new ExtendedChunkedResourceDescriptions(downcast(chunk2resourceDescriptions), indexedDelegate.builder(),
				resourceSet);
	}

	/**
	 * @deprecated Cannot set a container on an ImmutableChunkedResourceDescriptions
	 */
	@Deprecated
	@Override
	public ImmutableResourceDescriptionsData setContainer(String name, ResourceDescriptionsData descriptions) {
		throw new UnsupportedOperationException("Cannot set a container on an immutable instance");
	}

	/**
	 * @deprecated Cannot remove a container from an immutable instance
	 */
	@Deprecated
	@Override
	public ImmutableResourceDescriptionsData removeContainer(String name) {
		throw new UnsupportedOperationException("Cannot remove a container from an immutable instance");
	}

	@Override
	public ImmutableSet<URI> getAllURIs() {
		return getIndexedDelegate().getAllURIs();
	}

	@Override
	public ImmutableCollection<IResourceDescription> getAllResourceDescriptions() {
		return getIndexedDelegate().getAllResourceDescriptions();
	}

	@Override
	protected ImmutableCollection<IResourceDescription> getSelectables() {
		return getAllResourceDescriptions();
	}

}
