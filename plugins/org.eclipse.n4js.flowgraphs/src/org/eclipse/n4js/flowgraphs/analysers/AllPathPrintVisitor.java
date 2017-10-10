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
package org.eclipse.n4js.flowgraphs.analysers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analysers.AllPathPrintVisitor.AllPathPrintExplorer.AllPathPrintWalker;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorer;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorerInternal.PathWalkerInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllPathPrintVisitor extends GraphVisitor {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllPathPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 */
	public AllPathPrintVisitor(ControlFlowElement container) {
		this(container, null, Mode.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllPathPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 */
	public AllPathPrintVisitor(ControlFlowElement container, ControlFlowElement startElement) {
		this(container, startElement, Mode.Forward);
	}

	/**
	 * Constructor.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 * @param direction
	 *            the direction of the paths. Use only with {@literal Direction.Forward} and
	 *            {@literal Direction.Backward}
	 */
	public AllPathPrintVisitor(ControlFlowElement container, ControlFlowElement startElement, Mode direction) {
		super(container, direction);
		this.startElement = startElement;
	}

	@Override
	protected void initialize() {
		// nothing to do
	}

	@Override
	protected void initializeMode(Mode curDirection, ControlFlowElement curContainer) {
		if (startElement == null) {
			super.requestActivation(new AllPathPrintExplorer());
		}
	}

	@Override
	protected void terminateMode(Mode curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminate() {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (startElement != null && startElement == cfe && getActivatedExplorerCount() == 0) {
			super.requestActivation(new AllPathPrintExplorer());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	/** @return all found paths as strings */
	public List<String> getPathStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (PathExplorerInternal app : getActivatedExplorers()) {
			for (PathWalkerInternal ap : app.getAllPaths()) {
				AllPathPrintWalker printPath = (AllPathPrintWalker) ap;
				pathStrings.add(printPath.currString);
			}
		}
		return pathStrings;
	}

	class AllPathPrintExplorer extends PathExplorer {

		AllPathPrintExplorer() {
			super(Quantor.ForAllPaths);
		}

		@Override
		protected AllPathPrintWalker firstPathWalker() {
			return new AllPathPrintWalker("");
		}

		class AllPathPrintWalker extends PathWalker {
			String currString = "";

			AllPathPrintWalker(String initString) {
				this.currString = initString;
			}

			@Override
			protected void initialize() {
				// nothing to do
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				currString += FGUtils.getSourceText(cfe);
			}

			@Override
			protected void visit(FlowEdge edge) {
				currString += " -> ";
			}

			@Override
			protected AllPathPrintWalker forkPath() {
				return new AllPathPrintWalker(currString);
			}

			@Override
			protected void terminate() {
				// nothing to do
			}

		}
	}

}
