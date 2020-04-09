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
package org.eclipse.n4js.scoping.members

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.StaticWriteAccessFilterScope
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareMemberScope
import org.eclipse.n4js.scoping.utils.CompositeScope
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.UndefinedType
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes

/**
 */
class MemberScopingHelper {
	@Inject N4JSTypeSystem ts;
	@Inject TypeSystemHelper tsh;
	@Inject MemberScope.MemberScopeFactory memberScopeFactory
	@Inject private MemberVisibilityChecker memberVisibilityChecker
	@Inject	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Create a new member scope that filters using the given criteria (visibility, static access). Members retrieved
	 * via the scope returned by this method are guaranteed to be contained in a resource.
	 * <p>
	 * When choosing static scope, the {@code context} is inspected to determine read/write access
	 * but only if it's a {@link ParameterizedPropertyAccessExpression} or a {@code IndexedAccessExpression}.
	 *
	 * @param receiverTypeRef
	 *               TypeRef for the value whose scope is of interest.
	 * @param context
	 *               AST node used for (a) obtaining context resource, (b) visibility checking, and
	 *               (c) caching composed members.
	 * @param checkVisibility
	 *               if true, the member scope will be wrapped in a {@link VisibilityAwareMemberScope}; if
	 *               false, method {@link getPropertyTypeForNode(IScope,String)} will <b>never</b> return
	 *               {@link #INVISIBLE_MEMBER}.
	 * @param staticAccess
	 *               true: only static members are relevant; false: only non-static ones.
	 * @param structFieldInitMode
	 *               see {@link AbstractMemberScope#structFieldInitMode}.
	 */
	public def IScope createMemberScope(TypeRef receiverTypeRef, MemberAccess context,
		boolean checkVisibility, boolean staticAccess, boolean structFieldInitMode) {

		val isDynamicType = receiverTypeRef.isDynamic;
		return decoratedMemberScopeFor(receiverTypeRef,
			new MemberScopeRequest(receiverTypeRef, context, true, checkVisibility, staticAccess, structFieldInitMode, isDynamicType));
	}

	/**
	 * Same as {@link #createMemberScope(TypeRef, MemberAccess, boolean, boolean)}, but the returned scope <b><u>DOES
	 * NOT</u></b> guarantee that members will be contained in a resource (in particular, composed members will not be
	 * contained in a resource). In turn, this method does not require a context of type {@link MemberAccess}.
	 * <p>
	 * This method can be used if members are only used temporarily for a purpose that does not require proper
	 * containment of the member, e.g. retrieving the type of a field for validation purposes. There are two reasons
	 * for using this method:
	 * <ol>
	 * <li>client code is unable to provide a context of type {@link MemberAccess},
	 * <li>client code wants to invoke {@link IScope#getAllElements()} or similar methods on the returned scope and
	 *     wants to avoid unnecessary caching of all those members.
	 * </ol>
	 */
	public def IScope createMemberScopeAllowingNonContainedMembers(TypeRef receiverTypeRef, EObject context,
		boolean checkVisibility, boolean staticAccess, boolean structFieldInitMode) {

		val isDynamicType = receiverTypeRef.isDynamic;
		return decoratedMemberScopeFor(receiverTypeRef,
			new MemberScopeRequest(receiverTypeRef, context, false, checkVisibility, staticAccess, structFieldInitMode, isDynamicType));
	}

	/**
	 * Creates member scope via #members and decorates it via #decorate.
	 */
	private def IScope decoratedMemberScopeFor(TypeRef typeRef, MemberScopeRequest memberScopeRequest) {
		if(typeRef === null)
			return IScope.NULLSCOPE
		var result = members(typeRef, memberScopeRequest);
		return result;
	}

	/**
	 * Called only be members functions to decorate returned scope.
	 */
	private def IScope decorate(IScope scope, MemberScopeRequest memberScopeRequest, TypeRef receiverTypeRef) {
		if (scope == IScope.NULLSCOPE) {
			return scope;
		}
		var decoratedScope = scope;
		if (memberScopeRequest.checkVisibility &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, VisibilityAwareMemberScope)) {
			decoratedScope = new VisibilityAwareMemberScope(decoratedScope, memberVisibilityChecker, receiverTypeRef,
				memberScopeRequest.context);
		}
		if (memberScopeRequest.staticAccess &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, StaticWriteAccessFilterScope)) {
			decoratedScope = new StaticWriteAccessFilterScope(decoratedScope, memberScopeRequest.context);
		}
		if (memberScopeRequest.checkVisibility &&
			! FilterWithErrorMarkerScope.isDecoratedWithFilter(scope, TypingStrategyAwareMemberScope)) {
			decoratedScope = new TypingStrategyAwareMemberScope(decoratedScope, receiverTypeRef,
				memberScopeRequest.context);
		}
		return decoratedScope;
	}

