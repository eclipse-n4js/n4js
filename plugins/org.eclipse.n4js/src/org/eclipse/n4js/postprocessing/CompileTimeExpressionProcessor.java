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
package org.eclipse.n4js.postprocessing;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.compileTime.CompileTimeEvaluator;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Processing of compile-time expressions.
 * <p>
 * All expressions for which {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)} returns
 * <code>true</code> are evaluated and the resulting {@link CompileTimeValue} is stored in the cache. In some cases, the
 * value is also stored in the TModule.
 */
@Singleton
public class CompileTimeExpressionProcessor {

	@Inject
	private CompileTimeEvaluator compileTimeEvaluator;

	/**
	 * If the given AST node is an expression that is directly processed as a compile-time expression (cf.
	 * {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)}, this method will evaluate the
	 * expression and store the evaluation result in the given cache; otherwise, this method will do nothing.
	 */
	public void evaluateCompileTimeExpression(RuleEnvironment G, Expression astNode, ASTMetaInfoCache cache) {
		if (N4JSLanguageUtils.isProcessedAsCompileTimeExpression(astNode)) {
			CompileTimeValue value = compileTimeEvaluator.evaluateCompileTimeExpression(G, astNode);
			cache.storeCompileTimeValue(astNode, value);

			// in some cases, we have to store the compile-time value in the TModule:
			EObject parent = astNode.eContainer();
			if (parent instanceof VariableDeclaration) {
				VariableDeclaration vd = (VariableDeclaration) parent;
				if (vd.isDirectlyExported()) {
					storeValueInTModule(vd.getDefinedVariable(), value);
				}
			} else if (parent instanceof N4FieldDeclaration) {
				N4FieldDeclaration fd = (N4FieldDeclaration) parent;
				storeValueInTModule(fd.getDefinedField(), value);
			}
		}
	}

	private void storeValueInTModule(TConstableElement elem, CompileTimeValue value) {
		if (elem != null && elem.isConst()) {
			String valueStr = CompileTimeValue.serialize(value);
			EcoreUtilN4.doWithDeliver(false, () -> {
				elem.setCompileTimeValue(valueStr);
			}, elem);
		}
	}
}
