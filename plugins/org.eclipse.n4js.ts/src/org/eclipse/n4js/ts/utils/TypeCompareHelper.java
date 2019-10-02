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

import java.util.Comparator;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * Provides methods for comparing two types, two type references, etc. as well as corresponding implementations of
 * {@link Comparator}. Needs to be injected because comparison of types depends on their fully-qualified name and thus a
 * {@link IQualifiedNameProvider qualified name provider} is required.
 * <p>
 * If you only need an equality check (i.e. no lower / greater), then use class {@link TypeCompareUtils} which does not
 * require injection.
 */
public class TypeCompareHelper {

	//
	// IMPLEMENTATION NOTE: no actual logic should be put here; just delegate to TypeCompareLogic.
	//

	@Inject
	private IQualifiedNameProvider fqnProvider;

	/** Same as {@link #compare(Type, Type)}, but for comparing several types (pair-wise). */
	public int compareTypes(Iterable<Type> ts1, Iterable<Type> ts2) {
		return TypeCompareLogic.compareTypes(fqnProvider, ts1, ts2);
	}

	/** Same as {@link #compare(TypeArgument, TypeArgument)}, but for comparing several type arguments (pair-wise). */
	public int compareTypeArguments(Iterable<TypeArgument> ts1, Iterable<TypeArgument> ts2) {
		return TypeCompareLogic.compareTypeArguments(fqnProvider, ts1, ts2);
	}

	/** {@link Comparator#compare(Object, Object) Compares} two types. */
	public int compare(Type t1, Type t2) {
		return TypeCompareLogic.compare(fqnProvider, t1, t2);
	}

	/** {@link Comparator#compare(Object, Object) Compares} two type arguments. */
	public int compare(TypeArgument t1, TypeArgument t2) {
		return TypeCompareLogic.compare(fqnProvider, t1, t2);
	}

	private final class TypeComparator implements Comparator<Type> {
		@Override
		public int compare(Type t1, Type t2) {
			return TypeCompareLogic.compare(fqnProvider, t1, t2);
		}
	}

	private final class TypeArgumentComparator implements Comparator<TypeArgument> {
		@Override
		public int compare(TypeArgument t1, TypeArgument t2) {
			return TypeCompareLogic.compare(fqnProvider, t1, t2);
		}
	}

	private final class TypeRefComparator implements Comparator<TypeRef> {
		@Override
		public int compare(TypeRef t1, TypeRef t2) {
			return TypeCompareLogic.compare(fqnProvider, t1, t2);
		}
	}

	private final TypeComparator typeComparator = new TypeComparator();
	private final TypeArgumentComparator typeArgumentComparator = new TypeArgumentComparator();
	private final TypeRefComparator typeRefComparator = new TypeRefComparator();

	/**
	 * Comparator for types using fully qualified names, needs to be injected in order to be able to resolve correct
	 * {@link IQualifiedNameProvider}. The type references are compared by using the fully qualified name of the
	 * referenced type, or by comparing the meta type names.
	 */
	public Comparator<Type> getTypeComparator() {
		return typeComparator;
	}

	/** Comparator for type arguments, i.e. type references or wildcards. */
	public Comparator<TypeArgument> getTypeArgumentComparator() {
		return typeArgumentComparator;
	}

	/** Comparator for type references. */
	public Comparator<TypeRef> getTypeRefComparator() {
		return typeRefComparator;
	}
}
