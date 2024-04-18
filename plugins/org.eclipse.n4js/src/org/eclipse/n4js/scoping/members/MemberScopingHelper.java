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
package org.eclipse.n4js.scoping.members;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.booleanTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.StaticWriteAccessFilterScope;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareMemberScope;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.CompositeScope;
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope;
import org.eclipse.n4js.scoping.utils.UberParentScope;
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

import com.google.inject.Inject;

/**
 */
public class MemberScopingHelper {
	@Inject
	N4JSTypeSystem ts;
	@Inject
	TypeSystemHelper tsh;
	@Inject
	MemberScope.MemberScopeFactory memberScopeFactory;
	@Inject
	private MemberVisibilityChecker memberVisibilityChecker;
	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Create a new member scope that filters using the given criteria (visibility, static access). Members retrieved
	 * via the scope returned by this method are guaranteed to be contained in a resource.
	 * <p>
	 * When choosing static scope, the {@code context} is inspected to determine read/write access but only if it's a
	 * {@link ParameterizedPropertyAccessExpression} or a {@code IndexedAccessExpression}.
	 *
	 * @param receiverTypeRef
	 *            TypeRef for the value whose scope is of interest.
	 * @param context
	 *            AST node used for (a) obtaining context resource, (b) visibility checking, and (c) caching composed
	 *            members.
	 * @param checkVisibility
	 *            if true, the member scope will be wrapped in a {@link VisibilityAwareMemberScope}.
	 * @param staticAccess
	 *            true: only static members are relevant; false: only non-static ones.
	 * @param structFieldInitMode
	 *            see {@link AbstractMemberScope#structFieldInitMode}.
	 */
	public IScope createMemberScope(TypeRef receiverTypeRef, EObject context,
			boolean checkVisibility, boolean staticAccess, boolean structFieldInitMode) {

		MemberScopeRequest request = new MemberScopeRequest(receiverTypeRef, context, true, checkVisibility,
				staticAccess,
				structFieldInitMode, receiverTypeRef.isDynamic());
		return decoratedMemberScopeFor(receiverTypeRef, request);
	}

	/**
	 * Same as {@link #createMemberScope(TypeRef, EObject, boolean, boolean, boolean)}, but the returned scope
	 * <b><u>DOES NOT</u></b> guarantee that members will be contained in a resource (in particular, composed members
	 * will not be contained in a resource). In turn, this method does not require a context of type
	 * {@link MemberAccess}.
	 * <p>
	 * This method can be used if members are only used temporarily for a purpose that does not require proper
	 * containment of the member, e.g. retrieving the type of a field for validation purposes. There are two reasons for
	 * using this method:
	 * <ol>
	 * <li>client code is unable to provide a context of type {@link MemberAccess},
	 * <li>client code wants to invoke {@link IScope#getAllElements()} or similar methods on the returned scope and
	 * wants to avoid unnecessary caching of all those members.
	 * </ol>
	 */
	public IScope createMemberScopeAllowingNonContainedMembers(TypeRef receiverTypeRef, EObject context,
			boolean checkVisibility, boolean staticAccess, boolean structFieldInitMode) {

		MemberScopeRequest request = new MemberScopeRequest(receiverTypeRef, context, false, checkVisibility,
				staticAccess,
				structFieldInitMode, receiverTypeRef.isDynamic());
		return decoratedMemberScopeFor(receiverTypeRef, request);
	}

	/**
	 * Creates member scope via #members and decorates it via #decorate.
	 */
	private IScope decoratedMemberScopeFor(TypeRef typeRef, MemberScopeRequest memberScopeRequest) {
		if (typeRef == null) {
			return IScope.NULLSCOPE;
		}
		var result = members(typeRef, memberScopeRequest);
		return result;
	}

	/**
	 * Called only be members functions to decorate returned scope.
	 */
	private IScope decorate(IScope scope, MemberScopeRequest memberScopeRequest, TypeRef receiverTypeRef) {
		if (scope == IScope.NULLSCOPE) {
			return scope;
		}
		IScope decoratedScope = scope;
		if (memberScopeRequest.checkVisibility &&
				!FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, VisibilityAwareMemberScope.class)) {
			decoratedScope = new VisibilityAwareMemberScope(decoratedScope, memberVisibilityChecker, receiverTypeRef,
					memberScopeRequest.context);
		}
		if (memberScopeRequest.staticAccess &&
				!FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, StaticWriteAccessFilterScope.class)) {
			decoratedScope = new StaticWriteAccessFilterScope(decoratedScope, memberScopeRequest.context);
		}
		if (memberScopeRequest.checkVisibility &&
				!FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, TypingStrategyAwareMemberScope.class)) {
			decoratedScope = new TypingStrategyAwareMemberScope(decoratedScope, receiverTypeRef,
					memberScopeRequest.context);
		}
		return decoratedScope;
	}

	/**
	 * For the member given by (name, staticAccess) return the erroneous descriptions from the given scope.
	 */
	public Iterable<IEObjectDescriptionWithError> getErrorsForMember(IScope scope, String memberName) {
		Iterable<IEObjectDescription> descriptions = scope.getElements(QualifiedName.create(memberName));
		Iterable<IEObjectDescriptionWithError> errorsOrNulls = map(descriptions,
				d -> IEObjectDescriptionWithError.getDescriptionWithError(d));
		return filterNull(errorsOrNulls);
	}

	@SuppressWarnings("unused")
	private IScope members(UnknownTypeRef type, MemberScopeRequest request) {
		return new DynamicPseudoScope();
	}

	@SuppressWarnings("unused")
	private IScope members(LiteralTypeRef ltr, MemberScopeRequest request) {
		throw new UnsupportedOperationException("missing method for " + ltr.eClass().getName());
	}

	@SuppressWarnings("unused")
	private IScope members(BooleanLiteralTypeRef ltr, MemberScopeRequest request) {
		RuleEnvironment G = newRuleEnvironment(request.context);
		return members(booleanTypeRef(G), request);
	}

	@SuppressWarnings("unused")
	private IScope members(NumericLiteralTypeRef ltr, MemberScopeRequest request) {
		RuleEnvironment G = newRuleEnvironment(request.context);
		return members(numberTypeRef(G), request); // no need to distinguish between number and int
	}

	@SuppressWarnings("unused")
	private IScope members(StringLiteralTypeRef ltr, MemberScopeRequest request) {
		RuleEnvironment G = newRuleEnvironment(request.context);
		return members(stringTypeRef(G), request);
	}

	private IScope members(EnumLiteralTypeRef ltr, MemberScopeRequest request) {
		RuleEnvironment G = newRuleEnvironment(request.context);
		return members(N4JSLanguageUtils.getLiteralTypeBase(G, ltr), request);
	}

	private IScope members(ParameterizedTypeRef ptr, MemberScopeRequest request) {
		IScope result = membersOfType(ptr.getDeclaredType(), request);
		if (ptr.isDynamic() && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(decorate(result, request, ptr));
		}
		return decorate(result, request, ptr);
	}

	private IScope members(ParameterizedTypeRefStructural ptrs, MemberScopeRequest request) {
		IScope result = membersOfType(ptrs.getDeclaredType(), request);
		if (ptrs.isDynamic() && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(decorate(result, request, ptrs));
		}
		if (ptrs.getStructuralMembers().isEmpty()) {
			return decorate(result, request, ptrs);
		}
		IScope memberScopeRaw;
		if (ptrs.getStructuralType() != null) {
			memberScopeRaw = memberScopeFactory.create(result, ptrs.getStructuralType(), request.context,
					request.staticAccess,
					request.structFieldInitMode, request.isDynamicType);
		} else {
			// note: these are not the members of the defined type
			// however, we only scope locally, so that doesn't matter
			memberScopeRaw = memberScopeFactory.create(result, ptrs.getStructuralMembers(), request.context,
					request.staticAccess,
					request.structFieldInitMode, request.isDynamicType);
		}

		return decorate(memberScopeRaw, request, ptrs);
	}

	/**
	 * Note: N4JSScopeProvider already taking the upper bound before using this class (thus resolving ThisTypeRefs
	 * beforehand), so we will never enter this method from there; still provided to support uses from other code.
	 */
	private IScope members(ThisTypeRef thisTypeRef, MemberScopeRequest request) {
		// taking the upper bound to "resolve" the ThisTypeRef:
		// this[C] --> C (ParameterizedTypeRef)
		// ~~this[C] with { number prop; } --> ~~C with { number prop; } (ParameterizedTypeRefStructural)
		TypeRef ub = ts.upperBoundWithReopen(newRuleEnvironment(request.context), thisTypeRef);

		if (ub != null) { // ThisTypeRef was resolved
			return members(ub, request);
		}

		// probably an unbound ThisTypeRef or some other error (reported elsewhere)
		return IScope.NULLSCOPE;
	}

	private IScope members(TypeTypeRef ttr, MemberScopeRequest request) {
		MemberScopeRequest staticRequest = request.enforceStatic();
		RuleEnvironment G = newRuleEnvironment(request.context);
		Type ctrStaticType = tsh.getStaticType(G, ttr, true);
		IScope staticMembers = membersOfType(ctrStaticType, staticRequest); // staticAccess is always true in this case
		if (ctrStaticType instanceof TEnum) {
			// enums have their literals as static members
			staticMembers = decorate(Scopes.scopeFor(((TEnum) ctrStaticType).getLiterals(), staticMembers), request,
					ttr);
		}
		if (ttr.isDynamic() && !(staticMembers instanceof DynamicPseudoScope)) {
			staticMembers = new DynamicPseudoScope(decorate(staticMembers, staticRequest, ttr));
		}
		// in addition, we need instance members of either Function (in case of constructor{T}) or Object (for type{T})
		// except for @NumberBased/@StringBased enums:
		if (ctrStaticType instanceof TEnum && N4JSLanguageUtils.getEnumKind((TEnum) ctrStaticType) != EnumKind.Normal) {
			return decorate(staticMembers, staticRequest, ttr);
		}
		MemberScopeRequest instanceRequest = request.enforceInstance();
		BuiltInTypeScope builtInScope = BuiltInTypeScope.get(getResourceSet(ttr, request.context));
		TClassifier functionType = (ttr.isConstructorRef()) ? builtInScope.getFunctionType()
				: builtInScope.getObjectType();
		IScope ftypeScope = membersOfType(functionType, instanceRequest);
		IScope result = CompositeScope.create(
				// order matters (shadowing!)
				decorate(staticMembers, staticRequest, ttr),
				decorate(ftypeScope, instanceRequest, ttr));
		return result;
	}

	private IScope members(UnionTypeExpression uniontypeexp, MemberScopeRequest request) {
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(request.context);
		IScope anyPlusScope = null;
		List<IScope> subScopes = new ArrayList<>();
		for (TypeRef elementTypeRef : uniontypeexp.getTypeRefs()) {
			boolean structFieldInitMode = elementTypeRef
					.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
			IScope scope = members(elementTypeRef, request.setStructFieldInitMode(structFieldInitMode));
			if (RuleEnvironmentExtensions.isAnyDynamic(G, elementTypeRef)) {
				anyPlusScope = scope;
			} else {
				subScopes.add(scope);
			}
		}

		// only create union scope if really necessary, remember this optimization in test, since union{A} tests scope
		// of A only!
		IScope subScope = null;
		if (subScopes.size() == 1) {
			subScope = subScopes.get(0);
		} else if (subScopes.size() > 1) {
			subScope = new UnionMemberScope(uniontypeexp, request, subScopes, ts);
		}

		if (anyPlusScope == null && subScope == null) {
			return IScope.NULLSCOPE;
		}
		if (anyPlusScope == null) {
			return subScope;
		}
		if (subScope == null) {
			return anyPlusScope;
		}
		return new UberParentScope("", subScope, anyPlusScope);
	}

	private IScope members(IntersectionTypeExpression intersectiontypeexp, MemberScopeRequest request) {
		if (intersectiontypeexp.getTypeRefs().isEmpty()) {
			return IScope.NULLSCOPE;
		}

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(request.context);
		IScope anyPlusScope = null;
		List<IScope> subScopes = new ArrayList<>();
		for (TypeRef elementTypeRef : intersectiontypeexp.getTypeRefs()) {
			boolean structFieldInitMode = elementTypeRef
					.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
			IScope scope = members(elementTypeRef, request.setStructFieldInitMode(structFieldInitMode));
			if (RuleEnvironmentExtensions.isAnyDynamic(G, elementTypeRef)) {
				anyPlusScope = scope;
			} else {
				subScopes.add(scope);
			}
		}

		// only create union scope if really necessary, remember this optimization in test, since union{A} tests scope
		// of A only!
		IScope subScope = null;
		if (subScopes.size() == 1) {
			subScope = subScopes.get(0);
		} else if (subScopes.size() > 1) {
			subScope = new IntersectionMemberScope(intersectiontypeexp, request, subScopes, ts);
		}

		if (anyPlusScope == null && subScope == null) {
			return IScope.NULLSCOPE;
		}
		if (subScope != null) {
			return subScope;
		}
		return anyPlusScope;
	}

	private IScope members(FunctionTypeRef ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request);
	}

	private IScope members(FunctionTypeExpression ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request);
	}

	/**
	 * delegated from two methods above, to avoid catch-all of ParameterizedTypeRef for FuntionTypeRefs while
	 * dispatching
	 */
	private IScope membersOfFunctionTypeRef(FunctionTypeExprOrRef ftExpr, MemberScopeRequest request) {
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(getResourceSet(ftExpr, request.context));
		TClass fType = builtInTypeScope.getFunctionType();
		IScope ret = membersOfType(fType, request);
		return decorate(ret, request, ftExpr);
	}

	@SuppressWarnings("unused")
	private IScope membersOfType(UndefinedType type, MemberScopeRequest request) {
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		return IScope.NULLSCOPE;
	}

	@SuppressWarnings("unused")
	private IScope membersOfType(Void type, MemberScopeRequest request) {
		return new DynamicPseudoScope();
	}

	/**
	 * Primitive types have no members, but they can be auto-boxed to their corresponding object type which then,
	 * transparently to the user, provide members.
	 */
	private IScope membersOfType(PrimitiveType prim, MemberScopeRequest request) {
		TClassifier boxedType = prim.getAutoboxedType();
		return (boxedType != null) ? membersOfType(boxedType, request) : IScope.NULLSCOPE;
	}

	/**
	 * Creates member scope with parent containing members of implicit super types.
	 */
	private IScope membersOfType(ContainerType<?> type, MemberScopeRequest request) {
		IScope parentScope = (jsVariantHelper.activateDynamicPseudoScope(request.context))
				// cf. sec. 13.1
				? new DynamicPseudoScope()
				: IScope.NULLSCOPE;

		if (!request.staticAccess && type instanceof TClass && N4Scheme.isFromResourceWithN4Scheme(type)) {
			// classifiers defined in builtin_js.n4jsd and builtin_n4.n4jsd are allowed to extend primitive
			// types, and the following is required to support auto-boxing in such a case:
			Type rootSuperType = getRootSuperType((TClass) type);
			if (rootSuperType instanceof PrimitiveType) {
				TClassifier boxedType = ((PrimitiveType) rootSuperType).getAutoboxedType();
				if (boxedType != null) {
					parentScope = memberScopeFactory.create(parentScope, boxedType, request.context,
							request.staticAccess, request.structFieldInitMode, request.isDynamicType);
				}
			}
		}

		return memberScopeFactory.create(parentScope, type, request.context, request.staticAccess,
				request.structFieldInitMode, request.isDynamicType);
	}

	/**
	 * Returns a scope of the literals, that is members such as name or value. That is, the instance members of an
	 * enumeration. The static members are made available in {@link #members(EnumLiteralTypeRef, MemberScopeRequest)}
	 */
	private IScope membersOfType(TEnum enumeration, MemberScopeRequest request) {
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(getResourceSet(enumeration, request.context));
		// IDE-1221 select built-in type depending on whether this enumeration is tagged number-/string-based
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(enumeration);
		TClass specificEnumType = null;
		switch (enumKind) {
		case Normal:
			specificEnumType = builtInTypeScope.getN4EnumType();
			break;
		case NumberBased:
			specificEnumType = builtInTypeScope.getN4NumberBasedEnumType();
			break;
		case StringBased:
			specificEnumType = builtInTypeScope.getN4StringBasedEnumType();
			break;
		}
		return membersOfType(specificEnumType, request);
	}

	private IScope membersOfType(TStructuralType structType, MemberScopeRequest request) {
		if (structType.getOwnedMembers().isEmpty()) {
			return IScope.NULLSCOPE;
		}
		return memberScopeFactory.create(structType, request.context, request.staticAccess, request.structFieldInitMode,
				request.isDynamicType);
	}

	private ResourceSet getResourceSet(EObject type, EObject context) {
		var result = EcoreUtilN4.getResourceSet(type, context);
		if (result == null) {
			throw new IllegalStateException("type or context must be contained in a ResourceSet");
		}
		return result;
	}

	private Type getRootSuperType(TClass type) {
		Type curr = type;
		Type next;
		do {
			next = null;
			if (curr instanceof TClass) {
				TClass tc = (TClass) curr;
				next = tc.getSuperClassRef() == null ? null : tc.getSuperClassRef().getDeclaredType();
			}
			if (next != null) {
				curr = next;
			}
		} while (next != null);
		return curr;
	}

	private IScope members(TypeRef ftExpr, MemberScopeRequest request) {
		if (ftExpr instanceof FunctionTypeRef) {
			return members((FunctionTypeRef) ftExpr, request);
		} else if (ftExpr instanceof ParameterizedTypeRefStructural) {
			return members((ParameterizedTypeRefStructural) ftExpr, request);
		} else if (ftExpr instanceof FunctionTypeExpression) {
			return members((FunctionTypeExpression) ftExpr, request);
		} else if (ftExpr instanceof IntersectionTypeExpression) {
			return members((IntersectionTypeExpression) ftExpr, request);
		} else if (ftExpr instanceof ParameterizedTypeRef) {
			return members((ParameterizedTypeRef) ftExpr, request);
		} else if (ftExpr instanceof ThisTypeRef) {
			return members((ThisTypeRef) ftExpr, request);
		} else if (ftExpr instanceof TypeTypeRef) {
			return members((TypeTypeRef) ftExpr, request);
		} else if (ftExpr instanceof UnionTypeExpression) {
			return members((UnionTypeExpression) ftExpr, request);
		} else if (ftExpr instanceof BooleanLiteralTypeRef) {
			return members((BooleanLiteralTypeRef) ftExpr, request);
		} else if (ftExpr instanceof EnumLiteralTypeRef) {
			return members((EnumLiteralTypeRef) ftExpr, request);
		} else if (ftExpr instanceof NumericLiteralTypeRef) {
			return members((NumericLiteralTypeRef) ftExpr, request);
		} else if (ftExpr instanceof StringLiteralTypeRef) {
			return members((StringLiteralTypeRef) ftExpr, request);
		} else if (ftExpr instanceof LiteralTypeRef) {
			return members((LiteralTypeRef) ftExpr, request);
		} else if (ftExpr instanceof UnknownTypeRef) {
			return members((UnknownTypeRef) ftExpr, request);
		} else if (ftExpr != null) {
			return IScope.NULLSCOPE;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(ftExpr, request).toString());
		}
	}

	private IScope membersOfType(Type structType, MemberScopeRequest request) {
		if (structType instanceof TStructuralType) {
			return membersOfType((TStructuralType) structType, request);
		} else if (structType instanceof ContainerType) {
			return membersOfType((ContainerType<?>) structType, request);
		} else if (structType instanceof PrimitiveType) {
			return membersOfType((PrimitiveType) structType, request);
		} else if (structType instanceof UndefinedType) {
			return membersOfType((UndefinedType) structType, request);
		} else if (structType instanceof TEnum) {
			return membersOfType((TEnum) structType, request);
		} else if (structType != null) {
			// TODO member computation should be extracted
			if (structType.eIsProxy()) {
				return new DynamicPseudoScope();
			}
			if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
				return new DynamicPseudoScope();
			}

			return IScope.NULLSCOPE;
		} else {
			return membersOfType((Void) null, request);
		}
	}
}
