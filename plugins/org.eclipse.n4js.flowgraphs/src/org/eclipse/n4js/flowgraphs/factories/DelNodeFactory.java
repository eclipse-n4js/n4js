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
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Factory class to create Delegating nodes. It also re-enters the AST iterator.
 */
public class DelNodeFactory {

	/**
	 * Returns control flow after the delegate {@link ControlFlowElement} was visited.
	 *
	 * @return a new {@link DelegatingNode} instance
	 */
	static DelegatingNode create(ReentrantASTIterator astpp, String name, ControlFlowElement parent,
			ControlFlowElement delegate) {

		if (delegate == null)
			return null;

		DelegatingNode node = new DelegatingNode(name, astpp.pos(), parent, delegate);
		astpp.visitUtil(delegate);
		return node;
	}

}
