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
import org.eclipse.n4js.scoping.ScopeSnapshot;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.collect.ImmutableMap;

/**
 * Helper class that provides convenience methods to create instances of {@link ScopeSnapshot}s.
 */
public class ScopeSnapshotHelper {

	/** Returns a scope for the given arguments or the parent scope iff the given eObjects list is empty */
	public IScope scopeForEObjects(String name, EObject context, Iterable<? extends EObject> eObjects) {
		return scopeForEObjects(name, context, IScope.NULLSCOPE, eObjects);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given eObjects list is empty */
	public IScope scopeForEObjects(String name, EObject context, IScope parent, Iterable<? extends EObject> eObjects) {
		return scopeForEObjects(name, context, parent, false, eObjects);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given eObjects list is empty */
	public IScope scopeForEObjects(String name, EObject context, IScope parent, boolean ignoreCase,
			Iterable<? extends EObject> eObjects) {

		return scopeForEObjects(name, context, parent, ignoreCase, eObjects, null);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given eObjects list is empty */
	public IScope scopeForEObjects(String name, EObject context, IScope parent,
			Iterable<? extends EObject> eObjects, Function<IEObjectDescription, IEObjectDescription> wrap) {

		return internalScopeFor(name, context, parent, false, eObjects, this::convertToIEObjectDescription, wrap);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given eObjects list is empty */
	public IScope scopeForEObjects(String name, EObject context, IScope parent, boolean ignoreCase,
			Iterable<? extends EObject> eObjects, Function<IEObjectDescription, IEObjectDescription> wrap) {

		return internalScopeFor(name, context, parent, ignoreCase, eObjects, this::convertToIEObjectDescription, wrap);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given description list is empty */
	public IScope scopeFor(String name, EObject context, Iterable<IEObjectDescription> descriptions) {
		return scopeFor(name, context, IScope.NULLSCOPE, descriptions);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given description list is empty */
	public IScope scopeFor(String name, EObject context, IScope parent, Iterable<IEObjectDescription> descriptions) {
		return scopeFor(name, context, parent, false, descriptions);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given description list is empty */
	public IScope scopeFor(String name, EObject context, IScope parent, boolean ignoreCase,
			Iterable<IEObjectDescription> descriptions) {

		return scopeFor(name, context, parent, ignoreCase, descriptions, null);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given descriptions list is empty */
	public IScope scopeFor(String name, EObject context, IScope parent,
			Iterable<IEObjectDescription> descriptions, Function<IEObjectDescription, IEObjectDescription> wrap) {

		return internalScopeFor(name, context, parent, false, descriptions, t -> t, wrap);
	}

	/** Returns a scope for the given arguments or the parent scope iff the given descriptions list is empty */
	public IScope scopeFor(String name, EObject context, IScope parent, boolean ignoreCase,
			Iterable<IEObjectDescription> descriptions, Function<IEObjectDescription, IEObjectDescription> wrap) {

		return internalScopeFor(name, context, parent, ignoreCase, descriptions, t -> t, wrap);
	}

	private IEObjectDescription convertToIEObjectDescription(EObject eObject) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(eObject);
		QualifiedName qualifiedName = name == null ? null : QualifiedName.create(name);
		EObjectDescription description = qualifiedName == null ? null
				: new EObjectDescription(qualifiedName, eObject, null);
		return description;
	}

	private <T> IScope internalScopeFor(String name, EObject context, IScope parent, boolean ignoreCase,
			Iterable<? extends T> descriptions, Function<T, IEObjectDescription> convert,
			Function<IEObjectDescription, IEObjectDescription> wrap) {

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
			if (wrap != null) {
				description = wrap.apply(description);
			}
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

		return new ScopeSnapshot(name, context, parent, ImmutableMap.copyOf(mapByURI), ImmutableMap.copyOf(mapByQN),
				ignoreCase);
	}

}
