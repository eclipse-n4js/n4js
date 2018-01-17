/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorerInternal;

/**
 * {@link GraphExplorerInternal} that is used only in data flow analyses.
 */
class DataFlowGraphExplorer extends GraphExplorerInternal {
	final protected DataFlowVisitorHost dataFlowVisitorHost;

	/** Constructor */
	public DataFlowGraphExplorer(DataFlowVisitorHost dataFlowVisitorHost) {
		this.dataFlowVisitorHost = dataFlowVisitorHost;
	}

	@Override
	protected BranchWalkerInternal firstBranchWalker() {
		return new DataFlowBranchWalker();
	}

	@Override
	protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
		DataFlowBranchWalker mergedDFB = new DataFlowBranchWalker();
		for (BranchWalkerInternal bwi : branchWalkers) {
			DataFlowBranchWalker dfb = (DataFlowBranchWalker) bwi;

			for (Map.Entry<Object, Assumption> entry : dfb.assumptions.entrySet()) {
				Object key = entry.getKey();
				if (mergedDFB.assumptions.containsKey(key)) {
					Assumption ass = mergedDFB.assumptions.get(key);
					ass.mergeWith(entry.getValue());
				} else {
					mergedDFB.assumptions.put(key, entry.getValue());
				}
			}
		}
		return mergedDFB;
	}
}
