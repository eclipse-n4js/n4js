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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.RecursionGuard;

/**
 * Recursion-safe iterable over the transitive closure of declared super classes of a class; implicit super classes are
 * ignored. The class itself is never returned.
 */
public class ExtendedClassesIterable implements Iterable<TClass> {

	/**
	 * Recursion-safe iterator over super classes of a class.
	 */
	public class ExtendedClassesIterator implements Iterator<TClass> {

		private final RecursionGuard<TClass> guard = new RecursionGuard<>();
		private TClass next;

		ExtendedClassesIterator() {
			if (root != null) {
				guard.tryNext(root);
				next = retrieveNext(root);
			}
		}

		private TClass retrieveNext(TClass currentClass) {
			ParameterizedTypeRef superTypeRef = currentClass.getSuperClassRef();
			if (superTypeRef == null) {
				return null;
			}
			final Type superType = superTypeRef.getDeclaredType();
			if (superType instanceof TClass) { // if someone extends Object explicitly, we will traverse it here!
				final TClass superClass = (TClass) superType;
				if (guard.tryNext(superClass)) {
					return superClass;
				}
			}
			return null;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public TClass next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			TClass current = next;
			next = retrieveNext(current);
			return current;
		}
	}

	private final TClass root;

	/**
	 * Creates a new iterable for the extended classes of the given class.
	 */
	public ExtendedClassesIterable(TClass root) {
		this.root = root;
	}

	@Override
	public Iterator<TClass> iterator() {
		return new ExtendedClassesIterator();
	}

	@Override
	public Spliterator<TClass> spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), Spliterator.DISTINCT | Spliterator.NONNULL);
	}

	/**
	 * Returns the inheritance distance between class of this iterable and given super class. The distance from this
	 * class to itself is 0, to its directly extended class 1 and so on. If the given class is no declared super class
	 * of this class, -1 is returned. Note that implicit super classes are ignored.
	 */
	public int getInheritanceDistance(TClassifier superClass) {
		if (root == superClass) {
			return 0;
		}
		int distance = 1;
		Iterator<TClass> iter = iterator();
		while (iter.hasNext()) {
			if (iter.next() == superClass) {
				return distance;
			}
			distance++;
		}
		return -1;
	}
}
