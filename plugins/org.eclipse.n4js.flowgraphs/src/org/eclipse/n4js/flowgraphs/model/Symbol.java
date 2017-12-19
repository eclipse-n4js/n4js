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

@SuppressWarnings("javadoc")
abstract public class Symbol {

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

	/**
	 * @return the declaration, or null iff the declaration is available only with help of the type system
	 */
	public EObject getDeclaration() {
		return null;
	}

	/** @return the name of this {@link Symbol} */
	abstract public String getName();

	/** @return the location in the AST from which this {@link Symbol} was created */
	abstract public ControlFlowElement getASTLocation();

	protected Object getSymbolKey() {
		Object key = getDeclaration();
		if (key == null) {
			return getASTLocation();
		}
		return key;
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
