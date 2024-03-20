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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getCancelIndicator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DestructureHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.service.OperationCanceledManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The main poly processor responsible for typing poly expressions using a constraint-based approach.
 * <p>
 * It tells other processors which AST nodes it is responsible for (see
 * {@link PolyProcessor#isResponsibleFor(TypableElement) isResponsibleFor()}) and which AST nodes are an entry point to
 * constraint-based type inference (see {@link PolyProcessor#isEntryPoint(TypableElement) isEntryPoint()}). For those
 * "entry points" method {@link PolyProcessor#inferType(RuleEnvironment,Expression,ASTMetaInfoCache) inferType()} should
 * be invoked by the other processors (mainly the TypeProcessor).
 */
@Singleton
class PolyProcessor extends AbstractPolyProcessor {

	@Inject
	private PolyProcessor_ArrayLiteral arrayLiteralProcessor;
	@Inject
	private PolyProcessor_ObjectLiteral objectLiteralProcessor;
	@Inject
	private PolyProcessor_FunctionExpression functionExpressionProcessor;
	@Inject
	private PolyProcessor_CallExpression callExpressionProcessor;

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private DeclMergingHelper declMergingHelper;

	@Inject
	private DestructureHelper destructureHelper;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	// ################################################################################################################

	/**
	 * Tells if the given AST node's type should be inferred through constraint-based type inference. In that case, no
	 * other processor is allowed to add a type for this node to the {@link ASTMetaInfoCache}!
	 */
	boolean isResponsibleFor(TypableElement astNode) {
		EObject parent = astNode.eContainer();
		return isPoly(astNode)
				|| (astNode instanceof Argument && parent instanceof ParameterizedCallExpression && isPoly(parent))
				|| (astNode instanceof FormalParameter && parent instanceof FunctionExpression && isPoly(parent))
				|| (astNode instanceof FormalParameter && parent instanceof PropertyMethodDeclaration && isPoly(parent))
				|| (astNode instanceof ArrayElement && parent instanceof ArrayLiteral && isPoly(parent))
				|| (astNode instanceof PropertyAssignment && parent instanceof ObjectLiteral && isPoly(parent));
		// note in previous line:
		// even if the PropertyAssignment itself is NOT poly, we claim responsibility for it if the containing
		// ObjectLiteral is poly
	}

	/**
	 * Tells if the given AST node is an entry point to constraint-based type inference. In that case, and only in that
	 * case, method {@link #inferType(RuleEnvironment,Expression,ASTMetaInfoCache) inferType()} must be invoked for this
	 * AST node.
	 */
	boolean isEntryPoint(TypableElement astNode) {
		return isRootPoly(astNode);
	}

	// ################################################################################################################

	/**
	 * Main method for inferring the type of poly expressions, i.e. for constraint-based type inference. It should be
	 * invoked for all AST nodes for which method {@link #isEntryPoint(TypableElement) isEntryPoint()} returns
	 * <code>true</code> (and only for such nodes!). This will ensure that this method will be called for all roots of
	 * trees of nested poly expressions (including such trees that only consist of a root without children) but not for
	 * the nested children.
	 * <p>
	 * This method, together with its delegates, is responsible for adding to the cache types for all the following AST
	 * nodes:
	 * <ol>
	 * <li>the given root poly expression <code>rootPoly</code>,
	 * <li>all nested child poly expressions,
	 * <li>some nested elements that aren't expressions but closely belong to one of the above expressions, e.g. formal
	 * parameters contained in a function expression (see code of {@link #isResponsibleFor(TypableElement)} for which
	 * elements are included here).
	 * </ol>
	 * <p>
	 * The overall process of constraint-based type inference is as follows:
	 * <ol>
	 * <li>create a new, empty {@link InferenceContext} called <code>IC</code>.
	 * <li>invoke method {@link #processExpr(RuleEnvironment,Expression,TypeRef,InferenceContext,ASTMetaInfoCache)
	 * #processExpr()} for the given root poly expression and all its direct and indirect child poly expressions. This
	 * will ...
	 * <ol type="a">
	 * <li>add to <code>IC</code> (i) inference variables for all types to be inferred and (ii) appropriate constraints
	 * derived from the poly expressions and their relations.
	 * <li>register <code>onSolved</code> handlers to <code>IC</code> (see below what these handlers are doing).
	 * </ol>
	 * <li>solve the entire constraint system, i.e. invoke {@link InferenceContext#solve()} on <code>IC</code>.
	 * <li>once solution is done (no matter if successful or failed) <code>IC</code> will automatically trigger the
	 * <code>onSolved</code> handlers:
	 * <ul>
	 * <li>in the success case, the handlers will use the solution in <code>IC</code> to add types to the cache for the
	 * given root poly expression and all its nested child poly expressions (and also for contained, typable elements
	 * such as fpars of function expressions).
	 * <li>in the failure case, the handlers will add fall-back types to the cache.
	 * </ul>
	 * </ol>
	 */
	void inferType(RuleEnvironment G, Expression rootPoly, ASTMetaInfoCache cache) {
		// create a new constraint system
		InferenceContext infCtx = new InferenceContext(ts, tsh, declMergingHelper, operationCanceledManager,
				getCancelIndicator(G), G);

		// in plain JS files, we want to avoid searching for a solution (to avoid performance problems in some JS files
		// with extremely large array/object literals) but to avoid having to deal with this case with additional code,
		// we still build a constraint system as usual (TEMPORARAY HACK)
		// TODO find proper way to deal with extremely large array/object literals
		if (jsVariantHelper.doomTypeInference(rootPoly)) {
			infCtx.addConstraint(TypeConstraint.FALSE);
		}

		TypeRef expectedTypeByLiteralStructure = destructureHelper.calculateExpectedType(rootPoly, G, infCtx);
		// we have to pass the expected type to the #getType() method, so retrieve it first
		// (until the expectedType judgment is integrated into AST traversal, we have to invoke this judgment here;
		// in case of not-well-behaving expectedType rules, we use 'null' as expected type, i.e. no expectation)
		// TODO integrate expectedType judgment into AST traversal and remove #isProblematicCaseOfExpectedType()
		TypeRef expectedTypeRef = null;
		if (expectedTypeByLiteralStructure != null) {
			expectedTypeRef = expectedTypeByLiteralStructure;
		} else if (!isProblematicCaseOfExpectedType(rootPoly)) {
			expectedTypeRef = ts.expectedType(G, rootPoly.eContainer(), rootPoly);
		}

		// call #processExpr() (this will recursively call #processExpr() on nested expressions, even if non-poly)
		TypeRef typeRef = processExpr(G, rootPoly, expectedTypeRef, infCtx, cache);

		// add constraint to ensure that type of 'rootPoly' is subtype of its expected type
		if (!TypeUtils.isVoid(typeRef)) {
			if (expectedTypeRef != null && expectedTypeByLiteralStructure == null) {
				infCtx.addConstraint(0, typeRef, expectedTypeRef, Variance.CO);
			}
		}

		// compute solution
		// (note: we're not actually interested in the solution, here; we just want to make sure to trigger the
		// onSolved handlers registered by the #process*() methods of the other poly processors; see responsibilities of
		// #processExpr(RuleEnvironment, Expression, TypeRef, InferenceContext, ASTMetaInfoCache)
		infCtx.solve();
	}

	/**
	 * Key method for handling poly expressions.
	 * <p>
	 * It has the following responsibilities:
	 * <ul>
	 * <li>if given expression is non-poly: simply return its type<br>
	 * (note: in <em>this</em> case this method won't process nested expressions in any way).
	 * <li>if given expression is poly:
	 * <ol>
	 * <li>introduce a new inference variable to the given inference context for each type to be inferred for the given
	 * poly expression (usually only 1, but may be several, e.g. for a function expression we introduce an inference
	 * variable for the return type and each fpar),
	 * <li>add appropriate constraints to the given inference context,
	 * <li>recursively invoke this method for nested expressions (no matter if poly or non-poly).
	 * <li>register to the given inference context an <code>onSolved</code> handler that will - after the inference
	 * context will have been solved - add all required <b>final types</b> for 'expr' and its non-expression children
	 * (e.g. fpars) to the typing cache.
	 * <li>return <b>temporary type</b> of the given expression <code>expr</code>.
	 * </ol>
	 * </ul>
	 * IMPORTANT: the "temporary" type may contain inference variables; the "final" types must be proper, i.e. must not
	 * contain any inference variables!
	 */
	protected TypeRef processExpr(RuleEnvironment G, Expression expr, TypeRef expectedTypeRef,
			InferenceContext infCtx, ASTMetaInfoCache cache) {

		if (isPoly(expr)) {
			// poly -> delegate this to the appropriate, specific PolyProcessor
			if (expr instanceof ArrayLiteral) {
				return arrayLiteralProcessor.processArrayLiteral(G, (ArrayLiteral) expr, expectedTypeRef, infCtx,
						cache);
			} else if (expr instanceof ObjectLiteral) {
				return objectLiteralProcessor.processObjectLiteral(G, (ObjectLiteral) expr, expectedTypeRef, infCtx,
						cache);
			} else if (expr instanceof FunctionExpression) {
				return functionExpressionProcessor.processFunctionExpression(G, (FunctionExpression) expr,
						expectedTypeRef, infCtx, cache);
			} else if (expr instanceof ParameterizedCallExpression) {
				return callExpressionProcessor.processCallExpression(G, (ParameterizedCallExpression) expr,
						expectedTypeRef, infCtx, cache);
			} else if (expr instanceof ConditionalExpression) {
				ConditionalExpression ce = (ConditionalExpression) expr;
				if (isPoly(ce.getTrueExpression())) {
					TypeRef typeRef = processExpr(G, ce.getTrueExpression(), expectedTypeRef, infCtx, cache);
					// store a copy of the inferred type also at the conditional expression node
					infCtx.onSolved(solution -> cache.storeType(ce,
							TypeUtils.copy(cache.getTypeFailSafe(ce.getTrueExpression()))));
					return typeRef;
				} else if (isPoly(ce.getFalseExpression())) {
					TypeRef typeRef = processExpr(G, ce.getFalseExpression(), expectedTypeRef, infCtx, cache);
					// store a copy of the inferred type also at the conditional expression node
					infCtx.onSolved(solution -> cache.storeType(ce,
							TypeUtils.copy(cache.getTypeFailSafe(ce.getFalseExpression()))));
					return typeRef;
				} else {
					throw new IllegalArgumentException("missing case in #processExpr() for poly expression: " + expr);
				}
			}
			throw new IllegalArgumentException("missing case in #processExpr() for poly expression: " + expr);
		} else {
			// not poly -> directly infer type via type system
			TypeRef result = ts.type(G, expr);
			// do *not* store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache!)
			return result;
		}
	}

	/**
	 * Returns true if we are not allowed to ask for the expected type of 'node', because this would lead to illegal
	 * forward references (temporary).
	 */
	private boolean isProblematicCaseOfExpectedType(EObject node) {
		return node != null && node.eContainer() instanceof RelationalExpression;
	}
}
