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
package org.eclipse.n4js.n4idl

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef

/**
 * The MigrationSwitchComputer can be used to compute a {@link SwitchCondition} which 
 * recognizes values of a given compile-time {@link TypeRef} at runtime (with limits).
 */
class MigrationSwitchComputer {
	
	/** 
	 * Computes a {@link SwitchCondition which detects the given {@link TypeRef} 
	 * at runtime (with limits).
	 * 
	 *  Currently the generated switch conditions support the following {@link TypeRef} features:
	 * - union and intersection types (such as (A#1|A#2))
	 * - parameterized array types (such as [A#1] or Array<A#1>)
	 * - plain non-parameterized types (such as A#1)
	 * 
	 * Also the nesting of all above-mentioned type refs is supported.
	 * 
	 * Furthermore, the following {@link TypeRef}s are ignored and therefore always evaluate to true
	 * in the generated switch condition:
	 * 
	 * - TypeTypeRef
	 * 
	 * All other possible {@link TypeRef}s will lead to an {@link IllegalArgumentException}.
	 */
	public def SwitchCondition compute(TypeRef ref) {
		switch(ref) {
			UnionTypeExpression: {
				return SwitchCondition.or(ref.typeRefs.map[this.compute(it)]);
			}
			IntersectionTypeExpression: {
				return SwitchCondition.and(ref.typeRefs.map[this.compute(it)]);
			}
			ParameterizedTypeRef case isParameterizedArrayTypeRef(ref): {
				val elementTypeRef = ref.typeArgs.get(0) as TypeRef;
				return SwitchCondition.arrayOf(compute(elementTypeRef));
			}
			ParameterizedTypeRef:
				return SwitchCondition.instanceOf(ref.declaredType)
			case isIgnoredTypeRef(ref): {
				return SwitchCondition.trueCondition;
			}
			default: {
				throw new IllegalArgumentException("Cannot handle (sub-)type ref '" + ref.typeRefAsString + "'");
			}
		}
	}
	
	/** Returns {@code true} if the given {@link TypeRef} should be ignored
	 * by the generated {@link SwitchCondition}s.
	 */
	private def boolean isIgnoredTypeRef(TypeRef ref) {
		return ref instanceof TypeTypeRef;
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
}