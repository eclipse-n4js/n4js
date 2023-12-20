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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IntLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.Optional;
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresBefore;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Transforms ES2015 rest parameters to an ES5 equivalent.
 *
 * Dependencies:
 * <ul>
 * <li>requiresBefore {@link ArrowFunction_Part1_Transformation}:<br>
 * the transpiled output code for varargs requires the implicit local arguments variable, which is not available in
 * arrow functions; therefore, this transformation relies on arrow functions already having been transformed into
 * ordinary function expressions.
 * <li>requiresBefore {@link BlockTransformation}:<br>
 * the block transformation sometimes wraps the entire body into a newly created function (for async functions); the
 * handling of varargs added below must NOT be wrapped in such an inner function; the easiest way to ensure this is to
 * execute BlockTransformation before this transformation.
 * </ul>
 */
@Optional(GeneratorOption.RestParameters)
@RequiresBefore({ ArrowFunction_Part1_Transformation.class, BlockTransformation.class })
public class RestParameterTransformation extends Transformation {

	@Override
	public void assertPreConditions() {
		//
	}

	@Override
	public void assertPostConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			// as a result of this transformation no rest parameter should be found in the entire model.
			List<FormalParameter> allFormalParameter = EcoreUtil2.eAllOfType(getState().im, FormalParameter.class);
			List<FormalParameter> stillVariadic = toList(filter(allFormalParameter, fp -> fp.isVariadic()));
			assertTrue("no rest parameter should be in the model.", stillVariadic.size() == 0);
		}
	}

	@Override
	public void analyze() {
		//
	}

	@Override
	public void transform() {
		for (FunctionDefinition fd : collectNodes(getState().im, FunctionDefinition.class, true)) {
			transform(fd);
		}
	}

	private void transform(FunctionDefinition fdef) {
		if (fdef.getFpars().isEmpty()) {
			return;
		}

		int lastFparIdx = fdef.getFpars().size() - 1;
		FormalParameter lastFpar = fdef.getFpars().get(lastFparIdx);

		if (!lastFpar.isVariadic()) {
			return; // nothing to be done
		}

		// rewrite fpar-list.
		VariableStatement stmt = _VariableStatement();
		VariableDeclaration varDecl = _VariableDeclaration(lastFpar.getName());
		ParameterizedCallExpression callExpr = _CallExpr(
				// Array.prototype.slice.call(arguments, 2);
				_PropertyAccessExpr(steFor_Array(), steFor_prototype(), steFor_Array_slice(), steFor_Function_call()),
				_IdentRef(steFor_arguments()),
				_IntLiteral(lastFparIdx));
		varDecl.setExpression(callExpr);
		stmt.getVarDeclsOrBindings().add(varDecl);

		if (fdef.getBody() == null) {
			fdef.setBody(_Block());
		}
		replaceAndRelocate(lastFpar, stmt, stmt.getVarDecl().get(0), fdef.getBody());
	}
}
