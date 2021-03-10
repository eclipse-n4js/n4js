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
package org.eclipse.n4js.ts.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;

/**
 * Provides methods for checking two types, two type references, etc. for semi-semantic equality. For a full comparison,
 * i.e. also deciding between lower / greater, use class {@link TypeCompareHelper} (requires injection). For a fully
 * semantic equality check, use the type system's subtype judgment.
 * <p>
 * For more details how this semi-semantic equality check differs from a fully semantic equality check, see
 * {@link #isEqual(TypeArgument, TypeArgument)}.
 */
public class TypeCompareUtils {

	//
	// IMPLEMENTATION NOTE: no actual logic should be put here; just delegate to TypeCompareLogic.
	//

	/** Same as {@link #isEqual(Type, Type)}, but for checking several types (pair-wise). */
	public boolean isEqualTypes(Iterable<Type> ts1, Iterable<Type> ts2) {
		return TypeCompareLogic.compareTypes(null, ts1, ts2) == 0; // note: no fqnProvider required for equality check!
	}

	/** Same as {@link #isEqual(TypeArgument, TypeArgument)}, but for checking several type arguments (pair-wise). */
	public boolean isEqualTypeArguments(Iterable<TypeArgument> ts1, Iterable<TypeArgument> ts2) {
		return TypeCompareLogic.compareTypeArguments(null, ts1, ts2) == 0; // note: no fqnProvider required for equality
																			// check!
	}

	/** Checks two types for equality. */
	public static boolean isEqual(Type t1, Type t2) {
		return TypeCompareLogic.compare(null, t1, t2) == 0; // note: no fqnProvider required for equality check!
	}

	/**
	 * Implements a semi-semantic equality check for type references as a compromise between a full semantic equality
	 * check, i.e. A = B <=> A &lt;: B and B &lt;: A, and the trivial equality defined by object identity.
	 * <p>
	 * <b>WARNING</b>: never use this method to check whether two types are equal, except in some rare special cases
	 * when the subtype judgment of the type system is unavailable.
	 * <p>
	 * The notion of equality implemented here is most similar to structural equality as implemented by EMF standard
	 * method {@link EcoreUtil#equals(EObject,EObject)}. However, structural equality does not lead to usable results,
	 * here, as two TypeRefs referring to two different classes would be deemed equal, as long as the two classes have
	 * the same name and structurally equal members (which is possible if the classes reside in distinct modules).
	 * <p>
	 * Basic idea here is to require structural equality (i.e. properties have equal values) within the TypeRef itself,
	 * but whenever the TypeRef is referring to a Type, object identity is required.
	 */
	public static boolean isEqual(TypeArgument t1, TypeArgument t2) {
		return TypeCompareLogic.compare(null, t1, t2) == 0; // note: no fqnProvider required for equality check!
	}

	/**
	 * Small utility class to wrap a TypeRef such that it will be compared for equality by way of method
	 * {@link TypeCompareUtils#isEqual(TypeArgument,TypeArgument) isEqual()}.
	 */
	public static final class SemanticEqualsWrapper {
		private final TypeRef typeRef;
		private Integer hashCode = null;

		/** Creates an instance. */
		public SemanticEqualsWrapper(TypeRef typeRef) {
			this.typeRef = typeRef;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof SemanticEqualsWrapper)
				return isEqual(this.typeRef, ((SemanticEqualsWrapper) other).typeRef);
			return false;
		}

		@Override
		public int hashCode() {
			if (typeRef == null) {
				return 0;
			}
			if (hashCode == null) {
				hashCode = typeRef.internalGetTypeRefAsString().hashCode(); // TODO find better way to compute a hash code
			}
			return hashCode.intValue();
		}

		@Override
		public String toString() {
			return "@SemanticEqualsWrapper(" + (typeRef != null ? typeRef.getTypeRefAsString() : "") + ")";
		}
	}
}
