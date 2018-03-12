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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitorHost;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.factories.ControlFlowGraphFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.FlowGraph;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;

/**
 * Contains algorithms that start {@link GraphVisitorInternal}s using the {@link GraphVisitorGuideInternal}.
 */
public class GraphVisitorAnalysis {
	static private final DataCollector dcForwardAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Forward", "Flow Graphs", "Perform Analyses");
	static private final DataCollector dcBackwardAnalyses = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Backward", "Flow Graphs", "Perform Analyses");
	static private final DataCollector dcAugmentEffectInfo = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Augment Effect Information", "Flow Graphs", "Create Graphs");

	final N4JSFlowAnalyser flowAnalyzer;
	final FlowGraph cfg;
	private boolean forwardAnalysisDone = false;
	private boolean symbolsAugmented = false;

	/** Constructor */
	public GraphVisitorAnalysis(N4JSFlowAnalyser flowAnalyzer, FlowGraph cfg) {
		this.flowAnalyzer = flowAnalyzer;
		this.cfg = cfg;
	}

	/** see {@link N4JSFlowAnalyser#accept(GraphVisitor...)} */
	public void forwardAnalysis(FlowAnalyser[] flowAnalysers) {
		if (forwardAnalysisDone) {
			throw new IllegalStateException("Forward analysis can be performed only once.");
		}

		List<GraphVisitorInternal> graphVisitors = getGraphVisitors(flowAnalysers, TraverseDirection.Forward);
		GraphVisitorGuideInternal guide = new GraphVisitorGuideInternal(flowAnalyzer, graphVisitors);
		guide.init();

		for (ControlFlowElement container : cfg.getAllContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);
			traverseForwards(guide, cnContainer);
		}

		guide.terminate();
		forwardAnalysisDone = true;
	}

	/** see {@link N4JSFlowAnalyser#accept(GraphVisitor...)} */
	public void backwardAnalysis(FlowAnalyser[] flowAnalysers) {
		if (!forwardAnalysisDone) {
			throw new IllegalStateException("Forward analysis must be performed first.");
		}

		List<GraphVisitorInternal> graphVisitors = getGraphVisitors(flowAnalysers, TraverseDirection.Backward);
		GraphVisitorGuideInternal guide = new GraphVisitorGuideInternal(flowAnalyzer, graphVisitors);
		guide.init();

		for (ControlFlowElement container : cfg.getAllContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);
			traverseBackwards(guide, cnContainer);
		}

		guide.terminate();
	}

	private List<GraphVisitorInternal> getGraphVisitors(FlowAnalyser[] flowAnalysers, TraverseDirection direction) {
		List<GraphVisitorInternal> graphVisitors = new LinkedList<>();
		List<DataFlowVisitor> dataflowVisitorList = new LinkedList<>();
		for (FlowAnalyser flowAnalyser : flowAnalysers) {
			if (flowAnalyser instanceof GraphVisitorInternal) {
				GraphVisitorInternal graphVisitor = (GraphVisitorInternal) flowAnalyser;
				if (graphVisitor.getDirection() == direction) {
					graphVisitors.add(graphVisitor);
				}
			}
			if (flowAnalyser instanceof DataFlowVisitor) {
				DataFlowVisitor dataflowVisitor = (DataFlowVisitor) flowAnalyser;
				if (dataflowVisitor.getDirection() == direction) {
					dataflowVisitorList.add(dataflowVisitor);
				}
			}
		}
		if (!dataflowVisitorList.isEmpty()) {
			DataFlowVisitorHost dfvh = new DataFlowVisitorHost(direction, dataflowVisitorList);
			graphVisitors.add(dfvh);
		}
		return graphVisitors;
	}

	/** see {@link N4JSFlowAnalyser#augmentEffectInformation()} */
	public void augmentEffectInformation(SymbolFactory symbolFactory) {
		if (!symbolsAugmented) {
			Measurement msmnt = dcAugmentEffectInfo.getMeasurement("AugmentEffectInfo_" + cfg.getScriptName());
			ControlFlowGraphFactory.augmentDataflowInformation(cfg, symbolFactory);
			symbolsAugmented = true;
			msmnt.end();
		}
	}

	private void traverseForwards(GraphVisitorGuideInternal guide, ComplexNode cnContainer) {
		Measurement msmnt = dcForwardAnalyses.getMeasurement("Forward_" + cfg.getScriptName());
		guide.walkthroughForward(cnContainer);
		msmnt.end();
	}

	private void traverseBackwards(GraphVisitorGuideInternal guide, ComplexNode cnContainer) {
		Measurement msmnt = dcBackwardAnalyses.getMeasurement("Forward_" + cfg.getScriptName());
		guide.walkthroughBackward(cnContainer);
		msmnt.end();
	}

}
