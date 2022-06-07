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
package org.eclipse.n4js.json.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class JSONSyntacticSequencer extends AbstractSyntacticSequencer {

	protected JSONGrammarAccess grammarAccess;
	protected AbstractElementAlias match_JSONArray_CommaKeyword_3_q;
	protected AbstractElementAlias match_JSONObject_CommaKeyword_3_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (JSONGrammarAccess) access;
		match_JSONArray_CommaKeyword_3_q = new TokenAlias(false, true, grammarAccess.getJSONArrayAccess().getCommaKeyword_3());
		match_JSONObject_CommaKeyword_3_q = new TokenAlias(false, true, grammarAccess.getJSONObjectAccess().getCommaKeyword_3());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_JSONArray_CommaKeyword_3_q.equals(syntax))
				emit_JSONArray_CommaKeyword_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_JSONObject_CommaKeyword_3_q.equals(syntax))
				emit_JSONObject_CommaKeyword_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '[' (ambiguity) ']' (rule start)
	 *     elements+=JSONValue (ambiguity) ']' (rule end)
	 
	 * </pre>
	 */
	protected void emit_JSONArray_CommaKeyword_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     ','?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) '{' (ambiguity) '}' (rule start)
	 *     nameValuePairs+=NameValuePair (ambiguity) '}' (rule end)
	 
	 * </pre>
	 */
	protected void emit_JSONObject_CommaKeyword_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
