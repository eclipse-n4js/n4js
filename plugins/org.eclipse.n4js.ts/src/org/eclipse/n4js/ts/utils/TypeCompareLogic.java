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

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Internal class containing the logic for comparing types. Should not be used by client code; instead, use
 * {@link TypeCompareUtils} or {@link TypeCompareHelper}.
 */
/* package */ class TypeCompareLogic {

	/** Same as {@link #compare(IQualifiedNameProvider, Type, Type)}, but for several types. */
	/* package */ static <T extends Type> int compareTypes(IQualifiedNameProvider fqnProvider,
			Iterable<T> tvs1, Iterable<T> tvs2) {
		final Iterator<T> iter1 = tvs1.iterator();
		final Iterator<T> iter2 = tvs2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			final int c = compare(fqnProvider, iter1.next(), iter2.next());
			if (c != 0) {
				return c;
			}
		}
		if (iter1.hasNext()) {
			return 1;
		}
		if (iter2.hasNext()) {
			return -1;
		}
		return 0;
	}

	/** Same as {@link #compare(IQualifiedNameProvider, TypeArgument, TypeArgument)}, but for several type arguments. */
	/* package */ static <T extends TypeArgument> int compareTypeArguments(IQualifiedNameProvider fqnProvider,
			Iterable<T> tvs1, Iterable<T> tvs2) {
		final Iterator<T> iter1 = tvs1.iterator();
		final Iterator<T> iter2 = tvs2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			final int c = compare(fqnProvider, iter1.next(), iter2.next());
			if (c != 0) {
				return c;
			}
		}
		if (iter1.hasNext()) {
			return 1;
		}
		if (iter2.hasNext()) {
			return -1;
		}
		return 0;
	}

	/** WARNING: fqnProvider may be <code>null</code>, but then the lower/greater info will be unreliable! */
	/* package */ static int compare(IQualifiedNameProvider fqnProvider, Type t1, Type t2) {
		if (t1 == t2) {
			return 0;
		}
		if (t1 == null) {
			return -1;
		}
		if (t2 == null) {
			return 1;
		}
		// before comparing FQNs, compare versions
		if (t1.getVersion() != t2.getVersion()) {
			return 1;
		}
		if (fqnProvider != null) {
			// standard behavior relying on a fqnProvider
			final QualifiedName name1 = fqnProvider.getFullyQualifiedName(t1);
			final QualifiedName name2 = fqnProvider.getFullyQualifiedName(t2);
			if (name1 == null && name2 == null) {
				// since we know t1!=null && t2!=null, this means t1 and t2 are types without a FQN (i.e. for which
				// fqnProvider returns null), e.g. type variables, and since we know t1!=t2 (from above) we must
				// report a difference here!
				return 1;
			}
			if (name1 == null) {
				return -1;
			}
			if (name2 == null) {
				return 1;
			}
			return name1.compareTo(name2);
		} else {
			// fall-back behavior if fqnProvider not available
			return 1; // note: we already know t1!=t2
		}
	}

	/** WARNING: fqnProvider may be <code>null</code>, but then the lower/greater info will be unreliable! */
	/* package */ static int compare(IQualifiedNameProvider fqnProvider, TypeArgument arg1, TypeArgument arg2) {
		if (arg1 == arg2) {
			return 0;
		}
		if (arg1 == null) {
			return -1;
		}
		if (arg2 == null) {
			return 1;
		}

		if (arg1 instanceof Wildcard || arg2 instanceof Wildcard) {
			if (arg1 instanceof Wildcard && arg2 instanceof Wildcard) {
				final Wildcard w1 = (Wildcard) arg1;
				final Wildcard w2 = (Wildcard) arg2;
				int c;
				// lower bounds
				c = compare(fqnProvider, w1.getDeclaredLowerBound(), w2.getDeclaredLowerBound());
				if (c != 0) {
					return c;
				}
				// upper bounds
				c = compare(fqnProvider, w1.getDeclaredUpperBound(), w2.getDeclaredUpperBound());
				if (c != 0) {
					return c;
				}
				return 0;
			}
			return compareEClasses(arg1.eClass(), arg2.eClass());
		}
		// now, we know we have two TypeRefs
		final TypeRef ref1 = (TypeRef) arg1;
		final TypeRef ref2 = (TypeRef) arg2;

		// this needs to be checked before the EClass comparison, due to the inheritance of FunctionTypeExprOrRef and
		// its subclasses
		if (ref1 instanceof FunctionTypeExprOrRef && ref2 instanceof FunctionTypeExprOrRef) {
			final FunctionTypeExprOrRef f1 = (FunctionTypeExprOrRef) ref1;
			final FunctionTypeExprOrRef f2 = (FunctionTypeExprOrRef) ref2;
			return compareFunctionTypeExprOrRefs(fqnProvider, f1, f2);
		}

		int c;

		c = compareEClasses(ref1.eClass(), ref2.eClass());
		if (c != 0) {
			return c;
		}
		// note: ref1 and ref2 are of the same type, otherwise c would not be 0

		// declared type
		c = compare(fqnProvider, ref1.getDeclaredType(), ref2.getDeclaredType());
		if (c != 0) {
			return c;
		}

		// if we got a subclass of StructuralTypeRef -> check properties of StructuralTypeRef beforehand
		if (ref1 instanceof StructuralTypeRef) {
			final StructuralTypeRef sref1 = (StructuralTypeRef) ref1;
			final StructuralTypeRef sref2 = (StructuralTypeRef) ref2;

			// note: for simplicity, we here require sref1/sref2.structuralMembers to have the same order
			// (we aren't doing a semantic compare anyway)
			c = compareComparables(sref1.getTypingStrategy(), sref2.getTypingStrategy());
			if (c != 0) {
				return c;
			}
			c = compare(fqnProvider, sref1.getStructuralType(), sref2.getStructuralType());
			if (c != 0) {
				return c;
			}
			final Iterator<TStructMember> iter1 = sref1.getStructuralMembers().iterator();
			final Iterator<TStructMember> iter2 = sref2.getStructuralMembers().iterator();
			while (iter1.hasNext() && iter2.hasNext()) {
				c = compareMembers(fqnProvider, iter1.next(), iter2.next());
				if (c != 0) {
					return c;
				}
			}
			if (iter1.hasNext()) {
				return 1;
			}
			if (iter2.hasNext()) {
				return -1;
			}
		}

		if (ref1 instanceof ParameterizedTypeRef) {
			final ParameterizedTypeRef pref1 = (ParameterizedTypeRef) ref1;
			final ParameterizedTypeRef pref2 = (ParameterizedTypeRef) ref2;
			c = compareTypeArguments(fqnProvider, pref1.getTypeArgs(), pref2.getTypeArgs());
			if (c != 0) {
				return c;
			}
		} else if (ref1 instanceof ComposedTypeRef) {
			final ComposedTypeRef cref1 = (ComposedTypeRef) ref1;
			final ComposedTypeRef cref2 = (ComposedTypeRef) ref2;
			c = compareTypeArguments(fqnProvider, cref1.getTypeRefs(), cref2.getTypeRefs());
			if (c != 0) {
				return c;
			}
		} else if (ref1 instanceof TypeTypeRef) {
			final TypeTypeRef cref1 = (TypeTypeRef) ref1;
			final TypeTypeRef cref2 = (TypeTypeRef) ref2;
			c = compareComparables(cref1.isConstructorRef(), cref2.isConstructorRef());
			if (c != 0) {
				return c;
			}
			c = compare(fqnProvider, cref1.getTypeArg(), cref2.getTypeArg());
			if (c != 0) {
				return c;
			}
		} else if (ref1 instanceof BoundThisTypeRef) {
			final BoundThisTypeRef bref1 = (BoundThisTypeRef) ref1;
			final BoundThisTypeRef bref2 = (BoundThisTypeRef) ref2;
			c = compare(fqnProvider, bref1.getActualThisTypeRef(), bref2.getActualThisTypeRef());
			if (c != 0) {
				return c;
			}
		} else if (ref1 instanceof ExistentialTypeRef) {
			final ExistentialTypeRef e1 = (ExistentialTypeRef) ref1;
			final ExistentialTypeRef e2 = (ExistentialTypeRef) ref2;
			final boolean reopened1 = e1.isReopened();
			final boolean reopened2 = e2.isReopened();
			c = compareComparables(reopened1, reopened2);
			if (c != 0) {
				return c;
			}
			if (reopened1 && reopened2) {
				return compare(fqnProvider, e1.getWildcard(), e2.getWildcard());
			}
			return compareComparables(e1.getId(), e2.getId());
		}

		// dynamic
		c = Boolean.compare(ref1.isDynamic(), ref2.isDynamic());
		if (c != 0) {
			return c;
		}
		// note: we ignore null modifier and undef modifier here

		return 0;
	}

	private static int compareFunctionTypeExprOrRefs(IQualifiedNameProvider fqnProvider, FunctionTypeExprOrRef f1,
			FunctionTypeExprOrRef f2) {
		if (f1 == f2) {
			return 0;
		}
		if (f1 == null) {
			return -1;
		}
		if (f2 == null) {
			return 1;
		}

		// note: we do *not* require f1.eClass() == f2.eClass()

		int c;
		// name (if any)
		c = compareComparables(getFunctionName(f1), getFunctionName(f2));
		if (c != 0) {
			return c;
		}
		// declared this type
		c = compare(fqnProvider, f1.getDeclaredThisType(), f2.getDeclaredThisType());
		if (c != 0) {
			return c;
		}
		// type parameters
		c = compareTypes(fqnProvider, f1.getTypeVars(), f2.getTypeVars());
		if (c != 0) {
			return c;
		}
		// return type
		c = compare(fqnProvider, f1.getReturnTypeRef(), f2.getReturnTypeRef());
		if (c != 0) {
			return c;
		}
		// fpars
		c = compareFormalParameters(fqnProvider, f1.getFpars(), f2.getFpars());
		if (c != 0) {
			return c;
		}

		return 0;
	}

	private static int compareMembers(IQualifiedNameProvider fqnProvider, TStructMember m1, TStructMember m2) {
		if (m1 == m2) {
			return 0;
		}
		if (m1 == null) {
			return -1;
		}
		if (m2 == null) {
			return 1;
		}

		int c;

		// EClass
		c = compareEClasses(m1.eClass(), m2.eClass());
		if (c != 0) {
			return c;
		}
		// access modifier
		c = compareComparables(m1.getMemberAccessModifier(), m2.getMemberAccessModifier());
		if (c != 0) {
			return c;
		}
		// name
		c = compareComparables(m1.getName(), m2.getName());
		if (c != 0) {
			return c;
		}
		// type (in case of field/getter/setter) or return type (in case of method)
		c = compare(fqnProvider, getMemberTypeRef(m1), getMemberTypeRef(m2)); // for methods: will check
																				// the
																				// return type
		if (c != 0) {
			return c;
		}
		// fpars (in case of method)
		if (m1 instanceof TStructMethod) {
			final TStructMethod method1 = (TStructMethod) m1;
			final TStructMethod method2 = (TStructMethod) m2;
			c = compareFormalParameters(fqnProvider, method1.getFpars(), method2.getFpars());
			if (c != 0) {
				return c;
			}
		}

		return 0;
	}

	private static int compareFormalParameters(IQualifiedNameProvider fqnProvider, Iterable<TFormalParameter> fpars1,
			Iterable<TFormalParameter> fpars2) {
		final Iterator<TFormalParameter> iter1 = fpars1.iterator();
		final Iterator<TFormalParameter> iter2 = fpars2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			final int c = compareFormalParameters(fqnProvider, iter1.next(), iter2.next());
			if (c != 0) {
				return c;
			}
		}
		if (iter1.hasNext()) {
			return 1;
		}
		if (iter2.hasNext()) {
			return -1;
		}
		return 0;
	}

	private static int compareFormalParameters(IQualifiedNameProvider fqnProvider, TFormalParameter p1,
			TFormalParameter p2) {
		if (p1 == p2) {
			return 0;
		}
		if (p1 == null) {
			return -1;
		}
		if (p2 == null) {
			return 1;
		}

		int c;

		// name
		c = compareComparables(p1.getName(), p2.getName());
		if (c != 0) {
			return c;
		}
		// type
		c = compare(fqnProvider, p1.getTypeRef(), p2.getTypeRef());
		if (c != 0) {
			return c;
		}
		// optional
		c = Boolean.compare(p1.isOptional(), p2.isOptional());
		if (c != 0) {
			return c;
		}
		// variadic
		c = Boolean.compare(p1.isVariadic(), p2.isVariadic());
		if (c != 0) {
			return c;
		}

		return 0;
	}

	private static int compareEClasses(EClass ec1, EClass ec2) {
		if (ec1 == ec2) {
			return 0;
		}
		if (ec1 == null) {
			return -1;
		}
		if (ec2 == null) {
			return 1;
		}
		if (ec1.getEPackage() == ec2.getEPackage()) {
			return ec1.getClassifierID() - ec2.getClassifierID();
		} else {
			// NsURIs are to be different!
			return ec1.getEPackage().getNsURI().compareTo(ec2.getEPackage().getNsURI());
		}
	}

	/** Null-safe comparison of two {@link Comparable}s. */
	private static <T extends Comparable<T>> int compareComparables(T c1, T c2) {
		if (c1 == c2) {
			return 0;
		}
		if (c1 == null) {
			return -1;
		}
		if (c2 == null) {
			return 1;
		}
		return c1.compareTo(c2);
	}

	/** Returns the name of the function or <code>null</code> (e.g. in case of a {@link FunctionTypeExpression}). */
	private static String getFunctionName(FunctionTypeExprOrRef f) {
		final TFunction ft = f.getFunctionType();
		return ft != null ? ft.getName() : null;
	}

	/** Returns type of given field, getter, setter OR return type of method. */
	private static TypeRef getMemberTypeRef(TStructMember m) {
		if (m instanceof TStructField) {
			return ((TStructField) m).getTypeRef();
		} else if (m instanceof TStructGetter) {
			return ((TStructGetter) m).getDeclaredTypeRef();
		} else if (m instanceof TStructSetter) {
			return ((TStructSetter) m).getDeclaredTypeRef();
		} else if (m instanceof TStructMethod) {
			return ((TStructMethod) m).getReturnTypeRef();
		}
		return null;
	}
}
