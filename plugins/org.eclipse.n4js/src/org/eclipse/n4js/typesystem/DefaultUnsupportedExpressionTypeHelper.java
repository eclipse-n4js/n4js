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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import it.xsemantics.runtime.RuleEnvironment;

/**
 * Default implementation of {@link UnsupportedExpressionTypeHelper} that always throws an
 * {@link UnsupportedOperationException}. In N4JS, the method {@link #typeExpression(Expression, RuleEnvironment)} must
 * never be called as all types of expressions should be handled in Xsemantics.
 */
public class DefaultUnsupportedExpressionTypeHelper implements UnsupportedExpressionTypeHelper {
	@Override
	public TypeRef typeExpression(Expression expression, RuleEnvironment G) {
		throw new UnsupportedOperationException("Cannot determine the type of expression: " + expression);
	}

	/**
	 * This is the equivalence of the axiom expectedTypeNone that has been commented out in n4js.xsemantics
	 */
	@Override
	public TypeRef expectedExpressionTypeInEObject(EObject container, Expression expression, RuleEnvironment G) {
		return null;
	}
}
