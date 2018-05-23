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
package org.eclipse.n4js.json.ide.contentassist.antlr;

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.n4js.json.ide.contentassist.antlr.internal.InternalJSONParser;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class JSONParser extends AbstractContentAssistParser {

	@Inject
	private JSONGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalJSONParser createParser() {
		InternalJSONParser result = new InternalJSONParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getJSONObjectAccess().getAlternatives(), "rule__JSONObject__Alternatives");
					put(grammarAccess.getJSONArrayAccess().getAlternatives(), "rule__JSONArray__Alternatives");
					put(grammarAccess.getJSONValueAccess().getAlternatives(), "rule__JSONValue__Alternatives");
					put(grammarAccess.getJSONBooleanLiteralAccess().getAlternatives_1(), "rule__JSONBooleanLiteral__Alternatives_1");
					put(grammarAccess.getJSONDocumentAccess().getGroup(), "rule__JSONDocument__Group__0");
					put(grammarAccess.getJSONObjectAccess().getGroup_0(), "rule__JSONObject__Group_0__0");
					put(grammarAccess.getJSONObjectAccess().getGroup_0_2(), "rule__JSONObject__Group_0_2__0");
					put(grammarAccess.getJSONObjectAccess().getGroup_1(), "rule__JSONObject__Group_1__0");
					put(grammarAccess.getNameValuePairAccess().getGroup(), "rule__NameValuePair__Group__0");
					put(grammarAccess.getJSONArrayAccess().getGroup_0(), "rule__JSONArray__Group_0__0");
					put(grammarAccess.getJSONArrayAccess().getGroup_0_2(), "rule__JSONArray__Group_0_2__0");
					put(grammarAccess.getJSONArrayAccess().getGroup_1(), "rule__JSONArray__Group_1__0");
					put(grammarAccess.getJSONBooleanLiteralAccess().getGroup(), "rule__JSONBooleanLiteral__Group__0");
					put(grammarAccess.getJSONNullLiteralAccess().getGroup(), "rule__JSONNullLiteral__Group__0");
					put(grammarAccess.getJSONDocumentAccess().getContentAssignment_1(), "rule__JSONDocument__ContentAssignment_1");
					put(grammarAccess.getJSONObjectAccess().getNameValuePairsAssignment_0_1(), "rule__JSONObject__NameValuePairsAssignment_0_1");
					put(grammarAccess.getJSONObjectAccess().getNameValuePairsAssignment_0_2_1(), "rule__JSONObject__NameValuePairsAssignment_0_2_1");
					put(grammarAccess.getNameValuePairAccess().getNameAssignment_0(), "rule__NameValuePair__NameAssignment_0");
					put(grammarAccess.getNameValuePairAccess().getValueAssignment_2(), "rule__NameValuePair__ValueAssignment_2");
					put(grammarAccess.getJSONArrayAccess().getElementsAssignment_0_1(), "rule__JSONArray__ElementsAssignment_0_1");
					put(grammarAccess.getJSONArrayAccess().getElementsAssignment_0_2_1(), "rule__JSONArray__ElementsAssignment_0_2_1");
					put(grammarAccess.getJSONStringLiteralAccess().getValueAssignment(), "rule__JSONStringLiteral__ValueAssignment");
					put(grammarAccess.getJSONNumericLiteralAccess().getValueAssignment(), "rule__JSONNumericLiteral__ValueAssignment");
					put(grammarAccess.getJSONBooleanLiteralAccess().getBooleanValueAssignment_1_0(), "rule__JSONBooleanLiteral__BooleanValueAssignment_1_0");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_EOL", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public JSONGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(JSONGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
