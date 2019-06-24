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
package org.eclipse.n4js.flowgraphs.dataflow.guards;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A {@link GuardStructure} is a complete condition of an {@link IfStatement}, {@link ConditionalExpression} or the left
 * hand side of a {@link BinaryLogicalExpression}. The top expression of a {@link GuardStructure}s can contain nested
 * conditions and therefore can contain {@link Guard}s. Moreover, for the else branch, this top expression can be
 * negated.
 */
public class GuardStructure {

	/** Top expression */
	final Expression condition;
	/** true iff the {@link GuardStructure} is used for reasoning about the else branch */
	final boolean negate;
	/***/
	final private List<Guard> guardList;
	/**
	 * All guards within this {@link GuardStructure}. Mapping from guarded {@link Symbol} to the symbols {@link Guard}
	 */
	final private Multimap<Symbol, Guard> guards = HashMultimap.create();

	/** Constructor. */
	GuardStructure(SymbolFactory symbolFactory, Expression condition, boolean negate) {
		this.condition = condition;
		this.negate = negate;
		this.guardList = getGuardList();
		this.initSymbols(symbolFactory);
	}

	/** This will initialize the symbol of all guards, and delete guards where a {@link Symbol} cannot be found */
	private void initSymbols(SymbolFactory symbolFactory) {
		for (Iterator<Guard> guardsIter = guardList.iterator(); guardsIter.hasNext();) {
			Guard guard = guardsIter.next();
			guard.initSymbol(symbolFactory);
			Symbol symbol = guard.getSymbol();
			if (symbol != null) {
				guards.put(symbol, guard);
			} else {
				guardsIter.remove();
			}
		}
	}

	/** @return all {@link Guard} that refer to the given {@link Symbol} */
	public Collection<Guard> getGuards(Symbol symbol) {
		return guards.get(symbol);
	}

	/** @return true iff there are guards for the given {@link Symbol} */
	public boolean hasGuards(Symbol symbol) {
		return guards.containsKey(symbol);
	}

	/**
	 * Note the side effect of {@link #initSymbols(SymbolFactory)}
	 *
	 * @returns all guards.
	 */
	public List<Guard> allGuards() {
		return guardList;
	}

	private List<Guard> getGuardList() {
		LinkedList<Guard> gList = new LinkedList<>();
		List<Expression> allExpressions = EcoreUtil2.getAllContentsOfType(condition, Expression.class);
		allExpressions.add(condition);
		EObject conditionContainer = condition.eContainer();

		for (Expression expr : allExpressions) {
			Guard guard = GuardFactory.create(conditionContainer, expr, negate);
			if (guard != null) {
				gList.add(guard);
			}
		}
		return gList;
	}
}
