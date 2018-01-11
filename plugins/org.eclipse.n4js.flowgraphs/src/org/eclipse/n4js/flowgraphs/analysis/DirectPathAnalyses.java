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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Lists;

/**
 * Contains algorithms to reason about single paths between two {@link ControlFlowElement}s.
 */
public class DirectPathAnalyses {
	private final FlowGraph cfg;
	private final SuccessorPredecessorAnalysis spa;

	/** Constructor */
	public DirectPathAnalyses(FlowGraph cfg) {
		this.cfg = cfg;
		this.spa = new SuccessorPredecessorAnalysis(cfg);
	}

	/** see {@link N4JSFlowAnalyzer#getControlFlowTypeToSuccessors(ControlFlowElement , ControlFlowElement)}. */
	public TreeSet<ControlFlowType> getControlFlowTypeToSuccessors(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		Path path = getPath(cfe, cfeSucc);
		if (path.isConnecting()) {
			return path.getControlFlowTypes();
		} else {
			throw new IllegalArgumentException("No path found between given ControlFlowElements");
		}
	}

	/**
	 * see {@link N4JSFlowAnalyzer#isTransitiveSuccessor(ControlFlowElement, ControlFlowElement, ControlFlowElement)}.
	 */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo,
			ControlFlowElement cfeNotVia) {

		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		Path path = getPath(cfeFrom, cfeTo, cfeNotVia);
		return path.isConnecting();
	}

	/** see {@link N4JSFlowAnalyzer#getCommonPredecessors(ControlFlowElement , ControlFlowElement)}. */
	public Set<ControlFlowElement> getCommonPredecessors(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		Objects.requireNonNull(cfeA);
		Objects.requireNonNull(cfeB);

		LinkedHashSet<ControlFlowElement> commonPredSet = new LinkedHashSet<>();
		commonPredSet.addAll(getSomeCommonPredecessors(cfeA, cfeB));
		commonPredSet.addAll(getSomeCommonPredecessors(cfeB, cfeA));

		return commonPredSet;
	}

	private Set<ControlFlowElement> getSomeCommonPredecessors(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		LinkedHashSet<ControlFlowElement> commonPredSet = new LinkedHashSet<>();

		// step 1: traverse all predecessors, beginning from cfeA: mark each
		Set<ControlFlowElement> marked = new HashSet<>();
		LinkedList<ControlFlowElement> curCFEs = new LinkedList<>();
		// marked.add(cfeA);
		curCFEs.add(cfeA);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.removeFirst();
			if (!marked.contains(cfe)) {
				marked.add(cfe);
				Set<ControlFlowElement> preds = spa.getPredecessors(cfe);
				curCFEs.addAll(preds);
			}
		}

		// step 2: traverse all predecessors, beginning from cfeB: find mark (this is a common pred.)
		Set<ControlFlowElement> visited = new HashSet<>();
		curCFEs.clear();
		curCFEs.add(cfeB);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.removeFirst();
			if (marked.contains(cfe)) {
				commonPredSet.add(cfe);
			} else {
				if (!visited.contains(cfe)) {
					visited.add(cfe);
					Set<ControlFlowElement> preds = spa.getPredecessors(cfe);
					curCFEs.addAll(preds);
				}
			}
		}

		return commonPredSet;
	}

	/** @return the path from cfeFrom to cfeTo */
	public Path getPath(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return getPath(cfeFrom, cfeTo, null);
	}

	/** @return a path from cfeFrom to cfeTo that does not include over cfeNotVia */
	public Path getPath(ControlFlowElement cfeFrom, ControlFlowElement cfeTo, ControlFlowElement cfeNotVia) {
		ComplexNode cnStart = cfg.getComplexNode(cfeFrom);
		ComplexNode cnEnd = cfg.getComplexNode(cfeTo);
		Node nStart = cnStart.getRepresent();
		Node nEnd = cnEnd.getRepresent();
		Node nNotVia = null;
		if (cfeNotVia != null) {
			ComplexNode cnNotVia = cfg.getComplexNode(cfeNotVia);
			nNotVia = cnNotVia.getRepresent();
		}
		Path path = buildPath(nStart, nEnd, nNotVia);
		return path;
	}

	private Path buildPath(Node start, Node end, Node notVia) {
		NextEdgesProvider.Forward forwardEdgeProvider = new NextEdgesProvider.Forward();
		LinkedList<ControlFlowEdge> pathEdges = findPath(start, end, notVia, forwardEdgeProvider);
		Path path = null;
		if (pathEdges != null) {
			path = new Path(start, end, pathEdges, forwardEdgeProvider);
		} else {
			path = new NoPath(start, end, forwardEdgeProvider);
		}
		return path;
	}

	private LinkedList<ControlFlowEdge> findPath(Node startNode, Node endNode, Node notVia,
			NextEdgesProvider edgeProvider) {

		if (startNode == endNode) {
			return Lists.newLinkedList();
		}

		LinkedList<LinkedList<ControlFlowEdge>> allPaths = new LinkedList<>();

		// initialization
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(startNode, ControlFlowType.NonDeadTypes);
		for (ControlFlowEdge nextEdge : nextEdges) {
			LinkedList<ControlFlowEdge> path = new LinkedList<>();
			path.add(nextEdge);
			if (isEndNode(edgeProvider, endNode, nextEdge)) {
				return path; // direct edge from startNode to endNode due to nextEdge
			}
			allPaths.add(path);
		}

		// explore all paths, terminate when endNode is found
		while (!allPaths.isEmpty()) {
			LinkedList<ControlFlowEdge> firstPath = allPaths.removeFirst();
			LinkedList<LinkedList<ControlFlowEdge>> ch = getPaths(edgeProvider, firstPath, notVia);
			for (LinkedList<ControlFlowEdge> chPath : ch) {
				if (isEndNode(edgeProvider, endNode, chPath.getLast())) {
					return chPath;
				}
			}
			allPaths.addAll(ch);
		}

		return null;
	}

	private LinkedList<LinkedList<ControlFlowEdge>> getPaths(NextEdgesProvider edgeProvider,
			LinkedList<ControlFlowEdge> path, Node notVia) {

		LinkedList<LinkedList<ControlFlowEdge>> resultPaths = new LinkedList<>();
		ControlFlowEdge e = path.getLast();
		Node nextNode = edgeProvider.getNextNode(e);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(nextNode, ControlFlowType.NonDeadTypes);

		for (ControlFlowEdge nextEdge : nextEdges) {
			Node uberNextNode = edgeProvider.getNextNode(nextEdge);
			if (notVia != null && notVia == uberNextNode) {
				continue; // skip edges that target the notVia node
			}
			LinkedList<ControlFlowEdge> pathCopy = path;
			if (nextEdges.size() > 1)
				pathCopy = Lists.newLinkedList(pathCopy);
			pathCopy.add(nextEdge);
			resultPaths.add(pathCopy);
		}
		return resultPaths;
	}

	private boolean isEndNode(NextEdgesProvider edgeProvider, Node node, ControlFlowEdge edge) {
		return edgeProvider.getNextNode(edge) == node;
	}
}
