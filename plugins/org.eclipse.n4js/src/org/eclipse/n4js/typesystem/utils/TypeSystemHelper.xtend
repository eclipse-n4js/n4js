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

import com.google.common.collect.Iterables
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Arrays
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.n4idl.versioning.N4IDLVersionResolver
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeExtensions
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.DerivationComputer.BoundType
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.utils.StructuralTypesHelper
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

	@Inject private N4IDLVersionResolver versionResolver;

	// *****************************************************************************************************
	//   forwarding of utility methods implemented in strategy classes
	// *****************************************************************************************************

	@Inject private DerivationComputer derivationComputer;

	@Inject private GenericsComputer genericsComputer;

	@Inject private SimplifyComputer simplifyComputer;
	@Inject private JoinComputer joinComputer;
	@Inject private MeetComputer meetComputer;

	@Inject private SubtypeComputer subtypeComputer;
	@Inject private ExpectedTypeComputer expectedTypeCompuer;
	@Inject private StructuralTypingComputer structuralTypingComputer;


@Inject private StructuralTypesHelper structuralTypesHelper;
def StructuralTypesHelper getStructuralTypesHelper() {
	return structuralTypesHelper;
}
def StructuralTypingComputer getStructuralTypingComputer() {
	return structuralTypingComputer;
}


	def FunctionTypeExpression createSubstitutionOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		derivationComputer.createSubstitutionOfFunctionTypeExprOrRef(G,F);
	}
	def FunctionTypeExpression createUpperBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		derivationComputer.createUpperBoundOfFunctionTypeExprOrRef(G,F);
	}
	def FunctionTypeExpression createLowerBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F) {
		derivationComputer.createLowerBoundOfFunctionTypeExprOrRef(G,F);
	}
	def FunctionTypeExpression createBoundOfFunctionTypeExprOrRef(RuleEnvironment G, FunctionTypeExprOrRef F, BoundType boundType) {
		derivationComputer.createBoundOfFunctionTypeExprOrRef(G,F,boundType);
	}

	def void addSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		genericsComputer.addSubstitutions(G,typeRef)
	}
	def void addSubstitutions(RuleEnvironment G, ParameterizedCallExpression callExpr, TypeRef targetTypeRef) {
		genericsComputer.addSubstitutions(G,callExpr,targetTypeRef)
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

	def TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		simplifyComputer.createUnionType(G,elements)
	}

	def TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		simplifyComputer.createIntersectionType(G,elements)
	}
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType) {
		simplifyComputer.simplify(G,composedType)
	}
	def List<TypeRef> getSimplifiedTypeRefs(RuleEnvironment G, ComposedTypeRef composedType) {
		simplifyComputer.getSimplifiedTypeRefs(G,composedType)
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




	// *****************************************************************************************************
	//   small utility methods that do not have their own strategy class
	// *****************************************************************************************************

	/** see {@link N4JSTypeSystem#resolveType(RuleEnvironment,TypeArgument)} */
	public def TypeRef resolveType(RuleEnvironment G, TypeArgument typeArg) {
		var typeRef = if(typeArg !== null) ts.upperBound(G, typeArg).value;
		typeRef = if(typeRef !== null) TypeUtils.resolveTypeVariable(typeRef);
		// TODO IDE-2367 recursively resolve the resulting 'typeRef' until it is stable (requires refactoring of upper/lower bound judgment!)
		return typeRef;
	}

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

	public def TypeRef sanitizeTypeOfVariableFieldProperty(RuleEnvironment G, TypeArgument typeRaw) {
		if (typeRaw===null || typeRaw instanceof UnknownTypeRef) {
			return G.anyTypeRef;
		}
		val typeUB = ts.upperBound(G, typeRaw).value; // take upper bound to get rid of ExistentialTypeRef (if any)
		val declType = typeUB.declaredType
		if (declType===G.undefinedType || declType===G.nullType || declType===G.voidType) {
			// don't use these types to type variables, fields, properties -> replace with any
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
	public static def TypeRef declaredThisType(IdentifiableElement type) {
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
	 * Helper Method checking existence of "@StringBased" annotation.
	 */
	public static def boolean isStringBasedEnumeration (TEnum tEnum) {
		return tEnum.annotations.exists[it.name == AnnotationDefinition.STRING_BASED.name]
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
		val boundThisTypeRef = ts.thisTypeRef(G, location).value;
		val localG = G.wrap;
		localG.addThisType(boundThisTypeRef);
		// substitute all unbound ThisTypeRefs with the newly created BoundThisTypeRef
		return ts.substTypeVariables(localG, typeRef);
	}

	/**
	 * Checks if a value of type <code>typeRef</code> is "callable", i.e. if it can be directly invoked using a call
	 * expression.
	 */
	def public boolean isCallable(RuleEnvironment G, TypeRef typeRef) {
		if(isClassConstructorFunction(G, typeRef)) {
			// don't allow direct invocation of class constructors
			if(getCallableClassConstructorFunction(G, typeRef)!==null)
				return true; // exception: this is a class that provides a callable constructor function
			return false;
		}
		if(typeRef.declaredType instanceof TFunction)
			return true;
		if(typeRef instanceof FunctionTypeExprOrRef)
			return true;
		if (ts.subtypeSucceeded(G, typeRef, G.structuralFunctionTypeRef))
			return true;
		if(ts.subtypeSucceeded(G, typeRef, G.functionTypeRef))
			return true;
		if(typeRef.dynamic && ts.subtypeSucceeded(G, G.functionTypeRef, typeRef))
			return true;
		return false;
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
			if(cls instanceof TClass || cls instanceof TObjectPrototype)
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
			if(cls instanceof TClass || cls instanceof TObjectPrototype)
				type = cls;
		}
		if(type instanceof ContainerType<?>) {
			return type.callableCtor;
		}
		return null;
	}

	/**
	 * Returns the so-called "static type" of the given {@link TypeTypeRef} or <code>null</code> if not
	 * available.
	 * <p>
	 * Formerly, this was a utility operation in {@code TypeRefs.xcore} but since the introduction of wildcards in
	 * {@code TypeTypeRef}s the 'upperBound' judgment (and thus a RuleEnvironment) is required to compute this
	 * and hence it was moved here.
	 */
	def public Type getStaticType(RuleEnvironment G, TypeTypeRef ctorTypeRef) {
		return getStaticTypeRef(G, ctorTypeRef)?.declaredType; // will return null if #getStaticTypeRef() is not of type ParameterizedTypeRef
	}

	def public TypeRef getStaticTypeRef(RuleEnvironment G, TypeTypeRef ctorTypeRef) {
		var typeArg = ctorTypeRef.typeArg;
		while(typeArg instanceof Wildcard || typeArg instanceof ExistentialTypeRef || typeArg instanceof BoundThisTypeRef) {
			typeArg = ts.upperBound(G, typeArg).value;
		}
		return typeArg as TypeRef;
	}

	/**
	 * Creates a parameterized type ref to the wrapped static type of a TypeTypeRef, configured with the given
	 * TypeArguments. Returns UnknownTypeRef if the static type could not be retrieved (e.g. unbound This-Type).
	 */
	def public TypeRef createTypeRefFromStaticType(RuleEnvironment G, TypeTypeRef ctr, TypeArgument... typeArgs) {
		 val typeRef = getStaticTypeRef(G, ctr);
		 val type = typeRef.declaredType;
		 if (type !== null ) {
		 	 var resultTypeRef = TypeExtensions.ref(type,typeArgs);
		 	 return versionResolver.resolveVersion(resultTypeRef, typeRef);
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
	 * actual (outer) return type, which is {@code Generator<TYield,TReturn,TNext>}).
	 */
	def TypeRef getActualGeneratorReturnType(RuleEnvironment G, Expression expr) {
		val funDef = EcoreUtil2.getContainerOfType(expr?.eContainer, FunctionDefinition);
		val G2 = G.wrap;
		val myThisTypeRef = ts.thisTypeRef(G, expr).value;
		G2.addThisType(myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		if (funDef === null || !funDef.isGenerator)
			return null; // yields only occur in generator functions

		val tFun = funDef.definedType;
		if (tFun instanceof TFunction) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			val scope = G.getPredefinedTypes().builtInTypeScope;
			if (TypeUtils.isGenerator(actualReturnTypeRef, scope)) {
				return actualReturnTypeRef;
			}
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code Generator<TYield,TReturn,TNext>} class, this method returns TYield, if existent.
	 */
	def TypeRef getGeneratorTYield(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef yieldTypeRef = null;
		if (generatorTypeRef.typeArgs.length === 3) {
			val yieldTypeArg = generatorTypeRef.typeArgs.get(0);
			if (yieldTypeArg !== null)
				yieldTypeRef = ts.upperBound(G, yieldTypeArg).value; // take upper bound to get rid of Wildcard, etc.
		}
		return yieldTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code Generator<TYield,TReturn,TNext>} class, this method returns TReturn, if existent.
	 */
	def TypeRef getGeneratorTReturn(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef returnTypeRef = null;
		if (generatorTypeRef.typeArgs.length === 3) {
			val returnTypeArg = generatorTypeRef.typeArgs.get(1);
			if (returnTypeArg !== null)
				returnTypeRef = ts.upperBound(G, returnTypeArg).value; // take upper bound to get rid of Wildcard, etc.
		}
		return returnTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code Generator<TYield,TReturn,TNext>} class, this method returns TNext, if existent.
	 */
	def TypeRef getGeneratorTNext(RuleEnvironment G, TypeRef generatorTypeRef) {
		var TypeRef nextTypeRef = null;
		if (generatorTypeRef.typeArgs.length === 3) {
			val nextTypeArg = generatorTypeRef.typeArgs.get(2);
			if (nextTypeArg !== null)
				nextTypeRef = ts.upperBound(G, nextTypeArg).value; // take upper bound to get rid of Wildcard, etc.
		}
		return nextTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to an {@code Iterable<T>}, this method returns T, if existent.
	 */
	def TypeRef getIterableTypeArg(RuleEnvironment G, TypeRef iterableTypeRef) {
		var TypeRef typeRef = null;
		if (iterableTypeRef.typeArgs.length === 1) {
			val nextTypeArg = iterableTypeRef.typeArgs.get(0);
			if (nextTypeArg !== null)
				typeRef = ts.upperBound(G, nextTypeArg).value; // take upper bound to get rid of Wildcard, etc.
		}
		return typeRef;
	}
}
