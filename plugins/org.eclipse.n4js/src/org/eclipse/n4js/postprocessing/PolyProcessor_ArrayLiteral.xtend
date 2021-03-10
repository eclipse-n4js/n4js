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
import java.util.Arrays
import java.util.Collection
import java.util.List
import java.util.Map
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.ArrayPadding
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * {@link PolyProcessor} delegates here for processing array literals.
 *
 * @see PolyProcessor#inferType(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,ASTMetaInfoCache)
 * @see PolyProcessor#processExpr(RuleEnvironment,org.eclipse.n4js.n4JS.Expression,TypeRef,InferenceContext,ASTMetaInfoCache)
 */
@Singleton
package class PolyProcessor_ArrayLiteral extends AbstractPolyProcessor {
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
	def package TypeRef processArrayLiteral(RuleEnvironment G, ArrayLiteral arrLit, TypeRef expectedTypeRef,
		InferenceContext infCtx, ASTMetaInfoCache cache) {

		// note: we do not have the case !arrLit.isPoly here, as in the other poly processors
		// (array literals are always poly, because they cannot be explicitly typed in N4JS)

		val numOfElems = arrLit.elements.size;

		// we have to analyze the type expectation:
		// 1. we have to know up-front whether we aim for an actual type of Array/Iterable or for IterableN
		// 2. we have to know if we have concrete expectations for the element type(s)
		// To do so, we prepare a helper variable 'expectedElemTypeRefs'
		val expectedElemTypeRefs = getExpectedElemTypeRefs(G, expectedTypeRef);


// hack: faking an expectation of IterableN<...> here
// TODO instead we should get such an expectation in these cases from expectedType judgment!
val isValueToBeDestructured = DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit);
if(isValueToBeDestructured) {
	while(expectedElemTypeRefs.size < numOfElems)
		expectedElemTypeRefs.add(G.anyTypeRef);
}

		// performance tweak:
		val haveUsableExpectedType = !expectedElemTypeRefs.empty;
		if (!haveUsableExpectedType && !TypeUtils.isInferenceVariable(expectedTypeRef)) {
			// no type expectation or some entirely wrong type expectation (i.e. other than Array, Iterable, IterableN)
			// -> just derive type from elements (and do not introduce a new inference variable for this ArrayLiteral!)
			val elemTypeRefs = newArrayList;
			val nonNullElems = arrLit.elements.filter[expression !== null];
			for (arrElem : nonNullElems) {
				var arrElemTypeRef = polyProcessor.processExpr(G, arrElem.expression, null, infCtx, cache);
				arrElemTypeRef = ts.upperBoundWithReopen(G, arrElemTypeRef);
				if (arrElem.spread) {
					elemTypeRefs += extractSpreadTypeRefs(G, arrElemTypeRef); // more than one in case of IterableN; none in case of invalid value after spread operator
				} else {
					elemTypeRefs += arrElemTypeRef;
				}
			}

			infCtx.onSolved [ solution | handleOnSolvedPerformanceTweak(G, cache, arrLit, expectedElemTypeRefs) ];

			val unionOfElemTypes = if (!elemTypeRefs.empty) tsh.createUnionType(G, elemTypeRefs) else G.anyTypeRef;
			return G.arrayTypeRef(unionOfElemTypes);
		}

		val resultLen = getResultLength(arrLit, expectedElemTypeRefs);
		val TypeVariable[] resultInfVars = infCtx.newInferenceVariables(resultLen);

		processElements(G, cache, infCtx, arrLit, resultLen, resultInfVars);

		val TypeRef resultTypeRef = getResultTypeRef(G, resultLen, resultInfVars);

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved [ solution | handleOnSolved(G, cache, arrLit, expectedElemTypeRefs, resultTypeRef, solution) ];

		// return temporary type of arrLit (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * The return value is as follows:
	 * <ul>
	 * <li>#[ T ] for an expectedTypeRef of the form Array<T> or Iterable<T>,</li>
	 * <li>#[ T1, T2, ..., TN ] for an expectedTypeRef of the form IterableN<T1,T2,...,TN>,</li>
	 * <li>#[] for any other kind of expectedTypeRef</li>
	 * </ul>
	 */
	private def List<TypeRef> getExpectedElemTypeRefs(RuleEnvironment G, TypeRef expectedTypeRef) {
		if (expectedTypeRef !== null) {
			val extractedTypeRefs = tsh.extractIterableElementTypes(G, expectedTypeRef);
			return extractedTypeRefs; // will have len>1 only if expectation is IterableN
		} else {
			return newArrayList // no or invalid type expectation
		}
	}

	/**
	 * Makes a best effort for building a type in case something went awry. It's only non-trivial in case we have an
	 * expectation of IterableN.
	 */
	private def TypeRef buildFallbackTypeForArrayLiteral(boolean isIterableN, int resultLen,
		List<TypeRef> elemTypeRefs, List<TypeRef> expectedElemTypeRefs, RuleEnvironment G) {

		if (isIterableN) {
			val typeArgs = newArrayOfSize(resultLen);
			for (var i = 0; i < resultLen; i++) {
				val boolean isLastElem = i === (resultLen - 1);
				var TypeRef typeRef = null;
				if (isLastElem && elemTypeRefs.size > resultLen) {
					// special case:
					// we are at the last element AND we actually have more elements than we expect elements
					// -> have to check all remaining elements against the last expectation!
					val allRemainingElementTypeRefs = newArrayList;
					val currExpectedElemTypeRef = expectedElemTypeRefs.get(i);

					// if all remaining elements are a subtype of the last expectation, then use expectation, otherwise form union
					var boolean allMatch = true;
					for (var j = i; j < elemTypeRefs.size; j++) {

						val currElementTypeRef = elemTypeRefs.get(j);
						allRemainingElementTypeRefs.add(currElementTypeRef);

						if (allMatch) { // don't try further subtype checks if already failed
							val actualIsSubtypeOfExpected = ts.subtypeSucceeded(G, currElementTypeRef,
								currExpectedElemTypeRef);
							if (!actualIsSubtypeOfExpected) {
								allMatch = false;
							}
						}
					}
					if (allMatch) {
						// use expected type
						typeRef = currExpectedElemTypeRef;
					} else {
						// use actual types (will lead to follow-up errors caught by validations)
						typeRef = tsh.createUnionType(G, allRemainingElementTypeRefs);
					}
				} else {
					val currElemTypeRef = elemTypeRefs.get(i);
					val currExpectedElemTypeRef = expectedElemTypeRefs.get(i);
					val actualIsSubtypeOfExpected = ts.subtypeSucceeded(G, currElemTypeRef, currExpectedElemTypeRef);
					if (actualIsSubtypeOfExpected) {
						// use expected type
						typeRef = currExpectedElemTypeRef;
					} else {
						// use actual type (will lead to follow-up errors caught by validations)
						typeRef = currElemTypeRef;
					}
				}
				typeArgs.set(i, typeRef);
			}

			if (elemTypeRefs.size > resultLen) {
				// replace last entry in 'typeArgs' with union of all remaining in elemTypeRefs
				val remaining = Arrays.copyOfRange(elemTypeRefs, resultLen - 1, elemTypeRefs.size);
				typeArgs.set(resultLen - 1, tsh.createUnionType(G, remaining));
			}

			return G.iterableNTypeRef(resultLen, typeArgs);
		} else {
			val unionOfElemTypes = if (!elemTypeRefs.empty) tsh.createUnionType(G, elemTypeRefs) else G.anyTypeRef;
			return G.arrayTypeRef(unionOfElemTypes);
		}
	}

	/**
	 * choose correct number of type arguments in our to-be-created resultTypeRef
	 * (always 1 for Array<T> or Iterable<T> but N for IterableN<..>, e.g. 3 for Iterable3<T1,T2,T3>)
	 */
	private def int getResultLength(ArrayLiteral arrLit, List<TypeRef> expectedElemTypeRefs) {
		val numOfElems = arrLit.elements.size;
		val lenA = Math.min(
				expectedElemTypeRefs.size, // use number of type arguments provided by type expectation as a basis
				numOfElems // ... but never more than we have elements in the array literal
			);

		val lenB = Math.min(
				lenA,
				BuiltInTypeScope.ITERABLE_N__MAX_LEN // ... and never more than the max. allowed number of type arguments for IterableN
			);

		val resultLen = Math.max(
			lenB,
			1 // ... but at least 1 (even if numOfElems is 0, for example)
		);
		return resultLen;
	}

	/**
	 * Creates temporary type (i.e. may contain inference variables):
	 * <ul>
	 * <li>Array<T> (where T is a new inference variable) or</li>
	 * <li>Iterable<T> (where T is a new inference variable) or</li>
	 * <li>IterableN<T1,T2,...,TN> (where T1,...TN are new inference variables, N>=2)</li>
	 * </ul>
	 */
	private def TypeRef getResultTypeRef(RuleEnvironment G, int resultLen, TypeVariable[] resultInfVars) {
		val isIterableN = resultLen >= 2;
		val declaredType = if (isIterableN) G.iterableNType(resultLen) else G.arrayType;
		val typeArgs = resultInfVars.map[TypeUtils.createTypeRef(it)];
		val TypeRef resultTypeRef = TypeUtils.createTypeRef(declaredType, typeArgs);
		return resultTypeRef;
	}

	/**
	 * for each array element, add a constraint to ensure that its corresponding infVar in result type will be
	 * a super type of the array element's expression
	 */
	private def void processElements(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx, ArrayLiteral arrLit,
		int resultLen, TypeVariable[] resultInfVars
	) {
		val numOfElems = arrLit.elements.size;
		for (var idxElem = 0; idxElem < numOfElems; idxElem++) {
			val currElem = arrLit.elements.get(idxElem);
			if (currElem?.expression === null) {
				// currElem is null, or has no expression (broken AST), or is an ArrayPadding element
				// -> ignore (no constraint to add)
			} else {
				// currElem is a valid ArrayElement with an expression
				// -> add constraint currElemTypeRef <: Ti (Ti being the corresponding inf. variable in resultTypeRef)
				val idxResult = Math.min(idxElem, resultLen - 1);
				val currResultInfVar = resultInfVars.get(idxResult);
				val currResultInfVarTypeRef = TypeUtils.createTypeRef(currResultInfVar);
				val currExpectedTypeRef = if (currElem.spread) G.iterableTypeRef(TypeUtils.createWildcardExtends(currResultInfVarTypeRef)) else currResultInfVarTypeRef;
				val currElemTypeRef = polyProcessor.processExpr(G, currElem.expression, currExpectedTypeRef, infCtx, cache);
				infCtx.addConstraint(currElemTypeRef, currExpectedTypeRef, Variance.CO);
			}
		}
	}

	/**
	 * Writes final types to cache.
	 */
	private def void handleOnSolvedPerformanceTweak(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit,
		List<TypeRef> expectedElemTypeRefs
	) {
		val List<TypeRef> betterElemTypeRefs = storeTypesOfArrayElements(G, cache, arrLit);
		val fallbackTypeRef = buildFallbackTypeForArrayLiteral(false, 1, betterElemTypeRefs, expectedElemTypeRefs, G);
		cache.storeType(arrLit, fallbackTypeRef);
	}

	/**
	 * Writes final types to cache.
	 */
	private def void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit,
		List<TypeRef> expectedElemTypeRefs, TypeRef resultTypeRef, Optional<Map<InferenceVariable, TypeRef>> solution
	) {
		val resultLen = getResultLength(arrLit, expectedElemTypeRefs);
		val isIterableN = resultLen >= 2;
		if (solution.present) {
			// success case
			val typeRef = resultTypeRef.applySolution(G, solution.get);
			cache.storeType(arrLit, typeRef);
		} else {
			// failure case (unsolvable constraint system)
			val betterElemTypeRefs = arrLit.elements.map[getFinalResultTypeOfArrayElement(G, it, Optional.absent)];
			val typeRef = buildFallbackTypeForArrayLiteral(isIterableN, resultLen, betterElemTypeRefs, expectedElemTypeRefs, G);
			cache.storeType(arrLit, typeRef);
		}
		storeTypesOfArrayElements(G, cache, arrLit);
	}

	// PolyProcessor#isResponsibleFor(TypableElement) claims responsibility of AST nodes of type 'ArrayElement'
	// contained in an ArrayLiteral which is poly, so we are responsible for storing the types of those
	// 'ArrayElement' nodes in cache
	// (note: compare this with similar handling of 'Argument' nodes in PolyProcessor_CallExpression)
	private def List<TypeRef> storeTypesOfArrayElements(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit) {
		val List<TypeRef> storedElemTypeRefs = newArrayList;
		for (currElem : arrLit.elements) {
			if (currElem instanceof ArrayPadding) {
				cache.storeType(currElem, G.undefinedTypeRef);
			} else {
				val currElemTypeRef = getFinalResultTypeOfArrayElement(G, currElem, Optional.of(storedElemTypeRefs));
				cache.storeType(currElem, currElemTypeRef);
			}
		}
		return storedElemTypeRefs;
	}

	private def TypeRef getFinalResultTypeOfArrayElement(RuleEnvironment G, ArrayElement currElem, Optional<Collection<TypeRef>> addTypeRefsHere) {
		val currExpr = currElem?.expression;
		var currElemTypeRef = if (currExpr!==null) getFinalResultTypeOfNestedPolyExpression(currExpr);
		if (currElemTypeRef !== null) {
			currElemTypeRef = ts.upperBoundWithReopen(G, currElemTypeRef);
			val currElemTypeRefs = if (currElem.spread) extractSpreadTypeRefs(G, currElemTypeRef) else #[ currElemTypeRef ];
			if (addTypeRefsHere.present) {
				addTypeRefsHere.get += currElemTypeRefs;
			}
			return tsh.createUnionType(G, currElemTypeRefs);
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
	}

	private def List<? extends TypeRef> extractSpreadTypeRefs(RuleEnvironment G, TypeRef typeRef) {
		// case 1: built-in type string
		if (typeRef instanceof ParameterizedTypeRef) {
			if (typeRef.declaredType === G.stringType) {
				return #[ G.stringTypeRef ]; // spreading a string yields zero or more strings
			}
		}
		// case 2: Iterable or IterableN
		return tsh.extractIterableElementTypes(G, typeRef)
	}
}
