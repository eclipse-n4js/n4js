/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow.guards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Merges guards when merging branches while walking through the control flow graph
 */
public class GuardMerger {

	/** Merges two sets of {@link InstanceofGuard}s */
	static public Collection<InstanceofGuard> mergeInstanceofGuards(SymbolFactory symbolFactory,
			Collection<InstanceofGuard> guards1, Collection<InstanceofGuard> guards2) {

		Collection<InstanceofGuard> mergedGuards = new ArrayList<>(guards1);
		mergedGuards.retainAll(guards2);

		Collection<InstanceofGuard> guards1copy = new ArrayList<>(guards1);
		Collection<InstanceofGuard> guards2copy = new ArrayList<>(guards2);
		guards1copy.removeAll(mergedGuards);
		guards2copy.removeAll(mergedGuards);

		if (guards1copy.isEmpty() || guards2copy.isEmpty()) {
			return mergedGuards;
		}

		Multimap<Symbol, InstanceofGuard> symbolGuards1 = HashMultimap.create();
		Multimap<Symbol, InstanceofGuard> symbolGuards2 = HashMultimap.create();

		for (InstanceofGuard ig : guards1copy) {
			if (ig.asserts == GuardAssertion.AlwaysHolds) {
				symbolGuards1.put(ig.getSymbol(), ig);
			}
		}
		for (InstanceofGuard ig : guards2copy) {
			if (ig.asserts == GuardAssertion.AlwaysHolds) {
				symbolGuards2.put(ig.getSymbol(), ig);
			}
		}
		symbolGuards1.keySet().retainAll(symbolGuards2.keySet());
		symbolGuards2.keySet().retainAll(symbolGuards1.keySet());

		if (symbolGuards1.isEmpty() || symbolGuards2.isEmpty()) {
			return mergedGuards;
		}

		for (Symbol symbol : symbolGuards1.keySet()) {
			Collection<InstanceofGuard> sguards1 = symbolGuards1.get(symbol);
			Collection<InstanceofGuard> sguards2 = symbolGuards2.get(symbol);

			List<Expression> newTypeExpressions = new ArrayList<>();
			for (InstanceofGuard sg : sguards1) {
				newTypeExpressions.addAll(sg.typeIdentifiers);
			}
			for (InstanceofGuard sg : sguards2) {
				newTypeExpressions.addAll(sg.typeIdentifiers);
			}

			InstanceofGuard someGuard = sguards1.iterator().next();
			Expression someCondition = someGuard.condition;
			ControlFlowElement someSymbolCFE = someGuard.symbolCFE;
			InstanceofGuard mergedGuard = new InstanceofGuard(someCondition, GuardAssertion.AlwaysHolds,
					someSymbolCFE, newTypeExpressions);
			mergedGuard.initSymbol(symbolFactory);

			mergedGuards.add(mergedGuard);
		}

		return mergedGuards;
	}

}
