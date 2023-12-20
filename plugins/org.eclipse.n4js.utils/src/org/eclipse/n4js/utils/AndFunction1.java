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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.asList;

import java.util.List;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * A predicate like {@link Function1 function} implementation with logical conjunction capabilities. In other words the
 * current AND function will be evaluated to {@code true} if all wrapped functions are evaluated to {@code true}.
 * Otherwise it will be evaluated to {@code false}.
 */
public class AndFunction1<F> implements Function1<F, Boolean> {

	final List<Function1<? super F, Boolean>> functions;

	/**
	 * Creates a new function instance that will represent a logical conjunction.
	 */
	@SuppressWarnings("unchecked")
	static public <F> AndFunction1<F> conjunctionOf(Function1<? super F, Boolean> first,
			Function1<? super F, Boolean>... others) {

		return new AndFunction1<>(
				asList(
						checkNotNull(first, "first"),
						checkNotNull(others, "others")));
	}

	/**
	 * Creates a new logical AND predicate function with the iterable of wrapped functions.
	 */
	protected AndFunction1(Iterable<? extends Function1<? super F, Boolean>> toAdd) {
		checkNotNull(toAdd, "others");
		functions = ImmutableList.<Function1<? super F, Boolean>> builder().addAll(toAdd).build();
	}

	@Override
	public Boolean apply(F p) {
		for (Function1<? super F, Boolean> f : functions) {
			if (!f.apply(p)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns with a new AND function instance by copying the current instance and appending the argument to to it.
	 */
	@SuppressWarnings("unchecked")
	public AndFunction1<F> and(Function1<? super F, Boolean>... toAdd) {
		return (toAdd == null || toAdd.length == 0) ? this : new AndFunction1<>(Lists.asList(this, toAdd));
	}

}
