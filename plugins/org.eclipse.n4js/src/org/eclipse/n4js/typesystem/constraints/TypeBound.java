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
package org.eclipse.n4js.typesystem.constraints;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.Collections;
import java.util.Set;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareUtils;
import org.eclipse.n4js.types.utils.TypeUtils;

/**
 * Type bounds are similar to {@link TypeConstraint}s, but are required to be of a simpler, more unified form: the LHS
 * must always be an inference variable. The RHS, however, may be a {@link TypeUtils#isProper(TypeArgument) proper} or
 * improper type.
 */
/* package */ final class TypeBound {
	public final InferenceVariable left;
	public final TypeRef right;
	public final Variance variance;
	public final Set<InferenceVariable> referencedInfVars;

	private final int hashCode;

	/**
	 * Creates an instance.
	 */
	public TypeBound(InferenceVariable left, TypeRef right, Variance variance) {
		this(left, right, variance, toSet(filter(TypeUtils.getReferencedTypeVars(right), InferenceVariable.class)));
	}

	/**
	 * Creates an instance.
	 */
	public TypeBound(InferenceVariable left, TypeRef right, Variance variance,
			Set<InferenceVariable> referencedInfVars) {
		this.left = left;
		this.right = right;
		this.variance = variance;
		this.referencedInfVars = Collections.unmodifiableSet(referencedInfVars);
		this.hashCode = this.toString().hashCode(); // TODO find better way to compute hash code
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof TypeBound) {
			final TypeBound other = (TypeBound) obj;
			return other.left == this.left // n.b.: types can be compared with identity check!
					&& TypeCompareUtils.isEqual(other.right, this.right) // but: type references require deep compare
					&& other.variance == this.variance;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(this.left.getTypeAsString()) + " " + this.variance.getRelationString() + " "
				+ this.right.getTypeRefAsString();
	}

	/**
	 * Tells if this bound is of the form `α op α`.
	 */
	public boolean isTrivial() {
		return right.getDeclaredType() == left;
	}

	/**
	 * If a {@link TypeUtils#isRawTypeRef(TypeRef) raw type reference} is on the RHS of this bounds, returns a copy with
	 * a {@link TypeUtils#sanitizeRawTypeRef(TypeRef) sanitized} RHS; otherwise, the receiving type bound will be
	 * returned.
	 * <p>
	 * In any case, the receiving type bound will remain unchanged.
	 */
	public TypeBound sanitizeRawTypeRef() {
		if (TypeUtils.isRawTypeRef(right)) {
			final TypeBound cpy = new TypeBound(left, TypeUtils.copy(right), variance, referencedInfVars);
			TypeUtils.sanitizeRawTypeRef(cpy.right);
			return cpy;
		}
		return this;
	}
}
