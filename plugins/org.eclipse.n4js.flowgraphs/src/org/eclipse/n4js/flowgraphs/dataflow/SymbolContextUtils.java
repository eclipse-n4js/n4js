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
 * Utility functions to find aliases in {@link Symbol}s that have other {@link Symbol}s as a context.
 */
public class SymbolContextUtils {

	/**
	 * Given a {@link Symbol} with context, this function iterated through these contexts. In case one of the context
	 * {@link Symbol}s is an alias of the given alias {@link Symbol}, the list of context that was visited is returned.
	 *
	 * @param symbolWithContext
	 *            {@link Symbol} that has a context
	 * @param alias
	 *            {@link Symbol} to search for
	 * @return a list of context {@link Symbol}s
	 */
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

	/**
	 * Same behavior as {@link #getContextsToAlias(Symbol, Symbol)}, but searched the given list. Moreover, the
	 * {@link Symbol} that matched is returned as the {@link Pair#getKey()} entry. The {@link Pair#getValue()} is the
	 * context list ({@link #getContextsToAlias(Symbol, Symbol)}).
	 *
	 * @param symbolsWithContexts
	 *            a list of {@link Symbol} that have a context
	 * @param alias
	 *            {@link Symbol} to search for
	 * @return a pair where the key is the matched alias and the value is the list of context symbols
	 */
	static public Pair<Symbol, List<Symbol>> getSymbolAndContextsToAlias(Iterable<Symbol> symbolsWithContexts,
			Symbol alias) {

		Symbol matchedAlias = null;
		List<Symbol> contextList = Collections.emptyList();

		for (Symbol symbolWithContext : symbolsWithContexts) {
			List<Symbol> contextListTmp = getContextsToAlias(symbolWithContext, alias);
			if (!contextListTmp.isEmpty()) {
				matchedAlias = symbolWithContext;
				contextList = contextListTmp;
			}
		}

		Pair<Symbol, List<Symbol>> symbolAndContexts = Pair.of(matchedAlias, contextList);
		return symbolAndContexts;
	}

	/**
	 * If possible, this function returns a {@link Pair} of two symbols. The key element is the one context
	 * {@link Symbol} of the given symbols of {@code symbolsWithContexts} that matched with the given {@link Symbol}
	 * {@code alias}. The value element is a newly created {@link Symbol} that has at least one context {@link Symbol}.
	 * The base context symbol of the newly created symbol is the given {@code baseExpression}.
	 *
	 * @param symbolsWithContexts
	 *            a list of {@link Symbol} that have a context
	 * @param alias
	 *            {@link Symbol} to search for
	 * @param baseExpression
	 *            base expression that will be the base context of the synthesized symbol
	 * @return a pair where the key is the matched alias {@link Symbol} and the value is the synthesized {@link Symbol}
	 */
	static public Pair<Symbol, Symbol> getContextChangedSymbol(Iterable<Symbol> symbolsWithContexts, Symbol alias,
			Expression baseExpression) {

		Pair<Symbol, List<Symbol>> symbolAndContexts = getSymbolAndContextsToAlias(symbolsWithContexts, alias);
		Symbol matchedAlias = symbolAndContexts.getKey();
		List<Symbol> contextList = symbolAndContexts.getValue();
		Symbol synthSymbol = null;

		if (!contextList.isEmpty()) {
			synthSymbol = SymbolFactory.create(baseExpression, contextList);
		}

		Pair<Symbol, Symbol> cSymbols = Pair.of(matchedAlias, synthSymbol);
		return cSymbols;
	}

}
