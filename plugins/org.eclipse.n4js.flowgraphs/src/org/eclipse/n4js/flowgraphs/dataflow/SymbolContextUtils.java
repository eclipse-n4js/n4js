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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 *
 */
public class SymbolContextUtils {

	static public List<Symbol> getContextsToAlias(Symbol symbolWithContext, Symbol alias) {
		if (alias == null) {
			return Collections.emptyList();
		}

		List<Symbol> contexts = new LinkedList<>();
		contexts.add(symbolWithContext);

		while (symbolWithContext.getContextSymbol() != null) {
			symbolWithContext = symbolWithContext.getContextSymbol();
			if (alias.equals(symbolWithContext)) {
				return contexts;
			}
			contexts.add(0, symbolWithContext);
		}

		return Collections.emptyList();
	}

	static public Pair<Symbol, List<Symbol>> getSymbolAndContextsToAlias(Iterable<Symbol> symbolWithContexts,
			Symbol alias) {

		Symbol matchedAlias = null;
		List<Symbol> contextList = Collections.emptyList();

		for (Symbol symbolWithContext : symbolWithContexts) {
			List<Symbol> contextListTmp = getContextsToAlias(symbolWithContext, alias);
			if (!contextListTmp.isEmpty()) {
				matchedAlias = symbolWithContext;
				contextList = contextListTmp;
			}
		}

		Pair<Symbol, List<Symbol>> symbolAndContexts = Pair.of(matchedAlias, contextList);
		return symbolAndContexts;
	}

	static public Pair<Symbol, Symbol> getContextChangedSymbol(Iterable<Symbol> symbolWithContexts, Symbol lSymbol,
			Expression rExpression) {

		Pair<Symbol, List<Symbol>> symbolAndContexts = getSymbolAndContextsToAlias(symbolWithContexts, lSymbol);
		Symbol matchedAlias = symbolAndContexts.getKey();
		List<Symbol> contextList = symbolAndContexts.getValue();
		Symbol synthSymbol = null;

		if (!contextList.isEmpty()) {
			synthSymbol = SymbolFactory.create(rExpression, contextList);
		}

		Pair<Symbol, Symbol> cSymbols = Pair.of(matchedAlias, synthSymbol);
		return cSymbols;
	}
}
