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

/**
 * see {@link GraphVisitorInternal}
 */
abstract public class GraphExplorer extends GraphExplorerInternal {

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal()}
	 */
	protected GraphExplorer() {
		super();
	}

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal(Quantor)}
	 */
	protected GraphExplorer(Quantor quantor) {
		super(quantor);
	}

	/**
	 * see {@link GraphExplorerInternal#GraphExplorerInternal(Quantor, boolean)}
	 */
	protected GraphExplorer(Quantor quantor, boolean passAsDefault) {
		super(quantor, passAsDefault);
	}

	@Override
	abstract protected BranchWalkerInternal firstPathWalker();

}
