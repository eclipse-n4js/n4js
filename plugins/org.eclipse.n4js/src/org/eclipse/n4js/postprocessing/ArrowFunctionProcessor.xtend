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
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.EcoreUtilN4
import it.xsemantics.runtime.RuleEnvironment
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Temporary handling of a very specific special case of single-expression arrow functions.
 */
class ArrowFunctionProcessor {

	@Inject
	private N4JSTypeSystem ts;

	/**
	 * This changes the return type of single-expression arrow functions from non-void to void if the non-void return
	 * type would lead to a type error later on (for details see code of this method).
	 * <p>
	 * This tweak is only required because our poor man's return type inferencer in the types builder infers a wrong
	 * non-void return type in some cases, which is corrected in this method.
	 * <p>
	 * Example:
	 * <pre>
	 * function foo(f : {function(): void}) {}
	 * function none(): void {}
	 * foo(() => none()); // will show bogus error when disabling this method
	 * </pre>
	 * TODO remove this special handling once the Poor man's return type inferencer is improved
	 */
	def public void tweakArrowFunctions(RuleEnvironment G, EObject node, ASTMetaInfoCache cache) {
		if (node instanceof Expression) {
			val arrFun = N4JSASTUtils.getContainingSingleExpressionArrowFunction(node);
			if (arrFun !== null) {
				// 'node' is the expression of a single-expression arrow function
				val exprTypeRef = cache.getType(node).value; // should be in cache, by now
				if (TypeUtils.isVoid(exprTypeRef)) {
					// the actual type of 'node' is void
					val arrFunTypeRef = cache.getTypeFailSafe(arrFun).value;
					if (arrFunTypeRef instanceof FunctionTypeExpression) {
						if (!TypeUtils.isVoid(arrFunTypeRef?.returnTypeRef)) {
							// the return type of the containing single-expression arrow function is *not* void
							// --> this would lead to a type error in N4JSTypeValidation, which we want to fix now
							//     in case the outer type expectation for the containing arrow function has a
							//     return type of 'void' OR there is no outer type expectation at all
							val outerTypeExpectation = expectedTypeForArrowFunction(arrFun);
							val outerReturnTypeExpectation = outerTypeExpectation?.returnTypeRef;
							if (outerTypeExpectation === null
								|| (outerReturnTypeExpectation !== null && TypeUtils.isVoid(outerReturnTypeExpectation))) {
								// fix the future type error by changing the return type of the containing arrow function
								// from non-void to void
								EcoreUtilN4.doWithDeliver(false, [
									arrFunTypeRef.returnTypeRef = G.voidTypeRef;
								], arrFunTypeRef);
							}
						}
					}
				}
			}
		}
	}

	def private FunctionTypeExprOrRef expectedTypeForArrowFunction(ArrowFunction fe) {
		val G = fe.newRuleEnvironment;
		val tr = ts.expectedTypeIn(G, fe.eContainer(), fe).value;
		if (tr instanceof FunctionTypeExprOrRef) {
			return tr;
		}
		return null;
	}
}
