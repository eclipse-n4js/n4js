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
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;

/**
 *
 */
public class LoadResultInfoAdapter implements ILoadResultInfoAdapter {

	public static LoadResultInfoAdapter install(Resource resource) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAdapterForType(Object type) {
		// TODO Auto-generated method stub
		return false;
	}

	/**  */
	public void addNestedResource(URI uri, NestedResourceAdapter nra) {
		nestedResources.put(uri, nra);
	}

	@Override
	public Collection<URI> getNewUris() {
		return nestedResources.keySet();
	}

	@Override
	public void ensure(Resource resource) {
		for (URI uri : nestedResources.keySet()) {
			Resource nestedResource = resource.getResourceSet().getResource(uri, false);
			if (nestedResource == null) {
				nestedResource = resource.getResourceSet().createResource(uri);
			}
			NestedResourceAdapter.install(nestedResource, nestedResources.get(uri));
		}
	}

}
