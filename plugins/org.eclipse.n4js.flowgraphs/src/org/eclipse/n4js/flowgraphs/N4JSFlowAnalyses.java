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

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.analyses.DirectPathAnalyses;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerAnalysis;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.SuccessorPredecessorAnalysis;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
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
	private DirectPathAnalyses dpa;
	private GraphWalkerAnalysis gwa;
	private SuccessorPredecessorAnalysis spa;

	/**
	 * Performs the control flow analyses for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void perform(Script script) {
		Objects.requireNonNull(script);

		// Protect ASTPostprocessing from failures of flow analyses.
		try {
			_perform(script);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void _perform(Script script) {
		cfg = ControlFlowGraphFactory.build(script);
		dpa = new DirectPathAnalyses(cfg);
		gwa = new GraphWalkerAnalysis(cfg);
		spa = new SuccessorPredecessorAnalysis(cfg);
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
	public Set<ControlFlowElement> getPredecessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getPredecessors(cfe, followEdges);
	}

	/** @returns a list of all direct successors of cfe */
	public Set<ControlFlowElement> getSuccessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getSuccessors(cfe, followEdges);
	}

	/** @returns true iff cfe2 is a direct successor of cfe1 */
	public boolean isSuccessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		return spa.isSuccessor(cfe1, cfe2);
	}

	/** @returns true iff cfe2 is a direct predecessor of cfe1 */
	public boolean isPredecessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		return spa.isPredecessor(cfe1, cfe2);
	}

	/** @returns true iff cfeTo is a transitive successor of cfeFrom */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return dpa.isTransitiveSuccessor(cfeFrom, cfeTo);
	}

	/**
	 * <b>Attention:</b> On self loops, an empty set of successor types is returned!
	 *
	 * @returns all the {@link ControlFlowType}s that happen between the two direct successors cfe and cfeSucc
	 */
	public TreeSet<ControlFlowType> getControlFlowTypeToSuccessors(ControlFlowElement cfe, ControlFlowElement cfeSucc) {
		return dpa.getControlFlowTypeToSuccessors(cfe, cfeSucc);
	}

	/**
	 * Returns the common predecessor of two {@link ControlFlowElement}s.
	 * <p/>
	 * The common predecessor is computed as follows. First, the CF graph is traversed beginning from cfeA backwards
	 * until an element is reached which has no predecessor. All elements that were visited during that traversion are
	 * marked. Second, analogous the CF graph is now traversed beginning from cfeB backwards until an element is reached
	 * which has no predecessor. If an already marked element can be found during that traversion, this element is
	 * supposed to be the common predecessor of cfeA and cfeB.
	 * <p>
	 * The described algorithm is repeated for swapped cfeA and cfeB.
	 */
	public Set<ControlFlowElement> getCommonPredecessors(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		return dpa.getCommonPredecessors(cfeA, cfeB);
	}

	/**
	 * Returns an identifier for all paths between two {@link ControlFlowElement}s.
	 * <p/>
	 * The path identifier is computed as follows. First, the CF graph is traversed beginning from cfeB backwards until
	 * an element is reached which has no predecessor. All elements that were visited during that traversion are saved
	 * in P. Second, the CF graph is now traversed beginning from cfeA forwards. All elements that are visited during
	 * that second traversion are part of the path identifier iff they are contained in P.
	 */
	public String getPathIdentifier(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return dpa.getPathIdentifier(cfeFrom, cfeTo);
	}

	/**
	 * Performs all given {@link GraphWalkerInternal}s in a single run. The single run will traverse the control flow
	 * graph in the following manner. First forward beginning from the entries of every source container, then backward
	 * beginning from the exit of every source container. Finally, all remaining code elements are traversed first
	 * forward and then backward beginning from an arbitrary element.
	 */
	public void performAnalyzes(GraphWalkerInternal... graphWalkers) {
		List<GraphWalkerInternal> graphWalkerList = Lists.newArrayList(graphWalkers);
		gwa.analyseScript(this, graphWalkerList);
	}

}
