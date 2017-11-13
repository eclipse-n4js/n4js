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

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * see {@link GraphVisitorInternal}
 */
abstract public class GraphVisitor extends GraphVisitorInternal {
	final private Set<ControlFlowEdge> visitedEdgesInternal = new HashSet<>();
	final private Set<FlowEdge> visitedEdges = new HashSet<>();
	private boolean lastVisitedCFEIsDead = false;

	/** see {@link GraphVisitorInternal#GraphVisitorInternal(Mode...)} */
	protected GraphVisitor(Mode... modes) {
		this(null, modes);
	}

	/** see {@link GraphVisitorInternal#GraphVisitorInternal(ControlFlowElement, Mode...)} */
	public GraphVisitor(ControlFlowElement container, Mode... modes) {
		super(container, modes);
	}

	@Override
	final protected void visit(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfe = node.getRepresentedControlFlowElement();
			lastVisitedCFEIsDead = node.isUnreachable();
			visit(cfe);
		}
	}

	@Override
	final protected void initializeModeInternal(Mode curMode, ControlFlowElement curContainer) {
		visitedEdgesInternal.clear();
		visitedEdges.clear();
		initializeMode(curMode, curContainer);
	}

	/**
	 * Called after {@link #initialize()} and before any visit-method is called.
	 *
	 * @param curMode
	 *            mode of succeeding calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	protected void initializeMode(Mode curMode, ControlFlowElement curContainer) {
		// overwrite me
	}

	/**
	 * Analog to {@link GraphVisitorInternal#visit(Node)}
	 *
	 * @param cfe
	 *            {@link ControlFlowElement} that is visited
	 */
	protected void visit(ControlFlowElement cfe) {
		// overwrite me
	}

	/**
	 * Analog to {@link GraphVisitorInternal#visit(Node, Node, ControlFlowEdge)}
	 *
	 * @param lastCFE
	 *            {@link ControlFlowElement} that was visited before
	 * @param nextCFE
	 *            {@link ControlFlowElement} that is visited next
	 * @param edge
	 *            traversed edge that targets nextCFE. Does not necessarily start at nextCFE
	 */
	@Deprecated
	protected void visit(ControlFlowElement lastCFE, ControlFlowElement nextCFE, FlowEdge edge) {
		// overwrite me
	}

	/** @return true iff the last visited {@link ControlFlowElement} was not dead. */
	final public boolean isLiveCFE() {
		return !lastVisitedCFEIsDead;
	}

	/** @return true iff the last visited {@link ControlFlowElement} was dead. */
	final public boolean isDeadCFE() {
		return lastVisitedCFEIsDead;
	}

}
