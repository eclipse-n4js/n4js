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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4JSFlowAnalyses {
	private FlowGraph cfg;

	/**
	 * Performs the control flow analyses for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void perform(Script script) {
		Objects.requireNonNull(script);

		/*
		 * Protect ASTPostprocessing from failures of flow analyses.
		 */
		try {
			_perform(script);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void _perform(Script script) {
		cfg = ControlFlowGraphFactory.build(script);
	}

	/**
	 * @returns the underlying control flow graph
	 */
	public FlowGraph getControlFlowGraph() {
		return cfg;
	}

	/**
	 * @returns a list of all direct predecessors of cfe
	 */
	public List<ControlFlowElement> getPredecessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		Objects.requireNonNull(cfe);

		ComplexNode cn = cfg.getComplexNode(cfe);
		List<RepresentingNode> repNodes = cn.getRepresent().getPredecessors(followEdges);
		List<ControlFlowElement> cfElems = new LinkedList<>();
		for (RepresentingNode rNode : repNodes) {
			cfElems.add(rNode.getRepresentedControlFlowElement());
		}
		return cfElems;
	}

	/** @returns a list of all direct successors of cfe */
	public List<ControlFlowElement> getSuccessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		Objects.requireNonNull(cfe);

		ComplexNode cn = cfg.getComplexNode(cfe);
		List<RepresentingNode> repNodes = cn.getRepresent().getSuccessors(followEdges);
		List<ControlFlowElement> cfElems = new LinkedList<>();
		for (RepresentingNode rNode : repNodes) {
			cfElems.add(rNode.getRepresentedControlFlowElement());
		}
		return cfElems;
	}

	/** @returns true iff cfe2 is a direct successor of cfe1 */
	public boolean isSuccessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		List<ControlFlowElement> succs = getSuccessors(cfe1);
		return succs.contains(cfe2);
	}

	/** @returns true iff cfe2 is a direct predecessor of cfe1 */
	public boolean isPredecessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		List<ControlFlowElement> preds = getPredecessors(cfe1);
		return preds.contains(cfe2);
	}

	/** @returns true iff cfeTo is a transitive successor of cfeFrom */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		Path path = cfg.getPath(cfeFrom, cfeTo);
		return path.isConnecting();

		// ComplexNode cn = cfg.getComplexNode(cfeFrom);
		// boolean isTransitiveSuccessor = cn.getRepresent().isTransitiveSuccessor(cfeTo, new LinkedList<>());
		// return isTransitiveSuccessor;
	}

	/**
	 * @returns the {@link ControlFlowType} that happens between the two direct successors cfe and cfeSucc
	 */
	public ControlFlowType getControlFlowTypeToSuccessor(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		return cfg.getControlFlowTypeToSuccessor(cfe, cfeSucc);
	}

	/**
	 * @returns all the {@link ControlFlowType}s that happen between the two direct successors cfe and cfeSucc
	 */
	public TreeSet<ControlFlowType> getControlFlowTypeToSuccessors(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		return cfg.getControlFlowTypeToSuccessors(cfe, cfeSucc);
	}

	/**
	 * @returns a set of {@link ControlFlowType}s to the successors of cfe
	 */
	public TreeSet<ControlFlowType> getSuccessorsControlFlowTypes(ControlFlowElement cfe) {
		List<ControlFlowElement> succs = getSuccessors(cfe);
		TreeSet<ControlFlowType> succsCFTs = new TreeSet<>();
		for (ControlFlowElement succ : succs) {
			TreeSet<ControlFlowType> succCFTs = getControlFlowTypeToSuccessors(cfe, succ);
			succsCFTs.addAll(succCFTs);
		}
		return succsCFTs;
	}

	/**
	 * @returns a set of {@link ControlFlowType}s to the predecessors of cfe
	 */
	public TreeSet<ControlFlowType> getPredecessorsControlFlowTypes(ControlFlowElement cfe) {
		List<ControlFlowElement> preds = getPredecessors(cfe);
		TreeSet<ControlFlowType> predsCFTs = new TreeSet<>();
		for (ControlFlowElement pred : preds) {
			TreeSet<ControlFlowType> predCFTs = getControlFlowTypeToSuccessors(pred, cfe);
			predsCFTs.addAll(predCFTs);
		}
		return predsCFTs;
	}

	/**
	 * Returns the common predecessor of two {@link ControlFlowElement}s.
	 * <p/>
	 * Time: O(N)<br/>
	 * Space: O(N)<br/>
	 * N - Number of {@link ControlFlowElement}s connected to cfeA or cfeB
	 * <p/>
	 * The common predecessor is computed as follows. First, the CF graph is traversed beginning from cfeA backwards
	 * until an element is reached which has no predecessor. All elements that were visited during that traversion are
	 * marked. Second, analogous the CF graph is now traversed beginning from cfeB backwards until an element is reached
	 * which has no predecessor. If an already marked element can be found during that traversion, this element is
	 * supposed to be the common predecessor of cfeA and cfeB.
	 */
	public ControlFlowElement getCommonPredecessor(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		Objects.requireNonNull(cfeA);
		Objects.requireNonNull(cfeB);

		// step 1: traverse all predecessors, beginning from cfeA: mark each
		Set<ControlFlowElement> marked = new HashSet<>();
		List<ControlFlowElement> curCFEs = new LinkedList<>();
		curCFEs.add(cfeA);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			marked.add(cfe);
			List<ControlFlowElement> preds = getPredecessors(cfe, ControlFlowType.NonLoopTypes);
			curCFEs.addAll(preds);
		}

		// step 2: traverse all predecessors, beginning from cfeB: find mark (this is the common pred.)
		curCFEs.clear();
		curCFEs.add(cfeB);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			if (marked.contains(cfe)) {
				return cfe;
			}
			List<ControlFlowElement> preds = getPredecessors(cfe, ControlFlowType.NonLoopTypes);
			curCFEs.addAll(preds);
		}

		return null;
	}

	/**
	 * Returns an identifier for all paths between two {@link ControlFlowElement}s.
	 * <p/>
	 * Time: O(N)<br/>
	 * Space: O(N)<br/>
	 * N - Number of {@link ControlFlowElement}s connected to cfeA or cfeB
	 * <p/>
	 * The path identifier is computed as follows. First, the CF graph is traversed beginning from cfeB backwards until
	 * an element is reached which has no predecessor. All elements that were visited during that traversion are saved
	 * in P. Second, the CF graph is now traversed beginning from cfeA forwards. All elements that are visited during
	 * that second traversion are part of the path identifier iff they are contained in P.
	 */
	public String getPathIdentifier(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		Objects.requireNonNull(cfeFrom);
		Objects.requireNonNull(cfeTo);

		LinkedHashSet<ControlFlowElement> predSet = new LinkedHashSet<>();

		List<ControlFlowElement> curCFEs = new LinkedList<>();
		curCFEs.add(cfeTo);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			predSet.add(cfe);
			List<ControlFlowElement> preds = getPredecessors(cfe, ControlFlowType.NonLoopTypes);
			curCFEs.addAll(preds);
		}

		String pathString = "";
		curCFEs.clear();
		curCFEs.add(cfeFrom);
		while (!curCFEs.isEmpty()) {
			ControlFlowElement cfe = curCFEs.remove(0);
			if (predSet.contains(cfe)) {
				List<ControlFlowElement> succs = getSuccessors(cfe, ControlFlowType.NonLoopTypes);
				curCFEs.addAll(succs);
				String nameID = FGUtils.getNameID(cfe);
				pathString += nameID + "->";
			}
		}

		pathString = pathString.substring(0, pathString.length() - 2);
		return pathString;
	}

	/**
	 * @return collection of all non-control {@link ControlFlowElement}s covered by the control flow analyses
	 */
	public Collection<ControlFlowElement> getAllElements() {
		List<ControlFlowElement> allCFEs = new LinkedList<>();
		for (ComplexNode cn : cfg.getAllComplexNodes()) {
			if (!cn.isControlElement()) {
				ControlFlowElement cfe = cn.getControlFlowElement();
				allCFEs.add(cfe);
			}
		}
		return allCFEs;
	}

	public void performAnalyzes(GraphWalkerInternal... graphWalkers) {
		List<GraphWalkerInternal> graphWalkerList = Lists.newArrayList(graphWalkers);
		cfg.analyze(graphWalkerList);
	}

}
