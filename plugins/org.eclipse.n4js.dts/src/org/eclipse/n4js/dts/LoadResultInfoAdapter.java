/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;

/**
 * This adapter holds information about nested resources that need to be created and loaded when loading this resource.
 */
public class LoadResultInfoAdapter implements ILoadResultInfoAdapter {

	/**
	 * Returns an already installed {@link LoadResultInfoAdapter} or the newly installed {@link LoadResultInfoAdapter}
	 * on the given resource.
	 */
	public static LoadResultInfoAdapter getOrInstall(Resource resource) {
		LoadResultInfoAdapter adapter = (LoadResultInfoAdapter) ILoadResultInfoAdapter.get(resource);
		if (adapter != null) {
			return adapter;
		}
		adapter = new LoadResultInfoAdapter();
		resource.eAdapters().add(adapter);
		return adapter;
	}

	final Map<URI, NestedResourceAdapter> nestedResources = new LinkedHashMap<>();

	@Override
	public void notifyChanged(Notification notification) {
	}

	@Override
	public Notifier getTarget() {
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return false;
	}

	/** Adds the given {@link NestedResourceAdapter} to this adapter */
	public void addNestedResource(URI uri, NestedResourceAdapter nra) {
		nestedResources.put(uri, nra);
	}

	@Override
	public Collection<URI> getNewUris() {
		return nestedResources.keySet();
	}

	/** Returns the {@link NestedResourceAdapter} for the given {@link URI} or null */
	public NestedResourceAdapter getNestedResourceAdapter(URI uri) {
		return nestedResources.get(uri);
	}

	@Override
	public void ensureNestedResourcesExist(Resource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			// happens during testing
			return;
		}
		for (URI uri : nestedResources.keySet()) {
			Resource nestedResource = resourceSet.getResource(uri, false);
			if (nestedResource == null) {
				nestedResource = resourceSet.createResource(uri);
			}
			NestedResourceAdapter.update(nestedResource, nestedResources.get(uri));
		}
	}

}
