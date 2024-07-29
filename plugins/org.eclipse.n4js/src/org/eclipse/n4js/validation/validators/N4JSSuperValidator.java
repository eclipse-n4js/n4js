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

import static org.eclipse.n4js.validation.IssueCodes.CLF_CANNOT_CALL_ABSTRACT_SUPER_METHOD;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_ACCESS_FIELD;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_ACCESS_INVALID_LOC_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_CALL_NO_INDEXACCESS;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_CTOR_EXPRSTMT;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_CTOR_INVALID_EXPR_BEFORE;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_CTOR_NESTED;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_INVALID_USAGE;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_NESTED;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_NEW_NOT_SUPPORTED;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * Validate use of super keyword.
 *
 * N4JS spec 6.2.1 and 4.18.2
 */
public class N4JSSuperValidator extends AbstractN4JSDeclarativeValidator {

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

	@SuppressWarnings("javadoc")
	@Check
	public void checkSuper(SuperLiteral superLiteral) {
		EObject container = superLiteral.eContainer();
		N4MemberDeclaration containingMemberDecl = EcoreUtil2.getContainerOfType(container, N4MemberDeclaration.class);

		if (superLiteral.isSuperMemberAccess()) {
			internalCheckSuperMemberAccess(superLiteral, containingMemberDecl);
		} else if (superLiteral.isSuperConstructorAccess()) {
			internalCheckSuperConstructorAccess(superLiteral, containingMemberDecl,
					(ParameterizedCallExpression) container);
		} else if (container instanceof NewExpression) {

			// new super
			// error: not supported yet
			addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
					KEY_SUP_NEW_NOT_SUPPORTED.toIssueItem());
		} else {

			// Constraint 99:
			// error: invalid usage
			addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
					KEY_SUP_INVALID_USAGE.toIssueItem());
		}
	}

	/**
	 * N4JS spec 4.18.2 and 6.2.1 Constraints 97
	 */
	private boolean internalCheckSuperConstructorAccess(SuperLiteral superLiteral,
			N4MemberDeclaration containingMemberDecl, ParameterizedCallExpression cexpr) {

		// 97.1 is implicitly valid (otherwise we wouldn't be in this function)
		if (holdsExpressionOfExpressionStmt(superLiteral, cexpr)) {
			ExpressionStatement exprStmt = (ExpressionStatement) cexpr.eContainer();
			return holdsDirectlyContainedInConstructor(superLiteral, exprStmt, containingMemberDecl) &&
					holdsNoThisOrReturnBefore(superLiteral, exprStmt, containingMemberDecl);
		}
		return false;
	}

	/**
	 * Constraint 97.2
	 */
	private boolean holdsExpressionOfExpressionStmt(SuperLiteral superLiteral, ParameterizedCallExpression cexpr) {
		if (cexpr.eContainer() instanceof ExpressionStatement) {
			return true;
		}
		addIssue(cexpr, superLiteral.eContainmentFeature(), KEY_SUP_CTOR_EXPRSTMT.toIssueItem());
		return false;
	}

	/**
	 * N4JS constraints 97.3
	 */
	private boolean holdsDirectlyContainedInConstructor(SuperLiteral superLiteral, ExpressionStatement exprStmt,
			N4MemberDeclaration containingMemberDecl) {
		EObject block = exprStmt.eContainer();

		if (!(containingMemberDecl instanceof N4MethodDeclaration
				&& "constructor".equals(containingMemberDecl.getName()))) {
			return false;
		}

		if (!(block instanceof Block && block.eContainer() == containingMemberDecl)) {
			IssueItem issueItem = KEY_SUP_CTOR_NESTED.toIssueItem(
					validatorMessageHelper.descriptionWithLine(
							(block instanceof Block) ? block.eContainer()
									: block));
			addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(), issueItem);
			return false;
		}

		return true;
	}

	/**
	 * N4JS constraints 97.4
	 *
	 * @apiNote IDEBUG-147
	 */
	private boolean holdsNoThisOrReturnBefore(SuperLiteral superLiteral, ExpressionStatement exprStmt,
			N4MemberDeclaration containingMemberDecl) {
		for (Statement stmt : getBody(containingMemberDecl).getStatements()) {
			if (stmt == exprStmt) {
				return true;
			}

			if (stmt instanceof ReturnStatement) {
				addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
						KEY_SUP_CTOR_INVALID_EXPR_BEFORE.toIssueItem(validatorMessageHelper.descriptionWithLine(stmt)));
				return false;
			}

			EObject thisKeyword = findFirst(
					EcoreUtilN4.getAllContentsFiltered(stmt, it -> !(it instanceof FunctionOrFieldAccessor)),
					it -> it instanceof ThisLiteral || it instanceof ReturnStatement);

			if (thisKeyword != null) {
				addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
						KEY_SUP_CTOR_INVALID_EXPR_BEFORE
								.toIssueItem(validatorMessageHelper.descriptionWithLine(thisKeyword)));
				return false;
			}
		}

		// must not happen
		addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
				KEY_SUP_CTOR_NESTED.toIssueItem(validatorMessageHelper.descriptionWithLine(exprStmt.eContainer())));
		return false;
	}

	/**
	 * N4JS spec 6.2.1 Constraints 98
	 */
	private boolean internalCheckSuperMemberAccess(SuperLiteral superLiteral,
			N4MemberDeclaration containingMemberDecl) {
		return holdsSuperCallInMethodOrFieldAccessor(superLiteral, containingMemberDecl) //
				&& holdsSuperCallNotNested(superLiteral, containingMemberDecl) //
				&& holdsSuperIsReceiverOfCall(superLiteral)
				&& holdsSuperMethodIsConcrete(superLiteral);
	}

	/**
	 * Makes sure that super method calls are only allowed for non-abstract super methods.
	 */
	private boolean holdsSuperMethodIsConcrete(SuperLiteral superLiteral) {
		EObject literalContainer = superLiteral.eContainer();
		if (literalContainer instanceof ParameterizedPropertyAccessExpression &&
				((ParameterizedPropertyAccessExpression) literalContainer).getProperty() instanceof TMethod) {
			TMethod method = (TMethod) ((ParameterizedPropertyAccessExpression) literalContainer).getProperty();
			if (method.isAbstract()) {
				addIssue(literalContainer,
						N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY,
						CLF_CANNOT_CALL_ABSTRACT_SUPER_METHOD.toIssueItem());
				return false;
			}
		}
		return true;
	}

	/**
	 * Constraint 98.1, special cases
	 */
	private boolean holdsSuperIsReceiverOfCall(SuperLiteral superLiteral) {
		if (superLiteral.eContainer() instanceof ParameterizedPropertyAccessExpression &&
				((ParameterizedPropertyAccessExpression) superLiteral.eContainer()).getProperty() instanceof TField) {
			addIssue(superLiteral.eContainer(),
					N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY,
					KEY_SUP_ACCESS_FIELD.toIssueItem());
			return false;
		}
		if (superLiteral.eContainer() instanceof IndexedAccessExpression) {
			addIssue(superLiteral.eContainer(),
					N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET, KEY_SUP_CALL_NO_INDEXACCESS.toIssueItem());
			return false;
		}
		return true;
	}

	/**
	 * Constraint 98.3
	 */
	private boolean holdsSuperCallNotNested(SuperLiteral superLiteral, N4MemberDeclaration containingMemberDecl) {
		if (EcoreUtil2.getContainerOfType(superLiteral.eContainer(),
				FunctionOrFieldAccessor.class) != containingMemberDecl) {
			addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(), KEY_SUP_NESTED.toIssueItem());
			return false;
		}
		return true;
	}

	/**
	 * Constraint 98.2
	 */
	public boolean holdsSuperCallInMethodOrFieldAccessor(SuperLiteral superLiteral,
			N4MemberDeclaration containingMemberDecl) {
		if (!(containingMemberDecl instanceof N4MethodDeclaration ||
				containingMemberDecl instanceof N4GetterDeclaration
				|| containingMemberDecl instanceof N4SetterDeclaration)) {
			return false;
		}
		if (containingMemberDecl != null && containingMemberDecl.eContainer() instanceof N4InterfaceDeclaration) {
			addIssue(superLiteral.eContainer(), superLiteral.eContainmentFeature(),
					KEY_SUP_ACCESS_INVALID_LOC_INTERFACE.toIssueItem());
			return false;
		}
		return true;
	}
}
