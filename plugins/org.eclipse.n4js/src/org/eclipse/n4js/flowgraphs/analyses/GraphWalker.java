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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker.ActivatedPathPredicate.ActivePath;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
abstract public class GraphWalker {
	protected final N4JSFlowAnalyses flowAnalyses = null;
	private final List<ActivatedPathPredicate> activationRequests = new LinkedList<>();
	private final List<ActivatedPathPredicate> activatedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> activePredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> failedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> passedPredicates = new LinkedList<>();
	protected final Direction[] directions;
	private Direction curDirection;
	private boolean activeDirection = false;

	protected enum Direction {
		Forward, Backward, Islands
	}

	/** Default direction is {@literal Direction.Forward} */
	protected GraphWalker(Direction... directions) {
		if (directions.length == 0) {
			directions = new Direction[] { Direction.Forward };
		}
		this.directions = directions;
	}

	abstract protected void init();

	abstract protected void visit(ControlFlowElement cfe);

	abstract protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes);

	abstract protected void terminate();

	final void callVisit(ControlFlowElement cfe) {
		if (activeDirection) {
			visit(cfe);
		}
	}

	final void callVisit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
		if (activeDirection) {
			visit(start, end, cfTypes);
		}
	}

	final void setCurrentDirection(Direction curDirection) {
		this.curDirection = curDirection;
		activeDirection = false;
		for (Direction dir : directions) {
			if (dir == curDirection) {
				activeDirection = true;
				break;
			}
		}
	}

	final protected Direction getCurrentDirection() {
		return curDirection;
	}

	final public List<?> getPassed() {
		return passedPredicates;
	}

	final public List<?> getFailed() {
		return failedPredicates;
	}

	final protected void requestActivation(ActivatedPathPredicate app) {
		activationRequests.add(app);
	}

	final List<ActivePath> activate() {
		List<ActivePath> activatedPaths = new LinkedList<>();
		for (ActivatedPathPredicate app : activationRequests) {
			ActivePath activePath = app.first();
			app.activePaths.add(activePath);
			activatedPaths.add(activePath);
		}
		activatedPredicates.addAll(activationRequests);
		activePredicates.addAll(activationRequests);
		activationRequests.clear();
		return activatedPaths;
	}

	protected enum PredicateType {
		ForAllPaths, ForOnePath
	}

	abstract public class ActivatedPathPredicate {
		private final Set<ActivePath> activePaths = new HashSet<>();
		private final List<ActivePath> passedPaths = new LinkedList<>();
		private final List<ActivePath> failedPaths = new LinkedList<>();
		protected final PredicateType predicateType;

		protected ActivatedPathPredicate(PredicateType predicateType) {
			this.predicateType = predicateType;
		}

		abstract protected ActivePath first();

		final protected void deactivateAll() {
			while (!activePaths.isEmpty()) {
				ActivePath aPath = activePaths.iterator().next();
				aPath.deactivate();
			}
			checkPredicateDeactivation();
		}

		private void checkPredicateDeactivation() {
			if (activePaths.isEmpty()) {
				activePredicates.remove(this);

				if (!passedPaths.isEmpty()) {
					boolean predicatePassed = false;
					predicatePassed |= predicateType == PredicateType.ForOnePath;
					predicatePassed |= predicateType == PredicateType.ForAllPaths && failedPaths.isEmpty();
					if (predicatePassed) {
						passedPredicates.add(this);
					} else {
						failedPredicates.add(this);
					}
				}
			}
		}

		abstract public class ActivePath {

			abstract protected void init();

			abstract protected void visit(ControlFlowElement cfe);

			abstract protected void visit(ControlFlowElement start, ControlFlowElement end,
					Set<ControlFlowType> cfTypes);

			abstract protected ActivePath fork();

			abstract protected void terminate();

			final protected void callVisit(ControlFlowElement cfe) {
				if (activeDirection) {
					visit(cfe);
				}
			}

			final protected void callVisit(ControlFlowElement start, ControlFlowElement end,
					Set<ControlFlowType> cfTypes) {

				if (activeDirection) {
					visit(start, end, cfTypes);
				}
			}

			final ActivePath callFork() {
				ActivePath forkedPath = fork();
				activePaths.add(forkedPath);
				forkedPath.init();
				return forkedPath;
			}

			final protected void pass() {
				passedPaths.add(this);
				deactivate();
			}

			final protected void fail() {
				failedPaths.add(this);
				deactivate();
			}

			final boolean isActive() {
				return activePaths.contains(this);
			}

			final protected void deactivate() {
				activePaths.remove(this);
				terminate();
				checkPredicateDeactivation();
			}

		}
	}
}
