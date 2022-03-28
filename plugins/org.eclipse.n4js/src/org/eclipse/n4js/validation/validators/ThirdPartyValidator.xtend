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
package org.eclipse.n4js.validation.validators

import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Validations that implement warnings unrelated to the N4JS language. They hint towards limitations and problems in
 * third-party tools that are commonly used together with N4JS (e.g. Babel).
 */
class ThirdPartyValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * NEEEDED
	 * 
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Adds warning for <a href="https://github.com/babel/babel/issues/6302">Babel issue #6302</a>.
	 * <p>
	 * This can be removed once the corresponding test file compiles without errors in Babel:<br>
	 * {@code /org.eclipse.n4js.spec.tests/xpect-tests/Others/ThirdParty_Babel_LetConstInFunctionExpression.n4js.xt}.
	 */
	@Check
	def void checkLetConstInFunctionExpression(FunctionExpression funExpr) {
		val funName = funExpr.name;
		if (funName !== null) {
			for (stmnt : funExpr.body.statements) { // only interested in top-level statements of the body
				if (stmnt instanceof VariableStatement) {
					if (stmnt.varStmtKeyword === VariableStatementKeyword.LET // only interested in let/const
						|| stmnt.varStmtKeyword === VariableStatementKeyword.CONST) {
						for (varDecl : stmnt.varDecl) {
							if (varDecl.name == funName) {
								addIssue(messageForTHIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR,
									varDecl, N4JSPackage.eINSTANCE.abstractVariable_Name,
									THIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR);
							}
						}
					}
				}
			}
		}
	}
}
