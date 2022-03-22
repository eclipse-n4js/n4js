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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfVariableDeclaration extends Symbol {
	final VariableDeclaration vd;

	SymbolOfVariableDeclaration(VariableDeclaration vd) {
		this.vd = vd;
	}

	@Override
	public VariableDeclaration getASTLocation() {
		return vd;
	}

	@Override
	public String getName() {
		return vd.getName();
	}

	@Override
	public EObject getDeclaration() {
		return vd;
	}

	@Override
	protected Object createSymbolKey() {
		return vd.getDefinedVariable();
	}
}
