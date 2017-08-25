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
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Collects all reachable nodes.
 */
public class DeadCodePredicateWalker extends GraphWalker {
	Set<ControlFlowElement> allForwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allBackwardCFEs = new HashSet<>();
	Set<ControlFlowElement> allIslandsCFEs = new HashSet<>();
	Set<ControlFlowElement> unreachableCFEs = new HashSet<>();

	DeadCodePredicateWalker() {
		super(Direction.Forward, Direction.Backward, Direction.Islands);
	}

	@Override
	protected void init() {
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		switch (getCurrentDirection()) {
		case Forward:
			allForwardCFEs.add(cfe);
			break;
		case Backward:
			allBackwardCFEs.add(cfe);
			break;
		case Islands:
			allIslandsCFEs.add(cfe);
			break;
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
		// nothing
	}

	@Override
	protected void terminate() {
		unreachableCFEs.addAll(allBackwardCFEs);
		unreachableCFEs.removeAll(allForwardCFEs);
		unreachableCFEs.addAll(allIslandsCFEs);
	}

	public Set<ControlFlowElement> getReachableCFEs() {
		return allForwardCFEs;
	}

	public Set<ControlFlowElement> getUnreachableCFEs() {
		return unreachableCFEs;
	}

}
