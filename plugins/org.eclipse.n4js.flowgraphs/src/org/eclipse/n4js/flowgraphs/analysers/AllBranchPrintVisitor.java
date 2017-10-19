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
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analyses.GraphExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalker;
import org.eclipse.n4js.flowgraphs.analyses.BranchWalkerInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllBranchPrintVisitor extends GraphVisitor {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 */
	public AllBranchPrintVisitor() {
		this(null, null, Mode.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllBranchPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 */
	public AllBranchPrintVisitor(ControlFlowElement container) {
		this(container, null, Mode.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllBranchPrintVisitor} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 */
	public AllBranchPrintVisitor(ControlFlowElement container, ControlFlowElement startElement) {
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
	public AllBranchPrintVisitor(ControlFlowElement container, ControlFlowElement startElement, Mode direction) {
		super(container, direction);
		this.startElement = startElement;
	}

	@Override
	protected void initializeMode(Mode curDirection, ControlFlowElement curContainer) {
		if (startElement == null) {
			super.requestActivation(new AllBranchPrintExplorer());
		}
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (startElement != null && startElement == cfe && getActivatedExplorerCount() == 0) {
			super.requestActivation(new AllBranchPrintExplorer());
		}
	}

	/** @return all found paths as strings */
	public List<String> getPathStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (GraphExplorerInternal app : getActivatedExplorers()) {
			for (BranchWalkerInternal ap : app.getAllPaths()) {
				AllBranchPrintWalker printPath = (AllBranchPrintWalker) ap;
				pathStrings.add(printPath.branchString);
				// pathStrings.add(" ");
			}
		}
		return pathStrings;
	}

	static class AllBranchPrintExplorer extends GraphExplorer {

		AllBranchPrintExplorer() {
			super(Quantor.ForAllPaths);
		}

		@Override
		protected AllBranchPrintWalker firstPathWalker() {
			return new AllBranchPrintWalker();
		}
	}

	static class AllBranchPrintWalker extends BranchWalker {
		private String branchString = "";

		AllBranchPrintWalker() {
		}

		@Override
		protected void visit(ControlFlowElement cfe) {
			branchString += FGUtils.getSourceText(cfe);
		}

		@Override
		protected void visit(FlowEdge edge) {
			branchString += " -> ";
		}

		@Override
		protected AllBranchPrintWalker forkPath() {
			return new AllBranchPrintWalker();
		}
	}

}
