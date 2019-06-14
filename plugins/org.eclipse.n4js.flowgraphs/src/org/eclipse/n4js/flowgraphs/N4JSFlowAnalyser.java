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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.analysis.DirectPathAnalyses;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorAnalysis;
import org.eclipse.n4js.flowgraphs.analysis.SuccessorPredecessorAnalysis;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.utils.N4JSDataCollectors;

/**
 * Facade for all control and data flow related methods.
 */
public class N4JSFlowAnalyser {
	static private final Logger logger = Logger.getLogger(N4JSFlowAnalyser.class);

	private Callable<?> cancelledChecker;
	private FlowGraph cfg;
	private SymbolFactory symbolFactory;
	private DirectPathAnalyses dpa;
	private GraphVisitorAnalysis gva;
	private SuccessorPredecessorAnalysis spa;

	/** Constructor */
	public N4JSFlowAnalyser() {
		this(null);
	}

	/**
	 * Constructor.
	 *
	 * @param cancelledChecker
	 *            is called in the main loop to react on cancel events. Can be null.
	 */
	public N4JSFlowAnalyser(Callable<?> cancelledChecker) {
		this.cancelledChecker = cancelledChecker;
	}

	/**
	 * Creates the control flow graphs for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void createGraphs(Script script) {
		Objects.requireNonNull(script);
		String uriString = script.eResource().getURI().toString();

		try (Measurement m1 = N4JSDataCollectors.dcFlowGraphs.getMeasurement("flowGraphs_" + uriString);
				Measurement m2 = N4JSDataCollectors.dcCreateGraph.getMeasurement("createGraph_" + uriString);) {

			symbolFactory = new SymbolFactory();
			cfg = ControlFlowGraphFactory.build(script);
			dpa = new DirectPathAnalyses(cfg);
			gva = new GraphVisitorAnalysis(this, cfg);
			spa = new SuccessorPredecessorAnalysis(cfg);
		}

		augmentEffectInformation();
	}

	/**
	 * @see #createGraphs(Script)
	 *
	 * @param newCancelledChecker
	 *            is called in the main loop to react on cancel events. Can be null.
	 */
	public void createGraphs(Script script, Callable<?> newCancelledChecker) {
		this.cancelledChecker = newCancelledChecker;
		this.createGraphs(script);
	}

	/** Checks if the user hit the cancel button and if so, a RuntimeException is thrown. */
	public void checkCancelled() {
		if (cancelledChecker == null)
			return;

		try {
			cancelledChecker.call();
		} catch (OperationCanceledException e) {
			throw e;
		} catch (Exception e) {
			logger.warn("Unknown exception", e);
		}
	}

	/** @return the underlying control flow graph */
	public FlowGraph getControlFlowGraph() {
		return cfg;
	}

	/** @return the {@link SymbolFactory} */
	public SymbolFactory getSymbolFactory() {
		return symbolFactory;
	}

	/** @return a list of all direct internal predecessors of cfe */
	public Set<ControlFlowElement> getPredecessors(ControlFlowElement cfe) {
		return spa.getPredecessors(cfe);
	}

	/** @return a list of all direct external predecessors of cfe */
	public Set<ControlFlowElement> getPredecessorsSkipInternal(ControlFlowElement cfe) {
		return spa.getPredecessorsSkipInternal(cfe);
	}

	/** @return a list of all direct internal successors of cfe */
	public Set<ControlFlowElement> getSuccessors(ControlFlowElement cfe) {
		return spa.getSuccessors(cfe);
	}

	/** @return a list of all direct external successors of cfe */
	public Set<ControlFlowElement> getSuccessorsSkipInternal(ControlFlowElement cfe) {
		return spa.getSuccessorsSkipInternal(cfe);
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
		return dpa.isTransitiveSuccessor(cfeFrom, cfeTo, null);
	}

