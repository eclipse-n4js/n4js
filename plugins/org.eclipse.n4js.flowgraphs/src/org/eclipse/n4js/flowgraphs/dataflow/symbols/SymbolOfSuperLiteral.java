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
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfSuperLiteral extends Symbol {
	final SuperLiteral sl;

	SymbolOfSuperLiteral(SuperLiteral sl) {
		this.sl = sl;
	}

	@Override
	public SuperLiteral getASTLocation() {
		return sl;
	}

	@Override
	public String getName() {
		return "super literal";
	}

	@Override
	public EObject getDeclaration() {
		N4ClassDeclaration classDef = EcoreUtil2.getContainerOfType(sl, N4ClassDeclaration.class);
		if (classDef != null) { // can be null in broken AST
			TypeReferenceNode<ParameterizedTypeRef> superTypeRefInAST = classDef.getSuperClassRef();
			if (superTypeRefInAST != null) {
				TypeRef superTypeRef = superTypeRefInAST.getTypeRef();
				return superTypeRef;
			}
		}
		return null;
	}

}
