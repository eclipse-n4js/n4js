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
package org.eclipse.n4js.types.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.utils.RecursionGuard;

import com.google.common.base.Stopwatch;

/**
 * Static utility methods for type and type ref handling, for non-static utility methods see {@link TypeHelper}.
 */
public class TypeUtils {
	// GH-2615 FIXME: evaluate, remove
	public static Stopwatch sw0 = Stopwatch.createUnstarted();
	public static Stopwatch sw1 = Stopwatch.createUnstarted();
	public static Stopwatch sw2 = Stopwatch.createUnstarted();
	public static Stopwatch sw3 = Stopwatch.createUnstarted();
	public static Stopwatch sw4 = Stopwatch.createUnstarted();
	public static Stopwatch sw5 = Stopwatch.createUnstarted();
	public static Stopwatch sw6 = Stopwatch.createUnstarted();

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTSetter(String, String, TypeRef) */
	public static TSetter createTSetter(String name, String fparName, TypeRef fparTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTSetter(name, fparName, fparTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#wrapTypeInTypeRef(Type, TypeArgument...) */
	public static TypeRef wrapTypeInTypeRef(Type type, TypeArgument... typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.wrapTypeInTypeRef(type, typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeRef(Type, TypeArgument...) */
	public static ParameterizedTypeRef createTypeRef(Type declaredType, TypeArgument... typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeRef(declaredType, typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeRef(Type, TypingStrategy, TypeArgument...) */
	public static ParameterizedTypeRef createTypeRef(Type declaredType, TypingStrategy typingStrategy,
			TypeArgument... typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeRef(declaredType, typingStrategy, typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeRef(Type, TypingStrategy, boolean, TypeArgument...) */
	public static ParameterizedTypeRef createTypeRef(Type declaredType, TypingStrategy typingStrategy,
			boolean autoCreateTypeArgs, TypeArgument... typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeRef(declaredType, typingStrategy, autoCreateTypeArgs,
				typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeRefWithParamsAsArgs(Type) */
	public static ParameterizedTypeRef createTypeRefWithParamsAsArgs(Type type) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeRefWithParamsAsArgs(type);
	}

	/**
	 * @see org.eclipse.n4js.ts.types.util.TypeUtils#createParameterizedTypeRefStructural(Type, TypingStrategy,
	 *      TStructuralType)
	 */
	public static ParameterizedTypeRefStructural createParameterizedTypeRefStructural(Type declaredType,
			TypingStrategy typingStrategy, TStructuralType structuralType) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createParameterizedTypeRefStructural(declaredType,
				typingStrategy, structuralType);
	}

	/**
	 * @see org.eclipse.n4js.ts.types.util.TypeUtils#createParameterizedTypeRefStructural(Type, TypingStrategy,
	 *      TStructMember...)
	 */
	public static ParameterizedTypeRefStructural createParameterizedTypeRefStructural(Type declaredType,
			TypingStrategy typingStrategy, TStructMember... members) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createParameterizedTypeRefStructural(declaredType,
				typingStrategy, members);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeTypeRef(Type, boolean) */
	public static TypeTypeRef createTypeTypeRef(Type type, boolean isConstructorRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeTypeRef(type, isConstructorRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeTypeRef(TypeArgument, boolean) */
	public static TypeTypeRef createTypeTypeRef(TypeArgument typeArg, boolean isConstructorRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeTypeRef(typeArg, isConstructorRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createConstructorTypeRef(Type, TypeArgument...) */
	public static TypeRef createConstructorTypeRef(Type declaredType, TypeArgument... typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createConstructorTypeRef(declaredType, typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createNonSimplifiedUnionType(Iterable) */
	public static UnionTypeExpression createNonSimplifiedUnionType(Iterable<? extends TypeRef> elements) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createNonSimplifiedUnionType(elements);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createNonSimplifiedUnionType(TypeRef...) */
	public static UnionTypeExpression createNonSimplifiedUnionType(TypeRef... elements) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createNonSimplifiedUnionType(elements);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createNonSimplifiedIntersectionType(Iterable) */
	public static IntersectionTypeExpression createNonSimplifiedIntersectionType(Iterable<? extends TypeRef> elements) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createNonSimplifiedIntersectionType(elements);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createNonSimplifiedIntersectionType(TypeRef...) */
	public static IntersectionTypeExpression createNonSimplifiedIntersectionType(TypeRef... elements) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createNonSimplifiedIntersectionType(elements);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createUnionOfLiteralTypesFromEnumType(TypeArgument) */
	public static UnionTypeExpression createUnionOfLiteralTypesFromEnumType(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createUnionOfLiteralTypesFromEnumType(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createWildcard() */
	public static Wildcard createWildcard() {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createWildcard();
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createWildcardExtends(TypeRef) */
	public static Wildcard createWildcardExtends(TypeRef upperBound) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createWildcardExtends(upperBound);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createWildcardSuper(TypeRef) */
	public static Wildcard createWildcardSuper(TypeRef lowerBound) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createWildcardSuper(lowerBound);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#captureWildcard(Wildcard) */
	public static ExistentialTypeRef captureWildcard(Wildcard wildcard) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.captureWildcard(wildcard);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#convertTypeArgToRef(TypeArgument) */
	public static TypeRef convertTypeArgToRef(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.convertTypeArgToRef(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#convertTypeArgsToRefs(Iterable) */
	public static Iterable<? extends TypeRef> convertTypeArgsToRefs(Iterable<? extends TypeArgument> typeArgs) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.convertTypeArgsToRefs(typeArgs);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createBoundThisTypeRef(ParameterizedTypeRef) */
	public static BoundThisTypeRef createBoundThisTypeRef(ParameterizedTypeRef actualThisTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createBoundThisTypeRef(actualThisTypeRef);
	}

	/**
	 * @see org.eclipse.n4js.ts.types.util.TypeUtils#createBoundThisTypeRefStructural(ParameterizedTypeRef,
	 *      ThisTypeRefStructural)
	 */
	public static BoundThisTypeRef createBoundThisTypeRefStructural(ParameterizedTypeRef actualThisTypeRef,
			ThisTypeRefStructural thisTypeStructural) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createBoundThisTypeRefStructural(actualThisTypeRef,
				thisTypeStructural);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createClassifierBoundThisTypeRef(TypeTypeRef) */
	public static TypeTypeRef createClassifierBoundThisTypeRef(TypeTypeRef actualThisTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createClassifierBoundThisTypeRef(actualThisTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createResolvedThisTypeRef(BoundThisTypeRef) */
	public static ParameterizedTypeRef createResolvedThisTypeRef(BoundThisTypeRef boundThisTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createResolvedThisTypeRef(boundThisTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#enforceNominalTyping(TypeRef) */
	public static TypeRef enforceNominalTyping(TypeRef rawT) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.enforceNominalTyping(rawT);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#mergeTypeModifiers(TypeArgument, TypeRef, boolean) */
	public static TypeArgument mergeTypeModifiers(TypeArgument target, TypeRef source, boolean targetAlreadyCopied) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.mergeTypeModifiers(target, source, targetAlreadyCopied);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#mergeTypeModifiers(Wildcard, TypeRef, boolean) */
	public static Wildcard mergeTypeModifiers(Wildcard target, TypeRef source, boolean targetAlreadyCopied) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.mergeTypeModifiers(target, source, targetAlreadyCopied);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#mergeTypeModifiers(TypeRef, TypeRef, boolean) */
	public static TypeRef mergeTypeModifiers(TypeRef target, TypeRef source, boolean targetAlreadyCopied) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.mergeTypeModifiers(target, source, targetAlreadyCopied);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#concatTypingStrategies(TypingStrategy, TypingStrategy) */
	public static TypingStrategy concatTypingStrategies(TypingStrategy first, TypingStrategy second) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.concatTypingStrategies(first, second);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyStructuralTypingInfo(StructuralTypeRef, StructuralTypeRef) */
	public static void copyStructuralTypingInfo(StructuralTypeRef dest, StructuralTypeRef src) {
		org.eclipse.n4js.ts.types.util.TypeUtils.copyStructuralTypingInfo(dest, src);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyTypeModifiers(TypeRef, TypeRef) */
	public static void copyTypeModifiers(TypeRef target, TypeRef source) {
		org.eclipse.n4js.ts.types.util.TypeUtils.copyTypeModifiers(target, source);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createDeferredTypeRef() */
	public static DeferredTypeRef createDeferredTypeRef() {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createDeferredTypeRef();
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#containsDeferredTypeRefs(EObject) */
	public static boolean containsDeferredTypeRefs(EObject object) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.containsDeferredTypeRefs(object);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#assertNoDeferredTypeRefs(EObject) */
	public static void assertNoDeferredTypeRefs(EObject object) {
		org.eclipse.n4js.ts.types.util.TypeUtils.assertNoDeferredTypeRefs(object);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createFunctionTypeExpression(List, TypeRef) */
	public static FunctionTypeExpression createFunctionTypeExpression(
			List<TypeRef> fparTypeRefs,
			TypeRef returnTypeRef) {

		return org.eclipse.n4js.ts.types.util.TypeUtils.createFunctionTypeExpression(fparTypeRefs, returnTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createFunctionTypeExpression(TypeRef, List, List, TypeRef) */
	public static FunctionTypeExpression createFunctionTypeExpression(
			TypeRef declaredThisType, List<TypeVariable> ownedTypeVars,
			List<TFormalParameter> fpars, TypeRef returnTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createFunctionTypeExpression(declaredThisType, ownedTypeVars,
				fpars, returnTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#createTypeVariableMapping(TypeVariable, TypeArgument) */
	public static TypeVariableMapping createTypeVariableMapping(TypeVariable typeVar, TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.createTypeVariableMapping(typeVar, typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getActualDeclaredType(TypeAlias) */
	public static Type getActualDeclaredType(TypeAlias alias) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.getActualDeclaredType(alias);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getRootTypeRef(TypeRef) */
	public static TypeRef getRootTypeRef(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.getRootTypeRef(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#declaredSuperTypes(Type) */
	public static Iterable<? extends ParameterizedTypeRef> declaredSuperTypes(final Type type) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.declaredSuperTypes(type);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isRawSuperType(Type, Type) */
	public static boolean isRawSuperType(Type type, Type superTypeCandidate) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isRawSuperType(type, superTypeCandidate);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getMemberTypeRef(TMember) */
	public static TypeRef getMemberTypeRef(TMember m) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.getMemberTypeRef(m);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#setMemberTypeRef(TMember, TypeRef) */
	public static void setMemberTypeRef(TMember m, TypeRef typeRef) {
		org.eclipse.n4js.ts.types.util.TypeUtils.setMemberTypeRef(m, typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isInferenceVariable(TypeRef) */
	public static boolean isInferenceVariable(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isInferenceVariable(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isProper(TypeArgument) */
	public static boolean isProper(TypeArgument typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isProper(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isOrContainsType(TypeRef, Type) */
	public static boolean isOrContainsType(TypeRef typeRef, Type declaredType) {
		try {
			// sw0.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.isOrContainsType(typeRef, declaredType);
		} finally {
			// sw0.stop();
		}

	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isOrContainsTypeRefOfType(TypeRef, Class) */
	public static boolean isOrContainsTypeRefOfType(TypeRef typeRef, final Class<? extends TypeRef> typeOfTypeRef) {
		try {
			// sw1.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.isOrContainsTypeRefOfType(typeRef, typeOfTypeRef);
		} finally {
			// sw1.stop();
		}
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isOrContainsRefToTypeVar(EObject, TypeVariable...) */
	public static boolean isOrContainsRefToTypeVar(EObject obj, TypeVariable... typeVars) {
		try {
			// sw2.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.isOrContainsRefToTypeVar(obj, typeVars);
		} finally {
			// sw2.stop();
		}
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isOrContainsRefToInfVar(EObject, InferenceVariable...) */
	public static boolean isOrContainsRefToInfVar(EObject obj, InferenceVariable... infVars) {
		try {
			// sw3.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.isOrContainsRefToInfVar(obj, infVars);
		} finally {
			// sw3.stop();
		}
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getReferencedTypeVars(EObject) */
	public static Set<TypeVariable> getReferencedTypeVars(EObject obj) {
		try {
			// sw4.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.getReferencedTypeVars(obj);
		} finally {
			// sw4.stop();
		}
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getReferencedDeclaredTypes(EObject) */
	public static Set<Type> getReferencedDeclaredTypes(EObject obj) {
		try {
			// sw5.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.getReferencedDeclaredTypes(obj);
		} finally {
			// sw5.stop();
		}
	}

	/**
	 * @see org.eclipse.n4js.ts.types.util.TypeUtils#forAllTypeRefs(EObject, Class, boolean, boolean, Predicate,
	 *      Predicate, RecursionGuard)
	 */
	public static <T extends TypeRef> boolean forAllTypeRefs(EObject obj, Class<T> typeRefKind,
			boolean includeChildren, boolean followFunctionTypeRefs,
			Predicate<EObject> ignore, Predicate<T> operation,
			RecursionGuard<IdentifiableElement> guard) {

		try {
			// sw6.start();
			return org.eclipse.n4js.ts.types.util.TypeUtils.forAllTypeRefs(obj, typeRefKind, includeChildren,
					followFunctionTypeRefs, ignore, operation, guard);
		} finally {
			// sw6.stop();
		}
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isAccessorPair(TMember, TMember) */
	public static boolean isAccessorPair(TMember member, TMember member2) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isAccessorPair(member, member2);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#mayOverrideOrImplementByMetaType(MemberType, MemberType) */
	public static boolean mayOverrideOrImplementByMetaType(MemberType overrideCandidate,
			MemberType overriddenCandidate) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.mayOverrideOrImplementByMetaType(overrideCandidate,
				overriddenCandidate);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isRawTypeRef(TypeRef) */
	public static boolean isRawTypeRef(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isRawTypeRef(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#sanitizeRawTypeRef(TypeRef) */
	public static void sanitizeRawTypeRef(TypeRef typeRef) {
		org.eclipse.n4js.ts.types.util.TypeUtils.sanitizeRawTypeRef(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#getTypeVarsInStructMembers(StructuralTypeRef) */
	public static Set<TypeVariable> getTypeVarsInStructMembers(StructuralTypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.getTypeVarsInStructMembers(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copy(EObject) */
	public static final <T extends EObject> T copy(T source) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copy(source);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copy(EObject, EClass) */
	public static final <T extends EObject> T copy(T source, EClass eclass) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copy(source, eclass);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyIfContained(EObject) */
	public static final <T extends EObject> T copyIfContained(T source) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copy(source);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyWithProxies(EObject) */
	public static final <T extends EObject> T copyWithProxies(T source) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copyWithProxies(source);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyToParameterizedTypeRefStructural(ParameterizedTypeRef) */
	public static final ParameterizedTypeRefStructural copyToParameterizedTypeRefStructural(
			ParameterizedTypeRef source) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copyToParameterizedTypeRefStructural(source);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyPartial(EObject, EReference...) */
	public static final <T extends EObject> T copyPartial(T source, EReference... eRefsToIgnore) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copyPartial(source, eRefsToIgnore);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyAll(Collection) */
	public static <T extends EObject> Collection<T> copyAll(Collection<? extends T> sources) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copyAll(sources);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#copyAllIfContained(Collection) */
	public static <T extends EObject> Collection<T> copyAllIfContained(Collection<? extends T> sources) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.copyAllIfContained(sources);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isAny(TypeArgument) */
	public static boolean isAny(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isAny(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isNull(TypeArgument) */
	public static boolean isNull(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isNull(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isUndefined(TypeArgument) */
	public static boolean isUndefined(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isUndefined(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isVoid(TypeArgument) */
	public static boolean isVoid(TypeArgument typeArg) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isVoid(typeArg);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isVoidReturnType(FunctionTypeExprOrRef) */
	public static boolean isVoidReturnType(FunctionTypeExprOrRef funTypeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isVoidReturnType(funTypeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isStructural(TypeRef) */
	public static boolean isStructural(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isStructural(typeRef);
	}

	/** @see org.eclipse.n4js.ts.types.util.TypeUtils#isStructural(TypingStrategy) */
	public static boolean isStructural(TypingStrategy typingStrategy) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isStructural(typingStrategy);
	}

	/**
	 * Returns true iff the argument (or one of its components) is a {@link ThisTypeRef}.
	 *
	 * @param typeRef
	 *            the typeRef to be searched for (along with its components if any).
	 */
	public static boolean isOrContainsThisType(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.isOrContainsThisType(typeRef);
	}

	/**
	 * Null and proxy-safe method for retrieving typing strategy of a type ref.
	 *
	 * @param typeRef
	 *            may be null or a proxy
	 */
	public static TypingStrategy retrieveTypingStrategy(TypeRef typeRef) {
		return org.eclipse.n4js.ts.types.util.TypeUtils.retrieveTypingStrategy(typeRef);
	}

	/**
	 * Same as {@link #wrapTypeInTypeRef(Type, TypeArgument...)}, but can handle one more special case (creating a
	 * {@link ParameterizedTypeRefStructural} from a {@link TStructuralType}) because it is supplied with a built-in
	 * type scope.
	 */
	public static TypeRef wrapTypeInTypeRef(BuiltInTypeScope builtinTypeScope, Type type, TypeArgument... typeArgs) {
		if (type instanceof TStructuralType) {
			return createParameterizedTypeRefStructural(builtinTypeScope.getObjectType(), TypingStrategy.STRUCTURAL,
					(TStructuralType) type);
		}
		return wrapTypeInTypeRef(type, typeArgs);
	}

	/**
	 * Returns true iff the {@link TypeRef} refers to built-in type {@link BuiltInTypeScope#getPromiseType() Promise}.
	 * <p>
	 * WARNING: returns false for subtypes of <code>Promise</code>.
	 */
	public static boolean isPromise(TypeRef ref, BuiltInTypeScope scope) {
		if (ref instanceof ParameterizedTypeRef) {
			return ref.getDeclaredType() == scope.getPromiseType();
		}
		return false;
	}

	/**
	 * Returns true iff the {@link TypeRef} refers to built-in type {@link BuiltInTypeScope#getGeneratorType()
	 * Generator}.
	 * <p>
	 * WARNINGS:
	 * <ul>
	 * <li>returns false for <code>AsyncGenerator</code>.
	 * <li>returns false for subtypes of <code>Generator</code>.
	 * </ul>
	 */
	public static boolean isGenerator(TypeRef ref, BuiltInTypeScope scope) {
		if (ref instanceof ParameterizedTypeRef) {
			return ref.getDeclaredType() == scope.getGeneratorType();
		}
		return false;
	}

	/**
	 * Returns true iff the {@link TypeRef} refers to built-in type {@link BuiltInTypeScope#getAsyncGeneratorType()
	 * AsyncGenerator}.
	 * <p>
	 * WARNINGS:
	 * <ul>
	 * <li>returns false for <code>Generator</code>.
	 * <li>returns false for subtypes of <code>AsyncGenerator</code>.
	 * </ul>
	 */
	public static boolean isAsyncGenerator(TypeRef ref, BuiltInTypeScope scope) {
		if (ref instanceof ParameterizedTypeRef) {
			return ref.getDeclaredType() == scope.getAsyncGeneratorType();
		}
		return false;
	}

	/**
	 * Returns true iff the {@link TypeRef} refers to built-in type {@link BuiltInTypeScope#getGeneratorType()
	 * Generator} or {@link BuiltInTypeScope#getAsyncGeneratorType() AsyncGenerator}.
	 * <p>
	 * WARNING: returns false for subtypes of <code>Generator</code> and <code>AsyncGenerator</code>.
	 */
	public static boolean isGeneratorOrAsyncGenerator(TypeRef ref, BuiltInTypeScope scope) {
		if (ref instanceof ParameterizedTypeRef) {
			Type declType = ref.getDeclaredType();
			return declType == scope.getGeneratorType() || declType == scope.getAsyncGeneratorType();
		}
		return false;

	}

	/**
	 * For the given success and failure value types, this method returns a Promise<R,?> type reference. The failure
	 * type is optional (i.e. may be <code>null</code>). A success value type of <code>void</code> will be changed to
	 * type <code>undefined</code>, because <code>void</code> is not a valid type argument.
	 * <p>
	 * WARNING: this method will resolve proxies in 'successType' (in order to check if it points to type 'void')
	 */
	public static ParameterizedTypeRef createPromiseTypeRef(BuiltInTypeScope scope, TypeArgument successType,
			TypeArgument failureTypeOrNull) {

		Objects.requireNonNull(successType);
		TypeArgument successTypeArg = isVoid(successType) ? scope.getUndefinedTypeRef()
				: TypeUtils.copyWithProxies(successType);
		TypeArgument failureTypeArg = failureTypeOrNull != null ? TypeUtils.copyWithProxies(failureTypeOrNull)
				: TypeRefsFactory.eINSTANCE.createWildcard();

		return createTypeRef(scope.getPromiseType(), successTypeArg, failureTypeArg);
	}

	/**
	 * For a given generator function, a return type is created of the form {@code Generator<TYield,TReturn,TNext>} in
	 * case no type or a type other than Generator is declared. In case no type is declared, the types TYield and
	 * TReturn are inferred from the yield expressions and return statements in the body. The type TNext is
	 * <code>any</code>. In case a type other than <code>Generator</code> is declared, type type TYield becomes the
	 * declared type. The type TReturn is still inferred from the return statements. In case the declared type is
	 * <code>void</code>, both TYield and TReturn become the type <code>undefined</code>.
	 *
	 * <p>
	 * WARNING: this method will resolve proxies in 'successType' (in order to check if it points to type 'void')
	 */
	public static ParameterizedTypeRef createGeneratorTypeRef(BuiltInTypeScope scope, FunctionDefinition funDef) {
		Objects.requireNonNull(scope);
		Objects.requireNonNull(funDef);

		boolean async = funDef.isAsync();
		TypeRef definedReturn = funDef.getDeclaredReturnTypeRef();
		TypeArgument tYield;
		TypeArgument tReturn = inferReturnTypeFromReturns(funDef, scope);

		if (definedReturn == null) {
			tYield = inferYieldExprTypeFromYields(funDef, scope);
		} else {
			tYield = definedReturn;
			if (TypeUtils.isVoid(definedReturn)) {
				tReturn = scope.getUndefinedTypeRef();
			}
		}

		ParameterizedTypeRef generatorTypeRef = createGeneratorTypeRef(scope, async, tYield, tReturn, null);
		return generatorTypeRef;
	}

	/**
	 * Creates a generator type of the form {@code Generator<TYield,TReturn,TNext>}. In case {@code tYield} or
	 * {@code tReturn} is of type {@code void}, it will be transformed to {@code undefined}. In case {@code tNext} is
	 * {@code null}, it will be of type {@code any}.
	 */
	public static ParameterizedTypeRef createGeneratorTypeRef(BuiltInTypeScope scope, boolean async,
			TypeArgument tYield, TypeArgument tReturn, TypeArgument tNext) {

		tYield = isVoid(tYield) ? scope.getUndefinedTypeRef() : TypeUtils.copyWithProxies(tYield);
		tReturn = isVoid(tReturn) ? scope.getUndefinedTypeRef() : TypeUtils.copyWithProxies(tReturn);
		tNext = (tNext == null) ? scope.getAnyTypeRef() : TypeUtils.copyWithProxies(tNext);
		ParameterizedTypeRef generatorTypeRef = createTypeRef(
				async ? scope.getAsyncGeneratorType() : scope.getGeneratorType(),
				tYield, tReturn, tNext);
		return generatorTypeRef;
	}

	/**
	 * Infers the yield value type form all yield expressions in the body.
	 * <p>
	 * This is a poor man's type inference, meaning that the outcome is either {@code any} or {@code void}. (Similar to:
	 * {@code AbstractFunctionDefinitionTypesBuilder}).
	 */
	private static TypeRef inferYieldExprTypeFromYields(FunctionDefinition funDef, BuiltInTypeScope scope) {
		boolean hasNonVoidReturn = funDef.getBody() != null && funDef.getBody().hasNonVoidYield();
		if (hasNonVoidReturn) {
			return scope.getAnyTypeRef();
		} else {
			return scope.getVoidTypeRef();
		}
	}

	/**
	 * Infers the return value type form all yield expressions in the body.
	 * <p>
	 * This is a poor man's type inference, meaning that the outcome is either {@code any} or {@code void}. (Similar to:
	 * {@code AbstractFunctionDefinitionTypesBuilder}).
	 */
	private static TypeRef inferReturnTypeFromReturns(FunctionDefinition funDef, BuiltInTypeScope scope) {
		boolean hasNonVoidReturn = funDef.getBody() != null && funDef.getBody().hasNonVoidReturn();
		if (hasNonVoidReturn) {
			return scope.getAnyTypeRef();
		} else {
			return scope.getVoidTypeRef();
		}
	}

	/**
	 * Returns true iff given type is a built-in type, i.e. resides in a resource with scheme "n4scheme". It is safe to
	 * call this with a proxy (won't be resolved and returns proper result).
	 */
	public static boolean isBuiltIn(Type type) {
		return N4Scheme.isFromResourceWithN4Scheme(type);
	}

	/**
	 * Checks whether the given type ref is variadic and -- if positive -- wraps it in an array.
	 *
	 * @param scope
	 *            BuiltInScope
	 * @param typeRef
	 *            TypeRef to check
	 * @param fpar
	 *            Formal Parameter which the type ref belongs to
	 * @return wrapped type ref or original type ref
	 */
	public static TypeRef wrapIfVariadic(BuiltInTypeScope scope, TypeRef typeRef, FormalParameter fpar) {
		if (typeRef != null && fpar.isVariadic()) {
			return createTypeRef(scope.getArrayType(), typeRef);
		}
		return typeRef;
	}

	/**
	 * Returns with {@code true} if the member argument is
	 * <ul>
	 * <li>*NOT* {@link TMember#isOptional() optional} member,</li>
	 * <li>*NOT* {@link #isInitializedField(TMember) initialized field} and</li>
	 * <li>*NOT* {@link #isOptionalSetter(TMember) optional setter}.</li>
	 * </ul>
	 * Otherwise returns with {@code false}.
	 */
	public static boolean isMandatoryField(TMember it) {
		return null != it && !it.isOptional() && !isInitializedField(it) && !isOptionalSetter(it);
	}

	/**
	 * Argument is an instance of a {@link TField field} and {@link TField#isHasExpression() has initializer
	 * expression}. This method is {@code null} safe.
	 */
	public static boolean isInitializedField(TMember it) {
		return (it instanceof TField) ? ((TField) it).isHasExpression() : false;
	}

	/**
	 * Returns {@code true} if the member argument is an instance of a {@link TSetter setter} and has
	 * {@link AnnotationDefinition#PROVIDES_INITIALZER} annotation. Otherwise returns with {@code false}.
	 */
	public static boolean isOptionalSetter(TMember it) {
		return it instanceof TSetter && AnnotationDefinition.PROVIDES_INITIALZER.hasAnnotation(it);
	}
}
