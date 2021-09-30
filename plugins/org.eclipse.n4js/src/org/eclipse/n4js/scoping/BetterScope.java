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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

/**
 *
 */
public class BetterScope implements IScope {
	/** Name to identify the scope instance */
	final protected String name;
	/** {@link EObject} this scope was created for */
	final protected EObject context;
	/** Parent scope or null */
	final protected IScope parent;
	/** Number of parent scopes of type {@link BetterScope} above this scope +1 */
	final protected int depth;
	/** true iff this scope is not case sensitive */
	final protected boolean ignoreCase;
	/** Local elements mapped by {@link QualifiedName} */
	final protected ImmutableMap<QualifiedName, IEObjectDescription> localElementsForQN;
	/** Local elements mapped by {@link URI} */
	final protected ImmutableMap<URI, IEObjectDescription> localElementsForURI;

	/** Constructor */
	public BetterScope(String name, EObject context, IScope parent,
			ImmutableMap<URI, IEObjectDescription> elementsByURI,
			ImmutableMap<QualifiedName, IEObjectDescription> elementsByQN, boolean ignoreCase) {

		this.name = name;
		this.context = context;
		this.parent = parent;
		this.depth = computeDepth();
		this.ignoreCase = ignoreCase;
		this.localElementsForQN = elementsByQN;
		this.localElementsForURI = elementsByURI;
	}

	protected int computeDepth() {
		return (getParent() instanceof BetterScope) ? ((BetterScope) getParent()).computeDepth() + 1 : 0;
	}

	protected IScope getParent() {
		return parent;
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

	protected Collection<IEObjectDescription> getLocalElements(QualifiedName qName) {
		IEObjectDescription result = null;
		qName = isIgnoreCase() ? qName.toLowerCase() : qName;
		result = localElementsForQN.get(qName);
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

	protected Iterable<IEObjectDescription> getParentElements(QualifiedName qName) {
		return parent.getElements(qName);
	}

	protected Iterable<IEObjectDescription> getParentElements(EObject object) {
		return parent.getElements(object);
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName qName) {
		Iterable<IEObjectDescription> localElements = getLocalElements(qName);
		if (!localElements.iterator().hasNext()) {
			localElements = getParentElements(qName);
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

	/**
	 * Returns all elements in the order of shadowing. No elements are filtered out due to shadowing (as done by Xtext).
	 */
	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return Iterables.concat(getAllLocalElements(), getAllParentElements());
	}

	/**
	 * Returns all elements with the given name in the order of shadowing. No elements are filtered out due to shadowing
	 * (as done by Xtext).
	 */
	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName qName) {
		return Iterables.concat(getLocalElements(qName), getParentElements(qName));
	}

	/**
	 * Returns all elements of the given EObject in the order of shadowing. No elements are filtered out due to
	 * shadowing (as done by Xtext).
	 */
	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return Iterables.concat(getLocalElements(object), getParentElements(object));
	}

	@Override
	public String toString() {
		String parentString = null;
		try {
			final IScope parentScope = getParent();
			parentString = parentScope.toString();
		} catch (Throwable t) {
			parentString = t.getClass().getSimpleName() + " : " + t.getMessage();
		}
		return getClass().getSimpleName() + " " + name + " " + (ignoreCase ? "[ignore case]" : "")
				+ getAllLocalElements() + "\n -> " + parentString;
	}
}
