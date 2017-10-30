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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.flowgraphs.model.Node;

/**
 * A worklist that iterates through all {@link EdgeGuide}s.
 * <p>
 * Use the API in the following way:
 *
 * <pre>
 * {@link #EdgeGuideWorklist(Collection)}
 * -> {@link #initialize(ComplexNode, NextEdgesProvider)}
 * {
 *   -> {@link #hasNext()}
 *   -> {@link #next()}
 *   -> {@link #work()}
 * }*
 * </pre>
 */
public class EdgeGuideWorklist {
	private final Collection<? extends GraphVisitorInternal> walkers;
	private final EdgeGuideQueue currEdgeGuides = new EdgeGuideQueue();
	private final Set<ControlFlowEdge> allVisitedEdges = new HashSet<>();
	private EdgeGuide currEdgeGuide;
	private EdgeGuide nextEdgeGuide;

	/** Constructor */
	public EdgeGuideWorklist(Collection<? extends GraphVisitorInternal> walkers) {
		this.walkers = walkers;
	}

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

	void initialize(ComplexNode cn, NextEdgesProvider edgeProvider) {
		List<EdgeGuide> nextEGs = getFirstEdgeGuides(cn, edgeProvider);
		currEdgeGuides.addAll(nextEGs);
	}

	void work() {// consider to execute this in #next()
		allVisitedEdges.add(currEdgeGuide.edge);
		List<EdgeGuide> nextEGs = getNextEdgeGuides(currEdgeGuide);
		currEdgeGuides.addAll(nextEGs);
	}

	private void setNext() {
		if (nextEdgeGuide != null) {
			return;
		}
		nextEdgeGuide = null;
		while (!currEdgeGuides.isEmpty() && nextEdgeGuide == null) {
			nextEdgeGuide = currEdgeGuides.removeFirst();
			boolean alreadyVisitedAndObsolete = allVisitedEdges.contains(nextEdgeGuide.edge);
			alreadyVisitedAndObsolete &= nextEdgeGuide.activePaths.isEmpty();
			if (alreadyVisitedAndObsolete) {
				nextEdgeGuide = null;
			}
		}
	}

	private List<EdgeGuide> getFirstEdgeGuides(ComplexNode cn, NextEdgesProvider edgeProvider) {
		Set<PathWalkerInternal> activatedPaths = new HashSet<>();
		for (GraphVisitorInternal walker : walkers) {
			activatedPaths.addAll(walker.activateRequestedPathExplorers());
		}

		Node node = edgeProvider.getStartNode(cn);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(node);
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		List<EdgeGuide> nextEGs = new LinkedList<>();
		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, activatedPaths);
			nextEGs.add(eg);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<PathWalkerInternal> forkedPaths = new HashSet<>();
			for (PathWalkerInternal aPath : activatedPaths) {
				PathWalkerInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}
			EdgeGuide eg = new EdgeGuide(edgeProvider.copy(), nextEdge, forkedPaths);
			nextEGs.add(eg);
		}
		return nextEGs;
	}

	private List<EdgeGuide> getNextEdgeGuides(EdgeGuide currEG) {
		List<EdgeGuide> nextEGs = new LinkedList<>();
		List<ControlFlowEdge> nextEdges = currEG.getNextEdges();
		Iterator<ControlFlowEdge> nextEdgeIt = nextEdges.iterator();

		if (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			currEG.edge = nextEdge;
			nextEGs.add(currEG);
		}

		while (nextEdgeIt.hasNext()) {
			ControlFlowEdge nextEdge = nextEdgeIt.next();
			Set<PathWalkerInternal> forkedPaths = new HashSet<>();
			for (PathWalkerInternal aPath : currEG.activePaths) {
				PathWalkerInternal forkedPath = aPath.callFork();
				forkedPaths.add(forkedPath);
			}

			NextEdgesProvider epCopy = currEG.edgeProvider.copy();
			Set<JumpToken> fbContexts = currEG.finallyBlockContexts;
			EdgeGuide edgeGuide = new EdgeGuide(epCopy, nextEdge, forkedPaths, fbContexts);
			nextEGs.add(edgeGuide);
		}

		if (nextEGs.isEmpty()) {
			for (PathWalkerInternal aPath : currEG.activePaths) {
				aPath.deactivate();
			}
		}

		return nextEGs;
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
