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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.validation.IssueCodes.THIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR;

import java.util.Objects;

import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * Validations that implement warnings unrelated to the N4JS language. They hint towards limitations and problems in
 * third-party tools that are commonly used together with N4JS (e.g. Babel).
 */
public class ThirdPartyValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Adds warning for <a href="https://github.com/babel/babel/issues/6302">Babel issue #6302</a>.
	 * <p>
	 * This can be removed once the corresponding test file compiles without errors in Babel:<br>
	 * {@code /org.eclipse.n4js.spec.tests/xpect-tests/Others/ThirdParty_Babel_LetConstInFunctionExpression.n4js.xt}.
	 */
	@Check
	public void checkLetConstInFunctionExpression(FunctionExpression funExpr) {
		String funName = funExpr.getName();
		if (funName != null) {
			// only interested in top-level statements of the body
			for (Statement stmnt : funExpr.getBody().getStatements()) {
				if (stmnt instanceof VariableStatement) {
					VariableStatement vs = (VariableStatement) stmnt;
					if (vs.getVarStmtKeyword() == VariableStatementKeyword.LET // only interested in let/const
							|| vs.getVarStmtKeyword() == VariableStatementKeyword.CONST) {

						for (VariableDeclaration varDecl : vs.getVarDecl()) {
							if (Objects.equals(varDecl.getName(), funName)) {
								addIssue(varDecl, N4JSPackage.eINSTANCE.getAbstractVariable_Name(),
										THIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR.toIssueItem());
							}
						}
					}
				}
			}
		}
	}
}
