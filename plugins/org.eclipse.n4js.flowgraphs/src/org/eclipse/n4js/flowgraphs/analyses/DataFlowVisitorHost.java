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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
abstract public class DataFlowVisitorHost extends GraphVisitorInternal {
	final Collection<DataFlowVisitor> dfVisitors;

	static private TraverseDirection[] getDirections(Collection<DataFlowVisitor> dfVisitors) {
		Set<TraverseDirection> directions = new HashSet<>();
		for (DataFlowVisitor dfv : dfVisitors) {
			directions.add(dfv.direction);
		}
		return directions.toArray(new TraverseDirection[directions.size()]);
	}

	DataFlowVisitorHost(Collection<DataFlowVisitor> dfVisitors) {
		super(getDirections(dfVisitors));
		this.dfVisitors = dfVisitors;
	}

	@Override
	protected void initializeModeInternal(TraverseDirection curDirection, ControlFlowElement curContainer) {
		requestActivation(new DataFlowExplorer());
	}

	class DataFlowExplorer extends GraphExplorerInternal {
		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new DataFlowBranch();
		}

		@Override
		protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
			DataFlowBranch mergedDFB = new DataFlowBranch();
			for (BranchWalkerInternal bwi : branchWalkers) {
				DataFlowBranch dfb = (DataFlowBranch) bwi;
				mergedDFB.simpleAssumptions.addAll(dfb.simpleAssumptions);

				for (Map.Entry<Class<? extends AssumptionWithContext>, AssumptionWithContext> entry : dfb.contextAssumptions
						.entrySet()) {

					Class<? extends AssumptionWithContext> key = entry.getKey();
					if (mergedDFB.contextAssumptions.containsKey(key)) {
						AssumptionWithContext awc = mergedDFB.contextAssumptions.get(key);
						awc.mergeWith(entry.getValue());
					} else {
						mergedDFB.contextAssumptions.put(key, entry.getValue());
					}
				}
			}
			return mergedDFB;
		}
	}

	class DataFlowBranch extends BranchWalkerInternal {
		final Set<Assumption> simpleAssumptions = new HashSet<>();
		final Map<Class<? extends AssumptionWithContext>, AssumptionWithContext> contextAssumptions = new HashMap<>();

		@Override
		protected BranchWalkerInternal fork() {
			DataFlowBranch dfb = new DataFlowBranch();
			dfb.simpleAssumptions.addAll(simpleAssumptions);
			dfb.contextAssumptions.putAll(contextAssumptions);
			return dfb;
		}

		@Override
		protected void visit(Node node) {
			ControlFlowElement cfe = node.getControlFlowElement();
			for (EffectInfo effect : node.effectInfos) {
				for (DataFlowVisitor dfv : dfVisitors) {
					dfv.visitEffect(effect, cfe);
					Collection<Assumption> newAssumptions = dfv.moveNewAssumptions();
					Iterator<Assumption> assIter = newAssumptions.iterator();
					while (assIter.hasNext()) {
						Assumption ass = assIter.next();
						if (ass instanceof AssumptionWithContext) {
							AssumptionWithContext awc = (AssumptionWithContext) ass;
							contextAssumptions.put(awc.getClass(), awc);
							assIter.remove();
						}
					}
					simpleAssumptions.addAll(newAssumptions);
				}
			}
		}
	}

}
