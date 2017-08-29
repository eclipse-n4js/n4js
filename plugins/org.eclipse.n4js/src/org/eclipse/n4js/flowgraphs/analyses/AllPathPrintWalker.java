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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.analyses.AllPathPrintWalker.AllPathPrintPredicate.AllPathPrintPath;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.ActivatedPathPredicateInternal.ActivePathInternal;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
@SuppressWarnings("javadoc")
public class AllPathPrintWalker extends GraphWalker2 {
	final ControlFlowElement startElement;

	public AllPathPrintWalker(ControlFlowElement startElement) {
		super(Direction.Forward);
		this.startElement = startElement;
	}

	@Override
	protected void init2() {
		if (startElement == null) {
			super.requestActivation(new AllPathPrintPredicate());
		}
	}

	@Override
	protected void terminate() {
		// System.out.println("AllPathPrintWalker finished.");
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		// System.out.println("Walker at: " + FGUtils.getTextLabel(cfe) + ", " + getCurrentDirection());
		if (startElement != null && startElement == cfe && getActivatedPredicateCount() == 0) {
			super.requestActivation(new AllPathPrintPredicate());
		}
	}

	@Override
	protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
	}

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

	class AllPathPrintPredicate extends ActivatedPathPredicate2 {

		AllPathPrintPredicate() {
			super(PredicateType.ForAllPaths);
		}

		@Override
		protected AllPathPrintPath first() {
			return new AllPathPrintPath("");
		}

		class AllPathPrintPath extends ActivePath2 {
			String currString = "";

			AllPathPrintPath(String initString) {
				this.currString = initString;
			}

			@Override
			protected void init() {
				// System.out.println(this.hashCode() + ">> New Path!");
			}

			@Override
			protected void visit(ControlFlowElement cfe) {
				currString += FGUtils.getTextLabel(cfe);
			}

			@Override
			protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes) {
				currString += " -> ";
			}

			@Override
			protected AllPathPrintPath fork2() {
				// System.out.println(this.hashCode() + ">> Fork with: " + currString);
				return new AllPathPrintPath(currString);
			}

			@Override
			protected void terminate() {
				// System.out.println(this.hashCode() + ">> Terminate: " + currString);
			}

		}
	}

}