	/**
	 * For the member given by (name, staticAccess) return the erroneous descriptions from the given scope.
	 * <p>
	 * Precondition: {@link #isNonExistentMember} has negative answer.
	 */
	public def Iterable<IEObjectDescriptionWithError> getErrorsForMember(IScope scope, String memberName,
		boolean staticAccess) {
		val descriptions = scope.getElements(QualifiedName.create(memberName))
		val errorsOrNulls = descriptions.map[d|IEObjectDescriptionWithError.getDescriptionWithError(d)]
		return errorsOrNulls.filterNull
	}

	private def dispatch IScope members(TypeRef type, MemberScopeRequest request) {
		return IScope.NULLSCOPE
	}

	private def dispatch IScope members(UnknownTypeRef type, MemberScopeRequest request) {
		return new DynamicPseudoScope()
	}

	private def dispatch IScope members(ParameterizedTypeRef ptr, MemberScopeRequest request) {
		val IScope result = membersOfType(ptr.declaredType, request);
		if (ptr.dynamic && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result.decorate(request, ptr))
		}
		return result.decorate(request, ptr)
	}

	private def dispatch IScope members(ParameterizedTypeRefStructural ptrs, MemberScopeRequest request) {
		val IScope result = membersOfType(ptrs.declaredType, request);
		if (ptrs.dynamic && !(result instanceof DynamicPseudoScope)) {
			return new DynamicPseudoScope(result.decorate(request, ptrs))
		}
		if (ptrs.structuralMembers.empty) {
			return result.decorate(request, ptrs)
		}
		val memberScopeRaw = if (ptrs.structuralType !== null) {
				memberScopeFactory.create(result, ptrs.structuralType, request.context, request.staticAccess,
					request.structFieldInitMode, request.isDynamicType);
			} else {
				// note: these are not the members of the defined type
				// however, we only scope locally, so that doesn't matter
				memberScopeFactory.create(result, ptrs.structuralMembers, request.context, request.staticAccess,
					request.structFieldInitMode, request.isDynamicType);
			}

		return decorate(memberScopeRaw, request, ptrs);
	}

	/**
	 * Note: N4JSScopeProvider already taking the upper bound before using this class (thus resolving ThisTypeRefs
	 * beforehand), so we will never enter this method from there; still provided to support uses from other code.
	 */
	private def dispatch IScope members(ThisTypeRef thisTypeRef, MemberScopeRequest request) {
		// taking the upper bound to "resolve" the ThisTypeRef:
		// this[C] --> C (ParameterizedTypeRef)
		// ~~this[C] with { number prop; } --> ~~C with { number prop; } (ParameterizedTypeRefStructural)
		val ub = ts.upperBoundWithReopen(RuleEnvironmentExtensions.newRuleEnvironment(request.context), thisTypeRef);

		if (ub !== null) { // ThisTypeRef was resolved
			return members(ub, request);
		}

		// probably an unbound ThisTypeRef or some other error (reported elsewhere)
		return IScope.NULLSCOPE;
	}

	private def dispatch IScope members(TypeTypeRef ttr, MemberScopeRequest request) {
		val MemberScopeRequest staticRequest = request.enforceStatic;
		val G = RuleEnvironmentExtensions.newRuleEnvironment(request.context);
		val ctrStaticType = tsh.getStaticType(G, ttr);
		var IScope staticMembers = membersOfType(ctrStaticType, staticRequest) // staticAccess is always true in this case
		if (ctrStaticType instanceof TEnum) {
			// enums have their literals as static members
			staticMembers = Scopes.scopeFor(ctrStaticType.literals, staticMembers).decorate(request, ttr);
		}
		if (ttr.dynamic && !(staticMembers instanceof DynamicPseudoScope)) {
			staticMembers = new DynamicPseudoScope(staticMembers.decorate(staticRequest, ttr))
		}
		// in addition, we need instance members of either Function (in case of constructor{T}) or Object (for type{T})
		// except for @StringBased enums:
		if (ctrStaticType instanceof TEnum && AnnotationDefinition.STRING_BASED.hasAnnotation(ctrStaticType)) {
			return staticMembers.decorate(staticRequest, ttr);
		}
		val MemberScopeRequest instanceRequest = request.enforceInstance;
		val builtInScope = BuiltInTypeScope.get(getResourceSet(ttr, request.context));
		val functionType = if (ttr.isConstructorRef) builtInScope.functionType else builtInScope.objectType;
		val IScope ftypeScope = membersOfType(functionType, instanceRequest);
		val result = CompositeScope.create(
			// order matters (shadowing!)
			staticMembers.decorate(staticRequest, ttr),
			ftypeScope.decorate(instanceRequest, ttr)
		);
		return result
	}

	private def dispatch IScope members(UnionTypeExpression uniontypeexp, MemberScopeRequest request) {

		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		val subScopes = uniontypeexp.typeRefs.map [ elementTypeRef |
			val structFieldInitMode = elementTypeRef.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
			val scope = members(elementTypeRef, request.setStructFieldInitMode(structFieldInitMode));
			return scope;
		]

		switch (subScopes.size) { // only create union scope if really necessary, remember this optimization in test, since union{A} tests scope of A only!
			case 0: return IScope.NULLSCOPE
			case 1: return subScopes.get(0)
			default: return new UnionMemberScope(uniontypeexp, request, subScopes, ts)
		}
	}

	private def dispatch IScope members(IntersectionTypeExpression intersectiontypeexp, MemberScopeRequest request) {
		if (intersectiontypeexp.typeRefs.isEmpty) {
			return IScope.NULLSCOPE;
		}

		val List<IScope> subScopes = intersectiontypeexp.typeRefs.map [ elementTypeRef |
			val structFieldInitMode = elementTypeRef.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
			val scope = members(elementTypeRef, request.setStructFieldInitMode(structFieldInitMode));
			return scope;
		]

		return new IntersectionMemberScope(intersectiontypeexp, request, subScopes, ts);
	}

	private def dispatch IScope members(FunctionTypeRef ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request)
	}

	private def dispatch IScope members(FunctionTypeExpression ftExpr, MemberScopeRequest request) {
		return membersOfFunctionTypeRef(ftExpr, request)
	}

	/** delegated from two methods above, to avoid catch-all of ParameterizedTypeRef for FuntionTypeRefs while dispatching */
	def private IScope membersOfFunctionTypeRef(FunctionTypeExprOrRef ftExpr, MemberScopeRequest request) {
		val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(ftExpr, request.context));
		val fType = builtInTypeScope.functionType
		val ret = membersOfType(fType, request)
		return ret.decorate(request, ftExpr);
	}

