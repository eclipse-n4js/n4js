/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * A worklist that iterates through all {@link EdgeGuide}s.
 * <p>
 * Use the API in the following way:
 *
 * <pre>
 * {@link #EdgeGuideWorklist()}
 * -> {@link #initialize(ComplexNode, NextEdgesProvider, Set)}
 * {
 *   -> {@link #hasNext()}
 *   -> {@link #next()}
 *   -> {@link #getJoinGroups()}
 * }*
 * </pre>
 */
public class EdgeGuideWorklist {
	private final EdgeGuideQueue currEdgeGuides = new EdgeGuideQueue(this);
	private final Set<ControlFlowEdge> allVisitedEdges = new HashSet<>();
	private EdgeGuide currEdgeGuide;
	private EdgeGuide nextEdgeGuide;

	boolean hasNext() {
		setNext();
		return nextEdgeGuide != null;
	}

	EdgeGuide getCurrent() {
		return currEdgeGuide;
	}

	EdgeGuide next() {
		setNext();
		currEdgeGuide = nextEdgeGuide;
		nextEdgeGuide = null;
		return currEdgeGuide;
	}

	void initialize(ComplexNode cn, NextEdgesProvider edgeProvider, Set<BranchWalkerInternal> activatedPaths) {
		allVisitedEdges.clear();
		currEdgeGuide = null;
		nextEdgeGuide = null;
		currEdgeGuides.clear();
		List<EdgeGuide> nextEGs = EdgeGuide.getFirstEdgeGuides(cn, edgeProvider, activatedPaths);
		currEdgeGuides.addAll(nextEGs);
	}

	LinkedList<EdgeGuide> getJoinGroups() {
		allVisitedEdges.add(currEdgeGuide.getEdge());
		List<EdgeGuide> nextEGs = currEdgeGuide.getNextEdgeGuides();
		currEdgeGuides.addAll(nextEGs);

		LinkedList<EdgeGuide> joinGuideGroup = currEdgeGuides.removeFirstJoinGuide();
		return joinGuideGroup;
	}

	EdgeGuideMerged mergeJoinGroup(LinkedList<EdgeGuide> joinGuideGroup) {
		if (!joinGuideGroup.isEmpty()) {
			EdgeGuideMerged remainingEdgeGuide = new EdgeGuideMerged(joinGuideGroup);
			for (EdgeGuide eg : joinGuideGroup) {
				allVisitedEdges.add(eg.getEdge());
			}

			List<EdgeGuide> nextEGs = remainingEdgeGuide.getNextEdgeGuides();
			currEdgeGuides.addAll(nextEGs);
			return remainingEdgeGuide;
		}
		return null;
	}

	private void setNext() {
		if (nextEdgeGuide != null) {
			return;
		}
		nextEdgeGuide = null;
		while (!currEdgeGuides.isEmpty() && nextEdgeGuide == null) {
			nextEdgeGuide = currEdgeGuides.removeFirst();
			boolean alreadyVisitedAndObsolete = allVisitedEdges.contains(nextEdgeGuide.getEdge());
			alreadyVisitedAndObsolete &= nextEdgeGuide.isEmpty();
			if (alreadyVisitedAndObsolete) {
				nextEdgeGuide = null;
			}
		}
	}

	Iterable<EdgeGuide> getCurrentEdgeGuides() {
		return currEdgeGuides;
	}

	boolean edgeVisited(ControlFlowEdge edge) {
		return allVisitedEdges.contains(edge);
	}

	Set<Node> getAllVisitedNodes(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<Node> allVisitedNodes = new HashSet<>();
		for (ControlFlowEdge edge : allVisitedEdges) {
			allVisitedNodes.add(edge.start);
			allVisitedNodes.add(edge.end);
		}
		allVisitedNodes.add(edgeProvider.getStartNode(cn));
		return allVisitedNodes;
	}

}
