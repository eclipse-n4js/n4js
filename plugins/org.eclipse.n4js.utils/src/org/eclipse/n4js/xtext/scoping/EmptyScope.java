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
package org.eclipse.n4js.xtext.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Empty extended scope delegating all request to its parent. This scope implementation should only be used for scopes
 * not providing elements on their own.
 */
public class EmptyScope implements IScope {

	/**
	 * The parent scope, set in constructor.
	 */
	protected final IScope parent;

	/**
	 * @param parent
	 *            the parent scope, may not be null
	 */
	public EmptyScope(IScope parent) {
		this.parent = parent;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		return parent.getSingleElement(name);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return parent.getElements(name);
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		return parent.getSingleElement(object);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return parent.getElements(object);
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return parent.getAllElements();
	}
}
