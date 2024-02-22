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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRefDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.xbase.lib.Pair;

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
class PolyProcessor_ObjectLiteral extends AbstractPolyProcessor {

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
	TypeRef processObjectLiteral(RuleEnvironment G, ObjectLiteral objLit, TypeRef expectedTypeRef,
			InferenceContext infCtx, ASTMetaInfoCache cache) {

		if (!isPoly(objLit)) {
			TypeRef result = ts.type(G, objLit);
			// do not store in cache (TypeProcessor responsible for storing types of non-poly expressions in cache)
			return result;
		}

		// quick mode as a performance tweak:
		boolean haveUsableExpectedType = expectedTypeRef != null && expectedTypeRef.isStructuralTyping(); // TODO
																											// reconsider
		boolean quickMode = !haveUsableExpectedType && !TypeUtils.isInferenceVariable(expectedTypeRef);

		List<TStructMember> tMembers = new ArrayList<>();
		// in standard mode: the following list will contain pairs from property assignments to inference variables
		// in quick mode: the following list will contain pairs from property assignments to fallback types
		List<Pair<PropertyAssignment, ? extends EObject>> props2InfVarOrFallbackType = new ArrayList<>();
		processProperties(G, cache, infCtx, objLit, tMembers, quickMode, props2InfVarOrFallbackType);
		linkGetterSetterPairs(infCtx, tMembers, quickMode);

		// create temporary type (i.e. may contain inference variables)
		ParameterizedTypeRefStructural resultTypeRef = TypeUtils.createParameterizedTypeRefStructural(objectType(G),
				TypingStrategy.STRUCTURAL, tMembers.toArray(new TStructMember[0]));
		resultTypeRef.setASTNodeOptionalFieldStrategy(OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL);

		// register onSolved handlers to add final types to cache (i.e. may not contain inference variables)
		infCtx.onSolved(
				solution -> handleOnSolved(G, cache, infCtx, objLit, quickMode, props2InfVarOrFallbackType, solution));

		// return temporary type of objLit (i.e. may contain inference variables)
		return resultTypeRef;
	}

	/**
	 * Processes all properties of an object literal. As of now, methods are not processed.
	 */
	private void processProperties(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			ObjectLiteral objLit, List<TStructMember> tMembers,
			boolean quickMode, List<Pair<PropertyAssignment, ? extends EObject>> props2InfVarOrFallbackType) {
		for (PropertyAssignment pa : objLit.getPropertyAssignments()) {
			if (pa != null) {
				TStructMember tMember = null;
				if (pa.getDefinedMember() != null) {
					tMember = TypeUtils.copy(pa.getDefinedMember());
					tMembers.add(tMember);
				} else {
					// this happens if member name in AST was null (bad syntax in code; types builder won't
					// create a TMember in that case) or in case of an invalid computed name (not a compile-time
					// expression, unresolved reference, etc.; ComputedNameProcessor will remote TMember for
					// consistency with first case); this is an error case, but we still have to continue
					// processing, because nested expression must still be typed as usual.
				}

				if (isPoly(pa)) {
					if (!(tMember instanceof TMethod)) {
						processNonMethodProperties(G, cache, infCtx, tMember, pa,
								quickMode, props2InfVarOrFallbackType);
					}
				}
			}
		}
	}

