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

import com.google.common.collect.Iterators
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.LocalArgumentsVariable
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.SuperLiteral
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

	/**
	 * All references occurring in <code>body</code> that target the given {@link LocalArgumentsVariable}.
	 *
	 * For better speed early pruning is performed (ie, without delving into functions, unless arrow functions,
	 * or into a declaration that introduces a this context, ie without delving into any {@link ThisTarget} in general).
	 */
	public static def Iterator<? extends IdentifierRef> argumentsOccurrences(Block body, LocalArgumentsVariable target) {
		if (target === null) {
			return emptyList.iterator
		}
		return EcoreUtilN4.getAllContentsFiltered(body, [!introducesThisContext(it)]).filter(IdentifierRef).filter[it.id === target]
	}

	/**
	 * TODO For now, usages of 'arguments' inside an arrow function are resolved to the {@link LocalArgumentsVariable}
	 * owned by that arrow function. In fact, they should be resolved (together with similar usages in
	 * nested arrow functions, without delving into this-introducing contexts) to whatever 'arguments' is in effect.
	 * This method is used to patch up that resolution.
	 * <p>
	 * In detail, this method collects usages of 'arguments', walking down nested arrow-functions,
	 * stopping at arguments-introducing functions (ie, non-arrow ones). The result contains
	 * uses that point to different {@link LocalArgumentsVariable} instances. That's intended.
	 * For the purposes of alpha-renaming, all those uses denote the same arguments-capture,
	 * and thus will all be rewritten to the same identifier (a fresh name) in the xpiler's output.
	 */
	def static Iterator<? extends IdentifierRef> allArgumentsUsages(FunctionOrFieldAccessor argumentsBindingConstruct) {
		var Iterator<? extends IdentifierRef> result = argumentsOccurrences(argumentsBindingConstruct.body, argumentsBindingConstruct._lok)
		for (nestedResult : directlyEnclosedLambdas(argumentsBindingConstruct.body).map(it|allArgumentsUsages(it))) {
			result = Iterators.concat(result, nestedResult)
		}
		if (result.isEmpty) {
			return Collections.emptyIterator()
		} else {
			return result
		}
	}

	/**
	 * All {@link SuperLiteral}s occurring in <code>body</code> delving all the way.
	 */
	public static def superLiterals(Block body) {
		body.eAllContents.filter(SuperLiteral)
	}

	/**
	 * All arrow functions directly enclosed in <code>body</code> ie, without delving into functions, not even into arrow functions,
	 * or into a declaration that introduces a this context, ie without delving into any {@link ThisTarget} in general).
	 */
	public static def directlyEnclosedLambdas(Block body) {
		// TODO this is inefficient but I don't remember an Ecore utility to speed things up
		val result = new HashSet<FunctionExpression>()
		val candidates = allLambdas(body).toSet
		result.addAll(candidates)
		for (candidate : candidates) {
			val isCandidateItselfNested = result.exists[candidate !== it && candidate.isEnclosedBy(it)]
			if (isCandidateItselfNested) {
				result.remove(candidate)
			}
		}
		return result.toList
	}

	/**
	 * All arrow functions, be they directly or transitively enclosed in <code>body</code>. The search doesn't delve into non-arrow functions,
	 * same goes for declarations that introduce a this context, ie without delving into any {@link ThisTarget} in general).
	 */
	public static def allLambdas(Block body) {
		return EcoreUtilN4.getAllContentsFiltered(body, [!introducesThisContext(it)]).filter(ArrowFunction)
	}

	public static def isEnclosedBy(EObject inner, EObject outer) {
		return EcoreUtil.isAncestor(outer, inner)
	}

	public static def boolean isLambda(EObject funDef) {
		funDef instanceof ArrowFunction
	}

	public static def isSingleExprImplicitReturn(FunctionOrFieldAccessor funDef) {
		if (isLambda(funDef)) {
			(funDef as ArrowFunction).isSingleExprImplicitReturn
		} else {
			false
		}
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
