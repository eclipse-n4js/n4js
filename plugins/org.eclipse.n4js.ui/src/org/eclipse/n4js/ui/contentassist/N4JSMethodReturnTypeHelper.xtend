/**
 * Copyright (c) 2019 HAW Hamburg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributor:
 *   Philip Aguilar Bremer
 */
package org.eclipse.n4js.ui.contentassist

import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.AnyType
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.UndefinedType
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression

/**
 * Resolves the return type of a given method as a string.
 * If there is a simplified notation, this class provides it.
 */
class N4JSMethodReturnTypeHelper {
	
	/**
	 * Resolves the simplified return type of a given method recursively and returns it as string.
	 * @param methodMember a method of a class
	 */
	def public String getReturnTypeAsString(TMethod methodMember) {

		var topLevel = true;
		var boolean async = methodMember.declaredAsync;
		var boolean generator = methodMember.declaredGenerator;
		var returnTypeRef = methodMember.returnTypeRef;

		resolveReturnTypeTopLevel(returnTypeRef, topLevel, async, generator);
	}

	def private String resolveReturnTypeTopLevel(TypeArgument returnTypeRef, boolean topLevel, boolean async,
		boolean generator) {
		if (returnTypeRef instanceof TypeRef) {
			if (topLevel && (generator || async && (returnTypeRef.typeArgs.get(1).declaredType instanceof AnyType ||
				returnTypeRef.typeArgs.get(1) instanceof Wildcard))) {
				var firstReturnTypeArg = returnTypeRef.typeArgs.get(0);
				if (firstReturnTypeArg instanceof ParameterizedTypeRef) {
					if (firstReturnTypeArg.declaredType instanceof UndefinedType) {
						return ": void";
					}
				}
			}
		}
		return ": " + resolveReturnType(returnTypeRef, topLevel);
	}

	def private String resolveReturnType(TypeArgument returnTypeRef, boolean topLevel) {
		var StringBuilder strb = new StringBuilder();
		val notTopLevel = false;
		var declaredType = returnTypeRef.declaredType;
		if (returnTypeRef instanceof ParameterizedTypeRef) {
			if (returnTypeRef.isIterableTypeExpression) {
				var typeArgs = returnTypeRef.typeArgs;
				strb.append("[").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).append("]");
			} else if (returnTypeRef.isArrayLike && returnTypeRef.typeArgs.size > 0) {
				strb.append("Array<").append(resolveReturnType(returnTypeRef.typeArgs.get(0), notTopLevel)).append(">");
			} else if (declaredType.name.equalsIgnoreCase("Generator")) {
				var typeArgs = returnTypeRef.typeArgs;
				if (topLevel && !(typeArgs.get(0) instanceof Wildcard) &&
					!(typeArgs.get(0) instanceof ParameterizedTypeRef &&
						typeArgs.get(0).declaredType.name.equalsIgnoreCase("Generator")) &&
					(typeArgs.get(1).declaredType instanceof UndefinedType ||
						typeArgs.get(1).declaredType instanceof AnyType || typeArgs.get(1) instanceof Wildcard) &&
					(typeArgs.get(2).declaredType instanceof UndefinedType ||
						typeArgs.get(2).declaredType instanceof AnyType || typeArgs.get(2) instanceof Wildcard)) {
					strb.append(resolveReturnType(typeArgs.get(0), notTopLevel));
				} else {
					strb.append("Generator<").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).
						append(">");
				}
			} else if (declaredType.name.equalsIgnoreCase("Promise")) {
				var typeArgs = returnTypeRef.typeArgs;
				if (topLevel && !(typeArgs.get(0) instanceof Wildcard) &&
					!(typeArgs.get(0) instanceof ParameterizedTypeRef &&
						typeArgs.get(0).declaredType.name.equalsIgnoreCase("Promise")) &&
					(typeArgs.get(1) instanceof Wildcard || typeArgs.get(1).declaredType instanceof AnyType)) {
					strb.append(resolveReturnType(typeArgs.get(0), notTopLevel));
				} else {
					strb.append("Promise<").append(typeArgs.map[resolveReturnType(it, notTopLevel)].join(", ")).
						append(">");
				}
			} else {
				strb.append(declaredType.name);
			}
		} else if (returnTypeRef instanceof IntersectionTypeExpression) {
			var typeRefs = returnTypeRef.typeRefs;
			if (!topLevel) strb.append("(");
			strb.append(typeRefs.map[resolveReturnType(it, notTopLevel)].join(" & "));
			if (!topLevel) strb.append(")");
		} else if (returnTypeRef instanceof UnionTypeExpression) {
			var typeRefs = returnTypeRef.typeRefs;
			if (!topLevel) strb.append("(");
			strb.append(typeRefs.map[resolveReturnType(it, notTopLevel)].join(" | "));
			if (!topLevel) strb.append(")");
		} else if (returnTypeRef instanceof Wildcard) {
			strb.append("?");
		} else {
			strb.append(declaredType.name);
		}
		return strb.toString();
	}
}
