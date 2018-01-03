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

	/** @return the name of this {@link Symbol} */
	abstract public String getName();

	/** @return the location in the AST from which this {@link Symbol} was created */
	abstract public ControlFlowElement getASTLocation();

	/** @return the same key for {@link Symbol}s to the same variable. The key is cached. */
	protected Object getSymbolKey() {
		Object key = getDeclaration();
		if (key == null) {
			key = getASTLocation();
		}
		return key;
	}

	private Object internalGetSymbolKey() {
		if (cachedKey == null) {
			cachedKey = getSymbolKey();
		}
		return cachedKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Symbol))
			return false;

		Symbol s = (Symbol) obj;
		return internalGetSymbolKey().equals(s.internalGetSymbolKey());
	}

	@Override
	public int hashCode() {
		return internalGetSymbolKey().hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}
}
