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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;

/**
 * Helper service that allows to check whether a given type is visible in a certain context.
 */
public class TypeVisibilityChecker extends AbstractTypeVisibilityChecker<Type> {

	/**
	 * Returns <code>true</code> if the given type can be used from within the given module.
	 */
	@Override
	public TypeVisibility isVisible(EObject context, Type t) {
		TypeAccessModifier typeAccessModifier = t.getTypeAccessModifier();
		return isVisible(context, typeAccessModifier, t);
	}

}
