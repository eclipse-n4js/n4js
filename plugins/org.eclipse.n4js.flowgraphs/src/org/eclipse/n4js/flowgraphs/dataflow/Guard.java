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
	/** The symbol that is guarded */
	final public Symbol symbol;
	/** The context that is guaranteed, such as the right hand side of an {@code instanceof} {@link Expression} */
	final public Expression context;

	/** Constructor */
	public Guard(Expression condition, GuardType type, GuardAssertion asserts, Symbol symbol) {
		this(condition, type, asserts, symbol, null);
	}

	/** Constructor */
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
