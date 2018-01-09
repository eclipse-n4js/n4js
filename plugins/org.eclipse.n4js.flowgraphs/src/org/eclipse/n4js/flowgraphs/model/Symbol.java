/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

@SuppressWarnings("javadoc")
abstract public class Symbol {
	private Object cachedKey;
	private Object cachedKeyContextFree;

	/**
	 * @param expression
	 *            an expression such as {@link IdentifierRef} that represents this {@link Symbol}
	 * @return true iff this symbol is representing the given expression (aliasing is ignored)
	 */
	public boolean is(Expression expression) {
		return false;
	}

	/**
	 * @param alias
	 *            the {@link Symbol} in question
	 * @return true iff this {@link Symbol} is identical to the given {@link Symbol}
	 */
	public boolean is(Symbol alias) {
		return equals(alias);
	}

	/** @return true iff this {@link Symbol} is a {@link NumericLiteral} of value {@code 0} */
	public boolean isZeroLiteral() {
		return false;
	}

	/** @return true iff this {@link Symbol} is the {@link NullLiteral} */
	public boolean isNullLiteral() {
		return false;
	}

	/** @return true iff this {@link Symbol} is an {@link IdentifierRef} to built-in {@code undefined} */
	public boolean isUndefinedLiteral() {
		return false;
	}

	/** @return true iff this {@link Symbol} is neither an undefined, a null, nor a zero literal */
	public boolean isVariableSymbol() {
		return !isUndefinedLiteral() && !isNullLiteral() && !isZeroLiteral();
	}

	/**
	 * @param symbol
	 *            another {@link Symbol}
	 * @return true iff this and the given {@link Symbol} access the same property
	 */
	public boolean isStrucuralAlias(Symbol symbol) {
		return false;
	}

	/** @return the declaration, or null iff the declaration is available only with help of the type system */
	public EObject getDeclaration() {
		return null;
	}

	/**
	 * @return the context of the symbol, e.g. the target expression of a {@link ParameterizedPropertyAccessExpression}
	 */
	public Expression getContext() {
		return null;
	}

	/** @return the context symbol, e.g. the symbol of the target expression */
	public Symbol getContextSymbol() {
		return null;
	}

	public List<Symbol> getAllContextSymbols() {
		List<Symbol> contextSymbols = new LinkedList<>();

		return contextSymbols;
	}

	/** @return the name of this {@link Symbol} */
	abstract public String getName();

	/** @return the location in the AST from which this {@link Symbol} was created */
	abstract public ControlFlowElement getASTLocation();

	/** @return the same key for {@link Symbol}s to the same variable. The key is cached. */
	protected Object createSymbolKey() {
		return createContextFreeSymbolKey();
	}

	/** @return the same key for {@link Symbol}s to the same variable. The key is cached. */
	protected Object createContextFreeSymbolKey() {
		Object key = getDeclaration();
		if (key == null) {
			key = getASTLocation();
		}
		return key;
	}

	public final Object getSymbolKey() {
		if (cachedKey == null) {
			cachedKey = createSymbolKey();
		}
		return cachedKey;
	}

	public final Object getContextFreeSymbolKey() {
		if (cachedKeyContextFree == null) {
			cachedKeyContextFree = createContextFreeSymbolKey();
		}
		return cachedKeyContextFree;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Symbol))
			return false;

		Symbol s = (Symbol) obj;
		return getSymbolKey().equals(s.getSymbolKey());
	}

	@Override
	public int hashCode() {
		return getSymbolKey().hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
