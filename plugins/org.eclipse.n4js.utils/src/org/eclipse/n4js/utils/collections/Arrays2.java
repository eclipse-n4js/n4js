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

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;
import java.util.RandomAccess;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Utility class for arrays.
 */
public class Arrays2 {

	/**
	 * Transforms the given array into a list by applying the given function onto each element of the array argument.
	 * <br>
	 * The returning list is {@link RandomAccess random access} and {@link Serializable serializable}. The order of the
	 * elements does not change. Modification of the array is only supported via the {@link List#set(int, Object) set}
	 * and {@link List#replaceAll(java.util.function.UnaryOperator) replaceAll} methods.
	 *
	 * @param array
	 *            the array to transform.
	 * @param f
	 *            the function to apply onto each element for the transformation.
	 * @return a list of element.
	 */
	public static <F, T> List<T> transform(final F[] array, final Function<F, T> f) {
		if (null == array || 0 == array.length) {
			return emptyList();
		}
		@SuppressWarnings("unchecked")
		final T[] result = (T[]) Array.newInstance(Object.class, array.length);
		for (int i = 0; i < array.length; i++) {
			result[i] = f.apply(array[i]);
		}
		return asList(result);
	}

	/**
	 * Changes each element of the given array by applying the given function.
	 */
	public static <T> void transformInplace(T[] array, Function<T, T> fun) {
		if (array == null || array.length == 0) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = fun.apply(array[i]);
		}
	}

	/**
	 * Filters the elements from the array given as the argument. Always returns with a new array instance containing
	 * all those elements from the original list whose class is assignable form the {@code predicate} class argument.
	 *
	 * @param array
	 *            the input array to filter.
	 * @param predicate
	 *            the predicate class to filter.
	 * @return a new type safe filtered array instance.
	 */
	public static <F, T extends F> T[] filter(final F[] array, final Class<T> predicate) {
		checkNotNull(array, "array");
		checkNotNull(predicate, "clazz");
		final int size = array.length;
		final List<T> result = Lists.newArrayListWithExpectedSize(size);
		for (int i = 0; i < size; i++) {
			final F actual = array[i];
			if (null != actual && predicate.isAssignableFrom(actual.getClass())) {
				result.add(predicate.cast(actual));
			}
		}
		return Iterables.toArray(result, predicate);
	}

	/**
	 * Returns with {@code true} if the array argument is either {@code null} or its length is {@code 0}. Otherwise
	 * returns with {@code false}.
	 *
	 * @param array
	 *            the array to check whether it is empty or not.
	 * @return {@code true} if the array is empty, otherwise {@code false}.
	 */
	public static <T> boolean isEmpty(final T[] array) {
		return null == array || 0 == array.length;
	}

	/**
	 * Adds the elements to the end of the given {@code source} array. If the {@code otherToAdd} is empty this method
	 * returns with the {@code source} argument, otherwise this method always creates a new array instance, copies the
	 * elements from the {@code source} array to the new array instance then adds the additional elements to the end of
	 * the new array.
	 *
	 * @param source
	 *            the source array to 'append' to.
	 * @param othersToAdd
	 *            other optional elements. Could be {@code null} or could contain {@code null} elements.
	 * @return a new array instance with the copied and appended elements.
	 */
	@SafeVarargs
	public static <T> T[] add(final T[] source, final T... othersToAdd) {
		checkNotNull(source, "source");
		if (isEmpty(othersToAdd)) {
			return source;
		}
		@SuppressWarnings("unchecked")
		final T[] result = (T[]) Array.newInstance(Object.class, source.length + othersToAdd.length);
		System.arraycopy(source, 0, result, 0, source.length);
		System.arraycopy(othersToAdd, 0, result, source.length, othersToAdd.length);
		return result;
	}

}
