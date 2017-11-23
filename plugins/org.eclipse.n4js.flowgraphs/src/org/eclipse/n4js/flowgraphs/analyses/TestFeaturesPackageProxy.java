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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzerTestFeatures;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * This is a proxy that delegates calls to {@link N4JSFlowAnalyzerTestFeatures}. The reason to have this proxy is the
 * restricted accessibility of some recorded data.
 */
public class TestFeaturesPackageProxy {

	static void addMergedEdges(List<EdgeGuide> edgeGuides) {
		if (!N4JSFlowAnalyzerTestFeatures.isEnabled()) {
			return;
		}

		List<ControlFlowEdge> edges = new LinkedList<>();
		for (EdgeGuide eg : edgeGuides) {
			edges.add(eg.getEdge());
		}
		Node startNode = edgeGuides.get(0).getNextNode();

		N4JSFlowAnalyzerTestFeatures.addMergedEdges(startNode, edges);
	}

}
