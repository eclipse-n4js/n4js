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

import com.google.inject.Singleton;

import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleEnvironment;

/**
 * Front-end for accessing the {@link ASTMetaInfoCache}. Should have a public getter for each public getter in
 * {@code ASTMetaInfoCache}.
 */
@Singleton
public final class ASTMetaInfoCacheHelper {

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Convenience method for {@link ASTMetaInfoCache#getTypeFailSafe(TypableElement)}.
	 */
	public Result<TypeRef> getTypeFailSafe(TypableElement astNode) {
		return getOrCreate((N4JSResource) astNode.eResource()).getTypeFailSafe(astNode);
	}

	/**
	 * Convenience method for {@link ASTMetaInfoCache#getInferredTypeArgs(ParameterizedCallExpression)}.
	 */
	public List<TypeRef> getInferredTypeArgs(ParameterizedCallExpression callExpr) {
		return getOrCreate((N4JSResource) callExpr.eResource()).getInferredTypeArgs(callExpr);
	}

	/**
	 * Convenience method for {@link ASTMetaInfoCache#getCompileTimeValue(Expression)}.
	 */
	public CompileTimeValue getCompileTimeValue(Expression expr) {
		return getOrCreate((N4JSResource) expr.eResource()).getCompileTimeValue(expr);
	}

	/**
	 * Returns all AST nodes referencing the given local (i.e. non-exported) variable declaration. Returns empty list if
	 * variable declaration is exported.
	 */
	public List<EObject> getLocalVariableReferences(VariableDeclaration varDecl) {
		return getOrCreate((N4JSResource) varDecl.eResource()).getLocalVariableReferences(varDecl);
	}

	/**
	 * Returns the {@link ASTMetaInfoCache} of the given resource, optionally creating it if it does not exist already.
	 */
	public ASTMetaInfoCache getOrCreate(N4JSResource res) {
		return res.getASTMetaInfoCache();
	}
}
