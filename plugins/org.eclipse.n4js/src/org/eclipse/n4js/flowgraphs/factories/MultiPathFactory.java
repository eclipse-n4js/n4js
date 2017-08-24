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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.MultiPath;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 *
 */
public class MultiPathFactory extends NextEdgesProvider {

	public static MultiPath toSuccessors(Node start) {
		MultiPath multiPath = toNext(start, true);
		return multiPath;
	}

	public static MultiPath toPredecessors(Node start) {
		MultiPath multiPath = toNext(start, false);
		return multiPath;
	}

	private static MultiPath toNext(Node start, boolean forwards) {
		LinkedList<ControlFlowEdge> loopEdges = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = getNextEdges(start, forwards, loopEdges);

		for (ControlFlowEdge nextEdge : nextEdges) {

			LinkedList<LinkedList<ControlFlowEdge>> ch = getPaths(firstPath, forwards, loopEdges);
		}
		return null;
	}

}
