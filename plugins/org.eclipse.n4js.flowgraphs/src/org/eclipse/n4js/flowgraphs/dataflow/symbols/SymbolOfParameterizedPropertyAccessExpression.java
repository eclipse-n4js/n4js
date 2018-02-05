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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolOfParameterizedPropertyAccessExpression extends Symbol {
	private final SymbolFactory symbolFactory;
	final ParameterizedPropertyAccessExpression ppae;
	final Symbol contextSymbol;

	SymbolOfParameterizedPropertyAccessExpression(SymbolFactory symbolFactory,
			ParameterizedPropertyAccessExpression ppae) {

		this.symbolFactory = symbolFactory;
		this.ppae = ppae;
		this.contextSymbol = getContextSymbol();
	}

	@Override
	public ParameterizedPropertyAccessExpression getASTLocation() {
		return ppae;
	}

	@Override
	public String getName() {
		String name = ppae.getProperty().getName();
		Expression tgtExpr = ppae.getTarget();
		Symbol tgtSymbol = symbolFactory.create(tgtExpr);
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
	public Symbol getContextSymbol() {
		if (contextSymbol != null) {
			return contextSymbol;
		}
		return symbolFactory.create(getContext());
	}

	@Override
	protected Object createSymbolKey() {
		List<Object> keyChain = new LinkedList<>();
		keyChain.add(getDeclaration());
		Expression lastContext = getContext();
		Symbol tgtSymbol = getContextSymbol();
		while (tgtSymbol != null) {
			keyChain.add(tgtSymbol.getDeclaration());
			lastContext = tgtSymbol.getContext();
			tgtSymbol = tgtSymbol.getContextSymbol();
		}
		if (lastContext != null) {
			keyChain.add(lastContext);
		}
		int hash = Objects.hash(keyChain.toArray(new Object[keyChain.size()]));
		return hash;
	}

	@Override
	public boolean isStrucuralAlias(Symbol symbol) {
		if (!(symbol instanceof SymbolOfParameterizedPropertyAccessExpression))
			return false;
		SymbolOfParameterizedPropertyAccessExpression s = (SymbolOfParameterizedPropertyAccessExpression) symbol;

		return ppae.getProperty().equals(s.ppae.getProperty());
	}

}
