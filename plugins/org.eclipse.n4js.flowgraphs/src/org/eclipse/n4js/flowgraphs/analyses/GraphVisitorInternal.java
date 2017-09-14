/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * This class is the counterpart of {@link GraphVisitorGuideInternal} and the basis for the {@link GraphVisitor}.
 */
abstract public class GraphVisitorInternal {
	/** Reference to {@link N4JSFlowAnalyses}. Set before performing the analyses. */
	protected N4JSFlowAnalyses flowAnalyses;
	/** Container, specified in constructor */
	protected final ControlFlowElement container;
	/** Directions, specified in constructor */
	protected final Direction[] directions;

	private final List<PathExplorerInternal> activationRequests = new LinkedList<>();
	private final List<PathExplorerInternal> activatedPredicates = new LinkedList<>();
	private final List<PathExplorerInternal> activePredicates = new LinkedList<>();
	private final List<PathExplorerInternal> failedPredicates = new LinkedList<>();
	private final List<PathExplorerInternal> passedPredicates = new LinkedList<>();

	private ControlFlowElement currentContainer;
	private Direction currentDirection;
	private boolean activeDirection = false;

	/** Specifies the traverse direction of a {@link GraphVisitorInternal} instance. */
	public enum Direction {
		/** Forward edge-direction begins from the entry node of a given container. */
		Forward,
		/** Backward edge-direction begins from the exit node of a given container. */
		Backward,
		/**
		 * Begins from an arbitrary node N of a given container in the following manner: First in forward edge-direction
		 * beginning from the entry node of N, second in backward edge-direction beginning from the exit node of N.
		 * <p/>
		 * Note that N is unreachable from both the containers entry and its exit node.
		 */
		Islands,
		/** Forward edge-direction begins from the entry of a given catch block. */
		CatchBlocks
	}

	/**
	 * Constructor.
	 *
	 * @param directions
	 *            sets the directions for this instance. Default direction is {@literal Direction.Forward} if no
	 *            direction is given.
	 */
	protected GraphVisitorInternal(Direction... directions) {
		this(null, directions);
	}

	/**
	 * Constructor.
	 *
	 * @param container
	 *            sets the containing {@link ControlFlowElement} for this instance. Iff the given container is
	 *            {@code null}, this {@link GraphVisitorInternal} is applied on all containers.
	 * @param directions
	 *            sets the directions for this instance. Default directions are {@literal Direction.Forward} and
	 *            {@literal Direction.CatchBlocks} if no direction is given.
	 */
	protected GraphVisitorInternal(ControlFlowElement container, Direction... directions) {
		if (directions.length == 0) {
			directions = new Direction[] { Direction.Forward, Direction.CatchBlocks };
		}
		this.directions = directions;
		this.container = container;
	}

	/////////////////////// Abstract Methods ///////////////////////

	/** Called before any other method is called. */
	abstract protected void initAll();

	/**
	 * Called after {@link #initAll()} and before any visit-method is called.
	 *
	 * @param curDirection
	 *            direction of succeeding calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	abstract protected void initInternal(Direction curDirection, ControlFlowElement curContainer);

	/**
	 * Called for each node that is reachable w.r.t to the current direction and the current container.
	 * <p>
	 * Note that the order of nodes is arbitrary.
	 */
	abstract protected void visit(Node node);

	/**
	 * Called for each edge that is reachable w.r.t to the current direction and the current container.
	 * <p>
	 * Note that the order of edges is arbitrary.
	 *
	 * @param lastNode
	 *            node that was visited before
	 * @param currentNode
	 *            end node of the edge in terms of current direction
	 * @param edge
	 *            traversed edge
	 */
	abstract protected void visit(Node lastNode, Node currentNode, ControlFlowEdge edge);

	/**
	 * Called before {@link #terminateAll()} and after any visit-method is called.
	 *
	 * @param curDirection
	 *            direction of previous calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of previous calls to visit-methods
	 */
	abstract protected void terminate(Direction curDirection, ControlFlowElement curContainer);

