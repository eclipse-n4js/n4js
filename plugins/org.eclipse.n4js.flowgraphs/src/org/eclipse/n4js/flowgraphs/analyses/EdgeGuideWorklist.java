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

import java.util.Collection;
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
 *   -> {@link #work()}
 * }*
 * </pre>
 */
public class EdgeGuideWorklist {
	private final EdgeGuideQueue currEdgeGuides = new EdgeGuideQueue();
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
		List<EdgeGuide> nextEGs = EdgeGuide.getFirstEdgeGuides(cn, edgeProvider, activatedPaths);
		currEdgeGuides.addAll(nextEGs);
	}

	void work() {// consider to execute this in #next()
		allVisitedEdges.add(currEdgeGuide.getEdge());
		List<EdgeGuide> nextEGs = currEdgeGuide.getNextEdgeGuides();
		currEdgeGuides.addAll(nextEGs);

		Collection<LinkedList<EdgeGuide>> edgeGuideGroups = currEdgeGuides.removeJoinGuides();
		for (List<EdgeGuide> edgeGuideGroup : edgeGuideGroups) {
			EdgeGuide remainingEdgeGuide = EdgeGuide.join(edgeGuideGroup);
			currEdgeGuides.add(remainingEdgeGuide);
		}
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
