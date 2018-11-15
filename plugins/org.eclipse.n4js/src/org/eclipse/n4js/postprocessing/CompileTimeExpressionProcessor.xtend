/**
 * Copyright (c) 2017 NumberFour AG.
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
import com.google.inject.Singleton
import org.eclipse.n4js.compileTime.CompileTimeEvaluator
import org.eclipse.n4js.compileTime.CompileTimeValue
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.ts.types.TConstableElement
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment

/**
 * Processing of compile-time expressions.
 * <p>
 * All expressions for which {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)} returns
 * <code>true</code> are evaluated and the resulting {@link CompileTimeValue} is stored in the cache. In some cases,
 * the value is also stored in the TModule.
 */
@Singleton
class CompileTimeExpressionProcessor {

	@Inject
	private CompileTimeEvaluator compileTimeEvaluator;

	/**
	 * If the given AST node is an expression that is directly processed as a compile-time expression (cf.
	 * {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)}, this method will evaluate the
	 * expression and store the evaluation result in the given cache; otherwise, this method will do nothing.
	 */
	def public void evaluateCompileTimeExpression(RuleEnvironment G, Expression astNode, ASTMetaInfoCache cache,
		int indentLevel) {

		if (N4JSLanguageUtils.isProcessedAsCompileTimeExpression(astNode)) {
			val value = compileTimeEvaluator.evaluateCompileTimeExpression(G, astNode);
			cache.storeCompileTimeValue(astNode, value);

			// in some cases, we have to store the compile-time value in the TModule:
			val parent = astNode.eContainer;
			if (parent instanceof ExportedVariableDeclaration) {
				storeValueInTModule(G, parent.definedVariable, value);
			} else if (parent instanceof N4FieldDeclaration) {
				storeValueInTModule(G, parent.definedField, value);
			}
		}
	}

	def private void storeValueInTModule(RuleEnvironment G, TConstableElement elem, CompileTimeValue value) {
		if (elem !== null && elem.const) {
			val valueStr = CompileTimeValue.serialize(value);
			EcoreUtilN4.doWithDeliver(false, [
				elem.compileTimeValue = valueStr;
			], elem);
		}
	}
}
