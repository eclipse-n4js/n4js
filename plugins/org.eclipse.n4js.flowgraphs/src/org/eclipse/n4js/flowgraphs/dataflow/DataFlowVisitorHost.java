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
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.Collection;

import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * This class connects {@link DataFlowVisitor} instances to the control flow graph. Connects means that it traverses the
 * CFG and tracks data flow operations which are delegated to all {@link DataFlowVisitor}s.
 */
public class DataFlowVisitorHost extends GraphVisitorInternal {
	final Collection<DataFlowVisitor> dfVisitors;
	private DataFlowGraphExplorer dfExplorer;
	private AssignmentRelationFactory assignmentFactory;

	/** Constructor */
	public DataFlowVisitorHost(TraverseDirection direction, Collection<DataFlowVisitor> dfVisitors) {
		super(direction);
		this.dfVisitors = dfVisitors;
	}

	/** @return reference to the {@link SymbolFactory} */
	final protected AssignmentRelationFactory getAssignmentRelationFactory() {
		return assignmentFactory;
	}

	@Override
	protected void initializeContainerInternal(ControlFlowElement curContainer) {
		for (DataFlowVisitor dfVisitor : dfVisitors) {
			dfVisitor.setSymbolFactory(getSymbolFactory());
		}
		dfExplorer = new DataFlowGraphExplorer(this);
		assignmentFactory = new AssignmentRelationFactory(getSymbolFactory());
		requestActivation(dfExplorer);
	}

	@Override
	protected void terminateContainer(ControlFlowElement curContainer) {
		DataFlowBranchWalker dfb = (DataFlowBranchWalker) dfExplorer.getLastBranch();
		for (Assumption ass : dfb.assumptions.values()) {
			ass.terminate();
		}
	}
}
