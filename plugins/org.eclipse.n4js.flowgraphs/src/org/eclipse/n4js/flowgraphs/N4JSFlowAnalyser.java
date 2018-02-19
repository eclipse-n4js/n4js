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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.analysis.DirectPathAnalyses;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorAnalysis;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analysis.SuccessorPredecessorAnalysis;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitorHost;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;

/**
 * Facade for all control and data flow related methods.
 */
public class N4JSFlowAnalyser {
	static private final Logger LOGGER = Logger.getLogger(N4JSFlowAnalyser.class);
	static private final DataCollector dcFlowGraphs = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Flow Graphs");
	static private final DataCollector dcCreateGraph = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Create Graphs", "Flow Graphs");
	static private final DataCollector dcPerformAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Perform Analyses", "Flow Graphs");

	private final Callable<Void> cancelledChecker;
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
	public N4JSFlowAnalyser(Callable<Void> cancelledChecker) {
		this.cancelledChecker = cancelledChecker;
	}

	/**
	 * Creates the control flow graphs for all {@link ControlFlowElement}s in the given {@link Script}.
	 * <p/>
	 * Never completes abruptly, i.e. throws an exception.
	 */
	public void createGraphs(Script script, boolean enableDataFlow) {
		Objects.requireNonNull(script);
		String uriString = script.eResource().getURI().toString();
		Measurement msmnt1 = dcFlowGraphs.getMeasurement("flowGraphs_" + uriString);
		Measurement msmnt2 = dcCreateGraph.getMeasurement("createGraph_" + uriString);
		symbolFactory = new SymbolFactory();
		cfg = ControlFlowGraphFactory.build(symbolFactory, script, enableDataFlow);
		dpa = new DirectPathAnalyses(cfg);
		gva = new GraphVisitorAnalysis(cfg);
		spa = new SuccessorPredecessorAnalysis(cfg);
		msmnt2.end();
		msmnt1.end();
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
			LOGGER.warn("Unknown exception", e);
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
		List<GraphVisitorInternal> controlflowVisitorList = new LinkedList<>();
		List<DataFlowVisitor> dataflowVisitorList = new LinkedList<>();
		for (FlowAnalyser flowAnalyser : flowAnalysers) {
			if (flowAnalyser instanceof GraphVisitorInternal) {
				controlflowVisitorList.add((GraphVisitorInternal) flowAnalyser);
			}
			if (flowAnalyser instanceof DataFlowVisitor) {
				dataflowVisitorList.add((DataFlowVisitor) flowAnalyser);
			}
		}
		if (!dataflowVisitorList.isEmpty()) {
			DataFlowVisitorHost dfvh = new DataFlowVisitorHost(dataflowVisitorList);
			controlflowVisitorList.add(dfvh);
		}

		Measurement msmnt1 = dcFlowGraphs.getMeasurement("flowGraphs_" + cfg.getScriptName());
		Measurement msmnt2 = dcPerformAnalyses.getMeasurement("createGraph_" + cfg.getScriptName());
		gva.analyseScript(this, controlflowVisitorList);
		msmnt2.end();
		msmnt1.end();
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
