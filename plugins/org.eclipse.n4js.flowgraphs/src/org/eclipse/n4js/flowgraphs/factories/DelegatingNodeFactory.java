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
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Factory class to create Delegating nodes. It also re-enters the AST iterator.
 * <p>
 * Reasoning of method {@link #createOrHelper(ReentrantASTIterator, String, ControlFlowElement, ControlFlowElement)
 * createOrHelper}
 * <ol>
 * <li/>Since the flow graph is created from the AST, nodes are only created iff an AST node exists (e.g. if there is no
 * else-node in the AST, the flow graph does not have a corresponding node). In other words, node are not created if the
 * corresponding AST element is missing.
 * <li/>After the nodes in the flow graph have been created, they are connected by edges. These connection procedures
 * assume a flow graph that is based on a valid AST.
 * <li/>However, to simplify this edge creation, it is convenient to have some pseudo nodes are created even when the
 * corresponding AST element is missing. This is in particular true for broken/invalid ASTs where some arbitrary AST
 * elements are missing.
 * </ol>
 */
public class DelegatingNodeFactory {

	/**
	 * Returns control flow after the delegate {@link ControlFlowElement} was visited.
	 *
	 * @return a new {@link DelegatingNode} instance, iff the delegate is non-null.
	 */
	static DelegatingNode create(ReentrantASTIterator astpp, String name, ControlFlowElement parent,
			ControlFlowElement delegate) {

		if (delegate == null) {
			return null;
		}

		DelegatingNode node = new DelegatingNode(name, astpp.pos(), parent, delegate);
		astpp.visitUtil(delegate);
		return node;
	}

	/**
	 * Returns control flow after the delegate {@link ControlFlowElement} was visited.
	 *
	 * @return a new {@link DelegatingNode} instance. Iff the delegate is null, a {@link HelperNode} is returned.
	 */
	static Node createOrHelper(ReentrantASTIterator astpp, String name, ControlFlowElement parent,
			ControlFlowElement delegate) {

		Node node = create(astpp, name, parent, delegate);
		if (node == null) {
			node = new HelperNode(name, astpp.pos(), parent);
		}
		return node;
	}

}
