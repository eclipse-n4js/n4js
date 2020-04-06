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

import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.transpiler.AbstractTranspiler
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.Optional
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transforms ES2015 rest parameters to an ES5 equivalent.
 *
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link ArrowFunction_Part1_Transformation}:<br>
 *     the transpiled output code for varargs requires the implicit local arguments parameter, which is not available in
 *     arrow functions; therefore, this transformation relies on arrow functions already having been transformed into
 *     ordinary function expressions.
 * <li>requiresBefore {@link BlockTransformation}:<br>
 *     the block transformation sometimes wraps the entire body into a newly created function (for async functions);
 *     the handling of varargs added below must NOT be wrapped in such an inner function; the easiest way to ensure this
 *     is to execute BlockTransformation before this transformation.
 * </ul>
 */
@Optional(RestParameters)
@RequiresBefore(ArrowFunction_Part1_Transformation, BlockTransformation)
class RestParameterTransformation extends Transformation {


	override assertPreConditions() {
		//
	}

	override assertPostConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			// as a result of this transformation no rest parameter should be found in the entire model.
			val allFormalParameter = EcoreUtil2.eAllOfType(state.im, FormalParameter);
			val stillVariadic = allFormalParameter.filter[it.isVariadic].toList;
			assertTrue("no rest parameter should be in the model.", stillVariadic.size === 0);
		}
	}


	override analyze() {
		//
	}

	override transform() {
		collectNodes(state.im, FunctionDefinition, true).forEach[it.transform];
	}

	def private void transform(FunctionDefinition fdef) {
		if (fdef.fpars.isEmpty) {
			return;
		}

		val lastFparIdx = fdef.fpars.size - 1;
		val lastFpar = fdef.fpars.get(lastFparIdx);

		if (!lastFpar.isVariadic) {
			return; // nothing to be done
		}

		// rewrite fpar-list.
		val stmt = _VariableStatement() => [
			varDeclsOrBindings += _VariableDeclaration(lastFpar.name) => [
				it.expression = _CallExpr(
					// Array.prototype.slice.call(arguments, 2);
					_PropertyAccessExpr(steFor_Array, steFor_prototype, steFor_Array_slice, steFor_Function_call),
					_IdentRef(steFor_arguments),
					_IntLiteral(lastFparIdx)
				);
			];
		];

		if (fdef.body === null) {
			fdef.body = _Block();
		}
		replaceAndRelocate(lastFpar, stmt, stmt.varDecl.get(0), fdef.body)
	}
}
