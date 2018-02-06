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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * When enabled, the {@link N4JSFlowAnalyzerDataRecorder} class records data during creating and traversing the flow
 * graphs. This recording is disabled by default for performance reasons.
 */
public class N4JSFlowAnalyzerDataRecorder {
	static private boolean enabled = false;
	static private List<Pair<Node, List<ControlFlowEdge>>> mergedEdges = new ArrayList<>();

	/** Sets the test features to be enabled or not */
	static public void setEnabled(boolean pEnabled) {
		enabled = pEnabled;
		if (pEnabled) {
			clear();
		}
	}

	/** @return true iff the test features are enabled */
	static public boolean isEnabled() {
		return enabled;
	}

	/** Adds another entry to the merged edges map */
	static public void addMergedEdges(Node start, List<ControlFlowEdge> edges) {
		Pair<Node, List<ControlFlowEdge>> pair = new Pair<>(start, edges);
		mergedEdges.add(pair);
	}

	/** @return the merged edges map */
	static public List<Pair<Node, List<ControlFlowEdge>>> getMergedEdges() {
		Collections.sort(mergedEdges, N4JSFlowAnalyzerDataRecorder::compareNodes);
		return mergedEdges;
	}

	static int compareNodes(Pair<Node, List<ControlFlowEdge>> p1, Pair<Node, List<ControlFlowEdge>> p2) {
		Node n1 = p1.getKey();
		Node n2 = p2.getKey();
		return n1.getExtendedString().compareTo(n2.getExtendedString());
	}

	private static void clear() {
		mergedEdges.clear();
	}
}
