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
package org.eclipse.n4js.types.utils;

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.ThisArgProvider
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.ThisTarget
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.xtext.EcoreUtil2

/**
 * Utility methods added to support ES6 arrow functions, aka lambdas.
 */
public class LambdaUtils {

	/**
	 * All {@link ThisLiteral}s occurring in <code>body</code> that refer to the same this-value;
	 * ie without delving into functions (unless arrow functions) or into a declaration that introduces
	 * a this context, ie any {@link ThisTarget} in general.
	 */
	public static def thisLiterals(Block body) {
		EcoreUtilN4.getAllContentsFiltered(body, [!introducesThisContext(it)]).filter(it| it instanceof ThisLiteral)
	}

	private static def boolean introducesThisContext(EObject node) {
		switch node {
			FunctionExpression: !node.isArrowFunction
			FunctionDefinition: true
			ThisArgProvider: true
			default: false
		}
	}

	public static def boolean isLambda(EObject funDef) {
		funDef instanceof ArrowFunction
	}

	/**
	 * A this-binder is a non-lambda {@link FunctionDefinition}.
	 * This method looks up the nearest such construct that encloses the argument.
	 * In case the argument itself is a this-binder, it is returned.
	 * In case no this-binder exists (for example, the argument is enclosed in a top-level arrow-function) then null is returned.
	 * <p>
	 * Note: unlike {@link N4JSASTUtils#getContainingFunctionOrAccessor(EObject)} this method
	 * regards {@link N4FieldDeclaration} as valid answer (ie, a "nearest enclosing this-binder").
	 */
	public static def ThisArgProvider nearestEnclosingThisBinder(EObject expr) {
		if (expr === null) {
			return null;
		}
		val enclosingThisBinder = EcoreUtil2.getContainerOfType(expr, ThisArgProvider);
		if (enclosingThisBinder === null) {
			return null;
		}
		if (isLambda(enclosingThisBinder)) {
			// lambda functions provide no binding for 'this', keep searching
			return nearestEnclosingThisBinder(enclosingThisBinder.eContainer);
		}
		// non-lambda functions do bind 'this'; we've found it
		return enclosingThisBinder;
	}

	/**
	 * A top-level lambda has no enclosing lexical context that provides bindings for 'this' and 'arguments'.
	 */
	public static def isTopLevelLambda(FunctionExpression funExpr) {
		isLambda(funExpr) && {
			val enclosing = funExpr.eContainer;
			null === nearestEnclosingThisBinder(enclosing);
		}
	}

}
