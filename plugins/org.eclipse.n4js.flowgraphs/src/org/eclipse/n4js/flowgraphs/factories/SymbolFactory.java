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
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolFactory {

	/**  */
	public static Symbol create(VariableDeclaration vd) {
		return new SymbolOfVariableDeclaration(vd);
	}

	/** */
	public static Symbol create(Expression expr) {
		if (expr instanceof IdentifierRef) {
			return new SymbolOfIdentifierRef((IdentifierRef) expr);
		}
		if (expr instanceof ParameterizedPropertyAccessExpression) {
			return new SymbolOfParameterizedPropertyAccessExpression((ParameterizedPropertyAccessExpression) expr);
		}
		if (expr instanceof IndexedAccessExpression) {
			return new SymbolOfIndexedAccessExpression((IndexedAccessExpression) expr);
		}
		return null;
	}

	static class SymbolOfVariableDeclaration extends Symbol {
		final VariableDeclaration vd;

		SymbolOfVariableDeclaration(VariableDeclaration vd) {
			this.vd = vd;
		}
	}

	static class SymbolOfIdentifierRef extends Symbol {
		final IdentifierRef ir;

		SymbolOfIdentifierRef(IdentifierRef ir) {
			this.ir = ir;
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
				// id instanceof FormalParameter
				// id instanceof VariableDeclaration
				return id;
			}
		}
	}

	static class SymbolOfParameterizedPropertyAccessExpression extends Symbol {
		final ParameterizedPropertyAccessExpression ppae;

		SymbolOfParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression ppae) {
			this.ppae = ppae;
		}
	}

	static class SymbolOfIndexedAccessExpression extends Symbol {
		final IndexedAccessExpression iae;

		SymbolOfIndexedAccessExpression(IndexedAccessExpression iae) {
			this.iae = iae;
		}
	}
}
