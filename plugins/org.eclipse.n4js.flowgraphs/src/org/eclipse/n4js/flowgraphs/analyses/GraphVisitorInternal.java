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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorerInternal.PathWalkerInternal;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * This class is the counterpart of {@link GraphVisitorGuideInternal} and the basis for the {@link GraphVisitor}.
 */
abstract public class GraphVisitorInternal {
	/** Reference to {@link N4JSFlowAnalyzer}. Set before performing the analyses. */
	protected N4JSFlowAnalyzer flowAnalyzer;
	/** Container, specified in constructor */
	protected final ControlFlowElement container;
	/** Modes, specified in constructor */
	protected final Mode[] modes;

	private final List<PathExplorerInternal> activationRequests = new LinkedList<>();
	private final List<PathExplorerInternal> activatedExplorers = new LinkedList<>();
	private final List<PathExplorerInternal> activeExplorers = new LinkedList<>();

	private ControlFlowElement currentContainer;
	private Mode currentMode;
	private boolean activeMode = false;

	/** Specifies the traverse mode of a {@link GraphVisitorInternal} instance. */
	public enum Mode {
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
	 * @param modes
	 *            sets the {@link Mode}s for this instance. Default mode is {@literal Mode.Forward} if no mode is given.
	 */
	protected GraphVisitorInternal(Mode... modes) {
		this(null, modes);
	}

	/**
	 * Constructor.
	 *
	 * @param container
	 *            sets the containing {@link ControlFlowElement} for this instance. Iff the given container is
	 *            {@code null}, this {@link GraphVisitorInternal} is applied on all containers.
	 * @param modes
	 *            sets the modes for this instance. Default modes are {@literal Mode.Forward} and
	 *            {@literal Mode.CatchBlocks} if no mode is given.
	 */
	protected GraphVisitorInternal(ControlFlowElement container, Mode... modes) {
		if (modes.length == 0) {
			modes = new Mode[] { Mode.Forward, Mode.CatchBlocks };
		}
		this.modes = modes;
		this.container = container;
	}

	/////////////////////// Abstract Methods ///////////////////////

	/** Called before any other method is called. */
	abstract protected void initialize();

	/**
	 * Called after {@link #initialize()} and before any visit-method is called.
	 *
	 * @param curMode
	 *            mode of succeeding calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	abstract protected void initializeModeInternal(Mode curMode, ControlFlowElement curContainer);

	/**
	 * Called for each node that is reachable w.r.t to the current mode and the current container.
	 * <p>
	 * Note that the order of nodes is arbitrary.
	 */
	abstract protected void visit(Node node);

	/**
	 * Called for each edge that is reachable w.r.t to the current mode and the current container.
	 * <p>
	 * Note that the order of edges is arbitrary.
	 *
	 * @param lastNode
	 *            node that was visited before
	 * @param currentNode
	 *            end node of the edge in terms of current mode
	 * @param edge
	 *            traversed edge
	 */
	abstract protected void visit(Node lastNode, Node currentNode, ControlFlowEdge edge);

	/**
	 * Called before {@link #terminate()} and after any visit-method is called.
	 *
	 * @param curMode
	 *            mode of previous calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of previous calls to visit-methods
	 */
	abstract protected void terminateMode(Mode curMode, ControlFlowElement curContainer);

