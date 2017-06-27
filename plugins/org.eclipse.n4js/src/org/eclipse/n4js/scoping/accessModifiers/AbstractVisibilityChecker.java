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
package org.eclipse.n4js.scoping.accessModifiers;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.n4js.scoping.accessModifiers.AbstractTypeVisibilityChecker.TypeVisibility;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 */
public abstract class AbstractVisibilityChecker<E extends IdentifiableElement> {

	/**
	 * Returns <code>true</code> if the given element can be used from within the given resource.
	 */
	public abstract TypeVisibility isVisible(Resource contextResource, E e);

}
