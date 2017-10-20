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
package org.eclipse.n4js.typesystem;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.xtext.xbase.lib.Pair;

import it.xsemantics.runtime.StringRepresentation;

/**
 */
public class N4JSStringRepresenation extends StringRepresentation {

	/**
	 * Delegates to {@link TypeArgument#getTypeRefAsString()}
	 */
	protected String stringRep(TypeArgument typeArgument) {
		return typeArgument.getTypeRefAsString();
	}

	/**
	 * Overridden to avoid more expensive polymorphic dispatching in supertype for commonly used argument types.
	 */
	@Override
	public String string(Object object) {
		if (object instanceof TypeArgument) {
			return stringRep((TypeArgument) object);
		}
		if (object instanceof Class<?>) {
			return stringRep((Class<?>) object);
		}
		if (object instanceof EClassifier) {
			return stringRep((EClassifier) object);
		}
		if (object instanceof EObject) {
			return stringRep((EObject) object);
		}
		if (object instanceof Pair<?, ?>) {
			return stringRep(object);
		}
		if (object instanceof String) {
			return (String) object;
		}
		if (object instanceof List<?>) {
			return stringRep((List<?>) object);
		}
		return super.string(object);
	}
}
