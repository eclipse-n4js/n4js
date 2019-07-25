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

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.n4js.json.ide.contentassist.antlr.internal.InternalJSONParser;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class JSONParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(JSONGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, JSONGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getJSONObjectAccess().getAlternatives(), "rule__JSONObject__Alternatives");
			builder.put(grammarAccess.getJSONArrayAccess().getAlternatives(), "rule__JSONArray__Alternatives");
			builder.put(grammarAccess.getJSONValueAccess().getAlternatives(), "rule__JSONValue__Alternatives");
			builder.put(grammarAccess.getJSONBooleanLiteralAccess().getAlternatives_1(), "rule__JSONBooleanLiteral__Alternatives_1");
			builder.put(grammarAccess.getJSONDocumentAccess().getGroup(), "rule__JSONDocument__Group__0");
			builder.put(grammarAccess.getJSONObjectAccess().getGroup_0(), "rule__JSONObject__Group_0__0");
			builder.put(grammarAccess.getJSONObjectAccess().getGroup_0_2(), "rule__JSONObject__Group_0_2__0");
			builder.put(grammarAccess.getJSONObjectAccess().getGroup_1(), "rule__JSONObject__Group_1__0");
			builder.put(grammarAccess.getNameValuePairAccess().getGroup(), "rule__NameValuePair__Group__0");
			builder.put(grammarAccess.getJSONArrayAccess().getGroup_0(), "rule__JSONArray__Group_0__0");
			builder.put(grammarAccess.getJSONArrayAccess().getGroup_0_2(), "rule__JSONArray__Group_0_2__0");
			builder.put(grammarAccess.getJSONArrayAccess().getGroup_1(), "rule__JSONArray__Group_1__0");
			builder.put(grammarAccess.getJSONBooleanLiteralAccess().getGroup(), "rule__JSONBooleanLiteral__Group__0");
			builder.put(grammarAccess.getJSONNullLiteralAccess().getGroup(), "rule__JSONNullLiteral__Group__0");
			builder.put(grammarAccess.getJSONDocumentAccess().getContentAssignment_1(), "rule__JSONDocument__ContentAssignment_1");
			builder.put(grammarAccess.getJSONObjectAccess().getNameValuePairsAssignment_0_1(), "rule__JSONObject__NameValuePairsAssignment_0_1");
			builder.put(grammarAccess.getJSONObjectAccess().getNameValuePairsAssignment_0_2_1(), "rule__JSONObject__NameValuePairsAssignment_0_2_1");
			builder.put(grammarAccess.getNameValuePairAccess().getNameAssignment_0(), "rule__NameValuePair__NameAssignment_0");
			builder.put(grammarAccess.getNameValuePairAccess().getValueAssignment_2(), "rule__NameValuePair__ValueAssignment_2");
			builder.put(grammarAccess.getJSONArrayAccess().getElementsAssignment_0_1(), "rule__JSONArray__ElementsAssignment_0_1");
			builder.put(grammarAccess.getJSONArrayAccess().getElementsAssignment_0_2_1(), "rule__JSONArray__ElementsAssignment_0_2_1");
			builder.put(grammarAccess.getJSONStringLiteralAccess().getValueAssignment(), "rule__JSONStringLiteral__ValueAssignment");
			builder.put(grammarAccess.getJSONNumericLiteralAccess().getValueAssignment(), "rule__JSONNumericLiteral__ValueAssignment");
			builder.put(grammarAccess.getJSONBooleanLiteralAccess().getBooleanValueAssignment_1_0(), "rule__JSONBooleanLiteral__BooleanValueAssignment_1_0");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private JSONGrammarAccess grammarAccess;

	@Override
	protected InternalJSONParser createParser() {
		InternalJSONParser result = new InternalJSONParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
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
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
