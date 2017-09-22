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

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal;
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
	private final List<PathExplorerInternal> failedExplorers = new LinkedList<>();
	private final List<PathExplorerInternal> passedExplorers = new LinkedList<>();

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
	abstract protected void initAll();

	/**
	 * Called after {@link #initAll()} and before any visit-method is called.
	 *
	 * @param curMode
	 *            mode of succeeding calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of succeeding calls to visit-methods
	 */
	abstract protected void initInternal(Mode curMode, ControlFlowElement curContainer);

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
	 * Called before {@link #terminateAll()} and after any visit-method is called.
	 *
	 * @param curMode
	 *            mode of previous calls to visit-methods
	 * @param curContainer
	 *            containing {@link ControlFlowElement} of previous calls to visit-methods
	 */
	abstract protected void terminate(Mode curMode, ControlFlowElement curContainer);

	/** Called at last */
	abstract protected void terminateAll();

	/////////////////////// Methods called from {@link GraphWalkerGuideInternal} ///////////////////////

	/** Only called from {@link GraphVisitorGuideInternal}. Sets the reference to {@link N4JSFlowAnalyzer} singleton. */
	final void setFlowAnalyses(N4JSFlowAnalyzer flowAnalyzer) {
		this.flowAnalyzer = flowAnalyzer;
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #initInternal(Mode, ControlFlowElement)}.
	 */
	final void callInitInternal() {
		if (activeMode) {
			initInternal(getCurrentMode(), null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #initAll()}. */
	final void callInitAll() {
		initAll();
	}

	/**
	 * Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminate(Mode, ControlFlowElement)}.
	 */
	final void callTerminate() {
		if (activeMode) {
			terminate(getCurrentMode(), null);
		}
	}

	/** Only called from {@link GraphVisitorGuideInternal}. Delegates to {@link #terminateAll()}. */
	final void callTerminateAll() {
		terminateAll();
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

	final List<PathWalkerInternal> activate() {
		List<PathWalkerInternal> activatedPaths = new LinkedList<>();
		for (PathExplorerInternal app : activationRequests) {
			PathWalkerInternal activePath = app.firstPath();
			app.activePaths.add(activePath);
			app.allPaths.add(activePath);
			activePath.init();
			activatedPaths.add(activePath);
		}
		activatedExplorers.addAll(activationRequests);
		activeExplorers.addAll(activationRequests);
		activationRequests.clear();
		return activatedPaths;
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
		return passedExplorers;
	}

	/** @return all failed {@link PathExplorerInternal}s */
	final public List<?> getFailed() {
		return failedExplorers;
	}

	/** The {@link Quantor} defines under which condition a {@link PathExplorerInternal} fails or passes. */
	public enum Quantor {
		/** No specific condition. */
		None,
		/** The {@link PathExplorerInternal} passes iff all paths pass. */
		ForAllPaths,
		/** The {@link PathExplorerInternal} passes if at least one path passes. */
		AtLeastOnePath
	}

	/**
	 * An {@link PathExplorerInternal} is created and spawned from a {@link GraphVisitorInternal} on specific
	 * preconditions. It follows all paths beginning from the location of activation. Its initial path is forked using
	 * the method {@link #firstPath()}. Subsequent paths are forked from the initial paths. The
	 * {@link PathExplorerInternal} is deactivated in case it has no active paths anymore. The final state of a
	 * {@link PathExplorerInternal} can be either <i>Passed</i> or <i>Failed</i>.
	 * <p/>
	 * The life cycle of a {@link PathExplorerInternal}:
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
		/** Quantor, specified in constructor */
		protected final Quantor quantor;
		/** Default verdict, specified in constructor */
		protected final boolean passAsDefault;

		/**
		 * Constructor
		 * <p>
		 * The {@link PathExplorerInternal} will pass as default in case {@link PathWalkerInternal#fail()} is never
		 * called on any of its active paths.
		 *
		 * @param quantor
		 *            defines fail/pass condition
		 */
		protected PathExplorerInternal(Quantor quantor) {
			this(quantor, true);
		}

		/**
		 * Constructor
		 *
		 * @param quantor
		 *            defines fail/pass condition
		 * @param passAsDefault
		 *            iff true, the {@link PathExplorerInternal} will pass as default
		 */
		protected PathExplorerInternal(Quantor quantor, boolean passAsDefault) {
			this.quantor = quantor;
			this.passAsDefault = passAsDefault;
		}

		/////////////////////// Abstract Methods ///////////////////////

		/** Spawns the first path. Called right after this {@link PathExplorerInternal} gets activated. */
		abstract protected PathWalkerInternal firstPath();

		/////////////////////// Service Methods for inherited classes ///////////////////////

		/** @return true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Passed</i> */
		final public boolean isPassed() {
			return passedExplorers.contains(this);
		}

		/** @return true, iff the {@link PathExplorerInternal} is terminated and has verdict <i>Failed</i> */
		final public boolean isFailed() {
			return failedExplorers.contains(this);
		}

		/** @return all paths no matter if they are active or not, or passed or failed. */
		final public List<PathWalkerInternal> getAllPaths() {
			return allPaths;
		}

		/** Deactivates all active paths and hence this {@link PathExplorerInternal}. */
		final protected void deactivateAll() {
			while (!activePaths.isEmpty()) {
				PathWalkerInternal aPath = activePaths.iterator().next();
				aPath.deactivate();
			}
			checkExplorerDeactivation();
		}

		private void checkExplorerDeactivation() {
			if (activePaths.isEmpty()) {
				activeExplorers.remove(this);

				boolean somePassed = !passedPaths.isEmpty();
				boolean someFailed = !failedPaths.isEmpty();

				boolean explorerPassed = false;
				explorerPassed |= somePassed && quantor == Quantor.AtLeastOnePath;
				explorerPassed |= somePassed && !someFailed && quantor == Quantor.ForAllPaths;
				explorerPassed |= !somePassed && !someFailed && passAsDefault;
				if (explorerPassed) {
					passedExplorers.add(this);
				} else {
					failedExplorers.add(this);
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
				if (activeMode) {
					visit(node);
				}
			}

			/**
			 * Only called from {@link GraphVisitorGuideInternal}. Delegates to
			 * {@link PathWalkerInternal#visit(Node, Node, ControlFlowEdge)}.
			 */
			final void callVisit(Node start, Node end, ControlFlowEdge edge) {
				if (activeMode) {
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
				if (quantor == Quantor.AtLeastOnePath) {
					deactivateAll();
				} else {
					deactivate();
				}
			}

			/** Sets the verdict of this path to <i>Failed</i>. */
			final public void fail() {
				failedPaths.add(this);
				if (quantor == Quantor.ForAllPaths) {
					deactivateAll();
				} else {
					deactivate();
				}
			}

			/** Deactivates this path without setting the verdict. */
			final public void deactivate() {
				activePaths.remove(this);
				terminate();
				checkExplorerDeactivation();
			}

			/** @return true, iff this path is active. */
			final public boolean isActive() {
				return activePaths.contains(this);
			}

		}
	}
}
