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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;

/**
 * The <em>AST meta-info cache</em> is created and filled with information during post-processing of an N4JS resource
 * and contains meta information on the AST, such as types of AST nodes, links from declarations to all places where the
 * declared element is used, etc.
 * <p>
 * It is created only for {@link N4JSResource}s and only for such N4JS resources that are <u>loaded from source code</u>
 * and therefore have an AST (not those that are loaded from the Xtext index / TModule).
 */
public final class ASTMetaInfoCache {

	// ################################################################################################################
	// main content of the cache
	// (add new properties here; getters should be public, setters should have package visibility)

	private final N4JSResource resource;
	private final String projectName;
	private final boolean hasBrokenAST;
	private final ASTFlowInfo flowInfo;
	private final Map<TypableElement, TypeRef> actualTypes = new HashMap<>();
	private final Map<ParameterizedCallExpression, List<TypeRef>> inferredTypeArgs = new HashMap<>();
	private final Map<Expression, CompileTimeValue> compileTimeValue = new HashMap<>();
	private final Map<VariableDeclaration, List<EObject>> localVariableReferences = new HashMap<>();

	/* package */ ASTMetaInfoCache(N4JSResource resource, boolean hasBrokenAST, ASTFlowInfo flowInfo) {
		this.resource = resource;
		this.projectName = resource.getModule().getProjectName();
		this.hasBrokenAST = hasBrokenAST;
		this.flowInfo = flowInfo;
	}

	/** @return the {@link N4JSResource} the receiving cache belongs to. */
	public N4JSResource getResource() {
		return resource;
	}

	/** @return the name of the containing project; see {@link TModule#getProjectName()}. */
	public String getProjectName() {
		return projectName;
	}

	/** @return if the resource of this cache has a broken AST. */
	public boolean hasBrokenAST() {
		return hasBrokenAST;
	}

	/** @return the flow information for this resource */
	public ASTFlowInfo getFlowInfo() {
		return flowInfo;
	}

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Returns the actual type of the given astNode as stored in the cache, or <code>null</code> if not available.
	 */
	public TypeRef getTypeFailSafe(TypableElement astNode) {
		return actualTypes.get(astNode);
	}

	/**
	 * <b>IMPORTANT:</b> most clients should use {@link N4JSTypeSystem#type(RuleEnvironment, TypableElement)} instead!
	 * <p>
	 * Returns the actual type of the given astNode as stored in the cache. Throws exception if not available.
	 */
	public TypeRef getType(TypableElement astNode) {
		final TypeRef result = getTypeFailSafe(astNode);
		if (result == null) {
			if (resource.isFullyProcessed() && resource.getPostProcessingThrowable() != null) {
				// post processing was attempted but failed, so we expect the cache to be incompletely filled
				// -> do not throw exception in this case, because it is a follow-up issue
				return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
			}
			throw UtilN4.reportError(new IllegalStateException("cache miss: no actual type in cache for AST node: "
					+ astNode + " in resource: " + resource.getURI()));
		}
		return result;
	}

	/* package */ void storeType(TypableElement astNode, TypeRef actualType) {
		if (!isPostProcessing()) {
			throw new IllegalStateException(
					"attempt to store type in cache while post-processing not in progress");
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
		if (!isPostProcessing()) {
			throw new IllegalStateException(
					"attempt to store inferred type arguments in cache while post-processing not in progress");
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
		if (!isPostProcessing()) {
			throw new IllegalStateException(
					"attempt to store compile-time value in cache while post-processing not in progress");
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
	// helper variables used *only* internally by post-processors in package org.eclipse.n4js.postprocessing for
	// temporary data during post-processing

	// @formatter:off

	/* package */ final Set<EObject> forwardProcessedSubTrees = new LinkedHashSet<>();
	/* package */ final Set<EObject> astNodesCurrentlyBeingTyped = new LinkedHashSet<>();
	/* package */ final Queue<EObject> postponedSubTrees = new LinkedList<>(); // using LinkedList as FIFO queue, here
	/* package */ final List<FunctionOrFieldAccessor> potentialContainersOfLocalArgumentsVariable = new LinkedList<>();
	/* package */ final Set<IdentifiableElement> elementsReferencedAtRuntime = new LinkedHashSet<>();
	/* package */ final Set<TModule> modulesReferencedAtLoadtimeForInheritance = new LinkedHashSet<>();

	// @formatter:on

	/* package */ void clearTemporaryPostProcessingData() {
		forwardProcessedSubTrees.clear();
		astNodesCurrentlyBeingTyped.clear();
		postponedSubTrees.clear();
		potentialContainersOfLocalArgumentsVariable.clear();
		elementsReferencedAtRuntime.clear();
		modulesReferencedAtLoadtimeForInheritance.clear();
	}

	/* package */ boolean isPostProcessing() {
		return resource.isPostProcessing();
	}

	/* package */ boolean isFullyProcessed() {
		return resource.isFullyProcessed();
	}
}
