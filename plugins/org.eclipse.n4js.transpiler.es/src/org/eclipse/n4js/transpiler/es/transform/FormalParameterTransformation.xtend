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
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 *
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link BlockTransformation}:<br>
 *     the block transformation sometimes wraps the entire body into a newly created function (for async functions);
 *     the handling of varargs added below must NOT be wrapped in such an inner function; the easiest way to ensure this
 *     is to execute BlockTransformation before this transformation.
 * </ul>
 */
@RequiresBefore(BlockTransformation)
class FormalParameterTransformation extends Transformation {


	override analyze() {
		//
	}

	override assertPreConditions() {
		//
	}

	override assertPostConditions() {
		// as a result of this transformation no variadic parameter should be found in the entire model.
		val allFormalParameter = EcoreUtil2.eAllOfType(state.im, FormalParameter);
		val stillVariadic = allFormalParameter.filter[it.isVariadic].toList;
		assertTrue("No variadic parameter should be in the model.",stillVariadic.size === 0);
	}


	override transform() {
		collectNodes(state.im, FunctionDefinition, true ).forEach[ it.transform]
	}

	def void transform(FunctionDefinition fdef){

		if( fdef.fpars.isEmpty ) return;

		val lastArgIdx = fdef.fpars.size -1;
		val fpar = fdef.fpars.get(lastArgIdx);

		if( ! fpar.isVariadic ) return;

		// rewrite fpar - list.
		val stmt = _VariableStatement()=>[
			varDeclsOrBindings += _VariableDeclaration(fpar.name) => [
				it.expression = _CallExpr(
					// Array.prototype.slice.call(arguments, 2);
					_PropertyAccessExpr( steFor_Array,
										 steFor_prototype,
										 steFor_slice,
										 steFor_call )
					, _IdentRef( steFor_arguments)
					, _IntLiteral(lastArgIdx)
				);
			];
		];

		if(fdef.body===null) {
			fdef.body = _Block();
		}
		replaceAndRelocate(fpar,stmt, stmt.varDecl.get(0),fdef.body)
	}
}
