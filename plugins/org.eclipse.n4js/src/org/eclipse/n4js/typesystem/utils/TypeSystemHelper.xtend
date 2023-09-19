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
package org.eclipse.n4js.typesystem.utils

import com.google.common.base.Optional
import com.google.common.collect.Iterables
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedAccess
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.util.TypeExtensions
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer.StructTypingInfo
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.utils.StructuralTypesHelper
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.EcoreUtil2

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Utility methods used in the XSemantics type system. Must be injected.
 *
 * <p>Simple implementations are directly contained here. Complex operations such as join or meet are to
 * be implemented in strategy classes. For those operations, this class acts as a facade.</p>
 *
 * <p>EObject Reference Note: All methods prefer using {@link TypeUtils.copyIfContained(EObject)} instead
 * of {@link TypeUtils.copy(EObject)}. So, clients should follow this pattern as well.</p>
 *
 * Bibliography:
 * <a name="Pierce02a">[Pierce02a]</a> B. C. Pierce: Types and Programming Languages. The MIT Press, 1, 2002.
 */
@Log
@Singleton
class TypeSystemHelper {

	@Inject private N4JSTypeSystem ts;

	// *****************************************************************************************************
	//   forwarding of utility methods implemented in strategy classes
	// *****************************************************************************************************

	@Inject private GenericsComputer genericsComputer;

	@Inject private SimplifyComputer simplifyComputer;
	@Inject private JoinComputer joinComputer;
	@Inject private MeetComputer meetComputer;

	@Inject private SubtypeComputer subtypeComputer;
	@Inject private ExpectedTypeComputer expectedTypeCompuer;
	@Inject private StructuralTypingComputer structuralTypingComputer;
	@Inject private ThisTypeComputer thisTypeComputer;
	@Inject private IterableComputer iterableComputer;
	@Inject private TypeAliasComputer typeAliasComputer;

	@Inject private ContainerTypesHelper containerTypesHelper;


@Inject private StructuralTypesHelper structuralTypesHelper;
def StructuralTypesHelper getStructuralTypesHelper() {
	return structuralTypesHelper;
}


