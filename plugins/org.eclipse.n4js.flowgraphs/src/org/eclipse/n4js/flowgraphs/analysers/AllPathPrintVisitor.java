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
import org.eclipse.n4js.flowgraphs.analyses.PathExplorer;
import org.eclipse.n4js.flowgraphs.analyses.PathExplorerInternal;
import org.eclipse.n4js.flowgraphs.analyses.PathWalker;
import org.eclipse.n4js.flowgraphs.analyses.PathWalkerInternal;
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
		for (PathExplorerInternal app : getActivatedExplorers()) {
			for (PathWalkerInternal ap : app.getAllPaths()) {
				AllPathPrintWalker printPath = (AllPathPrintWalker) ap;
				pathStrings.add(printPath.currString);
				// pathStrings.add(" ");
			}
		}
		return pathStrings;
	}

	static class AllPathPrintExplorer extends PathExplorer {

		AllPathPrintExplorer() {
			super(Quantor.ForAllPaths);
		}

		@Override
		protected AllPathPrintWalker firstPathWalker() {
			return new AllPathPrintWalker("");
		}
	}

	static class AllPathPrintWalker extends PathWalker {
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

		String getPredString() {
			String s = "";
			AllPathPrintWalker pathPred = (AllPathPrintWalker) getPathPredecessor();
			if (pathPred != null)
				s += pathPred.getPredString();
			s += currString;
			return s;
		}

		String getSuccString() {
			String s = currString;
			AllPathPrintWalker pathSucc = (AllPathPrintWalker) getPathPredecessor();
			if (pathSucc != null)
				s += pathSucc.getSuccString();
			return s;
		}

		String getCompleteString() {
			AllPathPrintWalker pathPred = (AllPathPrintWalker) getPathPredecessor();
			AllPathPrintWalker pathSucc = (AllPathPrintWalker) getPathPredecessor();
			String s = "";
			if (pathPred != null)
				s += pathPred.getPredString();
			s += currString;
			if (pathSucc != null)
				s += pathSucc.getSuccString();
			return s;
		}
	}
}
