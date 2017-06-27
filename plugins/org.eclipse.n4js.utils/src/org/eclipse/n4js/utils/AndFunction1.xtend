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
package org.eclipse.n4js.utils

import com.google.common.collect.ImmutableList
import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1

import static com.google.common.collect.Lists.asList

import static extension com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.Lists

/**
 * A predicate like {@link Function1 function} implementation with logical conjunction capabilities.
 * In other words the current AND function will be evaluated to {@code true} if all wrapped functions
 * are evaluated to {@code true}. Otherwise it will be evaluated to {@code false}.
 */
class AndFunction1<F> implements Function1<F, Boolean> {

	val List<Function1<? super F, Boolean>> functions;

	/**
	 * Creates a new function instance that will represent a logical conjunction.
	 */
	def static <F> conjunctionOf(Function1<? super F, Boolean> first, Function1<? super F, Boolean>... others) {
		new AndFunction1<F>(
			asList(
				first.checkNotNull('first'),
				others.checkNotNull('others')
			)
		);
	}

	/**
	 * Creates a new logical AND predicate function with the iterable of wrapped functions.
	 */
	protected new(Iterable<? extends Function1<? super F, Boolean>> toAdd) {
		functions = ImmutableList.builder
			.addAll(toAdd.checkNotNull('others'))
			.build;
	}

	@Override
	override apply(F p) {
		for (f : functions) {
			if (!f.apply(p)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns with a new AND function instance by copying the current instance and appending the argument
	 * to to it.
	 */
	def AndFunction1<F> and(Function1<? super F, Boolean>... toAdd) {
		return if (toAdd.nullOrEmpty) this else new AndFunction1(Lists.asList(this, toAdd))
	}

}
