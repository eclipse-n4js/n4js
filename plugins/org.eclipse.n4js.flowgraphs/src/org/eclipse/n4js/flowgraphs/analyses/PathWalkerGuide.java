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

import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link PathWalkerGuide} traverses the internal control flow graph and maps it to the {@link ControlFlowElement}s
 * used in {@link IPathWalker}. The {@link PathWalkerGuide} is triggered from {@link Path}.
 */
public class PathWalkerGuide implements IPathWalkerInternal {
	final IPathWalker walker;
	final TreeSet<ControlFlowType> cfTypes = new TreeSet<>();
	private Node lastVisitedNode = null;

	/** Constructor */
	public PathWalkerGuide(IPathWalker walker) {
		this.walker = walker;
	}

	@Override
	public void visitNode(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfeEnd = node.getRepresentedControlFlowElement();
			if (lastVisitedNode != null) {
				ControlFlowElement cfeStart = lastVisitedNode.getRepresentedControlFlowElement();
				FlowEdge edge = new FlowEdge(cfeStart, cfeEnd, cfTypes);
				walker.visit(edge);
			}

			cfTypes.clear();
			walker.visit(cfeEnd);
			lastVisitedNode = node;
		}
	}

	@Override
	public void visitEdge(Node start, Node end, ControlFlowEdge edge) {
		cfTypes.add(edge.cfType);
	}

	@Override
	public void init() {
		// nothing to do
	}

	@Override
	public void finish() {
		// nothing to do
	}

}
