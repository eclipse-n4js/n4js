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
package org.eclipse.n4js.scoping.smith;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.MapBasedScope;

import com.google.common.collect.Iterables;

/**
 * Specialized to support {@link MeasurableScope}.
 */
public class MeasurableMapBasedScope extends MapBasedScope implements MeasurableScope {

	public static IScope createScope(IScope parent, Iterable<IEObjectDescription> descriptions, boolean ignoreCase) {
		Map<QualifiedName, IEObjectDescription> map = null;
		for (IEObjectDescription description : descriptions) {
			if (map == null)
				map = new LinkedHashMap<>(4);
			QualifiedName name = ignoreCase ? description.getName().toLowerCase() : description.getName();
			IEObjectDescription previous = map.put(name, description);
			// we are optimistic that no duplicate names are used
			// however, if the name was already used, the first one should win
			if (previous != null) {
				map.put(name, previous);
			}
		}
		if (map == null || map.isEmpty()) {
			return parent;
		}
		return new MeasurableMapBasedScope(parent, map, ignoreCase);
	}

	public static IScope createScope(IScope parent, Collection<IEObjectDescription> descriptions) {
		if (descriptions.size() == 1) {
			IEObjectDescription description = Iterables.getOnlyElement(descriptions);
			return new MeasurableMapBasedScope(parent, Collections.singletonMap(description.getName(), description),
					false);
		} else if (descriptions.isEmpty()) {
			return parent;
		}
		return createScope(parent, descriptions, false);
	}

	public static IScope createScope(IScope parent, Iterable<IEObjectDescription> descriptions) {
		return createScope(parent, descriptions, false);
	}

	private final Map<QualifiedName, IEObjectDescription> myElements;

	/**
	 * Constructor
	 */
	protected MeasurableMapBasedScope(IScope parent, Map<QualifiedName, IEObjectDescription> elements,
			boolean ignoreCase) {
		super(parent, elements, ignoreCase);
		this.myElements = elements;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new MeasurableMapBasedScope(MeasurableScope.decorate(getParent(), dataCollector), myElements,
				isIgnoreCase());
	}

}
