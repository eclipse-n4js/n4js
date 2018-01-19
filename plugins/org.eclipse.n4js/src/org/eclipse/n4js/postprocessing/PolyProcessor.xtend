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
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.misc.DestructNode
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xsemantics.runtime.RuleEnvironment
import org.eclipse.xtext.service.OperationCanceledManager

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * The main poly processor responsible for typing poly expressions using a constraint-based approach.
 * <p>
 * It tells other processors which AST nodes it is responsible for (see {@link PolyProcessor#isResponsibleFor(TypableElement) isResponsibleFor()})
 * and which AST nodes are an entry point to constraint-based type inference (see {@link PolyProcessor#isEntryPoint(TypableElement) isEntryPoint()}).
 * For those "entry points" method {@link PolyProcessor#inferType(RuleEnvironment,Expression,ASTMetaInfoCache) inferType()}
 * should be invoked by the other processors (mainly the TypeProcessor).
 */
@Singleton
package class PolyProcessor extends AbstractPolyProcessor {

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
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	// ################################################################################################################


	/**
	 * Tells if the given AST node's type should be inferred through constraint-based type inference. In that case,
	 * no other processor is allowed to add a type for this node to the {@link ASTMetaInfoCache}!
	 */
	def package boolean isResponsibleFor(TypableElement astNode) {
		astNode.isPoly
		|| (astNode instanceof Argument && astNode.eContainer instanceof ParameterizedCallExpression && astNode.eContainer.isPoly)
		|| (astNode instanceof FormalParameter && astNode.eContainer instanceof FunctionExpression && astNode.eContainer.isPoly)
		|| (astNode instanceof FormalParameter && astNode.eContainer instanceof PropertyMethodDeclaration && astNode.eContainer.isPoly)
		|| (astNode instanceof ArrayElement && astNode.eContainer instanceof ArrayLiteral && astNode.eContainer.isPoly)
		|| (astNode instanceof PropertyAssignment && astNode.eContainer instanceof ObjectLiteral && astNode.eContainer.isPoly)
		// note in previous line:
		// even if the PropertyAssignment itself is NOT poly, we claim responsibility for it if the containing ObjectLiteral is poly
	}

	/**
	 * Tells if the given AST node is an entry point to constraint-based type inference. In that case, and only in that
	 * case, method {@link #inferType(RuleEnvironment,Expression,ASTMetaInfoCache) inferType()} must be invoked for this
	 * AST node.
	 */
	def package boolean isEntryPoint(TypableElement astNode) {
		astNode.isRootPoly
	}


	// ################################################################################################################


	/**
	 * Main method for inferring the type of poly expressions, i.e. for constraint-based type inference. It should be
	 * invoked for all AST nodes for which method {@link #isEntryPoint(TypableElement) isEntryPoint()}
	 * returns <code>true</code> (and only for such nodes!). This will ensure that this method will be called for all
	 * roots of trees of nested poly expressions (including such trees that only consist of a root without children) but
	 * not for the nested children.
	 * <p>
	 * This method, together with its delegates, is responsible for adding to the cache types for all the following
	 * AST nodes:
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
	 * <li>invoke method {@link #processExpr(RuleEnvironment,Expression,TypeRef,InferenceContext,ASTMetaInfoCache) #processExpr()}
	 * for the given root poly expression and all its direct and indirect child poly expressions. This will ...
	 *     <ol type="a">
	 *     <li>add to <code>IC</code> (i) inference variables for all types to be inferred and (ii) appropriate
	 *         constraints derived from the poly expressions and their relations.
	 *     <li>register <code>onSolved</code> handlers to <code>IC</code> (see below what these handlers are doing).
	 *     </ol>
	 * <li>solve the entire constraint system, i.e. invoke {@link #solve()} on <code>IC</code>.
	 * <li>once solution is done (no matter if successful or failed) <code>IC</code> will automatically trigger the
	 *     <code>onSolved</code> handlers:
	 *     <ul>
	 *     <li>in the success case, the handlers will use the solution in <code>IC</code> to add types to the cache for
	 *         the given root poly expression and all its nested child poly expressions (and also for contained, typable
	 *         elements such as fpars of function expressions).
	 *     <li>in the failure case, the handlers will add fall-back types to the cache.
	 *     </ul>
	 * </ol>
	 */
	def package void inferType(RuleEnvironment G, Expression rootPoly, ASTMetaInfoCache cache) {
		// create a new constraint system
		val InferenceContext infCtx = new InferenceContext(ts, tsh, operationCanceledManager, G.cancelIndicator, G);

		// in plain JS files, we want to avoid searching for a solution (to avoid performance problems in some JS files
		// with extremely large array/object literals) but to avoid having to deal with this case with additional code,
		// we still build a constraint system as usual (TEMPORARAY HACK)
		// TODO find proper way to deal with extremely large array/object literals
		if (jsVariantHelper.doomTypeInference(rootPoly)) {
			infCtx.addConstraint(TypeConstraint.FALSE);
		}

		// we have to pass the expected type to the #getType() method, so retrieve it first
		// (until the expectedType judgment is integrated into AST traversal, we have to invoke this judgment here;
		// in case of not-well-behaving expectedType rules, we use 'null' as expected type, i.e. no expectation)
		// TODO integrate expectedType judgment into AST traversal and remove #isProblematicCaseOfExpectedType()
		var expectedTypeRef = if (!rootPoly.isProblematicCaseOfExpectedType) {
				ts.expectedTypeIn(G, rootPoly.eContainer(), rootPoly).getValue();
			};

		// In case of destructure pattern, we can calculate the expected type based on the structure of the destructure pattern.
		var rootDestructNode = if (rootPoly.eContainer instanceof VariableBinding) {
			DestructNode.unify(rootPoly.eContainer as VariableBinding)
		} else if (rootPoly.eContainer instanceof AssignmentExpression) {
			DestructNode.unify(rootPoly.eContainer as AssignmentExpression)
		} else if (rootPoly.eContainer instanceof ForStatement) {
			DestructNode.unify(rootPoly.eContainer as ForStatement)
		}
		else {
			null
		};

		// call #processExpr() (this will recursively call #processExpr() on nested expressions, even if non-poly)
		var typeRef = processExpr(G, rootPoly, expectedTypeRef, infCtx, cache);

		if (rootDestructNode !== null) {
			expectedTypeRef = calculateExpectedType(rootDestructNode, G)
		}
		if (rootDestructNode !== null) {
			// We need to adjust 'typeRef' in case of ForStatement
			// In the example: for(var [a4,b4: number] of [["Hi",42],["Ho",42]])
			// 		expectedTypeRef = Iterable2<any,number>
			//		typeRef			= Array<α>
			// Hence, we need to adjust typeRef to α
			if (expectedTypeRef !== null && rootDestructNode.astElement.eContainer instanceof ForStatement) {
				// Extract the type argument of the Array type
				if (typeRef.declaredType == RuleEnvironmentExtensions.arrayType(G)) {
					val singleTypeArgOfArray =  typeRef.typeArgs.get(0);
					if (singleTypeArgOfArray instanceof TypeRef) {
						typeRef = singleTypeArgOfArray
					}
				}
			}
		}

		// add constraint to ensure that type of 'rootPoly' is subtype of its expected type
		if (!TypeUtils.isVoid(typeRef)) {
			if (expectedTypeRef !== null) {
				infCtx.addConstraint(typeRef, expectedTypeRef, Variance.CO);
			}
		}

		// compute solution
		// (note: we're not actually interested in the solution, here; we just want to make sure to trigger the
		// onSolved handlers registered by the #process*() methods of the other poly processors; see responsibilities of
		// #processExpr(RuleEnvironment, Expression, TypeRef, InferenceContext, ASTMetaInfoCache)
		infCtx.solve;
	}

	/** Calculate expected type of a destructure pattern based on its structure */
	private def TypeRef calculateExpectedType(DestructNode destructNode, RuleEnvironment G) {
		val elementTypes = new ArrayList<TypeArgument>();
		val elementMembers = new ArrayList<TStructMember>();
		val elemCount = destructNode.nestedNodes.size
		for (nestedNode : destructNode.nestedNodes) {
			val elemExpectedType = if (nestedNode.nestedNodes !== null && nestedNode.nestedNodes.size > 0) {
				// Recursively calculate the expected type of the nested child
				calculateExpectedType(nestedNode, G)
			} else {
				// Extract type of leaf node
				nestedNode.createTypeFromLeafDestructNode(G)
			}

			if (nestedNode.propName !== null) {
				// We are dealing with object literals, hence create TStructMembers to construct a ParameterizedTypeRefStructural
				val field = TypesFactory.eINSTANCE.createTStructField
				field.name = nestedNode.propName;
				field.typeRef = elemExpectedType
				elementMembers.add(field)
			} else {
				elementTypes.add(elemExpectedType)
			}
		}

		var retTypeRef = if (elementMembers.size > 0) {
			TypeUtils.createParameterizedTypeRefStructural(G.objectType, TypingStrategy.STRUCTURAL, elementMembers)
		} else if (elementTypes.size > 0) {
			if (elemCount == 1) {
				 G.arrayTypeRef(elementTypes.get(0))
			} else if (elemCount > 1){
				G.iterableNTypeRef(elemCount, elementTypes);
//				G.iterableNTypeRef(elemCount, elementTypes.map[TypeRefsFactory.eINSTANCE.createWildcard]);
			} else {
				null
			}
		} else {
			throw new IllegalStateException("elementTypes and elementMembers can not both contain elements at the same time.")
		}

		return retTypeRef;
	}

	/** Create expected type for a leaf DestructNode */
	private def createTypeFromLeafDestructNode(DestructNode leafNode, RuleEnvironment G) {
		val varDecl = leafNode.varDecl
		val varRef = leafNode.varRef
		if (varDecl !== null) {
			// If it is a variable declaration, simply retrieve the declared type
			var declaredTypeRef = varDecl.declaredTypeRef;
			if (declaredTypeRef !== null) {
				return declaredTypeRef
			}
		} else if (varRef !== null) {
			// It is a variable reference, retrieve the declared type of the variable
			if (varRef.id instanceof VariableDeclaration && (varRef.id as VariableDeclaration).declaredTypeRef !== null) {
				return (varRef.id as VariableDeclaration).declaredTypeRef
			}
		}
		// In case the expected type does not exist, simply return 'any' type (top type)
		return G.topTypeRef
	}

	/**
	 * Key method for handling poly expressions.
	 * <p>
	 * It has the following responsibilities:
	 * <ul>
	 * <li>if given expression is non-poly: simply return its type<br>
	 *     (note: in <em>this</em> case this method won't process nested expressions in any way).
	 * <li>if given expression is poly:
	 *     <ol>
	 *     <li>introduce a new inference variable to the given inference context for each type to be inferred for the
	 *         given poly expression (usually only 1, but may be several, e.g. for a function expression we introduce an
	 *         inference variable for the return type and each fpar),
	 *     <li>add appropriate constraints to the given inference context,
	 *     <li>recursively invoke this method for nested expressions (no matter if poly or non-poly).
	 *     <li>register to the given inference context an <code>onSolved</code> handler that will - after the inference
	 *         context will have been solved - add all required <b>final types</b> for 'expr' and its non-expression
	 *         children (e.g. fpars) to the typing cache.
	 *     <li>return <b>temporary type</b> of the given expression <code>expr</code>.
	 *     </ol>
	 * </ul>
	 * IMPORTANT: the "temporary" type may contain inference variables; the "final" types must be proper, i.e. must not
	 * contain any inference variables!
	 */
	def protected TypeRef processExpr(RuleEnvironment G, Expression expr, TypeRef expectedTypeRef,
		InferenceContext infCtx, ASTMetaInfoCache cache) {

		if (isPoly(expr)) {
			// poly -> delegate this to the appropriate, specific PolyProcessor
			return switch(expr) {
				ArrayLiteral:
					arrayLiteralProcessor.processArrayLiteral(G, expr, expectedTypeRef, infCtx, cache)
				ObjectLiteral:
					objectLiteralProcessor.processObjectLiteral(G, expr, expectedTypeRef, infCtx, cache)
				FunctionExpression:
					functionExpressionProcessor.processFunctionExpression(G, expr, expectedTypeRef, infCtx, cache)
				ParameterizedCallExpression:
					callExpressionProcessor.processCallExpression(G, expr, expectedTypeRef, infCtx, cache)
				default:
					throw new IllegalArgumentException("missing case in #processExpr() for poly expression: " + expr)
			};
		} else {
			// not poly -> directly infer type via type system
			val result = ts.type(G, expr).getValue();
			// do *not* store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache!)
			return result;
		}
	}

	/**
	 * Returns true if we are not allowed to ask for the expected type of 'node', because this would lead to illegal
	 * forward references (temporary).
	 */
	def private boolean isProblematicCaseOfExpectedType(EObject node) {
		return node?.eContainer instanceof RelationalExpression
	}
}
