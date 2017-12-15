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
abstract public class DataFlowVisitorHost extends GraphVisitorInternal {
	final Collection<DataFlowVisitor> dfVisitors;
	private DataFlowExplorer dfExplorer;

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
		dfExplorer = new DataFlowExplorer();
		requestActivation(dfExplorer);
	}

	@Override
	protected void terminateMode(TraverseDirection curDirection, ControlFlowElement curContainer) {
		if (dfExplorer != null) {
			DataFlowBranch dfb = (DataFlowBranch) dfExplorer.getLastBranch();
			for (Assumption ass : dfb.simpleAssumptions) {
				ass.callHoldsAfterall();
			}
			for (Assumption ass : dfb.contextAssumptions.values()) {
				ass.callHoldsAfterall();
			}
		}
	}

	class DataFlowExplorer extends GraphExplorerInternal {
		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new DataFlowBranch(new AliasTable(getCurrentDirection()));
		}

		@Override
		protected BranchWalkerInternal joinBranchWalkers(List<BranchWalkerInternal> branchWalkers) {
			DataFlowBranch mergedDFB = new DataFlowBranch(new AliasTable(getCurrentDirection()));
			for (BranchWalkerInternal bwi : branchWalkers) {
				DataFlowBranch dfb = (DataFlowBranch) bwi;
				mergedDFB.aliasTable.mergeWith(dfb.aliasTable);
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
		final AliasTable aliasTable;
		final Set<Assumption> simpleAssumptions = new HashSet<>();
		final Map<Class<? extends AssumptionWithContext>, AssumptionWithContext> contextAssumptions = new HashMap<>();

		DataFlowBranch(AliasTable aliasTable) {
			this.aliasTable = aliasTable;
		}

		@Override
		protected BranchWalkerInternal fork() {
			DataFlowBranch dfb = new DataFlowBranch(aliasTable.getCopy());
			dfb.simpleAssumptions.addAll(simpleAssumptions);
			dfb.contextAssumptions.putAll(contextAssumptions);
			return dfb;
		}

		@Override
		protected void visit(Node node) {
			ControlFlowElement cfe = node.getControlFlowElement();
			for (EffectInfo effect : node.effectInfos) { // TODO: optimize: remove loop by passed all infos
				handleVisitEffect(cfe, effect);
				if (cfe instanceof AssignmentExpression) {
					AssignmentExpression ae = (AssignmentExpression) cfe;
					handleDataFlow(ae);
				}
			}
		}

		private void handleDataFlow(AssignmentExpression ae) {
			Expression lhs = ae.getLhs();
			Expression rhs = ae.getRhs();
			Symbol lSymbol = SymbolFactory.create(lhs);
			Symbol rSymbol = SymbolFactory.create(rhs);
			if (lSymbol != null && rSymbol != null) {
				aliasTable.addAlias(lSymbol, rSymbol);

				callHoldOnDataflow(simpleAssumptions, ae, lSymbol, rSymbol);
				callHoldOnDataflow(contextAssumptions.values(), ae, lSymbol, rSymbol);
			}
		}

		private void handleVisitEffect(ControlFlowElement cfe, EffectInfo effect) {
			for (DataFlowVisitor dfv : dfVisitors) {
				callHoldsOnEffect(simpleAssumptions, cfe, effect);
				callHoldsOnEffect(contextAssumptions.values(), cfe, effect);
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

		private void callHoldsOnEffect(Collection<? extends Assumption> assumptions, ControlFlowElement cfe,
				EffectInfo effect) {

			for (Iterator<? extends Assumption> assIter = assumptions.iterator(); assIter.hasNext();) {
				Assumption ass = assIter.next();
				if (ass.isActive()) {
					ass.callHoldsOnEffect(effect, cfe);
				}
				if (ass.isFailed()) {
					assIter.remove();
				}
			}
		}

		private void callHoldOnDataflow(Collection<? extends Assumption> assumptions, AssignmentExpression ae,
				Symbol lSymbol, Symbol rSymbol) {

			for (Iterator<? extends Assumption> assIter = assumptions.iterator(); assIter.hasNext();) {
				Assumption ass = assIter.next();
				if (ass.isActive()) {
					ass.callHoldsOnDataflow(lSymbol, rSymbol, ae);
				}
				if (ass.isFailed()) {
					assIter.remove();
				}
			}
		}
	}

}