// TODO member computation should be extracted
	private def dispatch IScope membersOfType(Type type, MemberScopeRequest request) {
		if (type.eIsProxy) {
			return new DynamicPseudoScope()
		}
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		return IScope.NULLSCOPE
	}

	private def dispatch IScope membersOfType(UndefinedType type, MemberScopeRequest request) {
		if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			return new DynamicPseudoScope();
		}

		return IScope.NULLSCOPE
	}

	private def dispatch IScope membersOfType(Void type, MemberScopeRequest request) {
		return new DynamicPseudoScope()
	}

	/**
	 * Primitive types have no members, but they can be auto-boxed to their
	 * corresponding object type which then, transparently to the user, provide members.
	 */
	private def dispatch IScope membersOfType(PrimitiveType prim, MemberScopeRequest request) {
		val boxedType = prim.autoboxedType;
		return if(boxedType!==null) membersOfType(boxedType, request) else IScope.NULLSCOPE;
	}

	/**
	 * Creates member scope with parent containing members of implicit super types.
	 */
	private def dispatch IScope membersOfType(ContainerType<?> type, MemberScopeRequest request) {
		var parentScope = if (jsVariantHelper.activateDynamicPseudoScope(request.context)) { // cf. sec. 13.1
			new DynamicPseudoScope()
		} else {
			IScope.NULLSCOPE
		};

		if (!request.staticAccess && type instanceof TObjectPrototype) {
			// TObjectPrototypes defined in builtin_js.n4ts and builtin_n4.n4ts are allowed to extend primitive
			// types, and the following is required to support auto-boxing in such a case:
			val rootSuperType = getRootSuperType(type as TObjectPrototype);
			if (rootSuperType instanceof PrimitiveType) {
				val boxedType = rootSuperType.autoboxedType;
				if(boxedType!==null) {
					parentScope = memberScopeFactory.create(parentScope, boxedType, request.context,
						request.staticAccess, request.structFieldInitMode, request.isDynamicType);
				}
			}
		}

		return memberScopeFactory.create(parentScope, type, request.context, request.staticAccess,
			request.structFieldInitMode, request.isDynamicType);
	}

	/**
	 * Returns a scope of the literals, that is members such as name or value.
	 * That is, the instance members of an enumeration. The static members are made available
	 * in {@link #members(EnumTypeRef, EObject, boolean)}
	 */
	private def dispatch IScope membersOfType(TEnum enumeration, MemberScopeRequest request) {
		val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(enumeration, request.context));
		// IDE-1221 select built-in type depending on whether this enumeration is tagged string-based
		val TObjectPrototype specificEnumType = if (TypeSystemHelper.isStringBasedEnumeration(enumeration)) {
				builtInTypeScope.n4StringBasedEnumType
			} else {
				builtInTypeScope.n4EnumType
			};
		return membersOfType(specificEnumType, request);
	}

	private def dispatch IScope membersOfType(TypeVariable typeVar, MemberScopeRequest request) {
		val declUB = typeVar.declaredUpperBound;
		if (declUB!==null) {
			return members(declUB, request)
		} else {
			val builtInTypeScope = BuiltInTypeScope.get(getResourceSet(typeVar, request.context));
			val anyType = builtInTypeScope.anyType
			return membersOfType(anyType, request);
		}

	}

	private def dispatch IScope membersOfType(TStructuralType structType, MemberScopeRequest request) {
		if (structType.ownedMembers.empty) {
			return IScope.NULLSCOPE
		}
		return memberScopeFactory.create(structType, request.context, request.staticAccess, request.structFieldInitMode, request.isDynamicType);
	}

	def private ResourceSet getResourceSet(EObject type, EObject context) {
		var result = EcoreUtilN4.getResourceSet(type, context);
		if (result === null)
			throw new IllegalStateException("type or context must be contained in a ResourceSet")
		return result;
	}

	def private Type getRootSuperType(TObjectPrototype type) {
		var Type curr = type;
		var Type next;
		do {
			next = if(curr instanceof TObjectPrototype) curr.superType?.declaredType;
			if (next !== null) {
				curr = next;
			}
		} while (next !== null);
		return curr;
	}
}
