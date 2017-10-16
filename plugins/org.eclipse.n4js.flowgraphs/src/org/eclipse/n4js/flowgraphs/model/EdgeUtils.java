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

import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.FinallyBlock;

/**
 * Used to connect two {@link Node}s by {@link ControlFlowEdge}s. The functions take care about
 * registering/de-registering the edge at/from the nodes.
 */
public class EdgeUtils {

	/** Adds a {@link ControlFlowEdge} from cf1 to cf2 with {@literal ControlFlowType.Successor}. */
	public static void connectCF(ControlFlowable cf1, ControlFlowable cf2) {
		connectCF(cf1.getExit(), cf2.getEntry());
	}

	/** Adds a {@link ControlFlowEdge} from n1 to n2 with {@literal ControlFlowType.Successor}. */
	static public ControlFlowEdge connectCF(Node n1, Node n2) {
		return connectCF(n1, n2, ControlFlowType.Successor);
	}

	/** Adds a {@link ControlFlowEdge} from n1 to n2 with the given {@link ControlFlowType}. */
	static public ControlFlowEdge connectCF(Node n1, Node n2, ControlFlowType cfType) {
		assert (n1 != n2) : "CF-Edge with same Start/End-Nodes";

		ControlFlowEdge cfEdge = new ControlFlowEdge(n1, n2, cfType);
		n1.addSuccessor(cfEdge);
		n2.addPredecessor(cfEdge);
		return cfEdge;
	}

	/**
	 * Adds a {@link ControlFlowEdge} from n1 to n2 with the given {@link JumpToken}.
	 * <p>
	 * <b>Attention:</b> Use this only when the edge connects to a {@link FinallyBlock}.
	 */
	public static ControlFlowEdge connectCF(Node n1, Node n2, JumpToken jumpContext) {
		assert (n1 != n2) : "CF-Edge with same Start/End-Nodes";

		ControlFlowEdge cfEdge = new ControlFlowEdge(n1, n2, jumpContext);
		n1.addSuccessor(cfEdge);
		n2.addPredecessor(cfEdge);
		return cfEdge;
	}

	/** Applies {@link #removeCF(ControlFlowEdge)} for every entry in succEdges */
	public static void removeAllCF(List<ControlFlowEdge> succEdges) {
		for (ControlFlowEdge succEdge : succEdges) {
			removeCF(succEdge);
		}
	}

	/** Removes the given edge from its start and end nodes */
	public static void removeCF(ControlFlowEdge succEdge) {
		Node start = succEdge.start;
		Node end = succEdge.end;
		start.succ.remove(succEdge);
		end.pred.remove(succEdge);
	}

	/** Adds a {@link DependencyEdge} from n1 to n2 with the given {@link DependencyEdgeType}. */
	@Deprecated
	public static DependencyEdge connectDep(DependencyEdgeType edgeType, Node n1, Node n2) {
		return connectDep(edgeType, n1, n2, null, false);
	}

	/** Adds a {@link DependencyEdge} from n1 to n2 with the given {@link DependencyEdgeType} and {@link Symbol}. */
	@Deprecated
	public static DependencyEdge connectDep(DependencyEdgeType edgeType, Node n1, Node n2, Symbol symbol) {
		return connectDep(edgeType, n1, n2, symbol, false);
	}

	/**
	 * Adds a {@link DependencyEdge} from n1 to n2 with the given {@link DependencyEdgeType}, {@link Symbol} and
	 * loop-carried property.
	 */
	@Deprecated
	public static DependencyEdge connectDep(DependencyEdgeType edgeType, Node n1, Node n2, Symbol symbol,
			boolean loopCarried) {
		assert (n1 != n2) : "Dep-Edge with same Start/End-Nodes";

		DependencyEdge depEdge = new DependencyEdge(edgeType, n1, n2, symbol, loopCarried);
		n1.addOutgoingDependency(depEdge);
		n2.addIncomingDependency(depEdge);

		return depEdge;
	}

}
