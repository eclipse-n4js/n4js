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
import org.eclipse.n4js.flowgraphs.analysers.AllPathPrintWalker.AllPathPrintPredicate.AllPathPrintPath;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.PathExplorerInternal.PathWalkerInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllPathPrintWalker extends GraphVisitor {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllPathPrintWalker} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 */
	public AllPathPrintWalker(ControlFlowElement container) {
		this(container, null, Direction.Forward);
	}

	/**
	 * Constructor.
	 * <p>
	 * Creates a {@link AllPathPrintWalker} in direction {@literal Direction.Forward}.
	 *
	 * @param container
	 *            the container of which all paths are computed. Must not be null.
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 */
	public AllPathPrintWalker(ControlFlowElement container, ControlFlowElement startElement) {
		this(container, startElement, Direction.Forward);
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
	public AllPathPrintWalker(ControlFlowElement container, ControlFlowElement startElement, Direction direction) {
		super(container, direction);
		this.startElement = startElement;
	}

	@Override
	protected void initAll() {
		// nothing to do
	}

	@Override
	protected void init(Direction curDirection, ControlFlowElement curContainer) {
		if (startElement == null) {
			super.requestActivation(new AllPathPrintPredicate());
		}
	}

	@Override
	protected void terminate(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
	}

	@Override
	protected void terminateAll() {
		// nothing to do
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		if (startElement != null && startElement == cfe && getActivatedPredicateCount() == 0) {
			super.requestActivation(new AllPathPrintPredicate());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
		// nothing to do
	}

	/** @returns all found paths as strings */
	public List<String> getPathStrings() {
		List<String> pathStrings = new LinkedList<>();
		for (PathExplorerInternal app : getActivatedPredicates()) {
			for (PathWalkerInternal ap : app.getAllPaths()) {
				AllPathPrintPath printPath = (AllPathPrintPath) ap;
				pathStrings.add(printPath.currString);
			}
		}
		return pathStrings;
	}

	class AllPathPrintPredicate extends PathExplorer {

		AllPathPrintPredicate() {
			super(PredicateType.ForAllPaths);
		}

		@Override
		protected AllPathPrintPath firstPath() {
			return new AllPathPrintPath("");
		}

		class AllPathPrintPath extends PathWalker {
			String currString = "";

			AllPathPrintPath(String initString) {
				this.currString = initString;
			}

			@Override
			protected void init() {
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
			protected AllPathPrintPath forkPath() {
				return new AllPathPrintPath(currString);
			}

			@Override
			protected void terminate() {
				// nothing to do
			}

		}
	}

}
