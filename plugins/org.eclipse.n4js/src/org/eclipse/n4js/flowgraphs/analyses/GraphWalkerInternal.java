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
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.ActivatedPathPredicateInternal.ActivePathInternal;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 *
 */
@SuppressWarnings("javadoc")
abstract public class GraphWalkerInternal {
	protected final N4JSFlowAnalyses flowAnalyses = null;
	private final List<ActivatedPathPredicateInternal> activationRequests = new LinkedList<>();
	private final List<ActivatedPathPredicateInternal> activatedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicateInternal> activePredicates = new LinkedList<>();
	private final List<ActivatedPathPredicateInternal> failedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicateInternal> passedPredicates = new LinkedList<>();
	protected final Direction[] directions;
	private Direction curDirection;
	private boolean activeDirection = false;

	protected enum Direction {
		Forward, Backward, Islands
	}

	/** Default direction is {@literal Direction.Forward} */
	protected GraphWalkerInternal(Direction... directions) {
		if (directions.length == 0) {
			directions = new Direction[] { Direction.Forward };
		}
		this.directions = directions;
	}

	abstract protected void init();

	abstract protected void visit(Node node);

	abstract protected void visit(Node start, Node end, ControlFlowType cfType);

	abstract protected void terminate();

	final protected void callInit() {
		if (activeDirection) {
			init();
		}
	}

	final protected void callTerminate() {
		if (activeDirection) {
			terminate();
		}
	}

	final void callVisit(Node cfe) {
		if (activeDirection) {
			visit(cfe);
		}
	}

	final void callVisit(Node start, Node end, ControlFlowType cfType) {
		if (activeDirection) {
			visit(start, end, cfType);
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

	final protected int getActivatedPredicateCount() {
		return getActivatedPredicates().size();
	}

	final protected List<ActivatedPathPredicateInternal> getActivatedPredicates() {
		return activatedPredicates;
	}

	final protected List<ActivatedPathPredicateInternal> getActivePredicates() {
		return activePredicates;
	}

	final protected int getActivePredicateCount() {
		return getActivePredicates().size();
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

	final protected void requestActivation(ActivatedPathPredicateInternal app) {
		activationRequests.add(app);
	}

	final List<ActivePathInternal> activate() {
		List<ActivePathInternal> activatedPaths = new LinkedList<>();
		for (ActivatedPathPredicateInternal app : activationRequests) {
			ActivePathInternal activePath = app.first();
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

	protected enum PredicateType {
		ForAllPaths, ForOnePath
	}

	abstract public class ActivatedPathPredicateInternal {
		private final Set<ActivePathInternal> activePaths = new HashSet<>();
		private final List<ActivePathInternal> passedPaths = new LinkedList<>();
		private final List<ActivePathInternal> failedPaths = new LinkedList<>();
		private final List<ActivePathInternal> allPaths = new LinkedList<>();
		protected final PredicateType predicateType;

		protected ActivatedPathPredicateInternal(PredicateType predicateType) {
			this.predicateType = predicateType;
		}

		abstract protected ActivePathInternal first();

		final protected void deactivateAll() {
			while (!activePaths.isEmpty()) {
				ActivePathInternal aPath = activePaths.iterator().next();
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

		final protected List<ActivePathInternal> getAllPaths() {
			return allPaths;
		}

		abstract public class ActivePathInternal {

			abstract protected void init();

			abstract protected void visit(Node cfe);

			abstract protected void visit(Node start, Node end, ControlFlowType cfType);

			abstract protected ActivePathInternal fork();

			abstract protected void terminate();

			final protected void callVisit(Node cfe) {
				if (activeDirection) {
					visit(cfe);
				}
			}

			final protected void callVisit(Node start, Node end, ControlFlowType cfType) {
				if (activeDirection) {
					visit(start, end, cfType);
				}
			}

			final ActivePathInternal callFork() {
				ActivePathInternal forkedPath = fork();
				allPaths.add(forkedPath);
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
