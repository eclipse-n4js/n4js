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
import org.eclipse.xsemantics.runtime.RuleEnvironment;

/**
 * This interface defines a variation point for extending the type system, in particular for extending the rules that
 * are used to type expressions. It can be used in sub languages such as N4JSX to type newly added expression types. It
 * cannot be used to override the typing of N4JS expressions, since it is only invoked when the N4JS type system
 * encounters expressions that it cannot handle itself.
 */
public interface UnsupportedExpressionTypeHelper {
	/**
	 * Calculate the expected type of an expression in a container. MUST return null if the expected type is not needed
	 * for cannot be calculated
	 *
	 * @param container
	 *            the container of the expression
	 *
	 * @param expression
	 *            the expression to type
	 * @param G
	 *            the rule environment
	 * @return a type reference
	 */
	public TypeRef expectedExpressionTypeInEObject(EObject container, Expression expression, RuleEnvironment G);

	/**
	 * Types an expression.
	 *
	 * @param expression
	 *            the expression to type
	 * @param G
	 *            the rule environment
	 * @return a type reference
	 */
	public TypeRef typeExpression(Expression expression, RuleEnvironment G);

}
