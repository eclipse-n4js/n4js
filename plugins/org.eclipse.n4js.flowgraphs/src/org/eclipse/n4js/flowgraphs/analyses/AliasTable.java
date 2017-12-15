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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.flowgraphs.model.Symbol;

/**
 *
 */
public class AliasTable {
	private final Map<Symbol, Set<Symbol>> aliases = new HashMap<>();
	private final TraverseDirection direction;

	/** Constructor. */
	public AliasTable(TraverseDirection direction) {
		this.direction = direction;
	}

	/** @return a copy of this instance */
	public AliasTable getCopy() {
		AliasTable copy = new AliasTable(direction);
		copy.aliases.putAll(aliases);
		return copy;
	}

	/** Merges the given {@link AliasTable} into this one */
	public void mergeWith(AliasTable aliasTable) {
		for (Map.Entry<Symbol, Set<Symbol>> entry : aliasTable.aliases.entrySet()) {
			Symbol symbol = entry.getKey();
			Set<Symbol> aliasedSymbols = entry.getValue();
			for (Symbol aliasedSymbol : aliasedSymbols) {
				addAlias(symbol, aliasedSymbol);
			}
		}
	}

	/** Adds an alias to the table */
	public void addAlias(Symbol lhs, Symbol rhs) {
		if (direction == TraverseDirection.Forward) {
			if (!aliases.containsKey(lhs)) {
				aliases.put(lhs, new HashSet<>());
			}
			aliases.get(lhs).clear();
			aliases.get(lhs).add(rhs);
		}
		if (direction == TraverseDirection.Backward) {
			if (!aliases.containsKey(lhs)) {
				aliases.put(lhs, new HashSet<>());
				aliases.get(lhs).add(rhs);
			}
		}
	}

	/** @return all aliases of the given {@link Symbol} */
	public Set<Symbol> getDirectAliases(Symbol symbol) {
		if (!aliases.containsKey(symbol)) {
			return Collections.emptySet();
		}
		return aliases.get(symbol);
	}

	/** @return all transitive aliases of the given {@link Symbol} */
	public Set<Symbol> getTransitiveAliases(Symbol symbol) {
		Set<Symbol> transAliases = new HashSet<>();
		Set<Symbol> nextAliases = new HashSet<>();
		nextAliases.addAll(getDirectAliases(symbol));
		while (!nextAliases.isEmpty()) {
			Symbol nextSymbol = nextAliases.iterator().next();
			nextAliases.remove(nextSymbol);
			if (!transAliases.contains(nextSymbol)) { // cycle?
				nextAliases.addAll(getDirectAliases(nextSymbol));
			}
			transAliases.add(nextSymbol);
		}

		return transAliases;
	}

}
