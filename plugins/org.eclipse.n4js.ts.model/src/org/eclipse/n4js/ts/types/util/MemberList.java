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
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;

/**
 * List of members, provides simple filters for different member types and simplified debugging by providing better
 * toString methods.
 */
public class MemberList<M extends TMember> extends ArrayList<M> {

	private static final MemberList<TMember> EMPTY_LIST;
	private static final String SEALED_ERROR = "Member list is sealed,";

	static {
		SealableMemberList<TMember> emptyList = new SealableMemberList<>();
		emptyList.seal();
		EMPTY_LIST = emptyList;
	}

	/**
	 * Creates a new member list.
	 */
	public static <T extends TMember> MemberList<T> newMemberList() {
		return new MemberList<>();
	}

	/**
	 * Creates an empty member list.
	 */
	public MemberList() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a member list with the given elements.
	 */
	public MemberList(Collection<M> c) {
		super(c);
	}

	/**
	 * Creates an empty list with the initial capacity.
	 */
	public MemberList(int initialCapacity) {
		super(initialCapacity);
	}

	private static class MemberIterator<T extends TMember> implements Iterator<T> {

		private final Iterator<? extends TMember> memberIter;
		private final Class<T> type;
		private T next;
		private boolean hasNext;

		private MemberIterator(Iterator<? extends TMember> iterator, Class<T> type) {
			this.memberIter = iterator;
			this.type = type;
			hasNext = true;
			next = findNext();
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T current = next;
			next = findNext();
			return current;
		}

		@SuppressWarnings("unchecked")
		private T findNext() {
			while (memberIter.hasNext()) {
				TMember m = memberIter.next();
				if (type.isInstance(m)) {
					return (T) m;
				}
			}
			hasNext = false;
			return null;
		}

	}

	/**
	 * Filtered view on a member list, providing additional support for stream and tests if the iterable is empty.
	 */
	public class MemberIterable<T extends TMember> implements Iterable<T> {

		private final Class<T> type;

		private MemberIterable(Class<T> type) {
			this.type = type;
		}

		@SuppressWarnings("unused")
		@Override
		public MemberIterator<T> iterator() {
			return new MemberIterator<T>(MemberList.this.iterator(), type);
		}

		/**
		 * Returns true if this iterable is empty.
		 */
		public boolean isEmpty() {
			return !iterator().hasNext();
		}

		/**
		 * Returns a non-parallel stream of this iterable.
		 */
		public Stream<T> stream() {
			return StreamSupport.stream(spliterator(), false);
		}

		/**
		 * For debug purposes only.
		 */
		@Override
		public String toString() {
			StringBuilder strb = new StringBuilder("[");
			StreamSupport.stream(spliterator(), false).map(m -> m.getMemberAsString()).collect(Collectors.joining(","));
			strb.append("]");
			return strb.toString();
		}

	}

	/**
	 * Returns a filtered view on that member list containing only members of type {@link TField}.
	 */
	public MemberIterable<TField> fields() {
		return new MemberIterable<>(TField.class);
	}

	/**
	 * Returns a filtered view on that member list containing only members of type {@link TMethod}.
	 */
	public MemberIterable<TMethod> methods() {
		return new MemberIterable<>(TMethod.class);
	}

	/**
	 * Returns a filtered view on that member list containing only members of type {@link TGetter}.
	 */
	public MemberIterable<TGetter> getters() {
		return new MemberIterable<>(TGetter.class);
	}

	/**
	 * Returns a filtered view on that member list containing only members of type {@link TField}.
	 */
	public MemberIterable<TSetter> setters() {
		return new MemberIterable<>(TSetter.class);
	}

	/**
	 * Returns a filtered view on that member list containing only members of type {@link FieldAccessor}.
	 */
	public MemberIterable<FieldAccessor> accessors() {
		return new MemberIterable<>(FieldAccessor.class);
	}

	/**
	 * For debug purposes only.
	 */
	/**
	 * For debug purposes only.
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder("[");
		strb.append(stream().map(m -> m != null ? m.getMemberAsString() : "null").collect(Collectors.joining(",")));
		strb.append("]");
		return strb.toString();
	}

	/**
	 * Returns the empty member list, which is not modifiable.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TMember> MemberList<T> emptyList() {
		return (MemberList<T>) EMPTY_LIST;
	}

	/**
	 * Member list that can be sealed, i.e. make unmodifiable.
	 */
	public static class SealableMemberList<M extends TMember> extends MemberList<M> {

		boolean sealed = false;

		/**
		 * Seals this list, that is, makes list unmodifiable.
		 */
		public void seal() {
			sealed = true;
		}

		@Override
		public M set(int index, M element) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.set(index, element);
		}

		@Override
		public void add(int index, M element) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			super.add(index, element);
		}

		@Override
		public boolean add(M e) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.add(e);
		}

		@Override
		public M remove(int index) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.remove(index);
		}

		@Override
		public boolean remove(Object o) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.remove(o);
		}

		@Override
		public void clear() {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			super.clear();
		}

		@Override
		public boolean addAll(Collection<? extends M> c) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.addAll(c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends M> c) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.addAll(index, c);
		}

		@Override
		protected void removeRange(int fromIndex, int toIndex) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			super.removeRange(fromIndex, toIndex);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.removeAll(c);
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.retainAll(c);
		}

		@Override
		public boolean removeIf(Predicate<? super M> filter) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			return super.removeIf(filter);
		}

		@Override
		public void replaceAll(UnaryOperator<M> operator) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			super.replaceAll(operator);
		}

		@Override
		public void sort(Comparator<? super M> c) {
			if (sealed) {
				throw new IllegalStateException(SEALED_ERROR);
			}
			super.sort(c);
		}
	}
}
