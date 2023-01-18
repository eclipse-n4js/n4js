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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * Some static convenience methods intended to be used in Xsemantics as extensions methods. By convention, all methods
 * take a Type or TypeRef (or subclass) as first argument.
 */
public class TypeExtensions {

	/**
	 * Creates a {@link TypeRef} for the given {@link Type}. Simply delegates to
	 * {@link TypeUtils#createTypeRef(Type, TypeArgument...)}.
	 */
	public static final ParameterizedTypeRef ref(Type declaredType, TypeArgument... typeArgs) {
		return TypeUtils.createTypeRef(declaredType, typeArgs);
	}

	/**
	 * Creates a {@link TypeRef} for the given {@link Type}. Simply delegates to
	 * {@link TypeUtils#createTypeRef(Type, TypingStrategy, boolean, TypeArgument...)}.
	 */
	public static final TypeRef ref(Type declaredType, TypingStrategy typingStrategy,
			boolean autoCreateTypeArgs, TypeArgument... typeArgs) {
		return TypeUtils.createTypeRef(declaredType, typingStrategy, autoCreateTypeArgs, typeArgs);
	}

	/**
	 * Type-safe version of {@link #ref(Type, TypeArgument...)} for creating {@link FunctionTypeRef}s.
	 */
	public static final FunctionTypeRef ref(TFunction declaredType, TypeArgument... typeArgs) {
		// we can be sure that TypeUtils#createTypeRef() will create a FunctionTypeRef here
		return (FunctionTypeRef) TypeUtils.createTypeRef(declaredType, typeArgs);
	}
}
