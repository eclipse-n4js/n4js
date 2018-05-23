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

import java.util.Collection;

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
		ControlFlowEdge cfEdge = new ControlFlowEdge(n1, n2, jumpContext);
		n1.addSuccessor(cfEdge);
		n2.addPredecessor(cfEdge);
		return cfEdge;
	}

	/** Applies {@link #removeCF(ControlFlowEdge)} for every entry in succEdges */
	public static void removeAllCF(Collection<ControlFlowEdge> succEdges) {
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

}
