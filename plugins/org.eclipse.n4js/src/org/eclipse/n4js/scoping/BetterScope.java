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
package org.eclipse.n4js.scoping;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;

/**
 *
 */
public class BetterScope implements IScope {
	final EObject context;
	final IScope parent;
	final int depth;
	final boolean ignoreCase;
	final Map<QualifiedName, IEObjectDescription> localElementsForQN;
	final Map<URI, IEObjectDescription> localElementsForURI;
	final LinkedHashMultimap<QualifiedName, IEObjectDescription> allElementsForQN = null;
	final LinkedHashMultimap<URI, IEObjectDescription> allElementsForURI = null;

	public BetterScope(EObject context, IScope parent, Map<URI, IEObjectDescription> elementsByURI,
			Map<QualifiedName, IEObjectDescription> elementsByQN, boolean ignoreCase) {

		this.context = context;
		this.parent = parent;
		this.depth = computeDepth();
		this.ignoreCase = ignoreCase;
		this.localElementsForQN = elementsByQN;
		this.localElementsForURI = elementsByURI;
	}

	protected int computeDepth() {
		return (parent instanceof BetterScope) ? ((BetterScope) parent).computeDepth() + 1 : 0;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public int getDepth() {
		return depth;
	}

	protected Collection<IEObjectDescription> getAllLocalElements() {
		return localElementsForQN.values();
	}

	protected Collection<IEObjectDescription> getLocalElements(QualifiedName name) {
		IEObjectDescription result = null;
		name = isIgnoreCase() ? name.toLowerCase() : name;
		result = localElementsForQN.get(name);
		if (result == null)
			return Collections.emptyList();
		return Collections.singleton(result);
	}

	protected Collection<IEObjectDescription> getLocalElements(EObject object) {
		final URI uri = EcoreUtil2.getPlatformResourceOrNormalizedURI(object);
		IEObjectDescription result = null;
		result = localElementsForURI.get(uri);
		if (result == null)
			return Collections.emptyList();
		return Collections.singleton(result);
	}

	protected Iterable<IEObjectDescription> getAllParentElements() {
		return parent.getAllElements();
	}

	protected Iterable<IEObjectDescription> getParentElements(QualifiedName name) {
		return parent.getElements(name);
	}

	protected Iterable<IEObjectDescription> getParentElements(EObject object) {
		return parent.getElements(object);
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		Iterable<IEObjectDescription> localElements = getLocalElements(name);
		if (!localElements.iterator().hasNext()) {
			localElements = getParentElements(name);
		}
		return localElements.iterator().hasNext() ? localElements.iterator().next() : null;
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		Iterable<IEObjectDescription> localElements = getLocalElements(object);
		if (!localElements.iterator().hasNext()) {
			localElements = getParentElements(object);
		}
		return localElements.iterator().hasNext() ? localElements.iterator().next() : null;
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return Iterables.concat(getAllLocalElements(), getAllParentElements());
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return Iterables.concat(getLocalElements(name), getParentElements(name));
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return Iterables.concat(getLocalElements(object), getParentElements(object));
	}

}
