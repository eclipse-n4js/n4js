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
package org.eclipse.n4js.flowgraphs.dataflow;

import org.eclipse.n4js.n4JS.Expression;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfUndefined extends Symbol {
	final Expression e;

	SymbolOfUndefined(Expression e) {
		this.e = e;
	}

	@Override
	public Expression getASTLocation() {
		return e;
	}

	@Override
	public String getName() {
		return "undefined";
	}

	@Override
	public boolean isUndefinedLiteral() {
		return true;
	}

}
