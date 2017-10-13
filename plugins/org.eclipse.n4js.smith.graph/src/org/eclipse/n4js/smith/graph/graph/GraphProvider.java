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
package org.eclipse.n4js.smith.graph.graph;

import java.util.Collection;
import java.util.List;

/**
 * Implementations of this interface define how to create a graph, i.e. nodes and edges, from a given input object. Call
 * sequence:
 * <ol>
 * <li>{@link #getElements(Object)}
 * <li>{@link #getNode(Object)}
 * <li>{@link #getConnectedEdges(Node, List)}
 * </ol>
 */
public interface GraphProvider<Input, Element> {
	/**
	 * Return the elements for the given input. Each element will later be passed to {@link #getNode(Object)} to create
	 * a corresponding node.
	 */
	public Collection<Element> getElements(Input input);

	/**
	 * Create and return a node for the given element.
	 */
	public Node getNode(Element element);

	/**
	 * Create and return all edges for the given node.
	 */
	public List<Edge> getConnectedEdges(Node node, List<Node> allNodes);
}
