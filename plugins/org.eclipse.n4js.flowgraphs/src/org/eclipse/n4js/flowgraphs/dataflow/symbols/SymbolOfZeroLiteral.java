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
package org.eclipse.n4js.flowgraphs.dataflow.symbols;

import java.math.BigDecimal;

import org.eclipse.n4js.n4JS.NumericLiteral;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfZeroLiteral extends Symbol {
	final NumericLiteral nl;

	SymbolOfZeroLiteral(NumericLiteral nl) {
		this.nl = nl;
	}

	@Override
	public NumericLiteral getASTLocation() {
		return nl;
	}

	@Override
	public String getName() {
		return nl.getValueAsString();
	}

	@Override
	public boolean isZeroLiteral() {
		return nl.getValue().equals(new BigDecimal(0));
	}

}
