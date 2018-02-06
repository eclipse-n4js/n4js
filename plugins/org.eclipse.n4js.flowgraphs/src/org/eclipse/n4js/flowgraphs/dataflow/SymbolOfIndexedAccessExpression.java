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

import org.eclipse.n4js.n4JS.IndexedAccessExpression;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfIndexedAccessExpression extends Symbol {
	final IndexedAccessExpression iae;

	SymbolOfIndexedAccessExpression(IndexedAccessExpression iae) {
		this.iae = iae;
	}

	@Override
	public IndexedAccessExpression getASTLocation() {
		return iae;
	}

	@Override
	public String getName() {
		return "Array Access";
	}

}
