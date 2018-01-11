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
package org.eclipse.n4js.flowgraphs.analysis;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzerDataRecorder;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * This is a proxy that delegates calls to {@link N4JSFlowAnalyzerDataRecorder}. The reason to have this proxy is the
 * restricted accessibility of some recorded data.
 */
public class DataRecorderPackageProxy {

	static void addMergedEdges(List<EdgeGuide> edgeGuides) {
		if (!N4JSFlowAnalyzerDataRecorder.isEnabled()) {
			return;
		}

		List<ControlFlowEdge> edges = new LinkedList<>();
		for (EdgeGuide eg : edgeGuides) {
			edges.add(eg.getEdge());
		}
		Node startNode = edgeGuides.get(0).getNextNode();

		N4JSFlowAnalyzerDataRecorder.addMergedEdges(startNode, edges);
	}

}
