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

import static com.google.common.base.Preconditions.checkState;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.FinallyBlock;

import com.google.common.collect.ComparisonChain;

/** Represents the control flow between two nodes. */
public class ControlFlowEdge extends AbstractEdge implements Comparable<ControlFlowEdge> {
	/** The context of an edge that is caused by {@link FinallyBlock}s */
	public final JumpToken finallyPathContext;

	/**
	 * Constructor.<br/>
	 * Creates a control flow edge from start to end.
	 */
	public ControlFlowEdge(Node start, Node end) {
		this(start, end, ControlFlowType.Successor);
	}

	/**
	 * Constructor.<br/>
	 * Creates a control flow edge from start to end of the given {@link ControlFlowType}.
	 */
	public ControlFlowEdge(Node start, Node end, ControlFlowType cfType) {
		super(start, end, cfType);
		boolean correctEdgeDirection = cfType.isBackwards() == (start.astPosition > end.astPosition);
		checkState(correctEdgeDirection, "Edge has wrong direction");

		this.finallyPathContext = null;
	}

	/**
	 * Constructor.<br/>
	 * Creates a control flow edge from start to end caused by a {@link FinallyBlock} and the given {@link JumpToken}.
	 */
	public ControlFlowEdge(Node start, Node end, JumpToken finallyPathContext) {
		super(start, end, finallyPathContext.cfType);
		this.finallyPathContext = finallyPathContext;
	}

	/** There should be no two edges with same start and end nodes. */
	@Override
	public int compareTo(ControlFlowEdge edge) {
		int result = ComparisonChain.start()
				.compare(start.id, edge.start.id)
				.compare(end.id, edge.end.id)
				.compare(cfType, edge.cfType)
				.result();

		return result;
	}

	@Override
	public String toString() {
		String s = "";
		s += start.getExtendedString();
		if (cfType != ControlFlowType.Successor) {
			s += "-" + cfType.name();
		}
		s += "-> ";
		s += end.getExtendedString();
		return s;
	}
}
