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

import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * With a class that implements this interface, a given {@link Path} can be traversed.
 */
public interface IPathWalkerInternal {

	/** Initially called before traversing the path. */
	public void init();

	/** Finally called after traversing the path. */
	public void finish();

	/** Called for each node in direction of a path. */
	public void visitNode(Node node);

	/**
	 * Called for each edge in direction of a path.
	 *
	 * @param start
	 *            start in terms of current direction
	 * @param end
	 *            end in terms of current direction
	 * @param edge
	 *            traversed edge
	 */
	public void visitEdge(Node start, Node end, ControlFlowEdge edge);

}
