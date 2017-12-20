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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.factories.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;

/**
 *
 */
public class DataFlowVisitorHost extends GraphVisitorInternal {
	final Collection<DataFlowVisitor> dfVisitors;
	private DataFlowExplorer dfExplorer;

	static private TraverseDirection[] getDirections(Collection<DataFlowVisitor> dfVisitors) {
		Set<TraverseDirection> directions = new HashSet<>();
		for (DataFlowVisitor dfv : dfVisitors) {
			directions.add(dfv.direction);
		}
		return directions.toArray(new TraverseDirection[directions.size()]);
	}

	/** Constructor */
	public DataFlowVisitorHost(DataFlowVisitor... dfVisitors) {
		this(Arrays.asList(dfVisitors));
	}

	/** Constructor */
	public DataFlowVisitorHost(Collection<DataFlowVisitor> dfVisitors) {
		super(getDirections(dfVisitors));
		this.dfVisitors = dfVisitors;
	}

	@Override
	protected void initializeModeInternal(TraverseDirection curDirection, ControlFlowElement curContainer) {
		dfExplorer = new DataFlowExplorer();
		requestActivation(dfExplorer);
	}

	@Override
	protected void terminateMode(TraverseDirection curDirection, ControlFlowElement curContainer) {
		if (dfExplorer != null) {
			DataFlowBranch dfb = (DataFlowBranch) dfExplorer.getLastBranch();
			for (Assumption ass : dfb.assumptions.values()) {
				ass.callHoldsAfterall();
			}
		}
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

	class DataFlowBranch extends BranchWalkerInternal {
		final Map<Object, Assumption> assumptions = new HashMap<>();

		@Override
		protected BranchWalkerInternal fork() {
			DataFlowBranch dfb = new DataFlowBranch();
			for (Map.Entry<Object, Assumption> entry : assumptions.entrySet()) {
				Object key = entry.getKey();
				Assumption ass = entry.getValue();
				dfb.assumptions.put(key, ass.copy());
			}
			return dfb;
		}

		@Override
		protected void visit(Node node) {
			ControlFlowElement cfe = node.getControlFlowElement();
			for (EffectInfo effect : node.effectInfos) { // TODO: optimize: remove loop by passed all infos
				boolean handledDataFlow = handleDataFlow(cfe);
				if (!handledDataFlow) {
					handleVisitEffect(cfe, effect);
				}
			}
		}

		private boolean handleDataFlow(ControlFlowElement cfe) {
			if (cfe instanceof AssignmentExpression) {
				AssignmentExpression ae = (AssignmentExpression) cfe;
				Expression lhs = ae.getLhs();
				Expression rhs = ae.getRhs();
				Symbol lSymbol = SymbolFactory.create(lhs);
				Symbol rSymbol = SymbolFactory.create(rhs);
				if (lSymbol != null && rSymbol != null && rSymbol.isVariableSymbol()) {
					callHoldOnDataflow(ae, lSymbol, rSymbol);
					return true;
				}
			}
			return false;
		}

		private void handleVisitEffect(ControlFlowElement cfe, EffectInfo effect) {
			for (DataFlowVisitor dfv : dfVisitors) {
				callHoldsOnEffect(cfe, effect);
				dfv.visitEffect(effect, cfe);

				Collection<Assumption> newAssumptions = dfv.moveNewAssumptions();
				Iterator<Assumption> assIter = newAssumptions.iterator();
				while (assIter.hasNext()) {
					Assumption ass = assIter.next();
					assumptions.put(ass.key, ass);
				}
			}
		}

		private void callHoldsOnEffect(ControlFlowElement cfe, EffectInfo effect) {
			for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
				Assumption ass = assIter.next();
				if (ass.isActive() && ass.aliases.contains(effect.symbol)) {
					ass.callHoldsOnEffect(effect, cfe);
				}
				if (ass.isFailed()) {
					assIter.remove();
				}
			}
		}

		private void callHoldOnDataflow(AssignmentExpression ae, Symbol lSymbol, Symbol rSymbol) {
			for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
				Assumption ass = assIter.next();
				if (ass.isActive() && ass.aliases.contains(lSymbol)) {
					ass.callHoldsOnDataflow(lSymbol, rSymbol, ae);
				}
				if (ass.isFailed()) {
					assIter.remove();
				}
			}
		}
	}

}
