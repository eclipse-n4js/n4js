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

import java.util.HashSet;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableSet;

/**
 * Immutable shadowed resource descriptions. Combines two immutable index instances to a single instance.
 */
public class ImmutableShadowedResourceDescriptions extends AbstractShadowedResourceDescriptions
		implements ImmutableResourceDescriptions {

	private final ImmutableResourceDescriptionsData localData;
	private final ImmutableSet<URI> removedResources;

	/**
	 * Constructor.
	 */
	public ImmutableShadowedResourceDescriptions(ImmutableResourceDescriptionsData localData,
			ImmutableSet<URI> removedResources, ImmutableResourceDescriptions parent) {
		super(parent);
		this.localData = localData;
		this.removedResources = removedResources;
	}

	/**
	 * Constructor.
	 */
	public ImmutableShadowedResourceDescriptions(ImmutableResourceDescriptionsData localData,
			ImmutableResourceDescriptions parent) {
		super(parent);
		this.localData = localData;
		this.removedResources = ImmutableSet.of();
	}

	/**
	 * Create a mutable copy of this index state.
	 */
	public MutableShadowedResourceDescriptions builder() {
		return new MutableShadowedResourceDescriptions(localData.builder(), getParentData(),
				new HashSet<>(removedResources));
	}

	@Override
	ImmutableResourceDescriptionsData getLocalData() {
		return localData;
	}

	@Override
	ImmutableSet<URI> getRemovedResources() {
		return removedResources;
	}

	/**
	 * Creates a new instance with the same shadowing information but a different parent.
	 */
	public ImmutableShadowedResourceDescriptions withParent(ImmutableChunkedResourceDescriptions newParent) {
		return new ImmutableShadowedResourceDescriptions(localData, removedResources, newParent);
	}

}
