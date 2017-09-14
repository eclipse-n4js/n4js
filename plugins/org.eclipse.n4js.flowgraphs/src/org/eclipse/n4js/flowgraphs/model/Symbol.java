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

@SuppressWarnings("javadoc")
abstract public class Symbol implements GraphElement {
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
