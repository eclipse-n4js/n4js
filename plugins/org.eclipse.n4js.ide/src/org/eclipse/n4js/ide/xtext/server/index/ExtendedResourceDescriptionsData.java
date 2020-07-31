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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

/**
 * Non-thread safe specialization of {@link ResourceDescriptionsData} that allows to capture an immutable snapshot via
 * {@link #snapshot()}.
 */
public class ExtendedResourceDescriptionsData extends ResourceDescriptionsData implements MutableResourceDescriptions {

	/**
	 * Accessible pointer to the same map as the super class holds.
	 */
	private final Map<URI, IResourceDescription> accessibleResourceDescriptionMap;
	private final Map<QualifiedName, Object> accessibleLookupMap;

	/**
	 * Creates and empty {@link ResourceDescriptionsData}.
	 */
	public ExtendedResourceDescriptionsData() {
		this(new LinkedHashMap<>(), new LinkedHashMap<>());
	}

	/**
	 * Create a new instance. The given maps are <em>not</em> copied.
	 */
	protected ExtendedResourceDescriptionsData(Map<URI, IResourceDescription> resourceDescriptionMap,
			Map<QualifiedName, Object> lookupMap) {
		super(resourceDescriptionMap, lookupMap);
		this.accessibleResourceDescriptionMap = resourceDescriptionMap;
		this.accessibleLookupMap = lookupMap;
	}

	/**
	 * Create a new instance and initialize it with the list of descriptions.
	 */
	public ExtendedResourceDescriptionsData(List<IResourceDescription> initial) {
		this();
		for (IResourceDescription description : initial) {
			addDescription(description.getURI(), description);
		}
	}

	/**
	 * Create an immutable snapshot of this index representation.
	 */
	@Override
	public ImmutableResourceDescriptionsData snapshot() {
		return new ImmutableResourceDescriptionsData(accessibleResourceDescriptionMap, accessibleLookupMap);
	}

	@Override
	public ExtendedResourceDescriptionsData copy() {
		return new ExtendedResourceDescriptionsData(
				copyResourceDescriptionsMap(),
				copyLookupMap());
	}

	Map<URI, IResourceDescription> copyResourceDescriptionsMap() {
		return new LinkedHashMap<>(accessibleResourceDescriptionMap);
	}

	@Override
	public void addDescription(URI uri, IResourceDescription newDescription) {
		// Implements a small optimization in case the new description is already kept.
		if (getResourceDescription(uri) != newDescription) {
			super.addDescription(uri, newDescription);
		}
	}

	@Override
	public IResourceDescription add(IResourceDescription newDescription) {
		URI uri = newDescription.getURI();
		IResourceDescription result = getResourceDescription(uri);
		if (result != newDescription) {
			super.addDescription(uri, newDescription);
		}
		return result;
	}

	@Override
	public IResourceDescription remove(URI uri) {
		IResourceDescription result = getResourceDescription(uri);
		if (result != null) {
			removeDescription(uri);
		}
		return result;
	}

	@Override
	public void clear() {
		accessibleLookupMap.clear();
		accessibleResourceDescriptionMap.clear();
	}

}
