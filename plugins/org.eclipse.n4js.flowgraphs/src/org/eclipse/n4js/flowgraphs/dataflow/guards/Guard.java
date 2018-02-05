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

import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;

/**
 * {@link Guard} hold information about {@link Expression}s that are used as a precondition of a control flow branch,
 * e.g. in an {@link IfStatement}:<br/>
 * {@code if (guard) ...}
 */
public class Guard {
	/** The {@link Expression} that is used as a condition of the guard */
	final public Expression condition;
	/** The type of the guard */
	final public GuardType type;
	/** The guarantee of the guard */
	final public GuardAssertion asserts;
	/**   */
	final public ControlFlowElement symbolCFE;
	/** The symbol that is guarded */
	private Symbol symbol;

	/** Constructor */
	public Guard(Expression condition, GuardType type, GuardAssertion asserts, ControlFlowElement symbolCFE) {
		this.condition = condition;
		this.type = type;
		this.asserts = asserts;
		this.symbolCFE = symbolCFE;
	}

	/** Initialized the {@link Symbol} of this guard. */
	final void initSymbol(SymbolFactory symbolFactory) {
		symbol = getSymbol(symbolFactory);
		if (symbol != null && !symbol.isVariableSymbol()) {
			symbol = null;
		}
	}

	/**   */
	protected Symbol getSymbol(SymbolFactory symbolFactory) {
		return symbolFactory.create(symbolCFE);
	}

	/** @return the symbol that is guarded */
	public Symbol getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		String str = "";
		str += type.toString();
		str += " " + asserts.toString();
		str += " on " + symbol.getName();
		return str;
	}

}
