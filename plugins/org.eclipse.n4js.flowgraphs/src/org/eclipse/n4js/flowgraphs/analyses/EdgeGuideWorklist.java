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
 * A work list that iterates through all {@link EdgeGuide}s.
 * <p>
 * Use the API in the following way:
 *
 * <pre>
 * {@link #EdgeGuideWorklist()}
 * -> {@link #initialize(ComplexNode, NextEdgesProvider, Collection)}
 * {
 *   -> {@link #hasNext()}
 *   -> {@link #next()}
 *   -> {@link #getJoinGroups()}
 *   -> {@link #mergeJoinGroup(LinkedList)}
 * }*
 * </pre>
 */
public class EdgeGuideWorklist {
	private final EdgeGuideQueue egQueue = new EdgeGuideQueue(this);
	private final Set<ControlFlowEdge> allVisitedEdges = new HashSet<>();
	private EdgeGuide currEdgeGuide;
	private EdgeGuide nextEdgeGuide;

	/** @return true iff a subsequent call to {@link #next()} returns a non null value */
	boolean hasNext() {
		setNext();
		return nextEdgeGuide != null;
	}

	/** @return an {@link Iterable} over all {@link EdgeGuide} that are currently in the work list */
	Iterable<EdgeGuide> getCurrentEdgeGuides() {
		return egQueue.getIterator();
	}

	/** @return the next {@link EdgeGuide} which is the new current {@link EdgeGuide} */
	EdgeGuide next() {
		setNext();
		currEdgeGuide = nextEdgeGuide;
		nextEdgeGuide = null;
		return currEdgeGuide;
	}

	/** Initializes this instance. Can be used for re-initialization. */
	void initialize(ComplexNode cn, NextEdgesProvider edgeProvider, Collection<BranchWalkerInternal> activatedPaths) {
		allVisitedEdges.clear();
		currEdgeGuide = null;
		nextEdgeGuide = null;
		egQueue.clear();
		List<EdgeGuide> nextEGs = EdgeGuide.getFirstEdgeGuides(cn, edgeProvider, activatedPaths);
		egQueue.addAll(nextEGs);
	}

	/**
	 * Sets the current edge to be visited.
	 *
	 * @return a list that is either empty or contains two or more {@link EdgeGuide} that can be merged.
	 */
	LinkedList<EdgeGuide> getJoinGroups() {
		allVisitedEdges.add(currEdgeGuide.getEdge());
		List<EdgeGuide> nextEGs = currEdgeGuide.getNextEdgeGuides();
		egQueue.addAll(nextEGs);

		LinkedList<EdgeGuide> joinGuideGroup = egQueue.removeFirstJoinGuide();
		return joinGuideGroup;
	}

	/**
	 * Sets the edges of the given {@link EdgeGuide}s to be visited.
	 *
	 * @return the {@link EdgeGuideMerged} that is the merge result of the given {@link EdgeGuide}s
	 */
	EdgeGuideMerged mergeJoinGroup(LinkedList<EdgeGuide> joinGuideGroup) {
		if (!joinGuideGroup.isEmpty()) {
			EdgeGuideMerged remainingEdgeGuide = new EdgeGuideMerged(joinGuideGroup);
			for (EdgeGuide eg : joinGuideGroup) {
				allVisitedEdges.add(eg.getEdge());
			}

			List<EdgeGuide> nextEGs = remainingEdgeGuide.getNextEdgeGuides();
			egQueue.addAll(nextEGs);
			return remainingEdgeGuide;
		}
		return null;
	}

	/** @return true iff the given edge was already visited */
	boolean edgeVisited(ControlFlowEdge edge) {
		return allVisitedEdges.contains(edge);
	}

	/** @return a set of all visited edges */
	Set<ControlFlowEdge> getAllVisitedEdges() {
		return allVisitedEdges;
	}

	/** @return a set of all visited nodes */
	Set<Node> getAllVisitedNodes(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<Node> allVisitedNodes = new HashSet<>();
		for (ControlFlowEdge edge : allVisitedEdges) {
			allVisitedNodes.add(edge.start);
			allVisitedNodes.add(edge.end);
		}
		allVisitedNodes.add(edgeProvider.getStartNode(cn));
		return allVisitedNodes;
	}

	private void setNext() {
		if (nextEdgeGuide != null) {
			return;
		}
		nextEdgeGuide = null;
		while (!egQueue.isEmpty() && nextEdgeGuide == null) {
			nextEdgeGuide = egQueue.removeFirst();
			boolean alreadyVisitedAndObsolete = allVisitedEdges.contains(nextEdgeGuide.getEdge());
			alreadyVisitedAndObsolete &= nextEdgeGuide.isEmpty();
			alreadyVisitedAndObsolete &= !nextEdgeGuide.deadContext.isForwardAndDeadInside();
			if (alreadyVisitedAndObsolete) {
				nextEdgeGuide = null; // optimization. might be removed
				// obsoleteCounter++;
				// System.out.println(obsoleteCounter);
			}
		}
	}

	static int obsoleteCounter = 0;
}
