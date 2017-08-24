/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.factories.ListUtils;

public class EdgeUtils {

	public static void connectCF(ControlFlowable cf1, ControlFlowable cf2) {
		addEdgeCF(cf1.getExit(), cf2.getEntry());
	}

	public static void connectLC(ControlFlowable conditionNode, ControlFlowable whileBlock) {
		addEdgeCFLC(conditionNode.getExit(), whileBlock.getEntry());
	}

	static public ControlFlowEdge addEdgeCF(Node n1, Node n2) {
		return addEdgeCF(n1, n2, ControlFlowType.Successor);
	}

	public static ControlFlowEdge addEdgeCFLC(Node n1, Node n2) {
		return addEdgeCF(n1, n2, ControlFlowType.Loop);
	}

	static public ControlFlowEdge addEdgeCF(Node n1, Node n2, ControlFlowType cfType) {
		assert (n1 != n2) : "CF-Edge with same Start/End-Nodes";

		ControlFlowEdge cfEdge = new ControlFlowEdge(n1, n2, cfType);
		n1.addSuccessor(cfEdge);
		n2.addPredecessor(cfEdge);

		return cfEdge;
	}

	public static DependencyEdge addEdgeDep(EdgeType edgeType, Node n1, Node n2) {
		assert (n1 != n2) : "Dep-Edge with same Start/End-Nodes";

		DependencyEdge depEdge = new DependencyEdge(edgeType, n1, n2);
		n1.addOutgoingDependency(depEdge);
		n2.addIncomingDependency(depEdge);

		return depEdge;
	}

	public static DependencyEdge addEdgeDep(EdgeType edgeType, Node n1, Node n2, Symbol symbol) {
		return addEdgeDep(edgeType, n1, n2, symbol, false);
	}

	public static DependencyEdge addEdgeDep(EdgeType edgeType, Node n1, Node n2, Symbol symbol,
			boolean loopCarried) {
		assert (n1 != n2) : "Dep-Edge with same Start/End-Nodes";

		DependencyEdge depEdge = new DependencyEdge(edgeType, n1, n2, symbol, loopCarried);
		n1.addOutgoingDependency(depEdge);
		n2.addIncomingDependency(depEdge);

		return depEdge;
	}

	public static void connectCF(List<ControlFlowable> cfs) {
		cfs = ListUtils.filterNulls(cfs);

		Iterator<ControlFlowable> it = cfs.iterator();
		if (!it.hasNext())
			return;

		ControlFlowable cf1 = it.next();
		while (it.hasNext()) {
			ControlFlowable cf2 = cf1;
			cf1 = it.next();
			addEdgeCF(cf2.getExit(), cf1.getEntry());
		}
	}

	/**
	 * Applies {@link #remove(ControlFlowEdge)} for every entry in succEdges
	 */
	public static void removeAll(List<ControlFlowEdge> succEdges) {
		for (ControlFlowEdge succEdge : succEdges) {
			remove(succEdge);
		}
	}

	/**
	 * Removes the given edge from its start and end nodes
	 */
	public static void remove(ControlFlowEdge succEdge) {
		Node start = succEdge.start;
		Node end = succEdge.end;
		start.succ.remove(succEdge);
		end.pred.remove(succEdge);
	}
}
