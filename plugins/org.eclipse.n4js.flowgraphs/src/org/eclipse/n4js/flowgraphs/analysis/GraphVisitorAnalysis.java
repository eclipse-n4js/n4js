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

import java.util.Collection;

import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
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

	final FlowGraph cfg;

	/** Constructor */
	public GraphVisitorAnalysis(FlowGraph cfg) {
		this.cfg = cfg;
	}

	/** see {@link N4JSFlowAnalyser#accept(GraphVisitor...)} */
	public void analyseScript(N4JSFlowAnalyser flowAnalyzer, Collection<? extends GraphVisitorInternal> graphWalkers) {
		GraphVisitorGuideInternal guide = new GraphVisitorGuideInternal(flowAnalyzer, graphWalkers);
		guide.init();

		for (ControlFlowElement container : cfg.getAllContainers()) {
			ComplexNode cnContainer = cfg.getComplexNode(container);

			traverseForwards(guide, cnContainer);
			traverseBackwards(guide, cnContainer);
		}

		guide.terminate();
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
