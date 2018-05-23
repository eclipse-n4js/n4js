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

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

/**
 * A lazily initialized optional value. The value is initialized using a supplier that is called at most once when
 * {@link #get()} is called for the first time. Subsequent calls to {@link #get()} will never call the supplier again
 * even if the supplier returned <code>null</code>. After a call supplier reference is set to <code>null</code>.
 *
 * Obtaining the value from the supplier should be thread safe.
 *
 * @see <a href="https://shipilev.net/blog/2016/close-encounters-of-jmm-kind/">JavaMemoryModel</a>
 * @see <a href="http://minborgsjavapot.blogspot.de/2016/01/be-lazy-with-java-8.html">lazy with java 8</a>
 */
public class Lazy<T> {
	private volatile T value;
	private volatile boolean initialized = false;
	private volatile Supplier<T> supplier;

	/**
	 * Creates a new instance using the given supplier. This is just a convenience method.
	 *
	 * @param supplier
	 *            the supplier to use
	 * @return the new instance
	 */
	public static <T> Lazy<T> create(Supplier<T> supplier) {
		return new Lazy<>(supplier);
	}

	/**
	 * Creates a new instance that uses the given supplier to initialized the lazily computed value. The given supplier
	 * is called at most once.
	 *
	 * @param supplier
	 *            the supplier to use
	 */
	public Lazy(Supplier<T> supplier) {
		this.supplier = requireNonNull(supplier);
	}

	/**
	 * Indicate whether this lazy value has been initialized.
	 *
	 * @return <code>true</code> if this value has been initialized and false otherwise
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Performs lazy initialization if necessary and returns the result, which may be <code>null</code>.
	 *
	 * @return the lazily initialized value, which may be <code>null</code>
	 */
	public T get() {
		return initialized ? value : compute();
	}

	/** Computes {@link Lazy#value}, sets {@link Lazy#initialized} flag and throws away the {@link Lazy#supplier}. */
	private synchronized T compute() {
		if (!initialized) {
			value = supplier.get();
			initialized = true;
			supplier = null;
		}
		return value;
	}

}
