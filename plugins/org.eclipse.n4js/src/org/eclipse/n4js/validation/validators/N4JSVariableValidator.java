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

import static org.eclipse.n4js.validation.IssueCodes.AST_VAR_DECL_RECURSIVE;
import static org.eclipse.n4js.validation.IssueCodes.CFG_LOCAL_VAR_UNUSED;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * Validations for variable declarations and variables.
 */
@SuppressWarnings("javadoc")
public class N4JSVariableValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	public void checkVariableDeclaration(VariableDeclaration varDecl) {
		if (varDecl.getExpression() != null) {
			// TODO: GH-331, remove cases where also the 'UsedBeforeDeclared' issue is raised
			List<IdentifierRef> refs = collectIdentifierRefsTo(varDecl.getExpression(), varDecl, new ArrayList<>());
			for (IdentifierRef currRef : refs) {
				addIssue(currRef, null, AST_VAR_DECL_RECURSIVE.toIssueItem(varDecl.getName()));
			}
		}
	}

	@Check
	public void checkUnusedVariables(VariableDeclaration varDecl) {
		if (varDecl.isDirectlyExported()) {
			return;
		}

		TVariable tVariable = varDecl.getDefinedVariable();
		if (tVariable != null && ASTMetaInfoUtils.getLocalVariableReferences(tVariable).isEmpty()) {
			// deactivated during tests
			addIssue(varDecl, findNameFeature(varDecl).getValue(), CFG_LOCAL_VAR_UNUSED.toIssueItem(varDecl.getName()));
		}
	}

	private static List<IdentifierRef> collectIdentifierRefsTo(EObject astNode, VariableDeclaration varDecl,
			List<IdentifierRef> result) {
		// exception cases:
		// do not inspect class expressions and function expressions because contained code will be executed later
		// and therefore self-reference is ok UNLESS the class is immediately instantiated / the function immediately
		// called
		if (astNode instanceof N4ClassExpression && !isInstantiatedOrCalled(astNode))
			return result; // add nothing
		if (astNode instanceof FunctionExpression && !isInstantiatedOrCalled(astNode))
			return result; // add nothing

		// standard cases:
		TVariable targetForReferencesToVarDecl = varDecl.getDefinedVariable();
		if (astNode instanceof IdentifierRef) {
			IdentifierRef idRef = (IdentifierRef) astNode;
			if (idRef.getId() == targetForReferencesToVarDecl) {
				result.add(idRef);
			}
		}
		for (EObject child : astNode.eContents()) {
			collectIdentifierRefsTo(child, varDecl, result);
		}
		return result;
	}

	public static boolean containsIdentifierRefsTo(EObject astNode, VariableDeclaration varDecl) {
		return !collectIdentifierRefsTo(astNode, varDecl, new ArrayList<>()).isEmpty();
	}

	/**
	 * Tells if given astNode is an expression serving as target to a new or call expression. This provides <b>only a
	 * heuristic</b>; might produce false negatives (but no false positives).
	 */
	private static boolean isInstantiatedOrCalled(EObject astNode) {
		EObject curr = astNode;
		while (curr.eContainer() instanceof ParenExpression) {
			curr = curr.eContainer();
		}
		EObject parent = curr.eContainer();
		if (parent instanceof NewExpression) {
			return ((NewExpression) parent).getCallee() == curr;
		}
		if (parent instanceof ParameterizedCallExpression) {
			return ((ParameterizedCallExpression) parent).getTarget() == curr;
		}
		return false;
	}
}
