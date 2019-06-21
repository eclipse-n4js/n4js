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
package org.eclipse.n4js.flowgraphs.dataflow.symbols;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

/**
 * {@link Symbol}s provide variable binding. As a result, two different AST elements like {@link IdentifierRef}s that
 * refer to the same variable (due to scoping), are abstracted by the same {@link Symbol} instance. Also, special
 * variables such as {@code null}, {@code undefined} or {@code 0} refer to the same {@link Symbol} instance to simplify
 * reasoning.
 * <p>
 * {@link Symbol}s are created by the {@link SymbolFactory}.
 */
abstract public class Symbol {
	private Object cachedKey;

	/** Set of all containers that define (ie. write) this symbol. */
	final public Set<ControlFlowElement> definingContainers = new HashSet<>();

	/** @return the name of this {@link Symbol} */
	abstract public String getName();

	/** @return the location in the AST from which this {@link Symbol} was created */
	abstract public ControlFlowElement getASTLocation();

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

	/**
	 * <b>Note:</b> Do not resolve proxies during the CFG/DFG analyses. This is done beforehand only (see
	 * N4JSPostProcessor#postProcessN4JSResource(...) in step 1)
	 *
	 * @return the declaration, or null iff the declaration is available only with help of the type system
	 */
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

	/** @return the same key for {@link Symbol}s to the same variable. The key is cached. */
	protected Object createSymbolKey() {
		Object key = getDeclaration();
		if (key == null) {
			key = getASTLocation();
		}
		return key;
	}

	/** Internal method to populate {@link #definingContainers} */
	public void addDefinitionCFE(ControlFlowElement defCFE) {
		ControlFlowElement cfContainer = FGUtils.getCFContainer(defCFE);
		this.definingContainers.add(cfContainer);
	}

	/** @return a unique key of this {@link Symbol} */
	final public Object getSymbolKey() {
		if (cachedKey == null) {
			cachedKey = createSymbolKey();
		}
		return cachedKey;
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
