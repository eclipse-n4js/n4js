/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Utility functions for collections
 */
public class Collections2 {
	/**
	 * Private constructor to prevent instantiation.
	 */
	private Collections2() {
	}

	/**
	 * Creates a new linked list containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newLinkedList}.
	 *
	 * @param initial
	 *            the initial elements of the list
	 *
	 * @return the newly created linked list
	 */
	@SafeVarargs
	public static <T> LinkedList<T> newLinkedList(T... initial) {
		return CollectionLiterals.newLinkedList(initial);
	}

	/**
	 * Creates a new array-backed list containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newArrayList}.
	 *
	 * @param initial
	 *            the initial elements of the list
	 *
	 * @return the newly created array-backed list
	 */
	@SafeVarargs
	public static <T> ArrayList<T> newArrayList(T... initial) {
		return CollectionLiterals.newArrayList(initial);
	}

	/**
	 * Creates a new hash set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newHashSet}.
	 *
	 * @param initial
	 *            the initial elements of the set
	 *
	 * @return the newly created hash set
	 */
	@SafeVarargs
	public static <T> HashSet<T> newHashSet(T... initial) {
		return CollectionLiterals.newHashSet(initial);
	}

	/**
	 * Creates a new linked hash set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newLinkedHashSet}.
	 *
	 * @param initial
	 *            the initial elements of the set
	 *
	 * @return the newly created linked hash set
	 */
	@SafeVarargs
	public static <T> LinkedHashSet<T> newLinkedHashSet(T... initial) {
		return CollectionLiterals.newLinkedHashSet(initial);
	}

	/**
	 * Creates a new tree set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newTreeSet}.
	 *
	 * @param comparator
	 *            the comparator to use
	 * @param initial
	 *            the initial elements of the set
	 *
	 * @return the newly created tree set
	 */
	@SafeVarargs
	public static <T> TreeSet<T> newTreeSet(Comparator<? super T> comparator, T... initial) {
		return CollectionLiterals.newTreeSet(comparator, initial);
	}

	/**
	 * Creates a new hash set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newHashMap}.
	 *
	 * @param initial
	 *            the initial elements of the map
	 *
	 * @return the newly created hash map
	 */
	@SafeVarargs
	public static <K, V> HashMap<K, V> newHashMap(Pair<? extends K, ? extends V>... initial) {
		return CollectionLiterals.newHashMap(initial);
	}

	/**
	 * Creates a new linked hash set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newLinkedHashMap}.
	 *
	 * @param initial
	 *            the initial elements of the map
	 *
	 * @return the newly created linked hash map
	 */
	@SafeVarargs
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Pair<? extends K, ? extends V>... initial) {
		return CollectionLiterals.newLinkedHashMap(initial);
	}

	/**
	 * Creates a new linked hash set containing the given elements. This just delegates to
	 * {@link CollectionLiterals#newTreeMap}.
	 *
	 * @param initial
	 *            the initial elements of the map
	 *
	 * @return the newly created linked hash map
	 */
	@SafeVarargs
	public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator,
			Pair<? extends K, ? extends V>... initial) {
		return CollectionLiterals.newTreeMap(comparator, initial);
	}

	/**
	 * Concatenate the given lists while omitting duplicates.
	 *
	 * @param listA
	 *            the first list, must not be <code>null</code>
	 * @param listB
	 *            the second list, must not be <code>null</code>
	 * @return the unique concatenation of the given lists
	 */
	public static <T> List<T> concatUnique(List<T> listA, List<T> listB) {
		Objects.requireNonNull(listA);
		Objects.requireNonNull(listB);

		Set<T> result = new LinkedHashSet<>(listA);
		result.addAll(listB);
		return new LinkedList<>(result);
	}

	/**
	 * Returns a stream of all pairs that can be formed with the elements of the given collection.
	 *
	 * Example: <code> [1,2,3] -> (1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3) </code>
	 */
	public static <T> Stream<Pair<T, T>> pairs(Collection<T> collection) {
		return collection.stream().flatMap(e1 -> collection.stream().map(e2 -> Pair.of(e1, e2)));
	}
}
