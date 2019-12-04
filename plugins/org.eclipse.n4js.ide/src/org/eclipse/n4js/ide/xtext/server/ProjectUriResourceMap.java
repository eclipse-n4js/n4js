/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingMap;
import com.google.inject.Inject;

/**
 * A project specific map of resources per URI. It acts as a mere filter to the {@link WorkspaceUriResourceMap} such that
 * an invocation of {@link #clear()} does not wipe out the entire map contents but only the resources from the current
 * project.
 */
public class ProjectUriResourceMap extends ForwardingMap<URI, Resource> {

	private final WorkspaceUriResourceMap globalMap;

	private final Set<URI> localContents;

	/**
	 * Standard constructor
	 */
	@Inject
	public ProjectUriResourceMap(WorkspaceUriResourceMap globalMap) {
		this.globalMap = globalMap;
		this.localContents = new HashSet<>();
	}

	@Override
	protected Map<URI, Resource> delegate() {
		return globalMap;
	}

	@Override
	public void clear() {
		delegate().keySet().removeAll(localContents);
		localContents.clear();
	}

	@Override
	public Resource put(URI key, Resource value) {
		localContents.add(key);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends URI, ? extends Resource> map) {
		localContents.addAll(map.keySet());
		super.putAll(map);
	}

	@Override
	public boolean remove(Object key, Object value) {
		if (localContents.remove(key)) {
			return super.remove(key, value);
		}
		return false;
	}

	@Override
	public Collection<Resource> values() {
		if (localContents.isEmpty()) {
			return Collections.emptyList();
		}
		return Collections2.filter(super.values(), resource -> localContents.contains(resource.getURI()));
	}

}
