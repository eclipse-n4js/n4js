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
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllPathPrintVisitor extends GraphVisitor {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 */
	public AllPathPrintVisitor() {
		this(null, null, Mode.Forward);
	}

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
	protected void initializeMode(Mode curDirection, ControlFlowElement curContainer) {
		if (startElement == null) {
			super.requestActivation(new AllPathPrintExplorer());
		}
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (startElement != null && startElement == cfe && getActivatedExplorerCount() == 0) {
			super.requestActivation(new AllPathPrintExplorer());
		}
	}

	/** @return all found paths as strings */
	public List<String> getPathStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (GraphExplorerInternal app : getActivatedExplorers()) {
			for (BranchWalkerInternal ap : app.getAllBranches()) {
				AllPathPrintWalker printPath = (AllPathPrintWalker) ap;
				pathStrings.add(printPath.currString);
				// pathStrings.add(" ");
			}
		}
		return pathStrings;
	}

	static class AllPathPrintExplorer extends GraphExplorer {

		AllPathPrintExplorer() {
			super(Quantor.ForAllBranches);
		}

		@Override
		protected AllPathPrintWalker firstBranchWalker() {
			return new AllPathPrintWalker("");
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return new AllPathPrintWalker("");
		}
	}

	static class AllPathPrintWalker extends BranchWalker {
		private String currString = "";

		AllPathPrintWalker(String currString) {
			this.currString = currString;
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
	}

}