	def void addSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		genericsComputer.addSubstitutions(G, typeRef)
	}
	def void addSubstitutions(RuleEnvironment G, ParameterizedCallExpression callExpr, FunctionTypeExprOrRef targetTypeRef, boolean defaultsTypeArgsToAny) {
		genericsComputer.addSubstitutions(G, callExpr, targetTypeRef, defaultsTypeArgsToAny)
	}
	def void addSubstitutions(RuleEnvironment G, NewExpression newExpr, TMethod constructSignature) {
		genericsComputer.addSubstitutions(G, newExpr, constructSignature);
	}
	def void addSubstitutions(RuleEnvironment G, ParameterizedPropertyAccessExpression accessExpr) {
		genericsComputer.addSubstitutions(G,accessExpr)
	}
	def TypeRef substTypeVariablesInStructuralMembers(RuleEnvironment G, StructuralTypeRef typeRef) {
		genericsComputer.substTypeVariablesInStructuralMembers(G, typeRef)
	}
	def void storePostponedSubstitutionsIn(RuleEnvironment G, StructuralTypeRef typeRef) {
		genericsComputer.storePostponedSubstitutionsIn(G, typeRef);
	}
	def void restorePostponedSubstitutionsFrom(RuleEnvironment G, StructuralTypeRef typeRef) {
		genericsComputer.restorePostponedSubstitutionsFrom(G, typeRef);
	}
	/** See {@link GenericsComputer#checkTypeArgumentCompatibility(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)}. */
	def Result checkTypeArgumentCompatibility(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg,
		Optional<Variance> varianceOpt, boolean useFancyErrMsg) {

		return genericsComputer.checkTypeArgumentCompatibility(G, leftArg, rightArg, varianceOpt, useFancyErrMsg);
	}
	/** See {@link GenericsComputer#reduceTypeArgumentCompatibilityCheck(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)}. */
	def List<TypeConstraint> reduceTypeArgumentCompatibilityCheck(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg,
		Optional<Variance> varianceOpt, boolean useFancyConstraints) {
		
		return genericsComputer.reduceTypeArgumentCompatibilityCheck(G, leftArg, rightArg, varianceOpt, useFancyConstraints);
	}

	def TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		simplifyComputer.createUnionType(G,elements)
	}

	def TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		simplifyComputer.createIntersectionType(G,elements)
	}
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType) {
		simplify(G,composedType, true)
	}
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType, boolean checkSubtypes) {
		simplifyComputer.simplify(G,composedType,checkSubtypes)
	}

	/**
	 * Convenience method calling {@link join(RuleEnvironment, Iterable<TypeRef>)} with
	 * type references inside an array.
	 */
	def TypeRef join(RuleEnvironment G, TypeRef... typeRefs) {
		joinComputer.join(G, Arrays.asList(typeRefs))
	}

	/**
  	 * Returns the join, sometimes called least common super type (LCST),
	 * of the given types.
	 * @see JoinComputer#join(RuleEnvironment, Iterable<? extends TypeRef>)
	 */
	def TypeRef join(RuleEnvironment G, Iterable<? extends TypeRef> typeRefsToJoin) {
		joinComputer.join(G, typeRefsToJoin)
	}

	/**
	 * Convenience method calling {@link meet(RuleEnvironment, Iterable<TypeRef>)} with
	 * type references inside an array.
	 */
	def TypeRef meet(RuleEnvironment G, TypeRef... typeRefs) {
		meetComputer.meet(G, Arrays.asList(typeRefs))
	}

	/**
  	 * Returns the meet (first common sub type) of the given types
  	 * @see  MeetComputer#meet(RuleEnvironment, Iterable<? extends TypeRef>)
	 */
	def TypeRef meet(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		meetComputer.meet(G, typeRefs)
	}

	def boolean isSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		return subtypeComputer.isSubtypeFunction(G, left, right)
	}

	def StructuralTypingResult isStructuralSubtype(RuleEnvironment G,TypeRef left, TypeRef right) {
		return structuralTypingComputer.isStructuralSubtype(G, left, right);
	}
	def public List<TypeConstraint> reduceMembers(RuleEnvironment G, TypeRef leftTypeRef, TMember left, TMember right, StructTypingInfo info) {
		return structuralTypingComputer.reduceMembers(G, leftTypeRef, left, right, info);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfReturnValueExpression(RuleEnvironment,Expression) */
	def TypeRef getExpectedTypeOfReturnValueExpression(RuleEnvironment G, Expression returnValueExpr) {
		return expectedTypeCompuer.getExpectedTypeOfReturnValueExpression(G, returnValueExpr);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfYieldValueExpression(RuleEnvironment,YieldExpression,Expression) */
	def TypeRef getExpectedTypeOfYieldValueExpression(RuleEnvironment G, YieldExpression yieldExpr, TypeRef exprTypeRef) {
		return expectedTypeCompuer.getExpectedTypeOfYieldValueExpression(G, yieldExpr, exprTypeRef);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfYieldValueExpression(RuleEnvironment,YieldExpression,Expression) */
	def TypeRef getExpectedTypeOfFunctionOrFieldAccessor(RuleEnvironment G, FunctionOrFieldAccessor fofa) {
		return expectedTypeCompuer.getExpectedTypeOfFunctionOrFieldAccessor(G, fofa);
	}

	/** @see ThisTypeComputer#getThisTypeAtLocation(RuleEnvironment,EObject) */
	def TypeRef getThisTypeAtLocation(RuleEnvironment G, EObject location) {
		return thisTypeComputer.getThisTypeAtLocation(G, location);
	}

	/** @see IterableComputer#extractIterableElementTypes(RuleEnvironment, TypeRef) */
	public def List<TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRef) {
		return iterableComputer.extractIterableElementTypes(G, typeRef);
	}

	/** @see IterableComputer#extractIterableElementType(RuleEnvironment, TypeRef, boolean) */
	public def TypeRef extractIterableElementType(RuleEnvironment G, TypeRef typeRef, boolean includeAsyncIterable) {
		return iterableComputer.extractIterableElementType(G, typeRef, includeAsyncIterable);
	}

	/** @see TypeAliasComputer#resolveTypeAliasFlat(RuleEnvironment, TypeRef) */
	public def TypeRef resolveTypeAliasFlat(RuleEnvironment G, TypeRef typeRef) {
		return typeAliasComputer.resolveTypeAliasFlat(G, typeRef);
	}

	/** @see TypeAliasComputer#resolveTypeAliases(RuleEnvironment, TypeRef) */
	public def TypeRef resolveTypeAliases(RuleEnvironment G, TypeRef typeRef) {
		return typeAliasComputer.resolveTypeAliases(G, typeRef);
	}

	/** @see TypeAliasComputer#resolveTypeAliases(RuleEnvironment, TypeArgument) */
	public def TypeArgument resolveTypeAliases(RuleEnvironment G, TypeArgument typeArg) {
		return typeAliasComputer.resolveTypeAliases(G, typeArg);
	}




	// *****************************************************************************************************
	//   small utility methods that do not have their own strategy class
	// *****************************************************************************************************

	public def boolean allEqualType(RuleEnvironment G, TypeRef... typeRefs) {
		val len = typeRefs.size;
		if(len>=2) {
			val firstRef = typeRefs.head;
			for(var i=1;i<len;i++) {
				if(!ts.equaltypeSucceeded(G, firstRef, typeRefs.get(i)))
					return false;
			}
		}
		return true;
	}

	public def TypeRef sanitizeTypeOfVariableFieldPropertyParameter(RuleEnvironment G, TypeArgument typeRaw, boolean resolveLiteralTypes) {
		if (typeRaw===null || typeRaw instanceof UnknownTypeRef) {
			return G.anyTypeRef;
		}
		// take upper bound to get rid of wildcards, etc. (if any)
		val typeUB = if (resolveLiteralTypes) {
			// ... and also replace literal types by their base type
			ts.upperBoundWithReopenAndResolveLiteralTypes(G, typeRaw)
		} else {
			ts.upperBoundWithReopen(G, typeRaw)
		};
		// replace silly types
		val declType = typeUB.declaredType
		if (declType===G.undefinedType || declType===G.nullType || declType===G.voidType) {
			return G.anyTypeRef;
		}
		return typeUB;
	}

	public def returnStatements(FunctionDefinition definition) {
		EcoreUtilN4.getAllContentsFiltered(definition,
			[!(it instanceof Expression || it instanceof FunctionDefinition)]).filter(ReturnStatement).filter[
			it.expression !== null]
	}

	/**
	 * If possible, a dynamic version of the given type ref is returned. If the type ref is already dynamic, it is returned.
	 * This is used for making all type refs dynamic in JavaScript mode.
	 */
	public def TypeRef makeDynamic(TypeRef typeRef) {
		if (! typeRef.dynamic) {
			if (typeRef instanceof ParameterizedTypeRef) {
				val ParameterizedTypeRef dyn = TypeUtils.copyIfContained(typeRef);
				dyn.dynamic = true;
				return dyn;
			}
		}
		return typeRef;
	}

	/**
	 * Returns the explicitly declared this type, or <code>null</code>.
	 * @param type either subtype of TFunction, of FieldAccessor, or of FunctionTypeExprOrRef can have a declared this
	 *             type ("@This")
	 * @return declaredThisType if any, null in other cases.
	 */
	public static def TypeRef getDeclaredThisType(IdentifiableElement type) {
		return switch ( type ) {
			TFunction: {
				type.declaredThisType
			}
			TGetter: {
				type.declaredThisType
			}
			TSetter: {
				type.declaredThisType
			}
			FunctionTypeExprOrRef: {
				type.declaredThisType
			}
			default:
				null
		}
	}

	/**
	 * Binds and substitutes the given {@link ThisTypeRef this type reference} after wrapping the
	 * given rule environment.
	 *
	 * <p>
	 * For instance after passing a {@code ~~this} type reference into a method in the context of container
	 * {@code class A}, the type reference argument will be bound to {@code this[A]} and finally will be substituted
	 * with {@code ~~this[A]} type reference. That will be the return value of the method.
	 *
	 * @param G
	 * 		the rule environment that will be wrapped for the operation.
	 * @param location
	 * 		location within the AST for which to create a BoundThisTypeRef. Same as the argument 'location' of
	 * 		judgment 'thisTypeRef' in Xsemantics.
	 * @param typeRef
	 * 		type reference to substitute; this can either be an unbound ThisTypeRef or any other kind of TypeRef that
	 * 		contains one or more unbound ThisTypeRefs. Need not be contained in the AST (as usual for type references).
	 */
	public def bindAndSubstituteThisTypeRef(RuleEnvironment G, EObject location, TypeRef typeRef) {
		// create a BoundThisTypeRef for given location
		val boundThisTypeRef = getThisTypeAtLocation(G, location);
		val localG = G.wrap;
		localG.setThisBinding(boundThisTypeRef);
		// substitute all unbound ThisTypeRefs with the newly created BoundThisTypeRef
		return ts.substTypeVariables(localG, typeRef);
	}

	@Data
	public static class Callable {
		/** The actual type reference that contributed function/method. Used for error reporting. */
		TypeRef callableTypeRef
		/**
		 * The signature of the function/method being invoked. Used for argument checking and to obtain
		 * the type of the return value. If absent, the invocation is possible, but no information about
		 * the parameters or the return value is available (e.g. when invoking values of built-in type
		 * {@code Function}).
		 */
		Optional<? extends FunctionTypeExprOrRef> signatureTypeRef
		/**
		 * Tells whether the function/method being invoked is {@link TypeRef#isDynamic() dynamic}.
		 */
		boolean dynamic
	}

	/**
	 * Checks if a value of the given type is "callable" (i.e. can be invoked with a call expression).
	 * If so, returns an instance of class {@link Callable} with further information; if not, returns
	 * <code>null</code>.
	 */
	def public Callable getCallableTypeRef(RuleEnvironment G, TypeRef typeRef) {
		val result = getCallableTypeRefs(G, typeRef);
		return if (result.size === 1) result.get(0);
	}

	def public List<Callable> getCallableTypeRefs(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnionTypeExpression) {
			// TODO implement special handling for unions
		} else if (typeRef instanceof IntersectionTypeExpression) {
			// TODO improve special handling for intersections
			val result = <Callable>newArrayList;
			var foundWithoutSignature = false;
			var foundWithoutSignatureDynamic = false;
			for (currTypeRef : typeRef.typeRefs) {
				val currCallable = internalGetCallableTypeRef(G, currTypeRef);
				if (currCallable !== null) {
					if (!currCallable.signatureTypeRef.isPresent()) {
						foundWithoutSignature = true;
						foundWithoutSignatureDynamic = foundWithoutSignatureDynamic || currCallable.dynamic;
					} else {
						result += currCallable;
					}
				}
			}
			if (result.empty && foundWithoutSignature) {
				return Collections.singletonList(new Callable(typeRef, Optional.absent(), foundWithoutSignatureDynamic));
			}
			return result;
		}
		val result = internalGetCallableTypeRef(G, typeRef);
		if (result !== null) {
			return Collections.singletonList(result);
		}
		return Collections.emptyList();
	}

	def private Callable internalGetCallableTypeRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnknownTypeRef) {
			return null;
		}
		if (typeRef instanceof FunctionTypeExprOrRef) {
			return new Callable(typeRef, Optional.of(typeRef), typeRef.dynamic);
		}
		if (isClassConstructorFunction(G, typeRef)) {
			// don't allow direct invocation of class constructors
			val callableCtor = getCallableClassConstructorFunction(G, typeRef);
			if (callableCtor !== null) {
				// exception: this is a class that provides a call signature
				return new Callable(typeRef, Optional.of(TypeUtils.createTypeRef(callableCtor) as FunctionTypeRef), typeRef.dynamic);
			}
			return null;
		}
		val callSig = getCallSignature(G, typeRef);
		if (callSig !== null) {
			return new Callable(typeRef, Optional.of(TypeUtils.createTypeRef(callSig) as FunctionTypeRef), typeRef.dynamic);
		}
		if (ts.subtypeSucceeded(G, typeRef, G.functionTypeRef)
			|| ts.subtypeSucceeded(G, typeRef, G.structuralFunctionTypeRef)
			|| (typeRef.dynamic && ts.subtypeSucceeded(G, G.functionTypeRef, typeRef))) {
			return new Callable(typeRef, Optional.absent(), typeRef.dynamic);
		}
		return null;
	}

	@Data
	public static class Newable {
		/** The actual type reference that contributed constructor or construct signature. Used for error reporting. */
		TypeRef newableTypeRef;
		/** The constructor or construct signature. Used for argument checking. Can be null. */
		TMethod ctorOrConstructSig;
		/** The type of the newly created instance. */
		TypeRef instanceTypeRef;
	}

	def public Newable getNewableTypeRef(RuleEnvironment G, NewExpression newExpr, boolean ignoreConstructSignatures) {
		val calleeTypeRef = ts.type(G, newExpr.callee);
		return getNewableTypeRef(G, calleeTypeRef, newExpr, ignoreConstructSignatures);
	}

	/**
	 * Checks if a value of the given type is "newable" (i.e. can be instantiated with keyword "new").
	 * If so, returns an instance of class {@link Newable) with further information; if not, returns
	 * <code>null</code>.
	 */
	def public Newable getNewableTypeRef(RuleEnvironment G, TypeRef typeRef, NewExpression newExpr, boolean ignoreConstructSignatures) {
		val result = getNewableTypeRefs(G, typeRef, newExpr, ignoreConstructSignatures);
		return if (result.size === 1) result.get(0);
	}

	def public List<Newable> getNewableTypeRefs(RuleEnvironment G, TypeRef typeRef, NewExpression newExpr, boolean ignoreConstructSignatures) {

		if (typeRef instanceof UnionTypeExpression) {
			val newables = <Newable>newArrayList;
			for (currTypeRef : typeRef.typeRefs) {
				val curr = internalGetNewableTypeRef(G, currTypeRef, newExpr, ignoreConstructSignatures);
				if (curr !== null) {
					newables += curr;
				}
			}
			val List<TypeRef> constructSigsReturn = new ArrayList();
			val List<TypeRef> resTypeRefs = new ArrayList();
			for (newable : newables) {
				resTypeRefs += newable.instanceTypeRef;
				if (newable.ctorOrConstructSig !== null) {
					constructSigsReturn += newable.ctorOrConstructSig.returnTypeRef
				}
			}
			
			// use non-simplified because union with any+ will remove other types that would give additional info to the user
			val resTypeRef = TypeUtils.createNonSimplifiedUnionType(resTypeRefs);
			val constructSig = TypesFactory.eINSTANCE.createTMethod();
			constructSig.returnTypeRef = TypeUtils.createNonSimplifiedUnionType(constructSigsReturn);
			val vArgs = TypesFactory.eINSTANCE.createTFormalParameter();
			constructSig.fpars += vArgs;
			vArgs.variadic = true;
			vArgs.typeRef = G.anyTypeRefDynamic;
			// TODO improve merging of construct signatures
		
			return Collections.singletonList(new Newable(typeRef, constructSig, resTypeRef));
			
		} else if (typeRef instanceof IntersectionTypeExpression) {
			// TODO improve special handling for intersections
			val typeRef2 = simplifyComputer.simplify(G, typeRef, true);
			if (typeRef2 instanceof IntersectionTypeExpression) {
				val result = <Newable>newArrayList;
				for (currTypeRef : typeRef2.typeRefs) {
					val curr = internalGetNewableTypeRef(G, currTypeRef, newExpr, ignoreConstructSignatures);
					if (curr !== null) {
						result += curr;
					}
				}
				return result;
			} else {
				return getNewableTypeRefs(G, typeRef2, newExpr, ignoreConstructSignatures);
			}
		}
		val result = internalGetNewableTypeRef(G, typeRef, newExpr, ignoreConstructSignatures);
		if (result !== null) {
			return Collections.singletonList(result);
		}
		return Collections.emptyList();
	}

	def private Newable internalGetNewableTypeRef(RuleEnvironment G, TypeRef calleeTypeRef, NewExpression newExpr, boolean ignoreConstructSignatures) {
		if (calleeTypeRef instanceof TypeTypeRef) {
			var ctor = null as TMethod;
			val staticType = getStaticType(G, calleeTypeRef, true);
			if (staticType instanceof ContainerType<?>) {
				ctor = containerTypesHelper.fromContext(G.contextResource).findConstructor(staticType);
			}
			val instanceTypeRef = createTypeRefFromStaticType(G, calleeTypeRef, newExpr);
			return new Newable(calleeTypeRef, ctor, instanceTypeRef)
		}
		if (G.isAnyDynamic(calleeTypeRef)) {
			val constructSig = TypesFactory.eINSTANCE.createTMethod();
			constructSig.returnTypeRef = G.anyTypeRefDynamic;
			val vArgs = TypesFactory.eINSTANCE.createTFormalParameter();
			constructSig.fpars += vArgs;
			vArgs.variadic = true;
			vArgs.typeRef = G.anyTypeRefDynamic;
		
			return new Newable(calleeTypeRef, constructSig, G.anyTypeRefDynamic);
		}
		if (!ignoreConstructSignatures) {
			val constructSig = getConstructSignature(G, calleeTypeRef);
			if (constructSig !== null) {
				val returnTypeRef = constructSig.getReturnTypeRef();
				if (returnTypeRef !== null && !TypeUtils.isVoid(returnTypeRef)) {
					val G2 = wrap(G);
					addSubstitutions(G2, newExpr, constructSig);
					val returnTypeRefSubst = ts.substTypeVariablesWithFullCapture(G2, returnTypeRef);
					return new Newable(calleeTypeRef, constructSig, returnTypeRefSubst);
				}
			}
		}
		return null;
	}

	/**
	 * Checks if a value of type <code>typeRef</code> is a class constructor function.
	 */
	def public boolean isClassConstructorFunction(RuleEnvironment G, TypeRef typeRef) {
		val declaredType = typeRef.declaredType;
		if(declaredType instanceof TMethod) {
			if(declaredType.isConstructor)
				return true;
		}
		if(typeRef instanceof FunctionTypeExprOrRef) {
			val ft = typeRef.functionType;
			if(ft instanceof TMethod) {
				if(ft.isConstructor)
					return true;
			}
		}
		if(typeRef instanceof TypeTypeRef) {
			val cls = getStaticType(G, typeRef);
			if(cls instanceof TClass)
				return true;
		}
		return false;
	}
	def public TMethod getCallableClassConstructorFunction(RuleEnvironment G, TypeRef typeRef) {
		var Type type = null;
		val declaredType = typeRef.declaredType;
		if(declaredType instanceof TMethod) {
			if(declaredType.isConstructor)
				type = declaredType.containingType;
		}
		if(typeRef instanceof FunctionTypeExprOrRef) {
			val ft = typeRef.functionType;
			if(ft instanceof TMethod) {
				if(ft.isConstructor)
					type = ft.containingType;
			}
		}
		if(typeRef instanceof TypeTypeRef) {
			val cls = getStaticType(G, typeRef);
			if(cls instanceof TClass)
				type = cls;
		}
		if(type instanceof TClass) {
			// note: "callable constructors" (i.e. call signatures in classes) are not inherited
			// and cannot appear in StructuralTypeRefs, so no need for ContainerTypesHelper or
			// checking for TStructuralType here:
			return type.callSignature;
		}
		return null;
	}

	def public TMethod getCallSignature(RuleEnvironment G, TypeRef calleeTypeRef) {
		return getCallSignature(G.contextResource, calleeTypeRef);
	}

	def public TMethod getCallSignature(Resource context, TypeRef calleeTypeRef) {
		return getCallConstructSignature(context, calleeTypeRef, false);
	}

	def public TMethod getConstructSignature(RuleEnvironment G, TypeRef calleeTypeRef) {
		return getConstructSignature(G.contextResource, calleeTypeRef);
	}

	def public TMethod getConstructSignature(Resource context, TypeRef calleeTypeRef) {
		return getCallConstructSignature(context, calleeTypeRef, true);
	}

	/**
	 * NOTE: does not cover "callable constructors" (i.e. call signatures in classes); use method
	 * {@link #getCallableClassConstructorFunction(RuleEnvironment,TypeRef)} for this purpose.
	 */
	def private TMethod getCallConstructSignature(Resource context, TypeRef calleeTypeRef, boolean searchConstructSig) {
		val declType = calleeTypeRef.declaredType;
		if (declType instanceof TInterface) {
			return if (searchConstructSig) {
				containerTypesHelper.fromContext(context).findConstructSignature(declType);
			} else {
				containerTypesHelper.fromContext(context).findCallSignature(declType);
			};
		}
		if (calleeTypeRef instanceof StructuralTypeRef) {
			val structType = calleeTypeRef.structuralType;
			if (structType !== null) {
				return if (searchConstructSig) {
					structType.constructSignature
				} else {
					structType.callSignature
				};
			}
		}
		return null;
	}
	
	def public FunctionTypeExprOrRef getFunctionTypeExprOrRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof FunctionTypeExprOrRef) {
			return typeRef;
		}
		val callable = getCallableTypeRef(G, typeRef);
		if (callable !== null) {
			return callable.signatureTypeRef.orNull;
		}
		return null;
	}

	/** Same as {@link #getStaticType(RuleEnvironment, TypeTypeRef, boolean)} without resolving type variables. */
	def public Type getStaticType(RuleEnvironment G, TypeTypeRef typeTypeRef) {
		return getStaticType(G, typeTypeRef, false);
	}

	/**
	 * Returns the so-called "static type" of the given {@link TypeTypeRef} or <code>null</code> if not
	 * available. Iff {@code resolveTypeVariables} is <code>true</code>, then type variables will be resolved
	 * (i.e. replaced by their explicit or implicit upper bound).
	 * <p>
	 * Formerly, this was a utility operation in {@code TypeRefs.xcore} but since the introduction of wildcards in
	 * {@code TypeTypeRef}s the 'upperBound' judgment (and thus a RuleEnvironment) is required to compute this
	 * and hence it was moved here.
	 */
	def public Type getStaticType(RuleEnvironment G, TypeTypeRef typeTypeRef, boolean resolveTypeVariables) {
		return getStaticTypeRef(G, typeTypeRef, resolveTypeVariables)?.declaredType; // will return null if #getStaticTypeRef() is not of type ParameterizedTypeRef
	}

	def public TypeRef getStaticTypeRef(RuleEnvironment G, TypeTypeRef typeTypeRef) {
		return getStaticTypeRef(G, typeTypeRef, false);
	}

	def public TypeRef getStaticTypeRef(RuleEnvironment G, TypeTypeRef typeTypeRef, boolean resolveTypeVariables) {
		val typeArg = typeTypeRef.typeArg;
		val typeArgUB = if (resolveTypeVariables) {
			ts.upperBoundWithReopenAndResolveTypeVars(G, typeArg)
		} else {
			ts.upperBoundWithReopen(G, typeArg)
		};
		return typeArgUB;
	}

	def public TypeRef createTypeRefFromStaticType(RuleEnvironment G, TypeTypeRef ctr, ParameterizedAccess paramAccess) {
		return createTypeRefFromStaticType(G, ctr, paramAccess.typeArgs.map[typeRef]);
	}

	/**
	 * Creates a parameterized type ref to the wrapped static type of a TypeTypeRef, configured with the given
	 * TypeArguments. Returns UnknownTypeRef if the static type could not be retrieved (e.g. unbound This-Type).
	 */
	def public TypeRef createTypeRefFromStaticType(RuleEnvironment G, TypeTypeRef ctr, TypeArgument... typeArgs) {
		 val typeRef = getStaticTypeRef(G, ctr);
		 val type = typeRef?.declaredType;
		 if (type !== null) {
		 	 return TypeExtensions.ref(type, typeArgs);
		 }
		 return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
	 }

	/**
	 * This method computes the set of all subtypes in the set of TypeRefs.
	 * It does not copy the TypeRefs!
	 */
	def List<TypeRef> getSubtypesOnly(RuleEnvironment G, TypeRef... typeRefs) {
		val intersectTRs = new LinkedList<TypeRef>();

		for (s : typeRefs) {
			if (! intersectTRs.exists[ts.subtypeSucceeded(G, it, s)]) {
				Iterables.removeIf(intersectTRs, [ts.subtypeSucceeded(G, s, it)]);
				intersectTRs.add(s)
			}
		}

		return intersectTRs
	}

	/**
	 * This method computes the set of all super types in the set of TypeRefs.
	 * It does not copy the TypeRefs!
	 */
	def List<TypeRef> getSuperTypesOnly(RuleEnvironment G, TypeRef... typeRefs) {
		val unionTRs = new LinkedList<TypeRef>();

		for (s : typeRefs) {
			if (! unionTRs.exists[ts.subtypeSucceeded(G, s, it)]) {
				Iterables.removeIf(unionTRs, [ts.subtypeSucceeded(G, s, it)]);
				unionTRs.add(s)
			}
		}

		return unionTRs
	}


	/**
	 * From any expression within a generator function or method, the type TNext is returned (referring to the
	 * actual (outer) return type, which is {@code [Async]Generator<TYield,TReturn,TNext>}).
	 */
	def TypeRef getActualGeneratorReturnType(RuleEnvironment G, Expression expr) {
		val funDef = EcoreUtil2.getContainerOfType(expr?.eContainer, FunctionDefinition);
		val G2 = G.wrap;
		val myThisTypeRef = getThisTypeAtLocation(G, expr);
		G2.setThisBinding(myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		if (funDef === null || !funDef.isGenerator)
			return null; // yield only occurs in generator functions

		val tFun = funDef.definedType;
		if (tFun instanceof TFunction) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			val scope = G.getPredefinedTypes().builtInTypeScope;
			if (TypeUtils.isGeneratorOrAsyncGenerator(actualReturnTypeRef, scope)) {
				return actualReturnTypeRef;
			}
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TYield, if existent.
	 */
	def TypeRef getGeneratorTYield(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef yieldTypeRef = null;
		if (generatorTypeRef.declaredTypeArgs.size >= 1) {
			val yieldTypeArg = generatorTypeRef.declaredTypeArgs.get(0);
			if (yieldTypeArg !== null)
				yieldTypeRef = ts.upperBound(G, yieldTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			yieldTypeRef = G.generatorType.typeVars.get(0).defaultArgument;
		}
		return yieldTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TReturn, if existent.
	 */
	def TypeRef getGeneratorTReturn(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef returnTypeRef = null;
		if (generatorTypeRef.declaredTypeArgs.size >= 2) {
			val returnTypeArg = generatorTypeRef.declaredTypeArgs.get(1);
			if (returnTypeArg !== null)
				returnTypeRef = ts.upperBound(G, returnTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			returnTypeRef = G.generatorType.typeVars.get(1).defaultArgument;
		}
		return returnTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TNext, if existent.
	 */
	def TypeRef getGeneratorTNext(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef nextTypeRef = null;
		if (generatorTypeRef.declaredTypeArgs.size >= 3) {
			val nextTypeArg = generatorTypeRef.declaredTypeArgs.get(2);
			if (nextTypeArg !== null)
				nextTypeRef = ts.upperBound(G, nextTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			nextTypeRef = G.generatorType.typeVars.get(2).defaultArgument;
		}
		return nextTypeRef;
	}
}
