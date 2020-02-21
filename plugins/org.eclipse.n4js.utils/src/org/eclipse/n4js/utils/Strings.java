/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Utility functions for strings
 */
abstract public class Strings {

	/** Extends {@link Function} with {@link Exception} thrown by {@link Function#apply(Object)} */
	public interface FunctionWithException<A, R, T extends Exception> {
		/** Invokes the given lambda */
		R apply(A a) throws T;
	}

	/**
	 * Converts the given iterable to a string representation of an array
	 *
	 * @param iterable
	 *            to be concatenated
	 */
	final static public String toString(Iterable<? extends Object> iterable) {
		return toString(Object::toString, iterable);
	}

	/**
	 * Converts the given iterable to a string representation of an array
	 *
	 * @param accessor
	 *            lambda to be used on every element of the iterable
	 */
	final static public <A, T extends Exception> String toString(FunctionWithException<A, String, T> accessor,
			Iterable<A> iterable) throws T {

		return "[" + join(", ", accessor, iterable) + "]";
	}

	/** Joins the given iterable with the given delimiter */
	final static public String join(String delimiter, Iterable<? extends Object> iterable) {
		return join(delimiter, Object::toString, iterable);
	}

	/** Joins the given array with the given delimiter */
	final static public String join(String delimiter, Object... objects) {
		return join(delimiter, Object::toString, objects);
	}

	/** Joins the given array with the given delimiter. The accessor is applied on every element of the array. */
	@SafeVarargs
	final static public <A, T extends Exception> String join(String delimiter,
			FunctionWithException<A, String, T> accessor, A... objects) throws T {

		return join(delimiter, accessor, Arrays.asList(objects));
	}

	/** Joins the given array with the given delimiter. The accessor is applied on every element of the iterable. */
	final static public <A, T extends Exception> String join(String delimiter,
			FunctionWithException<A, String, T> accessor, Iterable<A> iterable) throws T {

		if (iterable == null || delimiter == null || accessor == null) {
			return "";
		}
		String str = "";
		for (Iterator<A> it = iterable.iterator(); it.hasNext();) {
			A t = it.next();
			str += (t == null) ? "" : accessor.apply(t);
			if (it.hasNext()) {
				str += delimiter;
			}
		}

		return str;
	}
}
