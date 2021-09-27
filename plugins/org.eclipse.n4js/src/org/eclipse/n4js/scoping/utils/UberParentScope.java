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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.Iterables;

/**
 * A scope that puts another scope above the parent scope.
 */
public class UberParentScope implements IScope {
	private final String name;
	private final IScope parent;
	private final IScope uberParent;

	/** Constructor */
	public UberParentScope(String name, IScope parent, IScope uberParent) {
		this.name = name;
		this.parent = parent;
		this.uberParent = uberParent;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName qName) {
		IEObjectDescription singleElement = parent.getSingleElement(qName);
		if (singleElement == null) {
			singleElement = uberParent.getSingleElement(qName);
		}
		return singleElement;
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		IEObjectDescription singleElement = parent.getSingleElement(object);
		if (singleElement == null) {
			singleElement = uberParent.getSingleElement(object);
		}
		return singleElement;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName qName) {
		return Iterables.concat(parent.getElements(qName), uberParent.getElements(qName));
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return Iterables.concat(parent.getElements(object), uberParent.getElements(object));
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return Iterables.concat(parent.getAllElements(), uberParent.getAllElements());
	}

	@Override
	public String toString() {
		return name + " (UberParent)";
	}
}
