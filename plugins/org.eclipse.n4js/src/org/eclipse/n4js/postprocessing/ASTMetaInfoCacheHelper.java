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
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.OnChangeEvictingCache;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleEnvironment;

/**
 * Front-end for accessing the {@link ASTMetaInfoCache}. Should have a public getter for each public getter in
 * {@code ASTMetaInfoCache}.
 */
@Singleton
public final class ASTMetaInfoCacheHelper {

	private static final boolean DEBUG_TRACK_CACHE_CREATION_DELETION = false;

	@Inject
	private IResourceScopeCache resourceScopeCacheHelper;

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
		return resourceScopeCacheHelper.get(ASTMetaInfoCache.class, res, () -> {
			// at the time the cache is created (i.e. quite early), we can assume that all errors are syntax errors
			// created by the parser or the ASTStructureValidator
			final boolean hasBrokenAST = !res.getErrors().isEmpty();
			final ASTMetaInfoCache newCache = new ASTMetaInfoCache(res, hasBrokenAST);

			// DEBUG: use the following code to track cache creation/deletion
			if (DEBUG_TRACK_CACHE_CREATION_DELETION) {
				final String newCacheId = Integer.toHexString(newCache.hashCode());
				System.out.println("!! creating new cache " + newCacheId
						+ " (on resource " + Integer.toHexString(res.hashCode()) + "; URI: " + res.getURI() + ")");
				((OnChangeEvictingCache) resourceScopeCacheHelper).getOrCreate(res).addCacheListener((cacheAdapter) -> {
					if (!newCache.isEmpty()) {
						System.out.println("!!!! clearing non-empty cache " + newCacheId + " (on resource "
								+ Integer.toHexString(res.hashCode()) + "; URI: " + res.getURI() + ").");
					} else {
						System.out.println("!!!! clearing empty cache " + newCacheId + " (on resource "
								+ Integer.toHexString(res.hashCode()) + "; URI: " + res.getURI() + ").");
					}
					if (newCache.isProcessingInProgress()) {
						// DEBUG: good place for a break point when hunting down an accidental cache clear
						System.out.println("!!!! WARNING suspicious cache clear (cache " + newCacheId + " (on resource "
								+ Integer.toHexString(res.hashCode()) + "; URI: " + res.getURI() + ").");
					}
				});
			}

			return newCache;
		});
	}
}
