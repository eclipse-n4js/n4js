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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.OnChangeEvictingCache.CacheAdapter;

import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleEnvironment;

/**
 * The <em>AST meta-info cache</em> is created and filled with information during post-processing of an N4JS resource
 * and contains meta information on the AST, such as types of AST nodes, links from declarations to all places where the
 * declared element is used, etc.
 * <p>
 * It is created only for {@link N4JSResource}s and only for such N4JS resources that are <u>loaded from source code</u>
 * and therefore have an AST (not those that are loaded from the Xtext index / TModule).
 */
public final class ASTMetaInfoCache {

	private static Logger logger = Logger.getLogger(ASTMetaInfoCache.class);

	// ################################################################################################################
	// main content of the cache
	// (add new properties here; getters should be public, setters should have package visibility)

	private final N4JSResource resource;
	private final boolean hasBrokenAST;
	private final Map<TypableElement, Result<TypeRef>> actualTypes = new HashMap<>();
	private final Map<ParameterizedCallExpression, List<TypeRef>> inferredTypeArgs = new HashMap<>();
	private final Map<Expression, CompileTimeValue> compileTimeValue = new HashMap<>();

	private final Map<VariableDeclaration, List<EObject>> localVariableReferences = new HashMap<>();

	/* package */ ASTMetaInfoCache(N4JSResource resource, boolean hasBrokenAST) {
		this.resource = resource;
		this.hasBrokenAST = hasBrokenAST;
	}

	/**
	 * Returns the {@link N4JSResource} the receiving cache belongs to.
	 */
	public N4JSResource getResource() {
		return resource;
	}

