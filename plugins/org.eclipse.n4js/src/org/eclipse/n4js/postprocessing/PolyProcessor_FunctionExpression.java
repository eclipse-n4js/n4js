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

import static com.google.common.collect.Iterators.concat;
import static com.google.common.collect.Iterators.singletonIterator;
import static java.util.Collections.emptyList;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addFixedCapture;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addTypeMappings;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getBuiltInTypeScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
class PolyProcessor_FunctionExpression extends AbstractPolyProcessor {
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private ASTProcessor astProcessor;

	/**
	 * BEFORE CHANGING THIS METHOD, READ THIS:
	 * {@link PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)}
	 */
	TypeRef processFunctionExpression(RuleEnvironment G, FunctionExpression funExpr, TypeRef expectedTypeRef,
			InferenceContext infCtx, ASTMetaInfoCache cache) {
		TFunction fun = (TFunction) funExpr.getDefinedType(); // types builder will have created this already

		if (!isPoly(funExpr)) { // funExpr has declared types on all fpars and explicitly declared return type
			// can't use xsemantics here, because it would give us a DeferredTypeRef
			// return ts.type(G, funExpr).getValue();
			FunctionTypeExpression funTE = TypeUtils.createFunctionTypeExpression(null, emptyList(), fun.getFpars(),
					fun.getReturnTypeRef()); // FIXME support for declared this type!!
			// do not store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache)
			return funTE;
		}

		// prepare temporary result type reference
		FunctionTypeExpression funTE = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();

		if (fun.getDeclaredThisType() != null) {
			funTE.setDeclaredThisType(TypeUtils.copy(fun.getDeclaredThisType()));
		}

		if (!fun.getTypeVars().isEmpty()) {
			funTE.getOwnedTypeVars().addAll(toList(map(fun.getTypeVars(), tv -> TypeUtils.copy(tv))));
		}

		processFormalParameters(G, cache, infCtx, funExpr, funTE, expectedTypeRef);
		processReturnType(G, cache, infCtx, funExpr, funTE);

		funTE.setReturnValueMarkedOptional(expectedTypeRef instanceof FunctionTypeExprOrRef
				&& ((FunctionTypeExprOrRef) expectedTypeRef).isReturnValueOptional());

		// create temporary type (i.e. may contain inference variables)

		FunctionTypeExpression resultTypeRef;
		if (fun.getTypeVars().isEmpty()) {
			resultTypeRef = funTE;
		} else {
			// if fun is generic, we have to replace the type variables of fun by those of result1
			RuleEnvironment Gx = newRuleEnvironment(G);
			addTypeMappings(Gx, fun.getTypeVars(),
					toList(map(funTE.getOwnedTypeVars(), tv -> TypeUtils.createTypeRef(tv))));
			resultTypeRef = (FunctionTypeExpression) ts.substTypeVariables(Gx, funTE);
		}

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved(
				solution -> handleOnSolved(G, cache, infCtx, funExpr, expectedTypeRef, resultTypeRef, solution));

		// return temporary type of funExpr (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * Process formal parameters and also introduce inference variables for types of fpars, where needed.
	 */
	private void processFormalParameters(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			FunctionExpression funExpr, FunctionTypeExpression funTE, TypeRef expectedTypeRef) {
		TFunction fun = (TFunction) funExpr.getDefinedType(); // types builder will have created this already
		FunctionTypeExprOrRef expectedFunctionTypeExprOrRef = (expectedTypeRef instanceof FunctionTypeExprOrRef)
				? (FunctionTypeExprOrRef) expectedTypeRef
				: null;

		// first, new type refs for each formal parameter are created
		int len = Math.min(funExpr.getFpars().size(), fun.getFpars().size());
		Map<FormalParameter, TFormalParameter> typeRefMap = new HashMap<>();
		for (var i = 0; i < len; i++) {
			FormalParameter fparAST = funExpr.getFpars().get(i);
			TFormalParameter fparT = fun.getFpars().get(i);
			// use the TFormalParameter created by the types builder as a basis
			TFormalParameter fparTCopy = TypeUtils.copy(fparT);
			funTE.getFpars().add(fparTCopy);
			typeRefMap.put(fparAST, fparTCopy);

			if (fparAST.getDeclaredTypeRef() == null) {
				assertTrueIfRigid(cache, "type of formal parameter in TModule should be a DeferredTypeRef",
						fparTCopy.getTypeRef() instanceof DeferredTypeRef);

				// Deferred type refs have to be resolved here
				InferenceVariable iv = infCtx.newInferenceVariable();
				fparTCopy.setTypeRef(TypeUtils.createTypeRef(iv)); // <-- set new inference variable as type
			}
		}

		// Now, go through the map and check for deferred types.
		// If any, include them into the constraint problem.
		for (Map.Entry<FormalParameter, TFormalParameter> fparPair : typeRefMap.entrySet()) {
			FormalParameter fparAST = fparPair.getKey();
			TFormalParameter fparTCopy = fparPair.getValue();
			if (fparAST.getDeclaredTypeRef() == null) {
				InferenceVariable iv = (InferenceVariable) fparTCopy.getTypeRef().getDeclaredType();
				addConstraintForDefaultInitializers(fparAST, fparTCopy, G, cache, iv, infCtx, typeRefMap);
				inferOptionalityFromExpectedFpar(funExpr, funTE, expectedFunctionTypeExprOrRef, fparAST, fparTCopy);
			}
		}
	}

	/**
	 * When a function expression contains an initializer (in a default parameter), the type of this initializer is
	 * taken into account when calculating the parameter's type.
	 */
	private void addConstraintForDefaultInitializers(FormalParameter fparAST, TFormalParameter fparT,
			RuleEnvironment G, ASTMetaInfoCache cache, InferenceVariable iv, InferenceContext infCtx,
			Map<FormalParameter, TFormalParameter> typeRefMap) {

		if (fparAST.isHasInitializerAssignment()) {
			// Check if the initializer refers to other fpars

			Expression fparInitializer = fparAST.getInitializer();
			TFormalParameter referredFparCopy = null;
			boolean isPostponed = cache.postponedSubTrees.contains(fparInitializer);
			if (fparInitializer instanceof IdentifierRef) {
				IdentifierRef idRef = (IdentifierRef) fparInitializer;
				IdentifiableElement id = idRef.getId();
				Object idInAST = (id instanceof SyntaxRelatedTElement)
						? id.eGet(TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false)
						: null;
				referredFparCopy = typeRefMap.get(idInAST);
			}

			if (referredFparCopy != null) {
				// example: f(a, b = a) {}
				TypeRef tRef = referredFparCopy.getTypeRef(); // point to the inference variable introduced above
				infCtx.addConstraint(TypeUtils.createTypeRef(iv), TypeUtils.copy(tRef), Variance.CONTRA);
			} else if (!isPostponed) {
				TypeRef context = (fparT.eContainer() instanceof ContainerType<?>)
						? TypeUtils.createTypeRef((ContainerType<?>) fparT.eContainer())
						: null;
				RuleEnvironment G_withContext = ts.createRuleEnvironmentForContext(context, getContextResource(G));
				TypeRef iniTypeRef = (fparInitializer != null) ? ts.type(G_withContext, fparInitializer)
						: undefinedTypeRef(G);
				TypeRef iniTypeRefSubst = ts.substTypeVariables(G_withContext, iniTypeRef);
				infCtx.addConstraint(TypeUtils.createTypeRef(iv), TypeUtils.copy(iniTypeRefSubst), Variance.CONTRA);
			}
		}
	}

	/**
	 * if the corresponding fpar in the type expectation is optional, we make the fpar in the function expression
	 * optional as well Example: let fun: {function(string=)} = function(p) {};
	 */
	private void inferOptionalityFromExpectedFpar(FunctionExpression funExpr, FunctionTypeExpression funTE,
			FunctionTypeExprOrRef expectedFunctionTypeExprOrRef, FormalParameter fparAST, TFormalParameter fparTCopy) {
		if (expectedFunctionTypeExprOrRef != null) {
			int fparIdx = funExpr.getFpars().indexOf(fparAST);
			TFormalParameter fparExpected = expectedFunctionTypeExprOrRef.getFparForArgIdx(fparIdx);
			if (fparExpected != null && fparExpected.isOptional() && !fparExpected.isVariadic()) {
				IterableExtensions.last(funTE.getFpars()).setHasInitializerAssignment(true);
				EcoreUtilN4.doWithDeliver(false, () -> {
					fparAST.setHasInitializerAssignment(true);
					fparTCopy.setHasInitializerAssignment(true);
				}, fparAST, fparTCopy);
			}
		}
	}

	/**
	 * Processes return type (also introduce inference variable for return type, if needed)
	 */
	private void processReturnType(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			FunctionExpression funExpr, FunctionTypeExpression funTE) {
		TFunction fun = (TFunction) funExpr.getDefinedType(); // types builder will have created this already
		TypeRef returnTypeRef;
		if (funExpr.getDeclaredReturnTypeRef() != null) {
			// explicitly declared return type
			// -> take the type reference created by the types builder (but wrap in Promise if required)
			returnTypeRef = TypeUtils.copy(fun.getReturnTypeRef());
		} else {
			// undeclared return type
			// -> create infVar for return type IFF funExpr contains return statements OR is single expr arrow function;
			// otherwise use VOID as return type
			assertTrueIfRigid(cache, "return type of TFunction in TModule should be a DeferredTypeRef",
					fun.getReturnTypeRef() instanceof DeferredTypeRef);

			if (isReturningValue(funExpr)) {
				// introduce new inference variable for (inner) return type
				InferenceVariable iv = infCtx.newInferenceVariable();
				returnTypeRef = TypeUtils.createTypeRef(iv);
			} else {
				// void
				returnTypeRef = voidTypeRef(G);
			}
			// to obtain outer return type: wrap in Promise if asynchronous and not Promise already
			// for the time being, see N4JS Specification, Section 6.4.1 "Asynchronous Functions")
			returnTypeRef = N4JSLanguageUtils.makePromiseIfAsync(funExpr, returnTypeRef, getBuiltInTypeScope(G));
			// to obtain outer return type: wrap in Generator if it is a generator function
			// see N4JS Specification, Section 6.3.1 "Generator Functions")
			returnTypeRef = N4JSLanguageUtils.makeGeneratorIfGeneratorFunction(funExpr, returnTypeRef,
					getBuiltInTypeScope(G));
		}
		funTE.setReturnTypeRef(returnTypeRef);
	}

	/**
	 * Writes final types to cache
	 */
	private void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			FunctionExpression funExpr, TypeRef expectedTypeRef, FunctionTypeExpression resultTypeRef,
			Optional<Map<InferenceVariable, TypeRef>> solution) {

		Map<InferenceVariable, TypeRef> solution2 = (solution.isPresent()) ? solution.get()
				: createPseudoSolution(infCtx, anyTypeRef(G));
		// sanitize parameter types
		// (but do not turn closed ExistentialTypeRefs into their upper bound that also appear in the expected type,
		// by defining them as "fixed captures" via RuleEnvironmentExtensions#addFixedCapture())
		RuleEnvironment G2 = wrap(G);
		Type returnTypeInfVar = resultTypeRef.getReturnTypeRef() == null ? null
				: resultTypeRef.getReturnTypeRef().getDeclaredType(); // this infVar's solution must not be sanitized as
																		// it's not a parameter
		TypeRef expectedTypeRefSubst = applySolution(expectedTypeRef, G, solution2);
		if (expectedTypeRefSubst != null) {
			Iterable<ExistentialTypeRef> capturesInExpectedTypeRef = filter(filter(toIterable(
					concat(singletonIterator(expectedTypeRefSubst), expectedTypeRefSubst.eAllContents())),
					ExistentialTypeRef.class),
					etr -> !etr.isReopened());

			for (ExistentialTypeRef it : capturesInExpectedTypeRef) {
				addFixedCapture(G2, it);
			}
		}
		Map<InferenceVariable, TypeRef> solution3 = new HashMap<>(solution2);
		boolean resolveLiteralTypes = false; // if we resolved here, we might break constraints (it's the responsibility
												// of the constraint solver to avoid literal types as far as possible)
		solution3.replaceAll((k, v) -> (k != returnTypeInfVar)
				? tsh.sanitizeTypeOfVariableFieldPropertyParameter(G2, v, resolveLiteralTypes)
				: v);
		// apply solution to resultTypeRef
		FunctionTypeExprOrRef resultSolved0 = (FunctionTypeExprOrRef) applySolution(resultTypeRef, G, solution3);
		if (resultSolved0 instanceof FunctionTypeExpression) {
			resultSolved0 = TypeUtils.copy(resultSolved0);
			((FunctionTypeExpression) resultSolved0)
					.setASTNodeOptionalFieldStrategy(OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL);
		}
		FunctionTypeExprOrRef resultSolved = resultSolved0;
		// store type of funExpr in cache ...
		cache.storeType(funExpr, resultSolved);
		// update the defined function in the TModule
		TFunction fun = (TFunction) funExpr.getDefinedType(); // types builder will have created this already
		replaceDeferredTypeRefs(fun, resultSolved);
		if (fun.isReturnValueMarkedOptional() != resultSolved.isReturnValueOptional()) {
			EcoreUtilN4.doWithDeliver(false,
					() -> fun.setReturnValueMarkedOptional(resultSolved.isReturnValueOptional()), fun);
		}
		// store types of fpars in cache ...
		int len = Math.min(funExpr.getFpars().size(), fun.getFpars().size());
		for (var i = 0; i < len; i++) {
			FormalParameter fparAST = funExpr.getFpars().get(i);
			TFormalParameter fparT = fun.getFpars().get(i);
			TypeRef fparTw = TypeUtils.wrapIfVariadic(getPredefinedTypes(G).builtInTypeScope, fparT.getTypeRef(),
					fparAST);
			cache.storeType(fparAST, fparTw);
		}
		// tweak return type
		if (funExpr instanceof ArrowFunction) {
			log(0, "==START of special handling of single-expression arrow function");
			// NOTE: the next line requires the type of 'funExpr' and types of fpars to be in cache! For example:
			// function <T> foo(p: {function(int):T}) {return undefined;}
			// foo( (i) => [i] );
			tweakReturnTypeOfSingleExpressionArrowFunction(G, cache, (ArrowFunction) funExpr, resultSolved);
			log(0, "==END of special handling of single-expression arrow function");
		}
	}

