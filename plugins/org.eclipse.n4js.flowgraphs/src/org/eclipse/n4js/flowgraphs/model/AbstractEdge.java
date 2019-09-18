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

import java.util.Objects;

import org.eclipse.n4js.flowgraphs.ControlFlowType;

/** Represents any kind of edges between two {@link Node}s. */
abstract public class AbstractEdge {
	/** Start node of the edge */
	public final Node start;
	/** End node of the edge */
	public final Node end;
	/** The type of the control flow */
	public final ControlFlowType cfType;

	private final int cachedHash;

	/** Constructor */
	public AbstractEdge(Node start, Node end, ControlFlowType cfType) {
		checkState(start != null, "Start node must not be null");
		checkState(end != null, "End node must not be null");
		checkState(start != end, "Edge must not have same Start/End nodes");

		this.start = start;
		this.end = end;
		this.cfType = cfType;
		this.cachedHash = Objects.hash(start, end, cfType);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractEdge))
			return false;

		AbstractEdge edge = (AbstractEdge) obj;
		boolean equals = true;
		equals &= start.id == edge.start.id;
		equals &= end.id == edge.end.id;
		equals &= cfType == edge.cfType;
		return equals;
	}

	@Override
	public int hashCode() {
		return cachedHash;
	}
}
