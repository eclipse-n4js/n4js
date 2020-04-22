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

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.MultimapBasedScope;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Specialized to support {@link MeasurableScope}.
 */
public class MeasurableMultimapBasedScope extends MultimapBasedScope implements MeasurableScope {

	public static IScope createScope(IScope parent, Iterable<IEObjectDescription> descriptions, boolean ignoreCase) {
		Multimap<QualifiedName, IEObjectDescription> map = null;
		for (IEObjectDescription description : descriptions) {
			if (map == null)
				map = LinkedHashMultimap.create(5, 2);
			if (ignoreCase)
				map.put(description.getName().toLowerCase(), description);
			else
				map.put(description.getName(), description);
		}
		if (map == null || map.isEmpty()) {
			return parent;
		}
		return new MeasurableMultimapBasedScope(parent, map, ignoreCase);
	}

	private final Multimap<QualifiedName, IEObjectDescription> myElements;

	/**
	 * Constructor
	 */
	protected MeasurableMultimapBasedScope(IScope parent, Multimap<QualifiedName, IEObjectDescription> elements,
			boolean ignoreCase) {
		super(parent, elements, ignoreCase);
		this.myElements = elements;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new MeasurableMultimapBasedScope(MeasurableScope.decorate(getParent(), dataCollector), myElements,
				isIgnoreCase());
	}
}
