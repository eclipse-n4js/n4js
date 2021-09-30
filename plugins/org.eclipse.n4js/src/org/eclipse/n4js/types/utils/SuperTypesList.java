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
package org.eclipse.n4js.types.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 */
public final class SuperTypesList<E> implements Collection<E> {

	/**
	 * Convenience create method for getting rid of duplicate type arguments.
	 */
	public static <T> SuperTypesList<T> newSuperTypesList(Comparator<T> comparator) {
		return new SuperTypesList<>(comparator);
	}

	TreeSet<E> set;
	List<E> list;

	/**
	 * Creates super types list with given comparator
	 */
	public SuperTypesList(Comparator<E> comparator) {
		set = new TreeSet<>(comparator);
		list = new ArrayList<>();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		if (set.add(e)) {
			try {
				list.add(e);
				return true;
			} catch (RuntimeException ex) {
				set.remove(e);
				throw ex;
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (set.remove(o)) {
			try {
				list.remove(o);
				return true;
			} catch (RuntimeException ex) {
				set.add((E) o);
				throw ex;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			return false;
		}
		boolean changed = false;
		for (E e : c) {
			changed |= add(e);
		}
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (set.removeAll(c)) {
			list.removeAll(c);
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (set.retainAll(c)) {
			list.retainAll(c);
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		set.clear();
		list.clear();
	}

}