	/**
	 * Tells if the resource of this cache has a broken AST.
	 */
	public boolean hasBrokenAST() {
		return hasBrokenAST;
	}

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Returns the actual type of the given astNode as stored in the cache, or <code>null</code> if not available.
	 */
	public Result<TypeRef> getTypeFailSafe(TypableElement astNode) {
		return actualTypes.get(astNode);
	}

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Returns the actual type of the given astNode as stored in the cache. Throws exception if not available.
	 */
	public Result<TypeRef> getType(TypableElement astNode) {
		final Result<TypeRef> result = getTypeFailSafe(astNode);
		if (result == null) {
			if (isCanceled()) {
				return new Result<>(TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
			} else {
				throw UtilN4.reportError(new IllegalStateException(
						"cache miss: no actual type in cache for AST node: " + astNode
								+ " in resource: " + resource.getURI()));
			}
		}
		return result;
	}

	/* package */ void storeType(TypableElement astNode, TypeRef actualType) {
		storeType(astNode, new Result<>(actualType));
	}

	/* package */ void storeType(TypableElement astNode, Result<TypeRef> actualType) {
		if (!isProcessingInProgress()) {
			throw new IllegalStateException();
		}
		if (actualType == null) {
			throw new IllegalArgumentException("actualType may not be null");
		}
		if (astNode.eResource() != resource) {
			throw new IllegalArgumentException("astNode must be from this resource");
		}
		if (actualTypes.put(astNode, actualType) != null) {
			throw UtilN4.reportError(new IllegalStateException(
					"cache collision: multiple actual types put into cache for AST node: " + astNode +
							" in resource: " + resource.getURI()));
		}
	}

	/**
	 * Returns the inferred type arguments of the given generic, non-parameterized call expression or <code>null</code>
	 * if the given call expression is not generic or is parameterized (i.e. type arguments given in the expression) or
	 * type argument inference has not taken place yet.
	 */
	public List<TypeRef> getInferredTypeArgs(ParameterizedCallExpression callExpr) {
		return inferredTypeArgs.get(callExpr);
	}

	/* package */ void storeInferredTypeArgs(ParameterizedCallExpression callExpr, List<TypeRef> typeArgs) {
		if (!isProcessingInProgress()) {
			throw new IllegalStateException();
		}
		if (callExpr.eResource() != resource) {
			throw new IllegalArgumentException("astNode must be from this resource");
		}
		inferredTypeArgs.put(callExpr, Collections.unmodifiableList(new ArrayList<>(typeArgs)));
	}

	/**
	 * Returns the {@link CompileTimeValue compile-time value} that resulted from the compile-time evaluation of the
	 * given expression in one of the early phases of post-processing or <code>null</code> if no value was cached.
	 * Compile-time values are cached *only* for expressions for which method
	 * {@link N4JSLanguageUtils#isProcessedAsCompileTimeExpression(Expression)} returns <code>true</code>.
	 * <p>
	 * Note that the returned value may be {@link CompileTimeValue#isValid() invalid} in case the expression is not a
	 * valid compile-time expression.
	 */
	public CompileTimeValue getCompileTimeValue(Expression expr) {
		return compileTimeValue.get(expr);
	}

	/* package */ void storeCompileTimeValue(Expression expr, CompileTimeValue evalResult) {
		if (!isProcessingInProgress()) {
			throw new IllegalStateException();
		}
		if (expr.eResource() != resource) {
			throw new IllegalArgumentException("astNode must be from this resource");
		}
		if (compileTimeValue.put(expr, evalResult) != null) {
			throw UtilN4.reportError(new IllegalStateException(
					"cache collision: multiple evaluation results put into cache for AST node: " + expr
							+ " in resource: " + resource.getURI()));
		}
	}

	/**
	 * Returns all AST nodes referencing the given local (i.e. non-exported) variable declaration. Returns empty list if
	 * variable declaration is exported.
	 */
	public List<EObject> getLocalVariableReferences(VariableDeclaration varDecl) {
		final List<EObject> references = localVariableReferences.get(varDecl);
		if (references != null) {
			return Collections.unmodifiableList(references);
		} else {
			return Collections.emptyList();
		}
	}

	/* package */ void storeLocalVariableReference(VariableDeclaration varDecl, EObject sourceNode) {
		if (varDecl.eResource() != resource) {
			throw new IllegalArgumentException("astNode must be from this resource");
		}
		if (localVariableReferences.containsKey(varDecl)) {
			final List<EObject> references = localVariableReferences.get(varDecl);
			references.add(sourceNode);
		} else {
			final List<EObject> references = new ArrayList<>();
			references.add(sourceNode);
			localVariableReferences.put(varDecl, references);
		}
	}

	// ################################################################################################################
	// helper variables used *only* by post-processors in package org.eclipse.n4js.postprocessing

	private boolean isProcessingInProgress = false;
	private boolean isFullyProcessed = false;
	/* package */ CancelIndicator cancelIndicator = null;

	// @formatter:off

	/* package */ final Set<EObject> forwardProcessedSubTrees = new LinkedHashSet<>();
	/* package */ final Set<EObject> astNodesCurrentlyBeingTyped = new LinkedHashSet<>();
	/* package */ final Queue<EObject> postponedSubTrees = new LinkedList<>(); // using LinkedList as FIFO queue, here
	/* package */ final List<FunctionOrFieldAccessor> potentialContainersOfLocalArgumentsVariable = new LinkedList<>();

	// @formatter:on

	/* package */ boolean isProcessingInProgress() {
		return isProcessingInProgress;
	}

	/* package */ boolean isFullyProcessed() {
		return isFullyProcessed;
	}

	/* package */ boolean isCanceled() {
		return cancelIndicator != null && cancelIndicator.isCanceled();
	}

	/* package */ boolean isEmpty() {
		// only used for debugging to spot a suspicious cache clear (see ASTMetaInfoCacheHelper)
		return actualTypes.isEmpty() && inferredTypeArgs.isEmpty();
	}

	/* package */ void startProcessing(@SuppressWarnings("hiding") CancelIndicator cancelIndicator) {
		if (isProcessingInProgress || isFullyProcessed) {
			// this method should never be called more than once per N4JSResource
			logger.error("*#*#*#*#* multiple invocation of method ASTMetaInfoCache#startProcessing()\n"
					+ dumpDebugInfo(true, true));
			throw UtilN4.reportError(new IllegalStateException(
					"multiple invocation of method ASTMetaInfoCache#startProcessing()"));
		}
		isProcessingInProgress = true;
		this.cancelIndicator = cancelIndicator; // may be null
	}

	/* package */ void endProcessing() {
		if (!isProcessingInProgress) {
			throw new IllegalStateException("invalid invocation of method ASTMetaInfoCache#endProcessing()");
		}
		isFullyProcessed = true;
		isProcessingInProgress = false;
		cancelIndicator = null;
		forwardProcessedSubTrees.clear();
		astNodesCurrentlyBeingTyped.clear();
		postponedSubTrees.clear();
		potentialContainersOfLocalArgumentsVariable.clear();
	}

	/**
	 * Compiles some debug information for this ASTMetaInfoCache.
	 */
	public String dumpDebugInfo(boolean showSourceCode, boolean showOtherResources) {
		final StringBuilder sb = new StringBuilder();

		final String uriStr = resource != null ? String.valueOf(resource.getURI()) : null;
		sb.append("BEGIN ASTMetaInfoCache debug info for resource: " + uriStr + "\n");
		sb.append("cache's isProcessingInProgress == " + isProcessingInProgress + "\n");
		sb.append("cache's isFullyProcessed == " + isFullyProcessed + "\n");
		sb.append("cache's hasBrokenAST == " + hasBrokenAST + "\n");
		sb.append("cache's astNodesCurrentlyBeingTyped == " + astNodesCurrentlyBeingTyped + "\n");
		sb.append("resource' fullyPostProcessed = " + (resource != null ? resource.isFullyProcessed()
				: "don't know. Resource is null.") + "\n");
		sb.append("resource' isPostProcessing = "
				+ (resource != null ? resource.isProcessing() : "don't know. Resource is null") + "\n");

		if (showSourceCode) {
			final Script script = resource != null ? resource.getScript() : null;
			final INode scriptNode = script != null && !script.eIsProxy() ? NodeModelUtils.findActualNodeFor(script)
					: null;
			final String code = scriptNode != null ? scriptNode.getText() : null;
			sb.append("source code:\n------------\n" + code + "\n------------\n");
		}

		if (showOtherResources) {
			sb.append("Resources in containing resource set:\n");
			final ResourceSet resSet = resource != null ? resource.getResourceSet() : null;
			if (resSet != null) {
				// we know: resource!=null
				for (Resource otherRes : resSet.getResources()) {
					if (otherRes != null) {
						sb.append(otherRes.getURI());
						final CacheAdapter cacheAdapter = (CacheAdapter) EcoreUtil.getAdapter(resource.eAdapters(),
								CacheAdapter.class);
						final ASTMetaInfoCache astCache = cacheAdapter != null
								? cacheAdapter.get(ASTMetaInfoCache.class)
								: null;
						if (astCache != null) {
							if (astCache == this) {
								sb.append(" <=== this ASTMetaInfoCache is attached here!!!!");
							}
							final String nestedInfo = astCache.dumpDebugInfo(false, false);
							final String nestedInfoIndented = ("\n" + nestedInfo).replace("\n", "\n    ");
							sb.append(nestedInfoIndented);
						}
						sb.append("\n");
					}
				}
			}
			sb.append("END of list of resources in containing resource set\n");
		}
		sb.append("END ASTMetaInfoCache debug info for resource: " + uriStr);
		return sb.toString();
	}
}