	/** Called at last */
	abstract protected void terminateAll();

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/** Only called from {@link GraphVisitorGuideInternal}. Sets the reference to {@link N4JSFlowAnalyses} singleton. */
	final void setFlowAnalyses(N4JSFlowAnalyses fAnalyses) {
		this.flowAnalyses = fAnalyses;
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link #initInternal(Direction, ControlFlowElement)}.
	 */
	final void callInitInternal() {
		if (activeDirection) {
			initInternal(getCurrentDirection(), null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #initAll()}. */
	final void callInitAll() {
		initAll();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link #terminate(Direction, ControlFlowElement)}.
	 */
	final void callTerminate() {
		if (activeDirection) {
			terminate(getCurrentDirection(), null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminateAll()}. */
	final void callTerminateAll() {
		terminateAll();
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link GraphVisitorInternal#visit(Node)}. */
	final void callVisit(Node cfe) {
		if (activeDirection) {
			visit(cfe);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link GraphVisitorInternal#visit(Node,Node,ControlFlowEdge)}.
	 */
	final void callVisit(Node start, Node end, ControlFlowEdge edge) {
		if (activeDirection) {
			visit(start, end, edge);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Sets {@link #currentDirection} and {@link #currentContainer}.
	 */
	final void setContainerAndDirection(ControlFlowElement curContainer, Direction curDirection) {
		this.currentContainer = curContainer;
		this.currentDirection = curDirection;
		checkActive();
	}

	private void checkActive() {
		activeDirection = false;
		boolean containerActive = (container == null || container == currentContainer);

		if (containerActive) {
			for (Direction dir : directions) {
				if (dir == currentDirection) {
					activeDirection = true;
					break;
				}
			}
		}
	}

	final List<PathWalkerInternal> activate() {
		List<PathWalkerInternal> activatedPaths = new LinkedList<>();
		for (PathExplorerInternal app : activationRequests) {
			PathWalkerInternal activePath = app.firstPath();
			app.activePaths.add(activePath);
			app.allPaths.add(activePath);
			activePath.init();
			activatedPaths.add(activePath);
		}
		activatedPredicates.addAll(activationRequests);
		activePredicates.addAll(activationRequests);
		activationRequests.clear();
		return activatedPaths;
	}

	/////////////////////// Service Methods for inherited classes ///////////////////////

	/**
	 * Call this method to request the spawn of a new path predicate. The new path predicate is spawned after the
	 * current visit-method is finished. If not called from a visit-method, the new path predicate is spawned after the
	 * next visit-method is finished.
	 */
	final public void requestActivation(PathExplorerInternal app) {
		activationRequests.add(app);
	}

	/** @returns all activated predicates */
	final public List<PathExplorerInternal> getActivatedPredicates() {
		return activatedPredicates;
	}

	/** @returns all active predicates */
	final public List<PathExplorerInternal> getActivePredicates() {
		return activePredicates;
	}

	/** @returns the number of activated predicates */
	final public int getActivatedPredicateCount() {
		return getActivatedPredicates().size();
	}

	/** @returns the number of active predicates */
	final public int getActivePredicateCount() {
		return getActivePredicates().size();
	}

	/** @returns the current direction */
	final public Direction getCurrentDirection() {
		return currentDirection;
	}

	/** @returns the current container */
	final public ControlFlowElement getCurrentContainer() {
		return currentContainer;
	}

	/** @returns all passed predicates */
	final public List<?> getPassed() {
		return passedPredicates;
	}

	/** @returns all failed predicates */
	final public List<?> getFailed() {
		return failedPredicates;
	}

	/** The {@link PredicateType} defines under which condition a path predicate fails or passes. */
	public enum PredicateType {
		/** The path predicate passes iff all paths pass. */
		ForAllPaths,
		/** The path predicate passes if at least one path passes. */
		ForOnePath
	}

	/**
	 * An {@link PathExplorerInternal} is created and spawned from a {@link GraphVisitorInternal} on specific
	 * preconditions. It follows all paths beginning from the location of activation. Its initial path is forked using
	 * the method {@link #firstPath()}. Subsequent paths are forked from the initial paths. The
	 * {@link PathExplorerInternal} is deactivated in case it has no active paths anymore. The final state of a
	 * {@link PathExplorerInternal} can be either <i>Passed</i> or <i>Failed</i>.
	 * <p/>
	 * The life cycle of a path predicate:
	 * <ol>
	 * <li/>Instantiation
	 * <li/>Request for activation
	 * <li/>Activation
	 * <li/>Call to {@link PathExplorerInternal#firstPath()}
	 * <li/>De-Activation when all its {@link PathWalkerInternal}s are inactive
	 * <li/>Evaluation by user by calling e.g. {@link PathExplorerInternal#isPassed()}
	 * </ol>
	 */
	abstract public class PathExplorerInternal {
		private final Set<PathWalkerInternal> activePaths = new HashSet<>();
		private final List<PathWalkerInternal> passedPaths = new LinkedList<>();
		private final List<PathWalkerInternal> failedPaths = new LinkedList<>();
		private final List<PathWalkerInternal> allPaths = new LinkedList<>();
		/** Predicate type, specified in constructor */
		protected final PredicateType predicateType;
		/** Default verdict, specified in constructor */
		protected final boolean passAsDefault;

		/**
		 * Constructor
		 * <p>
		 * The predicate will pass as default in case {@link PathWalkerInternal#fail()} is never called on any of its
		 * active paths.
		 *
		 * @param predicateType
		 *            defines fail/pass condition
		 */
		protected PathExplorerInternal(PredicateType predicateType) {
			this(predicateType, true);
		}

		/**
		 * Constructor
		 *
		 * @param predicateType
		 *            defines fail/pass condition
		 * @param passAsDefault
		 *            iff true, the predicate will pass as default
		 */
		protected PathExplorerInternal(PredicateType predicateType, boolean passAsDefault) {
			this.predicateType = predicateType;
			this.passAsDefault = passAsDefault;
		}

		/////////////////////// Abstract Methods ///////////////////////

		/** Spawns the first path. Called right after this {@link PathExplorerInternal} gets activated. */
		abstract protected PathWalkerInternal firstPath();

		/////////////////////// Service Methods for inherited classes ///////////////////////

		/** @returns true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Passed</i> */
		final public boolean isPassed() {
			return passedPredicates.contains(this);
		}

		/** @returns true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Failed</i> */
		final public boolean isFailed() {
			return failedPredicates.contains(this);
		}

		/** @returns all paths no matter if they are active or not, or passed or failed. */
		final public List<PathWalkerInternal> getAllPaths() {
			return allPaths;
		}

		/** Deactivates all active paths and hence this path predicate. */
		final protected void deactivateAll() {
			while (!activePaths.isEmpty()) {
				PathWalkerInternal aPath = activePaths.iterator().next();
				aPath.deactivate();
			}
			checkPredicateDeactivation();
		}

		private void checkPredicateDeactivation() {
			if (activePaths.isEmpty()) {
				activePredicates.remove(this);

				boolean somePassed = !passedPaths.isEmpty();
				boolean someFailed = !failedPaths.isEmpty();

				boolean predicatePassed = false;
				predicatePassed |= somePassed && predicateType == PredicateType.ForOnePath;
				predicatePassed |= somePassed && !someFailed && predicateType == PredicateType.ForAllPaths;
				predicatePassed |= !somePassed && !someFailed && passAsDefault;
				if (predicatePassed) {
					passedPredicates.add(this);
				} else {
					failedPredicates.add(this);
				}
			}
		}

		/**
		 * Paths begin when a {@link PathExplorerInternal} gets active or when forked from another path. Paths end when
		 * no succeeding nodes are in the control flow graph, or the user calls either {@link #pass()}, {@link #fail()},
		 * or {@link #deactivate()}. Paths follow every edge and fork on every node that has more than one edge.
		 * However, each edge of type {@literal ControlFlowType.Repeat} is followed exactly twice.
		 */
		abstract public class PathWalkerInternal {

			/////////////////////// Abstract Methods ///////////////////////

			/** Called before any other method of this instance is called. */
			abstract protected void init();

			/** Called for each node in the order of nodes on the current path. */
			abstract protected void visit(Node node);

			/**
			 * Called for each edge in the order of edges on the current path.
			 *
			 * @param edge
			 *            traversed edge
			 */
			abstract protected void visit(Node start, Node end, ControlFlowEdge edge);

			/**
			 * Forks another path from the current node.<br/>
			 * <i>Take care about forking the current state of this instance!</i>
			 */
			abstract protected PathWalkerInternal fork();

			/** Called at last. */
			abstract protected void terminate();

			/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

			/**
			 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link PathWalkerInternal#visit(Node)}.
			 */
			final void callVisit(Node node) {
				if (activeDirection) {
					visit(node);
				}
			}

			/**
			 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
			 * {@link PathWalkerInternal#visit(Node, Node, ControlFlowEdge)}.
			 */
			final void callVisit(Node start, Node end, ControlFlowEdge edge) {
				if (activeDirection) {
					visit(start, end, edge);
				}
			}

			/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #fork()}. */
			final PathWalkerInternal callFork() {
				PathWalkerInternal forkedPath = fork();
				allPaths.add(forkedPath);
				activePaths.add(forkedPath);
				forkedPath.init();
				return forkedPath;
			}

			/////////////////////// Service Methods for inherited classes ///////////////////////

			/** Sets the verdict of this path to <i>Passed</i>. */
			final public void pass() {
				passedPaths.add(this);
				deactivate();
			}

			/** Sets the verdict of this path to <i>Failed</i>. */
			final public void fail() {
				failedPaths.add(this);
				deactivate();
			}

			/** Deactivates this path without setting the verdict. */
			final public void deactivate() {
				activePaths.remove(this);
				terminate();
				checkPredicateDeactivation();
			}

			/** @returns true, iff this path is active. */
			final public boolean isActive() {
				return activePaths.contains(this);
			}

		}
	}
}
