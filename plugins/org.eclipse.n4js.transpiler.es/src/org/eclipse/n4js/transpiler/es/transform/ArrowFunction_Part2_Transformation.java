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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._FunExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThisLiteral;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionType;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.Optional;
import org.eclipse.n4js.transpiler.TransformationDependency.Requires;

/**
 * Part 2 of {@link ArrowFunction_Part1_Transformation}.
 */
@Optional(GeneratorOption.ArrowFunctions)
@Requires(ArrowFunction_Part1_Transformation.class)
public class ArrowFunction_Part2_Transformation extends Transformation {

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
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			assertTrue("No arrow-function left in IM",
					collectNodes(getState().im, ArrowFunction.class, true).isEmpty());
		}
	}

	@Override
	public void transform() {
		for (ArrowFunction af : collectNodes(getState().im, ArrowFunction.class, true)) {
			transformArrowFunction(af);
		}
	}

	/** replace arrow-function by function-expression */
	private void transformArrowFunction(ArrowFunction arrowFunc) {

		// PART 2
		FunctionExpression fe = _FunExpr(arrowFunc.isAsync(), arrowFunc.getName(),
				arrowFunc.getFpars().toArray(new FormalParameter[0]), arrowFunc.getBody());
		// note: arrow functions cannot be generators, so we do *not* need to do:
		// fe.generator = arrowFunc.generator;

		ParameterizedCallExpression thisBinder = _CallExpr(
				_PropertyAccessExpr(
						_Parenthesis(fe),
						getSymbolTableEntryForMember(functionType(getState().G), "bind", false, false, true)),
				_ThisLiteral()); // end Call function*()

		replace(arrowFunc, thisBinder, fe);
	}
}
