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

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillModule;
import static org.eclipse.n4js.validation.IssueCodes.POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES;
import static org.eclipse.n4js.validation.IssueCodes.TYS_FOR_IN_VAR_STRING;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForPOLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForTYS_FOR_IN_VAR_STRING;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Statement validation rules for N4JS.
 */
public class N4JSStatementValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem typeSystem;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

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
	 * Checks that all variable declarations contained in const statement (expressed by means of a variable statement)
	 * contain an initializer expression.
	 *
	 * IDEBUG-214
	 */
	@Check
	public void checkVariableStatement(VariableStatement variableStatement) {
		if (variableStatement.getVarStmtKeyword() == VariableStatementKeyword.CONST) {
			variableStatement.getVarDecl().stream().forEach(varDecl -> holdsConstHasInitializer(varDecl));
		}

	}

	private boolean holdsConstHasInitializer(VariableDeclaration varDecl) {
		if (!jsVariantHelper.constantHasInitializer(varDecl)) {
			return true; // in .n4jsd --> anything goes
		}
		if (varDecl.getExpression() == null) {
			return false;
		}
		return true;
	}

	/**
	 * general top-level test: - invalid statements for StaticPolyfillModules. Constraints 140 (restrictions on
	 * static-polyfilling) IDE-1735
	 */
	@Check
	public void checkVariableStatement(Statement statement) {
		EObject con = statement.eContainer();
		if (con instanceof Script) { // top-level elements will be checked only.
			Script script = (Script) con;
			if (!isContainedInStaticPolyfillModule(script)) {
				return;
			}
			// FunctionDeclarations have finer grained check in own validator.
			if (statement instanceof FunctionDeclaration)
				return;

			// it is a static polyfill module
			addIssue(getMessageForPOLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES(), statement,
					POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES);

		}
	}

	/**
	 * 9.1.4 Iteration Statements, Constraints 118 (For-In-Statement Constraints) Variable declaration must be a string
	 */
	@Check
	public void checkForInLoop(ForStatement forStatement) {
		if (forStatement.isForIn()) {
			TypeRef loopVarType = null;
			EObject location = null;
			RuleEnvironment G = (RuleEnvironment) getContext().get(RuleEnvironment.class);
			if (G == null)
				return; // wrongly configured test
			if (!forStatement.getVarDeclsOrBindings().isEmpty()) {
				VariableDeclarationOrBinding varDeclOrBinding = forStatement.getVarDeclsOrBindings().iterator().next();
				location = varDeclOrBinding;
				if (varDeclOrBinding instanceof VariableDeclaration) {
					loopVarType = ((VariableDeclaration) varDeclOrBinding).getDeclaredTypeRef();
				} else {
					VariableBinding varBinding = (VariableBinding) varDeclOrBinding;
					TypeRef res = typeSystem.type(G, varBinding.getExpression());
					if (!(res instanceof UnknownTypeRef)) {
						loopVarType = res;
					}
				}
			} else if (forStatement.getInitExpr() != null) {
				location = forStatement.getInitExpr();
				TypeRef res = typeSystem.type(G, forStatement.getInitExpr());
				if (!(res instanceof UnknownTypeRef)) {
					loopVarType = res;
				}

			}
			if (loopVarType != null) {
				Result res = typeSystem.subtype(G, RuleEnvironmentExtensions.stringTypeRef(G), loopVarType);

				if (!res.isSuccess()) {
					addIssue(getMessageForTYS_FOR_IN_VAR_STRING(loopVarType.getTypeRefAsString()), location,
							TYS_FOR_IN_VAR_STRING);

				}
			}
		}
	}

}
