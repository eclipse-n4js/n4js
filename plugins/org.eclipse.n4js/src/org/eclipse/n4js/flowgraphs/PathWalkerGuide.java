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

import org.eclipse.n4js.flowgraphs.model.GraphPathWalker;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class PathWalkerGuide extends GraphPathWalker {
	final IPathWalker walker;
	final TreeSet<ControlFlowType> cfTypes = new TreeSet<>();
	private Node lastVisitedNode = null;

	PathWalkerGuide(IPathWalker walker) {
		this.walker = walker;
	}

	@Override
	public void visitNode(Node node) {
		if (node instanceof RepresentingNode) {
			ControlFlowElement cfeEnd = node.getRepresentedControlFlowElement();
			if (lastVisitedNode != null) {
				ControlFlowElement cfeStart = lastVisitedNode.getRepresentedControlFlowElement();
				walker.visitEdge(cfeStart, cfeEnd, cfTypes);
			}

			cfTypes.clear();
			walker.visitNode(cfeEnd);
			lastVisitedNode = node;
		}
	}

	@Override
	public void visitEdge(Node start, Node end, ControlFlowType cfType) {
		cfTypes.add(cfType);
	}

}
