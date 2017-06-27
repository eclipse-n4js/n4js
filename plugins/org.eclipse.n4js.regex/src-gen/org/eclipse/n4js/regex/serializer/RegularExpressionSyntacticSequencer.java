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
package org.eclipse.n4js.regex.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class RegularExpressionSyntacticSequencer extends AbstractSyntacticSequencer {

	protected RegularExpressionGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Disjunction_VerticalLineKeyword_0_1_1_0_a;
	protected AbstractElementAlias match_Disjunction_VerticalLineKeyword_0_1_1_0_p;
	protected AbstractElementAlias match_Disjunction_VerticalLineKeyword_1_1_0_a;
	protected AbstractElementAlias match_Disjunction_VerticalLineKeyword_1_1_0_p;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (RegularExpressionGrammarAccess) access;
		match_Disjunction_VerticalLineKeyword_0_1_1_0_a = new TokenAlias(true, true, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0());
		match_Disjunction_VerticalLineKeyword_0_1_1_0_p = new TokenAlias(true, false, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0());
		match_Disjunction_VerticalLineKeyword_1_1_0_a = new TokenAlias(true, true, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0());
		match_Disjunction_VerticalLineKeyword_1_1_0_p = new TokenAlias(true, false, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (ruleCall.getRule() == grammarAccess.getNOT_WORD_BOUNDARYRule())
			return getNOT_WORD_BOUNDARYToken(semanticObject, ruleCall, node);
		else if (ruleCall.getRule() == grammarAccess.getWORD_BOUNDARYRule())
			return getWORD_BOUNDARYToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * terminal NOT_WORD_BOUNDARY:
	 * 	'\\' 'B'
	 * ;
	 */
	protected String getNOT_WORD_BOUNDARYToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "\\B";
	}
	
	/**
	 * terminal WORD_BOUNDARY:
	 * 	'\\' 'b'
	 * ;
	 */
	protected String getWORD_BOUNDARYToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "\\b";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_Disjunction_VerticalLineKeyword_0_1_1_0_a.equals(syntax))
				emit_Disjunction_VerticalLineKeyword_0_1_1_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Disjunction_VerticalLineKeyword_0_1_1_0_p.equals(syntax))
				emit_Disjunction_VerticalLineKeyword_0_1_1_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Disjunction_VerticalLineKeyword_1_1_0_a.equals(syntax))
				emit_Disjunction_VerticalLineKeyword_1_1_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Disjunction_VerticalLineKeyword_1_1_0_p.equals(syntax))
				emit_Disjunction_VerticalLineKeyword_1_1_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     '|'*
	 *
	 * This ambiguous syntax occurs at:
	 *     elements+=Alternative (ambiguity) (rule end)
	 */
	protected void emit_Disjunction_VerticalLineKeyword_0_1_1_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '|'+
	 *
	 * This ambiguous syntax occurs at:
	 *     elements+=Alternative (ambiguity) elements+=Alternative
	 *     {Disjunction.elements+=} (ambiguity) (rule end)
	 *     {Disjunction.elements+=} (ambiguity) elements+=Alternative
	 */
	protected void emit_Disjunction_VerticalLineKeyword_0_1_1_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '|'*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) (rule start)
	 *     elements+=Alternative (ambiguity) (rule end)
	 */
	protected void emit_Disjunction_VerticalLineKeyword_1_1_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '|'+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) elements+=Alternative
	 *     elements+=Alternative (ambiguity) elements+=Alternative
	 */
	protected void emit_Disjunction_VerticalLineKeyword_1_1_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
