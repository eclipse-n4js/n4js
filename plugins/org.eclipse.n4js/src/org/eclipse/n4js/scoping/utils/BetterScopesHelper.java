/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.BetterScope;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/**
 *
 */
public class BetterScopesHelper {

	/** TODO */
	public IScope scopeForEObjects(EObject context, Iterable<? extends EObject> eObjects) {
		return scopeForEObjects(context, IScope.NULLSCOPE, false, eObjects);
	}

	/** TODO */
	public IScope scopeForEObjects(EObject context, IScope parent, boolean ignoreCase,
			Iterable<? extends EObject> eObjects) {

		return internalScopeFor(context, parent, ignoreCase, eObjects, this::convertToIEObjectDescription);
	}

	/** TODO */
	public IScope scopeFor(EObject context, Iterable<IEObjectDescription> descriptions) {
		return scopeFor(context, IScope.NULLSCOPE, false, descriptions);
	}

	/** TODO */
	public IScope scopeFor(EObject context, IScope parent, boolean ignoreCase,
			Iterable<IEObjectDescription> descriptions) {

		return internalScopeFor(context, parent, ignoreCase, descriptions, t -> t);
	}

	private IEObjectDescription convertToIEObjectDescription(EObject eObject) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(eObject);
		QualifiedName qualifiedName = name == null ? null : QualifiedName.create(name);
		EObjectDescription description = qualifiedName == null ? null
				: new EObjectDescription(qualifiedName, eObject, null);
		return description;
	}

	private <T> IScope internalScopeFor(EObject context, IScope parent, boolean ignoreCase,
			Iterable<? extends T> descriptions, Function<T, IEObjectDescription> convert) {

		Iterator<? extends T> iterator = descriptions.iterator();
		if (!iterator.hasNext()) {
			return parent;
		}

		Map<QualifiedName, IEObjectDescription> mapByQN = new LinkedHashMap<>();
		Map<URI, IEObjectDescription> mapByURI = new LinkedHashMap<>();

		for (T t : descriptions) {
			IEObjectDescription description = convert.apply(t);
			if (description == null) {
				continue;
			}
			QualifiedName qualifiedName = ignoreCase ? description.getName().toLowerCase() : description.getName();
			IEObjectDescription previous = mapByQN.put(qualifiedName, description);

			// we are optimistic that no duplicate names are used
			// however, if the name was already used, the first one should win
			if (previous != null) {
				// revert
				mapByQN.put(qualifiedName, previous);
			} else {
				// also add other entry
				URI uri = description.getEObjectURI();
				mapByURI.put(uri, description);
			}
		}

		return new BetterScope(context, parent, mapByURI, mapByQN, ignoreCase);
	}

}