	/**
	 * For each property in the object literal: a) introduce a new inference variable representing the property's type
	 * (except for methods) b) add a constraint: expressionTypeRef <: iv c) create a TStructMember to be used in the
	 * structural result type reference
	 *
	 * @param tMember
	 *            may be null in case of invalid members.
	 */
	private void processNonMethodProperties(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			TStructMember tMember, PropertyAssignment propAssignm,
			boolean quickMode, List<Pair<PropertyAssignment, ? extends EObject>> props2InfVarOrFallbackType) {
		if (tMember != null) {
			TypeRef originalMemberType = getTypeOfMember(tMember);

			assertTrueIfRigid(cache,
					"type of " + propAssignm.eClass().getName() + " in TModule should be a DeferredTypeRef",
					originalMemberType instanceof DeferredTypeRef);

			if (!(originalMemberType instanceof DeferredTypeRef)) {
				return; // if rigid assertions are turned off, we fail safe (should never happen)
			}
		}

		if (!quickMode) {
			// standard mode:
			// create new inference variable for the to-be-inferred type of this property
			InferenceVariable iv = infCtx.newInferenceVariable();
			// set it as type in tMember
			if (tMember != null) {
				setTypeOfMember(tMember, TypeUtils.createTypeRef(iv));
			}
			// add a constraint for the initializer expression (if any)
			if (propAssignm instanceof PropertyNameValuePair) {
				PropertyNameValuePair pnvp = (PropertyNameValuePair) propAssignm;
				if (pnvp.getExpression() != null) {
					TypeRef exprTypeRef = polyProcessor.processExpr(G, pnvp.getExpression(),
							TypeUtils.createTypeRef(iv), infCtx, cache);
					infCtx.addConstraint(exprTypeRef, TypeUtils.createTypeRef(iv), Variance.CO); // exprTypeRef <: iv
				}
			}
			// remember for later
			props2InfVarOrFallbackType.add(Pair.of(propAssignm, iv));
		} else {
			// quick mode:
			// compute a fall-back type
			TypeRef fallbackType = anyTypeRef(G);
			if (propAssignm instanceof PropertyNameValuePair) {
				PropertyNameValuePair pnvp = (PropertyNameValuePair) propAssignm;
				if (pnvp.getExpression() != null) {
					fallbackType = polyProcessor.processExpr(G, pnvp.getExpression(), null, infCtx, cache);
				}
			} else if (propAssignm instanceof PropertyGetterDeclaration) {
				fallbackType = getDeclaredTypeOfOtherAccessorInPair((PropertyGetterDeclaration) propAssignm);
			} else if (propAssignm instanceof PropertySetterDeclaration) {
				fallbackType = getDeclaredTypeOfOtherAccessorInPair((PropertySetterDeclaration) propAssignm);
			}
			if (fallbackType == null) {
				fallbackType = anyTypeRef(G);
			}

			// set it as type in tMember
			if (tMember != null) {
				setTypeOfMember(tMember, TypeUtils.copy(fallbackType));
			}
			// remember for later
			props2InfVarOrFallbackType.add(Pair.of(propAssignm, fallbackType));
		}
	}

	private TypeRef getDeclaredTypeOfOtherAccessorInPair(org.eclipse.n4js.n4JS.FieldAccessor accAST) {
		FieldAccessor otherPair = findOtherAccessorInPair(accAST.getDefinedAccessor());
		org.eclipse.n4js.n4JS.FieldAccessor otherPairAST = otherPair == null ? null
				: (org.eclipse.n4js.n4JS.FieldAccessor) otherPair.getAstElement();
		TypeRef declTypeRef = otherPairAST == null ? null : otherPairAST.getDeclaredTypeRef();
		return declTypeRef;
	}

	private FieldAccessor findOtherAccessorInPair(FieldAccessor acc) {
		if (acc != null && acc.getName() != null) {
			EObject type = acc.eContainer();
			if (type instanceof ContainerType<?>) {
				boolean lookForWriteAccess = acc instanceof TGetter;
				EObject result = ((ContainerType<?>) type).findOwnedMember(acc.getName(), lookForWriteAccess,
						acc.isStatic());
				if (result instanceof FieldAccessor) {
					return (FieldAccessor) result;
				}
			}
		}
		return null;
	}

