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

import static org.eclipse.n4js.types.utils.TypeUtils.createWildcardExtends;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRefDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayNTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isArrayN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isIterableN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
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
class PolyProcessor_ArrayLiteral extends AbstractPolyProcessor {
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
	TypeRef processArrayLiteral(RuleEnvironment G, ArrayLiteral arrLit, TypeRef expectedTypeRef,
			InferenceContext infCtx, ASTMetaInfoCache cache) {

		// note: we do not have the case !arrLit.isPoly here, as in the other poly processors
		// (array literals are always poly, because they cannot be explicitly typed in N4JS)

		int numOfElems = arrLit.getElements().size();

		// we have to analyze the type expectation:
		// 1. we have to know up-front whether we aim for an actual type of Array/Iterable or for ArrayN/IterableN
		// 2. we have to know if we have concrete expectations for the element type(s)
		// To do so, we prepare a helper variable 'expectedElemTypeRefs'
		List<TypeRef> expectedElemTypeRefs = getExpectedElemTypeRefs(G, expectedTypeRef);

		// hack: faking an expectation of ArrayN<...> here
		// TODO instead we should get such an expectation in these cases from expectedType judgment!
		boolean isValueToBeDestructured = DestructureUtils.isArrayOrObjectLiteralBeingDestructured(arrLit);
		if (isValueToBeDestructured) {
			while (expectedElemTypeRefs.size() < numOfElems)
				expectedElemTypeRefs.add(anyTypeRef(G));
		}

		// performance tweak:
		boolean haveUsableExpectedType = !expectedElemTypeRefs.isEmpty();
		if (!haveUsableExpectedType && !TypeUtils.isInferenceVariable(expectedTypeRef)) {
			// no type expectation or some entirely wrong type expectation (i.e. other than Array, ArrayN)
			// -> just derive type from elements (and do not introduce a new inference variable for this ArrayLiteral!)
			List<TypeRef> elemTypeRefs = new ArrayList<>();
			for (ArrayElement arrElem : arrLit.getElements()) {
				if (arrElem.getExpression() == null) {
					elemTypeRefs.add(anyTypeRef(G));
				} else {
					TypeRef arrElemTypeRef = polyProcessor.processExpr(G, arrElem.getExpression(), null, infCtx, cache);
					arrElemTypeRef = ts.upperBoundWithReopen(G, arrElemTypeRef);
					if (arrElem.isSpread()) {
						// more than one in case of ArrayN; none in case of invalid value after spread operator
						elemTypeRefs.addAll(extractSpreadTypeRefs(G, arrElemTypeRef));
					} else {
						elemTypeRefs.add(arrElemTypeRef);
					}
				}
			}

			infCtx.onSolved(solution -> handleOnSolvedPerformanceTweak(G, cache, arrLit, expectedElemTypeRefs));

			return createArrayType(G, elemTypeRefs);
		}

		int resultLen = getResultLength(arrLit, expectedElemTypeRefs);
		TypeVariable[] resultInfVars = infCtx.newInferenceVariables(resultLen);

		processElements(G, cache, infCtx, arrLit, expectedElemTypeRefs, resultInfVars);

		TypeRef resultTypeRef = getResultTypeRef(G, resultInfVars);

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved(solution -> handleOnSolved(G, cache, arrLit, expectedElemTypeRefs, resultTypeRef, solution));

		// return temporary type of arrLit (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * The return value is as follows:
	 * <ul>
	 * <li>#[ T ] for an expectedTypeRef of the form Array&lt;T> or Iterable&lt;T>,</li>
	 * <li>#[ T1, T2, ..., TN ] for an expectedTypeRef of the form ArrayN&lt;T1,T2,...,TN>,</li>
	 * <li>#[] for any other kind of expectedTypeRef</li>
	 * </ul>
	 */
	private List<TypeRef> getExpectedElemTypeRefs(RuleEnvironment G, TypeRef expectedTypeRef) {
		if (expectedTypeRef != null) {
			List<TypeRef> candidateTypeRefs = (expectedTypeRef instanceof UnionTypeExpression)
					? ((UnionTypeExpression) expectedTypeRef).getTypeRefs()
					: List.of(expectedTypeRef);
			TInterface iterableType = iterableType(G);
			TClass arrayType = arrayType(G);
			for (TypeRef candidateTypeRef : candidateTypeRefs) {
				Type declType = candidateTypeRef.getDeclaredType();
				if (declType == iterableType
						|| declType == arrayType
						|| isIterableN(G, declType)
						|| isArrayN(G, declType)) {
					List<TypeRef> extractedTypeRefs = tsh.extractIterableElementTypes(G, candidateTypeRef);
					if (extractedTypeRefs.size() > 0) {
						return extractedTypeRefs; // will have len>1 iff expectation is IterableN
					}
				}
			}
		}
		return new ArrayList<>(); // no or invalid type expectation
	}

	/**
	 * Writes final types to cache.
	 */
	private void handleOnSolvedPerformanceTweak(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit,
			List<TypeRef> expectedElemTypeRefs) {

		TypeRef fallbackTypeRef;
		if (isEmptyArrayLiteralAndCallReceiver(arrLit)) {
			fallbackTypeRef = arrayTypeRef(G, anyTypeRefDynamic(G));
		} else {
			List<TypeRef> betterElemTypeRefs = storeTypesOfArrayElements(G, cache, arrLit);
			int resultLen = getResultLength(arrLit, betterElemTypeRefs);
			fallbackTypeRef = buildFallbackTypeForArrayLiteral(resultLen, betterElemTypeRefs,
					expectedElemTypeRefs, G);
		}

		cache.storeType(arrLit, fallbackTypeRef);
	}

	/**
	 * Support for a special case:
	 *
	 * <pre>
	 * // XPECT type of 'c' --> Array<int|any+>
	 * const c = [].concat([1]);
	 * </pre>
	 */
	private boolean isEmptyArrayLiteralAndCallReceiver(ArrayLiteral arrLit) {
		if (arrLit.getElements().isEmpty() && arrLit.eContainer() instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) arrLit.eContainer();
			if (ppae.getTarget() == arrLit && ppae.eContainer() instanceof ParameterizedCallExpression) {
				ParameterizedCallExpression pce = (ParameterizedCallExpression) ppae.eContainer();
				if (pce.getTarget() == ppae) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Writes final types to cache.
	 */
	private void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit,
			List<TypeRef> expectedElemTypeRefs, TypeRef resultTypeRef,
			Optional<Map<InferenceVariable, TypeRef>> solution) {

		if (solution.isPresent()) {
			// success case
			TypeRef typeRef = applySolution(resultTypeRef, G, solution.get());
			cache.storeType(arrLit, typeRef);
		} else {
			// failure case (unsolvable constraint system)
			List<TypeRef> betterElemTypeRefs = toList(map(
					arrLit.getElements(), ae -> getFinalResultTypeOfArrayElement(G, ae, Optional.absent())));
			int resultLen = getResultLength(arrLit, betterElemTypeRefs);
			TypeRef typeRef = buildFallbackTypeForArrayLiteral(resultLen, betterElemTypeRefs,
					expectedElemTypeRefs, G);
			cache.storeType(arrLit, typeRef);
		}
		storeTypesOfArrayElements(G, cache, arrLit);
	}

	/**
	 * choose correct number of type arguments in our to-be-created resultTypeRef (always 1 for Array<T> or Iterable<T>
	 * but N for ArrayN<..>, e.g. 3 for Array3<T1,T2,T3>)
	 */
	private int getResultLength(ArrayLiteral arrLit, List<TypeRef> expectedElemTypeRefs) {
		int numOfElems = arrLit.getElements().size();
		int lenA = Math.min(
				expectedElemTypeRefs.size(), // use number of type arguments provided by type expectation as a basis
				numOfElems // ... but never more than we have elements in the array literal
		);

		int lenB = Math.min(
				lenA,
				BuiltInTypeScope.ITERABLE_N__MAX_LEN // ... and never more than the max. allowed number of type
														// arguments for ArrayN
		);

		int resultLen = Math.max(
				lenB,
				1 // ... but at least 1 (even if numOfElems is 0, for example)
		);
		return resultLen;
	}

	/**
	 * Makes a best effort for building a type in case something went awry. It's only non-trivial in case we have an
	 * expectation of IterableN.
	 */
	private TypeRef buildFallbackTypeForArrayLiteral(int resultLen,
			List<TypeRef> elemTypeRefsWithLiteralTypes, List<TypeRef> expectedElemTypeRefs, RuleEnvironment G) {

		List<TypeRef> elemTypeRefs = toList(map(
				elemTypeRefsWithLiteralTypes, elem -> N4JSLanguageUtils.getLiteralTypeBase(G, elem)));

		List<TypeRef> typeArgs = new ArrayList<>();
		for (var i = 0; i < resultLen; i++) {
			boolean isLastElem = i == (resultLen - 1);
			TypeRef typeRef = null;
			if (isLastElem && elemTypeRefs.size() > resultLen) {
				// special case:
				// we are at the last element AND we actually have more elements than we expect elements
				// -> have to check all remaining elements against the last expectation!
				List<TypeRef> allRemainingElementTypeRefs = elemTypeRefs.subList(i, elemTypeRefs.size());

				if (expectedElemTypeRefs.isEmpty()) {
					typeRef = tsh.createUnionType(G, allRemainingElementTypeRefs.toArray(new TypeRef[0]));

				} else {
					TypeRef currExpectedElemTypeRef = expectedElemTypeRefs.get(i);

					// if all remaining elements are a subtype of the last expectation, then use expectation,
					// otherwise
					// form union
					boolean allMatch = true;
					for (var j = i; j < elemTypeRefs.size(); j++) {
						TypeRef currElementTypeRef = elemTypeRefs.get(j);

						if (allMatch) { // don't try further subtype checks if already failed
							boolean actualIsSubtypeOfExpected = ts.subtypeSucceeded(G, currElementTypeRef,
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
						typeRef = tsh.createUnionType(G, allRemainingElementTypeRefs.toArray(new TypeRef[0]));
					}
				}
			} else if (i < elemTypeRefs.size()) {
				TypeRef currElemTypeRef = elemTypeRefs.get(i);
				if (i < expectedElemTypeRefs.size()) {
					TypeRef currExpectedElemTypeRef = expectedElemTypeRefs.get(i);
					boolean actualIsSubtypeOfExpected = ts.subtypeSucceeded(G, currElemTypeRef,
							currExpectedElemTypeRef);
					if (actualIsSubtypeOfExpected) {
						// use expected type
						typeRef = currExpectedElemTypeRef;
					} else {
						// use actual type (will lead to follow-up errors caught by validations)
						typeRef = currElemTypeRef;
					}
				} else {
					typeRef = currElemTypeRef;
				}
			}
			if (typeRef != null) {
				typeArgs.add(typeRef);
			}
		}

		if (elemTypeRefs.size() > resultLen) {
			// replace last entry in 'typeArgs' with union of all remaining in elemTypeRefs
			TypeRef[] remaining = Arrays.copyOfRange(elemTypeRefs.toArray(new TypeRef[0]), resultLen - 1,
					elemTypeRefs.size());

			typeArgs.add(tsh.createUnionType(G, remaining));
		} else {
			while (typeArgs.size() > 1) {
				int size = typeArgs.size();
				TypeRef last = typeArgs.get(size - 1);
				TypeRef beforeLast = typeArgs.get(size - 2);
				if (ts.equaltypeFastSucceeded(beforeLast, last)) {
					typeArgs.remove(size - 1);
				} else {
					break;
				}
			}
		}

		return createArrayType(G, typeArgs);
	}

	private TypeRef createArrayType(RuleEnvironment G, List<TypeRef> elemTypeRefs) {
		if (elemTypeRefs.size() == 0) {
			return arrayTypeRef(G, anyTypeRef(G));
		} else if (elemTypeRefs.size() == 1) {
			return arrayTypeRef(G, elemTypeRefs.get(0));
		} else if (elemTypeRefs.size() > 1) {
			if (elemTypeRefs.size() <= BuiltInTypeScope.ITERABLE_N__MAX_LEN) {
				return arrayNTypeRef(G, elemTypeRefs.size(), elemTypeRefs.toArray(new TypeRef[0]));
			} else {
				List<TypeRef> arrayNTypes = new ArrayList<>(
						elemTypeRefs.subList(0, BuiltInTypeScope.ITERABLE_N__MAX_LEN - 1));
				List<TypeRef> tail = elemTypeRefs.subList(BuiltInTypeScope.ITERABLE_N__MAX_LEN, elemTypeRefs.size());
				arrayNTypes.add(tsh.createUnionType(G, tail.toArray(new TypeRef[0])));
				return arrayNTypeRef(G, BuiltInTypeScope.ITERABLE_N__MAX_LEN, arrayNTypes.toArray(new TypeRef[0]));
			}
		}
		return arrayTypeRef(G, anyTypeRef(G)); // unreachable
	}

	/**
	 * Creates temporary type (i.e. may contain inference variables):
	 * <ul>
	 * <li>Array<T> (where T is a new inference variable) or</li>
	 * <li>ArrayN<T1,T2,...,TN> (where T1,...TN are new inference variables, N>=2)</li>
	 * </ul>
	 */
	private TypeRef getResultTypeRef(RuleEnvironment G, TypeVariable[] resultInfVars) {
		List<TypeRef> ptRefs = new ArrayList<>();
		for (int i = 0; i < resultInfVars.length; i++) {
			ptRefs.add(TypeUtils.createTypeRef(resultInfVars[i]));
		}
		return createArrayType(G, ptRefs);
	}

	/**
	 * for each array element, add a constraint to ensure that its corresponding infVar in result type will be a super
	 * type of the array element's expression
	 */
	private void processElements(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			ArrayLiteral arrLit, List<TypeRef> expectedElemTypeRefs, TypeVariable[] resultInfVars) {

		int numOfElems = arrLit.getElements().size();
		for (int idxElem = 0; idxElem < numOfElems; idxElem++) {
			ArrayElement currElem = arrLit.getElements().get(idxElem);
			if (currElem == null || currElem.getExpression() == null) {
				// currElem is null, or has no expression (broken AST), or is an ArrayPadding element
				// -> ignore (no constraint to add)
			} else {
				// currElem is a valid ArrayElement with an expression
				// -> add constraint currElemTypeRef <: Ti (Ti being the corresponding inf. variable in
				// resultTypeRef)

				TypeRef currExpectedTypeRef = expectedElemTypeRefs.isEmpty()
						? null
						: expectedElemTypeRefs.get(Math.min(idxElem, expectedElemTypeRefs.size() - 1));

				TypeRef currResultTypeRef;
				if (isArrayN(G, currExpectedTypeRef) || isIterableN(G, currExpectedTypeRef)) {
					currResultTypeRef = currExpectedTypeRef;
				} else {
					int idxResult = Math.min(idxElem, resultInfVars.length - 1);
					TypeVariable currResultInfVar = resultInfVars[idxResult];
					currResultTypeRef = TypeUtils.createTypeRef(currResultInfVar);
					if (currElem.isSpread()) {
						currResultTypeRef = iterableTypeRef(G, createWildcardExtends(currResultTypeRef));
					}
				}

				TypeRef currElemTypeRef = polyProcessor.processExpr(G, currElem.getExpression(),
						currResultTypeRef, infCtx, cache);
				infCtx.addConstraint(currElemTypeRef, currResultTypeRef, Variance.CO);
			}
		}
	}

	// PolyProcessor#isResponsibleFor(TypableElement) claims responsibility of AST nodes of type 'ArrayElement'
	// contained in an ArrayLiteral which is poly, so we are responsible for storing the types of those
	// 'ArrayElement' nodes in cache
	// (note: compare this with similar handling of 'Argument' nodes in PolyProcessor_CallExpression)
	private List<TypeRef> storeTypesOfArrayElements(RuleEnvironment G, ASTMetaInfoCache cache, ArrayLiteral arrLit) {
		List<TypeRef> storedElemTypeRefs = new ArrayList<>();
		for (ArrayElement currElem : arrLit.getElements()) {
			if (currElem instanceof ArrayPadding) {
				cache.storeType(currElem, anyTypeRef(G));
				storedElemTypeRefs.add(anyTypeRef(G));
			} else {
				TypeRef currElemTypeRef = getFinalResultTypeOfArrayElement(G, currElem,
						Optional.of(storedElemTypeRefs));
				cache.storeType(currElem, currElemTypeRef);
			}
		}
		return storedElemTypeRefs;
	}

	private TypeRef getFinalResultTypeOfArrayElement(RuleEnvironment G, ArrayElement currElem,
			Optional<Collection<TypeRef>> addTypeRefsHere) {
		Expression currExpr = currElem == null ? null : currElem.getExpression();
		TypeRef currElemTypeRef = (currExpr != null) ? getFinalResultTypeOfNestedPolyExpression(currExpr) : null;
		if (currElemTypeRef != null && currElem != null) {
			currElemTypeRef = ts.upperBoundWithReopen(G, currElemTypeRef);
			List<? extends TypeRef> currElemTypeRefs = (currElem.isSpread()) ? extractSpreadTypeRefs(G, currElemTypeRef)
					: List.of(currElemTypeRef);
			if (addTypeRefsHere.isPresent()) {
				addTypeRefsHere.get().addAll(currElemTypeRefs);
			}
			return tsh.createUnionType(G, currElemTypeRefs.toArray(new TypeRef[0]));
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	private List<? extends TypeRef> extractSpreadTypeRefs(RuleEnvironment G, TypeRef typeRef) {
		// case 1: built-in type string
		if (typeRef instanceof ParameterizedTypeRef) {
			if (typeRef.getDeclaredType() == stringType(G)) {
				return List.of(stringTypeRef(G)); // spreading a string yields zero or more strings
			}
		}
		// case 2: Iterable or ArrayN
		return tsh.extractIterableElementTypes(G, typeRef);
	}
}
