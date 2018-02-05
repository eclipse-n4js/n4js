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
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfThisLiteral extends Symbol {
	final ThisLiteral tl;

	SymbolOfThisLiteral(ThisLiteral tl) {
		this.tl = tl;
	}

	@Override
	public ThisLiteral getASTLocation() {
		return tl;
	}

	@Override
	public String getName() {
		return "this literal";
	}

	@Override
	public EObject getDeclaration() {
		N4ClassDeclaration classDef = EcoreUtil2.getContainerOfType(tl, N4ClassDeclaration.class);
		return classDef;
	}

}
