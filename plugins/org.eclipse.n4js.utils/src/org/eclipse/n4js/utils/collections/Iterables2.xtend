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
package org.eclipse.n4js.utils.collections

import com.google.common.base.Predicate
import com.google.common.collect.Iterables
import java.util.Iterator
import java.util.List
import java.util.function.Function

import static com.google.common.collect.Sets.newHashSet
import static java.util.Collections.singletonList

import static extension com.google.common.base.Preconditions.*

/**
 * Extension for {@link Iterable iterable}s.
 */
class Iterables2 {

	/**
	 * Like {@link #skipDuplicates(Function, Iterable[])}, using the elements themselves as keys, i.e. a later element is
	 * deemed a duplicate of an earlier element iff the two elements are {@link Object#equals(Object) equal}.
	 */
	@SafeVarargs
	def static <T, K> Iterable<T> skipDuplicates(Iterable<? extends T>... inputs) {
		return skipDuplicates(Function.identity, inputs);
	}

	/**
	 * Returns a view of the concatenation of the given {@link Iterable}s that hides all duplicate elements. A later element
	 * is deemed a duplicate of an earlier element iff the given key supplier returns {@link Object#equals(Object) equal} keys
	 * for the two elements.
	 */
	@SafeVarargs
	def static <T, K> Iterable<T> skipDuplicates(Function<T,K> keySupplier, Iterable<? extends T>... inputs) {
		val keySet = <K>newHashSet;
		val Predicate<T> recordingFilter = [ elem |
			val key = keySupplier.apply(elem);
			return keySet.add(key);
		];
		val len = inputs.length;
		val inputsWrapped = newArrayOfSize(len);
		for (var int i = 0; i < len; i++) {
			inputsWrapped.set(i, Iterables.filter(inputs.get(i), recordingFilter));
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
	 * <pre>
	 * #[1, 2, 3, 4, 5].chainOf(3) === #[3, 4, 5, 1, 2, 3];
	 * #[1, -1, 3, -1, 4, 5].chainOf(-1) === #[-1, 3, -1, 4, 5, 1, -1];
	 * </pre>
	 *
	 */
	def static <T> List<T> chainOf(Iterable<T> elements, T ref) {
		val copy = elements.checkNotNull.toList;
		val indexOf = copy.indexOf(ref);
		checkArgument(0 <= indexOf, '''Element «ref» does not contained in «elements».''');
		return (copy.subList(indexOf, copy.size) + copy.subList(0, indexOf) + singletonList(ref)).toList;
	}
	/**
	 * Returns an {@link Iterable} which aligns values of iterable1 and iterable2 by their indices.
	 * 
	 * The size of the resulting iterable is the minimum of iterable1.size and iterable2.size.
	 */
	def static <T1, T2> Iterable<Pair<T1, T2>> align(Iterable<T1> iterable1, Iterable<T2> iterable2) {
		return [
				val it1 = iterable1.iterator;
				val it2 = iterable2.iterator;
				
				return new Iterator<Pair<T1, T2>>() {
					
					override hasNext() {
						return it1.hasNext && it2.hasNext;
					}
					
					override next() {
						return it1.next -> it2.next
					}
					
				}
		]
	}

}
