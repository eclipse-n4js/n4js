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
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.RelationalExpression;

/**
 */
public class InstanceofGuard extends Guard {
	/**  */
	final public RelationalExpression re;

	/** Constructor */
	public InstanceofGuard(RelationalExpression re, GuardAssertion asserts) {
		super(re, GuardType.InstanceOf, asserts, null);
		this.re = re;
	}

	/** Initialized the {@link Symbol} of this guard. */
	@Override
	protected Symbol getSymbol(SymbolFactory symbolFactory) {
		Expression lhs = re.getLhs();
		Symbol symbol = symbolFactory.create(lhs);
		if (symbol != null && symbol.isVariableSymbol()) {
			return symbol;
		}
		Expression rhs = re.getRhs();
		symbol = symbolFactory.create(rhs);
		if (symbol != null && symbol.isVariableSymbol()) {
			return symbol;
		}
		return null;
	}
}
