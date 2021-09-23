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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.RecursionGuard;

/**
 * Recursion-safe iterable over the directly and indirectly implemented/extended interfaces of a class or interface. The
 * root element itself is never included, even if it is an interface.
 * <p>
 * Interfaces are iterated from current class and its implemented interfaces up to the most distant super class and
 * every interface is visited only once. This <b>DOES NOT</b> reflect the consumption strategy of members!
 */
public class SuperInterfacesIterable implements Iterable<TInterface> {

	/**
	 * Creates iterable of all directly or indirectly implemented/extended interfaces of the given classifier. The given
	 * classifier is never included, even if it is an interface.
	 */
	public static SuperInterfacesIterable of(TClassifier classifier) {
		return new SuperInterfacesIterable(classifier);
	}

	/**
	 * Creates iterable of all directly(!) implemented interfaces of the given class <em>and</em> all their extended
	 * interfaces.
	 * <p>
	 * Difference to method {@link #of(TClassifier)} is that in this case the interfaces implemented by super-classes of
	 * 'clazz' are <b>NOT</b> included.
	 */
	public static SuperInterfacesIterable ofThisOnly(TClass clazz) {
		return new SuperInterfacesIterable(clazz, true);
	}

	/**
	 * Recursion-safe iterator over directly and indirectly implemented/extended interfaces of a given class or
	 * interface.
	 */
	static class SuperInterfacesIterator implements Iterator<TInterface> {

		/** The currently processed class, may be null if iterator has been initialized with an interface */
		private TClass currClass = null;
		/**
		 * The currently processed class at the time when method {@link #next()} was called. This may be the same as
		 * {@link #currClass}, if the currently processed class has not changed since then.
		 */
		private TClass currClassAtLastNextInvocation = null;
		/**
		 * The element that will be returned by next call to {@link #next()} or <code>null</code> if no next element.
		 */
		private TInterface next = null;

		private final RecursionGuard<TClassifier> guard = new RecursionGuard<>();
		private Iterator<ParameterizedTypeRef> currIter;
		private final List<Iterator<ParameterizedTypeRef>> queuedIters = new ArrayList<>();

		SuperInterfacesIterator(TClass rootClass) {
			currClass = rootClass;
			if (currClass != null) {
				guard.tryNext(currClass);
				queuedIters.add(currClass.getImplementedInterfaceRefs().iterator());
				next = retrieveNext();
			}
		}

		SuperInterfacesIterator(TInterface rootRole) {
			guard.tryNext(rootRole);
			queuedIters.add(rootRole.getSuperInterfaceRefs().iterator());
			next = retrieveNext();
		}

		SuperInterfacesIterator(Iterable<ParameterizedTypeRef> rootInterfaces) {
			queuedIters.add(rootInterfaces.iterator());
			next = retrieveNext();
		}

		private TInterface retrieveNext() {
			do {
				// if required, get a new currIter
				while (currIter == null || !currIter.hasNext()) {
					if (!queuedIters.isEmpty()) {
						// priority #1: continue with queuedIters
						currIter = queuedIters.remove(queuedIters.size() - 1);
					} else if (currClass != null) {
						// priority #2: continue with interfaces of super-class of currClass
						ParameterizedTypeRef superType = currClass.getSuperClassRef();
						final TClassifier superClass;
						if (null != superType && superType.getDeclaredType() instanceof TClassifier) {
							superClass = (TClassifier) superType.getDeclaredType();
						} else {
							superClass = null;
						}
						if (superClass instanceof TClass && guard.tryNext(superClass)) {
							currClass = (TClass) superClass;
							queuedIters.add(currClass.getImplementedInterfaceRefs().iterator());
						} else {
							// no valid super class -> no next element
							return null;
						}
					} else {
						// no next element
						return null;
					}
				}
				// take next element from currIter (if any)
				while (currIter.hasNext()) {
					ParameterizedTypeRef typeRef = currIter.next();
					Type type = typeRef != null && !typeRef.eIsProxy() ? typeRef.getDeclaredType() : null;
					if (type instanceof TInterface && !type.eIsProxy()) {
						TInterface ifc = (TInterface) type;
						if (guard.tryNext(ifc)) {
							// before returning ifc, schedule its extended interfaces for iteration
							queuedIters.add(ifc.getSuperInterfaceRefs().iterator());
							return ifc;
						}
					}
				}
			} while (true);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public TInterface next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			TInterface returnNext = next;
			currClassAtLastNextInvocation = currClass;
			next = retrieveNext(); // n.b.: this may change value of 'currClass'
			return returnNext;
		}

		/**
		 * Returns the class directly or indirectly implementing the interface returned by last {@link #next()} call.
		 * This method reflects the overall interface implementation principle, that is, if an interface I is
		 * implemented by a class C and one of its super classes, then it is only recognized for class C itself and not
		 * for the super class.
		 */
		public TClass getImplementingClass() {
			return currClassAtLastNextInvocation;
		}
	}

	private final TClassifier root;
	private final boolean ignoreSuperClassInterfaces;

	/**
	 * Creates a new iterable for the implemented/extended interfaces of the given class or interface.
	 */
	protected SuperInterfacesIterable(TClassifier root) {
		this.root = root;
		ignoreSuperClassInterfaces = false;
	}

	/**
	 * Creates a new iterable for the implemented/extended interfaces of the given class or interface.
	 */
	protected SuperInterfacesIterable(TClass root, boolean ignoreSuperClassInterfaces) {
		this.root = root;
		this.ignoreSuperClassInterfaces = ignoreSuperClassInterfaces;
	}

	@Override
	public SuperInterfacesIterator iterator() {
		if (root instanceof TInterface) {
			return new SuperInterfacesIterator((TInterface) root);
		}
		if (ignoreSuperClassInterfaces && root != null) {
			return new SuperInterfacesIterator(((TClass) root).getImplementedInterfaceRefs());
		}
		return new SuperInterfacesIterator((TClass) root);
	}

	@Override
	public Spliterator<TInterface> spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(), Spliterator.DISTINCT | Spliterator.NONNULL);
	}

	/**
	 * Returns the class which effectively implements the given interface. This is either the root class of the
	 * receiving {@link SuperInterfacesIterable} or the first super class which directly or indirectly (via transitive
	 * interface extension) implements the given interface. If the interface is not implemented at all, or if the
	 * iterable has been created with an interface, null is returned.
	 */
	public TClass findClassImplementingInterface(TInterface ifc) {
		SuperInterfacesIterator iter = iterator();
		while (iter.hasNext()) {
			if (iter.next() == ifc) {
				return iter.getImplementingClass();
			}
		}
		return null;
	}
}
