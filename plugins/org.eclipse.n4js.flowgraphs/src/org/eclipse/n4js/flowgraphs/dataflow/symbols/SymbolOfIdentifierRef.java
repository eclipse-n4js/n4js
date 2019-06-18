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
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfIdentifierRef extends Symbol {
	final IdentifiableElement id;
	final IdentifierRef ir;

	SymbolOfIdentifierRef(IdentifiableElement id, IdentifierRef ir) {
		this.id = id;
		this.ir = ir;
	}

	@Override
	public IdentifierRef getASTLocation() {
		return ir;
	}

	@Override
	public String getName() {
		return id.getName();
	}

	@Override
	public EObject getDeclaration() {
		/*
		 * Implementation note:
		 *
		 * Conceptually, multiple identifier references (i.e. IdentifierRefs) can point to the same variable. Also, all
		 * identifier references that point to the same variable have the same declaration site (i.e. the AST node that
		 * represents the identifiable element's declaration). This method is intended to return a unique identifier for
		 * an IdentifierRef which is the declaration site.
		 *
		 * However, retrieving the declaration can cause the loading of other n4js modules, for instance when an
		 * exported declaration is imported and used in the current module. This loading triggers loading from source
		 * and can cause side effects. Hence, instead of the declaration site, the IdentifiableElement is used as a
		 * unique identifier for variables.
		 */
		return id;
	}

	@Override
	public boolean isUndefinedLiteral() {
		return "undefined".equals(getName());
	}

}
