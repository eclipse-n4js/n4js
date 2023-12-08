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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.Optional;
import org.eclipse.n4js.transpiler.es.assistants.BlockAssistant;

import com.google.inject.Inject;

/**
 * Transforms ES2015 arrow functions to an ES5 equivalent, using ordinary function expressions.
 */
@Optional(GeneratorOption.ArrowFunctions)
public class ArrowFunction_Part1_Transformation extends Transformation {

	@Inject
	BlockAssistant blockAssistant;

	@Override
	public void analyze() {
		// empty
	}

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void transform() {
		for (ArrowFunction af : collectNodes(getState().im, ArrowFunction.class, true)) {
			transformArrowFunction(af);
		}
	}

	/** turn implicit returns into explicit ones. */
	private void transformArrowFunction(ArrowFunction arrowFunc) {

		// PART 1
		if (arrowFunc.isSingleExprImplicitReturn()) {
			if (blockAssistant.needsReturnInsertionForBody(arrowFunc)) {
				// Wrap in return.
				ExpressionStatement exprToWrap = (ExpressionStatement) arrowFunc.getBody().getStatements().get(0);
				replace(exprToWrap, _ReturnStmnt(exprToWrap.getExpression())); // reuse expression
			}
		}
	}
}
