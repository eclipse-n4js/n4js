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
	final public Symbol symbol;
	final public GuardType type;
	final public GuardAssertion asserts;

	public Guard(Expression condition, GuardType type, Symbol symbol, GuardAssertion asserts) {
		this.condition = condition;
		this.type = type;
		this.symbol = symbol;
		this.asserts = asserts;
	}

}
