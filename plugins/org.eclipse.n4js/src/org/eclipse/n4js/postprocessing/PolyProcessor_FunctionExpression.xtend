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

import com.google.common.base.Optional
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.HashMap
import java.util.Map
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * {@link PolyProcessor} delegates here for processing array literals.
 *
 * @see PolyProcessor#inferType(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,ASTMetaInfoCache)
 * @see PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)
 */
@Singleton
package class PolyProcessor_FunctionExpression extends AbstractPolyProcessor {
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private ASTProcessor astProcessor;


	/**
	 * BEFORE CHANGING THIS METHOD, READ THIS:
	 * {@link PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)}
	 */
	def package TypeRef processFunctionExpression(RuleEnvironment G, FunctionExpression funExpr, TypeRef expectedTypeRef,
		InferenceContext infCtx, ASTMetaInfoCache cache
	) {
		val fun = funExpr.definedType as TFunction; // types builder will have created this already

		if (!funExpr.isPoly) { // funExpr has declared types on all fpars and explicitly declared return type
			// can't use xsemantics here, because it would give us a DeferredTypeRef
			// return ts.type(G, funExpr).getValue();
			val funTE = TypeUtils.createFunctionTypeExpression(null, #[], fun.fpars, fun.returnTypeRef); // FIXME support for declared this type!!
			// do not store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache)
			return funTE;
		}

		// prepare temporary result type reference
		val funTE = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression;

		if (fun.declaredThisType !== null) {
			funTE.declaredThisType = TypeUtils.copy(fun.declaredThisType);
		}

		if (!fun.typeVars.empty) {
			funTE.ownedTypeVars += fun.typeVars.map[tv|TypeUtils.copy(tv)];
		}

		processFormalParameters(G, cache, infCtx, funExpr, funTE, expectedTypeRef);
		processReturnType(G, cache, infCtx, funExpr, funTE);

		funTE.returnValueMarkedOptional = expectedTypeRef instanceof FunctionTypeExprOrRef
			&& (expectedTypeRef as FunctionTypeExprOrRef).returnValueOptional;

		// create temporary type (i.e. may contain inference variables)
		val resultTypeRef = if (fun.typeVars.empty) {
				funTE
			} else {
				// if fun is generic, we have to replace the type variables of fun by those of result1
				val Gx = G.newRuleEnvironment;
				Gx.addTypeMappings(fun.typeVars, funTE.ownedTypeVars.map[TypeUtils.createTypeRef(it)]);
				ts.substTypeVariables(Gx, funTE) as FunctionTypeExpression;
			};

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved [ solution | handleOnSolved(G, cache, infCtx, funExpr, resultTypeRef, solution)];

		// return temporary type of funExpr (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * Process formal parameters and also introduce inference variables for types of fpars, where needed.
	 */
	private def void processFormalParameters(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
		FunctionExpression funExpr, FunctionTypeExpression funTE, TypeRef expectedTypeRef
	) {
		val fun = funExpr.definedType as TFunction; // types builder will have created this already
		val expectedFunctionTypeExprOrRef = if (expectedTypeRef instanceof FunctionTypeExprOrRef) { expectedTypeRef };

		// first, new type refs for each formal parameter are created
		val len = Math.min(funExpr.fpars.size, fun.fpars.size);
		val Map<FormalParameter,TFormalParameter> typeRefMap = new HashMap();
		for (var i = 0; i < len; i++) {
			val fparAST = funExpr.fpars.get(i);
			val fparT = fun.fpars.get(i);
			val fparTCopy = TypeUtils.copy(fparT); // use the TFormalParameter created by the types builder as a basis
			funTE.fpars += fparTCopy;
			typeRefMap.put(fparAST, fparTCopy);

			if (fparAST.declaredTypeRef === null) {
				assertTrueIfRigid(cache, "type of formal parameter in TModule should be a DeferredTypeRef",
					fparTCopy?.typeRef instanceof DeferredTypeRef);

				// Deferred type refs have to be resolved here
				val iv = infCtx.newInferenceVariable;
				fparTCopy.typeRef = TypeUtils.createTypeRef(iv); // <-- set new inference variable as type
			}
		}

		// Now, go through the map and check for deferred types.
		// If any, include them into the constraint problem.
		for (Map.Entry<FormalParameter,TFormalParameter> fparPair : typeRefMap.entrySet) {
			val fparAST = fparPair.key;
			val fparTCopy = fparPair.value;
			if (fparAST.declaredTypeRef === null) {
				val iv = fparTCopy.typeRef.declaredType as InferenceVariable;
				addConstraintForDefaultInitializers(funExpr, fparAST, fparTCopy, G, cache, iv, infCtx, typeRefMap);
				inferOptionalityFromExpectedFpar(funExpr, funTE, expectedFunctionTypeExprOrRef, fparAST, fparTCopy);
			}
		}
	}

	/**
	 * When a function expression contains an initializer (in a default parameter),
	 * the type of this initializer is taken into account when calculating the parameter's type.
	*/
	private def void addConstraintForDefaultInitializers(FunctionExpression funExpr, FormalParameter fparAST, TFormalParameter fparT,
		RuleEnvironment G, ASTMetaInfoCache cache, InferenceVariable iv, InferenceContext infCtx, Map<FormalParameter,TFormalParameter> typeRefMap
	) {
		if (fparAST.hasInitializerAssignment) {
			// Check if the initializer refers to other fpars
			val allFPars = (fparAST.eContainer as FunctionDefinition).fpars;

			val fparInitializer = fparAST.initializer;
			var refIsInitializer = false;
			val isPostponed = cache.postponedSubTrees.contains(fparInitializer);
			if (fparInitializer instanceof IdentifierRef) {
				val id = fparInitializer.getId();
				refIsInitializer = allFPars.contains(id);
			}

			if (refIsInitializer) {
				// example: f(a, b = a) {}
				val iRef = fparInitializer as IdentifierRef;
				val fparam = iRef.getId() as FormalParameter;
				val fparTCopy = typeRefMap.get(fparam);
				val TypeRef tRef = fparTCopy.typeRef;
				infCtx.addConstraint(TypeUtils.createTypeRef(iv), TypeUtils.copy(tRef), Variance.CONTRA);
			} else if (!isPostponed) {
				val context = if (fparT.eContainer instanceof ContainerType<?>)
						TypeUtils.createTypeRef(fparT.eContainer as ContainerType<?>) else null;
				val G_withContext = ts.createRuleEnvironmentForContext(context, G.contextResource);
				val TypeRef iniTypeRef = if (fparInitializer !== null) ts.type(G_withContext, fparInitializer) else G.undefinedTypeRef;
				val iniTypeRefSubst = ts.substTypeVariables(G_withContext, iniTypeRef);
				infCtx.addConstraint(TypeUtils.createTypeRef(iv), TypeUtils.copy(iniTypeRefSubst), Variance.CONTRA);
			}
		}
	}

	/**
	 * if the corresponding fpar in the type expectation is optional, we make the fpar in the
	 * function expression optional as well
	 * Example:
	 * 		let fun: {function(string=)} = function(p) {};
	 */
	private def void inferOptionalityFromExpectedFpar(FunctionExpression funExpr, FunctionTypeExpression funTE,
		FunctionTypeExprOrRef expectedFunctionTypeExprOrRef, FormalParameter fparAST, TFormalParameter fparTCopy
	) {
		if (expectedFunctionTypeExprOrRef !== null) {
			val int fparIdx = funExpr.fpars.indexOf(fparAST);
			val fparExpected = expectedFunctionTypeExprOrRef.getFparForArgIdx(fparIdx);
			if (fparExpected !== null && fparExpected.optional && !fparExpected.variadic) {
				funTE.fpars.last.hasInitializerAssignment = true;
				EcoreUtilN4.doWithDeliver(false, [
					fparAST.hasInitializerAssignment = true;
					fparTCopy.hasInitializerAssignment = true;
				], fparAST, fparTCopy);
			}
		}
	}

	/**
	 * Processes return type (also introduce inference variable for return type, if needed)
	 */
	private def void processReturnType(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
		FunctionExpression funExpr, FunctionTypeExpression funTE
	) {
		val fun = funExpr.definedType as TFunction; // types builder will have created this already
		var TypeRef returnTypeRef;
		if (funExpr.returnTypeRef !== null) {
			// explicitly declared return type
			// -> take the type reference created by the types builder (but wrap in Promise if required)
			returnTypeRef = TypeUtils.copy(fun.returnTypeRef);
		} else {
			// undeclared return type
			// -> create infVar for return type IFF funExpr contains return statements OR is single expr arrow function; otherwise use VOID as return type
			assertTrueIfRigid(cache, "return type of TFunction in TModule should be a DeferredTypeRef",
				fun.returnTypeRef instanceof DeferredTypeRef);

			if (funExpr.isReturningValue) {
				// introduce new inference variable for (inner) return type
				val iv = infCtx.newInferenceVariable;
				returnTypeRef = TypeUtils.createTypeRef(iv);
			} else {
				// void
				returnTypeRef = G.voidTypeRef;
			}
			// to obtain outer return type: wrap in Promise if asynchronous and not Promise already
			// for the time being, see N4JS Specification, Section 6.4.1 "Asynchronous Functions")
			returnTypeRef = N4JSLanguageUtils.makePromiseIfAsync(funExpr, returnTypeRef, G.builtInTypeScope);
			// to obtain outer return type: wrap in Generator if it is a generator function
			// see N4JS Specification, Section 6.3.1 "Generator Functions")
			returnTypeRef = N4JSLanguageUtils.makeGeneratorIfGeneratorFunction(funExpr, returnTypeRef, G.builtInTypeScope);
		}
		funTE.returnTypeRef = returnTypeRef;
	}


	/**
	 * Writes final types to cache
	 */
	private def void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx, FunctionExpression funExpr,
		FunctionTypeExpression resultTypeRef, Optional<Map<InferenceVariable, TypeRef>> solution
	) {
		val solution2 = if (solution.present) solution.get else infCtx.createPseudoSolution(G.anyTypeRef);
		val resultSolved = resultTypeRef.applySolution(G, solution2) as FunctionTypeExprOrRef;
		// store type of funExpr in cache ...
		cache.storeType(funExpr, resultSolved);
		// update the defined function in the TModule
		val fun = funExpr.definedType as TFunction; // types builder will have created this already
		fun.replaceDeferredTypeRefs(resultSolved);
		if(fun.returnValueMarkedOptional !== resultSolved.returnValueOptional) {
			EcoreUtilN4.doWithDeliver(false, [
				fun.returnValueMarkedOptional = resultSolved.returnValueOptional;
			], fun);
		}
		// store types of fpars in cache ...
		val len = Math.min(funExpr.fpars.size, fun.fpars.size);
		for (var i = 0; i < len; i++) {
			val fparAST = funExpr.fpars.get(i);
			val fparT = fun.fpars.get(i);
			val fparTw = TypeUtils.wrapIfVariadic(G.getPredefinedTypes().builtInTypeScope, fparT.typeRef, fparAST);
			cache.storeType(fparAST, fparTw);
		}
		// tweak return type
		if (funExpr instanceof ArrowFunction) {
			log(0, "===START of special handling of single-expression arrow function");
			// NOTE: the next line requires the type of 'funExpr' and types of fpars to be in cache! For example:
			//   function <T> foo(p: {function(int):T}) {return undefined;}
			//   foo( (i) => [i] );
			tweakReturnTypeOfSingleExpressionArrowFunction(G, cache, funExpr, resultSolved);
			log(0, "===END of special handling of single-expression arrow function");
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
	 * <pre>
	 * function foo(f : {function(): void}) {}
	 * function none(): void {}
	 * foo(() => none()); // will show bogus error when disabling this method
	 * </pre>
	 */
	def private void tweakReturnTypeOfSingleExpressionArrowFunction(RuleEnvironment G, ASTMetaInfoCache cache,
		ArrowFunction arrFun, FunctionTypeExprOrRef arrFunTypeRef
	) {
		if (!arrFun.isSingleExprImplicitReturn) {
			return; // not applicable
		}
		// Step 1) process arrFun's body, which was postponed earlier according to ASTProcessor#isPostponedNode(EObject)
		// Rationale: the body of a single-expression arrow function isn't a true block, so we do not have to
		//            postpone it AND we need its types in the next step.
		val block = arrFun.body;
		if (block === null) {
			return; // broken AST
		}
		if(!cache.postponedSubTrees.remove(block)) {
			throw new IllegalStateException("body of single-expression arrow function not among postponed subtrees, in resource: " + arrFun.eResource.URI);
		}
		astProcessor.processSubtree(G, block, cache, 1);
		// Step 2) adjust arrFun's return type stored in arrFunTypeRef (if required)
		var didTweakReturnType = false;
		val expr = arrFun.getSingleExpression();
		if (expr === null) {
			return; // broken AST
		}
		val exprTypeRef = cache.getType(expr); // must now be in cache, because we just processed arrFun's body
		if (TypeUtils.isVoid(exprTypeRef)) {
			// the actual type of 'expr' is void
			if (arrFunTypeRef instanceof FunctionTypeExpression) {
				if (!TypeUtils.isVoid(arrFunTypeRef.returnTypeRef)) {
					// the return type of the single-expression arrow function 'arrFun' is *not* void
					// --> this would lead to a type error in N4JSTypeValidation, which we want to fix now
					//     in case the outer type expectation for the containing arrow function has a
					//     return type of 'void' OR there is no outer type expectation at all
					val outerTypeExpectation = expectedTypeForArrowFunction(G, arrFun);
					val outerReturnTypeExpectation = outerTypeExpectation?.returnTypeRef;
					if (outerTypeExpectation === null
						|| (outerReturnTypeExpectation !== null && TypeUtils.isVoid(outerReturnTypeExpectation))) {
						// fix the future type error by changing the return type of the containing arrow function
						// from non-void to void
						if (isDEBUG_LOG) {
							log(1, "tweaking return type from " + arrFunTypeRef.returnTypeRef?.typeRefAsString + " to void");
						}
						EcoreUtilN4.doWithDeliver(false, [
							arrFunTypeRef.returnTypeRef = G.voidTypeRef;
						], arrFunTypeRef);
						if (isDEBUG_LOG) {
							log(1, "tweaked type of arrow function is: " + arrFunTypeRef.typeRefAsString);
						}
						didTweakReturnType = true;
					}
				}
			}
		}
		if(!didTweakReturnType) {
			log(1, "tweaking of return type not required");
		}
	}

	/**
	 * Replaces all DeferredTypeRefs in the given TFunction (i.e. in the fpars' types and the return type)
	 * by the corresponding types in 'result'. Argument 'result' must not contain any DeferredTypeRefs and,
	 * when this method returns, also the given TFunction 'fun' won't contain DeferredTypeRefs anymore.
	 * Will throw exception if 'fun' and 'result' do not match (e.g. 'result' has fewer fpars than 'fun').
	 */
	def private void replaceDeferredTypeRefs(TFunction fun, FunctionTypeExprOrRef result) {
		val len = fun.fpars.length; // note: we do not take Math.min here (fail fast)
		for (var i = 0; i < len; i++) {
			val funFpar = fun.fpars.get(i);
			if (funFpar.typeRef instanceof DeferredTypeRef) {
				val idx = i;
				EcoreUtilN4.doWithDeliver(false, [
					funFpar.typeRef = TypeUtils.copy(result.fpars.get(idx).typeRef);
				], funFpar);
			}
		}
		if (fun.returnTypeRef instanceof DeferredTypeRef) {
			EcoreUtilN4.doWithDeliver(false, [
				fun.returnTypeRef = TypeUtils.copy(result.returnTypeRef);
			], fun);
		}
	}

	def private FunctionTypeExprOrRef expectedTypeForArrowFunction(RuleEnvironment G, ArrowFunction fe) {
		val G_new = G.newRuleEnvironment;
		val tr = ts.expectedTypeIn(G_new, fe.eContainer(), fe);
		if (tr instanceof FunctionTypeExprOrRef) {
			return tr;
		}
		return null;
	}
}
