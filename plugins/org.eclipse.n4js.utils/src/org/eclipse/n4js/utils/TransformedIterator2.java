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
package org.eclipse.n4js.utils;

import java.util.Iterator;

import com.google.common.collect.Iterators;

/**
 * Like Google Common's <code>TansformedIterator</code> that is created by method
 * {@link Iterators#transform(Iterator, com.google.common.base.Function) transform()}, but allows to optionally
 * transform one input element into two(!) target elements.
 */
public abstract class TransformedIterator2<F, T> implements Iterator<T> {

	/** The iterator being transformed. */
	protected final Iterator<? extends F> backingIterator;
	/** An additional element being inserted. */
	protected T additionalElement = null;

	/**
	 * New instance.
	 */
	public TransformedIterator2(Iterator<? extends F> backingIterator) {
		this.backingIterator = backingIterator;
	}

	/**
	 * Subclasses must implement this to perform actual transformation of elements from the backingIterator. Each
	 * element is transformed into either 1 or 2 target elements. In both cases, this method returns a target element.
	 * In the second case, this method calls {@link #setAdditionalElement(Object)} to set the second target element,
	 * which will then be inserted <em>after</em> the returned element (and before the next invocation of this method).
	 */
	protected abstract T transform(F from);

	/**
	 * Sets the additional element. Should only be called from method {@link #transform(Object)}. The additional element
	 * passed to this method will be added <em>after</em> the element returned from method <code>#transform()</code>.
	 */
	protected void setAdditionalElement(T elem) {
		additionalElement = elem;
	}

	@Override
	public final boolean hasNext() {
		return additionalElement != null || backingIterator.hasNext();
	}

	@Override
	public final T next() {
		if (additionalElement != null) {
			final T result = additionalElement;
			additionalElement = null;
			return result;
		}
		return transform(backingIterator.next());
	}

	@Override
	public final void remove() {
		additionalElement = null;
		backingIterator.remove();
	}
}
