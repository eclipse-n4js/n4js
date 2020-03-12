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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.JSON.util.JSONSwitch;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;

/**
 * Returns a proper document symbol for elements of a JSON document.
 */
public class JSONDocumentSymbolNameProvider extends DocumentSymbolNameProvider {

	private static class Impl extends JSONSwitch<String> {
		@Override
		public String caseNameValuePair(NameValuePair object) {
			JSONValue value = object.getValue();
			if (value != null && !value.isContainer()) {
				String valueAsString = doSwitch(value);
				if (valueAsString != null) {
					return String.format("%s : %s", object.getName(), valueAsString);
				}
			}
			return object.getName();
		}

		@Override
		public String caseJSONObject(JSONObject object) {
			return "<object>";
		}

		@Override
		public String caseJSONArray(JSONArray object) {
			return "<array>";
		}

		@Override
		public String caseJSONNumericLiteral(JSONNumericLiteral object) {
			return object.getValue().toString();
		}

		@Override
		public String caseJSONStringLiteral(JSONStringLiteral object) {
			return String.format("\"%s\"", object.getValue());
		}

		@Override
		public String caseJSONBooleanLiteral(JSONBooleanLiteral object) {
			return Boolean.toString(object.isBooleanValue());
		}

		@Override
		public String caseJSONNullLiteral(JSONNullLiteral object) {
			return "null";
		}
	}

	private final JSONSwitch<String> impl = new Impl();

	@Override
	public String getName(EObject object) {
		return impl.doSwitch(object);
	}

}
