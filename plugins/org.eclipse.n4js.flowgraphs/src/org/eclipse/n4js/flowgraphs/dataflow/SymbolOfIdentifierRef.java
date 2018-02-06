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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfIdentifierRef extends Symbol {
	final IdentifierRef ir;

	SymbolOfIdentifierRef(IdentifierRef ir) {
		this.ir = ir;
	}

	@Override
	public IdentifierRef getASTLocation() {
		return ir;
	}

	@Override
	public String getName() {
		return ir.getId().getName();
	}

	@Override
	public EObject getDeclaration() {
		VariableDeclaration varDecl = null;
		IdentifiableElement id = ir.getId();
		if (id instanceof TVariable) {
			TVariable tvar = (TVariable) id;
			varDecl = (VariableDeclaration) tvar.getAstElement();
			return varDecl;
		} else {
			// id instanceof FormalParameter, or
			// id instanceof VariableDeclaration
			return id;
		}
	}

	@Override
	public boolean isUndefinedLiteral() {
		IdentifiableElement id = ir.getId();
		if (id == null) {
			return false;
		}
		return "undefined".equals(id.getName());
	}

}
