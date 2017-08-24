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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
abstract public class GraphWalker {
	protected final N4JSFlowAnalyses flowAnalyses = null;
	private final List<ActivatedPathPredicate> activatedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> activePredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> failedPredicates = new LinkedList<>();
	private final List<ActivatedPathPredicate> passedPredicates = new LinkedList<>();

	abstract protected boolean isBackwards();

	abstract protected void visit(ControlFlowElement cfe);

	abstract protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType);

	final public List<?> getPassed() {
		return passedPredicates;
	}

	final public List<?> getFailed() {
		return failedPredicates;
	}

	final protected void activate(ActivatedPathPredicate app) {
		activatedPredicates.add(app);
		activePredicates.add(app);
	}

	abstract public class ActivatedPathPredicate {
		private final List<ActivePath> activePaths = new LinkedList<>();
		private boolean failed = false;

		abstract protected void init();

		abstract protected ActivePath first();

		abstract protected void terminate();

		final protected void pass() {
			passedPredicates.add(this);
			deactivateAll();
		}

		final protected void fail() {
			failed = true;
			failedPredicates.add(this);
			deactivateAll();
		}

		public void deactivateAll() {
			activePaths.clear();
			checkPredicateDeactivation();
		}

		private void checkPredicateDeactivation() {
			if (activePaths.isEmpty()) {
				activePredicates.remove(this);
			}
		}

		abstract public class ActivePath {

			abstract protected void init();

			abstract protected void visit(ControlFlowElement cfe);

			abstract protected void visit(ControlFlowElement start, ControlFlowElement end, ControlFlowType cfType);

			abstract protected ActivePath fork();

			abstract protected void terminate();

			protected void deactivate() {
				activePaths.remove(this);
				checkPredicateDeactivation();
			}

		}
	}
}