	/**
	 * Add a constraint for each getter/setter pair reflecting the relation between the getter's and setter's type.
	 * Required to make the getter obtain its implicit type from the corresponding setter, and vice versa.
	 */
	private void linkGetterSetterPairs(InferenceContext infCtx, List<TStructMember> tMembers, boolean quickMode) {
		if (!quickMode) { // not in quick mode
			for (TStructMember tMember : tMembers) {
				if (tMember instanceof TStructGetter) {
					TStructGetter tsg = (TStructGetter) tMember;
					FieldAccessor tOtherInPair = findOtherAccessorInPair(tsg);
					if (tOtherInPair != null) {
						TypeRef typeGetter = getTypeOfMember(tsg);
						TypeRef typeSetter = getTypeOfMember(tOtherInPair);
						if (TypeUtils.isInferenceVariable(typeGetter) || TypeUtils.isInferenceVariable(typeSetter)) {
							infCtx.addConstraint(typeGetter, typeSetter, Variance.CO);
						} else {
							// do not add a constraint if both types were explicitly declared
							// (then this constraint does not apply!!)
						}
					}
				}
			}
		}
	}

	/**
	 * Writes final types to cache
	 */
	private void handleOnSolved(RuleEnvironment G, ASTMetaInfoCache cache, InferenceContext infCtx,
			ObjectLiteral objLit,
			boolean quickMode, List<? extends Pair<PropertyAssignment, ? extends EObject>> props2InfVarOrFallbackType,
			Optional<Map<InferenceVariable, TypeRef>> solution) {
		for (Pair<PropertyAssignment, ? extends EObject> propPair : props2InfVarOrFallbackType) {
			PropertyAssignment propAssignm = propPair.getKey();
			TStructMember memberInTModule = propAssignm.getDefinedMember();
			if (memberInTModule != null) {
				TypeRef memberType = getMemberType(G, infCtx, solution, quickMode, propPair);
				boolean resolveLiteralTypes = (quickMode)
						? // quick mode means we do not have a type expectation, so we handle literal types exactly
							// as when inferring the implicit type of variables with an initializer expression
						!N4JSASTUtils.isImmutable(propAssignm)
						:
						// standard mode means we have a type expectation; if we resolved here, we might break
						// constraints (instead, it's the responsibility of the constraint solver to avoid literal
						// types as far as possible)
						false;
				TypeRef memberTypeSane = tsh.sanitizeTypeOfVariableFieldPropertyParameter(G, memberType,
						resolveLiteralTypes);
				EcoreUtilN4.doWithDeliver(false, () -> setTypeOfMember(memberInTModule, TypeUtils.copy(memberTypeSane)),
						memberInTModule);
			}
		}

		ParameterizedTypeRefStructural resultFinal = TypeUtils.createParameterizedTypeRefStructural(objectType(G),
				TypingStrategy.STRUCTURAL,
				(TStructuralType) objLit.getDefinedType());
		resultFinal.setASTNodeOptionalFieldStrategy(OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL);
		cache.storeType(objLit, resultFinal);

		for (PropertyAssignment currAss : objLit.getPropertyAssignments()) {
			if (currAss.getDefinedMember() != null) {
				if (currAss instanceof PropertyMethodDeclaration) {
					cache.storeType(currAss,
							TypeUtils.createTypeRef(((PropertyMethodDeclaration) currAss).getDefinedMember()));
				} else {
					cache.storeType(currAss, TypeUtils.copy(getTypeOfMember(currAss.getDefinedMember())));
				}
			} else {
				if (currAss instanceof PropertyNameValuePair
						&& ((PropertyNameValuePair) currAss).getExpression() != null) {
					TypeRef exprType = ts.type(G, ((PropertyNameValuePair) currAss).getExpression());
					cache.storeType(currAss, exprType);
				} else {
					cache.storeType(currAss, anyTypeRefDynamic(G));
				}
			}
		}
	}

