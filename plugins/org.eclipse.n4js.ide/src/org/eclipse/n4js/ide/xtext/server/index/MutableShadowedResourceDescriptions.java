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
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IShadowedResourceDescriptions;

import com.google.common.collect.ImmutableSet;

/**
 * An implementation of {@link IShadowedResourceDescriptions} that will work with an immutable parent and allows to work
 * on the given shadowing, mutable descriptions.
 */
public class MutableShadowedResourceDescriptions extends AbstractShadowedResourceDescriptions
		implements MutableResourceDescriptions {

	private final ExtendedResourceDescriptionsData localData;
	private final Set<URI> removedResources;

	/**
	 * Constructor. The mutating operations will happen directly on the given local data.
	 */
	public MutableShadowedResourceDescriptions(ExtendedResourceDescriptionsData localData,
			ImmutableResourceDescriptions parent) {
		this(localData, parent, new HashSet<>());
	}

	/**
	 * Constructor. The mutating operations will happen directly on the given local data.
	 */
	MutableShadowedResourceDescriptions(ExtendedResourceDescriptionsData localData,
			ImmutableResourceDescriptions parent, Set<URI> removedResources) {
		super(parent);
		this.localData = localData;
		this.removedResources = removedResources;
	}

	@Override
	public IResourceDescription add(IResourceDescription newDescription) {
		removedResources.remove(newDescription.getURI());
		return localData.add(newDescription);
	}

	@Override
	public IResourceDescription remove(URI uri) {
		removedResources.add(uri);
		return localData.remove(uri);
	}

	/**
	 * Create a shallow copy of this index that is effectively an index change. Use with caution since subsequent
	 * changes to either this or the returned instance will be effective on both instances.
	 */
	public MutableShadowedResourceDescriptions shallowChangeParent(ImmutableResourceDescriptions parent) {
		return new MutableShadowedResourceDescriptions(localData.copy(), parent, removedResources);
	}

	/**
	 * Drop the shadowing information that is currently present for the given URI.
	 */
	public void dropShadowingInformation(URI uri) {
		localData.remove(uri);
		removedResources.remove(uri);
	}

	@Override
	public void clear() {
		localData.clear();
		removedResources.clear();
	}

	@Override
	IResourceDescriptions getLocalData() {
		return localData;
	}

	@Override
	Set<URI> getRemovedResources() {
		return removedResources;
	}

	@Override
	public ImmutableShadowedResourceDescriptions snapshot() {
		return new ImmutableShadowedResourceDescriptions(localData.snapshot(), ImmutableSet.copyOf(removedResources),
				getParentData());
	}

}
