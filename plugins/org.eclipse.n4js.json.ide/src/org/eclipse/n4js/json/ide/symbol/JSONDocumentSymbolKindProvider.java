/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ide.symbol;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;

/**
 *
 */
public class JSONDocumentSymbolKindProvider extends DocumentSymbolKindProvider {

	@Override
	public SymbolKind getSymbolKind(EObject object) {
		if (object instanceof NameValuePair) {
			JSONValue value = ((NameValuePair) object).getValue();
			if (value != null && !(value instanceof JSONStringLiteral)) {
				return getSymbolKind(value);
			}
		}
		return super.getSymbolKind(object);
	}

	@Override
	protected SymbolKind getSymbolKind(EClass clazz) {
		if (clazz.getEPackage() == JSONPackage.eINSTANCE) {
			switch (clazz.getClassifierID()) {
			case JSONPackage.JSON_DOCUMENT: {
				return SymbolKind.File;
			}
			case JSONPackage.JSON_OBJECT: {
				return SymbolKind.Object;
			}
			case JSONPackage.JSON_ARRAY: {
				return SymbolKind.Array;
			}
			case JSONPackage.NAME_VALUE_PAIR: {
				return SymbolKind.Field;
			}
			case JSONPackage.JSON_STRING_LITERAL: {
				// The outline does not look like something super exciting with this symbol
				// kind, but its likely the best choice here
				return SymbolKind.String;
			}
			case JSONPackage.JSON_NUMERIC_LITERAL: {
				return SymbolKind.Number;
			}
			case JSONPackage.JSON_BOOLEAN_LITERAL: {
				return SymbolKind.Number;
			}
			case JSONPackage.JSON_NULL_LITERAL: {
				return SymbolKind.Null;
			}
			}
		}
		throw new UnsupportedOperationException("Not supported for " + clazz.getName());
	}
}