	/**
	 * Handling of a very specific special case of single-expression arrow functions.
	 * <p>
	 * If the given arrow function is a single-expression arrow function, this method changes the return type of the
	 * given function type reference from non-void to void if the non-void return type would lead to a type error later
	 * on (for details see code of this method).
	 * <p>
	 * This tweak is only required because our poor man's return type inferencer in the types builder infers a wrong
	 * non-void return type in some cases, which is corrected in this method.
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * function foo(f : {function(): void}) {}
	 * function none(): void {}
	 * foo(() => none()); // will show bogus error when disabling this method
	 * </pre>
	 */
	private void tweakReturnTypeOfSingleExpressionArrowFunction(RuleEnvironment G, ASTMetaInfoCache cache,
			ArrowFunction arrFun, FunctionTypeExprOrRef arrFunTypeRef) {
		if (!arrFun.isSingleExprImplicitReturn()) {
			return; // not applicable
		}
		// Step 1) process arrFun's body, which was postponed earlier according to ASTProcessor#isPostponedNode(EObject)
		// Rationale: the body of a single-expression arrow function isn't a true block, so we do not have to
		// postpone it AND we need its types in the next step.
		Block block = arrFun.getBody();
		if (block == null) {
			return; // broken AST
		}
		if (!cache.postponedSubTrees.remove(block)) {
			throw new IllegalStateException(
					"body of single-expression arrow function not among postponed subtrees, in resource: "
							+ arrFun.eResource().getURI());
		}
		astProcessor.processSubtree(G, block, cache, 1);
		// Step 2) adjust arrFun's return type stored in arrFunTypeRef (if required)
		boolean didTweakReturnType = false;
		Expression expr = arrFun.getSingleExpression();
		if (expr == null) {
			return; // broken AST
		}
		TypeRef exprTypeRef = cache.getType(G, expr); // must now be in cache, because we just processed arrFun's body
		if (TypeUtils.isVoid(exprTypeRef)) {
			// the actual type of 'expr' is void
			if (arrFunTypeRef instanceof FunctionTypeExpression) {
				if (!TypeUtils.isVoid(arrFunTypeRef.getReturnTypeRef())) {
					// the return type of the single-expression arrow function 'arrFun' is *not* void
					// --> this would lead to a type error in N4JSTypeValidation, which we want to fix now
					// in case the outer type expectation for the containing arrow function has a
					// return type of 'void' OR there is no outer type expectation at all
					FunctionTypeExprOrRef outerTypeExpectation = expectedTypeForArrowFunction(G, arrFun);
					TypeRef outerReturnTypeExpectation = outerTypeExpectation == null ? null
							: outerTypeExpectation.getReturnTypeRef();
					if (outerTypeExpectation == null
							|| (outerReturnTypeExpectation != null && TypeUtils.isVoid(outerReturnTypeExpectation))) {
						// fix the future type error by changing the return type of the containing arrow function
						// from non-void to void
						if (isDEBUG_LOG()) {
							log(1, "tweaking return type from " + (arrFunTypeRef.getReturnTypeRef() == null ? null
									: arrFunTypeRef.getReturnTypeRef().getTypeRefAsString()) + " to void");
						}
						EcoreUtilN4.doWithDeliver(false,
								() -> ((FunctionTypeExpression) arrFunTypeRef).setReturnTypeRef(voidTypeRef(G)),
								arrFunTypeRef);
						if (isDEBUG_LOG()) {
							log(1, "tweaked type of arrow function is: " + arrFunTypeRef.getTypeRefAsString());
						}
						didTweakReturnType = true;
					}
				}
			}
		}
		if (!didTweakReturnType) {
			log(1, "tweaking of return type not required");
		}
	}

