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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper.Callable;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * {@link PolyProcessor} delegates here for processing array literals.
 *
 * @see PolyProcessor#inferType(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,ASTMetaInfoCache)
 * @see PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)
 */
@Singleton
class PolyProcessor_CallExpression extends AbstractPolyProcessor {

	@Inject
	private PolyProcessor polyProcessor;

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * BEFORE CHANGING THIS METHOD, READ THIS:
	 * {@link PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)}
	 */
	@SuppressWarnings("unused")
	TypeRef processCallExpression(RuleEnvironment G, ParameterizedCallExpression callExpr,
			TypeRef expectedTypeRef, InferenceContext infCtx, ASTMetaInfoCache cache) {

		Expression target = callExpr.getTarget();
		// IMPORTANT: do not use #processExpr() here (if target is a PolyExpression, it has been processed in a
		// separate, independent inference!)
		TypeRef targetTypeRef = ts.type(G, target);
		RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
		tsh.addSubstitutions(G2, targetTypeRef);
		Callable callable = tsh.getCallableTypeRef(G2, targetTypeRef);
		if (callable == null || !callable.getSignatureTypeRef().isPresent()) {
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		}
		FunctionTypeExprOrRef sigTypeRef = callable.getSignatureTypeRef().get();

		if (!N4JSLanguageUtils.isPoly(sigTypeRef, callExpr)) {
			TypeRef result = ts.type(G2, callExpr);
			// do not store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache)
			return result;
		}

		// create an inference variable for each type parameter of fteor
		Map<TypeVariable, InferenceVariable> typeParam2infVar = new LinkedHashMap<>(); // type parameter of fteor ->
																						// inference variable
		for (TypeVariable typeParam : sigTypeRef.getTypeVars()) {
			typeParam2infVar.put(typeParam, infCtx.newInferenceVariable());
		}

		processParameters(G2, cache, infCtx, callExpr, sigTypeRef, typeParam2infVar);

		// create temporary type (i.e. may contain inference variables)
		TypeRef resultTypeRefRaw = sigTypeRef.getReturnTypeRef();
		TypeRef resultTypeRef = subst(resultTypeRefRaw, G2, typeParam2infVar);

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved(solution -> handleOnSolved(G2, cache, callExpr, resultTypeRef, typeParam2infVar, solution));

		// return temporary type of callExpr (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * Processes all parameters and derives constraints from their bounds and matching types.
	 */
	private void processParameters(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			ParameterizedCallExpression callExpr, FunctionTypeExprOrRef fteor,
			Map<TypeVariable, InferenceVariable> typeParam2infVar) {
		//
		// (1) derive constraints from the bounds of the type parameters
		//
		EList<TypeVariable> funcTypeVars = fteor.getTypeVars();
		for (TypeVariable currTypeVar : funcTypeVars) {
			// don't use currTypeVar.getDeclaredUpperBound() in next line!
			TypeRef currUB = fteor.getTypeVarUpperBound(currTypeVar);
			if (currUB == null) {
				currUB = N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G);
			}
			// constraint: currTypeVar <: current upper bound
			TypeRef leftTypeRef = TypeUtils.createTypeRef(currTypeVar);
			TypeRef leftTypeRefSubst = subst(leftTypeRef, G, typeParam2infVar);
			TypeRef rightTypeRef = currUB;
			TypeRef rightTypeRefSubst = subst(rightTypeRef, G, typeParam2infVar);
			infCtx.addConstraint(leftTypeRefSubst, rightTypeRefSubst, Variance.CO);
		}

		//
		// (2) derive constraints from matching type of each provided argument to type of corresponding fpar
		//
		int argsSize = callExpr.getArguments().size();
		for (var i = 0; i < argsSize; i++) {
			Expression arg = callExpr.getArguments().get(i) == null ? null
					: callExpr.getArguments().get(i).getExpression();
			TFormalParameter curr_fpar = fteor.getFparForArgIdx(i);
			if (arg != null && curr_fpar != null) {
				TypeRef fparTypeRef = curr_fpar.getTypeRef();
				TypeRef fparTypeRefSubst = subst(fparTypeRef, G, typeParam2infVar);
				TypeRef argType = polyProcessor.processExpr(G, arg, fparTypeRefSubst, infCtx, cache);
				if (argType != null) {
					// constraint: argType <: fpar.type
					infCtx.addConstraint(fparTypeRefSubst, argType, Variance.CONTRA);
					// (note: no substitution in argType required, because it cannot contain any of the new inference
					// variables introduced above)
				}
			} else if (arg != null) {
				// more arguments provided than fpars available
				// -> this is an error case, but make sure to process the surplus arguments to avoid
				// inconsistencies later on (cache misses etc.)
				polyProcessor.processExpr(G, arg, null, infCtx, cache);
			}
		}

		//
		// (3) derive constraints from matching expected return type to return type of function
		//
		// --> not required here (will be done by caller)
	}

	/**
	 * Writes final types to cache.
	 */
	private void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, ParameterizedCallExpression callExpr,
			TypeRef resultTypeRef, Map<TypeVariable, InferenceVariable> typeParam2infVar,
			Optional<Map<InferenceVariable, TypeRef>> solution) {
		if (solution.isPresent()) {
			// success case:
			cache.storeType(callExpr, applySolution(resultTypeRef, G, solution.get()));
			List<TypeRef> inferredTypeArgs = toList(map(typeParam2infVar.values(), iv -> solution.get().get(iv)));
			cache.storeInferredTypeArgs(callExpr, inferredTypeArgs);
		} else {
			// failure case (unsolvable constraint system)
			// to avoid leaking inference variables, replace them by their original type parameter
			Map<InferenceVariable, TypeRef> fakeSolution = new HashMap<>();
			for (Entry<TypeVariable, InferenceVariable> e : typeParam2infVar.entrySet()) {
				fakeSolution.put(e.getValue(), TypeUtils.createTypeRef(e.getKey()));
			}
			cache.storeType(callExpr, applySolution(resultTypeRef, G, fakeSolution));
			cache.storeInferredTypeArgs(callExpr, Collections.emptyList());
		}
		// PolyProcessor#isResponsibleFor(TypableElement) claims responsibility of AST nodes of type 'Argument'
		// contained in a ParameterizedCallExpression which is poly, so we are responsible for storing the types of
		// those 'Argument' nodes in cache
		// (note: compare this with similar handling of 'ArrayElement' nodes in PolyProcessor_ArrayLiteral)
		for (Argument arg : callExpr.getArguments()) {
			Expression expr = arg == null ? null : arg.getExpression();
			TypeRef exprType = (expr == null) ? null : getFinalResultTypeOfNestedPolyExpression(expr);
			if (exprType != null) {
				cache.storeType(arg, exprType);
			} else {
				cache.storeType(arg, TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
			}
		}
	}
}
