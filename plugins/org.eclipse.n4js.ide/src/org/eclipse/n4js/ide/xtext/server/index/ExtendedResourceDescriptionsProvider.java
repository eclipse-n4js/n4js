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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

/**
 *
 */
public class ExtendedResourceDescriptionsProvider extends ResourceDescriptionsProvider {

	@Override
	public IResourceDescriptions getResourceDescriptions(ResourceSet resourceSet) {
		String flag = getFlagFromLoadOptions(resourceSet);
		IResourceDescriptions result;
		if (NAMED_BUILDER_SCOPE.equals(flag)) {
			result = createBuilderScopeResourceDescriptions();
		} else if (LIVE_SCOPE.equals(flag)) {
			result = createLiveScopeResourceDescriptions();
		} else if (PERSISTED_DESCRIPTIONS.equals(flag)) {
			result = createPersistedResourceDescriptions();
		} else {
			result = ChunkedResourceDescriptions.findInEmfObject(resourceSet);
			if (result == null) {
				result = getResourceDescriptionsExtended(resourceSet);
			}
			if (result == null) {
				result = createResourceDescriptions();
			}
		}
		if (result instanceof IResourceDescriptions.IContextAware) {
			((IResourceDescriptions.IContextAware) result).setContext(resourceSet);
		}
		return result;
	}

	@Override
	public ImmutableResourceDescriptions createPersistedResourceDescriptions() {
		return (ImmutableResourceDescriptions) super.createPersistedResourceDescriptions();
	}

	@Override
	public ImmutableResourceDescriptions createResourceDescriptions() {
		return (ImmutableResourceDescriptions) super.createResourceDescriptions();
	}

	IResourceDescriptions getResourceDescriptionsExtended(ResourceSet resourceSet) {
		for (Adapter a : resourceSet.eAdapters()) {
			if (a instanceof ResourceSetAdapter) {
				return ((ResourceSetAdapter) a).getResourceDescriptions();
			}
		}
		return null;
	}

	private String getFlagFromLoadOptions(ResourceSet resourceSet) {
		Map<Object, Object> loadOptions = resourceSet.getLoadOptions();
		String[] mutualExclusiveFlags = new String[] { NAMED_BUILDER_SCOPE, LIVE_SCOPE, PERSISTED_DESCRIPTIONS };
		String result = null;
		for (int i = 0; i < mutualExclusiveFlags.length; i++) {
			String candidate = mutualExclusiveFlags[i];
			if (loadOptions.containsKey(candidate)) {
				if (result == null) {
					result = candidate;
				} else {
					String msg = "Ambiguous scope for the resource set. Can't combine " + result + " and " + candidate;
					throw new IllegalStateException(msg);
				}
			}
		}
		return result;
	}

	/**
	 * Attach the given resources descriptions to the resource set such that it can be obtained via
	 * {@link #getResourceDescriptions(ResourceSet)}.
	 */
	public void attachTo(IResourceDescriptions resourceDescriptions, ResourceSet resourceSet) {
		if (getResourceDescriptionsExtended(resourceSet) != null) {
			throw new IllegalStateException("Index is already installed.");
		}
		ResourceSetAdapter resourceSetAdapter = new ResourceSetAdapter(
				new ForwardingResourceDescriptions(resourceDescriptions, resourceSet));
		resourceSet.eAdapters().add(resourceSetAdapter);
	}

	public void replace(IResourceDescriptions resourceDescriptions, ResourceSet resourceSet) {
		List<Adapter> adapters = resourceSet.eAdapters();
		for (int i = 0; i < adapters.size(); i++) {
			Adapter a = adapters.get(i);
			if (a instanceof ResourceSetAdapter) {
				ResourceSetAdapter newAdapter = new ResourceSetAdapter(
						new ForwardingResourceDescriptions(resourceDescriptions, resourceSet));
				adapters.set(i, newAdapter);
				return;
			}
		}
		ResourceSetAdapter resourceSetAdapter = new ResourceSetAdapter(
				new ForwardingResourceDescriptions(resourceDescriptions, resourceSet));
		resourceSet.eAdapters().add(resourceSetAdapter);
	}

	static class ResourceSetAdapter extends AdapterImpl {

		private final IResourceDescriptions index;

		ResourceSetAdapter(IResourceDescriptions index) {
			this.index = index;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == ResourceSetAdapter.class;
		}

		public IResourceDescriptions getResourceDescriptions() {
			return index;
		}
	}
}
