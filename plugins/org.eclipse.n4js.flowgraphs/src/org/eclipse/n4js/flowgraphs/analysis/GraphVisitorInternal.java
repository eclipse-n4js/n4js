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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * This class is the counterpart of {@link GraphVisitorGuideInternal} and the basis for the {@link GraphVisitor}.
 */
abstract public class GraphVisitorInternal implements FlowAnalyser {
	/** Reference to {@link N4JSFlowAnalyser}. Set before performing the analyses. */
	protected N4JSFlowAnalyser flowAnalyzer;
	/** Container, specified in constructor */
	protected final ControlFlowElement container;
	/** Modes, specified in constructor */
	protected final TraverseDirection direction;

	private final List<GraphExplorerInternal> activationRequests = new LinkedList<>();
	private final List<GraphExplorerInternal> activatedExplorers = new LinkedList<>();
	private final List<GraphExplorerInternal> activeExplorers = new LinkedList<>();

	private ControlFlowElement currentContainer;
	private boolean activeMode = false;
	private boolean lastVisitedNodeIsDead = false;

	/**
	 * Constructor.
	 *
	 * @param direction
	 *            sets the direction for this instance. Default direction is {@literal Mode.Forward}.
	 */
	protected GraphVisitorInternal(TraverseDirection direction) {
		this(null, direction);
	}

	/**
	 * Constructor.
	 *
	 * @param container
	 *            sets the containing {@link ControlFlowElement} for this instance. Iff the given container is
	 *            {@code null}, this {@link GraphVisitorInternal} is applied on all containers.
	 * @param direction
	 *            sets the direction for this instance. Default direction is {@literal Mode.Forward}.
	 */
	protected GraphVisitorInternal(ControlFlowElement container, TraverseDirection direction) {
		if (direction == null) {
			direction = TraverseDirection.Forward;
		}
		this.direction = direction;
		this.container = container;
	}

	/////////////////////// Abstract Methods ///////////////////////

	/** Called before any other method is called. */
	protected void initialize() {
		// overwrite me
	}

	/**
	 * Called after {@link #initialize()} and before any visit-method is called.
	 *
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	protected void initializeContainerInternal(ControlFlowElement curContainer) {
		// overwrite me
	}

	/**
	 * Called for each node that is reachable w.r.t to the current direction and the current container.
	 * <p>
	 * Note that the order of nodes is arbitrary.
	 *
	 * @param node
	 *            the node that is visits
	 */
	protected void visit(Node node) {
		// overwrite me
	}

	/**
	 * Called for each edge that is reachable w.r.t to the current direction and the current container.
	 * <p>
	 * Note that the order of edges is arbitrary.
	 *
	 * @param lastVisitNode
	 *            nodes that were visited before
	 * @param currentNode
	 *            end node of the edge in terms of current direction
	 * @param edge
	 *            traversed edge
	 */
	protected void visit(Node lastVisitNode, Node currentNode, ControlFlowEdge edge) {
		// overwrite me
	}

	/**
	 * Called before {@link #terminate()} and after any visit-method is called.
	 *
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of previous calls to visit-methods
	 */
	protected void terminateContainer(ControlFlowElement curContainer) {
		// overwrite me
	}