	/** Called at last */
	abstract protected void terminate();

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/** Only called from {@link GraphVisitorGuideInternal}. Sets the reference to {@link N4JSFlowAnalyzer} singleton. */
	final void setFlowAnalyses(N4JSFlowAnalyzer flowAnalyzer) {
		this.flowAnalyzer = flowAnalyzer;
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #initialize()}. */
	final void callInitialize() {
		initialize();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link #initializeModeInternal(Mode, ControlFlowElement)}.
	 */
	final void callInitializeModeInternal() {
		if (activeMode) {
			initializeModeInternal(getCurrentMode(), null);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link #terminateMode(Mode, ControlFlowElement)}.
	 */
	final void callTerminateMode() {
		if (activeMode) {
			terminateMode(getCurrentMode(), null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminate()}. */
	final void callTerminate() {
		terminate();
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link GraphVisitorInternal#visit(Node)}. */
	final void callVisit(Node cfe) {
		if (activeMode) {
			visit(cfe);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
	 * {@link GraphVisitorInternal#visit(Node,Node,ControlFlowEdge)}.
	 */
	final void callVisit(Node start, Node end, ControlFlowEdge edge) {
		if (activeMode) {
			visit(start, end, edge);
		}
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Sets {@link #currentMode} and {@link #currentContainer}.
	 */
	final void setContainerAndMode(ControlFlowElement curContainer, Mode curMode) {
		this.currentContainer = curContainer;
		this.currentMode = curMode;
		checkActive();
	}

	private void checkActive() {
		activeMode = false;
		boolean containerActive = (container == null || container == currentContainer);

		if (containerActive) {
			for (Mode dir : modes) {
				if (dir == currentMode) {
					activeMode = true;
					break;
				}
			}
		}
	}

	/** Activates the {@link PathExplorerInternal} that wait for activation. */
	final List<PathWalkerInternal> activateRequestedPathExplorers() {
		List<PathWalkerInternal> activatedPaths = new LinkedList<>();
		for (PathExplorerInternal app : activationRequests) {
			PathWalkerInternal activePath = app.callFirstPathWalker(this);
			activatedPaths.add(activePath);
		}
		activatedExplorers.addAll(activationRequests);
		activeExplorers.addAll(activationRequests);
		activationRequests.clear();
		return activatedPaths;
	}

	/** Called from {@link PathExplorerInternal} when the calling {@link PathExplorerInternal} is finished. */
	final void deactivatePathExplorer(PathExplorerInternal pathExplorerInternal) {
		activeExplorers.remove(pathExplorerInternal);
	}
	/////////////////////// Service Methods for inherited classes ///////////////////////

	/**
	 * Call this method to request the spawn of a new {@link PathExplorerInternal}. The new {@link PathExplorerInternal}
	 * is spawned after the current visit-method is finished. If not called from a visit-method, the new
	 * {@link PathExplorerInternal} is spawned after the next visit-method is finished.
	 */
	final public void requestActivation(PathExplorerInternal app) {
		activationRequests.add(app);
	}

	/** @return all activated {@link PathExplorerInternal}s */
	final public List<PathExplorerInternal> getActivatedExplorers() {
		return activatedExplorers;
	}

	/** @return all active {@link PathExplorerInternal}s */
	final public List<PathExplorerInternal> getActiveExplorers() {
		return activeExplorers;
	}

	/** @return the number of activated {@link PathExplorerInternal}s */
	final public int getActivatedExplorerCount() {
		return getActivatedExplorers().size();
	}

	/** @return the number of active {@link PathExplorerInternal}s */
	final public int getActiveExplorerCount() {
		return getActiveExplorers().size();
	}

	/** @return the current mode */
	final public Mode getCurrentMode() {
		return currentMode;
	}

	/** @return the current container */
	final public ControlFlowElement getCurrentContainer() {
		return currentContainer;
	}

	/** @return all passed {@link PathExplorerInternal}s */
	final public List<?> getPassed() {
		List<PathExplorerInternal> passedPEI = new LinkedList<>();
		for (PathExplorerInternal pei : activatedExplorers) {
			if (pei.isPassed()) {
				passedPEI.add(pei);
			}
		}
		return passedPEI;
	}

	/** @return all failed {@link PathExplorerInternal}s */
	final public List<PathExplorerInternal> getFailed() {
		List<PathExplorerInternal> failedPEI = new LinkedList<>();
		for (PathExplorerInternal pei : activatedExplorers) {
			if (pei.isFailed()) {
				failedPEI.add(pei);
			}
		}
		return failedPEI;
	}

}
