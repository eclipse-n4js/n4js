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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
@SuppressWarnings("javadoc")
abstract public class GraphWalker2 extends GraphWalkerInternal {
	final private Stack<ControlFlowEdge> edgeStack = new Stack<>();
	private Node lastNode;
	private ControlFlowElement lastCFE;

	/** Default direction is {@literal Direction.Forward} */
	protected GraphWalker2(Direction... directions) {
		super(directions);
	}

	@Override
	final protected void visit(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfe = node.getRepresentedControlFlowElement();
			visit(cfe);
		}
	}

	@Override
	final protected void visit(ControlFlowEdge edge) {
		// backtrack
		while (!edgeStack.isEmpty() && edgeStack.peek().end != edge.start) {
			edgeStack.pop();
		}

		// call visit
		if (edge.end instanceof RepresentingNode) {
			Set<ControlFlowType> edgeTypes = new HashSet<>();
			edgeTypes.add(edge.cfType);
			Node startNode = getStartNodeAndEdgeTypes(edgeTypes);
			if (startNode != null) {
				ControlFlowElement startCFE = startNode.getRepresentedControlFlowElement();
				ControlFlowElement endECFE = edge.end.getRepresentedControlFlowElement();
				visit(startCFE, endECFE, edgeTypes);
			}
		}

		// push new edge
		edgeStack.push(edge);
	}

	private Node getStartNodeAndEdgeTypes(Set<ControlFlowType> edgeTypes) {
		for (int i = edgeStack.size() - 1; i >= 0; i--) {
			ControlFlowEdge e = edgeStack.get(i);
			if (e.end instanceof RepresentingNode) {
				return e.end;
			}
			edgeTypes.add(e.cfType);
		}
		return null;
	}

	@Override
	final protected void init() {
		lastCFE = null;
		edgeStack.clear();
		init2();
	}

	abstract protected void init2();

	abstract protected void visit(ControlFlowElement cfe);

	abstract protected void visit(ControlFlowElement start, ControlFlowElement end, Set<ControlFlowType> cfTypes);

	@Override
	abstract protected void terminate();

	abstract public class ActivatedPathPredicate2 extends ActivatedPathPredicateInternal {

		protected ActivatedPathPredicate2(PredicateType predicateType) {
			super(predicateType);
		}

		@Override
		abstract protected ActivePath2 first();

		abstract public class ActivePath2 extends ActivePathInternal {
			ControlFlowElement pLastCFE;
			Set<ControlFlowType> pEdgeTypes = new HashSet<>();

			@Override
			final protected void visit(Node node) {
				if (node instanceof RepresentingNode) {
					ControlFlowElement cfe = node.getRepresentedControlFlowElement();
					if (pLastCFE != null) {
						visit(pLastCFE, cfe, pEdgeTypes);
						pEdgeTypes.clear();
					}
					visit(cfe);
					pLastCFE = cfe;
				}
			}

			@Override
			final protected void visit(ControlFlowEdge edge) {
				pEdgeTypes.add(edge.cfType);
			}

			@Override
			abstract protected void init();

			abstract protected void visit(ControlFlowElement cfe);

			abstract protected void visit(ControlFlowElement start, ControlFlowElement end,
					Set<ControlFlowType> cfTypes);

			abstract protected ActivePath2 fork2();

			@Override
			final protected ActivePath2 fork() {
				ActivePath2 ap2 = fork2();
				ap2.pLastCFE = pLastCFE;
				ap2.pEdgeTypes.addAll(pEdgeTypes);
				return ap2;
			}

			@Override
			abstract protected void terminate();

		}
	}
}
