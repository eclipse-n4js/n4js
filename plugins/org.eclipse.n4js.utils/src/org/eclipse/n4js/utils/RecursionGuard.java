/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.xbase.typesystem.util.RecursionGuard<T>
 *	in bundle org.eclipse.xtext.xbase
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import static com.google.common.base.Equivalence.identity;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Stack;

import com.google.common.base.Equivalence;

/**
 * A simple guard against infinite recursion.
 *
 * Can be used as a stack or a sink. It uses identity comparison to detect recursion.
 *
 * It is designed to cause minimal overhead for recursive structures that are not deeper than approximately 15 elements
 * and has less elements in the average case.
 *
 * Initially copied from xbase typesystem.
 */
public class RecursionGuard<T> {

	private static class Item<T> {
		T element;
		Item<T> next;
	}

	private Item<T> head;
	private final Equivalence<? super T> equivalence;

	/**
	 * Creates a new recursion guard with {@link Equivalence#identity() identity equivalence} checking logic.
	 */
	public RecursionGuard() {
		this(identity());
	}

	/**
	 * Creates a new recursions guard instance with the given {@link Equivalence equivalence} argument.
	 *
	 * @param equivalence
	 *            the equivalence instance encapsulating the logic that is used to identify already visited items.
	 *            Cannot be {@code null}.
	 */
	public RecursionGuard(final Equivalence<? super T> equivalence) {
		this.equivalence = checkNotNull(equivalence, "equivalence");
	}

	/**
	 * Announce the next element and report whether that one is currently in the stack. That is, element should only be
	 * processed by caller if this method returns true.
	 */
	public boolean tryNext(final T element) {
		Item<T> item = head;
		while (item != null) {
			if (equivalent(item.element, element)) {
				return false;
			}
			item = item.next;
		}
		final Item<T> newHead = new Item<>();
		newHead.element = element;
		newHead.next = head;
		head = newHead;
		return true;
	}

	/**
	 * Mark the traversal of the element as done so it can be removed from the stack. Calling this method is optional:
	 * If it is never called, no item in a graph is visited twice.
	 *
	 * @throws IllegalStateException
	 *             if element is not found on stack.
	 */
	public void done(final T element) {
		Item<T> item = head;
		Item<T> prev = null;
		while (item != null) {
			if (equivalent(item.element, element)) {
				if (prev == null) {
					head = item.next;
				} else {
					prev.next = item.next;
				}
				return;
			}
			prev = item;
			item = item.next;
		}
		throw new IllegalStateException("Element not found: " + element);
	}

	/**
	 * Returns a new {@link Stack} instance with all elements for which {@link #tryNext(Object) tryNext()} has been
	 * called without a later call to {@link #done(Object) done()}.
	 * <p>
	 * NOTE: not optimized for performance; use only in exception cases, e.g. for error reporting when an erroneous
	 * cycle has been detected.
	 */
	public Stack<T> getElements() {
		final Stack<T> result = new Stack<>();
		Item<T> item = head;
		while (item != null) {
			result.push(item.element);
			item = item.next;
		}
		Collections.reverse(result);
		return result;
	}

	/**
	 * Returns {@code true} if the given arguments are considered to be equivalent. By default the equivalence is
	 * defined by the {@link Equivalence} property of the current guard, but clients may override this function to
	 * customize the equivalence logic.
	 * <p>
	 * Overriding clients must note that both {@code left} and {@code right} arguments can be {@code null}.
	 *
	 * @param left
	 *            the left hand side value. Can be {@code null}.
	 * @param right
	 *            the right hand side value. Can be {@code null}.
	 * @return {@code true} if the two arguments are equivalent.
	 */
	protected boolean equivalent(final T left, final T right) {
		return equivalence.equivalent(left, right);
	}
}