	/**
	 * @return true iff cfeTo is a transitive successor of cfeFrom and the connecting path does not include cfeNotVia
	 */
	public boolean isTransitiveSuccessor(ControlFlowElement cfeFrom, ControlFlowElement cfeTo,
			ControlFlowElement cfeNotVia) {
		return dpa.isTransitiveSuccessor(cfeFrom, cfeTo, cfeNotVia);
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
	 * until an element is reached which has no predecessor. All elements that were visited during that traversal are
	 * marked. Second, analogous the CF graph is now traversed beginning from cfeB backwards until an element is reached
	 * which has no predecessor. If an already marked element can be found during that traversal, this element is
	 * supposed to be the common predecessor of cfeA and cfeB.
	 * <p>
	 * The described algorithm is repeated for swapped cfeA and cfeB.
	 */
	public Set<ControlFlowElement> getCommonPredecessors(ControlFlowElement cfeA, ControlFlowElement cfeB) {
		return dpa.getCommonPredecessors(cfeA, cfeB);
	}

	/**
	 * Performs all given {@link FlowAnalyser}s in a single run. The single run will traverse the control flow graph in
	 * the following manner. First forward beginning from the entries of every source container, then backward beginning
	 * from the exit of every source container. Finally, all remaining code elements are traversed first forward and
	 * then backward beginning from an arbitrary element.
	 */
	public void accept(FlowAnalyser... flowAnalysers) {
		acceptForwardAnalysers(flowAnalysers);
		acceptBackwardAnalysers(flowAnalysers);
	}

	/**
	 * @see #accept(FlowAnalyser...)
	 *
	 * @param newCancelledChecker
	 *            is called in the main loop to react on cancel events. Can be null.
	 */
	public void accept(Callable<?> newCancelledChecker, FlowAnalyser... flowAnalysers) {
		this.cancelledChecker = newCancelledChecker;
		this.accept(flowAnalysers);
	}

	/**
	 * Performs all given {@link FlowAnalyser}s in a single run. Only instances of {@link TraverseDirection#Forward} are
	 * supported. This analysis must be performed before {@link #acceptBackwardAnalysers(FlowAnalyser...)} is invoked.
	 */
	public void acceptForwardAnalysers(FlowAnalyser... flowAnalysers) {
		String name = cfg.getScriptName();
		try (Measurement m1 = N4JSDataCollectors.dcFlowGraphs.getMeasurement("flowGraphs_" + name);
				Measurement m2 = N4JSDataCollectors.dcPerformAnalyses.getMeasurement("performAnalysis_" + name);
				Measurement m3 = N4JSDataCollectors.dcForwardAnalyses.getMeasurement("Forward_" + name);) {

			gva.forwardAnalysis(flowAnalysers);
		}
	}

	/**
	 * @see #acceptForwardAnalysers(FlowAnalyser...)
	 *
	 * @param newCancelledChecker
	 *            is called in the main loop to react on cancel events. Can be null.
	 */
	public void acceptForwardAnalysers(Callable<?> newCancelledChecker, FlowAnalyser... flowAnalysers) {
		this.cancelledChecker = newCancelledChecker;
		this.acceptForwardAnalysers(flowAnalysers);
	}

	/**
	 * Performs all given {@link FlowAnalyser}s in a single run. Only instances of {@link TraverseDirection#Backward}
	 * are supported. This analysis must be performed after {@link #acceptForwardAnalysers(FlowAnalyser...)} was
	 * performed.
	 */
	public void acceptBackwardAnalysers(FlowAnalyser... flowAnalysers) {
		String name = cfg.getScriptName();
		try (Measurement m1 = N4JSDataCollectors.dcFlowGraphs.getMeasurement("flowGraphs_" + name);
				Measurement m2 = N4JSDataCollectors.dcPerformAnalyses.getMeasurement("performAnalysis_" + name);
				Measurement m3 = N4JSDataCollectors.dcBackwardAnalyses.getMeasurement("Backward_" + name);) {

			gva.backwardAnalysis(flowAnalysers);
		}
	}

	/**
	 * @see #acceptBackwardAnalysers(FlowAnalyser...)
	 *
	 * @param newCancelledChecker
	 *            is called in the main loop to react on cancel events. Can be null.
	 */
	public void acceptBackwardAnalysers(Callable<?> newCancelledChecker, FlowAnalyser... flowAnalysers) {
		this.cancelledChecker = newCancelledChecker;
		this.acceptBackwardAnalysers(flowAnalysers);
	}

	/** Augments the flow graph with effect and symbol information. */
	public void augmentEffectInformation() {
		String name = cfg.getScriptName();
		try (Measurement m1 = N4JSDataCollectors.dcFlowGraphs.getMeasurement("flowGraphs_" + name);
				Measurement m2 = N4JSDataCollectors.dcCreateGraph.getMeasurement("createGraph_" + name);
				Measurement m = N4JSDataCollectors.dcAugmentEffectInfo.getMeasurement("AugmentEffectInfo_" + name);) {

			gva.augmentEffectInformation(symbolFactory);
		}
	}

	/** @return the containing {@link ControlFlowElement} for the given cfe. */
	public ControlFlowElement getContainer(ControlFlowElement cfe) {
		return cfg.getContainer(cfe);
	}

	/**
	 * @return all {@link ControlFlowElement}s that are containers in the {@link Script}. See
	 *         {@link FGUtils#isCFContainer(EObject)}
	 */
	public Collection<ControlFlowElement> getAllContainers() {
		return cfg.getAllContainers();
	}

}
