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
package org.eclipse.n4js.n4idl.scoping

import java.util.Objects
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

/**
 * An implementation of {@link IScope} that considers versioned objects.
 */
class N4IDLVersionAwareScope implements IScope {

	private final IScope delegate;

	private final N4IDLVersionableFilter filter;

	/**
	 * Creates a new instance that filters the elements from the given delegate scope using the given context version.
	 *
	 * @param delegate
	 *            the delegate to query for elements
	 * @param contextVersion
	 * 			  the context version to consider
	 */
	new(IScope delegate, int contextVersion) {
		if (contextVersion <= 0)
			throw new IllegalArgumentException("Context version must be a positive integer");
		this.delegate = Objects.requireNonNull(delegate);
		this.filter = new N4IDLVersionableFilter(contextVersion);
	}

	override getAllElements() {
		return delegate.getAllElements();
	}

	override getElements(QualifiedName name) {
		return filter.filterElements(delegate.getElements(name));
	}

	override getElements(EObject object) {
		return filter.filterElements(delegate.getElements(object));
	}

	override getSingleElement(QualifiedName name) {
		return selectElement(delegate.getElements(name));
	}

	override getSingleElement(EObject object) {
		return selectElement(delegate.getElements(object));
	}

	/**
	 * Selects the first description of an element that satisfies either of the following conditions:
	 *
	 * <ul>
	 * <li>The element is not an instance of {@link TClassifier}, i.e., it is not versionable.</li>
	 * <li>The element is an instance of {@link TClassifier} and its version is equal to the upper limit of the
	 * requested version range.</li>
	 * </ul>
	 * <p>
	 * If no element satisfies these conditions, then the given iterable only contains descriptions for instances of
	 * {@link TClassifier}. In that case, the element with the maximal version number that is still contained in the
	 * requested version range is selected and its description is returned.
	 * </p>
	 * <p>
	 * If no element is contained in the requested version range, then <code>null</code> is returned.
	 * </p>
	 */
	private def IEObjectDescription selectElement(Iterable<IEObjectDescription> descriptions) {
		return filter.filterElements(descriptions).head();
	}

	override toString() {
		return "N4IDLVersionAwareScope[contextVersion = " + this.filter.contextVersion + "] -> " + delegate.toString;
	}

}
