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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;

/**
 * Convenience methods for accessing information in the {@link ASTMetaInfoCache}. All these methods are only available
 * in state "Fully Processed" or during the transition from "Fully Initialized" to "Fully Processed" (i.e. during
 * post-processing) and will otherwise throw an {@link IllegalStateException}.
 */
public class ASTMetaInfoUtils {

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Convenience method for {@link ASTMetaInfoCache#getTypeFailSafe(TypableElement)}.
	 */
	public static Result<TypeRef> getTypeFailSafe(TypableElement astNode) {
		return ((N4JSResource) astNode.eResource()).getASTMetaInfoCacheVerifyContext().getTypeFailSafe(astNode);
	}

	/**
	 * Convenience method for {@link ASTMetaInfoCache#getInferredTypeArgs(ParameterizedCallExpression)}.
	 */
	public static List<TypeRef> getInferredTypeArgs(ParameterizedCallExpression callExpr) {
		return ((N4JSResource) callExpr.eResource()).getASTMetaInfoCacheVerifyContext().getInferredTypeArgs(callExpr);
	}

	/**
	 * Convenience method for {@link ASTMetaInfoCache#getCompileTimeValue(Expression)}.
	 */
	public static CompileTimeValue getCompileTimeValue(Expression expr) {
		return ((N4JSResource) expr.eResource()).getASTMetaInfoCacheVerifyContext().getCompileTimeValue(expr);
	}

	/**
	 * Returns all AST nodes referencing the given local (i.e. non-exported) variable declaration. Returns empty list if
	 * variable declaration is exported.
	 */
	public static List<EObject> getLocalVariableReferences(VariableDeclaration varDecl) {
		return ((N4JSResource) varDecl.eResource()).getASTMetaInfoCacheVerifyContext().getLocalVariableReferences(varDecl);
	}
}
