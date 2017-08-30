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

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
@SuppressWarnings("javadoc")
public class AllNodesAndEdgesPrintWalker extends GraphWalker2 {
	final List<ControlFlowElement> allNodes = new LinkedList<>();
	final List<FlowEdge> allEdges = new LinkedList<>();

	public AllNodesAndEdgesPrintWalker(ControlFlowElement container) {
		super(container, Direction.Forward, Direction.Backward, Direction.Islands);
	}

	@Override
	protected void init2() {
	}

	@Override
	protected void init(Direction direction) {
	}

	@Override
	protected void terminate(Direction direction) {
	}

	@Override
	protected void terminate() {
	}

	@Override
	protected void visit(ControlFlowElement cfe) {
		allNodes.add(cfe);
	}

	@Override
	protected void visit(FlowEdge edge) {
		allEdges.add(edge);
	}

	public List<String> getAllNodeStrings() {
		List<String> nodeStrings = new LinkedList<>();
		for (ControlFlowElement node : allNodes) {
			nodeStrings.add(FGUtils.getTextLabel(node));
		}
		return nodeStrings;
	}

	public List<String> getAllEdgeStrings() {
		List<String> edgeStrings = new LinkedList<>();
		for (FlowEdge edge : allEdges) {
			edgeStrings.add(edge.toString());
		}
		return edgeStrings;
	}

}
