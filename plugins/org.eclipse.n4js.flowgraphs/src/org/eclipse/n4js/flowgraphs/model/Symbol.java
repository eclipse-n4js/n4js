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
		return false;
	}

	/**
	 * @param symbol
	 *            the {@link Symbol} in question
	 * @return true iff this {@link Symbol} is aliased with the given {@link Symbol}
	 */
	public boolean alias(Symbol symbol) {
		return false;
	}

	/**
	 * @return the declaration, or null iff the declaration is available only with help of the type system
	 */
	public EObject getDeclaration() {
		return null;
	}

	// TODO GH-235

	// final Object token;
	//
	// abstract public boolean exists();
	//
	// abstract public AccessType getAccessType();
	//
	// abstract public String getName();
	//
	// abstract public boolean isLocal();
	//
	// abstract public boolean isPrimitive();
	//
	// abstract public String getTypeName();
	//
	// @Override
	// public String toString() {
	// return this.getName();
	// }
	//
	// public Object getToken() {
	// return token;
	// }
	//
	// public Symbol(Object token) {
	// this.token = token;
	// }
	//
	// public static class SymbolNew extends Symbol {
	// final private Expression astExpression;
	// final private String name;
	//
	// public SymbolNew(Object token, Expression astExpression, String name) {
	// super(token);
	// this.astExpression = astExpression;
	// this.name = name;
	// }
	//
	// @Override
	// public boolean exists() {
	// return false;
	// }
	//
	// @Override
	// public AccessType getAccessType() {
	// return AccessType.local;
	// }
	//
	// @Override
	// public String getName() {
	// return name;
	// }
	//
	// @Override
	// public boolean isLocal() {
	// return true;
	// }
	//
	// @Override
	// public boolean isPrimitive() {
	// return Symbols.isPrimitive(token);
	// }
	//
	// @Override
	// public String getTypeName() {
	// return Symbols.getReturnTypeName(astExpression);
	// }
	// }
	//
	// public static class SymbolWithToken extends Symbol {
	//
	// public SymbolWithToken(Object token) {
	// super(token);
	// }
	//
	// @Override
	// public boolean exists() {
	// return true;
	// }
	//
	// @Override
	// public AccessType getAccessType() {
	// if (isLocal())
	// return AccessType.local;
	// return AccessType.other;
	// }
	//
	// @Override
	// public String getName() {
	// return Symbols.getSymbolName(token);
	// }
	//
	// @Override
	// public boolean isLocal() {
	// return Symbols.isLocalSymbol(token);
	// }
	//
	// @Override
	// public boolean isPrimitive() {
	// return Symbols.isPrimitive(token);
	// }
	//
	// @Override
	// public String getTypeName() {
	// return Symbols.getTypeName(token);
	// }
	// }
}
