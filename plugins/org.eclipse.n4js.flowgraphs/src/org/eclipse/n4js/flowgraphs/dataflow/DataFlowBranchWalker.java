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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructure;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardStructureFactory;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Multimap;

/**
 * {@link BranchWalkerInternal} that is used only in data flow analyses.
 */
class DataFlowBranchWalker extends BranchWalkerInternal {
	final Map<Object, Assumption> assumptions = new HashMap<>();
	private int forkCount = 0; // memory performance: reduces number of copies

	@Override
	protected BranchWalkerInternal fork() {
		DataFlowBranchWalker dfb = new DataFlowBranchWalker();
		for (Map.Entry<Object, Assumption> entry : assumptions.entrySet()) {
			Object key = entry.getKey();
			Assumption ass = entry.getValue();

			if (ass.isOpen() && forkCount > 0) {
				ass = ass.copy();
			}
			dfb.assumptions.put(key, ass);
		}
		forkCount++;
		return dfb;
	}

	@Override
	protected void visit(Node node) {
		if (node.effectInfos.isEmpty()) {
			return;
		}

		ControlFlowElement cfe = node.getControlFlowElement();
		Multimap<Symbol, Object> assgns = getDataFlowVisitorHost().getAssignmentRelationFactory().findAssignments(cfe);

		Set<Symbol> handledDataFlowSymbols = new HashSet<>();
		for (Symbol lhs : assgns.keySet()) {
			Collection<Object> rhss = assgns.get(lhs);
			boolean handledDataFlow = handleDataflow(lhs, rhss);
			if (handledDataFlow) {
				handledDataFlowSymbols.add(lhs);
			}
		}
		for (EffectInfo effect : node.effectInfos) {
			handleVisitEffect(cfe, effect, handledDataFlowSymbols);
		}
	}

	@Override
	protected void visit(Node lastVisitNodes, Node end, ControlFlowEdge edge) {
		GuardStructure guardStructure = GuardStructureFactory.create(getSymbolFactory(), edge);
		if (guardStructure == null) {
			return;
		}

		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();

			if (ass.isOpen()) {
				for (Symbol alias : ass.aliases) {
					if (!guardStructure.hasGuards(alias)) {
						continue;
					}

					Collection<Guard> guards = guardStructure.getGuards(alias);
					for (Guard guard : guards) {
						ass.callHoldsOnGuards(guard);
					}
				}
			}
		}

		for (DataFlowVisitor dfv : getDataFlowVisitorHost().dfVisitors) {
			for (Guard guard : guardStructure.allGuards()) {
				dfv.visitGuard(guard);
			}
		}
	}

	@Override
	protected void terminate() {
		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();
			ass.checkAndFinalize();
			if (ass.isDone()) {
				assIter.remove();
			}
		}
	}

	@Override
	protected void switchedToDeadBranch() {
		for (Assumption ass : assumptions.values()) {
			ass.remove();
		}
		assumptions.clear();
	}

	private DataFlowVisitorHost getDataFlowVisitorHost() {
		return (DataFlowVisitorHost) getGraphVisitor();
	}

	private SymbolFactory getSymbolFactory() {
		return getDataFlowVisitorHost().getSymbolFactory();
	}

	private boolean handleDataflow(Symbol lhs, Collection<Object> rhss) {
		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();
			callHoldOnDataflowOnAliases(ass, lhs, rhss);
		}
		return true;
	}

	private void handleVisitEffect(ControlFlowElement cfe, EffectInfo effect, Set<Symbol> handledDataFlowSymbols) {
		callHoldsOnEffect(cfe, effect);

		for (DataFlowVisitor dfv : getDataFlowVisitorHost().dfVisitors) {
			dfv.visitEffect(effect, cfe);

			if (!handledDataFlowSymbols.contains(effect.symbol)) { // is this desired?
				Collection<Assumption> newAssumptions = dfv.moveNewAssumptions();
				Iterator<Assumption> assIter = newAssumptions.iterator();
				while (assIter.hasNext()) {
					Assumption ass = assIter.next();
					assumptions.put(ass.getKey(), ass);
				}
			}
		}
	}

	private void callHoldsOnEffect(ControlFlowElement cfe, EffectInfo effect) {
		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();

			if (ass.isApplicable(effect.symbol)) {
				ass.callHoldsOnEffect(effect, cfe);
			}
		}
	}

	private boolean callHoldOnDataflowOnAliases(Assumption ass, Symbol lhs, Collection<Object> rhss) {
		if (ass.isApplicable(lhs)) {
			ass.callHoldsOnDataflow(lhs, rhss);
			return true;
		}
		return false;
	}

}
