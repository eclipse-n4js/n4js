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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.Optional
import org.eclipse.n4js.transpiler.es.assistants.BlockAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transforms ES2015 arrow functions to an ES5 equivalent, using ordinary function expressions.
 */
@Optional(ArrowFunctions)
class ArrowFunction_Part1_Transformation extends Transformation {

	@Inject BlockAssistant blockAssistant;


	override analyze() {

	}

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override transform() {
		collectNodes(state.im, ArrowFunction, true).forEach[transformArrowFunction];
	}

	/** turn implicit returns into explicit ones. */
	private def void transformArrowFunction(ArrowFunction arrowFunc ) {

		// PART 1
		if( arrowFunc.isSingleExprImplicitReturn ) {
			if( blockAssistant.needsReturnInsertionForBody(arrowFunc)) {
				// Wrap in return.
				var exprToWrap = arrowFunc.body.statements.get(0) as ExpressionStatement;
				replace(exprToWrap, _ReturnStmnt(exprToWrap.expression)); // reuse expression
			}
		}
	}
}
