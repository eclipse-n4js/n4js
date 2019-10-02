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

	def public final boolean needsReturnInsertionForBody(ArrowFunction arrowFuncInIM) {
		// unfortunately, we need a properly contained AST element below (see preconditions above)
		val origAST = state.tracer.getOriginalASTNodeOfSameType(arrowFuncInIM, false);
		if (origAST === null) {
			// this arrow function does not come from the N4JS source code but was created by some transformation
			// -> we assume it was intended to return a value and hence a return must be inserted
			return true;
		}
		if (!origAST.isSingleExprImplicitReturn) {
			return false;
		}
		// check if body is typed void, in which case no return will be inserted
		val singleExpr = origAST.implicitReturnExpr();
		val implicitReturnTypeRef = ts.type(state.G, singleExpr);
		if (implicitReturnTypeRef?.declaredType === state.G.voidType) {
			return false;
		}
		return true;
	}
}
