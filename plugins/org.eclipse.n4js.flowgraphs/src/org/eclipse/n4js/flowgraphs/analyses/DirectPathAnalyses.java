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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
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

	/** see {@link N4JSFlowAnalyzer#isTransitiveSuccessor(ControlFlowElement , ControlFlowElement)}. */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		Path path = getPath(cfeFrom, cfeTo);
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

	/** see {@link N4JSFlowAnalyzer#getPathIdentifier(ControlFlowElement , ControlFlowElement)}. */
	public String getPathIdentifier(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		LinkedHashSet<ControlFlowElement> predSet = new LinkedHashSet<>();

		// step 1: traverse all predecessors, beginning from cfeTo: mark each
		List<ControlFlowElement> curCFEs = new LinkedList<>();
		curCFEs.add(cfeTo);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			predSet.add(cfe);
			Set<ControlFlowElement> preds = spa.getPredecessors(cfe, ControlFlowType.NonRepeatTypes);
			curCFEs.addAll(preds);
		}

		// step 2: traverse all successors, beginning from cfeFrom. All revisited are part of the identifier.
		String pathString = "";
		curCFEs.clear();
		curCFEs.add(cfeFrom);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			if (predSet.contains(cfe)) {
				Set<ControlFlowElement> succs = spa.getSuccessors(cfe, ControlFlowType.NonRepeatTypes);
				curCFEs.addAll(succs);
				String nameID = FGUtils.getNameID(cfe);
				pathString += nameID + "->";
			}
		}

		pathString = pathString.substring(0, pathString.length() - 2);
		return pathString;
	}

	/** @return the path from cfe to cfeSucc */
	public Path getPath(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		ComplexNode cnStart = cfg.getComplexNode(cfe);
		ComplexNode cnEnd = cfg.getComplexNode(cfeSucc);
		Node nStart = cnStart.getRepresent();
		Node nEnd = cnEnd.getRepresent();
		Path path = buildPath(nStart, nEnd);
		return path;
	}

	private Path buildPath(Node start, Node end) {
		NextEdgesProvider.Forward forwardEdgeProvider = new NextEdgesProvider.Forward();
		LinkedList<ControlFlowEdge> pathEdges = findPath(start, end, forwardEdgeProvider);
		Path path = null;
		if (pathEdges != null) {
			path = new Path(start, end, pathEdges, forwardEdgeProvider);
		} else {
			path = new NoPath(start, end, forwardEdgeProvider);
		}
		return path;
	}

	private LinkedList<ControlFlowEdge> findPath(Node startNode, Node endNode, NextEdgesProvider edgeProvider,
			ControlFlowType... cfTypes) {

		if (startNode == endNode) {
			return Lists.newLinkedList();
		}

		LinkedList<LinkedList<ControlFlowEdge>> allPaths = new LinkedList<>();

		// initialization
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(startNode, cfTypes);
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
			LinkedList<LinkedList<ControlFlowEdge>> ch = getPaths(edgeProvider, firstPath, cfTypes);
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
			LinkedList<ControlFlowEdge> path, ControlFlowType... cfTypes) {

		LinkedList<LinkedList<ControlFlowEdge>> resultPaths = new LinkedList<>();
		ControlFlowEdge e = path.getLast();
		Node nextNode = edgeProvider.getNextNode(e);
		List<ControlFlowEdge> nextEdges = edgeProvider.getNextEdges(nextNode, cfTypes);

		for (ControlFlowEdge nextEdge : nextEdges) {
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