	/** Called at last */
	protected void terminate() {
		// overwrite me
	}

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/** Only called from {@link GraphVisitorGuideInternal}. Sets the reference to {@link N4JSFlowAnalyser} singleton. */
	final void setFlowAnalyses(N4JSFlowAnalyser flowAnalyzer) {
		this.flowAnalyzer = flowAnalyzer;
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #initialize()}. */
	final void callInitialize() {
		initialize();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link #initializeContainerInternal(ControlFlowElement)}.
	 */
	final void callInitializeContainerInternal() {
		if (activeMode) {
			initializeContainerInternal(getCurrentContainer());
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminateContainer(ControlFlowElement)}.
	 */
	final void callTerminateContainer() {
		if (activeMode) {
			terminateContainer(null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminate()}. */
	final void callTerminate() {
		terminate();
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link GraphVisitorInternal#visit(Node)}. */
	final void callVisit(Node node) {
		if (activeMode) {
			this.lastVisitedNodeIsDead = node.isUnreachable();
			visit(node);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link GraphVisitorInternal#visit(Node, Node, ControlFlowEdge)}.
	 */
	final void callVisit(Node lastVisitNode, Node end, ControlFlowEdge edge) {
		if (activeMode) {
			this.lastVisitedNodeIsDead = end.isUnreachable();
			visit(lastVisitNode, end, edge);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Sets {@link #currentContainer}.
	 */
	final void setContainer(ControlFlowElement curContainer) {
		this.currentContainer = curContainer;
		checkActive();
	}

	private void checkActive() {
		activeMode = (container == null || container == currentContainer);
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Activates the {@link GraphExplorerInternal} that wait for
	 * activation.
	 */
	final List<BranchWalkerInternal> activateRequestedExplorers() {
		List<BranchWalkerInternal> activatedPaths = new LinkedList<>();
		for (GraphExplorerInternal app : activationRequests) {
			BranchWalkerInternal activePath = app.callFirstBranchWalker(this);
			activatedPaths.add(activePath);
		}
		activatedExplorers.addAll(activationRequests);
		activeExplorers.addAll(activationRequests);
		activationRequests.clear();
		return activatedPaths;
	}

	/** Called from {@link GraphExplorerInternal} when the calling {@link GraphExplorerInternal} is finished. */
	final void deactivateGraphExplorer(GraphExplorerInternal pathExplorerInternal) {
		activeExplorers.remove(pathExplorerInternal);
	}

	/////////////////////// Service Methods for inherited classes ///////////////////////

	/**
	 * Call this method to request the spawn of a new {@link GraphExplorerInternal}. The new
	 * {@link GraphExplorerInternal} is spawned after the current visit-method is finished. If not called from a
	 * visit-method, the new {@link GraphExplorerInternal} is spawned after the next visit-method is finished.
	 */
	final public void requestActivation(GraphExplorerInternal app) {
		activationRequests.add(app);
	}

	/** @return all activated {@link GraphExplorerInternal}s */
	final public List<GraphExplorerInternal> getActivatedExplorers() {
		return activatedExplorers;
	}

	/** @return all active {@link GraphExplorerInternal}s */
	final public List<GraphExplorerInternal> getActiveExplorers() {
		return activeExplorers;
	}

	/** @return the number of activated {@link GraphExplorerInternal}s */
	final public int getActivatedExplorerCount() {
		return getActivatedExplorers().size();
	}

	/** @return the number of active {@link GraphExplorerInternal}s */
	final public int getActiveExplorerCount() {
		return getActiveExplorers().size();
	}

	/** @return the direction */
	final public TraverseDirection getDirection() {
		return direction;
	}

	/** @return true iff the last visited node was not dead. */
	final public boolean isLiveCode() {
		return !lastVisitedNodeIsDead;
	}

	/** @return true iff the last visited node was dead. */
	final public boolean isDeadCodeNode() {
		return lastVisitedNodeIsDead;
	}

	/** @return the current container */
	final public ControlFlowElement getCurrentContainer() {
		return currentContainer;
	}

	/** @return all passed {@link GraphExplorerInternal}s */
	final public List<GraphExplorerInternal> getPassed() {
		List<GraphExplorerInternal> passedPEI = new LinkedList<>();
		for (GraphExplorerInternal pei : activatedExplorers) {
			if (pei.isPassed()) {
				passedPEI.add(pei);
			}
		}
		return passedPEI;
	}

	/** @return all failed {@link GraphExplorerInternal}s */
	final public List<GraphExplorerInternal> getFailed() {
		List<GraphExplorerInternal> failedPEI = new LinkedList<>();
		for (GraphExplorerInternal pei : activatedExplorers) {
			if (pei.isFailed()) {
				failedPEI.add(pei);
			}
		}
		return failedPEI;
	}

	/** @return reference to the {@link SymbolFactory} */
	final public SymbolFactory getSymbolFactory() {
		return flowAnalyzer.getSymbolFactory();
	}

}
