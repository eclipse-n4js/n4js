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

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.xtext.xbase.lib.ArrayLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Extension for {@link Iterable iterable}s.
 */
public class Iterables2 {

	/**
	 * Like {@link #skipDuplicates(Function, Iterable[])}, using the elements themselves as keys, i.e. a later element
	 * is deemed a duplicate of an earlier element iff the two elements are {@link Object#equals(Object) equal}.
	 */
	@SafeVarargs
	public static <T> Iterable<T> skipDuplicates(Iterable<? extends T>... inputs) {
		return skipDuplicates(Function.identity(), inputs);
	}

	/**
	 * Returns a view of the concatenation of the given {@link Iterable}s that hides all duplicate elements. A later
	 * element is deemed a duplicate of an earlier element iff the given key supplier returns
	 * {@link Object#equals(Object) equal} keys for the two elements.
	 */
	@SafeVarargs
	public static <T, K> Iterable<T> skipDuplicates(Function<T, K> keySupplier, Iterable<? extends T>... inputs) {
		Set<K> keySet = new HashSet<>();
		Predicate<T> recordingFilter = (elem) -> {
			K key = keySupplier.apply(elem);
			return keySet.add(key);
		};
		int len = inputs.length;
		Iterable<? extends T>[] inputsWrapped = ArrayLiterals.newArrayOfSize(len);
		for (int i = 0; i < len; i++) {
			inputsWrapped[i] = Iterables.filter(inputs[i], recordingFilter);
		}
		return Iterables.concat(inputsWrapped);
	}

	/**
	 * Creates a chain of the elements with the following rules:
	 * <ul>
	 * <li>Throws a NPE if the {@code elements} argument is {@code null}.</li>
	 * <li>Throws a IAE if the {@code elements} argument does not contain the {@code ref} argument.</li>
	 * <li>Returns with a new list which first and last element element will be the {@code ref}.</li>
	 * <ul>
	 * <li>If the {@code ref} argument contained multiple times in the list, then the first occurrence will be used.
	 * </li>
	 * </ul>
	 * <li>Creates a new list with the {@code ref}. Then finds the {@code ref} argument in the {@code elements} iterable
	 * and simply gets the rest of the elements from {@code elements} argument and appends it after the {@code ref},
	 * then gets all the elements before the {@code ref} in the original {@code elements} and appends to the new list.
	 * Finally appends the {@code ref} to the very end of the new list.
	 * </ul>
	 *
	 * For instance:
	 *
	 * <pre>
	 * #[1, 2, 3, 4, 5].chainOf(3) === #[3, 4, 5, 1, 2, 3];
	 * #[1, -1, 3, -1, 4, 5].chainOf(-1) === #[-1, 3, -1, 4, 5, 1, -1];
	 * </pre>
	 *
	 */
	public static <T> List<T> chainOf(Iterable<T> elements, T ref) {
		List<T> copy = IterableExtensions.toList(Preconditions.checkNotNull(elements));
		int indexOf = copy.indexOf(ref);
		checkArgument(0 <= indexOf, "Element " + ref + " does not contained in " + elements + ".");
		List<T> result = new ArrayList<>();
		result.addAll(copy.subList(indexOf, copy.size()));
		result.addAll(copy.subList(0, indexOf));
		result.add(ref);
		return result;
	}

	/**
	 * Returns an {@link Iterable} which aligns values of iterable1 and iterable2 by their indices.
	 *
	 * The size of the resulting iterable is the minimum of iterable1.size and iterable2.size.
	 */
	public static <T1, T2> Iterable<Pair<T1, T2>> align(Iterable<T1> iterable1, Iterable<T2> iterable2) {
		return new Iterable<>() {
			Iterator<T1> it1 = iterable1.iterator();
			Iterator<T2> it2 = iterable2.iterator();

			@Override
			public Iterator<Pair<T1, T2>> iterator() {
				return new Iterator<>() {

					@Override
					public boolean hasNext() {
						return it1.hasNext() && it2.hasNext();
					}

					@Override
					public Pair<T1, T2> next() {
						return Pair.of(it1.next(), it2.next());
					}
				};
			}
		};
	}

}
