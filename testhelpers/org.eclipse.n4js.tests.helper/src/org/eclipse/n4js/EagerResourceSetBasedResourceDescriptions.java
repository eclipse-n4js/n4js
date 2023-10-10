/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * We had to copy the {@link org.eclipse.xtext.resource.impl.EagerResourceSetBasedResourceDescriptions} that are part of
 * the framework because we have to inherit from {@link ResourceSetBasedResourceDescriptions} (see
 * {@link ResourceSetBasedAllContainersStateProvider}).
 */
public class EagerResourceSetBasedResourceDescriptions extends ResourceSetBasedResourceDescriptions {

	static class Descriptions extends AdapterImpl {
		Map<URI, IResourceDescription> map = Maps.newHashMap();
	}

	@Inject
	private IResourceServiceProvider.Registry registry;
	
	public ResourceSet resourceSet;

	private Descriptions getDescriptions(ResourceSet rs) {
		for (Adapter a : rs.eAdapters()) {
			if (a instanceof Descriptions) {
				return (Descriptions) a;
			}
		}
		return null;
	}

	private Map<URI, IResourceDescription> getDescriptionsMap() {
		Descriptions descriptions = getDescriptions(getResourceSet());
		if (descriptions == null) {
			descriptions = new Descriptions();
			getResourceSet().eAdapters().add(descriptions);
			List<Resource> list = new ArrayList<>(getResourceSet().getResources());
			for (Resource resource : list) {
				IResourceDescription description = computeResourceDescription(resource.getURI());
				if (description != null) {
					descriptions.map.put(resource.getURI(), description);
				}
			}
		}
		return descriptions.map;
	}

	private IResourceDescription computeResourceDescription(URI uri) {
		Resource resource = resourceSet.getResource(uri, false);
		if (resource == null)
			return null;
		IResourceServiceProvider resourceServiceProvider = registry.getResourceServiceProvider(uri);
		if (resourceServiceProvider == null)
			return null;
		IResourceDescription.Manager manager = resourceServiceProvider.getResourceDescriptionManager();
		if (manager == null)
			return null;
		return manager.getResourceDescription(resource);
	}

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		return Collections.unmodifiableCollection(getDescriptionsMap().values());
	}

	@Override
	public void setRegistry(IResourceServiceProvider.Registry registry) {
		this.registry = registry;
	}

	@Override
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	@Override
	protected Iterable<? extends ISelectable> getSelectables() {
		return getAllResourceDescriptions();
	}

	@Override
	public boolean isEmpty() {
		return getDescriptionsMap().isEmpty();
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected boolean hasDescription(URI uri) {
		return getDescriptionsMap().containsKey(uri);
	}

	@Override
	public void setContext(Notifier ctx) {
		this.resourceSet = EcoreUtil2.getResourceSet(ctx);
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + "\n  " + Joiner.on("\n  ").join(getAllResourceDescriptions()) + "\n]";
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		return getDescriptionsMap().get(uri);
	}
}
