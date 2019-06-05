/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.migrations

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.ts.types.TInterface

/**
 * The MigrationSwitchComputer can be used to compute a {@link SwitchCondition} which 
 * represents a runtime condition that matches values of a given compile-time {@link TypeRef} 
 * at runtime (within limits).
 */
class MigrationSwitchComputer {
	
	/**
	 * This exception is thrown when a {@link TypeRef} is passed to a {@link MigrationSwitchComputer}
	 * but the type of {@link TypeRef} is not handled by the current implementation of the computer.
	 * 
	 * For instance, this may happen for {@link ComposedTypeRef}s.
	 */
	public static final class UnhandledTypeRefException extends Exception {
		new(TypeRef typeRef) {
			super(String.format("The (sub-)expression %s cannot be handled by the TypeSwitch computer", typeRef.typeRefAsString));
		}
		
	}
	
	/** 
	 * Computes a {@link SwitchCondition which detects the given {@link TypeRef} 
	 * at runtime (within limits).
	 * 
	 *  Currently the generated switch conditions support the following {@link TypeRef} features:
	 * - parameterized array types (such as A#1[] or Array<A#1>)
	 * - plain non-parameterized types (such as A#1)
	 * 
	 * There is currently no support for composed type references (such as A#1|A#2).
	 * 
	 * Furthermore, the following {@link TypeRef}s are ignored and therefore always evaluate to true
	 * in the generated switch condition:
	 * 
	 * - TypeTypeRef
	 * 
	 * All other possible {@link TypeRef}s will lead to an {@link IllegalArgumentException}.
	 */
	public def SwitchCondition compute(TypeRef ref) throws UnhandledTypeRefException {
		switch(ref) {
			ParameterizedTypeRef case isParameterizedArrayTypeRef(ref): {
				return SwitchCondition.arrayOf(compute(ref.typeArgs.get(0) as TypeRef));
			}
			ParameterizedTypeRef case isUnhandledBuiltInType(ref.declaredType): {
				// built-in types that are provided by the runtime are not supported apart from 'Array'
				throw new UnhandledTypeRefException(ref);
			}
			ParameterizedTypeRef:
				return SwitchCondition.instanceOf(ref.declaredType)
			
			TypeTypeRef case ref.typeArg instanceof TypeRef 
				&& (ref.typeArg as TypeRef).declaredType !== null: {
				return SwitchCondition.type((ref.typeArg as TypeRef).declaredType);
			}
			default: {
				throw new UnhandledTypeRefException(ref);
			}
		}
	}
	
	/**
	 * Infers the generalized {@link TypeRef} of the given typeRef, which can be recognized by
	 * a type switch.
	 * 
	 * In many cases this {@link TypeRef} will be more generic than the given typeRef, since at runtime
	 * only limited type information is available (e.g. usually no type arguments). However, it always 
	 * holds true that the returned type reference is a subtype of the given type reference typeRef. 
	 */
	public def TypeRef toSwitchRecognizableTypeRef(RuleEnvironment ruleEnv, TypeRef typeRef) throws UnhandledTypeRefException {
		val condition = this.compute(typeRef);
		return toTypeRef(ruleEnv, condition);
	}
	
	/**
	 * Converts a given {@link SwitchCondition} to the corresponding recognized {@link TypeRef}.
	 */
	public def TypeRef toTypeRef(RuleEnvironment ruleEnv, SwitchCondition condition) {
		return SwitchCondition2TypeRefConverter.toTypeRef(ruleEnv, condition);
	}
	
	/** 
	 * Converter to convert a {@link SwitchCondition} back to an equivalent {@link TypeRef}. 
	 * 
	 * Use dynamically dispatched method {@link #toTypeRef} to trigger a recursive transformation.
	 */
	private static final class SwitchCondition2TypeRefConverter {
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, AndSwitchCondition condition) {
			return TypeUtils.createNonSimplifiedIntersectionType(condition.operands.map[o | toTypeRef(env, o)])
		}
		
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, OrSwitchCondition condition) {
			return TypeUtils.createNonSimplifiedUnionType(condition.operands.map[o | toTypeRef(env, o)])
		}
		
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, TypeSwitchCondition condition) {
			return TypeUtils.createTypeRef(condition.type, TypingStrategy.DEFAULT, true);
		}
		
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, TypeTypeCondition condition) {
			return TypeUtils.createTypeTypeRef(condition.type, false);
		}
		
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, ConstantSwitchCondition condition) {
			return TypeUtils.createTypeRef(RuleEnvironmentExtensions.anyType(env));
		}
		
		public static dispatch def TypeRef toTypeRef(RuleEnvironment env, ArrayTypeSwitchCondition condition) {
			return RuleEnvironmentExtensions.arrayTypeRef(env, toTypeRef(env, condition.elementTypeCondition));
		}
	}
	
	/** Returns the BuiltInTypeScope that is used for the given context object. */
	private def BuiltInTypeScope getBuiltInTypes(EObject context) {
		return BuiltInTypeScope.get(context.eResource.resourceSet);
	}
	
	/** 
	 * Returns {@code true} iff the given {@link TypeRef} refers to a parameterized Array type.
	 * 
	 * This excludes Array type references with type variables or wildcards as type argument. 
	 */
	private def boolean isParameterizedArrayTypeRef(ParameterizedTypeRef typeRef) {
		return typeRef.declaredType == getBuiltInTypes(typeRef).arrayType 
			&& typeRef.typeArgs.size > 0
			// TODO support for wildcards with bounds
			&& typeRef.typeArgs.get(0) instanceof TypeRef;
	}
	
	/** Returns {@code true} iff the given {@code type} is an unhandled built-in type. */
	private def boolean isUnhandledBuiltInType(Type type) {
		// We cannot check for instances of interfaces that are 
		// provided by the runtime (e.g. Iterable).
		return type !== null &&
			!(type instanceof PrimitiveType) &&
			type instanceof TInterface && 
			type.providedByRuntime;
	}
}
