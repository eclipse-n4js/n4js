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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Abstract base for scopes that forward most method calls to a delegate.
 */
public abstract class DelegatingScope implements IScope {

	/** The delegate scope. */
	protected final IScope delegate;

	/** Creates a new {@link DelegatingScope}. */
	protected DelegatingScope(IScope delegate) {
		this.delegate = delegate;
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

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return delegate.getAllElements();
	}
}
