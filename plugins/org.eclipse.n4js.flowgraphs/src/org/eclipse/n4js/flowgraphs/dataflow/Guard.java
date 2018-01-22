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

import org.eclipse.n4js.n4JS.Expression;

/**
 *
 */
public class Guard {
	final public Expression condition;
	final public GuardType type;
	final public GuardAssertion asserts;
	final public Symbol symbol;
	final public Expression context;

	public Guard(Expression condition, GuardType type, GuardAssertion asserts, Symbol symbol) {
		this(condition, type, asserts, symbol, null);
	}

	public Guard(Expression condition, GuardType type, GuardAssertion asserts, Symbol symbol,
			Expression context) {

		this.condition = condition;
		this.type = type;
		this.asserts = asserts;
		this.symbol = symbol;
		this.context = context;
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
