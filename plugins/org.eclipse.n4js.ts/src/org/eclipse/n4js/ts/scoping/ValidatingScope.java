/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * A scope implementation that filters the result of {@link #getAllElements()} by a given criteria.
 *
 * The {@link ValidatingScope} should always be the leaf element in the scope chain.
 */
public class ValidatingScope implements IScope {

	private final IScope delegate;
	private final Predicate<? super IEObjectDescription> allElementsFilter;

	/**
	 * Creates a new scope that filters the result of {@link #getAllElements()} by the given criteria. Only elements
	 * that evaluate the predicate to {@code true} are returned.
	 *
	 * @param delegate
	 *            the scope that does the heavy lifting.
	 * @param allElementsFilter
	 *            the filter for the elements.
	 */
	public ValidatingScope(IScope delegate, Predicate<? super IEObjectDescription> allElementsFilter) {
		this.delegate = delegate;
		this.allElementsFilter = allElementsFilter;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		return delegate.getSingleElement(name);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return delegate.getElements(name);
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		return delegate.getSingleElement(object);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return delegate.getElements(object);
	}

	/**
	 * Returns a filtered iterable of descriptions. Only elements that evaluate to {@code true} when passed to the
	 * {@link #allElementsFilter} are returned.
	 */
	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return Iterables.filter(delegate.getAllElements(), allElementsFilter);
	}

	@Override
	public String toString() {
		String delegateString = null;
		try {
			delegateString = delegate.toString();
		} catch (Throwable t) {
			delegateString = t.getClass().getSimpleName() + " : " + t.getMessage();
		}
		return getClass().getSimpleName() + (allElementsFilter != null ? "[filter=" + allElementsFilter + "]" : "")
				+ " -> " + delegateString;
	}
}
