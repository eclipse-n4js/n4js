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

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * TRUE as switch result means: found a cycle and should stop.
 */
public class DependencyTraverser<T> {

	/**
	 * The entry point of the graph.
	 */
	private final T rootNode;

	/**
	 * Used for tracking already visited nodes. - Never call {@link RecursionGuard#done(Object) done(T)}.
	 */
	private final RecursionGuard<T> guard;

	/**
	 * Used for tracking cycle among elements. Always call {@link RecursionGuard#done(Object) done(T)} if node is left.
	 */
	private final RecursionGuard<T> pathGuard;

	/**
	 * Function for getting dependencies for a particular node in the graph.
	 */
	private final Function<T, Collection<? extends T>> dependenciesFunc;

	/**
	 * A reference for the node that is first re-visited while the graph traversal.
	 */
	private final AtomicReference<T> cycleNodeEntryRef;

	/***/
	public DependencyTraverser(final T rootNode, final Function1<T, Collection<? extends T>> dependenciesFunc) {
		this(rootNode, new FunctionDelegate<>(dependenciesFunc));
	}

	/***/
	@SuppressWarnings("unchecked")
	public DependencyTraverser(final T rootNode, final Function<T, Collection<? extends T>> dependenciesFunc) {
		this(rootNode, (Equivalence<T>) Equivalence.identity(), dependenciesFunc);
	}

	/***/
	public DependencyTraverser(final T rootNode, final Equivalence<T> equivalence,
			final Function<T, Collection<? extends T>> dependenciesFunc) {

		this.rootNode = rootNode;
		guard = new RecursionGuard<>(equivalence);
		pathGuard = new RecursionGuard<>(equivalence);
		this.dependenciesFunc = dependenciesFunc;
		cycleNodeEntryRef = new AtomicReference<>(null);
	}

	/**
	 * Return the computed result.
	 *
	 * @return a new dependency cycle instance as the outcome of the graph dependency discovery.
	 */
	public DependencyCycle<T> getResult() {
		cycleNodeEntryRef.set(null);
		final Boolean result = doSwitch(rootNode);
		if (TRUE.equals(result)) {
			return new DependencyCycle<>(pathGuard.getElements(), cycleNodeEntryRef.get());
		}
		return DependencyCycle.getNoCycleInstance();
	}

	private Boolean caseT(final T node) {

		if (pathGuard.tryNext(node)) { // object on stack.

			// should visit node:
			if (guard.tryNext(node)) {

				// load dependency and analyze.
				final Collection<? extends T> dependencies = getDependencies(node);
				for (final T dependency : dependencies) {
					final Boolean result = doSwitch(dependency);
					if (TRUE.equals(result)) {
						return result;
					}
				}
			} else {
				// already visited, it was cycle-free:
			}

			// remove from guardPath
			pathGuard.done(node);
			return FALSE;
		} else {
			cycleNodeEntryRef.set(node);
			// found cycle:
			return TRUE;
		}

	}

	private Collection<? extends T> getDependencies(final T node) {
		final Collection<? extends T> collection = dependenciesFunc.apply(node);
		return null == collection ? emptyList() : collection;
	}

	private Boolean doSwitch(final T node) {
		if (null != node) {
			final Boolean result = caseT(node);
			if (result != null) {
				return result;
			}
		}
		return FALSE;
	}

	/**
	 * Wraps a collection of nodes in the graph that are part of transitive dependency cycle and a node that is first
	 * re-visited during the graph traversal.
	 */
	public static final class DependencyCycle<T> {

		private static final DependencyCycle<Object> NO_CYCLE = new DependencyCycle<>(emptyList(), null);

		private final Collection<T> nodesWithCycle;
		private final Optional<T> cycleNodeEntryRef;

		private DependencyCycle(Collection<T> nodesWithCycle, T cycleNodeEntryRef) {
			checkNotNull(nodesWithCycle, "nodesWithCycle");
			checkArgument(nodesWithCycle.isEmpty() == (null == cycleNodeEntryRef),
					"Cyclic nodes and entry point mismatch.");
			this.nodesWithCycle = unmodifiableCollection(nodesWithCycle);
			this.cycleNodeEntryRef = fromNullable(cycleNodeEntryRef);
		}

		@SuppressWarnings("unchecked")
		private static <T> DependencyCycle<T> getNoCycleInstance() {
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
		 * Prints the underlying dependency cycle in a human readable format using the given {@link #toString()}
		 * function.
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

}
