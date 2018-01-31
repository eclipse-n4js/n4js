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
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.xsemantics.runtime.RuleEnvironment

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Temporary handling of a very specific special case of single-expression arrow functions.
 */
class ArrowFunctionProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private ASTProcessor astProcessor;

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
	def public void tweakArrowFunctions(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		if (node instanceof ArrowFunction) {
			if (node.isSingleExprImplicitReturn) {
				log(indentLevel, "special handling of single-expression arrow function");
				// 1) process node's body, which was postponed according to ASTProcessor#isPostponedNode(EObject)
				// Rationale: the body of a single-expression arrow function isn't a true block
				val block = cache.postponedSubTrees.last;
				if (block !== node.body) {
					throw new IllegalStateException();
				}
				cache.postponedSubTrees.remove(block);
				astProcessor.processSubtree(G, block, cache, indentLevel + 1);
				// 2) adjust node's return type (if required)
				var didTweakReturnType = false;
				val expr = node.getSingleExpression();
				val exprTypeRef = cache.getType(expr).value; // must now be in cache, because we just processed node's body
				if (TypeUtils.isVoid(exprTypeRef)) {
					// the actual type of 'expr' is void
					val arrFunTypeRef = cache.getTypeFailSafe(node).value;
					if (arrFunTypeRef instanceof FunctionTypeExpression) {
						if (!TypeUtils.isVoid(arrFunTypeRef?.returnTypeRef)) {
							// the return type of the single-expression arrow function is *not* void
							// --> this would lead to a type error in N4JSTypeValidation, which we want to fix now
							//     in case the outer type expectation for the containing arrow function has a
							//     return type of 'void' OR there is no outer type expectation at all
							val outerTypeExpectation = expectedTypeForArrowFunction(G, node);
							val outerReturnTypeExpectation = outerTypeExpectation?.returnTypeRef;
							if (outerTypeExpectation === null
								|| (outerReturnTypeExpectation !== null && TypeUtils.isVoid(outerReturnTypeExpectation))) {
								// fix the future type error by changing the return type of the containing arrow function
								// from non-void to void
								if (isDEBUG_LOG) {
									log(indentLevel + 1, "tweaking return type from " + arrFunTypeRef.returnTypeRef?.typeRefAsString + " to void");
								}
								EcoreUtilN4.doWithDeliver(false, [
									arrFunTypeRef.returnTypeRef = G.voidTypeRef;
								], arrFunTypeRef);
								if (isDEBUG_LOG) {
									log(indentLevel + 1, "tweaked type of arrow function is: " + arrFunTypeRef.typeRefAsString);
								}
								didTweakReturnType = true;
							}
						}
					}
				}
				if(!didTweakReturnType) {
					log(indentLevel + 1, "tweaking of return type not required");
				}
				log(indentLevel, "done with special handling of single-expression arrow function");
			}
		}
	}

	def private FunctionTypeExprOrRef expectedTypeForArrowFunction(RuleEnvironment G, ArrowFunction fe) {
		val G_new = G.newRuleEnvironment;
		val tr = ts.expectedTypeIn(G_new, fe.eContainer(), fe).value;
		if (tr instanceof FunctionTypeExprOrRef) {
			return tr;
		}
		return null;
	}
}
