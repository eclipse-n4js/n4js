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
package org.eclipse.n4js.utils.collections;

import java.util.AbstractSequentialList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.google.common.collect.UnmodifiableListIterator;

/**
 * Unmodifiable view on concatenated lists. All changes in contained lists are directly reflected in this view.
 */
public class ComposedList<T> extends AbstractSequentialList<T> {

	/**
	 * Creates a composed view of the given lists.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> compose(List<? extends T> l1, List<? extends T> l2) {
		return new ComposedList(l1, l2);
	}

	/**
	 * Creates a composed view of the given lists.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> compose(List<? extends T> l1, List<? extends T> l2, List<? extends T> l3) {
		return new ComposedList(l1, l2, l3);
	}

	/**
	 * Creates a composed view of the given lists.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> compose(List<? extends T> l1, List<? extends T> l2, List<? extends T> l3,
			List<? extends T> l4) {
		return new ComposedList(l1, l2, l3, l4);
	}

	/**
	 * Precondition: lists not null and not empty
	 */
	class ComposedListIterator extends UnmodifiableListIterator<T> {

		int prevList = 0;
		int nextList = 0;
		int currentIndex = 0;
		ListIterator<? extends T> nextIter = null;
		ListIterator<? extends T> prevIter = null;

		private int offset; // used in listIndexContainingElementWithIndex

		ComposedListIterator(int index) {

			nextList = listIndexContainingElementWithIndex(index);
			if (nextList < 0) {
				nextIter = null;
				if (index > 0) {
					prevList = listIndexContainingElementWithIndex(index - 1);
					if (prevList >= 0) {
						int listRelativeIndex = index - offset;
						prevIter = lists[prevList].listIterator(listRelativeIndex);
					}
				}
			} else {
				int listRelativeIndex = index - offset;
				nextIter = lists[nextList].listIterator(listRelativeIndex);
				if (listRelativeIndex > 0) {
					prevList = nextList;
					prevIter = nextIter;
				} else {
					if (nextList > 0) {
						prevList = nextList - 1;
						prevIter = lists[prevList].listIterator(lists[prevList].size());
					}
				}
			}
		}

		int listIndexContainingElementWithIndex(int index) {
			offset = 0;
			int listIdx = 0;
			while (listIdx < lists.length && lists[listIdx].size() + offset <= index) {
				offset += lists[listIdx].size();
				listIdx++;
			}
			if (listIdx == lists.length) {
				return -1;
			}
			return listIdx;
		}

		@Override
		public boolean hasNext() {
			return nextIter != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T t = nextIter.next();
			currentIndex++;
			prevIter = nextIter;
			prevList = nextList;
			while (nextIter != null && !nextIter.hasNext()) {
				if (nextList + 1 < lists.length) {
					nextList++;
					nextIter = lists[nextList].listIterator();
				} else {
					nextIter = null;
				}
			}
			return t;
		}

		@Override
		public boolean hasPrevious() {
			return prevIter != null;
		}

		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			T t = prevIter.previous();
			currentIndex--;
			nextIter = prevIter;
			nextList = prevList;
			while (prevIter != null && !prevIter.hasPrevious()) {
				if (prevList > 0) {
					prevList--;
					prevIter = lists[prevList].listIterator(lists[prevList].size());
				} else {
					prevIter = null;
				}
			}
			return t;
		}

		@Override
		public int nextIndex() {
			return currentIndex;
		}

		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

	}

	private final List<? extends T>[] lists;

	/**
	 * @param lists
	 *            the contained lists
	 */
	@SafeVarargs
	public ComposedList(List<? extends T>... lists) {
		this.lists = lists;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		if (lists == null || lists.length == 0) {
			return Collections.<T> emptyList().listIterator(index);
		}
		return new ComposedListIterator(index);
	}

	@Override
	public int size() {
		int size = 0;
		for (List<? extends T> l : lists) {
			size += l.size();
		}
		return size;
	}

}
