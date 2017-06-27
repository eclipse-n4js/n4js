/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.builtin;

import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.xtext.scoping.FilteringScope;
import org.eclipse.xtext.scoping.IScope;

/**
 * This scope filters primitive types and the any type and delegates all other queries to its parent.
 */
public class NoPrimitiveTypesScope extends FilteringScope {

	/**
	 * Instantiates a new {@link NoPrimitiveTypesScope}
	 *
	 * @param parent
	 *            The parent scope whose results will be filtered
	 */
	public NoPrimitiveTypesScope(IScope parent) {
		super(parent, elementDescription -> {
			if (null == elementDescription || null == elementDescription.getEClass()) {
				return true;
			}
			int elementClassifierId = elementDescription.getEClass().getClassifierID();
			return elementClassifierId != TypesPackage.PRIMITIVE_TYPE &&
					elementClassifierId != TypesPackage.ANY_TYPE;
		});
	}
}