	private TypeRef getMemberType(RuleEnvironment G, InferenceContext infCtx,
			Optional<Map<InferenceVariable, TypeRef>> solution,
			boolean quickMode, Pair<PropertyAssignment, ? extends EObject> prop2InfVarOrFallbackType) {

		TypeRef memberType = null;
		PropertyAssignment propAssignm = prop2InfVarOrFallbackType.getKey();
		if (solution.isPresent()) {
			if (quickMode) {
				// success case (quick mode):
				TypeRef fallbackType = (TypeRef) prop2InfVarOrFallbackType.getValue(); // value is a TypeRef
				if (propAssignm instanceof PropertyNameValuePair) {
					memberType = getFinalResultTypeOfNestedPolyExpression(
							((PropertyNameValuePair) propAssignm).getExpression());
				} else {
					memberType = applySolution(TypeUtils.copy(fallbackType), G, solution.get());
				}

			} else {
				// success case (standard mode):
				InferenceVariable infVar = (InferenceVariable) prop2InfVarOrFallbackType.getValue(); // value is an
																										// infVar
				TypeRef fromSolution = solution.get().get(infVar);

				if (propAssignm instanceof PropertyNameValuePair) {
					TypeRef fromCache = (((PropertyNameValuePair) propAssignm).getExpression() instanceof ObjectLiteral)
							? getFinalResultTypeOfNestedPolyExpression(
									((PropertyNameValuePair) propAssignm).getExpression())
							: null;
					if (fromCache != null && ts.equaltypeSucceeded(G, fromCache, fromSolution)) {
						// tweak for nested ObjectLiterals in initializer expression of PropertyNameValuePairs:
						// the solution from the infCtx will be a StructuralTypeRef with 'genStructuralMembers'
						// but the result of the nested poly computation (via the cache) will give us a much more
						// efficient StructuralTypeRef with 'structuralType' pointing to the TStructuralType in the
						// TModule
						memberType = fromCache;
					} else {
						memberType = fromSolution;
					}
				} else {
					memberType = fromSolution;
				}
			}
		} else {
			// failure case (both modes):
			if (propAssignm instanceof PropertyNameValuePair) {
				memberType = getFinalResultTypeOfNestedPolyExpression(
						((PropertyNameValuePair) propAssignm).getExpression());
				memberType = adjustMemberTypeToAvoidMisleadingLiteralTypes(G, infCtx, quickMode,
						prop2InfVarOrFallbackType, memberType);
			} else {
				memberType = anyTypeRef(G);
			}
		}

		return memberType;
	}

	/**
	 * Replaces literal types by their base type to avoid confusing error messages.
	 * <p>
	 * Without this tweak, the following code
	 *
	 * <pre>
	 * let x: ~Object with { prop1: string, prop2: number } = { prop1: "hello", prop2: "BAD!" };
	 * </pre>
	 *
	 * would produce the misleading error message
	 *
	 * <pre>
	 * ~Object with { prop1: "hello"; prop2: "BAD!" } is not a structural subtype of ~Object with { prop1: string; prop2: number }: prop1 failed: "hello" is not equal to string and 1 more problems.
	 * </pre>
	 *
	 * With this tweak it changes to:
	 *
	 * <pre>
	 * ~Object with { prop1: string; prop2: "BAD!" } is not a structural subtype of ~Object with { prop1: string; prop2: number }: prop2 failed: "BAD!" is not equal to number.
	 * </pre>
	 */
	private TypeRef adjustMemberTypeToAvoidMisleadingLiteralTypes(RuleEnvironment G, InferenceContext infCtx,
			boolean quickMode, Pair<PropertyAssignment, ? extends EObject> prop2InfVarOrFallbackType,
			TypeRef memberType) {
		if (!quickMode) {
			if (memberType instanceof LiteralTypeRef) {
				// value is an infVar
				InferenceVariable infVar = (InferenceVariable) prop2InfVarOrFallbackType.getValue();
				if (!infCtx.isPromisingPartialSolution(infVar, memberType)) {
					TypeRef baseType = N4JSLanguageUtils.getLiteralTypeBase(G, memberType);
					if (infCtx.isPromisingPartialSolution(infVar, baseType)) {
						return baseType;
					}
				}
			}
		}
		return memberType;
	}
}
