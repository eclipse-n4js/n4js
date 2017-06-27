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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.Variance;

/**
 * A type constraint of the form
 *
 * <pre>
 * left variance right
 * </pre>
 *
 * where <code>left</code>, <code>right</code> are arbitrary type arguments (i.e. type references or wildcards) and
 * <code>variance</code> is one of <code>&lt;:</code>, <code>:&gt;</code>, or <code>=</code>.
 * <p>
 * No restrictions apply as to whether inference variables appear on none, one, or both sides.
 */
public class TypeConstraint {

	private static final String PREFIX = "\u27e8 ";
	private static final String SUFFIX = " \u27e9";

	/**
	 * A type constraint representing the boolean value <code>true</code>.
	 */
	public static final TypeConstraint TRUE = new TypeConstraint(null, null, Variance.CO);
	/**
	 * A type constraint representing the boolean value <code>false</code>. Adding it to an inference context will
	 * {@link InferenceContext#isDoomed() doom} the context.
	 */
	public static final TypeConstraint FALSE = new TypeConstraint(null, null, Variance.CONTRA);

	/** Left-hand side of this constraint. */
	public final TypeArgument left;
	/** Right-hand side of this constraint. */
	public final TypeArgument right;
	/** The {@link Variance variance} of this constraint. */
	public final Variance variance;

	/**
	 * Creates an instance.
	 */
	public TypeConstraint(TypeArgument left, TypeArgument right, Variance variance) {
		this.left = left;
		this.right = right;
		this.variance = variance;
	}

	/**
	 * Convenience method returning the declared type of the left-hand side or <code>null</code>.
	 */
	public Type leftDeclaredType() {
		return (left instanceof TypeRef) ? ((TypeRef) left).getDeclaredType() : null;
	}

	/**
	 * Convenience method returning the declared type of the right-hand side or <code>null</code>.
	 */
	public Type rightDeclaredType() {
		return (right instanceof TypeRef) ? ((TypeRef) right).getDeclaredType() : null;
	}

	@Override
	public String toString() {
		if (this == FALSE)
			return PREFIX + "FALSE" + SUFFIX;
		if (this == TRUE)
			return PREFIX + "TRUE" + SUFFIX;
		return toString(left, right, variance);
	}

	/**
	 * Like {@link #toString()}, but does not require an instance of {@link TypeConstraint} to be created.
	 */
	public static final String toString(EObject left, EObject right, Variance variance) {
		return PREFIX + toString(left) + " " + variance.getRelationString() + " " + toString(right) + SUFFIX;
	}

	private static final String toString(EObject obj) {
		if (obj == null)
			return "null";
		if (obj instanceof TypeArgument)
			return ((TypeArgument) obj).getTypeRefAsString();
		if (obj instanceof TFunction)
			return ((TFunction) obj).getFunctionAsString();
		if (obj instanceof TFormalParameter)
			return ((TFormalParameter) obj).getFormalParameterAsString();
		if (obj instanceof Type)
			return ((Type) obj).getTypeAsString();
		if (obj instanceof NamedElement)
			return ((NamedElement) obj).getName();
		if (obj instanceof IdentifiableElement)
			return ((IdentifiableElement) obj).getName();
		return obj.eClass().getName();
	}
}
