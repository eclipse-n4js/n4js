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

import java.util.Collection;

import com.google.common.base.Equivalence;

/**
 * A generic dependency traverser for traversing a dependency graph as given by a {@link DependencyProvider}.
 *
 * Integrates an optional {@link DependencyVisitor} that may be used to enable custom behavior during traversal (e.g.
 * collect transitive dependencies).
 *
 * Apart from that this traverser may also be used to detect dependency cycles.
 */
public class DependencyTraverser<T> {

	/**
	 * A visitor for traversing dependency trees.
	 *
	 * The {@link #accept(Object)} method of this visitor will be invoked for every visited node.
	 *
	 * @See {@link DependencyTraverser}.
	 */
	@FunctionalInterface
	public static interface DependencyVisitor<NodeT> {
		/**
		 * Visit the given dependency {@code node}.
		 */
		void accept(NodeT node);
	}

	/**
	 * Provider that is used to define the structure of the dependency graph in terms of the set of dependencies per
	 * node.
	 */
	@FunctionalInterface
	public static interface DependencyProvider<NodeT> {
		/**
		 * Return the list of nodes that represent the dependencies of {@code node}.
		 */
		Collection<? extends NodeT> getDependencies(NodeT node);
	}

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
	 * The visitor to walk through the dependency graph.
	 */
	private DependencyVisitor<T> visitor;

	/**
	 * The dependency provider to use for obtaining the list of dependencies per node.
	 */
	private DependencyProvider<T> dependencyProvider;

	/**
	 * Holds a reference to the first discovered cycle of the most recent traversal.
	 */
	private DependencyCycle<T> firstDiscoveredCycle;

	/**
	 * Indicates whether dependency cycles are ignored. More specifically, whether cyclic edges are simply not
	 * traversed.
	 */
	private final boolean ignoreCycles;

	/**
	 * Instantiates a new {@link DependencyTraverser} that detects a cycle based on an identity check between nodes and
	 * does not consider a custom visitor.
	 *
	 * Apart from that see
	 * {@link #DependencyTraverser(Object, Equivalence, DependencyVisitor, DependencyProvider, boolean)}.
	 */
	public DependencyTraverser(final T rootNode, DependencyProvider<T> dependencyProvider,
			boolean ignoreCycles) {
		this(rootNode, Equivalence.equals(), nopVisitor(), dependencyProvider, ignoreCycles);
	}

	/**
	 * Instantiates a new {@link DependencyTraverser} that detects a cycle based on an identity check between nodes.
	 *
	 * Apart from that see
	 * {@link #DependencyTraverser(Object, Equivalence, DependencyVisitor, DependencyProvider, boolean)}.
	 */
	public DependencyTraverser(final T rootNode, DependencyVisitor<T> visitor,
			DependencyProvider<T> dependencyProvider, boolean ignoreCycles) {
		this(rootNode, Equivalence.equals(), visitor, dependencyProvider, ignoreCycles);
	}

	/**
	 * Instantiates a new {@link DependencyTraverser} with the given parameters.
	 *
	 * @param rootNode
	 *            The root of the traversal.
	 * @param equivalence
	 *            The equivalence strategy to use for detecting dependency cycles.
	 * @param visitor
	 *            The visitor to use to obtain node dependencies and execute on every visited node.
	 * @param dependencyProvider
	 *            The provider to use to obtain the list of dependencies per node.
	 * @param ignoreCycles
	 *            Specifies whether dependency cycles should be ignored (e.g. do not follow cyclic edges, only visit
	 *            every node once).
	 */
	public DependencyTraverser(final T rootNode, final Equivalence<? super T> equivalence,
			DependencyVisitor<T> visitor, DependencyProvider<T> dependencyProvider, boolean ignoreCycles) {
		this.rootNode = rootNode;
		this.visitor = visitor;
		this.dependencyProvider = dependencyProvider;
		this.ignoreCycles = ignoreCycles;

		this.guard = new RecursionGuard<>(equivalence);
		this.pathGuard = new RecursionGuard<>(equivalence);
	}

	/**
	 * Searches for any dependency cycle in the dependency graph rooted in {@link #rootNode}. Returns the first
	 * discovered {@link DependencyCycle}, if the traversed graph contains any.
	 *
	 * Returns an empty {@link DependencyCycle}, if no dependency cycle could be discovered.
	 *
	 * If {@link #ignoreCycles} is {@code true}, this method will invoke {@link #visitor} for every node in the graph,
	 * regardless of any discovered dependency cycles.
	 *
	 * If {@link #ignoreCycles} is {@code false}, {@link #visitor} will only be called for a subset of all nodes, until
	 * the dependency cycle is discovered.
	 *
	 * @return a new dependency cycle instance as the outcome of the graph dependency discovery.
	 */
	public DependencyCycle<T> findCycle() {
		this.firstDiscoveredCycle = null;
		traverse();

		if (firstDiscoveredCycle != null) {
			return firstDiscoveredCycle;
		}
		return DependencyCycle.noCycle();
	}

	/**
	 * Traverses the dependency graph and invokes {@link #visitor} for every visited node.
	 *
	 * May abort the traversal early, depending on the configured value for {@link #ignoreCycles}.
	 *
	 * Returns {@code true} iff the traversal was terminated early (cycle detected and {@link #ignoreCycles} is
	 * {@code false}).
	 */
	public boolean traverse() {
		return this.doTraverse(this.rootNode);
	}

	/**
	 * Visits the given dependency node and recursively triggers the visiting of all node dependencies.
	 *
	 * Returns {@code true} iff the traversal was terminated early (cycle detected and {@link #ignoreCycles} is
	 * {@code false}).
	 */
	private boolean doTraverse(final T node) {
		if (node == null) {
			return false;
		}

		if (pathGuard.tryNext(node)) { // object on stack.
			// should visit node:
			if (guard.tryNext(node)) {
				// invoke visitor
				visitor.accept(node);
				// load dependency and analyze.
				final Collection<? extends T> dependencies = dependencyProvider.getDependencies(node);

				if (dependencies != null) {
					for (final T dependency : dependencies) {
						if (doTraverse(dependency)) {
							return true;
						}
					}
				}
			} else {
				// already visited
			}

			// remove from guardPath
			pathGuard.done(node);
			return false;
		} else {
			// cycle detected (in currently explored path)

			// keep reference to first detected cycle
			if (firstDiscoveredCycle == null) {
				firstDiscoveredCycle = new DependencyCycle<>(pathGuard.getElements(), node);
			}

			if (!ignoreCycles) {
				// found cycle, abort traversal
				return true;
			}
			// otherwise, ignore this edge and continue traversal
			return false;
		}

	}

	/**
	 * Instantiates a new No-Op-{@link DependencyVisitor} for the given generic type.
	 */
	private static <T> DependencyVisitor<T> nopVisitor() {
		return new DependencyVisitor<>() {
			@Override
			public void accept(T node) {
				// nop
			}
		};
	}

}
