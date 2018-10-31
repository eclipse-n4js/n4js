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
package org.eclipse.n4js.transpiler.es.assistants

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class BlockAssistant extends TransformationAssistant {

	@Inject
	private N4JSTypeSystem ts

	/**
	 * Some assertions related to arrow functions that apply to several transformations and are
	 * therefore factored out into this helper method.
	 */
	def public void assertArrowFunctionConditions() {
		assertTrue(
			"all arrow functions must have an original AST node "+
			"(i.e. not allowed to add arrow functions programmatically in a transformation)",
			state.im.eAllContents.filter(ArrowFunction).forall[
				state.tracer.getOriginalASTNodeOfSameType(it, false)!==null
			]);
	}

	def public final boolean needsReturnInsertionForBody(ArrowFunction arrowFuncInIM) {
		// unfortunately, we need a properly contained AST element below (see preconditions above)
		val origAST = state.tracer.getOriginalASTNodeOfSameType(arrowFuncInIM, true);
		if (!origAST.isSingleExprImplicitReturn) {
			return false;
		}
		// check if body is typed void, in which case no return will be inserted
		val singleExpr = origAST.implicitReturnExpr();
		val implicitReturnTypeRef = ts.type(state.G, singleExpr).value;
		if (implicitReturnTypeRef?.declaredType === state.G.voidType) {
			return false;
		}
		return true;
	}
}
