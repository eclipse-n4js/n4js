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
package org.eclipse.n4js.flowgraphs;

import java.util.TreeSet;

import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class PathWalker implements IPathWalker {

	@Override
	public void visitNode(ControlFlowElement cfe) {
		// stub implementation
	}

	@Override
	public void visitEdge(ControlFlowElement start, ControlFlowElement end, TreeSet<ControlFlowType> cfTypes) {
		// stub implementation
	}

}
