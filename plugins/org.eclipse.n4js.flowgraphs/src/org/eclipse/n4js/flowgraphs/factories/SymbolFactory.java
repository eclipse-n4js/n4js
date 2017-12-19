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

import java.util.HashMap;
import java.util.Map;

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
	static private Map<Symbol, Symbol> symbols = new HashMap<>();

	/**  */
	public static Symbol create(VariableDeclaration vd) {
		Symbol newSymbol = new SymbolOfVariableDeclaration(vd);
		if (!symbols.containsKey(newSymbol)) {
			symbols.put(newSymbol, newSymbol);
		}
		Symbol symbol = symbols.get(newSymbol);
		return symbol;
	}

	/** */
	public static Symbol create(Expression expr) {
		Symbol newSymbol = null;
		if (expr instanceof IdentifierRef) {
			newSymbol = new SymbolOfIdentifierRef((IdentifierRef) expr);
		}
		if (expr instanceof ParameterizedPropertyAccessExpression) {
			newSymbol = new SymbolOfParameterizedPropertyAccessExpression((ParameterizedPropertyAccessExpression) expr);
		}
		if (expr instanceof IndexedAccessExpression) {
			newSymbol = new SymbolOfIndexedAccessExpression((IndexedAccessExpression) expr);
		}

		Symbol symbol = null;
		if (newSymbol != null) {
			if (!symbols.containsKey(newSymbol)) {
				symbols.put(newSymbol, newSymbol);
			}
			symbol = symbols.get(newSymbol);
		}

		return symbol;
	}

	static class SymbolOfVariableDeclaration extends Symbol {
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
	}

	static class SymbolOfIdentifierRef extends Symbol {
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
	}

	static class SymbolOfParameterizedPropertyAccessExpression extends Symbol {
		final ParameterizedPropertyAccessExpression ppae;

		SymbolOfParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression ppae) {
			this.ppae = ppae;
		}

		@Override
		public ParameterizedPropertyAccessExpression getASTLocation() {
			return ppae;
		}

		@Override
		public String getName() {
			return ppae.getProperty().getName();
		}
	}

	static class SymbolOfIndexedAccessExpression extends Symbol {
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
}
