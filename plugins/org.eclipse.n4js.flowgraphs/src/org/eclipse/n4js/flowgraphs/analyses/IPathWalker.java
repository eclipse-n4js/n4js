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

import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Callback methods to traverse a path.
 */
public interface IPathWalker {

	/** Called for each node in direction of a path. */
	public void visit(ControlFlowElement cfe);

	/**
	 * Called for each edge in direction of a path. The edge direction is aligned to the traversing direction, i.e. the
	 * start node was traversed before the end node of the edge.
	 */
	public void visit(FlowEdge edge);

}
