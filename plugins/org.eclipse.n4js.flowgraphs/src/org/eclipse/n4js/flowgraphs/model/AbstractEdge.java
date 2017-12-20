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

import java.util.Objects;

/** Represents any kind of edges between two {@link Node}s. */
abstract public class AbstractEdge {
	/** Start node of the edge */
	public final Node start;
	/** End node of the edge */
	public final Node end;

	private final int cachedHash;

	/** Constructor */
	public AbstractEdge(Node start, Node end) {
		assert (start != null) : "Start node must not be null";
		assert (end != null) : "End node must not be null";
		assert (start != end) : "Edge must not have same Start/End nodes";
		this.start = start;
		this.end = end;
		this.cachedHash = Objects.hash(start, end);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractEdge))
			return false;

		AbstractEdge edge = (AbstractEdge) obj;
		boolean equals = true;
		equals &= start.id == edge.start.id;
		equals &= end.id == edge.end.id;
		return equals;
	}

	@Override
	public int hashCode() {
		return cachedHash;
	}
}
