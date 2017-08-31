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
package org.eclipse.n4js.xpect.methods.flowgraphs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalker;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.ActivatedPathPredicateInternal.ActivePathInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.xpect.methods.flowgraphs.AllPathPrintWalker.AllPathPrintPredicate.AllPathPrintPath;

/**
 * Finds all control flow paths beginning from a given start element.
 */
public class AllPathPrintWalker extends GraphWalker {
	final ControlFlowElement startElement;

	/**
	 * Constructor.
	 *
	 * @param startElement
	 *            if not null, all paths are found beginning at the startElement. Otherwise, all paths are found
	 *            beginning from the first element of one of the containers in the script.
	 */
	public AllPathPrintWalker(ControlFlowElement startElement) {
		super(Direction.Forward);
		this.startElement = startElement;
	}

	@Override
	protected void initAll() {
		if (startElement == null) {
			super.requestActivation(new AllPathPrintPredicate());
		}
	}

	@Override
	protected void init(Direction curDirection, ControlFlowElement curContainer) {
		// nothing to do
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
		for (ActivatedPathPredicateInternal app : getActivatedPredicates()) {
			for (ActivePathInternal ap : app.getAllPaths()) {
				AllPathPrintPath printPath = (AllPathPrintPath) ap;
				pathStrings.add(printPath.currString);
			}
		}
		return pathStrings;
	}

	class AllPathPrintPredicate extends ActivatedPathPredicate {

		AllPathPrintPredicate() {
			super(PredicateType.ForAllPaths);
		}

		@Override
		protected AllPathPrintPath firstPath() {
			return new AllPathPrintPath("");
		}

		class AllPathPrintPath extends ActivePath {
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
				currString += FGUtils.getTextLabel(cfe);
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, FlowEdge edge) {
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
