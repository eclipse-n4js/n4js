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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolFactory {
	static private Symbol undefined;
	static private Map<Symbol, Symbol> symbols = new HashMap<>();

	/**  */
	public static Symbol create(VariableDeclaration vd) {
		Symbol newSymbol = new SymbolOfVariableDeclaration(vd);
		symbols.putIfAbsent(newSymbol, newSymbol);
		Symbol symbol = symbols.get(newSymbol);
		return symbol;
	}

	/** */
	public static Symbol create(Expression expr) {
		Symbol newSymbol = null;
		if (expr instanceof IdentifierRef) {
			newSymbol = new SymbolOfIdentifierRef((IdentifierRef) expr);
		} else if (expr instanceof ParameterizedPropertyAccessExpression) {
			newSymbol = new SymbolOfParameterizedPropertyAccessExpression((ParameterizedPropertyAccessExpression) expr);
		} else if (expr instanceof IndexedAccessExpression) {
			newSymbol = new SymbolOfIndexedAccessExpression((IndexedAccessExpression) expr);
		} else if (expr instanceof NullLiteral) {
			newSymbol = new SymbolOfNullLiteral((NullLiteral) expr);
		} else if (expr instanceof NumericLiteral && new Integer(0).equals(((NumericLiteral) expr).getValue())) {
			newSymbol = new SymbolOfZeroLiteral((NumericLiteral) expr);
		}

		Symbol symbol = null;
		if (newSymbol != null) {
			symbols.putIfAbsent(newSymbol, newSymbol);
			symbol = symbols.get(newSymbol);
		}

		return symbol;
	}

	/** @return true iff the given {@link Expression} */
	public static boolean isUndefined(Expression expr) {
		Symbol undef = SymbolFactory.create(expr);
		return undef != null && undef.isUndefinedLiteral();
	}

	/** @return a {@link Symbol} that represents {@code undefined} */
	public static Symbol getUndefined() {
		if (undefined == null) {
			IdentifiableElement ieUndefined = TypesFactory.eINSTANCE.createIdentifiableElement();
			IdentifierRef irUndefined = N4JSFactory.eINSTANCE.createIdentifierRef();
			irUndefined.setId(ieUndefined);
			ieUndefined.setName("undefined");
			undefined = SymbolFactory.create(irUndefined);
		}
		return undefined;
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

		@Override
		public boolean isUndefinedLiteral() {
			IdentifiableElement id = ir.getId();
			if (id == null) {
				return false;
			}
			return "undefined".equals(id.getName());
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
			String name = ppae.getProperty().getName();
			Expression tgtExpr = ppae.getTarget();
			Symbol tgtSymbol = SymbolFactory.create(tgtExpr);
			if (tgtSymbol != null) {
				name = tgtSymbol.getName() + "." + name;
			}

			return name;
		}

		@Override
		public EObject getDeclaration() {
			return ppae.getProperty();
		}

		@Override
		public Expression getContext() {
			return ppae.getTarget();
		}

		@Override
		protected Object createSymbolKey() {
			List<Object> keyChain = new LinkedList<>();
			keyChain.add(getDeclaration());
			Expression tgtExpr = getContext();
			Symbol tgtSymbol = SymbolFactory.create(tgtExpr);
			while (tgtSymbol != null) {
				keyChain.add(tgtSymbol.getDeclaration());
				tgtExpr = tgtSymbol.getContext();
				tgtSymbol = SymbolFactory.create(tgtExpr);
			}
			if (tgtExpr != null) {
				keyChain.add(tgtExpr);
			}
			int hash = Objects.hash(keyChain.toArray(new Object[keyChain.size()]));
			return hash;
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

	static class SymbolOfNullLiteral extends Symbol {
		final NullLiteral nl;

		SymbolOfNullLiteral(NullLiteral nl) {
			this.nl = nl;
		}

		@Override
		public NullLiteral getASTLocation() {
			return nl;
		}

		@Override
		public String getName() {
			return "null literal";
		}

		@Override
		public boolean isNullLiteral() {
			return true;
		}
	}

	static class SymbolOfZeroLiteral extends Symbol {
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
			return nl.getValue().equals(0);
		}
	}
}
