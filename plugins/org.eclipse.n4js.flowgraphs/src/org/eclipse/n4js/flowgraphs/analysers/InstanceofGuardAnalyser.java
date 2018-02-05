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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructure;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructureFactory;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 */
public class InstanceofGuardAnalyser extends GraphVisitorInternal {
	Set<ControlFlowElement> allLiveNodes = new HashSet<>();
	Set<ControlFlowElement> allDeadNodes = new HashSet<>();

	/** Constructor */
	public InstanceofGuardAnalyser() {
		super(TraverseDirection.Forward);
	}

	static class InstanceofBranchWalker extends BranchWalkerInternal {

		@Override
		protected BranchWalkerInternal fork() {
			return null;
		}

		@Override
		protected void visit(Node lastVisitNode, Node end, ControlFlowEdge edge) {
			GuardStructure guardStructure = GuardStructureFactory.create(edge);

		}

	}
}
