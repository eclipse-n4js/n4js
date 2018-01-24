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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * {@link BranchWalkerInternal} that is used only in data flow analyses.
 */
class DataFlowBranchWalker extends BranchWalkerInternal {
	final Map<Object, Assumption> assumptions = new HashMap<>();
	private int forkCount = 0;

	@Override
	protected BranchWalkerInternal fork() {
		DataFlowBranchWalker dfb = new DataFlowBranchWalker();
		for (Map.Entry<Object, Assumption> entry : assumptions.entrySet()) {
			Object key = entry.getKey();
			Assumption ass = entry.getValue();
			if (forkCount > 0) {
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
		List<AssignmentRelation> ars = getDataFlowVisitorHost().getAssignmentRelationFactory().findAssignments(cfe);

		Set<Symbol> handledDataFlowSymbols = new HashSet<>();
		for (AssignmentRelation ar : ars) {
			boolean handledDataFlow = handleDataflow(ar);
			if (handledDataFlow) {
				handledDataFlowSymbols.add(ar.leftSymbol);
			}
		}
		for (EffectInfo effect : node.effectInfos) {
			if (!handledDataFlowSymbols.contains(effect.symbol)) {
				handleVisitEffect(cfe, effect);
			}
		}
	}

	@Override
	protected void visit(Node lastVisitNodes, Node end, ControlFlowEdge edge) {
		GuardStructure guardStructure = new GuardStructureFactory(getSymbolFactory()).create(edge);
		if (guardStructure == null) {
			return;
		}

		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();

			for (Symbol alias : ass.aliases) {
				if (!guardStructure.guards.containsKey(alias)) {
					continue;
				}

				List<Guard> guards = guardStructure.guards.get(alias);
				for (Guard guard : guards) {
					ass.callHoldsOnGuard(guard);
				}
			}

			if (!ass.isActive()) {
				assIter.remove();
			}
		}
	}

	@Override
	protected void switchedToDeadBranch() {
		for (Assumption ass : assumptions.values()) {
			ass.deactivate();
		}
		assumptions.clear();
	}

	private DataFlowVisitorHost getDataFlowVisitorHost() {
		return (DataFlowVisitorHost) getExplorer().getGraphVisitorInternal();
	}

	private SymbolFactory getSymbolFactory() {
		return getDataFlowVisitorHost().getSymbolFactory();
	}

	private boolean handleDataflow(AssignmentRelation ar) {
		for (Iterator<Assumption> assIter = assumptions.values().iterator(); assIter.hasNext();) {
			Assumption ass = assIter.next();

			callHoldOnDataflowOnAliases(ass, ar);

			// ******************************************************************************************
			// The following supports intra-procedural aliases.
			// It is disabled since its use is unsound because side effects of other aliases are ignored.
			// It is revisited when implementing TypeSta
			// ******************************************************************************************
			//
			// boolean callPerformed = callHoldOnDataflowOnAliases(ass, ar);
			// if (!callPerformed) {
			// callPerformed = callHoldOnDataflowOnFailedStructuralAliases(ass, ar);
			// }
			// if (!callPerformed) {
			// callPerformed = callHoldOnDataflowOnStructuralAliases(ass, ar);
			// }
			// if still (!callPerformed): not important

			if (!ass.isActive()) {
				assIter.remove();
			}
		}
		return true;
	}

	private void handleVisitEffect(ControlFlowElement cfe, EffectInfo effect) {
		callHoldsOnEffect(cfe, effect);

		for (DataFlowVisitor dfv : getDataFlowVisitorHost().dfVisitors) {
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

			if (ass.aliases.contains(effect.symbol)) {
				ass.callHoldsOnEffect(effect, cfe); // call for plain aliases

			} else {
				for (Symbol alias : ass.aliases) {
					if (effect.symbol.isStrucuralAlias(alias)) {
						ass.callHoldsOnEffect(effect, cfe); // also called for structural aliases
						break;
					}
				}
			}

			if (!ass.isActive()) {
				assIter.remove();
			}
		}
	}

	private boolean callHoldOnDataflowOnAliases(Assumption ass, AssignmentRelation ar) {
		if (ass.aliases.contains(ar.leftSymbol)) {
			ass.callHoldsOnDataflow(ar);
			return true;
		}
		return false;
	}

	// ******************************************************************************************
	// The following supports intra-procedural aliases.
	// It is disabled since its use is unsound because side effects of other aliases are ignored.
	// It is revisited when implementing TypeStates.
	// ******************************************************************************************
	//
	// private boolean callHoldOnDataflowOnStructuralAliases(Assumption ass, AssignmentRelation ar) {
	// Symbol lSymbol = ar.leftSymbol;
	// Expression assgnExpr = ar.assignedValue;
	// Pair<Symbol, Symbol> cSymbols = getContextChangedSymbol(getSymbolFactory(), ass.aliases, lSymbol, assgnExpr);
	// Symbol newLSymbol = cSymbols.getKey();
	// Symbol newRSymbol = cSymbols.getValue();
	// if (newRSymbol != null) {
	// AssignmentRelation newAR = new AssignmentRelation(newLSymbol, newRSymbol, null);
	// ass.callHoldsOnDataflow(newAR);
	// return true;
	// }
	//
	// cSymbols = getContextChangedSymbol(getSymbolFactory(), ass.failingStructuralAliases, lSymbol, assgnExpr);
	// newLSymbol = cSymbols.getKey();
	// newRSymbol = cSymbols.getValue();
	// if (newRSymbol != null) {
	// AssignmentRelation newAR = new AssignmentRelation(newLSymbol, newRSymbol, null);
	// ass.callHoldsOnDataflow(newAR);
	// return true;
	// }
	// return false;
	// }
	//
	// private boolean callHoldOnDataflowOnFailedStructuralAliases(Assumption ass, AssignmentRelation ar) {
	// Pair<Symbol, List<Symbol>> lSCA = SymbolContextUtils
	// .getSymbolAndContextsToAlias(ass.failingStructuralAliases, ar.leftSymbol);
	//
	// if (lSCA.getKey() != null) {
	// ass.failOnStructuralAlias(lSCA.getKey());
	// return true;
	// }
	//
	// Pair<Symbol, List<Symbol>> rCSA = SymbolContextUtils
	// .getSymbolAndContextsToAlias(ass.failingStructuralAliases, ar.rightSymbol);
	//
	// if (rCSA.getKey() != null) {
	// ass.failOnStructuralAlias(rCSA.getKey());
	// return true;
	// }
	//
	// return false;
	// }

}