	/**
	 * Replaces all DeferredTypeRefs in the given TFunction (i.e. in the fpars' types and the return type) by the
	 * corresponding types in 'result'. Argument 'result' must not contain any DeferredTypeRefs and, when this method
	 * returns, also the given TFunction 'fun' won't contain DeferredTypeRefs anymore. Will throw exception if 'fun' and
	 * 'result' do not match (e.g. 'result' has fewer fpars than 'fun').
	 */
	private void replaceDeferredTypeRefs(TFunction fun, FunctionTypeExprOrRef result) {
		int len = fun.getFpars().size(); // note: we do not take Math.min here (fail fast)
		for (var i = 0; i < len; i++) {
			TFormalParameter funFpar = fun.getFpars().get(i);
			if (funFpar.getTypeRef() instanceof DeferredTypeRef) {
				int idx = i;
				EcoreUtilN4.doWithDeliver(false,
						() -> funFpar.setTypeRef(TypeUtils.copy(result.getFpars().get(idx).getTypeRef())), funFpar);
			}
		}
		if (fun.getReturnTypeRef() instanceof DeferredTypeRef) {
			EcoreUtilN4.doWithDeliver(false, () -> fun.setReturnTypeRef(TypeUtils.copy(result.getReturnTypeRef())),
					fun);
		}
	}

	private FunctionTypeExprOrRef expectedTypeForArrowFunction(RuleEnvironment G, ArrowFunction fe) {
		RuleEnvironment G_new = newRuleEnvironment(G);
		TypeRef tr = ts.expectedType(G_new, fe.eContainer(), fe);
		if (tr instanceof FunctionTypeExprOrRef) {
			return (FunctionTypeExprOrRef) tr;
		}
		return null;
	}
}
