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
package org.eclipse.n4js.ide.xtext.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.common.collect.Maps;

/**
 * adds {@link #findUrisStartingWith(URI)}
 */
public class XResourceDescriptionsData extends ResourceDescriptionsData {

	/** Constructor */
	public XResourceDescriptionsData(Iterable<IResourceDescription> descriptions) {
		super(descriptions);
	}

	/** Constructor */
	public XResourceDescriptionsData(Map<URI, IResourceDescription> resourceDescriptionMap,
			Map<QualifiedName, Object> lookupMap) {

		super(resourceDescriptionMap, lookupMap);
	}

	@Override
	public XResourceDescriptionsData copy() {
		return new XResourceDescriptionsData(
				Maps.newLinkedHashMap(getResourceDescriptionMap()),
				copyLookupMap());
	}

	// or use ResourceDescriptionsData#getAllURIs()?
	public List<URI> findUrisStartingWith(URI prefix) {
		List<URI> uris = new ArrayList<>();
		Map<URI, IResourceDescription> resourceDescriptionMap = getResourceDescriptionMap();

		for (URI uri : resourceDescriptionMap.keySet()) {
			if (UriUtil.isPrefixOf(prefix, uri)) {
				uris.add(uri);
			}
		}

		return uris;
	}

	private Map<URI, IResourceDescription> getResourceDescriptionMap() {
		try {
			return new ReflectExtensions()
					.<Map<URI, IResourceDescription>> get(this, "resourceDescriptionMap");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
