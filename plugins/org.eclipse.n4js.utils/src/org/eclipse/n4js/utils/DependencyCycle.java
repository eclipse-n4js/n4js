/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Wraps a collection of nodes in the graph that are part of a transitive dependency cycle and a node that is first
 * re-visited during the graph traversal.
 */
public final class DependencyCycle<T> {

	private static final DependencyCycle<Object> NO_CYCLE = new DependencyCycle<>(emptyList(), null);

	private final Collection<T> nodesWithCycle;
	private final Optional<T> cycleNodeEntryRef;

	DependencyCycle(Collection<T> nodesWithCycle, T cycleNodeEntryRef) {
		checkNotNull(nodesWithCycle, "nodesWithCycle");
		checkArgument(nodesWithCycle.isEmpty() == (null == cycleNodeEntryRef),
				"Cyclic nodes and entry point mismatch.");
		this.nodesWithCycle = unmodifiableCollection(nodesWithCycle);
		this.cycleNodeEntryRef = fromNullable(cycleNodeEntryRef);
	}

	/**
	 * Returns a {@link DependencyCycle} instance that represents an empty dependency cycle.
	 */
	@SuppressWarnings("unchecked")
	public static <T> DependencyCycle<T> noCycle() {
		return (DependencyCycle<T>) NO_CYCLE;
	}

	/**
	 * Returns with {@code true} if the current instance represents a cycle, otherwise, {@code false}.
	 *
	 * @return {@code true} if cycle, otherwise {@code false}.
	 */
	public boolean hasCycle() {
		return !nodesWithCycle.isEmpty() && cycleNodeEntryRef.isPresent();
	}

	/**
	 * Prints the underlying dependency cycle in a human readable format.
	 *
	 * @return a human consumable string representation of the underlying dependency cycle.
	 */
	public String prettyPrint() {
		return prettyPrint(node -> String.valueOf(node));
	}

	/**
	 * Prints the underlying dependency cycle in a human readable format using the given {@link #toString()} function.
	 *
	 * @param toStringFunc
	 *            function to get the human readable format of the underlying nodes.
	 * @return a human consumable string representation of the underlying dependency cycle.
	 */
	public String prettyPrint(Function<? super T, String> toStringFunc) {
		if (!hasCycle()) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (final T node : nodesWithCycle) {
			if (sb.length() > 0) {
				sb.append(" -> ");
			}
			sb.append(prettyPrintNode(toStringFunc, node));
		}
		sb.append(" -> ");
		sb.append(prettyPrintNode(toStringFunc, cycleNodeEntryRef.get()));
		return sb.toString();
	}

	private String prettyPrintNode(Function<? super T, String> toStringFunc, final T node) {
		final StringBuilder sb = new StringBuilder();
		if (node.equals(cycleNodeEntryRef.get())) {
			sb.append("[");
		}
		sb.append(toStringFunc.apply(node));
		if (node.equals(cycleNodeEntryRef.get())) {
			sb.append("]");
		}
		return sb.toString();
	}

}