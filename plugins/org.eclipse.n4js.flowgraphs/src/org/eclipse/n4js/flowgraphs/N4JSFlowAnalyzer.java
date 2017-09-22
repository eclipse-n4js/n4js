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
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorAnalysis;
import org.eclipse.n4js.flowgraphs.analyses.SuccessorPredecessorAnalysis;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;

import com.google.common.collect.Lists;

/**
 * Facade for all control and data flow related methods.
 */
public class N4JSFlowAnalyzer {
	private FlowGraph cfg;
	private DirectPathAnalyses dpa;
	private GraphVisitorAnalysis gva;
	private SuccessorPredecessorAnalysis spa;

	/**
	 * Creates the control flow graphs for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void createGraphs(Script script) {
		Objects.requireNonNull(script);

		// StopWatchPrintUtil sw = new StopWatchPrintUtil("N4JSFlowAnalyses#perform");
		cfg = ControlFlowGraphFactory.build(script);
		dpa = new DirectPathAnalyses(cfg);
		gva = new GraphVisitorAnalysis(cfg);
		spa = new SuccessorPredecessorAnalysis(cfg);
		// sw.stop();
	}

	/** @return the underlying control flow graph */
	public FlowGraph getControlFlowGraph() {
		return cfg;
	}

	/** @return a list of all direct internal predecessors of cfe */
	public Set<ControlFlowElement> getPredecessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getPredecessors(cfe, followEdges);
	}

	/** @return a list of all direct external predecessors of cfe */
	public Set<ControlFlowElement> getPredecessorsSkipInternal(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getPredecessorsSkipInternal(cfe, followEdges);
	}

	/** @return a list of all direct internal successors of cfe */
	public Set<ControlFlowElement> getSuccessors(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getSuccessors(cfe, followEdges);
	}

	/** @return a list of all direct external successors of cfe */
	public Set<ControlFlowElement> getSuccessorsSkipInternal(ControlFlowElement cfe, ControlFlowType... followEdges) {
		return spa.getSuccessorsSkipInternal(cfe, followEdges);
	}

	/** @return true iff cfe2 is a direct successor of cfe1 */
	public boolean isSuccessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		return spa.isSuccessor(cfe1, cfe2);
	}

	/** @return true iff cfe2 is a direct predecessor of cfe1 */
	public boolean isPredecessor(ControlFlowElement cfe1, ControlFlowElement cfe2) {
		return spa.isPredecessor(cfe1, cfe2);
	}

	/** @return true iff cfeTo is a transitive successor of cfeFrom */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo) {
		return dpa.isTransitiveSuccessor(cfeFrom, cfeTo);
	}

	/**
	 * <b>Attention:</b> On self loops, an empty set of successor types is returned!
	 *
	 * @return all the {@link ControlFlowType}s that happen between the two direct successors cfe and cfeSucc
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
	 * Performs all given {@link GraphVisitor}s in a single run. The single run will traverse the control flow graph in
	 * the following manner. First forward beginning from the entries of every source container, then backward beginning
	 * from the exit of every source container. Finally, all remaining code elements are traversed first forward and
	 * then backward beginning from an arbitrary element.
	 */
	public void accept(GraphVisitor... graphWalkers) {
		List<GraphVisitor> graphWalkerList = Lists.newArrayList(graphWalkers);
		// StopWatchPrintUtil sw = new StopWatchPrintUtil("N4JSFlowAnalyses#analyze");
		gva.analyseScript(this, graphWalkerList);
		// sw.stop();
	}

	/** @return the containing {@link ControlFlowElement} for the given cfe. */
	public ControlFlowElement getContainer(ControlFlowElement cfe) {
		return cfg.getContainer(cfe);
	}

	/**
	 * @return all {@link ControlFlowElement}s that are containers in the {@link Script}. See
	 *         {@link FGUtils#isCFContainer(ControlFlowElement)}
	 */
	public Set<ControlFlowElement> getAllContainers() {
		return cfg.getAllContainers();
	}

	/** @return all {@link Block}s whose containers are of type {@link CatchBlock} */
	public List<Block> getCatchBlocksOfContainer(ControlFlowElement container) {
		return cfg.getCatchBlocksOfContainer(container);
	}

}
