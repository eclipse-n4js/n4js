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
package org.eclipse.n4js.utils;

/**
 * Similar to the {@link java.util.function.Consumer} but parameterized with exceptions. Useful when passing method
 * reference that throws exception.
 *
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

	/**
	 * Performs this operation on the given argument. Might throw declared exception that called needs to handle.
	 *
	 * @param t
	 *            the input argument
	 */
	void accept(T t) throws E;
}
