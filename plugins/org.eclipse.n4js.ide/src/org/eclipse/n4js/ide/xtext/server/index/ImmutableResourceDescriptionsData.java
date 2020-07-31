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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Immutable specialization of {@link ResourceDescriptionsData}.
 */
public final class ImmutableResourceDescriptionsData extends ResourceDescriptionsData
		implements ImmutableResourceDescriptions {

	/**
	 * Offers access to an empty resource descriptions data.
	 */
	public static ImmutableResourceDescriptionsData empty() {
		return new ImmutableResourceDescriptionsData(ImmutableMap.of(), ImmutableMap.of());
	}

	/**
	 * Create an immutable resource descriptions data based on the given descriptions.
	 */
	public static ImmutableResourceDescriptionsData from(List<IResourceDescription> descriptions) {
		// If it turns out to be a bottle-neck, we can optimize this.
		return new ExtendedResourceDescriptionsData(descriptions).snapshot();
	}

	private final ImmutableMap<URI, IResourceDescription> accessibleResourceDescriptionMap;

	/**
	 * Constructor.
	 */
	ImmutableResourceDescriptionsData(Map<URI, IResourceDescription> resourceDescriptions,
			Map<QualifiedName, Object> lookupMap) {
		this(ImmutableMap.copyOf(resourceDescriptions), copyLookupMap(lookupMap));
	}

	private ImmutableResourceDescriptionsData(ImmutableMap<URI, IResourceDescription> resourceDescriptions,
			Map<QualifiedName, Object> lookupMap) {
		super(resourceDescriptions, copyLookupMap(lookupMap));
		this.accessibleResourceDescriptionMap = resourceDescriptions;
	}

	@Override
	public ImmutableResourceDescriptionsData copy() {
		return this;
	}

	/**
	 * Create a mutable copy of this instance that can be worked with during a build.
	 */
	public ExtendedResourceDescriptionsData builder() {
		return new ExtendedResourceDescriptionsData(
				new LinkedHashMap<>(accessibleResourceDescriptionMap),
				super.copyLookupMap());
	}

	/**
	 * @deprecated this operation is not permitted.
	 */
	@Deprecated
	@Override
	public void addDescription(URI uri, IResourceDescription newDescription) {
		throw new UnsupportedOperationException("Cannot add descriptions to an immutable resource descriptions data");
	}

	/**
	 * @deprecated this operation is not permitted.
	 */
	@Deprecated
	@Override
	public void removeDescription(URI uri) {
		throw new UnsupportedOperationException(
				"Cannot remove descriptions from an immutable resource descriptions data");
	}

	/**
	 * @deprecated this operation is not permitted.
	 */
	@Deprecated
	@Override
	public void register(Delta delta) {
		throw new UnsupportedOperationException("Cannot register a delta at an immutable resource descriptions data");
	}

	/**
	 * Follows the semantics of {@link ResourceDescriptionsData#copy} but creates an immutable lookup map.
	 */
	private static Map<QualifiedName, Object> copyLookupMap(Map<QualifiedName, Object> lookupMap) {
		if (lookupMap instanceof ImmutableMap<?, ?>) {
			return lookupMap;
		}
		ImmutableMap.Builder<QualifiedName, Object> copyInto = ImmutableMap.builder();
		lookupMap.forEach((name, value) -> {
			if (value instanceof Set<?>) {
				copyInto.put(name, ImmutableSet.copyOf((Collection<?>) value));
			} else {
				copyInto.put(name, value);
			}
		});
		return copyInto.build();
	}

	/**
	 * @deprecated this operation is not expected to be ever called.
	 */
	@Deprecated
	@Override
	protected Map<QualifiedName, Object> copyLookupMap() {
		throw new UnsupportedOperationException("Should never be called.");
	}

	/**
	 * @deprecated this operation is not expected to be ever called.
	 */
	@Deprecated
	@Override
	protected void registerDescription(IResourceDescription description, Map<QualifiedName, Object> target) {
		throw new UnsupportedOperationException("Should never be called.");
	}

	/*
	 * Overridden to specialize the return type. We know that we will always return an immutable collection.
	 */
	@Override
	public ImmutableCollection<IResourceDescription> getAllResourceDescriptions() {
		return (ImmutableCollection<IResourceDescription>) super.getAllResourceDescriptions();
	}

	/*
	 * Overridden to specialize the return type. We know that we will always return an immutable collection.
	 */
	@Override
	protected ImmutableCollection<IResourceDescription> getSelectables() {
		return getAllResourceDescriptions();
	}

	/*
	 * Overridden to specialize the return type. We know that we will always return an immutable set.
	 */
	@Override
	public ImmutableSet<URI> getAllURIs() {
		return (ImmutableSet<URI>) super.getAllURIs();
	}

}
